package com.tibco.cep.studio.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.DrillDownComposite;

import com.tibco.cep.studio.ui.filter.StudioProjectsOnly;


public class StudioResourceContainer implements Listener {

	private Listener client;
	private boolean allowExistingResources = false;
	private String resourceType = "resource";
	private boolean showClosedProjects = true;
	private String problemMessage = "";//$NON-NLS-1$

	private StudioContainerComposite containerComposite;
	private Text nameField;
	private String currentProjectName;
	private String fileExtension;
	private static final int TEXT_FIELD_WIDTH = 250;

	private int problemType = PROBLEM_NONE;
	public static final int PROBLEM_NONE = 0;
	public static final int PROBLEM_RESOURCE_EMPTY = 1;
	public static final int PROBLEM_RESOURCE_EXIST = 2;
	public static final int PROBLEM_PATH_INVALID = 4;
	public static final int PROBLEM_CONTAINER_EMPTY = 5;
	public static final int PROBLEM_PROJECT_DOES_NOT_EXIST = 6;
	public static final int PROBLEM_NAME_INVALID = 7;
	public static final int PROBLEM_PATH_OCCUPIED = 8;

	
	/**
	 * @param parent
	 * @param client
	 * @param resourceFieldLabel
	 * @param resourceType
	 */
	public StudioResourceContainer(Composite parent, Listener client,
			String resourceFieldLabel, String resourceType) {
		this(parent, client, resourceFieldLabel, resourceType, true);
	}

	/**
	 * @param parent
	 * @param client
	 * @param resourceFieldLabel
	 * @param resourceType
	 * @param showClosedProjects
	 */
	public StudioResourceContainer(Composite parent, Listener client,
			String resourceFieldLabel, String resourceType,
			boolean showClosedProjects) {
		this(parent, client, resourceFieldLabel, resourceType,
				showClosedProjects, SWT.DEFAULT, null);
	}
	
	/**
	 * @param parent
	 * @param client
	 * @param resourceFieldLabel
	 * @param resourceType
	 * @param showClosedProjects
	 * @param heightHint
	 * @param currentProjectName
	 */
	public StudioResourceContainer(Composite parent, Listener client,
			String resourceFieldLabel, String resourceType,
			boolean showClosedProjects, int heightHint, String currentProjectName) {
		super();
		this.resourceType = resourceType;
		this.showClosedProjects = showClosedProjects;
		this.currentProjectName = currentProjectName;
		createContents(parent, resourceFieldLabel, heightHint);
		this.client = client;
	}

	public boolean areAllValuesValid() {
		return problemType == PROBLEM_NONE;
	}

	/**
	 * @param parent
	 * @param resourceLabel
	 * @param heightHint
	 */
	protected void createContents(Composite parent, 
								  String resourceLabel,
								  int heightHint) {

		Font font = parent.getFont();
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setFont(font);
		
		if (heightHint == SWT.DEFAULT) {
			containerComposite = new StudioContainerComposite(composite, this, true,
					null, showClosedProjects, currentProjectName);
		} else {
			containerComposite = new StudioContainerComposite(composite, this, true,
					null, showClosedProjects, heightHint,
					TEXT_FIELD_WIDTH, currentProjectName);
		}

		Label label = new Label(composite, SWT.NONE);
		label.setText(resourceLabel == null ? "": resourceLabel);
		label.setFont(font);

		nameField = new Text(composite, SWT.BORDER);
		nameField.addListener(SWT.Modify, this);
		nameField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				handleResourceNameFocusLostEvent();
			}
		});
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL);
		data.widthHint = TEXT_FIELD_WIDTH;
		nameField.setLayoutData(data);
		nameField.setFont(font);
		
		if(resourceLabel == null){
			label.setVisible(false);
			nameField.setVisible(false);
		}
		validateControls();
	}

	public IPath getContainerFullPath() {
		return containerComposite.getContainerFullPath();
	}

	public String getProblemMessage() {
		return problemMessage;
	}

	public int getProblemType() {
		return problemType;
	}
	
	public TreeViewer getTreeViewer() {
		return containerComposite.getTreeViewer();
	}

	public String getResource() {
		String resource = nameField.getText();
		if (useResourceExtension()) {
			return resource + '.' + fileExtension;
		}
		return resource;
	}
	
	public String getResourceName() {
		return nameField.getText();
	}

	public void setEnabled(boolean isEnabled){
		nameField.setEnabled(isEnabled);
	}
	
	public String getResourceExtension() {
		return fileExtension;
	}

	private boolean useResourceExtension() {
		String resource = nameField.getText();
		if ((fileExtension != null) && (fileExtension.length() > 0)
				&& (resource.length() > 0)
				&& (resource.endsWith('.' + fileExtension) == false)) {
			return true;
		}
		return false;
	}

	private void handleResourceNameFocusLostEvent() {
		if (useResourceExtension()) {
			setResourceName(nameField.getText() + '.' + fileExtension);
		}
	}

	public void handleEvent(Event e) {
		validateControls();
		if (client != null) {
			client.handleEvent(e);
		}
	}

	public void setAllowExistingResources(boolean value) {
		allowExistingResources = value;
	}

	public void setContainerFullPath(IPath path) {
		IResource initial = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(path);
		if (initial != null) {
			if (!(initial instanceof IContainer)) {
				initial = initial.getParent();
			}
			containerComposite.setSelectedContainer((IContainer) initial);
		}
		validateControls();
	}

	public void setFocus() {
		nameField.setSelection(0, nameField.getText().length());
		nameField.setFocus();
	}

	public void setResourceName(String value) {
		nameField.setText(value);
		validateControls();
	}

	public void setResourceExtension(String value) {
		fileExtension = value;
		validateControls();
	}

	protected boolean validateContainer() {
		IPath path = containerComposite.getContainerFullPath();
		if (path == null) {
			problemType = PROBLEM_CONTAINER_EMPTY;
			problemMessage = "No folder specified.";
			return false;
		}
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String projectName = path.segment(0);
		if (projectName == null
				|| !workspace.getRoot().getProject(projectName).exists()) {
			problemType = PROBLEM_PROJECT_DOES_NOT_EXIST;
			problemMessage = "The specified project does not exist.";
			return false;
		}
		IWorkspaceRoot root = workspace.getRoot();
		while (path.segmentCount() > 1) {
			if (root.getFile(path).exists()) {
				problemType = PROBLEM_PATH_OCCUPIED;
				problemMessage =  "A file already exists at that location: "+ path.makeRelative();
				return false;
			}
			path = path.removeLastSegments(1);
		}
		return true;
	}

	protected boolean validateControls() {
		if (containerComposite == null) {
			return false;
		}
		problemType = PROBLEM_NONE;
		problemMessage = "";//$NON-NLS-1$

		if (!validateContainer() || !validateResourceName()) {
			return false;
		}

		IPath path = containerComposite.getContainerFullPath()
				.append(getResource());
		return validateFullResourcePath(path);
	}

	protected boolean validateFullResourcePath(IPath resourcePath) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		IStatus result = workspace.validatePath(resourcePath.toString(),
				IResource.FOLDER);
		if (!result.isOK()) {
			problemType = PROBLEM_PATH_INVALID;
			problemMessage = result.getMessage();
			return false;
		}

		if (!allowExistingResources
				&& (workspace.getRoot().getFolder(resourcePath).exists() || workspace
						.getRoot().getFile(resourcePath).exists())) {
			problemType = PROBLEM_RESOURCE_EXIST;
			problemMessage = getResource() + " already exists.";

			return false;
		}
		return true;
	}

	protected boolean validateResourceName() {
		String resourceName = getResource();

		if (resourceName.length() == 0) {
			problemType = PROBLEM_RESOURCE_EMPTY;
			problemMessage = "The " +resourceType +" name is empty.";
			return false;
		}

		if (!Path.ROOT.isValidPath(resourceName)) {
			problemType = PROBLEM_NAME_INVALID;
			problemMessage = 
					resourceName + " is not a valid file name.";
			return false;
		}
		return true;
		
		
	}

	public boolean getAllowExistingResources() {
		return allowExistingResources;
	}

	public StudioContainerComposite getContainerGroup() {
		return containerComposite;
	}
	
	class StudioContainerComposite extends Composite {

		private Listener listener;
		private boolean allowNewContainerName = true;
		private boolean showClosedProjects = true;
		private IContainer selectedContainer;
		private Text containerNameField;
		protected TreeViewer treeViewer;
		private static final String DEFAULT_MSG_NEW_ALLOWED = "&Enter or select the parent folder:";
		private static final String DEFAULT_MSG_SELECT_ONLY = "Select the folder:";
		private static final int SIZING_SELECTION_PANE_WIDTH = 320;
		private static final int SIZING_SELECTION_PANE_HEIGHT = 300;
		protected String currentProjectName;

		/**
		 * @param parent
		 * @param listener
		 * @param allowNewContainerName
		 * @param message
		 * @param showClosedProjects
		 * @param currentProjectName
		 */
		public StudioContainerComposite(Composite parent, Listener listener,
				boolean allowNewContainerName, String message,
				boolean showClosedProjects,String currentProjectName) {
			this(parent, listener, allowNewContainerName, message,
					showClosedProjects, SIZING_SELECTION_PANE_HEIGHT,
					SIZING_SELECTION_PANE_WIDTH, null);
		}

		public StudioContainerComposite(Composite parent, 
				                        Listener listener,
										boolean allowNewContainerName, 
										String message,
										boolean showClosedProjects, 
										int heightHint, 
										int widthHint, 
										String currentProjectName) {
			super(parent, SWT.NONE);
			this.listener = listener;
			this.allowNewContainerName = allowNewContainerName;
			this.showClosedProjects = showClosedProjects;
			this.currentProjectName = currentProjectName;
			if (message != null) {
				createContents(message, heightHint, widthHint);
			} else if (allowNewContainerName) {
				createContents(DEFAULT_MSG_NEW_ALLOWED, heightHint, widthHint);
			} else {
				createContents(DEFAULT_MSG_SELECT_ONLY, heightHint, widthHint);
			}
		}

		public void containerSelectionChanged(IContainer container) {
			selectedContainer = container;

			if (allowNewContainerName) {
				if (container == null) {
					containerNameField.setText("");//$NON-NLS-1$
				} else {
					String text = TextProcessor.process(container.getFullPath()
							.makeRelative().toString());
					containerNameField.setText(text);
					containerNameField.setToolTipText(text);
				}
			}

			// fire an event so the parent can update its controls
			if (listener != null) {
				Event changeEvent = new Event();
				changeEvent.type = SWT.Selection;
				changeEvent.widget = this;
				listener.handleEvent(changeEvent);
			}
		}

		public void createContents(String message) {
			createContents(message, SIZING_SELECTION_PANE_HEIGHT,
					SIZING_SELECTION_PANE_WIDTH);
		}

		/**
		 * @param message
		 * @param heightHint
		 * @param widthHint
		 */
		public void createContents(String message, int heightHint, int widthHint) {
			GridLayout layout = new GridLayout();
			layout.marginWidth = 0;
			setLayout(layout);
			setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			Label label = new Label(this, SWT.WRAP);
			label.setText(message);
			label.setFont(this.getFont());

			if (allowNewContainerName) {
				containerNameField = new Text(this, SWT.SINGLE | SWT.BORDER /*| SWT.READ_ONLY*/);
				GridData gd = new GridData(GridData.FILL_HORIZONTAL);
				gd.widthHint = widthHint;
				containerNameField.setLayoutData(gd);
				containerNameField.addListener(SWT.Modify, listener);
				containerNameField.setFont(this.getFont());
			} else {
				// filler...
				new Label(this, SWT.NONE);
			}

			createTreeViewer(heightHint);
			Dialog.applyDialogFont(this);
		}

		protected void createTreeViewer(int heightHint) {
			DrillDownComposite drillDown = new DrillDownComposite(this, SWT.BORDER);
			GridData spec = new GridData(SWT.FILL, SWT.FILL, true, true);
			spec.widthHint = SIZING_SELECTION_PANE_WIDTH;
			spec.heightHint = heightHint;
			drillDown.setLayoutData(spec);

			treeViewer = new TreeViewer(drillDown, SWT.NONE);
			drillDown.setChildTree(treeViewer);
			TreeContentProvider cp = new TreeContentProvider();
			cp.showClosedProjects(showClosedProjects);
			treeViewer.setContentProvider(cp);
			treeViewer.setLabelProvider(WorkbenchLabelProvider
					.getDecoratingWorkbenchLabelProvider());
			treeViewer.setComparator(new ViewerComparator());
			treeViewer.setUseHashlookup(true);
			treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					IStructuredSelection selection = (IStructuredSelection) event
							.getSelection();
					containerSelectionChanged((IContainer) selection
							.getFirstElement()); // allow null
				}
			});
			treeViewer.addDoubleClickListener(new IDoubleClickListener() {
				public void doubleClick(DoubleClickEvent event) {
					ISelection selection = event.getSelection();
					if (selection instanceof IStructuredSelection) {
						Object item = ((IStructuredSelection) selection)
								.getFirstElement();
						if (item == null) {
							return;
						}
						if (treeViewer.getExpandedState(item)) {
							treeViewer.collapseToLevel(item, 1);
						} else {
							treeViewer.expandToLevel(item, 1);
						}
					}
				}
			});

			// This has to be done after the viewer has been laid out
			if(currentProjectName != null){
				treeViewer.addFilter(new StudioProjectsOnly(currentProjectName));
				treeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
			}else{
				treeViewer.setInput(ResourcesPlugin.getWorkspace());
			}
		}

		public IPath getContainerFullPath() {
			if (allowNewContainerName) {
				String pathName = containerNameField.getText();
				if (pathName == null || pathName.length() < 1) {
					return null;
				}
				// The user may not have made this absolute so do it for them
				return (new Path(TextProcessor.deprocess(pathName))).makeAbsolute();

			}
			if (selectedContainer == null)
				return null;
			return selectedContainer.getFullPath();

		}

		public void setInitialFocus() {
			if (allowNewContainerName) {
				containerNameField.setFocus();
			} else {
				treeViewer.getTree().setFocus();
			}
		}

		@SuppressWarnings("unchecked")
		public void setSelectedContainer(IContainer container) {
			selectedContainer = container;

			// expand to and select the specified container
			List itemsToExpand = new ArrayList();
			IContainer parent = container.getParent();
			while (parent != null) {
				itemsToExpand.add(0, parent);
				parent = parent.getParent();
			}
			treeViewer.setExpandedElements(itemsToExpand.toArray());
			treeViewer.setSelection(new StructuredSelection(container), true);
		}

		public TreeViewer getTreeViewer() {
			return treeViewer;
		}
		
		class TreeContentProvider implements ITreeContentProvider {
		    private boolean showClosedProjects = true;

		    public TreeContentProvider() {
		    }

		    public void dispose() {
		    }

		    /*
		     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
		     */
		    public Object[] getChildren(Object element) {
		        if (element instanceof IWorkspace) {
		            // check if closed projects should be shown
		            IProject[] allProjects = ((IWorkspace) element).getRoot()
		                    .getProjects();
		            if (showClosedProjects) {
						return allProjects;
					}

		            ArrayList accessibleProjects = new ArrayList();
		            for (int i = 0; i < allProjects.length; i++) {
		                if (allProjects[i].isOpen()) {
		                    accessibleProjects.add(allProjects[i]);
		                }
		            }
		            return accessibleProjects.toArray();
		        } else if (element instanceof IContainer) {
		            IContainer container = (IContainer) element;
		            if (container.isAccessible()) {
		                try {
		                    List children = new ArrayList();
		                    IResource[] members = container.members();
		                    for (int i = 0; i < members.length; i++) {
		                        if (members[i].getType() != IResource.FILE) {
		                            children.add(members[i]);
		                        }
		                    }
		                    return children.toArray();
		                } catch (CoreException e) {
		                    // this should never happen because we call #isAccessible before invoking #members
		                }
		            }
		        }
		        return new Object[0];
		    }

		    /*
		     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		     */
		    public Object[] getElements(Object element) {
		        return getChildren(element);
		    }

		    /*
		     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
		     */
		    public Object getParent(Object element) {
		        if (element instanceof IResource) {
					return ((IResource) element).getParent();
				}
		        return null;
		    }

		    /*
		     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
		     */
		    public boolean hasChildren(Object element) {
		        return getChildren(element).length > 0;
		    }

		    /*
		     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged
		     */
		    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		    }

		    public void showClosedProjects(boolean show) {
		        showClosedProjects = show;
		    }
		}
	}

	public void setCurrentProjectName(IProject project) {
		this.currentProjectName = project.getName();
		this.containerComposite.currentProjectName = currentProjectName;
		ViewerFilter[] filters = this.containerComposite.treeViewer.getFilters();
		for (ViewerFilter viewerFilter : filters) {
			if (viewerFilter instanceof StudioProjectsOnly) {
				this.containerComposite.treeViewer.removeFilter(viewerFilter);
			}
		}
		this.containerComposite.treeViewer.addFilter(new StudioProjectsOnly(currentProjectName));
		this.containerComposite.treeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
	}

}
