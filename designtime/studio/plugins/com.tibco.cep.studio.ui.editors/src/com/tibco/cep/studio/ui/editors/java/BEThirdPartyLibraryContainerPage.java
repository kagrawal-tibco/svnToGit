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
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TableColumn;

import com.tibco.cep.studio.common.configuration.BuildPathEntry;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.jdt.BEThirdPartyLibraryContainer;
import com.tibco.cep.studio.core.util.StudioJavaUtil;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;

public class BEThirdPartyLibraryContainerPage extends WizardPage implements IClasspathContainerPage, IClasspathContainerPageExtension {

	private static final Image CHECKED = EditorsUIPlugin.getDefault().getImage("icons/checked.gif");
	private static final Image UNCHECKED = EditorsUIPlugin.getDefault().getImage("icons/unchecked.gif");
	Composite mainPage;
	IClasspathEntry selection;
	private IJavaProject project;
	IClasspathEntry[] currentEntries;
	ListViewer thirdPartyLibViewer;
	boolean libExists = false;
	private StudioProjectConfiguration fConfig;

	public BEThirdPartyLibraryContainerPage() {
		super("BusinessEvents Third Party Library"); //$NON-NLS-1$
	}

	public BEThirdPartyLibraryContainerPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	public BEThirdPartyLibraryContainerPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		setTitle("BusinessEvents Third Party Library Container");
		setTitle("Select the BE Project libraries to include in classpath.");

//		mainPage = new Composite(parent, SWT.BORDER);
//		mainPage.setBackground(new Color(parent.getDisplay(), new RGB(255, 255, 255)));
//		mainPage.setLayout(new GridLayout());
//		mainPage.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		for (int i = 0; i < currentEntries.length; i++) {
			if (currentEntries[i].getPath().toString().equals(BEThirdPartyLibraryContainer.BE_THIRD_PARTY_LIBRARY_CONTAINER)) {
				libExists = true;
			}
		}
		/**
		 * TODO:PKD
		 * Need a listbox populating the list of project libraries only,
		 * no selection is needed here, proj libs are selected from Studio build path properties page
		 * and they are just shown here
		 */
		thirdPartyLibViewer = new ListViewer(parent,SWT.MULTI | SWT.H_SCROLL
			      | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		//createColumns(parent,projLibViewer);
		// Make lines and make header visible
		final List list = thirdPartyLibViewer.getList();
//		final Table table = projLibViewer.getTable();
//		table.setHeaderVisible(true);
//		table.setLinesVisible(true); 
		// Set the ContentProvider
		thirdPartyLibViewer.setContentProvider(new ThirdPartyLibContentProvider());
		thirdPartyLibViewer.setLabelProvider(new ThirdPartyLibLabelProvider());
		thirdPartyLibViewer.setInput(StudioJavaUtil.getThirdPartyCustomClassPathEntries(fConfig));
		// Layout the viewer
	    GridData gridData = new GridData();
	    gridData.verticalAlignment = GridData.FILL;
	    gridData.horizontalSpan = 1;
	    gridData.grabExcessHorizontalSpace = true;
	    gridData.grabExcessVerticalSpace = true;
	    gridData.horizontalAlignment = GridData.FILL;
	    thirdPartyLibViewer.getControl().setLayoutData(gridData);
		if (libExists) {
			setPageComplete(false);
		} else {
			setPageComplete(true);
		}

		setControl(list);
		list.setFocus();
	}
	
	public class ThirdPartyLibContentProvider extends ArrayContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if(inputElement instanceof java.util.List) {
				return ((java.util.List) inputElement).toArray();
			}  
			return super.getElements(inputElement);
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			super.inputChanged(viewer, oldInput, newInput);
		}
		
	}

	public class ThirdPartyLibLabelProvider extends LabelProvider {

		@Override
		public String getText(Object element) {
			if(element instanceof IClasspathEntry) {
				return ((IClasspathEntry)element).getPath().toOSString();
			}
			return super.getText(element);
		}
		
	}
	
	/**
	 * Creates the table columns
	 * @param parent
	 * @param viewer
	 */
	private void createColumns(final Composite parent, final TableViewer viewer) {
		
		String[] titles = {"Select","Project Libraries"};
		int[] bounds = {80,800};
		
		// First column for the selection checkbox
		TableViewerColumn col = createTableViewerColumn(viewer,titles[0],bounds[0],0);
		col.setLabelProvider(new ColumnLabelProvider() {
		      @Override
		      public String getText(Object element) {
		        return null;
		      }

		      @Override
		      public Image getImage(Object element) {
		        if (((ProjectLibraryEntry) element).isReadOnly()) {
		          return CHECKED;
		        } else {
		          return UNCHECKED;
		        }
		      }
		    });
		
		// Second column is for the project lib path
	    col = createTableViewerColumn(viewer,titles[1], bounds[1], 1);
	    col.setLabelProvider(new ColumnLabelProvider() {
	      @Override
	      public String getText(Object element) {
	    	  BuildPathEntry p = (BuildPathEntry) element;
	        return p.getPath(p.isVar());
	      }
	    });
		
	}

	private TableViewerColumn createTableViewerColumn(TableViewer viewer, String title, int bound, int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
		        SWT.NONE);
		    final TableColumn column = viewerColumn.getColumn();
		    column.setText(title);
		    column.setWidth(bound);
		    column.setResizable(true);
		    column.setMoveable(true);
		    return viewerColumn;
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
		if (libExists) {
			return true;
		}
		
		try {
			IClasspathEntry[] newEntries = new IClasspathEntry[currentEntries.length + 1];
			System.arraycopy(currentEntries, 0, newEntries, 0, currentEntries.length);
			selection = JavaCore.newContainerEntry(new Path(BEThirdPartyLibraryContainer.BE_THIRD_PARTY_LIBRARY_CONTAINER));
			newEntries[currentEntries.length] = selection;
			project.setRawClasspath(newEntries, null);
			
			IJavaProject[] javaProjects = new IJavaProject[] { getPlaceHolderProject() };
			IClasspathContainer[] containers = { null };
			JavaCore.setClasspathContainer(selection.getPath(), javaProjects, containers, null);
		} catch (JavaModelException e) {
			EditorsUIPlugin.log(e);
		}
		return true;
	}

	@Override
	public IClasspathEntry getSelection() {
		return selection;
	}

	@Override
	public void setSelection(IClasspathEntry containerEntry) {
		selection = containerEntry;

	}

	@Override
	public void initialize(IJavaProject project, IClasspathEntry[] currentEntries) {
		this.project = project;
		this.fConfig = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(project.getElementName());
		this.currentEntries = currentEntries;
	}
	
	

}
