package com.tibco.cep.studio.tester.ui.wizards;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tibco.cep.studio.core.TibExtClassPathContainer;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.util.Messages;

public class NewBEUnitTestWizard extends Wizard implements INewWizard {
	private NewBEUnitTestWizardPage page;
	private NewBEUnitTestDetailsWizardPage detailsPage;
	private ISelection selection;

	/**
	 * Constructor for NewBEUnitTestWizard.
	 */
	public NewBEUnitTestWizard() {
		super();
		setWindowTitle(Messages.getString("new.beunit.wizard.title"));
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new NewBEUnitTestWizardPage(selection);
		detailsPage = new NewBEUnitTestDetailsWizardPage("Unit Test Details");

		addPage(page);
		addPage(detailsPage);
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String containerName = page.getContainerFullPath().toString();
		final String fileName = page.getFileName();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName, fileName, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * The worker method. It will find the container, create the
	 * file if missing or just replace its contents, and open
	 * the editor on the newly created file.
	 */
	private void doFinish(
			String containerName,
			String fileName,
			IProgressMonitor monitor)
					throws CoreException {
		// create a sample file
		monitor.beginTask("Creating " + fileName, 2);
		IPath folderPath = new Path(containerName);
		IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject(folderPath.segment(0));
		folderPath = folderPath.removeFirstSegments(1);
		IFolder folder = proj.getFolder(folderPath);
		if (!folder.exists()) {
			createParentFolders(folder);
		}
		IContainer container = (IContainer) folder;
		final IFile file = container.getFile(new Path(fileName));
		try {
			InputStream stream = openContentStream();
			if (file.exists()) {
				file.setContents(stream, true, true, monitor);
			} else {
				file.create(stream, true, monitor);
			}
			// override the default encoding with UTF-8
			file.setCharset(ModelUtils.UTF8_ENCODING, monitor);
			stream.close();
		} catch (IOException e) {
		}
		addExtTibContainer(folder, monitor);
		monitor.worked(1);
		monitor.setTaskName("Opening file for editing...");
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page =
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (PartInitException e) {
				}
			}
		});
		monitor.worked(1);
	}

	private void createParentFolders(IContainer folder) throws CoreException {
		if (!folder.getParent().exists()) {
			createParentFolders(folder.getParent());
		}
		((IFolder) folder).create(true, true, null);
		
	}

	private void addExtTibContainer(IFolder folder, IProgressMonitor monitor) throws JavaModelException {
        IJavaProject javaProject = JavaCore.create(folder.getProject());
        IClasspathEntry[] entries = javaProject.getRawClasspath();
        IClasspathEntry[] newEntries = new IClasspathEntry[entries.length + 1];

        // add a new entry using the path to the container
        Path tibExtPath = new Path(
        		TibExtClassPathContainer.TIB_EXT_CLASSPATH_CONTAINER);
        for (int i=0; i<entries.length; i++) {
        	IClasspathEntry entry = entries[i];
			IPath path = entry.getPath();
			if (path.equals(tibExtPath)) {
				// already has classpath on container
				return;
			}
		}
        System.arraycopy(entries, 0, newEntries, 0, entries.length);
        newEntries[entries.length] = JavaCore
                .newContainerEntry(tibExtPath);
        javaProject.setRawClasspath(newEntries, monitor);
		
	}

	/**
	 * We will initialize file contents with a sample text.
	 */

	private InputStream openContentStream() {
		return page.getInitialContents();
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status =
				new Status(IStatus.ERROR, "com.tibco.cep.studio.tester.ui", IStatus.OK, message, null);
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
	
	public NewBEUnitTestDetailsWizardPage getDetailsPage() {
		return detailsPage;
	}
}