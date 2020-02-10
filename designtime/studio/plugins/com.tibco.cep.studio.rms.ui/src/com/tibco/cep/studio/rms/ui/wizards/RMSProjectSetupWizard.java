package com.tibco.cep.studio.rms.ui.wizards;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.rms.cli.RMSProjectSetupCLI;



/**
 * 
 * @author smarathe
 *
 */
public class RMSProjectSetupWizard extends Wizard {

	private IWorkbenchWindow window;
	RMSProjectSetupWizardPage page;
	IProject project;
	

	private static final String FLAG_RMS_PROJECT_NAME = "-projName";
	private static final String FLAG_BASE_LOCATION = "-baseLocation";
	private static final String FLAG_SOURCE_LOCATION = "-sourceLocation";
	private static final String FLAG_MODULE_TYPE = "-rms";
	private static final String FLAG_MODULE_OPERATION = "setup";
	private static final String FLAG_ACL_PATH = "-aclPath";
	
	Map<String, String> argsMap = new HashMap<String, String>();
	public RMSProjectSetupWizard() {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		setWindowTitle("Setup RMS Project");
	}
	
	@Override
	public boolean performFinish() {
		boolean onSuccess = false;
		RMSProjectSetupCLI projectSetup = new RMSProjectSetupCLI();
		argsMap.put(FLAG_RMS_PROJECT_NAME, page.getProjectName());
		argsMap.put(FLAG_SOURCE_LOCATION, page.getSourceLocation());
		argsMap.put(FLAG_BASE_LOCATION, page.getBaseLocation().equals("")?ResourcesPlugin.getWorkspace().getRoot().getLocation().toPortableString():page.getBaseLocation());
		argsMap.put(FLAG_ACL_PATH, page.getACLFilePath());
		argsMap.put(FLAG_MODULE_TYPE, FLAG_MODULE_OPERATION);
		try {
			projectSetup.runOperation(argsMap);
			MessageDialog.openInformation(this.window.getShell(),
					"RMS Project Setup", "RMS Project is setup successfully");
			onSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			String exceptionMsg = (e.getMessage() != null && !e.getMessage().isEmpty()) ? " " + e.getMessage() : "";
			MessageDialog.openError(window.getShell(),"RMS Project Setup" , "Error setting up RMS Project." + exceptionMsg);
			onSuccess = false;
		}
		return onSuccess;
	}

	@Override
	public void addPages() {
		page = new RMSProjectSetupWizardPage("RMS Setup Project", "Setup RMS Project", null);
		addPage(page);
	}
	
	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}
}
