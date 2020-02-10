package com.tibco.cep.metric.evaluator;

import java.util.Map;
import java.util.Stack;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

import com.tibco.cep.metric.condition.ast.filterLexer;
import com.tibco.cep.metric.condition.ast.filterParser;
import com.tibco.cep.runtime.model.element.Concept;

public class ASTWalker {

	private Tree tree;
//	private filterLexer lexer;
//	private filterParser parser;
	private Visitor visitor;
	private boolean testForPID;

//	Stack<Object> stack;
	
	public ASTWalker(final String input) throws Exception {
		if (input == null || input.length() == 0) {
			return;
		}
		CharStream charStream = new ANTLRStringStream(input.trim());
		filterLexer lexer = new filterLexer(charStream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		filterParser parser = new filterParser(tokens);
		tree = (CommonTree) parser.selector().getTree();
//		stack = new Stack<Object>();
		visitor = new Visitor();
		if (testForPID) {
			visitor.setTestForPID();
		}

		if (tree == null) {
			throw new Exception("condition [" + input + "] could not be parsed");
		}
	}

	public final boolean evaluate(final Concept concept) throws Exception {
		if (tree == null) {
			return true; // null input was passed in
		}
		Stack<Object> stack = new Stack<Object>();
		visitor.visit(tree, concept, null, stack);
		return (Boolean) stack.pop();
	}

	public final boolean evaluate(final Concept concept,
			Map<String, Object> bindValues) throws Exception {
		if (tree == null) {
			return true; // null input was passed in
		}
		Stack<Object> stack = new Stack<Object>();
		visitor.visit(tree, concept, bindValues, stack);
		return (Boolean) stack.pop();
	}
	
	public final void setTestForPID() {
		testForPID = true;
	}
}
