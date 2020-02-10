package com.tibco.cep.runtime.service.decision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: ssubrama
 * Creation Date: Aug 1, 2008
 * Creation Time: 7:20:42 PM
 * <p/>
 * $LastChangedDate$
 * $Rev$
 * $LastChangedBy$
 * $URL$
 */
public class DecisionTable {
    HashSet<RuleTupleInfo> ruleTuples = new HashSet<RuleTupleInfo>();
    LinkedHashMap<String, List<Cell>> conditionMatrix = new LinkedHashMap<String, List<Cell>>(); //Every cell [alias is id] it is Mcols (key) x N Rows. The matrix is reduced in the optimize phase
    LinkedHashMap<String, List<Filter>> optimizedMatrix = new LinkedHashMap<String, List<Filter>>();

    protected HashMap<String, Cell.CellInfo> cellInfoTable = new HashMap<String, Cell.CellInfo>();

    public void addRuleTupleInfo(RuleTupleInfo ri) {
        ruleTuples.add(ri);
    }

    public void addCell(Cell c) {
        if(c != null) {
            if(c.ci != null && c.ci.alias != null) {
                List<Cell> cells = conditionMatrix.get(c.ci.alias);
                if (cells == null) {
                    cells = new ArrayList<Cell>();
                    conditionMatrix.put(c.ci.alias, cells);
                }
                cells.add(c);
            }
        }
    }

    public void printStats() {
        int nosOfRules = ruleTuples.size();
        int nosOfConditions = 0;
        int [] cellTypes = new int[6];
        Set<Map.Entry<String, List<Cell>>> entrySet = conditionMatrix.entrySet();
        for (Map.Entry<String, List<Cell>> entry : entrySet) {
            List<Cell> lc = entry.getValue();
            nosOfConditions += lc.size();
            for (Cell c : lc) {
                ++(cellTypes[c.expression.operatorKind]);
            }
        }

        System.out.println("******** Non Optimized ***** ");
        System.out.printf("#Rules, #Conditions = [%d, %d]",  nosOfRules , nosOfConditions ).println();
        System.out.printf("#Total Cell size = %d MB",  ((nosOfConditions * 120)/1000000)).println();
        for (int i = 0; i < cellTypes.length; i++) {
            System.out.printf("# Conditions[%s]=%d", Operator.OperatorTypes[i] , cellTypes[i]).println();
        }

        nosOfConditions = 0;
        for (int i =0; i < cellTypes.length; i++) cellTypes[i] = 0;
        Set<Map.Entry<String, List<Filter>>> filterSet = optimizedMatrix.entrySet();
        for (Map.Entry<String, List<Filter>> entry : filterSet) {
            List<Filter> lf = entry.getValue();
            int cnt = 0;
            for (Filter f : lf ) {
                if (f != null) {
                    nosOfConditions += f.size();
                    cellTypes[cnt] += f.size();
                }
                ++cnt;
            }
        }

        System.out.println("******** Optimized ***** ");
        System.out.printf("#Rules, #Conditions = [%d, %d ]",  nosOfRules , nosOfConditions ).println();
        for (int i = 0; i < cellTypes.length; i++) {
            System.out.printf("# Conditions[%s]=%d", Operator.OperatorTypes[i] , cellTypes[i]).println();
        }

    }

    public void optimize() {
      long startTime = System.nanoTime();
        Set<Map.Entry<String, List<Cell>>> entrySet = conditionMatrix.entrySet();
        for (Map.Entry<String, List<Cell>> entry : entrySet) {
            Filter[] filters = new Filter[]{null, new EqualsFilter(), null, new RangeFilter(), null, null};
            List<Cell> lc = entry.getValue();

            for (Cell c : lc) {
                filters[c.expression.operatorKind].addCell(c);
            }
            optimizedMatrix.put(entry.getKey(), Arrays.asList(filters));
        }
        long endTime = System.nanoTime();
    System.out.printf("Total time for optimization :%d(ms)", (endTime - startTime)/1000000).println();

    }


    public void executeOptimized(HashMap input) throws Exception {

        HashSet resultantRules = evaluateConditionsOptimized(input);
        System.out.println("#Nos of rules to be fired - " + resultantRules.size());
        System.out.println("The following rules have to be fired :" + resultantRules.toString() );

        
    }

    public Set<String> getColumns() {
        return optimizedMatrix.keySet();
    }

    public Filter getFilter(String columnName) {
        List<Filter> filters = optimizedMatrix.get(columnName);
        for (Filter f : filters) {
            if (null == f) continue;
            if (f.size() > 0) return f;
        }
        return null;

    }


    public HashSet evaluateConditionsOptimized(HashMap input) {
        HashSet resultantRules = new HashSet();
        Set<Map.Entry<String, List<Filter>>> filterSet = optimizedMatrix.entrySet();
        int cnt = 0;
        long[] times = new long[filterSet.size()];
        String[] columns = new String[filterSet.size()];
        int[] sizes = new int[filterSet.size()];
        for (Map.Entry<String, List<Filter>> entry : filterSet) {
            List<Filter> lf = entry.getValue();

            Object value = input.get(entry.getKey());
            long stTime = System.nanoTime();
            HashSet columnSet = new HashSet();
            for (Filter f : lf ) {
                if (f != null) {
                    f.eval(value, columnSet);
                }
            }

            sizes[cnt] = columnSet.size();
            if (cnt == 0) {

                resultantRules.addAll(columnSet);

            }
            else {
                resultantRules.retainAll(columnSet);
            }

            times[cnt] = (System.nanoTime() - stTime)/1000000;
            columns[cnt]  = entry.getKey();
            ++cnt;

        }

        long totalTime = 0;
        for (int i=0; i< times.length; i++) {
            totalTime += times[i];
            System.out.printf("Evaluating Column[%s]= %d, Matched[#rules] = %d", columns[i], times[i], sizes[i]).println();
        }
        System.out.printf("Total Evaluation time takes %d", totalTime).println();

        return resultantRules;
    }


}
