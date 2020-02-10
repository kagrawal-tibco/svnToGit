package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationErrorMessage;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalRangeAlert extends LocalAlert {

	private static final String THIS_TYPE = BEViewsElementNames.RANGE_ALERT;

	public static final String PROP_KEY_LOWER_VALUE = "LowerValue";

	public static final String PROP_KEY_UPPER_VALUE = "UpperValue";

	public LocalRangeAlert() {
		this(THIS_TYPE);
	}

	public LocalRangeAlert(String configType) {
		super(configType);
	}

	public LocalRangeAlert(LocalElement parentElement, String configType, BEViewsElement mdElement) {
		super(parentElement, configType, mdElement);
	}

	public LocalRangeAlert(LocalElement parentConfig, String configType, String name) {
		super(parentConfig, configType, name);
	}

	public LocalRangeAlert(LocalElement parentConfig, String configType) {
		super(parentConfig, configType);
	}

	@Override
	public boolean isValid() throws Exception {
		super.isValid();
		String seriesName = ((LocalSeriesConfig) getParent().getParent()).getDisplayableName();
		String location = getParent().getParent().getURI();
		String sLowerValue = getPropertyValueWithNoException(PROP_KEY_LOWER_VALUE);
		String sUpperValue = getPropertyValueWithNoException(PROP_KEY_UPPER_VALUE);
		double lowerValue = Double.NEGATIVE_INFINITY;
		if (StringUtil.isEmpty(sLowerValue) == false) {
			try {
				lowerValue = Double.parseDouble(sLowerValue);
			} catch (NumberFormatException e) {
				addValidationMessage(new SynValidationErrorMessage("Invalid lower range threshold '" + sLowerValue + "' in '" + seriesName + "/" + getDisplayableName() + "'", location));
			}
		}
		double upperValue = Double.POSITIVE_INFINITY;
		if (StringUtil.isEmpty(sUpperValue) == false) {
			try {
				upperValue = Double.parseDouble(sUpperValue);
			} catch (NumberFormatException e) {
				addValidationMessage(new SynValidationErrorMessage("Invalid upper range threshold '" + sUpperValue + "' in '" + seriesName + "/" + getDisplayableName() + "'", location));
			}
		}
		if (lowerValue == Double.NEGATIVE_INFINITY && upperValue == Double.POSITIVE_INFINITY) {
			addValidationMessage(new SynValidationErrorMessage("No thresholds specified in '" + seriesName + "/" + getDisplayableName() + "'", location));
		}
		if (lowerValue > upperValue) {
			addValidationMessage(new SynValidationErrorMessage("Invalid range in '" + seriesName + "/" + getDisplayableName() + "'", location));
		}
		return (getValidationMessage() == null);
	}

}