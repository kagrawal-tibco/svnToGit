package com.tibco.cep.runtime.service.loader;

import java.lang.instrument.Instrumentation;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Oct 5, 2006
 * Time: 9:44:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class InstrumentLoader {

    //JDK 1.5 - Instrumentation
    static private Instrumentation defaultInstrument= null;

    static public void premain(String agentArguments, Instrumentation instrumentation) {
        defaultInstrument = instrumentation;
    }

    static public Instrumentation getInstrumentation() {
        return defaultInstrument;
    }
}
