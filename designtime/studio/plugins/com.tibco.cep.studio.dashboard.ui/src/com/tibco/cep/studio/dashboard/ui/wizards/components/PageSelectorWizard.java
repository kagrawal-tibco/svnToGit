package com.tibco.cep.studio.dashboard.ui.wizards.components;

import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.wizards.BaseViewsElementWizard;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class PageSelectorWizard extends BaseViewsElementWizard {

	 public PageSelectorWizard(){
		 super(BEViewsElementNames.PAGE_SELECTOR_COMPONENT, "Page Selector Component", "New Page Selector Wizard", "PageSelectorPage", "New Page Selector", "Create a new Page Selector");
		 setDefaultPageImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("pageselectorcomponent_wizard.png"));
	 }
}