package test;
import com.szachnowicz.entity.Money;
import com.szachnowicz.entity.Enums.Ticked;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TickedTest {

    @Test
    public void discountTest() {
         BigDecimal mockedValue = Money.zlotych("2.25").getAmount();
         BigDecimal enumValue = Ticked.NORMAL.getPriceWithDiscount().getAmount();
        System.out.println(enumValue);
        assertEquals(mockedValue,enumValue);

    }

    @Test
    void enumDisplay()
    {
        for (Ticked t :
                Ticked.values()) {
            System.out.println(t);
        }
    }
}