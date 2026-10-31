package enums;

import com.credit.simulator.enums.VehicleCondition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VehicleConditionTest {

    @Test
    void vehicleCondition_insensitiveCase(){
        assertEquals(VehicleCondition.BARU, VehicleCondition.from("baru"));
        assertEquals(VehicleCondition.BARU, VehicleCondition.from("BARU"));
        assertEquals(VehicleCondition.BARU, VehicleCondition.from("bArU"));
        assertEquals(VehicleCondition.BEKAS, VehicleCondition.from("bekas"));
        assertEquals(VehicleCondition.BEKAS, VehicleCondition.from("BEKAS"));
        assertEquals(VehicleCondition.BEKAS, VehicleCondition.from("bEkAs"));
    }

    @Test
    void vehicleCondition_invalidThrows() {
        assertThrows(IllegalArgumentException.class, () -> VehicleCondition.from("kotor"));
        assertThrows(IllegalArgumentException.class, () -> VehicleCondition.from(""));
        assertThrows(IllegalArgumentException.class, () -> VehicleCondition.from(null));
    }

    @Test
    void vehicleCondition_displayName() {
        assertEquals("Baru", VehicleCondition.BARU.getName());
        assertEquals("Bekas", VehicleCondition.BEKAS.getName());
    }
}
