package com.tibco.cep.studio.ui.util;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Widget;
import org.osgi.framework.Bundle;

import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.ui.preferences.classpathvar.VariableDialogField;
import com.tibco.cep.studio.ui.property.page.classpath.dialog.EditVariableEntryDialog;
import com.tibco.cep.studio.ui.property.page.classpath.dialog.NewVariableEntryDialog;

public class ClasspathVariableUiUtils {

	public static final String JARZIP_FILTER_STRING= "*.jar,*.zip"; //$NON-NLS-1$

	public static final String PRJECT_LIB_FILTER_STRING= "*.projlib"; //$NON-NLS-1$

	public static final String[] JAR_ZIP_FILTER_EXTENSIONS= new String[] {"*.jar;*.zip"}; //$NON-NLS-1$

	public static final String[] ALL_ARCHIVES_FILTER_EXTENSIONS= new String[] {"*.jar;*.zip", "*.*"}; //$NON-NLS-1$ //$NON-NLS-2$

	private static final String[] fgArchiveExtensions= { "jar", "zip" }; //$NON-NLS-1$ //$NON-NLS-2$
	
	private static final String[] fgProjlibExtensions= { "projlib" }; //$NON-NLS-1$ //$NON-NLS-2$
	
	public static final int COMBO_VISIBLE_ITEM_COUNT= 30;
	
	
	public static final boolean USE_TEXT_PROCESSOR;
	
	static {
		String testString= "args : String[]"; //$NON-NLS-1$
		USE_TEXT_PROCESSOR= testString != TextProcessor.process(testString);
	}

	
	/**
	 * @param shell
	 * @param initialEntryPath
	 * @param existingPaths
	 * @param projectLib
	 * @return
	 */
	public static IPath configureVariableEntry(Shell shell, IPath initialEntryPath, IPath[] existingPaths, boolean projectLib) {
		if (existingPaths == null) {
			throw new IllegalArgumentException();
		}

		EditVariableEntryDialog dialog= new EditVariableEntryDialog(shell, initialEntryPath, existingPaths, projectLib);
		if (dialog.open() == Window.OK) {
			return dialog.getPath();
		}
		return null;
	}

	/**
	 * @param shell
	 * @param existingPaths
	 * @param projectLib
	 * @return
	 */
	public static IPath[] chooseVariableEntries(Shell shell, IPath[] existingPaths, boolean projectLib) {
		if (existingPaths == null) {
			throw new IllegalArgumentException();
		}
		NewVariableEntryDialog dialog= new NewVariableEntryDialog(shell, projectLib);
		if (dialog.open() == Window.OK) {
			return dialog.getResult();
		}
		return null;
	}
	
	/**
	 * @param path
	 * @param allowAllAchives
	 * @return
	 */
	public static boolean isArchivePath(IPath path, boolean allowAllAchives) {
		if (allowAllAchives)
			return true;

		String ext= path.getFileExtension();
		if (ext != null && ext.length() != 0) {
			return isArchiveFileExtension(ext);
		}
		return false;
	}
	
	public static boolean isProjlibPath(IPath path) {
		String ext= path.getFileExtension();
		if (ext != null && ext.length() != 0) {
			return isProjlibFileExtension(ext);
		}
		return false;
	}
	
	public static boolean isProjlibFileExtension(String ext) {
		for (int i= 0; i < fgProjlibExtensions.length; i++) {
			if (ext.equalsIgnoreCase(fgProjlibExtensions[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param ext
	 * @return
	 */
	public static boolean isArchiveFileExtension(String ext) {
		for (int i= 0; i < fgArchiveExtensions.length; i++) {
			if (ext.equalsIgnoreCase(fgArchiveExtensions[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param widget
	 * @return
	 */
	public static Shell getShell(Widget widget) {
		if (widget instanceof Control)
			return ((Control)widget).getShell();
		if (widget instanceof Caret)
			return ((Caret)widget).getParent().getShell();
		if (widget instanceof DragSource)
			return ((DragSource)widget).getControl().getShell();
		if (widget instanceof DropTarget)
			return ((DropTarget)widget).getControl().getShell();
		if (widget instanceof Menu)
			return ((Menu)widget).getParent().getShell();
		if (widget instanceof ScrollBar)
			return ((ScrollBar)widget).getParent().getShell();

		return null;
	}

	/**
	 * @param button
	 * @return
	 */
	public static int getButtonWidthHint(Button button) {
		button.setFont(JFaceResources.getDialogFont());
		PixelConverter converter= new PixelConverter(button);
		int widthHint= converter.convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		return Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
	}

	/**
	 * @param button
	 */
	public static void setButtonDimensionHint(Button button) {
		Assert.isNotNull(button);
		Object gd= button.getLayoutData();
		if (gd instanceof GridData) {
			((GridData)gd).widthHint= getButtonWidthHint(button);
			((GridData)gd).horizontalAlignment = GridData.FILL;
		}
	}

	/**
	 * @param table
	 * @param rows
	 * @return
	 */
	public static int getTableHeightHint(Table table, int rows) {
		if (table.getFont().equals(JFaceResources.getDefaultFont()))
			table.setFont(JFaceResources.getDialogFont());
		int result= table.getItemHeight() * rows + table.getHeaderHeight();
		if (table.getLinesVisible())
			result+= table.getGridLineWidth() * (rows - 1);
		return result;
	}

	/**
	 * @param control
	 * @param text
	 */
	public static void setAccessibilityText(Control control, final String text) {
		control.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.accessibility.AccessibleAdapter#getName(org.eclipse.swt.accessibility.AccessibleEvent)
			 */
			@Override
			public void getName(AccessibleEvent e) {
				if (e.childID == ACC.CHILDID_SELF) {
					e.result= text;
				}
			}
		});
	}

	/**
	 * @param combo
	 */
	public static void setDefaultVisibleItemCount(Combo combo) {
		combo.setVisibleItemCount(COMBO_VISIBLE_ITEM_COUNT);
	}

	/**
	 * @param columns
	 * @return
	 */
	public static GridLayout newLayoutNoMargins(int columns) {
		GridLayout layout= new GridLayout(columns, false);
		layout.marginWidth= 0;
		layout.marginHeight= 0;
		return layout;
	}
	
	/**
	 * Calculates the number of columns needed by field editors
	 */
	public static int getNumberOfColumns(VariableDialogField[] editors) {
		int nCulumns= 0;
		for (int i= 0; i < editors.length; i++) {
			nCulumns= Math.max(editors[i].getNumberOfControls(), nCulumns);
		}
		return nCulumns;
	}

	/**
	 * Creates a composite and fills in the given editors.
	 * @param labelOnTop Defines if the label of all fields should be on top of the fields
	 */
	public static void doDefaultLayout(Composite parent, VariableDialogField[] editors, boolean labelOnTop) {
		doDefaultLayout(parent, editors, labelOnTop, 0, 0);
	}

	/**
	 * Creates a composite and fills in the given editors.
	 * @param labelOnTop Defines if the label of all fields should be on top of the fields
	 * @param marginWidth The margin width to be used by the composite
	 * @param marginHeight The margin height to be used by the composite
	 */
	public static void doDefaultLayout(Composite parent, VariableDialogField[] editors, boolean labelOnTop, int marginWidth, int marginHeight) {
		int nCulumns= getNumberOfColumns(editors);
		Control[][] controls= new Control[editors.length][];
		for (int i= 0; i < editors.length; i++) {
			controls[i]= editors[i].doFillIntoGrid(parent, nCulumns);
		}
		if (labelOnTop) {
			nCulumns--;
			modifyLabelSpans(controls, nCulumns);
		}
		GridLayout layout= null;
		if (parent.getLayout() instanceof GridLayout) {
			layout= (GridLayout) parent.getLayout();
		} else {
			layout= new GridLayout();
		}
		if (marginWidth != SWT.DEFAULT) {
			layout.marginWidth= marginWidth;
		}
		if (marginHeight != SWT.DEFAULT) {
			layout.marginHeight= marginHeight;
		}
		layout.numColumns= nCulumns;
		parent.setLayout(layout);
	}

	/**
	 * @param controls
	 * @param nCulumns
	 */
	private static void modifyLabelSpans(Control[][] controls, int nCulumns) {
		for (int i= 0; i < controls.length; i++) {
			setHorizontalSpan(controls[i][0], nCulumns);
		}
	}

	/**
	 * @param control
	 * @param span
	 */
	public static void setHorizontalSpan(Control control, int span) {
		Object ld= control.getLayoutData();
		if (ld instanceof GridData) {
			((GridData)ld).horizontalSpan= span;
		} else if (span != 1) {
			GridData gd= new GridData();
			gd.horizontalSpan= span;
			control.setLayoutData(gd);
		}
	}

	/**
	 * @param control
	 * @param widthHint
	 */
	public static void setWidthHint(Control control, int widthHint) {
		Object ld= control.getLayoutData();
		if (ld instanceof GridData) {
			((GridData)ld).widthHint= widthHint;
		}
	}

	/**
	 * @param control
	 * @param heightHint
	 */
	public static void setHeightHint(Control control, int heightHint) {
		Object ld= control.getLayoutData();
		if (ld instanceof GridData) {
			((GridData)ld).heightHint= heightHint;
		}
	}

	/**
	 * @param control
	 * @param horizontalIndent
	 */
	public static void setHorizontalIndent(Control control, int horizontalIndent) {
		Object ld= control.getLayoutData();
		if (ld instanceof GridData) {
			((GridData)ld).horizontalIndent= horizontalIndent;
		}
	}

	/**
	 * @param control
	 */
	public static void setHorizontalGrabbing(Control control) {
		Object ld= control.getLayoutData();
		if (ld instanceof GridData) {
			((GridData)ld).grabExcessHorizontalSpace= true;
		}
	}

	/**
	 * @param control
	 */
	public static void setVerticalGrabbing(Control control) {
		Object ld= control.getLayoutData();
		if (ld instanceof GridData) {
			GridData gd= ((GridData)ld);
			gd.grabExcessVerticalSpace= true;
			gd.verticalAlignment= SWT.FILL;
		}
	}
	
	/**
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static IStatus getMoreSevere(IStatus s1, IStatus s2) {
		if (s1.getSeverity() > s2.getSeverity()) {
			return s1;
		} else {
			return s2;
		}
	}

	/**
	 * Finds the most severe status from a array of stati.
	 * An error is more severe than a warning, and a warning is more severe
	 * than ok.
	 * @param status an array of stati
	 * @return the most severe status
	 */
	public static IStatus getMostSevere(IStatus[] status) {
		IStatus max= null;
		for (int i= 0; i < status.length; i++) {
			IStatus curr= status[i];
			if (curr.matches(IStatus.ERROR)) {
				return curr;
			}
			if (max == null || curr.getSeverity() > max.getSeverity()) {
				max= curr;
			}
		}
		return max;
	}

	/**
	 * Applies the status to the status line of a dialog page.
	 * @param page the dialog page
	 * @param status the status to apply
	 */
	public static void applyToStatusLine(DialogPage page, IStatus status) {
		String message= status.getMessage();
		if (message != null && message.length() == 0) {
			message= null;
		}
		switch (status.getSeverity()) {
			case IStatus.OK:
				page.setMessage(message, IMessageProvider.NONE);
				page.setErrorMessage(null);
				break;
			case IStatus.WARNING:
				page.setMessage(message, IMessageProvider.WARNING);
				page.setErrorMessage(null);
				break;
			case IStatus.INFO:
				page.setMessage(message, IMessageProvider.INFORMATION);
				page.setErrorMessage(null);
				break;
			default:
				page.setMessage(null);
				page.setErrorMessage(message);
				break;
		}
	}


	public static void createDerivedFolder(IFolder folder, boolean force, boolean local, IProgressMonitor monitor) throws CoreException {
		if (!folder.exists()) {
			IContainer parent= folder.getParent();
			if (parent instanceof IFolder) {
				createDerivedFolder((IFolder)parent, force, local, null);
			}
			folder.create(force ? (IResource.FORCE | IResource.DERIVED) : IResource.DERIVED, local, monitor);
		}
	}

	/**
	 * Creates a folder and all parent folders if not existing.
	 * Project must exist.
	 * <code> org.eclipse.ui.dialogs.ContainerGenerator</code> is too heavy
	 * (creates a runnable)
	 * @param folder the folder to create
	 * @param force a flag controlling how to deal with resources that
	 *    are not in sync with the local file system
	 * @param local a flag controlling whether or not the folder will be local
	 *    after the creation
	 * @param monitor the progress monitor
	 * @throws CoreException thrown if the creation failed
	 */
	public static void createFolder(IFolder folder, boolean force, boolean local, IProgressMonitor monitor) throws CoreException {
		if (!folder.exists()) {
			IContainer parent= folder.getParent();
			if (parent instanceof IFolder) {
				createFolder((IFolder)parent, force, local, null);
			}
			folder.create(force, local, monitor);
		}
	}

	/**
	 * Creates an extension.  If the extension plugin has not
	 * been loaded a busy cursor will be activated during the duration of
	 * the load.
	 *
	 * @param element the config element defining the extension
	 * @param classAttribute the name of the attribute carrying the class
	 * @return the extension object
	 * @throws CoreException thrown if the creation failed
	 */
	public static Object createExtension(final IConfigurationElement element, final String classAttribute) throws CoreException {
		// If plugin has been loaded create extension.
		// Otherwise, show busy cursor then create extension.
		String pluginId = element.getContributor().getName();
		Bundle bundle = Platform.getBundle(pluginId);
		if (bundle != null && bundle.getState() == Bundle.ACTIVE ) {
			return element.createExecutableExtension(classAttribute);
		} else {
			final Object[] ret = new Object[1];
			final CoreException[] exc = new CoreException[1];
			BusyIndicator.showWhile(null, new Runnable() {
				public void run() {
					try {
						ret[0] = element.createExecutableExtension(classAttribute);
					} catch (CoreException e) {
						exc[0] = e;
					}
				}
			});
			if (exc[0] != null)
				throw exc[0];
			else
				return ret[0];
		}
	}


	/**
	 * Starts a build in the background.
	 * @param project The project to build or <code>null</code> to build the workspace.
	 */
	public static void startBuildInBackground(final IProject project) {
		getBuildJob(project).schedule();
	}


	private static final class BuildJob extends Job {
		private final IProject fProject;
		private BuildJob(String name, IProject project) {
			super(name);
			fProject= project;
		}

		public boolean isCoveredBy(BuildJob other) {
			if (other.fProject == null) {
				return true;
			}
			return fProject != null && fProject.equals(other.fProject);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
		 */
		@Override
		protected IStatus run(IProgressMonitor monitor) {
			synchronized (getClass()) {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
		        Job[] buildJobs = Job.getJobManager().find(ResourcesPlugin.FAMILY_MANUAL_BUILD);
		        for (int i= 0; i < buildJobs.length; i++) {
		        	Job curr= buildJobs[i];
		        	if (curr != this && curr instanceof BuildJob) {
		        		BuildJob job= (BuildJob) curr;
		        		if (job.isCoveredBy(this)) {
		        			curr.cancel(); // cancel all other build jobs of our kind
		        		}
		        	}
				}
			}
			try {
				if (fProject != null) {
					monitor.beginTask(Messages.getString("CoreUtility_buildproject_taskname", getResourceName(fProject)), 2);
					fProject.build(IncrementalProjectBuilder.FULL_BUILD, new SubProgressMonitor(monitor,1));
					ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.INCREMENTAL_BUILD, new SubProgressMonitor(monitor,1));
				} else {
					monitor.beginTask(Messages.getString("CoreUtility_buildall_taskname"), 2);
					ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.FULL_BUILD, new SubProgressMonitor(monitor, 2));
				}
			} catch (CoreException e) {
				return e.getStatus();
			} catch (OperationCanceledException e) {
				return Status.CANCEL_STATUS;
			}
			finally {
				monitor.done();
			}
			return Status.OK_STATUS;
		}
		@Override
		public boolean belongsTo(Object family) {
			return ResourcesPlugin.FAMILY_MANUAL_BUILD == family;
		}
	}

	public static String getResourceName(IResource resource) {
		return markLTR(resource.getName());
	}
	
	/**
	 * Returns a label for a resource name.
	 *
	 * @param resourceName the resource name
	 * @return the label of the resource name.
	 */
	public static String getResourceName(String resourceName) {
		return markLTR(resourceName);
	}
	
	/**
	 * Returns a build job
	 * @param project The project to build or <code>null</code> to build the workspace.
	 * @return the build job
	 */
	public static Job getBuildJob(final IProject project) {
		Job buildJob= new BuildJob(Messages.getString("CoreUtility_job_title"), project);
		buildJob.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
		buildJob.setUser(true);
		return buildJob;
	}

	/**
	 * Sets whether building automatically is enabled in the workspace or not and returns the old
	 * value.
	 * 
	 * @param state <code>true</code> if automatically building is enabled, <code>false</code>
	 *            otherwise
	 * @return the old state
	 * @throws CoreException thrown if the operation failed
	 */
    public static boolean setAutoBuilding(boolean state) throws CoreException {
        IWorkspace workspace= ResourcesPlugin.getWorkspace();
        IWorkspaceDescription desc= workspace.getDescription();
        boolean isAutoBuilding= desc.isAutoBuilding();
        if (isAutoBuilding != state) {
            desc.setAutoBuilding(state);
            workspace.setDescription(desc);
        }
        return isAutoBuilding;
    }
    
	/**
	 * Returns the label of a path.
	 *
	 * @param path the path
	 * @param isOSPath if <code>true</code>, the path represents an OS path, if <code>false</code> it is a workspace path.
	 * @return the label of the path to be used in the UI.
	 */
	public static String getPathLabel(IPath path, boolean isOSPath) {
		String label;
		if (isOSPath) {
			label= path.toOSString();
		} else {
			label= path.makeRelative().toString();
		}
		return markLTR(label);
	}
	
	public static String markLTR(String string) {
		if (!USE_TEXT_PROCESSOR)
			return string;

		return TextProcessor.process(string);
	}

	
	/**
	 * @param variableName
	 * @return
	 */
	public static String getDeprecationMessage(String variableName) {
		String deprecationMessage= StudioCore.getClasspathVariableDeprecationMessage(variableName);
		if (deprecationMessage == null	)
			return null;
		else
			return Messages.getString("BuildPathSupport_deprecated",
					new Object[] {variableName, deprecationMessage});
	}
}
