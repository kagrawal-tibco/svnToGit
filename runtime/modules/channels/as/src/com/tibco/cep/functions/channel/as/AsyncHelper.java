package com.tibco.cep.functions.channel.as;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.as.space.*;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

import java.util.List;

import com.tibco.be.model.functions.Enabled;
import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.FunctionParamDescriptor;

/*
* Author: Suresh Subramani / Date: 10/16/12 / Time: 5:17 PM
*/
@com.tibco.be.model.functions.BEPackage(
		catalog = "ActiveSpaces",
        category = "Metaspace.Async",
        synopsis = "Async functions",
        enabled = @Enabled(property="com.tibco.cep.functions.as.metaspace.async", value=false))

public class AsyncHelper {

    @BEFunction(
            name = "begin",
            synopsis = "Begin Async space operations. It returns a Async operation handle, that needs to be passed to all the async operations",
        	signature = "Object begin (String fnHandler)",
            params = {
                    @FunctionParamDescriptor(name = "fnHandler", type = "String", desc = "The rule function that handles the async operation.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "a Async operation handle, that needs to be passed to all the async operations"),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Begin Async operations. The rulefunction that handles the Async operation should have the following signature" +
                    " void AsyncFunctionHandler(Object opResponse, Object[] closure)",
            cautions = "none",
            fndomain = {ACTION},
            example = "Object asyncHandle = Metaspace.Async.begin('/Rulefunctions/AsyncFunctionHandler');  " +
                    " Metaspace.Async.get(spaceName, keyTuple, asyncHandle, varargs{closure}); " +
                    " Metaspace.Async.finish(asyncHandle);"
    )
    public static Object begin(String fnHandler) {
        return new AsyncOperationHandle(fnHandler);
    }

    @BEFunction(
            name = "finish",
            synopsis = "Finish Async operations. It will block till the operations are complete",
            signature = "void finish (Object asyncHandle )",
            params = {
                    @FunctionParamDescriptor(name = "asyncHandle", type = "Object", desc = "The async handle that was made available when begin was issued.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Finish Async operations. It will block till the operations are complete",
            cautions = "none",
            fndomain = {ACTION},
            example = "Object asyncHandle = Metaspace.Async.begin('/Rulefunctions/AsyncFunctionHandler');  " +
                    " Metaspace.Async.get(spaceName, keyTuple, asyncHandle, varargs{closure}); " +
                    " Metaspace.Async.finish(asyncHandle);"
    )
    public static void finish(Object handle) {
        try {
            AsyncOperationHandle asyncHandle = (AsyncOperationHandle) handle;
            RuleSession ruleSession = RuleSessionManager.getCurrentRuleSession();
            RuleFunction ruleFunction = ((RuleSessionImpl)ruleSession).getRuleFunction(asyncHandle.getRuleFunctionName());
            asyncHandle.finish();
            List<AsyncOperationHandle.CallbackResult> callbackResults = asyncHandle.getCallbackResults();
            for(AsyncOperationHandle.CallbackResult cbresult : callbackResults) {
                if (cbresult != null) { //Fixed BE:17813, don't know why getting null result. need to investigate it further
            		ruleFunction.invoke(new Object[] {cbresult.getResult(), cbresult.getClosure()});
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void cancel(Object handle) {
    }

    @BEFunction(
            name = "get",
            synopsis = "Space Get operation. It will return immediately. The result will be invoked on the Async Function Handler. The response Object is a SpaceResult, and closure is what user supplied in varargs",
            signature = "void get(String spaceName, Object key, Object asyncHandle, Object... closure)",
            params = {
                    @FunctionParamDescriptor(name = "space", type = "String", desc = "The spaceName from which the get has to performed"),
                    @FunctionParamDescriptor(name = "key", type = "Object", desc = "Tuple based key"),
                    @FunctionParamDescriptor(name = "asyncHandle", type = "Object", desc = "The async handle that was made available when begin was issued."),
                    @FunctionParamDescriptor(name = "closure", type = "Object...", desc = "varargs for the closure.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Space async Get operations. ",
            async = true,
            cautions = "none",
            fndomain = {ACTION},
            example = "\n" +
                    "Object asyncHandle = Metaspace.Async.begin(\"/Rulefunctions/AsyncFunctionHandler\");  \n" +
                    "Metaspace.Async.get(spaceName, keyTuple, asyncHandle, concept, \"fieldfoo\"); \n" +
                    "Metaspace.Async.finish(asyncHandle);"
    )
    public static void get(String spaceName, Object key, Object handle, Object... closure) {
        try {
            GetOptions getOptions = GetOptions.create();
            AsyncOperationHandle asyncHandle = (AsyncOperationHandle) handle;
            getOptions.setResultHandler(asyncHandle);
            getOptions.setClosure(closure);
            Space space = ASSpaceHelper.getSpace(spaceName);
            space.get((Tuple)key, getOptions);
            asyncHandle.acquire();
        } catch (ASException e) {
            throw  new RuntimeException(e);
        }
    }

    @BEFunction(
            name = "put",
            synopsis = "Space Put operation. It will return immediately. The result will be invoked on the Async Function Handler. The response Object is a SpaceResult, and closure is what user supplied in varargs",
            signature = "void put(String spaceName, Object tuple, Object asyncHandle, Object... closure)",
            params = {
                    @FunctionParamDescriptor(name = "space", type = "String", desc = "The spaceName against which the put has to performed"),
                    @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "The Tuple you want to put"),
                    @FunctionParamDescriptor(name = "asyncHandle", type = "Object", desc = "The async handle that was made available when begin was issued."),
                    @FunctionParamDescriptor(name = "closure", type = "Object...", desc = "varargs for the closure.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Space async Put operation",
            cautions = "none",
            async = true,
            fndomain = {ACTION},
            example = "\n" +
                    "Object asyncHandle = Metaspace.Async.begin(\"/Rulefunctions/AsyncFunctionHandler\");  \n" +
                    "Metaspace.Async.get(spaceName, keyTuple, asyncHandle, concept, \"fieldfoo\"); \n" +
                    "Metaspace.Async.finish(asyncHandle);"
    )
    public static void put(String spaceName, Object tuple, Object handle, Object... closure) {
        try {
            PutOptions putOptions = PutOptions.create();
            AsyncOperationHandle asyncHandle = (AsyncOperationHandle) handle;
            putOptions.setResultHandler(asyncHandle);
            putOptions.setClosure(closure);
            Space space = ASSpaceHelper.getSpace(spaceName);
            space.put((Tuple) tuple, putOptions);
            asyncHandle.acquire();
        } catch (ASException e) {
            throw  new RuntimeException(e);
        }
    }

    @BEFunction(
            name = "take",
            synopsis = "Space take operation. It will return immediately. The result will be invoked on the Async Function Handler. The response Object is a SpaceResult, and closure is what user supplied in varargs",
            signature = "void take(String spaceName, Object tuple, Object asyncHandle, Object... closure)",
            params = {
                    @FunctionParamDescriptor(name = "space", type = "String", desc = "The spaceName from which the take has to performed"),
                    @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "The Tuple you want to take"),
                    @FunctionParamDescriptor(name = "asyncHandle", type = "Object", desc = "The async handle that was made available when begin was issued."),
                    @FunctionParamDescriptor(name = "closure", type = "Object...", desc = "varargs for the closure.")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Space Take async operation. ",
            cautions = "none",
            async = true,
            fndomain = {ACTION},
            example = "\n" +
                    "Object asyncHandle = Metaspace.Async.begin(\"/Rulefunctions/AsyncFunctionHandler\");  \n" +
                    "Metaspace.Async.take(spaceName, keyTuple, asyncHandle, concept, \"fieldfoo\"); \n" +
                    "Metaspace.Async.finish(asyncHandle);"
    )
    public static void take(String spaceName, Object tuple, Object handle, Object... closure) {
        try {
            TakeOptions takeOptions = TakeOptions.create();
            AsyncOperationHandle asyncHandle = (AsyncOperationHandle) handle;
            takeOptions.setResultHandler(asyncHandle);
            takeOptions.setClosure(closure);
            Space space = ASSpaceHelper.getSpace(spaceName);
            space.take((Tuple) tuple, takeOptions);
            asyncHandle.acquire();
        } catch (ASException e) {
            throw  new RuntimeException(e);
        }
    }
}
