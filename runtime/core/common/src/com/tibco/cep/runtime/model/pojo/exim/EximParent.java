package com.tibco.cep.runtime.model.pojo.exim;

import com.tibco.cep.runtime.model.element.impl.property.atomic.PropertyAtomAtomic;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomImpl;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArraySimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomSimple;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash / Date: 12/14/11 / Time: 11:51 AM
*/
public abstract class EximParent {
    public static final boolean separateTimezone = Boolean.parseBoolean(System.getProperty(SystemProperty.AS_TUPLE_EXPLICIT_STORE_TIMEZONES.getPropertyName(), Boolean.TRUE.toString()));
    protected static final String TIMEZONE_SUFFIX = "_tz$";	

    protected EximParent() {
    }

    protected String normalizeStateMachineName(String name) {
        return name.replace('$', '_');
    }

    protected String normalizeToSimpleType(PropertyAtomSimple source) {
        //return source.getClass().getSuperclass().getName();
        return source.getClass().getName();
    }

    protected String normalizeToSimpleType(PropertyAtomAtomic source) {
        //return source.getClass().getSuperclass().getName();
        return source.getClass().getName();
    }

    protected String normalizeToSimpleType(PropertyArraySimple source) {
        //return source.getClass().getSuperclass().getName();
        return source.getClass().getName();
    }
    
    protected String normalizeToSimpleType(PropertyAtomImpl source) {
        //return source.getClass().getSuperclass().getName();
        return source.getClass().getName();
    }
    
    protected String normalizeToSimpleType(PropertyArrayImpl source) {
        //return source.getClass().getSuperclass().getName();
        return source.getClass().getName();
    }
    
    public static String timeZoneName(String dateTimeName) {
    	return dateTimeName + TIMEZONE_SUFFIX;
    }
    
    protected boolean isClusterMode() {
    	return (RuleServiceProviderManager.getInstance().getDefaultProvider().getCluster() != null);
    }
}
