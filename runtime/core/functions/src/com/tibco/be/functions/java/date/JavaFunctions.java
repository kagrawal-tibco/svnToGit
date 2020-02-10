package com.tibco.be.functions.java.date;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.tibco.be.model.functions.FunctionDomain.*;

/**
 * User: apuneet
 * Date: Aug 24, 2004
 * Time: 3:35:56 PM
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Date",
        synopsis = "Utility Functions to Operate on DateTime Properties as Dates without Time")


public class JavaFunctions {
    @com.tibco.be.model.functions.BEFunction(
        name = "after",
        synopsis = "Returns true if the date <code>d1</code> is after the date <code>d2</code>.\nThe comparison is done independently of the time of day of both <code>d1</code> and <code>d2</code>.",
        signature = "boolean after (DateTime d1, DateTime d2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d1", type = "DateTime", desc = "A DateTime (date/time) that will be compared with the second argument."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d2", type = "DateTime", desc = "A DateTime (date/time) that will be compared with the first argument.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if <code>d1</code> comes after <code>d2</code>, otherwise return false."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Test if the first date come $1after$1 the second date, independently of the time of day.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean after (Calendar d1, Calendar d2) {
        if(d1 == null) throw new RuntimeException("Argument d1 for after() is null");
        if(d2 == null) throw new RuntimeException("Argument d2 for after() is null");
        if(d1.get(Calendar.YEAR) > d2.get(Calendar.YEAR)) {
            return true;
        }
        else if(d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR)) {
            if(d1.get(Calendar.MONTH) > d2.get(Calendar.MONTH)) {
                return true;
            }
            else if (d1.get(Calendar.MONTH) == d2.get(Calendar.MONTH)) {
                if(d1.get(Calendar.DAY_OF_MONTH) > d2.get(Calendar.DAY_OF_MONTH)) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "before",
        synopsis = "Returns true if the date <code>d1</code> is before the date <code>d2</code>.\nThe comparison is done independently of the time of day of both <code>d1</code> and <code>d2</code>.",
        signature = "boolean before (DateTime d1, DateTime d2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d1", type = "DateTime", desc = "A DateTime (date/time) that will be compared with the second argument."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d2", type = "DateTime", desc = "A DateTime (date/time) that will be compared with the first argument.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the date <code>d1</code> comes before the date <code>d2</code>, otherwise return false."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Test if the first date come before the second date, independently of the time of day.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean before (Calendar d1, Calendar d2) {
        if(d1 == null) throw new RuntimeException("Argument d1 for before() is null");
        if(d2 == null) throw new RuntimeException("Argument d2 for before() is null");
        if(d1.get(Calendar.YEAR) < d2.get(Calendar.YEAR)) {
            return true;
        }
        else if(d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR)) {
            if(d1.get(Calendar.MONTH) < d2.get(Calendar.MONTH)) {
                return true;
            }
            else if (d1.get(Calendar.MONTH) == d2.get(Calendar.MONTH)) {
                if(d1.get(Calendar.DAY_OF_MONTH) < d2.get(Calendar.DAY_OF_MONTH)) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "equals",
        synopsis = "Returns true if the date of <code>d1</code> is the same as the date of <code>d2</code>.\nThe comparison is done independently of the time of day of both <code>d1</code> and <code>d2</code>.",
        signature = "boolean equals (DateTime d1, DateTime d2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d1", type = "DateTime", desc = "A DateTime (date/time) that will be compared with the second argument."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d2", type = "DateTime", desc = "A DateTime (date/time) that will be compared with the first argument.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if d1 is the same as d2, otherwise return false."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Test if the first date the same as the second date, independently of the time of day.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean equals (Calendar d1, Calendar d2) {
        if(d1 == null) {
            if(d2 == null) return true;
            else return false;
        }
        else {
            return  (d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR)) &&
                (d1.get(Calendar.MONTH) == d2.get(Calendar.MONTH)) &&
                (d1.get(Calendar.DAY_OF_MONTH) == d2.get(Calendar.DAY_OF_MONTH));
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "today",
        synopsis = "Returns the current date as a DateTime.",
        signature = "DateTime today ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "The current date as a DateTime."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the current date time as a DateTime.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>\nString result = DateTime.format (Date.today (),  $1yyyy-MM-dd HH:mm:ss$1);<br /><br/>\nResult is: result contains: $12004-03-11 00:00:0$1."
    )
    public static Calendar today () {
        GregorianCalendar ret = new GregorianCalendar();
        ret.set( Calendar.HOUR_OF_DAY,0);
        ret.set( Calendar.MINUTE,0);
        ret.set( Calendar.SECOND,0);
        ret.set( Calendar.MILLISECOND,0);
        return ret;
    }

}
