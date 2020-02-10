package com.tibco.cep.runtime.model.element.stategraph.impl;

import com.tibco.cep.runtime.model.element.stategraph.SimpleTransitionLink;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 5, 2004
 * Time: 5:16:42 PM
 * To change this template use File | Settings | File Templates.
 */

public class SimpleTransitionLinkImpl extends TransitionLinkImpl implements SimpleTransitionLink{

    public SimpleTransitionLinkImpl(String name, SimpleStateVertexImpl source, SimpleStateVertexImpl target, boolean isCompletion, boolean emptyCondition, int timeout) {
        super(name,source, target,isCompletion, emptyCondition, timeout);
    }
}
