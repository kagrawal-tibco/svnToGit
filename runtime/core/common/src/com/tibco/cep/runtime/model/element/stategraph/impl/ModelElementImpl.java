package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.cep.runtime.model.element.stategraph.ModelElement;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 10, 2004
 * Time: 6:41:48 PM
 * To change this template use File | Settings | File Templates.
 */

public class ModelElementImpl implements ModelElement{
    public int mdx; // Model Element Index
    String name;

    public ModelElementImpl(String name) {
        this.name=name;
    }

    public Class getAssociatedModelElement() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getName() {
        return name;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getIndex() {
        return mdx;
    }

    public void setIndex(int index) {
        this.mdx = index;
    }
}
