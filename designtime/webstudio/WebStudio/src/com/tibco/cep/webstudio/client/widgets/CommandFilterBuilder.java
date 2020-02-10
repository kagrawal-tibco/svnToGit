package com.tibco.cep.webstudio.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.types.FieldType;
import com.tibco.cep.webstudio.client.CommandOperatorUtils;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RTIMessages;
import com.tibco.cep.webstudio.client.model.ruletemplate.SymbolInfo;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.FilterValue;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedEvent;
import com.tibco.cep.webstudio.model.rule.instance.IInstanceChangedListener;
import com.tibco.cep.webstudio.model.rule.instance.RelatedFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.SingleFilter;
import com.tibco.cep.webstudio.model.rule.instance.impl.EmptyFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.RelatedFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.SimpleFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.operators.CommandOperator;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

/**
 * Widget to represent a single filter builder for RTI commands
 * 
 * @author Vikram Patil
 * @author Ryan
 */
public class CommandFilterBuilder extends AbstractSingleFilterBuilder {
	protected static RTIMessages rtiMsgBundle = (RTIMessages)I18nRegistry.getResourceBundle(I18nRegistry.RTI_MESSAGES);

	public CommandFilterBuilder(Filter filter,
								CommandBuilderSubClause commandBuilderSubClause,
								AbstractEditor editor,
								List<SymbolInfo> symbols) {
		super(filter, commandBuilderSubClause, editor, false, false, symbols);
	}
	
	@Override
	public String[] getOperators() {
		FieldType type = this.getLinkType();
		List<IBuilderOperator> operators = this.getOperatorsForType(type);
		
		boolean isCreateCommand = false;
		if (parentClause instanceof CommandBuilderSubClause) {
			String actionType = ((CommandBuilderSubClause)parentClause).getCommand().getActionType();
			
			if (actionType != null && RuleTemplateInstanceEditorFactory.COMMAND_ACTIONTYPE_CREATE.equalsIgnoreCase(actionType)) {
				isCreateCommand = true;
			}
		}
		
		String opName = null;
		
		List<String> ops = new ArrayList<String>();
		for (IBuilderOperator operator : operators) {
			opName = operator.getValue();
			if (!isCreateCommand
					|| (isCreateCommand && !(opName
							.equals(CommandOperator.INCREMENT_BY.getValue())
							|| opName.equals(CommandOperator.INCREMENT_BY_FIELD
									.getValue())
							|| opName.equals(CommandOperator.DECREMENT_BY
									.getValue()) || opName
								.equals(CommandOperator.DECREMENT_BY_FIELD
										.getValue())))) {
				ops.add(opName);
			}
		}
		return ops.toArray(new String[ops.size()]);
	}

	protected CommandOperator getCommandOperator() {
		CommandOperator[] values = CommandOperator.values();
		String op = ((SingleFilter) this.filter).getOperator();
		for (CommandOperator fOp : values) {
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

		CommandOperator commandOperator = this.getCommandOperator();
		if (commandOperator == null) {
			return;
		}
		switch (commandOperator) {

		case SET_TO:
		case INCREMENT_BY:
		case DECREMENT_BY: {
			filterValue = new SimpleFilterValueImpl();
			break;
		}

		case INCREMENT_BY_FIELD:
		case SET_TO_FIELD:
		case DECREMENT_BY_FIELD: {
			final RelatedFilterValue relatedVal = new RelatedFilterValueImpl();
			relatedVal.addChangeListener(this.editor.getChangeHandler());
			relatedVal.addChangeListener(new IInstanceChangedListener() {

				@Override
				public void instanceChanged(IInstanceChangedEvent changeEvent) {
					CommandFilterBuilder.this.processRelatedFilterValueChangedEvent(relatedVal, changeEvent);
				}
			});
			filterValue = relatedVal;
			break;
		}

		/*case SET_TO_DATE: {
			filterValue = new SimpleFilterValueImpl();
			break;
		}*/

		case SET_TO_FALSE:
		case SET_TO_TRUE:
		case SET_TO_NULL: {
			filterValue = new EmptyFilterValueImpl();
			break;
		}

		default:
			break;
		}

		if (filterValue != null) {
			((SingleFilter) this.filter).setFilterValue(filterValue);
		}

	}

	@Override
	protected List<IBuilderOperator> getOperatorsForType(FieldType type) {
		return CommandOperatorUtils.getOperators(type);
	}

	@Override
	protected AbstractOperatorLink createInitialOperatorLink(DataSourcesLink initOpLink) {
		return new CommandOperatorLink(this, this.editor);
	}

	@Override
	protected AbstractRelatedLink createNewRelatedLink(RelatedLink modelLink, AbstractRelatedLink prevLink) {
		CommandRelatedLink cmdLink = new CommandRelatedLink(modelLink, CommandFilterBuilder.this, prevLink, true);
		cmdLink.editor = this.editor;

		return cmdLink;
	}
}
