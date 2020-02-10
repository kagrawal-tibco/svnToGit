package com.tibco.cep.functions.channel.ftl;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.tibco.ftl.TibProperties;

@com.tibco.be.model.functions.BEPackage(
        catalog = "Communication",
        category = "FTL.Property",
        synopsis = "Property Functions")
public class PropertyHelper {

    static ConcurrentMap<String, TibProperties> propRefTable = new ConcurrentHashMap<String, TibProperties>();

    @com.tibco.be.model.functions.BEFunction(
        name = "create",
        signature = "Object create ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Properties"),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Create an empty property list object without any default values. As a mechanism for configuring objects, some FTL creation calls accept a properties object, which contains paired property names and values.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static Object create () {
        return PropertyHelperDelegate.create();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "destroy",
        signature = "void destroy (Object property)",
        params = {
             @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = "property object")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Destroy the properties object.An application can destroy a properties object to reclaim its resources. Destroying a properties object does not invalidate objects created with it. (For example, the queue creation call copies property values into the new queue.)",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static void destroy (Object property) {
    	PropertyHelperDelegate.destroy(property);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "exists",
        signature = "boolean exists (Object property, String propname)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = "property object "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propname", type = "String", desc = "property name ")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "If the property Object is null then this method returns false<br/>In case property Object is not null one of following would happen<br/>1. If provided propName exists then returns true.<br/>2. If provided propName does not exist then returns false."),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Check whether a property exists.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static boolean exists (Object property, String propName) {
    	return PropertyHelperDelegate.exists(property, propName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getBoolean",
        signature = "boolean getBoolean (Object property, String propName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = "property object "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propName", type = "String", desc = "property name ")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns a boolean value of a property."),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get a boolean property.Return the boolean value of property name.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static boolean getBoolean (Object property, String propName) {
        return PropertyHelperDelegate.getBoolean(property, propName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDouble",
        signature = "double getDouble (Object property, String propName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = " property object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propName", type = "String", desc = "property name ")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "Returns a double value of a property."),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get a double integer property.Return the double integer value of property name.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static double getDouble (Object property, String propName) {
        return PropertyHelperDelegate.getDouble(property, propName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getInt",
        signature = "int getInt (Object property, String propName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = "property object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propName", type = "String", desc = "property name ")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Returns a integer value of a property."),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get an integer property.Return the integer value of property name.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static int getInt (Object property, String propName) {
        return PropertyHelperDelegate.getInt(property, propName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getLong",
        signature = "long getLong (Object property, String propName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = "property object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propName", type = "String", desc = "property name ")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "Returns a long value of a property."),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get a long property.Return the long value of property name.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static long getLong (Object property, String propName) {
        return PropertyHelperDelegate.getLong(property, propName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getString",
        signature = "String getString (Object property, String propName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = "property object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propName", type = "String", desc = "property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Returns a String value of a property."),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get a String property.Return the String value of property name.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static String getString (Object property, String propName) {
        return PropertyHelperDelegate.getString(property, propName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "remove",
        signature = "boolean remove (Object property, String propName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = "property object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propName", type = "String", desc = "property name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "If the property which needs to be removed does not exist it returns false, else returns true."),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "If the property which needs to be removed does not exist it returns false, else returns true.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static boolean remove (Object property, String propName) {
        return PropertyHelperDelegate.remove(property, propName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setLong",
        signature = "void set (Object property, String propName, long value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = "property object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propName", type = "String", desc = "property name "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "long", desc = "long value to be set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set a long property.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static void setLong(Object property, String propName, long value) {
    	PropertyHelperDelegate.setLong(property, propName, value);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setBoolean",
        signature = "void set (Object property, String propName, boolean value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = "property object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propName", type = "String", desc = "property name "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "boolean", desc = "boolean value to be set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set a boolean property.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static void setBoolean(Object property, String propName, boolean value) {
    	PropertyHelperDelegate.setBoolean(property, propName, value);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setInt",
        signature = "void set (Object property, String propName, int value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = "property object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propName", type = "String", desc = "property name "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "int", desc = "integer value to be set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set an integer property.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static void setInt(Object property, String propName, int value) {
    	PropertyHelperDelegate.setInt(property, propName, value);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setDouble",
        signature = "void set (Object property, String propName, double value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = "property object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propName", type = "String", desc = "property name "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "double", desc = "double value to be set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set a double property.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static void setDouble(Object property, String propName, double value) {
    	PropertyHelperDelegate.setDouble(property, propName, value);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setString",
        signature = "void set (Object property, String propName, String value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = "property object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propName", type = "String", desc = "property name "),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "String", desc = "String value to be set.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set a string property.",
        cautions = "none",
        fndomain={ACTION},
        example = ""
    )
    public static void setString(Object property, String propName, String value) {
    	PropertyHelperDelegate.setString(property, propName, value);
    }
}
