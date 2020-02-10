package com.tibco.cep.decision.table.constraintpane;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decisionproject.util.DTConstants;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Aug 3, 2008
 * Time: 1:14:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class RangeFilter implements Filter {

    private static final String CLASS = RangeFilter.class.getName();    
	TreeMap<Integer, LinkedHashSet<Cell>> rangeMap = new TreeMap<Integer, LinkedHashSet<Cell>>(); //should be per type for efficiency

	
    public RangeFilter() {
    	//this.decisionTable = table;
    }

    public void eval(Object o, HashSet<String> result) {
        if (rangeMap.size() == 0) return;
        int value = (Integer)o;

        SortedMap<Integer, LinkedHashSet<Cell>> headMap = rangeMap.headMap(value+1); //
        for (LinkedHashSet<Cell> cells : headMap.values()) {
            for (Cell c : cells) {
                int minValue = (Integer) c.expression.operands[0];
                int maxValue = (Integer) c.expression.operands[1];
                findInRange(result, value, c, minValue, maxValue);
            }
        }
    }

    private void findInRange(HashSet<String> result, int value, Cell c, int minValue, int maxValue) {
        String ruleId = null;
        switch (c.expression.rangeKind) {
            case Operator.RANGE_BOUNDED : {
                if ((value >= minValue) && (value <= maxValue)) ruleId = c.ri.id;
                break;
            }
            case Operator.RANGE_MIN_EXCL_BOUNDED :
            {
                if ((value > minValue) && (value <= maxValue)) ruleId = c.ri.id;
                break;
            }
            case Operator.RANGE_MAX_EXCL_BOUNDED :
            {
                if ((value >= minValue) && (value < maxValue)) ruleId = c.ri.id;
                break;
            }
            case Operator.RANGE_MIN_MAX_EXCL_BOUNDED :
            {
                if ((value > minValue) && (value < maxValue)) ruleId = c.ri.id;
                break;
            }
        }
        if (null != ruleId) {
        	result.add(ruleId);
        }
        return;
    }
    
    

    /* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.constraintpane.Filter#updateCell(com.tibco.cep.decision.table.constraintpane.Cell, com.tibco.cep.decision.table.constraintpane.Cell)
	 */
    public void updateCell(List<Cell> oldCells, List<Cell> updatedCells) {
    	DecisionTableUIPlugin.debug(CLASS, "Updating cell(s) {0}", oldCells.toArray());
		
		//remove op1:oldCell && op2:oldCell, forall old cells
		for (Cell c : oldCells) {
			// skip equals cells
			if (c.expression.operatorKind != Operator.RANGE_OPERATOR) {
				continue;
			}
			removeCell(c);
		}
		
		//add op1:newCell && op2:newCell, forall new cells
		for (Cell c: updatedCells) {
			//skip equals cells
			if (c.expression.operatorKind != Operator.RANGE_OPERATOR) {
				continue;
			}
			addCell(c);
		}
	}
    
    
    
	
	@Override
	public Collection<Cell> getFilteredValues(Object key) {
		throw new UnsupportedOperationException("This method not supported by Range Filter");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.constraintpane.Filter#removeCell(com.tibco.cep.decision.table.constraintpane.Cell)
	 */
	public void removeCell(Cell removeCell) {
		
		if (removeCell.expression.operatorKind != Operator.RANGE_OPERATOR) {
			return;
		}
		
		Object expr1 = removeCell.expression.operands[0];
    	if (expr1 instanceof Integer) {
    		removeCell((Integer) expr1, removeCell);
    	} else if (expr1 instanceof String) {
            String op = (String) expr1;
            if (op.length() > 0 && op.charAt(0) == '"') {
            	op = op.substring(1, op.length() - 1);
            }
            try {
	            int intValue = Integer.valueOf(op);
	            removeCell(intValue, removeCell);
            } catch (NumberFormatException nfe) {
            	DecisionTableUIPlugin.log(nfe);
            }
    	}
        Object expr2 = removeCell.expression.operands[1];
    	if (expr2 instanceof Integer) {
    		removeCell((Integer) expr2, removeCell);
    	} else if (expr2 instanceof String){
            String op = (String) expr2;
            if (op.length() > 0 && op.charAt(0) == '"') {
            	op = op.substring(1, op.length() - 1);
            }
            try {
	            int intValue = Integer.valueOf(op);
	            removeCell(intValue, removeCell);
            } catch (NumberFormatException nfe) {
            	DecisionTableUIPlugin.log(nfe);
            }
    	}
	}

	public void addCell2(Cell c) {
        addCell((Integer) c.expression.operands[0], c);
        addCell((Integer) c.expression.operands[1], c);
    }

    public void addCell(Cell c) {
    	
    	if(c.ci.type == PROPERTY_TYPES.INTEGER_VALUE) {
	    	//decisionTable.getColumns()
	    	if (c.expression.operatorKind != Operator.RANGE_OPERATOR) {
	    		return;
	    	}
	    	
	    	Object expr1 = c.expression.operands[0];
	    	Object expr2 = c.expression.operands[1];
	    	
	    	Integer intOp1 = 0;
	    	Integer intOp2 = 0;
	    	// verify expressions
	    	try {
	    		if (!(expr1 instanceof Integer) && (expr1 instanceof String)) {
	    			String op1 = (String) expr1;
	    			if (op1.length() > 0) {
		                if (op1.charAt(0) == '"') {
		                	op1 = op1.substring(1, op1.length() - 1);
		                }	                             
	    			}
	    			intOp1 = Integer.valueOf(op1);
	    		} else if (expr1 instanceof Integer) {
	    			intOp1 = (Integer)expr1;
	    		}
	    		
	    		if (!(expr2 instanceof Integer) && (expr2 instanceof String)) {
	    			String op2 = (String) expr2;
	                if (op2.length() > 0 && op2.charAt(0) == '"') {
	                	op2 = op2.substring(1, op2.length() - 1);
	                }
	    			intOp2 = Integer.valueOf(op2);
	    		} else if (expr2 instanceof Integer) {
	    			intOp2 = (Integer)expr2;
	    		}
	    		
	    	} catch (NumberFormatException nfe) {	    		
	    		DecisionTableUIPlugin.log(nfe);				  
	      		return;
	    	}	    	
	    	
	    	addCell(intOp1, c);
	    	addCell(intOp2, c);
    	}
    }

    private void addCell (int expr, Cell c) {
    	LinkedHashSet<Cell> cells = rangeMap.get(expr);
        if (null == cells) {
            cells = new LinkedHashSet<Cell>();
            rangeMap.put(expr, cells);
        }
       	cells.add(c);
    }
    
    private void removeCell (int expr, Cell cellToRemove) {
    	LinkedHashSet<Cell> cells = rangeMap.get(expr);
        if (null == cells) {
        	rangeMap.remove(expr);
            return;
        }
        cells.remove(cellToRemove);
        if (cells.size() == 0) {
	    	 //Only then remove the entire entry
        	rangeMap.remove(expr);
		}
    }

    public int size() {
        return rangeMap.size();
    }

    public int[] getMinMax(boolean excludeIntegerMinMax) {
        int[] minmax = new int[2];
        
        NavigableSet<Integer> ranges = new TreeSet<Integer>();
        Set<Integer> keySet = rangeMap.keySet();
        
        for (Integer key : keySet) {
        	HashSet<Cell> cells = rangeMap.get(key);
        	for (Cell cell : cells) {
        		if (true == cell.isEnabled()) {
        			ranges.add(key);
        			continue;
        		}
        	}
        }
        
        if (ranges.size() == 0) {
        	return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        }

        Integer[] rangeArray = new Integer[0];
        rangeArray = ranges.toArray(rangeArray);
        minmax[0] = rangeArray[0];
        minmax[1] = rangeArray[rangeArray.length - 1];
        if (minmax[0] == Integer.MIN_VALUE) minmax[0] = rangeArray[1];
        if (minmax[1] == Integer.MAX_VALUE) minmax[1] = rangeArray[rangeArray.length - 2];
        return minmax;
    }

	@Override
	public void updateCell(Cell oldCell, Cell c) {
		// TODO Auto-generated method stub
		
	}
	
	public void clearMap() {
		for (Object key : rangeMap.keySet()) {
			rangeMap.get(key).clear();
		}
		rangeMap.clear();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.constraintpane.Filter#getFilterPriority()
	 */
	@Override
	public int getFilterPriority() {
		return Integer.MAX_VALUE - 1;
	}
}
