package com.tibco.cep.dashboard.psvr.context;

import java.util.Properties;

/**
 * @author apatil
 * 
 */
public class Context {
	
	private String name;
	private String rolloverTime;

	public Context(String name, Properties properties) {
		if (name == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		this.name = name;
		if (properties != null) {
			this.rolloverTime = (String) ContextProperties.ROLLOVER_TIME_KEY.getValue(properties);
		}
	}

	public String getName() {
		return name;
	}

	public String getRolloverTime() {
		return rolloverTime;
	}

	public int hashCode() {
		return name.hashCode();
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder(Context.class.getName() + "[");
		buffer.append("name=" + name);
		buffer.append("]");
		return buffer.toString();
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if ((obj instanceof Context) == false) {
			return false;
		}
		Context castedObj = (Context) obj;
		return castedObj.name.equals(name);
	}

	boolean shutdown() {
		return true;
	}

}