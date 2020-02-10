package com.tibco.cep.studio.core.index.utils;

import org.eclipse.core.resources.IFile;

import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.utils.CommonRuleCreator;

public class RuleCreator extends CommonRuleCreator {

	public RuleCreator() {
		super(false);
	}

	public RuleCreator(boolean includeBodyText) {
		super(includeBodyText);
	}
	
	public Compilable createRule(IFile file) {
		if (IndexUtils.RULE_EXTENSION.equalsIgnoreCase(file.getFileExtension())) {
			RulesASTNode tree = (RulesASTNode) RulesParserManager.parseRuleFile(file, fIncludeBodyText);
			RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(fIncludeBodyText, file.getProject().getName());
			tree.accept(visitor);
			Compilable rule = visitor.getRule();
			return rule;
		} else if (IndexUtils.RULEFUNCTION_EXTENSION.equalsIgnoreCase(file.getFileExtension())) {
			RulesASTNode tree = (RulesASTNode) RulesParserManager.parseRuleFile(file, fIncludeBodyText);
			RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(fIncludeBodyText, file.getProject().getName());
			tree.accept(visitor);
			Compilable rule = visitor.getRule();
			return rule;
		}
		return null;
	}
	
}
