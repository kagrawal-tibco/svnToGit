package com.tibco.be.ws.rt;

import com.tibco.be.ws.rt.model.builder.FilterLinkDescriptorFactory;
import com.tibco.be.ws.rt.model.builder.ast.impl.AbstractFilterLinkDescriptor;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/4/12
 * Time: 5:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleTemplateASTNodeVisitor extends DefaultRulesASTNodeVisitor {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(RuleTemplateASTNodeVisitor.class);

    private List<AbstractFilterLinkDescriptor> filterLinkDescriptors = new ArrayList<AbstractFilterLinkDescriptor>();

    /**
     * Keep a pointer to the root descriptor.
     */
    private AbstractFilterLinkDescriptor rootLinkDescriptor;

    /**
     * Keep a pointer to the current descriptor and this will keep changing.
     */
    private AbstractFilterLinkDescriptor currentLinkDescriptor;

    @Override
    public boolean visit(RulesASTNode node) {
        super.visit(node);
        switch (node.getType()) {
            case RulesParser.EXPRESSION:
                return visitExpressionNode(node);
            case RulesParser.Identifier:
                return visitIdentifierNode(node);
            case RulesParser.QUALIFIER:
                return visitQualifierNode(node);
            case RulesParser.PREFIX:
                return visitPrefixNode(node);
            case RulesParser.StringLiteral:
            case RulesParser.FloatingPointLiteral:
            case RulesParser.NullLiteral:
            case RulesParser.DecimalLiteral:
            case RulesParser.HexLiteral:
            case RulesParser.CharacterLiteral:
                return visitConstantValueNode(node);
            default:
                return visitDefault(node);
        }
    }

    /**
     * @param astNode
     * @return
     */
    public boolean visitExpressionNode(RulesASTNode astNode) {
        try {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Visiting expression node %s", astNode);
            }
            rootLinkDescriptor = FilterLinkDescriptorFactory.createDescriptor(astNode);
            currentLinkDescriptor = rootLinkDescriptor;
            filterLinkDescriptors.add(rootLinkDescriptor);
            visitChildren(astNode);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Exception parsing expression node", e);
        }
        return false;
    }


    @Override
    public boolean visitBinaryRelationNode(RulesASTNode node) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Visiting binary relational node %s", node);
        }
        try {
            AbstractFilterLinkDescriptor childLinkDescriptor = FilterLinkDescriptorFactory.createDescriptor(node);
            currentLinkDescriptor.nextDescriptor = childLinkDescriptor;
            currentLinkDescriptor = childLinkDescriptor;
            //Get and visit children
            visitChildren(node);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Exception parsing expression node", e);
        }

        return false;
    }

    @Override
    public boolean visitPrimaryExpressionNode(RulesASTNode node) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Visiting primary expression node %s", node);
        }
        try {
            AbstractFilterLinkDescriptor childLinkDescriptor = FilterLinkDescriptorFactory.createDescriptor(node);
            currentLinkDescriptor.nextDescriptor = childLinkDescriptor;
            currentLinkDescriptor = childLinkDescriptor;
            visitChildren(node);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Exception parsing primary expression node", e);
        }
        return false;
    }

    public boolean visitQualifiedNameNode(RulesASTNode node) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Visiting Qualified name node %s", node);
        }
        //Get children
        visitChildren(node);
        return false;
    }


    public boolean visitIdentifierNode(RulesASTNode node) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Visiting Identifier node %s", node);
        }
        try {
            AbstractFilterLinkDescriptor childLinkDescriptor = FilterLinkDescriptorFactory.createDescriptor(node);
            currentLinkDescriptor.addChildDescriptor(childLinkDescriptor);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Exception parsing identifier node", e);
        }
        return false;
    }

    @Override
    public boolean visitSimpleNameNode(RulesASTNode node) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Visiting simple name node %s", node);
        }
        visitChildren(node);
        return false;
    }

    public boolean visitQualifierNode(RulesASTNode node) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Visiting qualifier node %s", node);
        }
        visitChildren(node);
        return false;
    }

    public boolean visitPrefixNode(RulesASTNode node) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Visiting prefix node %s", node);
        }
        visitChildren(node);
        return false;
    }

    @Override
    public boolean visitChildren(RulesASTNode node) {
        List<RulesASTNode> children = node.getChildren();
        for (RulesASTNode childNode : children) {
            visit(childNode);
        }
        return false;
    }

    public boolean visitConstantValueNode(RulesASTNode node) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Visiting Constant value node %s", node);
        }
        try {
            AbstractFilterLinkDescriptor childLinkDescriptor = FilterLinkDescriptorFactory.createDescriptor(node);
            //Add this to primary expression node.
            currentLinkDescriptor.addChildDescriptor(childLinkDescriptor);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Exception parsing constant node", e);
        }
        return false;
    }

    public List<AbstractFilterLinkDescriptor> getFilterLinkDescriptors() {
        return Collections.unmodifiableList(filterLinkDescriptors);
    }
}
