/**
 * 
 */
package com.tibco.cep.studio.core.rules;

import java.util.Arrays;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.rules.ast.HeaderASTNode;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.rules.text.HeaderASTRewriter;
import com.tibco.cep.studio.core.rules.text.RulesASTRewriter;



/**
 * @author aathalye
 *
 */
public class RuleGrammarUtils {
	
	public static final String NAME_SEPARATOR = ".";
	public static final String FOLDER_SEPARATOR = "/";
	
	public static final int FOLDER_FORMAT = 0;
	public static final int NAME_FORMAT = 1;
	
	public static final String TOKENS_DATA = "tokens";

	private static final String[] fGenericObjectTypes = new String[] { "Entity", "DateTime", "Object", "Event", "Concept", "ContainedConcept", "Exception",
		"String", "Double", "Long", "Integer", "Boolean", "TimeEvent", "SimpleEvent", "AdvisoryEvent" ,"Process" /* What is this? */ };

	static {
		Arrays.sort(fGenericObjectTypes);
	}

	public static String getSimpleNameFromNode(RulesASTNode nameNode) {
		if (nameNode == null) {
			return "";
		}
		if (nameNode.getType() == RulesParser.NAME) {
			return getSimpleNameFromNode(nameNode.getFirstChild());
		}
		if (nameNode.getType() != RulesParser.SIMPLE_NAME 
						&& nameNode.getType() != RulesParser.QUALIFIED_NAME) {
			return "";
		}
		if (nameNode.getType() == RulesParser.SIMPLE_NAME) {
			return nameNode.getFirstChild().getText();
		}
		RulesASTNode simpleNameNode = nameNode.getFirstChildWithType(RulesParser.SIMPLE_NAME);
		return getSimpleNameFromNode(simpleNameNode);
	}
	
	public static String getQualifierFromNode(RulesASTNode nameNode, int format) {
		if (nameNode == null) {
			return "";
		}
		if (nameNode.getType() == RulesParser.NAME) {
			return getQualifierFromNode(nameNode.getFirstChild(), format);
		}
		if (nameNode.getType() != RulesParser.QUALIFIED_NAME) {
			return "";
		}
		RulesASTNode qualifier = nameNode.getChildByFeatureId(RulesParser.QUALIFIER);
		return (format == FOLDER_FORMAT) ? FOLDER_SEPARATOR + getNameAsString(qualifier, format) + FOLDER_SEPARATOR : getNameAsString(qualifier, format);
	}
	
	public static String getFullNameFromNode(RulesASTNode nameNode, int format) {
		if (nameNode == null) {
			return "";
		}
		if (nameNode.getType() == RulesParser.NAME) {
			return getFullNameFromNode(nameNode.getFirstChild(), format);
		}
		if (nameNode.getType() == RulesParser.PRIMITIVE_TYPE) {
			return nameNode.getFirstChild() != null ? nameNode.getFirstChild().getText() : "";
		}
		if (nameNode.getType() != RulesParser.SIMPLE_NAME 
						&& nameNode.getType() != RulesParser.QUALIFIED_NAME) {
			return "";
		}
		RulesASTNode fullNameNode = getFullNameNode(nameNode);
		if (fullNameNode == null) {
			return "";
		}
		if (fullNameNode.getType() == RulesParser.QUALIFIED_NAME) {
			return getPartialNameFromNode(fullNameNode, format);
		}
		String name = getSimpleNameFromNode(fullNameNode);
		if (RuleGrammarUtils.isGenericType(name)) {
			return name;
		}
		return (format == FOLDER_FORMAT) ? FOLDER_SEPARATOR + name : name;
	}
	
	public static String getPartialNameFromNode(RulesASTNode nameNode, int format) {
		if (nameNode == null) {
			return "";
		}
		if (nameNode.getType() == RulesParser.NAME) {
			return getPartialNameFromNode(nameNode.getFirstChild(), format);
		}
		if (nameNode.getType() != RulesParser.SIMPLE_NAME 
						&& nameNode.getType() != RulesParser.QUALIFIED_NAME) {
			return "";
		}
		if (nameNode.getParent() != null && nameNode.getParent().getType() == RulesParser.QUALIFIED_NAME) {
			return getPartialNameFromNode((RulesASTNode) nameNode.getParent(), format);
		}
		return (format == FOLDER_FORMAT) ? FOLDER_SEPARATOR + getNameAsString(nameNode, format) : getNameAsString(nameNode, format);
	}

	public static String getNameAsString(RulesASTNode nameNode, int format) {
		if (nameNode == null) {
			return "";
		}
		String separator = NAME_SEPARATOR;
		if (format == FOLDER_FORMAT) {
			separator = FOLDER_SEPARATOR;
		}
		if (nameNode.getType() == RulesParser.PRIMITIVE_TYPE) {
			return nameNode.getFirstChild() != null ? nameNode.getFirstChild().getText() : "";
		}
		if (nameNode.getType() == RulesParser.QUALIFIED_NAME) {
			RulesASTNode qualifier = nameNode.getChildByFeatureId(RulesParser.QUALIFIER);
			RulesASTNode simpleName = nameNode.getFirstChildWithType(RulesParser.SIMPLE_NAME);
			return getNameAsString(qualifier, format) + separator + getSimpleNameFromNode(simpleName);
		}
		return getSimpleNameFromNode(nameNode);
	}

	public static RulesASTNode getFullNameNode(RulesASTNode node) {
		if (node == null) {
			return null;
		}
		if (isNamePart((RulesASTNode) node.getParent())) {
			return getFullNameNode((RulesASTNode) node.getParent());
		}
		if (isName(node)) {
			return node;
		}
		return null;
	}
	
	public static boolean isName(RulesASTNode node) {
		if (node == null) {
			return false;
		}
		return node.getType() == RulesParser.SIMPLE_NAME 
				|| node.getType() == RulesParser.QUALIFIED_NAME;
	}
	
	public static boolean isNamePart(RulesASTNode node) {
		if (node == null) {
			return false;
		}
		return isName(node) || node.getType() == RulesParser.QUALIFIER;
		
	}

	public static String getNodeText(CommonTokenStream tokens, RulesASTNode node) {
		return getNodeText(tokens, node, true, true);
	}
	
	public static String getNodeText(CommonTokenStream tokens, RulesASTNode node, boolean includeStartToken, boolean includeLastToken) {
		if (tokens == null || node == null) {
			return null;
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
		if (!includeStartToken) {
			tokenStartIndex++;
			while (tokens.get(tokenStartIndex).getType() == RulesParser.WS) {
				if ("\n".equals(tokens.get(tokenStartIndex).getText())) {
					tokenStartIndex++;
					break;
				}
				tokenStartIndex++;
			}
		}
		if (!includeLastToken) {
			tokenStopIndex--;
		}
		if (tokenStartIndex >= 0 && (tokenStopIndex > tokenStartIndex 
									|| (tokenStopIndex == tokenStartIndex && includeStartToken && includeLastToken))) {
			String text = tokens.toString(tokenStartIndex, tokenStopIndex);
			return text;
		}

		return null;
	}
	
	public static RulesASTNode getRootNode(RulesASTNode node) {
		while (node.getParent() != null) {
			node = (RulesASTNode) node.getParent();
		}
		return node;
	}

	public static RulesASTNode getMethodCallNode(RulesASTNode node) {
		RulesASTNode methodNode = node;
		if (methodNode.getType() == RulesParser.METHOD_CALL) {
			return methodNode.getChildByFeatureId(RulesParser.NAME);
		}
		while (methodNode.getParent() != null) {
			methodNode = (RulesASTNode) methodNode.getParent();
			if (methodNode.getType() == RulesParser.METHOD_CALL) {
				return methodNode.getChildByFeatureId(RulesParser.NAME);
			}
		}
		return methodNode;
	}
	
	public static EObject getElementReference(RulesASTNode node) {
		if (node == null) {
			return null;
		}
		RuleElement ruleElement = (RuleElement) RuleGrammarUtils.getRootNode(node).getData("element");
		if (ruleElement == null) {
			return null;
		}
		if (node.getType() == RulesParser.METHOD_CALL) {
			node = node.getChildByFeatureId(RulesParser.NAME);
		}
		if (node.getType() == RulesParser.QUALIFIED_NAME) {
			node = node.getFirstChildWithType(RulesParser.SIMPLE_NAME);
		}
		// convert the node to its corresponding ElementReference
		while (node.getType() != RulesParser.SIMPLE_NAME && node.getParent() != null) {
			node = (RulesASTNode) node.getParent();
		}
		
		if (node.getType() != RulesParser.SIMPLE_NAME) {
//			System.out.println("node is not part of a name node");
			return null;
		}
		EObject reference = getElementReference(node, ruleElement);
		return reference;
	}

	private static EObject getElementReference(RulesASTNode node,
			RuleElement ruleElement) {
		// need to find the element with the same offset/length
		// as the name node
		EList<GlobalVariableDef> defs = ruleElement.getGlobalVariables();
		for (GlobalVariableDef globalVariableDef : defs) {
			if (globalVariableDef.getOffset() == node.getOffset()
					&& globalVariableDef.getLength() == node.getLength()) {
//				System.out.println("Selection resolves to a global definition");
				return globalVariableDef;
			}
		}
		ScopeBlock scope = ruleElement.getScope();
		return getElementReference(node, scope);
	}
	
	private static EObject getElementReference(RulesASTNode node,
			ScopeBlock scope) {
		EList<ElementReference> refs = scope.getRefs();
		for (ElementReference reference : refs) {
			if (reference.getOffset() == node.getOffset()
					&& reference.getLength() == node.getLength()) {
				return reference;
			}
			while (reference.getQualifier() != null) {
				reference = reference.getQualifier();
				if (reference.getOffset() == node.getOffset()
						&& reference.getLength() == node.getLength()) {
					return reference;
				}
			}
		}
		EList<LocalVariableDef> defs = scope.getDefs();
		for (LocalVariableDef localVariableDef : defs) {
			if (localVariableDef.getOffset() == node.getOffset()
					&& localVariableDef.getLength() == node.getLength()) {
//				System.out.println("Selection resolves to a definition already");
				return localVariableDef;
			}
		}
		EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
		for (ScopeBlock scopeBlock : childScopeDefs) {
			EObject ref = getElementReference(node, scopeBlock);
			if (ref != null) {
				return ref;
			}
		}
		return null;
	}

	public static ScopeBlock getScope(ElementReference ref) {
		while (ref.eContainer() != null) {
			if (ref.eContainer() instanceof ScopeBlock) {
				return (ScopeBlock) ref.eContainer();
			}
			ref = (ElementReference) ref.eContainer();
		}
		return null;
	}

	public static String rewriteNode(RulesASTNode node) {
		if (node instanceof HeaderASTNode) {
			return rewriteHeaderNode(node);
		} 
		return rewriteRulesNode(node);
	}
	
	public static String rewriteHeaderNode(RulesASTNode node) {
		HeaderASTRewriter writer = new HeaderASTRewriter();
		node.accept(writer);
		return writer.getText();
	}
	
	public static String rewriteRulesNode(RulesASTNode node) {
		RulesASTRewriter writer = new RulesASTRewriter();
		node.accept(writer);
		return writer.getText();
	}
	
	public static RulesASTNode createName(String name) {
		RulesASTNode nameNode = CommonRulesParserManager.parseName(name);
		if (nameNode == null) {
			nameNode = new RulesASTNode(new CommonToken(RulesParser.SIMPLE_NAME, name));
			nameNode.addChild(new RulesASTNode(new CommonToken(RulesParser.Identifier, name)));
		}
		return nameNode;
	}

	public static boolean isGenericType(String name) {
		int result = Arrays.binarySearch(fGenericObjectTypes, name);
		if (result >= 0) {
			return true;
		}
		return false;
	}

	public static CommonTokenStream getTokens(RulesASTNode node) {
		RulesASTNode rootNode = getRootNode(node);
		return (CommonTokenStream) rootNode.getData(TOKENS_DATA);
	}

	public static ElementReference createElementReference(Path path) {
		ElementReference ref = IndexFactory.eINSTANCE.createElementReference();
		ref.setName(path.lastSegment());
		if (path.segmentCount() > 1) {
			ref.setQualifier(createElementReference(path.removeLastSegments(1)));
		}
		return ref;
	}
	
	public static String escapeSimpleString(String text) {
		if (text.length() > 1 && text.charAt(0) == '"') {
			text = text.substring(1, text.length()-1);
		}
		text = text.replaceAll("\"", "\\\\\"");
		return text;
	}
	
	public static String unescapeSimpleString(String text) {
		if (text.length() > 1 && text.charAt(0) == '"') {
			text = text.substring(1, text.length()-1);
		}
		text = text.replaceAll("\\\\\"", "\"");
		return text;
	}


}
