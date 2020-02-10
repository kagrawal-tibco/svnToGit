package com.tibco.cep.webstudio.client.widgets;

import com.smartgwt.client.data.DataSource;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.model.rule.instance.BuilderSubClause;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.impl.MultiFilterImpl;
import com.tibco.cep.webstudio.model.rule.instance.operators.FilterOperator;
import com.tibco.cep.webstudio.client.WebStudio;

/**
 * Widget for representing Filter Builder Sub Clause
 * 
 * @author Vikram Patil
 */
public class FilterBuilderSubClause extends AbstractBuilderSubClauseLayout implements IRuleTemplateFilter {

	public FilterBuilderSubClause(BuilderSubClause model,
									DataSource templateDataSource,
									final DataSource dataSource,
									AbstractEditor editor) {
		super(model, templateDataSource, dataSource, true, editor, null);
	}

	public void addNewNestedClause() {
		//MultiFilterImpl subClause = new MultiFilterImpl();
		MultiFilterImpl subClause = new MultiFilterImpl(WebStudio.get().getUserPreference().getRtiDefaultFilterType());
		this.model.addFilter(subClause);
	}

	@Override
	protected String getInitialOperator() {
		return FilterOperator.EQUALS.getValue();
	}

	@Override
	protected AbstractSingleFilterBuilder createNewFilterBuilder(Filter filter) {
		return new SingleFilterBuilder(filter, FilterBuilderSubClause.this, this.editor);
	}

}
