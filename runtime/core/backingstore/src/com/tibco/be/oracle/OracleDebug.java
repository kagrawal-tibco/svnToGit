package com.tibco.be.oracle;

/**
 * Created by IntelliJ IDEA.
 * User: agerst
 * Date: 18.12.2006
 * Time: 01:05:25
 * To change this template use File | Settings | File Templates.
 */
public class OracleDebug {
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
