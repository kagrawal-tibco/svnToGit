package com.tibco.cep.studio.ui.editors.rules.actions;

import java.util.ResourceBundle;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.TextEditorAction;

import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.refactoring.EntityElementRefactoring;
import com.tibco.cep.studio.core.refactoring.IRefactoringContext;
import com.tibco.cep.studio.core.refactoring.LocalVariableRenameProcessor;
import com.tibco.cep.studio.core.refactoring.StudioRefactoringContext;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.cep.studio.ui.refactoring.RenameElementRefactoringWizard;

/**
 * 
 * @author rhollom
 *
 */
public class RenameLocalVariableAction extends TextEditorAction {
	
	private IResolutionContextProvider fContextProvider;

	public RenameLocalVariableAction(ResourceBundle bundle, String prefix,
			ITextEditor editor, IResolutionContextProvider contextProvider) {
		super(bundle, prefix, editor);
		this.fContextProvider = contextProvider;
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
            
    		RulesASTNode foundNode = editor.getCurrentNode();
    		if (foundNode != null && !foundNode.isDirty()) {
    			EObject reference = RuleGrammarUtils.getElementReference(foundNode);
    			if (reference == null) {
    				System.out.println("Could not resolve current selection");
    				return;
    			}
    			Object elementToRefactor = null;
    			if (reference instanceof VariableDefinition) {
    				VariableDefinition def = (VariableDefinition) reference;
    				elementToRefactor = def;
//    				elementToRefactor = ElementReferenceResolver.resolveVariableDefinitionType(def);
    			} else {
    				elementToRefactor = ElementReferenceResolver.resolveElement((ElementReference) reference, fContextProvider.getResolutionContext((ElementReference) reference));
    			}
    			if (elementToRefactor instanceof VariableDefinition) {
    				performRefactoring((VariableDefinition) elementToRefactor, foundNode);
    			} else {
    				// current selection does not resolve to a variable
    			}
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	protected void performRefactoring(VariableDefinition elementToRefactor, RulesASTNode foundNode) {
		IRefactoringContext context = createRefactoringContext(elementToRefactor);
        RulesEditor editor = (RulesEditor) getTextEditor();
		LocalVariableRenameProcessor processor = new LocalVariableRenameProcessor(context, editor.getDocumentProvider().getDocument(editor.getEditorInput()), editor.getStorage(), foundNode); 
		EntityElementRefactoring refactoring = new EntityElementRefactoring(processor);
		RenameElementRefactoringWizard refactoringWizard = invokeRefactoringWizard(refactoring);
		refactoringWizard.setForcePreviewReview(false);
		processor.setNewName(elementToRefactor.getName());
		
		RefactoringWizardOpenOperation op= new RefactoringWizardOpenOperation(refactoringWizard);
		try {
			op.run(Display.getDefault().getActiveShell(), "Rename Variable");
		} catch (InterruptedException e) {
		}
	}
	
	protected IRefactoringContext createRefactoringContext(
			Object elementToRefactor) {
        RulesEditor editor = (RulesEditor) getTextEditor();
		StudioRefactoringContext context = new StudioRefactoringContext(elementToRefactor, IRefactoringContext.RENAME_REFACTORING, editor.getProjectName());
		return context;
	}
	
	protected RenameElementRefactoringWizard invokeRefactoringWizard(EntityElementRefactoring refactoring) {
		return new RenameElementRefactoringWizard(refactoring, RefactoringWizard.DIALOG_BASED_USER_INTERFACE);
	}

}
