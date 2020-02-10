package com.tibco.cep.studio.ui.wizards.export;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.cli.studiotools.GenerateClassCLI;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;


/**
 * 
 * @author smarathe
 *
 */
public class GenerateClassesWizard extends Wizard implements IExportWizard {

	GenerateClassesWizardPage page;
	IProject project;
//	private final static String FLAG_GENERATE_CLASS_HELP = "-h"; 	// Prints help
	private final static String FLAG_GENERATE_CLASS_OVERWRITE = "-x"; 	// Overwrite classes
	private final static String FLAG_GENERATE_CLASS_OUTPUTDIR = "-o"; 	// Output root directory to generate classes in
	private final static String FLAG_GENERATE_CLASS_PROJECTPATH = "-p"; 	// Path of the Studio project
	private final static String FLAG_GENERATE_CLASS_PROJECTNAME = "-n"; 	// Name of the Studio project
	private final static String FLAG_GENERATE_CLASS_EXTENDED_CP = "-cp";
	private boolean initialize = true;
	private IWorkbenchWindow window;
	private Map<String, String> argsMap = new HashMap<String, String>();
	
	public GenerateClassesWizard() {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		setWindowTitle("Generate Classes");
	}

	@Override
	public boolean performFinish() {
		GenerateClassCLI classes = new GenerateClassCLI();
		argsMap.put(FLAG_GENERATE_CLASS_OVERWRITE, page.getOverWriteExistingClasses());
		argsMap.put(FLAG_GENERATE_CLASS_OUTPUTDIR, page.getOutputDirectory());
		argsMap.put(FLAG_GENERATE_CLASS_PROJECTPATH, project.getLocation().toOSString());
		argsMap.put(FLAG_GENERATE_CLASS_PROJECTNAME, project.getName());
		argsMap.put(FLAG_GENERATE_CLASS_EXTENDED_CP, page.getExtendedClasspath());
		
		try {
			classes.runOperation(argsMap);
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(window.getShell(),"Generate Classes" , "Error generating classes for studio project");
			return false;
		}
		MessageDialog.openInformation(this.window.getShell(),
				"Generate Classes", "Generated classes for Studio Project");
		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		Object resource = selection.getFirstElement();
		if(resource instanceof IResource ) {
			project = StudioResourceUtils.getCurrentProject(selection);
			page = new GenerateClassesWizardPage("Generate Classes");
		} else {
			MessageDialog.openError(window.getShell(),"Generate Classes" , "Select a valid BE Project to Generate Classes");
			initialize = false;
			return;
		}
	}

	public void addPages() {
		super.addPages();
		if(initialize)
			addPage(page);
	}

}
