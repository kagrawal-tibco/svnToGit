package com.tibco.cep.query.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.tibco.cep.query.model.impl.DateTimeLiteralImpl;


/**
 * User: nprade
 * Date: 4/4/12
 * Time: 11:32 AM
 */
public class DateFormatProvider {


    private static final Object KEY = new Object();

    private final ThreadLocal<DateFormat> dateFormatThreadLocal = new ThreadLocal<DateFormat>();


    private DateFormatProvider() {
    }


    public DateFormat getDateFormat() {
        DateFormat dateFormat = dateFormatThreadLocal.get();
        if (null == dateFormat) {
            synchronized (KEY) {
                dateFormat = dateFormatThreadLocal.get();
                if (null == dateFormat) {
                    dateFormat = new SimpleDateFormat(DateTimeLiteralImpl.DATETIME_FORMAT);
                    dateFormatThreadLocal.set(dateFormat);
                }
            }
        }
        return dateFormat;
    }


    public static DateFormatProvider getInstance() {
        return LazySingletonHolder.SINGLETON;
    }


    private static class LazySingletonHolder {


        static DateFormatProvider SINGLETON = new DateFormatProvider();
    }

}
