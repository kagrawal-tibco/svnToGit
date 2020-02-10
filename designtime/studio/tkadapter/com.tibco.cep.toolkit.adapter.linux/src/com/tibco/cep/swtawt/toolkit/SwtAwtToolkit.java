package com.tibco.cep.swtawt.toolkit;

import sun.awt.AppContext;
import sun.awt.X11.XToolkit;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ListResourceBundle;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Nov 18, 2009
 * Time: 5:47:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class SwtAwtToolkit extends XToolkit {
    EventQueue eq;
    public static final String POST_EVENT_QUEUE_KEY = "PostEventQueue";
    static {
        try {
            ListResourceBundle resources = (ListResourceBundle) ResourceBundle.getBundle("sun.awt.resources.awt");
            try {
            	System.out.println("Resource Awt.EventQueue:" + resources.getString("AWT.EventQueueClass"));
            }
            catch(Exception e) { }

            Map lookup = (Map) getFieldByName(ListResourceBundle.class, "lookup", resources);
            //System.out.println("Lookup table -" + lookup);
            lookup.put("AWT.EventQueueClass", SwtAwtEventQueue.class.getName());

            System.out.println("Set Resource Awt.EventQueue:" + resources.getString("AWT.EventQueueClass"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public SwtAwtToolkit() {

        eq = new SwtAwtEventQueue();

        AppContext appContext = AppContext.getAppContext();
        appContext.put(AppContext.EVENT_QUEUE_KEY, eq);

        //Lets try to hack the PostEventQueue that the suntoolkit has
        try {
            Class cls = Class.forName("sun.awt.PostEventQueue");
            Constructor ctor = cls.getDeclaredConstructor(new Class[] {EventQueue.class});
            ctor.setAccessible(true);
            Object o = ctor.newInstance(new Object[] {eq});
            appContext.put(POST_EVENT_QUEUE_KEY, o);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }

    }

    public static Object getFieldByName(Class cls, String name, Object inst)
            throws Exception {
        Object ret = null;
        Field[] flds = cls.getDeclaredFields();
        for (int i = 0; i < flds.length; i++) {
            Field f = flds[i];
            //System.out.println("Field Name:" + f.getName());
            if (name.equals(f.getName())) {
                f.setAccessible(true);
                ret = f.get(inst);
            }
        }
        //System.out.println("Return is " + ret );
        return ret;
    }


    public static  Object getConstructor(Class cls, Class fieldType) throws Exception{
        Field[] flds = cls.getDeclaredFields();
        for (int i=0; i < flds.length ; i++)  {
            Field f = flds[i];
            if ((f.getType() == fieldType) && Modifier.isStatic(f.getModifiers())) {
                f.setAccessible(true);
                return f.get(null);

            }
        }
        return null;
    }


}
