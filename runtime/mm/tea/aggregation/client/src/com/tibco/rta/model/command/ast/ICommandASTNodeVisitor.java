package com.tibco.rta.model.command.ast;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 27/11/12
 * Time: 12:08 PM
 * Visitor for walking and visiting each node in the AST
 * represented by {@link CommandASTNode}
 */
public interface ICommandASTNodeVisitor {

    /**
     * Visit any command ast node
     * @param commandASTNode
     * @return
     */
    public boolean visit(CommandASTNode commandASTNode);

    /**
     * Visit children
     * @param commandASTNode
     * @return
     */
    public boolean visitChildren(CommandASTNode commandASTNode);

    /**
     * Visit the root node
     * @param commandASTNode
     * @return
     */
    public boolean visitCommandNode(CommandASTNode commandASTNode);

    /**
     * Visit verb node
     * @param commandASTNode
     * @return
     */
    public boolean visitVerbNode(CommandASTNode commandASTNode);

    /**
     * Visit model object nodes like schema, cube etc.
     * @param commandASTNode
     * @return
     */
    public boolean visitModelBlockNode(CommandASTNode commandASTNode);

    /**
     * Visit model object nodes like schema, cube etc.
     * @param commandASTNode
     * @return
     */
    public boolean visitTargetBlockNode(CommandASTNode commandASTNode);

    /**
     * Visit BLOCK node
     * @param commandASTNode
     * @return
     */
    public boolean visitBlockNode(CommandASTNode commandASTNode);

    /**
     * Visit any identifier node mapping to some common name.
     * @param commandASTNode
     * @return
     */
    public boolean visitIdentifierNode(CommandASTNode commandASTNode);

    /**
     * Visit optional attributes of a model object
     * @param commandASTNode
     * @return
     */
    public boolean visitModelAttributesNode(CommandASTNode commandASTNode);

    /**
     * Visit preposition node.
     * @param commandASTNode
     * @return
     */
    public boolean visitPrepositionNode(CommandASTNode commandASTNode);

    /**
     * Visit prepositions node.
     * @param commandASTNode
     * @return
     */
    public boolean visitPrepositionsNode(CommandASTNode commandASTNode);
}
