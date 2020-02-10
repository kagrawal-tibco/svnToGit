package com.tibco.cep.diagramming.drawing;

import static com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants.ANIMATION_LAYOUT_ALLOW;
import static com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants.ANIMATION_LAYOUT_FADE;
import static com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants.ANIMATION_LAYOUT_INTERPOLATION;
import static com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants.ANIMATION_LAYOUT_VIEWPORT_CHANGE;
import static com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants.FULL;
import static com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants.INCREMENTAL;
import static com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants.LAYOUT_ANIMATION_DURATION;
import static com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants.MAGNIFY_SIZE;
import static com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants.MAGNIFY_ZOOM;
import static com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants.RESET_TOOL_AFTER_CHANGES;
import static com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants.RUN_LAYOUT_ON_CHANGES;
import static com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants.UNDO_LIMIT;
import static com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants.VIEWPORT_CHANGE_DURATION;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.EditorPart;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.tool.CreateEdgeTool;
import com.tibco.cep.diagramming.tool.CreateNodeTool;
import com.tibco.cep.diagramming.tool.MagnificationTool;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.diagramming.utils.SyncXErrorHandler;
import com.tibco.cep.studio.ui.palette.views.PaletteView;
import com.tomsawyer.drawing.events.TSLayoutEvent;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.events.TSEEventManager;
import com.tomsawyer.graphicaldrawing.grid.TSGridPreferenceTailor;
import com.tomsawyer.interactive.TSInteractivePreferenceTailor;
import com.tomsawyer.interactive.animation.TSAnimationPreferenceTailor;
import com.tomsawyer.interactive.command.editing.TSEditingCommandPreferenceTailor;
import com.tomsawyer.interactive.swing.TSSwingCanvasPreferenceTailor;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateEdgeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolPreferenceTailor;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;
import com.tomsawyer.util.preference.TSPreferenceData;

/**
 * 
 * @author ggrigore
 *
 */
@SuppressWarnings("unchecked")
public class DiagramManager extends BaseDiagramManager implements IPropertyChangeListener, IDiagramManager {

	protected class DiagramMouseListener implements MouseListener {
		
		@Override
		public void mouseReleased(MouseEvent e) {}
		
		@Override
		public void mousePressed(MouseEvent e) {}
		
		@Override
		public void mouseExited(MouseEvent e) {}
		
		@Override
		public void mouseEntered(MouseEvent e) {}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				IWorkbenchPart activePart = getEditor().getEditorSite().getPage().getActivePart();
				if (activePart != null && activePart != getEditor()) {
					Display.getDefault().asyncExec(new Runnable() {
						
						@Override
						public void run() {
							getEditor().getEditorSite().getPage().activate(getEditor());
						}
					});
				}
			} catch (Exception ex) {
				// ignore
			}
		}
	}
	
	protected ManagedForm managedForm;
	protected IPreferenceStore diagramPrefStore;     
	protected EditorPart editor;
	
	protected TSAnimationPreferenceTailor animationPreferenceTailor;
	protected TSEditingToolPreferenceTailor editTailor;
	protected  TSInteractivePreferenceTailor intrTailor;
	protected Container pane;

	protected Composite diagramEditorControl;
	protected Set<IPartListener> createToolPartListeners;

	public Set<IPartListener> getCreateToolPartListeners() {
		return createToolPartListeners;
	}

	public void addCreateToolPartListener(IPartListener listener) {
		if (createToolPartListeners == null) {
			createToolPartListeners = new HashSet<IPartListener>();
		}
		createToolPartListeners.add(listener);
	}

	public Composite getDiagramEditorControl() {
		return diagramEditorControl;
	}
	
	public DiagramManager() {
		//initialize();
	}

	protected void initialize() {
		super.initialize();
		DiagrammingPlugin defInstance = DiagrammingPlugin.getDefault();
		if (defInstance != null)
			diagramPrefStore = DiagrammingPlugin.getDefault()
					.getPreferenceStore();
		createToolPartListeners = new HashSet<IPartListener>();
		
		TSEImage.setLoaderClass(this.getClass());				
		// TSESVGImage.initBatik();
		// TSESVGImage.setLoaderClass(this.getClass());
	}
	
	/**
	 * @param container
	 */
	public void createPartControl(Composite container) {
		this.managedForm = new ManagedForm(container);

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		getForm().getBody().setLayout(layout);

		ScrolledForm form = getForm();
		FormToolkit toolkit = this.managedForm.getToolkit();

		createFormParts(form, toolkit);
		// this.drawingCanvas.fitInCanvas(true);

		this.managedForm.initialize();
		this.initOverviewComponent();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				DiagramManager.this.graphManager.getEventManager().addListener(DiagramManager.this.graphManager, DiagramManager.this, TSLayoutEvent.POST_LAYOUT, TSLayoutEvent.class);
				DiagramManager.this.postPopulateDrawingCanvas();
			}
		});
	}
	
	public Control getControl() {
		return getForm();
	}

	public ScrolledForm getForm() {
		return this.managedForm.getForm();
	}	
	
	@Override
	public boolean canCopy() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean canCut() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean canDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean canPaste() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public TSGraphObject getCurrentSelection() {
		TSGraphObject obj = null;
		if (!isSelectedMultipleGraph()) {
			List<TSEEdge> edges = getSelectedEdges();
			List<TSENode> nodes = getSelectedNodes();
			if (nodes.size() > 0)
				obj = nodes.get(0);
			else if (edges.size() > 0)
				obj = edges.get(0);
			else
				obj = getGraphManager().selectedGraph();
		}

		return obj;
	}

	/**
	 * @param form
	 * @param toolkit
	 */
	protected void createFormParts(final ScrolledForm form, final FormToolkit toolkit) {
		diagramEditorControl = toolkit.createComposite(form.getBody(),SWT.EMBEDDED);
		GridLayout elayout = new GridLayout();
		elayout.numColumns = 1;
		elayout.makeColumnsEqualWidth = false;
		diagramEditorControl.setLayout(elayout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 110;
		gd.widthHint = 200;
		diagramEditorControl.setLayoutData(gd);
		waitForInitComplete();
		
		final Container swingContainer = getSwingContainer(diagramEditorControl);
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				swingContainer.add(createDrawingCanvas(), BorderLayout.CENTER);
				swingContainer.doLayout();
			}
		});
		toolkit.paintBordersFor(diagramEditorControl);
	}
	
	protected void setPreferences() {
		// DiagrammingPlugin.LOGGER.logDebug(null, "setting preferences");
		TSPreferenceData prefData = this.drawingCanvas.getPreferenceData();
		TSGridPreferenceTailor gridTailor = new TSGridPreferenceTailor(prefData);
		
		TSEditingCommandPreferenceTailor prefTailor = new TSEditingCommandPreferenceTailor(prefData);
		prefTailor.setApplyGeometricAdjustment(false);
		
		TSSwingCanvasPreferenceTailor swingTailor =	new TSSwingCanvasPreferenceTailor(prefData);
		// gridTailor.setPointGrid();
		gridTailor.setNoGrid();
		gridTailor.setSpacingX(15);
		gridTailor.setSpacingY(15);
		swingTailor.setAutoHideScrollBars(false);
	
		animationPreferenceTailor = new TSAnimationPreferenceTailor(prefData);
		IPreferenceStore prefstore=(DiagrammingPlugin.getDefault().getPreferenceStore());
	    boolean allow=prefstore.getBoolean(ANIMATION_LAYOUT_ALLOW);
	    setAnimationPreferences(allow);
        
	    editTailor = new TSEditingToolPreferenceTailor(prefData);
        editTailor.setReconnectEdgeSensitivity(10.0);
        intrTailor = new TSInteractivePreferenceTailor(prefData);
        intrTailor.setShowLayoutProgress(false);
        intrTailor.setLayoutCancelDelay(0);
        intrTailor.setLayoutPreserveFocus(false); // TODO: change this to true?
        intrTailor.setHitTolerance(5);
        intrTailor.setThreadedLayout(true);
        
        TSInteractivePreferenceTailor layoutIntrTailor = new TSInteractivePreferenceTailor(prefData);
        layoutIntrTailor.setDrawingFitToCanvas();
	}
	
	public void postPopulateDrawingCanvas() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (getDrawingCanvas().getZoomLevel() > 1.0) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							getDrawingCanvas().setZoomLevelInteractive(1.0);
						}
					});
				}
			}
		});
	}
	
	protected void registerTools() {
		super.registerTools();
		MagnificationTool magTool = new MagnificationTool();
		if (diagramPrefStore == null) {
			DiagrammingPlugin defInstance = DiagrammingPlugin.getDefault();
			if (defInstance != null) {
				diagramPrefStore = DiagrammingPlugin.getDefault().getPreferenceStore();
			}
		}
		magTool.setWindowSize(this.diagramPrefStore.getInt(MAGNIFY_SIZE));
		magTool.setZoom(this.diagramPrefStore.getInt(MAGNIFY_ZOOM));
        this.drawingCanvas.getToolManager().register("magnifyTool", magTool);

	}
	
	
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}
	
	/**
	 * To register for explicit preferences store,observing changed preference properties 
	 * @param preferenceStore
	 */
	protected void addPropertyChangeListener(IPreferenceStore preferenceStore) {
		preferenceStore.addPropertyChangeListener(this);
	}
	
	protected void removePropertyChangeListener(IPreferenceStore preferenceStore) {
		preferenceStore.removePropertyChangeListener(this);
	}
	
	/**
	 * @return
	 */
	public IPreferenceStore getPreferenceStore() {
		return this.diagramPrefStore;
	}
	
	/**
	 * @param store
	 */
	public void setPreferenceStore(IPreferenceStore store) {
		this.diagramPrefStore = store;
	}
	
	public TSECreateNodeTool getNodeTool(){
		if (this.createNodeTool == null) {
			this.createNodeTool = new CreateNodeTool(this);
		}
		return this.createNodeTool;
	}
	
	public TSECreateEdgeTool getEdgeTool(){
		if (this.createEdgeTool == null) {
			this.createEdgeTool = new CreateEdgeTool(this);
		}
		return this.createEdgeTool;
	}
	
	public DiagramChangeListener<? extends DiagramManager> getDiagramChangeListener() {
		if (this.changeListener == null) {
			this.changeListener = new DiagramChangeListener<DiagramManager>(this);
		}
		return this.changeListener;
	}
	
	protected DiagramViewPortChangeListener getDiagramViewPortChangeListener() {
		if (this.viewPortListener == null) {
			this.viewPortListener = new DiagramViewPortChangeListener(this);
		}
		return this.viewPortListener;
	}
	
	public  SelectionChangeListener getDiagramSelectionListener() {
		if (this.selectionListener == null) {
			this.selectionListener = new SelectionChangeListener(this);
		}
		
		return this.selectionListener;
	}
	
	protected void registerListeners() {
		super.registerListeners();
		//registering property change listener to handle preference change. Thi	s is default.  
		DiagrammingPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(this);

	}
	
	  public boolean layoutDiagramOnChange() {
	    	boolean executed = false;
	    	
			final String runLayout = this.getPreferenceStore().getString(RUN_LAYOUT_ON_CHANGES);
			if (runLayout != null && !runLayout.equalsIgnoreCase("diagram.none")) {
				final IViewPart view = getEditor().getSite().getPage().findView(PaletteView.ID);
				/*if (view != null) {
					PaletteView paletteView = (PaletteView) view;
					paletteView.setCursor(true);
				}*/
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						try{
							if (view != null) {
								PaletteView paletteView = (PaletteView) view;
								paletteView.setCursor(true);
							}
							
							layout_On_Diagram_Change=true;
							
							if (runLayout.equalsIgnoreCase(INCREMENTAL)) {
								
								getLayoutManager().callInteractiveIncrementalLayout();
							}
							else if (runLayout.equalsIgnoreCase(FULL)) {
								
								getLayoutManager().callInteractiveGlobalLayout();
							}
							else {
								// System.out.println("No layout requested on change.");
							}
						}finally{// in case of any exception, cursor should be back to original one
							if (view != null) {
								PaletteView paletteView = (PaletteView) view;
								paletteView.setCursor(false);
							}
						}
						
					}
				});
				
				if(runLayout.equalsIgnoreCase(FULL) || 
					(runLayout.equalsIgnoreCase(INCREMENTAL)) ){
					executed = true;
				}
			}
			/*else {
				System.err.println("No preference set for layout_on_change.");
			}*/

			return executed;
	    }	
	  
	  @SuppressWarnings("serial")
		protected Container getSwingContainer(Composite parent) {
			Frame frame = SWT_AWT.new_Frame(parent);
			new SyncXErrorHandler().installHandler();
			
			Panel panel = new Panel(new BorderLayout()) {
				public void update(java.awt.Graphics g) {
					paint(g);
				}
			};
			frame.add(panel);
			frame.setFocusable(true);
			final JRootPane root = new JRootPane();
			panel.add(root);
//			SwingUtilities.invokeLater(new Runnable() {
//			
//				@Override
//				public void run() {
					root.requestFocusInWindow();
//				}
//			});
			pane = root.getContentPane();
			return pane;
		}
	  
		public EditorPart getEditor() {
			return editor;
		}
		
		public void resetDiagramEditor() {
			try{
				//Resetting the select tool & palette
				DrawingCanvas drawingCanvas = getDrawingCanvas();
				if(drawingCanvas != null){
					drawingCanvas.getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(drawingCanvas.getToolManager()));
					resetPaletteSelection();
				}
		
				EditorPart editorPart = getEditor();
				if (editorPart != null){
					IWorkbenchPartSite partSite = editorPart.getSite();
					if (partSite != null) {
						IWorkbenchWindow workBenchWindow = partSite.getWorkbenchWindow();
						 if(workBenchWindow != null) {
							 final IWorkbenchPage page = workBenchWindow.getActivePage();
							 if(page != null){
									final IViewPart part = page.findView("com.tibco.cep.studio.projectexplorer.view");
									// To fix invalid thread access exception
									Thread currentThread = Thread.currentThread();
									Display display = PlatformUI.getWorkbench().getDisplay();
									if(currentThread == display.getThread()){
										page.activate(part);
										page.activate(this.getEditor());
									} else {
										display.asyncExec(new Runnable(){
											public void run() {
												page.activate(part);
												page.activate(getEditor());
											}
										});
									}
								}
						 }
					}
				}	
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public boolean isResetToolOnChange() {
			return this.diagramPrefStore.getBoolean(RESET_TOOL_AFTER_CHANGES);
		}
		
		public void dispose() {
			if(Display.getCurrent() != null) {
				disposeNow();
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						disposeLater();
						
					}
				});
			} else {
				disposeNow();
				disposeLater();
			}
			
		}
		private void disposeNow() {
			disposeTools();
			if (managedForm != null && managedForm.getToolkit() != null) {
				managedForm.dispose();
				managedForm = null;
			}
		}
		private void disposeLater() {
			
			if (layoutManager != null) {
				layoutManager.dispose();
				layoutManager = null;
			}
	        if (diagramManager != null) {
				diagramManager = null;
			}
			if (drawingCanvas != null) {
				drawingCanvas.getInnerCanvas().removeMouseListener(getDiagramMouseListener());
				drawingCanvas.dispose();
				drawingCanvas = null;
			}
			if (graphManager != null) {
				graphManager.getMainDisplayGraph().nodeSet.clear();
				graphManager.getMainDisplayGraph().edgeSet.clear();
				graphManager.setMainDisplayGraph(null);
				graphManager.getEventManager().removeAllListeners(this);
				graphManager.getEventManager().removeAllListeners(graphManager);
				graphManager.setNodeBuilder(null);
				graphManager.setEdgeBuilder(null);
				graphManager.setLabelBuilder(null);
				graphManager.setConnectorBuilder(null);
				graphManager.setGraphBuilder(null);
				graphManager.dispose();
				graphManager = null;
			}
			if (createToolPartListeners != null) {
				createToolPartListeners.clear();
				createToolPartListeners = null;
			}
			if (overviewComponent != null) {
				overviewComponent.removeAll();
				overviewComponent = null;
			}
			if (pane != null) {
				DiagramUtils.disposePanel(pane);
				pane = null;
			}
			if (this.diagramModelAdapter != null) {
				this.diagramModelAdapter = null;
			}
			if (this.editTailor != null) {
				this.editTailor = null;
			}
			if (this.intrTailor != null) {
				this.intrTailor = null;
			}
			editor = null;
			viewPortListener = null;
			selectionListener = null;
			animationPreferenceTailor = null;
			diagramPrefStore = null;
			selectedEdgeLabels = null;
			selectedEdges = null;
			selectedNodeLabels = null;
			selectedNodes = null;
			
		}

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			try {
				String property = event.getProperty();
				
				IPreferenceStore prefstore=(DiagrammingPlugin.getDefault().getPreferenceStore());
				if(property.equals(UNDO_LIMIT)){
					int undoLimit=prefstore.getInt(UNDO_LIMIT);
					intrTailor.setUndoLimit(undoLimit);
					
				}
				if (property.equals(RESET_TOOL_AFTER_CHANGES)) {
					isResetToolOnChange();
				
				}
				if (property.equals(ANIMATION_LAYOUT_ALLOW)) {
					boolean allow = prefstore.getBoolean(ANIMATION_LAYOUT_ALLOW);
					setAnimationPreferences(allow);
				}
				if (property.equals(LAYOUT_ANIMATION_DURATION)) {
					int layoutDuration = prefstore.getInt(LAYOUT_ANIMATION_DURATION);
					animationPreferenceTailor.setLayoutAnimationDuration(layoutDuration);
				}
				/*if (property.equals(VIEWPORT_CHANGE_DURATION)) {
					int viewportChangeDuration = prefstore.getInt(VIEWPORT_CHANGE_DURATION);
					animationPreferenceTailor.setViewportChangeAnimationDuration(viewportChangeDuration);
				}*/
				if (property.equals(ANIMATION_LAYOUT_FADE)) {
					boolean fade =  prefstore.getBoolean(ANIMATION_LAYOUT_FADE);
					animationPreferenceTailor.setLayoutFadeAnimation(fade);
				}
				if (property.equals(ANIMATION_LAYOUT_INTERPOLATION)) {
					boolean interpolation = prefstore.getBoolean(ANIMATION_LAYOUT_INTERPOLATION);
					animationPreferenceTailor.setLayoutInterpolationAnimation(interpolation);
				}
			
				if (property.equals(ANIMATION_LAYOUT_VIEWPORT_CHANGE)) {
					boolean viewportChange = prefstore.getBoolean(ANIMATION_LAYOUT_VIEWPORT_CHANGE);
					animationPreferenceTailor.setViewportChangeAnimation(viewportChange);
					int viewportChangeDuration = prefstore.getInt(VIEWPORT_CHANGE_DURATION);
					animationPreferenceTailor.setViewportChangeAnimationDuration(viewportChangeDuration);
					
					/*if(viewportChange)
					{
					animationPreferenceTailor.setViewportChangeAnimationDuration(viewportChangeDuration);
					}else{
						animationPreferenceTailor.setViewportChangeAnimationDuration(0);
					}
					*/	
					
				/**
				 * For Grid preference for Concept,Event,Project,StateModel
				 */
			
					
				}
				if (property.equals(VIEWPORT_CHANGE_DURATION)) {
					/*int viewportChangeDuration = prefstore.getInt(VIEWPORT_CHANGE_DURATION);
					if(viewportChange)
					{
					animationPreferenceTailor.setViewportChangeAnimationDuration(viewportChangeDuration);
					}else{
						animationPreferenceTailor.setViewportChangeAnimationDuration(0);
					}*/
						
				}
			} catch (Exception e) {
				DiagrammingPlugin.debug(this.getClass().getName(), e.getLocalizedMessage(), e.getMessage());
			}
		}
		
		/**
		 * Set Animation preferences
		 * @param allow
		 */
		private void setAnimationPreferences(boolean allow) {
			IPreferenceStore prefstore=(DiagrammingPlugin.getDefault().getPreferenceStore());
			if (allow){
	     		int layoutDuration =         prefstore.getInt(LAYOUT_ANIMATION_DURATION);
//	     		int viewportChangeDuration = prefstore.getInt(VIEWPORT_CHANGE_DURATION);
	     		boolean fade =               prefstore.getBoolean(ANIMATION_LAYOUT_FADE);
	     		boolean interpolation =      prefstore.getBoolean(ANIMATION_LAYOUT_INTERPOLATION);
	     		boolean viewportChange =     prefstore.getBoolean(ANIMATION_LAYOUT_VIEWPORT_CHANGE);
	     		animationPreferenceTailor.setLayoutFadeAnimation(fade);
	    		animationPreferenceTailor.setLayoutInterpolationAnimation(interpolation);
	    		animationPreferenceTailor.setViewportChangeAnimation(viewportChange);
				animationPreferenceTailor.setLayoutAnimationDuration(layoutDuration);
				/*if(viewportChange)
				{
				animationPreferenceTailor.setViewportChangeAnimationDuration(viewportChangeDuration);
				}else{
					animationPreferenceTailor.setViewportChangeAnimationDuration(0);
					
				}
				*/return;
			}
	     	animationPreferenceTailor.setLayoutFadeAnimation(false);
	    	animationPreferenceTailor.setLayoutInterpolationAnimation(false);
	    	animationPreferenceTailor.setViewportChangeAnimation(false);
	     	animationPreferenceTailor.setLayoutAnimationDuration(0);
			animationPreferenceTailor.setViewportChangeAnimationDuration(0);
	     	
		}
		
		public void unregisterListeners() {
			
			((TSEEventManager) this.graphManager.getEventManager()).removeAllSelectionChangeListeners(this.graphManager);
			((TSEEventManager) this.graphManager.getEventManager()).removeAllGraphChangeListeners(this.graphManager);
			((TSEEventManager) this.graphManager.getEventManager()).removeAllDrawingChangeListeners(this.graphManager);
			((TSEEventManager) this.graphManager.getEventManager()).removeAllViewportChangeListeners(this.graphManager);

			DiagrammingPlugin.getDefault().getPreferenceStore().removePropertyChangeListener(this);
		}
		
		protected MouseListener getDiagramMouseListener() {
			if (activationMouseListener == null) {
				activationMouseListener = new DiagramMouseListener();
			}
			return activationMouseListener;
		}
		
}