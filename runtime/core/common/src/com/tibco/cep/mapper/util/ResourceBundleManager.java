package com.tibco.cep.mapper.util;

import java.net.URL;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;


/**
 * ResourceBundleManager Class.
 * This class has a ResourceBundle per locale.
 * There can be N resource bundles per locale. This class merges all the resource bundles.
 */


public class ResourceBundleManager {

    static ResourceBundleManager manager = null;
    static final Object guard = new Object();
    private ConcurrentHashMap localeRegistry;
    private ConcurrentHashMap iconRegistry;

    private Locale defaultLocale;

    public static ResourceBundleManager getInstance() {
        if (manager == null) {
            synchronized (guard) {
                if (manager == null)
                    manager = new ResourceBundleManager();
            }
        }
        return manager;
    }

    private ResourceBundleManager() {
        localeRegistry = new ConcurrentHashMap();
        iconRegistry = new ConcurrentHashMap();
        defaultLocale = Locale.US;
        localeRegistry.put(defaultLocale, new HashMap());
    }

    public void setDefaultLocale(Locale l) {
        defaultLocale = l;
    }

    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    public static void addResourceBundle(String resourceName, Locale l) {
        getInstance()._addResourceBundle(resourceName, l);
    }

    /**
     * addResourceBundle - loads the resource bundle according to the ResourceBundle.getBundle method
     * @param resourceFile - A fully qualified name such as com.tibco.be.engine.main.MessageCode
     * @param l - The locale needed.
     * @return the newly loaded ResourceBundle for future use
     */
    private void _addResourceBundle(String resourceFile, Locale l ) {

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
        return ;
    }

    /**
     * Get a message, for a given locale, and if it doesn't exists, then return the defaultMessage
     * @param msgCode
     * @param l
     * @param defaultMessage
     * @return
     */

    private String getMessage(String msgCode, Locale l, String defaultMessage) {
        if (l == null) l = defaultLocale;
        HashMap b1 = (HashMap) localeRegistry.get(l);
        String s = (String)b1.get(msgCode);
        if (s == null) return defaultMessage;
        return s;
    }

    private String _getMessage(String msgCode)  {
        return getMessage(msgCode, defaultLocale, msgCode);
    }

    private String _getMessage(String msgCode, Locale l)  {
        return getMessage(msgCode, l, msgCode);

    }

    private String _getFormattedMessage(String msgCode, Object... args) {

        String s = getMessage(msgCode, defaultLocale, msgCode);
        MessageFormat f = new MessageFormat(s, defaultLocale);
        return f.format(args);
    }


    private String _getFormattedMessage(String msgCode, Locale l, Object... parms) {
        String s = getMessage(msgCode, l, msgCode);
        MessageFormat f = new MessageFormat(s, l);
        return f.format(parms);
    }

    public static String getMessage(String msgCode) {
        return ResourceBundleManager.getInstance()._getMessage(msgCode);

    }

    public static String getMessage(String msgCode, Locale l) {
        return ResourceBundleManager.getInstance()._getMessage(msgCode, l);

    }

    public static String getMessage(String msgCode, Object... args) {
        return ResourceBundleManager.getInstance()._getFormattedMessage(msgCode, args);

    }
    public static String getMessage(String msgCode, Locale l, Object... parms) {
        return ResourceBundleManager.getInstance()._getFormattedMessage(msgCode, l, parms);

    }


    public static Icon getIcon(String key, ClassLoader classLoader) {

        Icon icon = (Icon) getInstance().iconRegistry.get(key);
        if (icon == null) {
            String s = getMessage(key);

            if (s == null) throw new MissingResourceException(key, ResourceBundleManager.class.getName(), key);

            URL url = null;
            String name = s.substring(1);
            if (classLoader != null) {
            	url = classLoader.getResource(name);
            }
            if (url == null) {
            	ResourceBundleManager.class.getClassLoader().getResource(name);
            }
            if (url == null) {
                url = ClassLoader.getSystemClassLoader().getResource(s);
                if (url == null) {
                	throw new MissingResourceException(s, ResourceBundleManager.class.getName(), key);
                }
            }
            icon = new ImageIcon(url);
            getInstance().iconRegistry.put(key, icon);

        }

        return icon;
    }
}
