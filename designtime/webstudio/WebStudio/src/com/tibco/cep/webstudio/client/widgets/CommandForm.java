package com.tibco.cep.webstudio.client.widgets;

import java.util.List;

import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.model.ruletemplate.SymbolInfo;
import com.tibco.cep.webstudio.model.rule.instance.Command;

/**
 * Widget to represent Rule Template Instance Commands
 * 
 * @author Vikram Patil
 */
public class CommandForm extends VLayout implements IRuleTemplateFilter {

	private Command command;
	protected AbstractEditor editor;
	private List<SymbolInfo> symbolList;

	public CommandForm(Command command, AbstractEditor editor, List<SymbolInfo> symbolList) {
		super(5);
		this.command = command;
		this.editor = editor;
		this.symbolList = symbolList;
		this.initialize();
	}

	private void initialize() {
		this.setMargin(5);

		CommandBuilderSubClause subClause = new CommandBuilderSubClause(this.command.getSubClause(),
				this.editor,
				this.symbolList,
				this.command);
		this.addMember(subClause);
		subClause.setWidth100();
	}
}
