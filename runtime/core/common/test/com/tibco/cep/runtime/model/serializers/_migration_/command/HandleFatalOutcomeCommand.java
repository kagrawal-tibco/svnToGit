package com.tibco.cep.runtime.model.serializers._migration_.command;

import com.tibco.cep.runtime.model.serializers._migration_.ConversionLog;
import com.tibco.cep.runtime.model.serializers._migration_.ConversionScratchpad;

/*
* Author: Ashwin Jayaprakash Date: Jan 21, 2009 Time: 6:17:43 PM
*/
public class HandleFatalOutcomeCommand implements StatefulCommand {
    protected Throwable fatalOutcome;

    public void prepare() {
    }

    public Throwable getFatalOutcome() {
        return fatalOutcome;
    }

    public void setFatalOutcome(Throwable fatalOutcome) {
        this.fatalOutcome = fatalOutcome;
    }

    public void execute(ConversionScratchpad scratchpad) throws Exception {
        scratchpad.getConversionLog()
                .logMessage(ConversionLog.Level.FATAL, "Conversion failed", fatalOutcome);
    }

    public void clear() {
        fatalOutcome = null;
    }
}
