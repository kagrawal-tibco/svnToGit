package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;
import com.tibco.cep.studio.dashboard.ui.forms.SpinnerPropertyControl;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class RawChartColorSchemeForm extends SimplePropertyForm {

	private SpinnerPropertyControl colorSetControl;
	
	private SpinnerPropertyControl seriesColorControl;

	public RawChartColorSchemeForm(FormToolkit formToolKit, Composite parent) {
		super("Color Scheme", formToolKit, parent, false);
		colorSetControl = (SpinnerPropertyControl) addPropertyAsSpinner("Chart Area Color Index", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.CHART_COMPONENT, "ColorSet"));
		seriesColorControl = (SpinnerPropertyControl) addPropertyAsSpinner("Series Color Index", ViewsConfigReader.getInstance().getProperty(BEViewsElementNames.CHART_COMPONENT, "SeriesColor"));
	}
	
	@Override
	public void refreshSelections() {
		try {
			colorSetControl.disable();
			seriesColorControl.disable();
			if (localElement != null) {
				List<LocalElement> skins = localElement.getRoot().getChildren(BEViewsElementNames.SKIN);
				if (skins.isEmpty() == false) {
					//we have skins , enable both the controls 
					colorSetControl.enable();
					seriesColorControl.enable();
					//lets update the maximum value on the controls
					int colorSetControlMax = 0;
					int seriesColorControlMax = 0;
					String nativeType = ((LocalUnifiedComponent)localElement).getNativeType();
					for (LocalElement skin : skins) {
						List<LocalElement> colorSets = skin.getChildren(BEViewsElementNames.COMPONENT_COLOR_SET);
						int colorSetsCntInSkin = 0;
						for (LocalElement colorSet : colorSets) {
							if (colorSet.getElementType().startsWith(nativeType) == true) {
								colorSetsCntInSkin++;
								seriesColorControlMax = Math.max(seriesColorControlMax, colorSet.getChildren(BEViewsElementNames.SERIES_COLOR).size()-1);
							}
						}
						colorSetControlMax = Math.max(colorSetControlMax, colorSetsCntInSkin);
					}
					colorSetControl.setMaximum((double)colorSetControlMax);
					seriesColorControl.setMaximum((double)seriesColorControlMax);
				}
			}
			super.refreshSelections();
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not refresh "+getTitle(),e));
		}
	}

}