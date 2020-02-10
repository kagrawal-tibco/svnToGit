package com.tibco.be.functions.java.datetime;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.tibco.be.util.TimeZoneCache;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

/**
 * User: apuneet
 * Date: Aug 24, 2004
 * Time: 3:35:56 PM
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "DateTime",
        synopsis = "Utility Functions to Operate on DateTime Properties")


public class JavaFunctions {
	private static String DEFAULT_FORMAT = "M/d/yy h:mm a"; 
    static final ThreadLocal<GregorianCalendar> clonableCalendars = new ThreadLocal<GregorianCalendar>();

    static GregorianCalendar cloneACal() {
        GregorianCalendar gc = clonableCalendars.get();
        if (gc == null) {
            gc = new GregorianCalendar();
            clonableCalendars.set(gc);
        }

        GregorianCalendar ret = (GregorianCalendar) gc.clone();
        ret.set(Calendar.HOUR_OF_DAY, 0);
        ret.set(Calendar.MINUTE, 0);
        ret.set(Calendar.SECOND, 0);
        ret.set(Calendar.MILLISECOND, 0);

        return ret;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "after",
        synopsis = "Test if the first DateTime passed come <code>after</code> the second DateTime passed.",
        signature = "boolean after (DateTime d1, DateTime d2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d1", type = "DateTime", desc = "A DateTime (date/time) that will be compared with the second argument."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d2", type = "DateTime", desc = "A DateTime (date/time) that will be compared with the first argument.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if <code>d1</code> comes after <code>d2</code>, otherwise return false."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Test if the first DateTime passed come <code>after</code> the second DateTime passed.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean after (Calendar d1, Calendar d2) {
        if(d1 == null) throw new RuntimeException("Argument d1 for after() is null");
        if(d2 == null) throw new RuntimeException("Argument d2 for after() is null");
        return d1.after(d2);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "before",
        synopsis = "Test if the first DateTime passed come before the second DateTime passed.",
        signature = "boolean before (DateTime d1, DateTime d2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d1", type = "DateTime", desc = "A DateTime (date/time) that will be compared with the second argument."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d2", type = "DateTime", desc = "A DateTime (date/time) that will be compared with the first argument.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if <code>d1</code> comes before <code>d2</code>, otherwise return false."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Test if the first DateTime passed come before the second DateTime passed.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean before (Calendar d1, Calendar d2) {
        if(d1 == null) throw new RuntimeException("Argument d1 for before() is null");
        if(d2 == null) throw new RuntimeException("Argument d2 for before() is null");
        return d1.before(d2);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "equals",
        synopsis = "Is the date time value of first DateTime passed same as date time value of second DateTime passed.",
        signature = "boolean equals (DateTime d1, DateTime d2)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d1", type = "DateTime", desc = "A DateTime (date/time) that will be compared with the second argument."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d2", type = "DateTime", desc = "A DateTime (date/time) that will be compared with the first argument.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if <code>d1</code> is the same as <code>d2</code>, otherwise return false."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Test if the first DateTime the same as the second DateTime.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean equals (Calendar d1, Calendar d2) {
        if (d1 == null) {
            return (d2 == null);
        }
        else {
        	if (RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties()
            		.getProperty("tibco.be.datetime.compare.timezone", "false").
            		equals("true")) {
        		return d1.equals(d2);
            }
            return d1.compareTo(d2) == 0;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "notEquals",
        signature = "boolean notEquals (DateTime d1, DateTime d2)",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d1", type = "DateTime", desc = "A DateTime."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d2", type = "DateTime", desc = "A DateTime.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean",
                desc = "true if <code>d1</code> is the same DateTime as <code>d2</code>."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Compares the date time values of the arguments.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static boolean notEquals (Calendar d1, Calendar d2) {
        if (d1 == null) {
            return (d2 != null);
        }
        else {
        	if (RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties()
            		.getProperty("tibco.be.datetime.compare.timezone", "false").
            		equals("true")) {
        		return !d1.equals(d2);
            }
            return d1.compareTo(d2) != 0;
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getTimeInMillis",
        synopsis = "Returns the time in milliseconds since the start of 1970 GMT.",
        signature = "long getTimeInMillis (DateTime date)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "A DateTime date/time which will be converted to milliseconds since the start of 1970 GMT.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "The UTC time in milliseconds since start of 1970 GMT."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the time in milliseconds since the start of 1970 GMT.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static long getTimeInMillis(Calendar d1) {
        if(d1 == null) throw new RuntimeException("Argument d1 for getTimeInMillis() is null");
        return d1.getTimeInMillis();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "parseLong",
        synopsis = "Parses the time in milliseconds since the start of 1970 and return as a DateTime.",
        signature = "DateTime parseLong(long time)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "time", type = "long", desc = "The UTC time in milliseconds.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "The result DateTime."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Parses the time in milliseconds since the start of 1970 and return as a DateTime.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar parseLong(long time){
        Calendar cal = cloneACal();
        cal.setTimeInMillis(time);
        return cal;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "parseString",
        synopsis = "Parses the <code>date</code> provided in the string according to the format and returns it as a DateTime with default time zone.",
        signature = "DateTime parseString (String date, String format)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "String", desc = "3 digits millisecond is required if parsing millisecond.  i.e. $103-11-2004 14:59:04.250$1 is correct, $103-11-2004 14:59:04.25$1 is not valid."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "format", type = "String", desc = "A String containing a format describing how the date should be parsed.  e.g. $1MM-dd-yyyy HH:mm:ss$1, $1MM-dd-yyyy HH:mm:ss.SSS$1.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "The result DateTime."),
        version = "1.0",
        see = "java.text.SimpleDateFormat",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Parses the <code>date</code> provided in the string according to the format and returns it as a DateTime with default time zone.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar parseString(String date,String format) {
        if(date == null) return null;
        try {
            SimpleDateFormat df = getDateFormat(format);
            Calendar cal = cloneACal();
            df.setCalendar(cal);
            if (RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties()
            		.getProperty("tibco.be.datetime.parse.lenient", "true").
            		equals("false")) {
            	df.setLenient(false);
            	cal.setLenient(false);
            }
            df.parse(date);
            df.setCalendar(null);
            return cal;
        } catch(java.text.ParseException pe) {
            throw new RuntimeException(pe);
        }
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "parseStringWithTimeZone",
        signature = "DateTime parseStringWithTimeZone (String date, String format)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "String", desc = "The date to parse. Timezone value is required  i.e. $2012-09-30T10:10:10-0600$1"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "format", type = "String", desc = "Format used to parse the date and timezone against.  e.g. $1yyyy-MM-dd'T'HH:mm:ssZ$1")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "The result DateTime with the given timezone."),
        version = "5.6",
        see = "java.time.format.DateTimeFormatter",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Parses the <code>date</code> provided in the string according to the format and returns it as a DateTime with given time zone.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar parseStringWithTimeZone(String date, String format) {
    	if (date == null) return null;
    	
    	boolean isLenient = Boolean.parseBoolean(RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties()
         		.getProperty("tibco.be.datetime.parse.lenient", "true"));
    	
    	ZonedDateTime zdt = null;
    	if (hasTimezone(format)) {
    		DateTimeFormatter formatter = getDateTimeFormat(format, isLenient);
    		zdt = ZonedDateTime.parse(date, formatter);
    	} else {
    		throw new IllegalArgumentException(String.format("Datetime format[%s] missing timezone", format));
    	}
    	
    	return GregorianCalendar.from(zdt);
    }
    
    private static boolean hasTimezone(String format) {
    	String tzChars = "xzOVXZ";
    	for (char c : tzChars.toCharArray()) {
    		if (format.indexOf(c) != -1) return true;
    	}
    	return false;
    }

    private static ThreadLocal<Map<String, SimpleDateFormat>> formatLocals = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        /**
         * Returns the current thread's initial value for this thread-local
         * variable.  This method will be invoked at most once per accessing
         * thread for each thread-local, the first time the thread accesses the
         * variable with the {@link #get()} method.  The <tt>initialValue</tt>
         * method will not be invoked in a thread if the thread invokes the {@link
         * #set(Object)} method prior to the <tt>get</tt> method.
         * <p/>
         * <p>This implementation simply returns <tt>null</tt>; if the programmer
         * desires thread-local variables to be initialized to some value other
         * than <tt>null</tt>, <tt>ThreadLocal</tt> must be subclassed, and this
         * method overridden.  Typically, an anonymous inner class will be used.
         * Typical implementations of <tt>initialValue</tt> will invoke an
         * appropriate constructor and return the newly constructed object.
         *
         * @return the initial value for this thread-local
         */
        protected Map<String, SimpleDateFormat> initialValue() {
            return new HashMap<String, SimpleDateFormat>();
        }
    };
    
    private static ThreadLocal<Map<String, DateTimeFormatter>> dateTimeFormatters = new ThreadLocal<Map<String, DateTimeFormatter>>() {
    	protected Map<String, DateTimeFormatter> initialValue() {
    		return new HashMap<String, DateTimeFormatter>(); 
    	}
    };

    private static SimpleDateFormat getDateFormat(String format) {
        HashMap<String, SimpleDateFormat> formatMap = (HashMap<String, SimpleDateFormat>) formatLocals.get();
        SimpleDateFormat df = (SimpleDateFormat) formatMap.get(format);
        if (df == null) {
            if (format == null) {
                df = new SimpleDateFormat();
            }
            else {
                df = new SimpleDateFormat(format);
            }
            formatMap.put(format, df);
        }
        return df;
    }
    
    private static DateTimeFormatter getDateTimeFormat(String format, boolean isLenient) {
        Map<String, DateTimeFormatter> formatMap = (HashMap<String, DateTimeFormatter>) dateTimeFormatters.get();
        DateTimeFormatter df = (DateTimeFormatter) formatMap.get(format);
        if (df == null) {
        	DateTimeFormatterBuilder dtfb = new DateTimeFormatterBuilder();
        	if (isLenient) dtfb.parseLenient();
        	if (format != null) dtfb.appendPattern(format);
            df = dtfb.toFormatter();
            formatMap.put(format, df);
        }
        return df;
    }   
    
    @com.tibco.be.model.functions.BEFunction(
        name = "format",
        signature = "String format (DateTime d1, String format)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "d1", type = "DateTime", desc = "A DateTime to be formatted."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "format", type = "String", desc = "i.e. $1yyyy-MM-dd HH:mm:ss.SSS$1 is correct, but $1yyyy-MM-dd HH:mm:ss.S$1 is not valid.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A String representation of the supplied DateTime in the supplied format."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the DateTime passed as a formatted String.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String format (Calendar d1,String format) {
        if(d1 == null) return null;

        SimpleDateFormat df = getDateFormat(format);
        //not sure if clone is necessary
        //need to set calendar to get the timezone, locale, etc
        df.setCalendar((Calendar)d1.clone());
        String ret = df.format(d1.getTime());
        df.setCalendar(null);
        return ret;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "now",
        synopsis = "Returns the current System time as a DateTime.",
        signature = "DateTime now ()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "The current System time as a DateTime."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the current system time as a DateTime.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar now () {
        return new GregorianCalendar();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "addDay",
        synopsis = "Adds a number of <code>days</code> to a <code>date</code> and returns the resulting DateTime.\nThe <code>date</code> argument is not modified.",
        signature = "DateTime addDay (DateTime date, int days)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "A DateTime to add some amount of time to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "days", type = "int", desc = "The number of days to add to the DateTime.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "A new DateTime that results from adding the number of time units to the <code>date</code> argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Adds a number of days to a DateTime and returns the result. The input DateTime is not modified.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar addDay (Calendar date, int days) {
        if(date == null) return null;
        Calendar ret = (Calendar) date.clone();
        ret.add(Calendar.DATE, days);
        return ret;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "addMonth",
        synopsis = "Adds a number of <code>months</code> to a <code>date</code> and returns the resulting DateTime.\nThe <code>date</code> argument is not modified.",
        signature = "DateTime addMonth (DateTime date, int months)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "A DateTime to add some amount of time to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "months", type = "int", desc = "The number of months to add to the DateTime.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "A mew DateTime that results from adding the number of time units to the <code>date</code> argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Adds a number of months to a DateTime and returns the result. The input DateTime is not modified.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar addMonth (Calendar date, int months) {
        if(date == null) return null;
        Calendar ret = (Calendar) date.clone();
        ret.add(Calendar.MONTH,months);
        return ret;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "addYear",
        synopsis = "Adds a number of <code>years</code> to a <code>date</code> and returns the resulting DateTime.\nThe <code>date</code> argument is not modified.",
        signature = "DateTime addYear (DateTime date, int years)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "A DateTime to add some amount of time to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "years", type = "int", desc = "The number of years to add to the DateTime.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "A new DateTime that results from adding the number of time units to the <code>date</code> argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Adds a number of years to a DateTime and returns the result. The input DateTime is not modified.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar addYear (Calendar date, int years) {
        if(date == null) return null;
        Calendar ret = (Calendar) date.clone();
        ret.add(Calendar.YEAR,years);
        return ret;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "addWeek",
        synopsis = "Adds a number of <code>weeks</code> to a <code>date</code> and returns the resulting DateTime.\nThe <code>date</code> argument is not modified.",
        signature = "DateTime addWeek (DateTime date, int weeks)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "A DateTime to add some amount of time to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "weeks", type = "int", desc = "The number of weeks to add to the DateTime.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "A new DateTime that results from adding the number of time units to the <code>date</code> argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Adds a number of weeks to a DateTime and returns the result. The input DateTime is not modified.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar addWeek (Calendar date, int weeks) {
        if(date == null) return null;
        Calendar ret = (Calendar) date.clone();
        ret.add(Calendar.WEEK_OF_YEAR,weeks);
        return ret;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "addSecond",
        synopsis = "Adds a number of <code>seconds</code> to a <code>date</code> and returns the resulting DateTime.\nThe <code>date</code> argument is not modified.",
        signature = "DateTime addSecond (DateTime date, int seconds)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "A DateTime to add some amount of time to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "seconds", type = "int", desc = "The number of seconds to add to the DateTime.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "A new DateTime that results from adding the number of time units to the <code>date</code> argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Adds a number of seconds to a DateTime and returns the result. The input DateTime is not modified.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar addSecond (Calendar date, int seconds) {
        if(date == null) return null;
        Calendar ret = (Calendar) date.clone();
        ret.add(Calendar.SECOND, seconds);
        return ret;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "addMinute",
        synopsis = "Adds a number of <code>minutes</code> to a <code>date</code> and returns the resulting DateTime.\nThe <code>date</code> argument is not modified.",
        signature = "DateTime addMinute (DateTime date, int minutes)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "A DateTime to add some amount of time to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "minutes", type = "int", desc = "The number of minutes to add to the DateTime.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "A new DateTime that results from adding the number of time units to the <code>date</code> argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Adds a number of minutes to a DateTime and returns the result. The input DateTime is not modified.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar addMinute (Calendar date, int minutes) {
        if(date == null) return null;
        Calendar ret = (Calendar) date.clone();
        ret.add(Calendar.MINUTE, minutes);
        return ret;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "addHour",
        synopsis = "Adds a number of <code>hour</code> to a <code>date</code> and returns the resulting DateTime.\nThe <code>date</code> argument is not modified.",
        signature = "DateTime addHour(DateTime date, int hours)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "A DateTime to add some amount of time to."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "hours", type = "int", desc = "The number of hours to add to the DateTime.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "A new DateTime that results from adding the number of time units to the <code>date</code> argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Adds a number of hours to a DateTime and returns the result. The input DateTime is not modified.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar addHour (Calendar date, int hours) {
        if(date == null) return null;
        Calendar ret = (Calendar) date.clone();
        ret.add(Calendar.HOUR, hours);
        return ret;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getTimeZone",
        synopsis = "Gets timezone id from DateTime object.",
        signature = "String getTimeZone(DateTime date, boolean daylight_saving_name)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "A DateTime to get the timezone of."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "daylight_saving_name", type = "boolean", desc = "If true returns the daylight savings name.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The timezone id."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the timezone id from DateTime object.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String getTimeZone(Calendar date, boolean daylight_saving_name) {
        if (date == null) return null;
        return (date.getTimeZone().getDisplayName(daylight_saving_name, TimeZone.SHORT));
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "translateTime",
        synopsis = "translate a time to a new timezone and return the new translated DateTime object",
        signature = "DateTime translateTime(DateTime date, String tz)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "The DateTime to be translated."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tz", type = "String", desc = "The Time Zone ID.  If null, use the default TimeZone.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "The new translated DateTime of timezone tz."),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Translate a time to a new timezone and return the new translated DateTime object",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar translateTime(Calendar date, String tz) {
        if(date == null) return null;
        if (tz == null) {
            Calendar ret = cloneACal();
            ret.setTimeInMillis(date.getTimeInMillis());
            return ret;
        }
        else {
            Calendar ret = cloneACal();
            ret.setTimeZone(TimeZoneCache.getCachedTz(tz));
            ret.setTimeInMillis(date.getTimeInMillis());
            return ret;
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "createTime",
        synopsis = "Create a DateTime object of a particular timezone.",
        signature = "DateTime createTime(int year, int month, int date, int hrs, int min, int sec, String tz)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "year", type = "int", desc = "The value of the YEAR time field."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "month", type = "int", desc = "The value of the MONTH time field.  Month value is 0-based. e.g., 0 for January."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "int", desc = "The value of the DATE time field.  The day of month value between 1-31."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "hour", type = "int", desc = "The value of the HOUR time field.  Hour value between 0-23."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "min", type = "int", desc = "The value of the MINUTE time field.  Minute value between 0-59."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sec", type = "int", desc = "The value of the SECOND time field.  Second value between 0-59."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tz", type = "String", desc = "The Time Zone ID.  If null, the default TimeZone will be used.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "DateTime", desc = "The created DateTime with timezone set to tz."),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Create a DateTime object of a particular timezone.   Month value is 0-based. 0 for January, 11 for December.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Calendar createTime(int year, int month, int date, int hour, int min, int sec, String tz) {
        if(tz == null) {
            Calendar c = cloneACal();
            c.set(year, month, date, hour, min, sec);
            return c;
        }
        else {
            Calendar c = cloneACal();
            c.setTimeZone(TimeZoneCache.getCachedTz(tz));
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DATE, date);
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, min);
            c.set(Calendar.SECOND, sec);
            return c;
        }
    }


     @com.tibco.be.model.functions.BEFunction(
        name = "getYear",
        synopsis = "Gets the year on the DateTime Object passed as input.",
        signature = "int getYear(DateTime date)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "A DateTime to get the year of.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The year on the <code>date</code> argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the year on the DateTime Object passed as input.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int getYear(Calendar date) {
         if(date == null) throw new RuntimeException("Argument date for getYear() is null");
        return date.get(Calendar.YEAR);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getMonth",
        synopsis = "Gets the month on the DateTime Object passed as input.  Month value is 0-based.\ni.e. 0 for January, 11 for December.",
        signature = "int getMonth(DateTime date)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "A DateTime to get the month of.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The month on the <code>date</code> argument.  The first month of the year is 0."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the month on the DateTime Object passed as input.  Month value is 0-based.\ni.e. 0 for January, 11 for December.",
        cautions = "The first month of the year is 0.",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int getMonth(Calendar date) {
        if(date == null) throw new RuntimeException("Argument date for getMonth() is null");
        return date.get(Calendar.MONTH);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getDate",
        synopsis = "Gets the date on the DateTime Object passed as input.",
        signature = "int getDate(DateTime date)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "A DateTime to get the date of.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The date on the <code>date</code> argument.  The first day of the month is 1."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the date on the DateTime Object passed as input.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int getDate(Calendar date) {
        if(date == null) throw new RuntimeException("Argument date for getDate() is null");
        return date.get(Calendar.DATE);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getHour",
        synopsis = "Gets the hour on the DateTime Object passed as input.",
        signature = "int getHour(DateTime date)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "A DateTime to get the hour of.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The hour on the <code>date</code> argument.  Hour in day ranges from 0-23."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the hour on the DateTime Object passed as input.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int getHour(Calendar date) {
        if(date == null) throw new RuntimeException("Argument date for getHour() is null");
        return date.get(Calendar.HOUR_OF_DAY);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getMinute",
        synopsis = "Gets the minute on the DateTime Object passed as input.",
        signature = "int getMinute(DateTime date)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "A DateTime to get the minute of.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The minute on the <code>date</code> argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the minute on the DateTime Object passed as input.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int getMinute(Calendar date) {
        if(date == null) throw new RuntimeException("Argument date for getMinute() is null");
        return date.get(Calendar.MINUTE);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSecond",
        synopsis = "Gets the second on the DateTime Object passed as input.",
        signature = "int getSecond(DateTime date)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "date", type = "DateTime", desc = "A DateTime to get the second of.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The second on the <code>date</code> argument."),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the second on the DateTime Object passed as input.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static int getSecond(Calendar date) {
        if(date == null) throw new RuntimeException("Argument date for getSecond() is null");
        return date.get(Calendar.SECOND);
    }
}
