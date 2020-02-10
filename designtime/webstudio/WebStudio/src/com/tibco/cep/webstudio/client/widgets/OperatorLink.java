package com.tibco.cep.webstudio.client.widgets;

import java.util.List;

import com.smartgwt.client.types.FieldType;
import com.tibco.cep.webstudio.client.FilterOperatorUtils;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

/**
 * Widget for representing Various Operations within a Rule Template Instance
 * Filter
 * 
 * @author Vikram Patil
 */
public class OperatorLink extends AbstractOperatorLink {

	public OperatorLink(IFilterBuilder filterBuilder, AbstractEditor view) {
		super(filterBuilder, view);
	}

	@Override
	protected List<IBuilderOperator> getOperatorsForType(FieldType type) {
		return FilterOperatorUtils.getOperators(type);
	}

}
