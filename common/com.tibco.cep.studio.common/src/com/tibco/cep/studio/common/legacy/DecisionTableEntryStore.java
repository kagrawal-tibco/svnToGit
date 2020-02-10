/**
 * 
 */
package com.tibco.cep.studio.common.legacy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author aathalye
 *
 */
public class DecisionTableEntryStore {
	
	private List<String> dtPaths = new ArrayList<String>();
	
	public List<String> getDtPaths() {
		return dtPaths;
	}

	public boolean addEntry(String dtName, String folderPath) {
		String dtPath = new StringBuilder(folderPath).append(dtName).toString();
		return dtPaths.add(dtPath);
	}
	
	public Iterator<String> getEntries() {
		return dtPaths.iterator();
	}
	
	public void destroy() {
		dtPaths.clear();
	}

}
