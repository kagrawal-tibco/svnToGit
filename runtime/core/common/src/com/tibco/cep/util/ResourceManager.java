package com.tibco.cep.util;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 28, 2004
 * Time: 6:40:06 PM
 * To change this template use Options | File Templates.
 */

/**
 * BEResourceManager Class.
 * This class has a ResourceBundle per locale.
 * There can be N resource bundles per locale. This class merges all the resource bundles.
 */


public class ResourceManager {

    static ResourceManager manager = null;
    private HashMap localeRegistry;

    private Locale defaultLocale;

    public static ResourceManager getInstance() {

        if (manager == null) {
            manager = new ResourceManager();
        }

        return manager;

    }

    private ResourceManager() {
        localeRegistry = new HashMap();
        defaultLocale = Locale.US;
        localeRegistry.put(defaultLocale, new HashMap());
    }

    public void setDefaultLocale(Locale l) {
        defaultLocale = l;
    }

    public Locale getDefaultLocale() {
        return defaultLocale;
    }


    /**
     * addResourceBundle - loads the resource bundle according to the ResourceBundle.getBundle method
     * @param resourceFile - A fully qualified name such as com.tibco.be.engine.main.MessageCode
     * @param l - The locale needed.
     * @return the newly loaded BEResourceBundle for any future use for the programmer
     */
    public ResourceBundle addResourceBundle(String resourceFile, Locale l ) {

        ResourceBundle b1;
        HashMap entries;

        if (l == null) {
            l = defaultLocale;
        }

        b1 = PropertyResourceBundle.getBundle(resourceFile, l);


        entries = (HashMap)localeRegistry.get(l);
        if (entries == null){
            entries = new HashMap();
            localeRegistry.put(l, entries);
        }

        Enumeration e = b1.getKeys();

        while (e.hasMoreElements()) {
            String  key = (String)e.nextElement();
            Object value = b1.getObject(key);
            entries.put(key,value);

        }
        return b1;


    }

    private String getMessage(String msgCode, Locale l, String defaultMessage) {
        if (l == null) l = defaultLocale;
        HashMap b1 = (HashMap) localeRegistry.get(l);
        String s = (String)b1.get(msgCode);
        if (s == null) return defaultMessage;
        return s;
    }

    public String getMessage(String msgCode)  {
        return getMessage(msgCode, defaultLocale, msgCode);
   }

    public String getMessage(String msgCode, Locale l)  {
        return getMessage(msgCode, l, msgCode);
    }


    public String formatMessage(String msgCode,  Object... parms) {
        String s = getMessage(msgCode, defaultLocale, msgCode);
        MessageFormat f = new MessageFormat(s, defaultLocale);
        return f.format(parms);
    }

    public String formatMessage(String msgCode, Locale l, Object[] parms) {
        String s = getMessage(msgCode, l, msgCode);
        MessageFormat f = new MessageFormat(s, l);
        return f.format(parms);
    }
}
