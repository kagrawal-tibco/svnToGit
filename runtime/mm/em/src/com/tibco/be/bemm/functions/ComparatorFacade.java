/**
 * 
 */
package com.tibco.be.bemm.functions;

import java.util.Comparator;

import com.tibco.cep.runtime.model.event.SimpleEvent;

/**
 * @author vpatil
 */
public class ComparatorFacade implements Comparator<Object> {
	
	private String[][] sortSpec;
	private Comparator actualComparator;

	ComparatorFacade(String[] sortSpec) {
		this.sortSpec = new String[sortSpec.length][2];
		for (int i = 0; i < sortSpec.length; i++) {
			this.sortSpec[i] = sortSpec[i].split(",");
		}
	}
	
	@Override
	public int compare(Object o1, Object o2) {
		if (actualComparator == null){
            // handle null
            if (o1 == null && o2 == null){
                return 0;
            }
            else if (o1 == null){
                return -1;
            }
            else if (o2 == null) {
                return 1;
            }

			if (o1 instanceof SimpleEvent && o2 instanceof SimpleEvent){
				actualComparator = new SimpleEventComparator(sortSpec);
                return actualComparator.compare(o1, o2);
			} else if (o1 instanceof Object[] && o2 instanceof Object[]){
				actualComparator = new ObjectArrayComparator(sortSpec);
                return actualComparator.compare(o1, o2);
            }

            // Should not get here. No way to compare if o1 or o2 is not either SimpleEvent or Object[]
            return 0;
		} else {
            return actualComparator.compare(o1, o2);
        }
	}

}
