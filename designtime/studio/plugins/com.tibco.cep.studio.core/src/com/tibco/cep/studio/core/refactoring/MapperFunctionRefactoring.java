package com.tibco.cep.studio.core.refactoring;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringTickProvider;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.MultiTextEdit;

import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.migration.StudioProjectMigrationContext;
import com.tibco.cep.studio.core.util.MapperFunctionMigrator;

public class MapperFunctionRefactoring extends Refactoring {

	private class FileCollector implements IResourceVisitor {

		private List<IFile> fResources = new ArrayList<IFile>();
		
		public List<IFile> getResources() {
			return fResources;
		}

		@Override
		public boolean visit(IResource resource) throws CoreException {
			if (!(resource instanceof IFile)) {
				return true;
			}
			IFile file = (IFile) resource;
			// TODO : Add support for Expiry Actions and State Machines
			if (/*IndexUtils.isEntityType(file) ||*/ IndexUtils.isRuleType(file) || IndexUtils.STATEMACHINE_EXTENSION.equalsIgnoreCase(file.getFileExtension())) {
				fResources.add(file);
			}
			return false;
		}
		
	}

	private String fProjectName;
	private StudioProjectMigrationContext fContext;
	private boolean fUpdateVersion;
	
	public MapperFunctionRefactoring(String projectName) {
		this.fProjectName = projectName;
		this.fContext = new StudioProjectMigrationContext(new File(projectName), XPATH_VERSION.XPATH_20);
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		pm.beginTask("Checking final conditions", 1);
		pm.done();
		return new RefactoringStatus();
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		pm.beginTask("Checking initial conditions", 1);
		RefactoringStatus status = new RefactoringStatus();
		StudioProjectConfiguration projectConfiguration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(fProjectName);
		if (projectConfiguration != null && projectConfiguration.getXpathVersion() == XPATH_VERSION.XPATH_10) {
			status.addError("Project '"+fProjectName+"' is currently set to XPath version 1.0.\nChange this to 2.0 in the project's build path before continuing");
		}
		pm.done();
		return status;
	}

	
	@Override
	protected RefactoringTickProvider doGetRefactoringTickProvider() {
		return new RefactoringTickProvider(1, 1, 80, 1);
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		pm.beginTask("Updating mapper functions", 1);
		if (fProjectName == null) {
			return null;
		}
		FileCollector collector = new FileCollector();
		IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject(fProjectName);
		proj.accept(collector);
		List<IFile> resources = collector.getResources();
		SubProgressMonitor mon = new SubProgressMonitor(pm, 1);
		mon.beginTask("Processing", resources.size());
		CompositeChange change = new CompositeChange("File changes");
		for (IFile file : resources) {
			if (mon.isCanceled()) {
				return change;
			}
			mon.setTaskName("Processing file "+file.getName());
			processFile(file, change, null);
			mon.worked(1);
		}
		mon.done();
		return change;
	}

	private void processFile(IFile file, CompositeChange change, RefactoringStatus resultingStatus) {
		if (file.getLocation() == null || file.isLinked(IResource.CHECK_ANCESTORS)) {
			return; // linked file?  Eaither way, don't process
		}
		File jFile = file.getLocation().toFile();

		TextFileChange fileChange = new TextFileChange("Changes to "+file.getName(), file);
		if (createReplaceEdits(jFile, fileChange)) {
			change.add(fileChange);
		}
	}

	private boolean createReplaceEdits(File jFile,
			TextFileChange fileChange) {
		boolean changed = false;
		MapperFunctionMigrator migrator = new MapperFunctionMigrator();
		migrator.setUpdateVersions(fUpdateVersion);
		MultiTextEdit textEdits = migrator.createTextEdits(this.fContext, jFile);

		if (textEdits != null && textEdits.getChildrenSize() > 0) {
			fileChange.setEdit(textEdits);
			changed = true;
		}
		return changed;
	}

	@Override
	public String getName() {
		return "Mapper Function mappings";
	}

	public void setUpdateVersion(boolean shouldUpdate) {
		this.fUpdateVersion = shouldUpdate;
	}

}
