package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.forms.ScreenTipSettingsForm;

public class ChartComponentScreenTipSettingsForm extends ScreenTipSettingsForm {

	public ChartComponentScreenTipSettingsForm(FormToolkit formToolKit, Composite parent, boolean showGroup) {
		super(formToolKit, parent, showGroup);
	}

	@Override
	protected String getValue() throws Exception {
		if (localElement == null){
			return "";
		}
		// seriesconfig's parent is visualization whose parent is
		String type = localElement.getParent().getParent().getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE);
		if (type.equals("Table") == true) {
			SynProperty property = (SynProperty) localElement.getProperty("TextValueColumnField");
			if (localElement.getPropertyValue(property.getName()).equals(property.getDefault()) == false) {
				return localElement.getPropertyValue("TextValueColumnFieldTooltipFormat");
			}
			property = (SynProperty) localElement.getProperty("IndicatorValueColumnField");
			if (localElement.getPropertyValue(property.getName()).equals(property.getDefault()) == false) {
				return localElement.getPropertyValue("IndicatorValueColumnFieldTooltipFormat");
			}
			log(new Status(IStatus.WARNING, getPluginId(), this.localElement + " does not contain valid tooltip information for '" + type + "'"));
			return "";
		}
		return localElement.getPropertyValue("ValueAxisFieldTooltipFormat");
	}

	@Override
	protected void setValue(String value) throws Exception {
		// seriesconfig's parent is visualization whose parent is
		String type = localElement.getParent().getParent().getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE);
		if (type.equals("Table") == true) {
			SynProperty property = (SynProperty) localElement.getProperty("TextValueColumnField");
			if (localElement.getPropertyValue(property.getName()).equals(property.getDefault()) == false) {
				localElement.setPropertyValue("TextValueColumnFieldTooltipFormat", value);
			}
			property = (SynProperty) localElement.getProperty("IndicatorValueColumnField");
			if (localElement.getPropertyValue(property.getName()).equals(property.getDefault()) == false) {
				localElement.setPropertyValue("IndicatorValueColumnFieldTooltipFormat", value);
			}
		} else {
			localElement.setPropertyValue("ValueAxisFieldTooltipFormat", value);
		}
	}

}
