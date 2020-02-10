package com.tibco.cep.studio.decision.table.validation.impl;

import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

/**
 * 
 * Class representing a model entity matched post rename.
 *
 */
public class MatchingValidationEntity {
	
	/**
	 * Which model column object we are working with.
	 */
	private Column backendColumn;
	
	private PropertyDefinition matchedPropertyDefinition;
	
	private ArgumentProperty matchingArgumentProperty;
	
	private MatchingEntityType matchingEntityType;
	
	public enum MatchingEntityType {
		PROPERTY,
		PRIMITIVE,
		UNASSIGNED;
	}

	/**
	 * 
	 * @param backendColumn
	 * @param matchingArgumentProperty
	 * @param matchedPropertyDefinition
	 */
	public MatchingValidationEntity(Column backendColumn, 
			                        ArgumentProperty matchingArgumentProperty,
			                        PropertyDefinition matchedPropertyDefinition) {
		this.matchingArgumentProperty = matchingArgumentProperty;
		this.matchedPropertyDefinition = matchedPropertyDefinition;
		this.backendColumn = backendColumn;
		matchingEntityType = MatchingEntityType.PROPERTY;
	}

	/**
	 * 
	 * @param backendColumn
	 * @param matchingArgumentProperty
	 */
	public MatchingValidationEntity(Column backendColumn,
			                        ArgumentProperty matchingArgumentProperty) {
		this(backendColumn, matchingArgumentProperty, null);
		this.matchingEntityType = MatchingEntityType.PRIMITIVE;
	}
	
	/**
	 * 
	 * @param backendColumn
	 * 
	 */
	public MatchingValidationEntity(Column backendColumn) {
		this(backendColumn, null, null);
		this.matchingEntityType = MatchingEntityType.UNASSIGNED;
	}

	/**
	 * @return the matchedPropertyDefinition
	 */
	public final PropertyDefinition getMatchedPropertyDefinition() {
		return matchedPropertyDefinition;
	}

	/**
	 * @return the matchedPrimitive
	 */
	public final ArgumentProperty getMatchingArgumentProperty() {
		return matchingArgumentProperty;
	}

	/**
	 * @return the matchingEntityType
	 */
	public final MatchingEntityType getMatchingEntityType() {
		return matchingEntityType;
	}

	/**
	 * @return the backendColumn
	 */
	public final Column getBackendColumn() {
		return backendColumn;
	}
}
