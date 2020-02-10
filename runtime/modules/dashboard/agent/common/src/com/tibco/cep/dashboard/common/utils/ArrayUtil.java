package com.tibco.cep.dashboard.common.utils;

public class ArrayUtil {

	public static boolean[] expandArray(boolean[] originalArray, int newSize) {
		if (originalArray == null)
			return new boolean[newSize];

		if (originalArray.length > newSize)
			return originalArray;

		boolean[] newArray = new boolean[newSize];
		System.arraycopy(originalArray, 0, newArray, 0, originalArray.length);
		return newArray;
	}

	public static byte[] expandArray(byte[] originalArray, int newSize) {
		if (originalArray == null)
			return new byte[newSize];

		if (originalArray.length > newSize)
			return originalArray;

		byte[] newArray = new byte[newSize];
		System.arraycopy(originalArray, 0, newArray, 0, originalArray.length);
		return newArray;
	}

	public static int[] expandArray(int[] originalArray, int newSize) {
		if (originalArray == null)
			return new int[newSize];

		if (originalArray.length > newSize)
			return originalArray;

		int[] newArray = new int[newSize];
		System.arraycopy(originalArray, 0, newArray, 0, originalArray.length);
		return newArray;
	}

	public static long[] expandArray(long[] originalArray, int newSize) {
		if (originalArray == null)
			return new long[newSize];

		if (originalArray.length > newSize)
			return originalArray;

		long[] newArray = new long[newSize];
		System.arraycopy(originalArray, 0, newArray, 0, originalArray.length);
		return newArray;
	}
	
	public static String[] expandArray(String[] originalArray, int newSize) {
		if (originalArray == null)
			return new String[newSize];

		if (originalArray.length > newSize)
			return originalArray;

		String[] newArray = new String[newSize];
		System.arraycopy(originalArray, 0, newArray, 0, originalArray.length);
		return newArray;
	}
	
	public static int search(Object[] array,Object object){
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(object) == true){
				return i;
			}
		}
		return -1;
	}
	
	public static boolean compare(Object[] array1,Object[] array2){
		if (array1 == array2){
			return true;
		}
		if (array1 == null || array2 == null){
			return false;
		}
		if (array1.length != array2.length){
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			boolean equal = ObjectUtil.equalWithNull(array1[i], array2[i]);
			if (equal == false){
				return false;
			}
		}
		return true;
	}

}
