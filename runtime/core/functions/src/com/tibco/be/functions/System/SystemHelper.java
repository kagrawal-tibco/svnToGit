package com.tibco.be.functions.System;


import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;
import com.tibco.be.model.functions.Enabled;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;


/**
 * User: aamaya
 * Date: Jul 18, 2004
 * Time: 10:39:02 PM
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "System",
        synopsis = "System Wide Functions")
public class SystemHelper {

    public static final String BRK = System.getProperty("line.separator", "\n");
    
    @com.tibco.be.model.functions.BEFunction(
        name = "debugOut",
        synopsis = "Outputs the String passed to the USER sink.",
        signature = "void debugOut (String str)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "str", type = "String", desc = "A value to be output to the USER sink.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Outputs the String passed to the USER sink.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static void debugOut(String str) {
        LogManagerFactory.getLogManager().getLogger("user").log(Level.INFO, str);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSystemPropertyAsDouble",
        synopsis = "Returns the requested System property value as an double.",
        signature = "double getSystemPropertyAsDouble (String propertyKey, double defaultValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyKey", type = "String", desc = "The property key to set a new value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "defaultValue", type = "double", desc = "The value to return if the requested value is not present.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The requested System property value as an double."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the requested System property value as an double.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static double getSystemPropertyAsDouble(String propertyKey, double defaultValue) {
        String ret = System.getProperty(propertyKey);
        if(ret == null)
            return ((BEProperties)RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getProperties()).getDouble(propertyKey, defaultValue);
        else {
            try {
                return Double.parseDouble(ret);
            }
            catch(Exception e) {
                return defaultValue;
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSystemPropertyAsInt",
        synopsis = "Returns the requested System property value as an int.",
        signature = "int getSystemPropertyAsInt (String propertyKey, int defaultValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyKey", type = "String", desc = "The property key to set a new value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "defaultValue", type = "int", desc = "The value to return if the requested value is not present.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The requested System property value as an int."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the requested System property value as an int.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static int getSystemPropertyAsInt(String propertyKey, int defaultValue) {
        String ret = System.getProperty(propertyKey);
        if(ret == null)
            return ((BEProperties)RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getProperties()).getInt(propertyKey, defaultValue);
        else {
            try {
                return Integer.parseInt(ret);
            }
            catch(Exception e) {
                return defaultValue;
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSystemPropertyAsLong",
        synopsis = "Returns the requested System property value as a long.",
        signature = "long getSystemPropertyAsLong (String propertyKey, long defaultValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyKey", type = "String", desc = "The property key to set a new value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "defaultValue", type = "long", desc = "The value to return if the requested value is not present.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The requested System property value as a long."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the requested System property value as a long.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static long getSystemPropertyAsLong(String propertyKey, long defaultValue) {
        String ret = System.getProperty(propertyKey);
        if(ret == null)
            return ((BEProperties)RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getProperties()).getLong(propertyKey, defaultValue);
        else {
            try {
                return Long.parseLong(ret);
            }
            catch(Exception e) {
                return defaultValue;
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSystemPropertyAsString",
        synopsis = "Returns the requested System property value as a String.",
        signature = "String getSystemPropertyAsString (String propertyKey, String defaultValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyKey", type = "String", desc = "The property key to set a new value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "defaultValue", type = "String", desc = "The value to return if the requested value is not present.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The requested System property value as a String."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the requested System property value as a String.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static String getSystemPropertyAsString(String propertyKey, String defaultValue) {
        String ret = System.getProperty(propertyKey);
        if(ret == null)
            return ((BEProperties)RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getProperties()).getString(propertyKey, defaultValue);
        else {
            return ret;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSystemPropertyAsBoolean",
        synopsis = "Returns the requested System property value as a boolean.",
        signature = "boolean getSystemPropertyAsBoolean (String propertyKey, boolean defaultValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyKey", type = "String", desc = "The System property to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "defaultValue", type = "boolean", desc = "The value to return if the requested value is not present.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "The requested System property value as a boolean."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the requested System property value as a boolean.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static boolean getSystemPropertyAsBoolean(String propertyKey, boolean defaultValue) {
        String ret = System.getProperty(propertyKey);
        if(ret == null)
            return ((BEProperties)RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getProperties()).getBoolean(propertyKey, defaultValue);
        else {
            try {
                return Boolean.valueOf(ret).booleanValue();
            }
            catch(Exception e) {
                return defaultValue;
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getGlobalVariableAsBoolean",
        synopsis = "Returns the requested global variable's value as a boolean.",
        signature = "boolean getGlobalVariableAsBoolean (String propertyKey, boolean defaultValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyKey", type = "String", desc = "The property key to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "defaultValue", type = "boolean", desc = "The value to return if the requested value is not present.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "The requested global variable's value as a boolean."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the requested global variable's value as a boolean.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean getGlobalVariableAsBoolean(String propertyKey, boolean defaultValue) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        GlobalVariables gvars = session.getRuleServiceProvider().getGlobalVariables();
        boolean b = gvars.getVariableAsBoolean(propertyKey, defaultValue);

        return b;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getGlobalVariableAsLong",
        synopsis = "Returns the requested global variable's value as a long.",
        signature = "long getGlobalVariableAsLong (String propertyKey, long defaultValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyKey", type = "String", desc = "The property key to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "defaultValue", type = "long", desc = "The value to return if the requested value is not present.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The requested global variable's value as a long."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the requested global variable's value as a long.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long getGlobalVariableAsLong(String propertyKey, long defaultValue) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        GlobalVariables gvars = session.getRuleServiceProvider().getGlobalVariables();
        long l = gvars.getVariableAsLong(propertyKey, defaultValue);
        return l;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getGlobalVariableAsInt",
        synopsis = "Returns the requested global variable's value as an int.",
        signature = "int getGlobalVariableAsInt (String propertyKey, int defaultValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyKey", type = "String", desc = "The property key to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "defaultValue", type = "int", desc = "The value to return if the requested value is not present.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The requested global variable's value as an int."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the requested global variable's value as an int.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static  int getGlobalVariableAsInt(String propertyKey, int defaultValue) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        GlobalVariables gvars = session.getRuleServiceProvider().getGlobalVariables();
        int i = gvars.getVariableAsInt(propertyKey, defaultValue);
        return i;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getGlobalVariableAsDouble",
        synopsis = "Returns the requested global variable's value as a double.",
        signature = "double getGlobalVariableAsDouble (String propertyKey, double defaultValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyKey", type = "String", desc = "The property key to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "defaultValue", type = "double", desc = "The value to return if the requested value is not present.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The requested global variable's value as a double."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the requested global variable's value as a double.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double getGlobalVariableAsDouble(String propertyKey, double defaultValue) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        GlobalVariables gvars = session.getRuleServiceProvider().getGlobalVariables();
        double d = gvars.getVariableAsDouble(propertyKey, defaultValue);
        return d;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getGlobalVariableAsString",
        synopsis = "Returns the requested global variable's value as a String.",
        signature = "String getGlobalVariableAsString (String propertyKey, String defaultValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyKey", type = "String", desc = "The property key to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "defaultValue", type = "String", desc = "The value to return if the requested value is not present.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The requested global variable's value as a String."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the requested global variable's value as a String.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String getGlobalVariableAsString(String propertyKey, String defaultValue) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        GlobalVariables gvars = session.getRuleServiceProvider().getGlobalVariables();
        String s = gvars.getVariableAsString(propertyKey, defaultValue);
        return s;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "substituteGlobalVariables",
        synopsis = "Substitutes all known global variables in the input <code>text</code> (e.g. %%name%%)\nwith their values and return the resulting String.",
        enabled = @Enabled(value=false),
        signature = "String substituteGlobalVariables (String text)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "text", type = "String", desc = "A String that may contain Global Variables.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The String obtained by substituting all the global variables with their values."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Substitutes all global variables in the input <code>text</code> (e.g. %%name%%)\nwith their values and return the resulting value.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static String substituteGlobalVariables(String text) {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        GlobalVariables gvars = session.getRuleServiceProvider().getGlobalVariables();

        final CharSequence cs = gvars.substituteVariables(text);
        if (null == cs) {
            return "";
        }//if
        return cs.toString();

    }//substituteGlobalVariables

     @com.tibco.be.model.functions.BEFunction(
        name = "currentTimeMillis",
        synopsis = "Return currentTime in milliseconds",
        signature = "long currentTimeMillis()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The current time in millisecond."),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return currentTime in milliseconds",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long currentTimeMillis(){
        return System.currentTimeMillis();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "nanoTime",
        synopsis = "The current value of the system timer, in nanoseconds. This does not\nreflect the current time.",
        signature = "long nanoTime()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The current value of the system timer, in nanoseconds. This does not\nreflect the current time."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "The current value of the system timer, in nanoseconds. This does not\nreflect the current time.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long nanoTime() {
        return System.nanoTime();
   }

    @com.tibco.be.model.functions.BEFunction(
        name = "exec",
        synopsis = "Executes the specified string command in a separate process. The command argument is parsed\ninto tokens and then executed as a command in a separate process.",
        signature = "void exec(String command)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "command", type = "String", desc = "a specified system command.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Executes the specified string command in a separate process. The command argument is parsed\ninto tokens and then executed as a command in a separate process.",
        cautions = "",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void exec(String command) {
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //static Map cachedJavaMethod = Collections.synchronizedMap(new HashMap());
    static Map cachedJavaMethod = new ConcurrentHashMap();

    @com.tibco.be.model.functions.BEFunction(
        name = "execJava",
        synopsis = "Executes the specified Java method with the object instance and parameters.",
        signature = "Object execJava(String className, String method, String[] parameterTypes, Object object, Object[] parameters)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "className", type = "String", desc = "the class name of the Java method."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "method", type = "String", desc = "the name of the method."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "parameterTypes", type = "String[]", desc = "an array of Class in String format that identify the method's formal parameter types in declared order, use null if no arguments."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "object", type = "Object", desc = "the object the underlying method is invoked from, use null for static method."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "parameters", type = "Object[]", desc = "the arguments used for the method call, use null if no arguments.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "the result of the java method."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Executes the specified Java method with the object instance and parameters.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object execJava(String className, String method, String[] parameterTypes, Object object, Object[] parameters) {
        try {
            String cachedKey = className + "/" + method;
            for(int i = 0; parameterTypes != null && i < parameterTypes.length; i++) {
                cachedKey += "/" + parameterTypes[i];
            }
            Method m = (Method) cachedJavaMethod.get(cachedKey);
            if(m == null) {
                Class c = Class.forName(className);
                Class[] types  = parameterTypes == null ? null : new Class[parameterTypes.length];
                for(int i = 0; parameterTypes != null &&  i < parameterTypes.length; i++) {
                    String name = parameterTypes[i];
                    if(name.equals("int"))
                        types[i] = int.class;
                    else if (name.equals("long"))
                        types[i] = long.class;
                    else if (name.equals("double"))
                        types[i] = double.class;
                    else if (name.equals("boolean"))
                        types[i] = boolean.class;
                    else if (name.equals("short"))
                        types[i] = short.class;
                    else if (name.equals("float"))
                        types[i] = float.class;
                    else if (name.equals("char"))
                        types[i] = char.class;
                    else if (name.equals("byte"))
                        types[i] = byte.class;
                    else                     
                        types[i] = Class.forName(parameterTypes[i]);
                }
                m = c.getMethod(method, types);
                cachedJavaMethod.put(cachedKey, m);
            }
            return m.invoke(object, parameters);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new RuntimeException(e);
        }
    }
}
