package com.tibco.cep.studio.ui.statemachine.wizards;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.getConceptEditorReference;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.getDefaultEntityPropertiesMap;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.addCompilable;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.setDefaultExtendedProperties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditor;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineConstants;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;
import com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard;

public class NewStateMachineWizard extends AbstractNewEntityWizard<StateMachineFileCreationWizardPage> {

	private String ownerConceptPath = null;
    private StateMachine stateMachine;
	public static final int YES = 0;
	public static final int NO = 1;
	public static final int CANCEL = 2;
	public static final int DEFAULT = 3;

	public NewStateMachineWizard(){
		setWindowTitle(Messages.getString("new.statemachine.wizard.title"));
	}
	
	@Override
	protected void createEntity(String filename, String baseURI, IProgressMonitor monitor) throws Exception{
		stateMachine = StatesFactory.eINSTANCE.createStateMachine();
		populateEntity(filename, stateMachine);

		String ownerConceptPath = ((StateMachineFileCreationWizardPage)page).getOwnerConcept();
		stateMachine.setOwnerConceptPath(ownerConceptPath);
			
		StateStart startState = StatesFactory.eINSTANCE.createStateStart();
		startState.setName("Start");
		startState.setGUID(GUIDGenerator.getGUID());
		startState.setOwnerStateMachinePath(stateMachine.getFullPath());
		Rule rule = RuleFactory.eINSTANCE.createRule();
		addCompilable(rule, stateMachine, startState, project.getName(), StateMachineConstants.State_ExitAction_Name);
		startState.setExitAction(rule);
		RuleFunction timeOutExpression = ModelUtils.generateDefaultTimeoutExpression(startState);
		addCompilable(timeOutExpression, 
				      stateMachine, 
				      startState, 
				      project.getName(),  
				      StateMachineConstants.State_TimeoutExpression_Name);
		startState.setTimeoutExpression(timeOutExpression);
		
		startState.setExitAction(rule);
		setDefaultExtendedProperties(startState);
		stateMachine.getStateEntities().add(startState);

		StateEnd endState = StatesFactory.eINSTANCE.createStateEnd();
		endState.setName("End");
		endState.setGUID(GUIDGenerator.getGUID());
		endState.setOwnerStateMachinePath(stateMachine.getFullPath());
		rule = RuleFactory.eINSTANCE.createRule();
		addCompilable(rule, stateMachine, endState, project.getName(), StateMachineConstants.State_EntryAction_Name);
		endState.setEntryAction(rule);
		timeOutExpression = ModelUtils.generateDefaultTimeoutExpression(endState);
		addCompilable(timeOutExpression, 
				      stateMachine, 
				      endState, 
				      project.getName(),  
				      StateMachineConstants.State_TimeoutExpression_Name);
		endState.setTimeoutExpression(timeOutExpression);
		setDefaultExtendedProperties(endState);
		stateMachine.getStateEntities().add(endState);
		
		rule = RuleFactory.eINSTANCE.createRule();
		addCompilable(rule, stateMachine, stateMachine, project.getName(), StateMachineConstants.State_TimeoutAction_Name);
		stateMachine.setTimeoutAction(rule);
				
		timeOutExpression = ModelUtils.generateDefaultTimeoutExpression(stateMachine);
		addCompilable(timeOutExpression, 
				      stateMachine, 
				      stateMachine, 
				      project.getName(),  
				      StateMachineConstants.State_TimeoutExpression_Name);
		stateMachine.setTimeoutExpression(timeOutExpression);
		setDefaultExtendedProperties(stateMachine);
		
		//adding default extended Properties Map
		stateMachine.setExtendedProperties(getDefaultEntityPropertiesMap());
		
		StudioResourceUtils.persistEntity(stateMachine, baseURI, project,monitor);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard#performFinish()
	 */
	public boolean performFinish() {
		try {
			IWorkbenchPage workbenchpage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			ConceptFormEditor editor = getConceptEditorReference(workbenchpage,ownerConceptPath);
			if(editor != null){
				if(editor.isModified()){
					String message = editor.getTitle() + " has been modified. Save changes?"; 
					// Show a dialog.
					String[] buttons = new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL };
						MessageDialog d = new MessageDialog(getShell(), "Save Resource",
							null, message, MessageDialog.QUESTION, buttons, 0);
					int choice = d.open();
					if(choice ==  YES){
					   workbenchpage.saveEditor(editor, false);
					}else{
						return false;
					}
				}
			}
			super.performFinish();
			//Updating Concept entity
			if(editor==null){
				Concept concept =(Concept)IndexUtils.getEntity(project.getName(), ((StateMachineFileCreationWizardPage)page).getOwnerConcept());
				concept.getStateMachinePaths().add(stateMachine.getFullPath());
				ModelUtils.saveEObject(concept);
//				concept.eResource().save(ModelUtils.getPersistenceOptions());
				IFile file = IndexUtils.getFile(project.getName(), concept);
				file.refreshLocal(0, null);
			}
			else{
				//Handling if Editor open
				editor.getConcept().getStateMachinePaths().add(stateMachine.getFullPath());
				ModelUtils.saveEObject(editor.getConcept());
//				editor.getConcept().eResource().save(ModelUtils.getPersistenceOptions());
				editor.setSmAssociationFlag(true);
				if(editor.getConceptFromDesignViewer().getSmAssociationViewer().getTable().getItemCount() == 0){
					editor.getConceptFromDesignViewer().getSmAssociationViewer().setInput(editor.getConcept());
				}
				editor.getConceptFromDesignViewer().getSmAssociationViewer().refresh();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard#addPages()
	 */
	@Override
	public void addPages() {
		try {
			if(_selection != null && !_selection.isEmpty()){
				project =  StudioResourceUtils.getProjectForWizard(_selection);
				if(_selection.getFirstElement() instanceof IFile){
					IFile file = (IFile)_selection.getFirstElement();
					ownerConceptPath = IndexUtils.getFullPath(file);
				}
			}
			page = new StateMachineFileCreationWizardPage(getWizardTitle(),_selection, 
					                                      project == null ? null : project.getName(), ownerConceptPath, getEntityType());
			page.setDescription(getWizardDescription());
			page.setTitle(getWizardTitle());
			addPage(page);
       
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected String getEntityType() {
		return StudioWorkbenchConstants._WIZARD_TYPE_NAME_STATEMACHINE;
	}

	@Override
	protected String getWizardDescription() {
		return Messages.getString("new.statemachine.wizard.desc");
	}

	@Override
	protected String getWizardTitle() {
		return Messages.getString("new.statemachine.wizard.title");
	}

	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return StudioUIPlugin.getImageDescriptor("icons/newStateMechineWizard.png");
	}

}
