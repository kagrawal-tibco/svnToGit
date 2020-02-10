package com.tibco.tea.agent.be.comparator;

import java.util.Comparator;

import com.tibco.tea.agent.be.BEServiceInstance;

/**
 * This class is used to compare the service instance by name
 * 
 * @author dijadhav
 *
 */
public final class BEServiceInstanceComparatorByName implements Comparator<BEServiceInstance> {
	@Override
	public int compare(BEServiceInstance o1, BEServiceInstance o2) {
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
