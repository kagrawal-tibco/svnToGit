package com.tibco.cep.studio.core.rules.text;

import com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;

public class RulesASTRewriter extends DefaultRulesASTNodeVisitor {

	private StringBuffer fBuffer;

	public RulesASTRewriter() {
		super();
		init();
	}

	private void init() {
		fBuffer = new StringBuffer();
	}

	@Override
	public boolean visitDeclaratorNode(RulesASTNode node) {
		RulesASTNode typeNode = node.getChildByFeatureId(RulesParser.TYPE);
		RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.NAME);
		
		fBuffer.append('\t');
		fBuffer.append('\t');
		visit(typeNode);
		fBuffer.append(' ');
		visit(nameNode);
		fBuffer.append(';');
		newline();
		
		return false;
	}

	@Override
	public boolean visitActionContextStatementNode(RulesASTNode node) {
		RulesASTNode actionTypeNode = node.getChildByFeatureId(RulesParser.ACTION_TYPE);
		RulesASTNode typeNode = node.getChildByFeatureId(RulesParser.TYPE);
		RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.NAME);
		
		fBuffer.append('\t');
		fBuffer.append('\t');
		fBuffer.append(actionTypeNode.getText());
		fBuffer.append(' ');
		visit(typeNode);
		fBuffer.append(' ');
		visit(nameNode);
		fBuffer.append(';');
		newline();
		
		return false;
	}

	@Override
	public boolean visitQualifiedNameNode(RulesASTNode node) {
		RulesASTNode qualifier = node.getChildByFeatureId(RulesParser.QUALIFIER);
		if (qualifier != null) {
			visit(qualifier);
		}
		fBuffer.append('.');
		
		RulesASTNode simpleName = node.getFirstChildWithType(RulesParser.SIMPLE_NAME);
		visit(simpleName);
		
		return false;
	}

	@Override
	public boolean visitSimpleNameNode(RulesASTNode node) {
		fBuffer.append(node.getFirstChild().getText());
		return false;
	}

	public String getText() {
		return fBuffer.toString();
	}
	
	@Override
	public boolean visitAliasStatementNode(RulesASTNode node) {
		RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.LITERAL);
		
		fBuffer.append('\t');
		fBuffer.append('\t');
		fBuffer.append("alias = ");
		visit(nameNode);
		fBuffer.append(';');
		newline();
		
		return false;
	}

	@Override
	public boolean visitRankStatementNode(RulesASTNode node) {
		RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.LITERAL);
		
		fBuffer.append('\t');
		fBuffer.append('\t');
		fBuffer.append("rank = ");
		visit(nameNode);
		fBuffer.append(';');
		newline();
		
		return false;
	}
	
	@Override
	public boolean visitBindingViewStatement(RulesASTNode node) {
		RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.NAME);
		
		fBuffer.append('\t');
		fBuffer.append('\t');
		visit(nameNode);
		fBuffer.append(';');
		newline();
		
		return false;
	}
	
	@Override
	public boolean visitDisplayProperty(RulesASTNode node) {
		RulesASTNode nameNode = node.getChildByFeatureId(RulesParser.NAME);
		RulesASTNode litNode = node.getChildByFeatureId(RulesParser.LITERAL);
		
		fBuffer.append('\t');
		fBuffer.append('\t');
		visit(nameNode);
		fBuffer.append(' ');
		fBuffer.append('=');
		fBuffer.append(' ');
		visit(litNode);
		fBuffer.append(';');
		newline();
		
		return false;
	}
	
	@Override
	public boolean visitDisplayBlock(RulesASTNode node) {
		RulesASTNode blockNode = node.getChildByFeatureId(RulesParser.BLOCK);
		
		fBuffer.append('\t');
		fBuffer.append("display");
		fBuffer.append(' ');
		fBuffer.append('{');
		newline();
		visitChildren(blockNode);
		fBuffer.append('\t');
		fBuffer.append('}');
		newline();
		
		return false;
	}

	@Override
	public boolean visitStringLiteralNode(RulesASTNode node) {
		fBuffer.append(node.getText());
		return false;
	}

	private void newline() {
		fBuffer.append('\r');
		fBuffer.append('\n');
	}

	public void clear() {
		fBuffer.setLength(0);
	}

}
