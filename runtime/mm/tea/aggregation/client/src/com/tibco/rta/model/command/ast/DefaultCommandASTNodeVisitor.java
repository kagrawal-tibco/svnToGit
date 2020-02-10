package com.tibco.rta.model.command.ast;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.model.command.CommandActions;
import com.tibco.rta.model.command.CommandLineSession;
import com.tibco.rta.model.command.CommandObject;
import com.tibco.rta.model.command.CommandType;

import java.util.List;
import java.util.Map;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DATATYPE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_NAME_NAME;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 27/11/12
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultCommandASTNodeVisitor implements ICommandASTNodeVisitor {

    private CommandLineSession commandLineSession;
    
    private Command command;

    private CommandObject currentTargetObject;

    private CommandType currentCommandType;

    public DefaultCommandASTNodeVisitor(CommandLineSession commandLineSession, Map<ConfigProperty, String> configuration) {
        this.commandLineSession = commandLineSession;
    }

    /**
     * Visit the root node
     *
     * @param commandASTNode
     * @return
     */
    @Override
    public boolean visitCommandNode(CommandASTNode commandASTNode) {
        //Get children
        visitChildren(commandASTNode);
        return false;
    }

    /**
     * Visit verb node
     *
     * @param commandASTNode
     * @return
     */
    @Override
    public boolean visitVerbNode(CommandASTNode commandASTNode) {
        switch (commandASTNode.getType()) {
            case SPMCLIParser.CREATE :
                command = CommandFactory.getCommand(CommandActions.CREATE);
                break;
            case SPMCLIParser.CONNECT :
                command = CommandFactory.getCommand(CommandActions.CONNECT);
                break;
            case SPMCLIParser.REMOVE :
            case SPMCLIParser.EXPORT :
            case SPMCLIParser.IMPORT :
        }
        visitChildren(commandASTNode);
        commandLineSession.record(command);
        return true;
    }

    /**
     * Visit model object nodes like schema, cube etc.
     *
     * @param commandASTNode
     * @return
     */
    @Override
    public boolean visitModelBlockNode(CommandASTNode commandASTNode) {
        CommandASTNode childNode = commandASTNode.getChild(0);
        visitKeywordNode(childNode);
        return false;
    }

    /**
     * Visit model object nodes like schema, cube etc.
     *
     * @param commandASTNode
     * @return
     */
    @Override
    public boolean visitTargetBlockNode(CommandASTNode commandASTNode) {
        return visitModelBlockNode(commandASTNode);
    }

    /**
     * Visit any identifier node mapping to some common name.
     *
     * @param commandASTNode
     * @return
     */
    @Override
    public boolean visitIdentifierNode(CommandASTNode commandASTNode) {
        visitChildren(commandASTNode);
        return false;
    }

    /**
     * Visit optional attributes of a model object
     *
     * @param commandASTNode
     * @return
     */
    @Override
    public boolean visitModelAttributesNode(CommandASTNode commandASTNode) {
        return false;
    }

    /**
     * Visit preposition node.
     *
     * @param commandASTNode
     * @return
     */
    @Override
    public boolean visitPrepositionNode(CommandASTNode commandASTNode) {
        CommandASTNode modelObjectChild = commandASTNode.getChildByFeatureId(SPMCLIParser.MODEL_BLOCK);
        visitModelBlockNode(modelObjectChild);
        return false;
    }


    private boolean visitKeywordNode(CommandASTNode commandASTNode) {
        int nodeType = commandASTNode.getType();
        CommandASTNode parentNode = (CommandASTNode)commandASTNode.getParent();
        int parentNodeType = parentNode.getType();

        switch (nodeType) {
            case SPMCLIParser.CUBE :
                currentCommandType = CommandType.CUBE;
                break;
            case SPMCLIParser.SCHEMA :
                currentCommandType = CommandType.SCHEMA;
                break;
            case SPMCLIParser.MEASUREMENT :
                currentCommandType = CommandType.MEASUREMENT;
                break;
            case SPMCLIParser.DIMENSION :
                currentCommandType = CommandType.DIMENSION;
                break;
            case SPMCLIParser.ATTRIBUTE :
                currentCommandType = CommandType.ATTRIBUTE;
                break;
            case SPMCLIParser.HIERARCHY :
                currentCommandType = CommandType.HIERARCHY;
                break;
        }
        if (parentNodeType == SPMCLIParser.MODEL_BLOCK) {
            //Push it onto stack
            CommandObject currentPrepObject = new CommandObject();
            currentPrepObject.setCommandType(currentCommandType);
            //Get identifier
            CommandASTNode identifierNode = commandASTNode.getChildByFeatureId(SPMCLIParser.IDENTIFIER);
            currentPrepObject.addAttribute(ATTR_NAME_NAME, identifierNode.getChild(0).getText());
            //Push it onto stack
            command.push(currentPrepObject);
        }

        List<CommandASTNode> blockNodes = commandASTNode.getChildrenByFeatureId(SPMCLIParser.BLOCK);
        for (CommandASTNode blockNode : blockNodes) {
            visitBlockNode(blockNode);
        }

        return false;
    }

    private void processAttributesNode(CommandASTNode modelAttributesNode) {
        List<CommandASTNode> attributesChildren = modelAttributesNode.getChildren();

        for (CommandASTNode attributeChild : attributesChildren) {
            int nodeType = attributeChild.getType();
            CommandASTNode identifierNode = attributeChild.getChildByFeatureId(SPMCLIParser.IDENTIFIER);

            switch (nodeType) {
                case SPMCLIParser.DATATYPE:
                    currentTargetObject.addAttribute(ATTR_DATATYPE_NAME, identifierNode.getChild(0).getText());
                    break;
                case SPMCLIParser.AGGREGATOR:
//                    currentTargetObject.addAttribute(ATTR_AGGREGATOR_NAME, identifierNode.getChild(0).getText());
                    break;
            }
        }
    }

    /**
     * Visit BLOCK node
     *
     * @param commandASTNode
     * @return
     */
    @Override
    public boolean visitBlockNode(CommandASTNode commandASTNode) {
        currentTargetObject = new CommandObject();
        //Block has one identifier
        CommandASTNode identifierNode = commandASTNode.getChildByFeatureId(SPMCLIParser.IDENTIFIER);
        if (identifierNode != null) {
            //Working with target
            currentTargetObject.addAttribute(ATTR_NAME_NAME, identifierNode.getChild(0).getText());
            currentTargetObject.setCommandType(currentCommandType);
            command.addCommandObject(currentTargetObject);
        }
        //Get attributes if any
        CommandASTNode modelAttributesNode = commandASTNode.getChildByFeatureId(SPMCLIParser.ATTRIBUTES_BLOCK);
        if (modelAttributesNode != null) {
            processAttributesNode(modelAttributesNode);
        }
        return false;
    }

    /**
     * Visit any command ast node
     *
     * @param commandASTNode
     * @return
     */
    @Override
    public boolean visit(CommandASTNode commandASTNode) {
        int nodeType = commandASTNode.getType();
        switch (nodeType) {

            case SPMCLIParser.COMMAND :
                return visitCommandNode(commandASTNode);
            case SPMCLIParser.MODEL_BLOCK :
                return visitModelBlockNode(commandASTNode);
            case SPMCLIParser.TARGET_BLOCK :
                return visitTargetBlockNode(commandASTNode);
            case SPMCLIParser.ATTRIBUTES_BLOCK :
                return visitModelAttributesNode(commandASTNode);
            case SPMCLIParser.PREPOSITIONS :
                return visitPrepositionsNode(commandASTNode);
            case SPMCLIParser.PREPOSITION :
                return visitPrepositionNode(commandASTNode);
            case SPMCLIParser.IDENTIFIER :
                return visitIdentifierNode(commandASTNode);
            case SPMCLIParser.CREATE :
            case SPMCLIParser.REMOVE :
            case SPMCLIParser.EXPORT :
            case SPMCLIParser.IMPORT :
                visitVerbNode(commandASTNode);
        }
        return false;
    }

    public void performOp() throws Exception {
        commandLineSession.commit();
    }

    /**
     * Visit children
     *
     * @param commandASTNode
     * @return
     */
    @Override
    public boolean visitChildren(CommandASTNode commandASTNode) {
        for (CommandASTNode childASTNode : commandASTNode.getChildren()) {
            visit(childASTNode);
        }
        return false;
    }

    /**
     * Visit prepositions node.
     *
     * @param commandASTNode
     * @return
     */
    @Override
    public boolean visitPrepositionsNode(CommandASTNode commandASTNode) {
        visitChildren(commandASTNode);
        return false;
    }
}
