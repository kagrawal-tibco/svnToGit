package com.tibco.cep.studio.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IElementStateListener;
import org.eclipse.ui.texteditor.IStatusField;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorExtension;

import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.cep.studio.ui.editors.rules.text.IReconcilingListener;
import com.tibco.cep.studio.ui.editors.utils.Messages;

public abstract class AbstractRuleFormEditor extends AbstractSaveableEntityEditorPart implements ITextEditorExtension, IReconcilingListener, IElementStateListener, IPropertyChangeListener {

	protected class DirtyListener implements IPropertyListener {

		@Override
		public void propertyChanged(Object source, int propId) {
			if (propId == IEditorPart.PROP_DIRTY && source instanceof RulesEditor) {
				setDirty(((RulesEditor)source).isDirty());
			}
		}
		
	}
	
	protected int pageIndex;
    protected RulesEditor rulesEditor;
    protected DirtyListener dirtyListener = new DirtyListener();
    
	public RulesEditor getRulesEditor() {
		return rulesEditor;
	}
	
	protected void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		saving = true;
		rulesEditor.doSave(monitor);
		if (isDirty()) {
			setDirty(false);
		}
		getFormViewer().refreshRulesFormSourceViewerConfiguration(); // re-reconcile after save, in the case that something has changed
		saving = false;
	}

	protected void setDirty(boolean dirty) {
		if (dirty != isModified) {
			isModified = dirty;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}
	
	@Override
	public void doSaveAs() {
		super.doSaveAs();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		rulesEditor.removeReconcileListener(this);
		super.dispose();
		rulesEditor = null;
	}

	@Override
	protected void setActivePage(int pageIndex) {
		if(isFormPage(pageIndex)){
			boolean formEditorUsable = true;
			String message = "";
			if (rulesEditor.hasSyntaxErrors()) {
				formEditorUsable = false;
				message = Messages.getString("RuleFormEditor.UnableToUseFormEditor.ERROR");
			} else if (!rulesEditor.hasConditionsSection()) {
				formEditorUsable = false;
				message = Messages.getString("RuleFormEditor.UnableToUseFormEditor.NoConditionsSection.ERROR");
			} else if (!rulesEditor.hasActionsSection()) {
				formEditorUsable = false;
				message = Messages.getString("RuleFormEditor.UnableToUseFormEditor.NoActionsSection.ERROR");
			}
			if (!formEditorUsable) {
				MessageDialog.openError(getSite().getShell(), Messages.getString("RuleFormEditor.ERROR"), message);
				setActivePage(pageIndex == 0 ? 1 : 0);
				return;
			}
		}
		super.setActivePage(pageIndex);
	}

	@Override
	protected void pageChange(int newPageIndex) {
		if(isFormPage(newPageIndex)){
			boolean formEditorUsable = true;
			String message = "";
			if (rulesEditor.hasSyntaxErrors()) {
				formEditorUsable = false;
				message = Messages.getString("RuleFormEditor.UnableToUseFormEditor.ERROR");
			} else if (!rulesEditor.hasConditionsSection()) {
				formEditorUsable = false;
				message = Messages.getString("RuleFormEditor.UnableToUseFormEditor.NoConditionsSection.ERROR");
			} else if (!rulesEditor.hasActionsSection()) {
				formEditorUsable = false;
				message = Messages.getString("RuleFormEditor.UnableToUseFormEditor.NoActionsSection.ERROR");
			}
			if (!formEditorUsable) {
				MessageDialog.openError(getSite().getShell(), Messages.getString("RuleFormEditor.ERROR"), message);
				setSourceAsActivePage();
				rulesEditor.setEnabled(true);
				return;
			}
			
			refreshFormViewers();
			rulesEditor.setEnabled(false);
		} else {
			rulesEditor.setEnabled(true);
		}
		super.pageChange(newPageIndex);
	}

	protected void setSourceAsActivePage() {
		Boolean isFormFirstPage = EditorsUIPlugin.getDefault().getPreferenceStore().getBoolean(getShowFormPagePreferenceConstant());
		if (isFormFirstPage) {
			setActivePage(1);
		} else {
			setActivePage(0);
		}
	}

//	private boolean isSourcePage(int index) {
//		return !isFormPage(index);
//	}
	
	protected boolean isFormPage(int index) {
		Boolean isFormFirstPage = EditorsUIPlugin.getDefault().getPreferenceStore().getBoolean(getShowFormPagePreferenceConstant());
		if(isFormFirstPage && index == 0){
			return true;
		} else if (!isFormFirstPage && index == 1) {
			return true;
		}

		return false;
	}

	protected abstract String getShowFormPagePreferenceConstant();
	protected abstract AbstractRuleEntitiesFormViewer getFormViewer();
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		try{
			String property = event.getProperty();
			if(property.equalsIgnoreCase(getShowFormPagePreferenceConstant())){
				doSave(new NullProgressMonitor()); //If the editor not saved
				rulesEditor.removePropertyListener(dirtyListener);
				initPages();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	protected void initPages() throws PartInitException {
		removePage(1);removePage(0);addFormPage();
	}

	@Override
	public void addRulerContextMenuListener(IMenuListener listener) {
		getRulesEditor().addRulerContextMenuListener(listener);
	}

	@Override
	public boolean isEditorInputReadOnly() {
		return getRulesEditor().isEditorInputReadOnly();
	}

	@Override
	public void removeRulerContextMenuListener(IMenuListener listener) {
		getRulesEditor().removeRulerContextMenuListener(listener);		
	}

	@Override
	public void setStatusField(IStatusField field, String category) {
		if (getRulesEditor() != null) {
			getRulesEditor().setStatusField(field, category);		
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#doRefresh(org.eclipse.core.resources.IFile)
	 */
	public void doRefresh(IFile file){
		if(isFormPage(getActivePage())){
			refreshFormViewers();
			boolean formEditorUsable = true;
			String message = "";
			if (rulesEditor.hasSyntaxErrors()) {
				formEditorUsable = false;
				message = Messages.getString("RuleFormEditor.SwitchToSourceEditor.INFO");
			} else if (!rulesEditor.hasConditionsSection()) {
				formEditorUsable = false;
				message = Messages.getString("RuleFormEditor.NoConditionsSection.INFO");
			} else if (!rulesEditor.hasActionsSection()) {
				formEditorUsable = false;
				message = Messages.getString("RuleFormEditor.NoActionsSection.INFO");
			}
			if (!formEditorUsable) {
				// need to switch to source editor, as form editor is no longer valid
				setSourceAsActivePage();
				MessageDialog.openError(getSite().getShell(), Messages.getString("RuleFormEditor.ERROR"), message);
			}
		}
//		if(getActivePage() == 1 
//				|| getActivePage() == -1 /* no editor in focus */){
//			refreshFormViewers();
//			if (rulesEditor.hasSyntaxErrors()) {
//				// need to switch to source editor, as form editor is no longer valid
//				setActivePage(getActivePage() == 0 ? 1 : 0);
//				MessageDialog.openError(getSite().getShell(), Messages.getString("RuleFormEditor.ERROR"), Messages.getString("RuleFormEditor.SwitchToSourceEditor.INFO"));
//			}
//		}
	}
	
	protected void refreshFormViewers() {
		getFormViewer().refresh();		
	}

	@Override
	public void reconciled(Object result) {
	}
	
	@Override
	public void elementContentAboutToBeReplaced(Object element) {
	}

	@Override
	public void elementContentReplaced(Object element) {
		if (element.equals(rulesEditor.getEditorInput()) && element instanceof FileEditorInput) {
			doRefresh(((FileEditorInput) element).getFile());
		}
	}

	@Override
	public void elementDeleted(Object element) {
	}

	@Override
	public void elementDirtyStateChanged(Object element, boolean isDirty) {
	}

	@Override
	public void elementMoved(Object originalElement, Object movedElement) {
	}
	
	@Override
	public Object getAdapter(Class adapter) {
		if(adapter == ITextEditor.class) {
			return getRulesEditor();
		} else {
			return super.getAdapter(adapter);
		}
	}

}
