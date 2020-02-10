package com.tibco.cep.webstudio.client.widgets;

import java.util.List;

import com.smartgwt.client.types.FieldType;
import com.tibco.cep.webstudio.client.CommandOperatorUtils;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

/**
 * Widget for representing operators for RTI Commands
 * 
 * @author Vikram Patil
 */
public class CommandOperatorLink extends AbstractOperatorLink {

	public CommandOperatorLink(IFilterBuilder filterBuilder, AbstractEditor editor) {
		super(filterBuilder, editor);
	}

	@Override
	protected List<IBuilderOperator> getOperatorsForType(FieldType type) {
		return CommandOperatorUtils.getOperators(type);
	}

}
