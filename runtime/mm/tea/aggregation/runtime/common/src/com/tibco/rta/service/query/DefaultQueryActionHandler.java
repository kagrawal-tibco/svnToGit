package com.tibco.rta.service.query;

import java.util.Properties;

import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.runtime.model.rule.Action;
import com.tibco.rta.runtime.model.rule.ActionHandlerContext;
import com.tibco.rta.runtime.model.rule.Rule;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 10/1/13
 * Time: 9:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultQueryActionHandler implements ActionHandlerContext {

    @Override
    public void init(Properties configuration) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void stop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Action getAction(Rule rule, ActionDef actionDef) {
        return DefaultQueryAction.getInstance(rule, actionDef, this);
    }
}
