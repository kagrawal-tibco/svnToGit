package com.tibco.cep.runtime.model.element;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Mar 27, 2007
 * Time: 10:17:34 PM
 * To change this template use File | Settings | File Templates.
 */

/*
    If an object implements this interface, 
    using CodegenFunctions.entityEQ to compare it to null will return true
*/
public interface NullContainedConcept extends ContainedConcept{
}
