package com.tibco.cep.dashboard.psvr.streaming;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandlerCache;
import com.tibco.cep.dashboard.psvr.data.Updateable;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandler;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandlerFactory;
import com.tibco.cep.dashboard.psvr.plugin.IResolver;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.security.SecurityToken;

//TODO Add support for removing least used update handlers
public class DataSourceUpdateHandlerFactory extends AbstractHandlerFactory {

	private static final DataSourceUpdateHandler[] EMPTY_ARRAY = new DataSourceUpdateHandler[0];

	private static DataSourceUpdateHandlerFactory instance;

	public static final synchronized DataSourceUpdateHandlerFactory getInstance() {
		if (instance == null) {
			instance = new DataSourceUpdateHandlerFactory();
		}
		return instance;
	}

	private Map<String,DataSourceUpdateHandler> delegatingUpdateHandlersIndex;

	private Map<String,Map<SecurityToken,DataSourceUpdateHandler>> updateHandlersIndex;

	private DataSourceUpdateHandlerFactory() {
		super("datasourceupdatehandlercache", "Datasource UpdateHandler Cache", ResolverType.DATASOURCE_UPDATE_HANDLER);
		delegatingUpdateHandlersIndex = new HashMap<String, DataSourceUpdateHandler>();
		updateHandlersIndex = new HashMap<String, Map<SecurityToken,DataSourceUpdateHandler>>();
	}

	public /*synchronized*/ DataSourceUpdateHandler getDataSourceUpdateHandler(MALSeriesConfig seriesConfig, PresentationContext pCtx) throws DataException, PluginException {
		DataSourceHandler dataSourceHandler = DataSourceHandlerCache.getInstance().getDataSourceHandler(seriesConfig, pCtx);
		return getDataSourceUpdateHandler(seriesConfig, dataSourceHandler, pCtx);
	}

	//provide an API to prevent access to com.tibco.cep.dashboard.psvr.data.DataSourceHandlerCache since com.tibco.cep.dashboard.psvr.data.DataSourceHandlerCache is synchronized
	DataSourceUpdateHandler getDataSourceUpdateHandler(MALSeriesConfig seriesConfig, DataSourceHandler dataSourceHandler, PresentationContext pCtx) throws PluginException, DataException {
		DataSourceUpdateHandler dataSourceUpdateHandler = getDataSourceUpdateHandler(dataSourceHandler,pCtx.getSecurityToken());
		if (dataSourceUpdateHandler == null){
			//PATCH can we use the datasourcehandler for synchronization instead of this to allow multiple data source update handler creation ?
			synchronized (this) {
				dataSourceUpdateHandler = getDataSourceUpdateHandler(dataSourceHandler,pCtx.getSecurityToken());
				if (dataSourceUpdateHandler == null){
					dataSourceUpdateHandler = createDataSourceUpdateHandler(dataSourceHandler,seriesConfig);
					registerDataSourceUpdateHandler(dataSourceHandler,pCtx.getSecurityToken(),dataSourceUpdateHandler);
				}
			}
		}
		return dataSourceUpdateHandler;
	}

	private DataSourceUpdateHandler getDataSourceUpdateHandler(DataSourceHandler dataSourceHandler,SecurityToken token){
		if (dataSourceHandler instanceof Updateable){
			return delegatingUpdateHandlersIndex.get(dataSourceHandler.getUniqueName());
		}
		Map<SecurityToken, DataSourceUpdateHandler> updateHandlers = updateHandlersIndex.get(dataSourceHandler.getUniqueName());
		if (updateHandlers == null){
			return null;
		}
		return updateHandlers.get(token);
	}

	private DataSourceUpdateHandler createDataSourceUpdateHandler(DataSourceHandler dataSourceHandler, MALSeriesConfig seriesConfig) throws PluginException, DataException {
		IResolver resolver = getResolver(seriesConfig);
		if (resolver == null){
			//uh oh , we got no resolver, throw exception and bale out for now
			throw new DataException("No resolver found for "+seriesConfig+"'s "+ResolverType.DATASOURCE_UPDATE_HANDLER);
		}
		AbstractHandler[] handlers = instantiateHandlers(resolver.resolve(seriesConfig));
		if (handlers == null){
			//uh oh , we did not get a handler from the resolver
			throw new DataException(resolver+" did not return a handler for "+seriesConfig+"'s "+ResolverType.DATASOURCE_UPDATE_HANDLER);
		}
		if (handlers.length != 1){
			//uh oh , we did not get a single handler from the resolver
			throw new DataException(resolver+" did not return zero or more then one handler for "+seriesConfig+"'s "+ResolverType.DATASOURCE_UPDATE_HANDLER);
		}
		DataSourceUpdateHandler dataSourceUpdateHandler = (DataSourceUpdateHandler) handlers[0];
		validateHandlerType(dataSourceHandler,dataSourceUpdateHandler);
		dataSourceUpdateHandler.setDataSourceHandler(dataSourceHandler);
		initializeHandler(resolver.getPlugInID(), dataSourceUpdateHandler);
		dataSourceUpdateHandler.start();
		return dataSourceUpdateHandler;
	}

    private void validateHandlerType(DataSourceHandler dataSourceHandler,DataSourceUpdateHandler handler) {
        if (dataSourceHandler instanceof Updateable){
            if (handler instanceof UpdateDataProvider){
                throw new RuntimeException("updatehandler for "+Updateable.class.getName()+" cannot be of type "+UpdateDataProvider.class.getName());
            }
        }
        else if (dataSourceHandler instanceof DataSourceHandler){
            if ((handler instanceof UpdateDataProvider) == false){
                throw new RuntimeException("updatehandler["+handler.getClass().getName()+"] for "+DataSourceHandler.class.getName()+" should be of type "+UpdateDataProvider.class.getName());
            }
        }
    }

	private void registerDataSourceUpdateHandler(DataSourceHandler dataSourceHandler,SecurityToken token,DataSourceUpdateHandler dataSourceUpdateHandler){
		String primaryKey = dataSourceHandler.getUniqueName();
		if (dataSourceHandler instanceof Updateable){
			delegatingUpdateHandlersIndex.put(primaryKey,dataSourceUpdateHandler);
		}
		else {
			Map<SecurityToken, DataSourceUpdateHandler> updateHandlers = updateHandlersIndex.get(primaryKey);
			if (updateHandlers == null){
				updateHandlers = new HashMap<SecurityToken, DataSourceUpdateHandler>();
				updateHandlersIndex.put(primaryKey, updateHandlers);
			}
			updateHandlers.put(token,dataSourceUpdateHandler);
		}
	}

	public DataSourceUpdateHandler[] getPreCreatedHandlers(String uniqueName){
		DataSourceUpdateHandler dataSourceUpdateHandler = delegatingUpdateHandlersIndex.get(uniqueName);
		if (dataSourceUpdateHandler != null){
			return new DataSourceUpdateHandler[]{dataSourceUpdateHandler};
		}
		if (dataSourceUpdateHandler == null){
			Map<SecurityToken, DataSourceUpdateHandler> updateHandlers = updateHandlersIndex.get(uniqueName);
			if (updateHandlers != null){
				return updateHandlers.values().toArray(new DataSourceUpdateHandler[updateHandlers.size()]);
			}
		}
		return EMPTY_ARRAY;
	}

	synchronized boolean removeHandler(DataSourceUpdateHandler dataSourceUpdateHandler){
		DataSourceUpdateHandler removedHandler = delegatingUpdateHandlersIndex.remove(dataSourceUpdateHandler.subscriptionName);
		if (removedHandler == null){
			Map<SecurityToken, DataSourceUpdateHandler> updateHandlers = updateHandlersIndex.get(dataSourceUpdateHandler.subscriptionName);
			if (updateHandlers != null){
				SecurityToken token = null;
				for (Entry<SecurityToken, DataSourceUpdateHandler> entry : updateHandlers.entrySet()) {
					if (entry.getValue() == dataSourceUpdateHandler){
						token = entry.getKey();
						break;
					}
				}
				if (token != null){
					removedHandler = updateHandlers.remove(token);
					if (updateHandlers.isEmpty() == true){
						updateHandlersIndex.remove(dataSourceUpdateHandler.subscriptionName);
					}
				}
			}
		}
		return removedHandler != null;
	}

	@Override
	protected boolean doStop() {
		List<DataSourceUpdateHandler> handlers = new LinkedList<DataSourceUpdateHandler>(delegatingUpdateHandlersIndex.values());
		for (Map<SecurityToken, DataSourceUpdateHandler> updateHandlers : updateHandlersIndex.values()) {
			handlers.addAll(updateHandlers.values());
		}
		for (DataSourceUpdateHandler dataSourceUpdateHandler : handlers) {
			dataSourceUpdateHandler.shutdown();
		}
		return true;
	}

	Collection<DataSourceUpdateHandler> handlers(){
		return Collections.unmodifiableCollection(delegatingUpdateHandlersIndex.values());
	}

}