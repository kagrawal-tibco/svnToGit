package com.tibco.rta.service.persistence.memory.adapter.impl;

import java.util.ArrayList;
import java.util.List;

import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.filter.AndFilter;
import com.tibco.rta.query.filter.EqFilter;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.filter.GEFilter;
import com.tibco.rta.query.filter.GtFilter;
import com.tibco.rta.query.filter.LEFilter;
import com.tibco.rta.query.filter.LogicalFilter;
import com.tibco.rta.query.filter.LtFilter;
import com.tibco.rta.query.filter.NEqFilter;
import com.tibco.rta.query.filter.NotFilter;
import com.tibco.rta.query.filter.OrFilter;
import com.tibco.rta.query.filter.RelationalFilter;
import com.tibco.rta.service.persistence.memory.InMemoryConstant;
import com.tibco.store.query.model.BinaryOperator;
import com.tibco.store.query.model.Predicate;
import com.tibco.store.query.model.impl.AndPredicate;
import com.tibco.store.query.model.impl.OrPredicate;
import com.tibco.store.query.model.impl.PredicateFactory;
import com.tibco.store.query.model.impl.SimpleQueryExpression;
import com.tibco.store.query.model.impl.ValueExpression;

public class FilterTransformer {

	public static Predicate transform(Filter filter, String tableName) {
		if (filter instanceof AndFilter) {
			AndPredicate parentPredicate;
			Predicate childPredicate;
			Filter[] childFilters = ((LogicalFilter) filter).getFilters();
			List<Predicate> pList = new ArrayList<Predicate>();
			for (Filter childFilter : childFilters) {
				if ((childPredicate = transform(childFilter, tableName)) != null) {
					pList.add(childPredicate);
				}
			}
			parentPredicate = createAndPredicate(pList);
			return parentPredicate;

		} else if (filter instanceof OrFilter) {
			OrPredicate parentPredicate;
			Predicate childPredicate;
			List<Predicate> pList = new ArrayList<Predicate>();
			Filter[] childFilters = ((LogicalFilter) filter).getFilters();
			for (Filter childFilter : childFilters) {
				if ((childPredicate = transform(childFilter, tableName)) != null) {
					pList.add(childPredicate);
				}
			}
			parentPredicate = createOrPredicate(pList);
			return parentPredicate;
		} else if (filter instanceof NotFilter) {
			Filter baseFilter = ((NotFilter) filter).getBaseFilter();

			if (baseFilter instanceof RelationalFilter) {
				return evaluateNotRelationalFilter((RelationalFilter) baseFilter, tableName);
			}

			return transform(filter, tableName);
		}
		return evaluateRelationalFilter(filter, tableName);
	}

	private static Predicate evaluateNotRelationalFilter(RelationalFilter filter, String tableName) {
		RelationalFilter rFilter = filter;
		Object filterValue = rFilter.getValue();
		String columnName = null;

		if (rFilter.getKeyQualifier() != null) {
			columnName = rFilter.getKey();
		} else if (rFilter.getMetricQualifier() != null) {
			columnName = InMemoryConstant.DIMENSION_LEVEL_FIELD;
		}

		return PredicateFactory.createBinaryPredicate(new SimpleQueryExpression(tableName, columnName),
				new ValueExpression<Object>(filterValue), BinaryOperator.NOTEQ);

	}

	private static Predicate evaluateRelationalFilter(Filter filter, String tableName) {
		if (filter instanceof RelationalFilter) {
			RelationalFilter rFilter = (RelationalFilter) filter;
			Object filterValue = rFilter.getValue();
			String columnName = null;

			if (rFilter.getKeyQualifier() != null) {
				columnName = rFilter.getKey();
			} else if (rFilter.getMetricQualifier() != null) {
				if(rFilter.getMetricQualifier().name().equalsIgnoreCase(MetricQualifier.DIMENSION_LEVEL.name())){
					columnName = InMemoryConstant.DIMENSION_LEVEL_FIELD;
				}else if(rFilter.getMetricQualifier().name().equalsIgnoreCase(MetricQualifier.UPDATED_TIME.name())){
					columnName = InMemoryConstant.UPDATED_DATE_TIME_FIELD;
				}else if(rFilter.getMetricQualifier().name().equalsIgnoreCase(MetricQualifier.CREATED_TIME.name())){
					columnName = InMemoryConstant.CREATED_DATE_TIME_FIELD;
				}else if(rFilter.getMetricQualifier().name().equalsIgnoreCase(MetricQualifier.DIMENSION_LEVEL_NO.name())){
					columnName = InMemoryConstant.DIMENSION_LEVEL_NO;
				}else if(rFilter.getMetricQualifier().name().equalsIgnoreCase(MetricQualifier.IS_PROCESSED.name())){
					columnName = InMemoryConstant.IS_PROCESSED;
				}
			}

			return relationalPredicate(filter, tableName, columnName, filterValue);
		}
		return null;
	}

	private static String getColumnName(Filter filter) {
		if (filter instanceof RelationalFilter) {
			RelationalFilter rFilter = (RelationalFilter) filter;
			String columnName = null;

			if (rFilter.getKeyQualifier() != null) {
				columnName = rFilter.getKey();
			} else if (rFilter.getMetricQualifier() != null) {
				if(rFilter.getMetricQualifier().name().equalsIgnoreCase(MetricQualifier.DIMENSION_LEVEL.name())){
					columnName = InMemoryConstant.DIMENSION_LEVEL_FIELD;
				}else if(rFilter.getMetricQualifier().name().equalsIgnoreCase(MetricQualifier.UPDATED_TIME.name())){
					columnName = InMemoryConstant.UPDATED_DATE_TIME_FIELD;
				}else if(rFilter.getMetricQualifier().name().equalsIgnoreCase(MetricQualifier.CREATED_TIME.name())){
					columnName = InMemoryConstant.CREATED_DATE_TIME_FIELD;
				}else if(rFilter.getMetricQualifier().name().equalsIgnoreCase(MetricQualifier.DIMENSION_LEVEL_NO.name())){
					columnName = InMemoryConstant.DIMENSION_LEVEL_NO;
				}else if(rFilter.getMetricQualifier().name().equalsIgnoreCase(MetricQualifier.IS_PROCESSED.name())){
					columnName = InMemoryConstant.IS_PROCESSED;
				}
				
			}
			return columnName;
		}

		return null;
	}

	private static OrPredicate createOrPredicate(List<Predicate> pList) {
		OrPredicate previous;
		if (pList.size() >= 2) {
			previous = PredicateFactory.createOrPredicate(pList.get(0), pList.get(1));

			for (int i = 2; i < pList.size(); i++) {
				previous = PredicateFactory.createOrPredicate(pList.get(i), previous);
			}

			return previous;
		}else if(pList.size() == 1){
			return PredicateFactory.createOrPredicate(pList.get(0), pList.get(0));
		}
		return null;
	}

	private static AndPredicate createAndPredicate(List<Predicate> pList) {
		AndPredicate previous;
		if (pList.size() >= 2) {
			previous = PredicateFactory.createAndPredicate(pList.get(0), pList.get(1));

			for (int i = 2; i < pList.size(); i++) {
				previous = PredicateFactory.createAndPredicate(pList.get(i), previous);
			}

			return previous;
		}else if(pList.size() == 1){
			return PredicateFactory.createAndPredicate(pList.get(0), pList.get(0));
		}
		return null;
	}

	private static Predicate relationalPredicate(Filter filter, String tableName, String columnName, Object filterValue) {

		if (filter instanceof EqFilter) {
			return PredicateFactory.createBinaryPredicate(new SimpleQueryExpression(tableName, columnName),
					new ValueExpression<Object>(filterValue), BinaryOperator.EQ);
		} else if (filter instanceof NEqFilter) {
			return PredicateFactory.createBinaryPredicate(new SimpleQueryExpression(tableName, columnName),
					new ValueExpression<Object>(filterValue), BinaryOperator.NEQ);
		} else if (filter instanceof GtFilter) {
			return PredicateFactory.createBinaryPredicate(new SimpleQueryExpression(tableName, columnName),
					new ValueExpression<Object>(filterValue), BinaryOperator.GT);
		} else if (filter instanceof LtFilter) {
			return PredicateFactory.createBinaryPredicate(new SimpleQueryExpression(tableName, columnName),
					new ValueExpression<Object>(filterValue), BinaryOperator.LT);
		} else if (filter instanceof GEFilter) {
			return PredicateFactory.createBinaryPredicate(new SimpleQueryExpression(tableName, columnName),
					new ValueExpression<Object>(filterValue), BinaryOperator.GE);
		} else if (filter instanceof LEFilter) {
			return PredicateFactory.createBinaryPredicate(new SimpleQueryExpression(tableName, columnName),
					new ValueExpression<Object>(filterValue), BinaryOperator.LE);
		} else if (filter instanceof NotFilter) {
			return PredicateFactory.createBinaryPredicate(new SimpleQueryExpression(tableName, columnName),
					new ValueExpression<Object>(filterValue), BinaryOperator.NOTEQ);
		}
		return null;
	}

}
