package com.tibco.cep.kernel.service;

/**
 * User: nleong
 * Creation Date: Sep 11, 2009
 * Creation Time: 2:17:03 AM
 * <p/>
 * $LastChangedDate$
 * $Rev$
 * $LastChangedBy$
 * $URL$
 */
public interface Filter  {
    
    boolean evaluate(java.lang.Object o);
}