package com.tibco.cep.studio.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*
@author ssailapp
@date Feb 20, 2011
 */

public class IdUtil {

	public static String generateSequenceId(String prefix, ArrayList<String> curList) {
		String genStr = prefix;
        ArrayList<Integer> numbers = getNameIndexArray(curList);
        int enumIndex = 0;
        enumIndex = getIndexofElement(numbers);
		return genStr + "_" + enumIndex ;
	}
	
    private static ArrayList<Integer> getNameIndexArray(ArrayList<String> curList) {
    	ArrayList<Integer> numbers = new ArrayList<Integer>();
    	for (String str: curList) {
    		int index = str.lastIndexOf("_");
    		if (index != -1) {
    			String strLastCount = str.substring(index+1);
                Integer num;
                try {
                    num = new Integer(strLastCount);
                } catch (NumberFormatException nfe) {
                    num = new Integer(0);
                }
                numbers.add(num);
    		}
    	}
        return numbers;
    }

    private static int getIndexofElement(ArrayList<Integer> numbers) {
        Collections.sort(numbers);
        int index = 1;
        while (true) {
        	int loc = Collections.binarySearch(numbers, index);
        	if (loc >= 0)
        		index++;
        	else
        		break;
        }
        return index;
    }
    
    public static String generateUniqueName(String name) {
        name = (name == null) ? "" : name;
        Random random = new Random(System.currentTimeMillis());
        int id = Math.abs((name.hashCode() << 8) + random.nextInt());
        String requestId = Integer.toString(id, 16);
        return name+"_"+requestId.toUpperCase();
    }
	
}
