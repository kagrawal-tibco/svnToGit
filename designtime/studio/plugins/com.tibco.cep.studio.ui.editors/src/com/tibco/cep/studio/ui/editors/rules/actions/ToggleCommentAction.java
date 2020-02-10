package com.tibco.cep.studio.ui.editors.rules.actions;

import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.TextEditorAction;

import com.tibco.cep.studio.ui.editors.rules.RulesEditor;

public class ToggleCommentAction extends TextEditorAction {

    private ITextOperationTarget fTarget = null;

	public ToggleCommentAction(ResourceBundle bundle, String prefix,
			ITextEditor editor) {
		super(bundle, prefix, editor);
	}

	@Override
	public void update() {
        if (!canModifyEditor()) {
            setEnabled(false);
            return;
        }

        super.update();
	}

	@Override
	public void run() {
        RulesEditor editor = (RulesEditor) getTextEditor();
        try {
            IDocument doc = editor.getDocumentProvider().getDocument(
                    editor.getEditorInput());
            if (doc == null)
                return;
            TextSelection currentSelection = ((TextSelection) editor
                    .getSelectionProvider().getSelection());
            int startLine = currentSelection.getStartLine();
            int endLine = currentSelection.getEndLine();

            // first, determine whether we need to insert or remove comments
            // insert comments for selected range if ANY line in the current
            // selection
            // does not start with '//'. Remove comments if ALL lines in current
            // selection begin with '//'

            boolean insertComments = false;
            for (int i = startLine; i <= endLine; i++) {
                IRegion region = doc.getLineInformation(i);
                String currentLine = doc.get(region.getOffset(), region
                        .getLength());
                StringTokenizer st = new StringTokenizer(currentLine, " \n\t\r");
                if (st.hasMoreTokens()) {
                    String firstToken = st.nextToken();
                    if (!firstToken.startsWith("//")) {
                        insertComments = true;
                        break;
                    }
                } else {
                    // line is blank, insert comments
                    insertComments = true;
                    break;
                }
            }
            int op;
            if (insertComments)
                op = ITextOperationTarget.PREFIX;
            else
                op = ITextOperationTarget.STRIP_PREFIX;

            if (fTarget == null)
                fTarget = (ITextOperationTarget) editor
                        .getAdapter(ITextOperationTarget.class);

            /* delegate the replace to the TextViewer. 
             It prefixes/strips the selected region
             with the string specified in the source 
             viewer configuration's getDefaultPrefixes() method
             */
            if (fTarget != null)
                fTarget.doOperation(op);

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

}
