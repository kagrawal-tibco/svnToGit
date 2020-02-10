package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataExtractor;
import com.tibco.cep.designtime.core.model.beviewsconfig.FieldReferenceValueOption;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalStateSeriesConfig extends LocalSeriesConfig {

	private static final String TEXT = "Text";

	private static final String PROGRESS = "Progress";

	private static final String INDICATOR = "Indicator";

	private static final String NONE = "";

	private String propertyNamePrefixToUse = NONE;

	private List<String> propertiesToBeValidated;

	public LocalStateSeriesConfig() {
		super(BEViewsElementNames.STATE_SERIES_CONFIG);
	}

	public LocalStateSeriesConfig(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, BEViewsElementNames.STATE_SERIES_CONFIG, mdElement);
	}

	public LocalStateSeriesConfig(LocalElement parentElement, String name) {
		super(parentElement, BEViewsElementNames.STATE_SERIES_CONFIG, name);
	}

	@Override
	protected EObject extractValue(EObject propertyContainer) {
		if (propertyContainer instanceof DataExtractor) {
			DataExtractor dataExtractor = (DataExtractor) propertyContainer;
			return dataExtractor.getSourceField();
		}
		else if (propertyContainer instanceof FieldReferenceValueOption){
			FieldReferenceValueOption fieldReferenceValueOption = (FieldReferenceValueOption) propertyContainer;
			return fieldReferenceValueOption.getField();
		}
		return null;
	}

	@Override
	public boolean isValid() throws Exception {
		try {
			String[] prefixesToRemove = null;
			if (StateMachineComponentHelper.isIndicatorSeriesConfig(this) == true) {
				propertyNamePrefixToUse = INDICATOR;
				prefixesToRemove = new String[]{PROGRESS, TEXT};
			}
			else if (StateMachineComponentHelper.isProgressBarContentSeriesConfig(this) == true) {
				propertyNamePrefixToUse = PROGRESS;
				prefixesToRemove = new String[]{INDICATOR, TEXT};
			}
			else if (StateMachineComponentHelper.isTextContentSeriesConfig(this) == true) {
				propertyNamePrefixToUse = TEXT;
				prefixesToRemove = new String[]{PROGRESS, INDICATOR};
			}
			else {
				throw new IllegalStateException(("Unknown settings found in visualization for "+getParent().getURI()));
			}
			propertiesToBeValidated = new LinkedList<String>(getPropertyNames());
			ListIterator<String> listIterator = propertiesToBeValidated.listIterator();
			while (listIterator.hasNext()) {
				String propertyName = (String) listIterator.next();
				for (String prefix : prefixesToRemove) {
					if (propertyName.startsWith(prefix) == true) {
						listIterator.remove();
						break;
					}
				}
			}
			boolean valid = super.isValid();
			if (StateMachineComponentHelper.isProgressBarContentSeriesConfig(this) == true) {
				if (StringUtil.isEmpty(getPropertyValue("ProgressMinValue")) && StringUtil.isEmpty(getPropertyValue("ProgressMinField"))) {
					addValidationErrorMessage("Missing minimum value in "+getDisplayableName());
					valid = false;
				}
				if (StringUtil.isEmpty(getPropertyValue("ProgressMaxValue")) && StringUtil.isEmpty(getPropertyValue("ProgressMaxField"))) {
					addValidationErrorMessage("Missing maximum value in "+getDisplayableName());
					valid = false;
				}
			}
			return valid;
		} finally {
			propertyNamePrefixToUse = NONE;
		}
	}

	@Override
	protected boolean isToBeValidated(SynProperty prop) {
		String name = prop.getName();
		return propertiesToBeValidated.contains(name);
	}

	@Override
	protected String getValueFieldPropertyName() {
		return propertyNamePrefixToUse+"ValueField";
	}

	@Override
	protected String getValueDisplayLabelPropertyName() {
		return propertyNamePrefixToUse+"DisplayFormat";
	}

	@Override
	protected String getTooltipPropertyName() {
		return propertyNamePrefixToUse+"TooltipFormat";
	}

	@Override
	protected List<Object> getValueFieldEnumeration() {
		LocalDataSource dataSource = (LocalDataSource) getElement(BEViewsElementNames.ACTION_RULE).getElement(BEViewsElementNames.DATA_SOURCE);
		return dataSource.getEnumerations(LocalDataSource.ENUM_NUMERIC_FIELD);
	}

	@Override
	public String getDisplayableName() {
		try {
			if (StateMachineComponentHelper.isContentSeriesConfig(this) == true) {
				return "content settings";
			}
			else {
				return "indicator settings";
			}
		} catch (Exception e) {
			return " settings";
		}
	}

	@Override
	public String getURI() {
		try {
			return getParent().getURI();
		} catch (Exception e) {
			return super.getURI();
		}
	}

}