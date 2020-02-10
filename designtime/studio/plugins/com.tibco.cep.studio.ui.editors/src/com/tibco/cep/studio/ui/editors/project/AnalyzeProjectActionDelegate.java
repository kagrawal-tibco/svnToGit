/**
 * 
 */
package com.tibco.cep.studio.ui.editors.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.ui.AbstractNavigatorNode;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

/**
 * @author aathalye
 * 
 */
public class AnalyzeProjectActionDelegate implements IWorkbenchWindowActionDelegate, IObjectActionDelegate {

	private ISelection _selection;
	private IProject project;
	private IStructuredSelection structuredSelection;
	private ProjectAnalyzer projectAnalyzer;
	private IWorkbenchPage page;
	private boolean createViewWhenAnalyze = false;
	private List<String> projectAnalyzerWarnings = new ArrayList<String>();

	public AnalyzeProjectActionDelegate() {
	}

	public void run(IAction action) {

		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage();
		try {
			if (_selection != null
					&& _selection instanceof IStructuredSelection) {
				structuredSelection = (IStructuredSelection) _selection;
				Object firstElement = structuredSelection.getFirstElement();
				if (!(firstElement instanceof IProject)) {
					if (!StudioResourceUtils
							.isStudioProject(structuredSelection)) {
						return;
					}
					project = StudioResourceUtils.getCurrentProject(structuredSelection);
				} else {
					project = (IProject) firstElement;
				}
				if (project.getProject().isOpen()) {
					IPreferenceStore prefStore = EditorsUIPlugin.getDefault()
							.getPreferenceStore();
					createViewWhenAnalyze = prefStore
							.getBoolean(StudioPreferenceConstants.CREATE_VIEW);

					if (createViewWhenAnalyze) {
						final IFile projectViewFile = project.getFile(project
								.getName()
								+ Messages.getString("PV_extension"));
						if (projectViewFile.exists()) {
							EditorUtils.refreshDiagramEditor(page,
									projectViewFile);
						} else {
							File mfile = new File(StudioResourceUtils
									.getCurrentWorkspacePath()
									+ "/"
									+ project.getName()
									+ "/"
									+ project.getName()
									+ Messages.getString("PV_extension"));
							if (!mfile.exists())
								mfile.createNewFile();// creating the file
							IFile file = project.getFile(project.getName()
									+ ".projectview");
							EditorUtils.openDiagramEditor(page, file);
						}
					}

					AnalyzeJob anlayzeJob = new AnalyzeJob(project.getProject()
							.getName());
					anlayzeJob.setPriority(Job.BUILD);
					anlayzeJob.setUser(true);
					anlayzeJob.schedule();
				} else
					return;
			}
		} catch (Exception e) {
			StudioUIPlugin.log(e);
		}
	}

	class AnalyzeJob extends Job {

		public AnalyzeJob(String projectName) {
			super("Analyze Project");
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			return runAnalyzeProject(monitor);
		}

	}

	private IStatus runAnalyzeProject(IProgressMonitor monitor) {
		try {
			monitor.beginTask("Analyzing Project", IProgressMonitor.UNKNOWN);
			if (monitor.isCanceled()) {
				monitor.done();
				return new Status(Status.INFO, StudioUIPlugin.getUniqueIdentifier(),
						Messages.getString("studio.project.analyze.cancelled"));
			}
			monitor.worked(10);
			projectAnalyzer = new ProjectAnalyzer(project, monitor);
			if (monitor.isCanceled()) {
				monitor.done();
				return new Status(Status.INFO, StudioUIPlugin.getUniqueIdentifier(),
						Messages.getString("studio.project.analyze.cancelled"));
			}
			monitor.worked(40);
			projectAnalyzerWarnings = new ArrayList<String>();
			if (projectAnalyzer.analyzeProject(projectAnalyzerWarnings)) {
				monitor.done();
				return new Status(Status.INFO, StudioUIPlugin.getUniqueIdentifier(),
						Messages.getString("studio.project.analyze.cancelled"));			}
			monitor.worked(70);
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					int noOfWarnings = projectAnalyzerWarnings.toArray().length;
					String warningMessage = Messages.getString("studio.project.analyzer.warnings", project.getName(), noOfWarnings);
					if(noOfWarnings == 0) {
						MessageDialog.openInformation(StudioUIPlugin.getShell(),
							Messages.getString("studio.project.analyze"),
							Messages.getString("studio.project.analyze.successful") );
					} else {
						MessageDialog.openInformation(StudioUIPlugin.getShell(),
								Messages.getString("studio.project.analyze"),
								warningMessage );
					}
				}
			});
			monitor.worked(100);
			return Status.OK_STATUS;
		} catch (Exception e1) {
			monitor.worked(100);
			return new Status(Status.ERROR, StudioUIPlugin.getUniqueIdentifier(),
					Messages.getString("studio.project.analyze.failed"), e1);
		} finally {
			monitor.done();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */

	public void selectionChanged(IAction action, ISelection selection) {
		_selection = selection;
		if (!(_selection.isEmpty())
				&& _selection instanceof IStructuredSelection) {
			structuredSelection = (IStructuredSelection) _selection;
			if (structuredSelection.size() == 1) {
				action.setEnabled(true);
				if (structuredSelection.getFirstElement() instanceof AbstractNavigatorNode) {
					action.setEnabled(false);
					return;
				}
				if (!(structuredSelection.getFirstElement() instanceof IProject)) {
					project = StudioResourceUtils
							.getCurrentProject(structuredSelection);
				} else {
					project = (IProject) structuredSelection.getFirstElement();
				}
				if (project != null && project.isOpen()) {
					action.setEnabled(true);
				} else {
					action.setEnabled(false);
				}
			} else {
				action.setEnabled(false);
			}
		} else if (_selection.isEmpty()) {
			action.setEnabled(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.
	 * IWorkbenchWindow)
	 */
	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
	 * action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub

	}

}
