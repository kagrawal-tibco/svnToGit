package com.tibco.cep.runtime.service.decision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Aug 2, 2008
 * Time: 10:08:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class EqualsFilter implements Filter{
    HashMap<Object, List<Cell>> map = new HashMap<Object, List<Cell>>();

    public EqualsFilter() {

    }

    public void eval(Object o, HashSet set) {
        List<Cell> cells = map.get(o);
        if (cells == null) return;
        for (Cell c : cells) {
            set.add(c.ri.id); //return the list of ruleids to match
        }

    }

    public void addCell(Cell c) {
        String equalExpr = (String) c.expression.operands[0];
        List<Cell> cells = map.get(equalExpr);
        if (null == cells) {
            cells = new ArrayList<Cell>();
            map.put(equalExpr, cells);
        }
        cells.add(c);
    }

    public int size() {
        return map.size();
    }

    public Set getKeySet() {
        return map.keySet();
    }
}
