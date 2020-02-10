package com.tibco.cep.runtime.model;

import java.util.Calendar;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomBoolean;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.PropertyAtomDouble;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.PropertyAtomString;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;


/**
 * 
 * @author bgokhale
 * in some situations, we need a way to represent null values (dbconcepts, for example)
 * This class holds default values that are interpreted as "nulls" 
 * 
 * Values other than the ones below can be specified, by setting corresponding system properties
 * 
 * These values are typically set when a xs:nil while converting XML to a concept
 * If a xs:nil is encountered on an element, the corresponding "null" values are set on the 
 * instance. 
 * 
 * 
 * 
 */
public class PropertyNullValues {
	
	private static final String DEFAULT_NULL_INT = String.valueOf(Integer.MIN_VALUE);
	private static final String DEFAULT_NULL_LONG = String.valueOf(Long.MIN_VALUE);
	private static final String DEFAULT_NULL_DOUBLE = String.valueOf(Double.MIN_VALUE);
    private static final String DEFAULT_NULL_BOOLEAN = String.valueOf(Boolean.FALSE);
	
	private static String NULL_INT_STR;
	private static String NULL_LONG_STR;
	private static String NULL_DOUBLE_STR;
	private static String NULL_DATETIME_STR = null;
	private static String NULL_BOOLEAN_STR = "false";
	
	private static String NULL_STRING = null;
	private static int NULL_INT = Integer.MIN_VALUE;
	private static long NULL_LONG = Long.MIN_VALUE;
	private static double NULL_DOUBLE = Double.MIN_VALUE;
	private static Calendar NULL_DATETIME = null;
	private static boolean NULL_BOOLEAN = false;
    
    private static boolean BOOLEAN_NULL_VALUE_SET = false;
	
	static {

		NULL_INT_STR = System.getProperty("tibco.be.property.int.null.value", DEFAULT_NULL_INT);
		NULL_LONG_STR = System.getProperty("tibco.be.property.long.null.value", DEFAULT_NULL_LONG);
		NULL_DOUBLE_STR = System.getProperty("tibco.be.property.double.null.value", DEFAULT_NULL_DOUBLE);
        NULL_BOOLEAN_STR = System.getProperty("tibco.be.property.boolean.null.value");
        if(NULL_BOOLEAN_STR == null) {
            NULL_BOOLEAN_STR = DEFAULT_NULL_BOOLEAN;
        } else {
            BOOLEAN_NULL_VALUE_SET = true;
        }
		try {
			NULL_INT = Integer.valueOf(NULL_INT_STR);
		} catch (Exception e){}
		try {
			NULL_LONG = Long.valueOf(NULL_LONG_STR);
		}catch (Exception e){}
		try {
			NULL_DOUBLE = Double.valueOf(NULL_DOUBLE_STR);
		}catch(Exception e){}
        try {
            NULL_BOOLEAN = Boolean.valueOf(NULL_BOOLEAN_STR);
        }catch(Exception e){}
		
	}
	
	public static String getNullString(Concept cept) {
		return ((ConceptImpl)cept).treatNullValues() ? NULL_STRING : null;
	}
	public static int getNullInt(Concept cept) {
		return ((ConceptImpl)cept).treatNullValues() ? NULL_INT : 0;
	}
	public static double getNullDouble(Concept cept) {
		return ((ConceptImpl)cept).treatNullValues() ? NULL_DOUBLE : 0.0D;
	}
	public static long getNullLong(Concept cept) {
		return ((ConceptImpl)cept).treatNullValues() ? NULL_LONG : 0L;
	}
	public static Calendar getNullDateTime(Concept cept) {
		return ((ConceptImpl)cept).treatNullValues() ? NULL_DATETIME : null;
	}
	public static boolean getNullBoolean(Concept cept) {
		return ((ConceptImpl)cept).treatNullValues() ? NULL_BOOLEAN : false;
	}
	
	public static String getNullValue(PropertyAtom p) {
    	boolean treatNullValues = ((ConceptImpl)p.getParent()).treatNullValues();
		if (p instanceof PropertyAtomString){
			return treatNullValues ? NULL_STRING : null;
		} else if (p instanceof PropertyAtomInt){
			return treatNullValues ? NULL_INT_STR : "0";
		} else if (p instanceof PropertyAtomLong){
			return treatNullValues ? NULL_LONG_STR : "0L";
		} else if (p instanceof PropertyAtomDouble){
			return treatNullValues ? NULL_DOUBLE_STR : "0.0D";
		} else if (p instanceof PropertyAtomDateTime){
			return treatNullValues ? NULL_DATETIME_STR : null;
		} else if (p instanceof PropertyAtomBoolean){
			return treatNullValues ? NULL_BOOLEAN_STR : "false";
		}
		
		return null;
	}

	public static boolean isPropertyValueNull(PropertyAtom pa) {
		if (pa == null || (pa != null && pa.getValue() == null)) {
			return true;
		}
		if ( ! ((ConceptImpl)pa.getParent()).treatNullValues()) {
			return false;
		}
		Object val = pa.getValue();
		if (val instanceof String){
			return NULL_STRING != null ? val.equals(NULL_STRING) : false;
		} else if (val instanceof Integer){
			return ((Integer)val).intValue() == NULL_INT;
		} else if (val instanceof Long){
			return ((Long)val).longValue() == NULL_LONG;
		} else if (val instanceof Double){
			return ((Double)val).doubleValue() == NULL_DOUBLE;
		} else if (val instanceof Calendar){
			return NULL_DATETIME != null ? val.equals(NULL_DATETIME) : false;
		} else if (val instanceof Boolean && BOOLEAN_NULL_VALUE_SET){
			return ((Boolean)val).booleanValue() == NULL_BOOLEAN;
		} else {
			return false;
		}
	}
}
