package com.tibco.be.parser.semantic;

import java.util.Iterator;

import com.tibco.be.parser.RuleGrammarConstants;
import com.tibco.be.parser.Token;
import com.tibco.be.parser.tree.Node;
import com.tibco.be.parser.tree.ProductionNode;
import com.tibco.be.parser.tree.ProductionNodeListNode;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Aug 25, 2008
 * Time: 2:52:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeTransformation {
    public static final ProductionNode OPEN_RANGE = new ProductionNode(
            Token.newToken(RuleGrammarConstants.OPEN_RANGE, RuleGrammarConstants.tokenImage[RuleGrammarConstants.OPEN_RANGE]), 0);
    
    public static void transformDomainCondition(ProductionNode node) {
        Node lhs = node.getChild(0);
        Node rhs = node.getChild(1);
        Node cmpNode = null;
        ProductionNodeListNode pnln = null;
        if(lhs instanceof ProductionNodeListNode) {
            pnln = (ProductionNodeListNode) lhs;
            cmpNode = rhs;
        } else {
            pnln = (ProductionNodeListNode) rhs;
            cmpNode = lhs;
        }
        
        if(pnln.getListType() == ProductionNodeListNode.RANGE_TYPE) {
            transformRangeCondition(node, cmpNode, pnln);
        } else if(pnln.getListType() == ProductionNodeListNode.SET_MEMBERSHIP_TYPE) {
            transformSetMembershipCondition(node, cmpNode, pnln);

        }
    }

    private static void transformSetMembershipCondition(ProductionNode node, Node cmpNode, ProductionNodeListNode pnln) {
        //membership in an empty set is always false
        if(pnln.getChildCount() <= 0) {
            node.clearChildren();
            node.setToken(FALSE);
            node.setRelation(0);
        } else if(pnln.getChildCount() == 1) {
            node.clearChildren();
            //node already has the correct comparison token and releation
            node.prependChild(pnln.getChild(0));
            node.prependChild(cmpNode);
        } else {
            //the == or != token from the exp <op> {set} node (example 5 == {1,2,5})
            Token cmp = node.getToken();
            
            node.clearChildren();
            //result of transformation will be several equality nodes as children of
            //ORs for a =={b,c} -> a==b || a==c and ANDs for a !={b,c} -> a!=b && a!=c
            Token bool = OR;
            if(cmp.kind == EQ.kind) {
                bool = OR;
            } else if(cmp.kind == NE.kind) {
                bool = AND;
            }
            node.setToken(bool);
            node.setRelation(2);
            Node currentBoolNode = node;
            boolean first = true;
            //structure for A=={a,b,c} is &&(A == a, &&(A == b, A == c))
            for(Iterator children = pnln.getChildren(); children.hasNext();) {
                Node child = (Node)children.next();
                //do one comparison
                ProductionNode comparison = new ProductionNode(cmp, 2);
                comparison.appendChild(cmpNode);
                comparison.appendChild(child);
                //for first iteration just append to the existing node
                if(!first && children.hasNext()) {
                    //add comparison to the node and save bool node for next loop
                    ProductionNode boolNode = new ProductionNode(bool, 2);
                    boolNode.appendChild(comparison);
                    currentBoolNode.appendChild(boolNode);
                    currentBoolNode = boolNode;
                } else {
                    //last OR node will have two comparison node children instead of
                    //one comparison node and one OR node
                    currentBoolNode.appendChild(comparison);
                    first = false;
                }
            }
        }
    }
    
    private static void transformRangeCondition(ProductionNode node, Node cmpNode, ProductionNodeListNode pnln) {
        Node lower = null;
        Node upper = null;
        Node child = null;
        child = pnln.getChild(0);
        if(child != null && child != OPEN_RANGE) {
            lower = child;
        }
        child = pnln.getChild(1);
        if(child != null && child != OPEN_RANGE) {
            upper = child;
        }

        node.clearChildren();
        //a range of (,) basically means accept all numbers so just short cut it with a true literal
        if(lower == null && upper == null) {
            node.setToken(TRUE);
            node.setRelation(0);
        } else if(lower == null) {
            node.setToken(getRangeUpperOp(pnln));
            node.setRelation(2);
            node.prependChild(upper);
            node.prependChild(cmpNode);
        } else if(upper == null) {
            node.setToken(getRangeLowerOp(pnln));
            node.setRelation(2);
            node.prependChild(lower);
            node.prependChild(cmpNode);
        } else {
            ProductionNode lowerCmp = new ProductionNode(getRangeLowerOp(pnln), 2);
            lowerCmp.prependChild(lower);
            lowerCmp.prependChild(cmpNode);
            ProductionNode upperCmp = new ProductionNode(getRangeUpperOp(pnln), 2);
            upperCmp.prependChild(upper);
            upperCmp.prependChild(cmpNode);
            
            node.setToken(AND);
            node.setRelation(2);
            node.prependChild(lowerCmp);
            node.prependChild(upperCmp);
        }
    }

    private static final Token NE = Token.newToken(RuleGrammarConstants.NE, "!=");
    private static final Token EQ = Token.newToken(RuleGrammarConstants.EQ, "==");
    private static final Token AND = Token.newToken(RuleGrammarConstants.SC_AND, "&&");
    private static final Token OR = Token.newToken(RuleGrammarConstants.SC_OR, "||");
    //private static final Token NOP = Token.newToken(RuleGrammarConstants.NO_OP, RuleGrammarConstants.tokenImage[RuleGrammarConstants.NO_OP]);
    private static final Token TRUE = Token.newToken(RuleGrammarConstants.TRUE, "true");
    private static final Token FALSE = Token.newToken(RuleGrammarConstants.FALSE, "false");
    private static final Token RANGE_UPPER_OPEN_OP = Token.newToken(RuleGrammarConstants.LT, "<");
    private static final Token RANGE_UPPER_CLOSED_OP = Token.newToken(RuleGrammarConstants.LE, "<=");
    private static final Token RANGE_LOWER_OPEN_OP = Token.newToken(RuleGrammarConstants.GT, ">");
    private static final Token RANGE_LOWER_CLOSED_OP = Token.newToken(RuleGrammarConstants.GE, ">=");

    public static Token getRangeLowerOp(ProductionNodeListNode pnln) {
        Token first = pnln.getFirstToken();
        if(first.kind == RuleGrammarConstants.LPAREN)
            return RANGE_LOWER_OPEN_OP;
        else if(first.kind == RuleGrammarConstants.LBRACKET)
            return RANGE_LOWER_CLOSED_OP;
        else
            return null;
    }
    public static Token getRangeUpperOp(ProductionNodeListNode pnln) {
        Token last = pnln.getLastToken();
        if(last.kind == RuleGrammarConstants.RPAREN)
            return RANGE_UPPER_OPEN_OP;
        else if(last.kind == RuleGrammarConstants.RBRACKET)
            return RANGE_UPPER_CLOSED_OP;
        else
            return null;
    }
    
    public static boolean transformMissingLHS(ProductionNode node, Node lastLHS) {
        ProductionNode child = (ProductionNode)node.getChild(0);
        
        //node has a MISSING_LHS token.
        //change it to be the same as its child, and then discard the child
        node.clearChildren();
        node.setToken(child.getToken());
        node.setRelationKind(child.getRelationKind());
        for(Iterator it = child.getChildren(); it.hasNext();) {
            node.prependChild((Node)it.next());
        }
        child = null;
        
        if(lastLHS != null) {
            node.prependChild(lastLHS);
            node.overrideFirstToken(node.getToken());
            //the above child.getRelationKind would have returned unary_relation
            node.setRelationKind(Node.NODE_BINARY_RELATION);
            return true;
        }
        return false;
    }
}
