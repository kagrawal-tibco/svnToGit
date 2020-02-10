package com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.ThresholdUnitEnum;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalExternalReference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class StateMachineComponentHelper {

	public static List<State> getUsableStates(StateMachine stateMachine) {
		List<State> usableStates = new LinkedList<State>();
		List<StateEntity> stateEntities = stateMachine.getStateEntities();
		addUsableStates(stateEntities,usableStates);
		return usableStates;
	}

	private static void addUsableStates(List<StateEntity> stateEntities, List<State> usableStates) {
		for (StateEntity stateEntity : stateEntities) {
			if (stateEntity instanceof State) {
				// skip start and end states
				if (stateEntity instanceof StateStart || stateEntity instanceof StateEnd) {
					continue;
				}
				// add the state as usable state
				usableStates.add((State) stateEntity);
				// figure out if we need to dive further down
				if (stateEntity instanceof StateComposite) {
					StateComposite stateComposite = (StateComposite) stateEntity;
					if (stateComposite.isConcurrentState() == false) {
						addUsableStates(stateComposite.getStateEntities(), usableStates);
					}
				}
			}
		}
	}

	public static void setStateMachine(LocalStateMachineComponent component, LocalExternalReference stateMachineReference) throws Exception {
		setStateMachine(component, stateMachineReference, null);
	}

	public static void setStateMachine(LocalStateMachineComponent component, LocalExternalReference stateMachineReference, LocalStateVisualization templateVisualization) throws Exception {
		//remove all visualizations
		List<LocalElement> visualizations = component.getChildren(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION);
		for (LocalElement visualization : visualizations) {
			component.removeElement(visualization);
		}
		if (component.getElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_MACHINE) != null) {
			//remove the existing state machine
			component.removeElement(component.getElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_MACHINE));
		}
		component.addElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_MACHINE, stateMachineReference);
		addTemplateVisualizations(component, templateVisualization);
	}

	public static void addTemplateVisualizations(LocalStateMachineComponent component) throws Exception {
		addTemplateVisualizations(component, null);
	}

	public static void addTemplateVisualizations(LocalStateMachineComponent component, LocalStateVisualization templateVisualization) throws Exception {
		LocalExternalReference stateMachineRef = (LocalExternalReference) component.getElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_MACHINE);
		StateMachine stateMachine = (StateMachine) stateMachineRef.getEObject();
		if (stateMachine == null) {
			return;
		}
		List<State> usableStates = getUsableStates(stateMachine);
		addStateVisualizations(component, usableStates, templateVisualization);
	}

	private static void addStateVisualizations(LocalStateMachineComponent component, List<State> usableStates, LocalStateVisualization templateVisualization) throws Exception {
		for (StateEntity stateEntity : usableStates) {
			LocalStateVisualization stateVisualization = getStateVisualization(component, new LocalExternalReference(stateEntity));
			if (stateVisualization == null) {
				// we don't have an existing visualization, we need to add a new
				// one
				if (templateVisualization != null) {
					// we have been given a template, clone it and add it to
					// component
					stateVisualization = (LocalStateVisualization) templateVisualization.clone();
					component.addElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION, stateVisualization);
					// set the state entity
					stateVisualization.addElement("State", new LocalExternalReference(stateEntity));
				} else {
					// we don't have a template, add a state visualization using
					// automatic values
					addTemplateVisualization(component, stateEntity);
				}
			} else {
				// we have an existing state visualization
				if (getContentSeriesConfig(stateVisualization) == null) {
					// we don't have a content series config
					if (templateVisualization != null && getContentSeriesConfig(templateVisualization) != null) {
						// we have a template content series config , clone it
						// and add to the state visualization
						LocalElement contentSeriesConfig = (LocalElement) getContentSeriesConfig(templateVisualization).clone();
						stateVisualization.addElement(LocalStateVisualization.ELEMENT_KEY_STATE_SERIESCONFIG, contentSeriesConfig);
					} else {
						// we don't have a template or the template does not
						// contain a content series config,
						// create a content series config using automatic values
						addTemplateContentSeriesConfig(stateVisualization);
					}
				}
				if (getIndicatorSeriesConfig(stateVisualization) == null) {
					// we don't have a indicator series config
					if (templateVisualization != null && getIndicatorSeriesConfig(templateVisualization) != null) {
						// we have a template indicator series config , clone it
						// and add to the state visualization
						LocalElement indicatorSeriesConfig = (LocalElement) getIndicatorSeriesConfig(templateVisualization).clone();
						stateVisualization.addElement(LocalStateVisualization.ELEMENT_KEY_STATE_SERIESCONFIG, indicatorSeriesConfig);
					} else {
						// we don't have a template or the template does not
						// contain a indicator series config,
						// create a indicator series config using automatic
						// values
						addTemplateIndicatorSeriesConfig(stateVisualization);
					}
				}
			}
			syncAllNames(stateVisualization);
		}
	}

	public static LocalStateVisualization addTemplateVisualization(LocalStateMachineComponent component, StateEntity stateEntity) throws Exception {
		LocalStateVisualization stateVisualization = (LocalStateVisualization) component.createLocalElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION);
		stateVisualization.addElement("State", new LocalExternalReference(stateEntity));
		LocalDataSource dataSource = getDefaultDataSource(stateVisualization);
		if (dataSource != null) {
			addTemplateContentSeriesConfig(stateVisualization, dataSource);
			addTemplateIndicatorSeriesConfig(stateVisualization, dataSource);
		}
		syncAllNames(stateVisualization);
		return stateVisualization;
	}

	public static LocalStateSeriesConfig addTemplateContentSeriesConfig(LocalStateVisualization visualization) throws Exception {
		LocalDataSource targetDataSource = getDefaultDataSource(visualization);
		if (targetDataSource == null) {
			return null;
		}
		return addTemplateContentSeriesConfig(visualization, targetDataSource);
	}

	public static LocalStateSeriesConfig addTemplateContentSeriesConfig(LocalStateVisualization visualization, LocalDataSource dataSource) throws Exception {
		// create a state series config
		LocalStateSeriesConfig stateSeriesConfig = (LocalStateSeriesConfig) visualization.createLocalElement(LocalStateVisualization.ELEMENT_KEY_STATE_SERIESCONFIG);
		String valueFieldName = (String) dataSource.getEnumerations(LocalDataSource.ENUM_NUMERIC_FIELD).get(0);
		// create action rule
		LocalActionRule localActionRule = (LocalActionRule) stateSeriesConfig.createLocalElement(BEViewsElementNames.ACTION_RULE);
		// set data source
		localActionRule.setElement(BEViewsElementNames.DATA_SOURCE, dataSource);
		// set threshold unit to count
		localActionRule.setPropertyValue(LocalActionRule.PROP_KEY_THRESHOLD_UNIT, ThresholdUnitEnum.COUNT.getName());
		// set threshold count to 1
		localActionRule.setPropertyValue(LocalActionRule.PROP_KEY_THRESHOLD, "1");
		// set text formatting
		stateSeriesConfig.setPropertyValue("TextValueField", valueFieldName);
		stateSeriesConfig.setPropertyValue("TextDisplayFormat", "{" + valueFieldName + "}");
		stateSeriesConfig.setPropertyValue("TextTooltipFormat", "{" + valueFieldName + "}");
		return stateSeriesConfig;
	}

	public static LocalStateSeriesConfig addTemplateIndicatorSeriesConfig(LocalStateVisualization visualization) throws Exception {
		LocalDataSource targetDataSource = getDefaultDataSource(visualization);
		if (targetDataSource == null) {
			return null;
		}
		return addTemplateIndicatorSeriesConfig(visualization, targetDataSource);
	}

	public static LocalStateSeriesConfig addTemplateIndicatorSeriesConfig(LocalStateVisualization visualization, LocalDataSource dataSource) throws Exception {
		// create a state series config
		LocalStateSeriesConfig stateSeriesConfig = (LocalStateSeriesConfig) visualization.createLocalElement(LocalStateVisualization.ELEMENT_KEY_STATE_SERIESCONFIG);
		String valueFieldName = (String) dataSource.getEnumerations(LocalDataSource.ENUM_NUMERIC_FIELD).get(0);

		// create action rule
		LocalActionRule localActionRule = (LocalActionRule) stateSeriesConfig.createLocalElement(BEViewsElementNames.ACTION_RULE);
		// set data source
		localActionRule.setElement(BEViewsElementNames.DATA_SOURCE, dataSource);
		// set threshold unit to count
		localActionRule.setPropertyValue(LocalActionRule.PROP_KEY_THRESHOLD_UNIT, ThresholdUnitEnum.COUNT.getName());
		// set threshold count to 1
		localActionRule.setPropertyValue(LocalActionRule.PROP_KEY_THRESHOLD, "1");
		// set text formatting
		stateSeriesConfig.setPropertyValue("IndicatorValueField", valueFieldName);
		stateSeriesConfig.setPropertyValue("IndicatorTooltipFormat", "{" + valueFieldName + "}");
		return stateSeriesConfig;
	}

	public static LocalDataSource getDefaultDataSource(LocalElement element) throws Exception {
		List<LocalElement> metrics = new LinkedList<LocalElement>(element.getRoot().getChildren(BEViewsElementNames.METRIC));
		if (metrics.isEmpty() == true) {
			return null;
		}
		ListIterator<LocalElement> metricsIterator = metrics.listIterator();
		while (metricsIterator.hasNext()) {
			LocalMetric localMetric = (LocalMetric) metricsIterator.next();
			if (localMetric.getEnumerations(BEViewsElementNames.DATA_SOURCE).isEmpty() == true) {
				metricsIterator.remove();
			}
		}
		if (metrics.isEmpty() == true) {
			return null;
		}
		LocalElement defaultMetric = metrics.get(0);
		return (LocalDataSource) defaultMetric.getEnumerations(BEViewsElementNames.DATA_SOURCE).get(0);

	}

	public final static LocalStateVisualization getStateVisualization(LocalStateMachineComponent component, LocalExternalReference stateReference) throws Exception {
		return getStateVisualization(component, stateReference.getEObject());
	}

	private static LocalStateVisualization getStateVisualization(LocalStateMachineComponent component, Entity state) throws Exception {
		List<LocalElement> visualizations = component.getChildren(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION);
		for (LocalElement visualization : visualizations) {
			LocalElement element = visualization.getElement(LocalStateVisualization.ELEMENT_KEY_STATE);
			if (element != null) {
				Entity existingState = (Entity) element.getEObject();
				if (existingState.getGUID().equals(state.getGUID()) == true) {
					return (LocalStateVisualization) visualization;
				}
			}
		}
		return null;
	}

	public final static LocalStateSeriesConfig getContentSeriesConfig(LocalStateVisualization visualization) throws Exception {
		List<LocalElement> stateSeriesConfigs = visualization.getChildren("StateSeriesConfig");
		for (LocalElement seriesConfig : stateSeriesConfigs) {
			LocalStateSeriesConfig stateSeriesConfig = (LocalStateSeriesConfig) seriesConfig;
			if (isContentSeriesConfig(stateSeriesConfig) == true) {
				return stateSeriesConfig;
			}
		}
		return null;
	}

	public final static LocalStateSeriesConfig getIndicatorSeriesConfig(LocalStateVisualization visualization) throws Exception {
		List<LocalElement> stateSeriesConfigs = visualization.getChildren("StateSeriesConfig");
		for (LocalElement seriesConfig : stateSeriesConfigs) {
			LocalStateSeriesConfig stateSeriesConfig = (LocalStateSeriesConfig) seriesConfig;
			if (isIndicatorSeriesConfig(stateSeriesConfig) == true) {
				return stateSeriesConfig;
			}
		}
		return null;
	}

	public static boolean isContentSeriesConfig(LocalStateSeriesConfig seriesConfig) throws Exception {
		if (isProgressBarContentSeriesConfig(seriesConfig) || isTextContentSeriesConfig(seriesConfig)) {
			return true;
		}
		return false;
	}

	public static boolean isProgressBarContentSeriesConfig(LocalStateSeriesConfig seriesConfig) throws Exception {
		//we only use the ProgressDisplayFormat since ProgressValueField can be null for a deleted field
		return seriesConfig.isPropertyValueSameAsDefault("ProgressDisplayFormat")  == false;
		//return seriesConfig.isPropertyValueSameAsDefault("ProgressValueField")  == false && seriesConfig.isPropertyValueSameAsDefault("ProgressDisplayFormat")  == false;
//		SynProperty property = (SynProperty) seriesConfig.getProperty("ProgressValueField");
//		return !property.getValue().equals(property.getDefault());
	}

	public static boolean isTextContentSeriesConfig(LocalStateSeriesConfig seriesConfig) throws Exception {
		//we only use the TextDisplayFormat since TextValueField can be null for a deleted field
		return seriesConfig.isPropertyValueSameAsDefault("TextDisplayFormat")  == false;
		//return seriesConfig.isPropertyValueSameAsDefault("TextValueField")  == false && seriesConfig.isPropertyValueSameAsDefault("TextDisplayFormat")  == false;
//		SynProperty property = (SynProperty) seriesConfig.getProperty("TextValueField");
//		return !property.getValue().equals(property.getDefault());
	}

	public static boolean isIndicatorSeriesConfig(LocalStateSeriesConfig seriesConfig) throws Exception {
		//we will use the content series config identification since we may be dealing with a indicator series config with missing metric field
		return !isContentSeriesConfig(seriesConfig);
		//return seriesConfig.isPropertyValueSameAsDefault("IndicatorValueField")  == false;
//		SynProperty property = (SynProperty) seriesConfig.getProperty("IndicatorValueField");
//		return !property.getValue().equals(property.getDefault());
	}

	public static void syncAllNames(LocalStateVisualization visualization) throws Exception{
		LocalElement stateMachineComponent = visualization.getParent();
		if (stateMachineComponent == null){
			throw new IllegalArgumentException(visualization+" has no parent");
		}
		if (stateMachineComponent.getChildren(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION).size() > 1) {
			String name = stateMachineComponent.getNewName(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION);
			visualization.setName(name);
		}
		syncSeriesConfigNames(visualization);
	}

	public static void syncSeriesConfigNames(LocalStateVisualization visualization) throws Exception {
		LocalStateSeriesConfig seriesConfig = getContentSeriesConfig(visualization);
		if (seriesConfig != null){
			String name = "SSC_CONTENT";
			seriesConfig.setName(name);
			seriesConfig.setDisplayName("Content Settings");
		}
		seriesConfig = getIndicatorSeriesConfig(visualization);
		if (seriesConfig != null){
			String name = "SSC_INDICATOR";
			seriesConfig.setName(name);
			seriesConfig.setDisplayName("Indicator Settings");
		}
	}

	public static Difference computeDifference(LocalStateMachineComponent component) throws Exception{
		Difference difference = new Difference();
		LocalExternalReference stateMachineRef = (LocalExternalReference) component.getElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_MACHINE);
		if (stateMachineRef != null && stateMachineRef.getEObject() != null){
			StateMachine stateMachine = (StateMachine) stateMachineRef.getEObject();
			//get the golden list
			List<State> usableStates = getUsableStates(stateMachine);
			List<LocalElement> existingStateVizs = new LinkedList<LocalElement>(component.getChildren(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION));
			for (State state : usableStates) {
				LocalStateVisualization visualization = getStateVisualization(component, state);
				if (visualization == null){
					//we have a new state
					difference.statesToAdd.add(new LocalExternalReference(state));
				}
				else {
					//remove the hit from existing list
					existingStateVizs.remove(visualization);
				}
			}
			for (LocalElement localElement : existingStateVizs) {
				//all remaining visualizations are orphan, we should delete them
				difference.stateVisualizationsToBeDeleted.add((LocalStateVisualization) localElement);
			}
		}
		return difference;
	}

	public static class Difference {

		List<LocalExternalReference> statesToAdd;

		List<LocalStateVisualization> stateVisualizationsToBeDeleted;

		public Difference(){
			statesToAdd = new LinkedList<LocalExternalReference>();
			stateVisualizationsToBeDeleted = new LinkedList<LocalStateVisualization>();
		}

		public List<LocalExternalReference> getStatesToAdd(){
			return statesToAdd;
		}

		public List<LocalStateVisualization> getStatesVizualizationToBeDeleted(){
			return stateVisualizationsToBeDeleted;
		}

		public boolean hasDifferences(){
			return !statesToAdd.isEmpty() || !stateVisualizationsToBeDeleted.isEmpty();
		}
	}


}
