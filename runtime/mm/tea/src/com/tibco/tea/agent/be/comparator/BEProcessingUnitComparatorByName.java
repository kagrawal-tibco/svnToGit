package com.tibco.tea.agent.be.comparator;

import java.util.Comparator;

import com.tibco.tea.agent.be.BEProcessingUnit;

/**
 * This class is used to compare the processing unit by name
 * 
 * @author dijadhav
 *
 */
public final class BEProcessingUnitComparatorByName implements Comparator<BEProcessingUnit> {
	@Override
	public int compare(BEProcessingUnit o1, BEProcessingUnit o2) {
		if (null == o1 && null == o2) {
			return 0;
		} else if (null != o1 && null == o2) {
			return 1;
		} else if (null == o1 && null != o2) {
			return 1;
		} else {
			return o1.getName().compareTo(o2.getName());
		}
	}
}
