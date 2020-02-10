package com.tibco.store.persistence.model.invm.impl;

import java.sql.Timestamp;

public class CompareFactory {

	// TODO add more data types

	/*
	 * compare Value > value2
	 * 
	 * @return boolean
	 */
	public static boolean compareGT(Object value1, Object value2) {
		boolean flag = false;

		if (value1 instanceof Comparable<?> && value2 instanceof Comparable<?>) {
			if (value1.getClass().isInstance(value2)) {

				if (value1 instanceof String) {
					String str1 = (String) value1;
					String str2 = (String) value2;
					if (str1.compareTo(str2) > 0) {
						flag = true;
					}
				} else if (value1 instanceof Long) {
					Long l1 = (Long) value1;
					Long l2 = (Long) value2;
					if (l1 > l2) {
						flag = true;
					}

				} else if (value1 instanceof Double) {
					Double l1 = (Double) value1;
					Double l2 = (Double) value2;
					if (l1 > l2) {
						flag = true;
					}

				} else if (value1 instanceof Integer) {
					Integer l1 = (Integer) value1;
					Integer l2 = (Integer) value2;
					if (l1 > l2) {
						flag = true;
					}
				} else if (value1 instanceof Float) {
					Float l1 = (Float) value1;
					Float l2 = (Float) value2;
					if (l1 > l2) {
						flag = true;
					}
				} else if (value1 instanceof Timestamp) {
					Timestamp t1 = (Timestamp) value1;
					Timestamp t2 = (Timestamp) value2;
					if (t1.compareTo(t2) > 0) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	/*
	 * compare Value < value2
	 * 
	 * @return boolean
	 */
	public static boolean compareLT(Object value1, Object value2) {
		boolean flag = false;

		if (value1 instanceof Comparable<?> && value2 instanceof Comparable<?>) {
			if (value1.getClass().isInstance(value2)) {

				if (value1 instanceof String) {
					String str1 = (String) value1;
					String str2 = (String) value2;
					if (str1.compareTo(str2) > 0) {
						flag = true;
					}
				} else if (value1 instanceof Long) {
					Long l1 = (Long) value1;
					Long l2 = (Long) value2;
					if (l1 < l2) {
						flag = true;
					}

				} else if (value1 instanceof Double) {
					Double l1 = (Double) value1;
					Double l2 = (Double) value2;
					if (l1 < l2) {
						flag = true;
					}

				} else if (value1 instanceof Integer) {
					Integer l1 = (Integer) value1;
					Integer l2 = (Integer) value2;
					if (l1 < l2) {
						flag = true;
					}
				} else if (value1 instanceof Float) {
					Float l1 = (Float) value1;
					Float l2 = (Float) value2;
					if (l1 < l2) {
						flag = true;
					}
				} else if (value1 instanceof Timestamp) {
					Timestamp t1 = (Timestamp) value1;
					Timestamp t2 = (Timestamp) value2;
					if (t1.compareTo(t2) < 0) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	/*
	 * compare Value >= value2
	 * 
	 * @return boolean
	 */
	public static boolean compareGE(Object value1, Object value2) {
		boolean flag = false;

		if (value1 instanceof Comparable<?> && value2 instanceof Comparable<?>) {
			if (value1.getClass().isInstance(value2)) {

				if (value1 instanceof String) {
					String str1 = (String) value1;
					String str2 = (String) value2;
					if (str1.compareTo(str2) >= 0) {
						flag = true;
					}
				} else if (value1 instanceof Long) {
					Long l1 = (Long) value1;
					Long l2 = (Long) value2;
					if (l1 >= l2) {
						flag = true;
					}

				} else if (value1 instanceof Double) {
					Double l1 = (Double) value1;
					Double l2 = (Double) value2;
					if (l1 >= l2) {
						flag = true;
					}

				} else if (value1 instanceof Integer) {
					Integer l1 = (Integer) value1;
					Integer l2 = (Integer) value2;
					if (l1 >= l2) {
						flag = true;
					}
				} else if (value1 instanceof Float) {
					Float l1 = (Float) value1;
					Float l2 = (Float) value2;
					if (l1 >= l2) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	/*
	 * compare Value < value2
	 * 
	 * @return boolean
	 */
	public static boolean compareLE(Object value1, Object value2) {
		boolean flag = false;

		if (value1 instanceof Comparable<?> && value2 instanceof Comparable<?>) {
			if (value1.getClass().isInstance(value2)) {

				if (value1 instanceof String) {
					String str1 = (String) value1;
					String str2 = (String) value2;
					if (str1.compareTo(str2) <= 0) {
						flag = true;
					}
				} else if (value1 instanceof Long) {
					Long l1 = (Long) value1;
					Long l2 = (Long) value2;
					if (l1 <= l2) {
						flag = true;
					}

				} else if (value1 instanceof Double) {
					Double l1 = (Double) value1;
					Double l2 = (Double) value2;
					if (l1 <= l2) {
						flag = true;
					}

				} else if (value1 instanceof Integer) {
					Integer l1 = (Integer) value1;
					Integer l2 = (Integer) value2;
					if (l1 <= l2) {
						flag = true;
					}
				} else if (value1 instanceof Float) {
					Float l1 = (Float) value1;
					Float l2 = (Float) value2;
					if (l1 <= l2) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	/*
	 * compare Value > value2
	 * 
	 * @return boolean
	 */
	public static boolean compareEQ(Object value1, Object value2) {
		boolean flag = false;

		if (value1 instanceof Comparable<?> && value2 instanceof Comparable<?>) {
			if (value1.getClass().isInstance(value2)) {

				if (value1 instanceof String) {
					String str1 = (String) value1;
					String str2 = (String) value2;
					if (str1.compareTo(str2) == 0) {
						flag = true;
					}
				} else if (value1 instanceof Long) {
					Long l1 = (Long) value1;
					Long l2 = (Long) value2;
					if (l1.equals(l2)) {
						flag = true;
					}

				} else if (value1 instanceof Double) {
					Double d1 = (Double) value1;
					Double d2 = (Double) value2;
					if (d1.equals(d2)) {
						flag = true;
					}

				} else if (value1 instanceof Integer) {
					Integer i1 = (Integer) value1;
					Integer i2 = (Integer) value2;
					if (i1.equals(i2)) {
						flag = true;
					}
				} else if (value1 instanceof Float) {
					Float f1 = (Float) value1;
					Float f2 = (Float) value2;
					if (f1.equals(f2)) {
						flag = true;
					}
				}
				else if (value1 instanceof Boolean) {
					Boolean f1 = (Boolean) value1;
					Boolean f2 = (Boolean) value2;
					if (f1.equals(f2)) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}

}
