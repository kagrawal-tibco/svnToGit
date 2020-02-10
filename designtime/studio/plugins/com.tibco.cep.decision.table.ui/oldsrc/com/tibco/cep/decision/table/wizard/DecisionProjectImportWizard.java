package com.tibco.cep.decision.table.wizard;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.ui.dialogs.DiagnosticDialog;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.cep.decision.table.legacy.ImportDecisionTablesOperation;
import com.tibco.cep.decision.table.legacy.validator.LegacyOntologyValidator;
import com.tibco.cep.studio.common.legacy.LegacyOntCompliance;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * 
 * @author sasahoo
 * @author aathalye
 */
public class DecisionProjectImportWizard extends Wizard implements IImportWizard {
	
	private DecisionProjectImportWizardPage mainPage;
	
	private LegacyOntologyValidator validator;
	
	/**
	 * The designer project selected if any selected
	 */
	private IStructuredSelection _selection;

	public DecisionProjectImportWizard() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		String dpPath = mainPage.getDecisionProjectPath();
		String designerProjectName = mainPage.getSelectedDesignerProjectName();
		
		String workspacePath = StudioResourceUtils.getCurrentWorkspacePath();
		StringBuilder sb = new StringBuilder(workspacePath);
		sb.append(Path.SEPARATOR);
		sb.append(designerProjectName);
		String designerProjectPath = sb.toString();
		
		List<LegacyOntCompliance> compliances = new ArrayList<LegacyOntCompliance>();
		
		importDecisionProject(dpPath, designerProjectPath, compliances);
        if (compliances.size() == 0) {
        	WorkspaceModifyOperation op = 
        		new ImportDecisionTablesOperation(dpPath, designerProjectPath, validator);
        	try {
				getContainer().run(false, true, op);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			IProject project = 
				ResourcesPlugin.getWorkspace().getRoot().getProject(designerProjectName);
			fireProjectImportedNotification(project, new NullProgressMonitor());
			//Switching to Designer perspective
			StudioResourceUtils.switchStudioPerspective();
			return true;
        } else {
        	String errorMessage = Messages.getString("DecisionProjectImportWizardPage.Error.validationFailed");
        	//Format it
        	errorMessage = MessageFormat.format(errorMessage, dpPath, designerProjectName);
        	
        	BasicDiagnostic diagnostic = new BasicDiagnostic("Decision Project Import", 
        			                                         IStatus.ERROR,
        			                                         Messages.getString("DecisionProjectImportWizard.Error.ontology.validation"), 
        			                                         null);
        	for (LegacyOntCompliance ont : compliances) {
        		
        		Object[] details = new Object[] {new StringBuilder(ont.getDetail())};
        		BasicDiagnostic d = new BasicDiagnostic(IStatus.ERROR,
        				                                ont.getSource().getName(), 
        				                                IStatus.ERROR,
        				                                ont.getMessage(),
        				                                details);
        		diagnostic.add(d);
        	}
        	
        	DiagnosticDialog dialog = 
				new DiagnosticDialog(this.getShell(),
						             Messages.getString("DecisionProjectImportWizardPage.Error.title"),
						             errorMessage,
						             diagnostic, Diagnostic.ERROR); 	
        	dialog.create();
			dialog.open();
        	destroy();
        	this.dispose();
        	return false;
        }
	}
	
	private void importDecisionProject(String dpPath, 
			                           String designerProjectPath,
			                           List<LegacyOntCompliance> compliances) {
		//Validate ontology here
		validator = new LegacyOntologyValidator();
		
		try {
			validator.validate(dpPath, designerProjectPath, compliances);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Messages.getString("DecisionProjectImportWizard.Title")); //NON-NLS-1 //$NON-NLS-1$
		this._selection = selection;
		setNeedsProgressMonitor(true);
		mainPage = 
			new DecisionProjectImportWizardPage(Messages.getString("DecisionProjectImportWizard.ImportDesc"), _selection); //NON-NLS-1 //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    public void addPages() {
        super.addPages(); 
        addPage(mainPage);        
    }

	private void fireProjectImportedNotification(IProject project, IProgressMonitor monitor) {
		monitor.subTask("Notifying project import listeners");
		try {
			project.refreshLocal(IProject.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void destroy() {
		validator.getDtStore().destroy();
	}
}
