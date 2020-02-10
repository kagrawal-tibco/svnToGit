package com.tibco.cep.mapper.xml.xdata.bind;



/**
 * This needs a better home, but it is a simple interface for checking if an operation has been cancelled.
 */
public interface CancelChecker
{
    boolean hasBeenCancelled();
}

