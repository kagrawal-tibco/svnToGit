package com.tibco.cep.functions.channel.ftl;

import com.tibco.ftl.FTL;
import com.tibco.ftl.FTLException;
import com.tibco.ftl.TibProperties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PropertyHelperDelegate {

    static ConcurrentMap<String, TibProperties> propRefTable = new ConcurrentHashMap<String, TibProperties>();

    public static Object create () {
        try {
            return FTL.createProperties();
        } catch (FTLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void destroy (Object property) {

        TibProperties prop = TibProperties.class.cast(property);
        if (prop == null) return;
        prop.destroy();
    }

    public static boolean exists (Object property, String propName) {

        TibProperties prop = TibProperties.class.cast(property);
        if (prop == null) return false;
        return prop.exists(propName);
    }

    public static boolean getBoolean (Object property, String propName) {
        TibProperties prop = TibProperties.class.cast(property);
        if (prop == null) return false;
        try {
            return prop.getBoolean(propName);
        } catch (FTLException e) {
            throw new RuntimeException(e);
        }
    }

    public static double getDouble (Object property, String propName) {
        TibProperties prop = TibProperties.class.cast(property);
        if (prop == null) return Double.NaN;
        try {
            return prop.getDouble(propName);
        } catch (FTLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getInt (Object property, String propName) {
        TibProperties prop = TibProperties.class.cast(property);
        if (prop == null) return -1;
        try {
            return prop.getInt(propName);
        } catch (FTLException e) {
            throw new RuntimeException(e);
        }
    }

    public static long getLong (Object property, String propName) {
        TibProperties prop = TibProperties.class.cast(property);
        if (prop == null) return -1L;
        try {
            return prop.getLong(propName);
        } catch (FTLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getString (Object property, String propName) {
        TibProperties prop = TibProperties.class.cast(property);
        if (prop == null) return null;
        try {
            return prop.getString(propName);
        } catch (FTLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean remove (Object property, String propName) {
        TibProperties prop = TibProperties.class.cast(property);
        if (prop == null) return false ;
        try {
            return prop.remove(propName);
        } catch (FTLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setLong(Object property, String propName, long value) {
        TibProperties prop = TibProperties.class.cast(property);
        if (prop == null) return ;
        try {
             prop.set(propName, value);
        } catch (FTLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void setBoolean(Object property, String propName, boolean value) {
        TibProperties prop = TibProperties.class.cast(property);
        if (prop == null) return ;
        try {
             prop.set(propName, value);
        } catch (FTLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setInt(Object property, String propName, int value) {
        TibProperties prop = TibProperties.class.cast(property);
        if (prop == null) return ;
        try {
             prop.set(propName, value);
        } catch (FTLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setDouble(Object property, String propName, double value) {
        TibProperties prop = TibProperties.class.cast(property);
        if (prop == null) return ;
        try {
             prop.set(propName, value);
        } catch (FTLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setString(Object property, String propName, String value) {
        TibProperties prop = TibProperties.class.cast(property);
        if (prop == null) return ;
        try {
             prop.set(propName, value);
        } catch (FTLException e) {
            throw new RuntimeException(e);
        }
    }
}
