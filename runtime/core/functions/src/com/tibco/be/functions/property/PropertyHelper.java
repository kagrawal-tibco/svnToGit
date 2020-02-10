package com.tibco.be.functions.property;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

import com.tibco.cep.runtime.model.element.*;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.session.RuleSessionManager;

import java.util.Calendar;

/**
 * User: nleong
 * Date: Sep 14, 2004
 * Time: 1:35:10 PM
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Instance.PropertyAtom",
        synopsis = "Utility functions to operate on PropertyAtom properties")

public class PropertyHelper {
    @com.tibco.be.model.functions.BEFunction(
        name = "isSet",
        synopsis = "This method checks if the property has ever been set.",
        enabled = @Enabled(value=false),
        signature = "boolean isSet (PropertyAtom propertyAtom)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtom", type = "PropertyAtom", desc = "The property to check for the existence of.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the property has been set, otherwise returns false."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method checks if the property has been set ever.",
        cautions = "This function will return true even if the property <code>propertyAtom</code> is explicitly set to null.",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )

    public static boolean isSet(PropertyAtom propertyAtom) {
        return propertyAtom.isSet();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setValueAt",
        synopsis = "Sets the value of the PropertyAtom to the value passed with the time stamp specified.",
        signature = "boolean setValueAt (PropertyAtom pa, Object value, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The property atom to set a new value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "The new value for the property. Its type has to be matched the property type."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The time stamp describing when this property changed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if property is set to new value with the given time, false otherwise."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the value of the PropertyAtom to the value passed with the time stamp specified.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean setValueAt(PropertyAtom pa, Object value, long time) {
        return pa.setValue(value, time);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setPropertyValue",
        synopsis = "Sets the value of the PropertyAtom to the value passed.",
        signature = "boolean setPropertyValue (PropertyAtom pa, Object value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The property atom to set a new value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "The new value for the property. Its type has to be matched the property type.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if property is set to new value with the given time, false otherwise."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the value of the PropertyAtom to the value passed.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean setPropertyValue(PropertyAtom pa, Object value) {
        return pa.setValue(value);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "numHistoryValues",
        synopsis = "This method returns how many history values exist for given property.",
        signature = "int numHistoryValues(PropertyAtom pa)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The property to get the history value count on.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Number of history values that exist."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns how many history values exist for given property.\nThe value is bounded by history size.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int numHistoryValues(PropertyAtom pa) {
        try {
           return ((PropertyAtom)pa).howMany(0, 0);
        } catch (Exception e) {
            // should never happen though
        }
        return 0;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "historySize",
        synopsis = "This method returns the size of the history buffer as specified in the design environment.",
        signature = "int historySize(PropertyAtom pa)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pa", type = "PropertyAtom", desc = "The property to get the history size from.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Size of the history buffer."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method returns the size of the history buffer as specified in the design environment.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int historySize(PropertyAtom pa) {
        if(pa == null) return 0;
        else return pa.getHistorySize();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getValueAt",
        synopsis = "Gets the value of the PropertyAtom at a particular time.",
        signature = "Object getValueAt(PropertyAtom propertyAtom, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtom", type = "PropertyAtom", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "With history tracking enabled, a property's value can change over time. The function returns the value of the property at the specified time. For example to get the current property value, use System.currentTimeMillis().")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "the result.\nMeaning get the property value at the given time."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtom passed, at the time stamp specified.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object getValueAt(PropertyAtom propertyAtom, long time) {
        try {
            return propertyAtom.getValue(time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getPropertyValue",
        synopsis = "Gets the value of a PropertyAtom.",
        signature = "Object getPropertyValue(PropertyAtom propertyAtom)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtom", type = "PropertyAtom", desc = "The property atom to get the value for.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "the result."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the current value of a PropertyAtom.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object getPropertyValue(PropertyAtom propertyAtom) {
        try {
            return propertyAtom.getValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getHistoryValue",
        synopsis = "Gets the value of given PropertyAtom, at specified index in the ring buffer.\nOldest value at index 0.",
        signature = "Object getHistoryValue(PropertyAtom propertyAtom, int idx)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtom", type = "PropertyAtom", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "idx", type = "int", desc = "Index of the value needed in the ring buffer.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The result, meaning get the property value at the given index."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtom passed, at the specified index in the ring buffer.\noldest value is at index 0.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Object getHistoryValue(PropertyAtom propertyAtom, int idx) {
        try {
            return propertyAtom.getValueAtIdx(idx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getHistoryTime",
        synopsis = "Get the history timestamp of this PropertyAtom value for a given index. Returns 0 if the index has not yet been used.",
        signature = "long getHistoryTime(PropertyAtom propertyAtom, int idx)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtom", type = "PropertyAtom", desc = "The property atom to get the timestamp for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "idx", type = "Index", desc = "of the value timestamp is needed for.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "the timestamp of the PropertyAtom."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the history timestamp of this PropertyAtom value for a given index. Returns 0 if the index has not yet been used.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long getHistoryTime(PropertyAtom propertyAtom, int idx) {
        try {
            return propertyAtom.getTimeAtIdx(idx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setBoolean",
        synopsis = "Sets the value of the PropertyAtom to the value passed with the time stamp specified.",
        signature = "boolean setBoolean (PropertyAtomBoolean pab, boolean value, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pab", type = "PropertyAtomBoolean", desc = "The property atom to set a new value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "boolean", desc = "The new value for the property."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The time stamp describing when this property changed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if property is set to new value with the given time, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the value of the PropertyAtom to the value passed with the time stamp specified.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean setBoolean(PropertyAtomBoolean pab, boolean value, long time) {
        return pab.setBoolean(value, time);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getBoolean",
        synopsis = "Gets the value of the PropertyAtomBoolean at a particular time.",
        signature = "boolean getBoolean (PropertyAtomBoolean propertyAtomBoolean, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtomBoolean", type = "PropertyAtomBoolean", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "With history tracking enabled, a property's value can change over time. The function returns the value of the property at the specified time. For example to get the current property value, use System.currentTimeMillis().")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "The result, meaning get the property value at the given time."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomBoolean passed, at the time stamp specified.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean getBoolean(PropertyAtomBoolean propertyAtomBoolean, long time) {
        try {
            return propertyAtomBoolean.getBoolean(time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getBooleanHistoryValue",
        synopsis = "Gets the value of given PropertyAtomBoolean, at specified index in the ring buffer.\nOldest value at index 0.",
        signature = "boolean getBooleanHistoryValue (PropertyAtomBoolean propertyAtomBoolean, int idx)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtomBoolean", type = "PropertyAtomBoolean", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "idx", type = "int", desc = "Index of the value needed in the ring buffer.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "The result, meaning get the property value at the given index."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomBoolean passed, at the specified index in the ring buffer.\noldest value is at index 0.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean getBooleanHistoryValue(PropertyAtomBoolean propertyAtomBoolean, int idx) {
        try {
            return propertyAtomBoolean.getBooleanAtIdx(idx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "setDateTime",
        synopsis = "Sets the value of the PropertyAtom to the value passed with the time stamp specified.",
        signature = "boolean setDateTime (PropertyAtomDateTime padt, DateTime value, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "padt", type = "PropertyAtomDateTime", desc = "The property atom to set a new value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "DateTime", desc = "The new value for the property."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The time stamp describing when this property changed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if property is set to new value with the given time, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the value of the PropertyAtom to the value passed with the time stamp specified.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean setDateTime(PropertyAtomDateTime padt, Calendar value, long time) {
        return padt.setDateTime(value, time);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDateTime",
        synopsis = "Gets the value of the PropertyAtomDateTime at a particular time.",
        signature = "DateTime getDateTime (PropertyAtomDateTime propertyAtomDateTime, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtomDateTime", type = "PropertyAtomDateTime", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "With history tracking enabled, a property's value can change over time. The function returns the value of the property at the specified time. For example to get the current property value, use System.currentTimeMillis().")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "the result.\nMeaning get the property value at the given time."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomDateTime passed, at the time stamp specified.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar getDateTime(PropertyAtomDateTime propertyAtomDateTime, long time) {
        try {
            return propertyAtomDateTime.getDateTime(time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDateTimeHistoryValue",
        synopsis = "Gets the value of given PropertyAtomDateTime, at specified index in the ring buffer in the ring buffer.\nOldest value at index 0.",
        signature = "DateTime getDateTimeHistoryValue (PropertyAtomDateTime propertyAtomDateTime, int idx)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtomDateTime", type = "PropertyAtomDateTime", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "idx", type = "int", desc = "Index of the value needed in the ring buffer in the ring buffer.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "the result."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomDateTime passed, at the specified index in the ring buffer.\nOldest value is at index 0.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar getDateTimeHistoryValue(PropertyAtomDateTime propertyAtomDateTime, int idx) {
        try {
            return propertyAtomDateTime.getDateTimeAtIdx(idx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setDouble",
        synopsis = "Sets the value of the PropertyAtom to the value passed with the time stamp specified.",
        signature = "boolean setDouble (PropertyAtomDouble pad, double value, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property atom to set a new value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "double", desc = "The new value for the property."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The time stamp describing when this property changed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if property is set to new value with the given time, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the value of the PropertyAtom to the value passed with the time stamp specified.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean setDouble(PropertyAtomDouble pad, double value, long time) {
        return pad.setDouble(value, time);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDouble",
        synopsis = "Gets the value of the PropertyAtomDouble at a particular time.",
        signature = "double getDouble (PropertyAtomDouble propertyAtomDouble, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtomDouble", type = "PropertyAtomDouble", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "With history tracking enabled, a property's value can change over time. The function returns the value of the property at the specified time. For example to get the current property value, use System.currentTimeMillis().")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "the result."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomDouble passed, at the time stamp specified.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double getDouble(PropertyAtomDouble propertyAtomDouble, long time) {
        try {
            return propertyAtomDouble.getDouble(time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDoubleHistoryValue",
        synopsis = "Gets the value of given PropertyAtomDouble, at specified index in the ring buffer.\nOldest value at index 0.",
        signature = "double getDoubleHistoryValue (PropertyAtomDouble propertyAtomDouble, int idx)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtomDouble", type = "PropertyAtomDouble", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "idx", type = "int", desc = "Index of the value needed in the ring buffer.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "the result."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomDouble passed, at the specified index in the ring buffer.\nOldest value is at index 0.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static double getDoubleHistoryValue(PropertyAtomDouble propertyAtomDouble, int idx) {
        try {
            return propertyAtomDouble.getDoubleAtIdx(idx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

/*  @com.tibco.be.model.functions.BEFunction(
        name = "getNextDouble",
        synopsis = "Gets the value of the PropertyAtomDouble at a particular time.",
        signature = "double getNextDouble (PropertyAtomDouble propertyAtomDouble, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pad", type = "PropertyAtomDouble", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "With history tracking enabled, a property's value can change over time. The function returns the value of the property at the specified time. For example to get the current property value, use System.currentTimeMillis().")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = "the result.\nMeaning get the property value at the given time."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of PropertyAtomDouble passed, at the time stamp specified; uses\na static iterator obtained from the object for optimized access.",
        cautions = "As the first call gets a static iterator object from given PropertyAtomDouble\nand sets it in the Helper; getNextDouble calls should not be mixed for different instances.",
        fndomain = {ACTION},
        example = ""
    )
    public static double getDouble(PropertyAtomDouble pad, Iterator iter, PropertyAtom.History h, long time) {
        if (iter == null && h == null) {
            iter = pad.historyIterator();
            if (iter.hasNext()) h = (PropertyAtom.History)iter.next();
            else                return PropertyHelper.getDouble(pad, time); // throw exception from property level
        }

        do {
            if (h.time <= time) return ((Double)h.value).doubleValue();
            else                h = (PropertyAtom.History)iter.next();
        } while (iter.hasNext());
        return PropertyHelper.getDouble(pad, time); // throw exception from property level
    }
*/

    @com.tibco.be.model.functions.BEFunction(
        name = "setInt",
        synopsis = "Sets the value of the PropertyAtom to the value passed with the time stamp specified.",
        signature = "boolean setInt (PropertyAtomInt pai, int value, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pai", type = "PropertyAtomInt", desc = "The property atom to set a new value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "int", desc = "The new value for the property."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The time stamp describing when this property changed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if property is set to new value with the given time, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the value of the PropertyAtom to the value passed with the time stamp specified.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean setInt(PropertyAtomInt pai, int value, long time) {
        return pai.setInt(value, time);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getInt",
        synopsis = "Gets the value of the PropertyAtomInt at a particular time.",
        signature = "int getInt (PropertyAtomInt propertyAtomInt, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtomInt", type = "PropertyAtomInt", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "With history tracking enabled, a property's value can change over time. The function returns the value of the property at the specified time. For example to get the current property value, use System.currentTimeMillis().")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "the result."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomInt passed, at the time stamp specified.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int getInt(PropertyAtomInt propertyAtomInt, long time) {
        try {
            return propertyAtomInt.getInt(time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getIntHistoryValue",
        synopsis = "Gets the value of given PropertyAtomInt, at specified index in the ring buffer.\nOldest value at index 0.",
        signature = "int getIntHistoryValue (PropertyAtomInt propertyAtomInt, int idx)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtomInt", type = "PropertyAtomInt", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "idx", type = "int", desc = "Index of the value needed in the ring buffer.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "the result."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomInt passed, at the specified index in the ring buffer.\nOldest value is at index 0.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int getIntHistoryValue(PropertyAtomInt propertyAtomInt, int idx) {
        try {
            return propertyAtomInt.getIntAtIdx(idx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setLong",
        synopsis = "Sets the value of the PropertyAtom to the value passed with the time stamp specified.",
        signature = "boolean setLong (PropertyAtomLong pal, long value, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pal", type = "PropertyAtomLong", desc = "The property atom to set a new value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "long", desc = "The new value for the property."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The time stamp describing when this property changed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if property is set to new value with the given time, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the value of the <code> propertyAtomLong </code> to the <code> value </code> passed with the time stamp specified.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean setLong(PropertyAtomLong pal, long value, long time) {
        return pal.setLong(value, time);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getLong",
        synopsis = "Gets the value of the PropertyAtomLong at a particular time.",
        signature = "long getLong (PropertyAtomLong propertyAtomLong, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtomLong", type = "PropertyAtomLong", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "With history tracking enabled, a property's value can change over time. The function returns the value of the property at the specified time. For example to get the current property value, use System.currentTimeMillis().")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "the result."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomLong passed, at the time stamp specified.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long getLong(PropertyAtomLong propertyAtomLong, long time) {
        try {
            return propertyAtomLong.getLong(time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getLongHistoryValue",
        synopsis = "Gets the value of given PropertyAtomLong, at specified index in the ring buffer.\nOldest value at index 0.",
        signature = "long getLongHistoryValue (PropertyAtomLong propertyAtomLong, int idx)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtomLong", type = "PropertyAtomLong", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "idx", type = "int", desc = "Index of the value needed in the ring buffer.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "the result."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomLong passed, at the specified index in the ring buffer.\nOldest value is at index 0.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long getLongHistoryValue(PropertyAtomLong propertyAtomLong, int idx) {
        try {
            return propertyAtomLong.getLongAtIdx(idx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setString",
        synopsis = "Sets the value of the PropertyAtom to the value passed with the time stamp specified.",
        signature = "boolean setString (PropertyAtomString pas, String value, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pas", type = "PropertyAtomString", desc = "The property atom to set a new value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "String", desc = "The new value for the property."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The time stamp describing when this property changed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if property is set to new value with the given time, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the value of the PropertyAtom to the value passed with the time stamp specified.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean setString(PropertyAtomString pas, String value, long time) {
        try {
        return pas.setString(value, time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getString",
        synopsis = "Gets the value of the PropertyAtomString at a particular time.",
        signature = "String getString (PropertyAtomString propertyAtomString, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtomString", type = "PropertyAtomString", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "With history tracking enabled, a property's value can change over time. The function returns the value of the property at the specified time. For example to get the current property value, use System.currentTimeMillis().")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "the result."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomString passed, at the time stamp specified.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String getString(PropertyAtomString propertyAtomString, long time) {
        try {
            return propertyAtomString.getString(time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getStringHistoryValue",
        synopsis = "Gets the value of given PropertyAtomString, at specified index in the ring buffer.\nOldest value at index 0.",
        signature = "String getStringHistoryValue (PropertyAtomString propertyAtomString, int idx)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyAtomString", type = "PropertyAtomString", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "idx", type = "int", desc = "Index of the value needed in the ring buffer.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "the result."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomString passed, at the specified index in the ring buffer.\nOldest value is at index 0.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String getStringHistoryValue(PropertyAtomString propertyAtomString, int idx) {
        try {
            return propertyAtomString.getStringAtIdx(idx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setConceptReference",
        synopsis = "Sets the value of the PropertyAtomConceptReference to the value passed with the time stamp specified.",
        signature = "boolean setConceptReference (PropertyAtomConceptReference parc, Concept value, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "parc", type = "PropertyAtomConceptReference", desc = "The property atom to set a new value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Concept", desc = "The new value for the property."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The time stamp describing when this property changed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if property is set to new value with the given time, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the value of the PropertyAtom to the value passed with the time stamp specified.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean setConceptReference(PropertyAtomConceptReference parc, Concept value, long time) {
        return parc.setConcept(value, time);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getConceptReference",
        synopsis = "Gets the value of the PropertyAtomConceptReference at a particular time.",
        signature = "Concept getConceptReference (PropertyAtomConceptReference parc, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "parc", type = "PropertyAtomConceptReference", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "With history tracking enabled, a property's value can change over time. The function returns the value of the property at the specified time. For example to get the current property value, use System.currentTimeMillis().")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The result."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomConceptReference at a particular time.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Concept getConceptReference(PropertyAtomConceptReference parc, long time) {
        try {
            return (Concept)parc.getValue(time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getConceptReferenceHistoryValue",
        synopsis = "Gets the value of given PropertyAtomConceptReference, at specified index in the ring buffer.\nOldest value at index 0.",
        signature = "Concept getConceptReferenceHistoryValue (PropertyAtomConceptReference parc, int idx)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "parc", type = "PropertyAtomConceptReference", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "idx", type = "int", desc = "Index of the value needed in the ring buffer.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The result."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomConceptReference at a given specified index in the ring buffer.\nOldest value is at index 0",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Concept getConceptReferenceHistoryValue(PropertyAtomConceptReference parc, int idx) {
        try {
        	return (Concept)parc.getValueAtIdx(idx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setContainedConcept",
        synopsis = "Sets the value of the PropertyAtomContainedConcept to the value passed with the time stamp specified.",
        signature = "boolean setContainedConcept (PropertyAtomContainedConcept pacc, ContainedConcept value, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pacc", type = "PropertyAtomContainedConcept", desc = "The property atom to set a new value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "ContainedConcept", desc = "The new value for the property."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The time stamp describing when this property changed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if property is set to new value with the given time, false otherwise."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the value of the PropertyAtom to the value passed with the time stamp specified.",
        cautions = "If a new ContainedConcept is set to the current value of PropertyContainedConcept, the\nold one will be retracted automatically from the working memory.",
        fndomain = {ACTION, BUI},
        example = ""
    )
    public static boolean setContainedConcept(PropertyAtomContainedConcept pacc, ContainedConcept value, long time) {
        return pacc.setContainedConcept(value, time);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getContainedConcept",
        synopsis = "Gets the value of the PropertyAtomContainedConcept at a particular time.",
        signature = "Concept getContainedConcept (PropertyAtomContainedConcept pacc, long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pacc", type = "PropertyAtomContainedConcept", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "With history tracking enabled, a property's value can change over time. The function returns the value of the property at the specified time. For example to get the current property value, use System.currentTimeMillis().")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The result."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomContainedConcept at a particular time.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Concept getContainedConcept(PropertyAtomContainedConcept pacc, long time) {
        try {
            return (Concept)pacc.getValue(time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getContainedConceptHistoryValue",
        synopsis = "Gets the value of given PropertyAtomContainedConcept, at specified index in the ring buffer.\nOldest value at index 0.",
        signature = "Concept getContainedConceptHistoryValue (PropertyAtomContainedConcept pacc, int idx)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pacc", type = "PropertyAtomContainedConcept", desc = "The property atom to get the value for."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "idx", type = "int", desc = "Index of the value needed in the ring buffer.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The result."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the PropertyAtomContainedConcept at a specified index in the ring buffer.\nOldest value is at index 0.",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Concept getContainedConceptHistoryValue(PropertyAtomContainedConcept pacc, int idx) {
        try {
            return (Concept)pacc.getValueAtIdx(idx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "isPropertyNull",
        synopsis = "This function Returns true if property is set else returns false.",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.instance.property.check.null.value", value=false),
        signature = "boolean isPropertyNull(Concept cept, String propertyName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cept", type = "Concept", desc = "Concept instance"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "Name of the property")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns true if property is set else returns false."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This function Returns true if property is set else returns false",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
	public static boolean isPropertyNull(Concept cept, String propertyName) {
		if(cept == null) {
			return true;
		}

		if(cept instanceof ConceptImpl) {
			Property prop =  ((ConceptImpl) cept).getPropertyNullOK(propertyName);
			if(prop != null){
				return false;
			}
		}
		return true;
	}
}
