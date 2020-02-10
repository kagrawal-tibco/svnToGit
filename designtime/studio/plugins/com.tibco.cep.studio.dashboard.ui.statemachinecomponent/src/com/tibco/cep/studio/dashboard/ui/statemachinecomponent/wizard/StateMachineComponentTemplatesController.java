package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard;

import org.eclipse.core.resources.IProject;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class StateMachineComponentTemplatesController {

	private IProject project;

	private String folder;

	private String namespace;

	private LocalDataSource defaultDataSource;

	private LocalStateMachineComponent templateStateComponent;

	private LocalStateVisualization templateStateVisualization;

	public StateMachineComponentTemplatesController(IProject project,String folder,String namespace, LocalDataSource defaultDataSource){
		this.project = project;
		this.folder = folder;
		this.namespace = namespace;
		this.defaultDataSource = defaultDataSource;
	}

	public void createTemplateComponent() throws Exception {
		templateStateComponent = (LocalStateMachineComponent) LocalECoreFactory.getInstance(project).createLocalElement(BEViewsElementNames.STATE_MACHINE_COMPONENT);
		templateStateComponent.getID();
		templateStateComponent.setFolder(folder);
		templateStateComponent.setNamespace(namespace);
		templateStateComponent.setOwnerProject(project.getName());
		templateStateComponent.setName("TemplateStateComponent");
		templateStateVisualization = (LocalStateVisualization) templateStateComponent.createLocalElement(LocalStateMachineComponent.ELEMENT_KEY_STATE_VISUALIZATION);
		if (defaultDataSource != null) {
			StateMachineComponentHelper.addTemplateContentSeriesConfig(templateStateVisualization, defaultDataSource);
			StateMachineComponentHelper.addTemplateIndicatorSeriesConfig(templateStateVisualization, defaultDataSource);
		}
	}

	public void prepPage(AbstractTemplateSettingsWizardPage page) throws Exception {
		if (page.getSeriesConfig() == null) {
			switch (page.getType()){
				case INDICATOR :
					page.setSeriesConfig(StateMachineComponentHelper.getIndicatorSeriesConfig(templateStateVisualization));
					break;
				case CONTENT :
					page.setSeriesConfig(StateMachineComponentHelper.getContentSeriesConfig(templateStateVisualization));
					break;
			}
		}
	}

	public void deleteTemplateComponent() throws Exception {
		if (templateStateComponent != null){
			templateStateComponent.getParent().removeElement(templateStateComponent, true);
		}
	}

	public LocalStateVisualization getTemplateStateVisualization() {
		return templateStateVisualization;
	}

}