package com.tibco.cep.decision.table.constraintpane;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.tibco.decision.table.core.DecisionTableCorePlugin;



/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Aug 2, 2008
 * Time: 10:08:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class EqualsFilter implements Filter {
	
	// Map of [Operand] => List<Cells> for the column
	// Eg. 10=>[C12, C22, C32, ...]
    private HashMap<Object, LinkedHashSet<Cell>> map = new HashMap<Object, LinkedHashSet<Cell>>();

    public EqualsFilter() {

    }
    
    public HashSet<Cell> get(Object key) {
    	if (map.containsKey(key)) {
    		return map.get(key);
    	} else {
    		return new LinkedHashSet<Cell>();
    	}
    }

    public void eval(Object o, HashSet<String> set) {
    	LinkedHashSet<Cell> cells = map.get(o);
        if (cells == null) return;
        for (Cell c : cells) {
            set.add(c.getRuleTupleInfo().getId()); //return the list of ruleids to match
        }

    }
    
    protected boolean checkOperator(byte operatorKind) {
    	if (operatorKind != Operator.EQUALS_OPERATOR) {
    		return false;
    	}
    	return true;
    }

    public void addCell(Cell c) {
    	if (!checkOperator(c.getExpression().getOperatorKind())) {
    		return;    	
    	}
    	Object equalExpr = c.getExpression().getOperands()[0];
    	LinkedHashSet<Cell> cells = map.get(equalExpr);
        if (null == cells) {
            cells = new LinkedHashSet<Cell>();
            map.put(equalExpr, cells);
        }
        for (Cell tempCell : cells) {
        	if (tempCell.id.equals(c.id)) {
        		//Break off since there is already a cell
        		//with this id in this filter.
        		return;
        	}
        }
        cells.add(c);
    }
 
       
    public void updateCell(List<Cell> oldCells, List<Cell> updatedCells) {
		// clear old cells
    	DecisionTableCorePlugin.debug(this.getClass().getName(), "Updating cells {0}", oldCells.toArray());
		for (Cell oldCell : oldCells) {
			// skip range cells
			if (!checkOperator(oldCell.getExpression().getOperatorKind())) {
	    		return;    	
	    	}
			
			Object equalExpr = oldCell.getExpression().getOperands()[0];
			LinkedHashSet<Cell> cells = map.get(equalExpr);
			if (cells != null) {
				if (cells.size() == 1) {
			    	 //Only then remove the entire entry
			    	 map.remove(equalExpr);
			     } else {
			    	 cells.remove(oldCell);
			     }
			}
		}
		
		// add new cells
		for (Cell updatedCell : updatedCells) {
			//Check if this key exists
			// skip range cells
			if (!checkOperator(updatedCell.getExpression().getOperatorKind())) {
	    		return;    	
	    	}
			
			Object updatedExpression = updatedCell.getExpression().getOperands()[0];
			if (map.containsKey(updatedExpression)) {
				LinkedHashSet<Cell> existingCells = map.get(updatedExpression);
				//Append this new cell
				existingCells.add(updatedCell);
			} else {
				//Create key
				LinkedHashSet<Cell> newCells = new LinkedHashSet<Cell>();
				newCells.add(updatedCell);
				map.put(updatedExpression, newCells);
			}
		}
	}
    
    
    
    @Override
    /**
     * In this case we simply return all matching values
     */
	public Collection<Cell> getFilteredValues(Object key) {
		return map.get(key);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.constraintpane.Filter#removeCell(com.tibco.cep.decision.table.constraintpane.Cell)
	 */
	public void removeCell(Cell removeCell) {
		if (!checkOperator(removeCell.getExpression().getOperatorKind())) {
    		return;    	
    	}
		Object equalExpr = removeCell.getExpression().getOperands()[0];
		LinkedHashSet<Cell> cells = map.get(equalExpr);
		if (null == cells) {
			// Clear the entry
			map.remove(equalExpr);
			return;
		}
		cells.remove(removeCell);
		if (cells.size() == 0) {
	    	//Only then remove the entire entry
	    	map.remove(equalExpr);
		}
	}

	public int size() {
        return map.size();
    }

    public Set<Object> getKeySet() {
        return map.keySet();
    }

	@Override
	public void updateCell(Cell oldCell, Cell c) {
		// TODO Auto-generated method stub
		
	}
	
	public void clearMap() {
		for (Object key : map.keySet()) {
			map.get(key).clear();
		}
		map.clear();
	}
	
	/**
	 * Return all values in this filter
	 * @return
	 */
	public Collection<LinkedHashSet<Cell>> getValues() {
		return map.values();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.constraintpane.Filter#getFilterPriority()
	 */
	@Override
	public int getFilterPriority() {
		return Integer.MAX_VALUE;
	}

	public HashMap<Object, LinkedHashSet<Cell>> getMap() {
		return map;
	}
	
}