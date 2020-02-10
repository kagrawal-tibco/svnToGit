package com.tibco.cep.decision.table.wizard;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.cli.GenerateDecisionTableClassCLI;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;



/**
 * 
 * @author smarathe
 *
 */
public class GenerateDTClassWizard extends Wizard implements IExportWizard {

	GenerateDTClassWizardPage page;
	IProject project;
	protected IStructuredSelection selection;
	protected IWorkbenchWindow workbenchWindow;
//	private final static String FLAG_GENERATE_DT_CLASS_HELP = "-h"; 	// Prints help
	private final static String FLAG_GENERATE_DT_CLASS_OVERWRITE = "-x"; 	// Overwrite classes
	private final static String FLAG_GENERATE_DT_CLASS_OUTPUTDIR = "-o"; 	// Output root directory to generate classes in
	private final static String FLAG_GENERATE_DT_CLASS_PROJECTPATH = "-p"; 	// Path of the Studio project
	private final static String FLAG_GENERATE_DT_CLASS_DTPATH = "-d"; 	// Name of the Studio project
	private final static String FLAG_GENERATE_DT_CLASS_EARPATH = "-e";
	private final static String FLAG_GENERATE_CLASS_EXTENDED_CP = "-cp";
	
	private IWorkbenchWindow window;
	private Map<String, String> argsMap = new HashMap<String, String>();
	
	public GenerateDTClassWizard() {
		this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		setWindowTitle("Generate Classes");
	}

	@Override
	public boolean performFinish() {
		GenerateDecisionTableClassCLI classes = new GenerateDecisionTableClassCLI();
		argsMap.put(FLAG_GENERATE_DT_CLASS_OVERWRITE, page.getOverWriteExistingClasses());
		argsMap.put(FLAG_GENERATE_DT_CLASS_OUTPUTDIR, page.getOutputDirectory());
		argsMap.put(FLAG_GENERATE_DT_CLASS_PROJECTPATH, project.getLocation().toOSString());
		argsMap.put(FLAG_GENERATE_DT_CLASS_DTPATH, page.getDTPath() + ".rulefunctionimpl");
		argsMap.put(FLAG_GENERATE_CLASS_EXTENDED_CP, page.getExtendedClasspath());
		argsMap.put(FLAG_GENERATE_DT_CLASS_EARPATH, page.getEARSPath());
		
		try {
			classes.runOperation(argsMap);
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(window.getShell(),"Generate Classes" , "Error generating classes for studio project");
			return false;
		}
		MessageDialog.openInformation(this.window.getShell(),
				"Generate DT Class", "Generated classes for Studio Project");
		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		project = StudioResourceUtils.getCurrentProject(selection);
		page = new GenerateDTClassWizardPage("Generate Classes", selection);
	}

	public void addPages() {
		addPage(page);
	}

}
