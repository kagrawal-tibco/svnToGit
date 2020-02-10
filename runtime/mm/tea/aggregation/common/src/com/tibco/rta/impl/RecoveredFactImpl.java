package com.tibco.rta.impl;

import java.util.ArrayList;
import java.util.List;

import com.tibco.rta.model.DimensionHierarchy;

/**
 * Used by the revovery process, to tell which hierarchies are already processed and which are not.
 * 
 * @author bgokhale
 *
 */
public class RecoveredFactImpl extends FactImpl {

	private static final long serialVersionUID = 1L;
	
	List<DimensionHierarchy> unprocessedList = new ArrayList<DimensionHierarchy>();
	
	public List<DimensionHierarchy> getUnProcessedHierarchies() {
		return unprocessedList;
	}
	
	public void addToUnprocessedList(DimensionHierarchy hierarchy) {
		this.unprocessedList.add(hierarchy);
	}

}
