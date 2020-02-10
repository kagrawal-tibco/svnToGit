package com.tibco.cep.query.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.query.api.QueryListener;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

import java.util.concurrent.ConcurrentHashMap;

@com.tibco.be.model.functions.BEPackage(
		catalog = "CEP Query",
        category = "Query.Callback",
        synopsis = "Query callback manipulation functions.")
public class CallbackFunctions {


    protected static final ConcurrentHashMap<String, ListenerContext> NAME_TO_CONTEXT =
            new ConcurrentHashMap<String, ListenerContext>();



    protected static class ListenerContext {

        private QueryListener listener;
        private String name;
        private String statementName;

        public ListenerContext(String name, QueryListener listener, String statementName) {
            this.listener = listener;
            this.name = name;
            this.statementName = statementName;
        }

        public QueryListener getListener() {
            return listener;
        }

        public String getName() {
            return this.name;
        }

        public String getStatementName() {
            return this.statementName;
        }
    }



    private static QuerySession checkAndGetQuerySession() {
        final RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if (!(session instanceof QuerySession)) {
            throw new RuntimeException("Operation not allowed outside of a QuerySession.");
        }
        return (QuerySession) session;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "delete",
        synopsis = "Deletes the listener.",
        signature = "void delete(String listenerName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listenerName", type = "String", desc = "name of the listener.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Deletes the listener.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void delete(String listenerName) {
        checkAndGetQuerySession();
        final ListenerContext context = NAME_TO_CONTEXT.remove(listenerName);
        if (null != context) {
            QueryListener listener = context.getListener();
            if(listener instanceof RuleFunctionQueryListener) {
                ((RuleFunctionQueryListener)listener).getListenerHandle().requestStop();    
            } 
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "exists",
        synopsis = "Checks if a listener with the given name exists.",
        signature = "boolean exists(String listenerName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "listenerName", type = "String", desc = "name of the listener.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if a listener by that name exists."),
        version = "3.0.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Checks if a listener with the given name exists.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static boolean exists(String listenerName) {
        return null != NAME_TO_CONTEXT.get(listenerName);
    }


    static void register(String statementName, String listenerName, RuleFunctionQueryListener listener) {
        if ((null == listenerName)
                || (null == listener)
                || (null == statementName)) {
            throw new IllegalArgumentException();
        }
        final ListenerContext context = new ListenerContext(listenerName, listener, statementName);
        if (null != NAME_TO_CONTEXT.putIfAbsent(listenerName, context)) {
            throw new IllegalArgumentException("Listener name already in use.");            
        }
    }

    static void register(String statementName, String listenerName, QueryListener listener) {
        if ((null == listenerName)
                || (null == listener)
                || (null == statementName)) {
            throw new IllegalArgumentException();
        }
        final ListenerContext context = new ListenerContext(listenerName, listener, statementName);
        if (null != NAME_TO_CONTEXT.putIfAbsent(listenerName, context)) {
            throw new IllegalArgumentException("Listener name already in use.");
        }
    }


}
