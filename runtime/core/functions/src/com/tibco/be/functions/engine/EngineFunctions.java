/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 19/8/2010
 */

package com.tibco.be.functions.engine;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashSet;
import java.util.List;

import com.tibco.be.model.functions.Enabled;
import com.tibco.be.functions.cluster.ClusterFunctions;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.codegen.RuleFunctionUserNameAnnotation;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.model.knowledgebase.ExecutionContext;
import com.tibco.cep.kernel.model.rule.*;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.repo.provider.impl.SharedArchiveResourceProviderImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.basic.ShutdownWatcher;
import com.tibco.cep.runtime.service.cluster.util.WorkManager;
import com.tibco.cep.runtime.session.BEManagedThread;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.PreprocessContext;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Engine",
        synopsis = "Functions to get information about the Engine.")

public class EngineFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "engineName",
        synopsis = "Returns the name of the TIBCO BusinessEvents engine.",
        signature = "String engineName ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The Engine name."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Engine name.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String engineName() {
        return RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getName();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "sessionName",
        synopsis = "Returns the current RuleSession Name.",
        signature = "String sessionName ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The Session Name."),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the current RuleSession Name.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static String sessionName() {
        return RuleSessionManager.getCurrentRuleSession().getName();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "sessionIndex",
        synopsis = "Returns the current Index of the RuleSession in the EAR's. If numLocal is provided, then it will give that info.",
        signature = "int sessionIndex ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The Session Index."),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the current Index of the RuleSession in the EAR's. If numLocal is provided, then it will give that info.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static int sessionIndex() {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        RuleSessionManagerImpl manager = (RuleSessionManagerImpl) RuleSessionManager.getCurrentRuleSession().getRuleRuntime();
        return manager.getSessionIndex(session);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "gc",
        synopsis = "Runs the Java Garbage Collection.",
        signature = "void gc()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Runs the Java Garbage Collection.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void gc() {
        System.runFinalization();
        System.gc();
        Thread.yield();
    }

//    /**
//     * @.name lastCheckpointDuration
//     * @.synopsis Returns the duration of last Checkpoint.  This function returns
//     * 0 for non-persistence Object Manager.
//     * @.signature long lastCheckpointDuration()
//     * @return long Duration in milliseconds.
//     * @.version 1.2
//     * @.see
//     * @.mapper false
//     * @.description
//     * Returns the duration of the last Checkpoint in milliseconds.  This function
//     * returns 0 for non-persistence Object Manager.
//     * @.cautions none
//     * @.domain action, condition
//     * @.example
//     */
//    public static long lastCheckpointDuration() {
//        Object objectStore = RuleSessionManager.getCurrentRuleSession().getObjectManager();
//        if (objectStore instanceof PersistentStore)
//            return ((PersistentStore) objectStore).lastCheckpointDuration();
//        else
//            return 0;
//    }

    @com.tibco.be.model.functions.BEFunction(
        name = "jvmMemoryMax",
        synopsis = "Returns the maximum memory size of the JVM.",
        signature = "long jvmMemoryMax ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The maximum memory size of the JVM."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the maximum memory size of the JVM.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        reevaluate = true,
        example = ""
    )
    public static long jvmMemoryMax() {
        return Runtime.getRuntime().maxMemory();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "jvmMemoryFree",
        synopsis = "Returns an estimate of the free memory available to the JVM.",
        signature = "long jvmMemoryFree ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The size of the free memory."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an estimate of the free memory available to the JVM.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        reevaluate = true,
        example = ""
    )
    public static long jvmMemoryFree() {
        return jvmMemoryMax() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "jvmMemoryUsed",
        synopsis = "Returns an estimate of the used memory in the JVM.",
        signature = "long jvmMemoryUsed ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The size of the used memory."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an estimate of the used memory in the JVM.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long jvmMemoryUsed() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "numberOfEvents",
        synopsis = "Returns the total number of events exist in the working memory",
        signature = "int numberOfEvents()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The number of events in the working memory."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the total number of events exist in the working memory.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        reevaluate = true,
        example = ""
    )
    public static int numberOfEvents() {
        return RuleSessionManager.getCurrentRuleSession().getObjectManager().numOfEvent();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "numberOfInstances",
        synopsis = "Returns the total number of instances exist in the working memory",
        signature = "int numberOfInstances()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The number of instances in the working memory."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the total number of instances exist in the working memory.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        reevaluate = true,
        example = ""
    )
    public static int numberOfInstances() {
        return RuleSessionManager.getCurrentRuleSession().getObjectManager().numOfElement();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "numberOfRulesFired",
        synopsis = "Returns the total number of rules fired in the current working memory since the counter was last reset.",
        signature = "long numberOfRulesFired ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The number of rules fired in current working memory."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the total number of rules fired in current working memory since the counter was last reset.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        reevaluate = true,
        example = ""
    )
    public static long numberOfRulesFired() {
        return ((RuleSessionImpl) RuleSessionManager.getCurrentRuleSession()).getWorkingMemory().getTotalNumberRulesFired();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "resetNumberOfRulesFired",
        synopsis = "Resets the counter that tracks the total number of rules fired in the current working memory.",
        signature = "void resetNumberOfRulesFired ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Resets the counter that tracks the total number of rules fired in current working memory.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void resetNumberOfRulesFired() {
        ((RuleSessionImpl) RuleSessionManager.getCurrentRuleSession()).getWorkingMemory().resetTotalNumberRulesFired();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "sleep",
        synopsis = "Put current rule session to sleep. Caution! In case of single-threaded RTC, this method may block current thread as well as all other rule sessions.",
        signature = "void sleep(long millisec)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Put current rule session to sleep. Caution! In case of single-threaded RTC, this method may block current thread as well as all other rule sessions.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void sleep(long millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "executionContext",
        synopsis = "Returns the current execution context.  The first element in the String[] indicates where\nthis call is executing under. The remaining elements in the String[] contains the objects\nthat associated with the context. For example, if this is executing in Rule A.B.C and the\nscope is InstanceX. This function will returns { $1Rule=A.B.C$1, $1InstanceX@id=12@extId=123$1 }.",
        signature = "String[] executionContext()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "The current execution context."),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the current execution context.  The first element in the String[] indicates where\nthis call is executing under. The remaining elements in the String[] contains the objects\nthat associated with the context.  For example, if this is executing in Rule A.B.C and the\nscope is InstanceX. This function will returns { $1Rule=A.B.C$1, $1InstanceX@id=12@extId=123$1 }.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static String[] executionContext() {
        RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        if (session == null)
            return null;
        ExecutionContext context = ((WorkingMemoryImpl) session.getWorkingMemory()).getCurrentContext();
        if (context == null)
            return null;
        return context.info(); // Note: ExecutionContext class is AgendaItem
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "ruleName",
        synopsis = "Returns the name of the currently executing Rule, or \"\" if there is none.",
        signature = "String ruleName()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The name of the currently executing Rule, or \"\" if there is none."),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the name of the currently executing Rule, or \"\" if there is none.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static String ruleName() {
        RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        if (session == null)
            return null;
        ExecutionContext context = ((WorkingMemoryImpl) session.getWorkingMemory()).getCurrentContext();
        if (context == null)
            return null;
        return context.info()[0];
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "ruleFunctionName",
        synopsis = "Returns the name of the currently executing RuleFunction, or \"\" if there is none.",
        signature = "String ruleFunctionName()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The name of the currently executing RuleFunction, or \"\" if there is none."),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the name of the currently executing RuleFunction, or \"\" if there is none.  For example, if executing RuleFunction A.B.C, this function will return \"A.B.C\".",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static String ruleFunctionName() {
        StackTraceElement[] elems = new Exception().getStackTrace();
        for(StackTraceElement elem : elems) {
        	if (elem != null) {
        		String className = elem.getClassName();
        		if (className != null && className.startsWith(ModelNameUtil.GENERATED_PACKAGE_PREFIX)) {
            		ClassLoader cl = RuleServiceProviderManager.getInstance().getDefaultProvider().getClassLoader();
            		try {
	            		Class<?> cls = cl.loadClass(className);
	            		if (cls != null) {
	            			RuleFunctionUserNameAnnotation fnName = cls.getAnnotation(RuleFunctionUserNameAnnotation.class);
	            			if (fnName != null) {
	            				return fnName.userName();
	            			}
	            		}
            		} catch (ClassNotFoundException cnfe){}
        		}
        	}
        }
        return "";
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "currentRule",
        synopsis = "Returns currently executing Rule, or 'null' if there is none.",
        signature = "Object currentRule()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Currently executing Rule, or null if there is none."),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns currently executing Rule, or 'null' if there is none.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Object currentRule() {
        RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        if (session == null)
            return null;
        ExecutionContext context = ((WorkingMemoryImpl) session.getWorkingMemory()).getCurrentContext();
        if (context == null)
            return null;
        return context.getCause();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "currentRuleFunction",
        synopsis = "Returns currently executing RuleFunction, or 'null' if there is none.",
        signature = "Object currentRuleFunction()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Currently executing RuleFunction, or null if there is none."),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns currently executing RuleFunction, or 'null' if there is none.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Object currentRuleFunction() {
        RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        if (session == null)
            return null;
        String ruleFuncURI = "/" + EngineFunctions.ruleFunctionName().replaceAll("[.]", "/");
        if ((ruleFuncURI != null) && (ruleFuncURI.trim().length() > 0)) {
            return ((RuleSessionImpl) session).getRuleFunction(ruleFuncURI);
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "currentRuleActions",
        synopsis = "Returns currently executing Rule's Actions, or 'null' if there is none.",
        signature = "Object[] currentRuleActions",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Currently executing Rule's Actions, or null if there is none."),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns currently executing Rule's Actions, or 'null' if there is none.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Object[] currentRuleActions() {
        RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        if (session == null)
            return null;
        ExecutionContext context = ((WorkingMemoryImpl) session.getWorkingMemory()).getCurrentContext();
        if (context == null)
            return null;
		Rule rule = (Rule) context.getCause();
		if (rule == null)
			return null;
        return rule.getActions();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "currentRuleConditions",
        synopsis = "Returns currently executing Rule's Conditions, or 'null' if there is none.",
        signature = "Object[] currentRuleConditions()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Currently executing Rule's Conditions, or null if there is none."),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns currently executing Rule's Conditions, or 'null' if there is none.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Object[] currentRuleConditions() {
        RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        if (session == null)
            return null;
        ExecutionContext context = ((WorkingMemoryImpl) session.getWorkingMemory()).getCurrentContext();
        if (context == null)
            return null;
		Rule rule = (Rule) context.getCause();
		if (rule == null)
			return null;
        return rule.getConditions();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "currentRuleIdentifiers",
        synopsis = "Returns currently executing Rule's Identifiers (arguments), or 'null' if there is none.",
        signature = "Object[] currentRuleIdentifiers()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Currently executing Rule's Identifiers (arguments), or null if there is none."),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns currently executing Rule's Identifiers (arguments), or 'null' if there is none.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Object[] currentRuleIdentifiers() {
        RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        if (session == null)
            return null;
        ExecutionContext context = ((WorkingMemoryImpl) session.getWorkingMemory()).getCurrentContext();
        if (context == null)
            return null;
		Rule rule = (Rule) context.getCause();
		if (rule == null)
			return null;
        return rule.getIdentifiers();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "invokeRule",
        synopsis = "Find all the matched tuples of a rule given partial input set.  And for each matched tuple, execute the rule action.",
        signature = "Object[] invokeRule(String uri, Object[] inputSet, boolean dirtyRead)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "The URI of the rule to invoke."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "inputSet", type = "Object[]", desc = "support one input in the array.)"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "dirtyRead", type = "boolean", desc = "If true, it won't synchronize the working memory during the read when calling from preprocessor")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "The result Set, each element in the Object[] is a result tuple which is another Object[]."),
        version = "2.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Find all the matched tuples of a rule given partial input set.  And for each tuple, execute the rule action.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Object[] invokeRule(String uri, Object[] inputSet, boolean dirtyRead) {
        RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        if (session == null)
            throw new RuntimeException("Current RuleSession is not set");
        return session.findMatches(uri, inputSet, true, dirtyRead);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "invokeRuleFunction",
        synopsis = "Invoke a rule function by name.",
        signature = "Object invokeRuleFunction(String uri, Object[] arguments)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "The URI of the rule function to invoke."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arguments", type = "Object[]", desc = "Array elements must be in the same order as the arguments are declared by the rule function.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The result if any. Will be null if function returns void."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Invoke a rule function by name.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static Object invokeRuleFunction(String uri, Object[] arguments) {
        RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        if (session == null)
            throw new RuntimeException("Current RuleSession is not set");
        RuleFunction rf = session.getRuleFunction(uri);
        return rf.invoke(arguments);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "shutdown",
        synopsis = "Shuts down the BusinessEvents engine.",
        signature = "void shutdown(boolean immediate)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "immediate", type = "boolean", desc = "if false waits for completion of RTC and for acknowledgment of events received.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Shutdown the engine.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void shutdown(boolean immediate) {
        RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        if (session != null) {
            session.getRuleServiceProvider().getLogger(EngineFunctions.class).log(Level.INFO, 
                    "***** Invoked Engine.shutdown(), shutting down... *****");
            if (immediate)
                session.stopAndShutdown();
            ShutdownThread s = new ShutdownThread(session.getRuleServiceProvider());
            s.start();
        }
        else{
            exitSystem(-1);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "executeRules",
        synopsis = "Execute Rules in the preprocessor context.",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.engine.executeRules", value=true),
        signature = "void executeRules()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Execute the rules in the preprocessor context. Whatever is present (mainly asserted or deleted, will be used for execution;",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public final static void executeRules() {
        PreprocessContext pc = PreprocessContext.getContext();
        if (pc != null) {
            RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                try {
                    if (pc.hasWork()) {
                        List asserted = pc.getAsserted();
                        LinkedHashSet deleted = pc.getDeleted();
                        List reloaded = pc.getReloaded();
                        List reevaluate = pc.getReevaluate();
                        PreprocessContext.setContext(null);
                        ((ReteWM) session.getWorkingMemory()).executeRules("Preprocessor: ", reloaded, asserted, deleted, reevaluate);
                    }
                } finally {
                    if (Thread.currentThread() instanceof BEManagedThread) {
                        ((BEManagedThread) Thread.currentThread()).executeEpilogue();
                    }
                    pc.reset();
                    PreprocessContext.setContext(pc);
                }
            }
        } 
        else {
            throw new RuntimeException("Has to execute in a preprocess context...");
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "clusterSubscriptionLoadOnly",
        synopsis = "Loads the Entity that comes from Cluster Subscription into the working memory only "
            + "and does not execute any rule for it."
            + "This can only be used in the preprocessor for the Entity which Subscribes Cluster.",
        signature = "void clusterSubscriptionLoadOnly()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description  = "Loads the Entity that comes from Cluster Subscription into the working memory only "
                + "and does not execute any rule for it."
                + "This can only be used in the preprocessor for the Entity which Subscribes Cluster.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public final static void clusterSubscriptionLoadOnly() {
        PreprocessContext pc = PreprocessContext.getContext();
        if (pc != null)
            pc.setClusterSubscriptionReloadOnly();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "executeRulesAsync",
        synopsis = "Execute Rules in the preprocessor context. Execution is done in parallel mode using the supplied work-manager",
        signature = "void executeRulesAsync(String workManagerID)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "workManagerID", type = "String", desc = "Name of an existing work-manager to be used for the execution")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Execute the rules in the preprocessor context. Whatever is present (mainly asserted or deleted, will be used for execution;",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public final static void executeRulesAsync(String workManagerID) {
        PreprocessContext pc = PreprocessContext.getContext();
        if (pc != null) {
            RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                try {
                    WorkManager workManager = ClusterFunctions.getWorkManager(workManagerID);
                    if (workManager == null) {
                        throw new RuntimeException("WorkManager with ID = " + workManagerID + " not registered");
                    }
                    if (pc.hasWork()) {
                        List asserted = pc.getAsserted();
                        LinkedHashSet deleted = pc.getDeleted();
                        List reloaded = pc.getReloaded();
                        List reevaluate = pc.getReevaluate();
                        workManager.submitJob(new ExecuteRulesTask(session, asserted, deleted, reloaded, reevaluate));
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                } finally {
                    PreprocessContext.setContext(null);
                    PreprocessContext.newContext(session);
                }
            }
        } 
        else {
            throw new RuntimeException("Has to execute in a preprocess context...");
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "assertEvent_Async",
        synopsis = "Asserts a simple event using WorkManager",
        signature = "void assertEvent_Async()",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "workManagerID", type = "String", desc = "Name of an existing work-manager to be used for the execution"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "SimpleEvent", desc = "Event to be asserted"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleFunction", type = "String", desc = "Fully qualified name of the preprocessor function if any, else null.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Execute the rules in the preprocessor context. Whatever is present (mainly asserted or deleted, will be used for execution;",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public final static void assertEvent_Async(String workManagerID, SimpleEvent event, String ruleFunction) {
        RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        if (session != null) {
            try {
                WorkManager workManager = ClusterFunctions.getWorkManager(workManagerID);
                if (workManager == null) {
                    throw new RuntimeException("WorkManager with ID = " + workManagerID + " not registered");
                }
                workManager.submitJob(new AssertEventAsyncTask(session, ruleFunction, event));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "threadName",
            synopsis = "Gets the name of the current thread.",
            signature = "String threadName()",
            params = {
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Thread name."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Gets the name of the current thread.",
            cautions = "none",
            fndomain = {ACTION, CONDITION, QUERY, BUI},
            example = ""
    )
    public static String threadName() {
        return Thread.currentThread().getName();
    }

    @com.tibco.be.model.functions.BEFunction(
    		name = "loadResourceAsByte",
    		synopsis = "When a relative path to a file is provided, then the byte representation of the file contents are returned.",
    		signature = "Object loadResourceAsByte(String uri)",
    		params = {
    			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "Relative path of a file if any uri is provided, else null.")	
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Returns the contents of the file the uri is pointing to in a byte array format."),
    		version = "5.2.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "When a relative path to a file is provided, then the byte representation of the file contents are returned.",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = ""
    )
    public static Object loadResourceAsByte(String uri){
        //BE-21297 related changes made by Archit
    	// Make sure that the uri passed is in an expected format
    	// Correct formats would be /L1/L2/L3...
    	
    	if (uri.startsWith("mem:///")) {
    		uri = uri.substring("mem:///".length());
    		final int index = uri.indexOf('/');
    		if(index != -1){
    			uri = uri.substring(index);
    		}
    	}
    	if (!uri.startsWith("/")) {
    		uri = "/" + uri;
    	}
    	RuleSession rs = RuleSessionManager.getCurrentRuleSession();
    	SharedArchiveResourceProviderImpl test = SharedArchiveResourceProviderImpl.class.cast(rs.getRuleServiceProvider().getProject().getSharedArchiveResourceProvider());
    	return test.getResourceAsByteArray(uri);
    }
    
    @com.tibco.be.model.functions.BEFunction(
    		name = "loadResourceAsString",
    		synopsis = "When a relative path to a file is provided, then the contents of the file are returned in String format. The type of encoding to be used can be determined by the user. In case no encoding is provided, then UTF-8 is used by default.",
    		signature = "String loadResourceAsString(String uri, String encoding)",
    		params = {
    			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "Relative path of a file if any uri is provided, else null."),
    			@com.tibco.be.model.functions.FunctionParamDescriptor(name = "encoding", type = "String", desc = "Type of encoding to be used, if encoding mentioned is empty string or null, then UTF-8 would be used as default encoding.")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Returns the contents of the file in String format after applying the encoding (UTF-8 by default)."),
    		version = "5.2.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "When a relative path to a file is provided, then the contents of the file are returned in String format. The type of encoding to be used can be determined by the user. In case no encoding is provided, then UTF-8 is used by default.",
    		cautions = "none",
    		fndomain = {ACTION, CONDITION, QUERY, BUI},
    		example = ""
    )
    public static String loadResourceAsString(String uri, String encoding) {
    	if (uri == null || uri == "") {
    		throw new IllegalStateException("Uri can not be null or an empty String");
    	}
    	String value = null;
    	if (encoding == null || encoding == "") {
    		encoding = "UTF-8";
    	}
    	RuleSession rs = RuleSessionManager.getCurrentRuleSession();
    	try {
    		byte[] arr = (byte[])loadResourceAsByte(uri);
			value = new String(arr, encoding);
		} catch (UnsupportedEncodingException e) {
		}
    	return value;
    }

    private static void exitSystem(int status) {
        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        ShutdownWatcher shutdownWatcher = registry.getShutdownWatcher();
        if (shutdownWatcher == null) {
            System.exit(status);
        } 
        else {
            shutdownWatcher.exitSystem(status);
        }
    }

    static class ShutdownThread extends Thread {
        RuleServiceProviderImpl rsp;
        ShutdownThread(RuleServiceProvider rsp_) {
            rsp = (RuleServiceProviderImpl) rsp_;
            setDaemon(true);
        }

        public void run() {
            try {
                RuleServiceProvider container = rsp.getContainerRsp();
                if (container != null)
                    container.shutdown();
                else
                    rsp.shutdown();
            } 
            finally {
                EngineFunctions.exitSystem(0);
            }
        }
    }

    static class ExecuteRulesTask implements Runnable {
        RuleSessionImpl ruleSession;
        List asserted;
        List reevaluate;
        LinkedHashSet deleted;
        List reloaded;

        ExecuteRulesTask(RuleSessionImpl ruleSession, List asserted, LinkedHashSet deleted, List reloaded, List reevaluate) {
            this.asserted = asserted;
            this.deleted = deleted;
            this.reloaded = reloaded;
            this.reevaluate = reevaluate;
            this.ruleSession = ruleSession;
        }

        public void run() {
            try {
                ((ReteWM) ruleSession.getWorkingMemory()).executeRules("ExecuteRulesTask: ", reloaded, asserted, deleted, reevaluate);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    static class AssertEventAsyncTask implements Runnable {
        RuleSessionImpl ruleSession;
        RuleFunction ruleFunction;
        SimpleEvent event;

        AssertEventAsyncTask(RuleSessionImpl ruleSession, String ruleFunction, SimpleEvent event) {
            this.ruleSession = ruleSession;
            if (ruleFunction != null)
                this.ruleFunction = ruleSession.getRuleFunction(ruleFunction);
            this.event = event;
        }

        public void run() {
            try {
                if (ruleFunction != null) {
                    ((RuleSessionImpl) ruleSession).preprocessPassthru(ruleFunction, event);
                } else {
                    ((RuleSessionImpl) ruleSession).assertObject(event, true);
                }
            } catch (Exception ex) {
                // Do not throw the exception or the thread will die.
                ex.printStackTrace();
            }
        }
    }
}
