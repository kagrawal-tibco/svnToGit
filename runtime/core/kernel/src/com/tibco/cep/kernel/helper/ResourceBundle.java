package com.tibco.cep.kernel.helper;

import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2006
 * Time: 1:17:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceBundle {

    PropertyResourceBundle m_resourceBundle;
    Locale                 m_locale;

    public ResourceBundle(String propertyFile, Locale locale) {
        if (locale == null ) {
            m_resourceBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(propertyFile, Locale.getDefault());
            m_locale = Locale.getDefault();
        }
        else {
            m_resourceBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(propertyFile, locale);
            m_locale = locale;
        }
    }

    public Locale getLocale() {
        return m_locale;
    }

    public String getString(String key) {
        return m_resourceBundle.getString(key);
    }

    public Object getObject(String key) {
        return m_resourceBundle.getObject(key);
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
}
