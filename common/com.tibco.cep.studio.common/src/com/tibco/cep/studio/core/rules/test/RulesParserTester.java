package com.tibco.cep.studio.core.rules.test;

import java.io.IOException;
import java.util.Date;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.ast.RulesTreeAdaptor;
import com.tibco.cep.studio.core.rules.grammar.RulesLexer;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.rules.grammar.RulesParser.startRule_return;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class RulesParserTester {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("No file specified.  Exiting");
			return;
		}
		try {
			System.out.println("Attempting parse of "+args[0]);
			Date start = new Date();
			/*
			 * You can also use ANTLRStringStream rather than ANTLRFileStream here:
			 * ANTLRStringStream stream = new ANTLRStringStream("Here is my input");
			 */
			RulesLexer lexer = new RulesLexer(new ANTLRFileStream(args[0], ModelUtils.DEFAULT_ENCODING));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			RulesParser parser = new RulesParser(tokens);
			parser.setTreeAdaptor(new RulesTreeAdaptor());
			startRule_return startRule = parser.startRule();
			Date end = new Date();
//			RulesASTNode headerNode = parser.getHeaderNode();
			System.out.println("Parse finished in "+(end.getTime() - start.getTime()));
			if (startRule != null) {
				CommonTree tree = (CommonTree) startRule.getTree();
				System.out.println("Tree: "+tree.toStringTree());
//				walkAST(0, tree, tokens);
			} else {
				System.out.println("Return value is null");
			}
			RuleElement element = parser.getRuleElement();
			EList<GlobalVariableDef> globalVariables = element.getGlobalVariables();
			ScopeBlock scope = element.getScope();
			System.out.println("");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
	}

	private static void walkAST(int tabs, CommonTree tree, CommonTokenStream tokens) {
		StringBuilder builder = new StringBuilder();
		builder.append("type: "+tree.getType());
		builder.append(" -- text: '"+tree.getText()+"'");
		int tokenStartIndex = tree.getTokenStartIndex(); // the first token that spans this node
		int tokenStopIndex = tree.getTokenStopIndex(); // the last token that spans this node
		if (tokenStartIndex == -1) {
			// this is an 'implicit' node that has no corresponding tokens (i.e. NAME)
			// the offset/length information can be obtained from its children
			int childCount = tree.getChildCount();
			if (childCount > 0) {
				CommonTree firstChild = (CommonTree) tree.getChild(0);
				CommonTree lastChild = (CommonTree) tree.getChild(childCount - 1);
				
				tokenStartIndex = firstChild.getTokenStartIndex();
				tokenStopIndex = lastChild.getTokenStopIndex();
			}
		} 
		if (tokenStartIndex >= 0) {
			CommonToken startToken = (CommonToken) tokens.get(tokenStartIndex);
			CommonToken stopToken = (CommonToken) tokens.get(tokenStopIndex);
			int offset = startToken.getStartIndex();
			int length = stopToken.getStopIndex() - offset + 1;
			builder.append(" -- node offset: "+offset);
			builder.append(" -- node length: "+length);
			builder.append(" -- line number: "+startToken.getLine());
			builder.append(" -- column number: "+startToken.getCharPositionInLine());
		}
		
		System.out.println(getTabs(tabs)+builder.toString());
		int childCount = tree.getChildCount();
		for (int i = 0; i < childCount; i++) {
			CommonTree child = (CommonTree) tree.getChild(i);
			walkAST(tabs+1, child, tokens);
		}
	}
	
	private static String getTabs(int tabCount) {
		String tabs = "";
		for (int i = 0; i < tabCount; i++) {
			tabs += "\t";
		}
		return tabs;
	}
}
