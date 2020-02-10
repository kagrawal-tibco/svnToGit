package com.tibco.rta.model.command.ast;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 27/11/12
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandASTNode extends CommonTree {

    public CommandASTNode(Token token) {
        super(token);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CommandASTNode> getChildren() {
        if (children == null) {
            children = new ArrayList<CommandASTNode>();
        }
        return children;
    }

    public List<CommandASTNode> getChildrenByFeatureId(int featureId) {
        List<CommandASTNode> allChildrenNodes = getChildren();
        List<CommandASTNode> children = new ArrayList<CommandASTNode>();
        if (allChildrenNodes != null) {
            for (CommandASTNode childNode : allChildrenNodes) {
                if (childNode.getType() == featureId) {
                    children.add(childNode);
                }
            }
        }
        return children;
    }

    public CommandASTNode getChildByFeatureId(int featureId) {
        return getFirstChildWithType(featureId);
    }

    @Override
    public CommandASTNode getFirstChildWithType(int type) {
    	Tree child = super.getFirstChildWithType(type);
		if (child instanceof CommandASTNode) {
			return (CommandASTNode) child;
		} else if (child instanceof CommonTree) {
			return new CommandASTNode(((CommonTree)child).token);
		}
		return null;
    }

    /**
     * Standard visitor pattern accept for target
     * @param commandASTNodeVisitor
     */
    public void accept(ICommandASTNodeVisitor commandASTNodeVisitor) {
        if (commandASTNodeVisitor.visit(this)) {
            doVisitChildren(commandASTNodeVisitor);
        }
    }

    private void doVisitChildren(ICommandASTNodeVisitor commandASTNodeVisitor) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            CommandASTNode child = getChild(i);
            child.accept(commandASTNodeVisitor);
        }
    }

    @Override
    public CommandASTNode getChild(int i) {
        Tree child = super.getChild(i);
        if (child instanceof CommandASTNode) {
            return (CommandASTNode) child;
        } else if (child instanceof CommonTree) {
            return new CommandASTNode(((CommonTree)child).token);
        }
        return null;
    }
}
