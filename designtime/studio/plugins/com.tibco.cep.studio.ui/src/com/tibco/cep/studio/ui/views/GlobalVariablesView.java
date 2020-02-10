package com.tibco.cep.studio.ui.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IContributedContentsView;
import org.eclipse.ui.part.ViewPart;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.manager.IGlobalVariablesChangeListener;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.providers.GlobalVariablesContentProvider;
import com.tibco.cep.studio.ui.providers.GlobalVariablesLabelProvider;

@SuppressWarnings("unused")
public class GlobalVariablesView extends ViewPart implements IContributedContentsView,IReusableTrayView {
	
	public static final String ID = "com.tibco.cep.studio.ui.views.GlobalVariablesView";
	private TreeViewer fTreeViewer;
	private DrillDownAdapter fDrillDownAdapter;
	private Action fCopyAction, fDoubleClickAction, fOpenGvEditorAction;
	private IProject activeProject;
	private GlobalVariablesProvider gvProvider; 
	private String projectName;
	private GlobalVariablesChangeListener fListener = new GlobalVariablesChangeListener();
	private GlobalVariablesContentProvider fContentProvider;
	
	
	private ISelectionListener selectionListener = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
			// ignore our own selections
			if (sourcepart != GlobalVariablesView.this) {
			    reloadView();
			}
		}
	};
	
	private IMemento memento;
	private IActionBars actionBars;
	private IToolBarManager toolBarManager;
	private IStatusLineManager statusLineManager;

	public class GlobalVariableContainerNode {
		
		List<Object> fChildren = new ArrayList<Object>();
		private String fName;
		
		public GlobalVariableContainerNode(String name) {
			this.fName = name;
		}

		public String getName() {
			return fName;
		}
		
		public List<Object> getChildren() {
			return fChildren;
		}
	}
	
	private class GlobalVariablesChangeListener implements IGlobalVariablesChangeListener, IPartListener2 {

		@Override
		public void globalVariablesChanged(final GlobalVariablesProvider gvProvider, String projectName) {
			if (activeProject == null) {
				return;
			}
			if (projectName.equals(activeProject.getName())) {
				Display.getDefault().asyncExec(new Runnable() {
				
					@Override
					public void run() {
						fTreeViewer.setInput(getRootInput(gvProvider));
						refresh();
					}
				});
			} else {
				
			}
		}

		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
			reloadView();
		}

		@Override
		public void partBroughtToTop(IWorkbenchPartReference partRef) {
			partActivated(partRef);
		}
		
		@Override
		public void partClosed(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partDeactivated(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partHidden(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partInputChanged(IWorkbenchPartReference partRef) {
			partActivated(partRef);
		}

		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
			partActivated(partRef);
		}

		@Override
		public void partVisible(IWorkbenchPartReference partRef) {
			partActivated(partRef);
		}
	}

	public void reloadView() {
		IProject project = getSelectedProject();
		if(projectName == null){
			fOpenGvEditorAction.setEnabled(false);
		} else {
			fOpenGvEditorAction.setEnabled(true);
		}
			
		if (project != null && !project.isOpen()) {
			activeProject = project;
			fTreeViewer.setInput(null);
			refresh();
			return;
		}
		if (project == activeProject) {
			if (activeProject != null) {
				projectName = activeProject.getName();
			}else{
				//fTreeViewer.setInput(null);
			}
			return;
		}
		activeProject = project;
		if (activeProject == null) {
			return;
		}
		projectName = activeProject.getName();
		
		gvProvider = GlobalVariablesMananger.getInstance().getProvider(projectName);
		fTreeViewer.setInput(getRootInput(gvProvider));
		refresh();
	}
	
	class NameSorter extends ViewerSorter {
		@Override
		public int compare(Viewer viewer, Object e1, Object e2){
		
			if(e1 instanceof GlobalVariableDescriptor && e2 instanceof GlobalVariableDescriptor ){
				return ((GlobalVariableDescriptor)e1).getName().compareTo(((GlobalVariableDescriptor)e2).getName());
			}
			else if(e1 instanceof GlobalVariableContainerNode && e2 instanceof GlobalVariableContainerNode ){
				return ((GlobalVariableContainerNode)e1).getName().compareTo(((GlobalVariableContainerNode)e2).getName());
			}
			else{
				return 0;
			}
		}
	}

	/**
	 * The constructor.
	 */
	public GlobalVariablesView() {
		GlobalVariablesMananger.getInstance().addChangeListener(fListener);
	}

	public void refresh() {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				if (!fTreeViewer.getTree().isDisposed()) {
					Object expObjs[] = fTreeViewer.getExpandedElements();
					fTreeViewer.refresh();
					fTreeViewer.setExpandedElements(expObjs);
				}
			}
		});
	}

	@Override
	public void dispose() {
		GlobalVariablesMananger.getInstance().removeChangeListener(fListener);
		if(getSite() != null) {
			getSite().getPage().removePartListener(fListener);
			getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(selectionListener);
			super.dispose();
		}
	}

	private void createColumnViewers() {
		createTreeViewerColumn("Name");
		createTreeViewerColumn("Value");		
		createTreeViewerColumn("Type");		
		createTreeViewerColumn("Override");		
		createTreeViewerColumn("Project Source");		
		
		Tree tree = fTreeViewer.getTree();
		tree.setHeaderVisible(true);
		
		for (int i=0; i<tree.getColumnCount(); i++) {
			tree.getColumn(i).pack();
		}
		
		tree.getColumn(0).setWidth(150);
		tree.getColumn(1).setWidth(150);
		tree.getColumn(2).setWidth(80);
		tree.getColumn(3).setWidth(40);
		tree.getColumn(4).setWidth(150);
	}
	
	private TreeViewerColumn createTreeViewerColumn(String colName) {
		TreeViewerColumn column = new TreeViewerColumn(fTreeViewer, SWT.NONE);
		column.getColumn().setText(colName);
		column.getColumn().setResizable(true);
		column.getColumn().setMoveable(false);
		return column;
	}
	
	
	public void createPartControl(Composite parent) {
		createControl(parent);
		getSite().getPage().addPartListener(fListener);
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(selectionListener);
	}
	
	public void createControl(Composite parent) {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage = window.getActivePage();
		IProject project = null;
		if (activePage != null) {
			IEditorPart activeEditor = activePage.getActiveEditor();
			if (activeEditor != null) {
				IEditorInput editorInput = activeEditor.getEditorInput();
				if (editorInput instanceof FileEditorInput) {
					project = ((FileEditorInput) editorInput).getFile().getProject();
					activeProject = project;
				}
			}
		}
		createControl(parent, project);
	}
	
	@Override
	public void createControl(Composite parent, IProject project) {
		fTreeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		createColumnViewers();
		fDrillDownAdapter = new DrillDownAdapter(fTreeViewer);
		
		fContentProvider = new GlobalVariablesContentProvider();
		fTreeViewer.setContentProvider(fContentProvider);
		GlobalVariablesLabelProvider labelProvider = new GlobalVariablesLabelProvider();
		fTreeViewer.setLabelProvider(labelProvider);
		fTreeViewer.setSorter(new NameSorter());
		if (project != null) {
			gvProvider = GlobalVariablesMananger.getInstance().getProvider(project.getName());
			fTreeViewer.setInput(getRootInput(gvProvider));
		} else {
			fTreeViewer.setInput(null);
		}

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(fTreeViewer.getControl(), "com.tibco.cep.studio.ui.viewer");
	
		initDragSupport();
		initializeActions();
		hookContextMenu();
		//hookDoubleClickAction();
		contributeToActionBars();
	}

	@Override
	public Control getControl() {
		return fTreeViewer.getControl();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		if(adapter == IReusableTrayView.class) {
			return this;
		}
		return super.getAdapter(adapter);
	}
	
	public void init(IActionBars bars, IToolBarManager toolBarManager,
			IStatusLineManager statusLineManager, IMemento memento) {
		this.memento = memento;
		this.actionBars = bars;
		this.toolBarManager = toolBarManager;
		this.statusLineManager = statusLineManager;
	}
	
	@Override
	public void update(IWorkbenchPart part, Control c, boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	
	private void initDragSupport() {
		if(getViewSite() != null) {
			
			fTreeViewer.addDragSupport(DND.DROP_COPY | DND.DROP_MOVE,  new Transfer[] {TextTransfer.getInstance()},
					new GlobalVariablesDragListener(fTreeViewer, getSite().getPage()));
		} else {
			fTreeViewer.addDragSupport(DND.DROP_COPY | DND.DROP_MOVE,  new Transfer[] {TextTransfer.getInstance()},
					new GlobalVariablesDragListener(fTreeViewer, null));			
		}
		/*
		DragSource ds = new DragSource(fTreeViewer.getTree(), DND.DROP_MOVE);
		ds.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		ds.addDragListener(new GlobalVariablesDragListener(fTreeViewer, getSite().getPage()));
		*/
	}
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				GlobalVariablesView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(fTreeViewer.getControl());
		fTreeViewer.getControl().setMenu(menu);
		if(getSite() != null){
			getSite().registerContextMenu(menuMgr, fTreeViewer);
		} 
	}

	private void contributeToActionBars() {
		if(getViewSite() != null) {
			IActionBars bars = getViewSite().getActionBars();
			fillLocalPullDown(bars.getMenuManager());
			fillLocalToolBar(bars.getToolBarManager());
		}
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(fOpenGvEditorAction);
		//manager.add(fAddGroupAction);
		//		manager.add(fCopyAction);
		manager.add(new Separator());
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(fOpenGvEditorAction);
		//manager.add(fAddGroupAction);
		//manager.add(fCopyAction);
		manager.add(new Separator());
		//fDrillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(fOpenGvEditorAction);
		//manager.add(fAddGroupAction);
//		manager.add(fCopyAction);
		manager.add(new Separator());
		fDrillDownAdapter.addNavigationActions(manager);
	}

	private void initializeActions() {
		initializeCopyAction();
		initializeDoubleClickAction();
		initializeOpenGvEditorAction();
	}
	
	private void initializeCopyAction() {
		fCopyAction = new Action() {
			public void run() {
				ISelection selection = fTreeViewer.getSelection();
				StringBuffer buffer = new StringBuffer();
				if (selection instanceof StructuredSelection) {
					Object[] array = ((StructuredSelection) selection).toArray();
					for (Object obj : array) {
						if (obj instanceof GlobalVariableDescriptor) {
							String name = ((GlobalVariableDescriptor) obj).getFullName();
							if (name.startsWith("/")) {
								name = name.substring(1);
							}
							buffer.append("%%"+name+"%%");
						} else {
							buffer.append(obj.toString());
						}
					}
				}
				final Clipboard cb = new Clipboard(Display.getDefault());

				String textData = buffer.toString();
				if (textData.length() > 0) {
					TextTransfer textTransfer = TextTransfer.getInstance();
					cb.setContents(new Object[]{textData}, new Transfer[]{textTransfer});
				}
			}
		};
		fCopyAction.setText("Copy");
		fCopyAction.setToolTipText("Copy to clipboard");
		fCopyAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
	}

	private void initializeDoubleClickAction() {
		fDoubleClickAction = new Action() {
			public void run() {
				ISelection selection = fTreeViewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				showMessage("Double-click detected on "+obj.toString());
			}
		};
	}
	
	private void initializeOpenGvEditorAction() {
		fOpenGvEditorAction = new Action() {
			public void run() {
				try {
					if (activeProject == null) {
						if(projectName == null){
							fOpenGvEditorAction.setEnabled(false);
							return;
						} else {
							fOpenGvEditorAction.setEnabled(true);
							activeProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
						}
					}
					IFolder gvFolder = activeProject.getFolder("defaultVars");
					if (!gvFolder.exists()) {
						gvFolder.create(true, true, null);
					}
					IFile gvFile = gvFolder.getFile("defaultVars.substvar");
					IEditorInput editorInput = new FileEditorInput(gvFile);
					IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					IWorkbenchPage page = window.getActivePage();
					page.openEditor(editorInput, "com.tibco.cep.studio.ui.editor.GlobalVariablesEditor");
				} catch (CoreException e) {
				}
			}

		};
		fOpenGvEditorAction.setText("Edit Global Variables...");
		fOpenGvEditorAction.setToolTipText("Edit Global Variables...");
		fOpenGvEditorAction.setImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/global_var_edit.png"));
	}

	/**
	 * @return
	 */
	private IProject getSelectedProject() {
		ISelectionService service = getSite().getWorkbenchWindow().getSelectionService();
		if (getSite().getWorkbenchWindow().getActivePage() == null) {
			return null;
		}
		IWorkbenchPart part = getSite().getWorkbenchWindow().getActivePage().getActivePart();
		if (part instanceof IViewPart && ((IViewPart)part).getViewSite().getId().equals("com.tibco.cep.studio.projectexplorer.view")) {
			IStructuredSelection selection = (IStructuredSelection) service.getSelection("com.tibco.cep.studio.projectexplorer.view");
			if (selection != null && selection.getFirstElement() instanceof IResource)
				return (((IResource)selection.getFirstElement()).getProject());
		}
		//Handling File Editors for GV View update 
		if (part instanceof IEditorPart) {
			if (((IEditorPart)part).getEditorInput() instanceof FileEditorInput) {
				return ((FileEditorInput)((IEditorPart)part).getEditorInput()).getFile().getProject();
			}
		}
		return null;
	}
	
	private void hookDoubleClickAction() {
		fTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				fDoubleClickAction.run();
			}
		});
	}
	
	private void showMessage(String message) {
		MessageDialog.openInformation(
			fTreeViewer.getControl().getShell(), "Global variables", message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		fTreeViewer.getControl().setFocus();
	}
	
	@SuppressWarnings("rawtypes")
	private GlobalVariableContainerNode getRootInput(GlobalVariablesProvider gvProvider) {
		GlobalVariableContainerNode rootNode = new GlobalVariableContainerNode("_invisibleRoot");
		Collection variables = gvProvider.getVariables();
		for (Iterator iterator = variables.iterator(); iterator.hasNext();) {
			GlobalVariableDescriptor desc = (GlobalVariableDescriptor) iterator.next();
			String path = desc.getPath();
			int idx = path.indexOf('/');
			if (idx != -1 && path.length() > 1) {
				int i;
				GlobalVariableContainerNode parentNode = getContainerNode(rootNode, path);
				parentNode.getChildren().add(desc);
			} else {
				rootNode.getChildren().add(desc);
			}
		}

		return rootNode;
	}

	private GlobalVariableContainerNode getContainerNode(GlobalVariableContainerNode parentNode, String fullPath) {
		String path = fullPath.substring(0, fullPath.indexOf('/'));
		fullPath = fullPath.substring(fullPath.indexOf('/')+1);
		List<Object> children = parentNode.getChildren();
		for (int i = 0; i < children.size(); i++) {
			Object object = children.get(i);
			if (object instanceof GlobalVariableContainerNode) {
				GlobalVariableContainerNode container = (GlobalVariableContainerNode) object;
				String containerName = container.getName();
				if (containerName.equals(path)) {
					if (fullPath != null && fullPath.length() > 0) {
						return getContainerNode(container, fullPath);
					} else {
						return container;
					}
				}
			}
		}
		// hasn't been created yet
		GlobalVariableContainerNode container = new GlobalVariableContainerNode(path);
		parentNode.getChildren().add(container);
		if (fullPath != null && fullPath.length() > 0) {
			return getContainerNode(container, fullPath);
		} else {
			return container;
		}
	}

	@Override
	public IWorkbenchPart getContributingPart() {
		return getSite().getPage().getActiveEditor();
	}
	
	
	public GlobalVariablesProvider getGVProvider() {
		return gvProvider;
	}

	@Override
	public void setInput(Object project) {
		this.activeProject = (IProject) project;
	}
}