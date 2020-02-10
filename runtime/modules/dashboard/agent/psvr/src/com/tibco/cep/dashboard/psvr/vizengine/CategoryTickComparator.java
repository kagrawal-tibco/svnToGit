package com.tibco.cep.dashboard.psvr.vizengine;

import java.util.Comparator;

public class CategoryTickComparator implements Comparator<CategoryTick> {

	private boolean useDisplayValue;

	private boolean ascending;

	public CategoryTickComparator(boolean ascending) {
		this(false, ascending);
	}

	public CategoryTickComparator(boolean useDisplayValue, boolean ascending) {
		this.useDisplayValue = useDisplayValue;
		this.ascending = ascending;
	}

	@Override
	public int compare(CategoryTick o1, CategoryTick o2) {
		int cmp = 0;
		if (useDisplayValue == true) {
			cmp = o1.getDisplayValue().compareTo(o2.getDisplayValue());
		}
		else {
			cmp = o1.getId().compareTo(o2.getId());
		}
		if (ascending == true) {
			return cmp;
		}
		return -cmp;
	}

}
