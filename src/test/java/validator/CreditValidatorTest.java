package validator;

import com.credit.simulator.enums.VehicleCondition;
import com.credit.simulator.enums.VehicleType;
import com.credit.simulator.model.CreditRequest;
import com.credit.simulator.validator.CreditValidator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

public class CreditValidatorTest {

    private final CreditValidator validator = new CreditValidator();
    private final int currentYear = Year.now().getValue();

    private CreditRequest baru(long loan, int tenure, long dp) {
        return new CreditRequest(VehicleType.MOBIL, VehicleCondition.BARU, currentYear,
            BigDecimal.valueOf(loan), tenure, BigDecimal.valueOf(dp));
    }

    private CreditRequest bekas(int year, long loan, int tenure, long dp) {
        return new CreditRequest(VehicleType.MOBIL, VehicleCondition.BEKAS, year,
            BigDecimal.valueOf(loan), tenure, BigDecimal.valueOf(dp));
    }

    private String errorOf(CreditRequest request) {
        return assertThrows(IllegalArgumentException.class, () -> validator.validate(request)).getMessage();
    }

    @Test
    void validRequest_doesNotThrow() {
        assertDoesNotThrow(() -> validator.validate(baru(1_000_000_000L, 6, 500_000_000L)));
    }

    @Test
    void loanAmount_exactlyOneBillion_isAllowed() {
        assertDoesNotThrow(() -> validator.validate(baru(1_000_000_000L, 3, 350_000_000L)));
    }

    @Test
    void loanAmount_aboveOneBillion_isRejected() {
        String msg = errorOf(baru(1_000_000_001L, 3, 500_000_000L));
        assertTrue(msg.contains("Total pinjaman"));
    }

    @Test
    void loanAmount_zero_isRejected() {
        assertTrue(errorOf(baru(0, 3, 0)).contains("Total pinjaman"));
    }

    @Test
    void loanAmount_negative_isRejected() {
        assertTrue(errorOf(baru(-1, 3, 0)).contains("Total pinjaman"));
    }

    @Test
    void tenor_boundaries1And6_areAllowed() {
        assertDoesNotThrow(() -> validator.validate(baru(100_000_000L, 1, 50_000_000L)));
        assertDoesNotThrow(() -> validator.validate(baru(100_000_000L, 6, 50_000_000L)));
    }

    @Test
    void tenor_zero_isRejected() {
        assertEquals("Tenor harus antara 1 dan 6", errorOf(baru(100_000_000L, 0, 50_000_000L)));
    }

    @Test
    void tenor_seven_isRejected() {
        assertEquals("Tenor harus antara 1 dan 6", errorOf(baru(100_000_000L, 7, 50_000_000L)));
    }


    @Test
    void vehicleYear_baru_currentYearAndLastYearAreAllowed() {
        CreditRequest thisYear = new CreditRequest(VehicleType.MOBIL, VehicleCondition.BARU, currentYear,
            BigDecimal.valueOf(100_000_000L), 3, BigDecimal.valueOf(50_000_000L));
        CreditRequest lastYear = new CreditRequest(VehicleType.MOBIL, VehicleCondition.BARU, currentYear - 1,
            BigDecimal.valueOf(100_000_000L), 3, BigDecimal.valueOf(50_000_000L));
        assertDoesNotThrow(() -> validator.validate(thisYear));
        assertDoesNotThrow(() -> validator.validate(lastYear));
    }

    @Test
    void vehicleYear_baru_olderThanLastYear_isRejected() {
        CreditRequest old = new CreditRequest(VehicleType.MOBIL, VehicleCondition.BARU, currentYear - 2,
            BigDecimal.valueOf(100_000_000L), 3, BigDecimal.valueOf(50_000_000L));
        String msg = errorOf(old);
        assertEquals("Tahun kendaraan baru minimal " + (currentYear - 1), msg);
    }

    @Test
    void vehicleYear_bekas_oldYearIsAllowed() {
        assertDoesNotThrow(() -> validator.validate(bekas(2000, 100_000_000L, 3, 25_000_000L)));
    }

    @Test
    void downPayment_baru_exactly35Percent_isAllowed() {
        assertDoesNotThrow(() -> validator.validate(baru(100_000_000L, 3, 35_000_000L)));
    }

    @Test
    void downPayment_baru_below35Percent_isRejected() {
        String msg = errorOf(baru(100_000_000L, 3, 34_999_999L));
        assertTrue(msg.startsWith("DP minimal Rp."), msg);
    }

    @Test
    void downPayment_bekas_exactly25Percent_isAllowed() {
        assertDoesNotThrow(() -> validator.validate(bekas(2020, 95_000_000L, 2, 25_000_000L)));
    }

    @Test
    void downPayment_bekas_below25Percent_isRejected() {
        String msg = errorOf(bekas(2020, 100_000_000L, 3, 24_999_999L));
        assertTrue(msg.startsWith("DP minimal Rp."), msg);
    }

    @Test
    void downPayment_bekas_30Percent_wouldBeRejectedForBaru() {
        assertDoesNotThrow(() -> validator.validate(bekas(2020, 100_000_000L, 3, 30_000_000L)));
        assertThrows(IllegalArgumentException.class,
            () -> validator.validate(baru(100_000_000L, 3, 30_000_000L)));
    }
}
