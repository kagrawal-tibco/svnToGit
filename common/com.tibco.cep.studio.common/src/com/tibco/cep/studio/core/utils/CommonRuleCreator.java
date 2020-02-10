package com.tibco.cep.studio.core.utils;

import java.io.File;
import java.io.InputStream;

import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class CommonRuleCreator {

	protected boolean fIncludeBodyText;
	protected boolean fIncludeFullRuleText;

	public CommonRuleCreator() {
		this(false);
	}

	public CommonRuleCreator(boolean includeBodyText) {
		this(includeBodyText, false);
	}
	
	public CommonRuleCreator(boolean includeBodyText, boolean includeFullRuleText) {
		this.fIncludeBodyText = includeBodyText;
		this.fIncludeFullRuleText = includeFullRuleText;
	}

	public Compilable createRule(File file, String projectName) {
		if (file == null) return null;
		String absPath = file.getAbsolutePath();
		if (absPath == null) return null;
		if (absPath.endsWith(CommonIndexUtils.RULE_EXTENSION)
                || absPath.endsWith(CommonIndexUtils.RULE_TEMPLATE_EXTENSION)
                || absPath.endsWith(CommonIndexUtils.RULEFUNCTION_EXTENSION)) {
			RulesASTNode tree = (RulesASTNode) CommonRulesParserManager.parseRuleFile(null, file, fIncludeBodyText);
			if (tree == null) return null;
			RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(fIncludeBodyText, fIncludeFullRuleText, projectName);
			tree.accept(visitor);
			Compilable rule = visitor.getRule();
			return rule;
		}
		return null;
	}
	
	public Compilable createRule(InputStream is, String projectName) {
		if (is == null) return null;
		RulesASTNode tree = (RulesASTNode) CommonRulesParserManager.parseRuleInputStream(null, is, fIncludeBodyText);
		if (tree == null) return null;
		RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(fIncludeBodyText, fIncludeFullRuleText, projectName);
		tree.accept(visitor);
		Compilable rule = visitor.getRule();
		return rule;
	}
	
}
