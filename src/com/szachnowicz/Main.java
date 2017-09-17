package com.szachnowicz;

import com.szachnowicz.entity.Machine;

public class Main {

    public static void main(String[] args) {

        Machine machine = Machine.getInstance();
        machine.launchMachine();

    }


}
