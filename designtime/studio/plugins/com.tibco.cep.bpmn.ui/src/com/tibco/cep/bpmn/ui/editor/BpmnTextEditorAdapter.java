package com.tibco.cep.bpmn.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

public class BpmnTextEditorAdapter implements ITextEditor {

	private BpmnEditor bpmnEditor;

	BpmnTextEditorAdapter(BpmnEditor bpmnEditor) {
		this.bpmnEditor = bpmnEditor;
	}

	public void close(boolean save) {
		// Implementing interface method
	}

	public void doRevertToSaved() {
		// Implementing interface method
	}

	public IAction getAction(String actionId) {
		// Implementing interface method
		return null;
	}

	public IDocumentProvider getDocumentProvider() {
		return bpmnEditor.getDocumentProvider();
	}

	public IRegion getHighlightRange() {
		// Implementing interface method
		return null;
	}

	public ISelectionProvider getSelectionProvider() {
		// Implementing interface method
		return null;
	}

	public boolean isEditable() {
		// Implementing interface method
		return false;
	}

	public void removeActionActivationCode(String actionId) {
		// Implementing interface method
	}

	public void resetHighlightRange() {
		// Implementing interface method
	}

	public void selectAndReveal(int offset, int length) {
//		System.out.println("Show node");
	}

	public void setAction(String actionId, IAction action) {
		// Implementing interface method
	}

	public void setActionActivationCode(String actionId, char activationCharacter, int activationKeyCode, int activationStateMask) {
		// Implementing interface method
	}

	public void setHighlightRange(int offset, int length, boolean moveCursor) {
		// Implementing interface method
	}

	public void showHighlightRangeOnly(boolean showHighlightRangeOnly) {
		// Implementing interface method
	}

	public boolean showsHighlightRangeOnly() {
		// Implementing interface method
		return false;
	}

	public IEditorInput getEditorInput() {
		return bpmnEditor.getEditorInput();
	}

	public IEditorSite getEditorSite() {
		// Implementing interface method
		return null;
	}

	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		// Implementing interface method
	}

	public void addPropertyListener(IPropertyListener listener) {
		// Implementing interface method
	}

	public void createPartControl(Composite parent) {
		// Implementing interface method
	}

	public void dispose() {
		// Implementing interface method
	}

	public IWorkbenchPartSite getSite() {
		return bpmnEditor.getSite();
	}

	public String getTitle() {
		// Implementing interface method
		return null;
	}

	public Image getTitleImage() {
		// Implementing interface method
		return null;
	}

	public String getTitleToolTip() {
		// Implementing interface method
		return null;
	}

	public void removePropertyListener(IPropertyListener listener) {
		// Implementing interface method
	}

	public void setFocus() {
		// Implementing interface method
	}

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if(adapter.equals(IGraphEditor.class)) {
			return this.bpmnEditor;
		}
		return null;
	}


	public void doSave(IProgressMonitor monitor) {
		// Implementing interface method
	}

	public void doSaveAs() {
		// Implementing interface method
	}

	public boolean isDirty() {
		return false;
	}

	public boolean isSaveAsAllowed() {
		// Implementing interface method
		return false;
	}

	public boolean isSaveOnCloseNeeded() {
		// Implementing interface method
		return false;
	}
}
