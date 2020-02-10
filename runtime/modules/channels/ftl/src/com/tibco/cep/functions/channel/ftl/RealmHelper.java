package com.tibco.cep.functions.channel.ftl;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;



@com.tibco.be.model.functions.BEPackage(
        catalog = "Communication",
        category = "FTL.Realm",
        synopsis = "Realm functions")
public class RealmHelper {
	public static ConcurrentHashMap<String,Object> pubMap = new ConcurrentHashMap<String,Object>();
	public static ConcurrentHashMap<String,Object> subMap = new ConcurrentHashMap<String,Object>();
	private static AtomicInteger subCounter = new AtomicInteger();
	private static AtomicInteger pubCounter = new AtomicInteger();
	
    @com.tibco.be.model.functions.BEFunction(
            name = "connectToRealmServer",
            signature = "Object connectToRealmServer (String realmUrl, String applicationName, String username, String password, TibProperties tibProperties)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "realmUrl", type = "String", desc = "The URL where the FTL Realm Server is running."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "applicationName", type = "String", desc = "The String application name."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "username", type = "String", desc = "The username credential to be used for connecting to FTL Realm server. This username would override the username passed in TibProperties Object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "password", type = "String", desc = "The password credential to be used for connecting to FTL Realm server. This password would override the username passed in TibProperties Object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tibProperties", type = "Object", desc = "TibProperties properties")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Realm type Object"),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Connect to a FTL Realm Server, and create a Realm object.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static Object connectToRealmServer (String realmUrl, String applicationName, String username, String password, Object tibProperties) {
        return RealmHelperDelegate.connectToRealmServer(realmUrl, applicationName, username, password, tibProperties);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getVersionInformation",
            signature = "String getVersionInformation ()",
            params = {
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Version of the FTL Java client library (Java archive file)."),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Version of the FTL Java client library (Java archive file).",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static String getVersionInformation () {
        return RealmHelperDelegate.getVersionInformation();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setLogFiles",
            signature = "void setLogFiles (String filePrefix, long maxFileSize, int maxFiles, Object arg)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filePrefix", type = "String", desc = "All log files begin with this filename prefix."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "maxFileSize", type = "long", desc = "FTL rotates the log files when the current log file exceeds this limit (in Bytes).<br/>This value must be greater than 102400 (100 KB)."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "maxFiles", type = "int", desc = "FTL limites the number of logfiles to this maximum value set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arg", type = "Object", desc = "TibProperties type Object, by default set to null.")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Arrange rotating log files.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static void setLogFiles (String filePrefix, long maxFileSize, int maxFiles, Object arg) {
        RealmHelperDelegate.setLogFiles(filePrefix, maxFileSize, maxFiles, arg);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "setLogLevel",
            signature = "void setLogLevel (String level)",
            params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "level", type = "String", desc = "The String value representing the level of logging requested.<br/>The possible logging levels which are String constants can be obtained from the java API documentation of FTL class.")

            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Set the FTL Log trace level.",
            cautions = "none",
            fndomain={ACTION},
            example = ""
    )
    public static void setLogLevel (String level) {
        RealmHelperDelegate.setLogLevel(level);
    }
    
    @com.tibco.be.model.functions.BEFunction(
    		name = "getPublisher",
    		signature="Object getPublisher(Object realmObject, String endPointName, String appInstanceIdentifier, Object props)",
    		params = {
    		@com.tibco.be.model.functions.FunctionParamDescriptor(name="realmObject", type = "Object", desc = "Object of type Realm"),
    		@com.tibco.be.model.functions.FunctionParamDescriptor(name="endPointName", type="String", desc="The String endPoint name."),
    		@com.tibco.be.model.functions.FunctionParamDescriptor(name="appInstanceIdentifier", type="String", desc = "The String appInstanceIdentifier."),
    		@com.tibco.be.model.functions.FunctionParamDescriptor(name="props", type="Object", desc="The Object of type FTL.Property.")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="Object", desc="An FTLPublisher type Object"),
    		version = "5.2",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "This method returns a FTLPublisher Object, using the passed Realm Object, endPointName and appInstanceIdentifier provided.",
    		cautions = "none",
    		fndomain={ACTION},
    		example = ""
    )
    
    public static Object getPublisher(Object realmObject, String endPointName, String appInstanceIdentifier, Object props){
    	return RealmHelperDelegate.getPublisher(realmObject, endPointName, appInstanceIdentifier, props);
    }
    
    @com.tibco.be.model.functions.BEFunction(
    		name = "getSubscriber",
    		signature="Object getSubscriber(Object realmObject, String endPointNamername, String appInstanceIdentifier, Object props)",
    		params = {
    		@com.tibco.be.model.functions.FunctionParamDescriptor(name="realmObject", type="Object", desc = "Object of type Realm"),
    		@com.tibco.be.model.functions.FunctionParamDescriptor(name="endPointName", type="String", desc = "The String endPoint name."),
    		@com.tibco.be.model.functions.FunctionParamDescriptor(name="appInstanceIdentifier", type="String", desc="The String appInstanceIdentifier."),
    		@com.tibco.be.model.functions.FunctionParamDescriptor(name="props", type="Object", desc = "The Object of type FTL.Property.") 		
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name="", type="Object", desc="An FTLSubscriber type Object"),
    		version = "5.2",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "This method returns a FTLSubscriber Object, using the passed Realm Object, endPointName and appInstanceIdentifier provided.",
    		cautions = "none",
    		fndomain={ACTION},
    		example= ""
    )
    
    public static Object getSubscriber(Object realmObject, String endPointName, String appInstanceIdentifier, Object props){
    	return RealmHelperDelegate.getSubscriber(realmObject, endPointName, appInstanceIdentifier, props);
    }
    
    
}
