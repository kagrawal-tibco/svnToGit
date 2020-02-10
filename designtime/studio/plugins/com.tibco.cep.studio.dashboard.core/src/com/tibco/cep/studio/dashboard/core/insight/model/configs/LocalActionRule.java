package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.beviewsconfig.ActionRule;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalActionRule extends LocalConfig {

	public static final String PROP_KEY_THRESHOLD = "Threshold";

	public static final String PROP_KEY_THRESHOLD_UNIT = "ThresholdUnit";

	public static final String ELEMENT_KEY_DRILLABLE_FIELDS = "DrillableFields";

	public static final String ELEMENT_KEY_QUERY_PARAM = "QueryParam";

	private static final String THIS_TYPE = BEViewsElementNames.ACTION_RULE;

	public LocalActionRule() {
		super(THIS_TYPE);
	}

	public LocalActionRule(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalActionRule(LocalElement parentElement, String name) {
		super(parentElement, THIS_TYPE, name);
	}

	private LocalElement getMeasure() {
		LocalElement dataSource = getElement(BEViewsElementNames.DATA_SOURCE);
		if (dataSource != null) {
			return dataSource.getElement(LocalDataSource.ELEMENT_KEY_SRC_ELEMENT);
		}
		return null;
	}

	@Override
	protected void synchronizeChildren() {
		// clone and add existing query params to ensure that they are sync'ed
		List<LocalElement> children = getChildren(ELEMENT_KEY_QUERY_PARAM);
		for (LocalElement child : children) {
			// if (child.isExisting() == true) {
			LocalElement childClone = (LocalElement) child.clone();
			// we can safely remove the child , when synchronized since the underlying list is empty nothing will happen
			removeElement(child);
			addElement(ELEMENT_KEY_QUERY_PARAM, childClone);
			// }
		}
		((ActionRule) getEObject()).getParams().clear();
		super.synchronizeChildren();
	}

	@Override
	public LocalElement createLocalElement(String childrenType) {
		if (childrenType.equals(ELEMENT_KEY_DRILLABLE_FIELDS)) {
			// This is by reference and should never be created directly.
			throw new IllegalArgumentException("Drillable fields is by reference and should never be created directly.");
		}
		return super.createLocalElement(childrenType);
	}

	@Override
	protected LocalElement convertToLocalElement(EObject emfElement, String particleName, String expectedConfigType) {
		if (emfElement instanceof PropertyDefinition) {
			PropertyDefinition property = (PropertyDefinition) emfElement;
			// Find the metric
			LocalElement measure = getMeasure();
			// ntamhank: Nov 19 2010. Fixed NPE if measure is null
			if (null == measure) {
				return null;
			}
			// and then find the field
			return measure.getElement(LocalMetric.ELEMENT_KEY_FIELD, property.getName(), LocalElement.FOLDER_NOT_APPLICABLE);
		}
		return super.convertToLocalElement(emfElement, particleName, expectedConfigType);
	}

	@Override
	public boolean setElement(String particleName, LocalElement newDataSource) {
		if (particleName.equals(BEViewsElementNames.DATA_SOURCE)) {
			LocalElement oldDataSource = super.getElement(particleName);
			boolean result = super.setElement(particleName, newDataSource);
			updateActionRule(oldDataSource, newDataSource);
			return result;
		} else {
			return super.setElement(particleName, newDataSource);
		}
	}

	private void updateActionRule(LocalElement oldDataSource, LocalElement newDataSource) {
		try {
			if (oldDataSource != null && newDataSource != null && oldDataSource.getID().equals(newDataSource.getID())) {
				// Same datasource is in the action rule as the selected datasource, that means the params remains the same
				return;
			}
			List<LocalElement> queryParams = getChildren(LocalActionRule.ELEMENT_KEY_QUERY_PARAM);
			for (LocalElement localElement : queryParams) {
				removeElement(localElement);
			}
			if (newDataSource == null)
				return;
			List<LocalElement> dsParams = newDataSource.getChildren(LocalDataSource.ELEMENT_KEY_QUERY_PARAM);
			for (LocalElement dsParam : dsParams) {
				LocalElement newParam = (LocalElement) dsParam.clone();
				this.addElement(LocalActionRule.ELEMENT_KEY_QUERY_PARAM, newParam);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update action rule based on selected data source", e);
		}
	}

	@Override
	protected void validateParticle(LocalParticle particle) throws Exception {
		if (BEViewsElementNames.DATA_SOURCE.equals(particle.getName())) {
			String seriesName = ((LocalConfig) getParent()).getDisplayableName();
			LocalElement dataSource = getElement(BEViewsElementNames.DATA_SOURCE);
			if (dataSource == null) {
				addValidationErrorMessage("Missing '" + BEViewsElementNames.DATA_SOURCE + "' in " + seriesName);
				return;
			}
			List<LocalElement> dsQueryParams = dataSource.getChildren(LocalDataSource.ELEMENT_KEY_QUERY_PARAM);
			List<LocalElement> queryParams = getChildren(LocalActionRule.ELEMENT_KEY_QUERY_PARAM);
			// Find missing params
			for (LocalElement dsQueryParam : dsQueryParams) {
				boolean present = false;
				for (LocalElement queryParam : queryParams) {
					if (dsQueryParam.getName().equals(queryParam.getName())) {
						//
						present = true;

						// Also check is it of same type
						String dsQPDataType = ((LocalQueryParam) dsQueryParam).getDataType();
						String qpDataType = ((LocalQueryParam) queryParam).getDataType();
						if (dsQPDataType.equals(qpDataType) == false) {
							// Data Type mismatch
							addValidationErrorMessage("Incorrect data type for '" + dsQueryParam.getName() + "', expecting '" + dsQPDataType + "', found '" + qpDataType + "' in " + seriesName);
						}
					}
				}
				if (present == false) {
					addValidationErrorMessage("Missing query parameter [" + dsQueryParam.getName() + "] in " + seriesName);
				}
			}

			// Find additional params
			for (LocalElement queryParam : queryParams) {
				boolean present = false;
				for (LocalElement dsQueryParam : dsQueryParams) {
					if (dsQueryParam.getName().equals(queryParam.getName())) {
						//
						present = true;
					}
				}
				if (present == false) {
					addValidationErrorMessage("Unwanted query parameter [" + queryParam.getName() + "] in " + seriesName);
				}
			}
		} else {
			super.validateParticle(particle);
		}
	}

}