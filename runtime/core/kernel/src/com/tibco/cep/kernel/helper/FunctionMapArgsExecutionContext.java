package com.tibco.cep.kernel.helper;

import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.kernel.model.knowledgebase.ExecutionContext;
import com.tibco.cep.kernel.model.rule.RuleFunction;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 11, 2007
 * Time: 1:01:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class FunctionMapArgsExecutionContext implements ExecutionContext {
    RuleFunction func;
    Map args;

    public FunctionMapArgsExecutionContext(RuleFunction func, Map args) {
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
        int len = args==null? 0: args.size();
        String[] ret = new String[len + 1];
        ret[0] = "RuleFunction=" + func.getSignature();
        Iterator ite=args.entrySet().iterator();
        int count = 1;
        while(ite.hasNext()) {
            Map.Entry entry = (Map.Entry) ite.next();
            String obj = entry.getValue().toString();
            if(obj.startsWith("be.gen"))
                obj = obj.substring("be.gen".length() + 1);
            ret[count] = entry.getKey() + "=" +  obj;
            count++;
        }
        return ret;
    }


}
