package com.tibco.cep.runtime.model.serializers._migration_.command;

import com.tibco.cep.runtime.model.serializers._migration_.ConversionScratchpad;
import com.tibco.cep.runtime.model.serializers._migration_.command.StatefulCommand;

/*
* Author: Ashwin Jayaprakash Date: Jan 21, 2009 Time: 6:01:02 PM
*/
public class LogStartCommand implements StatefulCommand {
    protected String transactionId;

    public void prepare() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void execute(ConversionScratchpad scratchpad) throws Exception {
        scratchpad.getConversionLog().startDocument("Conversion starting: " + transactionId);
    }

    public void clear() {
        transactionId = null;
    }
}
