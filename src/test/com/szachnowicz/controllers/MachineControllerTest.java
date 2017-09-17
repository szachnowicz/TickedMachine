package com.szachnowicz.controllers;

import com.szachnowicz.entity.Enums.PlnCoins;
import com.szachnowicz.entity.Money;
import com.szachnowicz.interfaces.IDisplayCallBack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MachineControllerTest {

    private MachineController machineController;

    @BeforeEach
    void setUp() {
        machineController = new MachineController(new IDisplayCallBack() {
            @Override
            public void showMessage(String message) {
                System.out.println(message);
            }
        });
    }

    @Test
    void multiCoinInput() {
        List<PlnCoins> money = machineController.getCoinInput("0.10 zł , 0 .5 0zł , 5.00zł,3.0 zł ");
        assertEquals(Money.countCoins(money), Money.zlotych("5.60"));
    }


    @Test
    void singleCoinInput() {
        List<PlnCoins> money = machineController.getCoinInput("10.00");
        assertNotEquals(Money.countCoins(money), Money.zlotych("10.00"));
        money = machineController.getCoinInput("5.00zł      ");
        assertEquals(Money.countCoins(money), Money.zlotych("5.0"));

    }
//
//    @Test
//    void coinReturning() {
//
//        machineController.returnCoins(Money.zlotych("14.80"));
//        machineController.returnCoins(Money.zlotych("13.70"));
//        machineController.returnCoins(Money.zlotych("14.12"));
//        machineController.returnCoins(Money.zlotych("1.40"));
//        machineController.returnCoins(Money.zlotych("10.70"));
//
//
//    }
}