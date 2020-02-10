package com.tibco.cep.dashboard.plugin.internal.streaming;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.dashboard.plugin.internal.DefaultPlugIn;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandler;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.plugin.SimpleResolverImpl;

public class DefaultDataSourceUpdateHandlerResolver extends SimpleResolverImpl {
	
	private List<Class<? extends AbstractHandler>> updatableDataSourceUpdator;
	
	private List<Class<? extends AbstractHandler>> nonUpdatableDataSourceUpdator;

	public DefaultDataSourceUpdateHandlerResolver() {
		super(DefaultPlugIn.PLUGIN_ID, ResolverType.DATASOURCE_UPDATE_HANDLER, null);
		updatableDataSourceUpdator = new ArrayList<Class<? extends AbstractHandler>>(1);
		updatableDataSourceUpdator.add(DefaultDataSourceUpdateHandler.class);
		nonUpdatableDataSourceUpdator = new ArrayList<Class<? extends AbstractHandler>>(1);
		nonUpdatableDataSourceUpdator.add(DefaultUpdateDataProvider.class);
	}
	
	@Override
	public List<Class<? extends AbstractHandler>> resolve(MALElement element) throws PluginException {
		if (element instanceof MALSeriesConfig){
			//PATCH DataSourceHandlerCache.getInstance().getPreCreatedDataSourceHandler((MALSeriesConfig) element) needs to be able to handle username/role bindings , how I don't know
//			try {
//				DataSourceHandler dataSourceHandler = DataSourceHandlerCache.getInstance().getPreCreatedDataSourceHandler((MALSeriesConfig) element);
//				if (dataSourceHandler instanceof Updateable){
//					return updatableDataSourceUpdator;
//				}
//			} catch (DataException e) {
//				throw new PluginException(e.getMessage(),e);
//			}
			return updatableDataSourceUpdator;
		}
		return nonUpdatableDataSourceUpdator;
	}	

}
