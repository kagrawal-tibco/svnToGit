package com.tibco.cep.studio.dashboard.core.model.impl.metric;

import java.util.Comparator;

public class GroupByPositionComparator implements Comparator<LocalMetricField> {

	public int compare(LocalMetricField o1, LocalMetricField o2) {
		try {
	        return (int) (o1.getGroupByPosition() - o2.getGroupByPosition());
        } catch (Exception e) {
        	return 0;
        }
    }

}