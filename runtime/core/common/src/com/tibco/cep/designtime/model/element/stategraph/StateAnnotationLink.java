package com.tibco.cep.designtime.model.element.stategraph;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 26, 2006
 * Time: 2:22:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StateAnnotationLink extends StateLink {


    /**
     * Get the "from" Annotation (where the link originates) for this link.
     *
     * @return The "from" Annotation (where the link originates) for this link.
     */
    StateTextLabel getFromAnnotation();


    /**
     * Get the "to" StateEntity (where the link terminates) for this link.
     *
     * @return The "to" StateEntity (where the link terminates) for this link.
     */
    StateEntity getToStateEntity();
}
