/**
 * User: ishaan
 * Date: Mar 29, 2004
 * Time: 11:39:45 AM
 */
package com.tibco.be.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

public class BEResourceBundle {

    static String language;
    static String country;
    static String variant;
    static Locale locale;
    static {
        language = System.getProperty("com.tibco.be.language");
        if (language != null) {
            country  = System.getProperty("com.tibco.be.country");
            if (country != null) {
                variant  = System.getProperty("com.tibco.be.variant");
                if (variant != null) {
                    locale = new Locale(language, country, variant);
                }
                locale = new Locale(language, country);
            }
            locale = new Locale(language);
        }
    }

    PropertyResourceBundle m_resourceBundle;

    public BEResourceBundle(String propertyFile) {
        if (locale == null ) {
            m_resourceBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(propertyFile);
        }
        else {
            m_resourceBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(propertyFile, locale);
        }
    }

    public BEResourceBundle(InputStream stream) throws IOException {
            m_resourceBundle = new PropertyResourceBundle(stream);            
    }

    public String getString(String key) {
        try {
            return m_resourceBundle.getString(key);
        }
        catch(MissingResourceException ex) {
            return key;
        }
    }

    public Object getObject(String key) {
        return m_resourceBundle.getObject(key);
    }

    public Locale getLocale() {
        return locale;
    }

    public String[] getStringArray(String key){
        return m_resourceBundle.getStringArray(key);
    }

    public Object handleGetObject(String key) {
        return m_resourceBundle.handleGetObject(key);
    }

    public Enumeration getKeys(){
        return m_resourceBundle.getKeys();
    }

    public String formatString(String key, Object arg0) {
        try {
            MessageFormat formatter = new MessageFormat(m_resourceBundle.getString(key));
            Object[] args = { arg0 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return key + " " + arg0;
        }
    }

    public String formatString(String key, Object arg0, Object arg1) {
        try {
            MessageFormat formatter = new MessageFormat(m_resourceBundle.getString(key));
            Object[] args = { arg0, arg1 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return key + " " + arg0 + " " + arg1;
        }
    }

    public String formatString(String key, Object arg0, Object arg1, Object arg2) {
        try {
            MessageFormat formatter = new MessageFormat(m_resourceBundle.getString(key));
            Object[] args = { arg0, arg1, arg2 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return key + " " + arg0 + " " + arg1 + " " + arg2;
        }
    }

    public String formatString(String key, Object arg0, Object arg1, Object arg2, Object arg3) {
        try {
            MessageFormat formatter = new MessageFormat(m_resourceBundle.getString(key));
            Object[] args = { arg0, arg1, arg2, arg3 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return key + " " + arg0 + " " + arg1 + " " + arg2 + " " + arg3;
        }
    }

    public String formatString(String key, Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
        try {
            MessageFormat formatter = new MessageFormat(m_resourceBundle.getString(key));
            Object[] args = { arg0, arg1, arg2, arg3, arg4 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return key + " " + arg0 + " " + arg1 + " " + arg2 + " " + arg3 + " " + arg4;
        }
    }

    public String formatString(String key, Object arg0, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        try {
            MessageFormat formatter = new MessageFormat(m_resourceBundle.getString(key));
            Object[] args = { arg0, arg1, arg2, arg3, arg4, arg5 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return key + " " + arg0 + " " + arg1 + " " + arg2 + " " + arg3 + " " + arg4 + " " + arg5;
        }
    }

}
