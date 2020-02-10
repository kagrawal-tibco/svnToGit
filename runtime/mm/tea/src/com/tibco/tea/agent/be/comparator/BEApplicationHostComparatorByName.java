package com.tibco.tea.agent.be.comparator;

import java.util.Comparator;

import com.tibco.tea.agent.be.BEApplicationHost;

/**
 * This class is used to compare the application host by name
 * 
 * @author dijadhav
 *
 */
public final class BEApplicationHostComparatorByName implements Comparator<BEApplicationHost> {
	@Override
	public int compare(BEApplicationHost o1, BEApplicationHost o2) {
		if (null == o1 && null == o2) {
			return 0;
		} else if (null != o1 && null == o2) {
			return 1;
		} else if (null == o1 && null != o2) {
			return 1;
		} else {
			if (null == o1.getName() && null == o2.getName())
				return 0;
			if (null != o1.getName() && null == o2.getName())
				return -1;
			if (null == o1.getName() && null != o2.getName())
				return 1;
			return o1.getName().compareTo(o2.getName());
		}
	}
}
