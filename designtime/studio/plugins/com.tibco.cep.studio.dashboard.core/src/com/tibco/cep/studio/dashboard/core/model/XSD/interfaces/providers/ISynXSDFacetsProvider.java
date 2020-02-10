package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.providers;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDFacet;

/**
 * @ *
 */
public interface ISynXSDFacetsProvider {
	/**
	 * 
	 * @return List The List of available ISynXSDFacet's in this restriction
	 */
	public abstract List<ISynXSDFacet> getFacets();

	public abstract void setFacets(List<ISynXSDFacet> facets);

	public abstract void addFacet(ISynXSDFacet facet);

	public abstract void removeFacet(ISynXSDFacet facet);

	/**
	 * The exact length of the value
	 * 
	 * @return
	 */
	public abstract int getLength();

	public abstract void setLength(int length);

	/**
	 * The min length for a String
	 */
	public abstract int getMinLength();

	public abstract void setMinLength(int minLength);

	/**
	 * The max length for a String value '-1' means unbounded, default to 255
	 */
	public abstract int getMaxLength();

	public abstract void setMaxLength(int maxLength);

	/**
	 * The min value accepted as a value The imlementation uses a String to
	 * represent the min value for flexibility to support all numeric types
	 */
	public abstract String getMinInclusive();

	public abstract void setMinInclusive(String minInclusive);

	/**
	 * The Max value accepted as a value The imlementation uses a String to
	 * represent the max value for flexibility to support all numeric types
	 */
	public abstract String getMaxInclusive();

	public abstract void setMaxInclusive(String maxInclusive);

	/**
	 * The min value accepted as a value The imlementation uses a String to
	 * represent the min value for flexibility to support all numeric types
	 */
	public abstract String getMinExclusive();

	public abstract void setMinExclusive(String minExclusive);

	/**
	 * The Max value accepted as a value The imlementation uses a String to
	 * represent the max value for flexibility to support all numeric types
	 */
	public abstract String getMaxExclusive();

	public abstract void setMaxExclusive(String maxExclusive);

	/**
	 * An enumeration is a concrete list of values that is acceptable for this
	 * type.
	 * 
	 * A common use of this would be to provide a String list of textual days:
	 * Monday, Tuesday, Wednesday, etc...
	 */
	public abstract List<Object> getEnumerations();

	public abstract void setEnumerations(List<Object> enumerations);

	public abstract void addEnumeration(Object enumeration);

	public abstract void removeEnumeration(Object enumeration);

	/**
	 * The String representing a pattern that the value must conform to
	 * 
	 * @see java.util.regex.Pattern
	 */
	public abstract String getPattern();

	public abstract void setPattern(String pattern);

	/**
	 * 
	 * @return
	 */
	public abstract int getTotalDigits();

	public abstract void setTotalDigits(int totalDigits);

	/**
	 * 
	 * @return
	 */
	public abstract int getFractionDigits();

	public abstract void setFractionDigits(int fractionDigits);

	/**
	 * 
	 * @return
	 */
	public abstract boolean isNumeric();

	public abstract void setNumeric(boolean numeric);
}
