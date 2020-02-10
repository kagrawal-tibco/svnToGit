package com.tibco.cep.studio.dashboard.ui.wizards.skin;

import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.wizards.BaseViewsElementWizard;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class TextColorSetWizard extends BaseViewsElementWizard {

	 public TextColorSetWizard(){
		 super(BEViewsElementNames.TEXT_COLOR_SET, "Text Color Set", "New Text Color Set Wizard", "TextComponentColorSetPage", "New Text Color Set", "Create a new Text Color Set");
		 setDefaultPageImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("textcomponentcolorset_wizard.png"));
	 }
}