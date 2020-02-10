package com.tibco.cep;


import com.tibco.cep.util.PlatformUtil;


public class Bootstrap {


    private static final boolean ENABLED = ! (
            Boolean.getBoolean("com.tibco.xml.parsers.disabled")
                    || PlatformUtil.INSTANCE.isStudioPlatform()
    );

    private static boolean firstRun = true;


    private Bootstrap() {
    }


    public static void ensureBootstrapped() {
        if (ENABLED) {
            synchronized (Bootstrap.class) {
                if (firstRun) {
                    com.tibco.xml.parsers.xmlfactories.XMLParsersFactory.bootstrapFactories();
                    firstRun = false;
                }
            }
        }
    }

}