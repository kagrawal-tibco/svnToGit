/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.functions.coherence.constants;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.model.functions.Enabled;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomDateTimeSimple;

import java.util.Calendar;
import java.util.GregorianCalendar;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Coherence",
        category = "Coherence.Constants",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.coherence",value=false),
        synopsis = "Functions for querying the Cache")
public class CoherenceConstantFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "C_StringConstant",
        synopsis = "Return the Wrapper for a String",
        signature = "Object C_StringConstant(String value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "String", desc = "Constant value to be returned")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the Wrapper for a String",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_StringConstant(String value) {
        try {
            return value;   //todo??
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_IntConstant",
        synopsis = "Return the Wrapper for a int",
        signature = "Object C_IntConstant(int value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "int", desc = "Constant value to be returned")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the Wrapper for a int",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_IntConstant(int value) {
        try {
            return new Integer(value);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_DoubleConstant",
        synopsis = "Return the Wrapper for a Double",
        signature = "Object C_DoubleConstant(double value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "double", desc = "Constant value to be returned")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the Wrapper for a Double",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )

    public static Object C_DoubleConstant(double value) {
        try {
            return new Double(value);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_LongConstant",
        synopsis = "Return the Wrapper for a Long",
        signature = "Object C_LongConstant(long value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "long", desc = "Constant value to be returned")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the Wrapper for a Long",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_LongConstant(long value) {
        try {
            return new Long(value);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "C_DateTimeConstant",
        synopsis = "Return the Wrapper for a DateTime. The DateTime format is yyyy-MM-dd HH:mm:ss z",
        signature = "Object C_DateTimeConstant(String date)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "String", desc = "Constant value to be returned")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the Wrapper for a DateTime. The DateTime format is yyyy-MM-dd HH:mm:ss z",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Object C_DateTimeConstant(String date) {
        try {
            return PropertyAtomDateTimeSimple.objectToDateTime(date);
        }
        catch (Exception e) {
            try {
                final long ms = Long.parseLong(date);
                final Calendar cal = new GregorianCalendar();
                cal.setTimeInMillis(ms);
                return cal;
            } catch (NumberFormatException nfe) {
                throw new RuntimeException(e);
            }
        }
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "C_BooleanConstant",
        synopsis = "Return the Wrapper for a Boolean",
        signature = "Object C_BooleanConstant(boolean value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Boolean", desc = "Constant value to be returned")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the Wrapper for a Boolean",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
   public static Object C_BooleanConstant(boolean value) {
       try {
           return new Boolean(value);
       }
       catch (Exception e) {
           throw new RuntimeException(e);
       }
   }

}
