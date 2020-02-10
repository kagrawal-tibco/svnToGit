/**
 * 
 */
package com.tibco.cep.store.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.cep.store.StoreExt;
import com.tibco.cep.store.StoreType;
import com.tibco.cep.store.factory.StoreExtFactory;

/**
 * @author vpatil
 *
 */
@BEPackage(
    catalog = "CEP Store",
    category = "Store.Util",
    synopsis = "Store Utility functions")
public class UtilFunctions {
	
	private static Map<StoreType, StoreExt> typeToStoreExtMap;
	
	static{
		typeToStoreExtMap = new ConcurrentHashMap<StoreType, StoreExt>();
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getClientVersion",
		signature = "String getClientVersion (String storeType)",
		params = {
			@FunctionParamDescriptor(name = "storeType", type = "String", desc = "Store Type (valid values - AS)")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "Client version details"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets the client library version details used to connect to the Store.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static String getClientVersion(String storeType) {
		StoreExt storeExt = getStoreExtention(storeType);
		try {
			return storeExt.getVersion();	
		} catch (Exception exception) {
			throw new RuntimeException(String.format("Error fetching client version details of Store [%s]", storeType), exception);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setLogLevel",
		signature = "void setLogLevel (String storeType, String logLevel)",
		params = {
			@FunctionParamDescriptor(name = "storeType", type = "String", desc = "Store Type (valid values - AS)"),
			@FunctionParamDescriptor(name = "logLevel", type = "String", desc = "Log Level. Possible values INFO/DEBUG/WARN/SEVERE/VERBOSE/OFF")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets the log level for the underlying Store",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setLogLevel(String storeType, String logLevel) {
		StoreExt storeExt = getStoreExtention(storeType);
		try {
			storeExt.setLogLevel(logLevel);	
		} catch (Exception exception) {
			throw new RuntimeException(String.format("Error setting log level for Store [%s]", storeType), exception);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setLogFiles",
		signature = "void setLogFiles (String storeType, String filePrefix, long maxFileSize, int maxFiles)",
		params = {
			@FunctionParamDescriptor(name = "storeType", type = "String", desc = "Store Type (valid values - AS)"),
			@FunctionParamDescriptor(name = "filePrefix", type = "String", desc = "Prefix for the log files"),
			@FunctionParamDescriptor(name = "maxFileSize", type = "long", desc = "Max file size post which the Store rotates the log file. The value should be greater than 102400 bytes (100kb)"),
			@FunctionParamDescriptor(name = "maxFiles", type = "int", desc = "Max number of log files")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets the log file details",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setLogFiles(String storeType, String filePrefix, long maxFileSize, int maxFiles) {
		StoreExt storeExt = getStoreExtention(storeType);
		try {
			storeExt.setLogFiles(filePrefix, maxFileSize, maxFiles);	
		} catch (Exception exception) {
			throw new RuntimeException(String.format("Error setting the log file details for Store [%s]", storeType), exception);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setLogHandler",
		signature = "void setLogHandler (String storeType, Object logHandler)",
		params = {
			@FunctionParamDescriptor(name = "storeType", type = "String", desc = "Store Type (valid values - AS)"),
			@FunctionParamDescriptor(name = "logHandler", type = "Object", desc = "Instance of an custom implementation of log handler specific to the Store. Needs to implement interface com.tibco.datagrid.LogHandler.")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets the custom log handler",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setLogHandler(String storeType, Object logHandler) {
		StoreExt storeExt = getStoreExtention(storeType);
		try {
			storeExt.setLogHandler(logHandler);
		} catch (Exception exception) {
			throw new RuntimeException(String.format("Error setting the customer log handler for Store [%s]", storeType), exception);
		}
	}
	
	private static StoreExt getStoreExtention(String storeTypeString) {
		StoreType storeType = StoreType.valueOf(storeTypeString);
		if (storeType == null) throw new IllegalArgumentException(String.format("Invalid Store Type[%s] specified. Valid Store types are %s.", storeTypeString, StoreType.getNames()));

		StoreExt storeExt = typeToStoreExtMap.get(storeType);
		if (storeExt == null){
			storeExt = StoreExtFactory.createStoreExt(storeTypeString);
			typeToStoreExtMap.put(storeType, storeExt);
		}
		return storeExt;
	}
}
