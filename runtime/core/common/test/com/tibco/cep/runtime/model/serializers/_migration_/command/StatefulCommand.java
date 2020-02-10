package com.tibco.cep.runtime.model.serializers._migration_.command;

/*
* Author: Ashwin Jayaprakash Date: Jan 19, 2009 Time: 1:34:28 PM
*/
public interface StatefulCommand extends Command {
    void prepare();

    void clear();
}