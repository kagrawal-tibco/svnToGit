package com.tibco.cep.dashboard.psvr.vizengine;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGeneratorFactory;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigUtils;

/**
 * @author anpatil
 *
 */
public abstract class ComponentHandler extends EngineHandler {

    public VisualizationModel getVisualizationModel(MALComponent component, List<String> seriesConfigNames, PresentationContext pCtx) throws VisualizationException, IOException, MALException, ElementNotFoundException, PluginException {
        VisualizationModel visualizationModel = createVisualizationModel(component, seriesConfigNames, pCtx);
        visualizationModel.setHelp(component.getDescription());
        ActionConfigGenerator[] configGenerators = ActionConfigGeneratorFactory.getInstance().getGenerators(component);
        if (pCtx.generateAdditionalOutputs() == true && configGenerators != null && configGenerators.length > 0){
        	List<ActionConfigType> actionCfgs = new LinkedList<ActionConfigType>();
        	for (ActionConfigGenerator actionConfigGenerator : configGenerators) {
				actionCfgs.addAll(actionConfigGenerator.generateActionConfigs(component, null, pCtx));
			}
            visualizationModel.addActionConfig(ActionConfigUtils.createActionConfigSet("ROOT",false,actionCfgs));
        }
        return visualizationModel;
    }

    protected abstract VisualizationModel createVisualizationModel(MALComponent component, List<String> seriesConfigNames, PresentationContext ctx) throws VisualizationException, IOException, MALException, ElementNotFoundException, PluginException;

    public abstract VisualizationData getVisualizationHeaderData(MALComponent component, List<String> seriesConfigNames, PresentationContext pCtx) throws VisualizationException, IOException, MALException, ElementNotFoundException, DataException, PluginException;

	public abstract VisualizationData getVisualizationData(MALComponent component,SeriesDataHolder seriesDataHolder,PresentationContext pCtx) throws VisualizationException, IOException, MALException, ElementNotFoundException, PluginException, DataException;

}