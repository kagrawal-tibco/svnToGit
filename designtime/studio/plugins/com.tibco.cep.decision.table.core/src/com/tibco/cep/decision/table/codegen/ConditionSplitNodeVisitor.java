package com.tibco.cep.decision.table.codegen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.parser.RuleGrammarConstants;
import com.tibco.be.parser.tree.DeclarationNode;
import com.tibco.be.parser.tree.FunctionNode;
import com.tibco.be.parser.tree.NameNode;
import com.tibco.be.parser.tree.Node;
import com.tibco.be.parser.tree.NodeVisitor;
import com.tibco.be.parser.tree.ProductionNode;
import com.tibco.be.parser.tree.ProductionNodeListNode;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.be.parser.tree.SourceType;
import com.tibco.be.parser.tree.TemplatedDeclarationNode;
import com.tibco.be.parser.tree.TemplatedProductionNode;
import com.tibco.be.parser.tree.TypeNode;

public class ConditionSplitNodeVisitor implements NodeVisitor {
	private ArrayList<RootNode> newNodes = new ArrayList<RootNode>(); 
	//allows for re-use of the original root nodes
	private RootNode currentOriginalNode;
	private SourceType currentSourceType;
	
	public ConditionSplitNodeVisitor() {
		reset();
	}
	
	private void reset() {
		//nodes = null;
		//currentOriginalNode = null;
		currentSourceType = null;
		newNodes.clear();
	}
	
	public void splitConditions(List<RootNode> rootNodes) {
		if(rootNodes.size() <= 0) return;
		for(RootNode node : rootNodes) {
			visitRootNode(node);
		}
		rootNodes.clear();
		//TODO is all this copying slow?
		rootNodes.addAll(newNodes);
		reset();
	}
	
    private void addToRootNode(Node node) {
    	RootNode rootNode;
		if(currentOriginalNode != null) {
			//re-use the original root node for one of the split nodes
			rootNode = currentOriginalNode;
			currentOriginalNode = null;
		} else {
			rootNode = new RootNode(currentSourceType, node.getFirstToken(), node.getLastToken());
		}
		newNodes.add(rootNode);
		rootNode.prependChild(node);
    }

    private void visitChildren(Node n) {
        for(Iterator<Node> it = n.getChildren(); it.hasNext();) {
            Node child = it.next();
            if(child != null) child.accept(this);
        }
    }    
    
    public Object visitFunctionNode(FunctionNode node) {
    	addToRootNode(node);
        return null;
    }

    public Object visitProductionNode(ProductionNode node) {
    	if(node.getRelationKind() == Node.NODE_BINARY_RELATION
    		&& node.getToken() != null 
    		&& node.getToken().kind == RuleGrammarConstants.SC_AND)
    	{
    		visitChildren(node);
    	} else {
    		addToRootNode(node);
    	}
        return null;
    }

    public Object visitProductionNodeListNode(ProductionNodeListNode node) {
    	addToRootNode(node);
        return null;
    }

    public Object visitDeclarationNode(DeclarationNode node) {
    	addToRootNode(node);
        return null;
    }

    public Object visitRootNode(RootNode node) {
    	currentSourceType = node.getSourceType();
    	for (Iterator<Node> it = node.getChildren(); it.hasNext();) {
            Node child = it.next();
            //re-use this original root node for the last child of this root node
            //and clear this node's list of children
            //have to wait to clear until the last child or it will break the iterator
            if(!it.hasNext()) {
            	currentOriginalNode = node;
            	node.clearChildren();
            }
            if(child != null) child.accept(this);
        }
        return null;
    }

    public Object visitNameNode(NameNode node) {
    	addToRootNode(node);
        return null;
    }

    public Object visitTypeNode(TypeNode node) {
    	addToRootNode(node);
        return null;
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
