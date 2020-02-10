package com.tibco.be.ws.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/4/12
 * Time: 7:30 AM
 * @.category WS.Common.Stack
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.Common.Stack",
        synopsis = "WebStudio Stack Functions",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Common.Stack", value=true))

public class WebstudioServerUtilFunctions {

    private static Map<String, Stack> STACKS = new ConcurrentHashMap<String, Stack>();

    @com.tibco.be.model.functions.BEFunction(
        name = "create",
        signature = "Object create(String stackID)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stackID", type = "String", desc = "The String ID of the Stack in which the lookup is to be performed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Creates a new <code>Stack</code> for the given <code>stackID</code> or returns previously created one."),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a new Stack structure or returns one if exists.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object create(String stackID) {
        Stack stack = STACKS.get(stackID);
        if (null == stack) {
            stack = new Stack();
            STACKS.put(stackID, stack);
        }
        return stack;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "peek",
        signature = "Object peek(Object stackObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stackObject", type = "Object", desc = "The Stack in which lookup is desired.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The top element in the stack."),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the top element in the stack",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object peek(Object stackObject) {
        if (!(stackObject instanceof Stack)) {
            throw new IllegalArgumentException("Stack parameter should be of java.util.Stack");
        }
        return ((Stack)stackObject).peek();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "push",
        signature = "Object push(Object stackObject, Object value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stackObject", type = "Object", desc = "The Stack in which lookup is desired."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "The value to push onto the stack.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The Stack on which to push"),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Pushes an element to the top of the stack.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object push(Object stackObject, Object value) {
        if (!(stackObject instanceof Stack)) {
            throw new IllegalArgumentException("Stack parameter should be of java.util.Stack");
        }
        return ((Stack)stackObject).push(value);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "pop",
        signature = "Object pop(Object stackObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stackObject", type = "Object", desc = "The Stack in which lookup is desired.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The topmost element on the stack."),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Pops an element off the stack.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object pop(Object stackObject) {
        if (!(stackObject instanceof Stack)) {
            throw new IllegalArgumentException("Stack parameter should be of java.util.Stack");
        }
        return ((Stack)stackObject).pop();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "clear",
        signature = "void clear(Object stackObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stackObject", type = "Object", desc = "The Stack in which lookup is desired.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Clear the contents of the stack.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void clear(Object stackObject) {
        if (!(stackObject instanceof Stack)) {
            throw new IllegalArgumentException("Stack parameter should be of java.util.Stack");
        }
        ((Stack)stackObject).clear();
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "contains",
        signature = "boolean contains(Object stackObject, Object objToLookup)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stackObject", type = "Object", desc = "The Stack in which lookup is desired."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "objToLookup", type = "Object", desc = "The object that needs to be looked up.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "True if present"),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Clear the contents of the stack.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean contains(Object stackObject, Object objToLookup) {
        if (!(stackObject instanceof Stack)) {
            throw new IllegalArgumentException("Stack parameter should be of java.util.Stack");
        }
        return ((Stack)stackObject).contains(objToLookup);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isEmpty",
        signature = "boolean isEmpty(Object stackObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stackObject", type = "Object", desc = "The Stack in which lookup is desired.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Check whether this stack is empty or not..",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean isEmpty(Object stackObject) {
        if (!(stackObject instanceof Stack)) {
            throw new IllegalArgumentException("Stack parameter should be of java.util.Stack");
        }
        return ((Stack)stackObject).isEmpty();
    }

    @com.tibco.be.model.functions.BEFunction(
		name = "toArray",
		signature = "Object toArray(Object stackObject, String arrayClassType, String classloaderClass)",
		params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stackObject", type = "Object", desc = "The Stack in which lookup is desired."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arrayClassType", type = "String", desc = "Type of the array to return."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "classloaderClass", type = "String", desc = "Classloader to use to load the class. null would mean use system CL")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The array containing all stack elements"),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns an array containing all of the elements in this Stack.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
    public static Object toArray(Object stackObject,
                                 String arrayClassType,
                                 String classloaderClass) {
    	if (!(stackObject instanceof Stack)) {
            throw new IllegalArgumentException("Stack parameter should be of java.util.Stack");
        }
        Stack stack = (Stack)stackObject;
        ClassLoader classLoader = null;
        try {
            if (classloaderClass == null) {
                //Default to system
                classLoader = ClassLoader.getSystemClassLoader();
            } else {
                Class<?> classloaderClazz = Class.forName(classloaderClass);
                if (BEClassLoader.class.isAssignableFrom(classloaderClazz)) {
                    RuleSession ruleSession = RuleSessionManager.getCurrentRuleSession();
                    if (ruleSession != null) {
                        RuleServiceProvider RSP = ruleSession.getRuleServiceProvider();
                        classLoader = RSP.getClassLoader();
                    }
                }
            }
            if (arrayClassType == null) {
                return stack.toArray();
            } else {
                Class<?> arrayTypeClazz = classLoader.loadClass(arrayClassType);
                Object stackArray = Array.newInstance(arrayTypeClazz, stack.size());
                for (int i = stack.size() - 1; i >= 0; i--) {
                    Array.set(stackArray, i, stack.get(i));
                }
                return stackArray;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
		name = "toArrayList",
		signature = "Object toArrayList(Object stackObject)",
		params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "stackObject", type = "Object", desc = "The Stack in which lookup is desired.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The ArrayList containing all stack elements"),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns an ArrayList containing all of the elements in this Stack.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
    public static Object toArrayList(Object stackObject) {
    	if (!(stackObject instanceof Stack)) {
            throw new IllegalArgumentException("stackObject parameter should be of java.util.Stack");
        }
        Stack stack = (Stack)stackObject;
    	return (new ArrayList(stack));
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "delete",
        signature = "void delete(String stackID)",
        params = {
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "stackID", type = "String", desc = "The String ID of the Stack which need to be deleted")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Removes the stack.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void delete(String stackID) {
        Stack removedStack = STACKS.remove(stackID);
        if (removedStack != null) {
        	removedStack.clear();
        	removedStack = null;
        }
    }
}
