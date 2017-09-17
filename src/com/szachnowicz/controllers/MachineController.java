package com.szachnowicz.controllers;

import com.sun.xml.internal.ws.util.StringUtils;
import com.szachnowicz.entity.Machine;
import com.szachnowicz.entity.MachineConf;
import com.szachnowicz.entity.Money;
import com.szachnowicz.entity.Enums.PlnCoins;
import com.szachnowicz.entity.Enums.Ticked;
import com.szachnowicz.interfaces.IDisplayCallBack;

import java.util.*;

import static com.szachnowicz.entity.MachineConf.getTranslation;
import static com.szachnowicz.entity.Money.countCoins;

public class MachineController {


    private IDisplayCallBack displayCallBack;
    private List<Ticked> avilabeTickeds;
    private MachineConf machineConf;


    public MachineController(IDisplayCallBack callBack) {
        displayCallBack = callBack;
        avilabeTickeds = Arrays.asList(Ticked.values());
        machineConf = MachineConf.getDefConf();
    }

    public void changeLangueMenu() {
        displayCallBack.showMessage("Prosze wybrać język  / Please choose language");
        displayCallBack.showMessage(" Polski - Pl : English - En");

        String choosenLng = getConsolInput();

        if (choosenLng.toLowerCase().contains("pl") || choosenLng.toLowerCase().contains("pol")) {
            displayCallBack.showMessage(getTranslation("choosenLangue"));
        } else if (choosenLng.toLowerCase().contains("us") || choosenLng.toLowerCase().contains("en")) {
            machineConf.setLanguage(MachineConf.EN);
            displayCallBack.showMessage(getTranslation("choosenLangue"));

        }
        showMenu();
    }


    private String getConsolInput() {
        Scanner input = new Scanner(System.in);
        String inptString = input.nextLine();
        while (inptString.isEmpty())
            inptString = input.nextLine();

        return inptString;
    }

    public void showMenu() {
        displayCallBack.showMessage(getTranslation("mainMenu"));
        String choose = getConsolInput();
        if (choose.equals("1")) {

            beginTickedPourchase();
        }
        if (choose.equals("2")) {

            avilabeTickeds.forEach(ticked -> displayCallBack.showMessage(ticked.toString()));
        }
        if (choose.equals("3")) {

            changeLangueMenu();
        }
        showMenu();


    }

    private void beginTickedPourchase() {

        displayCallBack.showMessage(getTranslation("limitedOrSingle"));
        String choose = getConsolInput();


        if (choose.equals("1")) {
            avilabeTickeds.stream()
                    .filter(Ticked::isTimeLimited)
                    .forEach(t -> displayCallBack.showMessage((avilabeTickeds.indexOf(t) + 1) + ". " + t.toString()));
        }
        if (choose.equals("2")) {
            avilabeTickeds.stream().
                    filter(t -> !t.isTimeLimited()).
                    forEach(t -> displayCallBack.showMessage((avilabeTickeds.indexOf(t) + 1) + ". " + t.toString()));

        }

        int slection = Integer.parseInt(getConsolInput()) - 1;
        if (slection < avilabeTickeds.size() && slection >= 0) {
            Ticked selected = avilabeTickeds.get(slection);
            askForQuantityAndDiscount(selected);
        } else {
            displayCallBack.showMessage(getTranslation("error"));
            showMenu();
            return;
        }
        showMenu();
    }


    /*

    */
    private void askForQuantityAndDiscount(Ticked selected) {
        displayCallBack.showMessage(getTranslation("askForQuanity"));
        int quantity;
        try {
            quantity = Integer.valueOf(getConsolInput());
            if (quantity < 0 || quantity > 100) {
                displayCallBack.showMessage(getTranslation("error"));
                showMenu();
                return;
            }
        } catch (NumberFormatException ex) {
            displayCallBack.showMessage(getTranslation("error"));
            showMenu();
            return;
        }

        displayCallBack.showMessage(getTranslation("discount"));

        String discount = getConsolInput();

        if (discount.toLowerCase().equals(getTranslation("yes")) ||
                discount.toLowerCase().contains(getTranslation("yes").substring(0, 2))) {

            chargeForTicked(selected, quantity, true);

        } else {
            chargeForTicked(selected, quantity, false);

        }


    }

    private void chargeForTicked(Ticked selected, int quantity, boolean discount) {
        Money amount = Money.zlotych("0");
        amount.addValue(selected.getPrice());

        if (discount) {
            amount.applyDiscount();
        }
        amount.mulityplyPrice(quantity);
        displayCallBack.showMessage(getTranslation("payment") + amount.toString());

        final List<PlnCoins> insertedCoins = takeCoins(amount);

        if (insertedCoins == null) {
            showMenu();
            return;
        }
        machineConf.addCoins(insertedCoins);
        Money moneyToReturn = Money.diffrence(Money.countCoins(insertedCoins), amount);
        endTransactionPrintTicked(selected, quantity);
        returnCoins(moneyToReturn);

    }

    public void returnCoins(Money moneyToReturn) {
        displayCallBack.showMessage(getTranslation("amountToReturn") + moneyToReturn.toString());
        Money zlotych = Money.zlotych("0");
        final PlnCoins[] values = PlnCoins.values();
        for (int i = values.length - 1; i >= 0; ) {
            if (!machineConf.hasCoin(values[i])) {
                displayCallBack.showMessage(getTranslation("missingCoin"));
                if (i > 1)
                    i--;
            }
            if (values[i].getCurrency().moreOrEqual(moneyToReturn)) {
                displayCallBack.showMessage(getTranslation("coinReturned") + values[i].toString());
                zlotych.addValue(values[i].getCurrency());
                moneyToReturn.subbstract(values[i].getCurrency());
            } else {
                i--;
            }
        }
        displayCallBack.showMessage(getTranslation("returnedAmount") + zlotych.toString());
        showMenu();

    }

    private void endTransactionPrintTicked(Ticked selected, int quantity) {
        displayCallBack.showMessage(getTranslation("printing"));
        for (int i = 0; i < quantity; i++) {
            displayCallBack.showMessage(selected.getTickedPrintingInfo());


        }


    }

    private List<PlnCoins> takeCoins(final Money costOfTickeds) {
        Money inMachine = Money.zlotych("0");
        List<PlnCoins> insertedCoins = new ArrayList<>();

        displayCallBack.showMessage(getTranslation("plsEnterCoins") + PlnCoins.allCoins());
        displayCallBack.showMessage(getTranslation("enterValueOrExit"));

        Money leftToPay = Money.diffrence(costOfTickeds, inMachine);

        while (!costOfTickeds.moreOrEqual(inMachine)) {

            displayCallBack.showMessage(getTranslation("eneterDisc"));

            String inputFromConsole = getConsolInput();

            if (inputFromConsole.toLowerCase().contains("x")) {
                endTransactionWithOutPrinting(insertedCoins);
                return null;
            }
            insertedCoins.addAll(getCoinInput(inputFromConsole));
            inMachine = Money.countCoins(insertedCoins);

            leftToPay = Money.diffrence(costOfTickeds, inMachine);
            if (!costOfTickeds.moreOrEqual(inMachine)) {
                displayCallBack.showMessage(getTranslation("amountLeft") + leftToPay);
            }
        }
        return insertedCoins;

    }

    private void endTransactionWithOutPrinting(List<PlnCoins> inMachine) {
        inMachine.stream().filter(money -> !money.equals(Money.zlotych("0"))).forEach(money -> {
            displayCallBack.showMessage(getTranslation("coinReturned") + money.toString());
        });

    }

    public List<PlnCoins> getCoinInput(String input) {
        List<PlnCoins> coinsList = new ArrayList<>();
        input = trimCoinInput(input);

        if (input.contains(",")) {
            final String[] split = input.split(",");

            for (int i = 0; i < split.length; i++) {

                final PlnCoins coins = getPlnCoins(split[i]);
                if (coins != null)
                    coinsList.add(coins);
            }

        } else {
            final PlnCoins coins = getPlnCoins(input);
            if (coins != null)
                coinsList = Collections.singletonList(coins);
        }


        return coinsList;
    }

    private PlnCoins getPlnCoins(String coin) {

        final PlnCoins coins = PlnCoins.recognizeCoin(coin);
        if (coins != null) {
            displayCallBack.showMessage(getTranslation("coinEntred") + coins.toString());
            displayCallBack.showMessage("");
        } else {
            displayCallBack.showMessage(getTranslation("wrongCoin"));
            displayCallBack.showMessage(coin + getTranslation("coinReturned"));
        }
        return coins;
    }

    private String trimCoinInput(String s) {
        final String zl = "zł";
        final String zl_2 = "zl";
        if (s.contains(zl)) {
            s = s.replace(zl, "");
        }
        if (s.contains(zl_2)) {
            s = s.replace(zl_2, "");
        }
        s = s.replace(" ", "");
        return s;
    }
}

