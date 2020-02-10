package com.tibco.cep.runtime.service.logging;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.log4j.spi.LoggingEvent;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Layout;

/**
 * Created with IntelliJ IDEA.
 * User: Ameya
 * Date: 11/12/14
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class TraceMessageLayoutV2 extends Layout {

    private static final String BRK = System.getProperty("line.separator", "\n");
    //TODO temporary, remove when Admin can accept our timezone format
    private static final boolean PRINT_TZ_MINUTES = Boolean.getBoolean("be.trace.print_tz_minutes");
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
    private static final int MILLISECONDS_PER_MINUTE = 60000;
    private static final int MINUTES_PER_HOUR = 60;
    private static final Map<String, String> ROLE_LEVEL_NAME_TO_ADMIN_NAME = new HashMap<String, String>();
    private final static Locale locale = Locale.US;
    
    //Tibco Administrator format by default. Maybe overridden using a BE property.
    private static String FORMAT_STRING = "yyyy MMM dd HH:mm:ss:SSS 'GMT' ";
    private static boolean IS_TIBCO_ADMINISTRATOR_DATE_FORMAT = true;
    private static FastDateFormat formatter;

    public static final Pattern PACKAGE_NAME_EXTRACTION_PATTERN =
            Pattern.compile("^com\\.tibco\\.(?:(?:be)|(?:cep))\\.(\\w+(?:\\.\\w+)?)");
    static {
        ROLE_LEVEL_NAME_TO_ADMIN_NAME.put("INFO", "Info");
        ROLE_LEVEL_NAME_TO_ADMIN_NAME.put("WARN", "Warning");
        ROLE_LEVEL_NAME_TO_ADMIN_NAME.put("ERROR", "Error");
        ROLE_LEVEL_NAME_TO_ADMIN_NAME.put("DEBUG", "Debug");
        ROLE_LEVEL_NAME_TO_ADMIN_NAME.put("TRACE", "Trace");
        ROLE_LEVEL_NAME_TO_ADMIN_NAME.put("USER", "User");   // Not in Admin
        ROLE_LEVEL_NAME_TO_ADMIN_NAME.put("FATAL", "Fatal"); // Not in Admin
        ROLE_LEVEL_NAME_TO_ADMIN_NAME.put("OFF", "None"); // Not in Admin
        ROLE_LEVEL_NAME_TO_ADMIN_NAME.put("ALL", "All"); // Not in Admin
    }


    private String name;


    public TraceMessageLayoutV2(String name, String dateFormat) {
        this.name = this.preformat(name);
    	if (null != dateFormat) {
    		//override default format string
    		FORMAT_STRING = dateFormat;
    		IS_TIBCO_ADMINISTRATOR_DATE_FORMAT = false;
    	}
    	formatter = FastDateFormat.getInstance(FORMAT_STRING, locale);
    }


    //TODO: make sure that this really conforms to the layout that Administrator understands
    public String format(LoggingEvent loggingEvent) {
        String categoryName = loggingEvent.getLogger().getName();
        final Matcher m = PACKAGE_NAME_EXTRACTION_PATTERN.matcher(categoryName);

        if(m.find())
            categoryName = m.group(1);
        return this.getTimeStamp()
                + ' ' + this.name
                + ' ' + this.getRole(loggingEvent)
                + " [" + this.preformat(Thread.currentThread().getName())
                + "] - [" + categoryName
                + "] " + loggingEvent.getMessage() + BRK;
    }


    private String preformat(String text) {
        if ((null == text) || "".equals(text)) {
            return "_";
        } else {
            return WHITESPACE_PATTERN.matcher(text).replaceAll("_");
        }
    }


    private String getRole(LoggingEvent loggingEvent) {
        if (null != loggingEvent) {
            final String name = loggingEvent.getLogger().getName();
            if (name.endsWith("$user")) {
                return "User";
            }
            final String roleName = ROLE_LEVEL_NAME_TO_ADMIN_NAME.get(loggingEvent.getLevel().toString());
            return (null == roleName) ? name : roleName;
        }
        return "?";
    }


    public boolean ignoresThrowable() {
        return true;
    }


    public void activateOptions() {

    }


    //TODO: make sure that this really conforms to the layout that Administrator understands
    public String getTimeStamp() {
        final StringBuffer buf = new StringBuffer(FORMAT_STRING.length() + 10);
        final Date now = new Date();
    	formatter.format(now, buf);
    	if (IS_TIBCO_ADMINISTRATOR_DATE_FORMAT) {
    		int minutes = TimeZone.getDefault().getOffset(now.getTime());
    		minutes /= MILLISECONDS_PER_MINUTE;
    		final int hours = minutes / MINUTES_PER_HOUR;
    		minutes = Math.abs(minutes % MINUTES_PER_HOUR);
    		if(hours > 0) buf.append('+');
    		buf.append(hours);
    		if(minutes > 0 && PRINT_TZ_MINUTES) {
    			buf.append(':');
    			if(minutes < 10) buf.append('0');
    			buf.append(minutes);
    		}
        }
        return buf.toString();
    }
}
