package com.tibco.be.migration;


/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 14, 2008
 * Time: 1:47:08 PM
 * To change this template use File | Settings | File Templates.
 */

public class EnvironmentInfo {
    private static EnvironmentInfo singleton;
    static String[] allArgs;
    static String[] frameworkArgs;
    static String[] appArgs;

    private EnvironmentInfo() {
        super();
    }

    public static EnvironmentInfo getDefault() {
        if (singleton == null)
            singleton = new EnvironmentInfo();
        return singleton;
    }

    public boolean inDevelopmentMode() {
        return BEMigrationProperties.getProperty("be.migration.dev") != null;
    }

    public boolean inDebugMode() {
        return BEMigrationProperties.getProperty("be.migration.debug") != null;
    }

    public String[] getCommandLineArgs() {
        return allArgs;
    }

    public String[] getFrameworkArgs() {
        return frameworkArgs;
    }

    public String[] getNonFrameworkArgs() {
        return appArgs;
    }

    public static void setAllArgs(String[] allArgs) {
        if (EnvironmentInfo.allArgs == null)
            EnvironmentInfo.allArgs = allArgs;
    }

    public static void setAppArgs(String[] appArgs) {
        if (EnvironmentInfo.appArgs == null)
            EnvironmentInfo.appArgs = appArgs;
    }


    public String getCommandLineArgs2String() {
        StringBuffer buf = new StringBuffer();
        for (int i=0; i < appArgs.length; i++) {
            buf.append(appArgs[i]);
            buf.append(" ");
        }
        return buf.toString();
    }
}

