package com.tibco.cep.runtime.service.tester.beunit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TestUtils {
	private static SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public static String formatDateTime(Calendar cal) {
		return dtf.format(cal.getTime());
	}

	public static Date parseDateTime(String dt) throws ParseException {
		return dtf.parse(dt);
	}

	public static boolean compareString(String nm, String av, String ev, ArrayList<String> expectedDesc,
			ArrayList<String> mismatchDesc) {
		if ((av == null) && (ev == null)) {
			return true;
		}
		if ((av == null) && (ev != null)) {
			expectedDesc.add("property " + nm + "=" + ev.toString());
			mismatchDesc.add("property " + nm + " is null");
			return false;
		}
		if ((ev == null) && (av != null)) {
			expectedDesc.add("property " + nm + " = null");
			mismatchDesc.add("property " + nm + " = " + av.toString());
			return false;
		}
		if (av.compareTo(ev) == 0) {
			return true;
		}
		expectedDesc.add("property " + nm + "=" + ev);
		mismatchDesc.add("property " + nm + "=" + av);
		return false;
	}
}
