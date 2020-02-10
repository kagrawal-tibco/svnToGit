package com.tibco.be.util.DotNetSupport;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Mar 10, 2006
 * Time: 7:59:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class StackTraceHelper {
    public static String getStackTrace(Throwable tbl) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tbl.printStackTrace(pw);
        pw.flush();
        pw.close();
        sw.flush();
        return sw.toString();
    }
}
