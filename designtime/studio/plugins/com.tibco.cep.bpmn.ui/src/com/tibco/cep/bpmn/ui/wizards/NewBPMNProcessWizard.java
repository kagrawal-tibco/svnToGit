package com.tibco.cep.bpmn.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.INewWizard;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnEditorInput;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;
import com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;
import com.tibco.cep.studio.ui.wizards.IDiagramEntitySelection;


public class NewBPMNProcessWizard extends AbstractNewEntityWizard<EntityFileCreationWizard> implements INewWizard {

	private BpmnEditorInput input;
	private boolean createPrivateProcess;

	public NewBPMNProcessWizard() {
		createPrivateProcess = false;
		setWindowTitle(Messages.getString("new.bpmn.process.wizard.title"));
	}
	
	public NewBPMNProcessWizard(IDiagramEntitySelection diagramEntitytSelection,String currentProjectName) {
		this();
 		setDefaultPageImageDescriptor(getDefaultPageImageDescriptor());
		this.diagramEntitySelect = diagramEntitytSelection;
		this.currentProjectName = currentProjectName;
	}
	
	public NewBPMNProcessWizard(IDiagramEntitySelection diagramEntitytSelection,String currentProjectName, boolean isPrivate) {
		this(diagramEntitytSelection, currentProjectName);
		this.createPrivateProcess = isPrivate;
	}

	@Override
	protected void createEntity(String filename, String baseURI,
			IProgressMonitor monitor) throws Exception {
//		File file = new File(fName);
		IFile file = getModelFile();
		if(file == null || !file.exists()){
//			file.createNewFile();			
			//BPMNCorePlugin.getDefault().getBpmnModelManager().createNewBpmnIndex(file);
		}
		
	}
	
	@Override
	protected String getEntityType() {
		return StudioWorkbenchConstants._WIZARD_TYPE_NAME_BPMN;
	}
	
	@Override
	protected String getWizardDescription() {
		return Messages.getString("new.bpmn.process.wizard.desc");
	}
	
		
	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return BpmnUIPlugin.getDefault().getImageDescriptor("icons/appicon48x48.gif");
	}
	


	@Override
	protected String getWizardTitle() {
		return Messages.getString("new.bpmn.process.wizard.title");
	}
	
	public IEditorInput getEditorInput(){
		if(input==null){
			input = new BpmnEditorInput(getModelFile());
			input.setDescription(page.getTypeDesc());
			input.setIsPrivateProcess(createPrivateProcess);
		}
		return input;
	}
	
	@Override
	public boolean performFinish() {
		return super.performFinish();
	}
	
	public EObjectWrapper<EClass, EObject> getCreatedProcessModel(){
		if(input != null)
			return ((BpmnEditorInput)input).getProcessModel();
		
		return null;
	}
	
	public EObjectWrapper<EClass, EObject> getCreatedProcessModelForCallActivityTask(){
		if(input != null)
			return ((BpmnEditorInput)input).getProcessModelForCallActivityTask();
		
		return null;
	}
	
}
