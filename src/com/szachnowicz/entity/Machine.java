package com.szachnowicz.entity;

import com.szachnowicz.controllers.MachineController;
import com.szachnowicz.interfaces.IDisplayCallBack;

public final class  Machine implements IDisplayCallBack {

    private static Machine instance;
    private MachineController machineController;


    private Machine() {
        machineController = new MachineController(this);
    }

    public static Machine getInstance() {
        if (instance == null) {
            instance = new Machine();
        }
        return instance;
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    public void launchMachine() {
        machineController.showMenu();
    }
}
