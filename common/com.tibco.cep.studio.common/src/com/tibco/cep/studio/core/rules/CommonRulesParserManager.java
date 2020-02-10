package com.tibco.cep.studio.core.rules;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

import com.tibco.cep.designtime.model.rule.Compilable.CodeBlock;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.ast.RulesTreeAdaptor;
import com.tibco.cep.studio.core.rules.grammar.RulesLexer;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class CommonRulesParserManager {

	private static boolean fDebug = false;
	
	public static CommonTree parseRuleFile(String projectName, String fullFilePath) {
		return parseRuleFile(projectName, fullFilePath, null);
	}
	
	public static RulesASTNode parseRuleFile(String projectName, String fullFilePath, IProblemHandler collector) {
		return parseRuleFile(projectName, fullFilePath, collector, false);
	}
	
	public static RulesASTNode parseRuleFile(String projectName, String fullFilePath, IProblemHandler collector, boolean includeTokensInTree) {
		try {
			printDebug("Attempting parse of "+fullFilePath);
			Date start = new Date();
			RulesLexer lexer = new RulesLexer(new ANTLRFileStream(fullFilePath, ModelUtils.DEFAULT_ENCODING));
			lexer.setProblemHandler(collector);
			CommonTokenStream tokens = new CommonTokenStream(lexer, Token.DEFAULT_CHANNEL);
			RulesParser parser = new RulesParser(tokens);
			parser.setTreeAdaptor(new RulesTreeAdaptor());
			if (collector == null) {
				collector = new DefaultProblemHandler();
			}
			parser.addProblemCollector(collector);
			ParserRuleReturnScope startRule = parser.startRule();
			Date end = new Date();
			printDebug("Parse finished in "+(end.getTime() - start.getTime()));
			if (startRule != null) {
				RulesASTNode tree = (RulesASTNode) startRule.getTree();
				if (tree == null) {
					System.err.println("AST is null.  Perhaps source is empty?");
					return null;
				}
				populateASTData(projectName, parser, tree, collector, includeTokensInTree);
				return tree;
			} else {
                printDebug("Return value is null");
			}
		} catch (IOException e) {
			e.printStackTrace();
//			StudioCommonPlugin.getDefault().getLog().log(new Status(Status.ERROR, StudioCommonPlugin.PLUGIN_ID,
//					"Could not parse file "+fullFilePath, e));
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static RulesASTNode parseRuleInputStream(String projectName, InputStream is, IProblemHandler collector, boolean includeTokensInTree, boolean shared) {
		try {
			ANTLRInputStream antlrStream = new ANTLRInputStream(is,ModelUtils.DEFAULT_ENCODING);
			return parseRuleInputStream(projectName, antlrStream, collector,
					includeTokensInTree, shared);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static RulesASTNode parseRuleCharArray(String projectName, char[] charArray, IProblemHandler collector, boolean includeTokensInTree) {
		try {
			ANTLRStringStream antlrStream = new ANTLRStringStream(charArray,charArray.length);
			return parseRuleInputStream(projectName, antlrStream, collector,
					includeTokensInTree, false);
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static RulesASTNode parseRuleInputStream(String projectName,
			ANTLRStringStream antlrStream, IProblemHandler collector,
			boolean includeTokensInTree, boolean shared)
			throws RecognitionException {
		Date start = new Date();
		RulesLexer lexer = new RulesLexer(antlrStream);
		lexer.setProblemHandler(collector);
		CommonTokenStream tokens = new CommonTokenStream(lexer, Token.DEFAULT_CHANNEL);
		RulesParser parser = new RulesParser(tokens);
		if (shared) {
			parser.setRuleElement(IndexFactory.eINSTANCE.createSharedRuleElement());
		}
		parser.setTreeAdaptor(new RulesTreeAdaptor());
		if (collector == null) {
			collector = new DefaultProblemHandler();
		}
		parser.addProblemCollector(collector);
		ParserRuleReturnScope startRule = parser.startRule();
		Date end = new Date();
		printDebug("Parse finished in "+(end.getTime() - start.getTime()));
		if (startRule != null) {
			RulesASTNode tree = (RulesASTNode) startRule.getTree();
			if (tree == null) {
				printDebug("AST is null.  Perhaps source is empty?");
				return null;
			}
			populateASTData(projectName, parser, tree, collector, includeTokensInTree);
			return tree;
		} else {
			printDebug("Return value is null");
			return null;
		}
	}
	
	private static void printDebug(String message) {
		if (fDebug ) {
			System.out.println(message);
		}
	}

	public static CommonTree parseConditionsBlockString(String projectName, String contents, IProblemHandler collector) {
		try {
			Date start = new Date();
			if (collector == null) {
				collector = new DefaultProblemHandler();
			}
			RulesParser parser = initParser(contents, collector);
			ParserRuleReturnScope whenReturn = parser.predicateStatements();
			checkForExtraTokens(collector, parser);
			Date end = new Date();
			printDebug("Parse finished in "+(end.getTime() - start.getTime()));
			if (whenReturn != null && whenReturn.getTree() != null) {
				RulesASTNode tree = (RulesASTNode) whenReturn.getTree();
				populateASTData(projectName, parser, tree, collector, true);
				return tree;
			} else {
                printDebug("Return value is null");
			}
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static CommonTree parsePreConditionsBlockString(String projectName, String contents, IProblemHandler collector) {
		try {
			Date start = new Date();
			if (collector == null) {
				collector = new DefaultProblemHandler();
			}
			RulesParser parser = initParser(contents, collector);
			ParserRuleReturnScope whenReturn = parser.preconditionStatements();
			checkForExtraTokens(collector, parser);
			Date end = new Date();
			printDebug("Parse finished in "+(end.getTime() - start.getTime()));
			if (whenReturn != null && whenReturn.getTree() != null) {
				RulesASTNode tree = (RulesASTNode) whenReturn.getTree();
				populateASTData(projectName, parser, tree, collector, true);
				return tree;
			} else {
                printDebug("Return value is null");
			}
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static CommonTree parseActionBlockString(String projectName, String contents, IProblemHandler collector) {
		try {
			Date start = new Date();
			if (collector == null) {
				collector = new DefaultProblemHandler();
			}
			if (contents.length() > 0 && contents.charAt(contents.length()-1) != '\n') {
				contents += '\n';
			}
			RulesParser parser = initParser(contents, collector);
			ParserRuleReturnScope thenReturn = parser.thenStatements();
			checkForExtraTokens(collector, parser);
			Date end = new Date();
			printDebug("Parse finished in "+(end.getTime() - start.getTime()));
			if (thenReturn != null && thenReturn.getTree() != null) {
				RulesASTNode tree = (RulesASTNode) thenReturn.getTree();
				populateASTData(projectName, parser, tree, collector, true);
				return tree;
			} else {
                printDebug("Return value is null");
			}
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static CommonTree parseActionContextBlockString(String projectName, String contents, IProblemHandler collector) {
		try {
			Date start = new Date();
			if (collector == null) {
				collector = new DefaultProblemHandler();
			}
			RulesParser parser = initParser(contents, collector);
			parser.setInActionContextBlock(true);
			ParserRuleReturnScope thenReturn = parser.actionContextStatements();
			checkForExtraTokens(collector, parser);
			Date end = new Date();
			printDebug("Parse finished in "+(end.getTime() - start.getTime()));
			if (thenReturn != null && thenReturn.getTree() != null) {
				RulesASTNode tree = (RulesASTNode) thenReturn.getTree();
				populateASTData(projectName, parser, tree, collector, true);
				return tree;
			} else {
                printDebug("Return value is null");
			}
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void checkForExtraTokens(IProblemHandler collector,
			RulesParser parser) {
		CommonTokenStream tknStream = (CommonTokenStream)parser.getTokenStream();
		int idx = tknStream.index();
		if (idx < tknStream.size()) {
			for (int i=idx; i<tknStream.size(); i++) {
				if (tknStream.get(i).getChannel() != 99) {
					// report unexpected token, there should not be any non-ws tokens leftover here
					RecognitionException ex = new RecognitionException();
					ex.token=tknStream.get(i);
					RulesSyntaxProblem problem = new RulesSyntaxProblem(
							"Unexpected token",
							ex);
					collector.handleProblem(problem);
				}
			}
		}
	}
	
	public static CommonTree parseRuleString(String projectName, String contents, IProblemHandler collector) {
		return parseRuleString(projectName, contents, collector, false);
	}
	
	public static CommonTree parseRuleString(String projectName, String contents, IProblemHandler collector, boolean sharedElement) {
		try {
			Date start = new Date();
			if (collector == null) {
				collector = new DefaultProblemHandler();
			}
			RulesParser parser = initParser(contents, collector);
			if (sharedElement) {
				parser.setRuleElement(IndexFactory.eINSTANCE.createSharedRuleElement());
			}
			ParserRuleReturnScope startRule = parser.startRule();
			Date end = new Date();
			printDebug("Parse finished in "+(end.getTime() - start.getTime()));
			if (startRule != null && startRule.getTree() != null) {
				RulesASTNode tree = (RulesASTNode) startRule.getTree();
				populateASTData(projectName, parser, tree, collector, true);
				return tree;
			} else {
                printDebug("Return value is null");
			}
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static CommonTree parseExpressionString(String projectName, String contents, IProblemHandler collector, boolean sharedElement) {
		try {
			Date start = new Date();
			if (collector == null) {
				collector = new DefaultProblemHandler();
			}
			RulesParser parser = initParser(contents, collector);
			if (sharedElement) {
				parser.setRuleElement(IndexFactory.eINSTANCE.createSharedRuleElement());
			}
			ParserRuleReturnScope expression = parser.expression();
			Date end = new Date();
			printDebug("Parse finished in "+(end.getTime() - start.getTime()));
			if (parser.getTokenStream().size() > ((CommonTokenStream)parser.getTokenStream()).index()) {
				collector.getHandledProblems().add(new RulesSyntaxProblem("Additional tokens available", null));
			}
			if (expression != null && expression.getTree() != null) {
				RulesASTNode tree = (RulesASTNode) expression.getTree();
				populateASTData(projectName, parser, tree, collector, true);
				return tree;
			} else {
                printDebug("Return value is null");
			}
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static CommonTree parseReturnTypeString(String projectName, String contents, IProblemHandler collector, boolean sharedElement) {
		try {
			Date start = new Date();
			if (collector == null) {
				collector = new DefaultProblemHandler();
			}
			RulesParser parser = initParser(contents, collector);
			if (sharedElement) {
				parser.setRuleElement(IndexFactory.eINSTANCE.createSharedRuleElement());
			}
			ParserRuleReturnScope returnType = parser.returnType();
			Date end = new Date();
			printDebug("Parse finished in "+(end.getTime() - start.getTime()));
			if (returnType != null && returnType.getTree() != null) {
				RulesASTNode tree = (RulesASTNode) returnType.getTree();
				populateASTData(projectName, parser, tree, collector, true);
				return tree;
			} else {
                printDebug("Return value is null");
			}
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void populateASTData(String projectName, RulesParser parser,
			RulesASTNode tree, IProblemHandler handler, boolean includeTokens) {
		parser.getRuleElement().setIndexName(projectName);
		tree.setData("element", parser.getRuleElement());
		if (includeTokens) {
			tree.setData("tokens", parser.getTokenStream());
		}
		if (parser.getHeaderNode() != null) {
			tree.setData("HEADER", parser.getHeaderNode());
		}
		if (handler != null) {
			tree.setMalformed(handler.hasProblems());
		}
	}

	private static RulesParser initParser(String contents,
			IProblemHandler collector) {
		RulesLexer lexer = new RulesLexer(new ANTLRStringStream(contents));
		lexer.setProblemHandler(collector);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		RulesParser parser = new RulesParser(tokens);
		parser.setTreeAdaptor(new RulesTreeAdaptor());
		parser.addProblemCollector(collector);
		return parser;
	}

	public static CommonTree parseRuleFile(String projectName, File file, boolean includeTokensInTree) {
		return parseRuleFile(projectName, file, null, includeTokensInTree);
	}
	
	public static CommonTree parseRuleInputStream(String projectName, InputStream is, boolean includeTokensInTree) {
		return parseRuleInputStream(projectName, is, null, includeTokensInTree, false);
	}
	
	
	public static CommonTree parseRuleFile(String projectName, File file, IProblemHandler collector, boolean includeTokensInTree) {
		return parseRuleFile(projectName, file.getAbsolutePath(), collector, includeTokensInTree);
	}

	public static RulesASTNode parseName(String name) {
		try {
			Date start = new Date();
			DefaultProblemHandler collector = new DefaultProblemHandler();
			RulesParser parser = initParser(name, collector);
			ParserRuleReturnScope nameReturn = parser.name(-1);
			Date end = new Date();
			printDebug("Parse finished in "+(end.getTime() - start.getTime()));
			if (nameReturn != null && nameReturn.getTree() != null) {
				RulesASTNode tree = (RulesASTNode) nameReturn.getTree();
				return tree;
			} else {
                printDebug("Could not parse name:" + name);
			}
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static int countLines(String text) {
		String[] lines = text.split("\n");
		return 0;
	}

	public static int getCodeBlockLineOffset(RulesASTNode rulesAST, int blockType,boolean isEndOfBlock) {
		if (rulesAST == null || rulesAST.getFirstChild() == null) {
			return 0;
		}
		List<RulesASTNode> children = rulesAST.getFirstChild().getChildrenByFeatureId(RulesParser.BLOCKS);
		if (children == null) {
			return -1;
		}
		for (RulesASTNode node : children) {
			if (node.getType() == blockType) {
				RulesASTNode blockNode = node.getFirstChildWithType(RulesParser.BLOCK);
//				Tree rootNode = getRootNode(blockNode);
				return calculateBlockLineOffSet(rulesAST,blockNode,isEndOfBlock);
//				List<RulesASTNode> list = blockNode.getChildrenByFeatureId(RulesParser.STATEMENTS);
//				if (list.size() > 0) {
//					if(isEndOfBlock) {
//						return list.get(list.size()-1).getLine();
//					}else {
//						return list.get(0).getLine();
//					}
//				}
//				return node.getLine();
			}
		}
		return -1;
	}
	
	private static int calculateBlockLineOffSet(RulesASTNode rootNode,
			RulesASTNode node, boolean isEndOfBlock) {
		CommonTokenStream tokens = (CommonTokenStream) rootNode.getData("tokens");
		if (tokens == null || node == null) {
			return -1;
		}
		int tokenStartIndex = node.getTokenStartIndex();
		int tokenStopIndex = node.getTokenStopIndex();

		if (tokenStartIndex == -1) {
			// this is an 'invisible' node, get the text based on the child nodes
			RulesASTNode startNode = node.getFirstChild();
			RulesASTNode stopNode = node.getLastChild();
			tokenStartIndex = startNode != null ? startNode.getTokenStartIndex() : -1;
			tokenStopIndex = stopNode != null ? stopNode.getTokenStopIndex() : -1;
		}
		int startIndex = tokenStartIndex;
		int stopIndex = tokenStopIndex;
		
		if (!isEndOfBlock) { // start of block
			int startLine = tokens.get(tokenStartIndex).getLine();
			tokenStartIndex++; // starting brace {
			while (tokens.get(tokenStartIndex).getType() == RulesParser.WS && tokenStartIndex < tokenStopIndex) {
				if(tokens.get(tokenStartIndex).getLine() != startLine){
					break;
				}
				tokenStartIndex++;
			}
			return tokenStartIndex == tokenStopIndex ? tokens.get(startIndex).getLine() :tokens.get(tokenStartIndex).getLine();
		} else {
			int stopLine = tokens.get(tokenStopIndex).getLine();
			tokenStopIndex--; // ending brace  }
			while(tokens.get(tokenStopIndex).getType() == RulesParser.WS && tokenStopIndex > tokenStartIndex) {
				if(tokens.get(tokenStopIndex).getLine() != stopLine){
					break;
				}
				tokenStopIndex--;
			}
			return tokenStopIndex == tokenStartIndex ? tokens.get(stopIndex).getLine(): tokens.get(tokenStopIndex).getLine();
		}
	}

	private static Tree getRootNode(Tree node) {
		for(Tree n = node; n != null;) {
			if(n.getParent() != null) {
				n = n.getParent();
			} else {
				return n;
			}
		}
		return null;
	}

	public static int getCodeBlockOffset(RulesASTNode rulesAST, int blockType,boolean isEndOfBlock) {
		List<RulesASTNode> children = rulesAST.getFirstChild().getChildrenByFeatureId(RulesParser.BLOCKS);
		for (RulesASTNode node : children) {
			if (node.getType() == blockType) {
				RulesASTNode blockNode = node.getFirstChildWithType(RulesParser.BLOCK);
				List<RulesASTNode> list = blockNode.getChildrenByFeatureId(RulesParser.STATEMENTS);
				if (list.size() > 0) {
					if(isEndOfBlock) {
						return list.get(list.size()-1).getOffset();
					}else {
						return list.get(0).getOffset();
					}
				}
				return node.getLine();
			}
		}
		return -1;
	}
	
	public static CodeBlock calculateOffset(int blockType,String source) {
		if (source != null && !source.trim().equals("")) {
			ByteArrayInputStream bais;
			try {
				bais = new ByteArrayInputStream(source.getBytes(ModelUtils.DEFAULT_ENCODING));
				RulesASTNode tree = (RulesASTNode) CommonRulesParserManager.parseRuleInputStream(null, bais, true);
				int cstart = CommonRulesParserManager.getCodeBlockLineOffset(tree, blockType,false);
				int cend = CommonRulesParserManager.getCodeBlockLineOffset(tree, blockType,true);
				CodeBlockImpl cb = new CodeBlockImpl(cstart,cend);
				return cb;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}		
		return new CodeBlockImpl(-1,-1);	
	}

	
}
