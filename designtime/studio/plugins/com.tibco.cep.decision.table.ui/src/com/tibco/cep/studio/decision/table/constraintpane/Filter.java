package com.tibco.cep.studio.decision.table.constraintpane;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: suresh Date: Aug 2, 2008 Time: 10:07:35 PM To
 * change this template use File | Settings | File Templates.
 */
public interface Filter {
	
	void eval(Object o, HashSet<String> result);
	
	/**
	 * Add a new {@link Cell}
	 * @param c
	 */
	void addCell(Cell c);
	
	/**
	 * Update an existing cell with new cell
	 * @param oldCell
	 * @param c
	 */
	void updateCell(Cell oldCell, Cell c);
	
	/**
	 * @param removeCell
	 */
	void removeCell(Cell removeCell);

	int size();

	/**
	 * Update a cell for all the ORed and ANDed expressions it contains
	 * @param oldCells the existing expressions
	 * @param updatedCells the new expressions
	 */
	void updateCell(List<Cell> oldCells, List<Cell> updatedCells);
	
	void clearMap();
	
	/**
	 * Filter a collection and get values for this key
	 * @param key
	 * @return
	 */
	Collection<Cell> getFilteredValues(Object key);
	
	/**
	 * An integer used for ordering filters
	 * @return
	 */
	int getFilterPriority();
}
