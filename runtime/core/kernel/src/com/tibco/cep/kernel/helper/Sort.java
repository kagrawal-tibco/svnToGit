package com.tibco.cep.kernel.helper;

import java.util.Arrays;

public class Sort {
	public static <T> T[] sort(T[] arr) {
		Arrays.sort(arr);
		return arr;
	}
}
