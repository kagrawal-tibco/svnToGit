package com.tibco.cep.dashboard.management;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.utils.StringUtil;

public class ManagementProperties extends Properties {

	private static final long serialVersionUID = -2068264688887091820L;

	protected Properties source;

	protected String keyPrefix;

	protected boolean keepUnmatchedEntries;

	protected boolean retainOriginalEntries;

	public ManagementProperties(Properties source, String keyPrefix){
		this(source,keyPrefix,false);
	}

	public ManagementProperties(Properties source, String keyPrefix, boolean keepUnmatchedEntries){
		this(source);
		if (StringUtil.isEmptyOrBlank(keyPrefix) == true){
			throw new IllegalArgumentException("invalid key prefix");
		}
		this.keyPrefix = keyPrefix;
		if (this.keyPrefix.endsWith(".") == false){
			this.keyPrefix = this.keyPrefix + ".";
		}
		this.keepUnmatchedEntries = keepUnmatchedEntries;
		int beginIndex = keyPrefix.length() + 1;
		//go over all the source properties
		Enumeration<Object> keys = source.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String propValue = source.getProperty(key);
			if (StringUtil.isEmptyOrBlank(propValue) == false){
				if (key.startsWith(this.keyPrefix) == true){
					key = key.substring(beginIndex);
					setPropertyActually(key, propValue);
				}
				else if (keepUnmatchedEntries == true) {
					this.setPropertyActually(key, propValue);
				}
				else {
					//skip the entry
				}
			}
		}
	}

	protected ManagementProperties(Properties source){
		this.source = source;
		this.keyPrefix = null;
		this.keepUnmatchedEntries = true;
	}

	protected synchronized Object setPropertyActually(String key,String value){
		return super.put(key, value);
	}

	@Override
	public synchronized Object setProperty(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized Object put(Object key, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized void putAll(Map<? extends Object, ? extends Object> t) {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized Object remove(Object key) {
		throw new UnsupportedOperationException();
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	//INFO this API inadvertently exposes the underlying properties class
	public Properties getSource(){
		return source;
	}

	public void updateProperty(String key, String value) {
		String sourceKey = key;
		String keyPrefix = getKeyPrefix();
		if (key.startsWith(keyPrefix) == true){
			key = key.substring(keyPrefix.length());
		}
		else {
			sourceKey = keyPrefix + key;
		}
		setPropertyActually(key, value);
		source.setProperty(sourceKey, value);
	}

}