package com.tibco.cep.dashboard.common.utils;

public final class DateTimeUtils {

	public static final long MILLISECOND = 1;

	public static final long SECOND = 1000L;

	public static final long MINUTE = 60 * SECOND;

	public static final long HOUR = 60 * MINUTE;

	public static final long DAY = 24 * HOUR;

	// public static final long WEEK = 7 * DAY;

	public static final long MONTH = 30 * DAY;

	public static final long YEAR = 365 * DAY;

	public static final long[] TIME_PERIODS = new long[] { YEAR, MONTH, /* WEEK, */DAY, HOUR, MINUTE, SECOND, MILLISECOND };

	public static final String[] TIME_PERIOD_STRS = new String[] { "year", "month", /* "week", */"day", "hour", "minute", "second", "msec" };

	public static String getTimeAsStr(long time) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i < TIME_PERIODS.length && time > 0) {
			int quotient = Math.round(time / TIME_PERIODS[i]);
			if (quotient > 0) {
				sb.append(" ");
				sb.append(quotient);
				sb.append(" ");
				sb.append(TIME_PERIOD_STRS[i]);
				if (quotient > 1) {
					sb.append("s");
				}
				time = time - quotient * TIME_PERIODS[i];
			}
			i++;
		}
		return sb.toString().trim();
	}

	public static int computeScaleUnit(long time) {
		for (int i = 0; i < TIME_PERIODS.length; i++) {
			long scaleFactor = time / TIME_PERIODS[i];
			if (scaleFactor > 1) {
				return i;
			}
		}
		return TIME_PERIODS.length - 1;
	}

	public static String getUnitName(int unitIndex) {
		if (unitIndex < 0 || unitIndex > TIME_PERIOD_STRS.length - 1) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return TIME_PERIOD_STRS[unitIndex];
	}
	
	public static long getUnitValue(int unitIndex) {
		if (unitIndex < 0 || unitIndex > TIME_PERIOD_STRS.length - 1) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return TIME_PERIODS[unitIndex];
	}
	
	public static void main(String[] args) {
		System.out.println(getTimeAsStr((long) (1 * YEAR)) + " - " + getUnitName(computeScaleUnit((long) (1 * YEAR))));
		System.out.println(getTimeAsStr(5 * MONTH) + " - " + getUnitName(computeScaleUnit(5 * MONTH)));
		System.out.println(getTimeAsStr(DAY) + " - " + getUnitName(computeScaleUnit(DAY)));
		System.out.println(getTimeAsStr(24 * HOUR) + " - " + getUnitName(computeScaleUnit(24 * HOUR)));
		System.out.println(getTimeAsStr(MINUTE) + " - " + getUnitName(computeScaleUnit(MINUTE)));
		System.out.println(getTimeAsStr(SECOND) + " - " + getUnitName(computeScaleUnit(SECOND)));
	}

}
