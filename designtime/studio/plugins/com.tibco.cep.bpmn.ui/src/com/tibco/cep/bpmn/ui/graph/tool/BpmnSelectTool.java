package com.tibco.cep.bpmn.ui.graph.tool;

import static com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils.editGraph;
import static com.tibco.cep.diagramming.utils.DiagramUtils.refreshDiagram;
import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.openEditor;
import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverview;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.IHandlerService;

import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.debug.ProcessBreakpointPropertiesAction;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramHelper;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.editor.BpmnEditorInput;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.editor.GraphSelection;
import com.tibco.cep.bpmn.ui.editor.IGraphInfo;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.AbstractNodeCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.IGraphCommand;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.PoolLaneNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.SubProcessNodeUIFactory;
import com.tibco.cep.bpmn.ui.menu.ProcessMenuBreakPoint;
import com.tibco.cep.bpmn.ui.preferences.BpmnPreferenceConstants;
import com.tibco.cep.diagramming.menu.popup.DiagramPopupMenuInfo;
import com.tibco.cep.diagramming.menu.popup.DiagramPopupMenuUtils;
import com.tibco.cep.diagramming.tool.EDIT_TYPES;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.tool.popup.ContextMenuController;
import com.tibco.cep.diagramming.tool.popup.EntityResourceConstants;
import com.tibco.cep.diagramming.ui.ChildGraphNodeUI;
import com.tibco.cep.diagramming.ui.ShapeNodeUI;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpoint;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.drawing.TSDGraph;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSExpTransform;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.interactive.command.TSCommand;
import com.tomsawyer.interactive.command.editing.TSEAddNodeCommand;
import com.tomsawyer.interactive.command.editing.TSEDeleteNodeCommand;
import com.tomsawyer.interactive.swing.TSEHitTesting;
import com.tomsawyer.interactive.swing.editing.tool.TSEEditTextTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEResizeGraphObjectTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;
import com.tomsawyer.interactive.swing.tool.TSEWindowInputTool;
import com.tomsawyer.interactive.swing.tool.TSEWindowTool;
import com.tomsawyer.interactive.swing.tool.TSToolPreferenceTailor;
import com.tomsawyer.interactive.swing.viewing.tool.TSESelectTool;

/**
 * 
 * @author ggrigore
 *
 */
public class BpmnSelectTool  extends SelectTool implements IGraphSelectTool {
	
	private BpmnDiagramManager bpmnGraphDiagramManager;
	public static BpmnSelectTool tool;
	protected BpmnPopupMenuController bpmnPopupMenuController;	
	private TSEConnector lastConnectorMousedOver = null;
	ListenerList mouseListenerList = new ListenerList();
	ListenerList menuListenerList = new ListenerList();
	TSConstPoint lastMousePoint = new TSConstPoint();

	private Cursor moveCursor = null;
	private Cursor defaultCursor = null;

	public BpmnSelectTool(BpmnDiagramManager bpmnGraphDiagramManager) {
		this.bpmnGraphDiagramManager = bpmnGraphDiagramManager;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				getPopupMenuController();
				DUMMY_PANEL = new JPanel();
			}
		});
		tool = this;
	}
	
	@Override
	public void addMouseListener(IGraphMouseListener l){
		mouseListenerList.add(l);
	}
	
	@Override
	public void removeMouseListener(IGraphMouseListener l) {
		mouseListenerList.remove(l);
	}
	/**
	 * @param l
	 */
	@Override
	public void addMenuListener(IGraphMenuListener l){
		menuListenerList.add(l);
	}
	
	@Override
	public void removeContextMenuListener(IGraphMenuListener l) {
		menuListenerList.remove(l);
	}
	
	public TSConstPoint getLastMousePoint() {
		return lastMousePoint;
	}
	
	public BpmnDiagramManager getBpmnGraphDiagramManager() {
		return bpmnGraphDiagramManager;
	}
	
	@Override
	public Control getControl() {
		return bpmnGraphDiagramManager.getControl();
	}
	
	/**
	 * Convert a AWT event to SWT event
	 * @param event
	 * @return
	 */
	public org.eclipse.swt.events.MouseEvent toSwtMouseEvent(MouseEvent event) {
        int button = org.eclipse.swt.SWT.NONE;
 		lastMousePoint = this.getNonalignedWorldPoint(event);
        switch (event.getButton()) {
        case 1: button = org.eclipse.swt.SWT.BUTTON1; break;
        case 2: button = org.eclipse.swt.SWT.BUTTON2; break;
        case 3: button = org.eclipse.swt.SWT.BUTTON3; break;
        }
        @SuppressWarnings("unused")
		int modifiers = 0;
        if ((event.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {
            modifiers |= SWT.CTRL ;
        }
        if ((event.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0) {
            modifiers |= SWT.SHIFT;
        }
        if ((event.getModifiersEx() & InputEvent.ALT_DOWN_MASK) != 0) {
            modifiers |= SWT.ALT;
        }
        Event tev = new Event();
        tev.display = Display.getCurrent();
        tev.widget = getBpmnGraphDiagramManager().getDiagramEditorControl();
        tev.x = event.getX();
        tev.y = event.getY();
        tev.count = event.getClickCount();
        tev.button = button;
        tev.data = getGraphObjectAt(event);
        
        org.eclipse.swt.events.MouseEvent swtMouseEvent = new org.eclipse.swt.events.MouseEvent(tev);
        return swtMouseEvent;
    }
	
	
	 protected static JPanel DUMMY_PANEL;

	/**
	 * Convert a SWT event to AWT event
	 * @param event
	 * @return
	 */
	public static MouseEvent toAwtMouseEvent(org.eclipse.swt.events.MouseEvent event) {
		int button = MouseEvent.NOBUTTON;
		switch (event.button) {
		case 1:
			button = MouseEvent.BUTTON1;
			break;
		case 2:
			button = MouseEvent.BUTTON2;
			break;
		case 3:
			button = MouseEvent.BUTTON3;
			break;
		}
		int modifiers = 0;
		if ((event.stateMask & SWT.CTRL) != 0) {
			modifiers |= InputEvent.CTRL_DOWN_MASK;
		}
		if ((event.stateMask & SWT.SHIFT) != 0) {
			modifiers |= InputEvent.SHIFT_DOWN_MASK;
		}
		if ((event.stateMask & SWT.ALT) != 0) {
			modifiers |= InputEvent.ALT_DOWN_MASK;
		}
		MouseEvent awtMouseEvent = new MouseEvent(DUMMY_PANEL, event.hashCode(), event.time, modifiers, event.x, event.y, 1, false, button);
		return awtMouseEvent;
	}
	
   protected void fireMouseEvent(final org.eclipse.swt.events.MouseEvent event,final int type) {
       Object[] array = mouseListenerList.getListeners();
       for (int i = 0; i < array.length; i++) {
           final IGraphMouseListener l = (IGraphMouseListener) array[i];
           SafeRunnable.run(new SafeRunnable() {
               public void run() {
            	   switch(type) {
            	   case IGraphMouseListener.MOUSE_CLICKED:
            		   l.onMouseClicked(event);
            		   break;
            	   case IGraphMouseListener.MOUSE_RELEASED:
            		   l.onMouseReleased(event);
            		   break;
            	   case IGraphMouseListener.MOUSE_PRESSED:
            		   l.onMousePressed(event);
            		   break;
            	   case IGraphMouseListener.MOUSE_MOVED:
            		   l.onMouseMoved(event);
            		   break;
            	   case IGraphMouseListener.MOUSE_DRAGGED:
            		   l.onMouseDragged(event);
            		   break;
            	   case IGraphMouseListener.MOUSE_WHEEL_MOVED:
            		   l.onMouseWheelMoved(event);
            		   break;
            	   case IGraphMouseListener.MOUSE_ENTERED:
            		   l.onMouseEntered(event);
            		   break;
            	   case IGraphMouseListener.MOUSE_EXITED:
            		   l.onMouseExited(event);
            		   break;
            	   }
               }
           });
       }

   }
   

   @SuppressWarnings("rawtypes")
@Override
   public Object getAdapter(Class adapter) {
	   if(adapter == IGraphInfo.class) {
		   return new GraphInfoAdapter(this);
	   }
	   return null;
   }
	
	public TSEGraph getGraph() {
		return super.getGraph();
	}
	
	public TSEObject getGraphObjectAt(MouseEvent event) {
		// get the point where the mouse is pressed.
		TSEHitTesting hitTesting = this.getHitTesting();
		TSConstPoint point = this.getNonalignedWorldPoint(event);
		TSToolPreferenceTailor tailor = new TSToolPreferenceTailor(this.getSwingCanvas().getPreferenceData());
		TSEObject object = hitTesting.getGraphObjectAt(point, super.getGraph(), tailor.isNestedGraphInteractionEnabled());
		return object;
	}
	
	@Override
	public void onMouseReleased(MouseEvent arg0) {
		try {
			super.onMouseReleased(arg0);
		} finally {
			fireMouseEvent(toSwtMouseEvent(arg0), IGraphMouseListener.MOUSE_RELEASED);
		}
	}
	
	@Override
	public void onMouseClicked(MouseEvent arg0) {
		try {
			super.onMouseClicked(arg0);
		} finally {
			fireMouseEvent(toSwtMouseEvent(arg0), IGraphMouseListener.MOUSE_CLICKED);
		}
	}
	
	@Override
	public void onMouseDragged(MouseEvent arg0) {
		try {
			super.onMouseDragged(arg0);
			
		} finally {
			fireMouseEvent(toSwtMouseEvent(arg0), IGraphMouseListener.MOUSE_DRAGGED);
		}
	}
	
	@Override
	public void onMouseEntered(MouseEvent event) {
		try {
			super.onMouseEntered(event);
		} finally {
			fireMouseEvent(toSwtMouseEvent(event), IGraphMouseListener.MOUSE_ENTERED);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.tool.TSEWindowInputTool#onMouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void onMouseExited(MouseEvent event) {
		try {
			super.onMouseExited(event);
		} finally {
			fireMouseEvent(toSwtMouseEvent(event), IGraphMouseListener.MOUSE_EXITED);
		}
	}
	
	
	@Override
	public void onMouseWheelMoved(MouseWheelEvent arg0) {
		try {
			super.onMouseWheelMoved(arg0);
		} finally {
			fireMouseEvent(toSwtMouseEvent(arg0), IGraphMouseListener.MOUSE_WHEEL_MOVED);
		}
	}
	
	private TSEObject selectedObject = null;
	
	
	public void onMouseMoved(MouseEvent event) {
		// get the point where the mouse is pressed.
		TSEHitTesting hitTesting = this.getHitTesting();
		TSConstPoint point = this.getNonalignedWorldPoint(event);
		TSToolPreferenceTailor tailor = new TSToolPreferenceTailor(
			this.getSwingCanvas().getPreferenceData());
		TSEObject object = hitTesting.getGraphObjectAt(
			point, this.getGraph(), tailor.isNestedGraphInteractionEnabled());
		TSENode node = null;
//		boolean showBreakPoints = BpmnUIPlugin.getDefault().getPreferenceStore().getBoolean(BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS);
		boolean showBreakPointsOnMouseover = BpmnUIPlugin.getDefault().getPreferenceStore().getBoolean(
			BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS_ON_MOUSEOVER);
		try {
			//Setting Default Cursor on mouse move to Breakpoint circle
			if (object instanceof TSENode) {
				node = (TSENode) object;
				if (selectedObject == object && showBreakPointsOnMouseover) {
					showBreakpoints(object, true);
				}
				
				selectedObject = object;
				
				if (moveCursor == null) {
					moveCursor = getActionCursor();
				}
				if(isErrorAnnotation(node, event)) {
					List<Annotation> annotations = bpmnGraphDiagramManager.getErrorAnnotation(node);
					StringBuilder sb = new StringBuilder();
					for(Annotation annotation:annotations) {
						sb.append("<p><b>Error: </b> " + annotation.getText() + "</p>");
					}
					node.setTooltipText(sb.toString());
				} else {
					node.setTooltipText(BpmnDiagramHelper.getNodeToolTip(node));
				}
				if (isStartBreakPoint(node, event) 
						|| isEndBreakPoint(node, event)) {
					if (defaultCursor == null) {
						defaultCursor = new Cursor(Cursor.HAND_CURSOR);
					}
					this.setActionCursor(defaultCursor);
					this.setCursor(defaultCursor);
				} else {
					this.setActionCursor(moveCursor);
					this.setCursor(moveCursor);
				}
			} 
			
			if (selectedObject != null && selectedObject != object) {
				showBreakpoints(selectedObject, false);
			}
			
			if (this.lastConnectorMousedOver != null) {
				this.lastConnectorMousedOver.setSize(20.0, 20.0);
				TSENode lastParent = (TSENode) this.lastConnectorMousedOver.getOwner();
				if (lastParent != node) {
					this.bpmnGraphDiagramManager.refreshNode(lastParent);
				}
				this.lastConnectorMousedOver = null;
			}
			
			TSEConnector connector = null;
			if (object instanceof TSEConnector) {
				connector = (TSEConnector) object;
			}
			else {
				super.onMouseMoved(event);
			}
			if (connector != null) {
				connector.setSize(40.0, 40.0);
				this.lastConnectorMousedOver = connector;
				node = (TSENode) connector.getOwner();
				this.bpmnGraphDiagramManager.refreshNode(node);
			}		
		} finally {
			fireMouseEvent(toSwtMouseEvent(event), IGraphMouseListener.MOUSE_MOVED);
		}


	}	
	
	/**
	 * This method responds to the mouse button being pressed in
	 * the client area of the swing canvas.
	 */
	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.viewing.tool.TSESelectTool#onMousePressed(java.awt.event.MouseEvent)
	 */
	public void onMousePressed(MouseEvent event) {
		try {
			tool = this; //resetting Active Select Tool when mouse pressed.
			TSEHitTesting hitTesting = this.getHitTesting();

			// get the point where the mouse is pressed.
			TSConstPoint point = this.getNonalignedWorldPoint(event);
			TSToolPreferenceTailor tailor = new TSToolPreferenceTailor(this.getSwingCanvas().getPreferenceData());
			TSEObject object = hitTesting.getGraphObjectAt(point, this.getGraph(), tailor.isNestedGraphInteractionEnabled());
			final int clickCount = event.getClickCount();
//			final int button = event.getButton();
//			final int modifier = event.getModifiersEx();

			if (event.getButton() == MouseEvent.BUTTON3 || event.isPopupTrigger()) {
				boolean inputBreakpoint = false;
				boolean outputBreakpoint = false;
				
				boolean inputBreakpointToggle = false;
				boolean outputBreakpointToggle = false;
				
				if (object != null && object instanceof TSENode) {
					TSENode node = (TSENode) object;
					inputBreakpoint = isStartBreakPoint((TSENode)object, event);
					outputBreakpoint = isEndBreakPoint((TSENode)object, event);
					
					if (node.getUI() instanceof ShapeNodeUI) {
						ShapeNodeUI nodeUI = (ShapeNodeUI) node.getUI();
						if (inputBreakpoint) {
							inputBreakpointToggle = nodeUI.isInputBreakPointToggle();
						}
						if (outputBreakpoint) {
							outputBreakpointToggle = nodeUI.isOutputBreakPointToggle();
						}
					}
					BpmnEditor editor = bpmnGraphDiagramManager.getBpmnGraphEditor();
					BpmnGraphUtils.setWorkbenchSelection(node, editor, false);
				}
				
				//Model driven and extension-point based 
				DiagramPopupMenuInfo processPopupMenu = null;
				for (DiagramPopupMenuInfo menu : DiagramPopupMenuUtils.getDiagramPopupMenuInfos()) {
					if (menu.getExtension().equals(CommonIndexUtils.PROCESS_EXTENSION)) {
						processPopupMenu = menu;
						break;
					}
				}
				processPopupMenu.getMenuHandler().setManager(bpmnGraphDiagramManager);
				processPopupMenu.getMenuStateValidator().setManager(bpmnGraphDiagramManager);
				processPopupMenu.getMenuHandler().setSwingCanvas(this.getSwingCanvas());
				processPopupMenu.getMenuHandler().setHitPoint(point);
				processPopupMenu.getMenuHandler().setHitObject(object);
				processPopupMenu.getMenuHandler().setGraphAtHitPoint(hitTesting.getGraphAt(point, this.getGraph()));
				processPopupMenu.getMenuFilter().setPopupMenu(processPopupMenu.getPopupMenu());
				processPopupMenu.getMenuFilter().setMenuMap(processPopupMenu.getItemPatternMap());
				
				List<String> matchPatterns = new ArrayList<String>();
				if (object != null && object instanceof TSENode) {
					EObject userObject = (EObject) object.getUserObject();
					EObjectWrapper<EClass, EObject> userObjectWrap = EObjectWrapper.wrap(userObject);
					if(BpmnModelClass.ACTIVITY.isSuperTypeOf(userObjectWrap.getEClassType())) {
						matchPatterns.add(BpmnMetaModelConstants.ACTIVITY.getExpandedForm());
					}
					if(BpmnModelClass.START_EVENT.isSuperTypeOf(userObjectWrap.getEClassType())) {
						matchPatterns.add(BpmnMetaModelConstants.START_EVENT.getExpandedForm());
					}
					if(BpmnModelClass.END_EVENT.isSuperTypeOf(userObjectWrap.getEClassType())) {
						matchPatterns.add(BpmnMetaModelConstants.END_EVENT.getExpandedForm());
					}
					if(BpmnModelClass.GATEWAY.isSuperTypeOf(userObjectWrap.getEClassType())) {
						matchPatterns.add(BpmnMetaModelConstants.GATEWAY.getExpandedForm());
					}
				}
				ProcessMenuBreakPoint processMenuBreakPoint = null;
				if ((inputBreakpoint && inputBreakpointToggle) || (outputBreakpoint && outputBreakpointToggle)) {
					processMenuBreakPoint = new ProcessMenuBreakPoint(inputBreakpoint, inputBreakpointToggle, outputBreakpoint, outputBreakpointToggle);
				}

				Object obj = null;
				if (processMenuBreakPoint != null) {
					obj = processMenuBreakPoint;
					matchPatterns.clear();
				} else {
					obj = object;
				}

				processPopupMenu.getMenuFilter().applyPatterns(obj == null ? this.getGraph() : obj, matchPatterns);
				processPopupMenu.getMenuStateValidator().validate(processPopupMenu.getItemPatternMap());

				//for development testing purpose, later remove this popup menu,  
//				processPopupMenu = null;

				BpmnSelectToolHandler.popupTriggered(event, this.getGraph(), this, 
						processPopupMenu == null ? null : processPopupMenu.getPopupMenu(), inputBreakpoint,inputBreakpointToggle, outputBreakpoint, outputBreakpointToggle);

			} else {
				TSENode node = null;
				if (object != null && object instanceof TSENode) {
					node = (TSENode) object;
					EClass nodeType = (EClass) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
					String nodeName = (String) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
					// String nodeLabel = (String)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
					String toolId = (String) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
					String resUrl = (String) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
					EObject userObject = (EObject) node.getUserObject();
					EObjectWrapper<EClass, EObject> userObjectWrap = EObjectWrapper.wrap(userObject);
					if (clickCount == 1 ) {
						if (isStartBreakPoint(node, event)) {
							this.hitObject = object;
							onAddBreakpoint(this.hitObject, userObjectWrap, true, false);
						} 
						if (isEndBreakPoint(node, event)) {
							this.hitObject = object;
							onAddBreakpoint(this.hitObject, userObjectWrap, false, true);
						} 
						// TODO: Add proper checking if node is pool
						EObject userObj = (EObject) node.getOwnerGraph().getUserObject();
						boolean isProcess = BpmnModelClass.PROCESS.isSuperTypeOf(userObj.eClass());
						if (nodeType != null && BpmnModelClass.LANE.isSuperTypeOf(nodeType)) {
							if(isProcess) {
								//System.out.println("found pool");
								if (this.clickedOnBadge(node, event)) {
									this.createLane(node);
									return;
								}
							} else {
								//System.out.println("found lane");
								if (this.clickedOnBadge(node, event)) {
									this.deleteLane(node);
									return;
								}
							}
						}
						super.onMousePressed(event);
					} else if (clickCount == 2) {
						if (node.getChildGraph() != null) {
							if (TSENestingManager.isCollapsed(node)) {
								TSENestingManager.expand(node);
								// Always check pool/lane with respect to node
								// attrib eclass type
								// TODO:When creating new Nodes the above should
								// be taken care of.

								if (BpmnModelClass.LANE.isSuperTypeOf(nodeType)) {
									EObject userObj = (EObject) node.getOwnerGraph().getUserObject();

									PoolLaneNodeUIFactory uiFactory = (PoolLaneNodeUIFactory) BpmnGraphUIFactory.getInstance(
											(BpmnLayoutManager) getBpmnGraphDiagramManager().getLayoutManager()).getNodeUIFactory(nodeName, resUrl, toolId,
											BpmnMetaModel.LANE, BpmnMetaModel.INSTANCE.getExpandedName(userObj.eClass()));
									// node.setUI(uiFactory.getNodeUI());
									uiFactory.decorateNode(node);
									uiFactory.layoutNode(node);
								} else if (BpmnModelClass.SUB_PROCESS.isSuperTypeOf(nodeType)) {
									SubProcessNodeUIFactory uiFactory = (SubProcessNodeUIFactory) BpmnGraphUIFactory.getInstance(
											(BpmnLayoutManager) getBpmnGraphDiagramManager().getLayoutManager()).getNodeUIFactory(nodeName, resUrl, toolId,
											BpmnMetaModel.SUB_PROCESS);
									// node.setUI(uiFactory.getNodeUI(true));
									uiFactory.setExpanded(true, node);
									uiFactory.onNodeExpanded(node, true);
									getBpmnGraphDiagramManager().refreshNodeForIconAndColor(node);
								}
							} else {
								TSENestingManager.collapse(node);
								if (BpmnModelClass.SUB_PROCESS.isSuperTypeOf(nodeType)) {
									SubProcessNodeUIFactory uiFactory = (SubProcessNodeUIFactory) BpmnGraphUIFactory.getInstance(
											(BpmnLayoutManager) getBpmnGraphDiagramManager().getLayoutManager()).getNodeUIFactory(nodeName, resUrl, toolId,
											BpmnMetaModel.SUB_PROCESS);
									uiFactory.setExpanded(false, node);
									uiFactory.onNodeExpanded(node, false);
									node.setUI(uiFactory.getNodeUI(false));
									node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
									node.setSize(70, 50);
									getBpmnGraphDiagramManager().refreshNodeForIconAndColor(node);
								}
							}
						} 
						if (nodeType.equals(BpmnModelClass.CALL_ACTIVITY)) {
							EObject attribute = (EObject) userObjectWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_CALLED_ELEMENT);
							if (attribute != null) {
								EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(attribute);
								openProcessEditor(processWrapper);
							}
						}
						if (nodeType.equals(BpmnModelClass.TEXT_ANNOTATION)) {
							// this.editText((TSESolidObject) this.hitObject);
							// super.onMousePressed(event);
							TSEEditTextTool tool = (TSEEditTextTool) TSEditingToolHelper.getEditTextTool(this.getToolManager());
							if (tool != null) {
								TSEWindowTool activeTool = (TSEWindowTool) this.getToolManager().getActiveTool();
								tool.setParentTool(activeTool);
								tool.setChangedGraphObject(node);
								activeTool.setToChildTool(tool);
							}
						}
						refreshDiagram(bpmnGraphDiagramManager);
						refreshOverview(bpmnGraphDiagramManager.getEditor().getEditorSite(), true, true);						
						
					} else if(!(object instanceof TSENodeLabel)){// in case of node label don't do any thing on double click
						super.onMousePressed(event);
					} else { // clickCount = 2
						super.onMousePressed(event);
					}
				} else  { // object != null && object instanceof TSENode
					super.onMousePressed(event);
				}

			}
		}
		catch(Exception e){
			BpmnUIPlugin.log(e);
		} finally {
			fireMouseEvent(toSwtMouseEvent(event), IGraphMouseListener.MOUSE_PRESSED);
		}
	}
	
	@Override
	public void onDragEnter(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		super.onDragEnter(arg0);
	}
	
	@Override
	public void onDrop(DropTargetDropEvent arg0) {
		// TODO Auto-generated method stub
		super.onDrop(arg0);
	}
	
	@Override
	public void onDragGestureRecognized(DragGestureEvent arg0) {
		// TODO Auto-generated method stub
		super.onDragGestureRecognized(arg0);
	}
	
	private void openProcessEditor(EObjectWrapper<EClass, EObject> processWrapper){
		String folder = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
		String name = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		String processPath =folder+IPath.SEPARATOR+name+"."+BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION;
		IFile file = getBpmnGraphDiagramManager().getProject().getFile(processPath);
		BpmnEditorInput input = new BpmnEditorInput(file);
		IWorkbenchPage activePage = getBpmnGraphDiagramManager().getEditor().getSite().getPage();
		openEditor(activePage, input,BpmnEditor.ID);	
	}
	
	
	private boolean clickedOnBadge(TSENode node, MouseEvent event) {
		// need to check if we just clicked on pool or lane.
		// we need to ensure this is the case before calling this method
		if (!(node.getUI() instanceof ChildGraphNodeUI)) {
			return false;
		}
		else {
			TSConstRect entireBadgeBounds  = ((ChildGraphNodeUI) node.getUI()).getBadgeBounds();
			if (entireBadgeBounds != null) {
				TSTransform transform = this.getHitTesting().getTransform();
				double x = transform.xToWorld(event.getX());
				double y = transform.yToWorld(event.getY());

				if (entireBadgeBounds.contains(x, y)) {
					return true;
				} 
			}
		}
		return false;
	}
	
	private boolean isStartBreakPoint(TSENode node, MouseEvent event) {
		if (node.getUI() instanceof ShapeNodeUI) {
			TSConstRect inputBreakPointBounds  = ((ShapeNodeUI) node.getUI()).getInputBreakPointBounds();
			if (inputBreakPointBounds != null) {
				TSConstPoint point = this.getNonalignedWorldPoint(event);
				inputBreakPointBounds  = processInnerBounds(node, inputBreakPointBounds);
				if (inputBreakPointBounds.contains(point)) {
					return true;
				} 
			}
		}
		return false;
	}
	
	
	/**
	 * @param node
	 * @param bounds
	 * @return
	 */
	private TSConstRect processInnerBounds(TSENode node, TSConstRect bounds) {
		if(node.getOwnerGraph().isChildGraph()) {
			TSDGraph cg = (TSDGraph) node.getOwnerGraph();
			while(!cg.isMainDisplayGraph()) {
				TSExpTransform txm = cg.getLocalToAnchorGraphTransform();
				if(txm != null && bounds != null) {
					bounds = txm.getTransformedRect(bounds);
//					System.out.println("Bounds:"+bounds);
				}
				TSDGraph anchorGraph = cg.getAnchorGraph();
				if(anchorGraph == cg)
					break;
				cg = (TSDGraph) anchorGraph;
			}
		}
		return bounds;
	}
	
	private boolean isErrorAnnotation(TSENode node, MouseEvent event) {
		
		if (node.getUI() instanceof ShapeNodeUI) {
			TSConstRect errorBounds  = ((ShapeNodeUI) node.getUI()).getErrorBounds();
//			System.out.println("Aligned:"+this.getAlignedWorldPoint(event));
//			System.out.println("World:"+this.getWorldPoint(event));
//			System.out.println("Non-Aligned:"+this.getNonalignedWorldPoint(event));
//			System.out.println("Bounds:"+errorBounds);
			/**
			 * Transform at every child graph level
			 */
			if (errorBounds != null) {
				TSConstPoint point = this.getNonalignedWorldPoint(event);
				errorBounds  = processInnerBounds(node, errorBounds);
				if (errorBounds.contains(point)) {
					return true;
				} 
			}
		}
		return false;
	}
	
	private boolean isEndBreakPoint(TSENode node, MouseEvent event) {
		if (node.getUI() instanceof ShapeNodeUI) {
			TSConstRect outputBreakPointBounds  = ((ShapeNodeUI) node.getUI()).getOutputBreakPointBounds();
			if (outputBreakPointBounds != null) {
				TSConstPoint point = this.getNonalignedWorldPoint(event);
				outputBreakPointBounds  = processInnerBounds(node, outputBreakPointBounds);
				if (outputBreakPointBounds.contains(point)) {
					return true;
				} 
			}
		}
		return false;
	}

//	@SuppressWarnings("unused")
//	private boolean clickedOnBadgeOFF(TSENode node) {
//		TSConstRect badgeBounds = ((ChildGraphNodeUI) node.getUI()).getBadgeBounds();
//		if (badgeBounds!= null && badgeBounds.contains(
//				this.getHitTesting().getTransform().xToWorld(this.getMouseEvent().getX()),
//				this.getHitTesting().getTransform().yToWorld(this.getMouseEvent().getY()))) {
//			return true;
//		}	
//		else {
//			return false;
//		}
//	}
	
	private void createLane(TSENode poolNode) {
		BpmnDiagramManager diagramManager = this.getDiagramManager();
		AbstractNodeCommandFactory cf = (AbstractNodeCommandFactory) ((BpmnDiagramManager)diagramManager).getModelGraphFactory().getCommandFactory(BpmnModelClass.LANE, null);
		IGraphCommand<TSENode> cmd = cf.getCommand(IGraphCommand.COMMAND_ADD,(TSEGraph)poolNode.getChildGraph(), 0.0,0.0);

		this.getDiagramManager().executeCommand((TSCommand)cmd);
		TSENode newLane = ((TSEAddNodeCommand)cmd).getNode();
		newLane.setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, BpmnModelClass.LANE);		
		newLane.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, "Lane");
		newLane.setName(Messages.getString("title.lane"));
		
		EObject userObject = (EObject)newLane.getUserObject();
		EObjectWrapper<EClass, EObject> userObjectWrapper = EObjectWrapper.wrap(userObject);
		userObjectWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, "Lane");
		
//		((BpmnLayoutManager) (this.getDiagramManager().getLayoutManager())).setLayoutOptionsForPool(poolNode);
//		this.getDiagramManager().getLayoutManager().callBatchIncrementalLayout();
//		
//		
		diagramManager.refreshNode(newLane);
	}
	
	
	private void deleteLane(TSENode laneNode) {
		TSEDeleteNodeCommand cmd = new TSEDeleteNodeCommand(laneNode);
		this.getDiagramManager().executeCommand(cmd);
		this.getDiagramManager().getLayoutManager().callBatchIncrementalLayout();
	}
	
	
	/**
	 * This method responds to the mouse being dragged.
	 */
	public void onMouseDraggedOFF(MouseEvent event) {
		if (this == this.getToolManager().getActiveTool()) {
			switch (this.mode) {
				case TSESelectTool.PRE_BEND :
				case TSESelectTool.PRE_BEND_TOGGLE_SELECT :
					this.createPNode(this.startPoint);

					TSEWindowInputTool activeTool =
						((TSEWindowInputTool) this.getToolManager().getActiveTool());

					if (activeTool != this) {
						activeTool.onMouseDragged(event);
					}

					break;

				case TSESelectTool.EXPANDED_PRE_MARQUE :
					this.selectRegion(this.startPoint);
					break;

				case TSESelectTool.PRE_MARQUEE :
					// this.selectRegion(this.startPoint);
//					System.out.println("forcing resizing..");
					this.resizeGraphObject(this.startPoint);
					break;

				case TSESelectTool.PRE_RESIZE :
//					System.out.println("about to resize...");
					this.resizeGraphObject(this.startPoint);
					break;

				case TSESelectTool.PRE_EDIT :

					// so .. we thought they wish to edit the text while
					// in reality they wanted to move some objects, as
					// indicated by the detected mouse drag. Switch the
					// mode and act as if we knew all along that moving
					// was what the user intended

					this.mode = TSESelectTool.PRE_MOVE;

				case TSESelectTool.PRE_MOVE :
				case TSESelectTool.PRE_MOVE_TOGGLE_SELECT :

                    // make sure that the selected graph has selected graph members
                    if (this.getSelectedGraph().hasSelected() ||
                            (this.getSelectedGraph().getOwnerGraphManager().
                                queryIntergraph() != null &&
                            ((TSEGraph) this.getSelectedGraph().
                                getOwnerGraphManager().queryIntergraph()).
                                    hasSelected()))
                    {
                        boolean isMac =
                        ((System.getProperty("os.name")).startsWith("Mac"));

                        if ((isMac && event.isShiftDown()) ||
                                (!isMac && event.isControlDown()))
                        {
                            this.transferSelected(this.startPoint);
                        }
                        else
                        {
                            this.moveSelected(this.startPoint);
                        }
                    }

                break;
			}
		}

		// once you are back reset this tool's mode
		this.mode = SELECT;
	}	
	
	/**
	 * This method is called whenever a node is to be resized. It
	 * switches to the registered resize graph object tool. The mouse
	 * cursor of the resize node tool is set to match the current
	 * cursors.
	 */
	public void resizeGraphObject(TSConstPoint startPoint) {
		try {
			TSEResizeGraphObjectTool nextTool =
				(TSEResizeGraphObjectTool) TSEditingToolHelper.getResizeGraphObjectTool(
					this.getToolManager());

			if (nextTool != null) {
				// set the cursor of the tool to the current cursor of this
				// tool. If we are in the pre_resize mode then our cursor
				// must be the right one, for the grapple type and thus we
				// can just copy it for the resize tool
				nextTool.setDefaultCursor(this.getSwingCanvas().getCursorOnInnerCanvas());
				nextTool.setActionCursor(this.getSwingCanvas().getCursorOnInnerCanvas());
				nextTool.setParentTool(this);
				nextTool.setStartPoint(startPoint);
				nextTool.setAdjustToGrid(this.isGridOn());
				this.setToChildTool(nextTool);
			}
		}
		catch (Exception e)
		{
			BpmnUIPlugin.log(e);
		}
	}	
	
	/**
	 * This method sets the enabled or disabled state of the popup menu items.
	 * It is called before the popup menu is displayed.
	 */
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#setPopupState(javax.swing.JPopupMenu)
	 */
	public void setPopupState(JPopupMenu popup) {
		
		for (int i = popup.getComponentCount() - 1; i >= 0; --i) {
			Component element = popup.getComponent(i);

			if (element instanceof JMenu) {
				this.setMenuState((JMenu) element);
			} else if (element instanceof JMenuItem) {
				BpmnSelectToolHandler.chooseState((JMenuItem) element, 
						this.getGraph(), 
						this, 
						((BpmnEditor)bpmnGraphDiagramManager.getEditor()).isEnabled());
			}
		}
		
	}

	/**
	 * This method sets the state of all items in the input popup menu based on
	 * their action commands.
	 */
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#setMenuState(javax.swing.JMenu)
	 */
	public void setMenuState(JMenu menu) {
		/*
		for (int i = menu.getMenuComponentCount() - 1; i >= 0; --i) {
			JMenuItem item = menu.getItem(i);

			// With Swing 1.1.1, iterating through the items of a
			// submenu appears to return the separators as well as the
			// menu items. So we skip the null items.

			if (item != null) {
				if ((item != menu) && (item instanceof JMenu)) {
					this.setMenuState((JMenu) item);
				} else {
					decisionGraphSelectToolHandler.chooseState(item, 
							this.getGraph(), 
							this, 
							((decisionGraphEditor)decisionGraphDiagramManager.getEditor()).isEnabled());
				}
			}
		}
		*/
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent action) {
		String command = action.getActionCommand();
		if (command.startsWith(EntityResourceConstants.HIT_IN_BREAKPOINT)) {
			onBreakpointHit(true);
		} else if (EntityResourceConstants.HIT_OUT_BREAKPOINT.equals(command)) {
			onBreakpointHit(false);
		} else if (command.startsWith(EntityResourceConstants.REMOVE_IN_BREAKPOINT)) {
			clearBreakpoints(true);
		} else if (EntityResourceConstants.REMOVE_OUT_BREAKPOINT.equals(command)) {
			clearBreakpoints(false);
		} else if (EntityResourceConstants.REMOVE_ALL_BREAKPOINTS.equals(command)) {
			clearAllBreakpoints();
		}  else if (EntityResourceConstants.PROCESS_BREAKPOINT_PROPERTIES.equals(command)) {
			StudioUIUtils.invokeOnDisplayThread(new Runnable() {
				@Override
				public void run() {
					ProcessBreakpointPropertiesAction prBrkAction =  new ProcessBreakpointPropertiesAction(getDiagramManager().getEditor());
					prBrkAction.run();
				}
			}, false);
		}
		else {
			super.actionPerformed(action);
		}
	}
	
	private void clearBreakpoints(boolean isInputBreakpointHit) {
		if (this.hitObject instanceof TSENode) {
			TSENode node = (TSENode) this.hitObject;
			if (node.getUI() instanceof ShapeNodeUI) {
				ShapeNodeUI nodeUI = (ShapeNodeUI) node.getUI();
				//TODO: After the task is done 
				if (isInputBreakpointHit) {
					nodeUI.setInputBreakpointHit(false);
				} else {
					nodeUI.setOutputBreakpointHit(false);
				}
				bpmnGraphDiagramManager.refreshNode(node);
			}
		}
	}
	
	private void clearAllBreakpoints() {
		if (this.hitObject instanceof TSENode) {
			TSENode node = (TSENode) this.hitObject;
			if (node.getUI() instanceof ShapeNodeUI) {
				ShapeNodeUI nodeUI = (ShapeNodeUI) node.getUI();
				//TODO: After the task is done 
				nodeUI.setInputBreakpointHit(false);
				nodeUI.setOutputBreakpointHit(false);
				bpmnGraphDiagramManager.refreshNode(node);
			}
		}
	}
	
	private void onBreakpointHit(boolean isInputBreakpointHit) {
		if (this.hitObject instanceof TSENode) {
			TSENode node = (TSENode) this.hitObject;
			if (node.getUI() instanceof ShapeNodeUI) {
				ShapeNodeUI nodeUI = (ShapeNodeUI) node.getUI();
				if (isInputBreakpointHit) {
					if (nodeUI.isInputBreakPointToggle()) {
						nodeUI.setOutputBreakpointHit(false);
						nodeUI.setInputBreakpointHit(true);
					}
				} else {
					if (nodeUI.isOutputBreakPointToggle()) {
						nodeUI.setInputBreakpointHit(false);
						nodeUI.setOutputBreakpointHit(true);
					}
				}
			}
			bpmnGraphDiagramManager.refreshNode(node);
		}
	}
	
	private void showBreakpoints(TSEObject object, boolean show) {
		boolean showBreakPoints = BpmnUIPlugin.getDefault().getPreferenceStore().getBoolean(BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS);
		try {
			//Setting Default Cursor on mouse move to Breakpoint circle
			if (object instanceof TSENode) {
				if (!showBreakPoints) {
					TSENode node = (TSENode)object;
					if (node.getUI() instanceof ShapeNodeUI) {
						ShapeNodeUI nodeUI = (ShapeNodeUI) node.getUI();
						nodeUI.setShowBreakPoints(show);
					}
					bpmnGraphDiagramManager.refreshNode(node);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
	}
	
	public void onAddBreakpoint(TSEObject hitObject, EObjectWrapper<EClass, EObject> userObjectWrap, boolean isInput, boolean breakpointHit) {
		if (hitObject instanceof TSENode) {
			TSENode node = (TSENode) hitObject;
			// Removing breakpoints from Subprocess Start and End nodes
			EObject eobjP = BpmnModelUtils.getFlowElementContainer((EObject)node.getUserObject()) ;
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap( (EObject)node.getUserObject() );
			EList<EObject> listAttribute = null ;
			if ( userObjWrapper != null && userObjWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS ) )
				listAttribute = userObjWrapper
							.getListAttribute( BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS );
			
			
			if (node.getUI() instanceof ShapeNodeUI) {
				ShapeNodeUI nodeUI = (ShapeNodeUI) node.getUI();
				if (isInput) {
					if (!nodeUI.isInputBreakpointHit()) {
						nodeUI.setInputBreakpoint(true);
						nodeUI.setInputBreakPointToggle(!nodeUI.isInputBreakPointToggle());
						node.setAttribute(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_LOCATION, IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.START);
					}
				}
				else {
					if (!nodeUI.isOutputBreakpointHit()) {
						nodeUI.setOutputBreakpoint(true);
						nodeUI.setOutputBreakPointToggle(!nodeUI.isOutputBreakPointToggle());
						node.setAttribute(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_LOCATION, IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.END);
					}
				}
				if (userObjectWrap.isInstanceOf(BpmnModelClass.ACTIVITY) || (BpmnModelClass.EVENT.isSuperTypeOf(userObjectWrap.getEClassType()))
						|| (BpmnModelClass.GATEWAY.isSuperTypeOf(userObjectWrap.getEClassType()))) {
					BpmnEditor editor = bpmnGraphDiagramManager.getBpmnGraphEditor();
					BpmnGraphUtils.setWorkbenchSelection(node, editor, false);
					IWorkbench workbench = editor.getSite().getWorkbenchWindow().getWorkbench();
					IHandlerService serv = (IHandlerService) workbench.getService(IHandlerService.class);
					IEvaluationContext state = serv.getCurrentState();
					state.addVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME, new GraphSelection(node));
					editor.triggerAction("graphCtrlDoubleClick");
				}
			}
			
			// Removing breakpoints from Subprocess Start and End nodes
			if ( eobjP !=null && BpmnModelClass.SUB_PROCESS.isSuperTypeOf( eobjP.eClass() ) && userObjWrapper != null 
					&& ( userObjWrapper.isInstanceOf( BpmnModelClass.START_EVENT ) || userObjWrapper
							.isInstanceOf( BpmnModelClass.END_EVENT ) )
					&& listAttribute != null && listAttribute.size() == 0 ) {
				
				if (node.getUI() instanceof ShapeNodeUI) {
					ShapeNodeUI nodeUI = (ShapeNodeUI) node.getUI();
					nodeUI.setShowBreakPoints(false);
				}
			} 
			
			bpmnGraphDiagramManager.refreshNode(node);
		}
	}
			
			
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#delete()
	 */
	@Override
	protected void deleteDiagramComponents(){	
	   editGraph(EDIT_TYPES.DELETE, bpmnGraphDiagramManager.getEditor().getEditorSite(), bpmnGraphDiagramManager);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#cut()
	 */
	protected void cutGraph(){
		bpmnGraphDiagramManager.setCutGraph(true);
		editGraph(EDIT_TYPES.CUT, bpmnGraphDiagramManager.getEditor().getEditorSite(), bpmnGraphDiagramManager);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#copy()
	 */
	protected void copyGraph(){
		bpmnGraphDiagramManager.setCopyGraph(true);
		editGraph(EDIT_TYPES.COPY, bpmnGraphDiagramManager.getEditor().getEditorSite(), bpmnGraphDiagramManager);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#paste()
	 */
	protected void pasteGraph(){
		bpmnGraphDiagramManager.setPasteGraph(true);
		editGraph(EDIT_TYPES.PASTE, bpmnGraphDiagramManager.getEditor().getEditorSite(), bpmnGraphDiagramManager);
	}
	
	public static BpmnSelectTool getTool() {
		return tool;
	}
	
	protected ContextMenuController getPopupMenuController() {
		if (this.bpmnPopupMenuController == null) {
			this.bpmnPopupMenuController = new BpmnPopupMenuController();
		}
		return (this.bpmnPopupMenuController);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.SelectTool#getPage()
	 */
	@Override
	public IWorkbenchPage getPage() {
		return bpmnGraphDiagramManager.getEditor().getSite().getPage();
	}

	public BpmnDiagramManager getDiagramManager() {
		return bpmnGraphDiagramManager;
	}

}
