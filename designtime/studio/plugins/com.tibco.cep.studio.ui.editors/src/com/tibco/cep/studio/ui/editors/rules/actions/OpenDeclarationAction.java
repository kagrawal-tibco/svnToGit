package com.tibco.cep.studio.ui.editors.rules.actions;

import java.util.ResourceBundle;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.TextEditorAction;

import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.cep.studio.ui.editors.rules.text.XsltHyperlink;
import com.tibco.cep.studio.ui.editors.rules.utils.RulesEditorUtils;

public class OpenDeclarationAction extends TextEditorAction {

	private IResolutionContextProvider fContextProvider;
	private ISourceViewer fSourceViewer;
	private int fSourceType;

	public OpenDeclarationAction(ResourceBundle bundle, String prefix,
			ITextEditor editor, IResolutionContextProvider contextProvider, ISourceViewer sourceViewer, int sourceType) {
		super(bundle, prefix, editor);
		this.fContextProvider = contextProvider;
		this.fSourceViewer = sourceViewer;
		this.fSourceType = sourceType;
	}

	@Override
	public void run() {
		RulesEditor editor = (RulesEditor) getTextEditor();
		RulesASTNode foundNode = editor.getCurrentNode();
		if (foundNode != null && !foundNode.isDirty()) {
			if (foundNode.getType() == RulesParser.Identifier) {
				foundNode = (RulesASTNode) foundNode.getParent();
			}
			if (foundNode.getType() == RulesParser.StringLiteral) {
				String text = foundNode.getText();
				if (text.startsWith(RulesEditorUtils.XPATH_PREFIX) || text.startsWith(RulesEditorUtils.XSLT_PREFIX)) {
					XsltHyperlink link = new XsltHyperlink(foundNode, editor.getProjectName(), fSourceViewer.getDocument(), fContextProvider, canModifyEditor());
					link.open();
					return;
				}
			}
			OpenNodeAction action = new OpenNodeAction(fSourceViewer, fSourceType, foundNode, editor.getProjectName(), fContextProvider);
			action.run();
		}
	}

}
