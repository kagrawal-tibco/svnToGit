package com.tibco.cep.studio.dashboard.ui.wizards.skin;

import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.wizards.BaseViewsElementWizard;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class SeriesColorWizard extends BaseViewsElementWizard {

	 public SeriesColorWizard(){
		 super(BEViewsElementNames.SERIES_COLOR, "Series Color", "New Series Color Wizard", "SeriesColorPage", "New Series Color", "Create a new Series Color");
		 setDefaultPageImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("seriescolor_wizard.png"));
	 }
}