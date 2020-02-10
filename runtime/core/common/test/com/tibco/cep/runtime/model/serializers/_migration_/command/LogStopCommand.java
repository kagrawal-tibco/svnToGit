package com.tibco.cep.runtime.model.serializers._migration_.command;

import com.tibco.cep.runtime.model.serializers._migration_.ConversionScratchpad;
import com.tibco.cep.runtime.model.serializers._migration_.command.Command;

/*
* Author: Ashwin Jayaprakash Date: Jan 21, 2009 Time: 6:01:02 PM
*/
public class LogStopCommand implements Command {
    public void execute(ConversionScratchpad scratchpad) throws Exception {
        scratchpad.getConversionLog().endDocument("Conversion completed");
    }
}