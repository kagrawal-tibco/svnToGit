package com.tibco.be.model.types.xsd;

import java.util.Date;

import com.tibco.be.model.types.DefaultTypeRegistry;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeRegistryRegistry;
import com.tibco.be.model.types.TypeRenderer;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.values.XsBoolean;
import com.tibco.xml.data.primitive.values.XsDate;
import com.tibco.xml.data.primitive.values.XsDateTime;
import com.tibco.xml.data.primitive.values.XsDouble;
import com.tibco.xml.data.primitive.values.XsFloat;
import com.tibco.xml.data.primitive.values.XsInteger;
import com.tibco.xml.data.primitive.values.XsString;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 18, 2004
 * Time: 8:35:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class XSDTypeRegistry extends DefaultTypeRegistry{
    final static ExpandedName registryName=ExpandedName.makeName("xsd");
    final static XSDTypeRegistry INSTANCE= new XSDTypeRegistry(registryName);

    /**
     *
     * @param registryName
     */
    public XSDTypeRegistry(ExpandedName registryName) {
        super(registryName);    //To change body of overridden methods use File | Settings | File Templates.
        BooleanConverter.register(this);
        StringConverter.register(this);
        DecimalConverter.register(this);
        DoubleConverter.register(this);
        ByteConverter.register(this);
        FloatConverter.register(this);
        LongConverter.register(this);
        ShortConverter.register(this);
        IntConverter.register(this);
        DatetimeConverter.register(this);
        DateConverter.register(this);
        TypeRegistryRegistry.registerRegistry(registryName,this);
    }

    /***
     *
     * @return
     */
    final public static XSDTypeRegistry getInstance() {
        return INSTANCE;
    }


//    public static void main (String args []) {
//        XSDTypeRegistry registry = XSDTypeRegistry.getInstance();
//
//        /** Convert XsBoolean to java type */
//        try {
//            // test boolean
//            testBoolean(registry);
//            testInteger(registry);
//            testDateTime(registry);
//            testDate(registry);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    static void testBoolean (XSDTypeRegistry registry) throws Exception{
        TypeConverter tc= registry.nativeToForeign(XsBoolean.class,  Boolean.class);


        Boolean b= (Boolean) tc.convertSimpleType("true");
        System.out.println("Test1: Returned Boolean==" + b);
        b = (Boolean) tc.convertSimpleType(XsBoolean.TRUE);
        System.out.println("Test2: Returned Boolean==" + b);

        b= (Boolean) tc.convertSimpleType("false");
        System.out.println("Test3: Returned Boolean==" + b);
        b = (Boolean) tc.convertSimpleType(XsBoolean.FALSE);
        System.out.println("Test4: Returned Boolean==" + b);

        b= (Boolean) tc.convertSimpleType("fff");
        System.out.println("Test5: Returned Boolean==" + b);

        b= (Boolean) tc.convertSimpleType("121212");
        System.out.println("Test5: Returned Boolean==" + b);

        // Reverse Direction
        TypeRenderer tr= registry.foreignToNative(XsBoolean.class,  Boolean.class);
        Object o= tr.convertToTypedValue(Boolean.FALSE);
        System.out.println("Test6: Returned Object==" + o + ", class=" + o.getClass());

        o= tr.convertToTypedValue(Boolean.TRUE);
        System.out.println("Test7: Returned Object==" + o + ", class=" + o.getClass());

        o= tr.convertToTypedValue("1");
        System.out.println("Test8: Returned Object==" + o + ", class=" + o.getClass());

        o= tr.convertToTypedValue(new Integer(1));
        System.out.println("Test9: Returned Object==" + o + ", class=" + o.getClass());

        /**
        o= tr.convertToTypedValue("true");
        System.out.println("Test8: Returned Object==" + o + ", class=" + o.getClass());

        o= tr.convertToTypedValue("True");
        System.out.println("Test9: Returned Object==" + o + ", class=" + o.getClass());

        o= tr.convertToTypedValue("TRUE");
        System.out.println("Test10: Returned Object==" + o + ", class=" + o.getClass());
        **/

    }

    static void testInteger (XSDTypeRegistry registry) throws Exception{
        TypeConverter tc= registry.nativeToForeign(XsInteger.class,  Integer.class);


        Integer b= (Integer) tc.convertSimpleType("-1233");
        System.out.println("Test1: Returned Integer==" + b);

        b= (Integer) tc.convertSimpleType(new XsDouble(1233.12));
        System.out.println("Test2: Returned Integer==" + b);

        b= (Integer) tc.convertSimpleType(new XsInteger(1233));
        System.out.println("Test3: Returned Integer==" + b);

        b= (Integer) tc.convertSimpleType(new XsFloat(1233));
        System.out.println("Test4: Returned Integer==" + b);

        b= (Integer) tc.convertSimpleType(new XsString("1233"));
        System.out.println("Test5: Returned Integer==" + b);

        b= (Integer) tc.convertSimpleType(XsBoolean.TRUE);
        System.out.println("Test6: Returned Integer==" + b);

        // Reverse Direction
        TypeRenderer tr= registry.foreignToNative(XsInteger.class,  Integer.class);
        Object o= tr.convertToTypedValue("1234");
        System.out.println("Test7: Returned Object==" + o + ", class=" + o.getClass());

        o= tr.convertToTypedValue(new Integer(121221));
        System.out.println("Test8: Returned Object==" + o + ", class=" + o.getClass());

        o= tr.convertToTypedValue(new Double(1234.00));
        System.out.println("Test9: Returned Object==" + o + ", class=" + o.getClass());

        o= tr.convertToTypedValue(new Float(1234.00));
        System.out.println("Test10: Returned Object==" + o + ", class=" + o.getClass());

        o= tr.convertToTypedValue(new Long(12121212L));
        System.out.println("Test10: Returned Object==" + o + ", class=" + o.getClass());

    }


    static void testDateTime (XSDTypeRegistry registry) throws Exception{
        TypeConverter tc= registry.nativeToForeign(XsDateTime.class,  Date.class);

        TypeRenderer tr= registry.foreignToNative(XsDateTime.class, Date.class);

        Object o= tr.convertToTypedValue(new Date());
        System.out.println("Test1: Returned Datetime==" + o + ", class=" + o.getClass());

        Date d= (Date) tc.convertSimpleType("2004-09-18T09:42:33.439");
        System.out.println("Test2: Returned Datetime==" + d);

        d= (Date) tc.convertSimpleType("2004-09-18T12:00:00");
        System.out.println("Test3: Returned Datetime==" + d);

    }

    static void testDate (XSDTypeRegistry registry) throws Exception{
        TypeConverter tc= registry.nativeToForeign(XsDate.class,  Date.class);

        TypeRenderer tr= registry.foreignToNative(XsDate.class, Date.class);

        Object o= tr.convertToTypedValue(new Date());
        System.out.println("Test1: Returned Date==" + o + ", class=" + o.getClass());

        Date d= (Date) tc.convertSimpleType("2004-09-18");
        System.out.println("Test2: Returned Date==" + d);

        d= (Date) tc.convertSimpleType("2004-09-18");
        System.out.println("Test3: Returned Date==" + d);
    }


}
