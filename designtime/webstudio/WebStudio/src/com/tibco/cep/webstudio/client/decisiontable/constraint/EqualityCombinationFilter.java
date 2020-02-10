/**
 * 
 */
package com.tibco.cep.webstudio.client.decisiontable.constraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * A combination filter which simply delegates operations
 * like add/remove/update etc. to its children.
 * @author aathalye
 *
 */
public class EqualityCombinationFilter extends EqualsFilter implements Filter {

	private List<EqualsFilter> children = new ArrayList<EqualsFilter>();
	
	public void addFilter(EqualsFilter child) {
		if (child != null) {
			children.add(child);
		}
	}
	
	public Set<Object> getKeySet() {
		Set<Object> keySet = new LinkedHashSet<Object>();
		for (EqualsFilter child : children) {
			keySet.addAll(child.getKeySet());
		}
		return keySet;
	}
	
	public HashSet<Cell> get(Object key) {
		HashSet<Cell> values = new LinkedHashSet<Cell>();
		for (EqualsFilter child : children) {
			// Check if any filter has it
			HashSet<Cell> cells = child.get(key);
			if (!cells.isEmpty()) {
				return cells;
			}
		}
		return values;
	}
	
	public EqualsFilter getFilterForKey(Object key) {
		for (EqualsFilter child : children) {
			HashSet<Cell> cells = child.get(key);
			if (!cells.isEmpty()) {
				return child;
			}
		}
		
		return null;
	}

	@Override
	public Collection<Cell> getFilteredValues(Object key) {
		Collection<Cell> filteredValues = new LinkedHashSet<Cell>();
		for (EqualsFilter filter : children) {
			Collection<Cell> filterFilteredValues = filter.getFilteredValues(key);
			if (filterFilteredValues != null) {
				filteredValues.addAll(filterFilteredValues);
			}
		}
		return filteredValues;
	}

	@Override
	public Collection<LinkedHashSet<Cell>> getValues() {
		Collection<LinkedHashSet<Cell>> allValues = new LinkedHashSet<LinkedHashSet<Cell>>();
		for (EqualsFilter filter : children) {
			allValues.addAll(filter.getValues());
		}
		return allValues;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.constraintpane.EqualsFilter#size()
	 */
	@Override
	public int size() {
		int size = 0;
		for (EqualsFilter filter : children) {
			size += filter.size();
		}
		return size;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.constraintpane.EqualsFilter#addCell(com.tibco.cep.decision.table.constraintpane.Cell)
	 */
	@Override
	public void addCell(Cell cell) {
		//Do this for each child
		for (EqualsFilter childFilter : children) {
			childFilter.addCell(cell);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.constraintpane.EqualsFilter#removeCell(com.tibco.cep.decision.table.constraintpane.Cell)
	 */
	@Override
	public void removeCell(Cell removeCell) {
		//Do this for each child
		for (EqualsFilter childFilter : children) {
			childFilter.removeCell(removeCell);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.constraintpane.EqualsFilter#updateCell(com.tibco.cep.decision.table.constraintpane.Cell, com.tibco.cep.decision.table.constraintpane.Cell)
	 */
	@Override
	public void updateCell(Cell oldCell, Cell newCell) {
		//Do this for each child
		for (EqualsFilter childFilter : children) {
			childFilter.updateCell(oldCell, newCell);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.constraintpane.EqualsFilter#updateCell(java.util.List, java.util.List)
	 */
	@Override
	public void updateCell(List<Cell> oldCells, List<Cell> updatedCells) {
		//Do this for each child
		for (EqualsFilter childFilter : children) {
			childFilter.updateCell(oldCells, updatedCells);
		}
	}
}
