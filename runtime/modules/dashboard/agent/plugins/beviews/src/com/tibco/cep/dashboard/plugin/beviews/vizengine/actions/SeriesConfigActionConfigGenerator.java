package com.tibco.cep.dashboard.plugin.beviews.vizengine.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.plugin.beviews.mal.ExternalURL;
import com.tibco.cep.dashboard.plugin.beviews.mal.MALExternalURLUtils;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.managers.MALSearchPageManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.CommandType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigUtils;
import com.tibco.cep.kernel.service.logging.Level;

public class SeriesConfigActionConfigGenerator extends ActionConfigGenerator {

	private String[][] EXTERNAL_LINK_DYN_PARAMS = new String[][]{new String[]{"hrefparams", CURRENTDATACOLUMN_TYPE_SPEC_HREF_PARAMS_DYN_PARAM}};

	private ActionConfigType enabledDrillDownLinkAction;

	private ActionConfigType disabledDrillDownLinkAction;

	private ActionConfigType disabldExternalLinkActionConfig;

	@Override
	protected void init() {

		enabledDrillDownLinkAction = ActionConfigUtils.createActionConfig("DrillDown", CommandType.LAUNCHINTERNALLINK, false, null, new String[][]{new String[]{"link", CURRENTDATACOLUMN_LINK_DYN_PARAM}}, null, null);

		disabledDrillDownLinkAction = ActionConfigUtils.createActionConfig("DrillDown", CommandType.LAUNCHINTERNALLINK, true, null, null, null, null);

		disabldExternalLinkActionConfig = ActionConfigUtils.createActionConfig("Links", CommandType.LAUNCHEXTERNALLINK, true, null, null, null, null);

	}

	@Override
	public List<ActionConfigType> generateActionConfigs(MALElement element, Map<String, String> dynParamSubMap, PresentationContext ctx) throws VisualizationException {
		List<ActionConfigType> actions = new LinkedList<ActionConfigType>();
		MALSeriesConfig seriesConfig = (MALSeriesConfig) element;
		MALPage searchPage = ctx.getViewsConfigHelper().getPageByType(MALSearchPageManager.DEFINITION_TYPE);
		if (searchPage == null || seriesConfig.getActionRule().getDrillableFieldsCount() == 0){
			actions.add(disabledDrillDownLinkAction);
		}
		else {
			actions.add(enabledDrillDownLinkAction);
		}

		Map<String, String> externalLinksMap = getAllExternalLinkNamesWithFieldNamesFor(seriesConfig,ctx);

        if (externalLinksMap.size() != 0){
            String componentID = (String) dynParamSubMap.get(CURRENTCOMPONENT_ID_DYN_PARAM);
            String seriesCfgID = (String) dynParamSubMap.get(CURRENTSERIES_ID_DYN_PARAM);
            ArrayList<ActionConfigType> linkChildren = new ArrayList<ActionConfigType>(externalLinksMap.size());
            for (String externalLinkName : externalLinksMap.keySet()) {
            	String fieldName = externalLinksMap.get(externalLinkName);
                linkChildren.add(createExternalLinkActionCfg(componentID, seriesCfgID, externalLinkName, fieldName, ctx));
            }
            ActionConfigType externalLinksHolderActionConfig = ActionConfigUtils.createActionConfigSet("Links", false, linkChildren);
            actions.add(externalLinksHolderActionConfig);
        }
        else{
            actions.add(disabldExternalLinkActionConfig);
        }
		return actions;
	}

    private Map<String,String> getAllExternalLinkNamesWithFieldNamesFor(MALSeriesConfig seriesConfig, PresentationContext pCtx) {
        try {
            Map<String,String> urlNameToFieldNameMap = new LinkedHashMap<String,String>();
            DataSourceHandler handler = pCtx.getDataSourceHandler(seriesConfig);
            MALSourceElement sourceElement = handler.getSourceElement();
            Map<String, ExternalURL> urls = MALExternalURLUtils.getFieldNameToURLsMap(sourceElement.getId());
            for (Map.Entry<String, ExternalURL> entry : urls.entrySet()) {
            	urlNameToFieldNameMap.put(entry.getValue().getName(), entry.getKey());
			}
            return urlNameToFieldNameMap;
        } catch (VisualizationException e) {
            String msg = getMessage("actionconfig.generator.seriescfg.urlinfofetchfailure", pCtx.getLocale(), pCtx.getMessageGeneratorArgs(e, URIHelper.getURI(seriesConfig)));
            exceptionHandler.handleException(msg, e, Level.WARN, Level.DEBUG);
            return Collections.emptyMap();
        }
    }

    private ActionConfigType createExternalLinkActionCfg(String componentID, String seriesCfgID,String linkName,String fieldName, PresentationContext pCtx) {
    	String[][] basicparams = new String[][]{
    		//component id
    		new String[]{"componentid",componentID},
    		//series config id
    		new String[]{"seriesid",seriesCfgID},
    		//field name
    		new String[]{"fieldname",fieldName}
    	};

        return ActionConfigUtils.createActionConfig(linkName, CommandType.LAUNCHEXTERNALLINK, false, null, EXTERNAL_LINK_DYN_PARAMS, basicparams, null);
    }


	@Override
	protected void shutdown() throws NonFatalException {
		throw new UnsupportedOperationException("shutdown");

	}

}
