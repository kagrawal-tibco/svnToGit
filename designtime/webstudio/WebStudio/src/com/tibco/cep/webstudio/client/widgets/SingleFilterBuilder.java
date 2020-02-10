package com.tibco.cep.webstudio.client.widgets;

import java.util.List;

import com.smartgwt.client.types.FieldType;
import com.tibco.cep.webstudio.client.FilterOperatorUtils;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.FilterValue;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedListener;
import com.tibco.cep.webstudio.model.rule.instance.RelatedFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.SingleFilter;
import com.tibco.cep.webstudio.model.rule.instance.impl.EmptyFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.RangeFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.RelatedFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.SimpleFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.operators.FilterOperator;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

/**
 * Widget for representing a single Filter builder
 * 
 * @author Ryan
 * @author Vikram Patil
 */
public class SingleFilterBuilder extends AbstractSingleFilterBuilder {

	public SingleFilterBuilder(Filter filter,
								AbstractBuilderSubClauseLayout filterBuilderSubClause,
								AbstractEditor editor) {
		this(filter, filterBuilderSubClause, editor, false, false);
	}

	public SingleFilterBuilder(Filter filter,
								AbstractBuilderSubClauseLayout filterBuilderSubClause,
								AbstractEditor editor,
								boolean emptyClause,
								boolean allowNestedClause) {
		super(filter, filterBuilderSubClause, editor, emptyClause, allowNestedClause, null);
	}

	protected FilterOperator getFilterOperator() {

		FilterOperator[] values = FilterOperator.values();
		String op = ((SingleFilter) this.filter).getOperator();
		for (FilterOperator fOp : values) {
			if (fOp.getValue().equals(op)) {
				return fOp;
			}
		}
		return null;
	}

	@Override
	public void updateValueForm() {
		if (this.valueForm != null) {
			this.removeMember(this.valueForm);
			this.valueForm = null;
		}
		FilterValue filterValue = null;

		FilterOperator filterOperator = this.getFilterOperator();
		if (filterOperator == null) {
			return;
		}
		switch (filterOperator) {
		case BETWEEN:
		case BETWEEN_INCLUSIVE: {
			filterValue = new RangeFilterValueImpl();
			break;
		}

		case GREATER_THAN:
		case LESS_THAN:
		case GREATER_THAN_OR_EQUAL_TO:
		case LESS_THAN_OR_EQUAL_TO:
		case EQUALS:
		case EQUALS_IGNORE_CASE:
		case NOT_EQUALS:
		case NOT_EQUALS_IGNORE_CASE:
		case CONTAINS:
		case DOES_NOT_CONTAIN:
		case STARTS_WITH:
		case ENDS_WITH: {
			filterValue = new SimpleFilterValueImpl();
			break;
		}

		case DIFFERS_FROM_FIELD:
		case GREATER_THAN_FIELD:
		case GREATER_THAN_OR_EQUAL_TO_FIELD:
		case LESS_THAN_FIELD:
		case LESS_THAN_OR_EQUAL_TO_FIELD:
		case MATCHES_OTHER_FIELD: {
			final RelatedFilterValue relatedVal = new RelatedFilterValueImpl();
			relatedVal.addChangeListener(this.editor.getChangeHandler());
			relatedVal.addChangeListener(new IInstanceChangedListener() {

				@Override
				public void instanceChanged(IInstanceChangedEvent changeEvent) {
					SingleFilterBuilder.this.processRelatedFilterValueChangedEvent(relatedVal, changeEvent);
				}
			});
			filterValue = relatedVal;
			break;
		}

		case IS_NOT_NULL:
		case IS_NULL:
		case EQUALS_TRUE:
		case EQUALS_FALSE: {
			filterValue = new EmptyFilterValueImpl();
			break;
		}

		default:
			break;
		}

		if (filterValue != null) {
			filterValue.addChangeListener(this.editor.getChangeHandler());
			((SingleFilter) this.filter).setFilterValue(filterValue);
		}

	}

	@Override
	protected List<IBuilderOperator> getOperatorsForType(FieldType type) {
		return FilterOperatorUtils.getOperators(type);
	}

	@Override
	protected AbstractOperatorLink createInitialOperatorLink(DataSourcesLink initOpLink) {
		return new OperatorLink(this, this.editor);
	}

	@Override
	protected AbstractRelatedLink createNewRelatedLink(RelatedLink modelLink, AbstractRelatedLink prevLink) {
		return new FilterRelatedLink(modelLink, SingleFilterBuilder.this, prevLink, this.editor);
	}
}
