package com.tibco.cep.kernel.service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;

import com.tibco.cep.kernel.helper.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2006
 * Time: 2:03:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceManager {
    static HashMap        m_resBundles;
    static ResourceBundle m_defaultBundle;
    static {
        m_resBundles = new HashMap();
        m_defaultBundle = new ResourceBundle("com.tibco.cep.kernel.messages", Locale.getDefault());
        m_resBundles.put(Locale.getDefault(), m_defaultBundle);
        m_resBundles.put(Locale.ENGLISH, new ResourceBundle("com.tibco.cep.kernel.messages", Locale.ENGLISH));
    }

    static public void setDefaultLocale(Locale locale) {
        m_defaultBundle = (ResourceBundle) m_resBundles.get(locale);
        if(m_defaultBundle == null) {
            m_defaultBundle = new ResourceBundle("com.tibco.cep.kernel.messages", locale);
            m_resBundles.put(locale, m_defaultBundle);
        }
    }

    static public Locale getDefaultLocale() {
        return m_defaultBundle.getLocale();
    }

    static public String getString(String msgCode) {
        try {
            return m_defaultBundle.getString(msgCode);
        }
        catch(MissingResourceException ex) {
            return msgCode;
        }
    }

    static public String getString (String msgCode, Locale locale) {
        try {
            ResourceBundle bundle = (ResourceBundle) m_resBundles.get(locale);
            if(bundle == null) {
                return m_defaultBundle.getString(msgCode);
            }
            else {
                return bundle.getString(msgCode);
            }
        }
        catch(MissingResourceException ex) {
            return msgCode;
        }
    }

    static public String formatString(String msgCode, Object arg0) {
        try {
            MessageFormat formatter = new MessageFormat(m_defaultBundle.getString(msgCode));
            Object[] args = { arg0 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return msgCode + " " + arg0;
        }
    }

    static public String formatString(String msgCode, Locale locale, Object arg0) {
        try {
            ResourceBundle bundle = (ResourceBundle) m_resBundles.get(locale);
            String msg = (bundle == null? m_defaultBundle.getString(msgCode): bundle.getString(msgCode));
            MessageFormat formatter = new MessageFormat(msg);
            Object[] args = { arg0 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return msgCode + " " + arg0;
        }
    }

    static public String formatString(String msgCode, Object arg0, Object arg1) {
        try {
            MessageFormat formatter = new MessageFormat(m_defaultBundle.getString(msgCode));
            Object[] args = { arg0, arg1 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return msgCode + " " + arg0 + " " + arg1;
        }
    }

    static public String formatString(String msgCode, Locale locale, Object arg0, Object arg1) {
        try {
            ResourceBundle bundle = (ResourceBundle) m_resBundles.get(locale);
            String msg = (bundle == null? m_defaultBundle.getString(msgCode): bundle.getString(msgCode));
            MessageFormat formatter = new MessageFormat(m_defaultBundle.getString(msg));
            Object[] args = { arg0, arg1 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return msgCode + " " + arg0 + " " + arg1;
        }
    }

    static public String formatString(String msgCode, Object arg0, Object arg1, Object arg2) {
        try {
            MessageFormat formatter = new MessageFormat(m_defaultBundle.getString(msgCode));
            Object[] args = { arg0, arg1, arg2 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return msgCode + " " + arg0 + " " + arg1 + " " + arg2;
        }
    }

    static public String formatString(String msgCode, Locale locale, Object arg0, Object arg1, Object arg2) {
        try {
            ResourceBundle bundle = (ResourceBundle) m_resBundles.get(locale);
            String msg = (bundle == null? m_defaultBundle.getString(msgCode): bundle.getString(msgCode));
            MessageFormat formatter = new MessageFormat(m_defaultBundle.getString(msg));
            Object[] args = { arg0, arg1, arg2 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return msgCode + " " + arg0 + " " + arg1 + " " + arg2;
        }
    }

    static public String formatString(String msgCode, Object arg0, Object arg1, Object arg2, Object arg3) {
        try {
            MessageFormat formatter = new MessageFormat(m_defaultBundle.getString(msgCode));
            Object[] args = { arg0, arg1, arg2, arg3 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return msgCode + " " + arg0 + " " + arg1 + " " + arg2 + " " + arg3;
        }
    }

    static public String formatString(String msgCode, Locale locale, Object arg0, Object arg1, Object arg2, Object arg3) {
        try {
            ResourceBundle bundle = (ResourceBundle) m_resBundles.get(locale);
            String msg = (bundle == null? m_defaultBundle.getString(msgCode): bundle.getString(msgCode));
            MessageFormat formatter = new MessageFormat(m_defaultBundle.getString(msg));
            Object[] args = { arg0, arg1, arg2, arg3 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return msgCode + " " + arg0 + " " + arg1 + " " + arg2 + " " + arg3;
        }
    }

    static public String formatString(String msgCode, Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
        try {
            MessageFormat formatter = new MessageFormat(m_defaultBundle.getString(msgCode));
            Object[] args = { arg0, arg1, arg2, arg3, arg4 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return msgCode + " " + arg0 + " " + arg1 + " " + arg2 + " " + arg3 + " " + arg4;
        }
    }

    static public String formatString(String msgCode, Locale locale, Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
        try {
            ResourceBundle bundle = (ResourceBundle) m_resBundles.get(locale);
            String msg = (bundle == null? m_defaultBundle.getString(msgCode): bundle.getString(msgCode));
            MessageFormat formatter = new MessageFormat(m_defaultBundle.getString(msg));
            Object[] args = { arg0, arg1, arg2, arg3, arg4 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return msgCode + " " + arg0 + " " + arg1 + " " + arg2 + " " + arg3 + " " + arg4;
        }
    }

    static public String formatString(String msgCode, Object arg0, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        try {
            MessageFormat formatter = new MessageFormat(m_defaultBundle.getString(msgCode));
            Object[] args = { arg0, arg1, arg2, arg3, arg4, arg5 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return msgCode + " " + arg0 + " " + arg1 + " " + arg2 + " " + arg3 + " " + arg4 + " " + arg5;
        }
    }

    static public String formatString(String msgCode, Locale locale, Object arg0, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        try {
            ResourceBundle bundle = (ResourceBundle) m_resBundles.get(locale);
            String msg = (bundle == null? m_defaultBundle.getString(msgCode): bundle.getString(msgCode));
            MessageFormat formatter = new MessageFormat(m_defaultBundle.getString(msg));
            Object[] args = { arg0, arg1, arg2, arg3, arg4, arg5 };
            return formatter.format(args);
        }
        catch(MissingResourceException ex) {
            return msgCode + " " + arg0 + " " + arg1 + " " + arg2 + " " + arg3 + " " + arg4 + " " + arg5;
        }
    }
}
