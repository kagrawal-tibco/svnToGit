package com.tibco.cep.studio.ui.views;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISharedImages;
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

import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.FunctionsCategoryChangeListener;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.be.parser.semantic.FunctionsCatalogManager.ObservableMapStore;
import com.tibco.be.parser.semantic.FunctionsCatalogManager.ObservableMapStore.ObservableActions;
import com.tibco.cep.studio.core.FunctionsHelpBundleMananger;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.providers.CatalogFunctionsContentProvider;
import com.tibco.cep.studio.ui.providers.CatalogFunctionsLabelProvider;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;
import com.tibco.cep.studio.ui.util.Messages;

public class CatalogFunctionsView extends ViewPart implements IContributedContentsView {
	
	public class CustomFunctionsCatalogNode {
		
		private Map<String, FunctionsCatalog> fCatalogs;

		public CustomFunctionsCatalogNode(Map<String, FunctionsCatalog> catalogs) {
			this.fCatalogs = catalogs;
		}

		public Map<String, FunctionsCatalog> getCatalogs() {
			return fCatalogs;
		}
		
	}
	
	public class FunctionsCatalogNode {

		public static final int STATIC_CATALOG = 0;
		public static final int ONTOLOGY_CATALOG = 1;
		public static final int CUSTOM_CATALOG = 2;
		
		private FunctionsCatalog fCatalog;
		private int fType;

		public FunctionsCatalogNode(FunctionsCatalog catalog, int type) {
			this.fCatalog = catalog;
			this.fType = type;
		}

		public FunctionsCatalog getCatalog() {
			return fCatalog;
		}

		public int getType() {
			return fType;
		}

		@Override
		public String toString() {
			if (fType == FunctionsCatalogNode.STATIC_CATALOG) {
				return "Built-in Functions";
			} else if (fType == FunctionsCatalogNode.ONTOLOGY_CATALOG) {
				return "Ontology Functions";
			} else if (fType == FunctionsCatalogNode.CUSTOM_CATALOG) {
				return "Custom Functions";
			}
			return "Catalog Functions";
		}
		
	}
	
	private class FunctionsChangeListener implements FunctionsCategoryChangeListener, IPartListener2,Observer {
		
		
		@Override
		public void update(Observable o, Object arg) {
			if (ObservableActions.PUT.equals(arg) || ObservableActions.REMOVE.equals(arg)) {
				refresh();
			}
		}

		@Override
		public void onCategoryChange(FunctionsCategory functionsCategory) {
			refresh();
		}

		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);
			if (part instanceof IEditorPart) {
				IEditorInput editorInput = ((IEditorPart) part).getEditorInput();
				String projectName = null;
				if (editorInput instanceof IFileEditorInput) {
					projectName = ((IFileEditorInput) editorInput).getFile().getProject().getName();
				} else if (editorInput instanceof JarEntryEditorInput) {
					projectName = ((JarEntryEditorInput) editorInput).getProjectName();
				}
				if (projectName != null) {
					fContentProvider.setActiveProject(projectName);
					refresh();
					// reset hover support specific to this project
					updateHoverSupport();
					FunctionsCatalogManager.getInstance().addCustomCategoryChangeListener(this);
				}
			} else if (part != null) {
				try {
					IEditorPart editor = part.getSite().getWorkbenchWindow().getActivePage().getActiveEditor();
					if (editor == null) {
						// there is no active editor, do not show any ontology functions
						fContentProvider.setActiveProject(null);
						refresh();
						// reset hover support specific to this project
						updateHoverSupport();
					} 
				} catch (Exception e) {
				}
			}
		}

		@Override
		public void partBroughtToTop(IWorkbenchPartReference partRef) {}
		@Override
		public void partClosed(IWorkbenchPartReference partRef) {}
		@Override
		public void partDeactivated(IWorkbenchPartReference partRef) {
			FunctionsCatalogManager.getInstance().removeCustomCategoryChangeListener(this);
		}
		@Override
		public void partHidden(IWorkbenchPartReference partRef) {}
		@Override
		public void partInputChanged(IWorkbenchPartReference partRef) {}
		@Override
		public void partOpened(IWorkbenchPartReference partRef) {}
		@Override
		public void partVisible(IWorkbenchPartReference partRef) {}

	}
	
	private TreeViewer fTreeViewer;
	private DrillDownAdapter fDrillDownAdapter;
	private Action fRefreshAction;
	private Action fCopyAction;
	private Action fDoubleClickAction;

	private FunctionsChangeListener fListener = new FunctionsChangeListener();
	private CatalogFunctionsContentProvider fContentProvider;
	private FunctionHoverMouseListener fMouseListener;
	
	class NameSorter extends ViewerSorter {
		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			if(e1 != null && e2 != null){
				if(e1 instanceof FunctionsCategory && !(e2 instanceof FunctionsCategory))
					return -1;
				else if(e2 instanceof FunctionsCategory && !(e1 instanceof FunctionsCategory))
					return 1;
			}
			
			return super.compare(viewer, e1, e2);
		}
	}

	/**
	 * The constructor.
	 */
	public CatalogFunctionsView() {
		StudioCorePlugin.getDefault().addFunctionsCategoryChangeListener(fListener);
	}

	public void refresh() {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				if (!fTreeViewer.getTree().isDisposed()) {
					fTreeViewer.refresh();
				}
			}
			
		});
	}

	@Override
	public void dispose() {
		getSite().getPage().removePartListener(fListener);
		super.dispose();		
	}

	public void createPartControl(Composite parent) {
		fTreeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		fDrillDownAdapter = new DrillDownAdapter(fTreeViewer);
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage = window.getActivePage();
		String projectName = null;
		if (activePage != null) {
			IEditorPart activeEditor = activePage.getActiveEditor();
			if (activeEditor != null) {
				IEditorInput editorInput = activeEditor.getEditorInput();
				if (editorInput instanceof FileEditorInput) {
					projectName = ((FileEditorInput) editorInput).getFile().getProject().getName();
				} else if (editorInput instanceof JarEntryEditorInput) {
					projectName = ((JarEntryEditorInput) editorInput).getProjectName();
				}
			}
		}
		fContentProvider = new CatalogFunctionsContentProvider(projectName);
		
		fTreeViewer.setContentProvider(fContentProvider);
		CatalogFunctionsLabelProvider labelProvider = new CatalogFunctionsLabelProvider();
		fTreeViewer.setLabelProvider(new DecoratingLabelProvider(labelProvider, PlatformUI.getWorkbench().getDecoratorManager()));
		fTreeViewer.setSorter(new NameSorter());
		FunctionsCatalogNode node1 = new FunctionsCatalogNode(FunctionsCatalogManager.getInstance().getStaticRegistry(), FunctionsCatalogNode.STATIC_CATALOG);
		FunctionsCatalogNode node2 = new FunctionsCatalogNode(FunctionsCatalogManager.getInstance().getOntologyRegistry(), FunctionsCatalogNode.ONTOLOGY_CATALOG);
		CustomFunctionsCatalogNode node3 = null;
		try {
			ObservableMapStore<String,FunctionsCatalog> registry = FunctionsCatalogManager.getInstance().getCustomRegistry();
			node3 = new CustomFunctionsCatalogNode(registry);
		} catch (NoClassDefFoundError e) {
			MessageDialog.openError(getViewSite().getShell()
					, Messages.getString("project.buildpath.tab.javalib.title")
					, Messages.getString("project.buildpath.tab.javalib.customfunc.classnotfound")+"\n"+e.getMessage());
			StudioUIPlugin.log(Messages.getString("project.buildpath.tab.javalib.customfunc.classnotfound")+"\n"+e.getMessage(), e);
		} catch (Exception e) {
			MessageDialog.openError(getViewSite().getShell()
					, Messages.getString("project.buildpath.tab.javalib.title")
					, Messages.getString("project.buildpath.tab.javalib.customfunc.load.error")+"\n"+e.getMessage());
			StudioUIPlugin.log(Messages.getString("project.buildpath.tab.javalib.customfunc.load.error")+"\n"+e.getMessage(), e);
		}
		if (node3 != null) {
			fTreeViewer.setInput(new Object[] { node1, node2, node3 });
		} else {
			fTreeViewer.setInput(new Object[] { node1, node2 });
		}

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(fTreeViewer.getControl(), "com.tibco.cep.studio.ui.viewer");
	
		fTreeViewer.addDragSupport(DND.DROP_COPY | DND.DROP_MOVE,  new Transfer[] {TextTransfer.getInstance()},
				new CatalogFunctionsDragListener(fTreeViewer, getSite().getPage()));
		
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		setHoverSupport(fTreeViewer);
		getSite().getPage().addPartListener(fListener);
	}

	private void updateHoverSupport() {
		if (fMouseListener != null) {
			fMouseListener.updateBundle(FunctionsHelpBundleMananger.getInstance().getHelpBundle(fContentProvider.getActiveProject()));
		}
	}
	
	/**
	 * Sets hover support for the function catalog treeviewer.
	 * 
	 * @param treeViewer
	 */
	private void setHoverSupport(TreeViewer treeViewer) {
		if (fMouseListener == null) {
			fMouseListener = FunctionHoverMouseListener
			.getInstance(FunctionsHelpBundleMananger.getInstance().getHelpBundle(fContentProvider.getActiveProject()));
			treeViewer.getTree().addMouseTrackListener(fMouseListener);
			treeViewer.getTree().addMouseListener(fMouseListener);
		}
	}
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				CatalogFunctionsView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(fTreeViewer.getControl());
		fTreeViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, fTreeViewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(fRefreshAction);
		manager.add(new Separator());
//		manager.add(fCopyAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(fRefreshAction);
		manager.add(fCopyAction);
		manager.add(new Separator());
		fDrillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(fRefreshAction);
//		manager.add(fCopyAction);
		manager.add(new Separator());
		fDrillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		fRefreshAction = new Action() {
			public void run() {
				refresh();
			}
		};
		fRefreshAction.setText("Refresh");
		fRefreshAction.setToolTipText("Refresh");
		fRefreshAction.setImageDescriptor(StudioUIPlugin.getImageDescriptor("icons/refresh.gif"));
		
		fCopyAction = new Action() {
			public void run() {
				ISelection selection = fTreeViewer.getSelection();
				StringBuffer buffer = new StringBuffer();
				if (selection instanceof StructuredSelection) {
					Object[] array = ((StructuredSelection) selection).toArray();
					for (Object obj : array) {
						buffer.append(obj.toString());
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
		fCopyAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		
		fDoubleClickAction = new Action() {
			public void run() {
				ISelection selection = fTreeViewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				if (fTreeViewer.getExpandedState(obj)) {
					fTreeViewer.collapseToLevel(obj, 1);
				} else {
					fTreeViewer.expandToLevel(obj, 1);
				}
			}
		};
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
			fTreeViewer.getControl().getShell(),
			"Ontology Functions",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		fTreeViewer.getControl().setFocus();
	}
	
	@Override
	public IWorkbenchPart getContributingPart() {
		return getSite().getPage().getActiveEditor();
	}
}