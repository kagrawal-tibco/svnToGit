package com.tibco.cep.functions.channel.as;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.tibco.as.space.ASCommon;
import com.tibco.as.space.FileLogOptions;
import com.tibco.as.space.LogLevel;
import com.tibco.as.space.MemberDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.RuntimeASException;
import com.tibco.as.space.FieldDef.FieldType;
import com.tibco.be.util.FileUtils;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASConstants;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * @author Pranab Dhar
 *
 */
public class ASHelper {

	protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ASHelper.class);

    protected static final Map<String,MetaspaceInfo> cacheMap;
    static {
        synchronized (ASHelper.class) {
            cacheMap = Collections.synchronizedMap(new HashMap<String,MetaspaceInfo>());
            //Should be done in the AScode.
            if (Boolean.parseBoolean(System.getProperty("as.loadLibrary.explicit", "false"))) {  //DO NOT DO THIS ON LINUX.
                System.loadLibrary("as-core");
                System.loadLibrary("as-common");
                System.loadLibrary("as-tibpgm");
            }
        }
    }

    protected static Map<String, FieldType> fieldTypeMap;
    static {
    	fieldTypeMap = Collections.synchronizedMap(new HashMap<String, FieldType>());
        fieldTypeMap.put("boolean", FieldType.BOOLEAN);
        fieldTypeMap.put("short", FieldType.SHORT);
        fieldTypeMap.put("int", FieldType.INTEGER);
        fieldTypeMap.put("integer", FieldType.INTEGER);
        fieldTypeMap.put("long", FieldType.LONG);
        fieldTypeMap.put("float", FieldType.FLOAT);
        fieldTypeMap.put("double", FieldType.DOUBLE);

        fieldTypeMap.put("char", FieldType.CHAR);
        fieldTypeMap.put("character", FieldType.CHAR);
        fieldTypeMap.put("string", FieldType.STRING);

        fieldTypeMap.put("date", FieldType.DATETIME);

        fieldTypeMap.put("blob", FieldType.BLOB);
        fieldTypeMap.put("byte", FieldType.BLOB);
        fieldTypeMap.put("object", FieldType.BLOB);
    }

    public static String CACHE_API_LOCK="CACHE_API_LOCK";
    public static boolean serializationEnabled = false;
    public static Class serializationClass;
    public static boolean classNameIsCacheName = false;

    /**
     * @author Pranab Dhar
     *
     */
    public static class MetaspaceInfo {
        private String name;
        private MemberDef memberDef;
        private Metaspace metaSpace;
        
        public MetaspaceInfo(String metaSpaceName, MemberDef memberDef) {
            super();
            this.name = metaSpaceName;
            this.memberDef = memberDef;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public MemberDef getMemberDef() {
            return memberDef;
        }
        
        public void setMemberDef(MemberDef memberDef) {
            this.memberDef = memberDef;
        }
        
        public Metaspace getMetaSpace() {
            return metaSpace;
        }
        
        public void setMetaSpace(Metaspace metaSpace) {
            this.metaSpace = metaSpace;
        }
        
        void connect() throws Exception {
            Metaspace metaspace = ASCommon.getMetaspace(getName());
            if (metaspace == null) {
            	metaspace = Metaspace.connect(getName(), getMemberDef());
				// Set the AS log level and directory
				//the default file name is : as-pid.log
				//the default log level is INFO
				//the default log directory is current directory
            	Properties properties = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getProperties();
				String asLogFileName = properties.getProperty(ASConstants.PROP_AS_LOG_FILE_NAME, "");
				String asLogLevel = properties.getProperty(ASConstants.PROP_AS_LOG_LEVEL, LogLevel.INFO.name());
				String asLogDir = properties.getProperty(ASConstants.PROP_AS_LOG_DIR, "");
				
				// Process the directory name
				//if null, current directory is used
				if (asLogDir == null || asLogDir.equals("")) { 
					asLogDir = System.getProperty(SystemProperty.TRACE_FILE_DIR.getPropertyName(), "logs");
				}
				// Process the file name
				if (asLogFileName == null || asLogFileName.isEmpty()) {
					asLogFileName = System.getProperty(SystemProperty.TRACE_FILE_NAME.getPropertyName(),"as-logs"); 
				}
				
				RuleServiceProvider rsp = null;
				if (RuleSessionManager.getCurrentRuleSession() != null) {
					rsp = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider() ;
				}
				if (rsp != null) {
					asLogFileName = rsp.getGlobalVariables().substituteVariables(asLogFileName).toString();
					asLogLevel = rsp.getGlobalVariables().substituteVariables(asLogLevel).toString();
					asLogDir = rsp.getGlobalVariables().substituteVariables(asLogDir).toString();
				}
				
				FileUtils.createWritableDirectory(asLogDir);

				LogLevel asEnumLogLevel;
				
				try { 
					asEnumLogLevel = LogLevel.valueOf(asLogLevel.toUpperCase()); 
				}
				catch (IllegalArgumentException e) { 
					// In case of invalid arguments, default to INFO level. 
					LOGGER.log(Level.WARN, "Invalid AS log level, defaulting to INFO");
					asEnumLogLevel = LogLevel.INFO; 
            	}
            	try {
            		ASCommon.setLogLevel(asEnumLogLevel);
            		FileLogOptions logOpts = FileLogOptions.create()
            			.setFile(new File(asLogDir, asLogFileName))
            			.setLogLevel(asEnumLogLevel);
            		ASCommon.setFileLogging(logOpts); 
            	}
            	catch (RuntimeASException ase) {
					LOGGER.log(Level.WARN, "Invalid AS log setting folder=%s file=%s error=%s", asLogDir, asLogFileName, ase.getMessage());
            	}
            }
            setMetaSpace(metaspace);
        }
    }

    static Object getMetaspaceInfo(String metaspaceName) {
        return cacheMap.get(metaspaceName);
    }
}
