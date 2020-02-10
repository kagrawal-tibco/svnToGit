/**
 * 
 */
package com.tibco.cep.store.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.cep.store.StoreConnection;

/**
 * @author vpatil
 *
 */
@BEPackage(
        catalog = "CEP Store",
        category = "Store.Metadata",
        synopsis = "Store Metadata functions")
public class MetadataFunctions {
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getVersion",
		signature = "String getVersion(String url)",
		params = {
			@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "Current version of the Store"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the current version of the Store",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static String getVersion(String url) {
		StoreConnection storeConnection = StoreFunctions.getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				return storeConnection.getStoreMetadata().getVersion();			
			} catch (Exception exception) {
				throw new RuntimeException("Error fetching Store version", exception);
			}
		}
		else throw new IllegalArgumentException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getContainerNames",
		signature = "String[] getContainerNames(String url)",
		params = {
			@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "String[]", desc = "Container names in the stores"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns all the container names from the Store",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static String[] getContainerNames(String url) {
		StoreConnection storeConnection = StoreFunctions.getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				return storeConnection.getStoreMetadata().getContainerNames();			
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error fetching container names for Store url[%s]", url), exception);
			}
		}
		else throw new IllegalArgumentException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getName",
		signature = "String getName(String url)",
		params = {
			@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "Store name"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the Store name",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static String getName(String url) {
		StoreConnection storeConnection = StoreFunctions.getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				return storeConnection.getStoreMetadata().getName();			
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error fetching Store name for Store url[%s]", url), exception);
			}
		}
		else throw new IllegalArgumentException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getContainerFieldNames",
		signature = "String[] getContainerFieldNames(String url, String containerName)",
		params = {
			@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
			@FunctionParamDescriptor(name = "containerName", type = "String", desc = "Container Name")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "String[]", desc = "List of Field Names"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the list of all field names within a given container",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static String[] getContainerFieldNames(String url, String containerName) {
		StoreConnection storeConnection = StoreFunctions.getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				return storeConnection.getStoreMetadata().getContainerFieldNames(containerName);			
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error fetching fields associated to container[%s]", containerName), exception);
			}
		}
		else throw new IllegalArgumentException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getContainerFieldType",
		signature = "String getContainerFieldType(String url, String containerName, String fieldName)",
		params = {
			@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
			@FunctionParamDescriptor(name = "containerName", type = "String", desc = "Container Name"),
			@FunctionParamDescriptor(name = "fieldName", type = "String", desc = "Field Name")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "Field Type"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the data type of the specified field",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static String getContainerFieldType(String url, String containerName, String fieldName) {
		StoreConnection storeConnection = StoreFunctions.getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				return storeConnection.getStoreMetadata().getContainerFieldType(containerName, fieldName);
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error fetching data type associated to field[%s] in container[%s]", fieldName, containerName), exception);
			}
		}
		else throw new IllegalArgumentException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getContainerPrimaryIndex",
		signature = "String getContainerPrimaryIndex(String url, String containerName)",
		params = {
			@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
			@FunctionParamDescriptor(name = "containerName", type = "String", desc = "Container Name")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "Primary index of the container"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the primary index for the specified container",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static String getContainerPrimaryIndex(String url, String containerName) {
		StoreConnection storeConnection = StoreFunctions.getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				return storeConnection.getStoreMetadata().getContainerPrimaryIndex(containerName);			
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error fetching primary index associated to container[%s]", containerName), exception);
			}
		}
		else throw new IllegalArgumentException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getContainerIndexNames",
		signature = "String[] getContainerIndexNames(String url, String containerName)",
		params = {
			@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
			@FunctionParamDescriptor(name = "containerName", type = "String", desc = "Container Name")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "String[]", desc = "List of indexes on the containers"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the list of index names on the specified container",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static String[] getContainerIndexNames(String url, String containerName) {
		StoreConnection storeConnection = StoreFunctions.getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				return storeConnection.getStoreMetadata().getContainerIndexNames(containerName);			
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error fetching indexes associated to container[%s]", containerName), exception);
			}
		}
		else throw new IllegalArgumentException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getContainerIndexFieldNames",
		signature = "String[] getContainerIndexFieldNames(String url, String containerName, String indexName)",
		params = {
			@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
			@FunctionParamDescriptor(name = "containerName", type = "String", desc = "Container Name"),
			@FunctionParamDescriptor(name = "indexName", type = "String", desc = "Index Name")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "String[]", desc = "List of fields associated to the index"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the list of fields associated to the specified index",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static String[] getContainerIndexFieldNames(String url, String containerName, String indexName) {
		StoreConnection storeConnection = StoreFunctions.getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				return storeConnection.getStoreMetadata().getContainerIndexFieldNames(containerName, indexName);			
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error fetching associated fields for index[%s]", indexName), exception);
			}
		}
		else throw new IllegalArgumentException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}

}
