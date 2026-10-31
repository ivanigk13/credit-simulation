package utils;

import com.credit.simulator.utils.FormatUtils;
import com.credit.simulator.utils.StringUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @Test
    void isBlank_nullAndEmpty_areBlank() {
        assertTrue(StringUtils.isBlank(""));
    }

    @Test
    void isBlank_nonEmpty_isNotBlank() {
        assertFalse(StringUtils.isBlank("a"));
        assertFalse(StringUtils.isBlank(" a "));
    }

    @Test
    void moneyFormat_usesThousandSeparatorAndTwoDecimals() {
        assertEquals("1,000,000,000.00", FormatUtils.moneyFormat(new BigDecimal("1000000000")));
        assertEquals("9,309,584.29", FormatUtils.moneyFormat(new BigDecimal("9309584.29")));
        assertEquals("7,500,000.00", FormatUtils.moneyFormat(new BigDecimal("7500000")));
    }
}
