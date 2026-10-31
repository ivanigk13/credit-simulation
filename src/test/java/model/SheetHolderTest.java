package model;

import com.credit.simulator.model.CreditResponse;
import com.credit.simulator.model.SheetHolder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class SheetHolderTest {

    private CreditResponse response(String vehicle) {
        return new CreditResponse(vehicle, BigDecimal.TEN, BigDecimal.ONE, 1, BigDecimal.ONE, new LinkedHashMap<>());
    }

    @Test
    void newHolder_isEmpty() {
        SheetHolder holder = new SheetHolder();
        assertNull(holder.getCreditResponse());
        assertTrue(holder.getSheets().isEmpty());
    }

    @Test
    void addSheet_storesCurrentCreditResponseUnderName() {
        SheetHolder holder = new SheetHolder();
        CreditResponse expected = response("Mobil Baru");
        holder.setCreditResponse(expected);

        holder.addSheet("sheet1");

        assertSame(expected, holder.getSheets().get("sheet1"));
    }
}
