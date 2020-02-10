/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.functions.coherence.query.backingstore.converters;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import com.tibco.be.model.functions.Enabled;

import static com.tibco.be.model.functions.FunctionDomain.*;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Coherence",
        category = "Coherence.Query.BackingStore.Converters",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.coherence.query.backingstore",value=false),
        synopsis = "Functions for querying the Cache")
public class CoherenceBackingStoreConverterFunctions {
     static ThreadLocal currentContext = new ThreadLocal();

    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnAsString",
        synopsis = "",
        signature = "String getColumnAsString(Object colValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "colValue", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        enabled=@Enabled(property="TIBCO.BE.function.catalog.coherence.query.backingstore.converters",value=false),
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static String getColumnAsString(Object colValue) {
        if (colValue instanceof String) {
            return (String) colValue;
        } else if (colValue != null) {
            return colValue.toString();
        } else {
            return null;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnAsInt",
        synopsis = "",
        signature = "int getColumnAsInt(Object colValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "colValue", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static int getColumnAsInt(Object colValue) {
        if (colValue instanceof Integer) {
            return ((Integer) colValue).intValue();
        } else if (colValue instanceof BigInteger){
            return ((BigInteger)colValue).intValue();
        } else if (colValue instanceof BigDecimal){
            return ((BigDecimal)colValue).intValue();
        } else if (colValue == null) {
            return 0;
        } else {
            throw new RuntimeException("Unable to convert " + colValue + " to int" );
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnAsDouble",
        synopsis = "",
        signature = "double getColumnAsDouble(Object colValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "colValue", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "double", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static double getColumnAsDouble(Object colValue) {
        if (colValue instanceof Double) {
            return ((Double) colValue).doubleValue();
        } else if (colValue instanceof BigInteger){
            return ((BigInteger)colValue).doubleValue();
        } else if (colValue instanceof BigDecimal){
            return ((BigDecimal)colValue).doubleValue();
        } else if (colValue == null) {
            return 0.0;
        } else {
            throw new RuntimeException("Unable to convert " + colValue + " to int" );
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnAsLong",
        synopsis = "",
        signature = "long getColumnAsLong(Object colValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "colValue", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static long getColumnAsLong(Object colValue) {
        if (colValue instanceof Long) {
            return ((Long) colValue).longValue();
        } else if (colValue instanceof BigInteger){
            return ((BigInteger)colValue).longValue();
        } else if (colValue instanceof BigDecimal){
            return ((BigDecimal)colValue).longValue();
        } else if (colValue instanceof Integer){
            return ((Integer)colValue).longValue();
        } else if (colValue == null) {
            return 0L;
        } else {
            throw new RuntimeException("Unable to convert " + colValue + " to int" );
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnAsDate",
        synopsis = "",
        signature = "Calendar getColumnAsDate(Object colValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "colValue", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Calendar", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static Calendar getColumnAsDate(Object colValue) {
        if (colValue instanceof java.sql.Date) {
            Calendar cal=com.tibco.be.functions.java.datetime.JavaFunctions.now();
            cal.setTime((java.sql.Date) colValue);
            return cal;
        } else if (colValue instanceof java.util.Date) {
            Calendar cal=com.tibco.be.functions.java.datetime.JavaFunctions.now();
            cal.setTime((java.util.Date) colValue);
            return cal;
        } else if (colValue == null) {
            return null;
        } else {
            throw new RuntimeException("Unable to convert " + colValue + " to int" );
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnAsTime",
        synopsis = "",
        signature = "long getColumnAsTime(Object colValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "colValue", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static long getColumnAsTime(Object colValue) {
        if (colValue instanceof java.sql.Date) {
            return ((java.sql.Date) colValue).getTime();
        } else if (colValue instanceof java.util.Date) {
            return ((java.util.Date) colValue).getTime();
        } else if (colValue == null) {
            throw new RuntimeException("NULL Value, use IsNULL  " );
        } else {
            throw new RuntimeException("Unable to convert " + colValue + " to int" );
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isNull",
        synopsis = "",
        signature = "boolean isNull(Object colValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "colValue", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = ""
    )
    public static boolean isNull(Object colValue) {
        return (colValue == null);
    }
}
