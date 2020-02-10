package com.tibco.cep.runtime.model.element.stategraph;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 18, 2004
 * Time: 7:49:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ForkTransitionLink extends StartTransitionLink{
    public StartStateVertex [] getTargets();
}
