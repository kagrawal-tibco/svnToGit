package com.tibco.cep.runtime.session;

/**
 * User: nprade
 * Date: 11/1/12
 * Time: 6:08 PM
 */
public class ProcessingContextProvider {


    private final ThreadLocal<ProcessingContext> processingContextThreadLocal = new ThreadLocal<ProcessingContext>();


    public ProcessingContext getProcessingContext() {
        return this.processingContextThreadLocal.get();
    }


    public void setProcessingContext(
            ProcessingContext processingContext) {
        this.processingContextThreadLocal.set(processingContext);
    }

}
