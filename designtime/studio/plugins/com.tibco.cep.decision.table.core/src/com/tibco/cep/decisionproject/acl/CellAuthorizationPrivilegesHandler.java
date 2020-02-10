/**
 * 
 */
package com.tibco.cep.decisionproject.acl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.parser.RuleGrammarConstants;
import com.tibco.be.parser.Token;
import com.tibco.be.parser.tree.Node;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.security.authz.permissions.actions.ActionsFactory;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.util.SecurityUtil;
import com.tibco.util.StringUtilities;

/**
 * @author aathalye
 *
 */
public class CellAuthorizationPrivilegesHandler {
	
	private Collection<ValidationError> errors;

	private List<Role> roles;
	
	private static FunctionsCatalog functionsRegistry;
	
	private Map<String, String> aliasToResourceRegistry = new HashMap<String, String>();
	
	private Ontology ontology;
	
	static {
		try {
			functionsRegistry = FunctionsCatalog.getINSTANCE();
		} catch (Exception e) {
			throw new RuntimeException("Function Registry could not be initialized");
		}
	}
	private Table table; //The table for which this handler is used

		
	private List<RootNode> allNodes;
	
	private boolean isCondition;//Whether this is condition or action
	
	public CellAuthorizationPrivilegesHandler(final Table table,
			                                  final Ontology ontology,
			                                  final List<Role> roles,
											  final List<RootNode> allNodes,
											  boolean isCondition,
			                                  Collection<ValidationError> errors) {
		this.table = table;
		this.roles = roles;
		this.allNodes = allNodes;
		if (errors == null) {
			errors = new ArrayList<ValidationError>(1);
		}
		this.isCondition = isCondition;
		this.ontology = ontology;
		this.errors = errors;
		buildBaseAliases();
	}
	
	/**
	 * Build a registry of base aliases coming from the RF declaration
	 */
	private void buildBaseAliases() {
		List<Argument> arguments = table.getArgument();
		for (Argument arg : arguments) {
			ArgumentProperty argProperty = arg.getProperty();
			//Get alias
			String argAlias = argProperty.getAlias();
			aliasToResourceRegistry.put(argAlias, argProperty.getPath());
		}
	}
	
	/**
	 * Run authorization check on the concerned cell's contents.
	 * <p>
	 * This checks for property modification privileges for standard
	 * columns, as well as custom ones.
	 * </p>
	 * <p>
	 * This covers catalog functions also
	 * </p>
	 */
	public void authorize() {
		//Build a linked structure of all nodes
		List<List<Token>> allTokens = buildTokens();
		
		for (List<Token> tokens : allTokens) {
			traverseTokens(tokens);
		}
	}
	
	/**
	 * Traverses the {@linkplain List} of tokens and parses
	 * each token and checks the access privileges of the combination
	 * @param tokens
	 */
	private void traverseTokens(final List<Token> tokens) {
		//Traverse all tokens
		String path = null;
		
		List<String> fnStack = new ArrayList<String>();
		for (Token token : tokens) {
			int kind = token.kind;
			String tokenName = token.image;
			
			switch (kind) {
			case RuleGrammarConstants.IDENTIFIER: {
				
				if (path != null) {
					if (isIdentifierAChild(path, tokenName)) {
						//Check whether this identifier can combine with the path
						StringBuilder sb = new StringBuilder(path);
						sb.append("/");
						sb.append(tokenName);
						//If path has been resolved, then we may have the property
						//Depending on where we are accessing use ACL
						checkPropertyAccess(sb.toString());
						return;
					}
				}
				//Check if this is an alias
				path = resolveAlias(tokenName);
				if (path == null) {
					//This is not resolvable to an argument. It could be a function
					fnStack.add(tokenName);
				} 
				break;
			}
			case RuleGrammarConstants.DOT: {
				//If path is not null, we may have found a property
				//else we may be working with a function
				if (path == null) {
					//This could be a BE entity like a concept
					//example A.B.Concept
					fnStack.add(tokenName);
				} 
				break;
			}
			case RuleGrammarConstants.LPAREN: {
				//Convert to full String
				String fnName = StringUtilities.join(fnStack.toArray(new String[0]), "");
				//Check if this exists
				Object function = functionsRegistry.lookup(fnName, true);
				
				if (function != null) {
					//This is a function for sure
					//Call function acl
					checkFunctionAccess(fnName);
				} 
				break;
			}
			case RuleGrammarConstants.ASSIGN: {
				fnStack.size();
				//Check if the LHS type resolves to a resource
				resolveResource(fnStack);
				break;
			}
			case RuleGrammarConstants.SEMICOLON: {
				//Null out previous statement context
				path = null;
				//There may not be an RHS
				resolveResource(fnStack);
				break;
			}
			}
		}
	}
	
	/**
	 * Check if this identifier which could be a property name
	 * is a child of the resource specified by path = parentPath
	 * @param parentPath
	 * @param identifier
	 * @return
	 */
	private boolean isIdentifierAChild(String parentPath, 
			                           String identifier) {
		//Check if the path is an entity
		Entity entity = ontology.getEntity(parentPath);
		if (entity == null) {
			return false;
		}
		//This could be concept/event
		if (entity instanceof com.tibco.cep.designtime.model.element.Concept) {
			com.tibco.cep.designtime.model.element.Concept concept = 
				(com.tibco.cep.designtime.model.element.Concept)entity;
			if (concept.getPropertyDefinition(identifier, false) != null) {
				//This contains it
				return true;
			}
		}
		if (entity instanceof com.tibco.cep.designtime.model.event.Event) {
			com.tibco.cep.designtime.model.event.Event event = 
				(com.tibco.cep.designtime.model.event.Event)entity;
			if (event.getPropertyDefinition(identifier, false) != null) {
				//This contains it
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Tries to resolve fully qualified package prefixed name
	 * of a resource to equivalent BE type
	 * @param tokenStack
	 */
	private void resolveResource(List<String> tokenStack) {
		//Add / in start
		StringBuilder sb = new StringBuilder("/");
		//Check for tokens with .
		int size = tokenStack.size();
		if (size == 0) {
			return;
		}
		String alias = null;
		for (int counter = 0; counter < size; counter++) {
			if (counter < size - 1) {
				sb.append(tokenStack.get(counter));
			}
		}
		alias = tokenStack.get(size - 1);
		//Get the path a.b.c
		String unmassgedPath = sb.toString();
		
		String massagedPath = unmassgedPath.replaceAll("\\.", "/");
		//Check if this is an ontology resource
		Entity entity = ontology.getEntity(massagedPath);
		//Add this to the map
		if (entity != null && !aliasToResourceRegistry.containsKey(alias)) {
			aliasToResourceRegistry.put(alias, massagedPath);
		}
	}
	
		
	/**
	 * Check to see if the function specified by fnName
	 * is accessible to user for invoke.
	 * @param fnStack
	 */
	private void checkFunctionAccess(String fnName) {
		if (fnName != null) {
//			TRACE.logDebug(CLASS,
//					     "Function name under consideration {0}", 
//					      fnName);
			// Apply ACL on this function
			IAction action = ActionsFactory.getAction(
					ResourceType.CATALOGFUNCTION, "invoke");
			Permit permit = SecurityUtil.checkACL(fnName,
					ResourceType.CATALOGFUNCTION, roles, action);
			if (Permit.DENY.equals(permit)) {
				ValidationError vError = new ValidationErrorImpl(
						ValidationError.ERROR_FN_ACCESS_DENIED,
						"Invoke privilege denied for this function " + fnName, table.getFolder() + table.getName());
				if (!errors.contains(vError)) {
					errors.add(vError);
				}
			}
		}
	}
	
	
	/**
	 * Check to see if the property specified by resourcePath
	 * is readable/modifiable depending on whether used in condition/action.
	 * @param resourcePath
	 */
	private void checkPropertyAccess(String resourcePath) {
		if (resourcePath != null) {
			// Apply ACL on this property
			String operation = (isCondition) ? "read" : "modify";
			IAction action = ActionsFactory.getAction(
					ResourceType.PROPERTY, operation);
			Permit permit = SecurityUtil.checkACL(resourcePath,
					ResourceType.PROPERTY, roles, action);
			if (Permit.DENY.equals(permit)) {
				ValidationError vError = new ValidationErrorImpl(
						ValidationError.ERROR_FN_ACCESS_DENIED,
						operation + " privilege denied for this resource " + resourcePath, table.getFolder() + table.getName());
				if (!errors.contains(vError)) {
					errors.add(vError);
				}
			}
		}
	}
	
	/**
	 * Checks to see whether this alias resolves to a resource path
	 * @param identifier
	 * @return
	 */
	private String resolveAlias(String identifier) {
		//Check if this exists in map
		String path = aliasToResourceRegistry.get(identifier);
		if (path != null) {
			return path;
		}
		return null;
	}
	
	/**
	 * @return
	 */
	private List<List<Token>> buildTokens() {
		List<List<Token>> allTokens = new ArrayList<List<Token>>();
		for (Node rootNode : allNodes) {
			List<Token> tokens = buildTokens(rootNode);
			allTokens.add(tokens);
		}
		return allTokens;
	}
	
	/**
	 * @param rootNode
	 * @return
	 */
	private List<Token> buildTokens(Node rootNode) {
		List<Token> linkedList = new ArrayList<Token>();
		//Get start token
		Token token = rootNode.getFirstToken();
		linkedList.add(token);
		while (token.next != null) {
			token = token.next;
			linkedList.add(token);
		}
		return linkedList;
	}
}
