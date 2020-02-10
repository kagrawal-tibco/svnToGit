package com.tibco.tea.agent.be.comparator;

import java.util.Comparator;

import com.tibco.cep.bemm.model.Application;
import com.tibco.tea.agent.be.BEApplication;

/**
 * Compare the application by creation time
 * 
 * @author dijadhav
 *
 */
public class BEApplicationComparatorByCreationTime implements Comparator<BEApplication> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(BEApplication o1, BEApplication o2) {
		if (o1 != null && o2 != null) {
			Application app = o1.getApplication();
			Application app1 = o2.getApplication();

			if (app != null && app1 != null) {
				if (app.getCreationTime() < app1.getCreationTime()) {
					return 1;
				} else if (app.getCreationTime() > app1.getCreationTime()) {
					return -1;
				} else {
					return 0;
				}
			}
		} else {
			return -1;
		}
		return 0;
	}

}
