/**
 * 
 */
package com.tibco.cep.store.as.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.cep.store.as.ASConnectionInfo;

/**
 * @author vpatil
 *
 */
@BEPackage(
		catalog = "CEP Store",
        category = "Store.ConnectionInfo.AS",
        synopsis = "AS Connection Information functions")
public class ASConnectionInfoFunctions {
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setConnectionWaitTime",
		signature = "void setConnectionWaitTime (Object storeConnectionInfo, double waitTime)",
		params = {
				@FunctionParamDescriptor(name = "storeConnectionInfo", type = "Object", desc = "Store Connection Info object"),
				@FunctionParamDescriptor(name = "waitTime", type = "double", desc = "Connection timeout(seconds) during the initial connect call to AS Store")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets the connection wait time for getting a response back from the proxy servers as part of the initial connect call to AS Store. Default is 0.1 seconds. Note - This time does not apply to the entire connect call, it just applies to the time to wait for responses from the proxy servers.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setConnectionWaitTime(Object storeConnectionInfo, double waitTime) {
		if (storeConnectionInfo instanceof ASConnectionInfo) {
			ASConnectionInfo dgStoreConnectionInfo = (ASConnectionInfo) storeConnectionInfo;
			dgStoreConnectionInfo.setWaitTime(waitTime);
		} else {
			throw new IllegalArgumentException("Invalid Store properties type. Expected type Store Connection Info object.");
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setUserCredentials",
		signature = "void setUserCredentials (Object storeConnectionInfo, String userName, String password)",
		params = {
				@FunctionParamDescriptor(name = "storeConnectionInfo", type = "Object", desc = "Store Connection Info object"),
				@FunctionParamDescriptor(name = "userName", type = "String", desc = "User name"),
				@FunctionParamDescriptor(name = "password", type = "String", desc = "User password")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets the user credentials if using a authenticated realm server.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setUserCredentials(Object storeConnectionInfo, String userName, String password) {
		if (userName == null || userName.isEmpty()) throw new RuntimeException("Missing user name. User name is required for a secure communication.");
		if (password == null || password.isEmpty()) throw new RuntimeException("Missing user password. User password is required for a secure communication.");
		if (storeConnectionInfo instanceof ASConnectionInfo) {
			ASConnectionInfo dgStoreConnectionInfo = (ASConnectionInfo) storeConnectionInfo;
			dgStoreConnectionInfo.setUserCredentials(userName, password);
		} else {
			throw new IllegalArgumentException("Invalid Store properties type. Expected type Store Connection Info object.");
		}
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "setTrustFile",
		signature = "void setTrustFile (Object storeConnectionInfo, String trustFilePath)",
		params = {
				@FunctionParamDescriptor(name = "storeConnectionInfo", type = "Object", desc = "Store Connection Info object"),
				@FunctionParamDescriptor(name = "trustFilePath", type = "String", desc = "Trust file path")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets the trust file. The client trusts the secure realm server based on this trust file.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setTrustFile(Object storeConnectionInfo, String trustFilePath) {
		if (trustFilePath == null || trustFilePath.isEmpty()) throw new RuntimeException("Missing trust file path. User name is required for a secure communication.");
		if (storeConnectionInfo instanceof ASConnectionInfo) {
			ASConnectionInfo dgStoreConnectionInfo = (ASConnectionInfo) storeConnectionInfo;
			dgStoreConnectionInfo.setTrustFile(trustFilePath);
		} else {
			throw new IllegalArgumentException("Invalid Store properties type. Expected type Store Connection Info object.");
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setTrustAll",
		signature = "void setTrustAll (Object storeConnectionInfo)",
		params = {
				@FunctionParamDescriptor(name = "storeConnectionInfo", type = "Object", desc = "Store Connection Info object")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets the trust to all. The client trusts all realm servers.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setTrustAll(Object storeConnectionInfo) {
		if (storeConnectionInfo instanceof ASConnectionInfo) {
			ASConnectionInfo dgStoreConnectionInfo = (ASConnectionInfo) storeConnectionInfo;
			dgStoreConnectionInfo.setTrustAll();
		} else {
			throw new IllegalArgumentException("Invalid Store properties type. Expected type Store Connection Info object.");
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setSecondaryStoreUrl",
		signature = "void setSecondaryStoreUrl (Object storeConnectionInfo, String secondaryServerUrl)",
		params = {
				@FunctionParamDescriptor(name = "storeConnectionInfo", type = "Object", desc = "Store Connection Info object"),
				@FunctionParamDescriptor(name = "secondaryServerUrl", type = "String", desc = "Secondary/backup store server url")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.6.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets the secondary/backup store server url",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setSecondaryStoreUrl(Object storeConnectionInfo, String secondaryStoreUrl) {
		if (storeConnectionInfo instanceof ASConnectionInfo) {
			ASConnectionInfo dgStoreConnectionInfo = (ASConnectionInfo) storeConnectionInfo;
			dgStoreConnectionInfo.setSecondaryStoreUrl(secondaryStoreUrl);
		} else {
			throw new IllegalArgumentException("Invalid Store properties type. Expected type Store Connection Info object.");
		}
	}
}
