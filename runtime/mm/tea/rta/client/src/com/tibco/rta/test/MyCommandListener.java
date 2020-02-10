package com.tibco.rta.test;

import com.tibco.rta.RtaCommand;
import com.tibco.rta.RtaCommandListener;

public class MyCommandListener implements RtaCommandListener {
    @Override
    public synchronized void onCommand(RtaCommand command) {

        System.out.println(String.format("Command received: %s", command.getCommand()));
        System.out.println(String.format("   Command parameters"));
        for (String property : command.getProperties()) {
            System.out.println(String.format("     Param [%s] and value [%s]", property, command.getValue(property)));
        }
    }
}
