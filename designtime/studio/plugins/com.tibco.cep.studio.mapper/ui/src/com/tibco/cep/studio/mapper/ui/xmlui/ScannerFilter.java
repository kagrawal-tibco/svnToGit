package com.tibco.cep.studio.mapper.ui.xmlui;


/**
 * A simple filter, currently used by {@link SchemaScanner}.
 */
public interface ScannerFilter
{
    public boolean isMember(Object candidate);
}
