package com.tibco.cep.kernel.helper;

import com.tibco.cep.kernel.model.knowledgebase.ExecutionContext;
import com.tibco.cep.kernel.model.rule.Action;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 11, 2007
 * Time: 12:59:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class ActionExecutionContext implements ExecutionContext {
    Action action;
    Object[] args;

    public ActionExecutionContext(Action action, Object[] args) {
        this.action = action;
        this.args = args;
    }

    public Object getCause() {
        return action;
    }

    public Object getParams() {
        return args;
    }

    public String[] info() {
        String value;
        int len = args==null? 0: args.length;
        String[] ret = new String[len + 1];
        value = action.getClass().getName();
        if(value.startsWith("be.gen"))
            value = value.substring("be.gen".length() + 1);

        ret[0] = "Action=" + value;
        for(int i=1; i < ret.length; i++) {
            value = args[i-1].toString();
            ret[i] = value.startsWith("be.gen")? value.substring("be.gen".length() + 1): value;
        }
        return ret;
    }

}
