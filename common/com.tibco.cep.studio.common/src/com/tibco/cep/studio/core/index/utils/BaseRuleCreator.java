package com.tibco.cep.studio.core.index.utils;

import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.utils.CommonRuleCreator;

public class BaseRuleCreator extends CommonRuleCreator {

	public BaseRuleCreator() {
		super(false);
	}

	public BaseRuleCreator(boolean includeBodyText) {
		super(includeBodyText);
	}
	
	public Compilable createRule(String projectName,Path file) {
		if (CommonIndexUtils.RULE_EXTENSION.equalsIgnoreCase(file.getFileExtension())) {
			RulesASTNode tree = (RulesASTNode) BaseRulesParserManager.parseRuleFile(projectName,file, fIncludeBodyText);
			RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(fIncludeBodyText, projectName);
			tree.accept(visitor);
			Compilable rule = visitor.getRule();
			return rule;
		} else if (CommonIndexUtils.RULEFUNCTION_EXTENSION.equalsIgnoreCase(file.getFileExtension())) {
			RulesASTNode tree = (RulesASTNode) BaseRulesParserManager.parseRuleFile(projectName,file, fIncludeBodyText);
			RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(fIncludeBodyText, projectName);
			tree.accept(visitor);
			Compilable rule = visitor.getRule();
			return rule;
		}
		return null;
	}
	
}
