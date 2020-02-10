package com.tibco.cep.dashboard.psvr.data;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandler;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandlerFactory;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * @author anpatil
 */
// TODO Add support for removing least used handlers
public class DataSourceHandlerCache extends AbstractHandlerFactory {

	private static DataSourceHandlerCache instance = null;

	public static final synchronized DataSourceHandlerCache getInstance() {
		if (instance == null) {
			instance = new DataSourceHandlerCache();
		}
		return instance;
	}

	private Map<DataSourceHandlerKey, DataSourceHandler> handlerMap;

	private Map<String, Set<String>> handlerUsageTrackMap;

	private Set<DataCacheListener> listeners;

	private DataSourceHandlerCache() {
		super("datasourcehandlercache", "Datasource Handler Cache", ResolverType.DATASOURCE_HANDLER);
		handlerMap = new HashMap<DataSourceHandlerKey, DataSourceHandler>();
		handlerUsageTrackMap = new HashMap<String, Set<String>>();
		listeners = new HashSet<DataCacheListener>();
	}

	//PATCH we should avoid synchronized method. Also we should limit synchronization to bare minimum and fire notifications post synchronization
	public synchronized final DataSourceHandler getDataSourceHandler(MALSeriesConfig seriesConfig, PresentationContext pCtx) throws DataException, PluginException {
		IDataSourceResolver resolver = (IDataSourceResolver) getResolver(seriesConfig);
		if (resolver == null) {
			// we got no resolver, throw exception and bale out for now
			throw new DataException("No resolver found for " + seriesConfig + "'s " + ResolverType.DATASOURCE_HANDLER);
		}
		DataSourceHandlerKey dataSourceHandlerKey = resolver.getKey(logger, seriesConfig, pCtx);
		DataSourceHandler dataHandler = handlerMap.get(dataSourceHandlerKey);
		if (dataHandler == null) {
			AbstractHandler[] handlers = instantiateHandlers(resolver.resolve(seriesConfig));
			if (handlers == null) {
				// we did not get a handler from the resolver
				throw new DataException(resolver + " did not return a handler for " + seriesConfig + "'s " + ResolverType.DATASOURCE_HANDLER);
			}
			if (handlers.length != 1) {
				// we did not get a single handler from the resolver
				throw new DataException(resolver + " did not return zero or more then one handler for " + seriesConfig + "'s " + ResolverType.DATASOURCE_HANDLER);
			}
			dataHandler = (DataSourceHandler) handlers[0];
			initializeHandler(resolver.getPlugInID(), dataHandler);
			String path = URIHelper.getURI(seriesConfig);
			dataHandler.setName(path);
			// PORT setting unique name as the datahandler's id
			dataHandler.setUniqueName(dataHandler.getId());
			dataHandler.setQueryParams(dataSourceHandlerKey.getQueryParams());
			dataHandler.setQuery(dataSourceHandlerKey.getQuery());
			dataHandler.setThreshold(dataSourceHandlerKey.getThreshold());
			dataHandler.configure(seriesConfig, pCtx);
			handlerMap.put(dataSourceHandlerKey, dataHandler);
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Registering " + dataHandler + " for " + path + " as provided by " + findPlugIn(resolver.getPlugInID()));
			}
			notifyDataSourceHandlerAdded(seriesConfig, dataHandler, pCtx);
		}
		trackUsage(dataHandler, pCtx.getSecurityToken(), seriesConfig);
		return dataHandler;
	}

	private void trackUsage(DataSourceHandler dataHandler, SecurityToken userToken, MALSeriesConfig seriesConfig) {
		String dataSrcHandlerID = dataHandler.getId();
		Set<String> users = handlerUsageTrackMap.get(dataSrcHandlerID);
		if (users == null) {
			users = new HashSet<String>();
			handlerUsageTrackMap.put(dataSrcHandlerID, users);
		}
		String seriesPath = URIHelper.getURI(seriesConfig);
		if (users.add(seriesPath) == true) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Registering " + seriesPath + " as a user of " + dataHandler);
			}
			dataHandler.referenced(seriesPath);
		}
	}

	public final synchronized void removeDataSourceHandler(MALSeriesConfig seriesConfig, PresentationContext pCtx) throws DataException, PluginException {
		IDataSourceResolver resolver = (IDataSourceResolver) getResolver(seriesConfig);
		if (resolver == null) {
			// we got no resolver, throw exception and bale out for now
			throw new DataException("No resolver found for " + seriesConfig + "'s " + ResolverType.DATASOURCE_HANDLER);
		}
		DataSourceHandlerKey dataSourceHandlerKey = resolver.getKey(logger, seriesConfig, pCtx);
		DataSourceHandler dataHandler = handlerMap.get(dataSourceHandlerKey);
		if (dataHandler != null) {
			Set<String> users = handlerUsageTrackMap.get(dataHandler.getId());
			String seriesPath = URIHelper.getURI(seriesConfig);
			users.remove(seriesPath);
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Deregistering " + seriesPath + " as a user of " + dataHandler);
			}
			if (users.isEmpty() == true) {
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Shutting down " + dataHandler.getUniqueName() + "...");
				}
				handlerUsageTrackMap.remove(dataHandler.getId());
				handlerMap.remove(dataSourceHandlerKey);
				notifyDataSourceHandlerRemoved(seriesConfig, dataHandler, pCtx);
				try {
					dataHandler.shutdown();
				} catch (NonFatalException e) {
					exceptionHandler.handleException("could not shutdown " + dataHandler.getUniqueName(), e, Level.WARN);
				}

			} else {
				dataHandler.dereferenced(seriesPath);
			}
		}
	}

	//PATCH DataSourceHandlerCache.getInstance().getPreCreatedDataSourceHandler((MALSeriesConfig) element) needs to be able to handle username/role bindings , how I don't know
//	public final DataSourceHandler getPreCreatedDataSourceHandler(MALSeriesConfig seriesConfig) throws DataException, PluginException {
//		IDataSourceResolver resolver = (IDataSourceResolver) getResolver(seriesConfig);
//		if (resolver == null) {
//			// we got no resolver, throw exception and bale out for now
//			throw new DataException("No resolver found for " + seriesConfig + "'s " + ResolverType.DATASOURCE_HANDLER);
//		}
//		DataSourceHandlerKey dataSourceHandlerKey = resolver.getKey(seriesConfig);
//		return handlerMap.get(dataSourceHandlerKey);
//	}

	@Override
	protected boolean doStop() {
		logger.log(Level.INFO, "Shutting down " + handlerMap.size() + " datasource handlers...");
		Collection<DataSourceHandler> handlers = handlerMap.values();
		for (DataSourceHandler dataHandler : handlers) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Shutting down " + dataHandler.getUniqueName() + "...");
			}
			try {
				dataHandler.shutdown();
			} catch (Exception e) {
				exceptionHandler.handleException("could not shutdown " + dataHandler.getUniqueName(), e, Level.WARN);
			}
		}
		handlerMap.clear();
		handlerUsageTrackMap.clear();
		return true;
	}

	public void addDataCacheListener(DataCacheListener listener) {
		listeners.add(listener);
	}

	public void removeDataCacheListener(DataCacheListener listener) {
		listeners.remove(listener);
	}

	protected void notifyDataSourceHandlerAdded(MALSeriesConfig seriesConfig, DataSourceHandler handler, PresentationContext pCtx) {
		for (DataCacheListener listener : listeners) {
			listener.dataSourceHandlerAdded(seriesConfig, handler, pCtx);
		}
	}

	protected void notifyDataSourceHandlerRemoved(MALSeriesConfig seriesConfig, DataSourceHandler handler, PresentationContext pCtx) {
		for (DataCacheListener listener : listeners) {
			listener.dataSourceHandlerRemoved(seriesConfig, handler, pCtx);
		}
	}

	Collection<DataSourceHandler> handlers() {
		return Collections.unmodifiableCollection(handlerMap.values());
	}

}