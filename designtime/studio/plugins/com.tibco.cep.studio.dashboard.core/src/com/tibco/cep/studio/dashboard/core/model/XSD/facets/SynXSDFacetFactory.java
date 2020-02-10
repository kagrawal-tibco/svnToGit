package com.tibco.cep.studio.dashboard.core.model.XSD.facets;

import java.util.ArrayList;
import java.util.Map;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAtomicSimpleTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDFacet;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSimpleTypeDefinition;
import com.tibco.cep.studio.dashboard.core.util.BasicValidations;

/**
 * @
 *  
 */
public class SynXSDFacetFactory {

    /**
     * A factory method for populating the facets of a restriction per the java
     * primitive type with alloweNegative defaults to false
     * 
     * @param restriction
     * @param baseClass
     */
    
    public static final void getFacets(Map<String, ISynXSDFacet> facetMap, Class<?> primitiveClass) {
        getFacets(facetMap,primitiveClass,false);
    }
    
    /**
     * A factory method for populating the facets of a restriction per the java
     * primitive type
     * 
     * @param facetMap
     * @param primitiveClass
     * @param allowNegative
     */
    public static final void getFacets(Map<String, ISynXSDFacet> facetMap, Class<?> primitiveClass, boolean allowNegative) {

        /*
         * Note: The fully qualified prefix of 'java.lang.' is used to ensure we
         * are given the java primitives classes and not a user-defined class by
         * the same name
         */

        String primitiveClassName = primitiveClass.getName();

        if (primitiveClassName.equals(java.lang.String.class.getName())) {
            facetMap.put(SynXSDFacet.CONSTRAINT_LEGTH, new SynXSDFacet(SynXSDFacet.CONSTRAINT_LEGTH, -1));
            facetMap.put(SynXSDFacet.CONSTRAINT_MIN_LENGTH, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MIN_LENGTH, 0));
            facetMap.put(SynXSDFacet.CONSTRAINT_MAX_LENGTH, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MAX_LENGTH, BasicValidations.MAX_STRNG_LENGTH));
            facetMap.put(SynXSDFacet.CONSTRAINT_PATTERN, new SynXSDFacet(SynXSDFacet.CONSTRAINT_PATTERN, ""));
            facetMap.put(SynXSDFacet.CONSTRAINT_ENUMERATION, new SynXSDFacet(SynXSDFacet.CONSTRAINT_ENUMERATION, new ArrayList<Object>()));
            facetMap.put(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, new SynXSDFacet(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, "false"));
        }
        else if (primitiveClassName.equals(java.lang.Character.class.getName()) || primitiveClassName.equals(char.class.getName())) {
            facetMap.put(SynXSDFacet.CONSTRAINT_LEGTH, new SynXSDFacet(SynXSDFacet.CONSTRAINT_LEGTH, 1));
            facetMap.put(SynXSDFacet.CONSTRAINT_MIN_LENGTH, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MIN_LENGTH, 0));
            facetMap.put(SynXSDFacet.CONSTRAINT_MAX_LENGTH, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MAX_LENGTH, 1));
            facetMap.put(SynXSDFacet.CONSTRAINT_PATTERN, new SynXSDFacet(SynXSDFacet.CONSTRAINT_PATTERN, ""));
            facetMap.put(SynXSDFacet.CONSTRAINT_ENUMERATION, new SynXSDFacet(SynXSDFacet.CONSTRAINT_ENUMERATION, new ArrayList<Object>()));
            facetMap.put(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, new SynXSDFacet(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, "false"));
        }
        else if (primitiveClassName.equals(java.lang.Boolean.class.getName()) || primitiveClassName.equals(boolean.class.getName())) {
            facetMap.put(SynXSDFacet.CONSTRAINT_LEGTH, new SynXSDFacet(SynXSDFacet.CONSTRAINT_PATTERN, "(?i)true|(?i)false"));
            facetMap.put(SynXSDFacet.CONSTRAINT_LEGTH, new SynXSDFacet(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, "false"));
        }
        else if (primitiveClassName.equals(java.lang.Float.class.getName()) || primitiveClassName.equals(float.class.getName())) {
            facetMap.put(SynXSDFacet.CONSTRAINT_PATTERN, new SynXSDFacet(SynXSDFacet.CONSTRAINT_PATTERN, "[\\-+]?[0-9.]?[0-9]+"));
            facetMap.put(SynXSDFacet.CONSTRAINT_PATTERN, new SynXSDFacet(SynXSDFacet.CONSTRAINT_PATTERN, new ArrayList<Object>()));
            facetMap.put(SynXSDFacet.CONSTRAINT_MAX_INCLUSIVE, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MAX_INCLUSIVE, java.lang.Float.POSITIVE_INFINITY));
            facetMap.put(SynXSDFacet.CONSTRAINT_MIN_INCLUSIVE, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MIN_INCLUSIVE, true == allowNegative ? java.lang.Float.NEGATIVE_INFINITY : 0));
            facetMap.put(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, new SynXSDFacet(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, "true"));
        }
        else if (primitiveClassName.equals(java.lang.Double.class.getName()) || primitiveClassName.equals(double.class.getName())) {
            facetMap.put(SynXSDFacet.CONSTRAINT_PATTERN, new SynXSDFacet(SynXSDFacet.CONSTRAINT_PATTERN, "[\\-+]?[0-9.]?[0-9]+"));
            facetMap.put(SynXSDFacet.CONSTRAINT_ENUMERATION, new SynXSDFacet(SynXSDFacet.CONSTRAINT_ENUMERATION, new ArrayList<Object>()));
            facetMap.put(SynXSDFacet.CONSTRAINT_MAX_INCLUSIVE, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MAX_INCLUSIVE, java.lang.Double.POSITIVE_INFINITY));
            facetMap.put(SynXSDFacet.CONSTRAINT_MIN_INCLUSIVE, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MIN_INCLUSIVE, true == allowNegative ? java.lang.Double.NEGATIVE_INFINITY : 0));
            facetMap.put(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, new SynXSDFacet(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, "true"));
        }

        else if (primitiveClassName.equals(java.lang.Integer.class.getName()) || primitiveClassName.equals(int.class.getName())) {
            facetMap.put(SynXSDFacet.CONSTRAINT_PATTERN, new SynXSDFacet(SynXSDFacet.CONSTRAINT_PATTERN, "[\\-+]?[0-9]+"));
            facetMap.put(SynXSDFacet.CONSTRAINT_ENUMERATION, new SynXSDFacet(SynXSDFacet.CONSTRAINT_ENUMERATION, new ArrayList<Object>()));
            facetMap.put(SynXSDFacet.CONSTRAINT_MAX_INCLUSIVE, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MAX_INCLUSIVE, java.lang.Integer.MAX_VALUE));
            facetMap.put(SynXSDFacet.CONSTRAINT_MIN_INCLUSIVE, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MIN_INCLUSIVE, true == allowNegative ? java.lang.Integer.MIN_VALUE : 0));
            facetMap.put(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, new SynXSDFacet(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, "true"));
        }
        else if (primitiveClassName.equals(java.lang.Long.class.getName()) || primitiveClassName.equals(long.class.getName())) {
            facetMap.put(SynXSDFacet.CONSTRAINT_PATTERN, new SynXSDFacet(SynXSDFacet.CONSTRAINT_PATTERN, "[\\-+]?[0-9]+"));
            facetMap.put(SynXSDFacet.CONSTRAINT_ENUMERATION, new SynXSDFacet(SynXSDFacet.CONSTRAINT_ENUMERATION, new ArrayList<Object>()));
            facetMap.put(SynXSDFacet.CONSTRAINT_MAX_INCLUSIVE, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MAX_INCLUSIVE, java.lang.Long.MAX_VALUE));
            facetMap.put(SynXSDFacet.CONSTRAINT_MIN_INCLUSIVE, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MIN_INCLUSIVE, true == allowNegative ? java.lang.Long.MIN_VALUE : 0));
            facetMap.put(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, new SynXSDFacet(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, "true"));
        }
        else if (primitiveClassName.equals(java.lang.Short.class.getName()) || primitiveClassName.equals(short.class.getName())) {
            facetMap.put(SynXSDFacet.CONSTRAINT_PATTERN, new SynXSDFacet(SynXSDFacet.CONSTRAINT_PATTERN, "[\\-+]?[0-9]+"));
            facetMap.put(SynXSDFacet.CONSTRAINT_ENUMERATION, new SynXSDFacet(SynXSDFacet.CONSTRAINT_ENUMERATION, new ArrayList<Object>()));
            facetMap.put(SynXSDFacet.CONSTRAINT_MAX_INCLUSIVE, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MAX_INCLUSIVE, java.lang.Short.MAX_VALUE));
            facetMap.put(SynXSDFacet.CONSTRAINT_MIN_INCLUSIVE, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MIN_INCLUSIVE, true == allowNegative ? java.lang.Short.MIN_VALUE : 0));
            facetMap.put(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, new SynXSDFacet(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, "true"));
        }
        else if (primitiveClassName.equals(java.lang.Byte.class.getName()) || primitiveClassName.equals(byte.class.getName())) {
            facetMap.put(SynXSDFacet.CONSTRAINT_PATTERN, new SynXSDFacet(SynXSDFacet.CONSTRAINT_PATTERN, "[\\-+]?[0-9]+"));
            facetMap.put(SynXSDFacet.CONSTRAINT_ENUMERATION, new SynXSDFacet(SynXSDFacet.CONSTRAINT_ENUMERATION, new ArrayList<Object>()));
            facetMap.put(SynXSDFacet.CONSTRAINT_MAX_INCLUSIVE, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MAX_INCLUSIVE, java.lang.Byte.MAX_VALUE));
            facetMap.put(SynXSDFacet.CONSTRAINT_MIN_INCLUSIVE, new SynXSDFacet(SynXSDFacet.CONSTRAINT_MIN_INCLUSIVE, true == allowNegative ? java.lang.Byte.MIN_VALUE : 0));
            facetMap.put(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, new SynXSDFacet(SynXSDFacet.FUNDAMENTAL_IS_NUMERIC, "true"));
        }

        else {
            throw new UnsupportedOperationException(primitiveClassName
                    + " is not a recognized class name for a java primitive.  This operation can not continue.");
        }
    }

    
    /**
     * A factory method for populating the facets of a restriction per the given
     * ISynXSDSimpleTypeDefinition with alloweNegative defaults to false
     * @param facetMap
     * @param base
     */
    public static final void getFacets(Map<String, ISynXSDFacet> facetMap, ISynXSDSimpleTypeDefinition base) {
        getFacets(facetMap,base,false);
    }

    /**
     * A factory method for populating the facets of a restriction per the given
     * ISynXSDSimpleTypeDefinition
     * @param facetMap
     * @param base
     * @param allowNegative
     */
    public static final void getFacets(Map<String, ISynXSDFacet> facetMap, ISynXSDSimpleTypeDefinition base,boolean allowNegative) {

        if (base instanceof ISynXSDAtomicSimpleTypeDefinition) {
            SynXSDFacetFactory.getFacets(facetMap, ((ISynXSDAtomicSimpleTypeDefinition) base).getJavaType(),allowNegative);
        }
        else {

            /*
             * Continue recusrsively until a java primitive is found. This is
             * because this implementation has java primitives at the root of
             * all ISynXSDSimpleTypeDefinition
             */
            SynXSDFacetFactory.getFacets(facetMap, base.getBase(),allowNegative);
        }
    }
}
