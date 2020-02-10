package com.tibco.cep.studio.dashboard.ui.wizards.page.dashboard;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPage;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPanel;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPartition;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalEntity;
import com.tibco.cep.studio.dashboard.ui.wizards.page.PageTemplate;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class StandardDashboardPageWithStateMachineCompPageTemplate extends PageTemplate {

	protected StandardDashboardPageWithStateMachineCompPageTemplate() {
		super("Standard Dashboard Page With State Model Component",
				"Template to create a dashboard page which contains a state model component and charts",
				new StandardDashboardPageWithStateMachineCompWizardPage("Standard Dashboard Page"));
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
		types.remove(BEViewsElementNames.STATE_MACHINE_COMPONENT);
		return types.isEmpty();
	}

	@Override
	public List<LocalEntity> buildPage(IProject project, LocalPage page) throws Exception {
		StandardDashboardPageWithStateMachineCompWizardPage myPage = (StandardDashboardPageWithStateMachineCompWizardPage) super.wizardPage;
		// add a single partition
		LocalPartition partition = (LocalPartition) page.createLocalElement(BEViewsElementNames.PARTITION);
		partition.setName("Partition");
		partition.setDisplayName("Partition");
		partition.setPropertyValue("SpanPercentage", "100%");
		// add a panel for the state model component
		LocalPanel smcPanel = (LocalPanel) partition.createLocalElement(BEViewsElementNames.PANEL);
		smcPanel.setName("SMC_Panel");
		smcPanel.setDisplayName("State Model Component Panel");
		smcPanel.setPropertyValue("SpanPercentage", myPage.getStateMachineComponentSpan()+"%");
		if (myPage.getStateMachineComponent() != null) {
			smcPanel.addElement(BEViewsElementNames.COMPONENT, myPage.getStateMachineComponent());
		}
		//add a panel for the charts
		LocalPanel chartsPanel = (LocalPanel) partition.createLocalElement(BEViewsElementNames.PANEL);
		chartsPanel.setName("Charts_Panel");
		chartsPanel.setDisplayName("Charts Panel");
		chartsPanel.setPropertyValue("SpanPercentage", (100-myPage.getStateMachineComponentSpan())+"%");
		List<LocalElement> components = myPage.getChartComponents();
		if (components != null) {
			if (components.isEmpty() == false) {
				for (LocalElement component : components) {
//					if (component.getElementType().equals(BEViewsElementNames.TEXT_CHART_COMPONENT) == true) {
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
		return Collections.emptyList();
	}

}
