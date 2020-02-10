/**
 * 
 */
package com.tibco.cep.studio.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author aathalye
 *
 */
public class HelperUtil {
	
	private static int getLocationOfSubArray(List<Byte> source, byte[] target) {
		ArrayList<Byte> l2 = getBytesList(target);
		int index = Collections.indexOfSubList(source, l2);
		return index;
	}
	
	/**
	 * Replace contents of the source array matching the first occurrence of the
	 * old array with the target array.
	 * @param source: The main array
	 * @param old: The array to find and replace
	 * @param target: The replacement array
	 * @return the replaced array
	 */
	public static byte[] replaceBytesInList(byte[] source, byte[] old, byte[] target) {
		ArrayList<Byte> l1 = getBytesList(source);
		int index = getLocationOfSubArray(l1, old);
		
		byte[] part1 = new byte[index];
		System.arraycopy(source, 0, part1, 0, part1.length);
		
		byte[] part3 = new byte[source.length - (index + old.length)];
		System.arraycopy(source, index + old.length, part3, 0, part3.length);
		
		byte[] comb1 = new byte[part1.length + target.length];
		System.arraycopy(part1, 0, comb1, 0, part1.length);
		System.arraycopy(target, 0, comb1, part1.length, target.length);
		
		byte[] end = new byte[comb1.length + part3.length];
		System.arraycopy(comb1, 0, end, 0, comb1.length);
		System.arraycopy(part3, 0, end, comb1.length, part3.length);
		part1 = part3 = comb1 = null;
		return end;
	}
	
	private static ArrayList<Byte> getBytesList(byte[] bytes) {
		ArrayList<Byte> l = new ArrayList<Byte>(bytes.length);
		for (byte b : bytes) {
			l.add(b);
		}
		return l;
	}
}
