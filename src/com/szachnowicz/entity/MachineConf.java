package com.szachnowicz.entity;

import com.szachnowicz.entity.Enums.PlnCoins;

import java.util.*;

public class MachineConf {

    public static final String PL = "_pl_pl";
    public static final String EN = "_en_us";
    private static MachineConf defConf;
    private static ResourceBundle stringBunndle;
    private static Map<PlnCoins, Integer> change;

    private MachineConf() {
        stringBunndle = ResourceBundle.getBundle("res.StringBuddle" + PL);
    }

    public static MachineConf getDefConf() {
        if (defConf == null) {
            defConf = new MachineConf();
            change = new HashMap<>();
            generateChange();
        }
        return defConf;
    }

    private static void generateChange() {
        Random random = new Random();
        for (PlnCoins coins : PlnCoins.values()) {
            change.put(coins, random.nextInt(40));
        }
    }


    public static String getTranslation(String key) {
        if (defConf == null) {
            getDefConf();
        }
        return stringBunndle.getString(key);
    }

    public static void setLanguage(final String key) {
        stringBunndle = ResourceBundle.getBundle("res.StringBuddle" + key);
    }

    public static Map<PlnCoins, Integer> getChange() {
        return change;
    }

    public boolean hasCoin(PlnCoins value) {
        final Integer integer = change.get(value);
        return integer >= 1;
    }

    public void addCoins(List<PlnCoins> insertedCoins) {
        insertedCoins.stream().forEach(plnCoins -> {
            int oldValue = change.get(plnCoins);
            oldValue++;
            change.put(plnCoins, oldValue);
        });
    }
}
