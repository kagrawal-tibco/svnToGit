package com.tibco.cep.runtime.model.element.impl.property.simple;


import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;


/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 11, 2006
 * Time: 5:38:29 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PropertyStateMachineCompositeState extends PropertyStateMachineState{
    protected PropertyStateMachineCompositeState(Object owner) {
        super(owner);    //To change body of overridden methods use File | Settings | File Templates.
    }               

    protected PropertyStateMachineCompositeState(Object owner, int defaultValue) {
        super(owner, defaultValue);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void childDone(Object [] args) {

        if (hasCompletion() || isEndState()) {
            final Logger logger = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()
                    .getLogger(this.getClass());
            if (logger.isEnabledFor(Level.DEBUG)) {
                logger.log(Level.DEBUG, "%s [State-ChildDone] %s", args[0], getFullName());
            }

            exit(args, true);
        }
    }

    public void timeout(Object[] objs) {
        RuleSession rs = null;
        boolean reEnter = false;
        if(getTimeoutPolicy() == com.tibco.cep.runtime.model.element.stategraph.StateVertex.NO_ACTION_TIMEOUT_POLICY) {
            rs = RuleSessionManager.getCurrentRuleSession();
            reEnter = true;
        }
        timeoutChildren(objs, reEnter, rs);
        super.timeout(objs);
    }

    public void timeoutChildren(Object[] objs, boolean reEnter, RuleSession rs) {}

    public abstract void exitChildren(Object [] args);

    public abstract void rollbackChildren();

    public void rollback() {
        rollbackChildren();
        super.rollback();
    }
}
