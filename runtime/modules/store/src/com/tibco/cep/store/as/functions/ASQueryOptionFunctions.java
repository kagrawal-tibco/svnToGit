/**
 * 
 */
package com.tibco.cep.store.as.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.cep.store.as.ASQueryOptions;

/**
 * @author vpatil
 *
 */
@BEPackage(
		catalog = "CEP Store",
        category = "Store.QueryOptions.AS",
        synopsis = "AS Query Option functions")
public class ASQueryOptionFunctions {
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setGlobalSnapshotConsistency",
		signature = "void setGlobalSnapshotConsistency (Object queryOptions)",
		params = {
				@FunctionParamDescriptor(name = "queryOptions", type = "Object", desc = "Query option object")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets snapshot consistency to global. A global snapshot will ensure no partially committed transactions spanning multiple nodes are observed in the query results. However there is cost involved in coordinating across all nodes.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setGlobalSnapshotConsistency(Object queryOptions) {
		if (queryOptions instanceof ASQueryOptions) {
			ASQueryOptions dgQueryOps = (ASQueryOptions) queryOptions;
			dgQueryOps.setGlobalSnapshotConsistency();
		} else {
			throw new IllegalArgumentException("Invalid object type. Expected type Query Options object.");
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setLocalSnapshotConsistency",
		signature = "void setLocalSnapshotConsistency (Object queryOptions)",
		params = {
				@FunctionParamDescriptor(name = "queryOptions", type = "Object", desc = "Query option object")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets snapshot consistency to local, i.e. snapshot taken at each node independently. Even though global coordination across all nodes is not needed here, this could cause partially committed transaction show up in the results.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setLocalSnapshotConsistency(Object queryOptions) {
		if (queryOptions instanceof ASQueryOptions) {
			ASQueryOptions dgQueryOps = (ASQueryOptions) queryOptions;
			dgQueryOps.setLocalSnapshotConsistency();
		} else {
			throw new IllegalArgumentException("Invalid object type. Expected type Query Options object.");
		}
	}
}
