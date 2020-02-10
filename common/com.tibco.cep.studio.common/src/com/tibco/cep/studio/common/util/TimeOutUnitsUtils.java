package com.tibco.cep.studio.common.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;

//Added by Anand - 01/17/2011 to fix BE-10395
public class TimeOutUnitsUtils {
	
	private static List<TIMEOUT_UNITS> DEPRECATED_TIMEOUT_UNITS = Arrays.asList(
		TIMEOUT_UNITS.WEEK_DAYS,
		TIMEOUT_UNITS.WEEKS,
		TIMEOUT_UNITS.MONTHS,
		TIMEOUT_UNITS.YEARS
	);
	
	public static TIMEOUT_UNITS[] getValidTimeOutUnits(){
		List<TIMEOUT_UNITS> allowedList = new LinkedList<TIMEOUT_UNITS>(TIMEOUT_UNITS.VALUES);
		allowedList.removeAll(DEPRECATED_TIMEOUT_UNITS);
		return allowedList.toArray(new TIMEOUT_UNITS[allowedList.size()]);
	}
	
	public static boolean isValid(TIMEOUT_UNITS timeout_unit){
		if (DEPRECATED_TIMEOUT_UNITS.contains(timeout_unit) == true) {
			return false;
		}
		return true;
	}

}
