/**
 * 
 */
package com.tibco.cep.store.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.cep.store.StoreQueryOptions;
import com.tibco.cep.store.factory.StoreQueryOpionsFactory;

/**
 * @author vpatil
 *
 */
@BEPackage(
        catalog = "CEP Store",
        category = "Store.QueryOptions",
        synopsis = "Store Query Option functions")
public class QueryOptionFunctions {
	@com.tibco.be.model.functions.BEFunction(
		name = "create",
		signature = "Object create (String storeType)",
		params = {
				@FunctionParamDescriptor(name = "storeType", type = "String", desc = "Store Type (valid values - AS)")
		},
		freturn = @FunctionParamDescriptor(name = "Query Options Object", type = "Object", desc = "Returns a Query Options Object"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Creates a query options object specific to the Store to configure query execution properties.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static Object create(String storeType) {
		return StoreQueryOpionsFactory.createQueryOptions(storeType);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setPrefetchSize",
		signature = "void setPrefetchSize (Object queryOptions, long prefetchSize)",
		params = {
				@FunctionParamDescriptor(name = "queryOptions", type = "Object", desc = "Query option object"),
				@FunctionParamDescriptor(name = "prefetchSize", type = "long", desc = "Query prefetch size.")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets the query prefetch size.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setPrefetchSize(Object queryOptions, long prefetchSize) {
		if (queryOptions instanceof StoreQueryOptions) {
			StoreQueryOptions queryOps = (StoreQueryOptions) queryOptions;
			queryOps.setPrefetchSize(prefetchSize);
		} else {
			throw new IllegalArgumentException("Invalid object type. Expected type Query Options object.");
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setReuse",
		signature = "void setReuse (Object queryOptions, boolean reuse)",
		params = {
				@FunctionParamDescriptor(name = "queryOptions", type = "Object", desc = "Query option object"),
				@FunctionParamDescriptor(name = "reuse", type = "boolean", desc = "Sets the query to be cached for reuse")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Sets the query to be cached for reuse during subsequent calls.The caching is restricted to the containing transacted/nontransacted session. Will not be available across transactions.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void setReuse(Object queryOptions, boolean reuse) {
		if (queryOptions instanceof StoreQueryOptions) {
			StoreQueryOptions queryOps = (StoreQueryOptions) queryOptions;
			queryOps.setReuse(reuse);
		} else {
			throw new IllegalArgumentException("Invalid object type. Expected type Query Options object.");
		}
	}
}
