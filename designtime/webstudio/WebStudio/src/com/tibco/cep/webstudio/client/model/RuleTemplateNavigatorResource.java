package com.tibco.cep.webstudio.client.model;

import java.util.List;

import com.tibco.cep.webstudio.client.model.ruletemplate.CommandInfo;
import com.tibco.cep.webstudio.client.model.ruletemplate.SymbolInfo;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;

/**
 * Tree Node for representing Rule Template
 * 
 * @author Vikram Patil
 */
public class RuleTemplateNavigatorResource extends NavigatorResource{

	private List<SymbolInfo> symbols;
	private List<CommandInfo> commands;

	public List<CommandInfo> getCommands() {
		return commands;
	}

	public RuleTemplateNavigatorResource(String name,
											String parent,
											String id,
											String type,
											String icon,
											ARTIFACT_TYPES editorType,
											List<SymbolInfo> symbols,
											List<CommandInfo> commands) {
		super(name, parent, id, type, icon, editorType);
		this.symbols = symbols;
		this.commands = commands;
	}

	public List<SymbolInfo> getSymbols() {
		return symbols;
	}

	/**
	 * Copy constructor;
	 * @param resource
	 */
	public RuleTemplateNavigatorResource(RuleTemplateNavigatorResource resource) {
		this(resource.getName(), resource.getParent(), resource.getId(),
				resource.getType(), resource.getIcon(), resource
						.getEditorType(), resource.getSymbols(), resource
						.getCommands());
	}
}
