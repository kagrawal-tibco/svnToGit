package com.tibco.cep.dashboard.common.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

/**
 * Compare two tuple based on a list of fields.
 */
public class TupleComparator implements Comparator<Tuple> {

	private List<SortInfo> sortInfos;

	public TupleComparator() {
		sortInfos = new ArrayList<SortInfo>();
	}

	public void addSortSpec(String fieldName, boolean ascending) {
		sortInfos.add(new SortInfo(fieldName, ascending));
	}

	public boolean removeSortSpec(String fieldName) {
		ListIterator<SortInfo> iterator = sortInfos.listIterator();
		while (iterator.hasNext()) {
			SortInfo sortInfo = iterator.next();
			if (sortInfo.fieldName.equals(fieldName) == true) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	public String[] getFieldNames() {
		String[] fieldNames = new String[sortInfos.size()];
		int i = 0;
		for (SortInfo sortInfo : sortInfos) {
			fieldNames[i] = sortInfo.fieldName;
			i++;
		}
		return fieldNames;
	}

	public boolean isSortAscending(String fieldName) {
		SortInfo sortInfo = searchSortInfo(fieldName);
		if (sortInfo != null) {
			return sortInfo.ascending;
		}
		throw new IllegalArgumentException(fieldName);
	}

	public boolean updateSortSpec(String fieldName, boolean ascending) {
		SortInfo sortInfo = searchSortInfo(fieldName);
		if (sortInfo != null) {
			sortInfo.ascending = ascending;
			return true;
		}
		return false;
	}

	private SortInfo searchSortInfo(String fieldName) {
		ListIterator<SortInfo> iterator = sortInfos.listIterator();
		while (iterator.hasNext()) {
			SortInfo sortInfo = iterator.next();
			if (sortInfo.fieldName.equals(fieldName) == true) {
				return sortInfo;
			}
		}
		return null;
	}

	public int compare(Tuple tuple1, Tuple tuple2) {
		// No field to compare. Assume two tuples are the same.
		if (sortInfos.size() == 0) {
			return 0;
		}
		for (SortInfo sortInfo : sortInfos) {
			FieldValue value1 = tuple1.getFieldValueByName(sortInfo.fieldName);
			FieldValue value2 = tuple2.getFieldValueByName(sortInfo.fieldName);
			int cmp = value1.compareTo(value2);
			// Continue as long as the two fields from two tuples are the same.
			if (cmp != 0) {
				if (sortInfo.ascending == true) {
					return cmp;
				} else {
					return -cmp;
				}
			}
		}
		// All fields are the same.
		return 0;
	}

	class SortInfo {

		String fieldName;
		boolean ascending;

		SortInfo(String fieldName, boolean ascending) {
			this.fieldName = fieldName;
			this.ascending = ascending;
		}

	}
}
