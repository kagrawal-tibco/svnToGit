package com.tibco.cep.bpmn.ui.editor;

import static com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils.setProcessClipboard;
import static com.tibco.cep.diagramming.utils.DiagramUtils.display;
import static com.tibco.cep.diagramming.utils.DiagramUtils.unselect;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CommandNotMappedException;
import org.eclipse.ui.actions.ContributedAction;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ConfigurationElementSorter;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.IUpdate;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.bpmn.core.nature.BpmnProjectNatureManager;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnIndexUpdateHandler;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.graph.properties.PropertySheetPage;
import com.tibco.cep.bpmn.ui.graph.tool.IGraphSelectTool;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.drawing.IDiagramManager;
import com.tibco.cep.diagramming.drawing.IDiagramModelAdapter;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.interactive.service.TSEAllOptionsServiceInputData;
import com.tomsawyer.interactive.swing.TSSwingCanvas;
import com.tomsawyer.interactive.swing.overview.TSEOverviewComponent;
import com.tomsawyer.service.layout.TSHierarchicalLayoutInputTailor;

public class BpmnEditor extends AbstractSaveableGraphEditorPart implements ITabbedPropertySheetPageContributor, IGotoMarker {
	

	
	private static class SelectionManager extends EventManager {
		/**
		 * 
		 * @param listener
		 *            listen
		 */
		public void addSelectionChangedListener(
				ISelectionChangedListener listener) {
			addListenerObject(listener);
		}

		/**
		 * 
		 * @param listener
		 *            listen
		 */
		public void removeSelectionChangedListener(
				ISelectionChangedListener listener) {
			removeListenerObject(listener);
		}

		/**
		 * 
		 * @param event
		 *            the event
		 */
		public void selectionChanged(final SelectionChangedEvent event) {
			// pass on the notification to listeners
			Object[] listeners = getListeners();
			for (int i = 0; i < listeners.length; ++i) {
				final ISelectionChangedListener l = (ISelectionChangedListener) listeners[i];
				SafeRunner.run(new SafeRunnable() {
					public void run() {
						l.selectionChanged(event);
					}
				});
			}
		}

	}
	
	/**
	 * A selection provider/listener for this view. It is a selection provider
	 * for this view's site.
	 */
	public class SelectionProvider implements IPostSelectionProvider {

		private SelectionManager fSelectionListener = new SelectionManager();

		private SelectionManager fPostSelectionListeners = new SelectionManager();
		
		private ISelection selection = null;

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IPostSelectionProvider#addPostSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
		 */
		public void addPostSelectionChangedListener(
				ISelectionChangedListener listener) {
			fPostSelectionListeners.addSelectionChangedListener(listener);
		}

		/*
		 * (non-Javadoc) Method declared on ISelectionProvider.
		 */
		public void addSelectionChangedListener(
				ISelectionChangedListener listener) {
			fSelectionListener.addSelectionChangedListener(listener);
		}

		/*
		 * (non-Javadoc) Method declared on ISelectionProvider.
		 */
		public ISelection getSelection() {
			if(selection != null) {
				return selection;
			}
			return StructuredSelection.EMPTY;
		}

		/**
		 * The selection has changed, so notify any post-selection listeners.
		 * 
		 * @param event
		 *            the change
		 */
		public void postSelectionChanged(final SelectionChangedEvent event) {
			fPostSelectionListeners.selectionChanged(event);			
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IPostSelectionProvider#removePostSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
		 */
		public void removePostSelectionChangedListener(
				ISelectionChangedListener listener) {
			fPostSelectionListeners.removeSelectionChangedListener(listener);
		}

		/*
		 * (non-Javadoc) Method declared on ISelectionProvider.
		 */
		public void removeSelectionChangedListener(
				ISelectionChangedListener listener) {
			fSelectionListener.removeSelectionChangedListener(listener);
		}

		/**
		 * The selection has changed. Process the event, notifying selection
		 * listeners and post selection listeners.
		 * 
		 * @param event
		 *            the change
		 */
		public void selectionChanged(final SelectionChangedEvent event) {
			fSelectionListener.selectionChanged(event);
		}

		/*
		 * (non-Javadoc) Method declared on ISelectionProvider.
		 */
		public void setSelection(ISelection selection) {
			if(this.selection != selection) {
				this.selection = selection;
				SelectionChangedEvent event = new SelectionChangedEvent(this, selection);
				selectionChanged(event);
				postSelectionChanged(event);
			}
		}
	}
	
	
	
	
	public static final String PERSPECTIVE_ID = "com.tibco.cep.diagramming.diagram.perspective";
	public final static String ID = "com.tibco.cep.bpmn.ui.editors.BpmnEditor";

	protected BpmnDiagramManager bpmnGraphDiagramManager;
	protected int pageIndex;
	private TextEditor sourcePage;
	protected boolean isEditorModified;
	private HashMap<String, Action> handler;
	private PropertySheetPage propertySheetPage;
	private BpmnEditorPartListener graphListener = null;
	
	private BpmnEditorInput bpmnGraphEditorInput;

	@SuppressWarnings("unused")
	private IEditorSite site;

    private IAnnotationModel graphAnnotationModel;
    
    
	static{
		BpmnIndexUpdateHandler.registerHandlers(new BpmnEditorIndexUpdateHandler());
	}
	

	/** The actions registered with the editor. */
	private Map<Object,Object> fActions= new HashMap<Object,Object>(10);

	/**----------------------------------------------------
	 * New Palette Code 
	 * ----------------------------------------------------
	 */
//	private PaletteRoot root;
	@SuppressWarnings("unused")
	private IEditorInput input;
	
	
	protected static final String PALETTE_DOCK_LOCATION = "Dock location"; //$NON-NLS-1$


	protected static final String PALETTE_SIZE = "Palette Size"; //$NON-NLS-1$


	protected static final String PALETTE_STATE = "Palette state"; //$NON-NLS-1$

	

	protected static final int DEFAULT_PALETTE_SIZE = 130;
	
	static {
		BpmnUIPlugin.getDefault().getPreferenceStore()
				.setDefault(PALETTE_SIZE, DEFAULT_PALETTE_SIZE);
	}

	public BpmnEditor() {
		bpmnGraphDiagramManager = new BpmnDiagramManager(this);
		GraphDocumentProvider provider = BpmnUIPlugin.getDefault().getDocumentProvider();
		setDocumentProvider(provider);
	}
	
	private void addFormPage() {
		getBpmnGraphDiagramManager().createPartControl(getContainer());
		pageIndex = addPage(getBpmnGraphDiagramManager().getControl());
		this.setActivePage(0);
		
		//important: set palette root to the Edit domain here i.e. after editor pages created
//		getEditDomain().setPaletteRoot(getPaletteRoot());
	}
	
	/**
	 * create the model
	 * @throws Exception 
	 */
	public void createModel(IEditorInput input) throws Exception {
		IFile file = null;
		if (input instanceof IFileEditorInput) {
			file = ((IFileEditorInput) input).getFile();
		}
		createModel(file);
	}
	
	public void createModel(IFile file) throws Exception {
		if (file != null) {
			setEditingDomain(createEditingDomain(file));
			getEditingDomain().getCommandStack().addCommandStackListener(
					new CommandStackListener() {
						public void commandStackChanged(final EventObject event) {
							getContainer().getDisplay().asyncExec(
									new Runnable() {
										public void run() {
											modified();
											Command mostRecentCommand = ((CommandStack) event
													.getSource())
													.getMostRecentCommand();
											if (mostRecentCommand != null) {
												// TODO
											}
										}
									});
						}
					});
		}
	}
	
	
	@Override
	protected void createPages() {
		createUIEditorPage();
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}		
		updateTitle();
		
	}
	private void createUIEditorPage() {
		try {
			addFormPage();
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		
	}
	
	
	


	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#dispose()
	 */
	@Override
	public void dispose() {
		try{
			//removing all part listeners associated from createTool 
			if (getBpmnGraphDiagramManager().getCreateToolPartListeners() != null) {
				for (IPartListener listener: getBpmnGraphDiagramManager().getCreateToolPartListeners()) {
					getSite().getPage().removePartListener(listener);
				}
			}
			if (graphListener != null) {
				getSite().getPage().removePartListener(graphListener);
			}
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
			super.dispose();
		}
		catch(Exception e){
			BpmnUIPlugin.log(e);
		}

	}
	
	@Override
	public void doRefresh(IFile file) {
		super.doRefresh(file);
		try {
			IFile oldFile = ((BpmnEditorInput)getEditorInput()).getFile();
			if (oldFile.getFullPath().equals(file.getFullPath())) {
				((BpmnEditorInput) getEditorInput()).initialize();
				try{
					bpmnGraphDiagramManager.setLoading(true);
					bpmnGraphDiagramManager.reLoadModel();
				}finally{
					bpmnGraphDiagramManager.setLoading(false);
				}
				
				
			}
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
	}
	
	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}
	
	public void enableActions(){
		if (handler == null) {
			handler = new HashMap<String, Action>();
		}
		Set<String> keySet = handler.keySet();
		for (String key : keySet) {
			BpmnEditorHandler action = (BpmnEditorHandler)handler.get(key);
			boolean enabled = action.isEnabled();
			action.setEnabled(enabled);
		}
	}

	/**
	 * Returns the action with the given action id that has been contributed via XML to this editor.
	 * The lookup honors the dependencies of plug-ins.
	 *
	 * @param actionID the action id to look up
	 * @return the action that has been contributed
	 */
	private IAction findContributedAction(String actionID) {
		List<IConfigurationElement> actions= new ArrayList<IConfigurationElement>();
		IConfigurationElement[] elements= Platform.getExtensionRegistry().getConfigurationElementsFor(PlatformUI.PLUGIN_ID, "editorActions"); //$NON-NLS-1$
		for (int i= 0; i < elements.length; i++) {
			IConfigurationElement element= elements[i];
			if ("editorContribution".equals(element.getName())) {
				if (!getSite().getId().equals(element.getAttribute("targetID"))) //$NON-NLS-1$
					continue;

				IConfigurationElement[] children= element.getChildren("action"); //$NON-NLS-1$
				for (int j= 0; j < children.length; j++) {
					IConfigurationElement child= children[j];
					if (actionID.equals(child.getAttribute("actionID"))) //$NON-NLS-1$
						actions.add(child);
				}
			}
		}
		int actionSize= actions.size();
		if (actionSize > 0) {
			IConfigurationElement element;
			if (actionSize > 1) {
				IConfigurationElement[] actionArray= (IConfigurationElement[])actions.toArray(new IConfigurationElement[actionSize]);
				ConfigurationElementSorter sorter= new ConfigurationElementSorter() {
					/*
					 * @see org.eclipse.ui.texteditor.ConfigurationElementSorter#getConfigurationElement(java.lang.Object)
					 */
					public IConfigurationElement getConfigurationElement(Object object) {
						return (IConfigurationElement)object;
					}
				};
				sorter.sort(actionArray);
				element= actionArray[0];
			} else
				element= (IConfigurationElement)actions.get(0);

			try {
				return new ContributedAction(getSite(), element);
			} catch (CommandNotMappedException e) {
				// out of luck, no command action mapping
			}
		}

		return null;
	}
	
	public IAction getAction(String actionID) {
		Assert.isNotNull(actionID);
		IAction action= (IAction) fActions.get(actionID);

		if (action == null) {
			action= findContributedAction(actionID);
			if (action != null)
				setAction(actionID, action);
		}

		return action;
	}
	
	/**
	 * @param id
	 * @param graphManager
	 * @param canvas
	 * @param layoutManager
	 * @return
	 */
	public Action getActionHandler(String id, BpmnDiagramManager diagramManger, TSEGraphManager graphManager, DrawingCanvas canvas, BpmnLayoutManager layoutManager) {
		if (handler == null) {
			handler = new HashMap<String, Action>();
		}
		if (handler.get(id) == null) {
			handler.put(id, new BpmnEditorHandler(this, id,
					diagramManger,
					graphManager,
					canvas,
					layoutManager));
		}
		return handler.get(id);
	}
	

	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySheetPage.class) {
			return getPropertySheetPage();
		} else if (IGotoMarker.class.equals(adapter)) {
			return this;
		} else if(adapter == IGraphSelectTool.class){
			return getBpmnGraphDiagramManager().getSelectTool();
		} else if(adapter == IGraphEditor.class) {
			return this;
		} else if(adapter == BpmnEditor.class) {
			return this;
		} else if( adapter == ITextEditor.class) {
			return new BpmnTextEditorAdapter(this);
		} else if( adapter == IProject.class ) {
			return getProject();
		} 
		return super.getAdapter(adapter);
	}
	

	/**
	 * @return the bpmnGraphDiagramManager
	 */
	public BpmnDiagramManager getBpmnGraphDiagramManager() {
		return bpmnGraphDiagramManager;
	}	

	public TSEGraph getCompositeGraph() {
		return null;
	}
	
	public TSEGraph getConcurrentGraph() {
		return null;
	}
	
	@Override
	public String getContributorId() {
		return "com.tibco.cep.bpmn.ui.propertyContributor";
	}
	
	@Override
	public IDiagramManager getDiagramManager() {
		return bpmnGraphDiagramManager;
	}

	public IDiagramModelAdapter getDiagramModelAdapter() {
    	return bpmnGraphDiagramManager.getDiagramModelAdapter();
    }
	
	@Override
	public TSSwingCanvas getDrawingCanvas() {
		return bpmnGraphDiagramManager.getDrawingCanvas();
	}

	public IAnnotationModel getGraphAnnotationModel() {
		return graphAnnotationModel;
	}
	
	
	@Override
	public TSEGraphManager getGraphManager() {
		return bpmnGraphDiagramManager.getGraphManager();
	}

	public TSHierarchicalLayoutInputTailor getHierarchicalLayoutInputTailor() {
		return null;
	}

	@Override
	public com.tibco.cep.diagramming.drawing.LayoutManager getLayoutManager() {
		return bpmnGraphDiagramManager.getLayoutManager();
	}

	@Override
	public TSEOverviewComponent getOverviewComponent() {
		return bpmnGraphDiagramManager.getOverviewComponent();
	}
	
	public int getPageIndex() {
		return pageIndex;
	}
	
	@Override
	public PALETTE getPalette() {
		return PALETTE.GRAPH;
	}
	
	public BpmnEditorPartListener getPartListener() {
		return graphListener;
	}

	@Override
	public String getPerspectiveId() {
		return PERSPECTIVE_ID;
	}
	
	
	/**
	 * Get the new property sheet page for this editor.
	 * 
	 * @return the new property sheet page.
	 */
	public TabbedPropertySheetPage getPropertySheetPage() {
		if (propertySheetPage == null || propertySheetPage.getControl() == null) {
			propertySheetPage = new PropertySheetPage(this);
		}
		return propertySheetPage;
	}
	
	public TSEAllOptionsServiceInputData getServiceInputData() {
		return bpmnGraphDiagramManager.getServiceInputData();
	}


	public TextEditor getSourcePage() {
		return sourcePage;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.ide.IGotoMarker#gotoMarker(org.eclipse.core.resources.IMarker)
	 */
	@Override
	public void gotoMarker(IMarker marker) {
		try{
			if(marker == null) return;
//			Object typeAttr = marker.getAttribute(PROCESS_OBJECT_TYPE_MARKER_ATTRIB);
//			if (typeAttr != null) {
				if(marker.getAttribute(IMarker.LOCATION) == null)
					return;
				String location_id = marker.getAttribute(IMarker.LOCATION).toString();
				for (TSEObject object : bpmnGraphDiagramManager.getModelGraphFactory().getNodeRegistry().getAllNodes()){
					if (object instanceof TSENode) {
						TSENode node = (TSENode)object;
						EObject userObject = (EObject) node.getUserObject();
						EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
						String nodeId = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
						if (location_id.intern() == nodeId.intern()) {
							unselect((TSEGraph)bpmnGraphDiagramManager.getGraphManager().getMainDisplayGraph());
							display(node, bpmnGraphDiagramManager);
							break;
						}
					}
				}
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean isEditable(){
		if (IndexUtils.isProjectLibType(file)) {
			return false;
		}
		if (getEditorInput() instanceof JarEntryEditorInput) {
			return false;
		}
		return true;
	}
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		try {
			this.input = input;
			this.site = site;
//			setEditDomain(new DefaultEditDomain(this));
			if (input instanceof BpmnEditorInput || input instanceof FileEditorInput) {
				if(input instanceof FileEditorInput)
					file = ((FileEditorInput)input).getFile();	
				else
					file = ((BpmnEditorInput)input).getFile();	
				// if project is not having bpmn nature, set bpmn nature
				IProject proj = file.getProject();
				if(!BpmnProjectNatureManager.getInstance().isBpmnProject(proj)){
					BpmnProjectNatureManager.getInstance().enableBpmnNature(proj, true);
				}
				
				bpmnGraphEditorInput = new BpmnEditorInput(this,file);
				bpmnGraphEditorInput.initialize();
				if(input instanceof BpmnEditorInput){
					bpmnGraphEditorInput.setDescription(((BpmnEditorInput)input).getDescription());
					bpmnGraphEditorInput.setIsPrivateProcess(((BpmnEditorInput)input).isPrivateProcess());
					((BpmnEditorInput)input).setGraphEditor(this);
				}
				super.init(site, bpmnGraphEditorInput);
				setSelectionProvider(new SelectionProvider());
				if(graphListener == null){
					graphListener = new BpmnEditorPartListener(this);
				}
				site.getPage().addPartListener(graphListener);
				getBpmnGraphDiagramManager().init();
				this.graphAnnotationModel = getDocumentProvider().getAnnotationModel(bpmnGraphEditorInput);
					
				
			} else {
				super.init(site, input);			
			}
		    
			//creating process swt edit clipboard
			setProcessClipboard(this);
		
		} catch (Exception e) {
			throw new PartInitException("Failed to initialize editor",e);
		}
		

	}
	
	public void initializeGraphDocumentationModel(){
		try {
			if(this.graphAnnotationModel == null){
				 getDocumentProvider().connect(bpmnGraphEditorInput);
				 this.graphAnnotationModel = getDocumentProvider().getAnnotationModel(bpmnGraphEditorInput);
				 this.bpmnGraphDiagramManager.addAnnotationModelListener();
			}
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		
	}

	public void inputChanged() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}


	@Override
	public void modified() {
		super.modified();
		
		enableActions();
	}


	/**
	 * This method rebinds the extension defs to the objects when the index is rebuilt
	 * @param project
	 * @param index
	 * @throws Exception
	 */
	public void onIndexUpdate(IProject project, EObject index) throws Exception{
		if(bpmnGraphEditorInput == null || getProject() != project ) {
			return;
		}
		bpmnGraphEditorInput.onIndexUpdate(project, index);		
	}
	
	
	@Override
	protected void postSaveResource() {
//		((BasicCommandStack) editingDomain.getCommandStack()).saveIsDone();
		setModified(false);	
		
		//Refresh Breakpoints for new nodes
		bpmnGraphDiagramManager.refreshBreakpoints();
//		bpmnGraphDiagramManager.showTaskIcons();
	}
	
	/**
	 * @param file
	 * @param pm 
	 */
	public void saveModel(IFile file, IProgressMonitor pm) {
		getBpmnGraphDiagramManager().saveModel(file,pm);
	}
	
	
	
	
	@Override
	protected void saveResource(IProgressMonitor pm) throws Exception {
		if(!isDirty())
			return;
		saveModel(bpmnGraphEditorInput.getFile(),pm);
	}
	
	public void setAction(String actionID, IAction action) {
		Assert.isNotNull(actionID);
		if (action == null) {
			action= (IAction) fActions.remove(actionID);
			if (action != null){
//				fActivationCodeTrigger.unregisterActionFromKeyActivation(action);
			}
		} else {
			if (action.getId() == null)
				action.setId(actionID); // make sure the action ID has been set
			fActions.put(actionID, action);
//			fActivationCodeTrigger.registerActionForKeyActivation(action);
		}
	}
	
	
	
	/**
	 * @param bpmnGraphDiagramManager the bpmnGraphDiagramManager to set
	 */
	public void setBpmnGraphDiagramManager(
			BpmnDiagramManager bpmnGraphDiagramManager) {
		this.bpmnGraphDiagramManager = bpmnGraphDiagramManager;
	}
	@Override
	public void setFocus() {
		super.setFocus();

	}
	@Override
	public void setLayoutManager(LayoutManager mgr) {
		bpmnGraphDiagramManager.getLayoutManager();		
	}
	/**
	 * @param pageIndex
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public void setPartListener() {
		
	}
	public void setSourcePage(TextEditor sourcePage) {
		this.sourcePage = sourcePage;
	}

	public void triggerAction(String actionID) {
		IAction action= getAction(actionID);
		if (action != null) {
			if (action instanceof IUpdate)
				((IUpdate) action).update();
			if (action.isEnabled())
				action.run();
		}
	}



	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractGraphicalPaletteEditor#getPaletteRoot()
	 */
//	@Override
//	protected PaletteRoot getPaletteRoot() {
//		if (root == null) {
//			IProject project = null;
//			if (input instanceof FileEditorInput) {
//				FileEditorInput fileEditorInput = (FileEditorInput)input;
//				project = fileEditorInput.getFile().getProject();
//			}
//			root = BpmnUIPlugin.createPalette(project, site);
//		}
//		return root;
//	}

//	/* (non-Javadoc)
//	 * @see com.tibco.cep.studio.ui.AbstractGraphicalPaletteEditor#createPalettePage()
//	 */
//	protected CustomPalettePage createPalettePage() {
//		return new CustomPalettePage(getPaletteViewerProvider()) {
//			public void init(IPageSite pageSite) {
//				super.init(pageSite);
//				IAction copy = getActionRegistry().getAction(
//						ActionFactory.COPY.getId());
//				pageSite.getActionBars().setGlobalActionHandler(
//						ActionFactory.COPY.getId(), copy);
//			}
//		};
//	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractGraphicalPaletteEditor#createPaletteViewerProvider()
	 */
//	protected PaletteViewerProvider createPaletteViewerProvider() {
//		return new PaletteViewerProvider(getEditDomain()) {
//			private IMenuListener menuListener;
//
//			protected void configurePaletteViewer(PaletteViewer viewer) {
//				super.configurePaletteViewer(viewer);
//				viewer.setCustomizer(new BPMNPaletteCustomizer());
//				viewer.addDragSourceListener(new BPMNTransferDragSourceListener(viewer));
//			}
//
//			protected void hookPaletteViewer(PaletteViewer viewer) {
//				super.hookPaletteViewer(viewer);
//				final CopyTemplateAction copy = (CopyTemplateAction) getActionRegistry()
//						.getAction(ActionFactory.COPY.getId());
//				viewer.addSelectionChangedListener(copy);
//				if (menuListener == null)
//					menuListener = new IMenuListener() {
//					public void menuAboutToShow(IMenuManager manager) {
//						if (copy != null) {
//							manager.appendToGroup(PaletteActionConstants.GROUP_COPY, copy);
//						}
//					}
//				};
//				viewer.getContextMenu().addMenuListener(menuListener);
//			}
//		};
//	}
}