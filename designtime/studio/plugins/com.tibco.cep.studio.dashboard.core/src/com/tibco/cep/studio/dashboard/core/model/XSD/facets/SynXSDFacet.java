package com.tibco.cep.studio.dashboard.core.model.XSD.facets;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAnnotation;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDFacet;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSchemaComponent;

/**
 * @ *
 */
public class SynXSDFacet implements ISynXSDFacet {

	private ISynXSDSchemaComponent container;

	private ISynXSDAnnotation annotation;

	private String targetNameSpace;

	private Object value;

	private boolean fixed = false;

	private String name;

	public SynXSDFacet(String name, List<Object> value) {
		setName(name);
		setValue(value);
	}

	public SynXSDFacet(String name, String value) {
		setName(name);
		setValue(value);
	}

	public SynXSDFacet(String name, double value) {
		setName(name);
		setValue(value);
	}

	public SynXSDFacet(String name, float value) {
		setName(name);
		setValue(value);
	}

	public SynXSDFacet(String name, int value) {
		setName(name);
		setValue(value);
	}

	public SynXSDFacet(String name, long value) {
		setName(name);
		setValue(value);
	}

	public SynXSDFacet(String name, boolean value) {
		setName(name);
		setValue(value);
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setValue(List<Object> value) {
		this.value = value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValue(double value) {
		this.value = value + "";
	}

	public void setValue(float value) {
		this.value = value + "";
	}

	public void setValue(int value) {
		this.value = value + "";
	}

	public void setValue(long value) {
		this.value = value + "";
	}

	public void setValue(boolean value) {
		this.value = value + "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {

		return getValue().toString();
	}

	public ISynXSDAnnotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(ISynXSDAnnotation annotation) {
		this.annotation = annotation;
	}

	public String getTargetNameSpace() {
		return targetNameSpace;
	}

	public void setTargetNameSpace(String targetNameSpace) {
		this.targetNameSpace = targetNameSpace;
	}

	// ===============================================================
	// Containment
	// ===============================================================

	/**
	 * @return Returns the container.
	 */
	public ISynXSDSchemaComponent getContainer() {
		return container;
	}

	/**
	 * @param container
	 *            The container to set.
	 */
	public void setContainer(ISynXSDSchemaComponent container) {
		this.container = container;
	}

	// ===============================================================
	// Following are convenience methods
	// ===============================================================

	/**
	 * Adds the enumValue to the enumeration List If this facet is not an enumeration do nothing
	 *
	 * @param enumValue
	 */
	@SuppressWarnings("unchecked")
	public void addEnumeration(Object enumValue) {
		if (value instanceof List) {
			((List<Object>) value).add(enumValue);
		}
	}

	/**
	 * Removes the enumValue from the enumeration List If this facet is not an enumeration do nothing
	 *
	 * @param enumValue
	 */
	@SuppressWarnings("unchecked")
	public void removeEnumeration(Object enumValue) {
		if (value instanceof List) {
			((List<Object>) value).remove(enumValue);
		}
	}

	@SuppressWarnings("unchecked")
	public Object cloneThis() throws Exception {
		Object cloneValue = null;
		if (this.value instanceof List) {
			if (value == null) {
				cloneValue = null;
			} else {
				cloneValue = new ArrayList<Object>();
				((ArrayList<Object>) cloneValue).addAll((ArrayList<Object>) value);
			}
		} else {
			cloneValue = this.value;
		}
		SynXSDFacet clone = null;
		if (cloneValue instanceof ArrayList) {
			clone = new SynXSDFacet(this.name, (ArrayList<Object>) cloneValue);
		} else if (cloneValue instanceof String) {
			clone = new SynXSDFacet(this.name, (String) cloneValue);
		} else {
			throw new IllegalArgumentException();
		}
		if (this.container == null) {
			clone.container = null;
		} else {
			clone.container = (ISynXSDSchemaComponent) this.container.cloneThis();
		}
		if (this.annotation == null) {
			clone.annotation = null;
		} else {
			clone.annotation = (ISynXSDAnnotation) this.annotation.cloneThis();
		}
		clone.targetNameSpace = this.targetNameSpace;
		clone.fixed = this.fixed;
		return clone;
	}
}
