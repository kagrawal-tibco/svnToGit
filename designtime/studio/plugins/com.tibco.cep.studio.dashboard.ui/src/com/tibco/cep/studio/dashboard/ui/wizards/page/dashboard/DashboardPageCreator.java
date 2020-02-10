package com.tibco.cep.studio.dashboard.ui.wizards.page.dashboard;

import com.tibco.cep.studio.dashboard.ui.wizards.page.PageCreator;
import com.tibco.cep.studio.dashboard.ui.wizards.page.PageTemplate;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class DashboardPageCreator extends PageCreator {

	private PageTemplate defaultTemplate = new StandardDashboardPageTemplate();

	private PageTemplate[] availableTemplates = new PageTemplate[] {
		defaultTemplate,
		new SwitchableDashboardPageTemplate(),
		new StandardDashboardPageWithStateMachineCompPageTemplate(),
		new SwitchableDashboardPageWithStateMachineCompTemplate()
	};

	protected DashboardPageCreator() {
		super();
		setTemplate(defaultTemplate);
	}

	@Override
	protected String getType() {
		return BEViewsElementNames.DASHBOARD_PAGE;
	}

	public PageTemplate[] getAvailableTemplates() {
		return availableTemplates;
	}

	public PageTemplate getDefaultTemplate(){
		return defaultTemplate;
	}

}
