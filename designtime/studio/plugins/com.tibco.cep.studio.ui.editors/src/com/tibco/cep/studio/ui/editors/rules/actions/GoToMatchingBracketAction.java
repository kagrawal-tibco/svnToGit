package com.tibco.cep.studio.ui.editors.rules.actions;

import java.util.ResourceBundle;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.TextEditorAction;

import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;

public class GoToMatchingBracketAction extends TextEditorAction {

    private ITextOperationTarget fTarget = null;
    private int previousOffset;
    private boolean running = false;
    
	public GoToMatchingBracketAction(ResourceBundle bundle, String prefix,
			ITextEditor editor) {
		super(bundle, prefix, editor);
	}

	@Override
	public void update() {
        if (!canModifyEditor()) {
            setEnabled(false);
            return;
        }
		RulesEditor editor = (RulesEditor) getTextEditor();
		int currentOffset = editor.getCurrentOffset();
		
        super.update();
	}

	@Override
	public void run() {
		running = true;
		RulesEditor editor = (RulesEditor) getTextEditor();
		IDocument doc = editor.getDocumentProvider().getDocument(
				editor.getEditorInput());
		if (doc == null)
			return;
		RulesASTNode currentNode = editor.getCurrentNode();
		RulesASTNode blockNode = getBlockNode(currentNode);
		if (blockNode == null) {
			return;
		}
		int currentOffset = editor.getCurrentOffset();
		if (currentOffset == blockNode.getOffset() || currentOffset == blockNode.getOffset()+1) {
			if (previousOffset > 0) {
				editor.selectAndReveal(previousOffset, 0);
			} else {
				editor.selectAndReveal(blockNode.getOffset() + blockNode.getLength()-1, 0);
			}
		} else if (currentOffset == blockNode.getOffset() + blockNode.getLength()-1 || currentOffset == blockNode.getOffset() + blockNode.getLength()) {
			editor.selectAndReveal(blockNode.getOffset()+1, 0);
		} else {
			previousOffset = currentOffset;
			editor.selectAndReveal(blockNode.getOffset() + blockNode.getLength()-1, 0);
		}
		running = false;
	}

	private RulesASTNode getBlockNode(RulesASTNode currentNode) {
		while (currentNode != null && currentNode.getType() != RulesParser.BLOCK) {
			currentNode = (RulesASTNode) currentNode.getParent();
		}
		return currentNode;
	}

	public void reset() {
		if (!running) {
			previousOffset = -1;
		}
	}

}
