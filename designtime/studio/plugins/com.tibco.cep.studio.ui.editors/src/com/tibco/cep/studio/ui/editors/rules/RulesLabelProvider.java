package com.tibco.cep.studio.ui.editors.rules;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;

public class RulesLabelProvider extends LabelProvider implements IColorProvider {

	public Color getBackground(Object element) {
		return null;
	}

	public Color getForeground(Object element) {
		return null;
	}

	@Override
	public Image getImage(Object element) {
		if (!(element instanceof CommonTree)) {
			return super.getImage(element);
		}
		CommonTree tree = (CommonTree) element;
		if (tree.getType() == RulesParser.RULE_DECL) {
			return EditorsUIPlugin.getDefault().getImage("icons/rules.png");
		}
		if (tree.getType() == RulesParser.RULE_FUNC_DECL) {
			return EditorsUIPlugin.getDefault().getImage("icons/rule-function.png");
		}
		
		return EditorsUIPlugin.getDefault().getImage("icons/outline_node.png");
	}

	@Override
	public String getText(Object element) {
		if (!(element instanceof RulesASTNode)) {
			return super.getText(element);
		}
		RulesASTNode node = (RulesASTNode) element;
		RulesASTNode nameNode = node.getChildOfType(RulesParser.NAME);
		if (nameNode != null) {
			return super.getText(element) + " " + RuleGrammarUtils.getSimpleNameFromNode(nameNode);
		}
		return super.getText(element);
	}

}
