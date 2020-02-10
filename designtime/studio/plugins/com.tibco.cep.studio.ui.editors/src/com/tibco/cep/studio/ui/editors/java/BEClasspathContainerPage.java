package com.tibco.cep.studio.ui.editors.java;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPage;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPageExtension;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.studio.core.BEClassPathContainer;
import com.tibco.cep.studio.ui.StudioUIPlugin;

public class BEClasspathContainerPage extends WizardPage implements IClasspathContainerPage, IClasspathContainerPageExtension {

	Composite mainPage;
	IClasspathEntry selection;
	private IJavaProject project;
	IClasspathEntry[] currentEntries;
	Button check;
	boolean libExists = false;

	public BEClasspathContainerPage() {
		super("BusinessEvents Core Library"); //$NON-NLS-1$
	}

	public BEClasspathContainerPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	public BEClasspathContainerPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		setTitle("BusinessEvents Core Library");
		setTitle("Select the BE Core libraries to include in classpath.");

		mainPage = new Composite(parent, SWT.BORDER);
		mainPage.setBackground(new Color(parent.getDisplay(), new RGB(255, 255, 255)));
		mainPage.setLayout(new GridLayout());
		mainPage.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		for (int i = 0; i < currentEntries.length; i++) {
			if (currentEntries[i].getPath().toString().equals(BEClassPathContainer.BE_CLASSPATH_CONTAINER)) {
				libExists = true;
			}
		}

		check = new Button(mainPage, SWT.CHECK);
		check.setText(System.getProperty(SystemProperty.BE_HOME.getPropertyName()) + "/lib");
		check.setBackground(new Color(parent.getDisplay(), new RGB(255, 255, 255)));
		check.setImage(StudioUIPlugin.getDefault().getImage("icons/library_obj.gif"));
		check.setSelection(true);

		check.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (((Button) (e.widget)).getSelection()) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		if (libExists) {
			check.setEnabled(false);
			setPageComplete(false);
		} else {
			setPageComplete(true);
		}

		setControl(mainPage);
		mainPage.setFocus();
	}

	public static IJavaProject getPlaceHolderProject() {
		String name = "####BEStudio"; //$NON-NLS-1$
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		while (true) {
			IProject project = root.getProject(name);
			if (!project.exists()) {
				return JavaCore.create(project);
			}
			name += '1';
		}
	}

	@Override
	public boolean finish() {
		// TODO Auto-generated method stub
		if (libExists) {
			return true;
		}

		try {
			IClasspathEntry[] newEntries = new IClasspathEntry[currentEntries.length + 1];
			System.arraycopy(currentEntries, 0, newEntries, 0, currentEntries.length);
			selection = JavaCore.newContainerEntry(new Path(BEClassPathContainer.BE_CLASSPATH_CONTAINER));
			newEntries[currentEntries.length] = selection;
			project.setRawClasspath(newEntries, null);

			IJavaProject[] javaProjects = new IJavaProject[] { getPlaceHolderProject() };
			IClasspathContainer[] containers = { null };
			JavaCore.setClasspathContainer(selection.getPath(), javaProjects, containers, null);
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public IClasspathEntry getSelection() {
		// TODO Auto-generated method stub
		return selection;
	}

	@Override
	public void setSelection(IClasspathEntry containerEntry) {
		// TODO Auto-generated method stub
		selection = containerEntry;

	}

	@Override
	public void initialize(IJavaProject project, IClasspathEntry[] currentEntries) {
		// TODO Auto-generated method stub
		this.project = project;
		this.currentEntries = currentEntries;
	}

}
