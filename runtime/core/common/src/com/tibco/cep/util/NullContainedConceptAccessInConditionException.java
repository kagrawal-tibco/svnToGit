package com.tibco.cep.util;

import com.tibco.cep.kernel.helper.ForceConditionFailureException;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Mar 27, 2007
 * Time: 10:02:35 PM
 * To change this template use File | Settings | File Templates.
 */

/*
    Thrown when a property of a null ContainedConcept is accessed in a condition
*/
public class NullContainedConceptAccessInConditionException  extends ForceConditionFailureException {
}