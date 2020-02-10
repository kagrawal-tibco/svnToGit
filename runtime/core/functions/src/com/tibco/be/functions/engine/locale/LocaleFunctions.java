package com.tibco.be.functions.engine.locale;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.cep.runtime.session.RuleSessionManager;

import java.util.Locale;
import java.util.TimeZone;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Engine.Locale",
        synopsis = "Functions to get info about the locale.")

public class LocaleFunctions {


    @com.tibco.be.model.functions.BEFunction(
        name = "getLanguage",
        synopsis = "Returns the language in current locale.",
        signature = "String getLanguage ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Language in current locale."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the language in current locale.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String getLanguage() {
        String lang =  RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getProperties().getProperty("be.locale.language");
        if(lang == null || lang.length() == 0)
            return Locale.getDefault().getLanguage();
        else
            return lang;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getCountry",
        synopsis = "Returns the country code in current locale.",
        signature = "String getCountry ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Country code in current locale."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the country code in current locale.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String getCountry() {
        String lang =  RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getProperties().getProperty("be.locale.language");
        if(lang == null || lang.length() == 0)
            return Locale.getDefault().getCountry();
        else
            return RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getProperties().getProperty("be.locale.country");
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDefaultTimeZone",
        synopsis = "Gets the default timezone; value depends on JVM implementation.",
        signature = "String getDefaultTimeZone()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The timezone id."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the default timezone; value depends on JVM implementation.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String getDefaultTimeZone() {
        return TimeZone.getDefault().getID();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setDefaultTimeZone",
        synopsis = "Sets the default timezone id. null sets it back to the value at JVM start",
        signature = "void setDefaultTimeZone(String tz)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tz", type = "String", desc = "The Time Zone ID; invalid id defaults to GMT.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the default timezone id. null sets it back to the value at JVM start.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void setDefaultTimeZone(String tz) {
        TimeZone.setDefault(TimeZone.getTimeZone(tz));
    }
}
