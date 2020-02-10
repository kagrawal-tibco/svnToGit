package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.part.EditorPart;

import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditorInput;

public class ReadOnlyEmbeddedStateMachineEditor extends StateMachineEditor {

	private EditorPart embeddingEditor;

	private StateMachineEditorInput stateMachineEditorInput;

	private ManagedForm managedForm;

	private Composite composite;

	ReadOnlyEmbeddedStateMachineEditor(EditorPart embeddingEditor) {
		this.embeddingEditor = embeddingEditor;
	}

	EditorPart getEmbeddingEditor(){
		return this.embeddingEditor;
	}

	void setManagedForm(ManagedForm managedForm){
		this.managedForm = managedForm;
	}

	void setContainer(Composite composite){
		this.composite = composite;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (input == null){
			throw new IllegalArgumentException("Editor Input cannot be null");
		}
		this.site = site;
		stateMachineEditorInput = (StateMachineEditorInput) input;
		ReadOnlyStateMachineDiagramManager readOnlyStateMachineDiagramManager = new ReadOnlyStateMachineDiagramManager(this);
		readOnlyStateMachineDiagramManager.setManagedForm(managedForm);
		//need to set the state machine diagram before calling the createPartControl else tools wont initialize
		stateMachineDiagramManager = readOnlyStateMachineDiagramManager;
		readOnlyStateMachineDiagramManager.createPartControl(composite);
		site.setSelectionProvider(this);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void doSaveAs() {
		throw new UnsupportedOperationException();
	}


	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {
	}

	@Override
	public IEditorInput getEditorInput() {
		return stateMachineEditorInput;
	}

	@Override
	public IEditorSite getEditorSite() {
		return site;
	}

	@Override
    public IWorkbenchPartSite getSite() {
		if (site == null) {
			return null;
		}
        return site.getPart().getSite();
    }

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public StateMachine getStateMachine() {
		return stateMachineEditorInput.getStateMachine();
	}

	public void setStateMachine(StateMachine stateMachine){
		if (getStateMachine() != stateMachine){
			stateMachineEditorInput.setStateMachine(stateMachine);
			stateMachineDiagramManager.setStateMachine(stateMachine);
		}
	}

	@Override
	public void enableEdit(boolean enabled) {
		super.enableEdit(false);
	}
}
