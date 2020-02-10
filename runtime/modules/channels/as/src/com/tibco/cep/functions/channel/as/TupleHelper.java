package com.tibco.cep.functions.channel.as;

/**
* Generated Code from String Template.
* Date : Sep 6, 2012
*/

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.Calendar;
import java.util.GregorianCalendar;
import com.tibco.cep.functions.channel.as.ASTupleHelper;

import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.FunctionParamDescriptor;


@BEPackage(
        catalog = "ActiveSpaces",
        category = "Metaspace.Tuple",
        synopsis = "Tuple functions")
public class TupleHelper {

    @BEFunction(
        name = "clear",
        synopsis = "Removes all the fields in the Tuple.",
        signature = "void clear (Object tuple)",
        params = {
            @FunctionParamDescriptor(name="tuple", type="Object", desc="Tuple object")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Clears the tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void clear (Object tuple) {
        ASTupleHelper.clear (tuple);
    }

    @BEFunction(
        name = "containsKey",
        synopsis = "Returns true if the tuple contains the specified key.",
        signature = "boolean containsKey (Object tuple, Object key)",
        params = {
            @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
            @FunctionParamDescriptor(name = "key", type = "Object", desc = "key Object ")

        },
        freturn = @FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the tuple contains the specified key"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns true if the tuple contains the specified key",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean containsKey (Object tuple, Object key) {
    	return ASTupleHelper.containsKey (tuple, key);
    }

    @BEFunction(
        name = "containsValue",
        synopsis = "Returns true if the tuple contains the value Object.",
        signature = "boolean containsValue (Object tuple, Object value)",
        params = {
            @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
            @FunctionParamDescriptor(name = "value", type = "Object", desc = "value Object ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the tuple contains the value Object"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns true if the tuple contains the value Object",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean containsValue (Object tuple, Object value) {
    	return ASTupleHelper.containsValue (tuple, value);
    }

    @BEFunction(
        name = "create",
        synopsis = "Returns a newly created Tuple.",
        signature = "Object create ()",
        params = {
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The new Tuple instance"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a newly created Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object create () {
        return ASTupleHelper.create();
    }

    @BEFunction(
        name = "deserialize",
        synopsis = "Deserializes byte[] to the Tuple.",
        signature = "void deserialize (Object tuple, Object data)",
        params = {
             @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
             @FunctionParamDescriptor(name = "data", type = "Object", desc = "byte[] data ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Deserializes byte[] to the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void deserialize (Object tuple, Object data) {
    	ASTupleHelper.deserialize(tuple, data);
    }

    @BEFunction(
        name = "entrySet",
        synopsis = "Returns the entrySet from the Tuple.",
        signature = "Object entrySet (Object tuple)",
        params = {
            @FunctionParamDescriptor(name="tuple", type="Object", desc="Tuple Object")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The entrySet(java.util.Set instance) from the Tuple"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the entrySet from the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object entrySet (Object tuple) {
    	return ASTupleHelper.entrySet(tuple);
    }

    @BEFunction(
        name = "exists",
        synopsis = "Returns true if the parameter key exists in the Tuple.",
        signature = "boolean exists (Object tuple, String key)",
        params = {
            @FunctionParamDescriptor(name="tuple",type="Object",desc="Tuple Object"),
            @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the parameter key exists in the Tuple"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns true if the parameter key exists in the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean exists (Object tuple, String key) {
    	return ASTupleHelper.exists(tuple, key);
    }

    @BEFunction(
        name = "get",
        synopsis = "Returns the object from the Tuple based on the key.",
        signature = "Object get (Object tuple, String key)",
        params = {
            @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
            @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The object from the Tuple based on the key"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the object from the Tuple based on the key",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object get (Object tuple, String key) {
    	return ASTupleHelper.get(tuple, key);
    }

    @BEFunction(
        name = "getBlob",
        synopsis = "Returns a BLOB byte[] object based on the key.",
        signature = "Object getBlob (Object tuple, String key)",
        params = {
            @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
            @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The BLOB byte[] object based on the key"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a BLOB byte[] object based on the key",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object getBlob (Object tuple, String key) {
    	return ASTupleHelper.getBlob(tuple, key);
    }

    @BEFunction(
        name = "getBoolean",
        synopsis = "Returns the Boolean object based on the specified key from the Tuple.",
        signature = "boolean getBoolean (Object tuple, String key)",
        params = {
            @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
            @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "boolean", desc = "The Boolean object based on the specified key from the Tuple"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Boolean object based on the specified key from the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean getBoolean (Object tuple, String key) {
        return ASTupleHelper.getBoolean(tuple, key);
    }

    @BEFunction(
        name = "getChar",
        synopsis = "Returns the Char object based on the specified key from the Tuple.",
        signature = "Object getChar (Object tuple, String key)",
        params = {
            @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object"),
            @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ")

        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The java.lang.Character object based on the specified key from the Tuple"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the java.lang.Character object based on the specified key from the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object getChar (Object tuple, String key) {
    	return ASTupleHelper.getChar(tuple, key);
    }

    @BEFunction(
        name = "getDateTime",
        synopsis = "Returns the Calendar datetime based on the specified key from the Tuple.",
        signature = "Object getDateTime (Object tuple, String key)",
        params = {
             @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
             @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The java.util.Calendar datetime based on the specified key from the Tuple"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Calendar datetime based on the specified key from the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object getDateTime (Object tuple, String key) {
    	return ASTupleHelper.getDateTime(tuple, key);
    }

    @BEFunction(
        name = "getDouble",
        synopsis = "Returns the Double Object based on the specified key from the Tuple Object.",
        signature = "double getDouble (Object tuple, String key)",
        params = {
             @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
             @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "double", desc = "The double based on the specified key from the Tuple Object"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Double Object based on the specified key from the Tuple Object",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static double getDouble (Object tuple, String key) {
    	return ASTupleHelper.getDouble(tuple, key);
    }

    @BEFunction(
        name = "getFieldNames",
        synopsis = "Returns a Collection of field names from the Tuple.",
        signature = "Object getFieldNames (Object tuple)",
        params = {
            @FunctionParamDescriptor(name="tuple", type="Object", desc="Tuple Object")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "Collection instance of field names from the Tuple"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a Collection of field names from the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object getFieldNames (Object tuple) {
    	return ASTupleHelper.getFieldNames(tuple);
    }

    @BEFunction(
        name = "getFieldType",
        synopsis = "Returns the Field type class for the given key.",
        signature = "Object getFieldType (Object tuple, String key)",
        params = {
            @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple object"),
            @FunctionParamDescriptor(name = "key", type = "String", desc = "field key")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The com.tibco.as.space.FieldDef$FieldType type instance for the given key"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Field type class for the given key",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object getFieldType (Object tuple, String key) {
    	return ASTupleHelper.getFieldType(tuple, key);
    }

    @BEFunction(
        name = "getFloat",
        synopsis = "Returns a Float object based on the specified key from the Tuple.",
        signature = "double getFloat (Object tuple, String key)",
        params = {
            @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
            @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "double", desc = "The float value based on the specified key from the Tuple"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a Float object based on the specified key from the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static double getFloat (Object tuple, String key) {
    	return ASTupleHelper.getFloat(tuple, key);
    }

    @BEFunction(
        name = "getInt",
        synopsis = "Returns the Integer object based on the specified key from the Tuple.",
        signature = "int getInt (Object tuple, String key)",
        params = {
            @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
            @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ")

        },
        freturn = @FunctionParamDescriptor(name = "", type = "int", desc = "The integer value based on the specified key from the Tuple"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Integer object based on the specified key from the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static int getInt (Object tuple, String key) {
    	return ASTupleHelper.getInt(tuple, key);
    }

    @BEFunction(
        name = "getLong",
        synopsis = "Returns the Long object based on the specified key from the Tuple.",
        signature = "long getLong (Object tuple, String key)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple object "),
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ")

        },
        freturn = @FunctionParamDescriptor(name = "", type = "long", desc = "The long value based on the specified key from the Tuple"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Long object based on the specified key from the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static long getLong (Object tuple, String key) {
    	return ASTupleHelper.getLong(tuple, key);
    }

    @BEFunction(
        name = "getShort",
        synopsis = "Returns the Short object based on the specified key from the Tuple.",
        signature = "int getShort (Object tuple, String key)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object"),
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "int", desc = "The short value based on the specified key from the Tuple"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Short object based on the specified key from the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static int getShort (Object tuple, String key) {
    	return ASTupleHelper.getShort(tuple, key);
    }

    @BEFunction(
        name = "getString",
        synopsis = "Returns the String object based on the specified key from the Tuple.",
        signature = "String getString (Object tuple, String key)",
        params = {
            @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
            @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "The string value based on the specified key from the Tuple"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the String object based on the specified key from the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static String getString (Object tuple, String key) {
    	return ASTupleHelper.getString(tuple, key);
    }

    @BEFunction(
        name = "isEmpty",
        synopsis = "Returns true if the tuple is empty.",
        signature = "boolean isEmpty (Object tuple)",
        params = {
            @FunctionParamDescriptor(name="tuple", type="Object", desc="Tuple Object")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the tuple is empty"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns true if the tuple is empty",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean isEmpty (Object tuple) {
    	return ASTupleHelper.isEmpty(tuple);
    }

    @BEFunction(
        name = "isNull",
        synopsis = "Returns true if the Object value at the specified key is null from the Tuple.",
        signature = "boolean isNull (Object tuple, String key)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object"),
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the Object value at the specified key is null from the Tuple"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns true if the Object value at the specified key is null from the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean isNull (Object tuple, String key) {
    	return ASTupleHelper.isNull(tuple, key);
    }

    @BEFunction(
        name = "keySet",
        synopsis = "Returns the keySet for the specified tuple.",
        signature = "Object keySet (Object tuple)",
        params = {
            @FunctionParamDescriptor(name="tuple", type="Object", desc="Object key")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The keySet(java.util.Set instance) for the specified tuple"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the keySet for the specified tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object keySet (Object tuple) {
    	return ASTupleHelper.keySet(tuple);
    }

    @BEFunction(
        name = "put",
        synopsis = "Puts the Object value for the specified key into the Tuple.",
        signature = "Object put (Object tuple, String key, Object value)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object"),
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key "),
                @FunctionParamDescriptor(name = "value", type = "Object", desc = "Object value ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The previous value associated with key, \n or null if there was no mapping for key."),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Puts the Object value for the specified key into the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object put (Object tuple, String key, Object value) {
    	return ASTupleHelper.put(tuple, key, value);
    }

    @BEFunction(
        name = "putAll",
        synopsis = "Puts Tuple key value pairs from input tuple to the specified Tuple Object.",
        signature = "void putAll (Object tuple, Object input)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "com.tibco.as.space.Tuple Object to be modified"),
                @FunctionParamDescriptor(name = "input", type = "Object", desc = "Input com.tibco.as.space.Tuple object ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Puts Tuple key value pairs from input tuple to the specified Tuple Object",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void putAll (Object tuple, Object input) {
    	ASTupleHelper.putAll(tuple, input);
    }

    @BEFunction(
        name = "putBlob",
        synopsis = "Puts a BLOB value for the specified key into the Tuple.",
        signature = "Object putBlob (Object tuple, String key, Object value)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key "),
                @FunctionParamDescriptor(name = "value", type = "Object", desc = "java.lang.byte[] instance ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The previous value if it exists, null otherwise"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Puts a BLOB value for the specified key into the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object putBlob (Object tuple, String key, Object value) {
    	return ASTupleHelper.putBlob(tuple, key, value);
    }

    @BEFunction(
        name = "putBoolean",
        synopsis = "Puts a Boolean object based on the specified key into the Tuple.",
        signature = "Object putBoolean (Object tuple, String key, boolean value)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key "),
                @FunctionParamDescriptor(name = "value", type = "boolean", desc = "Object value ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The previous value if it exists, null otherwise"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Puts a Boolean object based on the specified key into the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object putBoolean (Object tuple, String key, boolean value) {
    	return ASTupleHelper.putBoolean(tuple, key, value);
    }

    @BEFunction(
        name = "putChar",
        synopsis = "Puts a Char object based on the specified key into the Tuple.",
        signature = "Object putChar (Object tuple, String key, Object value)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key "),
                @FunctionParamDescriptor(name = "value", type = "Object", desc = "Instance of Character ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The previous value if it exists, null otherwise"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Puts a Char object based on the specified key into the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object putChar (Object tuple, String key, Object value) {
    	return ASTupleHelper.putChar(tuple, key, value);
    }

    @BEFunction(
        name = "putDateTime",
        synopsis = "Puts a Calendar object based on the specified key into the Tuple.",
        signature = "Object putDateTime (Object tuple, String key, Object value)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key "),
                @FunctionParamDescriptor(name = "value", type = "Object", desc = "Instance of java.util.Calender ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The previous value if it exists, null otherwise"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Puts a Calendar object based on the specified key into the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object putDateTime (Object tuple, String key, Object value) {
    	return ASTupleHelper.putDateTime(tuple, key, value);
    }

    @BEFunction(
        name = "putDouble",
        synopsis = "Puts a Double object based on the specified key into the Tuple.",
        signature = "Object putDouble (Object tuple, String key, double value)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object"),
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key "),
                @FunctionParamDescriptor(name = "value", type = "double", desc = "Object value ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The previous value if it exists, null otherwise"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Puts a Double object based on the specified key into the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object putDouble (Object tuple, String key, double value) {
    	return ASTupleHelper.putDouble(tuple, key, value);
    }

    @BEFunction(
        name = "putFloat",
        synopsis = "Puts a Float object based on the specified key into the Tuple.",
        signature = "Object putFloat (Object tuple, String key, Object value)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object ") ,
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ") ,
                @FunctionParamDescriptor(name = "value", type = "Object", desc = "Instance of java.lang.Float ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The previous value if it exists, null otherwise"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Puts a Float object based on the specified key into the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object putFloat (Object tuple, String key, Object value) {
    	return ASTupleHelper.putFloat(tuple, key, value);
    }

    @BEFunction(
        name = "putInt",
        synopsis = "Puts a Integer object based on the specified key into the Tuple.",
        signature = "Object putInt (Object tuple, String key, int value)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object ") ,
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ") ,
                @FunctionParamDescriptor(name = "value", type = "int", desc = "Object value ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The previous value if it exists, null otherwise"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Puts a Integer object based on the specified key into the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object putInt (Object tuple, String key, int value) {
    	return ASTupleHelper.putInt(tuple, key, value);
    }

    @BEFunction(
        name = "putLong",
        synopsis = "Puts a Long object based on the specified key into the Tuple.",
        signature = "Object putLong (Object tuple, String key, long value)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object") ,
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ") ,
                @FunctionParamDescriptor(name = "value", type = "long", desc = "Object value")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The previous value if it exists, null otherwise"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Puts a Long object based on the specified key into the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object putLong (Object tuple, String key, long value) {
    	return ASTupleHelper.putLong(tuple, key, value);
    }

    @BEFunction(
        name = "putShort",
        synopsis = "Puts a Short object based on the specified key into the Tuple.",
        signature = "Object putShort (Object tuple, String key, Object value)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object") ,
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ") ,
                @FunctionParamDescriptor(name = "value", type = "Object", desc = "java.lang.Short instance ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The previous value if it exists, null otherwise"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Puts a Short object based on the specified key into the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object putShort (Object tuple, String key, Object value) {
    	return ASTupleHelper.putShort(tuple, key, value);
    }

    @BEFunction(
        name = "putString",
        synopsis = "Puts a String object based on the specified key into the Tuple.",
        signature = "Object putString (Object tuple, String key, String value)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object ") ,
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ") ,
                @FunctionParamDescriptor(name = "value", type = "String", desc = "Object value ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The previous value if it exists, null otherwise"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Puts a String object based on the specified key into the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object putString (Object tuple, String key, String value) {
    	return ASTupleHelper.putString(tuple, key, value);
    }

    @BEFunction(
        name = "putObject",
        synopsis = "Puts a Object based on the specified key into the Tuple",
        signature = "Object putObject (Object tuple, Object keys, Object values)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object ") ,
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ") ,
                @FunctionParamDescriptor(name = "value", type = "Object", desc = "Object value ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "void", desc = "The previous value if it exists, null otherwise"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Puts a Object based on the specified key into the Tuple",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void putObject (Object tuple, String key, Object value) {
    	ASTupleHelper.putObject(tuple, key, value);
    }

    @BEFunction(
        name = "remove",
        synopsis = "Removed an object from the Tuple based on the specified key.",
        signature = "Object remove (Object tuple, String key)",
        params = {
                @FunctionParamDescriptor(name = "tuple", type = "Object", desc = "Tuple Object "),
                @FunctionParamDescriptor(name = "key", type = "String", desc = "Object key ")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "The previous value, null if it does not exist"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Removed an object from the Tuple based on the specified key",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object remove (Object tuple, String key) {
    	return ASTupleHelper.remove(tuple, key);
    }

    @BEFunction(
        name = "serialize",
        synopsis = "Serializes the tuple.",
        signature = "Object serialize (Object tuple)",
        params = {
            @FunctionParamDescriptor(name="tuple",type="Object",desc="Tuple Object")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "java.lang.Byte[], The serialized version of tuple"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Outputs the String passed to the USER sink.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object serialize (Object tuple) {
    	return ASTupleHelper.serialize(tuple);
    }

    @BEFunction(
        name = "size",
        synopsis = "Returns a count of key-value pairs in the Tuple, else 0",
        signature = "int size (Object tuple)",
        params = {
            @FunctionParamDescriptor(name="tuple",type="Object",desc="Tuple Object")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "int", desc = "The count of key-value pairs in the Tuple, else 0"),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a count of key-value pairs in the Tuple, else 0",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static int size (Object tuple) {
    	return ASTupleHelper.size(tuple);
    }

    @BEFunction(
        name = "values",
        synopsis = "Returns the collection of value Objects from the Tuple.",
        signature = "Object values (Object tuple)",
        params = {
            @FunctionParamDescriptor(name="tuple",type="Object",desc="Tuple Object")
        },
        freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "Instance of java.util.Collection of value Objects from the Tuple."),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the collection of value Objects from the Tuple.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object values (Object tuple) {
    	return ASTupleHelper.values(tuple);
    }
}