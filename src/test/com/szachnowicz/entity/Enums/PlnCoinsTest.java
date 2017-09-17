package com.szachnowicz.entity.Enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlnCoinsTest {
    @Test
    void recognizeCoin() {

       assertEquals(PlnCoins.recognizeCoin("5.00"),PlnCoins.FIVE_PLNS);
       assertEquals(PlnCoins.recognizeCoin("2.00"),PlnCoins.TWO_PLNS);
       assertEquals(PlnCoins.recognizeCoin("1.00"),PlnCoins.ONE_PLN);
       assertEquals(PlnCoins.recognizeCoin("0.10"),PlnCoins.TEN_PENNY);
       assertEquals(PlnCoins.recognizeCoin("0.1"),PlnCoins.TEN_PENNY);
       assertEquals(PlnCoins.recognizeCoin("0.2"),PlnCoins.TWEENTY_PENNY);
       assertEquals(PlnCoins.recognizeCoin("0.20"),PlnCoins.TWEENTY_PENNY);
       assertEquals(PlnCoins.recognizeCoin("0.50"),PlnCoins.FIFTY_PENNY);
       assertEquals(PlnCoins.recognizeCoin("0.5"),PlnCoins.FIFTY_PENNY);
       assertEquals(PlnCoins.recognizeCoin("0.5"),PlnCoins.FIFTY_PENNY);
       assertEquals(PlnCoins.recognizeCoin("0.5000"),null);
    }

}