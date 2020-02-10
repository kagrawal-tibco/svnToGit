package com.tibco.cep.runtime.model.element.stategraph;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 4, 2004
 * Time: 12:51:27 PM
 * To change this template use File | Settings | File Templates.
 */

public interface ModelElement {
    public Class getAssociatedModelElement();
    public String getName();
    public int getIndex();
    public void setIndex(int index);
}
