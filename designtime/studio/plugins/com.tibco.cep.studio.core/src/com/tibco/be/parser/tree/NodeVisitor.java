package com.tibco.be.parser.tree;



/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jun 9, 2004
 * Time: 7:56:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NodeVisitor {
    public Object visitFunctionNode(FunctionNode node);
    public Object visitProductionNode(ProductionNode node);
    public Object visitProductionNodeListNode(ProductionNodeListNode node);
    public Object visitDeclarationNode(DeclarationNode node);
	public Object visitRootNode(RootNode node);
    public Object visitNameNode(NameNode node);
    public Object visitTypeNode(TypeNode node);
    public Object visitTemplatedDeclarationNode(TemplatedDeclarationNode node);
    public Object visitTemplatedProductionNode(TemplatedProductionNode node);
}
