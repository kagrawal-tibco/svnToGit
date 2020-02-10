/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Sep 1, 2004
 */

package com.tibco.cep.designtime.model.element.stategraph.mutable;

import com.tibco.cep.designtime.model.element.stategraph.StateAnnotationLink;
import com.tibco.cep.designtime.model.element.stategraph.StateEntity;
import com.tibco.cep.designtime.model.element.stategraph.StateTextLabel;


/**
 * A link between an annotation and a state or state transition.
 */
public interface MutableStateAnnotationLink extends StateAnnotationLink, MutableStateLink {


    /**
     * Set the new "from" annotation (where the link originates) for this link.
     *
     * @param fromAnnotation The new "from" annotation (where the link originates) for this link.
     */
    public void setFromAnnotation(
            StateTextLabel fromAnnotation);


    /**
     * Set the new "to" StateEntity (where the link terminates) for this link.
     *
     * @param toStateEntity The new "to" State (where the link terminates) for this link.
     */
    public void setToStateEntity(
            StateEntity toStateEntity);
}// end interface StateAnnotationLink
