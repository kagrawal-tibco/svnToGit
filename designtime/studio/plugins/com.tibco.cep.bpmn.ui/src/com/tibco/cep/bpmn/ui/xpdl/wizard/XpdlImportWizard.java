package com.tibco.cep.bpmn.ui.xpdl.wizard;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.xpdl.utils.XpdlImportHelper;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;


/**
 * 
 * @author majha
 *
 */
public class XpdlImportWizard extends Wizard implements IImportWizard{
	
	private XpdlImportWizardPage importXpdlWizardPage;
	@SuppressWarnings("unused")
	private IWorkbenchWindow window;
	private IProject project;
	private boolean initialize;

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		if (initialize) {
			addPage(importXpdlWizardPage);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performCancel()
	 */
	@Override
	public boolean performCancel() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		final String fullFilename = importXpdlWizardPage.getXpdlFileFullPath();
//		final String processName = importXpdlWizardPage.getFileName();
		final IPath fileFolder = importXpdlWizardPage.getContainerFullPath();
		IContainer container = null;
		if(project.getName().equals(fileFolder.toFile().getName()))
			container = project;
		else
			container = project.getParent().getFolder(fileFolder);
		
		final IContainer folder = container;
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
				try {
					monitor.beginTask(Messages.getString("IMPORTING_XPDL"), 100);
					monitor.worked(10);
					if (fullFilename != null) {
						XpdlImportHelper xpdlImportHelper = new XpdlImportHelper(new File(fullFilename), project, folder.getProjectRelativePath());
						Map<String, EObjectWrapper<EClass, EObject>> processesMap = xpdlImportHelper.importXpdl();
						Iterator<Entry<String, EObjectWrapper<EClass, EObject>>> iterator = processesMap.entrySet().iterator();
						while (iterator.hasNext()) {
							Map.Entry<String, EObjectWrapper<EClass,EObject>> entry = (Map.Entry<String, EObjectWrapper<EClass,EObject>>) iterator
									.next();
							saveProcess(entry.getKey(), entry.getValue().getEInstance(), folder, true);
							
						}
						
//						openProcessEditor(process);
					}

				} catch (Exception e) {
					monitor.setTaskName(Messages.getString("IMPORT_XPDL_WIZARD_PAGE_ERROR"));
					monitor.done();
					BpmnUIPlugin.log(e);
					MessageDialog.openError(getShell(),BpmnMessages.getString("xpdlImport_messageDialog_title"), BpmnMessages.getString("xpdlImport_messageDialog_message"));
					throw new RuntimeException(e);
				}
			}
		};
		try {
			getContainer().run(false, true, op);
		} catch (InvocationTargetException e) {
			BpmnUIPlugin.log(e);
		} catch (InterruptedException e) {
			BpmnUIPlugin.log(e);
		}
		return true;
	}
	
	
	protected void saveProcess(String newName, EObject process, IContainer target, boolean overwrite) throws CoreException {
		try {
			IFile file = target.getFile(new Path(newName +"."+ BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION));
			if (!file.exists() || overwrite){ 
					ECoreHelper.serializeModelXMI(file,process);
			}
		} catch (Exception e) {
			BpmnUIPlugin.log("Error while saving",e);
		} 
	}

	public XpdlImportWizardPage getProjectPage() {
		return importXpdlWizardPage;
	}

	public void setProjectPage(XpdlImportWizardPage projectPage) {
		this.importXpdlWizardPage = projectPage;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Messages.getString("IMPORT_XPDL_WIZARD_WINDOW_TITLE"));
		this.window = workbench.getActiveWorkbenchWindow();
		try {
			project= StudioResourceUtils.getProjectForWizard(selection);
			if(project == null){
				MessageDialog.openError(getShell(), Messages.getString("Project_selection_Error"), Messages.getString("Project_selection_Error_Message"));
				initialize = false;
				return;
			}
			importXpdlWizardPage = new XpdlImportWizardPage(selection, project.getName());
		} catch (Exception e) {
			initialize = false;
			BpmnUIPlugin.log(e);
		}
		initialize = true;
	}

}
