package com.tibco.cep.runtime.model.serializers._migration_;

/*
* Author: Ashwin Jayaprakash Date: Jan 21, 2009 Time: 4:35:51 PM
*/
public interface ConversionLog {
    void startDocument(String message);

    void startStep(String message);

    void logMessage(Level level, String message);

    void logMessage(Level level, String message, Throwable throwable);

    void endStep(String message);

    void endDocument(String message);

    //-----------

    public static enum Level {
        INFO, WARNING, ERROR, FATAL
    }
}
