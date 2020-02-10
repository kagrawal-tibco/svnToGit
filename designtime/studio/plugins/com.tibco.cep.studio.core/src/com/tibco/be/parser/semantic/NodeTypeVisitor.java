package com.tibco.be.parser.semantic;

import static com.tibco.be.parser.RuleGrammarConstants.ARRAY_ALLOCATOR;
import static com.tibco.be.parser.RuleGrammarConstants.ARRAY_LITERAL;
import static com.tibco.be.parser.RuleGrammarConstants.BANG;
import static com.tibco.be.parser.RuleGrammarConstants.BLOCK_STATEMENT;
import static com.tibco.be.parser.RuleGrammarConstants.BREAK;
import static com.tibco.be.parser.RuleGrammarConstants.CATCH;
import static com.tibco.be.parser.RuleGrammarConstants.CONTINUE;
import static com.tibco.be.parser.RuleGrammarConstants.DECIMAL_INT_LITERAL;
import static com.tibco.be.parser.RuleGrammarConstants.DECIMAL_LONG_LITERAL;
import static com.tibco.be.parser.RuleGrammarConstants.DECR;
import static com.tibco.be.parser.RuleGrammarConstants.DOUBLE_LITERAL;
import static com.tibco.be.parser.RuleGrammarConstants.EXPRESSION_NAME;
import static com.tibco.be.parser.RuleGrammarConstants.FALSE;
import static com.tibco.be.parser.RuleGrammarConstants.FINALLY;
import static com.tibco.be.parser.RuleGrammarConstants.FOR;
import static com.tibco.be.parser.RuleGrammarConstants.HEX_INT_LITERAL;
import static com.tibco.be.parser.RuleGrammarConstants.HEX_LONG_LITERAL;
import static com.tibco.be.parser.RuleGrammarConstants.IDENTIFIER;
import static com.tibco.be.parser.RuleGrammarConstants.IF;
import static com.tibco.be.parser.RuleGrammarConstants.INCR;
import static com.tibco.be.parser.RuleGrammarConstants.INSTANCEOF;
import static com.tibco.be.parser.RuleGrammarConstants.LINE_STATEMENT;
import static com.tibco.be.parser.RuleGrammarConstants.MINUS;
import static com.tibco.be.parser.RuleGrammarConstants.MISSING_LHS;
import static com.tibco.be.parser.RuleGrammarConstants.NULL;
import static com.tibco.be.parser.RuleGrammarConstants.OPEN_RANGE;
import static com.tibco.be.parser.RuleGrammarConstants.PLUS;
import static com.tibco.be.parser.RuleGrammarConstants.PLUSEQ;
import static com.tibco.be.parser.RuleGrammarConstants.POST_DECR;
import static com.tibco.be.parser.RuleGrammarConstants.POST_INCR;
import static com.tibco.be.parser.RuleGrammarConstants.RETURN;
import static com.tibco.be.parser.RuleGrammarConstants.SEMICOLON;
import static com.tibco.be.parser.RuleGrammarConstants.STRING_LITERAL;
import static com.tibco.be.parser.RuleGrammarConstants.THROW;
import static com.tibco.be.parser.RuleGrammarConstants.TRUE;
import static com.tibco.be.parser.RuleGrammarConstants.TRY;
import static com.tibco.be.parser.RuleGrammarConstants.WHILE;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.model.functions.PredicateWithXSLT;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.parser.CompileErrors;
import com.tibco.be.parser.MapperFunctionValidator;
import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.Token;
import com.tibco.be.parser.TokenUtils;
import com.tibco.be.parser.tree.BindingNode;
import com.tibco.be.parser.tree.DeclarationNode;
import com.tibco.be.parser.tree.FunctionNode;
import com.tibco.be.parser.tree.NameNode;
import com.tibco.be.parser.tree.Node;
import com.tibco.be.parser.tree.NodeType;
import com.tibco.be.parser.tree.NodeType.NodeTypeFlag;
import com.tibco.be.parser.tree.NodeType.TypeContext;
import com.tibco.be.parser.tree.NodeVisitor;
import com.tibco.be.parser.tree.ProductionNode;
import com.tibco.be.parser.tree.ProductionNodeListNode;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.be.parser.tree.SourceType;
import com.tibco.be.parser.tree.TemplatedDeclarationNode;
import com.tibco.be.parser.tree.TemplatedProductionNode;
import com.tibco.be.parser.tree.TypeNode;
import com.tibco.cep.studio.core.functions.annotations.ParamTypeInfo;
import com.tibco.cep.studio.core.functions.model.EMFModelJavaFunction;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jun 9, 2004
 * Time: 7:52:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeTypeVisitor implements NodeVisitor
{
    protected RLSymbolTable rlSymbolTable;
    protected ModelLookup modelLookup;
    protected FunctionLookup functionLookup;
    protected MapperFunctionValidator mapperValidator = null;
    
    protected ArrayList errorList = new ArrayList();
    //protected boolean inCondition = false;
    protected SourceType sourceType;
    //when loop depth is greater than 0, break and continue are allowed
    protected int loopDepth = 0;
    //used for comparisons with missing a LHS
    //needs to be the last LHS visited , assuming a left-first traversal
    //LHSs will only come from ProductionNodes 
    //with a binary arity and a comparison operator
    protected Node lastLHS = null;
    
    protected static final BigInteger MIN_LONG_MAGNITUDE = BigInteger.valueOf(Long.MIN_VALUE).negate();
    protected static final BigInteger MAX_LONG_MAGNITUDE = BigInteger.valueOf(Long.MAX_VALUE);
    protected static final BigInteger MIN_INT_MAGNITUDE = BigInteger.valueOf(Integer.MIN_VALUE).negate();
    protected static final BigInteger MAX_INT_MAGNITUDE = BigInteger.valueOf(Integer.MAX_VALUE);

    protected static final NodeType[] mapperFnArgs = {new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false)};
    
    public NodeTypeVisitor(RLSymbolTable symbolTable, ModelLookup modelLookup, FunctionLookup functionLookup) {
        this(symbolTable, modelLookup, functionLookup, null);    
    }
    public NodeTypeVisitor(RLSymbolTable symbolTable, ModelLookup modelLookup, FunctionLookup functionLookup, MapperFunctionValidator mapperValidator) {
        this.rlSymbolTable = symbolTable;
        this.modelLookup = modelLookup;
        this.functionLookup = functionLookup;
        this.mapperValidator = mapperValidator;
    }   
    
    public void populateNodeTypes(Iterator rootNodes) {
        clearErrors();
        rlSymbolTable.clearLocals();
        lastLHS = null;
        while(rootNodes.hasNext()) {
            RootNode rootNode = (RootNode)rootNodes.next();
            rootNode.accept(this);
        }
    }
    
    public Object visitTypeNode(TypeNode node) {
        node.setType(modelLookup.getTypeFromName(node.getTypeName().toName(), false, false, node.getArrayDimension()));
        return null;
    }
    
    public Object visitNameNode(NameNode node) {
        assert(false);
        return null;
    }
   
    public Object visitProductionNodeListNode(ProductionNodeListNode node) {
        if(node.getListType() == ProductionNodeListNode.BLOCK_TYPE) rlSymbolTable.pushScope();
        
        acceptNodes(node.getChildren());
        
        if(node.getListType() == ProductionNodeListNode.BLOCK_TYPE) rlSymbolTable.popScope();
        node.setType(NodeType.VOID);
        return null;
    }

    public Object visitRootNode(RootNode node) {
        sourceType = node.getSourceType();
        acceptNodes(node.getChildren());
        
        //rule conditions must be boolean expressions
        if(sourceType == SourceType.RULE_CONDITION || sourceType == SourceType.DT_CONDITION_CELL) {
            Node rhs = node.getChild(0);
            NodeType rhsType = rhs.getType();
            if(!(!rhsType.isArray() && (rhsType.isBoolean() || rhsType.isUnknown()))) {
                reportError(CompileErrors.incompatibleTypes("boolean", rhsType == null ? "NULL" : rhsType.getDisplayName()), findBeginToken(rhs), node);
            }
        }
        node.setType(NodeType.VOID);
        return null;
    }
    
    /*public void checkConditionExpression(RootNode node, String expectedTypeName) {
        node.accept(this);
        NodeType expectedType = modelLookup.getTypeFromName(expectedTypeName,  false, false, 0);
        assert(!expectedType.isUnknown());
        if(node.getChildCount() > 0) {
            NodeType expressionType = node.getChild(0).getType();
            assert(expressionType != null);
            if(expressionType != null) {
                if(!checkAssignmentTypeCompatibility(expectedType, expressionType)) {
                    reportError(CompileErrors.incompatibleTypes(expectedType.getName(), expressionType.getName()), node.getFirstToken(), node);
                }
            }
        }
    }*/

    public Object visitDeclarationNode(DeclarationNode node) {
        TypeNode typeNode = node.getDeclarationType();
        if(typeNode == null) {
            assert(false);
            return null;
        }
        
        NodeType declType = modelLookup.getTypeFromName(typeNode.getTypeName().toName(), true, false, typeNode.getArrayDimension());
        if(declType == null || declType.isUnknown()) {
            reportError(CompileErrors.declaredTypeUnknown(), findBeginToken(typeNode), typeNode); 
            node.setType(NodeType.UNKNOWN);
            return null;
        }

        return this.visitDeclarationNode(node, declType);
    }

    
    private Object visitDeclarationNode(
            DeclarationNode node,
            NodeType declType) {

        node.setType(declType);
        
        for(Iterator it = node.getDeclarations(); it.hasNext();) {
            ProductionNode declNode = (ProductionNode)it.next();
            Token idToken;
            String id;
            boolean hasInitializer = TokenUtils.isAssignOp(declNode.getToken()); 
            if(hasInitializer) {
                idToken = ((ProductionNode)declNode.getChild(0)).getToken();
                id = idToken.image;
            } else {
                idToken = declNode.getToken();
                id = idToken.image;
            }
            
            if(!(rlSymbolTable.getDeclaredIdentifierType(id).isUnknown())) {
                reportError(CompileErrors.identifierAlreadyDefined(id), idToken, idToken, idToken); 
                declNode.setType(NodeType.UNKNOWN);
                continue;
            }
            
            if(hasInitializer) {
                Node lhs = declNode.getChild(0);
                Node rhs = declNode.getChild(1);
                lhs.setType(declType);
                if(rhs instanceof ProductionNodeListNode) {
                    processArrayLiteralInitializer(declType, (ProductionNodeListNode)rhs);
                } else {
                    rhs.accept(this);
                }
                
                if(checkAssignment(declNode, lhs, rhs)) {
                    declNode.setType(NodeType.VOID);
                } else {
                    declNode.setType(NodeType.UNKNOWN);
                }
            }
            
            //do this after checking the initialization so that a newly declared variable
            //can't be used in its own initialization expression
            if ((node instanceof BindingNode) && (rlSymbolTable instanceof RuleTemplateSymbolTable)) {
                ((RuleTemplateSymbolTable) rlSymbolTable).addBindingType(id, declType);
            } else {
                rlSymbolTable.addLocalDeclaration(id, declType);
            }
        }
        return null;
    }


    @Override
    public Object visitTemplatedDeclarationNode(
            TemplatedDeclarationNode node) {

        node.setAccessibleSymbolNames(this.getAccessibleSymbolNames());
        
        switch (node.getMode()) {

            case CREATE:
                return this.visitDeclarationNode(node);

            case CALL:
                final Node child = node.getChild(0);
                if (child instanceof FunctionNode) {
                    this.visitFunctionNode((FunctionNode) child, false);
//                    child.accept(this);
                    return this.visitDeclarationNode(node, child.getType());
                }

            default:
                //todo: error
                return null;
        }
    }


    private List<String> getAccessibleSymbolNames() {
        final List<String> names = new ArrayList<String>();
        for (final Iterator i = this.rlSymbolTable.visibleIds(); i.hasNext(); ) {
            names.add((String) i.next());
        }
        return names;
    }


    protected void processArrayLiteralInitializer(NodeType declType, ProductionNodeListNode rhs) {
        acceptNodes(rhs.getChildren());
        
        if(!declType.isArray()) {
            reportError(CompileErrors.unexpectedArrayLiteral(), findBeginToken(rhs), rhs);
            return;
        }
        
        NodeType unitType = declType.getComponentType(true);
        for(Iterator it = rhs.getChildren(); it.hasNext();) {
            Node child = (Node)it.next();
            NodeType childType = child.getType();
            if(childType != null && !childType.isUnknown() && !checkAssignmentTypeCompatibility(unitType, child.getType())) {
                reportError(CompileErrors.incompatibleTypes(unitType.getDisplayName(), child.getType().getDisplayName()), findBeginToken(child), child);
            }
        }
        rhs.setType(declType);
    }


    public Object visitFunctionNode(
            FunctionNode node) {

        return this.visitFunctionNode(node, true);
    }


    private Object visitFunctionNode(
            FunctionNode node,
            boolean processArguments) {

        //ArrayList argTypes = new ArrayList();
        if (processArguments) {
            acceptNodes(node.getArgs());
        }
        
        NameNode nameNode = node.getName();
        String name;
        if(nameNode == null) {
            name = null;
        } else {
            name = nameNode.toName();
        }
        
        
        //NodeType[] nodeTypes = new NodeType[argTypes.size()];
        //argTypes.toArray(nodeTypes);
        
        //FunctionRec funcRec = functionLookup.lookupFunction(name, nodeTypes);
        FunctionRec funcRec = functionLookup.lookupFunction(name, null);
        node.setFunctionRec(funcRec);
        
        if(funcRec == null || funcRec.function == null) {
            node.setType(NodeType.UNKNOWN);
            reportError(CompileErrors.functionNotFound(name), findBeginToken(nameNode), node);
        } else if(throwsNonRuntimeException(funcRec)) {
            node.setType(NodeType.UNKNOWN);
            reportError(CompileErrors.exceptionNotAllowed(), findBeginToken(node), node);
        } else if (funcRec.returnType.isUnknown()) {
            node.setType(NodeType.UNKNOWN);
            if (node.getFunctionRec().function.getReturnClass() == null) {
            	if (node.getFunctionRec().function instanceof EMFModelJavaFunction) {
            		ParamTypeInfo retType = ((EMFModelJavaFunction)node.getFunctionRec().function).getFunctionInfo().getReturnType();
            		reportError(CompileErrors.noBETypeForFunctionReturnType(retType.getTypeClassName()), findBeginToken(node), node);
            	} else {
            		reportError(CompileErrors.noBETypeForFunctionReturnType("unknown"), findBeginToken(node), node);
            	}
            } else {
            	reportError(CompileErrors.noBETypeForFunctionReturnType(node.getFunctionRec().function.getReturnClass().getName()), findBeginToken(node), node);
            }
        } else {
            if(!funcRec.function.isValidInCondition() && 
                    (sourceType == SourceType.RULE_CONDITION || sourceType == SourceType.CONDITION_EXPRESSION || sourceType == SourceType.DT_CONDITION_CELL)) {
                node.setType(funcRec.returnType);
                reportError(CompileErrors.functionNotAllowedInCondition(name), findBeginToken(node), node);
            } else if(!funcRec.function.isValidInCondition() && (sourceType == SourceType.RULE_FUNCTION_CONDITION_OK)) {
                node.setType(funcRec.returnType);
                reportError(CompileErrors.functionNotAllowedInNonActionOnlyRuleFunction(name), findBeginToken(node), node);
            } else if(!funcRec.function.isValidInQuery() && sourceType == SourceType.RULE_FUNCTION_QUERY_OK) {
                node.setType(funcRec.returnType);
                reportError(CompileErrors.functionNotAllowedInQueryRuleFunction(name), findBeginToken(node), node);
            } else if(!funcRec.function.isValidInAction() && (sourceType == SourceType.RULE_ACTION || sourceType == SourceType.DT_ACTION_CELL)) {
                node.setType(funcRec.returnType);
                reportError(CompileErrors.functionNotAllowedInAction(name), findBeginToken(node), node);
            } else if(!funcRec.function.isValidInAction() && (sourceType.isFunction() || sourceType.isDT())) {
                node.setType(funcRec.returnType);
                reportError(CompileErrors.functionNotAllowedInRuleFunction(name), findBeginToken(node), node);
            } else if(processArguments && !checkFunctionArgs(node)) {
                node.setType(NodeType.UNKNOWN);
                //an error will be reported by checkFunctionArgs for the offending argument(s)
            } else {
                node.setType(funcRec.returnType);
            }
        }
        return null;
    }


    //call accept on all Nodes in nodeIterator
    protected void acceptNodes(Iterator nodeIterator) {
        if (nodeIterator == null) return;
        while(nodeIterator.hasNext()) {
            Node next = (Node)nodeIterator.next();
            if(next != null) {
                next.accept(this);
            }
        }
    }
    
    public Object visitProductionNode(ProductionNode node) {
        if(node == null) return null;

        if(node.getToken() == null) {
            //process children before reporting error
            acceptNodes(node.getChildren());
            reportInternalError(CompileErrors.InternalErrors.productionNodeNullToken());
            node.setType(NodeType.UNKNOWN);
            return null;
        }

        if(node.getRelationKind() == Node.NODE_NULL_RELATION) {
            processNullRelation(node);
        } else if(node.getRelationKind() == Node.NODE_UNARY_RELATION) {
            processUnaryRelation(node);
        } else if(node.getRelationKind() == Node.NODE_BINARY_RELATION) {
            processBinaryRelation(node);
        //note that an if statement can be either binary (handled above) or trinary (handled below)
        } else if(node.getRelationKind() > Node.NODE_BINARY_RELATION) {
            processGreaterThan2Relation(node);
        }
        return null;
    }

    public Object visitTemplatedProductionNode(
            TemplatedProductionNode node) {

        if (null == node) {
            return null;
        }

        node.setAccessibleSymbolNames(this.getAccessibleSymbolNames());

        switch (node.getMode()) {
            case MODIFY:
                this.acceptNodes(node.getChildren());
                node.setType(this.rlSymbolTable.getDeclaredIdentifierType(node.getChild(0).getFirstToken().image));
                return null;
            default:
                //todo: error
                return null;
        }
    }


    protected void processNullRelation(ProductionNode node) {
        //process children before this node
        acceptNodes(node.getChildren());
        Token token = node.getToken();
        
        switch(token.kind) {
            case DECIMAL_INT_LITERAL:
            case HEX_INT_LITERAL:
            case DECIMAL_LONG_LITERAL:
            case HEX_LONG_LITERAL:
            	// != null means it was already processed by the minus operator as a negative literal
            	if(node.getType() == null) processIntegerLiteral(node, token, false);
                break;
            case DOUBLE_LITERAL:
                node.setType(new NodeType(NodeTypeFlag.DOUBLE_FLAG, TypeContext.PRIMITIVE_CONTEXT, false));
                break;
            case STRING_LITERAL:
                node.setType(new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false));
                break;
            case TRUE:
            case FALSE:
                node.setType(new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, false));
                break;
            case NULL:
                node.setType(NodeType.NULL);
                break;
            case IDENTIFIER:
                assert(false);
                NodeType type = rlSymbolTable.getDeclaredIdentifierType(token.image);
                if(type == null) type = NodeType.UNKNOWN;
                if(type.isUnknown()) reportError(CompileErrors.unknownIdentifier(token.image), token, node);
                node.setType(type);
                break;
            case LINE_STATEMENT:
            case BLOCK_STATEMENT:    
                node.setType(NodeType.VOID);
                break;
            case RETURN:
                node.setType(NodeType.VOID);
                break;
            case BREAK:
            case CONTINUE:
                if(loopDepth <= 0) {
                    reportError(CompileErrors.loopOnlyStatementNotExpected(token.image), token, node);
                }
                node.setType(NodeType.VOID);
                break;
            case SEMICOLON:
                node.setType(NodeType.VOID);
                break;
            case OPEN_RANGE:
                node.setType(NodeType.VOID);
                break;
            default:
                node.setType(NodeType.UNKNOWN);
                reportInternalError(CompileErrors.InternalErrors.unexpectedNullRelation(token.image), token);
        }
    }
    
    protected void processUnaryRelation(ProductionNode node) {
        Token token = node.getToken();
        
        if(token.kind == EXPRESSION_NAME) { 
                processExpressionName(node);
                return;
        }

        if(token.kind == MISSING_LHS) {
            processMissingLHS(node);
            return;
        }
        
        //MAX_INT is one less than -MIN_INT
        //so the range check needs to know if it's negative 
        if(token.kind == MINUS) {
        	Node childNode = node.getChild(0);
        	if(childNode instanceof ProductionNode && childNode.getRelationKind() == Node.NODE_NULL_RELATION) {
        		ProductionNode child = (ProductionNode) childNode;
    			Token t = child.getToken();
    			if(t != null) {
    				switch(t.kind) {
        				case DECIMAL_INT_LITERAL:
        	            case HEX_INT_LITERAL:
        	            case DECIMAL_LONG_LITERAL:
        	            case HEX_LONG_LITERAL:
        	            	processIntegerLiteral(child, t, true);
        	            	break;
        			}
        		}
        	}
        } 
	    
        //process children before this node
        acceptNodes(node.getChildren());
        
        if(token.kind == TRY) {
            node.setType(NodeType.VOID);
            return;
        }

        if(token.kind == FINALLY) {
            node.setType(NodeType.VOID);
            return;
        }
        
        Node rhs = node.getChild(0);
        
        if(rhs == null) {
            reportInternalError(CompileErrors.InternalErrors.unaryRHSNull(), token);
            node.setType(NodeType.UNKNOWN);
            return;
        }
        
//        if(token.kind == RuleGrammarConstants.NO_OP) {
//            node.setType(rhs.getType());
//            return;
//        }
        
        //only ++ and -- will come here, others are binary ops
        if(TokenUtils.isCompoundAssignment(token)) {
            NodeType rhsType = rhs.getType();
            if(!rhsType.isMutable()) {
                reportError(CompileErrors.immutableAssignmentTarget(), token, node);
                node.setType(NodeType.UNKNOWN);
            }
        }
        
        if(!checkUnaryOpCompatibility(token, rhs)) {
            reportIncompatibleUnaryOpError(node, rhs);
            node.setType(NodeType.UNKNOWN);
        } else {
            node.setType(unaryResultType(token, rhs));
        }
    }
    
    protected void processMissingLHS(ProductionNode node) {
        if(NodeTransformation.transformMissingLHS(node, lastLHS)) { 
            node.accept(this);
        } else {
            //the RHS of the comparison
            Node rhs = node.getChild(0);
            NodeType rhsType = NodeType.UNKNOWN;
            if(rhs != null) {
                rhs.accept(this);
                rhsType = rhs.getType();
            }
            node.setType(NodeType.UNKNOWN);
            reportError(CompileErrors.binaryOpIncompatible(node.getToken().image, "<MISSING>", rhsType.getDisplayName()), node.getToken(), node.getToken(), node.getLastToken());
        }
    }
    
    protected void processExpressionName(ProductionNode node) {
        /*
        Split up the expression name into a type name (of a scorecard for example)
        or a variable name, and series of field accesses
        Name lookup order:
        Local / Scope variables and their properties
        Fully Specified static variable (Scorecard) accesses
        Shortcut names. (if only one type in the model has the name of the first identifier
            then the first identifier acts as an expression name for the scorecard of that type
        */
    
        NameNode nameNode = (NameNode)node.getChild(0);
        Token[] ids = nameNode.getIds();
        //try scope / locals first:
        //check if first identifier is the the scope
        NodeType type = rlSymbolTable.getDeclaredIdentifierType(ids[0].image);
        if (type.isUnknown() && (rlSymbolTable instanceof RuleTemplateSymbolTable)) {
            type = ((RuleTemplateSymbolTable) rlSymbolTable).getBindingType(ids[0].image);
        }
        for(int ii = 1; ii < ids.length && !type.isUnknown(); ii++) {
            //names only include . characters, so only need to try getPropertyType
            type = modelLookup.getPropertyType(type, ids[ii].image);
        }
        //if every identifier was successfully looked up, then this expression name is
        //a string of property access of the first identifier, ex. a.b.c where a is variable
        if(!type.isUnknown()) {
            convertExpressionNameToPropertyAccess(node, 1, type);
            return;
        }
        
        String name = "";
        int idCount;
        //the shortest package depth is favored so that a.b.c 
        //where c is a property of scorecard a.b 
        //is favored over the scorecard a.b.c
        for(idCount = 0; idCount < ids.length && (type.isUnknown() || !type.isScorecard()); idCount++) {
            if(idCount > 0) name+=".";
            name += ids[idCount];
            type = modelLookup.getTypeFromName(name, false, false, 0);
        }
        if(!type.isUnknown() && type.isScorecard()) {
            convertExpressionNameToPropertyAccess(node, idCount, type);
            return;
        }
        
        //use the first ID as a shortcut name
        type = modelLookup.getTypeFromName(ids[0].image, false, false, 0);
        for(int ii = 1; ii < ids.length && (type.isUnknown() || !type.isScorecard()); ii++) {
            //names only include . characters, so only need to try getPropertyType
            type = modelLookup.getPropertyType(type, ids[ii].image);
        }
        
        //if every identifier was successfully looked up, then this expression name is
        //a string of property access of the first identifier (a shortcut name for a scorecard)
        if(!type.isUnknown() && type.isScorecard()) {
            //in this case the node doesn't need to be converted 
            // to property access or it has been through this step previously
            convertExpressionNameToPropertyAccess(node, 1, type);
            return;
        }
        
        //For Decision Tables, an unknown _single_ identifier is treated as an unquoted string
        if(sourceType.isDT() && ids.length == 1) {
            node.getToken().image = '"' + nameNode.toName() + '"';
            node.setType(new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false));
            node.setRelation(0);
            node.removeFirstChild();
            return;
        }
        
        node.setType(NodeType.UNKNOWN);
        reportError(CompileErrors.couldntResolveName(nameNode.join(".")), findBeginToken(nameNode), nameNode);
    }
    
    protected void convertExpressionNameToPropertyAccess(ProductionNode node, int lastNameId, NodeType expNameType) {
        //if the entire name node is an expression name, nothing needs to change
        if(lastNameId == ((NameNode)node.getChild(0)).getIds().length) {
            node.setType(expNameType);
            return;
        }
        
        NameNode nameNode = (NameNode)node.removeFirstChild();
        Token[] newIds = new Token[lastNameId];
        Token[] newDots = new Token[lastNameId - 1];
        Token[] oldIds = nameNode.getIds();
        Token[] oldDots = nameNode.getDots();
        
        for(int ii = 0; ii < newIds.length; ii++) {
            newIds[ii] = oldIds[ii];
        }
        for(int ii = 0; ii < newDots.length; ii++) {
            newDots[ii] = oldDots[ii];
        }
        //take the expression name part of the old NameNode and make a new NameNode out of it;
        NameNode expNameNode = new NameNode(newIds, newDots);
        //add the expNameNode to a new EXPRESSION_NAME ProductionNode
        ProductionNode lhs = new ProductionNode(node.getToken(), Node.NODE_UNARY_RELATION);
        lhs.prependChild(expNameNode);
        
        //The ids that aren't part of expNameNode are property accesses of the
        //value named by expNameNode, so change the enclosing ProductionNode to reflect that
        for(int ii = lastNameId; ii < oldIds.length - 1; ii++) {
            lhs = addFieldAccess(lhs, oldDots[ii-1], oldIds[ii]);
        }
        //need to re-use the original production node for the final field access
        addFieldAccess(node, lhs, oldDots[oldDots.length-1], oldIds[oldIds.length-1]);
        
        //re-accept the node now that it has been modified
        node.accept(this);
    }
    
    protected ProductionNode addFieldAccess(ProductionNode outer, ProductionNode lhs, Token dot, Token rhs) {
        if(outer == null) {
            outer = new ProductionNode(dot, Node.NODE_BINARY_RELATION);
        } else {
            outer.setToken(dot);
            outer.setRelation(Node.NODE_BINARY_RELATION);
        }
        outer.prependChild(new ProductionNode(rhs, Node.NODE_NULL_RELATION));
        outer.prependChild(lhs);
        return outer;
    }
    protected ProductionNode addFieldAccess(ProductionNode lhs, Token dot, Token rhs) {
        return (addFieldAccess(null, lhs, dot, rhs));
    }
    
    protected void reportIncompatibleUnaryOpError(ProductionNode node, Node rhs) {
        Token token = node.getToken();
        NodeType rhsType = rhs.getType();
        switch(token.kind) {
            case RETURN:
                reportError(CompileErrors.incompatibleTypes(rlSymbolTable.getReturnType().getDisplayName(), rhsType == null ? "NULL" : rhsType.getDisplayName()), findBeginToken(rhs), rhs);
                break;
            case CATCH:
                Node errorNode = node.getChild(0);
                if(errorNode == null) errorNode = node;
                reportError(CompileErrors.unaryOpIncompatible(token.image, rhsType == null ? "NULL" : rhsType.getDisplayName()), token, errorNode);
            default:
                reportError(CompileErrors.unaryOpIncompatible(token.image, rhsType == null ? "NULL" : rhsType.getDisplayName()), token, node);
                break;
        }
    }
    
    protected void processBinaryRelation(ProductionNode node) {
        Token token = node.getToken();
        Node lhs = node.getChild(0);
        Node rhs = node.getChild(1);
        if(lhs == null || rhs == null) {
            if(lhs == null) reportInternalError(CompileErrors.InternalErrors.binaryLHSNull(token.image), token);
            if(rhs == null) reportInternalError(CompileErrors.InternalErrors.binaryRHSNull(token.image), token);
            node.setType(NodeType.UNKNOWN);
            return;
        }
        
        //this is a special case, since only the dot immediately following
        //a function call or a declared identifier is considered an operator, and because
        //the NamedNode on the RHS doesn't have a type.
        if(TokenUtils.isPropertyAccess(token)) {
            processPropertyOrAttributeAccess(node, false);
        } else if(TokenUtils.isAttributeAccess(token)) {
            processPropertyOrAttributeAccess(node, true);
        } else if(TokenUtils.isAssignOp(token) || TokenUtils.isCompoundAssignment(token)) {
            //process children before this node
            acceptNodes(node.getChildren());
            if(checkAssignment(node, lhs, rhs)) {
                node.setType(NodeType.VOID);
                if(TokenUtils.isCompoundAssignment(token)) {
                    if(processOtherBinaryOp(node, lhs, rhs, token)) {
                        //above call may change the type
                        //force assignments back to having void type
                        //if there was no error
                        //node.setType(NodeType.VOID);
                    }
                }
                //node.setType(lhs.getType());
            } else {
                node.setType(NodeType.UNKNOWN);
            }
        } else if(token.kind == IF) {
            processIf(node);
        } else if(token.kind == WHILE) {
            processWhile(node);
        } else if(token.kind == ARRAY_LITERAL) {
            processArrayLiteral(node);
        } else if(token.kind == ARRAY_ALLOCATOR) {
            processArrayAllocator(node);
        } else if(token.kind == INSTANCEOF) {
            processInstanceof(node);
        } else if(lhs instanceof ProductionNodeListNode || rhs instanceof ProductionNodeListNode){
            processDomainCondition(node);
        } else {
            processOtherBinaryOp(node, lhs, rhs, token);
        }

        //token may have been changed above
        token = node.getToken();
        //set lastLHS if this is a node that should provide a lastLHS
        if(TokenUtils.isComparisonOp(token) || token.kind == INSTANCEOF) {
            lastLHS = node.getChild(0);
        }
    }
    
    protected boolean processOtherBinaryOp(ProductionNode node, Node lhs, Node rhs, Token token) {
        //process children before this node
        acceptNodes(node.getChildren());
        if(checkBinaryOpCompatibility(token, lhs, rhs)) {
            node.setType(binaryResultType(token, lhs, rhs));
            return true;
        } else {
            node.setType(NodeType.UNKNOWN);
            NodeType lhsType = lhs.getType();
            NodeType rhsType = rhs.getType();
            reportError(CompileErrors.binaryOpIncompatible(token.image, lhsType == null ? null : lhsType.getDisplayName(), rhsType == null ? null : rhsType.getDisplayName()), token, node);
            return false;
        }
    }
    
    protected void processDomainCondition(ProductionNode node) {
        Node lhs = node.getChild(0);
        Node rhs = node.getChild(1);
        Token token = node.getToken();
        //can't compare two domain specs to each other i.e (1,2] == {1,2,3}
        if(lhs instanceof ProductionNodeListNode && rhs instanceof ProductionNodeListNode) {
            reportError(CompileErrors.binaryOpIncompatible(token.image, binaryOpTypeName(lhs), binaryOpTypeName(rhs)), token, node);
            node.setType(NodeType.UNKNOWN);
        } else if(lhs instanceof ProductionNodeListNode || rhs instanceof ProductionNodeListNode) {
            acceptNodes(node.getChildren());
            if(!checkDomainSpec(token, node)) {
                node.setType(NodeType.UNKNOWN);
            } else {
                NodeTransformation.transformDomainCondition(node);
                node.accept(this);
            }
        }
    }
    
    private String binaryOpTypeName(Node node) {
        NodeType type = node.getType();
        if(type != null) {
            if(node instanceof ProductionNodeListNode) {
                ProductionNodeListNode pnln = (ProductionNodeListNode)node;
                if(pnln.getListType() == ProductionNodeListNode.RANGE_TYPE) {
                    return CompileErrors.RangeType();
                } else if (pnln.getListType() == ProductionNodeListNode.SET_MEMBERSHIP_TYPE) {
                    return CompileErrors.SetMembershipType();
                }
            } else {
                return type.getDisplayName();
            }
        }
        return null;
    }
    
    protected void processInstanceof(ProductionNode node) {
        Node lhs = node.getChild(0);
        lhs.accept(this);
        NodeType lhsType = lhs.getType();
        
        TypeNode rhs = (TypeNode) node.getChild(1);
        rhs.accept(this);
        NodeType rhsType = rhs.getType();
        
        if(rhsType.isProcess() && !rhsType.isGenericProcess()) {
        	reportError(CompileErrors.processInstanceof(), findBeginToken(rhs), rhs);
        } else if(lhsType == null || lhsType.isUnknown()) {
            node.setType(NodeType.UNKNOWN);
        } else if(rhsType == null || rhsType.isUnknown()) {
            reportError(CompileErrors.couldntResolveName(rhs.toString()), findBeginToken(rhs), rhs);
            node.setType(NodeType.UNKNOWN);
        } else if(!(lhsType.isEntity() || lhsType.isObject() || lhsType.isString() || lhsType.isDateTime() 
                || lhsType.isArray() || lhsType.isException())) {
            reportError(CompileErrors.incompatibleInstanceofLHS(lhs.getType().getDisplayName()), node.getToken(), findBeginToken(lhs), node.getToken()); 
            node.setType(NodeType.UNKNOWN);
        } else if(!checkInstanceof(lhsType, rhsType)) {
            reportError(CompileErrors.instanceofNotPossible(lhsType.getDisplayName(), rhsType.getDisplayName()), node.getToken(), node);
            node.setType(NodeType.UNKNOWN);
        } else {
            node.setType(new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, false));
        }
    }
    
    protected boolean checkInstanceof(NodeType lhs, NodeType rhs) {
        if(lhs.isEqual(rhs)) return true;
        //typenames like PropertyArray aren't valid in the rules language even in the types are supported
        if(lhs.hasPropertyContext() && lhs.isArray()) return false;
        return modelLookup.isA(lhs, rhs) || modelLookup.isA(rhs, lhs);
    }
    
    protected void processArrayLiteral(ProductionNode node) {
        ////check if they tried to create a multi-dimensional array
        //if(node.getRelationKind() > Node.NODE_BINARY_RELATION) {
        //    reportError(CompileErrors.multiDimensionalArraysNotSupported(), findBeginToken(node.getChild(2)), node);
        //    return;
        //}
        TypeNode typeNode = (TypeNode)node.getChild(0);
        NodeType arrType = modelLookup.getTypeFromName(typeNode.getTypeName().toName(), false, false, typeNode.getArrayDimension());
        node.setType(arrType);
        
        if(arrType == null || arrType.isUnknown()) {
            reportError(CompileErrors.couldntResolveName(typeNode.toString()), findBeginToken(typeNode), typeNode);
            return;
        }
        
        Node secondNode = node.getChild(1);
        if(secondNode instanceof ProductionNodeListNode) {
            processArrayLiteralInitializer(arrType, (ProductionNodeListNode)secondNode);
        }
    }
    
    protected void processArrayAllocator(ProductionNode node) {
        TypeNode typeNode = (TypeNode)node.getChild(0);
        NodeType arrType = modelLookup.getTypeFromName(typeNode.getTypeName().toName(), false, false, typeNode.getArrayDimension());
        node.setType(arrType);
        
        if(arrType == null || arrType.isUnknown()) {
            reportError(CompileErrors.couldntResolveName(typeNode.toString()), findBeginToken(typeNode), typeNode);
            return;
        }
        
        Node lengthExpression = node.getChild(1);
        lengthExpression.accept(this);
        if(!(lengthExpression.getType().isNumber() && !lengthExpression.getType().isArray())){
            reportError(CompileErrors.incompatibleTypes("int", lengthExpression.getType().getDisplayName()), findBeginToken(lengthExpression), lengthExpression);
         }
    }
    
    protected void processGreaterThan2Relation(ProductionNode node) {
        Token token = node.getToken();
        switch(token.kind) {
        case IF:
            processIf(node);
            break;
        case FOR:
            processFor(node);
            break;
        case CATCH:
            processCatch(node);
            break;
        default:
            assert(false);
        //case RuleGrammarConstants.ARRAY_LITERAL:
        //    processArrayLiteral(node);
        //    break;
        }
    }
    
    protected void processCatch(ProductionNode node) {
        rlSymbolTable.pushScope();

        Iterator children = node.getChildren();
        TypeNode expTypeNode = (TypeNode)children.next();
        expTypeNode.accept(this);
        NodeType expType = modelLookup.getTypeFromName(expTypeNode.getTypeName().toName(), false, false, 0);
        
        ProductionNode expIdNode = null;
        if(children.hasNext()) {
            expIdNode = (ProductionNode)children.next();
            expIdNode.setType(expType);
        }
        //error for non-exception type
        if(expType == null || expType.isUnknown()) {
            reportError(CompileErrors.declaredTypeUnknown(), findBeginToken(expTypeNode), expTypeNode); 
        } else if(!expType.isException()) {
                reportError(CompileErrors.incompatibleTypes(RDFTypes.EXCEPTION.getName(), expTypeNode.getType().getDisplayName()), findBeginToken(expTypeNode), expTypeNode);
        } else if(expIdNode != null) {
            Token idToken = expIdNode.getToken();
            String id = idToken.image;

            if(!(rlSymbolTable.getDeclaredIdentifierType(id).isUnknown())) {
                reportError(CompileErrors.identifierAlreadyDefined(id), idToken, idToken, idToken);
                expIdNode.setType(NodeType.UNKNOWN);
            } else {
                rlSymbolTable.addLocalDeclaration(expIdNode.getToken().image, expType);
            }
        }
        
        acceptNodes(children);
        rlSymbolTable.popScope();
        node.setType(NodeType.VOID);
    }
    protected void processIf(ProductionNode node) {
        acceptNodes(node.getChildren());
        Node condition = node.getChild(0);
        if(!(condition.getType().isBoolean() || condition.getType().isUnknown())) {
            reportError(CompileErrors.incompatibleTypes("boolean", condition.getType().getDisplayName()), findBeginToken(condition), condition);
        }
        node.setType(NodeType.VOID);
    }
    protected void processWhile(ProductionNode node) {
        loopDepth++;
        acceptNodes(node.getChildren());
        loopDepth--;
        
        Node condition = node.getChild(0);
        if(!(condition.getType().isBoolean() || condition.getType().isUnknown())) {
            reportError(CompileErrors.incompatibleTypes("boolean", condition.getType().getDisplayName()), findBeginToken(condition), condition);
        }
        node.setType(NodeType.VOID);
    }
    protected void processFor(ProductionNode node) {
        loopDepth++;
        rlSymbolTable.pushScope();
        acceptNodes(node.getChildren());
        rlSymbolTable.popScope();
        loopDepth--;
        
        Node condition = null;
        for(Iterator it = node.getChildren(); it.hasNext();){
            if((condition instanceof ProductionNode) && ((ProductionNode)condition).getToken().kind == SEMICOLON) {
                condition = (Node)it.next();
                //if the condition is omitted this will be another semicolon
                //otherwise it will be an expression
                if(((ProductionNode)condition).getToken().kind == SEMICOLON) {
                    condition = null;
                }
                break;
            } else {
                condition = (Node)it.next();
            }
        }
        
        if(condition != null) {
            if(!(condition.getType().isBoolean() || condition.getType().isUnknown())) {
                reportError(CompileErrors.incompatibleTypes("boolean", condition.getType().getDisplayName()), findBeginToken(condition), condition);
            }
        }
        node.setType(NodeType.VOID);
    }
    
    protected void processPropertyOrAttributeAccess(ProductionNode node, boolean isAttribute) {
        //todo need to mark somehow that the property is derived from a declaration to disallow assignments to declaration properties in condition
        
        //LHS could be an identifier (ProductionNode) or a
        //function call (FunctionNode)
        Node LHS = node.getChild(0);
        ProductionNode RHS = (ProductionNode)node.getChild(1);
        
        if(LHS == null || RHS == null) {
            node.setType(NodeType.UNKNOWN);
            return;
        }
        
        //process LHS before trying to get its type
        //never accept RHS because it's a single identifier
        //and doesn't really have a type
        RHS.setType(NodeType.TEST_FLAG);
        LHS.accept(this);
        
        NodeType lhsType = LHS.getType();
        
        if(lhsType == null || lhsType.isUnknown()) {
            node.setType(NodeType.UNKNOWN);
            return;
        }
        
        //disallow access of properties of concept references 
        //and properties of attributes in the condition
        //or rule functions called from the condition
        if(sourceType == SourceType.RULE_CONDITION || sourceType == SourceType.CONDITION_EXPRESSION || sourceType.isNonActionOnlyRuleFunction()) {
            if(LHS instanceof ProductionNode) {
                ProductionNode pnLHS = (ProductionNode)LHS;
                boolean isLHSAttribute = TokenUtils.isAttributeAccess(pnLHS.getToken());
                if(TokenUtils.isPropertyAccess(node.getToken())) {
                    if(isLHSAttribute) {
                        if(sourceType == SourceType.RULE_CONDITION || sourceType == SourceType.CONDITION_EXPRESSION) {
                            reportError(CompileErrors.propertyOfAttributeInCondition(), node.getToken(), node.getToken(), RHS.getToken());
                        } else {
                            reportError(CompileErrors.propertyOfAttributeInNonActionOnlyRuleFunction(), node.getToken(), node.getToken(), RHS.getToken());
                        }
                    } else if(isConceptReference(lhsType)) {
                        if(sourceType == SourceType.RULE_CONDITION || sourceType == SourceType.CONDITION_EXPRESSION) {
                            reportError(CompileErrors.propertyOfConceptReferenceInCondition(), node.getToken(), node.getToken(), RHS.getToken());
                        } else {
                            reportError(CompileErrors.propertyOfConceptReferenceInNonActionOnlyRuleFunction(), node.getToken(), node.getToken(), RHS.getToken());
                        }
                    }
                } 
                //todo prohibit the specific case when this is unsafe
                //else if(isLHSAttribute) {
                //         if(sourceType == SourceType.RULE_CONDITION || sourceType == SourceType.CONDITION_EXPRESSION) {
                //            reportError(CompileErrors.attributeOfAttributeInCondition(), node.getToken(), node.getToken(), RHS.getToken());
                //         } else {
                //             reportError(CompileErrors.attributeOfAttributeInNonActionOnlyRuleFunction(), node.getToken(), node.getToken(), RHS.getToken()); 
                //         }
                //     }
                }
            }
        
        NodeType thisType = null;
        if(isAttribute) {
            thisType = modelLookup.getAttributeType(lhsType, RHS.getToken().image);
        } else { 
            thisType = modelLookup.getPropertyType(lhsType, RHS.getToken().image);
        }
        
        if(thisType == null || thisType.isUnknown()) {
            if(isAttribute) {
                reportError(CompileErrors.unknownAttribute(RHS.getToken().image, lhsType.getDisplayName()), RHS.getToken(), RHS.getToken(), RHS.getToken());
            } else {
                reportError(CompileErrors.unknownProperty(RHS.getToken().image, lhsType.getDisplayName()), RHS.getToken(), RHS.getToken(), RHS.getToken());
            }
            node.setType(NodeType.UNKNOWN);
            return;
        } else {
            node.setType(thisType);
        }
    }
    
    protected boolean checkUnaryOpCompatibility(Token op, Node rhs) {
        if(rhs == null || rhs.getType() == null || rhs.getType().isUnknown()) return true;
//        if(rhs.getType().isArray()) return false;
        NodeType rhsType = rhs.getType();
        switch(op.kind) {
            case PLUS:
            case MINUS:
            case INCR:
            case POST_INCR:
            case DECR:
            case POST_DECR:
                return rhsType.isObject() || (!rhsType.isArray() && rhsType.isNumber());
            case BANG:
                return !rhsType.isArray() && rhsType.isBoolean();
            case RETURN:
                return argIsCompatible(rlSymbolTable.getReturnType(), rhsType);
            case THROW:
                return !rhsType.isArray() && rhsType.isException();
            default:
                reportInternalError(CompileErrors.InternalErrors.unexpectedUnaryOperator(op.image), op);
                return false;
        }
    }
    
    protected boolean checkBinaryOpCompatibility(Token op, Node lhs, Node rhs) {
        NodeType lhsType = lhs.getType();
        NodeType rhsType = rhs.getType();
        
        if(lhsType == null || rhsType == null || lhsType.isUnknown() || rhsType.isUnknown()) return true;
    
        if(TokenUtils.isArrayAccess(op)) {
            return lhsType.isArray() && rhsType.isNumber() && !rhsType.isArray();
        }
        
        // null == null is considered a valid comparison here
        if(TokenUtils.isEqualityOp(op)) {
            //object is comparable to any type (non-reference types will be boxed).
            if(lhsType.isObject() || rhsType.isObject()) return true;
            //null and object are both comparable to any atom of a reference type, and arrays of all types
            if(rhsType.isNull() && !(lhsType.isValueType() && !lhsType.hasBoxedContext() && !lhsType.isArray())) return true;
            if(lhsType.isNull() && !(rhsType.isValueType() && !rhsType.hasBoxedContext() && !rhsType.isArray())) return true;
            
            if(lhsType.isArray() != rhsType.isArray()) return false;
            if(lhsType.isArray() && (lhsType.hasPropertyContext() != rhsType.hasPropertyContext())) return false;
            
            if(lhsType.isTypeEqual(rhsType)) return true;
            //autoconversion for numeric types doesn't work for arrays
            if(!lhsType.isArray() && lhsType.isNumber() && rhsType.isNumber()) return true;
            if(lhsType.isEntity() && rhsType.isEntity()) {
                return modelLookup.isA(lhsType, rhsType) || modelLookup.isA(rhsType, lhsType);
            }
            
            return false;
        }
       
        if(op.kind == PLUS) {
            if(lhsType.isString() && !lhsType.isArray()) {
                return !rhsType.isVoid();
            } else if(rhsType.isString() && !rhsType.isArray()) {
                return !lhsType.isVoid();
            }
        }
        
        if(op.kind == PLUSEQ) {
        	if(lhsType.isString() && !lhsType.isArray()) {
                return !rhsType.isVoid();
        	} else if(rhsType.isString() && !rhsType.isArray()) {
                return lhsType.isObject();
            }
        }

        //the following operators are not compatible with arrays
        if(lhsType.isArray() || rhsType.isArray()) return false;
        
        if(TokenUtils.isBinaryArithmeticOp(op) || TokenUtils.isCompoundAssignment(op)) {
            return 
                    lhsType.isNumber() && rhsType.isNumber()
                ||  lhsType.isNumber() && rhsType.isObject()
                ||  lhsType.isObject() && rhsType.isNumber();
        }
        
        //eq(==) and ne(!=) are also comparison ops but they are handled separately
        else if(TokenUtils.isComparisonOp(op)) {
            return 
            //should also allow comparison to null literal?
                (lhsType.isNumber() && (rhsType.isNumber() || rhsType.isObject()))
             || ((lhsType.isNumber() || lhsType.isObject()) && rhsType.isNumber())
             || (lhsType.isString() && (rhsType.isString() || rhsType.isObject()))
             || ((lhsType.isString() || lhsType.isObject()) && rhsType.isString())
             || (lhsType.isDateTime() && (rhsType.isDateTime() || rhsType.isObject()))
             || ((lhsType.isDateTime() || lhsType.isObject()) && rhsType.isDateTime());
        }
        else if(TokenUtils.isBinaryLogicalOp(op)) {
            return 
                    lhsType.isBoolean() && (rhsType.isBoolean() || rhsType.isObject())
                ||  (lhsType.isBoolean() ||lhsType.isObject()) && rhsType.isBoolean();
        }       
        reportInternalError(CompileErrors.InternalErrors.unexpectedBinaryOperator(op.image), op);
        return false;
    }
    
    protected boolean checkDomainSpec(Token op, Node node) {
        Node lhs = node.getChild(0);
        Node rhs = node.getChild(1);
        ProductionNodeListNode pnln = null;
        Node cmpNode = null;
        if(lhs instanceof ProductionNodeListNode) {
            pnln = (ProductionNodeListNode) lhs;
            cmpNode = rhs;
        } else {
            pnln = (ProductionNodeListNode) rhs;
            cmpNode = lhs;
        }
        
        boolean hasErrors = false;
        
        if(pnln.getListType() == ProductionNodeListNode.RANGE_TYPE) {
            //range only works for numeric types
            if(!cmpNode.getType().isNumber()) {
                reportError(CompileErrors.binaryOpIncompatible(op.image,
                        binaryOpTypeName(lhs),
                        binaryOpTypeName(rhs)), op, node);
                return false;
            }
            Node child = pnln.getChild(0);
            //lower bound
            if(child != null && child != NodeTransformation.OPEN_RANGE) {
                Token rangeOp = NodeTransformation.getRangeLowerOp(pnln);
                if(!checkBinaryOpCompatibility(op, lhs == cmpNode ? lhs : child, rhs == cmpNode ? rhs : child)) {
                    //don't pass the op token since it will be == or != and it will be confusing
                    //to highlight == with the error "> not compatible with int, String"
                    reportDomainConditionChildError(null, rangeOp, lhs, rhs, cmpNode, child);
                    hasErrors = true;
                }
            }
            child = pnln.getChild(1);
            //upperBoud
            if(child != null && child != NodeTransformation.OPEN_RANGE) {
                Token rangeOp = NodeTransformation.getRangeUpperOp(pnln);
                if(!checkBinaryOpCompatibility(op, lhs == cmpNode ? lhs : child, rhs == cmpNode ? rhs : child)) {
                    //don't pass the op token since it will be == or != and it will be confusing
                    //to highlight == with the error "> not compatible with int, String"
                    reportDomainConditionChildError(null, rangeOp, lhs, rhs, cmpNode, child);
                    hasErrors = true;
                }
            }
        }
        //set membership does equality operations
        else if(pnln.getListType() == ProductionNodeListNode.SET_MEMBERSHIP_TYPE) {
            for(Iterator it = pnln.getChildren(); it != null && it.hasNext();) {
                Node child = (Node)it.next();
                if(!checkBinaryOpCompatibility(op, lhs == cmpNode ? lhs : child, rhs == cmpNode ? rhs : child)) {
                    reportDomainConditionChildError(op, op, lhs, rhs, cmpNode, child);
                    hasErrors = true;
                }
            }
        }
        
        return !hasErrors;
    }
    
    private void reportDomainConditionChildError(Token eqOp, Token domainOp, Node lhs, Node rhs, Node cmpNode, Node child) {
        Token failure = eqOp;
        if(failure == null) {
            failure = findBeginToken(child);
        }
        reportError(CompileErrors.binaryOpIncompatible(domainOp.image,
                binaryOpTypeName(lhs == cmpNode ? lhs : child),
                binaryOpTypeName(rhs == cmpNode ? rhs : child)), failure, child);
    }
 
    protected boolean checkAssignment(ProductionNode node, Node lhs, Node rhs) {
        Token op = node.getToken();
        NodeType lhsType = lhs.getType();
        NodeType rhsType = rhs.getType();
        boolean result = false;
        
        if(lhsType == null || rhsType == null || lhsType.isUnknown() || rhsType.isUnknown()) return true;
        
        if(!lhsType.isMutable()) {
            reportError(CompileErrors.immutableAssignmentTarget(), op, node);
            return false;
        }
        
        //disallow assignments to properties of concepts, scorecards, etc in 
        //non-action-only RuleFunctions
        if(lhsType.hasPropertyContext() && sourceType.isNonActionOnlyRuleFunction()) {
            reportError(CompileErrors.propertyAssignmentNotAllowedInNonActionOnlyRuleFunction(), op, node);
            return false;
        }
        
        //can append anything to string with +=
        if(op.kind == PLUSEQ && lhsType.isString() && !lhsType.isArray()) return true;
        result = checkAssignmentTypeCompatibility(lhsType, rhsType);
        if(!result) {
            reportError(CompileErrors.assignmentTypeMismatch(lhsType.getDisplayName(), rhsType.getDisplayName()), op, node);
        }
        return result;
    }
        
    protected boolean convertibleToObject(boolean objectArray, NodeType typ) {
            //any type can be cast (and boxed) to Object
            if(!objectArray) return true;
            if(typ.isObject()) return true;
            if(typ.isNull()) return true;
            //PropertyXYZArray is a single object, not an array
            if(typ.hasPropertyContext()) return false;
            if(typ.isArray()) {
                //can't cast int[] to Object[]
                return !(typ.hasPrimitiveContext() && (typ.isNumber() || typ.isBoolean()));
            }
            return false;
    }
    
    protected boolean convertibleFromObject(boolean objectArray, NodeType typ) {
        //Object can be cast (and unboxed) to any type
        if(!objectArray) return true;
        if(typ.isObject()) return true;
        //PropertyXYZArray is a single object, not an array
        if(typ.hasPropertyContext()) return false;
        if(typ.isArray()) {
                //can't cast Object[] to int[]
            return !(typ.hasPrimitiveContext() && (typ.isNumber() || typ.isBoolean()));
            }
            return false;
        }
        
    protected boolean checkAssignmentTypeCompatibility(NodeType lhsType, NodeType rhsType) {
        boolean result = false;
        if(lhsType.isObject()) {
            return convertibleToObject(lhsType.isArray(), rhsType);
        }
        
        if(rhsType.isObject()) {
            return convertibleFromObject(rhsType.isArray(), lhsType);
        }
        
        if(rhsType.isNull()) {
            //any mutable array type can be set to null, as well as 
            // String, DateTime, Boxed types and Enities.
            //Standalone PropertyAtoms should also be allowed to be set to null although there is no way
            //to distiguish this since making an assignment to NodeType where
            //isArray is false and hasProeprtyContext is true is an assignment to that properties current value
            result = lhsType.isArray() || lhsType.isString() || lhsType.isDateTime() || lhsType.isEntity() || lhsType.hasBoxedContext() || lhsType.isException() || lhsType.isObject(); 
        }

        //primtive atoms and property atoms can be assigned to each other, but not so with arrays
        else if(lhsType.isArray() && rhsType.isArray() && (lhsType.hasPropertyContext() != rhsType.hasPropertyContext())) {
            result = false;
        }

        //generic property is compatible with arrays or non-arrays so this has to go before the next test
        else if(lhsType.isGenericProperty()) {
            result = rhsType.hasPropertyContext();
        }
        
        else if(lhsType.isArray() != rhsType.isArray()) {
            result = false;
        }
        
        else if(lhsType.isTypeEqual(rhsType)) {
            return true;
        }
        
        else if(lhsType.isGenericPropertyArray() || lhsType.isGenericPropertyAtom()) {
            //property / non-property and array / non-array compatibility was already checked above
            result = rhsType.hasPropertyContext();
        }
        
        else if(lhsType.isInt()) {
            //numerical conversion only applys for non-arrays
        	//assigning a promoted long literal to an integer should give an error so that users aren't surprised that 
        	//they get MAX_INT instead of the value they were expecting
            result = rhsType.isInt() || (!lhsType.isArray() && !rhsType.isPromotedLong() && (rhsType.isLong() || rhsType.isDouble()));
        }
        
        else if(lhsType.isDouble()) {
            //numerical conversion only applys for non-arrays
            result = rhsType.isDouble() || (!lhsType.isArray() && (rhsType.isLong() || rhsType.isInt()));
        }

        else if(lhsType.isLong()) {
            //numerical conversion only applys for non-arrays
            result = rhsType.isLong() || (!lhsType.isArray() && (rhsType.isInt() || rhsType.isDouble() ));
        }
        
        else if(lhsType.isEntity()) {
            result = modelLookup.isA(lhsType, rhsType) || modelLookup.isA(rhsType, lhsType);
        }
        
        /*
        if(lhsType.isContainedConcept()) {
        //todo need a more specific condition?
        return modelLookup.isA(lhsType, rhsType) || modelLookup.isA(rhsType, lhsType); 
        }
        if(lhsType.isConcept()) {
        return modelLookup.isA(lhsType, rhsType) || modelLookup.isA(rhsType, lhsType);
        }
        
        if(lhsType.isEvent()) {
        return modelLookup.isA(lhsType, rhsType) || modelLookup.isA(rhsType, lhsType);
        }
        */
        
        return result;
    }
 
    protected boolean checkFunctionArgs(FunctionNode node) {
        FunctionRec functionRec = node.getFunctionRec();
        boolean varArgs = functionRec.function.isVarargs();
        NodeType[] fnArgTypes;
        if(functionRec.isMapper) {
            fnArgTypes = mapperFnArgs;
        } else {
            fnArgTypes = functionRec.argTypes;
        }

        int userArgLen = 0;
        String fnArgsString;
        String userArgsString;
        String fnName = functionRec.function.getName().getNamespaceURI() + "." + functionRec.function.getName().getLocalName();

        {
            StringBuilder fnArgs = new StringBuilder ();
            //make error string for function argument types
            for(int ii = 0; ii < fnArgTypes.length; ii++) {
                NodeType argType = fnArgTypes[ii];
                if(ii > 0) fnArgs.append(", ");
                if(argType == null) {
                    fnArgs.append("null");
                } else if(varArgs && argType.isArray() && (ii == fnArgTypes.length - 1)) {
                    fnArgs.append(argType.getComponentType(false).getDisplayName());
                    fnArgs.append("...");
                } else {
                  fnArgs.append(argType.getDisplayName());
                }
            }
            fnArgsString = fnArgs.toString();
        }
        {
            StringBuilder userArgs = new StringBuilder ();
            Iterator userArgNodes = node.getArgs();
            //make error string for user argument types
            //and determine number of user arguments
            while(userArgNodes.hasNext()) {
                NodeType argType = ((Node)userArgNodes.next()).getType();
                if(userArgLen > 0) userArgs.append(", ");
                userArgs.append(argType == null ? "null" : argType.getDisplayName());
                userArgLen++;
            }
            userArgsString = userArgs.toString();
        }

        //with varargs user can skip the last arg for one less, or can provide many more args that what is declared
        if((userArgLen < fnArgTypes.length - 1) || (!varArgs && userArgLen != fnArgTypes.length)) {
            reportError(CompileErrors.functionSignatureMismatch(fnName, fnArgsString, userArgsString), findBeginToken(node.getName()), node);
            return false;
        } else {
            Iterator userArgNodes = node.getArgs();
            int varargsStart = Integer.MAX_VALUE;
            if(varArgs) varargsStart = fnArgTypes.length - 1;
            NodeType fnArgType = null;
            int fnArgIdx = 0;
            for(int ii = 0; userArgNodes.hasNext(); ii++) {
                Node userArgNode = (Node)userArgNodes.next();
                NodeType userArgType = userArgNode.getType();
                if(ii <= varargsStart) {
                    fnArgType = fnArgTypes[ii];
                    fnArgIdx = ii;
                    if(ii == varargsStart && fnArgType != null && userArgType != null) {
                        //if more than 1 args passed to varargs, always use component type for fnArgType
                    	//for single non-array arguments, use the component type
                    	//use original type (no change) for null arg and for arrays of compatible types
                        //use Object for arrays passed to an Object... parameter for consistency 
                    	//between passing ..., ObjArray1) and ..., ObjArray1, ObjArray2), 
                    	//otherwise adding the second arg would change how the first arg was passed
                        if(userArgNodes.hasNext() || fnArgType.isObject() || (!userArgNodes.hasNext() && !userArgType.isArray())) {
                            fnArgType = fnArgType.getComponentType(false);
                        }
                    }
                }
                //in this case, an unknown type means the java type of
                //this argument has no corresponding rules language type
                if(fnArgType == null || fnArgType.isUnknown()) {
                    reportError(CompileErrors.noBETypeForFunctionArgumentType(functionRec.function.getArguments()[fnArgIdx].getName()), findBeginToken(userArgNode), userArgNode);
                }
                if(!argIsCompatible(fnArgType, userArgType)) {
                    reportError(CompileErrors.functionSignatureMismatch(fnName, fnArgsString, userArgsString), findBeginToken(userArgNode), userArgNode);
                    return false;
                }
            }
        }
        
        //do additional mapper validation
        if(mapperValidator != null && functionRec.function instanceof PredicateWithXSLT) {
            //casting node.getArgs().next() to a ProductionNode is normally unsafe, but the XSLT string should always
            //be a single string literal
            String error = mapperValidator.validateXSLT((PredicateWithXSLT)functionRec.function, ((ProductionNode)node.getArgs().next()).getToken().image);
            if(mapperValidator.hasErrors() && error != null) {
                reportError(String.valueOf(error), findBeginToken(node.getName()), node);
                return false;
            }
        }
        
        return true;
    }

    //This method tries to support the Java sematics for method invocation conversion detailed here:
    //http://java.sun.com/docs/books/jls/second_edition/html/conversions.doc.html#12687
    //for example a long or int can be passed to a function requiring a double argument.
    protected boolean argIsCompatible(NodeType fnType, NodeType userType) {
        if(fnType.isUnknown()) return true;
        if(userType.isUnknown()) return true;
        if(fnType.isObject()) return convertibleToObject(fnType.isArray(), userType);
        if(userType.isObject()) return convertibleFromObject(userType.isArray(), fnType);
        
        if(userType.isNull()) {
            //all property object argument types accept null
            //this is true even though in the language no array properties may be set to null
            //and only non-array concept or string properties may be set to null
            return !(fnType.isValueType() && fnType.hasPrimitiveContext() && !fnType.isArray());
        }
        
        //a user supplied PropertyAtom can be coerced into a primitive atom for the function argument
        if((fnType.hasPropertyContext() != userType.hasPropertyContext()) && (userType.hasPropertyContext() && userType.isArray())) {
            return false;
        }

          //generic property is compatible with arrays or non-arrays so this has to go before the next test
        if(fnType.isGenericProperty()) {
            return true;
        }
        
        if(fnType.isArray() != userType.isArray()) {
            return false;
        }
        
        if(fnType.isTypeEqual(userType)) {
            return true;
        }
        
//        if(fnType.isObject() && (userType.isString() || userType.isEvent() || userType.isConcept() || userType.isLong() || userType.isInt())) {
//            return true;
//        }

        if(fnType.isGenericPropertyArray() || fnType.isGenericPropertyAtom()) {
            //property / non-property and array / non-array compatibility was already checked above
            return true;
        }
        
        /*
        if(fnType.isGenericConcept()) {
            return userType.isConcept() || userType.isGenericEntity();
        }
        if(fnType.isGenericConceptReference()) {
            return userType.isConcept() || userType.isGenericEntity();
        }
        if(fnType.isGenericContainedConcept()) {
            return userType.isContainedConcept() || (userType.isGeneric() && userType.isConcept()) || userType.isGenericEntity();
        }
        if(fnType.isGenericEvent()) {
            return userType.isEvent() || userType.isGenericEntity();
        }
        if(fnType.isGenericTimeEvent()) {
            return userType.isTimeEvent() || userType.isGenericEvent();
        }
        if(fnType.isGenericSimpleEvent()) {
            return userType.isSimpleEvent() || userType.isGenericEvent();
        }
        if(fnType.isGenericEntity()) {
            return userType.isEntity();
        }
        */
        
        if(fnType.isDouble()) {
            //numerical conversion only applies for primitive atoms
            return userType.isDouble() || (!fnType.isArray() && !fnType.hasPropertyContext() && (userType.isLong() || userType.isInt()));
        }
        if(fnType.isLong()) {
            //numerical conversion only applies for primitive atoms
            return userType.isLong() || (!fnType.isArray() && !fnType.hasPropertyContext() && (userType.isInt() || userType.isDouble()));
        }
        if(fnType.isInt()) {
            //numerical conversion only applies for primitive atoms
        	//assigning a promoted long literal to an integer should give an error so that users aren't surprised that
        	//the get MAX_INT instead of their value
            return userType.isInt() || (!fnType.isArray() && !fnType.hasPropertyContext() 
            		&& !userType.isPromotedLong() && (userType.isLong() || userType.isDouble()));
        }
        
        if(fnType.isContainedConcept()) {
            //todo need a more specific condition?
            return modelLookup.isA(fnType, userType) || modelLookup.isA(userType, fnType); 
        }
        if(fnType.isConcept()) {
            return modelLookup.isA(fnType, userType) || modelLookup.isA(userType, fnType);
        }
        
        if(fnType.isEvent()) {
            return modelLookup.isA(fnType, userType) || modelLookup.isA(userType, fnType);
        }

        //.isEntity is also true for concept, contained concept, event etc above.
        //therefore all tests for more specific entity types should come before this test.
        if(fnType.isEntity()) {
            return userType.isEntity();
        }
        
        return false;
    }
    
    protected boolean throwsNonRuntimeException(FunctionRec rec) {
        if(rec.thrownExceptions == null) return false;
        for(int ii = 0; ii < rec.thrownExceptions.length; ii++) {
            if(!(RuntimeException.class.isAssignableFrom(rec.thrownExceptions[ii]) || Error.class.isAssignableFrom(rec.thrownExceptions[ii]))) {
                return true;
            }
        }
        return false;
    }
    
    protected NodeType unaryResultType(Token op, Node rhs) {
        if(rhs == null || rhs.getType().isUnknown()) return NodeType.UNKNOWN;
        
        int kind = op.kind;
        NodeType type = rhs.getType();
        
        if(kind == PLUS || kind == MINUS) {
            return new NodeType(type, null, TypeContext.PRIMITIVE_CONTEXT, false, null, null, null);
        } else if(kind == BANG) {
            return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
        } else if(kind == RETURN) {
            return NodeType.VOID;
        } else if(kind == THROW) {
            return NodeType.VOID;
        } else if (kind == INCR || kind == DECR || kind == POST_INCR || kind == POST_DECR) {
            return NodeType.VOID;
        } else {
            reportInternalError(CompileErrors.InternalErrors.unexpectedUnaryOperator(op.image));
            return NodeType.UNKNOWN;
        }
    }
    
    protected NodeType binaryResultType(Token op, Node lhs, Node rhs) {
        NodeType lhsType = lhs.getType();
        NodeType rhsType = rhs.getType();
        
        if(lhsType == null || rhsType == null || lhsType.isUnknown() || rhsType.isUnknown()) return NodeType.UNKNOWN;
        
        if((op.kind == PLUS) && (lhsType.isString() || rhsType.isString())) {
            return new NodeType(NodeTypeFlag.STRING_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
        } else if(TokenUtils.isBinaryArithmeticOp(op)) {
            //lhs and rhs are both number types if execution reaches this part
            return binaryNumericPromotedType(lhsType, rhsType);
        } else if(TokenUtils.isComparisonOp(op) || TokenUtils.isBinaryLogicalOp(op)) {
            return new NodeType(NodeTypeFlag.BOOLEAN_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
        } else if(TokenUtils.isArrayAccess(op)) {
            NodeType componentType = lhsType.getComponentType(true);
            assert(!componentType.isUnknown());
            return componentType;
        } else if (TokenUtils.isCompoundAssignment(op)) {
            return NodeType.VOID;
        }
        // else if(TokenUtils.isAssignOp(op)) {
        //    return lhsType;
        //}
        reportInternalError(CompileErrors.InternalErrors.unexpectedBinaryOperator(op.image), op);
        return NodeType.UNKNOWN;
    }
    
    //these rules come from http://www.janeg.ca/scjp/oper/promotions.html
    //todo the resulting types will always be primitive?
    protected NodeType binaryNumericPromotedType(NodeType lhsType, NodeType rhsType) {
        if(lhsType.isDouble() || rhsType.isDouble()) return new NodeType(NodeTypeFlag.DOUBLE_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
        if(lhsType.isLong() || rhsType.isLong()) return new NodeType(NodeTypeFlag.LONG_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
        return new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false);
    }
    
    protected void processIntegerLiteral(ProductionNode node, Token t, boolean negative) {
    	try {
	        boolean isLong = false;
	        BigInteger val = null;
            switch(t.kind) {
	        	case DECIMAL_INT_LITERAL:
	        		val = new BigInteger(t.image);
            		break;
	            case DECIMAL_LONG_LITERAL:
	            	isLong = true;
	            	val = new BigInteger(t.image.substring(0, t.image.length() - 1));
	            	break;
            	case HEX_INT_LITERAL:
            		val = new BigInteger(t.image.substring(2), 16);
            		break;
		    	case HEX_LONG_LITERAL:
		    		isLong = true;
		    		val = new BigInteger(t.image.substring(2, t.image.length() - 1), 16);
		    		break;
            }

	        BigInteger intLimit;
	        BigInteger longLimit;
	        if(negative) {
	        	intLimit = MIN_INT_MAGNITUDE;
	        	longLimit = MIN_LONG_MAGNITUDE;
	        } else {
	        	intLimit = MAX_INT_MAGNITUDE;
	        	longLimit = MAX_LONG_MAGNITUDE;
	        }
            
            if(isLong) {
            	if(val.compareTo(longLimit) > 0) {
            		reportError(CompileErrors.longLiteralOutOfRange(t.image), t, t, t);
            	}
            	node.setType(new NodeType(NodeTypeFlag.LONG_FLAG, TypeContext.PRIMITIVE_CONTEXT, false));
            } else {
            	if(val.compareTo(intLimit) > 0) {
            		//promote integer literals without a trailing L to long if they will fit in a long
            		if(val.compareTo(longLimit) > 0) {
            			reportError(CompileErrors.longLiteralOutOfRange(t.image), t, t, t);
            		}
        			node.setType(new NodeType(NodeTypeFlag.LONG_PROMOTED_FLAG, TypeContext.PRIMITIVE_CONTEXT, false));
            	} else {
            		node.setType(new NodeType(NodeTypeFlag.INT_FLAG, TypeContext.PRIMITIVE_CONTEXT, false));
            	}
            }
        } catch(NumberFormatException nfe) {
            reportInternalError(CompileErrors.InternalErrors.intLiteralRangeCheckNumberFormatException(t.image));
        } catch(NullPointerException npe) {
            reportInternalError(CompileErrors.InternalErrors.intLiteralRangeCheckNullPointerException(t.image));
        }
    }
    
    protected void checkNarrowingConversion(NodeType lhsType, NodeType rhsType) {
        //todo implement narrowing conversion warnings if necessary
        return;
    }
    
    protected void reportError(String message, Token failure, Token begin, Token end) {
        errorList.add(new RuleError(message, failure, begin, end, RuleError.SEMANTIC_TYPE));
    }
    
    protected void reportError(String message, Token failure, Node source) {
        reportError(message, failure, findBeginToken(source), findEndToken(source));
    }
    
    protected void reportInternalError(String message) {
        errorList.add(new RuleError(message, RuleError.INTERNAL_TYPE));
    }
    
    protected void reportInternalError(String message, Token token) {
        //throw new RuntimeException(message);
        errorList.add(new RuleError(message, token, RuleError.INTERNAL_TYPE));
    }
    
    protected void reportInternalError(String message, Token failure, Token begin, Token end) {
        errorList.add(new RuleError(message, failure, begin, end, RuleError.INTERNAL_TYPE));
    }
    
    protected void reportWarning(String message) {
        //throw new RuntimeException(message);
        errorList.add(new RuleError(message, RuleError.WARNING_TYPE));
    }

    protected void reportWarning(String message, Token token) {
        //throw new RuntimeException(message);
        errorList.add(new RuleError(message, token, RuleError.WARNING_TYPE));
    }

    protected void reportWarning(String message, Token failure, Token begin, Token end) {
        errorList.add(new RuleError(message, failure, begin, end, RuleError.WARNING_TYPE));
    }
    
    public List getErrors() {
        return errorList;
    }
    public void clearErrors() {
        errorList.clear();
    }
    
    protected static Token findBeginToken(Node node) {
        if(node != null) return node.getFirstToken();
        else return null;
    }
    
    protected static Token findEndToken(Node node) {
        if(node != null) return node.getLastToken();
        else return null;
    }

    protected static boolean isConceptReference(NodeType type) {
        if(type.isGenericConceptReference()) return true;
        return type.hasPropertyContext() && !type.isGeneric() && type.isConceptReference();
    }
}
