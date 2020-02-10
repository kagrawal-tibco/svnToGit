package com.tibco.cep.studio.ui.diagrams.actions;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.addStateMachineAssocations;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.resetStatemachineCombo;

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
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
import com.tibco.cep.studio.ui.forms.components.StateMachineResourceSelector;
import com.tibco.cep.studio.ui.navigator.view.ProjectExplorer;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
/**
 * 
 * @author sasahoo
 *
 */
public class AssociateStateMachinesAction implements IWorkbenchWindowActionDelegate,IObjectActionDelegate {

	private ISelection _selection;
	private IStructuredSelection strSelection;
	private IProject project;	
	private IWorkbenchPage page;
	private String ownerConceptpath;
	private boolean isEditorOpen = false;
	private AbstractSaveableEntityEditorPart editor = null;
	private Concept concept;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {

		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			if(_selection != null && _selection instanceof IStructuredSelection){
				strSelection = (IStructuredSelection)_selection;
				
				project = StudioResourceUtils.getCurrentProject(strSelection);
				
				IFile file = (IFile)strSelection.getFirstElement();
				isEditorOpen = false;
				checkEditorReference(file);
				if(!isEditorOpen){
					concept = IndexUtils.getConcept(project.getName(),IndexUtils.getFullPath(file));
				}
				ownerConceptpath = IndexUtils.getFullPath(file);
				try{
					openStateMachineResourceSelector(page.getWorkbenchWindow().getShell(), concept, project, ownerConceptpath,false, isEditorOpen);
					
					if(isEditorOpen){
						editor.modified();
						resetStatemachineCombo(((ConceptFormEditor)editor).getConceptFromDesignViewer().getStateMachineCombo(), 
								                            concept, editor);
					}
				}
				catch(Exception e2){
					e2.printStackTrace();
				}
			}else
				return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param shell
	 * @param concept
	 * @param project
	 * @param ownerConceptpath
	 * @param isEditor
	 * @param isEditorOpen
	 */
	@SuppressWarnings("unchecked")
	public static void openStateMachineResourceSelector(Shell shell, 
														Concept concept, 
														IProject project, 
														String ownerConceptpath, 
														boolean isEditor, 
														boolean isEditorOpen){
		try{
			StateMachineResourceSelector picker = new StateMachineResourceSelector(shell,
														project.getName(), concept.getStateMachines());
			if (picker.open() == Dialog.OK) {
				if (picker.getFirstResult() != null) {
					Set<Object> smObjects =(Set<Object>) picker.getFirstResult();
					
					List<StateMachine> stateMachines = concept.getStateMachines();
//					for (StateMachine sm : stateMachines) {
						//We need to clear out bidirectional references
//						sm.setOwnerConceptPath(null);
						//Also save the resource
//						sm.eResource().save(null);
//					}
					concept.getStateMachinePaths().clear();
					for (Object sm : smObjects) {
						IFile smFile = (IFile)sm;
						String fullPath = IndexUtils.getFullPath(smFile);
						StateMachine stateMachine = (StateMachine)IndexUtils.getEntity(project.getName(), 
																	fullPath, 
																	ELEMENT_TYPES.STATE_MACHINE);
						
						addStateMachineAssocations(concept, stateMachine, false);
						
						//Sandip>> TODO: this has to be synched UI Changes
						//Uncomment this when above done. 
//						stateMachine.eResource().save(null);
					}
					if (!isEditor && !isEditorOpen) {
						ModelUtils.saveEObject(concept);
//						concept.eResource().save(ModelUtils.getPersistenceOptions());

						//Refreshing the Project Explorer
						IViewPart view = 
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ProjectExplorer.ID);
						if(view != null) {
							((ProjectExplorer)view).getCommonViewer().refresh();
						}
					}
				} else {
					concept.getStateMachines().clear();
					concept.getStateMachinePaths().clear();
					if (!isEditor && !isEditorOpen) {
						ModelUtils.saveEObject(concept);
//						concept.eResource().save(ModelUtils.getPersistenceOptions());
						
						//Refreshing the Project Explorer
						IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ProjectExplorer.ID);
						if (view != null) {
							((ProjectExplorer)view).getCommonViewer().refresh();
						}
					}
				}
			}
		}
		catch(Exception e2) {
			e2.printStackTrace();
		}
	}
	
	/**
	 * @param file
	 */
	private void checkEditorReference(IFile file) {
		for (IEditorReference reference : page.getEditorReferences()) {
			try {
				if (reference.getEditorInput() instanceof ConceptFormEditorInput) {
					ConceptFormEditorInput conceptFormEditorInput = 
						(ConceptFormEditorInput)reference.getEditorInput();
					if (conceptFormEditorInput.getFile().getFullPath().toPortableString().equals(file.getFullPath().toPortableString())) {
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

	public void selectionChanged(IAction action, ISelection selection) {
		_selection = selection;
	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
	}
}