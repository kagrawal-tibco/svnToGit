package com.tibco.tea.agent.be.comparator;

import java.util.Comparator;

import com.tibco.tea.agent.be.BEAgent;

/**
 * This class is used to compare the running agents by name
 * 
 * @author dijadhav
 *
 */
public final class BEAgentComparatorByName implements Comparator<BEAgent> {
	@Override
	public int compare(BEAgent o1, BEAgent o2) {
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
