package com.tibco.cep.studio.decision.table.providers;

/**
 * @author vdhumal
 *
 */
public interface IDataConverter {

    /**
     * @param obj
     * @return the String representation of the value object
     */
    String toString(Object obj);

    /**
     * @param s
     * @return the Object from the String representation 
     */
    Object fromString(String s);
}
