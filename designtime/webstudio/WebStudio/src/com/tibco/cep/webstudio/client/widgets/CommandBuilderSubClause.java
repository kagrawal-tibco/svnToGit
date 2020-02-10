package com.tibco.cep.webstudio.client.widgets;

import java.util.List;

import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
import com.tibco.cep.webstudio.client.model.ruletemplate.SymbolInfo;
import com.tibco.cep.webstudio.model.rule.instance.BuilderSubClause;
import com.tibco.cep.webstudio.model.rule.instance.Command;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.operators.CommandOperator;

/**
 * Sub Clause Widget for Command Builder
 * 
 * @author Vikram Patil
 */
public class CommandBuilderSubClause extends AbstractBuilderSubClauseLayout {
	private WebStudioClientLogger logger = WebStudioClientLogger.getLogger(CommandBuilderSubClause.class.getName());
	private Command command;

	public CommandBuilderSubClause(BuilderSubClause model, AbstractEditor editor, List<SymbolInfo> symbols, Command command) {
		super();
		this.model = model;
		this.allowNestedClause = false;
		this.editor = editor;
		this.symbols = symbols;
		this.command = command;
		initialize();
	}

	public void addNewNestedClause() {
		logger.error(new UnsupportedOperationException("Nested clauses not supported for commands"));
	}

	@Override
	protected AbstractSingleFilterBuilder createNewFilterBuilder(Filter filter) {
		return new CommandFilterBuilder(filter, CommandBuilderSubClause.this, this.editor, this.symbols);
	}

	@Override
	protected String getInitialOperator() {
		return CommandOperator.SET_TO.getValue();
	}
	
	public Command getCommand(){
		return this.command;
	}
}
