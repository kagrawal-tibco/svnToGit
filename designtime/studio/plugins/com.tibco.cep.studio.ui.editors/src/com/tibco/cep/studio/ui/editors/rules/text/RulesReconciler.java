package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.texteditor.ITextEditor;

import com.tibco.cep.studio.ui.editors.AbstractRuleFormEditor;

public class RulesReconciler extends MonoReconciler {

	private class PartListener implements IPartListener {

		@Override
		public void partActivated(IWorkbenchPart part) {
			if (part.equals(fEditor)) {
				forceReconciling();
			} else if (part instanceof AbstractRuleFormEditor) {
				if (fEditor == null) {
					return;
				}
				IEditorInput editorInput = fEditor.getEditorInput();
				IEditorInput editorInput2 = ((AbstractRuleFormEditor) part).getRulesEditor().getEditorInput();
				
				if (editorInput.equals(editorInput2)) {
					forceReconciling();
				}
			}
		}

		@Override
		public void partBroughtToTop(IWorkbenchPart part) {
		}

		@Override
		public void partClosed(IWorkbenchPart part) {
		}

		@Override
		public void partDeactivated(IWorkbenchPart part) {
		}

		@Override
		public void partOpened(IWorkbenchPart part) {
		}
		
	}

	private ITextEditor fEditor;
    private IPartListener fPartListener;
	private IReconcilingStrategy fStrategy;

	public RulesReconciler(ITextEditor editor, IReconcilingStrategy strategy, boolean isIncremental) {
		super(strategy, isIncremental);
		this.fEditor = editor;
		this.fStrategy = strategy;
	}

	@Override
	public void install(ITextViewer textViewer) {
		super.install(textViewer);
		
        fPartListener = new PartListener();
        if (fEditor == null) {
        	return;
        }
        IWorkbenchPartSite site = fEditor.getSite();
        IWorkbenchWindow window = site.getWorkbenchWindow();
        window.getPartService().addPartListener(fPartListener);

	}
	
    public void uninstall() {
        super.uninstall();
        if (fStrategy instanceof RulesReconcilingStrategy) {
        	((RulesReconcilingStrategy) fStrategy).uninstall();
        }
        fStrategy = null;
    	if (fEditor == null) {
    		return;
    	}
        IWorkbenchPartSite site = fEditor.getSite();
        IWorkbenchWindow window = site.getWorkbenchWindow();
        if (window == null) {
        	return;
        }
        if (fPartListener != null) {
        	window.getPartService().removePartListener(fPartListener);
        }
        fPartListener = null;
        fEditor = null;
    }

    public void setEnablement(boolean enabled) {
        if (fStrategy instanceof RulesReconcilingStrategy) {
        	((RulesReconcilingStrategy) fStrategy).setEnablement(enabled);
        	fStrategy.reconcile(null); // re-reconcile
        }
    }
}
