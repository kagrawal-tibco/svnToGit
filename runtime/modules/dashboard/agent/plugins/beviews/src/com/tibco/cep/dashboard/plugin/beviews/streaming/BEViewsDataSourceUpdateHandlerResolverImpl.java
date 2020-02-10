/**
 *
 */
package com.tibco.cep.dashboard.plugin.beviews.streaming;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.dashboard.plugin.beviews.BEViewsPlugIn;
import com.tibco.cep.dashboard.plugin.beviews.data.EmptyDataSourceHandler;
import com.tibco.cep.dashboard.psvr.common.FatalException;
import com.tibco.cep.dashboard.psvr.mal.model.MALActionRule;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALQueryParam;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandler;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.plugin.SimpleResolverImpl;

/**
 * @author anpatil
 *
 */
public class BEViewsDataSourceUpdateHandlerResolverImpl extends SimpleResolverImpl {

	private ArrayList<Class<? extends AbstractHandler>> nullQueryParamsHandler;

	public BEViewsDataSourceUpdateHandlerResolverImpl() throws FatalException{
		super(BEViewsPlugIn.PLUGIN_ID,ResolverType.DATASOURCE_UPDATE_HANDLER,QueryBasedDataSourceUpdateHandler.class);
		nullQueryParamsHandler = new ArrayList<Class<? extends AbstractHandler>>(1);
		nullQueryParamsHandler.add(EmptyDataSourceUpdateHandler.class);
	}

	@Override
	public boolean isAcceptable(MALElement element) {
		if (element instanceof MALSeriesConfig){
			MALSeriesConfig seriesConfig = (MALSeriesConfig) element;
			MALActionRule actionRule = seriesConfig.getActionRule();
			if (actionRule != null){
				return actionRule.getDataSource() != null;
			}
		}
		return false;
	}

	public List<Class<? extends AbstractHandler>> resolve(MALElement element) throws PluginException {
		if (element instanceof MALSeriesConfig) {
			MALSeriesConfig seriesConfig = (MALSeriesConfig) element;
			MALActionRule actionRule = seriesConfig.getActionRule();
			if (actionRule != null) {
				for (MALQueryParam queryParam : actionRule.getParams()) {
					if (queryParam.getValue() == null) {
						//return null if any one of the query param as null which means no value
						return nullQueryParamsHandler;
					}
				}
			}
		}
		return super.resolve(element);
	}


}