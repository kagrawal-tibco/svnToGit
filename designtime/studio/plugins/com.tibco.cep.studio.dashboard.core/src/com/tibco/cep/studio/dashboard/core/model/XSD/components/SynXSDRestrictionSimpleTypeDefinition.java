package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.tibco.cep.studio.dashboard.core.exception.SynValidationErrorMessage;
import com.tibco.cep.studio.dashboard.core.model.ISynPropertyEnumProvider;
import com.tibco.cep.studio.dashboard.core.model.XSD.facets.SynXSDFacet;
import com.tibco.cep.studio.dashboard.core.model.XSD.facets.SynXSDFacetGroup;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAtomicSimpleTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDFacet;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSimpleTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.providers.ISynXSDFacetsProvider;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;

/**
 * @ *
 */
public class SynXSDRestrictionSimpleTypeDefinition extends SynXSDSimpleTypeDefinition implements ISynXSDFacetsProvider {

	private String readablePatternDescription = "";

	/**
	 * Each implementation of this restriction type may contain a List of available Facets.
	 *
	 * The List is provided as information-only in this class and it is up to the caller of this class to determine what to do with it.
	 *
	 * An example is when presenting the users with the possible properties to set in this restriction; the list can be used to determine which facets are available.
	 */
	private SynXSDFacetGroup facetGroup = new SynXSDFacetGroup();

	private ISynPropertyEnumProvider enumProvider;

	/**
	 * The base type of a simple type restriction must be a simple type
	 */
	private ISynXSDSimpleTypeDefinition base;

	public SynXSDRestrictionSimpleTypeDefinition() {
		super();
		enumProvider = new DefaultEnumProvider(this);
	}

	/**
	 * @param name
	 * @param base
	 */
	public SynXSDRestrictionSimpleTypeDefinition(String name, SynXSDSimpleTypeDefinition base) {
		this(name, base, false);
	}

	/**
	 * @param name
	 * @param base
	 */
	public SynXSDRestrictionSimpleTypeDefinition(String name, SynXSDSimpleTypeDefinition base, boolean allowsNegative) {
		super(name, base);
		enumProvider = new DefaultEnumProvider(this);
		facetGroup = new SynXSDFacetGroup(base, allowsNegative);
	}

	/**
	 * Assumption: maxExclusive is greater than or equal to minExclusive.
	 *
	 * @param name
	 * @param base
	 * @param minExclusive must be a valid number, null, or "".
	 * @param maxExclusive must be a valid number, null, or "".
	 */
	public SynXSDRestrictionSimpleTypeDefinition(String name, SynXSDSimpleTypeDefinition base, String minExclusive, String maxExclusive) {

		super(name, base);
		boolean allowsNegative = true;
		enumProvider = new DefaultEnumProvider(this);
		facetGroup = new SynXSDFacetGroup(base, allowsNegative);
		if (!StringUtil.isEmpty(minExclusive)) {
			facetGroup.setMinInclusive(minExclusive);
		}
		if (!StringUtil.isEmpty(maxExclusive)) {
			facetGroup.setMaxExclusive(maxExclusive);
		}
	}

	/**
	 * Return whether the given object is valid
	 */
	public boolean isValid(Object value) {
		setValidationMessage(null);
		if (false == super.isValid(value)) {
			return false;
		}

		ISynXSDAtomicSimpleTypeDefinition atomicType = getAtomicTypeDefinition();
		if (null != atomicType) {
			Class<?> javaTypeClass = atomicType.getJavaType();
			if (null != javaTypeClass) {

				String javaTypeName = javaTypeClass.getName();

				/*
				 * Validate String first because it is the most common
				 */
				if (javaTypeName.equals(java.lang.String.class.getName())) {
					return isStringValid(value.toString());
				}

				/*
				 * Validate enumeration
				 */
				if (null != getEnumerations() && getEnumerations().size() > 0) {
					return isEnumValid(value);
				}

				/*
				 * Validate numeric values
				 */
				if (true == isNumeric()) {
					return isNumberValid(value);
				}
			} else {
				throw new IllegalArgumentException("Java primitive type is not found");
			}
		} else {
			throw new IllegalArgumentException("Atomic type is not found");
		}

		return true;
	}

	protected boolean isStringValid(String value) {
		if (getLength() != -1 && value.length() != getLength()) {
			setValidationMessage(new SynValidationErrorMessage("The length of \"" + value + "\" is required to be exactly  " + getLength()));
			return false;
		} else if (value.length() > getMaxLength()) {
			setValidationMessage(new SynValidationErrorMessage("The length of \"" + value + "\" is longer than the maximum allowed length of  " + getMaxLength()));
			return false;
		} else if (value.length() < getMinLength() && false == isNullable()) {
			setValidationMessage(new SynValidationErrorMessage("The length of \"" + value + "\" is required to be at least  " + getMinLength()));
			return false;
		}

		if (null == getPattern() || getPattern().length() == 0) {
			return true;
		} else if (null != value && value.length() > 0 && false == Pattern.matches(getPattern(), value)) {
			setValidationMessage(new SynValidationErrorMessage("The value of \"" + value + "\" does not match the required pattern; " + getReadablePatternDescription()));
			return false;
		}
		return true;
	}

	protected boolean isNumberValid(Object value) {
		String valueString = value.toString();
		double dValue = Double.parseDouble(value.toString());
		double maxValue = Double.parseDouble(getMaxInclusive());
		double minValue = Double.parseDouble(getMinInclusive());
		if (Double.isInfinite(maxValue) == false && Double.isNaN(maxValue) == false && dValue > maxValue) {
			setValidationMessage(new SynValidationErrorMessage(valueString + " is greater than the maxInclusive of " + getMaxInclusive()));
			return false;
		}
		if (Double.isInfinite(maxValue) == false && Double.isNaN(maxValue) == false && dValue < minValue) {
			setValidationMessage(new SynValidationErrorMessage(valueString + " is less than the MinInclusive of " + getMinInclusive()));
			return false;
		}
		/*
		 * TODO: must expand to cover totalDigits and fractionDigits
		 */
		return true;
	}

	protected boolean isEnumValid(Object value) {
		if (false == getEnumerations().contains(value)) {
			setValidationMessage(new SynValidationErrorMessage(value.toString() + " is not a valid enum"));
		}
		return true;
	}

	/**
	 * @return Returns the patterReadableDescription.
	 */
	public String getReadablePatternDescription() {
		return readablePatternDescription;
	}

	/**
	 * @param patterReadableDescription The patterReadableDescription to set.
	 */
	public void setReadablePatternDescription(String patterReadableDescription) {
		this.readablePatternDescription = patterReadableDescription;
	}

	// ====================================================================
	// Folowing are convenience delegations
	// ====================================================================

	/**
	 * @param enumeration
	 */
	public void addEnumeration(Object enumeration) {
		facetGroup.addEnumeration(enumeration);
	}

	/**
	 * @param facet
	 */
	public void addFacet(ISynXSDFacet facet) {
		facetGroup.addFacet(facet);
	}

	/**
	 * @return either an empty array list, or a list of valid enumeration values.
	 */
	public List<Object> getEnumerations(String name) {
		// ensure returning either an empty array list, or a list of valid enumeration values.
		List<Object> l;
		try {
			l = enumProvider.getEnumerations(name);
			if (null != l) {
				// Anand 2/10/09 - Commented out call to sort the property enumerations, we should show what the coder has set
				// Collections.sort(l);
			} else {
				l = new ArrayList<Object>(1);
			}

		} catch (Exception e) {
			l = new ArrayList<Object>(1);
		}
		return l;
	}

	public List<Object> getEnumerations() {
		try {
			return getEnumerations(getName());
		} catch (Exception e) {
			return new ArrayList<Object>(1);
		}
	}

	/**
	 * @param name
	 * @return
	 */
	public SynXSDFacet getFacet(String name) {
		return facetGroup.getFacet(name);
	}

	/**
	 * @return
	 */
	public List<ISynXSDFacet> getFacets() {
		return facetGroup.getFacets();
	}

	/**
	 * @return
	 */
	public int getFractionDigits() {
		return facetGroup.getFractionDigits();
	}

	/**
	 * @return
	 */
	public int getLength() {
		return facetGroup.getLength();
	}

	/**
	 * @return
	 */
	public String getMaxExclusive() {
		return facetGroup.getMaxExclusive();
	}

	/**
	 * @return
	 */
	public String getMaxInclusive() {
		return facetGroup.getMaxInclusive();
	}

	/**
	 * @return
	 */
	public int getMaxLength() {
		return facetGroup.getMaxLength();
	}

	/**
	 * @return
	 */
	public String getMinExclusive() {
		return facetGroup.getMinExclusive();
	}

	/**
	 * @return
	 */
	public String getMinInclusive() {
		return facetGroup.getMinInclusive();
	}

	/**
	 * @return
	 */
	public int getMinLength() {
		return facetGroup.getMinLength();
	}

	/**
	 * @return
	 */
	public String getPattern() {
		return facetGroup.getPattern();
	}

	/**
	 * @return
	 */
	public int getTotalDigits() {
		return facetGroup.getTotalDigits();
	}

	/**
	 * @return
	 */
	public boolean isNumeric() {
		return facetGroup.isNumeric();
	}

	/**
	 * @param enumeration
	 */
	public void removeEnumeration(Object enumeration) {
		facetGroup.removeEnumeration(enumeration);
	}

	/**
	 * @param facet
	 */
	public void removeFacet(ISynXSDFacet facet) {
		facetGroup.removeFacet(facet);
	}

	/**
	 * @param enumerations
	 */
	public void setEnumerations(List<Object> enumerations) {
		try {
			enumProvider.setEnumerations(getName(), enumerations);
		} catch (Exception e) {
		}
	}

	public void setEnumerations(ISynPropertyEnumProvider enumProvider) {
		this.enumProvider = enumProvider;
	}

	/**
	 * @param facets
	 */
	public void setFacets(List<ISynXSDFacet> facets) {
		facetGroup.setFacets(facets);
	}

	/**
	 * @param fractionDigits
	 */
	public void setFractionDigits(int fractionDigits) {
		facetGroup.setFractionDigits(fractionDigits);
	}

	/**
	 * @param length
	 */
	public void setLength(int length) {
		facetGroup.setLength(length);
	}

	/**
	 * @param maxExclusive
	 */
	public void setMaxExclusive(String maxExclusive) {
		facetGroup.setMaxExclusive(maxExclusive);
	}

	/**
	 * @param maxInclusive
	 */
	public void setMaxInclusive(String maxInclusive) {
		facetGroup.setMaxInclusive(maxInclusive);
	}

	/**
	 * @param maxLength
	 */
	public void setMaxLength(int maxLength) {
		facetGroup.setMaxLength(maxLength);
	}

	/**
	 * @param minExclusive
	 */
	public void setMinExclusive(String minExclusive) {
		facetGroup.setMinExclusive(minExclusive);
	}

	/**
	 * @param minInclusive
	 */
	public void setMinInclusive(String minInclusive) {
		facetGroup.setMinInclusive(minInclusive);
	}

	/**
	 * @param minLength
	 */
	public void setMinLength(int minLength) {
		facetGroup.setMinLength(minLength);
	}

	/**
	 * @param numeric
	 */
	public void setNumeric(boolean numeric) {
		facetGroup.setNumeric(numeric);
	}

	/**
	 * @param pattern
	 */
	public void setPattern(String pattern) {
		facetGroup.setPattern(pattern);
	}

	/**
	 * @param totalDigits
	 */
	public void setTotalDigits(int totalDigits) {
		facetGroup.setTotalDigits(totalDigits);
	}

	public Object cloneThis() throws Exception {
		SynXSDRestrictionSimpleTypeDefinition clone = new SynXSDRestrictionSimpleTypeDefinition();
		this.cloneThis(clone);
		return clone;
	}

	protected void cloneThis(SynXSDRestrictionSimpleTypeDefinition clone) throws Exception {
		super.cloneThis(clone);
		clone.readablePatternDescription = this.readablePatternDescription;
		if (this.facetGroup == null) {
			clone.facetGroup = null;
		} else {
			clone.facetGroup = (SynXSDFacetGroup) this.facetGroup.cloneThis();
		}
		if (this.enumProvider == null) {
			clone.enumProvider = null;
		} else {
			clone.enumProvider = (ISynPropertyEnumProvider) this.enumProvider.cloneThis();
		}
		if (this.base == null) {
			clone.base = null;
		} else {
			clone.base = (ISynXSDSimpleTypeDefinition) this.base.cloneThis();
		}
	}

	class DefaultEnumProvider implements ISynPropertyEnumProvider {

		private SynXSDRestrictionSimpleTypeDefinition parent;

		public DefaultEnumProvider(SynXSDRestrictionSimpleTypeDefinition parent) {
			this.parent = parent;
		}

		public List<Object> getEnumerations(String name) {
			return parent.facetGroup.getEnumerations();
		}

		public void addEnumeration(String name, Object o) {
			parent.facetGroup.addEnumeration(o);
		}

		public void setEnumerations(String name, List<Object> l) {
			parent.facetGroup.setEnumerations(l);
		}

		public void removeEnumeration(String name, Object o) {
			parent.facetGroup.removeEnumeration(o);
		}

		public Object cloneThis() throws Exception {
			DefaultEnumProvider clone = new DefaultEnumProvider(this.parent);
			return clone;
		}
	}
}
