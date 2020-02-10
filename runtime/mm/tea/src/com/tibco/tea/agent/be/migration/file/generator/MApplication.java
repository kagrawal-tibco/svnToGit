package com.tibco.tea.agent.be.migration.file.generator;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author ssinghal
 *
 */

public class MApplication {
	
	String name;
	String earPath;
	String cddPath;
	//Map<String, Object> globalVariables;
	List<MInstance> instance;
	//List<MMachine> machines;
	boolean isSelected = false;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEarPath() {
		return earPath;
	}
	public void setEarPath(String earPath) {
		this.earPath = earPath;
	}
	public String getCddPath() {
		return cddPath;
	}
	public void setCddPath(String cddPath) {
		this.cddPath = cddPath;
	}
	/*public Map<String, Object> getGlobalVariables() {
		return globalVariables;
	}
	public void setGlobalVariables(Map<String, Object> globalVariables) {
		this.globalVariables = globalVariables;
	}*/
	public List<MInstance> getInstance() {
		return instance;
	}
	public void setInstance(List<MInstance> instance) {
		this.instance = instance;
	}
	/*public List<MMachine> getMachines() {
		return machines;
	}
	public void setMachines(List<MMachine> machines) {
		this.machines = machines;
	}*/

	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	
}
