package com.tibco.cep.decision.table.language;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

public class RowReorderer {
	protected HashMap<String, Integer> condIndexMap;
	protected HashMap<String, Integer> actIndexMap;
	protected RowCol retVal;
	
	/**
	 * Use the table's column model as orientation model.
	 * @param orientationModel
	 */
	public RowReorderer(Columns orientationModel) {
		condIndexMap = new HashMap<String, Integer>(2);
		actIndexMap = new HashMap<String, Integer>(2);

		int condIdx = 0;
		int actIdx = 0;
		if (orientationModel != null) {
			for (Column c : orientationModel.getColumn()) {
				if (c != null) {
					ColumnType ctype = c.getColumnType();
					if (ctype != null) {
						if (ctype == ColumnType.CONDITION) {
							condIndexMap.put(c.getId(), condIdx++);
						} else if (ctype.isAction()) {
							actIndexMap.put(c.getId(), actIdx++);
						}
					}
				}
			}
		}
		retVal = new RowCol(condIdx > actIdx ? condIdx : actIdx);
	}

	protected Collection<TableRuleVariable> reorderConditions(List<TableRuleVariable> row) {
		return reorder(row, condIndexMap);
	}

	protected Collection<TableRuleVariable> reorderActions(List<TableRuleVariable> row) {
		return reorder(row, actIndexMap);
	}

	protected Collection<TableRuleVariable> reorder(List<TableRuleVariable> row, Map<String, Integer> indexMap) {
		if (row == null) {
			retVal.reset(0);
		} else {
			retVal.reset(row.size());

			int tailIndex = indexMap.size();
			for (TableRuleVariable trv : row) {
				if (trv != null) {
					String columnId = trv.getColId();
					if (columnId != null) {
						Integer map = indexMap.get(columnId);
						if (map != null) {
							retVal.set(trv, map);
							continue;
						}
					}
					// if index can't be found in indexMap, put trv after all
					// slots reserved for what is in the indexMap
					retVal.set(trv, tailIndex++);
				}
			}
		}
		return retVal;
	}

	private static class RowCol extends AbstractCollection<TableRuleVariable>
			implements Iterator<TableRuleVariable> {
		private int size = 0;
		private int currIdx = 0;
		private TableRuleVariable[] arr;

		public RowCol(int initialSize) {
			arr = new TableRuleVariable[initialSize];
		}

		public void reset(int rowSize) {
			size = 0;
			if (rowSize > arr.length) {
				arr = new TableRuleVariable[rowSize];
			}
		}

		public void set(TableRuleVariable trv, int idx) {
			if (idx >= size)
				expand(idx);
			arr[idx] = trv;
		}

		private void expand(int idx) {
			if (idx >= arr.length) {
				TableRuleVariable[] old = arr;
				arr = new TableRuleVariable[idx + 1];
				for (int ii = 0; ii < old.length; ii++)
					arr[ii] = old[ii];
			}
			if (idx > size)
				for (int ii = size; ii < idx; ii++)
					arr[ii] = null;
			size = idx + 1;
		}

		@Override
		public Iterator<TableRuleVariable> iterator() {
			currIdx = 0;
			return this;
		}

		@Override
		public int size() {
			return size;
		}

		// iterator methods

		@Override
		public boolean hasNext() {
			return currIdx < size;
		}

		@Override
		public TableRuleVariable next() {
			return arr[currIdx++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Iterator.remove");
		}

	}
}