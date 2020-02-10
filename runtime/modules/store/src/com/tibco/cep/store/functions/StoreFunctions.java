/**
 * 
 */
package com.tibco.cep.store.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.cep.store.Item;
import com.tibco.cep.store.StoreConnection;
import com.tibco.cep.store.StoreConnectionInfo;
import com.tibco.cep.store.StoreContainer;
import com.tibco.cep.store.StoreIterator;
import com.tibco.cep.store.StoreQueryOptions;
import com.tibco.cep.store.factory.StoreConnectionFactory;
import com.tibco.cep.store.serializer.StoreSerializer;
import com.tibco.cep.store.util.StoreUtil;

/**
 * @author vpatil
 *
 */
@BEPackage(
        catalog = "CEP Store",
        category = "Store",
        synopsis = "Store functions")
public class StoreFunctions {
	
	protected static Map<String, Map<String, StoreConnection>> urlToStoreConnectionMap;
	private static AtomicInteger currentConnection;
	private static StoreConnection[] connectionPool;
	
	static {
		urlToStoreConnectionMap = new ConcurrentHashMap<String, Map<String, StoreConnection>>();
		StoreSerializer.setIgnoreConceptOrReference(true);
		currentConnection = new AtomicInteger();
		StoreSerializer.setIgnoreConceptOrReference(true);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "connect",
		signature = "void connect (Object storeConnectionInfo)",
		params = {
				@FunctionParamDescriptor(name = "storeConnectionInfo", type = "Object", desc = "Store Connection info details. Refer Store.ConnectionInfo.* for more details.")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Connects to specified Store. All connections based on the pool size setting are connected",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static synchronized void connect(Object storeConnectionInfo) {
		if (!(storeConnectionInfo instanceof StoreConnectionInfo)) throw new RuntimeException("Invalid Store Connection Info object.");
		
		StoreConnectionInfo storeConnInfo = (StoreConnectionInfo) storeConnectionInfo;
		
		if (connectionPool == null) {
			int connectionCnt = storeConnInfo.getPoolSize();
			if (connectionCnt == 0) connectionCnt = 1;
			
			connectionPool = new StoreConnection[connectionCnt];
			for (int i=0; i<connectionCnt; i++) {
				connectionPool[i] = StoreConnectionFactory.createStoreConnection(storeConnInfo);
				try {
					connectionPool[i].connect();
				} catch (Exception exception) {
					throw new RuntimeException(String.format("Error occurred while connecting to Store with url[%s]", storeConnInfo.getUrl()), exception);
				}
			}
		} else {
			throw new RuntimeException(String.format("Pool of size[%s] already exists for store with url[%s]", connectionPool.length, storeConnInfo.getUrl()));
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "open",
		signature = "void open (String url, String containerName)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
				@FunctionParamDescriptor(name = "containerName", type = "String", desc = "Container(i.e. Table in AS3) Name")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Opens a container(i.e. Table in AS3) for operations(put/get/delete). If transactional behavior is desired, call Store.Transactions.enableTransactions before opening the container.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void open(String url, String containerName) {
		StoreConnection storeConnection = StoreFunctions.getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				storeConnection.openContainer(containerName);
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error occurred while opening container[%s]", containerName), exception);
			}
		}
		else throw new IllegalArgumentException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "put",
		signature = "void put (String url, String containerName, Object putObject)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
				@FunctionParamDescriptor(name = "containerName", type = "String", desc = "Name of the Container(i.e. Table in AS3) where the item gets put"),
				@FunctionParamDescriptor(name = "putObject", type = "Object", desc = "Item or Entity(Concept/Event) to put in the container")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Puts the given Item/Entity within the specified container(i.e.Table in AS3). If transactional behavior is desired, call Store.Transactions.enableTransactions before opening the container and then perform put operation.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void put(String url, String containerName, Object putObject) {
		StoreConnection storeConnection = getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				StoreContainer<? extends Item> storeContainer = storeConnection.getContainer(containerName);
				if (storeContainer != null) {
					storeContainer.put(putObject);
					StoreUtil.putConcepts(url, putObject);
				} else {
					throw new IllegalArgumentException(String.format("Invalid or missing container[%s]", containerName));
				}
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error occurred while putting item into Store with url[%s] & container[%s]", url, containerName), exception);
			}
		}
		else throw new RuntimeException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "putAll",
		signature = "void putAll (String url, Object[] putObjects)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
				@FunctionParamDescriptor(name = "putObjects", type = "Object[]", desc = "Item or Entity(Concept/Event) object's (with key) to put into the store")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.6.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Puts the items into the store. Need to make sure all containers for the associated item/entity are opened using Store.open api before putAll call.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void putAll(String url, Object[] putObjects) {
		StoreConnection storeConnection = getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				storeConnection.putAll(url, putObjects);
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error occurred while putting items into the Store with url[%s]", url), exception);
			}
		}
		else throw new RuntimeException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "get",
		signature = "Object get (String url, String containerName, Object getObject)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
				@FunctionParamDescriptor(name = "containerName", type = "String", desc = "Name of the Container(i.e. Table in AS3) from where the item is fetched"),
				@FunctionParamDescriptor(name = "getObject", type = "Object", desc = "Item or Entity(Concept/Event) object (with key) to get from the container")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "Item or Entity(Concept/Event)"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets the item from the specified container(i.e. Table in AS3).",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static Object get(String url, String containerName, Object getObject) {
		StoreConnection storeConnection = getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				StoreContainer<? extends Item> storeContainer = storeConnection.getContainer(containerName);
				if (storeContainer != null) {
					Object entity = storeContainer.get(getObject);
					if (entity != null) StoreUtil.loadConcepts(url, getObject, entity, false);
					return entity;
				} else {
					throw new IllegalArgumentException(String.format("Invalid or missing container[%s]", containerName));
				}
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error occurred while getting item from Store with url[%s] and container[%s]", url, containerName), exception);
			}
		}
		else throw new RuntimeException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getAll",
		signature = "Object[] getAll (String url, Object[] getObjects)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
				@FunctionParamDescriptor(name = "getObjects", type = "Object[]", desc = "Item or Entity(Concept/Event) object's (with key) to get from the store")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "Object[]", desc = "Item or Entity(Concept/Event) objects"),
		version = "5.6.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets the items from the store. Need to make sure all containers for the associated item/entity are opened using Store.open api before getAll call.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static Object[] getAll(String url, Object[] getObjects) {
		StoreConnection storeConnection = getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				return storeConnection.getAll(url, getObjects);
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error occurred while getting items from Store with url[%s]", url), exception);
			}
		}
		else throw new RuntimeException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "delete",
		signature = "void delete (String url, String containerName, Object deleteObject)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
				@FunctionParamDescriptor(name = "containerName", type = "String", desc = "Name of the Container(i.e. Table in AS3) from where the item is deleted"),
				@FunctionParamDescriptor(name = "deleteObject", type = "Object", desc = "Item object (with key) to remove from the container")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Removes the given item from the specified container(i.e. Table in AS3). If transactional behavior is desired, call Store.Transactions.enableTransactions before opening the container and then perform delete operation.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void delete(String url, String containerName, Object deleteObject) {
		StoreConnection storeConnection = getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				StoreContainer<? extends Item> storeContainer = storeConnection.getContainer(containerName);
				if (storeContainer != null) {
					storeContainer.delete(deleteObject);
					StoreUtil.loadConcepts(url, deleteObject, null, true);
				} else {
					throw new IllegalArgumentException(String.format("Invalid or missing container[%s]", containerName));
				}
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error occurred while getting item from Store with url[%s] and container[%s]", url, containerName), exception);
			}
		}
		else throw new RuntimeException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "deleteAll",
		signature = "void deleteAll (String url, Object[] deleteObjects)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
				@FunctionParamDescriptor(name = "deleteObjects", type = "Object[]", desc = "Item or Entity(Concept/Event) object's (with key) to delete from the store")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.6.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Deletes the items from the store. Need to make sure all containers for the associated item/entity are opened using Store.open api before deleteAll call.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void deleteAll(String url, Object[] deleteObjects) {
		StoreConnection storeConnection = getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				storeConnection.deleteAll(url, deleteObjects);
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error occurred while deleting items from Store with url[%s]", url), exception);
			}
		}
		else throw new RuntimeException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "query",
		signature = "Object query (String url, String query, Object[] queryParameters, Object queryOptions, String returnEntityPath)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
				@FunctionParamDescriptor(name = "query", type = "String", desc = "Query that needs to be executed over the Store. Supports parameterized query i.e. select tid from t where age > ?."),
				@FunctionParamDescriptor(name = "queryParameters", type = "Object[]", desc = "Parameters applicable to the query."),
				@FunctionParamDescriptor(name = "queryOptions", type = "Object", desc = "If any, query options that govern the behavior of query execution. Refer to Store.QueryOptions.*"),
				@FunctionParamDescriptor(name = "returnEntityPath", type = "String", desc = "Return entity path. Only if the expected iterator element should be an Entity(Event/Concept), else should be null."),
		},
		freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "Iterator of Item/Entity objects that match the filter"),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns an Iterator of Item/Entity objects from the Store that match the specified filter.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static Object query(String url, String query, Object[] queryParameters, Object queryOptions, String returnEntityPath) {
		if (query == null || query.isEmpty()) throw new IllegalArgumentException("Missing query string. Query string is required.");
		if (queryOptions != null && !(queryOptions instanceof StoreQueryOptions)) throw new IllegalArgumentException("Invalid parameter type. Expected type is Query Options object.");
		StoreConnection storeConnection = getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				return storeConnection.query(query, queryParameters, queryOptions, returnEntityPath);
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error occurred while while executing the query[%s] on the Store.", query), exception);
			}
		}
		else throw new RuntimeException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "closeQuery",
		signature = "void closeQuery (Object queryIterator)",
		params = {
				@FunctionParamDescriptor(name = "queryIterator", type = "Object", desc = "Iterator returned via the 'query' call")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Closes the query and its dependent objects. Use this API only if you need to exit without or inbetween query result iteration. By default, on complete query result iteration query close happens implicitly, no need for explicit call to this API.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void closeQuery(Object queryIterator) {
		if (!(queryIterator instanceof StoreIterator)) throw new RuntimeException("Invalid query Iterator object.");
		try {
			StoreIterator storeIterator = ((StoreIterator)queryIterator);
			if (storeIterator != null) {
				storeIterator.cleanup();
				storeIterator = null;
			}
		} catch(Exception exception) {
			throw new RuntimeException("Error occurred while while closing query.", exception);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "executeUpdate",
		signature = "long executeUpdate (String url, String query)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
				@FunctionParamDescriptor(name = "query", type = "String", desc = "Query that needs to be executed over the Store. Update supports Create/Drop Table/Index.")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "long", desc = "Number of store items effected by the update query"),
		version = "5.6.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Returns the number of store items effected based on the executeUpdate. Note - This api has a prerequisite of the tibdgadmin service running. ",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static Object executeUpdate(String url, String query) {
		if (query == null || query.isEmpty()) throw new IllegalArgumentException("Missing query string. Query string is required.");
		StoreConnection storeConnection = getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				return storeConnection.executeUpdate(query);
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error occurred while while executing the query[%s] on the Store.", query), exception);
			}
		}
		else throw new RuntimeException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "close",
		signature = "void close (String url, String containerName)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store"),
				@FunctionParamDescriptor(name = "containerName", type = "String", desc = "Container Name(i.e. Table in AS3)")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Closes the container. No operation can be performed on this container(i.e. Table in AS3) after closing it.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public static void close(String url, String containerName) {
		StoreConnection storeConnection = StoreFunctions.getStoreConnection(url, null);
		if (storeConnection != null) {
			try {
				storeConnection.closeContainer(containerName);			
			} catch (Exception exception) {
				throw new RuntimeException(String.format("Error occurred while closing container[%s]", containerName), exception);
			}
		}
		else throw new IllegalArgumentException(String.format("Invalid Store url[%s] or Store not connected.", url));
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "disconnect",
		signature = "void disconnect (String url)",
		params = {
				@FunctionParamDescriptor(name = "url", type = "String", desc = "Url of the Store")
		},
		freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.5.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Disconnects from the Store. And closes all the associated connections.",
		cautions = "none",
		fndomain = {ACTION},
		example = ""
	)
	public synchronized static void disconnect(String url) {
		Map<String, StoreConnection> threadToStoreConnection = urlToStoreConnectionMap.remove(url);
		
		if (threadToStoreConnection == null) {
			if (connectionPool == null || connectionPool.length == 0) {
				throw new RuntimeException(String.format("There are no connections associated to the store with url[%s]", url));
			}
		}
		if (threadToStoreConnection != null) {
			closeConnections(url, threadToStoreConnection.values());
			threadToStoreConnection.clear();
		}
		// clean up connection pool
		closeConnections(url, Arrays.asList(connectionPool));
		
		Arrays.fill(connectionPool, null);
		connectionPool = null;
		currentConnection.set(0);
	}
	
	private static void closeConnections(String url, Collection<StoreConnection> storeConnections) {
		try {
			for (StoreConnection storeConnection : storeConnections) {
				if (storeConnection != null) {
					storeConnection.disconnect();
					storeConnection = null;
				}
			}
		} catch (Exception exception) {
			throw new RuntimeException(String.format("Error occurred while disconnecting from Store with url[%s]", url), exception);
		}
	}
	
	public static StoreConnection getStoreConnection(String url, StoreConnectionInfo storeConnectionInfo) {
		if (connectionPool == null) {
			throw new RuntimeException(String.format("Store.connect call is missing with url[%s]", url));
		}
		if (storeConnectionInfo != null && storeConnectionInfo.getUrl() != null) {
			url = storeConnectionInfo.getUrl();
		}
		if (url == null || url.isEmpty()) throw new IllegalArgumentException("Missing Store url. Store url is mandatory to establish connection with the Store.");
		
		Map<String, StoreConnection> threadTostoreConnection = urlToStoreConnectionMap.get(url);
		if (threadTostoreConnection == null) {
			synchronized(StoreFunctions.class) {
				threadTostoreConnection = urlToStoreConnectionMap.get(url);
				if (threadTostoreConnection == null) {
					threadTostoreConnection = new ConcurrentHashMap<String, StoreConnection>();
					urlToStoreConnectionMap.put(url, threadTostoreConnection);
				}
			}
		}
		String storeConnectionKey = Thread.currentThread().getName();
		
		StoreConnection storeConnection = threadTostoreConnection.get(storeConnectionKey);
		if (storeConnection == null) {
			synchronized(StoreFunctions.class) {
				storeConnection = threadTostoreConnection.get(storeConnectionKey);
				if (storeConnection == null) {
					int index = currentConnection.getAndIncrement();
					if (index >= connectionPool.length) {
						currentConnection.set(0);
						index = 0;
					}
					storeConnection = connectionPool[index];
					threadTostoreConnection.put(storeConnectionKey, storeConnection);
				}
			}
		}
		return storeConnection;
	}
	
}
