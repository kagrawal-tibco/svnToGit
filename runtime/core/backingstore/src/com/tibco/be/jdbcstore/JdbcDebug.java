package com.tibco.be.jdbcstore;

/**
 * Created by IntelliJ IDEA.
 * User: agerst
 * Date: 18.12.2006
 * Time: 01:05:25
 */
public class JdbcDebug {
    static int loglevel=9;

    @Deprecated
    public static void debugln(Object out) {
        if (loglevel==9) {
            System.out.println(out);
        }
    }
    @Deprecated
    static void debug(Object out) {
        if (loglevel==9) {
            System.out.print(out);
        }
    }
    @Deprecated
    static public boolean isDebugOn() {
        return (loglevel == 9);
    }
    @Deprecated
    public static void setDebugLevel(int level) {
        loglevel=level;
    }
}
