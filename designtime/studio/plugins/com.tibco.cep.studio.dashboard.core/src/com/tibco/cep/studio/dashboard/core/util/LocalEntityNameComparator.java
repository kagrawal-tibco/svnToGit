package com.tibco.cep.studio.dashboard.core.util;

import java.text.Collator;
import java.util.Comparator;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

/**
 * @
 *  
 */
public class LocalEntityNameComparator implements Comparator<LocalElement> {

	public int compare(LocalElement entity1, LocalElement entity2) {
		return Collator.getInstance().compare(entity1.getName(),entity2.getName());
    }

}
