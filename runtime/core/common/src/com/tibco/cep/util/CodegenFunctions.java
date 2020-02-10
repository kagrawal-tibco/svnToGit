package com.tibco.cep.util;

import static com.tibco.be.parser.codegen.CompoundAssignment.DECR;
import static com.tibco.be.parser.codegen.CompoundAssignment.INCR;
import static com.tibco.be.parser.codegen.CompoundAssignment.MINUSEQ;
import static com.tibco.be.parser.codegen.CompoundAssignment.PCNTEQ;
import static com.tibco.be.parser.codegen.CompoundAssignment.PLUSEQ;
import static com.tibco.be.parser.codegen.CompoundAssignment.POST_DECR;
import static com.tibco.be.parser.codegen.CompoundAssignment.POST_INCR;
import static com.tibco.be.parser.codegen.CompoundAssignment.SLASHEQ;
import static com.tibco.be.parser.codegen.CompoundAssignment.STAREQ;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeRenderer;
import com.tibco.be.model.types.xsd.XSDTypeRegistry;
import com.tibco.be.parser.codegen.CompoundAssignment;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.NullContainedConcept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArrayBoolean;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyArrayDateTime;
import com.tibco.cep.runtime.model.element.PropertyArrayDouble;
import com.tibco.cep.runtime.model.element.PropertyArrayInt;
import com.tibco.cep.runtime.model.element.PropertyArrayLong;
import com.tibco.cep.runtime.model.element.PropertyArrayString;
import com.tibco.cep.runtime.model.element.PropertyAtomBoolean;
import com.tibco.cep.runtime.model.element.PropertyAtomConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.PropertyAtomDouble;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.PropertyAtomString;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.exception.BEException;
import com.tibco.cep.runtime.service.decision.impl.VRFImpl;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsBoolean;
import com.tibco.xml.data.primitive.values.XsDateTime;
import com.tibco.xml.data.primitive.values.XsDayTimeDuration;
import com.tibco.xml.data.primitive.values.XsDouble;
import com.tibco.xml.data.primitive.values.XsInt;
import com.tibco.xml.data.primitive.values.XsLong;
import com.tibco.xml.data.primitive.values.XsString;

/**
 * User: aamaya
 * Date: Jul 8, 2004
 * Time: 3:48:27 PM
 *
 * Functions used by generated code
 */
public class CodegenFunctions {
    private static final Integer dummyInteger = 0;

    public static boolean propertyEqualsNull(Property prop) {
        return prop == null;
    }

    public static boolean propertyEqualsNull(PropertyAtomString prop) {
        if(prop == null) return true;
        return stringEQ(prop.getString(), null);
    }

    public static boolean propertyEqualsNull(PropertyAtomDateTime prop) {
        if(prop == null) return true;
        return dateTimeEQ(prop.getDateTime(), null);
    }

    public static boolean propertyEqualsNull(PropertyAtomConcept prop) {
        if(prop == null) return true;
        return entityEQ(prop.getConcept(), null);
    }

    public static boolean entityEQ(Entity a, Entity b) {
        if(a == b) return true;
        if(a == null || b == null) {
            return a instanceof NullContainedConcept || b instanceof NullContainedConcept;
        }
        if(a instanceof NullContainedConcept && b instanceof NullContainedConcept) return true;
        return a.getId() == b.getId();
    }

    //Rather than a compare function that returns an integer (-1, 0, 1)
    //separate functions are used so that the proper result can be determined
    //when a string is compared to null
    public static boolean stringEQ(String a, String b) {
        if(a == b) return true;
        if(a == null || b == null) return false;
        return a.equals(b);
    }

    public static boolean stringGT(String a, String b) {
        if(a == null || b == null) return false;
        return a.compareTo(b) > 0;
    }

    public static boolean stringGE(String a, String b) {
        if(a == null || b == null) return false;
        return a.compareTo(b) >= 0;
    }

    public static boolean stringLT(String a, String b) {
        if(a == null || b == null) return false;
        return a.compareTo(b) < 0;
    }

    public static boolean stringLE(String a, String b) {
        if(a == null || b == null) return false;
        return a.compareTo(b) <= 0;
    }

    public static boolean dateTimeEQ(Calendar a, Calendar b) {
        //todo what is behavior of operators WRT timezone and locale
        if(a == b) return true;
        if(a == null || b == null) return false;
        return a.equals(b);
    }

    public static boolean dateTimeGT(Calendar a, Calendar b) {
        if(a == null || b == null) return false;
        return a.after(b);
    }

    public static boolean dateTimeGE(Calendar a, Calendar b) {
        if(a == null || b == null) return false;
        return a.after(b) || a.equals(b);
    }

    public static boolean dateTimeLT(Calendar a, Calendar b) {
        if(a == null || b == null) return false;
        return a.before(b);
    }

    public static boolean dateTimeLE(Calendar a, Calendar b) {
        if(a == null || b == null) return false;
        return a.before(b) || a.equals(b);
    }
    
    //when comparing with an object, these will be used,
    //otherwise the above ones will be used which avoids the 
    //expense of instanceof

    public static boolean entityEQ(Object a, Object b) {
        if(a == b) return true;
        if(a == null || b == null) {
            return a instanceof NullContainedConcept || b instanceof NullContainedConcept;
        }
        if(a instanceof NullContainedConcept && b instanceof NullContainedConcept) return true;
        if(!(a instanceof Entity && b instanceof Entity)) return false;
        return ((Entity)a).getId() == ((Entity)b).getId();
    }

    //Rather than a compare function that returns an integer (-1, 0, 1)
    //separate functions are used so that the proper result can be determined
    //when a string is compared to null
    public static boolean stringEQ(Object a, Object b) {
        if(a == b) return true;
        if(a == null || b == null) return false;
        if(!(a instanceof String && b instanceof String)) return false; 
        return a.equals(b);
    }

    public static boolean stringGT(Object a, Object b) {
        if(a == null || b == null) return false;
        if(!(a instanceof String && b instanceof String)) return false;
        return ((String)a).compareTo((String)b) > 0;
    }

    public static boolean stringGE(Object a, Object b) {
        if(a == null || b == null) return false;
        if(!(a instanceof String && b instanceof String)) return false;
        return ((String)a).compareTo((String)b) >= 0;
    }

    public static boolean stringLT(Object a, Object b) {
        if(a == null || b == null) return false;
        if(!(a instanceof String && b instanceof String)) return false;
        return ((String)a).compareTo((String)b) < 0;
    }

    public static boolean stringLE(Object a, Object b) {
        if(a == null || b == null) return false;
        if(!(a instanceof String && b instanceof String)) return false;
        return ((String)a).compareTo((String)b) <= 0;
    }

    public static boolean dateTimeEQ(Object a, Object b) {
        //todo what is behavior of operators WRT timezone and locale
        if(a == b) return true;
        if(a == null || b == null) return false;
        if(!(a instanceof Calendar && b instanceof Calendar)) return false;
        return ((Calendar)a).equals((Calendar)b);
    }

    public static boolean dateTimeGT(Object a, Object b) {
        if(a == null || b == null) return false;
        if(!(a instanceof Calendar && b instanceof Calendar)) return false;
        return ((Calendar)a).after((Calendar)b);
    }

    public static boolean dateTimeGE(Object a, Object b) {
        if(a == null || b == null) return false;
        if(!(a instanceof Calendar && b instanceof Calendar)) return false;
        return ((Calendar)a).after((Calendar)b) || ((Calendar)a).equals((Calendar)b);
    }

    public static boolean dateTimeLT(Object a, Object b) {
        if(a == null || b == null) return false;
        if(!(a instanceof Calendar && b instanceof Calendar)) return false;
        return ((Calendar)a).before((Calendar)b);
    }

    public static boolean dateTimeLE(Object a, Object b) {
        if(a == null || b == null) return false;
        if(!(a instanceof Calendar && b instanceof Calendar)) return false;
        return ((Calendar)a).before((Calendar)b) || ((Calendar)a).equals((Calendar)b);
    }

    
    public static boolean unbox(Boolean bool) {
        if(bool == null) return PropertyAtomBoolean.DEFAULT_VALUE;
        return bool.booleanValue();
    }

    public static double unbox(Double num) {
        if(num == null) return PropertyAtomDouble.DEFAULT_VALUE;
        return num.doubleValue();
    }

    public static int unbox(Integer num) {
        if(num == null) return PropertyAtomInt.DEFAULT_VALUE;
        return num.intValue();
    }

    public static long unbox(Long num) {
        if(num == null) return PropertyAtomLong.DEFAULT_VALUE;
        return num.longValue();
    }

    public static Object unbox(Object obj) {
        return obj;
    }

    public static Boolean box(boolean bool) {
        return (bool ? Boolean.TRUE : Boolean.FALSE);
    }

    public static Double box(double num) {
        return new Double(num);
    }

    public static Integer box(int num) {
        return new Integer(num);
    }

    public static Long box(long num) {
        return new Long(num);
    }
    
    public static Object box(Object obj) {
        return obj;
    }
    
    public static Double toDouble(Number num) {
        if(num == null) return null;
        return new Double(num.doubleValue());
    }

    public static Integer toInteger(Number num) {
        if(num == null) return null;
        return new Integer(num.intValue());
    }

    public static Long toLong(Number num) {
        if(num == null) return  null;
        return new Long(num.longValue());
    }
    
    public static String toString(Object o) {
        return o == null ? null : o.toString();
    }

    public static String toString(long l) {
        return String.valueOf(l);
    }

    public static String toString(int i) {
        return String.valueOf(i);
    }

    public static String toString(double d) {
        return String.valueOf(d);
    }

    public static String toString(boolean b) {
        return String.valueOf(b);
    }

    public static XmlTypedValue wrapForMapperInt(Integer wrap) {
        if(wrap == null) return null;
        return wrapForMapperInt(wrap.intValue());
    }
    public static XmlTypedValue wrapForMapperInt(int val) {
        return new XsInt(val);
    }
    public static XmlTypedValue wrapForMapperLong(Long wrap) {
        if(wrap == null) return null;
        return wrapForMapperLong(wrap.longValue());
    }
    public static XmlTypedValue wrapForMapperLong(long val) {
            return new XsLong(val);
    }
    public static XmlTypedValue wrapForMapperDouble(Double wrap) {
        if(wrap == null) return null;
        return wrapForMapperDouble(wrap.doubleValue());
    }
    public static XmlTypedValue wrapForMapperDouble(double val) {
        return new XsDouble(val);
    }
    public static XmlTypedValue wrapForMapperBoolean(Boolean wrap) {
        if(wrap == null) return null;
        return wrapForMapperBoolean(wrap.booleanValue());
    }
    public static XmlTypedValue wrapForMapperBoolean(boolean val) {
        return XsBoolean.create(val);
    }
    public static XmlTypedValue wrapForMapperString(String str) {
        if(str == null) return null;
        return new XsString(str);
    }
    private static TypeRenderer java2xsd_dt_conv= XSDTypeRegistry.getInstance().foreignToNative(XsDateTime.class, GregorianCalendar.class);
    private static BigDecimal bigDecimalZero = new BigDecimal(0.0);
    public static XmlTypedValue wrapForMapperDateTime(Calendar cal) {
        if(cal == null) return null;

        try {
            return java2xsd_dt_conv.convertToTypedValue(cal);
        } catch(ConversionException ce) {
            try {
                return java2xsd_dt_conv.convertToTypedValue(PropertyAtomDateTime.DEFAULT_VALUE);
            } catch(ConversionException e1) {
                Calendar dflt = PropertyAtomDateTime.DEFAULT_VALUE;
                return new XsDateTime(dflt.getTimeInMillis(), new XsDayTimeDuration(dflt.getTimeZone().getOffset(dflt.getTimeInMillis()), 0, bigDecimalZero));
            }
        }
    }

    public static void copyPrimitiveToPropertyArray(boolean[] from, PropertyArrayBoolean to) {
    //support for factory functions in generated Concepts
        for(int ii = 0; ii < from.length; ii++ ) {
            to.set(ii, from[ii]);
        }
    }

    public static void copyPrimitiveToPropertyArray(Concept[] from, PropertyArrayConceptReference to) {
        for(int ii = 0; ii < from.length; ii++ ) {
            to.set(ii, from[ii]);
        }
    }

    public static void copyPrimitiveToPropertyArray(ContainedConcept[] from, PropertyArrayContainedConcept to) {
        for(int ii = 0; ii < from.length; ii++ ) {
            to.set(ii, from[ii]);
        }
    }

    public static void copyPrimitiveToPropertyArray(Calendar[] from, PropertyArrayDateTime to) {
        for(int ii = 0; ii < from.length; ii++ ) {
            to.set(ii, from[ii]);
        }
    }

    public static void copyPrimitiveToPropertyArray(double[] from, PropertyArrayDouble to) {
        for(int ii = 0; ii < from.length; ii++ ) {
            to.set(ii, from[ii]);
        }
    }

    public static void copyPrimitiveToPropertyArray(int[] from, PropertyArrayInt to) {
        for(int ii = 0; ii < from.length; ii++ ) {
            to.set(ii, from[ii]);
        }
    }

    public static void copyPrimitiveToPropertyArray(long[] from, PropertyArrayLong to) {
        for(int ii = 0; ii < from.length; ii++ ) {
            to.set(ii, from[ii]);
        }
    }

    public static void copyPrimitiveToPropertyArray(String[] from, PropertyArrayString to) {
        for(int ii = 0; ii < from.length; ii++ ) {
            to.set(ii, from[ii]);
        }
    }

    public static int hashcode(boolean exp) {
        return new Boolean(exp).hashCode();
    }

    public static int hashcode(int exp) {
        return new Integer(exp).hashCode();
    }

    public static int hashcode(long exp) {
        return new Long(exp).hashCode();
    }

    public static int hashcode(double exp) {
        return new Double(exp).hashCode();
    }

    public static int hashcode(Object exp) {
        if(exp == null) return 0;
        return exp.hashCode();
    }
    
    public static RuntimeException BEExceptionAsRuntimeException(BEException exp) {
        if(exp == null) return null;
        else return exp.toException();
    }

    public static Class classForName(ClassLoader loader, String className) {
        try {
            return Class.forName(className, true, loader);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        }
    }
    
    //used by the default generated virtual rule function class
    public static VRFImpl getVRFDefaultImpl(String vrfName) {
        if(vrfName != null && vrfName.length() > 0) {
            ClassLoader cl = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            if(cl instanceof BEClassLoader) {
                return ((BEClassLoader)cl).getVRFRegistry().getVRFDefaultImpl(vrfName);
            }
        }
        return null;
    }

    public static int[] invokeVRFImpl(VRFImpl impl, Object[] args) {
    	return (int[]) impl.invoke(args);
    }
    
    public static Object preIncrement(Object o) {
        return compoundAssignment(o, dummyInteger, INCR);
    }
    public static Object preDecrement(Object o) {
        return compoundAssignment(o, dummyInteger, DECR);
    }
    public static Object postIncrement(Object o) {
        return compoundAssignment(o, dummyInteger, POST_INCR);
    }
    public static Object postDecrement(Object o) {
        return compoundAssignment(o, dummyInteger, POST_DECR);
    }
    public static Object addAssign(Object o, Object arg) {
        return compoundAssignment(o, arg, PLUSEQ);
    }
    public static Object subAssign(Object o, Object arg) {
        return compoundAssignment(o, arg, MINUSEQ);
    }
    public static Object multAssign(Object o, Object arg) {
        return compoundAssignment(o, arg, STAREQ);
    }
    public static Object divAssign(Object o, Object arg) {
        return compoundAssignment(o, arg, SLASHEQ);
    }
    public static Object modAssign(Object o, Object arg) {
        return compoundAssignment(o, arg, PCNTEQ);
    }

    public static void preIncrement(Event e, String prop) {
        compoundAssignment(e, prop, dummyInteger, INCR);
    }
    public static void preDecrement(Event e, String prop) {
        compoundAssignment(e, prop, dummyInteger, DECR);
    }
    public static void postIncrement(Event e, String prop) {
        compoundAssignment(e, prop, dummyInteger, POST_INCR);
    }
    public static void postDecrement(Event e, String prop) {
        compoundAssignment(e, prop, dummyInteger, POST_DECR);
    }
    public static void addAssign(Event e, String prop, Object arg) {
        compoundAssignment(e, prop, arg, PLUSEQ);
    }
    public static void subAssign(Event e, String prop, Object arg) {
        compoundAssignment(e, prop, arg, MINUSEQ);
    }
    public static void multAssign(Event e, String prop, Object arg) {
        compoundAssignment(e, prop, arg, STAREQ);
    }
    public static void divAssign(Event e, String prop, Object arg) {
        compoundAssignment(e, prop, arg, SLASHEQ);
    }
    public static void modAssign(Event e, String prop, Object arg) {
        compoundAssignment(e, prop, arg, PCNTEQ);
    }

    private static Object compoundAssignment(Object val, Object arg, CompoundAssignment op) {
        if(val instanceof PropertyAtomInt) {
            op.op((PropertyAtomInt) val, arg);
            return val;
        } else if (val instanceof PropertyAtomLong) {
            op.op((PropertyAtomLong) val, arg);
            return val;
        } else if (val instanceof PropertyAtomDouble) {
            op.op((PropertyAtomDouble) val, arg);
            return val;
        } else if (val instanceof PropertyAtomString) {
            op.op((PropertyAtomString) val, arg);
            return val;
        } else if (val instanceof Number) {
            if(arg instanceof String) {
                return op.op(String.valueOf(val), arg);
            } else if (val instanceof Integer) {
                return op.op((Integer)val, ((Number)arg).intValue());
            } else if (val instanceof Long) {
                return op.op((Long)val, ((Number)arg).longValue());
            } else if (val instanceof Double) {
                return op.op((Double)val, ((Number)arg).doubleValue());
            } else {
                assert false : "Unimplemented compound assignment for op " + op.name() + " on type " + val.getClass().getName();
                return val;
            }
        } else if (val instanceof String || (val == null && arg instanceof String)) {
                return op.op((String)val, arg);
        } else {
            assert false : "Unimplemented compound assignment for op " + op.name() + " on type " + (val == null ? null : val.getClass().getName());
            return val;
        }
    }

    private static void compoundAssignment(Event e, String prop, Object arg, CompoundAssignment op) {
        SimpleEvent se = (SimpleEvent)e;
        try {
            se.setProperty(prop, compoundAssignment(se.getProperty(prop), arg, op));
        } catch (Exception ex) {
            if(ex instanceof RuntimeException) throw (RuntimeException)ex;
            else throw new RuntimeException(ex);
        }
    }
}