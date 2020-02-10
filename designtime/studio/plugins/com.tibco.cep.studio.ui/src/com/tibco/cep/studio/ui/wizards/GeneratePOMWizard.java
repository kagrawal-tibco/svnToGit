package com.tibco.cep.studio.ui.wizards;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.internal.events.BuildCommand;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.be.maven.BEMavenPomGenerator;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.dialog.OverwriteMessageDialog;
import com.tibco.cep.studio.ui.preferences.StudioUIPreferenceConstants;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.widgets.StudioWizard;

/**
 * 
 * @author pdeokar
 * 
 */
public class GeneratePOMWizard extends StudioWizard {

	private GeneratePOMWizardPage fDeployableWizardGeneratePOMPage;
	
	private IWorkbenchWindow fWindow;
	
	private IProject fProject;

	/**
	 * @param window
	 * @param title
	 * @param selection 
	 */
	public GeneratePOMWizard(IWorkbenchWindow window, String title, IProject project) {
		setWindowTitle(title);
		setDefaultPageImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/buildEARWizard.png"));
		this.fWindow = window;
		this.fProject = project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		String projectName = this.fProject.getName();
		if (projectName != null) {
			this.fDeployableWizardGeneratePOMPage = new GeneratePOMWizardPage(projectName);
			addPage(this.fDeployableWizardGeneratePOMPage);
		}
	}
	

	


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performCancel()
	 */
	@Override
	public boolean performCancel() {
		return true;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		String projectLocation = fProject.getLocation().toString();	
		Map<String,String> projectArgs = new HashMap<String,String>();
		projectArgs.put("projectPath", projectLocation);
		projectArgs.put("groupId", this.fDeployableWizardGeneratePOMPage.getGroupId());
		projectArgs.put("artifactId", this.fDeployableWizardGeneratePOMPage.getArtifactId());
		projectArgs.put("version", this.fDeployableWizardGeneratePOMPage.getVerison());
		boolean overwrite=false;
		overwrite = StudioUIPlugin.getDefault().getPreferenceStore().getBoolean(StudioUIPreferenceConstants.STUDIO_OVERWRITE_POM);
		if(!overwrite){
			if(new File(projectArgs.get("projectPath").replace('\\', '/') + "/pom.xml").exists()){
				overwrite = OverwriteMessageDialog.openQuestion(fWindow.getShell(),
					Messages.getString("Generate.POM.task"), Messages
							.getString("Generate.POM.file.exists"), StudioUIPlugin.getDefault().getPreferenceStore(), StudioUIPreferenceConstants.STUDIO_OVERWRITE_POM,  Messages
							.getString("Generate.POM.file.always.overwrite"));
			}else
				overwrite = true;
		}
		if(overwrite){
			BEMavenPomGenerator bePomGenerate = new BEMavenPomGenerator();
			bePomGenerate.generatePOM(projectArgs);
			addMavenNature(fProject);
		}
		return true;
	}

	private void addMavenNature( IProject project)
	{
		try
		{			
			IProjectDescription desc = project.getDescription();
			
			String[] prevNatures = desc.getNatureIds();
			String[] newNatures = new String[prevNatures.length + 1];
			
			System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
			
			newNatures[prevNatures.length] = "org.eclipse.m2e.core.maven2Nature";
			desc.setNatureIds(newNatures);
			
			project.setDescription(desc, new NullProgressMonitor());
		
			ICommand[] commands = desc.getBuildSpec();
			List<ICommand> commandList = Arrays.asList( commands );
			ICommand build = new BuildCommand();
			build.setBuilderName("org.eclipse.m2e.core.maven2Builder");
			List<ICommand> modList = new ArrayList<>( commandList );
			modList.add( build );
			desc.setBuildSpec( modList.toArray(new ICommand[]{}));
			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		}
		catch(Exception e)
		{
			StudioUIPlugin.log("Failed to set maven nature to project.");
			StudioUIPlugin.log(e);
		}
	}

	/**
	 * @return
	 */
	public GeneratePOMWizardPage getProjectPage() {
		return fDeployableWizardGeneratePOMPage;
	}

	@Override
	public boolean isGlobalVarAvailable() {
		return true;
	}

	@Override
	public String getProjectName() {
		return this.fProject.getName();
	}

}