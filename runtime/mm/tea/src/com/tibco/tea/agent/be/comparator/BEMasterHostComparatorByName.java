package com.tibco.tea.agent.be.comparator;

import java.util.Comparator;

import com.tibco.tea.agent.be.BEMasterHost;

/**
 * This class is used to compare the master host by name
 * 
 * @author dijadhav
 *
 */
public final class BEMasterHostComparatorByName implements Comparator<BEMasterHost> {
	@Override
	public int compare(BEMasterHost o1, BEMasterHost o2) {
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
