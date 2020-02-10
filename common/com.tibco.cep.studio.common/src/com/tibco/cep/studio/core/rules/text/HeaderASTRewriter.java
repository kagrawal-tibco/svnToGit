package com.tibco.cep.studio.core.rules.text;

import java.util.List;

import com.tibco.cep.studio.core.rules.ast.DefaultHeaderASTNodeVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.HeaderParser;

public class HeaderASTRewriter extends DefaultHeaderASTNodeVisitor {

	private StringBuffer fBuffer;

	public HeaderASTRewriter() {
		super();
		init();
	}

	private void init() {
		fBuffer = new StringBuffer();
	}

	@Override
	public boolean visitAuthorStatementNode(RulesASTNode node) {
		fBuffer.append(' ');
		fBuffer.append('*');
		fBuffer.append(' ');
		fBuffer.append("@author ");
		List<RulesASTNode> children = node.getChildrenByFeatureId(HeaderParser.NAME);
		if (children != null) {
			for (RulesASTNode child : children) {
				if (child.getType() == HeaderParser.NEWLINE) {
					fBuffer.append(child.getText());
				} else {
					fBuffer.append(child.getText());
				}
			}
		}
		fBuffer.append('\r');
		fBuffer.append('\n');
		return false;
	}

	@Override
	public boolean visitDescriptionStatementNode(RulesASTNode node) {
		fBuffer.append(' ');
		fBuffer.append('*');
		fBuffer.append(' ');
		fBuffer.append("@description ");
		List<RulesASTNode> children = node.getChildrenByFeatureId(HeaderParser.LITERALS);
		if (children != null) {
			for (RulesASTNode child : children) {
				if (child.getType() == HeaderParser.NEWLINE) {
					fBuffer.append(child.getText());
				} else {
					fBuffer.append(child.getText());
				}
			}
		}
		fBuffer.append('\r');
		fBuffer.append('\n');
		return false;
	}

	@Override
	public boolean visitHeaderBlockNode(RulesASTNode node) {
		RulesASTNode children = node.getChildByFeatureId(HeaderParser.STATEMENTS);
		visitChildren(children);
		return false;
	}

	public String getText() {
		return fBuffer.toString();
	}

}
