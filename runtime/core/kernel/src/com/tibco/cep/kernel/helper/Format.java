package com.tibco.cep.kernel.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 17, 2006
 * Time: 5:03:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Format {
    static public final String BRK = System.getProperty("line.separator", "\n");

    static public final String CODEGEN_PREFIX = "be.gen";

    static public String STANDARD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss z";

    static public String formatCalendar(Calendar c) {
        if(c == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_FORMAT);
        sdf.setTimeZone(c.getTimeZone());
        return sdf.format(c.getTime());
    }

    static public String objsToStr(Object[] objects) {
        StringBuffer buf = new StringBuffer("");
        if (objects != null) {
            for (int i = 0; i < objects.length; i++) {
                buf.append(objects[i]);
                if (i != objects.length - 1) {
                    buf.append(", ");
                }
            }
        }
        return buf.toString();
    }
}
