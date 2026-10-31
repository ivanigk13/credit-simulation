package service;

import com.credit.simulator.enums.VehicleCondition;
import com.credit.simulator.enums.VehicleType;
import com.credit.simulator.model.CreditRequest;
import com.credit.simulator.model.CreditResponse;
import com.credit.simulator.model.YearlyCreditDetail;
import com.credit.simulator.service.CreditServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CreditServiceImplTest {

    private final CreditServiceImpl service = new CreditServiceImpl();

    private CreditRequest defaultRequest() {
        CreditRequest request = new CreditRequest();
        request.setVehicleType(VehicleType.MOBIL);
        request.setVehicleCondition(VehicleCondition.BARU);
        request.setVehicleYear(2025);
        request.setTotalLoanAmount(BigDecimal.valueOf(100_000_000));
        request.setDownPayment(BigDecimal.valueOf(25_000_000));
        request.setLoanTenure(3);

        return request;
    }

    @Test
    void calculateNewMobile_shouldReturnResponse() {
        CreditResponse response = service.calculate(defaultRequest());
        assertNotNull(response);
        assertEquals("Mobil Baru", response.getVehicle());
        assertEquals(3, response.getLoanTenure());
        assertEquals(3, response.getYearlyDetails().size());
    }

    @Test
    void calculateMotorBekas_shouldReturnResponse() {
        CreditRequest request = new CreditRequest(
            VehicleType.MOTOR,
            VehicleCondition.BEKAS,
            2020,
            new BigDecimal(50_000_000),
            6,
            new BigDecimal(20_000_000)
        );
        CreditResponse response = service.calculate(request);
        assertNotNull(response);
        assertEquals("Motor Bekas", response.getVehicle());
        assertEquals(6, response.getLoanTenure());
        assertEquals(6, response.getYearlyDetails().size());
    }


    @Test
    void calculateMobil_shouldGenerateThreeYearsInterestRate() {
        CreditResponse response = service.calculate(defaultRequest());
        assertEquals(new BigDecimal("0.08"), response.getYearlyDetails().get(1).getInterestRate());
        assertEquals(new BigDecimal("0.081"), response.getYearlyDetails().get(2).getInterestRate());
        assertEquals(new BigDecimal("0.086"), response.getYearlyDetails().get(3).getInterestRate());
    }

    @Test
    void calculateMotor_shouldGenerateThreeYearsInterestRate() {
        CreditRequest request = defaultRequest();
        request.setVehicleType(VehicleType.MOTOR);
        CreditResponse response = service.calculate(request);
        assertEquals(new BigDecimal("0.09"), response.getYearlyDetails().get(1).getInterestRate());
        assertEquals(new BigDecimal("0.091"), response.getYearlyDetails().get(2).getInterestRate());
        assertEquals(new BigDecimal("0.096"), response.getYearlyDetails().get(3).getInterestRate());
    }

    @Test
    void calculate_mobilBaru_tenor6() {
        CreditResponse res = service.calculate(
            new CreditRequest(
                VehicleType.MOBIL,
                VehicleCondition.BARU,
                2026,
                new BigDecimal(1_000_000_000L),
                6,
                new BigDecimal(500_000_000L))
        );

        assertEquals("Mobil Baru", res.getVehicle());
        assertEquals(new BigDecimal("1000000000"), res.getVehiclePrice());
        assertEquals(new BigDecimal("500000000"), res.getDownPayment());
        assertEquals(6, res.getLoanTenure());
        assertEquals(6, res.getYearlyDetails().size());

        String[] expectedMonthly = {"7500000.00", "8107500.00", "8804745.00", "9570757.82", "10451267.53", "11423235.41"};
        String[] expectedRate = {"0.08", "0.081", "0.086", "0.087", "0.092", "0.093"};
        for (int year = 1; year <= 6; year++) {
            YearlyCreditDetail detail = res.getYearlyDetails().get(year);
            assertEquals(new BigDecimal(expectedMonthly[year - 1]), detail.getMonthlyInstallment());
            assertEquals(new BigDecimal(expectedRate[year - 1]), detail.getInterestRate());
        }
        assertEquals(new BigDecimal("9309584.29"), res.getMonthlyAverage());
    }
}
