package com.tibco.cep.studio.dashboard.ui.wizards.page.dashboard;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPage;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPageSelectorComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPanel;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPartition;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalEntity;
import com.tibco.cep.studio.dashboard.ui.wizards.page.PageTemplate;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class SwitchableDashboardPageTemplate extends PageTemplate {

	public SwitchableDashboardPageTemplate() {
		super("Switchable Dashboard Page",
				"Template to create a dashboard page which contains a page selector component and charts"
				,new SwitchableDashboardPageWizardPage("Switchable Dashboard Page"));
	}

	@Override
	public boolean isAcceptable(List<LocalElement> selection) {
		if (selection.isEmpty() == true) {
			return false;
		}
		HashSet<String> types = new HashSet<String>();
		for (LocalElement localElement : selection) {
			types.add(localElement.getElementType());
		}
		types.removeAll(BEViewsElementNames.getChartOrTextComponentTypes());
		types.remove(BEViewsElementNames.PAGE_SELECTOR_COMPONENT);
		return types.isEmpty();
	}

	@Override
	public List<LocalEntity> buildPage(IProject project, LocalPage page) throws Exception {
		SwitchableDashboardPageWizardPage myPage = (SwitchableDashboardPageWizardPage) super.wizardPage;
		// add a switcher partition
		LocalPartition switcherPartition = (LocalPartition) page.createLocalElement(BEViewsElementNames.PARTITION);
		switcherPartition.setName("PageSwitcherPartition");
		switcherPartition.setDisplayName("Dashboard Switcher Partition");
		switcherPartition.setPropertyValue("SpanPercentage", myPage.getPageSwitcherSpan()+"%");
		// add a panel to switcher partition
		LocalPanel panel = (LocalPanel) switcherPartition.createLocalElement(BEViewsElementNames.PANEL);
		panel.setName("PageSwitcherPanel");
		panel.setDisplayName("Dashboard Switcher Panel");
		panel.setPropertyValue("SpanPercentage", "100%");
		LocalPageSelectorComponent pageSwitcherComponent = (LocalPageSelectorComponent) myPage.getPageSwitcherComponent();
		if (pageSwitcherComponent == null){
			pageSwitcherComponent = (LocalPageSelectorComponent) LocalECoreFactory.getInstance(project).createLocalElement(BEViewsElementNames.PAGE_SELECTOR_COMPONENT);
			pageSwitcherComponent.setName("TemplatePageSelectorComponent");
			pageSwitcherComponent.setDisplayName("Template Page Selector Component");
			pageSwitcherComponent.setDescription("Template Page Selector Component");
			pageSwitcherComponent.setFolder(page.getFolder());
			pageSwitcherComponent.setNamespace(page.getNamespace());
			pageSwitcherComponent.setOwnerProject(project.getName());
		}
		panel.addElement(BEViewsElementNames.COMPONENT, pageSwitcherComponent);
		//add charts partition
		LocalPartition chartsPartition = (LocalPartition) page.createLocalElement(BEViewsElementNames.PARTITION);
		chartsPartition.setName("ChartsPartition");
		chartsPartition.setDisplayName("Charts Partition");
		chartsPartition.setPropertyValue("SpanPercentage", (100-myPage.getPageSwitcherSpan())+"%");
		// add a panel to charts partition
		LocalPanel chartsPanel = (LocalPanel) chartsPartition.createLocalElement(BEViewsElementNames.PANEL);
		chartsPanel.setName("ChartsPanel");
		chartsPanel.setDisplayName("Charts");
		chartsPanel.setPropertyValue("SpanPercentage", "100%");
		List<LocalElement> components = myPage.getChartComponents();
		if (components != null) {
			if (components.isEmpty() == false) {
				for (LocalElement component : components) {
//					if (component.getElementType().equals(BEViewsElementNames.TEXT_ORCHART_COMPONENT) == true) {
						chartsPanel.addElement(BEViewsElementNames.COMPONENT, component);
//					}
				}
			} else {
				// add the first chart to the panel
				List<LocalElement> charts = LocalECoreFactory.getInstance(project).getChildren(BEViewsElementNames.TEXT_OR_CHART_COMPONENT);
				if (charts.isEmpty() == false) {
					chartsPanel.addElement(BEViewsElementNames.COMPONENT, charts.get(0));
				}
			}
		}
		if (myPage.getPageSwitcherComponent() == null){
			return Arrays.asList((LocalEntity)pageSwitcherComponent);
		}
		return Collections.emptyList();
	}

}
