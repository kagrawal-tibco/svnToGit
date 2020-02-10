package com.tibco.be.bemm.functions;

import java.util.Comparator;

import com.tibco.cep.runtime.model.event.SimpleEvent;

class SimpleEventComparator implements Comparator<SimpleEvent> {

	private String[] fieldNames;
	
	private boolean[] fieldOrder;

	SimpleEventComparator(String[][] sortSpec) {
		fieldNames = new String[sortSpec.length];
		fieldOrder = new boolean[sortSpec.length];		
		for (int i = 0; i < sortSpec.length; i++) {
			fieldNames[i] = sortSpec[i][0];
			fieldOrder[i] = Boolean.parseBoolean(sortSpec[i][1]);
		}
	}

	public int compare(SimpleEvent o1, SimpleEvent o2) {
		if (o1 != null && o2 != null){
			int i = 0;
			for (int j = 0; j < fieldNames.length; j++) {
				String fieldName = fieldNames[j];
				try {
					Comparable value1 = (Comparable) o1.getProperty(fieldName);
					Comparable value2 = (Comparable) o2.getProperty(fieldName);
					int comparision = 0;
					if (value1 != null && value2 != null){
						comparision = value1.compareTo(value2);
					}
					else if (value1 == null){
						comparision = -1;
					}
					else {
						comparision = 1;
					}
					if (fieldOrder[j] == false){
						comparision = -comparision;
					}
					i = i + comparision;
				} catch (NoSuchFieldException e) {
					throw new RuntimeException("could not find "+fieldName,e);
				}
			}
			return i;
		}
		else if (o1 == null){
			return -1;
		}
		else {
			return 1;
		}		
	}

}
