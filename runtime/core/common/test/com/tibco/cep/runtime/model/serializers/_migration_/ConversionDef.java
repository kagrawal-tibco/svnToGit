package com.tibco.cep.runtime.model.serializers._migration_;

import com.tibco.cep.runtime.model.serializers._migration_.command.Command;
import com.tibco.cep.runtime.model.serializers._migration_.command.NonFatalException;

/*
* Author: Ashwin Jayaprakash Date: Jan 16, 2009 Time: 3:44:42 PM
*/
public class ConversionDef<I extends TypeDef, O extends TypeDef> {
    protected I oldDef;

    protected O newDef;

    protected Command[] commands;

    protected ConversionScratchpad scratchpad;

    public void execute() {
        for (Command command : commands) {
            try {
                command.execute(null);
            }
            catch (NonFatalException e) {
                //todo
            }
            catch (Exception e) {
                //todo

                break;
            }
        }
    }
}
