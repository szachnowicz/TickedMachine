package com.szachnowicz.entity;

import com.szachnowicz.entity.Enums.Ticked;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void moreOrEqualTest() {
        Money money = Money.zlotych(("3.3"));
        Money money2 = Money.zlotych(("3.0"));
        assertTrue(money.moreOrEqual(money2));
        assertTrue(money.moreOrEqual(money));

    }

    @Test
    public void monetyTesT() {
        Money money = Money.zlotych(("3.0"));
        System.out.println(money.toString());
        money.applyDiscount();
        System.out.println(money.toString());

    }

    @Test
    public void ticketQuantityTest() {
        Money tPrice = Ticked.NORMAL.getPrice();
        BigDecimal sum = tPrice.mulityplyPrice(5);
        assertEquals(sum, new BigDecimal("15.00"));

    }

    @Test
    public void ticketQuantityTestWithDiscount() {
        Money tPrice = Ticked.NORMAL.getPrice();
        tPrice.applyDiscount();
        BigDecimal sum = tPrice.mulityplyPrice(5);
        assertEquals(sum, new BigDecimal("11.25"));


        Money t2Price = Ticked.DAY.getPrice();
        t2Price.applyDiscount();
        BigDecimal multiBuy = t2Price.mulityplyPrice(5);
        assertEquals(multiBuy, new BigDecimal("41.25"));

    }

    @Test
    void addValueTest() {
        Money money = Money.zlotych("5.00");
        Money money2 = Money.zlotych("5.00");
        money.addValue(money2);
        assertEquals(money, Money.zlotych("10.00"));
    }

    @Test
    void diffrence() {

        final Money tenZlotych = Money.zlotych("10.00");
        final Money oneZlotych = Money.zlotych("1.00");

        final Money diffrence = Money.diffrence(tenZlotych, oneZlotych);
        final Money diffrence2 = Money.diffrence(oneZlotych, tenZlotych);
        assertEquals(diffrence,Money.zlotych("9.00"));
        assertEquals(diffrence2,Money.zlotych("-9.00"));

    }
}