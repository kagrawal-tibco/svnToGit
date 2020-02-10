package com.tibco.be.functions.property;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

import com.tibco.cep.runtime.model.element.*;

import java.lang.reflect.Array;
import java.util.Calendar;

/**
 * User: nleong
 * Date: Sep 14, 2004
 * Time: 8:47:24 PM
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Instance.PropertyArray",
        synopsis = "Utility Functions to Operate on PropertyArray types of Concepts")

public class PropertyArrayHelper {
    @com.tibco.be.model.functions.BEFunction(
        name = "length",
        synopsis = "Returns the length of the <code>propertyArray</code> .",
        enabled = @Enabled(value=false),
        signature = "int length (PropertyArray propertyArray)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyArray", type = "PropertyArray", desc = "A PropertyArray to get the length of.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Return the length of the <code>propertyArray</code> passed."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static int length(PropertyArray propertyArray) {
        return propertyArray.length();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "delete",
        synopsis = "Deletes the array element in <code>arr</code> located at <code>index</code> and\nshifts any subsequent elements to the left (subtracts one from their indices)",
        signature = "void delete (PropertyArray arr, int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to delete an element from."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to delete.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Deletes the array element in <code>arr</code> located at <code>index</code>\nand shifts any subsequent elements to the left (subtracts one from their indices).",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void delete(PropertyArray arr, int index) {
        arr.remove(index);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "clear",
        synopsis = "Removes all elements from <code>arr</code>. For a Concept Array, you will have to \nloop and delete each instance using: <code>Instance.PropertyArray.removeContainedConcept()</code> \nor <code>Instance.PropertyArray.removeConceptReference()</code>.",
        signature = "void clear (PropertyArray arr)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to clear all elements from.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Removes all elements from <code>arr</code>. For a Concept Array, you will have to \nloop and delete each instance using: <code>Instance.PropertyArray.removeContainedConcept()</code> \nor <code>Instance.PropertyArray.removeConceptReference()</code>.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void clear(PropertyArray arr) {
        arr.clear();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "get",
        synopsis = "Gets the array element in <code>arr</code> located at <code>index</code>.",
        enabled = @Enabled(value=false),
        signature = "PropertyAtom get (PropertyArray arr, int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to get an element from."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index of an element to get.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "PropertyAtom", desc = "The array element in <code>arr</code> located at <code>index</code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the array element in <code>arr</code> located at <code>index</code>.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static PropertyAtom get(PropertyArray arr, int index) {
        return arr.get(index);
    }



    @com.tibco.be.model.functions.BEFunction(
        name = "setInt",
        synopsis = "Sets the array element in <code>arr</code> located at <code>index</code> to the <code>value</code>.",
        enabled = @Enabled(value=false),
        signature = "void setInt (PropertyArrayInt arr, int index, int value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayInt", desc = "A PropertyArray to set an element of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index of an element to set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "int", desc = "The new value to set the PropertyArray element to.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the array element in <code>arr</code> located at <code>index</code> to the int, <code>value</code>.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void setInt(PropertyArrayInt arr, int index, int value) {
        arr.add(index, value);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "setLong",
        synopsis = "Sets the array element in <code>arr</code> located at <code>index</code> to the <code>value</code>",
        enabled = @Enabled(value=false),
        signature = "void setLong (PropertyArrayLong arr, int index, long value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayLong", desc = "A PropertyArray to set an element of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "long", desc = "The new value to set the PropertyArray element to.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the array element in <code>arr</code> located at <code>index</code> to the long, <code>value</code>.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void setLong(PropertyArrayLong arr, int index, long value) {
        arr.add(index, value);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "setDouble",
        synopsis = "Sets the array element in <code>arr</code> located at <code>index</code> to the double, <code>value</code>.",
        enabled = @Enabled(value=false),
        signature = "void setDouble (PropertyArrayDouble arr, int index, double value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayDouble", desc = "A PropertyArray to set an element of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "double", desc = "The new value to set the PropertyArray element to.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the array element in <code>arr</code> located at <code>index</code> to the double, <code>value</code>.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void setDouble(PropertyArrayDouble arr, int index, double value) {
        arr.add(index, value);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "setBoolean",
        synopsis = "Sets the array element in <code>arr</code> located at <code>index</code> to the boolean, </code>value</code>.",
        enabled = @Enabled(value=false),
        signature = "void setBoolean (PropertyArrayBoolean arr, int index, boolean value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayBoolean", desc = "A PropertyArray to set an element of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "boolean", desc = "The new value to set the PropertyArray element to.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the array element in <code>arr</code> located at <code>index</code> to the boolean, <code>value</code>.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void setBoolean(PropertyArrayBoolean arr, int index, boolean value) {
        arr.add(index, value);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "setString",
        synopsis = "Sets the array element in <code>arr</code> located at <code>index</code> to the String, <code>value</code>.",
        enabled = @Enabled(value=false),
        signature = "void setString (PropertyArrayString arr, int index, String value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayString", desc = "A PropertyArray to set an element of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "String", desc = "The new value to set the PropertyArray element to.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the array element in <code>arr</code> located at <code>index</code> to the String, <code>value</code>.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void setString(PropertyArrayString arr, int index, String value) {
        arr.add(index, value);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "setConceptReference",
        synopsis = "Sets the array element in <code>arr</code> located at <code>index</code>\nto the Concept <code>value</code>.",
        enabled = @Enabled(value=false),
        signature = "void setConceptReference (PropertyArrayConceptReference arr, int index, Concept value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayConceptReference", desc = "A PropertyArray to set an element of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Concept", desc = "The new value to set the PropertyArray element to.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the array element in <code>arr</code> located at <code>index</code> to the Concept, <code>value</code>.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void setConceptReference(PropertyArrayConceptReference arr, int index, Concept value) {
        arr.add(index, value);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "setContainedConcept",
        synopsis = "Sets the array element in <code>arr</code> located at <code>index</code> to the ContainedConcept, <code>value</code>.",
        enabled = @Enabled(value=false),
        signature = "void setContainedConcept (PropertyArrayContainedConcept arr, int index, ContainedConcept value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayContainedConcept", desc = "A PropertyArray to set an element of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "ContainedConcept", desc = "The new value to set the PropertyArray element to.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the array element in <code>arr</code> located at <code>index</code> to the ContainedConcept, <code>value</code>.",
        cautions = "A new ContainedConcept is set to PropertyContainedConcept, the\nold one will be retracted automatically from the working memory.",
        fndomain = {ACTION},
        example = ""
    )
    public static void setContainedConcept(PropertyArrayContainedConcept arr, int index, ContainedConcept value) {
        arr.add(index, value);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "setDateTime",
        synopsis = "Sets the array element in <code>arr</code> located at <code>index</code> to the DateTime, <code>value</code>.",
        enabled = @Enabled(value=false),
        signature = "void setDateTime(PropertyArrayDateTime arr, int index, DateTime value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayDateTime", desc = "A PropertyArray to set an element of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "DateTime", desc = "The new value to set the PropertyArray element to.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the array element in <code>arr</code> located at <code>index</code> to the DateTime, <code>value</code>.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void setDateTime(PropertyArrayDateTime arr, int index, Calendar value) {
        arr.add(index, value);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getInt",
        synopsis = "Gets a value from a PropertyArray at a specified index.",
        enabled = @Enabled(value=false),
        signature = "PropertyAtomInt getInt (PropertyArrayInt arr, int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayInt", desc = "A PropertyArray to get an element from."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to get.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "PropertyAtomInt", desc = "<code>arr[index]</code>, i.e. the int value at\nindex <code>index</code> in the array <code>arr</code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets a value from a PropertyArray at a specified index.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static PropertyAtomInt getInt(PropertyArrayInt arr, int index) {
        return (PropertyAtomInt)arr.get(index);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getLong",
        synopsis = "Gets a value from a PropertyArray at a specified index.",
        enabled = @Enabled(value=false),
        signature = "PropertyAtomLong getLong (PropertyArrayLong arr, int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayLong", desc = "A PropertyArray to get an element from."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to get.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "PropertyAtomLong", desc = "<code>arr[index]</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets a value from a PropertyArray at a specified index.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static PropertyAtomLong getLong(PropertyArrayLong arr, int index) {
        return (PropertyAtomLong)arr.get(index);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getDouble",
        synopsis = "Gets a value from a PropertyArray at a specified index.",
        enabled = @Enabled(value=false),
        signature = "PropertyAtomDouble getDouble (PropertyArrayDouble arr, int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to get an element from."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to get.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "PropertyAtomDouble", desc = "<code>arr[index]</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets a value from a PropertyArray at a specified index.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static PropertyAtomDouble getDouble(PropertyArrayDouble arr, int index) {
        return (PropertyAtomDouble)arr.get(index);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getBoolean",
        synopsis = "Gets a value from a PropertyArray at a specified index.",
        enabled = @Enabled(value=false),
        signature = "PropertyAtomBoolean getBoolean (PropertyArrayBoolean arr, int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to get an element from."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to get.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "PropertyAtomBoolean", desc = "<code>arr[index]</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets a value from a PropertyArray at a specified index.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static PropertyAtomBoolean getBoolean(PropertyArrayBoolean arr, int index) {
        return (PropertyAtomBoolean)arr.get(index);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getString",
        synopsis = "Gets a value from a PropertyArray at a specified index.",
        enabled = @Enabled(value=false),
        signature = "PropertyAtomString getString (PropertyArrayString arr, int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to get an element from."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to get.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "PropertyAtomString", desc = "<code>arr[index]</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets a value from a PropertyArray at a specified index.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static PropertyAtomString getString(PropertyArrayString arr, int index) {
        return (PropertyAtomString)arr.get(index);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getConcept",
        synopsis = "Gets a value from a PropertyArray at a specified index.",
        enabled = @Enabled(value=false),
        signature = "PropertyAtomConcept getConcept (PropertyArrayConceptReference arr, int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to get an element from."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to get.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "PropertyAtomConcept", desc = "<code>arr[index]</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets a value from a PropertyArray at a specified index.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static PropertyAtomConcept getConcept(PropertyArrayConcept arr, int index) {
        return (PropertyAtomConcept)arr.get(index);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getConceptReference",
        synopsis = "Gets a value from a PropertyArray at a specified index.",
        enabled = @Enabled(value=false),
        signature = "PropertyAtomConceptReference getConceptReference (PropertyArrayConceptReference arr, int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to get an element from."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to get.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "PropertyAtomConceptReference", desc = "<code>arr[index]</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets a value from a PropertyArray at a specified index.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static PropertyAtomConceptReference getConceptReference(PropertyArrayConceptReference arr, int index) {
        return (PropertyAtomConceptReference)arr.get(index);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getContainedConcept",
        synopsis = "Gets a value from a PropertyArray at a specified index.",
        enabled = @Enabled(value=false),
        signature = "PropertyAtomContainedConcept getContainedConcept (PropertyArrayContainedConcept arr, int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to get an element from."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to get.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "PropertyAtomContainedConcept", desc = "<code>arr[index]</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets a value from a PropertyArray at a specified index.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static PropertyAtomContainedConcept getContainedConcept(PropertyArrayContainedConcept arr, int index) {
        return (PropertyAtomContainedConcept)arr.get(index);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getDateTime",
        synopsis = "Gets a value from a PropertyArray at a specified index.",
        enabled = @Enabled(value=false),
        signature = "PropertyAtomDateTime getDateTime (PropertyArrayDateTime arr, int index)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to get an element from."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "index", type = "int", desc = "The index in the PropertyArray <code>arr</code> to get.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "PropertyAtomDateTime", desc = "<code>arr[index]</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets a value from a PropertyArray at a specified index.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static PropertyAtomDateTime getDateTime(PropertyArrayDateTime arr, int index) {
        return (PropertyAtomDateTime)arr.get(index);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "appendInt",
        synopsis = "Creates a new propertyAtomInt of value with initial history timestamp time and appends to the end of the PropertyArray.\nOr by doing instanceX.arr[instanceX.arr@length] = value, the engine uses system current time.",
        signature = "void appendInt (PropertyArrayInt arr, int value, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to append an element to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "int", desc = "A value to append to the PropertyArray, <code>arr</code>."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The initial history timestamp.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a new propertyAtomInt of value with initial history timestamp time and appends to the end of the\nPropertyArray. Use instanceX.arr[instanceX.arr@length] = value for appending with system current time",
        cautions = "Use instanceX.arr[instanceX.arr@length] = value for appending with system current time",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void appendInt(PropertyArrayInt arr, int value, long time) {
        PropertyAtomInt pint = (PropertyAtomInt) arr.add();
        pint.setInt(value, time);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "appendLong",
        synopsis = "Creates a new propertyAtomLong of value with initial history timestamp time and appends to the end of the PropertyArray.\nOr by doing instanceX.arr[instanceX.arr@length] = value, the engine uses system current time.",
        signature = "void appendLong (PropertyArrayLong arr, long value, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to append an element to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "long", desc = "A value to append to the PropertyArray, <code>arr</code>."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The initial history timestamp.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a new propertyAtomLong of value with initial history timestamp time and appends to the end of the\nPropertyArray. Use instanceX.arr[instanceX.arr@length] = value for appending with system current time",
        cautions = "Use instanceX.arr[instanceX.arr@length] = value for appending with system current time",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void appendLong(PropertyArrayLong arr, long value, long time) {
        PropertyAtomLong plong = (PropertyAtomLong) arr.add();
        plong.setLong(value, time);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "appendDouble",
        synopsis = "Creates a new propertyAtomDouble of value with initial history timestamp time and appends to the end of the PropertyArray.\nOr by doing instanceX.arr[instanceX.arr@length] = value, the engine uses system current time.",
        signature = "void appendDouble (PropertyArrayDouble arr, double value, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to append an element to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "double", desc = "A value to append to the PropertyArray, <code>arr</code>."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The initial history timestamp.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a new propertyAtomDouble of value with initial history timestamp time and appends to the end of the\nPropertyArray. Use instanceX.arr[instanceX.arr@length] = value for appending with system current time",
        cautions = "Use instanceX.arr[instanceX.arr@length] = value for appending with system current time",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void appendDouble(PropertyArrayDouble arr, double value, long time) {
        PropertyAtomDouble pdouble = (PropertyAtomDouble) arr.add();
        pdouble.setDouble(value, time);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "appendBoolean",
        synopsis = "Creates a new propertyAtomBoolean of value with initial history timestamp time and appends to the end of the PropertyArray.\nOr by doing instanceX.arr[instanceX.arr@length] = value, the engine uses system current time.",
        signature = "void appendBoolean (PropertyArrayBoolean arr, boolean value, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to append an element to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "boolean", desc = "A value to append to the PropertyArray, <code>arr</code>."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The initial history timestamp.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a new propertyAtomBoolean of value with initial history timestamp time and appends to the end of the\nPropertyArray. Use instanceX.arr[instanceX.arr@length] = value for appending with system current time.",
        cautions = "Use instanceX.arr[instanceX.arr@length] = value for appending with system current time",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void appendBoolean(PropertyArrayBoolean arr, boolean value, long time) {
        PropertyAtomBoolean pBoolean = (PropertyAtomBoolean) arr.add();
        pBoolean.setBoolean(value, time);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "appendString",
        synopsis = "Creates a new propertyAtomString of value with initial history timestamp time and appends to the end of the PropertyArray.\nOr by doing instanceX.arr[instanceX.arr@length] = value, the engine uses system current time.",
        signature = "void appendString (PropertyArrayString arr, String value, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to append an element to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "String", desc = "A value to append to the PropertyArray, <code>arr</code>."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The initial history timestamp.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a new propertyAtomString of value with initial history timestamp time and appends to the end of the\nPropertyArray. Use instanceX.arr[instanceX.arr@length] = value for appending with system current time",
        cautions = "Use instanceX.arr[instanceX.arr@length] = value for appending with system current time",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void appendString(PropertyArrayString arr, String value, long time) {
        PropertyAtomString pstring = (PropertyAtomString) arr.add();
        pstring.setString(value, time);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "appendDateTime",
        synopsis = "Creates a new propertyAtomDateTime of value with initial history timestamp time and appends to the end of the PropertyArray.\nOr by doing instanceX.arr[instanceX.arr@length] = value, the engine uses system current time.",
        signature = "void appendDateTime (PropertyArrayDateTime arr, DateTime value, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to append an element to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "DateTime", desc = "A value to append to the PropertyArray, <code>arr</code>."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The initial history timestamp.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a new propertyAtomDateTime of value with initial history timestamp time and appends to the end of the\nPropertyArray. Use instanceX.arr[instanceX.arr@length] = value for appending with system current time",
        cautions = "Use instanceX.arr[instanceX.arr@length] = value for appending with system current time",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void appendDateTime(PropertyArrayDateTime arr, Calendar value, long time) {
        PropertyAtomDateTime ptime = (PropertyAtomDateTime) arr.add();
        ptime.setDateTime(value, time);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "appendConceptReference",
        synopsis = "Creates a new <code>PropertyAtomConceptReference</code> set to <code>instance</code> with initial history timestamp time\nand adds it to the end of the PropertyArray <code>arr</code>.\nOr by doing instanceX.arr[instanceX.arr@length] = instance, the engine uses system current time.",
        signature = "void appendConceptReference (PropertyArrayConceptReference arr, Concept instance, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray to append an element to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "A value to append to the PropertyArray <code>arr</code>."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The initial history timestamp.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a new <code>PropertyAtomConceptReference</code> set to <code>instance</code> with initial history\ntimestamp time and adds it to the end of the PropertyArray <code>arr</code>. Use\ninstanceY.arr[instanceY.arr@length] = instanceX for appending with system current time.",
        cautions = "Use instanceY.arr[instanceY.arr@length] = instanceX for appending with system current time",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void appendConceptReference(PropertyArrayConceptReference arr, Concept instance, long time) {
        PropertyAtomConceptReference cref = (PropertyAtomConceptReference) arr.add();
        cref.setConcept(instance, time);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "appendContainedConcept",
        synopsis = "Creates a new <code>PropertyAtomContainedConcept</code> set to <code>instance</code> with initial history timestamp time\nand adds it to the end of the PropertyArray <code>arr</code>.\nOr by doing instanceX.arr[instanceX.arr@length] = instance, the engine uses system current time.",
        signature = "void appendContainedConcept (PropertyArrayContainedConcept arr, ContainedConcept instance, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A <code>PropertyArray</code> to append an element to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "ContainedConcept", desc = "A value to append to the <code>PropertyArray arr</code>."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The initial history timestamp.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a new <code>PropertyAtomContainedConcept</code> set to <code>instance</code> with initial history timestamp time\nand adds it to the end of the PropertyArray <code>arr</code>. Use\ninstanceY.arr[instanceY.arr@length] = instanceX for appending with system current time.<br/>Objects those are markes for delete or deleted should not be appended.",
        cautions = "Use instanceY.arr[instanceY.arr@length] = instanceX for appending with system current time",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static void appendContainedConcept(PropertyArrayContainedConcept arr, ContainedConcept instance, long time) {
        PropertyAtomContainedConcept pcon = (PropertyAtomContainedConcept) arr.add();
        pcon.setContainedConcept(instance, time);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "indexOfBoolean",
        synopsis = "Returns the index in the PropertyArrayBoolean of the first occurrence of the specified boolean value,\nor -1 if the PropertyArrayBoolean does not contain this boolean value.",
        signature = "int indexOfBoolean(PropertyArrayBoolean arr, boolean value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayBoolean", desc = "The <code>PropertyArrayBoolean</code> for searching."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "boolean", desc = "The specified <code>boolean</code> value to search for.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The index of <code>value</code> in the given PropertyArrayBoolean."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the index in the PropertyArrayBoolean of the first occurrence of the specified boolean value,\nor -1 if the PropertyArrayBoolean does not contain this boolean value.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int indexOfBoolean(PropertyArrayBoolean arr, boolean value) {
        for(int i = 0; i < arr.length(); i++) {
            if(((PropertyAtomBoolean)arr.get(i)).getBoolean() == value) return i;
        }
        return -1;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "indexOfConceptReference",
        synopsis = "Returns the index in the PropertyArrayConceptReference of the first occurrence of the specified Concept instance,\nor -1 if the PropertyArrayConceptReference does not contain this Concept instance.",
        signature = "int indexOfConceptReference(PropertyArrayConceptReference arr, Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayConceptReference", desc = "The <code>PropertyArrayConceptReference</code> for searching."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The specified <code>Concept instance</code> to search for.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The index of <code>value</code> in the given PropertyArrayConceptReference."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the index in the PropertyArrayConceptReference of the first occurrence of the specified Concept instance,\nor -1 if the PropertyArrayConceptReference does not contain this Concept instance.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int indexOfConceptReference(PropertyArrayConceptReference arr, Concept instance) {
        for(int i = 0; i < arr.length(); i++) {
            if(((PropertyAtomConceptReference)arr.get(i)).getConceptId() == instance.getId()) return i;
        }
        return -1;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "indexOfContainedConcept",
        synopsis = "Returns the index in the PropertyArrayContainedConcept of the first occurrence of the specified ContainedConcept instance,\nor -1 if the PropertyArrayContainedConcept does not contain this ContainedConcept instance.",
        signature = "int indexOfContainedConcept(PropertyArrayContainedConcept arr, ContainedConcept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayContainedConcept", desc = "The <code>PropertyArrayContainedConcept</code> for searching."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "ContainedConcept", desc = "The specified <code>ContainedConcept instance</code> to search for.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The index of <code>value</code> in the given PropertyArrayContainedConcept."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the index in the PropertyArrayContainedConcept of the first occurrence of the specified ContainedConcept instance,\nor -1 if the PropertyArrayContainedConcept does not contain this ContainedConcept instance.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int indexOfContainedConcept(PropertyArrayContainedConcept arr, ContainedConcept instance) {
        for(int i = 0; i < arr.length(); i++) {
            if(((PropertyAtomContainedConcept)arr.get(i)).getContainedConceptId() == instance.getId()) return i;
        }
        return -1;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "indexOfDateTime",
        synopsis = "Returns the index in the PropertyArrayDateTime of the first occurrence of the specified DateTime value,\nor -1 if the PropertyArrayDateTime does not contain this DateTime value.",
        signature = "int indexOfDateTime(PropertyArrayDateTime arr, DateTime value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayDateTime", desc = "The <code>PropertyArrayDateTime</code> for searching."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "DateTime", desc = "The specified <code>DateTime</code> value to search for.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The index of <code>value</code> in the given PropertyArrayDateTime."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the index in the PropertyArrayDateTime of the first occurrence of the specified DateTime value,\nor -1 if the PropertyArrayDateTime does not contain this DateTime value.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int indexOfDateTime(PropertyArrayDateTime arr, Calendar value) {
        for(int i = 0; i < arr.length(); i++) {
            if(((PropertyAtomDateTime)arr.get(i)).getDateTime().equals(value)) return i;
        }
        return -1;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "indexOfDouble",
        synopsis = "Returns the index in the PropertyArrayDouble of the first occurrence of the specified double value,\nor -1 if the PropertyArrayDouble does not contain this double value.",
        signature = "int indexOfDouble(PropertyArrayDouble arr, double value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayDouble", desc = "The <code>PropertyArrayDouble</code> for searching."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "double", desc = "The specified <code>double</code> value to search for.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The index of <code>value</code> in the given PropertyArrayDouble."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the index in the PropertyArrayDouble of the first occurrence of the specified double value,\nor -1 if the PropertyArrayDouble does not contain this double value.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int indexOfDouble(PropertyArrayDouble arr, double value) {
        for(int i = 0; i < arr.length(); i++) {
            if(((PropertyAtomDouble)arr.get(i)).getDouble() ==value) return i;
        }
        return -1;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "indexOfInt",
        synopsis = "Returns the index in the PropertyArrayInt of the first occurrence of the specified int value,\nor -1 if the PropertyArrayInt does not contain this int value.",
        signature = "int indexOfInt(PropertyArrayInt arr, int value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayInt", desc = "The <code>PropertyArrayInt</code> for searching."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "int", desc = "The specified <code>int</code> value to search for.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The index of <code>value</code> in the given PropertyArrayInt."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the index in the PropertyArrayInt of the first occurrence of the specified int value,\nor -1 if the PropertyArrayInt does not contain this int value.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int indexOfInt(PropertyArrayInt arr, int value) {
        for(int i = 0; i < arr.length(); i++) {
            if(((PropertyAtomInt)arr.get(i)).getInt() == value) return i;
        }
        return -1;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "indexOfLong",
        synopsis = "Returns the index in the PropertyArrayLong of the first occurrence of the specified long value,\nor -1 if the PropertyArrayLong does not contain this long value.",
        signature = "int indexOfLong(PropertyArrayLong arr, long value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayLong", desc = "The <code>PropertyArrayLong</code> for searching."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "long", desc = "The specified <code>long</code> value to search for.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The index of <code>value</code> in the given PropertyArrayLong."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the index in the PropertyArrayLong of the first occurrence of the specified long value,\nor -1 if the PropertyArrayLong does not contain this long value.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int indexOfLong(PropertyArrayLong arr, long value) {
        for(int i = 0; i < arr.length(); i++) {
            if(((PropertyAtomLong)arr.get(i)).getLong() == value) return i;
        }
        return -1;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "indexOfString",
        synopsis = "Returns the index in the PropertyArrayString of the first occurrence of the specified String value,\nor -1 if the PropertyArrayString does not contain this String value.",
        signature = "int indexOfString(PropertyArrayString arr, String value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayString", desc = "The <code>PropertyArrayString</code> for searching."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "String", desc = "specified <code>String</code> value to search for.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The index of <code>value</code> in the given PropertyArrayString."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the index in the PropertyArrayString of the first occurrence of the specified String value,\nor -1 if the PropertyArrayString does not contain this String value.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int indexOfString(PropertyArrayString arr, String value) {
        for(int i = 0; i < arr.length(); i++) {
            if(((PropertyAtomString)arr.get(i)).getString().equals(value)) return i;
        }
        return -1;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "removeBoolean",
        synopsis = "Removes the first occurrence of PropertyAtomBoolean for which the current value equals\nto <code>value</code> from a PropertyArrayBoolean.  It returns true and shifts all the\nsubsequent array elements PropertyAtomBoolean to the left (substracts one to their indices)\nif the PropertyAtomBoolean is found, false otherwise.",
        signature = "boolean removeBoolean (PropertyArrayBoolean arr, boolean value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayBoolean", desc = "A PropertyArrayBoolean for the action to be performed on."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "boolean", desc = "Current value of the PropertyAtomBoolean for removal.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "if a PropertyAtomBoolean is removed."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method removes the first occurrence of PropertyAtomBoolean for which the current value\nequals to <code>value</code> from a PropertyArrayBoolean.  It returns true and shifts all the\nsubsequent array elements PropertyAtomBoolean to the left (substracts one to their indices)\nif the PropertyAtomBoolean is found, false otherwise.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean removeBoolean(PropertyArrayBoolean arr, boolean value) {
        return arr.removePropertyAtom(value) != null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "removeConceptReference",
        synopsis = "Removes the first occurrence of PropertyAtomConceptReference for which the current value is set to\n<code>instance</code> from a PropertyArrayConceptReference.  It returns true and shifts all the\nsubsequent array elements PropertyAtomConceptReference to the left (substracts one to their indices)\nif the PropertyAtomConceptReference is found, false otherwise.",
        signature = "boolean removeConceptReference (PropertyArrayConceptReference arr, Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayConceptReference", desc = "A PropertyArrayConceptReference for the action to be performed on."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "Current value of the PropertyAtomConceptReference for removal.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "if a PropertyAtomConceptReference is removed."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method removes the first occurrence of PropertyAtomConceptReference for which the current value\nis set to <code>instance</code> from a PropertyArrayConceptReference.  It returns true and shifts all the\nsubsequent array elements PropertyAtomConceptReference to the left (substracts one to their indices)\nif the PropertyAtomConceptReference is found, false otherwise.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean removeConceptReference(PropertyArrayConceptReference arr, Concept instance) {
        return arr.removePropertyAtom(instance) != null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "removeContainedConcept",
        synopsis = "Removes the first occurrence of PropertyAtomContainedConcept for which the current value is set to\n<code>instance</code> from a PropertyArrayContainedConcept.  It returns true and shifts all the\nsubsequent array elements PropertyAtomContainedConcept to the left (substracts one to their indices)\nif the PropertyAtomContainedConcept is found, false otherwise. Also, the concept instance is deleted from cache \nat the end of RTC with cache only mode.",
        signature = "boolean removeContainedConcept (PropertyArrayContainedConcept arr, ContainedConcept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayContainedConcept", desc = "A PropertyArrayContainedConcept for the action to be performed on."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "ContainedConcept", desc = "Current value of the PropertyAtomContainedConcept for removal.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "if a PropertyAtomContainedConcept is removed."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method removes the first occurrence of PropertyAtomContainedConcept for which the current value\nis set to <code>instance</code> from a PropertyArrayContainedConcept.  It returns true and shifts all the\nsubsequent array elements PropertyAtomContainedConcept to the left (substracts one to their indices)\nif the PropertyAtomContainedConcept is found, false otherwise. Also, the concept <code>instance</code> is deleted from cache \nat the end of RTC with cache only mode.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean removeContainedConcept(PropertyArrayContainedConcept arr, ContainedConcept instance) {
        return arr.removePropertyAtom(instance) != null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "removeDateTime",
        synopsis = "Removes the first occurrence of PropertyAtomDateTime for which the current value equals\nto <code>value</code> from a PropertyArrayDateTime.  It returns true and shifts all the\nsubsequent array elements PropertyAtomDateTime to the left (substracts one to their indices)\nif no PropertyAtomDateTime is found, false otherwise.",
        signature = "boolean removeDateTime (PropertyArrayDateTime arr, DateTime value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayDateTime", desc = "A PropertyArrayDateTime for the action to be performed on."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "DateTime", desc = "Current value of the PropertyAtomDateTime for removal.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "if a PropertyAtomDateTime is removed."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method removes the first occurrence of PropertyAtomDateTime for which the current value\nequals to <code>value</code> from a PropertyArrayDateTime.  It returns true and shifts all the\nsubsequent array elements PropertyAtomDateTime to the left (substracts one to their indices)\nif the PropertyAtomDateTime is found, false otherwise.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean removeDateTime(PropertyArrayDateTime arr, Calendar value) {
        return arr.removePropertyAtom(value) != null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "removeDouble",
        synopsis = "Removes the first occurrence of PropertyAtomDouble for which the current value equals\nto <code>value</code> from a PropertyArrayDouble.  It returns true and shifts all the\nsubsequent array elements PropertyAtomDouble to the left (substracts one to their indices)\nif the PropertyAtomDouble is found, false otherwise.",
        signature = "boolean removeDouble (PropertyArrayDouble arr, double value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayDouble", desc = "A PropertyArrayDouble for the action to be performed on."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "double", desc = "Current value of the PropertyAtomDouble for removal.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "if a PropertyAtomDouble is removed."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method removes the first occurrence of PropertyAtomDouble for which the current value\nequals to <code>value</code> from a PropertyArrayDouble.  It returns true and shifts all the\nsubsequent array elements PropertyAtomDouble to the left (substracts one to their indices)\nif the PropertyAtomDouble is found, false otherwise.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean removeDouble(PropertyArrayDouble arr, double value) {
        return arr.removePropertyAtom(value) != null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "removeInt",
        synopsis = "Removes the first occurrence of PropertyAtomInt for which the current value equals\nto <code>value</code> from a PropertyArrayInt.  It returns true and shifts all the\nsubsequent array elements PropertyAtomInt to the left (substracts one to their indices)\nif the PropertyAtomInt is found, false otherwise.",
        signature = "boolean removeInt (PropertyArrayInt arr, int value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayInt", desc = "A PropertyArrayInt for the action to be performed on."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "int", desc = "Current value of the PropertyAtomInt for removal.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "if a PropertyAtomInt is removed."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method removes the first occurrence of PropertyAtomInt for which the current value\nequals to <code>value</code> from a PropertyArrayInt.  It returns true and shifts all the\nsubsequent array elements PropertyAtomInt to the left (substracts one to their indices)\nif the PropertyAtomInt is found, false otherwise.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean removeInt(PropertyArrayInt arr, int value) {
        return arr.removePropertyAtom(value) != null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "removeLong",
        synopsis = "Removes the first occurrence of PropertyAtomLong for which the current value equals\nto <code>value</code> from a PropertyArrayLong.  It returns true and shifts all the\nsubsequent array elements PropertyAtomLong to the left (substracts one to their indices)\nif the PropertyAtomLong is found, false otherwise.",
        signature = "boolean removeLong (PropertyArrayLong arr, long value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayLong", desc = "A PropertyArrayLong for the action to be performed on."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "long", desc = "Current value of the PropertyAtomLong for removal.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "if a PropertyAtomLong is removed."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method removes the first occurrence of PropertyAtomLong for which the current value\nequals to <code>value</code> from a PropertyArrayLong.  It returns true and shifts all the\nsubsequent array elements PropertyAtomLong to the left (substracts one to their indices)\nif the PropertyAtomLong is found, false otherwise.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean removeLong(PropertyArrayLong arr, long value) {
        return arr.removePropertyAtom(value) != null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "removeString",
        synopsis = "Removes the first occurrence of PropertyAtomString for which the current value equals\nto <code>value</code> from a PropertyArrayString.  It returns true and shifts all the\nsubsequent array elements PropertyAtomString to the left (substracts one to their indices)\nif the PropertyAtomString is found, false otherwise.",
        signature = "boolean removeString (PropertyArrayString arr, String value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayString", desc = "A PropertyArrayString for the action to be performed on."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "String", desc = "Current value of the PropertyAtomString for removal.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "if a PropertyAtomString is removed."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method removes the first occurrence of PropertyAtomString for which the current value\nequals to <code>value</code> from a PropertyArrayString.  It returns true and shifts all the\nsubsequent array elements PropertyAtomString to the left (substracts one to their indices)\nif the PropertyAtomString is found, false otherwise.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean removeString(PropertyArrayString arr, String value) {
        return arr.removePropertyAtom(value) != null;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "toArrayBoolean",
        synopsis = "Returns a boolean[] containing all of the boolean values in a PropertyArrayBoolean in the correct order",
        signature = "boolean[] toArrayBoolean(PropertyArrayBoolean arr)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayBoolean", desc = "A PropertyArray of boolean type.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean[]", desc = "A boolean array."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a boolean[] containing all of the boolean values in a PropertyArrayBoolean in the correct order",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static boolean[] toArrayBoolean(PropertyArrayBoolean arr) {
        int len = arr.length();
        if(len == 0) {
            return new boolean[0];
        }
        else {
            boolean[] ret = new boolean[len];
            for(int i = 0; i < len; i++) {
                ret[i] = ((PropertyAtomBoolean)arr.get(i)).getBoolean();
            }
            return ret;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "toArrayConcept",
        synopsis = "Returns a Concept[] containing all of the instances in a PropertyArrayConcept in the correct order",
        signature = "Concept[] toArrayConcept(PropertyArrayConcept arr)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayConcept", desc = "A PropertyArray of type ConceptReference or ContainedConcept.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept[]", desc = "A Concept array containing all of the instances in PropertyArrayConcept in the correct order."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a Concept[] containing all of the instances in a PropertyArrayConcept in the correct order",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Concept[] toArrayConcept(PropertyArrayConcept arr) {
        int len = arr.length();
        if(len == 0) {
            return makeConceptArray(arr.getType(), len);
        }
        else {
            Concept[] ret = makeConceptArray(arr.getType(), len);
            for(int i = 0; i < len; i++) {
                ret[i] = ((PropertyAtomConcept)arr.get(i)).getConcept();
            }
            return ret;
        }
    }

    private static Concept[] makeConceptArray(Class typeClass, int len) {
        if(typeClass == null || !Concept.class.isAssignableFrom(typeClass)) {
            typeClass = Concept.class;
        }
        return (Concept[])Array.newInstance(typeClass, len);
    }

    private static ContainedConcept[] makeContainedConceptArray(Class typeClass, int len) {
        if(typeClass == null || !ContainedConcept.class.isAssignableFrom(typeClass)) {
            typeClass = Concept.class;
        }
        return (ContainedConcept[])Array.newInstance(typeClass, len);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "toArrayContainedConcept",
        synopsis = "Returns a ContainedConcept[] containing all of the instances in a PropertyArrayContainedConcept in the correct order",
        signature = "ContainedConcept[] toArrayContainedConcept(PropertyArrayContainedConcept arr)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayContainedConcept", desc = "A PropertyArray of ContainedConcept type.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "ContainedConcept[]", desc = "A ContainedConcept array containing all of the instances in PropertyArrayContainedConcept in the correct order."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a ContainedConcept[] containing all of the instances in a PropertyArrayContainedConcept in the correct order",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static ContainedConcept[] toArrayContainedConcept(PropertyArrayContainedConcept arr) {
        int len = arr.length();
        if(len == 0) {
            return makeContainedConceptArray(arr.getType(), len);
        }
        else {
            ContainedConcept[] ret = makeContainedConceptArray(arr.getType(), len);
            for(int i = 0; i < len; i++) {
                ret[i] = ((PropertyAtomContainedConcept)arr.get(i)).getContainedConcept();
            }
            return ret;
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "toArrayDateTime",
        synopsis = "Returns a DateTime[] containing all of the DateTime values in a PropertyArrayDateTime in the correct order",
        signature = "DateTime[] toArrayDateTime(PropertyArrayDateTime arr)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArrayDateTime", desc = "A PropertyArray of DateTime type.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime[]", desc = "A DateTime array."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a DateTime[] containing all of the DateTime values in a PropertyArrayDateTime in the correct order",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Calendar[] toArrayDateTime(PropertyArrayDateTime arr) {
        int len = arr.length();
        if(len == 0) {
            return new Calendar[0];
        }
        else {
            Calendar[] ret = new Calendar[len];
            for(int i = 0; i < len; i++) {
                ret[i] = ((PropertyAtomDateTime)arr.get(i)).getDateTime();
            }
            return ret;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "toArrayDouble",
        synopsis = "Returns a double[] containing all of the double values in a PropertyArray in the correct order.",
        signature = "double[] toArrayDouble(PropertyArray arr)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray of type double, int, or long.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double[]", desc = "A double array.  Returns null if pass in Array type can't be casted to double."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a double[] containing all of the values in a PropertyArray in the correct order",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static double[] toArrayDouble(PropertyArray arr) {
        if( (arr instanceof PropertyArrayDouble)) {
            int len = arr.length();
            if(len == 0) {
                return new double[0];
            }
            else {
                double[] ret = new double[len];
                for(int i = 0; i < len; i++) {
                    ret[i] = ((PropertyAtomDouble)arr.get(i)).getDouble();
                }
                return ret;
            }
        }
        else if (arr instanceof PropertyArrayInt) {
            int len = arr.length();
            if(len == 0) {
                return new double[0];
            }
            else {
                double[] ret = new double[len];
                for(int i = 0; i < len; i++) {
                    ret[i] = ((PropertyAtomInt)arr.get(i)).getInt();
                }
                return ret;
            }
        }
        else if (arr instanceof PropertyArrayLong) {
            int len = arr.length();
            if(len == 0) {
                return new double[0];
            }
            else {
                double[] ret = new double[len];
                for(int i = 0; i < len; i++) {
                    ret[i] = ((PropertyAtomLong)arr.get(i)).getLong();
                }
                return ret;
            }
        }
        else {
            return null;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "toArrayInt",
        synopsis = "Returns a int[] containing all of the int values in a PropertyArray in the correct order",
        signature = "int[] toArrayInt(PropertyArray arr)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray of type double, int, or long.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int[]", desc = "A int array.  Returns null if pass in Array type can't be casted to int."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a int[] containing all of the values in a PropertyArray in the correct order",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static int[] toArrayInt(PropertyArray arr) {
        if (arr instanceof PropertyArrayInt) {
            int len = arr.length();
            if(len == 0) {
                return new int[0];
            }
            else {
                int[] ret = new int[len];
                for(int i = 0; i < len; i++) {
                    ret[i] = ((PropertyAtomInt)arr.get(i)).getInt();
                }
                return ret;
            }
        }
        else if (arr instanceof PropertyArrayLong) {
            int len = arr.length();
            if(len == 0) {
                return new int[0];
            }
            else {
                int[] ret = new int[len];
                for(int i = 0; i < len; i++) {
                    ret[i] = (int)((PropertyAtomLong)arr.get(i)).getLong();
                }
                return ret;
            }
        }
        else if( (arr instanceof PropertyArrayDouble)) {
            int len = arr.length();
            if(len == 0) {
                return new int[0];
            }
            else {
                int[] ret = new int[len];
                for(int i = 0; i < len; i++) {
                    ret[i] = (int)((PropertyAtomDouble)arr.get(i)).getDouble();
                }
                return ret;
            }
        }
        else {
            return null;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "toArrayLong",
        synopsis = "Returns a long[] containing all of the long values in a PropertyArray in the correct order",
        signature = "long[] toArrayLong(PropertyArray arr)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "A PropertyArray of type double, int, or long.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long[]", desc = "A long array.  Returns null if pass in Array type can't be casted to long."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a long[] containing all of the values in a PropertyArray in the correct order",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static long[] toArrayLong(PropertyArray arr) {
        if (arr instanceof PropertyArrayLong) {
            int len = arr.length();
            if(len == 0) {
                return new long[0];
            }
            else {
                long[] ret = new long[len];
                for(int i = 0; i < len; i++) {
                    ret[i] = ((PropertyAtomLong)arr.get(i)).getLong();
                }
                return ret;
            }
        }
        else if (arr instanceof PropertyArrayInt) {
            int len = arr.length();
            if(len == 0) {
                return new long[0];
            }
            else {
                long[] ret = new long[len];
                for(int i = 0; i < len; i++) {
                    ret[i] = ((PropertyAtomInt)arr.get(i)).getInt();
                }
                return ret;
            }
        }
        else if( (arr instanceof PropertyArrayDouble)) {
            int len = arr.length();
            if(len == 0) {
                return new long[0];
            }
            else {
                long[] ret = new long[len];
                for(int i = 0; i < len; i++) {
                    ret[i] = (long)((PropertyAtomDouble)arr.get(i)).getDouble();
                }
                return ret;
            }
        }
        else {
            return null;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "toArrayString",
        synopsis = "Returns a String[] containing all of the String values in a PropertyArray in the correct order",
        signature = "String[] toArrayString(PropertyArray arr)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "arr", type = "PropertyArray", desc = "Any of the PropertyArray type e.g. PropertyArrayString, PropertyArrayInt, etc...")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "A String array."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a String[] containing all of the values in a PropertyArray in the correct order",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static String[] toArrayString(PropertyArray arr) {
        int len = arr.length();
        if(len == 0) {
            return new String[0];
        }
        else {
            String[] ret = new String[len];
            for(int i = 0; i < len; i++) {
                ret[i] = ((PropertyAtom)arr.get(i)).getString();
            }
            return ret;
        }
    }
}
