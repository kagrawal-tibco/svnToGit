package com.tibco.cep.ws.Export.wizard;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.isValidProject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ws.WsPlugin;
import com.tibco.cep.ws.utils.Messages;

/**
 * @author majha
 */

public class WsdlExportWizard extends Wizard implements IImportWizard {

	protected String projName;
	private IProject project;

	private boolean initialize;
	private WsdlExportPage wsdlExportPage;
	
	protected  IStructuredSelection _selection;

	public WsdlExportWizard() {
		super();
	}

	@Override
	public boolean performFinish() {
		
		boolean exportFailed = isValidProject(project, getShell(), "Export Wsdl failed!");
		if(!exportFailed){ 
			return false;
		}
		
		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

			protected void execute(IProgressMonitor monitor)
					throws CoreException, InvocationTargetException,
					InterruptedException {
				monitor.beginTask("Exporting Wsdl...", 100);
				monitor.worked(10);
				try {
					wsdlExportPage.performFinish(monitor);
					MessageDialog.openInformation(getShell(), "WSDL Export Log","WSDL Export completed");
				} catch (Exception e) {
					WsPlugin.log(e);
					String localizedMessage = e.getLocalizedMessage();
					String msg = localizedMessage != null? localizedMessage : e.toString();
					MessageDialog.openError(getShell(),
							"WSDL Export Error","error while exporting wsdl\n"+ msg);
						
				}
				monitor.done();
			}

		};
		try {
			String dir =  wsdlExportPage.getWSDLLocation();
			String name = wsdlExportPage.wsdlName;
			File file = new File(dir, name );
			if (file.exists()) {
				boolean openQuestion = MessageDialog.openQuestion(getShell(),
						"Export Wsdl ", file.getName() + " exist on the disk."
								+ "\nDo you want to overwrite?");
				if (!openQuestion) {
					return false;
				}
			}
			getContainer().run(false, true, op);
			if (project != null) {
				project.refreshLocal(IProject.DEPTH_INFINITE, null);
			}
		} catch (Exception e) {
			WsPlugin.log(e);
			String localizedMessage = e.getLocalizedMessage();
			String msg = localizedMessage != null ? localizedMessage : e.toString();
			MessageDialog.openError(getShell(), "Error", msg +"\n\nPlease check Error Log for details.");
			return false;
		}
		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("WSDL Export");
		try {
			_selection = selection;
			project = StudioResourceUtils.getProjectForWizard(selection);
			if (project == null) {
				MessageDialog.openError(getShell(), Messages
						.getString("Project_selection_Error"), Messages
						.getString("Project_selection_Error_Message"));
				initialize = false;
				return;
			}
			this.projName = project.getName();
			setNeedsProgressMonitor(true);
			wsdlExportPage = new WsdlExportPage(project);

		} catch (Exception e) {
			WsPlugin.log(e);
			String localizedMessage = e.getLocalizedMessage();
			String msg = localizedMessage != null? localizedMessage : e.toString();
			MessageDialog.openError(getShell(), "Error", "error while exporting wsdl\n"+ msg);
			initialize = false;
		}
		initialize = true;
	}

	public void addPages() {
		super.addPages();
		if (initialize)
			super.addPage(wsdlExportPage);
	}

	public boolean performCancel() {
		return super.performCancel();
	}
}