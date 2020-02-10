package com.tibco.be.functions.logging;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;

import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.AdvisoryEventDictionary;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.model.exception.BEException;
import com.tibco.cep.runtime.session.RuleSessionManager;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Log",
        synopsis = "Can be used to perform logging from rules/rule functions.")
public class LoggerFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "getLogger",
        synopsis = "Get a logger for use inside a rule/rulefunction.",
        signature = "Object getLogger(String loggerName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "loggerName", type = "String",
                    desc = "Name of the Logger to get. Use the project path of the current rule or rulefunction.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get a logger to be used inside a rule/rulefunction name.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "Object logger = Log.getLogger(\"Rules.VerifyCreditRF\");"
    )
    public static Object getLogger(
            String loggerName) {

        return LogManagerFactory.getLogManager().getLogger(loggerName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "log",
        synopsis = "Logs a message.",
        signature = "void log(Object logger, String logLevel, String message, Object... arguments)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "logger", type = "Object",
                    desc = "The logger obtained from getLogger(..)"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "level", type = "String",
                    desc = "The log level. Defaults to \"debug\"."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "String",
                    desc = "The message to log, with optional argument placeholders."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arguments", type = "Object...",
                    desc = "The argument values to be substituted in the message.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Logs a message.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "Object logger = Log.getLogger(\"Rules.VerifyCreditRF\");\n"
                + "Log.log(logger, \"info\", \"Logged this message with argument %s\", myArgument);"
    )
    public static void log(
            Object logger,
            String level,
            String message,
            Object... arguments) {
        doLog(logger, level, message, arguments);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "logException",
        synopsis = "Logs a message with an exception.",
        signature = "void logException(Object logger, String level, String message, BEException thrown, " +
                "Object... arguments)",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "logger", type = "Object",
                        desc = "The logger obtained from getLogger(..)"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "level", type = "String",
                        desc = "The log level. Defaults to \"debug\"."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "message", type = "String",
                        desc = "The message to log, with optional argument placeholders."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "thrown", type = "BEException",
                        desc = "An exception to be logged."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arguments", type = "Object...",
                        desc = "The argument values to be substituted in the message.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Logs a message with an exception.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "Object logger = Log.getLogger(\"Rules.VerifyCreditRF\");\n"
                + "Log.logException(logger, \"error\", \"Logged this message with argument %s\", " +
                "Exception.newException(....), myArgument);"
    )
    public static void logException(
            Object logger,
            String level,
            String message,
            BEException thrown,
            Object... arguments) {
        doLog(logger, level, message, thrown, arguments);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setLevel",
            synopsis = "Change the level of a given logger",
            signature = "void setLevel(Object oLogger, String logLevel)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "oLogger", type = "Object", desc = "The logger obtained from getLogger(..)"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "logLevel", type = "String", desc = "The log level. defaults to debug.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Change the level.",
            cautions = "none",
            fndomain = {ACTION, CONDITION, QUERY, BUI},
            example = ""
        )
    public static void setLevel(Object oLogger,
                                String logLevel) {
        doSetLevel(oLogger, logLevel);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getLevel",
            synopsis = "Get the log level of a given logger",
            signature = "String getLevel(Object oLogger)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "oLogger", type = "Object", desc = "The logger obtained from getLogger(..)")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
            version = "5.6",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get the log level.",
            cautions = "none",
            fndomain = {ACTION, CONDITION, QUERY, BUI},
            example = ""
        )
    public static String getLevel(Object oLogger) {
    	if (oLogger instanceof Logger) {
    		return ((Logger)oLogger).getLevel().toString();
    	} else {
    		throw new IllegalArgumentException("Logger is null or is not a valid Logger Object");
    	}
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setLevelByName",
            synopsis = "Change the level of all loggers matching the pattern",
            signature = "void setLevelByName(String loggerNamePattern, String logLevel)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "loggerNamePattern", type = "String", desc = "The name pattern to use to find the logger(s). Use * for all loggers"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "logLevel", type = "String", desc = "The log level. defaults to debug.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Change the level.",
            cautions = "none",
            fndomain = {ACTION, CONDITION, QUERY, BUI},
            example = ""
        )
    public static void setLevelByName(String loggerNamePattern,
                                String logLevel) {
        doSetLevel(loggerNamePattern, logLevel);
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getName",
            synopsis = "Get the name of a given logger",
            signature = "String getName(Object oLogger)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "oLogger", type = "Object", desc = "The logger obtained from getLogger(..)")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
            version = "6.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Get the name of logger.",
            cautions = "none",
            fndomain = {ACTION, CONDITION, QUERY, BUI},
            example = ""
        )
    public static String getName(Object oLogger) {
    	if (oLogger instanceof Logger) {
    		return ((Logger)oLogger).getName();
    	} else {
    		throw new IllegalArgumentException("Logger is null or is not a valid Logger Object");
    	}
    }

    /**
     * doLog invokes the logger to write appropriately log message to its destination
     * 
     * @param oLogger
     * @param logLevel
     * @param message
     * @param arguments
     */
    private static void doLog(Object oLogger,
                              String logLevel,
                              String message,
                              Object... arguments) {
    	Level level = validate(oLogger, logLevel);
    	
        Logger logger = (Logger)oLogger;
        logger.log(level, message, arguments);
    }

    /**
     * doLog invokes the logger to write appropriately error message to its destination
     * 
     * @param oLogger
     * @param logLevel
     * @param message
     * @param arguments
     */
    private static void doLog(Object oLogger,
                              String logLevel,
                              String message,
                              BEException exception,
                              Object... arguments) {
    	Level level = validate(oLogger, logLevel);
    	
        Logger logger = (Logger)oLogger;
        logger.log(level, message, exception.toException(), arguments);
    }

    /**
     * doSetLevel sets the logging level
     * 
     * @param oLogger
     * @param logLevel
     */
    private static void doSetLevel(Object oLogger,
                                   String logLevel) {
    	Level level = validate(oLogger, logLevel);
    	
        Logger logger = (Logger)oLogger;
        logger.setLevel(level);
        
        String actualLogLevel = logLevel.toLowerCase();
        assertAdvisoryEvent(((Logger)oLogger).getName(), actualLogLevel);
    }

    /**
     * doSetLevel sets the logging level
     * 
     * @param loggerNamePattern
     * @param logLevel
     */
    private static void doSetLevel(String loggerNamePattern,
                                   String logLevel) {
        if (loggerNamePattern == null) {
            throw new IllegalArgumentException("Logger Name Pattern cannot be null");
        }
        if (logLevel == null) {
           logLevel = "debug";
        }
        LogManagerFactory.getLogManager().setLevel(loggerNamePattern, logLevel);
        
        assertAdvisoryEvent(loggerNamePattern, logLevel);
    }
    
    private static void assertAdvisoryEvent(String loggerName, String logLevel) {
    	try {
        	String msg = "Log level for logger '" + loggerName + "' updated to level '" + logLevel + "'";
			RuleSessionManager.getCurrentRuleSession().assertObject(new AdvisoryEventImpl(
					RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getIdGenerator().nextEntityId(AdvisoryEventImpl.class),
			        null, AdvisoryEvent.CATEGORY_ENGINE, AdvisoryEventDictionary.ENGINE_LogLevelUpdated, msg), true);
		} catch (DuplicateExtIdException e) {
			// extId is left as null, this Exception would never occur.
		}
    }

    /**
     * validate checks the parameters for null values
     * 
     * @param oLogger
     * @param logLevel
     * @return
     */
    private static Level validate(Object oLogger, String logLevel) {
		if (oLogger == null) {
			throw new IllegalArgumentException("Logger cannot be null. Create one using getLogger first");
		}
		if (logLevel == null) {
			logLevel = "debug";
		}
		String actualLogLevel = logLevel.toLowerCase();
		Level level = Level.valueOf(actualLogLevel);
		if (level == null) {
			//Default to debug
			level = Level.DEBUG;
		}
		return level;
    }
}
