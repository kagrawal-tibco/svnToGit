package com.tibco.cep.runtime.model.element.impl.property;

import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Mar 30, 2007
 * Time: 7:14:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PropertyAtomContainedConcept_CalledFromCondition extends PropertyAtomContainedConcept {
    ContainedConcept getContainedConcept(boolean calledFromCondition);
}
