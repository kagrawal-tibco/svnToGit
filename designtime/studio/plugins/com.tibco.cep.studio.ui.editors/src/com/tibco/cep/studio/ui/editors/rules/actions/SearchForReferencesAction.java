package com.tibco.cep.studio.ui.editors.rules.actions;

import java.util.ResourceBundle;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.TextEditorAction;

import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.cep.studio.ui.search.StudioElementSearchQuery;

public class SearchForReferencesAction extends TextEditorAction {

	public SearchForReferencesAction(ResourceBundle bundle, String prefix,
			ITextEditor editor) {
		super(bundle, prefix, editor);
	}

	@Override
	public void run() {
		
		RulesEditor editor = (RulesEditor) getTextEditor();
		RulesASTNode foundNode = editor.getCurrentNode();
		if (foundNode != null && !foundNode.isDirty()) {
			if (foundNode.getType() == RulesParser.Identifier) {
				foundNode = (RulesASTNode) foundNode.getParent();
			}
			EObject reference = RuleGrammarUtils.getElementReference(foundNode);
			if (reference == null) {
				System.out.println("Could not resolve current selection");
				return;
			}
			StudioElementSearchQuery query = new StudioElementSearchQuery(editor.getProjectName(), reference);
			NewSearchUI.runQueryInBackground(query);

		}
	}
	

}
