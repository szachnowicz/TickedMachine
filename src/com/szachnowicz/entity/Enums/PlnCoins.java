package com.szachnowicz.entity.Enums;

import com.szachnowicz.entity.Money;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PlnCoins {
    TEN_PENNY(Money.zlotych("0.1")),
    TWEENTY_PENNY(Money.zlotych("0.2")),
    FIFTY_PENNY(Money.zlotych("0.5")),
    ONE_PLN(Money.zlotych("1.00")),
    TWO_PLNS(Money.zlotych("2.00")),
    FIVE_PLNS(Money.zlotych("5.00"));


    private Money currency;


    PlnCoins(Money zlotych) {
        this.currency = zlotych;
    }


    public static String allCoins() {
        StringBuilder stringBuilder = new StringBuilder();

        for (PlnCoins plnCoins : values()) {
            stringBuilder.append(plnCoins);

        }
        return stringBuilder.toString();
    }

    public Money getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return currency.toString();
    }

    public static PlnCoins recognizeCoin(String coin) {
        PlnCoins plnCoins = null;
        // more the one . or contais any letters
        if (coin.length() == 0 || coin.length() > 4 ||
                coin.chars().filter(n -> n == '.').count() > 1 ||
                coin.toLowerCase().contains("[a-z]")
                ) {
            return null;
        }


        final PlnCoins[] values = values();
        for (PlnCoins value : values) {
            if (value.currency.equals(Money.zlotych(coin))) {
                plnCoins = value;
            }
        }

        return plnCoins;
    }
}

