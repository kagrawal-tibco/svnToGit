package com.tibco.be.util;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 27, 2004
 * Time: 9:02:37 AM
 * To change this template use Options | File Templates.
 */


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.cep.runtime.service.logging.impl.LoggerImpl;
import com.tibco.cep.runtime.util.SystemProperty;


public class BEProperties extends Properties {
    private ArrayList listeners = new ArrayList();

    static BEProperties thisInstance;

    public BEProperties() {
        super();
    }

    public BEProperties(Properties props) {
        super();
        mergeProperties(props);
    }

    protected static BEProperties loadDefaultProperties() {
        //SS:TODO - Put this properties in a resource file. So it can be used as a template for administration
        String[] values = {

                "be.engine.hotDeploy.enabled", "false",
                //Engine params
//            "be.engine.name", "engine",    // Set only through command line parameter, not here.
//            "be.engine.subject.prefix", "be",
//            "be.engine.status.period", "30",
//            "be.engine.wm.resolver", "com.tibco.be.engine.core.conflict.SimpleConflictResolver",
//            "be.engine.wm.resolver", "com.tibco.be.engine.core.conflict.PriorityConflictResolver",
//            "be.engine.model.id.min", "1",

//            "be.engine.kpi.channel.rate.interval", "-1",

//            "be.engine.channel.payload.saveMsg", "false",

                // Property for maximum number of milliseconds that the WMTimer thread can run tasks in a single go
//            "be.engine.timer.maxWMLockTimeMillis", "150",  //todo - put this back in

                //Engine FT parameters
                /*
                "be.ft.enable", "false",
                "be.ft.service", "7500",
                "be.ft.network", "",
                "be.ft.daemon", "tcp:7500",
                "be.ft.groupprefix","be.ft.",
                "be.ft.heartbeatinterval", "10000",
                "be.ft.prepinterval", "40000",
                "be.ft.activationinterval", "45000",
                */

                //Language Properties
//           "be.locale.language", "",
//           "be.locale.country", "",
//           "be.locale.variant", "",

//           "be.trace.name", "be-engine",    //use the engine name for the trace. $name is the argument for name
                SystemProperty.TRACE_ENABLED.getPropertyName(), "true",
                //enable trace object itself. At some time, we might have multiple traces

                SystemProperty.TRACE_TERM_ENABLED.getPropertyName(), "true",
                SystemProperty.TRACE_TERM_SYSOUT_REDIRECT.getPropertyName(), "true",
                SystemProperty.TRACE_TERM_SYSERR_REDIRECT.getPropertyName(), "true",

                SystemProperty.TRACE_FILE_ENABLED.getPropertyName(), "true",
                //the file name for the log is the the value of be.trace.name + ".log"
//           "be.trace.log.dir", "logs",    //always relative to the executable
                SystemProperty.TRACE_FILE_MAX_SIZE.getPropertyName(), "10000000",    //size of the log file
                SystemProperty.TRACE_FILE_MAX_NUM.getPropertyName(), "10",    //nos of logfile, before recycling
//           "be.trace.log.append", "true",    //append  -- never used!!!

                SystemProperty.TRACE_ROLES.getPropertyName(), "*:info",

                /*
                // all generated java files will have this as their root package
                // and so will be written into directories under this one
                "be.codegen.rootPackage", "be.gen",
                //Rule files (output from the rule editor) have this extension
                "be.codegen.ruleFileExtension", ".rul",
                "be.codegen.javaFileExtension", ".java",
                "be.codegen.sourceTextExtension", "sourceText",


                 // Temporary directory on the filesystem for packaging jar, etc
                "be.packaging.tempdir", System.getProperty("java.io.tmpdir"),
                // Classpath for functions, etc
                "be.packaging.extraclasspath", "",
                */

                // OM Properties
                "be.engine.om.datastore.factory",
                "com.tibco.cep.runtime.service.om.impl.datastore.berkeleydb.BerkeleyDBFactory",
                // Class that implements the DataStoreFactory interface
                "be.engine.om.berkeleydb.dbenv", "./db",
                // Use the engine name for the database directory name under working dir.
                "be.engine.om.berkeleydb.conceptable", "BEConcepts",
                // Table to store the Concept Instances
                //"be.engine.om.berkeleydb.concepturimap", "BEConceptURIMap", //Secondary table (index) for extId to Concept Instance.
                "be.engine.om.berkeleydb.propertiestable", "BEProperties",
                // Table to store the Properties of Concepts
                "be.engine.om.berkeleydb.eventslog", "BEEventLog", // Event log table
                "be.engine.om.berkeleydb.migratedActiveStates", "BEMigratedActiveStates",
                // Created during 1.4->2.0 state machine migration
                //"be.engine.om.berkeleydb.eventurimap", "BEEventURIMap", // Secondary (index) for extId to Event.
                //"be.engine.om.berkeleydb.payloadstable", "BEEventPayloads", // Event Payloads table.
                //"be.engine.om.berkeleydb.namedinstancemap", "BENamedInstanceIds", // Scorecard (Named Instances) class names vs. internal Id map.
                "be.engine.om.berkeleydb.propertyindextable", "BEPropertiesIndexMap",
                // Stores Property Hierarchy Indices
                "be.engine.om.berkeleydb.beversiontable", "BEVersionTable",
                // Table that stores Database Version
                "be.engine.om.berkeleydb.internalcachepercent", "20",
                // Berkeleydb internal cache percentage (% of JVM HEAP).
                "be.engine.om.berkeleydb.truncate", "false",
                // Truncate all data at startup? TODO: Review this property.
                "be.engine.om.berkeleydb.nocreatedbenv", "false",
                // Do not try to create an Env directory if one does not already exist.
                "be.engine.om.checkpointer.interval", "-1",
                // Checkpoint interval : -1 means get from the BE$$OMDeployment generated class
                //"be.engine.om.propertycache.maxsize", "-1", // No. of properties to keep in the OM cache : -1 means get from the BE$$OMDeployment generated class
                "be.engine.om.eventcache.defaultmaxsize", "-1",
                // No. of properties to keep in the OM cache : -1 means get it from the Property Cache Size specified in the omConfig in engine-config.xml
                //"be.engine.om.synccheckpoint", "false", // Checkpoints synchronized with Working Memory access or not? TODO: Review this property.
                "be.engine.om.removeRefOnDeleteNonRepeatingTimeEvent", "true",
                //fix for keeping state timeout time events in memory after state has been left

                "com.tibco.cep.runtime.session.ruleserviceprovider",
                "com.tibco.cep.runtime.session.impl.FTAsyncRuleServiceProviderImpl",
                // Fault tolerance RuseServiceProvider
                //"com.tibco.cep.runtime.service.om.NodeManager","com.tibco.cep.runtime.service.om.tangosol.FTNodeManagerImpl", //Fault tolerant node manager implementation
                //"com.tibco.cep.runtime.service.om.NodeManager","com.tibco.cep.runtime.service.om.tangosol.TangosolFTNodeManager", // Fault tolerant node manager implementation

                //Entity Factory
                //"com.tibco.cep.entity.factory", BEClassLoader.class.getName(),
                "com.tibco.cep.entity.factory",
                "com.tibco.cep.runtime.service.loader.BEClassLoader",
                //"com.tibco.cep.entity.classloader.parent", null,
                //trace properties

                SystemProperty.TRACE_LOGGER_CLASS_NAME.getPropertyName(), LoggerImpl.class.getName(),
                "be.engine.statemachine.deleteStateTimeoutOnStateExit", "true",
        };

        BEProperties props = new BEProperties();
        props.addProps(values);
        return props;
    }

    protected void addProps(String[] values) {
        String k, v;

        for (int i = values.length - 1; 0 < i; i -= 2) {
            v = values[i];
            k = values[i - 1];
            if (getProperty(k) == null || v.length() > 0) {
                put(k, v);
            }
        }
    }


    public void mergeProperties(Properties props) {
        String k, v;
        for (Enumeration keys = props.propertyNames(); keys.hasMoreElements();) {
            k = (String) keys.nextElement();
            v = props.getProperty(k);
            if ((null == this.getProperty(k))
                    || ((null != v) && v.length() > 0)) {
                if (v != null) {
                    put(k, v);
                }
            }
        }
    }


    /**
     * Adds the entries from a Properties that are not already set to a non-null value in this.
     *
     * @param props Properties
     */
    public void addNewProperties(Properties props) {
        for (final Map.Entry<Object, Object> entry : props.entrySet()) {
            final Object key = entry.getKey();
            if (null == this.get(key)) {
                this.put(key, entry.getValue());
            }
        }
    }

    /**
     * @param key
     * @return Trimmed string of length > 0. <code>null</code> otherwise.
     */
    public String getString(String key) {
        String s = getProperty(key);

        if (s != null) {
            s = s.trim();

            if (s.length() == 0) {
                s = null;
            }
        }

        return s;
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     * @see #getString(String)
     */
    public String getString(String key, String defaultValue) {
        String s = getString(key);

        if (s == null) {
            return defaultValue;
        }

        return s;
    }


    /**
     * @param key
     * @param variableValues
     * @param defaultValue
     * @return
     * @see #getString(String)
     */
    public String getString(
            String key,
            LinkedHashMap<String, String> variableValues,
            String defaultValue) {

        if (null == variableValues) {
            return this.getString(key, defaultValue);

        } else {
            // Tries complete resolution first.
            String resolvedKey = key;
            for (String varName : variableValues.keySet()) {
                final String varValue = variableValues.get(varName);
                if (null != varValue) {
                    resolvedKey = resolvedKey.replaceAll(Pattern.quote("${" + varName + "}"), varValue);
                }
            }
            String value = this.getProperty(resolvedKey);
            if (null != value) {
                return value;
            }

            // Tries again with partial resolutions.
            final List<String> keys = new ArrayList<String>(variableValues.keySet());
            Collections.reverse(keys); // Re-sorts by reverse priority.
            for (String varName : keys) {
                final LinkedHashMap<String, String> smallerMap = new LinkedHashMap<String, String>(variableValues);
                smallerMap.remove(varName); // The smaller map of variables has one less variable.
                value = this.getString(key, smallerMap, null);
                if (null != value) {
                    return value;
                }
            }

            // Nothing was found.
            return defaultValue;
        }
    }


    public Properties getDefaults() {
        return super.defaults;
    }

    /**
     * Properties of the form "prefix.suffix" will be collected into a separate object with the
     * property key being the only the suffix part.
     *
     * @return returns an EngineProperties containing all properties that start with "prefix.".  The
     *         prefix and . will be removed from the key.
     */
    public BEProperties getBranch(String prefix) {
        final Properties propBranch = new Properties();
        final int prefixLength = prefix.length();
        for (Enumeration propNames = propertyNames(); propNames.hasMoreElements();) {
            final String name = (String) propNames.nextElement();
            if (prefixLength < name.length() && name.startsWith(prefix) &&
                    (name.charAt(prefixLength) == '.')) {
                final String newName = name.substring(prefixLength + 1);
                final Object value = this.get(name);
                if (value instanceof String) {
                    propBranch.setProperty(newName, (String) value);
                }
            }//if
        }//for
        //System.out.println("** subset for "+prefix);
        //subset.list(System.out);
        final BEProperties branch = new BEProperties(propBranch);
        return branch;
    }//getBranch


    /**
     * Properties starting with prefix will be returned without modification.
     *
     * @return returns an EngineProperties containing all properties that start with prefix
     */
    public BEProperties getStartsWith(String prefix) {
        Properties subset = new Properties();
        String k;
        for (Enumeration keys = propertyNames(); keys.hasMoreElements();) {
            k = (String) keys.nextElement();
            if (k.startsWith(prefix)) {
                subset.setProperty(k, getProperty(k));
            }
        }
        return new BEProperties(subset);
    }

    /**
     * Gets a boolean property.
     *
     * @param key          the name of the property
     * @param defaultValue the value to return if the property wasn't found
     * @return The value associated with the property.
     */
    public final boolean getBoolean(String key, boolean defaultValue) {
        String str = getString(key, Boolean.toString(defaultValue));

        return Boolean.parseBoolean(str);
    }

    
    /**
     * Gets a boolean property.
     *
     * @param key            String name of the property
     * @param variableValues LinkedHashMap of variable names and values, used to resolve the property name
     * @param defaultValue   boolean value to return if the property wasn't found
     * @return boolean value associated with the property.
     */
    public final boolean getBoolean(
            String key,
            LinkedHashMap<String, String> variableValues,
            boolean defaultValue) {
        final String str = this.getString(key, variableValues, Boolean.toString(defaultValue));
        return Boolean.parseBoolean(str);
    }


    public final String getFilename(String key) {
        return getFilename(key, null);
    }

    public final String getFilename(String key, String defaultValue) {
        String str = getString(key);

        if (null == str) {
            str = defaultValue;
            if (null == str) {
                return null;
            }
        }
        if ('/' == File.separatorChar) {
            str = str.replace('\\', File.separatorChar);
        }
        else {
            str = str.replace('/', File.separatorChar);
        }
        return str;
    }

    public final File getFile(String key, File defaultValue) {
        String str = getFilename(key, null);

        if (null == str) {
            return defaultValue;
        }
        return new File(str);
    }

    /**
     * Gets a long property.
     *
     * @param key          the key associated with the value
     * @param defaultValue the value to return if the parameter wasn't found
     * @return The value associated with the key.
     */
    public final long getLong(String key, long defaultValue) {
        String str = getString(key, Long.toString(defaultValue));

        return Long.parseLong(str);
    }

    /**
     * Gets a int property.
     *
     * @param key          the key associated with the value
     * @param defaultValue the value to return if the parameter wasn't found
     * @return The value associated with the key.
     */
    public final int getInt(String key, int defaultValue) {
        String str = getString(key, Integer.toString(defaultValue));

        return Integer.parseInt(str);
    }

    /**
     * Gets a int property.
     *
     * @param key          String key associated with the value
     * @param variableValues LinkedHashMap of variable names and values, used to resolve the property name
     * @param defaultValue int value to return if the parameter wasn't found
     * @return int value associated with the key.
     */
    public final int getInt(
            String key,
            LinkedHashMap<String, String> variableValues,
            int defaultValue) {
        final String str = this.getString(key, variableValues, null);
        if (null == str) {
            return defaultValue;
        }
        return Integer.parseInt(str);
    }


    public double getDouble(String key, double defaultValue) {
        String str = getString(key, Double.toString(defaultValue));

        return Double.parseDouble(str);
    }

    /**
     * Gets an array of Strings identified by a key.
     *
     * @param key the key associated with the value
     * @return The value associated with the obj and key.
     */
    public String[] getStringArray(String key) {
        return parseToStrArray(getString(key), ",");
    }

    public String[] getStringArray(String key, String sep) {
        return parseToStrArray(getString(key), sep);
    }

    public static final String[] parseToStrArray(String str) {
        return parseToStrArray(str, ",");
    }

    public static final String[] parseToStrArray(String str, String sep) {
        if (null == str ||
                0 == str.length()) {
            return null;
        }
        StringTokenizer toker = new StringTokenizer(str, sep);
        int cnt = toker.countTokens();

        if (0 == cnt) {
            return null;
        }
        String[] sa = new String[cnt];
        int i;

        for (i = 0; i < cnt; i++) {
            sa[i] = toker.nextToken();
        }
        return sa;
    }

//    public static final String formStringFromArray(String[] array) {
//        if (null == array) {
//            return null;
//        }
//        StringBuffer buf = new StringBuffer();
//        int i, cnt = array.length;
//
//        for (i = 0; i < cnt; i++) {
//            if (0 < i) {
//                buf.append(',');
//            }
//            buf.append(array[i]);
//        }
//        return new String(buf);
//    }

    public static BEProperties getInstance() {
        if (thisInstance == null) {
            thisInstance = loadDefaultProperties();
        }
        return thisInstance;
    }

    public static BEProperties loadDefault() {
        return loadDefaultProperties();
    }


    public void substituteTibcoEnvironmentVariables() {
        //find the branch which contains tibco.env
        BEProperties tibcoenv = this.getBranch("tibco.env");
        Iterator envIter = tibcoenv.entrySet().iterator();
        while (envIter.hasNext()) {
            Map.Entry entry = (Map.Entry) envIter.next();
            //search and substitute for all element which has this string by this value
            substituteAll("%" + entry.getKey().toString() + "%", entry.getValue().toString());
        }
    }

    public void substituteAll(String findStr, String replaceStr) {

        Iterator envIter = this.entrySet().iterator();
        while (envIter.hasNext()) {
            Map.Entry entry = (Map.Entry) envIter.next();

            //search and substitute for all element which has this string by this value
            Object value = entry.getValue();
            if (value instanceof String) {
                entry.setValue(((String) value).replaceAll(findStr, Matcher.quoteReplacement(replaceStr)));
            }

        }
    }

    public void updateSystemProperties() {
        for (Map.Entry entry : this.entrySet()) {
            String key = (String) entry.getKey();
            final Object value = entry.getValue();
            if ((value instanceof String)
				//  && (System.getProperty(key) == null)
                    && !key.startsWith("com.sun.management.jmxremote.")) {
                System.setProperty(key, (String) value);
            }
        }
    }

    public String summarize() {
    	StringBuffer sb = new StringBuffer();
    	Enumeration keys = this.keys();
    	while (keys.hasMoreElements()) {
    		String key = (String)keys.nextElement();
    		if (key.startsWith("be.")) {
    			sb.append(key + "=" + this.getProperty(key) + "\n");
    		}
    	}
    	return sb.toString();
    }

    public interface PropertiesChangeListener {

        public void notify(PropertiesChangeEvent e);

        public interface ChangeEvent {
            Properties getProperties();

            String getKey();

            String getOldValue();

            String getNewValue();
        }
    }

    public class PropertiesChangeEvent implements PropertiesChangeListener.ChangeEvent {
        private String key;

        private String oldValue;

        private String newValue;

        private Properties props;

        public PropertiesChangeEvent(Properties _props, String _key, String _oldValue,
                                     String _newValue) {
            props = _props;
            key = _key;
            oldValue = _oldValue;
            newValue = _newValue;
        }

        public Properties getProperties() {
            return props;
        }

        public String getKey() {
            return key;
        }

        public String getOldValue() {
            return oldValue;
        }

        public String getNewValue() {
            return newValue;
        }
    }


    public synchronized Object setProperty(String key, String value) {
        String oldValue = getProperty(key);
        Object obj = null;
        obj = super.setProperty(key, value);
        for (int i = 0; i < listeners.size(); i++) {
            PropertiesChangeListener l = (PropertiesChangeListener) listeners.get(i);
            l.notify(new PropertiesChangeEvent(this, key, oldValue, value));
        }
        return obj;
    }

    public void addPropertiesChangeListener(PropertiesChangeListener l) {
        this.listeners.add(l);
    }

    public void removePropertiesChangeListener(PropertiesChangeListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }
    
    public String toString(boolean beOnly) {
        StringBuffer sb = new StringBuffer();
        Enumeration keys = this.keys();
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            if (beOnly == false || key.startsWith("be.")) {
                sb.append(key + "=" + this.getProperty(key) + "\n");
            }
        }
        return sb.toString();
    }
}
