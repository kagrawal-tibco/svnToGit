package com.tibco.be.bw.factory;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.be.functions.bw.BWSupport;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.pe.MessageCode;
import com.tibco.pe.PEMain;
import com.tibco.pe.core.Engine;
import com.tibco.pe.core.EngineSupportException;
import com.tibco.pe.core.JobPool;
import com.tibco.pe.core.JobPoolCreator;
import com.tibco.pe.plugin.TraceMessage;
import com.tibco.share.util.Trace;

/*
 * User: ssubrama
 * Date: Jul 13, 2006
 * Time: 12:05:37 PM
 */


/**
 * Implementation of logger using MSink...
 */
public class BWLogger
        implements Logger {


    static String INFO = "infoRole";
    static String WARN = "warnRole";
    static String DEBUG = "debugRole";
    static String ERROR = "errorRole";
    static String USER = "User";

    private static final Map<Level, String> LEVEL_TO_ROLE_NAME = new HashMap<Level, String>();
    private static final Map<String, Level> ROLE_NAME_TO_LEVEL = new HashMap<String, Level>();


    static {
        LEVEL_TO_ROLE_NAME.put(Level.INFO, INFO);
        LEVEL_TO_ROLE_NAME.put(Level.WARN, WARN);
        LEVEL_TO_ROLE_NAME.put(Level.DEBUG, DEBUG);
        LEVEL_TO_ROLE_NAME.put(Level.ERROR, ERROR);
        LEVEL_TO_ROLE_NAME.put(Level.FATAL, ERROR);
        ROLE_NAME_TO_LEVEL.put(INFO, Level.INFO);
        ROLE_NAME_TO_LEVEL.put(WARN, Level.WARN);
        ROLE_NAME_TO_LEVEL.put(DEBUG, Level.DEBUG);
        ROLE_NAME_TO_LEVEL.put(ERROR, Level.ERROR);
    }


    private Map<String, Trace> roleNameToTrace = new HashMap<String, Trace>();
    private String name;


    public BWLogger(String name) {
        this.name = name;
        createUserRole();
        Iterator r = Trace.getAllTraceObjects();
        while (r.hasNext()) {
            Trace trc = (Trace) r.next();
            if (trc.isOn() && !roleNameToTrace.containsKey(trc.getRoleName())) {
                roleNameToTrace.put(trc.getRoleName(), trc);
            }
        }
    }


    public Level getLevel() {
        Level currentLevel = Level.OFF;
        for (Trace trc : this.roleNameToTrace.values()) {
            if (trc.isOn()) {
                final Level level = ROLE_NAME_TO_LEVEL.get(trc.getRoleName());
                if ((currentLevel == Level.OFF) || (currentLevel.compareTo(level) < 0)) {
                    currentLevel = level;
                }
            }
        }
        return currentLevel;
    }

    
    public String getName() {
        return this.name;
    }


    public boolean isEnabledFor(Level level) {
        final Trace t = this.roleNameToTrace.get(LEVEL_TO_ROLE_NAME.get(level));
        return (t != null) && t.isOn();

    }


    public void log(Level level, String format, Object... args) {
        final Trace trc = this.roleNameToTrace.get(LEVEL_TO_ROLE_NAME.get(level));
        if ((trc != null) && trc.isOn()) {
            trc.trace(this.name, DEBUG, Trace.PLUGIN_CATEGORY, String.format(format, args));
        }
    }


    public void log(Level level, String message) {
        final Trace trc = this.roleNameToTrace.get(LEVEL_TO_ROLE_NAME.get(level));
        if ((trc != null) && trc.isOn()) {
            trc.trace(this.name, DEBUG, Trace.PLUGIN_CATEGORY, message);
        }
    }


    public void log(Level level, String format, Throwable thrown, Object... args) {
        this.log(level, thrown, format, args);        
    }


    public void log(Level level, Throwable thrown, String format, Object... args) {
        final Trace trc = this.roleNameToTrace.get(LEVEL_TO_ROLE_NAME.get(level));
        if ((trc != null) && trc.isOn()) {
            final String message = String.format(format, args);
            if (thrown == null) {
                trc.trace(this.name, DEBUG, Trace.PLUGIN_CATEGORY, message);
            } else {
                trc.trace(this.name, DEBUG, Trace.PLUGIN_CATEGORY, makeMessage(thrown, message));
            }
        }
    }


    public void log(Level level, Throwable thrown, String message) {
        final Trace trc = this.roleNameToTrace.get(LEVEL_TO_ROLE_NAME.get(level));
        if ((trc != null) && trc.isOn()) {
            if (thrown == null) {
                trc.trace(this.name, DEBUG, Trace.PLUGIN_CATEGORY, message);
            } else {
                trc.trace(this.name, DEBUG, Trace.PLUGIN_CATEGORY, makeMessage(thrown, message));
            }
        }
    }


    public void setLevel(Level level) {
        for (Trace trc : this.roleNameToTrace.values()) {
            final Level currentLevel = ROLE_NAME_TO_LEVEL.get(trc.getRoleName());
            if ((null != currentLevel) && (currentLevel.compareTo(level) >= 0)) {
                trc.on();
            } else {
                trc.off();
            }
        }
    }


    private String makeMessage(Throwable throwable, String o) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        throwable.printStackTrace(pw);
        pw.flush();
        pw.close();

        StringBuffer sb = new StringBuffer();
        sb.append(o);
        sb.append(sw);
        return sb.toString();
    }



    public void close() {
        this.roleNameToTrace.clear();
    }


    /**
     * Hijacked from BW code & modified (WriteToLogActivity)
     */
    private void createUserRole() {
        if (!PEMain.isMain && !Engine.testMode) {
            return;
        }
        try {
            String role = "User";
            String key = "Trace.Role." + role;
            String wildKey = "Trace.Role.*";
            Trace traceRole = Trace.lookupTraceObject(role);
            if (traceRole == null) {
                String propValue;
                if (!Engine.props.containsKey(key + ".*")) {
                    Engine.props.setProperty(key + ".*", "true");
                }
                if (!Engine.props.containsKey(key + ".Term")) {
                    if (!Engine.props.containsKey(wildKey + ".Term")) {
                        propValue = "true";
                    } else {
                        propValue = Engine.props.getProperty(wildKey + ".Term");
                    }
                    Engine.props.setProperty(key + ".Term", propValue);
                }
                if (!Engine.props.containsKey(key + ".Log")) {
                    if (!Engine.props.containsKey(wildKey + ".Log")) {
                        propValue = "true";
                    } else {
                        propValue = Engine.props.getProperty(wildKey + ".Log");
                    }
                    Engine.props.setProperty(key + ".Log", propValue);
                }

                String engineName = Engine.props.getProperty("name");

                JobPool pool = (JobPool) BWSupport.getField(Engine.class,
                        JobPool.class, null);
                if (pool != null) {
                    try {
                        traceRole = JobPoolCreator.createAppTrace(role,
                                Engine.props.getBranch("Trace.Role." + role),
                                engineName);
                        pool.userTracers.put(role, traceRole);
                        // enable or disable the new traceRole according to the
                        // current Trace.Role properties.
                        pool.setUserDefinedTracers();
                    } catch (Exception e) {
                        throw new EngineSupportException(TraceMessage
                                .build(MessageCode.TRACE_SETUP), null, e);
                    }
                }
            }
        }

        catch (Exception e) {
            System.out.println("Can't set user role");
        }
    }

}
