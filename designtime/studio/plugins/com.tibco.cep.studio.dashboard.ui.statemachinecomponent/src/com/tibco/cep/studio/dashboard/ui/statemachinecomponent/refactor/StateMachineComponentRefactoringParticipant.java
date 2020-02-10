package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.refactor;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataSource;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.StateSeriesConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.StateVisualization;
import com.tibco.cep.designtime.core.model.beviewsconfig.Visualization;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.refactoring.ComponentRefactoringParticipant;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.utils.StateMachineComponentStateRefIDReplacer;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class StateMachineComponentRefactoringParticipant extends ComponentRefactoringParticipant {

	public StateMachineComponentRefactoringParticipant() {
		super(BEViewsElementNames.EXT_STATE_MACHINE_COMPONENT,
				BEViewsElementNames.TEXT_OR_CHART_COMPONENT,
				BEViewsElementNames.CHART_COMPONENT,
				BEViewsElementNames.TEXT_COMPONENT,
				BEViewsElementNames.DATA_SOURCE,
				BEViewsElementNames.METRIC);

	}

	@Override
	protected List<LocalElement> getAffectedComponents(IProject project) throws Exception {
		LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(project);
		return coreFactory.getChildren(BEViewsElementNames.STATE_MACHINE_COMPONENT);
	}

	@Override
	protected boolean updateRelatedComponentReferences(URI affectedComponentURI, Component affectedComponent, Component referredComponent, URI refactoredURI) {
		boolean changed = false;
		List<Visualization> visualizations = affectedComponent.getVisualization();
		for (Visualization visualization : visualizations) {
			StateVisualization stateVisualization = (StateVisualization) visualization;
			List<BEViewsElement> relatedComponents = stateVisualization.getRelatedElement();
			for (int i = 0; i < relatedComponents.size(); i++) {
				BEViewsElement relatedComponent = relatedComponents.get(i);
				if (checkEquals(referredComponent, relatedComponent)) {
					Component proxyObject = (Component) createProxyToNewPath(refactoredURI, affectedComponentURI, referredComponent.eClass());
					relatedComponents.set(i, proxyObject);
					changed = true;
				}
			}
		}
		return changed;
	}

	@Override
	protected boolean deleteRelatedComponentReference(Component component, EObject relatedComponent) {
		boolean changed = false;
		List<Visualization> visualizations = component.getVisualization();
		for (Visualization visualization : visualizations) {
			StateVisualization stateVisualization = (StateVisualization) visualization;
			List<BEViewsElement> relatedComponents = stateVisualization.getRelatedElement();
			ListIterator<?> listIterator = relatedComponents.listIterator();
			while (listIterator.hasNext()) {
				EObject existingRelatedComponent = (EObject) listIterator.next();
				if (checkEquals(existingRelatedComponent, relatedComponent) == true) {
					listIterator.remove();
					changed = true;
					break;
				}
			}
		}
		return changed;
	}

	@Override
	protected List<DataConfig> getDataConfigs(SeriesConfig seriesConfig) {
		StateSeriesConfig stateSeriesConfig = (StateSeriesConfig) seriesConfig;
		List<DataConfig> dataConfigs = new LinkedList<DataConfig>();
		dataConfigs.addAll(stateSeriesConfig.getValueDataConfig());
		return dataConfigs;
	}

	@Override
	protected void updateReferences(EObject object, String projectName, CompositeChange compositeChange) throws Exception {
		if (object instanceof StateMachine){
			URI refactoredURI = createRefactoredURI(object);
			StateMachine refactoredStateMachine = (StateMachine) object;
			LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(getResource().getProject());
			List<LocalElement> localStateMachineComponents = coreFactory.getChildren(BEViewsElementNames.STATE_MACHINE_COMPONENT);
			//go over each state model component
			for (LocalElement localStateMachineComponent : localStateMachineComponents) {
				StateMachineComponent existingStateMachineComponent = (StateMachineComponent) localStateMachineComponent.getEObject();
				EObject referencedStateMachine = existingStateMachineComponent.getStateMachine();
				//is the state model component using the refactored state machine
				if (checkEquals(refactoredStateMachine, referencedStateMachine) == true){
					//yes, it is.. make a copy and start updating it
					StateMachineComponent stateMachineComponentCopy = (StateMachineComponent) EcoreUtil.copy(existingStateMachineComponent);
					//create a proxy to the state machine itself
					StateMachine proxyObject = (StateMachine)createProxyToNewPath(refactoredURI, existingStateMachineComponent.eResource().getURI(), refactoredStateMachine.eClass());
					//set it on the copy
					stateMachineComponentCopy.setStateMachine(proxyObject);
					//we don't need to update the individual state reference since we are storing GUID's of the state see BE-11815
					//update the state's in the state visualization
//					for (Visualization existingVisualization : existingStateMachineComponent.getVisualization()) {
//						Visualization visualizationCopy = null;
//						for (Visualization possibleVizCopyMatch : stateMachineComponentCopy.getVisualization()) {
//							if (possibleVizCopyMatch.getGUID().equals(existingVisualization.getGUID()) == true) {
//								visualizationCopy = possibleVizCopyMatch;
//								break;
//							}
//						}
//						if (visualizationCopy != null) {
//							StateVisualization existingStateVisualization = (StateVisualization) existingVisualization;
//							StateVisualization stateVisualizationCopy = (StateVisualization) visualizationCopy;
//							//update the state reference in visualizationCopy using fragment in existing visualization
//							URI stateURI = createRefactoredURI(object);
//							stateURI = stateURI.appendFragment(EcoreUtil.getURI(existingStateVisualization.getState()).fragment());
//							EObject refactoredState = createProxyToNewPath(stateURI, existingStateMachineComponent.eResource().getURI(), StatesPackage.eINSTANCE.getState());
//							stateVisualizationCopy.setState(refactoredState);
//						}
//						else {
//							throw new Exception("could not find a match for "+existingVisualization.getName()+" under "+localStateMachineComponent.getDisplayableName());
//						}
//					}
					Change change = createTextFileChange(IndexUtils.getFile(projectName, existingStateMachineComponent), stateMachineComponentCopy);
					compositeChange.add(change);
				}
			}
		}
		else {
			super.updateReferences(object, projectName, compositeChange);
		}
	}

	@Override
	protected String changeTitle() {
		return "State Model Component Changes:";
	}

	@Override
	protected boolean isSupportedResource(IFile file) {
		if (isDeleteRefactor() == true) {
			return isExtensionOfInterest(file.getFileExtension());
		}
		return super.isSupportedResource(file);
	}

//	@Override
//	protected boolean isElementOfInterest(String elementType) {
//		return super.isElementOfInterest(elementType);
//	}

	@Override
	protected boolean isExtensionOfInterest(String extension) {
		return "statemachine".equalsIgnoreCase(extension);
	}

	@Override
	protected void deleteReferences(EObject object, String projectName, CompositeChange compositeChange) throws Exception {
		if (object instanceof StateMachine) {
			StateMachine deletedStateMachine = (StateMachine) object;
			//find all users of the state machine
			LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(getResource().getProject());
			List<LocalElement> localStateMachineComponents = coreFactory.getChildren(BEViewsElementNames.STATE_MACHINE_COMPONENT);
			//go over each state model component
			for (LocalElement localStateMachineComponent : localStateMachineComponents) {
				StateMachineComponent existingStateMachineComponent = (StateMachineComponent) localStateMachineComponent.getEObject();
				EObject referencedStateMachine = existingStateMachineComponent.getStateMachine();
				//is the state model component using the refactored state machine
				if (checkEquals(deletedStateMachine, referencedStateMachine) == true){
					//yes, it is.. make a copy and start updating it
					StateMachineComponent stateMachineComponentCopy = (StateMachineComponent) EcoreUtil.copy(existingStateMachineComponent);
					stateMachineComponentCopy.setStateMachine(null);
					for (Visualization affectedVisualization : stateMachineComponentCopy.getVisualization()) {
						((StateVisualization)affectedVisualization).setStateRefID(null);
					}
					Change change = createTextFileChange(IndexUtils.getFile(projectName, existingStateMachineComponent), stateMachineComponentCopy);
					compositeChange.add(change);
				}
			}
		}
		else {
			super.deleteReferences(object, projectName, compositeChange);
		}
	}

	protected boolean deleteMetricReference(Component affectedComponent, EObject metric) {
		boolean isChanged = false;
		EList<Visualization> copyVisualizations = affectedComponent.getVisualization();
		for (Visualization copyVisualization : copyVisualizations) {
			EList<SeriesConfig> copySeriesConfigs = copyVisualization.getSeriesConfig();
			ListIterator<SeriesConfig> seriesConfigsListIterator = copySeriesConfigs.listIterator();
			while (seriesConfigsListIterator.hasNext()) {
				SeriesConfig copySeriesConfig = seriesConfigsListIterator.next();
				ActionRule actionRule = copySeriesConfig.getActionRule();
				DataSource dataSource = actionRule.getDataSource();
				if (dataSource != null && checkEquals(metric, dataSource.getSrcElement()) == true) {
					// we will keep the series config , but empty all impacted fields
					actionRule.setDataSource(null);
					actionRule.getDrillableFields().clear();
					List<DataConfig> dataConfigs = getDataConfigs(copySeriesConfig);
					for (DataConfig dataConfig : dataConfigs) {
						dataConfig.getExtractor().setSourceField(null);
						DataFormat formatter = dataConfig.getFormatter();
						//INFO we do not wipe out the display format since it tells us which type of settings it is see com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper.isIndicatorSeriesConfig(LocalStateSeriesConfig)
						//formatter.setDisplayFormat(null);
						formatter.setToolTipFormat(null);
					}
					isChanged = true;
				}
			}
		}
		return isChanged;
	}

	protected boolean deleteDataSourceReference(Component affectedComponent, EObject referredDataSource) {
		boolean isChanged = false;
		EList<Visualization> copyVisualizations = affectedComponent.getVisualization();
		for (Visualization copyVisualization : copyVisualizations) {
			EList<SeriesConfig> copySeriesConfigs = copyVisualization.getSeriesConfig();
			for (SeriesConfig copySeriesConfig : copySeriesConfigs) {
				ActionRule actionRule = copySeriesConfig.getActionRule();
				DataSource dataSource = actionRule.getDataSource();
				if (checkEquals(referredDataSource, dataSource) == true) {
					// we will keep the series config , but empty all impacted fields
					actionRule.setDataSource(null);
					actionRule.getDrillableFields().clear();
					List<DataConfig> dataConfigs = getDataConfigs(copySeriesConfig);
					for (DataConfig dataConfig : dataConfigs) {
						dataConfig.getExtractor().setSourceField(null);
						DataFormat formatter = dataConfig.getFormatter();
						//INFO we do not wipe out the display format since it tells us which type of settings it is see com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper.isIndicatorSeriesConfig(LocalStateSeriesConfig)
						//formatter.setDisplayFormat(null);
						formatter.setToolTipFormat(null);
					}
					isChanged = true;
				}
			}
		}
		return isChanged;
	}


	@Override
	protected void pasteEntity(String newName, Entity entity, IResource source, IContainer target, boolean overwrite) throws CoreException {
		if (target.getProject().getName().equals(entity.getOwnerProjectName()) == false) {
			//we are processing a paste across projects, we need to update the GUIDs
			//get the state machine used
			StateMachineComponent stateMachineComponent = (StateMachineComponent) entity;
			EObject stateMachineObj = stateMachineComponent.getStateMachine();
			if (stateMachineObj.eIsProxy() == true) {
				stateMachineObj = EcoreUtil.resolve(stateMachineObj, new ResourceSetImpl());
			}
			StateMachine sourceStateMachine = (StateMachine) stateMachineObj;
			//get it's path
			String stateMachinePath = sourceStateMachine.getFullPath()+"."+IndexUtils.getFileExtension(sourceStateMachine);
			//create the replacer
			StateMachineComponentStateRefIDReplacer replacer = new StateMachineComponentStateRefIDReplacer(source.getProject(), target.getProject(), stateMachineComponent, stateMachinePath);
			//trigger the update
			replacer.update();
		}
		super.pasteEntity(newName, entity, source, target, overwrite);
	}


}