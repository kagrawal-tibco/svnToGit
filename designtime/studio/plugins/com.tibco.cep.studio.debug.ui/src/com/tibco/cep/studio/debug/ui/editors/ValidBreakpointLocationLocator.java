package com.tibco.cep.studio.debug.ui.editors;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Token;

import com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;

public class ValidBreakpointLocationLocator extends DefaultRulesASTNodeVisitor {
	public static final int LOCATION_NOT_FOUND= 0;
	public static final int LOCATION_LINE= 1;
	public static final int LOCATION_METHOD= 2;
	public static final int LOCATION_FIELD= 3;
	
	private RulesASTNode fCompilationUnit;
	private int fLineNumber;

	private int fLocationType;
	private boolean fLocationFound;
	private int fLineLocation;
	private int fMemberOffset;
    private static RulesASTNodeComparator comparator = new RulesASTNodeComparator();
	
    /**
	 * @param compilationUnit the JDOM CompilationUnit of the source code.
     * @param lineNumber the line number in the source code where to put the breakpoint.
	 */
	public ValidBreakpointLocationLocator(RulesASTNode compilationUnit, int lineNumber) {
		fCompilationUnit= compilationUnit;
		fLineNumber= lineNumber;
		fLocationFound= false;
	}
	
	static class RulesASTNodeComparator implements Comparator<RulesASTNode> {

		@Override
		public int compare(RulesASTNode arg0, RulesASTNode arg1) {
			return arg0.getOffset() > arg1.getOffset() ? 1 : -1;
		}
		
	}
	
	/**
	 * Return the type of the valid location found
	 * @return one of LOCATION_NOT_FOUND, LOCATION_LINE, LOCATION_METHOD or LOCATION_FIELD
	 */
	public int getLocationType() {
		return fLocationType;
	}
	
		
	/**
	 * Return the line number of the computed valid location, if the location type is LOCATION_LINE.
	 */
	public int getLineLocation() {
		if (fLocationType == LOCATION_LINE) {
			return fLineLocation;
		}
		return -1;
	}
	
	/**
	 * Return the offset of the member which is the valid location,
	 * if the location type is LOCATION_METHOD or LOCATION_FIELD.
	 */
	public int getMemberOffset() {
		return fMemberOffset;
	}
	
	private int lineNumber(int offset) {
		CommonTokenStream tokens = fCompilationUnit != null ? (CommonTokenStream) fCompilationUnit.getData("tokens") : null;
		int lineNumber = -1;
		if (tokens != null) {
			Token t = tokens.get(offset);
			lineNumber = t.getLine();
		}
		return lineNumber < 1 ? 1 : lineNumber;
	}
	
	/**
	 * Return <code>true</code> if this node children may contain a valid location
	 * for the breakpoint.
	 * @param node the node.
	 * @param isCode true indicated that the first line of the given node always
	 *	contains some executable code, even if split in multiple lines.
	 */
	private boolean visit(RulesASTNode node, boolean isCode) {
		// if we already found a correct location
		// no need to check the element inside.
		if (fLocationFound) {
			return false;
		}
		if(node.getTokenStartIndex() == -1 || node.getTokenStopIndex() == -1) {
			if(node.getChildCount() == 0){
				return false;
			} else {
				return true;
			}
		}
		int startPosition= node.getTokenStartIndex();
		int endLine = -1;
		endLine= lineNumber(node.getTokenStopIndex());
		// if the position is not in this part of the code
		// no need to check the element inside.
		if (endLine < fLineNumber) {
			return false;
		}
		// if the first line of this node always represents some executable code and the
		// breakpoint is requested on this line or on a previous line, this is a valid 
		// location
		int startLine = lineNumber(startPosition);
		if (isCode && (fLineNumber <= startLine)) {
			fLineLocation= startLine;
			fLocationFound= true;
			fLocationType= LOCATION_LINE;
//			fTypeName= computeTypeName(node);
			return false;
		}
		return true;
	}
	
	@Override
	protected boolean visitDefault(RulesASTNode node) {
		switch(node.getType()) {
		case RulesParser.ARRAY_LITERAL:
		case RulesParser.INITIALIZER:
		case RulesParser.LOCAL_INITIALIZER:
		case RulesParser.RANGE_EXPRESSION:
		case RulesParser.BREAK_STATEMENT:
		case RulesParser.CONTINUE_STATEMENT:
		case RulesParser.RETURN_STATEMENT:
		case RulesParser.THROW_STATEMENT:
		case RulesParser.EXPRESSION:
		case RulesParser.PREFIX:
			return visit(node,true);
		case RulesParser.TRY_STATEMENT:
		case RulesParser.TRY_RULE:
		case RulesParser.FINALLY_RULE:
		case RulesParser.FINALLY_CLAUSE:
		case RulesParser.CATCH_CLAUSE:
		case RulesParser.CATCH_RULE:
		case RulesParser.IF_RULE:
		case RulesParser.WHILE_RULE:
			return visit(node,false);
		case RulesParser.ARRAY_ALLOCATOR: 
			return visit(node,node.getChildOfType(RulesParser.EXPRESSION) != null);
		case RulesParser.LITERAL:
			return visit(node,!hasParentOfType(node, RulesParser.ATTRIBUTE_BLOCK) &&
					!hasParentOfType(node, RulesParser.DECLARE_BLOCK) &&
					!hasParentOfType(node, RulesParser.SCOPE_BLOCK));
		case RulesParser.FOR_LOOP:
			return visit(node,node.getChildCount() == 2 && 
					node.getChild(0).getType() == RulesParser.SEMICOLON &&
					node.getChild(1).getType() == RulesParser.SEMICOLON); // for(;;)
			
		default:
				return super.visitDefault(node);
		}
	}
	
	private boolean hasParentOfType(RulesASTNode node,int type) {
		return node.getAncestor(type) != null;
	}
	
	
	@Override
	public boolean visitBlockNode(RulesASTNode node) {
		if (visit(node, false)) {
//			if (node.getChildOfType(RulesParser.STATEMENTS).getChildCount() == 0  &&
//					(node.getParent().getType() == RulesParser.WHEN_BLOCK ||
//					 node.getParent().getType() == RulesParser.THEN_BLOCK)) {
//				// in case of an empty method, set the breakpoint on the last line of the empty block.
//				fLineLocation= lineNumber(node.getTokenStopIndex());
//				fLocationFound= true;
//				fLocationType= LOCATION_LINE;
//				return false;
//			}
			List<RulesASTNode> childStatements = node.getChildrenByFeatureId(RulesParser.STATEMENTS);
			if (childStatements != null && childStatements.size() > 1) {
				Collections.sort(childStatements, comparator);
			}
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor#visitAdditiveExpressionNode(com.tibco.cep.studio.core.rules.ast.RulesASTNode)
	 */
	@Override
	public boolean visitAdditiveExpressionNode(RulesASTNode node) {
		 return visit(node,true);
	}
	

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor#visitBinaryRelationNode(com.tibco.cep.studio.core.rules.ast.RulesASTNode)
	 */
	@Override
	public boolean visitBinaryRelationNode(RulesASTNode node) {
		return visit(node,true);
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor#visitDeclaratorNode(com.tibco.cep.studio.core.rules.ast.RulesASTNode)
	 */
	@Override
	public boolean visitDeclaratorNode(RulesASTNode node) {
		
		return visit(node,false);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor#visitLocalVariableDeclNode(com.tibco.cep.studio.core.rules.ast.RulesASTNode)
	 */
	@Override
	public boolean visitLocalVariableDeclNode(RulesASTNode node) {
		
		return visit(node,false);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor#visitMappingBlockNode(com.tibco.cep.studio.core.rules.ast.RulesASTNode)
	 */
	@Override
	public boolean visitMappingBlockNode(RulesASTNode node) {
		
		return visit(node,true);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor#visitMethodCallNode(com.tibco.cep.studio.core.rules.ast.RulesASTNode)
	 */
	@Override
	public boolean visitMethodCallNode(RulesASTNode node) {
		
		return visit(node,true);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor#visitMultiplicativeExpressionNode(com.tibco.cep.studio.core.rules.ast.RulesASTNode)
	 */
	@Override
	public boolean visitMultiplicativeExpressionNode(RulesASTNode node) {
		
		return visit(node,true);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor#visitPredicatesNode(com.tibco.cep.studio.core.rules.ast.RulesASTNode)
	 */
	@Override
	public boolean visitPredicatesNode(RulesASTNode node) {
		
		return visit(node,true);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor#visitPrimaryAssignmentExpressionNode(com.tibco.cep.studio.core.rules.ast.RulesASTNode)
	 */
	@Override
	public boolean visitPrimaryAssignmentExpressionNode(RulesASTNode node) {
		
		return visit(node,true);
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor#visitRelationExpressionNode(com.tibco.cep.studio.core.rules.ast.RulesASTNode)
	 */
	@Override
	public boolean visitRelationExpressionNode(RulesASTNode node) {
		
		return visit(node,true);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor#visitReturnStatementNode(com.tibco.cep.studio.core.rules.ast.RulesASTNode)
	 */
	@Override
	public boolean visitReturnStatementNode(RulesASTNode node) {
		
		return visit(node,true);
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor#visitTypeNode(com.tibco.cep.studio.core.rules.ast.RulesASTNode)
	 */
	@Override
	public boolean visitTypeNode(RulesASTNode node) {
		
		return visit(node,false);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor#visitUnaryExpressionNode(com.tibco.cep.studio.core.rules.ast.RulesASTNode)
	 */
	@Override
	public boolean visitUnaryExpressionNode(RulesASTNode node) {
		
		return visit(node,true);
	}
	
	@Override
	public boolean visitPrimaryExpressionNode(RulesASTNode node) {
		return visit(node,true);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.rules.ast.DefaultRulesASTNodeVisitor#visitVariableDeclaratorNode(com.tibco.cep.studio.core.rules.ast.RulesASTNode)
	 */
	@Override
	public boolean visitVariableDeclaratorNode(RulesASTNode node) {
		boolean hasInitializer = node.getChildOfType(RulesParser.EXPRESSION) != null ||
		 node.getChildOfType(RulesParser.INITIALIZER) != null;
		if (visit(node,false) && hasInitializer) {
			int startLine = lineNumber(node.getTokenStartIndex());
            
			if (fLineNumber == startLine) {
				fLineLocation= startLine;
				fLocationFound= true;
				fLocationType= LOCATION_LINE;
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	

}
