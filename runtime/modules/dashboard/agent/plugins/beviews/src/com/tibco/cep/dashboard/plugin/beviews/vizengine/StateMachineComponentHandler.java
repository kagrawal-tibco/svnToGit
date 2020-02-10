package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALStateMachineComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALStateVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.ActivityConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.ProcessConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.ProcessModel;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationSeriesContainer;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.ComponentHandler;
import com.tibco.cep.dashboard.psvr.vizengine.SeriesDataHolder;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationHandler;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationHandlerFactory;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateMachine;

public class StateMachineComponentHandler extends ComponentHandler {

	@Override
	protected void init() {
	}

	@Override
	protected VisualizationModel createVisualizationModel(MALComponent component, List<String> seriesConfigNames, PresentationContext ctx) throws VisualizationException, IOException, MALException, ElementNotFoundException,
			PluginException {

		//Cast incoming component to MALStateMachineComponent
		MALStateMachineComponent stateMachineComponent = (MALStateMachineComponent) component;
		//Extract the state machine being used
		StateMachine stateMachine = (StateMachine) ctx.resolveRef(stateMachineComponent.getStateMachine());
		//create the indexer and initialize the indexer
		StateMachineIndex.getInstance(ctx).setStateMachine(stateMachine);
		//create the appropriate state machine parser
		StateMachineParser stateMachineParser;
		//ctx.addAttribute("layoutmode", new String[]{"external"});
		if (ctx.getAttribute("layoutmode") == null) {
			stateMachineParser = new StateMachineTraverser(logger, new ActivityConfigCreator(stateMachineComponent, ctx));
		} else {
			String layoutMode = ((String[]) ctx.getAttribute("layoutmode"))[0];
			if (layoutMode.equals("external")) {
				stateMachineParser = new ExternalStateMachineParser(logger, stateMachineComponent, new ActivityConfigCreator(stateMachineComponent, ctx));
			} else {
				stateMachineParser = new StateMachineTraverser(logger, new ActivityConfigCreator(stateMachineComponent, ctx));
			}
		}
		//trigger the parser processing
		stateMachineParser.process(stateMachine, ctx);

		ProcessConfig processConfig = stateMachineParser.getRootProcessConfig();
		processConfig.setComponentID(component.getId());
		processConfig.setBackgroundColor(ctx.getViewsConfigHelper().getInsightSkin().getComponentBackGroundColor());

//        if(stateMachineParser instanceof ExternalStateMachineParser){
//        	int numConfigs = processConfig.getActivityConfigCount();
//	        for(int i = 0; i < numConfigs; i++){
//	        	ActivityConfig ac = processConfig.getActivityConfig(i);
//	        	System.err.println(
//	        		"ACTIVITY_CONFIG:\n" +
//	        		"\tx = " + ac.getX() + "\n" +
//	        		"\ty = " + ac.getY() + "\n" +
//	        		"\twidth = " + ac.getWidth() + "\n" +
//	        		"\theight = " + ac.getHeight() + "\n"
//	        	);
//	        	for(int j = 0; j < ac.getPathCount(); j++){
//	        		Path p = ac.getPath(j);
//	        		System.err.println("\tpath");
//	        		for(int k = 0; k < p.getPathnodeCount(); k++){
//	        			CoordinatePoint n = p.getPathnode(k);
//	        			System.err.println("\t\tnode(" + n.getX() + ", " + n.getY() + ")");
//	        		}
//
//	        	}
//	    	}
//        }

		// Create the processModel to represent the config and data model for ProcessModel Component
		ProcessModel processModel = new ProcessModel();

		// Set the processConfig to processModel
		processModel.setProcessConfig(processConfig);
		return processModel;
	}

	@Override
	public VisualizationData getVisualizationData(MALComponent component, SeriesDataHolder seriesDataHolder, PresentationContext ctx) throws VisualizationException, IOException, MALException, ElementNotFoundException,
			PluginException, DataException {

		// Create the pagesetDataModel to represent the Data for the PageSetSelector Component
		VisualizationData processDataModel = new VisualizationData();
		processDataModel.setComponentID(component.getId());

		// Get the Visualizations to return as data rows
		MALVisualization[] activityVisualizations = component.getVisualization();

		if (activityVisualizations.length != 0) {
			// Process the Activity Visualizations
			VisualizationHandler activityVisualizationHandler = VisualizationHandlerFactory.getInstance().getHandler(activityVisualizations[0]);
			for (MALVisualization visualization : activityVisualizations) {
				MALStateVisualization stateVisualization = (MALStateVisualization) visualization;
				DataRow[] activityDataRows = activityVisualizationHandler.getVisualizationData(component, stateVisualization, seriesDataHolder, null, ctx);
				if (activityDataRows != null && activityDataRows.length != 0) {
					processDataModel.addDataRow(activityDataRows[0]);
				}
			}
		}
		return processDataModel;
	}

	@Override
	public VisualizationData getVisualizationHeaderData(MALComponent component, List<String> seriesConfigNames, PresentationContext ctx) throws VisualizationException, IOException, MALException, ElementNotFoundException,
			DataException, PluginException {
		//we don't create any header data in process model component
		return null;
	}

	@Override
	protected void shutdown() throws NonFatalException {
	}

	class ActivityConfigCreator implements com.tibco.cep.dashboard.plugin.beviews.vizengine.StateMachineTraverser.ActivityConfigCreator {

		private MALStateMachineComponent component;
		private Map<String, MALStateVisualization> visualizations;

		ActivityConfigCreator(MALStateMachineComponent component, PresentationContext ctx) throws MALException, ElementNotFoundException {
			this.component = component;
			this.visualizations = new HashMap<String, MALStateVisualization>();
			for (MALVisualization visualization : component.getVisualization()) {
				MALStateVisualization stateVisualization = (MALStateVisualization) visualization;
				State state = StateMachineIndex.getInstance(ctx).findState(stateVisualization.getStateRefID());
				if (state != null) {
					visualizations.put(state.getGUID(), stateVisualization);
				}
			}
		}

		@Override
		public ActivityConfig createActivityConfig(State state, PresentationContext ctx) throws VisualizationException, IOException, MALException, ElementNotFoundException, PluginException {
			String stateId = state.getGUID();
			MALStateVisualization stateVisualization = visualizations.get(stateId);
			if (stateVisualization != null) {
				VisualizationHandler handler = VisualizationHandlerFactory.getInstance().getHandler(stateVisualization);
				VisualizationSeriesContainer[] seriesContainers = handler.getVisualizationSeriesContainers(component, stateVisualization, ctx);
				return (ActivityConfig) seriesContainers[0];
			}
			ActivityConfig activityConfig = new ActivityConfig();
			activityConfig.setActivityID(stateId);
			activityConfig.setTitle(state.getName());
			return activityConfig;
		}

	}

}