package enums;

import com.credit.simulator.enums.VehicleType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTypeTest {

    @Test
    void vehicleType_insensitiveCase(){
        assertEquals(VehicleType.MOTOR, VehicleType.from("motor"));
        assertEquals(VehicleType.MOTOR, VehicleType.from("MOTOR"));
        assertEquals(VehicleType.MOTOR, VehicleType.from("mOtOr"));
        assertEquals(VehicleType.MOBIL, VehicleType.from("mobil"));
        assertEquals(VehicleType.MOBIL, VehicleType.from("MOBIL"));
        assertEquals(VehicleType.MOBIL, VehicleType.from("mObIl"));
    }

    @Test
    void vehicleType_invalidThrows() {
        assertThrows(IllegalArgumentException.class, () -> VehicleType.from("pesawat"));
        assertThrows(IllegalArgumentException.class, () -> VehicleType.from(""));
        assertThrows(IllegalArgumentException.class, () -> VehicleType.from(null));
    }

    @Test
    void vehicleType_displayName() {
        assertEquals("Mobil", VehicleType.MOBIL.getName());
        assertEquals("Motor", VehicleType.MOTOR.getName());
    }
}
