package service;

import com.credit.simulator.enums.VehicleCondition;
import com.credit.simulator.enums.VehicleType;
import com.credit.simulator.model.CreditRequest;
import com.credit.simulator.service.FileTxtService;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class FileTxtServiceTest {

    private final FileTxtService service = new FileTxtService();

    @Test
    void readFile_shouldBuildCreditRequest() throws Exception {
        File file = File.createTempFile("file_inputs", ".txt");
        Files.write(file.toPath(),
            Arrays.asList(
                "vehicleType=Mobil",
                "vehicleCondition=Baru",
                "vehicleYear=2025",
                "totalLoanAmount=100000000",
                "loanTenure=3",
                "downPayment=25000000"
            )
        );

        CreditRequest request = service.readFile(file.getAbsolutePath());

        assertEquals(VehicleType.MOBIL, request.getVehicleType());
        assertEquals(VehicleCondition.BARU, request.getVehicleCondition());
        assertEquals(Integer.valueOf(2025), request.getVehicleYear());
        assertEquals(new BigDecimal("100000000"), request.getTotalLoanAmount());
        assertEquals(Integer.valueOf(3), request.getLoanTenure());
        assertEquals(new BigDecimal("25000000"), request.getDownPayment());
    }

    @Test
    void readFile_shouldThrowException_whenFileNotFound() {
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> service.readFile("not-found.txt")
        );
        assertEquals("Gagal membaca file input", exception.getMessage());
    }

    @Test
    void readFile_shouldThrowException_whenRequiredPropertyMissing() throws Exception {

        File file = File.createTempFile("file_inputs", ".txt");
        Files.write(
            file.toPath(),
            Arrays.asList(
                "vehicleType=Mobil",
                "vehicleYear=2025"
            )
        );

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> service.readFile(file.getAbsolutePath())
        );

        assertTrue(exception.getMessage().contains("vehicleCondition"));
    }
}
