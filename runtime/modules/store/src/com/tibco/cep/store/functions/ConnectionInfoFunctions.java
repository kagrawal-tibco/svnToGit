/**
 * 
 */
package com.tibco.cep.store.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.cep.store.StoreConnectionInfo;
import com.tibco.cep.store.factory.StoreConnectionInfoFactory;

/**
 * @author vpatil
 *
 */
@BEPackage(
        catalog = "CEP Store",
        category = "Store.ConnectionInfo",
        synopsis = "Store Connection Information functions")
public class ConnectionInfoFunctions {
	
	@com.tibco.be.model.functions.BEFunction(
		name = "create",
		signature = "Object create (String url, String type)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
				@FunctionParamDescriptor(name = "type", type = "String", desc = "Store Type (valid values - AS)")
		},
		freturn = @FunctionParamDescriptor(name = "StoreConnectionInfo Object", type = "Object", desc = "Returns a Store ConnectionInfo Object"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Creates a Store Connection Info object to initialize it with various Store specific connection configurations.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static Object create(String url, String type) {
		return StoreConnectionInfoFactory.createStoreConnectionInfo(type, url);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setName",
		signature = "void setName (Object storeConnectionInfo, String name)",
		params = {
				@FunctionParamDescriptor(name = "storeConnectionInfo", type = "Object", desc = "Store Connection Info object"),
				@FunctionParamDescriptor(name = "name", type = "String", desc = "Store Name")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets the name of the store. For AS Store if using default grid name, pass null\\dont set a name at all.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setName(Object storeConnectionInfo, String name) {
		if (storeConnectionInfo instanceof StoreConnectionInfo) {
			StoreConnectionInfo storeConnInfo = (StoreConnectionInfo) storeConnectionInfo;
			storeConnInfo.setName(name);
		} else {
			throw new IllegalArgumentException("Invalid Store properties type. Expected type Store Connection Info object.");
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setConnectionTimeout",
		signature = "void setConnectionTimeout (Object storeConnectionInfo, double connectionTimeout)",
		params = {
				@FunctionParamDescriptor(name = "storeConnectionInfo", type = "Object", desc = "Store Connection Info object"),
				@FunctionParamDescriptor(name = "connectionTimeout", type = "double", desc = "Connection timeout(seconds) for calls between client and Store")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets the connection timeout for all calls from client to Store, except the initial connect call. Default is 5 seconds.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setConnectionTimeout(Object storeConnectionInfo, double connectionTimeout) {
		if (storeConnectionInfo instanceof StoreConnectionInfo) {
			StoreConnectionInfo storeConnInfo = (StoreConnectionInfo) storeConnectionInfo;
			storeConnInfo.setConnectionTimeout(connectionTimeout);
		} else {
			throw new IllegalArgumentException("Invalid Store properties type. Expected type Store Connection Info object.");
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setConnectionPoolSize",
		signature = "void setConnectionPoolSize (Object storeConnectionInfo, int connectionPoolSize)",
		params = {
				@FunctionParamDescriptor(name = "storeConnectionInfo", type = "Object", desc = "Store Connection Info object"),
				@FunctionParamDescriptor(name = "connectionPoolSize", type = "int", desc = "Connection pool size")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets the connection pool size.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setConnectionPoolSize(Object storeConnectionInfo, int connectionPoolSize) {
		if (storeConnectionInfo instanceof StoreConnectionInfo) {
			StoreConnectionInfo storeConnInfo = (StoreConnectionInfo) storeConnectionInfo;
			storeConnInfo.setPoolSize(connectionPoolSize);
		} else {
			throw new IllegalArgumentException("Invalid Store properties type. Expected type Store Connection Info object.");
		}
	}
}
