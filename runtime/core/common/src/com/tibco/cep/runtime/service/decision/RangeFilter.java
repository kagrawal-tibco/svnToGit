package com.tibco.cep.runtime.service.decision;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Aug 3, 2008
 * Time: 1:14:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class RangeFilter implements Filter {

    TreeMap<Integer, List<Cell>> rangeMap = new TreeMap<Integer, List<Cell>>(); //should be per type for efficiency
    public RangeFilter() {

    }

    public void eval(Object o, HashSet result) {
        if (rangeMap.size() == 0) return;
        int value = (Integer)o;

        SortedMap<Integer,List<Cell>> headMap = rangeMap.headMap(value+1); //
        for (List<Cell> cells : headMap.values()) {
            for (Cell c : cells) {
                int minValue = (Integer) c.expression.operands[0];
                int maxValue = (Integer)c.expression.operands[1];
                findInRange(result, value, c, minValue, maxValue);
            }
        }
    }

    private void findInRange(HashSet result, int value, Cell c, int minValue, int maxValue) {
        String ruleId = null;
        switch (c.expression.rangeKind) {
            case Operator.RANGE_BOUNDED: {
                if ((value >= minValue) && (value <= maxValue)) ruleId = c.ri.id;
                break;
            }
            case Operator.RANGE_MIN_EXCL_BOUNDED:
            {
                if ((value > minValue) && (value <= maxValue)) ruleId = c.ri.id;
                break;
            }
            case Operator.RANGE_MAX_EXCL_BOUNDED:
            {
                if ((value >= minValue) && (value < maxValue)) ruleId = c.ri.id;
                break;
            }
            case Operator.RANGE_MIN_MAX_EXCL_BOUNDED:
            {
                if ((value > minValue) && (value < maxValue)) ruleId = c.ri.id;
                break;
            }
        }
        if (null != ruleId) result.add(ruleId);
        return;

    }

    public void addCell(Cell c) {
        addCell((Integer) c.expression.operands[0], c);
        addCell((Integer) c.expression.operands[1], c);
    }

    private void addCell (int expr, Cell c) {
        List<Cell> cells = rangeMap.get(expr);
        if (null == cells) {
            cells = new ArrayList<Cell>();
            rangeMap.put(expr, cells);
        }
        cells.add(c);
    }

    public int size() {
        return rangeMap.size();
    }

    public int[] getMinMax(boolean excludeIntegerMinMax) {
        int[] minmax = new int[2];
        Set<Integer> ranges = rangeMap.keySet();

        Integer[] rangeArray = new Integer[0];
        rangeArray = ranges.toArray(rangeArray);
        minmax[0] = rangeArray[0];
        minmax[1] = rangeArray[rangeArray.length - 1];
        if (minmax[0] == Integer.MIN_VALUE) minmax[0] = rangeArray[1];
        if (minmax[1] == Integer.MAX_VALUE) minmax[0] = rangeArray[rangeArray.length - 2];
        return minmax;
    }
}
