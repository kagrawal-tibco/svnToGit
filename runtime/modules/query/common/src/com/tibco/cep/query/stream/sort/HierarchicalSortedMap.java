package com.tibco.cep.query.stream.sort;

import java.util.Comparator;

/*
 * Author: Ashwin Jayaprakash Date: Oct 5, 2007 Time: 2:29:59 PM
 */

public class HierarchicalSortedMap
        extends GenericHierarchicalSortedMap<Object, GenericHierarchicalSortedMap> {
    public HierarchicalSortedMap(Comparator<? super Object> c) {
        super(c);
    }
}
