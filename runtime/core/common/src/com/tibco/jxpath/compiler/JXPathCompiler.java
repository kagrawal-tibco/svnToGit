package com.tibco.jxpath.compiler;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonErrorNode;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.Tree;

import com.tibco.jxpath.Expression;

/*
* Author: Suresh Subramani / Date: 10/29/11 / Time: 8:13 PM
*/
public class JXPathCompiler {

	public class XPathTreeAdaptor extends CommonTreeAdaptor {

		@Override
		public Object create(Token payload) {
			JXPathASTNode node = new JXPathASTNode(payload);
			return node;
		}

		@Override
		public Object errorNode(TokenStream input, Token start, Token stop,
				RecognitionException e) {
			return new JXPathASTErrorNode(input, start, stop, e);
		}
		
		
	}
	

    public JXPathCompiler ()
    {
    }

    public Expression compile(String input) throws Exception
    {
        CommonTreeAdaptor xpathTreeAdaptor = new XPathTreeAdaptor();
        CharStream charStream = new ANTLRNoCaseStringStream(input);
        JXPathLexer lexer = new JXPathLexer(charStream);
        CommonTokenStream cts = new CommonTokenStream(lexer);
        JXPathParser parser = new JXPathParser(cts);
        parser.setTreeAdaptor(xpathTreeAdaptor);
        Tree tree = (CommonTree) parser.main().getTree();

        if (tree instanceof CommonErrorNode) {
            throw new Exception(((CommonErrorNode)tree).getText());
        }

        Expression expr = walkTree(tree);
        if (expr == null) {
            throw new Exception("Failed to construct Expression");
        }
        return expr;
    }

    private Expression walkTree(Tree tree) throws Exception {

    	JXPathASTWalker walker = new JXPathASTWalker();
    	if (tree instanceof JXPathASTNode) {
    		((JXPathASTNode) tree).accept(walker);
    		Expression rootExpression = walker.getRootExpression();
    		return rootExpression;
    	}
        return null;
    }
    	

}
