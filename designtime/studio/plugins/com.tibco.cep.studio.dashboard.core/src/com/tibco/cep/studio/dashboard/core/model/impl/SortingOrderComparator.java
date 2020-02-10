package com.tibco.cep.studio.dashboard.core.model.impl;

import java.util.Comparator;
import java.util.logging.Logger;

/**
 * @
 *
 */
public class SortingOrderComparator implements Comparator<LocalElement>{
	
    public static final Logger LOGGER = Logger.getLogger(SortingOrderComparator.class.getName());
    
    /**
     *
     */
    public SortingOrderComparator() {
        super();
    }

	public int compare(LocalElement element1, LocalElement element2) {
		try {
	        return element1.getSortingOrder() - element2.getSortingOrder();
        } catch (Exception e) {
	        return 0;
        }
    }

}
