package com.tibco.be.parser.tree;

import java.util.Iterator;

import com.tibco.be.parser.Token;
public class ParseSyntaxTree {
    private java.util.Stack nodes;

    public ParseSyntaxTree() {
        nodes = new java.util.Stack();
    }

    /* Call this to reinitialize the node stack.  It is called
    automatically by the parser's ReInit() method. */
    public void reset() {
        nodes.removeAllElements();
    }

    /* Returns the root node of the AST.  It only makes sense to call
    this after a successful parse. */
    public Node rootNode() {
        return (Node)nodes.elementAt(0);
    }

    /* Pushes a node on to the stack. */
    public void pushNode(Node n) {
        nodes.push(n);
    }

    /* Returns the node on the top of the stack, and remove it from the
    stack.  */
    public Node popNode() {
        return (Node)nodes.pop();
    }

    /* Returns the node currently on the top of the stack. */
    public Node peekNode() {
        return (Node)nodes.peek();
    }

    public boolean isEmpty() {
        return nodes.empty();
    }

    public String toString() {
        return nodes.toString();
    }


    public void buildTree(Node parentNode) {
        buildTree(parentNode, parentNode.getRelationKind());
    }
    
    public void buildTree(Node parentNode, int arity) {
        if(nodes.size() == 0 || arity == 0) return;
        
        //build node n's tree before adding it to parentNode
        Node n = (Node)nodes.pop();
        buildTree(n);
        
        parentNode.prependChild(n);
        //continue the tree building process on the parent until it has as many nodes prepended as its arity
        buildTree(parentNode, arity - 1);
        
        //n = (Node)nodes.pop();
        //parentNode.prependChild(n);
        //switch (n.getRelationKind()) {
        //    case Node.NODE_NULL_RELATION:
        //        break;
        //    case Node.NODE_UNARY_RELATION:
        //        buildTree(n);
        //        break;
        //    case Node.NODE_BINARY_RELATION:
        //        buildTree(n);
        //        //n = (Node)nodes.pop();
        //        //parentNode.insertNode(n);
        //        buildTree(n);
        //        break;
        //}
    }

    public NameNode buildNameNode() {
        if(nodes.size() <= 0) {
            assert(false);
            return null;
        }
        Token[] ids = new Token[(nodes.size() / 2) + 1];
        Token[] dots = new Token[nodes.size() / 2];
        
        int ii = 0;
        Iterator it = nodes.iterator();
        ProductionNode next;
        while(true) {
            if(it.hasNext()) {
                next = (ProductionNode)it.next();
                ids[ii] = next.getToken();
            } else break;

            if(it.hasNext()) {
                next = (ProductionNode)it.next();
                dots[ii] = next.getToken();
            } else break;
            ii++;
        }
        return new NameNode(ids, dots);
    }
    
    public Node buildExpressionNode() {
        Node n = (Node)nodes.pop();
        buildTree(n);
        nodes.push(n);
        return n;
    }

    public FunctionNode buildFunctionNode(Node fnName) {

        FunctionNode node = new FunctionNode((NameNode)fnName);

        while (nodes.size() > 0) {
            //consume argument expressions one at a time
            buildTree(node, 1);
        }
        return node;
    }

    //This method assumes that every statement in this ParseSyntaxTree belongs in the new statement list node.
    public ProductionNodeListNode buildProductionNodeListNode(Token start, Token end, ProductionNodeListNode.ListType listType) {
        ProductionNodeListNode node = new ProductionNodeListNode(start, end, listType);
        
        while(nodes.size() > 0) {
            //Choice of 1 for arity is arbitrary.  Could modify buildTree for building nodes that consume the entire tree. 
            buildTree(node, 1);
        }
        return node;
    }

    public DeclarationNode buildDeclarationNode() {
        DeclarationNode node = new DeclarationNode();
        while(nodes.size() > 0) {
            buildTree(node, 1);
        }
        return node;
    }


    public BindingNode buildBindingNode() {
        final BindingNode node = new BindingNode();
        while(nodes.size() > 0) {
            buildTree(node, 1);
        }
        return node;
    }


    public TemplatedDeclarationNode buildTemplatedDeclarationNode(
            TemplatedDeclarationNode.Mode mode) {

        final TemplatedDeclarationNode node = new TemplatedDeclarationNode(mode);
        while(nodes.size() > 0) {
            buildTree(node, 1);
        }
        return node;
    }


    public TemplatedProductionNode buildTemplatedProductionNode(
            Token token,
            int reln,
            TemplatedProductionNode.Mode mode) {

        final TemplatedProductionNode node = new TemplatedProductionNode(token, reln, mode);
        while(nodes.size() > 0) {
            buildTree(node, 1);
        }
        return node;
    }


}



