package com.tibco.cep.runtime.model.serializers._migration_.command;

import com.tibco.cep.runtime.model.serializers._migration_.ConversionScratchpad;

/*
* Author: Ashwin Jayaprakash Date: Jan 19, 2009 Time: 1:34:28 PM
*/
public interface Command {
    void execute(ConversionScratchpad scratchpad) throws Exception;
}
