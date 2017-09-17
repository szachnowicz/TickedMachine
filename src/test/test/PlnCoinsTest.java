package test;

import com.szachnowicz.entity.Enums.PlnCoins;
import org.junit.jupiter.api.Test;

class PlnCoinsTest {


    @Test
    void EnumDisplay() {
        for (PlnCoins coins : PlnCoins.values())
            System.out.println(coins);
    }

}