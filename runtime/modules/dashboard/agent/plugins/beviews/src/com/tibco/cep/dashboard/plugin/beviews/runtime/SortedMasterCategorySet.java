package com.tibco.cep.dashboard.plugin.beviews.runtime;

import java.util.Collection;
import java.util.Collections;

import com.tibco.cep.dashboard.psvr.vizengine.CategoryTick;
import com.tibco.cep.dashboard.psvr.vizengine.CategoryTickComparator;

public class SortedMasterCategorySet extends MasterCategorySet {

	private CategoryTickComparator categoryTickComparator;

	public SortedMasterCategorySet(String identifier, boolean ascending) {
		super(identifier);
		categoryTickComparator = new CategoryTickComparator(ascending);
	}

	@Override
	public void setTicks(Collection<CategoryTick> ticks) {
		super.setTicks(ticks);
		Collections.sort(residentTicks, categoryTickComparator);
	}

//	@Override
//	public void merge(Logger logger, List<CategoryTick> ticks){
//		residentTicks.addAll(ticks);
//		Collections.sort(residentTicks, categoryTickComparator);
//	}

}