/**
 * 
 */
package com.tibco.cep.decisionproject.acl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.parser.tree.DeclarationNode;
import com.tibco.be.parser.tree.FunctionNode;
import com.tibco.be.parser.tree.NameNode;
import com.tibco.be.parser.tree.Node;
import com.tibco.be.parser.tree.NodeType;
import com.tibco.be.parser.tree.NodeVisitor;
import com.tibco.be.parser.tree.ProductionNode;
import com.tibco.be.parser.tree.ProductionNodeListNode;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.be.parser.tree.TemplatedDeclarationNode;
import com.tibco.be.parser.tree.TemplatedProductionNode;
import com.tibco.be.parser.tree.TypeNode;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.security.authz.permissions.actions.ActionsFactory;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.util.SecurityUtil;

/**
 * @author aathalye
 * 
 */
public class AuthorizableNodeVisitor implements NodeVisitor {

	private Collection<ValidationError> errors;

	private List<Role> roles;
	
	private AuthzCompiler compiler; //Keep this instance to access the symbol table
	
	private Table table; //The table for which this visitor is used


	public AuthorizableNodeVisitor(Collection<ValidationError> errors,
			final List<Role> roles) {
		if (roles == null) {
			throw new IllegalArgumentException("Illegal Input values");
		}
		if (errors == null) {
			errors = new ArrayList<ValidationError>(1);
		}
		this.errors = errors;
		this.roles = roles;
	}

	private void visitChildren(Node n) {
		for (Iterator<Node> it = n.getChildren(); it.hasNext();) {
			Node child = ((Node) it.next());
			if (child != null) {
				child.accept(this);
			}
		}
	}

	public Object visitFunctionNode(FunctionNode node) {
		NameNode fnNameNode = node.getName();
		if (fnNameNode != null) {
			String fnName = fnNameNode.toName();
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
		visitChildren(node);
		return null;
	}

	public Object visitProductionNode(ProductionNode node) {
		if (node != null) {
			//Get the relation type which will help us in determining type
			//of action requested
			
			NodeType nodeType = node.getType();
			int relationKind = node.getRelationKind();
//			TRACE.logDebug(CLASS, 
//				      "Relation kind {0}", relationKind);
			ResourceType resourceType = null;
			if (nodeType != null) {
				if (nodeType.isConcept()) {
//					TRACE.logInfo(CLASS, "Found a concept in production node");
					resourceType = ResourceType.CONCEPT;
				}
				if (nodeType.isEvent()) {
//					TRACE.logInfo(CLASS, "Found an event in production node");
					resourceType = ResourceType.EVENT;
				}
				if (nodeType.hasPropertyContext()) {
//					TRACE.logInfo(CLASS, 
//							"Found property in production node");
				}
				
//				TRACE.logDebug(CLASS, "Name of the production node {0}",
//						name);
				//handleBinaryRelation(node, errors);
				if ((relationKind & Node.NODE_NULL_RELATION) == Node.NODE_NULL_RELATION) {
					//Handle modification requests here
					//processNullRelation(node, errors);
				}
				if (resourceType != null) {
					//Check for read first, and then modify. 
					//How do we detect create?
					//performAClCheck(resourceType, name, "read", errors);
					
					if ((relationKind & Node.NODE_BINARY_RELATION) != 0) {
						//Handle modification requests here
						handleBinaryRelation(node, errors);
					}
						//performAClCheck(resourceType, name, "modify", errors);
				}
			}
			visitChildren(node);
		}
		return null;
	}

//	private void performAClCheck(final ResourceType resourceType,
//			                     final String resourceName,
//			                     final String action,
//			                     final Collection<ValidationError> errors) {
//		IAction iAction = ActionsFactory.getAction(
//				resourceType, action);
//		Permit permit = SecurityUtil.checkACL(resourceName,
//				resourceType, roles, iAction);
//		if (Permit.DENY.equals(permit)) {
//			errors
//					.add(new ValidationErrorImpl(
//							ErrorCode.createErrorCode(action),
//							action + " privilege denied for resource",
//							resourceName));
//		}
//	}

	public Object visitDeclarationNode(DeclarationNode node) {
		visitChildren(node);
        return null;
	}

	public Object visitRootNode(RootNode node) {
		visitChildren(node);
		return null;
	}

	public Object visitNameNode(NameNode node) {
//		TRACE.logDebug(CLASS, "Name of the named node {0}", node.toName());
		visitChildren(node);
		return null;
	}

	public Object visitTypeNode(TypeNode node) {
		/*if (node != null) {
			NodeType nodeType = node.getType();
			if (nodeType != null) {
				if (nodeType.isConcept()) {
					TRACE.logInfo(CLASS, "Found a concept in type node");
				}
				if (nodeType.isEvent()) {
					TRACE.logInfo(CLASS, "Found an event type node");
				}
				TRACE.logInfo(CLASS, "Name of the Type node {0}", nodeType
						.getName());
			}
			visitChildren(node);
		}*/
		visitChildren(node);
		return null;
	}

	public AuthorizableNodeVisitor() {
	}

	/**
	 * @return the errors
	 */
	public final Collection<ValidationError> getErrors() {
		return errors;
	}

	/**
	 * @param errors
	 *            the errors to set
	 */
	public final void setErrors(Collection<ValidationError> errors) {
		this.errors = errors;
	}

	/**
	 * @return the roles
	 */
	public final List<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public final void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	private void handleBinaryRelation(final ProductionNode pNode,
			                          final Collection<ValidationError> errors) {
		if (pNode == null) {
			return;
		}
		//final Token token = pNode.getToken();
		//Get operators on both sides
		Node lhs = pNode.getChild(0);
		if (lhs != null) {
//			TRACE.logDebug(CLASS, "LHS of operation {0}", lhs.getType().getName());
		}
		Node rhs = pNode.getChild(1);
		if (rhs != null) {
//			TRACE.logDebug(CLASS, "RHS of operation {0}", rhs.getType().getName());
		}
	}
	
//	private void handleLeftOperators(final Node lhs) {
//		Iterator<Node> lchildren = lhs.getChildren();
//		while (lchildren.hasNext()) {
//			Node child = lchildren.next();
//			//TRACE.logInfo("LHS node representation {0}", child.);
//			Token fTok = child.getFirstToken();
////			TRACE.logDebug("First Token obtained in LHS {0}", fTok.image);
//			Token lTok = child.getFirstToken();
////			TRACE.logDebug("Last Token obtained in LHS {0}", lTok.image);
//			child.accept(this);
//		}
//	}
	
	public Object visitProductionNodeListNode(ProductionNodeListNode node) {
        if(node.getListType() == 
        	ProductionNodeListNode.BLOCK_TYPE) compiler.getSymbolTable().pushScope();
        
        acceptNodes(node.getChildren());
        
        if(node.getListType() == 
        	ProductionNodeListNode.BLOCK_TYPE) compiler.getSymbolTable().popScope();
        node.setType(NodeType.VOID);
        return null;
    }
	
	private void acceptNodes(Iterator<Node> nodeIterator) {
        if (nodeIterator == null) return;
        while(nodeIterator.hasNext()) {
            Node next = (Node)nodeIterator.next();
            if(next != null) {
                next.accept(this);
            }
        }
    }
	
//	private void processNullRelation(ProductionNode node,
//			                         final Collection<ValidationError> errors) {
//        //process children before this node
//        //acceptNodes(node.getChildren());
//		//Get the symbol table
//		RLSymbolTable symbolTable = compiler.getSymbolTable();
//        Token token = node.getToken();
////        TRACE.logDebug(CLASS, "Kind of Token {0}", token.kind);
//        switch(token.kind) {
//            case RuleGrammarConstants.DECIMAL_INT_LITERAL:
//            case RuleGrammarConstants.HEX_INT_LITERAL:
//                //checkIntegerLiteralRange(token);
//                node.setType(new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false));
//                break;
//            case RuleGrammarConstants.DECIMAL_LONG_LITERAL:
//            case RuleGrammarConstants.HEX_LONG_LITERAL:
//                //checkIntegerLiteralRange(token);
//                node.setType(new NodeType(NodeTypeFlag.LONG_FLAG, TypeContext.PRIMITIVE_CONTEXT, false));
//                break;
//            case RuleGrammarConstants.DOUBLE_LITERAL:
//                node.setType(new NodeType(NodeTypeFlag.DOUBLE_FLAG, TypeContext.PRIMITIVE_CONTEXT, false));
//                break;
//            case RuleGrammarConstants.STRING_LITERAL:
//                node.setType(new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false));
//                break;
//            case RuleGrammarConstants.TRUE:
//            case RuleGrammarConstants.FALSE:
//                node.setType(new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, false));
//                break;
//            case RuleGrammarConstants.NULL:
//                node.setType(NodeType.NULL);
//                break;
//            case RuleGrammarConstants.IDENTIFIER:
//                //assert(false);
////                TRACE.logInfo(CLASS, "Null relation token name {0}", token.image);
//                NodeType type = symbolTable.getDeclaredIdentifierType(token.image);
//                if(type == null) type = NodeType.UNKNOWN;
//                //if(type.isUnknown()) reportError(CompileErrors.unknownIdentifier(token.image), token, node);
//                node.setType(type);
//                break;
//            case RuleGrammarConstants.LINE_STATEMENT:
//            case RuleGrammarConstants.BLOCK_STATEMENT:    
//                node.setType(NodeType.VOID);
//                break;
//            case RuleGrammarConstants.RETURN:
//                node.setType(NodeType.VOID);
//                break;
//            case RuleGrammarConstants.BREAK:
//            case RuleGrammarConstants.CONTINUE:
//                /*if(loopDepth <= 0) {
//                    reportError(CompileErrors.loopOnlyStatementNotExpected(token.image), token, node);
//                }*/
//                node.setType(NodeType.VOID);
//                break;
//            case RuleGrammarConstants.SEMICOLON:
//                node.setType(NodeType.VOID);
//                break;
//            case RuleGrammarConstants.ASSIGN:
//            	//Handle equal to
//            	Node lhs = node.getChild(0);
//            	Node rhs = node.getChild(1);
////            	TRACE.logDebug(CLASS, "LHS {0}", lhs);
//            	handleLeftOperators(lhs);
////            	TRACE.logDebug(CLASS, "RHS {0}", rhs);
//            	break;
//            default:
//                node.setType(NodeType.UNKNOWN);
//                //reportInternalError(CompileErrors.InternalErrors.unexpectedNullRelation(token.image), token);
//        }
//    }

	/**
	 * @param compiler the compiler to set
	 */
	public final void setCompiler(AuthzCompiler compiler) {
		this.compiler = compiler;
	}

	/**
	 * @param table the table to set
	 */
	public final void setTable(Table table) {
		this.table = table;
	}

	@Override
	public Object visitTemplatedDeclarationNode(TemplatedDeclarationNode node) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visitTemplatedProductionNode(TemplatedProductionNode node) {
		// TODO Auto-generated method stub
		return null;
	}
}
