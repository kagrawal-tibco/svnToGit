/*
 * Created by IntelliJ IDEA.
 * User: dimitris
 * Date: Feb 22, 2002
 * Time: 2:41:01 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.tibco.cep.mapper.xml.xdata;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;

/**
 * Date string utility class.  Code copied and modified from adapter SDK source.
 */
public class ISODateTimeParser {

    private static final TimeZone DefaultTimeZone = TimeZone.getDefault();
    private static final TimeZone UTCTimeZone = TimeZone.getTimeZone("GMT");

    /**
     * Parses an ISO 8601 datetime string, for example: 2002-01-15T15:24:31.144-08:00
     * means January 15th, 2002, 24 minutes, 31 seconds and 144 milliseconds past
     * three in the afternoon, in a timezone that is 8 hours 0 minutes behind UTC.
     * If the date string has no timezone segment, then it is parsed according to
     * the host's timezone.
     */
    public static Date parseISO8601Date(String rawDate)
    {
        if(rawDate.equals(""))
        {
            // All dates are created wrt UTC
            return new Date(Calendar.getInstance(DefaultTimeZone).getTime().getTime());
        }
        StringTokenizer parser = new StringTokenizer(rawDate);
        String tok[] = new String[2];
        int i = 0;
        while(parser.hasMoreTokens())
        {
            tok[i] = parser.nextToken("T");
            //  System.err.println("Toks"+tok[i]);
            i++;
        }
        //parse yyyy-mm-dd part
        parser = new StringTokenizer(tok[0]);
        int year = Integer.parseInt(parser.nextToken("-"));
        int month = Integer.parseInt(parser.nextToken("-"));
        int day = Integer.parseInt(parser.nextToken("-"));

        //parse hh:mm:ss.s
        int sep = -1;
        if(tok[1].indexOf('+')!= -1) sep = tok[1].indexOf('+');
        else if(tok[1].indexOf('-') != -1) sep = tok[1].indexOf('-');
        else if(tok[1].indexOf('Z') != -1) sep = tok[1].indexOf('Z');

        String rawTime = null;
        String rawTimeZone =null;
        if(sep == -1)  //hh:mm:ss
            rawTime = tok[1];
        else
        {
            rawTime = tok[1].substring(0,sep);
            rawTimeZone = tok[1].substring(sep, tok[1].length());
        }

        parser = new StringTokenizer(rawTime);
        int hour = Integer.parseInt(parser.nextToken(":"));
        int min = Integer.parseInt(parser.nextToken(":"));

        String rawSec = parser.nextToken(":");
        Double part = new Double(new Double(rawSec).floatValue() * 1000);
        int sec = (new Double(Math.rint(part.doubleValue()))).intValue();

        int millis = 0;
        TimeZone tz = DefaultTimeZone;
        //parse timeZoneOffset
        if(rawTimeZone != null) {
            if (rawTimeZone.equals("Z")) {
                tz = UTCTimeZone;
            } else if(rawTimeZone.length() != 1) {
                //Add stuff here if we needto support somethine like +02
                int colonIndex = rawTimeZone.indexOf(":");
                boolean minPresent = true;
                if(colonIndex == -1){ colonIndex = rawTimeZone.length(); minPresent = false;}
                String rawHour = rawTimeZone.substring(0,colonIndex);
                if(rawHour.startsWith("-"))  millis = -1;
                else millis = 1;
                int hourOff = Integer.parseInt(rawHour.substring(1, rawHour.length()));
                int minOff = 0;
                if(minPresent)
                     minOff  = Integer.parseInt(rawTimeZone.substring(colonIndex+1,rawTimeZone.length()));
                millis *= (hourOff *60 *60 + minOff * 60)*1000;
                tz.setRawOffset(millis);
            }

        }

        Calendar cl  = Calendar.getInstance(tz);
        cl.clear();
        cl.set(Calendar.YEAR, year);
        cl.set(Calendar.MONTH, month-1);
        cl.set(Calendar.DAY_OF_MONTH, day);
        cl.set(Calendar.HOUR_OF_DAY,hour);
        cl.set(Calendar.MINUTE,min);
        //Set all seconds as millisecond to accomodate partial secs.
        //cl.set(Calendar.SECOND,sec);
        cl.set(Calendar.MILLISECOND,sec);
        return new TZDate(cl.getTime().getTime(), rawDate);
    }

    public static Date parseISO8601TimeOfDay(String timeStr)
    {
        //hh:mm:ss.S+01:23
        if(timeStr.equals(""))
        {
            // All times are created wrt UTC
            return new Date(Calendar.getInstance(DefaultTimeZone).getTime().getTime());
        }
        int sep = -1;
        if(timeStr.indexOf('+')!= -1) sep = timeStr.indexOf('+');
        else if(timeStr.indexOf('-') != -1) sep = timeStr.indexOf('-');
        else if(timeStr.indexOf('Z') != -1) sep = timeStr.indexOf('Z');

        String rawTime = null;
        String rawTimeZone =null;
        if(sep == -1) {
            //hh:mm:ss
            rawTime = timeStr;
        } else {
            rawTime = timeStr.substring(0,sep);
            rawTimeZone = timeStr.substring(sep, timeStr.length());
        }

        StringTokenizer parser = new StringTokenizer(rawTime);
        int hour = Integer.parseInt(parser.nextToken(":"));
        int min = Integer.parseInt(parser.nextToken(":"));

        String rawSec = parser.nextToken(":");
        Double part = new Double(new Double(rawSec).floatValue() * 1000);
        int sec = (new Double(Math.rint(part.doubleValue()))).intValue();

        int millis = 0;
        TimeZone tz = DefaultTimeZone;
        //parse timeZoneOffset
        if(rawTimeZone != null) {
            if (rawTimeZone.equals("Z")) {
                tz = UTCTimeZone;
            } else if(rawTimeZone.length() != 1) {
                //Add stuff here if we needto support somethine like +02
                int colonIndex = rawTimeZone.indexOf(":");
                boolean minPresent = true;
                if(colonIndex == -1){ colonIndex = rawTimeZone.length(); minPresent = false;}
                String rawHour = rawTimeZone.substring(0,colonIndex);
                if(rawHour.startsWith("-"))  millis = -1;
                else millis = 1;
                int hourOff = Integer.parseInt(rawHour.substring(1, rawHour.length()));
                int minOff = 0;
                if(minPresent)
                     minOff  = Integer.parseInt(rawTimeZone.substring(colonIndex+1,rawTimeZone.length()));
                millis *= (hourOff *60 *60 + minOff * 60)*1000;
                tz.setRawOffset(millis);
            }
        }

        Calendar cl  = Calendar.getInstance(tz);
        cl.set(Calendar.HOUR_OF_DAY,hour);
        cl.set(Calendar.MINUTE,min);
        //Set all seconds as millisecond to accomodate partial secs.
        //cl.set(Calendar.SECOND,sec);
        cl.set(Calendar.MILLISECOND,sec);
        return new TZDate(cl.getTime().getTime(), timeStr);
    }


    /**
     * Render a Date object to its ISO 8601 notation in the UTC timezone
     */
    public static String toISO8601String(Date date) {
        return toISO8601String(date, UTCTimeZone);
    }

    /**
     * Render a Date object to its ISO 8601 notation in the specified timezone
     */
    public static String toISO8601String(Date date, TimeZone tz)
    {
        if(date == null) return null;
        // eg. 2088-04-07T18:30:09-08:00
        Calendar cl = Calendar.getInstance(tz);
        cl.clear();
        cl.setTime(date);

        String year  = new Integer(cl.get(Calendar.YEAR)).toString();
        String month = padZero(new Integer(cl.get(Calendar.MONTH)+1).toString());
        String day   = padZero(new Integer (cl.get(Calendar.DAY_OF_MONTH)).toString());
        String hour  = padZero(new Integer(cl.get(Calendar.HOUR_OF_DAY)).toString());
        String min   = padZero(new Integer (cl.get(Calendar.MINUTE)).toString());

        String sec   = padZero(new Integer (cl.get(Calendar.SECOND)).toString());


        int  milli =  cl.get(Calendar.MILLISECOND);
        int offsetSec =(tz.getRawOffset())/1000;
        int raw = offsetSec/60;
        int offHr = raw/60;
        int offmin = raw%60;

        String posNegTz = "";
        String  offHrStr = "";
        String  offminStr = "";
        posNegTz = "";
        if(offHr==0 && offmin == 0) offminStr ="Z";
        else
        {
            posNegTz = "+";
            if(offHr < 0 ) {offHr *= (-1); posNegTz = "-";} //
            if(offmin < 0 ) offmin *= (-1);
                offHrStr = padZero(new String() + offHr) + ":";
                offminStr =padZero(new String() + offmin);
        }

        //07:25:2001-05:00
        String milliStr ="";
        if(milli != 0) milliStr += "." + milli;
        // offHrStr already has the ':' at its end
        return (year+"-"+month+"-"+day+"T"+hour+":"+min+":"+sec+milliStr+posNegTz+offHrStr+ offminStr);
    }

    private static String padZero(String sb)
    {
        int padHere = 1;

        if(sb.length() == padHere)
            sb = "0" + sb;
        return sb;
    }
}
