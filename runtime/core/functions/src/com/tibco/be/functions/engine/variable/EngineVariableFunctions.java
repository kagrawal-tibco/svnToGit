package com.tibco.be.functions.engine.variable;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;

import java.util.Calendar;


@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Engine.Variable",
        synopsis = "Functions to set and get Engine Variable.")
public class EngineVariableFunctions {
    @com.tibco.be.model.functions.BEFunction(
        name = "getEngineIntVar",
        synopsis = "Gets the engine variable as int of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns 0 if the variable is not set.",
        signature = "int getEngineIntVar(String varName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The value of this variable"),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the engine variable as int of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns 0 if the variable is not set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int getEngineIntVar(String varName) {
        Integer i = (Integer) ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).getVariable(varName);
        if(i == null) return 0;
        return i.intValue();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setEngineIntVar",
        synopsis = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        signature = "void setEngineIntVar(String varName, int value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "int", desc = "The value of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.\n* @.cautions none",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setEngineIntVar(String varName, int value) {
        ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).setVariable(varName, new Integer(value));

    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEngineBooleanVar",
        synopsis = "Gets the engine variable as boolean of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns false if the variable is not set.",
        signature = "boolean getEngineBooleanVar(String varName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "The value of this variable"),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the engine variable as boolean of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns false if the variable is not set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean getEngineBooleanVar(String varName) {
        Boolean b = (Boolean) ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).getVariable(varName);
        if(b == null) return false;
        return b.booleanValue();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setEngineBooleanVar",
        synopsis = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        signature = "void setEngineBooleanVar(String varName, boolean value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "boolean", desc = "The value of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.\n* @.cautions none",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setEngineBooleanVar(String varName, boolean value) {
        ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).setVariable(varName, Boolean.valueOf(value));
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEngineDateTimeVar",
        synopsis = "Gets the engine variable as DateTime of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        signature = "DateTime getEngineDateTimeVar(String varName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "The value of this variable"),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the engine variable as DateTime of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar getEngineDateTimeVar(String varName) {
        return (Calendar) ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).getVariable(varName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setEngineDateTimeVar",
        synopsis = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        signature = "void setEngineDateTimeVar(String varName, DateTime value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "DateTime", desc = "The value of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.\n* @.cautions none",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setEngineDateTimeVar(String varName, Calendar value) {
        ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).setVariable(varName, value);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEngineObjectVar",
        synopsis = "Gets the engine variable as Object of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        signature = "Object getEngineObjectVar(String varName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The value of this variable"),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the engine variable as Object of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object getEngineObjectVar(String varName) {
        return ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).getVariable(varName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setEngineObjectVar",
        synopsis = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        signature = "void setEngineObjectVar(String varName, Object value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "The value of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.\n* @.cautions none",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setEngineObjectVar(String varName, Object value) {
        ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).setVariable(varName, value);

    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEngineDoubleVar",
        synopsis = "Gets the engine variable as double of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns 0.0 if the variable is not set.",
        signature = "double getEngineDoubleVar(String varName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "The value of this variable"),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the engine variable as double of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function 0.0 false if the variable is not set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double getEngineDoubleVar(String varName) {
        Double d = (Double) ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).getVariable(varName);
        if(d == null) return 0.0;
        return d.doubleValue();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setEngineDoubleVar",
        synopsis = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        signature = "void setEngineDoubleVar(String varName, double value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "double", desc = "The value of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.\n* @.cautions none",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setEngineDoubleVar(String varName, double value) {
        ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).setVariable(varName, new Double(value));
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEngineLongVar",
        synopsis = "Gets the engine variable as long of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns 0L if the variable is not set.",
        signature = "long getEngineLongVar(String varName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The value of this variable"),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the engine variable as long of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns 0L if the variable is not set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long getEngineLongVar(String varName) {
        Long ll = (Long) ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).getVariable(varName);
        if(ll == null) return 0L;
        return ll.longValue();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setEngineLongVar",
        synopsis = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        signature = "void setEngineLongVar(String varName, long value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "long", desc = "The value of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.\n* @.cautions none",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setEngineLongVar(String varName, long value) {
        ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).setVariable(varName, new Long(value));
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEngineStringVar",
        synopsis = "Gets the engine variable as String of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        signature = "String getEngineStringVar(String varName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The value of this variable"),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the engine variable as String of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String getEngineStringVar(String varName) {
        return (String)((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).getVariable(varName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setEngineStringVar",
        synopsis = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        signature = "void setEngineStringVar(String varName, String value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "String", desc = "The value of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.\n* @.cautions none",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setEngineStringVar(String varName, String value) {
        ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).setVariable(varName, value);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEngineIntArrayVar",
        synopsis = "Gets the engine variable as int[] of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        signature = "int[] getEngineIntArrayVar(String varName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int[]", desc = "The value of this variable"),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the engine variable as int[] of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int[] getEngineIntArrayVar(String varName) {
        return (int[])((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).getVariable(varName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setEngineIntArrayVar",
        synopsis = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        signature = "void setEngineIntArrayVar(String varName, int[] value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "int[]", desc = "The value of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.\n* @.cautions none",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setEngineIntArrayVar(String varName, int[] value) {
        ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).setVariable(varName, value);

    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEngineBooleanArrayVar",
        synopsis = "Gets the engine variable as boolean[] of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        signature = "boolean[] getEngineBooleanArrayVar(String varName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean[]", desc = "The value of this variable"),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the engine variable as boolean[] of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )

    public static boolean[] getEngineBooleanArrayVar(String varName) {
        return (boolean[]) ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).getVariable(varName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setEngineBooleanArrayVar",
        synopsis = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        signature = "void setEngineBooleanArrayVar(String varName, boolean[] value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "boolean[]", desc = "The value of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.\n* @.cautions none",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setEngineBooleanArrayVar(String varName, boolean[] value) {
        ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).setVariable(varName, value);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEngineDateTimeArrayVar",
        synopsis = "Gets the engine variable as DateTime[] of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        signature = "DateTime[] getEngineDateTimeArrayVar(String varName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "The value of this variable"),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the engine variable as DateTime[] of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar[] getEngineDateTimeArrayVar(String varName) {
        return (Calendar[]) ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).getVariable(varName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setEngineDateTimeArrayVar",
        synopsis = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        signature = "void setEngineDateTimeArrayVar(String varName, DateTime[] value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "DateTime[]", desc = "The value of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.\n* @.cautions none",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setEngineDateTimeArrayVar(String varName, Calendar[] value) {
        ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).setVariable(varName, value);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEngineDoubleArrayVar",
        synopsis = "Gets the engine variable as double[] of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        signature = "double getEngineDoubleArrayVar(String varName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double[]", desc = "The value of this variable"),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the engine variable as double[] of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function 0.0 false if the variable is not set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double[] getEngineDoubleArrayVar(String varName) {
        return (double[]) ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).getVariable(varName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setEngineDoubleArrayVar",
        synopsis = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        signature = "void setEngineDoubleArrayVar(String varName, double[] value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "double[]", desc = "The value of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.\n* @.cautions none",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setEngineDoubleArrayVar(String varName, double[] value) {
        ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).setVariable(varName, value);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEngineLongArrayVar",
        synopsis = "Gets the engine variable as long[] of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        signature = "long[] getEngineLongArrayVar(String varName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long[]", desc = "The value of this variable"),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the engine variable as long[] of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )

    public static long[] getEngineLongArrayVar(String varName) {
        return (long[]) ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).getVariable(varName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setEngineLongArrayVar",
        synopsis = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        signature = "void setEngineLongArrayVar(String varName, long[] value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "long[]", desc = "The value of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.\n* @.cautions none",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setEngineLongArrayVar(String varName, long[] value) {
        ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).setVariable(varName, value);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getEngineStringArrayVar",
        synopsis = "Gets the engine variable as String[] of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        signature = "String[] getEngineStringArrayVar(String varName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "The value of this variable"),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the engine variable as String[] of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.  This function returns null if the variable is not set.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String[] getEngineStringArrayVar(String varName) {
        return (String[])((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).getVariable(varName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setEngineStringArrayVar",
        synopsis = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        signature = "void setEngineStringArrayVar(String varName, String[] value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "String[]", desc = "The value of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the engine variable of name varName to value.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setEngineStringArrayVar(String varName, String[] value) {
        ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).setVariable(varName, value);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "removeEngineVar",
        synopsis = "Removes the engine variable of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        signature = "void removeEngineVar(String varName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "varName", type = "String", desc = "The name of the variable.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Removes the engine variable of name varName.  Engine variable is a in-memory variable share\nacross all the rule sessions for a RuleServiceProvider.  Rule condition won't have dependency of this variable i.e. changes\nof this variable won't retrigger the rule if it is used in the condition.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void removeEngineVar(String varName) {
        ((RuleServiceProviderImpl) RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()).removeVariable(varName);
    }
}
