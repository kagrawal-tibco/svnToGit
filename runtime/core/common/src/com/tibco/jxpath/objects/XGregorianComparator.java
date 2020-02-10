package com.tibco.jxpath.objects;

import java.util.Comparator;

public class XGregorianComparator implements Comparator<XGregorian> {

	public static XGregorianComparator INSTANCE = new XGregorianComparator();
	
	@Override
	public int compare(XGregorian o1, XGregorian o2) {
		// compare years
		if (o1.getYear() > o2.getYear()) {
			return 1;
		} else if (o1.getYear() < o2.getYear()) {
			return -1;
		}
		
		if (o1.getMonth() > o2.getMonth()) {
			return 1;
		} else if (o1.getMonth() < o2.getMonth()) {
			return -1;
		}
		
		if (o1.getDayOfMonth() > o2.getDayOfMonth()) {
			return 1;
		} else if (o1.getDayOfMonth() < o2.getDayOfMonth()) {
			return -1;
		}
		
		if (o1.getHourOfDay() > o2.getHourOfDay()) {
			return 1;
		} else if (o1.getHourOfDay() < o2.getHourOfDay()) {
			return -1;
		}
		
		if (o1.getMinutes() > o2.getMinutes()) {
			return 1;
		} else if (o1.getMinutes() < o2.getMinutes()) {
			return -1;
		}
		
		// should return 0 for non-time gregorians
		return o1.getSeconds().compareTo(o2.getSeconds());
	}

}
