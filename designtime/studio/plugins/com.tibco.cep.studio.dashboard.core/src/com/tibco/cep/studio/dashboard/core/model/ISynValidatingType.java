package com.tibco.cep.studio.dashboard.core.model;

import java.io.Serializable;

/**
 * 
 *  
 */
public interface ISynValidatingType extends ISynValidationProvider, Cloneable, Serializable {

    public abstract boolean equals(Object o);

    public abstract String getName();

    public abstract Object clone() throws CloneNotSupportedException;

}
