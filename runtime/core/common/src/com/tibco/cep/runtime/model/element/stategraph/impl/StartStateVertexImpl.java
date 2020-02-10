package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.cep.runtime.model.element.stategraph.StartStateVertex;
import com.tibco.cep.runtime.model.element.stategraph.StartTransitionLink;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 6, 2004
 * Time: 12:18:04 PM
 * To change this template use File | Settings | File Templates.
 */

public class StartStateVertexImpl extends SimpleStateVertexImpl implements StartStateVertex{
    public StartStateVertexImpl(String name,CompositeStateVertexImpl superState) {
        super(name, superState);
    }

    public StartTransitionLink getStartTransition() {
        return null;  
    }
}
