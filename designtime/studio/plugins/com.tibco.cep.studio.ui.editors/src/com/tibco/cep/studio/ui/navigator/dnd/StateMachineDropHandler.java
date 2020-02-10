package com.tibco.cep.studio.ui.navigator.dnd;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.addStateMachineAssocations;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.resetStatemachineCombo;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditor;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditorInput;
import com.tibco.cep.studio.ui.navigator.view.ProjectExplorer;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachineDropHandler {
	
	private Object target;
	private DropTargetEvent dropTargetEvent;
	private boolean isEditorOpen = false;
	private AbstractSaveableEntityEditorPart editor = null;
	private Concept concept;
	/**
	 * @param dropTargetEvent
	 * @param target
	 */
	public StateMachineDropHandler(DropTargetEvent dropTargetEvent, Object target) {
		this.dropTargetEvent = dropTargetEvent;
		this.target = target;
	}

	/**
	 * @return
	 */
	public IStatus handleStateMachineDrop() {
		if (!(dropTargetEvent.data instanceof IStructuredSelection)) return null;
		IStructuredSelection selection = (IStructuredSelection)dropTargetEvent.data;
		if (selection.isEmpty()) { 
			return null;
		}
		IFile targetFile = (IFile)target;
		isEditorOpen = false;
		checkEditorReference(targetFile);
		IProject project = targetFile.getProject();
		String projectName = project.getName();
		if (!isEditorOpen) {
			concept = IndexUtils.getConcept(projectName, 
					                        IndexUtils.getFullPath(targetFile));
		}
		int oldSMInstancesSize = concept.getStateMachines().size();
		Object[] objs = selection.toArray();
		
		for (Object obj : objs) {
			if (obj instanceof IFile) {
				IFile file = (IFile)obj;
				if (!file.getFileExtension().equals("statemachine")) {
				   return null;	
				} else {
					StateMachine stateMachine = 
						(StateMachine)IndexUtils.getEntity(file.getProject().getName(), 
								                           IndexUtils.getFullPath(file),
								                           ELEMENT_TYPES.STATE_MACHINE);
					addStateMachineAssocations(concept, 
							       stateMachine, 
							       false);
					
					//Sandip>> TODO: this has to be synched UI Changes
					//Uncomment this when above done. 
//					try {
//						stateMachine.eResource().save(null);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
			}
		}
		if (diff(oldSMInstancesSize, concept.getStateMachines().size())) {
			try {
				if (isEditorOpen) {
					editor.modified();
					resetStatemachineCombo(((ConceptFormEditor)editor).getConceptFromDesignViewer().getStateMachineCombo(), 
							concept, editor);
				} else {
					ModelUtils.saveEObject(concept);
//					concept.eResource().save(ModelUtils.getPersistenceOptions());

					//Refreshing the Project Explorer
					IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ProjectExplorer.ID);
					if(view!=null){
						((ProjectExplorer)view).getCommonViewer().refresh();
					}
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return Status.OK_STATUS;
		}
		return null;
	}
	
	/**
	 * @param file
	 */
	private void checkEditorReference(IFile file) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		for (IEditorReference reference:page.getEditorReferences()){
			try {
				if (reference.getEditorInput() instanceof ConceptFormEditorInput) {
					ConceptFormEditorInput conceptFormEditorInput = (ConceptFormEditorInput)reference.getEditorInput();
					if(conceptFormEditorInput.getFile().getFullPath().toPortableString().equals(file.getFullPath().toPortableString())) {
						isEditorOpen  = true;
						editor = (AbstractSaveableEntityEditorPart)reference.getEditor(true);
						concept = conceptFormEditorInput.getConcept();
						break;
					}
				}
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param file
	 * @param changed
	 * @return
	 */
	private boolean diff(int oldSize, int newSize) {
		if (newSize > oldSize) {
			return true;
		}
		return false;
	}
}