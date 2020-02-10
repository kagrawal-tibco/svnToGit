package com.tibco.cep.dashboard.integration.embedded;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.tibco.cep.dashboard.config.SynchronizedSimpleDateFormat;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALActionRule;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.studio.dashboard.preview.SeriesDataSet;

class EPresentationContext extends PresentationContext {

	private Map<MALSeriesConfig, DataSourceHandler> handlers;

	public EPresentationContext(SecurityToken token, TokenRoleProfile tokenRoleProfile, SeriesDataSet[] seriesData) throws MALException, ElementNotFoundException {
		super();
		this.token = token;
		this.generateAdditionalOutputs = false;
		this.profile = tokenRoleProfile;
		this.viewsConfigHelper = tokenRoleProfile.getViewsConfigHelper();
		handlers = new HashMap<MALSeriesConfig, DataSourceHandler>();
		profile = tokenRoleProfile;
	    MALComponent component = profile.getViewsConfigHelper().getComponentById(null);
		MALVisualization[] visualizations = component.getVisualization();
		for (int i = 0; i < visualizations.length; i++) {
			MALVisualization visualization = visualizations[i];
			MALSeriesConfig[] seriesConfigs = visualization.getSeriesConfig();
			for (int j = 0; j < seriesConfigs.length; j++) {
				MALSeriesConfig seriesConfig = seriesConfigs[j];
				MALActionRule actionRule = seriesConfig.getActionRule();
				SeriesDataSet data = null;
				if (seriesData != null) {
					for (int k = 0; k < seriesData.length; k++) {
						if (seriesData[k].getSeriesID().equals(seriesConfig.getId())) {
							data = seriesData[k];
							break;
						}
					}
				}
				handlers.put(seriesConfig, new EDataSourceHandler(actionRule, data));
			}
		}
	}

	@Override
	public Locale getLocale() {
		return Locale.getDefault();
	}

	@Override
	public DataSourceHandler getDataSourceHandler(MALSeriesConfig seriesCfg) throws VisualizationException {
		return handlers.get(seriesCfg);
	}

	@Override
	public TokenRoleProfile getTokenRoleProfile() {
		return profile;
	}

	@Override
	public ViewsConfigHelper getViewsConfigHelper() {
		return profile.getViewsConfigHelper();
	}

	@Override
	public MALFieldMetaInfo resolveFieldRef(Object sourceField) throws MALException, ElementNotFoundException {
		try{
			return (MALFieldMetaInfo) sourceField;
		}
		catch(ClassCastException ex){
			return null;
		}
	}

//	@Override
//	public AlertEvaluator getVisualEvaluator(MALSeriesConfig seriesConfig) {
//		return null;
//	}

	@Override
	public SynchronizedSimpleDateFormat getCustomDateFormat() {
		return null;
	}
}
