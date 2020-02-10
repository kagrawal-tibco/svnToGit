package com.tibco.cep.runtime.driver;

import java.util.List;

/**
 * @author ssinghal
 *
 */
public abstract class DriverPojo {
	
	public String type, label, className, description, version;
	public List<DriverProperty> driverProperties;
	public List<DriverProperty> driverSecurityProperties;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<DriverProperty> getDriverProperties() {
		return driverProperties;
	}

	public void setDriverProperties(List<DriverProperty> driverProperties) {
		this.driverProperties = driverProperties;
	}

	public List<DriverProperty> getDriverSecurityProperties() {
		return driverSecurityProperties;
	}

	public void setDriverSecurityProperties(List<DriverProperty> driverSecurityProperties) {
		this.driverSecurityProperties = driverSecurityProperties;
	}
	
	

}
