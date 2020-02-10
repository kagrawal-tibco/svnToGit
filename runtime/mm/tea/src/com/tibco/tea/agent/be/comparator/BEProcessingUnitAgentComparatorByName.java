package com.tibco.tea.agent.be.comparator;

import java.util.Comparator;

import com.tibco.tea.agent.be.BEProcessingUnitAgent;

/**
 * This class is used to compare the agents configured in cdd by name
 * 
 * @author dijadhav
 *
 */
public final class BEProcessingUnitAgentComparatorByName implements Comparator<BEProcessingUnitAgent> {
	@Override
	public int compare(BEProcessingUnitAgent o1, BEProcessingUnitAgent o2) {
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
