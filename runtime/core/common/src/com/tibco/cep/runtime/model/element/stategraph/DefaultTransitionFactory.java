package com.tibco.cep.runtime.model.element.stategraph;

import com.tibco.cep.runtime.model.element.stategraph.impl.CompositeStateVertexImpl;
import com.tibco.cep.runtime.model.element.stategraph.impl.FromAndToGroupTransitionLinkImpl;
import com.tibco.cep.runtime.model.element.stategraph.impl.FromGroupTransitionLinkImpl;
import com.tibco.cep.runtime.model.element.stategraph.impl.InternalTransitionLinkImpl;
import com.tibco.cep.runtime.model.element.stategraph.impl.SimpleStateVertexImpl;
import com.tibco.cep.runtime.model.element.stategraph.impl.SimpleTransitionLinkImpl;
import com.tibco.cep.runtime.model.element.stategraph.impl.StartStateVertexImpl;
import com.tibco.cep.runtime.model.element.stategraph.impl.StartTransitionLinkImpl;
import com.tibco.cep.runtime.model.element.stategraph.impl.StateVertexImpl;
import com.tibco.cep.runtime.model.element.stategraph.impl.ToGroupTransitionLinkImpl;
import com.tibco.cep.runtime.model.element.stategraph.impl.TransitionLinkImpl;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 27, 2004
 * Time: 1:44:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultTransitionFactory {

    public static TransitionLinkImpl newTransition(String transitionName, StateVertex from, StateVertex to,
                                        int timeout, boolean isLambda, boolean emptyCondition, boolean isInternal) throws RuntimeException {

        if (to instanceof StartStateVertex) {
            return new StartTransitionLinkImpl(transitionName, (CompositeStateVertexImpl)from, (StartStateVertexImpl)to);
        }
        else if ((from instanceof CompositeStateVertex) && (to instanceof CompositeStateVertex)) {
            return new FromAndToGroupTransitionLinkImpl(transitionName, (CompositeStateVertexImpl)from, (CompositeStateVertexImpl)to, isLambda, emptyCondition, timeout);
        }
        else if ((from instanceof CompositeStateVertex) ) {
            return new FromGroupTransitionLinkImpl(transitionName, (CompositeStateVertexImpl)from, (SimpleStateVertexImpl)to, isLambda, emptyCondition, timeout);
        }
        else if ((to instanceof CompositeStateVertex)) {
            return new ToGroupTransitionLinkImpl(transitionName, (SimpleStateVertexImpl)from, (CompositeStateVertexImpl)to, isLambda, emptyCondition, timeout);
        }
        else if (isInternal) {
            return new InternalTransitionLinkImpl(transitionName, (StateVertexImpl)from);
        }
        return new SimpleTransitionLinkImpl(transitionName, (SimpleStateVertexImpl)from, (SimpleStateVertexImpl)to, isLambda, emptyCondition, timeout);
    }
}
