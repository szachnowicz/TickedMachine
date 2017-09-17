package com.szachnowicz.entity.Enums;


import com.szachnowicz.entity.Money;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.szachnowicz.entity.MachineConf.getTranslation;

public enum Ticked {

    HALF_HOUR(0.5, Money.zlotych("3.00")),

    HOUR(1, Money.zlotych("4.50")),

    ONE_AND_HALF_HOURS(1.5, Money.zlotych("6.00")),

    DAY(24, Money.zlotych("11.0")),

    NORMAL(Money.zlotych("3.00")),

    SPECIAL(Money.zlotych("3.20"));

    private double drutaionInHour;
    private Money price;
    private boolean isTimeLimited;


    Ticked(double duration, Money price) {
        isTimeLimited = true;
        this.price = price;
        this.drutaionInHour = duration;
    }


    Ticked(Money price) {
        isTimeLimited = false;
        this.price = price;
    }

    public double getDrutaionInHour() {
        return drutaionInHour;
    }

    public Money getPrice() {
        return price;
    }

    public Money getPriceWithDiscount() {
        price.applyDiscount();
        return price;
    }

    public String getTickedPrintingInfo() {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        String minutes = drutaionInHour < 2 ? (int) (drutaionInHour * 60) + " min " : (int) drutaionInHour + " h  ";
        sb.append(getTranslation("ticked") + minutes);
        sb.append(getTranslation("serial") + new BigInteger(130, random).toString(32));
        sb.append(getTranslation("data") + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return sb.toString();
    }

    public boolean isTimeLimited() {
        return isTimeLimited;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        String minutes = drutaionInHour < 2 ? (int) (drutaionInHour * 60) + " min " : (int) drutaionInHour + " h  ";
        if (isTimeLimited) {
            sb.append(getTranslation("ticked") + minutes);
        } else {

            sb.append(handleUnsuallName());
        }

        sb.append(getTranslation("price") + getPrice().toString());

        return sb.toString();
    }

    private String handleUnsuallName() {
        String result = getTranslation("ticked");
        if (this == NORMAL) {
            return result + getTranslation("normal");
        }
        if (this == SPECIAL) {
            return result + getTranslation("special");
        }

        return result;
    }


}
