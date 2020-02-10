package com.tibco.cep.dashboard.psvr.util;

import java.util.Comparator;

/**
 * @author anpatil
 *
 */
public class StringComparator implements Comparator<String> {
    
    private boolean ascending;

    public StringComparator(boolean ascending){
        this.ascending = ascending;
    }

	public int compare(String o1, String o2) {
		int result = o1.compareTo(o2);
        if (result == 0){
            return result;
        }
        if (ascending == true){
            return result;
        }
        return -(result);
		
	}

}