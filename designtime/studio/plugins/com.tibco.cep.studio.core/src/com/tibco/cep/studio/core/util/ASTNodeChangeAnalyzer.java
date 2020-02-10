package com.tibco.cep.studio.core.util;

import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

/**
 * This class is responsible for analyzing changes to
 * ANTLR ASTNodes and creating the resulting text edits
 * that would take place.<br><br>
 * This class will analyze node additions, deletions, and
 * replacements.
 * 
 * @author rhollom
 *
 */
public class ASTNodeChangeAnalyzer {
	
	private MultiTextEdit fCurrentEdit; // the aggregated text edit of all of the add/del/replace operations
	private IDocument fDocument;

	private ASTNodeChangeAnalyzer() {
		initialize();
	}
	
	public ASTNodeChangeAnalyzer(IDocument document) {
		this();
		this.fDocument = document;
		
	}

	private void initialize() {
		fCurrentEdit = new MultiTextEdit();
	}

	/**
	 * Returns the aggregate text edit that
	 * results from all of the add/remove/replace
	 * calls
	 * @return
	 */
	public MultiTextEdit getCurrentEdit() {
		return fCurrentEdit;
	}
	
	/**
	 * Analyze the text edit resulting from the addition of <code>addedNode</code>
	 * to <code>parentNode</code> at the <code>insertionIndex</code> of feature <code>featureId</code>.
	 * If <code>insertionIndex == -1</code>, the node will be added to the end of the feature list
	 * @param removedNode
	 * @return
	 */
	public TextEdit analyzeASTNodeAdd(RulesASTNode addedNode, RulesASTNode parentNode, int featureId, int insertionIndex) {
		String nodeText = RuleGrammarUtils.rewriteNode(addedNode);
        int insertionOffset = -1;
        
        List<RulesASTNode> children = parentNode.getChildrenByFeatureId(featureId);
        if (children == null) {
        	return new MultiTextEdit();
        }
        if (insertionIndex == -1) {
            // insert at the end of the list
            insertionIndex = children.size();
        }
        try {
        	int offset = 0;
        	boolean usePreviousLine = false;
        	
            if (insertionIndex == 0 && children.size() == 0) {
                // there are no children yet
                offset = parentNode.getOffset();
                offset += parentNode.getLength();
            } else if (insertionIndex == 0) {
                // the position is at the beginning of the children list
                RulesASTNode firstChild = (RulesASTNode) children.get(0);
                offset = firstChild.getOffset();
                usePreviousLine = true;
            } else if (insertionIndex > 0) {
            	RulesASTNode node = (RulesASTNode) children.get(insertionIndex - 1);
                offset = node.getOffset();
                offset += node.getLength();
            } else {
                return null;
            }
            int line = fDocument.getLineOfOffset(offset);
            if (usePreviousLine && line > 0) {
                line--;
            }
            int lineLength = fDocument.getLineLength(line);
            IRegion region = fDocument.getLineInformation(line);
            insertionOffset = region.getOffset() + lineLength;
        } catch (BadLocationException e) {
        	e.printStackTrace();
        }

        InsertEdit edit = new InsertEdit(insertionOffset, nodeText);
		addEdit(edit);
		return edit;
	}
	
	/**
	 * Analyze the text edit resulting from the addition of <code>addedNode</code>
	 * to <code>parentNode</code> at the <code>featureId</code>
	 * @param removedNode
	 * @return
	 */
	public TextEdit analyzeASTNodeAdd(RulesASTNode addedNode, RulesASTNode parentNode, int featureId) {
		return analyzeASTNodeAdd(addedNode, parentNode, featureId, -1);
	}
	
	/**
	 * Analyze the text edit resulting from the removal of <code>removedNode</code>
	 * @param removedNode
	 * @return
	 */
	public TextEdit analyzeASTNodeDelete(RulesASTNode removedNode) {
		DeleteEdit edit = new DeleteEdit(removedNode.getOffset(), removedNode.getLength());
		addEdit(edit);
		return edit;
	}

	/**
	 * Analyze the text edit resulting from the replacement of <code>oldNode</code>
	 * with the inserted text
	 * @param removedNode
	 * @return
	 */
	public TextEdit analyzeASTNodeReplace(RulesASTNode oldNode, String newText) {
		ReplaceEdit edit = new ReplaceEdit(oldNode.getOffset(), oldNode.getLength(), newText);
		addEdit(edit);
		return edit;
	}
	
	private void addEdit(TextEdit edit) {
		fCurrentEdit.addChild(edit);
	}
	
}
