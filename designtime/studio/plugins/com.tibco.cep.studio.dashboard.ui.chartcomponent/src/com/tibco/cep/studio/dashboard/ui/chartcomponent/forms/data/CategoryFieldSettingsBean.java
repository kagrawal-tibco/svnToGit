package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data;

import com.tibco.cep.designtime.core.model.beviewsconfig.ThresholdUnitEnum;
import com.tibco.cep.studio.dashboard.ui.forms.ValueFieldSettingsBean;

public class CategoryFieldSettingsBean extends ValueFieldSettingsBean {
	
	private String threshold;

	private String thresholdUnit;

	public CategoryFieldSettingsBean(String fieldName, String dataType) {
		super(fieldName, dataType);
		threshold = "0";
		thresholdUnit = ThresholdUnitEnum.COUNT.getLiteral();
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getThresholdUnit() {
		return thresholdUnit;
	}

	public void setThresholdUnit(String thresholdUnit) {
		this.thresholdUnit = thresholdUnit;
	}
	
}