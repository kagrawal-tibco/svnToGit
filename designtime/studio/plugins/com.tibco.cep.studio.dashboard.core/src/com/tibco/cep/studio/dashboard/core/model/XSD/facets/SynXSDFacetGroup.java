package com.tibco.cep.studio.dashboard.core.model.XSD.facets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDFacet;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSimpleTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.providers.ISynXSDFacetsProvider;

/**
 * @
 *  
 */
public class SynXSDFacetGroup implements ISynXSDFacetsProvider {

    /**
     * Each implementation of this restriction type may contain a List of
     * available Facets.
     * 
     * The List is provided as information-only in this class and it is up to
     * the caller of this class to determine what to do with it.
     * 
     * An example is when presenting the users with the possible properties to
     * set in this restriction; the list can be used to determine which facets
     * are available.
     */
    private Map<String, ISynXSDFacet> facetsMap ;

    public SynXSDFacetGroup() {

    }

    /**
     *  
     */
    public SynXSDFacetGroup(Class<?> primitiveClass) {
        super();
        /**
         * The factory will fill the facets Map with the facets available to
         * this restriction determined by the primitive class given
         */
        SynXSDFacetFactory.getFacets(getFacetsMap(), primitiveClass);

    }

    /**
     *  
     */
    public SynXSDFacetGroup(ISynXSDSimpleTypeDefinition base, boolean allowsNegative) {
        super();
        /**
         * The factory will fill the facets Map with the facets available to
         * this restriction determined by the simple type given
         */
        SynXSDFacetFactory.getFacets(getFacetsMap(), base, allowsNegative);

    }
    /**
     *  
     */
    public SynXSDFacetGroup(ISynXSDSimpleTypeDefinition base) {
        this(base,false);
    }
    /**
     * @return Returns the facets.
     */
    public List<ISynXSDFacet> getFacets() {
        return new ArrayList<ISynXSDFacet>(facetsMap.values());
    }

    public void setFacets(List<ISynXSDFacet> facetsList) {
        for (Iterator<ISynXSDFacet> iter = facetsList.iterator(); iter.hasNext();) {
            ISynXSDFacet facet = iter.next();
            facetsMap.put(facet.getName(), facet);
        }
    }

    /**
     * @param facets
     *            The facet to add.
     */
    public void addFacet(ISynXSDFacet facet) {
        if (null == facetsMap) {
            facetsMap = new HashMap<String,ISynXSDFacet>();
        }
        facetsMap.put(facet.getName(), facet);
    }

    /**
     * @param facets
     *            The facet to remove.
     */
    public void removeFacet(ISynXSDFacet facet) {
        facetsMap.remove(facet.getName());
    }

    public Object clone() throws CloneNotSupportedException {
        SynXSDFacetGroup rClone = new SynXSDFacetGroup();
        rClone.setFacets(getFacets());
        return rClone;
    }

    //============================================================
    // Following are convenience methods for accessing facets
    //============================================================

    /**
     * Return a particular facet by the name given
     * 
     * @param name
     * @return
     * 
     * @see com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDFacet
     */
    public SynXSDFacet getFacet(String name) {
        SynXSDFacet facet = (SynXSDFacet) facetsMap.get(name);
        if (null == facet) {
            /*
             * This guarantees to return a facet with an empty value so that
             * callers do not have to make independent checks on whether the
             * facet is available before calling get/setValue(...)
             */
                        return new SynXSDFacet(name,"");
            //            throw new SynInvalidPropertyKeyException(name + " is not a
            // supported facet in this type");
        }

        return (SynXSDFacet) facetsMap.get(name);
    }

    /**
     * @return Returns the enumeration.
     */
    @SuppressWarnings("unchecked")
	public List<Object> getEnumerations() {
        SynXSDFacet facet = getFacet(ISynXSDFacet.CONSTRAINT_ENUMERATION);
        if(null != facet && facet.getValue() instanceof List) {
            return (List<Object>)facet.getValue();
        }
        return null;
    }

    /**
     * Delegating to the ISynXSDFacet If this facet is not an enumeration do
     * nothing
     * 
     * @param enumValue
     */
    public void addEnumeration(Object enumValue) {
        getFacet(ISynXSDFacet.CONSTRAINT_ENUMERATION).addEnumeration(enumValue);
    }

    /**
     * Delegating to the ISynXSDFacet If this facet is not an enumeration do
     * nothing
     * 
     * @param enumValue
     */
    public void removeEnumeration(Object enumValue) {
        getFacet(ISynXSDFacet.CONSTRAINT_ENUMERATION).removeEnumeration(enumValue);
    }

    /**
     * @param enumeration
     *            The enumeration to set.
     */
    public void setEnumerations(List<Object> enumeration) {
        getFacet(ISynXSDFacet.CONSTRAINT_ENUMERATION).setValue(enumeration);
    }

    /**
     * @return Returns the fractionDigits.
     */
    public int getFractionDigits() {
        return Integer.parseInt(getFacet(ISynXSDFacet.CONSTRAINT_FRACTION_DIGITS).toString());
    }

    /**
     * @param fractionDigits
     *            The fractionDigits to set.
     */
    public void setFractionDigits(int fractionDigits) {
        getFacet(ISynXSDFacet.CONSTRAINT_FRACTION_DIGITS).setValue(fractionDigits);
    }

    /**
     * @return Returns the length.
     */
    public int getLength() {
        return Integer.parseInt(getFacet(ISynXSDFacet.CONSTRAINT_LEGTH).toString());
    }

    /**
     * @param length
     *            The length to set.
     */
    public void setLength(int length) {
        getFacet(ISynXSDFacet.CONSTRAINT_LEGTH).setValue(length);
    }

    /**
     * @return Returns the maxExclusive.
     */
    public String getMaxExclusive() {
        
        SynXSDFacet facet = getFacet(ISynXSDFacet.CONSTRAINT_MAX_EXCLUSIVE);
        
        if(null == facet || facet.toString().length()<1) {
            return "0";
        }
        return facet.toString();
    }

    /**
     * @param maxExclusive
     *            The maxExclusive to set.
     */
    public void setMaxExclusive(String maxExclusive) {
        getFacet(ISynXSDFacet.CONSTRAINT_MAX_EXCLUSIVE).setValue(maxExclusive);
    }

    /**
     * @return Returns the maxInclusive.
     */
    public String getMaxInclusive() {
        return getFacet(ISynXSDFacet.CONSTRAINT_MAX_INCLUSIVE).toString();
    }

    /**
     * @param maxInclusive
     *            The maxInclusive to set.
     */
    public void setMaxInclusive(String maxInclusive) {
        getFacet(ISynXSDFacet.CONSTRAINT_MAX_INCLUSIVE).setValue(maxInclusive);
    }

    /**
     * @return Returns the maxLength.
     */
    public int getMaxLength() {
        return Integer.parseInt(getFacet(ISynXSDFacet.CONSTRAINT_MAX_LENGTH).toString());
    }

    /**
     * @param maxLength
     *            The maxLength to set.
     */
    public void setMaxLength(int maxLength) {
        getFacet(ISynXSDFacet.CONSTRAINT_MAX_LENGTH).setValue(maxLength);
    }

    /**
     * @return Returns the minExclusive.
     */
    public String getMinExclusive() {
        return getFacet(ISynXSDFacet.CONSTRAINT_MIN_EXCLUSIVE).toString();
    }

    /**
     * @param minExclusive
     *            The minExclusive to set.
     */
    public void setMinExclusive(String minExclusive) {
        getFacet(ISynXSDFacet.CONSTRAINT_MIN_EXCLUSIVE).setValue(minExclusive);
    }

    /**
     * @return Returns the minInclusive.
     */
    public String getMinInclusive() {
        return getFacet(ISynXSDFacet.CONSTRAINT_MIN_INCLUSIVE).toString();
    }

    /**
     * @param minInclusive
     *            The minInclusive to set.
     */
    public void setMinInclusive(String minInclusive) {
        getFacet(ISynXSDFacet.CONSTRAINT_MIN_INCLUSIVE).setValue(minInclusive);
    }

    /**
     * @return Returns the minLength.
     */
    public int getMinLength() {
        return Integer.parseInt(getFacet(ISynXSDFacet.CONSTRAINT_MIN_LENGTH).toString());
    }

    /**
     * @param minLength
     *            The minLength to set.
     */
    public void setMinLength(int minLength) {
        getFacet(ISynXSDFacet.CONSTRAINT_MIN_LENGTH).setValue(minLength);
    }

    /**
     * @return Returns the pattern.
     */
    public String getPattern() {
        return getFacet(ISynXSDFacet.CONSTRAINT_PATTERN).toString();
    }

    /**
     * @param pattern
     *            The pattern to set.
     */
    public void setPattern(String pattern) {
        getFacet(ISynXSDFacet.CONSTRAINT_PATTERN).setValue(pattern);
    }

    /**
     * @return Returns the totalDigits.
     */
    public int getTotalDigits() {
        return Integer.parseInt(getFacet(ISynXSDFacet.CONSTRAINT_TOTAL_DIGITS).toString());
    }

    /**
     * @param totalDigits
     *            The totalDigits to set.
     */
    public void setTotalDigits(int totalDigits) {
        getFacet(ISynXSDFacet.CONSTRAINT_TOTAL_DIGITS).setValue(totalDigits);
    }

    public boolean isNumeric() {
        return getFacet(ISynXSDFacet.FUNDAMENTAL_IS_NUMERIC).toString().equalsIgnoreCase("true");
    }
    /**
     * @param totalDigits
     *            The totalDigits to set.
     */
    public void setNumeric(boolean value) {
        getFacet(ISynXSDFacet.FUNDAMENTAL_IS_NUMERIC).setValue(value);
    }

    public Map<String, ISynXSDFacet> getFacetsMap() {
        if(null == facetsMap) {
            facetsMap = new HashMap<String, ISynXSDFacet>();
        }
        
        return facetsMap;
    }
    public void setFacetsMap(Map<String, ISynXSDFacet> facetsMap) {
        this.facetsMap = facetsMap;
    }
    
    public Object cloneThis() {
    	SynXSDFacetGroup clone = new SynXSDFacetGroup();
    	clone.facetsMap = new HashMap<String, ISynXSDFacet>();
    	Iterator<String> iterator = this.facetsMap.keySet().iterator();
    	while ( iterator.hasNext() ) {
    		String key = iterator.next();
    		clone.facetsMap.put(key, this.facetsMap.get(key));
    	}
    	return clone;
    }
}
