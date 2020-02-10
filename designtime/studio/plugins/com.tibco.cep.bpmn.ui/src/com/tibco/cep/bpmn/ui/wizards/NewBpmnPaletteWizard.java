package com.tibco.cep.bpmn.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.utils.BpmnPaletteResourceUtil;
import com.tibco.cep.studio.ui.StudioUIPlugin;
/**
 * 
 * @author mgoel
 *
 */
public class NewBpmnPaletteWizard extends Wizard implements INewWizard {
	
	protected IProject project;
	private IWorkbench workbench;
	protected IStructuredSelection selection;
	protected NewBpmnPaletteNamePage bpmnPaletteNamePage;
	private NewBpmnPaletteDetailPage bpmnPaletteDetailPage;
	//protected NewClusterConfigTemplatePage cddTemplatePage;
	

	public NewBpmnPaletteWizard() {
		setWindowTitle(Messages.getString("new.bpmnpalette.wizard.title"));
	}

	@Override
	public boolean performFinish() {
		BpmnPaletteModel newModel = bpmnPaletteDetailPage.getNewModel();
        IFile bpmnPaletteFile = bpmnPaletteNamePage.createNewFile();
        if (bpmnPaletteFile == null)
            return false;
        
        saveModel(newModel, bpmnPaletteFile);
		// Open an editor on the new file.
		try {
			// this is now done by defining a content type for shared resources
//			cddFile.setCharset(ModelUtils.DEFAULT_ENCODING, new NullProgressMonitor());
			IWorkbenchPage workbenchPage = workbench.getActiveWorkbenchWindow().getActivePage();
			workbenchPage.openEditor
			(new FileEditorInput(bpmnPaletteFile),"com.tibco.cep.bpmn.ui.editors.BpmnPaletteConfigurationEditor");
			
			//PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(cddFile.getLocation().toOSString()).getId())
		} catch (PartInitException pie) {
			StudioUIPlugin.log(pie);
		} 
        return true;
	}
	
	private void saveModel(BpmnPaletteModel bpmnPaletteModel, IFile file) {
		BpmnPaletteResourceUtil.saveBpmnPalette(bpmnPaletteModel, file);
	}
	

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
	}

	public IProject getProject() {
		return project;
	}

	@Override
	public void addPages() {
		
		bpmnPaletteNamePage = new NewBpmnPaletteNamePage(selection);
		bpmnPaletteDetailPage = new NewBpmnPaletteDetailPage();
        addPage(bpmnPaletteNamePage);
        addPage(bpmnPaletteDetailPage);
	}

}
