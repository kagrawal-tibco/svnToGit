package com.tibco.cep.studio.core.index.utils;

import org.eclipse.core.resources.IFile;

import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class RuleElementCreator {

	private boolean fIncludeBodyText;

	public RuleElementCreator() {
		this(false);
	}

	public RuleElementCreator(boolean includeBodyText) {
		this.fIncludeBodyText = includeBodyText;
	}
	
	public RuleElement createRuleElement(IFile file) {
		RulesASTNode tree = (RulesASTNode) RulesParserManager.parseRuleFile(file, fIncludeBodyText);
		if (tree == null) {
			StudioCorePlugin.debug("Could not create rule element for "+file.getName());
//			System.out.println("Could not create rule element for "+file.getName());
			return null;
		}
		RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(fIncludeBodyText, file.getProject().getName());
		tree.accept(visitor);
		RuleElement element = (RuleElement) tree.getData("element");
		String fileName = file.getFullPath().removeFileExtension().lastSegment();
		if (!fileName.equals(element.getName())) {
			element.setName(fileName);
		}
		if (!IndexUtils.getFileFolder(file).equals(element.getFolder())) {
			element.setFolder(IndexUtils.getFileFolder(file));
		}
		element.setIndexName(file.getProject().getName());
		element.setElementType(IndexUtils.getIndexType(file));
		Compilable rule = visitor.getRule();
		if (rule instanceof RuleFunction) {
			element.setVirtual(((RuleFunction)rule).isVirtual());
		} else {
			element.setVirtual(false);
		}
		element.setRule(rule);
		return element;
	}
	
}
