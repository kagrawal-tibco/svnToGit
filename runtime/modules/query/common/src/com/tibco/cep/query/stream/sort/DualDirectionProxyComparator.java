package com.tibco.cep.query.stream.sort;

import java.util.Comparator;

/*
 * Author: Ashwin Jayaprakash Date: Dec 12, 2007 Time: 6:48:33 PM
 */

public class DualDirectionProxyComparator implements Comparator {
    protected final boolean ascending;

    protected final Comparator<Object> actualComparator;

    public DualDirectionProxyComparator(boolean ascending, Comparator<Object> actualComparator) {
        this.ascending = ascending;
        this.actualComparator = actualComparator;
    }

    public boolean isAscending() {
        return ascending;
    }

    public Comparator<Object> getActualComparator() {
        return actualComparator;
    }

    public int compare(Object o1, Object o2) {
        int c = actualComparator.compare(o1, o2);

        return ascending ? c : (-1 * c);
    }
}
