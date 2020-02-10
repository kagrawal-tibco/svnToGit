package com.tibco.cep.query.stream._join2_.util;

import java.util.Calendar;
import java.util.Date;

/*
* Author: Ashwin Jayaprakash Date: Jun 4, 2009 Time: 7:34:33 PM
*/
public class DateMaker {
    public static Date makeDate(Calendar calendar, int year, int month, int date) {
        return makeDate(calendar, year, month, date, 0, 0, 0);
    }

    public static Date makeDate(Calendar calendar, int year, int month, int date,
                                int hourOfDay, int minute, int second) {
        calendar.set(year, month, date, hourOfDay, minute, second);

        return calendar.getTime();
    }
}
