package com.szachnowicz.entity;

import com.szachnowicz.entity.Enums.PlnCoins;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

// Money Pattern
public class Money {
    private static final Currency PLN = Currency.getInstance("PLN");
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    private BigDecimal amount;
    private Currency currency;

    public static Money zlotych(String amount) {

        return new Money(new BigDecimal(amount), PLN);
    }

    Money(BigDecimal amount, Currency currency) {
        this(amount, currency, DEFAULT_ROUNDING);
    }

    Money(BigDecimal amount, Currency currency, RoundingMode rounding) {
        this.amount = amount;
        this.currency = currency;
        this.amount = amount.setScale(currency.getDefaultFractionDigits(), rounding);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return " " + getAmount() + " " + getCurrency().getSymbol();
    }

    public String toString(Locale locale) {
        return getAmount() + " " + getCurrency().getSymbol(locale);
    }

    public void applyDiscount() {
        final String discount = "0.65";
        amount = amount.multiply(new BigDecimal(discount)).setScale(2, RoundingMode.HALF_UP);

    }

    public BigDecimal mulityplyPrice(int ticketQuantity) {
        amount = amount.multiply(new BigDecimal(ticketQuantity));
        return amount;
    }

    public boolean moreOrEqual(Money inMachine) {

        int result = getAmount().compareTo(inMachine.getAmount());

        //          0  = "Both values are equal ";
        //          1 = "First Value is greater ";
        //         -1 = "Second value is greater";

        return result == 0 || result == -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (amount != null ? !amount.equals(money.amount) : money.amount != null) return false;
        return currency != null ? currency.equals(money.currency) : money.currency == null;
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    public void addValue(Money coinInput) {
        if (coinInput != null) {
            amount = amount.add(coinInput.getAmount());
        }

    }

    public static Money diffrence(Money amount, Money amount2) {

        final BigDecimal subtract = amount.getAmount().subtract(amount2.getAmount());
        return new Money(subtract, PLN);
    }

    public static Money countCoins(List<PlnCoins> insertedCoins) {
        Money sum = Money.zlotych("0");
        insertedCoins.stream().forEach(plnCoins -> sum.addValue(plnCoins.getCurrency()));
        return sum;
    }

    public void subbstract(Money currency) {
        amount = getAmount().subtract(currency.getAmount());
    }
}
