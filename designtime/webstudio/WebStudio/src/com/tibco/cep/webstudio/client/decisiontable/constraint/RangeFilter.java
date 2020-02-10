package com.tibco.cep.webstudio.client.decisiontable.constraint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Aug 3, 2008
 * Time: 1:14:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class RangeFilter implements Filter {

	TreeMap<Object, LinkedHashSet<Cell>> rangeMap = new TreeMap<Object, LinkedHashSet<Cell>>(); // should
																								// be
																								// per
																								// type
																								// for
																								// efficiency

	public RangeFilter() {

	}

	public void eval(Object o, HashSet<String> result) {
		if (rangeMap.size() == 0)
			return;
		if (o instanceof Long) {
			long value = (Long) o;

			SortedMap<Object, LinkedHashSet<Cell>> headMap = rangeMap
					.headMap(value + 1); //
			for (LinkedHashSet<Cell> cells : headMap.values()) {
				for (Cell c : cells) {
					long minValue = (Long) c.getExpression().getOperands()[0];
					long maxValue = (Long) c.getExpression().getOperands()[1];
					findInRange(result, value, c, minValue, maxValue);
				}
			}
		}
	}

	private void findInRange(HashSet<String> result, long value, Cell c,
			long minValue, long maxValue) {
		String ruleId = null;
		switch (c.getExpression().getRangeKind()) {
		case Operator.RANGE_BOUNDED: {
			if ((value >= minValue) && (value <= maxValue))
				ruleId = c.getRuleTupleInfo().getId();
			break;
		}
		case Operator.RANGE_MIN_EXCL_BOUNDED: {
			if ((value > minValue) && (value <= maxValue))
				ruleId = c.getRuleTupleInfo().getId();
			break;
		}
		case Operator.RANGE_MAX_EXCL_BOUNDED: {
			if ((value >= minValue) && (value < maxValue))
				ruleId = c.getRuleTupleInfo().getId();
			break;
		}
		case Operator.RANGE_MIN_MAX_EXCL_BOUNDED: {
			if ((value > minValue) && (value < maxValue))
				ruleId = c.getRuleTupleInfo().getId();
			break;
		}
		}
		if (null != ruleId) {
			result.add(ruleId);
		}
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.decision.table.constraintpane.Filter#updateCell(com.tibco
	 * .cep.decision.table.constraintpane.Cell,
	 * com.tibco.cep.decision.table.constraintpane.Cell)
	 */
	public void updateCell(List<Cell> oldCells, List<Cell> updatedCells) {

		// remove op1:oldCell && op2:oldCell, forall old cells
		for (Cell c : oldCells) {
			// skip equals cells
			if (c.getExpression().getOperatorKind() != Operator.RANGE_OPERATOR) {
				continue;
			}
			removeCell(c);
		}

		// add op1:newCell && op2:newCell, forall new cells
		for (Cell c : updatedCells) {
			// skip equals cells
			if (c.getExpression().getOperatorKind() != Operator.RANGE_OPERATOR) {
				continue;
			}
			addCell(c);
		}
	}

	@Override
	public Collection<Cell> getFilteredValues(Object key) {
		throw new UnsupportedOperationException(
				"This method not supported by Range Filter");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.decision.table.constraintpane.Filter#removeCell(com.tibco
	 * .cep.decision.table.constraintpane.Cell)
	 */
	public void removeCell(Cell removeCell) {

		if (removeCell.getExpression().getOperatorKind() != Operator.RANGE_OPERATOR) {
			return;
		}

		Object expr1 = removeCell.getExpression().getOperands()[0];
		if (expr1 instanceof Integer) {
			removeCell((Integer) expr1, removeCell);
		} else if (expr1 instanceof String) {
			String op = (String) expr1;
			if (op.length() > 0 && op.charAt(0) == '"') {
				op = op.substring(1, op.length() - 1);
			}
			try {
				int intValue = Integer.valueOf(op);
				removeCell(intValue, removeCell);
			} catch (NumberFormatException nfe) {
			}
		}
		Object expr2 = removeCell.getExpression().getOperands()[1];
		if (expr2 instanceof Integer) {
			removeCell((Integer) expr2, removeCell);
		} else if (expr2 instanceof String) {
			String op = (String) expr2;
			if (op.length() > 0 && op.charAt(0) == '"') {
				op = op.substring(1, op.length() - 1);
			}
			try {
				int intValue = Integer.valueOf(op);
				removeCell(intValue, removeCell);
			} catch (NumberFormatException nfe) {
			}
		}
	}

	public void addCell2(Cell c) {
		addCell((Integer) c.getExpression().getOperands()[0], c);
		addCell((Integer) c.getExpression().getOperands()[1], c);
	}

	public void addCell(Cell c) {

		if (c.getCellInfo().getType() == PROPERTY_TYPES.INTEGER_VALUE
				|| c.getCellInfo().getType() == PROPERTY_TYPES.LONG_VALUE) {

			// decisionTable.getColumns()
			if (c.getExpression().getOperatorKind()!= Operator.RANGE_OPERATOR) {
				return;
			}

			Object expr1 = c.getExpression().getOperands()[0];
			Object expr2 = c.getExpression().getOperands()[1];

			Long intOp1 = 0l;
			Long intOp2 = 0l;
			// verify expressions
			try {
				if (!(expr1 instanceof Long) && (expr1 instanceof String)) {
					String op1 = (String) expr1;
					if (op1.length() > 0) {
						if (op1.charAt(0) == '"') {
							op1 = op1.substring(1, op1.length() - 1);
						}
					}
					if (op1.contains("L")) {
						op1 = op1.replaceAll("L", "").trim();
					}
					if (op1.contains("l")) {
						op1 = op1.replaceAll("l,", "").trim();
					}
					intOp1 = Long.valueOf(op1);
				} else if (expr1 instanceof Long) {
					intOp1 = (Long) expr1;
				}

				if (!(expr2 instanceof Long) && (expr2 instanceof String)) {
					String op2 = (String) expr2;
					if (op2.length() > 0 && op2.charAt(0) == '"') {
						op2 = op2.substring(1, op2.length() - 1);
					}
					if (op2.contains("L")) {
						op2 = op2.replaceAll("L", "").trim();
					}
					if (op2.contains("l")) {
						op2 = op2.replaceAll("l,", "").trim();
					}
					intOp2 = Long.valueOf(op2);
				} else if (expr2 instanceof Long) {
					intOp2 = (Long) expr2;
				}

			} catch (NumberFormatException nfe) {
				return;
			}

			addCell(intOp1, c);
			addCell(intOp2, c);

		}
		if (c.getCellInfo().getType() == PROPERTY_TYPES.DATE_TIME_VALUE) {

			// decisionTable.getColumns()
			if (c.getExpression().getOperatorKind() != Operator.RANGE_OPERATOR) {
				return;
			}

			Object expr1 = c.getExpression().getOperands()[0];
			Object expr2 = c.getExpression().getOperands()[1];

			Date intOp1 = null;
			Date intOp2 = null;
			// verify expressions
			try {
				if (null != expr1 && expr1 instanceof Date) {
					intOp1 = (Date) expr1;
				}

				if (null != expr2 && expr2 instanceof Date) {
					intOp2 = (Date) expr2;
				}

			} catch (NumberFormatException nfe) {
				return;
			}
			if (null != intOp1) {
				addCell(intOp1, c);
			}
			if (null != intOp2) {
				addCell(intOp2, c);
			}

		}
	}

	private void addCell(Object expr, Cell c) {
		LinkedHashSet<Cell> cells = rangeMap.get(expr);
		if (null == cells) {
			cells = new LinkedHashSet<Cell>();
			rangeMap.put(expr, cells);
		}
		cells.add(c);
	}

	private void removeCell(int expr, Cell cellToRemove) {
		LinkedHashSet<Cell> cells = rangeMap.get(expr);
		if (null == cells) {
			rangeMap.remove(expr);
			return;
		}
		cells.remove(cellToRemove);
		if (cells.size() == 0) {
			// Only then remove the entire entry
			rangeMap.remove(expr);
		}
	}

	public int size() {
		return rangeMap.size();
	}

	public Object[] getMinMax(boolean excludeIntegerMinMax) {
		Object[] minmax = new Object[2];

		NavigableSet<Object> ranges = new TreeSet<Object>();
		Set<Object> keySet = rangeMap.keySet();

		for (Object key : keySet) {
			HashSet<Cell> cells = rangeMap.get(key);
			for (Cell cell : cells) {
				if (true == cell.isEnabled()) {
					ranges.add(key);
					continue;
				}
			}
		}

		if (ranges.size() == 0) {
			return new Object[] { Long.MAX_VALUE, Long.MIN_VALUE };
		}

		Object[] rangeArray = new Object[0];
		rangeArray = ranges.toArray(rangeArray);
		minmax[0] = rangeArray[0];
		minmax[1] = rangeArray[rangeArray.length - 1];
		if (minmax[0] instanceof Long) {
			if ((Long) minmax[0] == Long.MIN_VALUE)
				minmax[0] = rangeArray[1];
		}
		if (minmax[1] instanceof Long) {
			if ((Long) minmax[1] == Long.MAX_VALUE)
				minmax[1] = rangeArray[rangeArray.length - 2];
		}
		try {
			DateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss");
			Date minDate =format.parse("1601-01-01T00:00:00");
			Date maxDate = format.parse("9999-12-31T00:00:00");
			if (minmax[0] instanceof Date) {
				if (minDate.equals((Date)minmax[0]))
					minmax[0] = rangeArray[1];
			}
			if (minmax[1] instanceof Date) {
				if (maxDate.equals((Date)minmax[1]))
					minmax[1] = rangeArray[rangeArray.length - 2];
			}
		} catch (ParseException parseException) {
		}
		return minmax;
	}

	@Override
	public void updateCell(Cell oldCell, Cell c) {
		// TODO Auto-generated method stub

	}

	public void clearMap() {
		for (Object key : rangeMap.keySet()) {
			rangeMap.get(key).clear();
		}
		rangeMap.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.decision.table.constraintpane.Filter#getFilterPriority()
	 */
	@Override
	public int getFilterPriority() {
		return Integer.MAX_VALUE - 1;
	}
}
