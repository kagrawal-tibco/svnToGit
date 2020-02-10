package com.tibco.rta.model.command.ast;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 27/11/12
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandTreeParser {

    /**
     *
     * @param commandString
     * @return
     * @throws RecognitionException
     */
    public static CommandASTNode parse(String commandString) throws RecognitionException {
        SPMCLIParser parser = initParser(commandString);
        SPMCLIParser.command_expr_return modelCreationRule = parser.command_expr();

        if (modelCreationRule != null) {
        	return (CommandASTNode) modelCreationRule.getTree();
        }
        return null;
    }

    private static SPMCLIParser initParser(String commandString) {
        //TODO add problem handler
        SPMCLILexer lexer = new SPMCLILexer(new ANTLRStringStream(commandString));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SPMCLIParser parser = new SPMCLIParser(tokens);
        parser.setTreeAdaptor(new CommandTreeAdaptor());
        return parser;
    }
    

    /**
     * Sample code to print the AST. Courtesy antlr examples
     * @param t
     * @param indent
     */
    public static void printTree(CommonTree t, int indent) {
    	if (t != null) {
    		StringBuffer sb = new StringBuffer(indent);

    		if (t.getParent() == null){
    			System.out.println(sb.toString() + t.getText());
    		}
    		for (int i = 0; i < indent; i++ )
    			sb = sb.append("   ");
    		for (int i = 0; i < t.getChildCount(); i++ ) {
    			System.out.println(sb.toString() + t.getChild(i).toString());
    			printTree((CommonTree)t.getChild(i), indent + 1);
    		}
    	}
    }
}
