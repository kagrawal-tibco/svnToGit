package com.tibco.cep.kernel.helper;

import com.tibco.cep.kernel.model.knowledgebase.ExecutionContext;
import com.tibco.cep.kernel.model.rule.RuleFunction;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 11, 2007
 * Time: 1:08:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class FunctionExecutionContext implements ExecutionContext {
    RuleFunction func;
    Object[] args;

    public FunctionExecutionContext(RuleFunction func, Object[] args) {
        this.func = func;
        this.args = args;
    }

    public Object getCause() {
        return func;
    }

    public Object getParams() {
        return args;
    }

    public String[] info() {
        String value;
        int len = args==null? 0: args.length;
        String[] ret = new String[len + 1];
        value = func.getSignature();
//        if(value.startsWith("be.gen"))
//            value = value.substring("be.gen".length() + 1);

        ret[0] = "RuleFunction=" + value;
        for(int i=1; i < ret.length; i++) {
            if (args[i-1] != null) {
                value = args[i-1].toString();
                ret[i] = value.startsWith("be.gen")? value.substring("be.gen".length() + 1): value;
            }
        }
        return ret;
    }
    
}
