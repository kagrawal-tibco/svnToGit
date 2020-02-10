package com.tibco.cep.bpmn.ui.editor;

import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.END_EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.EXCLUSIVE_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.MESSAGE_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.RULE_FUNCTION_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SEQUENCE_FLOW;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.START_EVENT;
import static com.tibco.cep.diagramming.utils.DiagramUtils.gridType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModelEvent;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelListener;
import org.eclipse.jface.text.source.IAnnotationModelListenerExtension;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.texteditor.MarkerAnnotation;

import com.tibco.cep.bpmn.core.debug.GraphPosition;
import com.tibco.cep.bpmn.core.debug.ProcessDebugModel;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.index.update.BpmnElementDelta;
import com.tibco.cep.bpmn.core.index.update.BpmnModelChangedEvent;
import com.tibco.cep.bpmn.core.index.update.BpmnModelDelta;
import com.tibco.cep.bpmn.core.index.update.BpmnProjectDelta;
import com.tibco.cep.bpmn.core.index.update.IBpmnElementDelta;
import com.tibco.cep.bpmn.core.index.update.IBpmnModelChangedListener;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.core.wsdl.WsdlWrapper;
import com.tibco.cep.bpmn.core.wsdl.WsdlWrapperFactory;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BPMNCommonImages;
import com.tibco.cep.bpmn.model.designtime.utils.BPMNCommonImages.IMAGE_SIZE;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.bpmn.ui.graph.handler.BpmnGraphSelectionListener;
import com.tibco.cep.bpmn.ui.graph.handler.GraphChangeListener;
import com.tibco.cep.bpmn.ui.graph.model.AbstractEdgeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.EmfModelPropertiesUpdateCommand;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeAdapterFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeEvent;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeListener;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelGraphFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.BPMNCallActivityNodeUI;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.BPMNCollapsedSubprocessNodeUI;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.BPMNExpandedSubprocessNodeUI;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.TaskNodeUI;
import com.tibco.cep.bpmn.ui.graph.palette.PaletteLoader;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.graph.rule.DeleteNodeRule;
import com.tibco.cep.bpmn.ui.graph.rule.DiagramRuleSet;
import com.tibco.cep.bpmn.ui.graph.tool.BpmnCreateEdgeTool;
import com.tibco.cep.bpmn.ui.graph.tool.BpmnCreateNodeTool;
import com.tibco.cep.bpmn.ui.graph.tool.BpmnEditTextTool;
import com.tibco.cep.bpmn.ui.graph.tool.BpmnPasteTool;
import com.tibco.cep.bpmn.ui.graph.tool.BpmnReconnectEdgeTool;
import com.tibco.cep.bpmn.ui.graph.tool.BpmnSelectTool;
import com.tibco.cep.bpmn.ui.graph.tool.BpmnTransferSelectedTool;
import com.tibco.cep.bpmn.ui.preferences.BpmnPreferenceConstants;
import com.tibco.cep.bpmn.ui.transfer.BPMNProcessTransfer;
import com.tibco.cep.bpmn.ui.validation.BpmnProcessValidator;
import com.tibco.cep.diagramming.actions.LeftLayoutActionDelegate;
import com.tibco.cep.diagramming.drawing.DiagramChangeListener;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tibco.cep.diagramming.tool.EDIT_TYPES;
import com.tibco.cep.diagramming.tool.ReconnectEdgeTool;
import com.tibco.cep.diagramming.tool.SelectTool;
import com.tibco.cep.diagramming.ui.ActivityBadgeNodeUI;
import com.tibco.cep.diagramming.ui.NoteNodeUI;
import com.tibco.cep.diagramming.ui.ShapeNodeUI;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.diagramming.utils.GatewayType;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.update.IStudioElementDelta;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.validation.IResourceValidator;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpoint;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpointInfo;
import com.tibco.cep.studio.debug.core.process.ProcessBreakpointInfo;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.overview.OverviewUtils;
import com.tomsawyer.canvas.swing.TSBaseSwingCanvas;
import com.tomsawyer.drawing.TSDGraph;
import com.tomsawyer.drawing.TSLabel;
import com.tomsawyer.drawing.TSPEdge;
import com.tomsawyer.drawing.TSPNode;
import com.tomsawyer.drawing.events.TSLayoutEvent;
import com.tomsawyer.drawing.events.TSLayoutEventData;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.drawing.geometry.shared.TSConstSize;
import com.tomsawyer.drawing.geometry.shared.TSPolygonShape;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graph.events.TSEventManager;
import com.tomsawyer.graph.events.TSGraphChangeEvent;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.graphicaldrawing.events.TSEEventManager;
import com.tomsawyer.graphicaldrawing.events.TSEViewportChangeEvent;
import com.tomsawyer.graphicaldrawing.events.TSEViewportChangeListener;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
import com.tomsawyer.graphicaldrawing.xml.TSEVisualizationXMLReader;
import com.tomsawyer.graphicaldrawing.xml.TSEVisualizationXMLWriter;
import com.tomsawyer.interactive.command.TSCommand;
import com.tomsawyer.interactive.command.TSCommandInterface;
import com.tomsawyer.interactive.swing.TSSwingCanvasPreferenceTailor;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEEditTextTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEPasteTool;
import com.tomsawyer.interactive.swing.editing.tool.TSETransferSelectedTool;
import com.tomsawyer.service.layout.TSLayoutConstants;
import com.tomsawyer.service.layout.TSSeparationConstraint;
import com.tomsawyer.util.preference.TSPreferenceData;

/**
 * 
 * @author ggrigore
 * 
 */
public class BpmnDiagramManager extends DiagramManager implements ModelChangeListener, IPropertyChangeListener, IDiagramManager, IBpmnDiagramManager {

	private class AnnotationManagerListener implements IAnnotationModelListener,IAnnotationModelListenerExtension {

		@Override
		public void modelChanged(AnnotationModelEvent event) {
			if (!event.isValid())
				return;


			if (event.isWorldChange()) {
				updateAnnotations();
				return;
			}

			Annotation[] annotations= event.getAddedAnnotations();
			int length= annotations.length;
			for (int i= 0; i < length; i++) {
				if (!skipAnnotation(annotations[i].getType())) {
					updateAnnotations();
					return;
				}
			}

			annotations= event.getRemovedAnnotations();
			length= annotations.length;
			for (int i= 0; i < length; i++) {
				if (!skipAnnotation(annotations[i].getType())) {
					updateAnnotations();
					return;
				}
			}

			annotations= event.getChangedAnnotations();
			length= annotations.length;
			for (int i= 0; i < length; i++) {
				if (!skipAnnotation(annotations[i].getType())) {
					try {
						updateAnnotations();
					} catch (Exception e) {
						//TODO
					}
					return;
				}
			}

		}

		@Override
		public void modelChanged(IAnnotationModel model) {
			updateAnnotations();

		}

	}

	public static class BpmnEventManager extends TSEEventManager {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2323505567005144174L;

		public BpmnEventManager() {
			super();
		}

		@Override
		public boolean isFiringEvents() {
			// TODO Auto-generated method stub
			return super.isFiringEvents();
		}

		@Override
		public void setFireEvents(boolean arg0) {
			// TODO Auto-generated method stub
			super.setFireEvents(arg0);
		}
	}
	public static class BpmnGraphManager extends TSEGraphManager {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6678148485303712666L;
		TSEEventManager m = null;

		public BpmnGraphManager() {
			super();
		}

		@Override
		public TSEventManager getEventManager() {
			if (m == null) {
				m = new BpmnEventManager();
			}
			return m;
		}
	}
	// ///////////////////////////////////////////////////////////////////////////////////////////////
	public class BpmnModelUpdateListener implements IBpmnModelChangedListener {

		protected void collectElementsToRefresh(EObject delta, List<IBpmnElementDelta> elementsToRefresh) {
			TreeIterator<EObject> contents = delta.eAllContents();
			while (contents.hasNext()) {
				elementsToRefresh.add(new BpmnElementDelta(contents.next(), IBpmnElementDelta.CHANGED));
			}

		}

		public void modelChanged(BpmnModelChangedEvent event) {
			try {
				final BpmnModelDelta delta = event.getDelta();
				List<BpmnProjectDelta> changedProjects = delta.getChangedProjects();
				for (BpmnProjectDelta bpmnProjectDelta : changedProjects) {
					// System.out.println(designerProjectDelta.toString());
					EObject index = bpmnProjectDelta.getChangedProject();
					EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(index);
					if (!indexWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME).equals(getProject().getName())) {
						break; // not interested in these changes
					}
					if (bpmnProjectDelta.getType() == IStudioElementDelta.ADDED || bpmnProjectDelta.getType() == IStudioElementDelta.REMOVED) {
						break; // the entire project was added/removed, refresh
						// not (totally) applicable
					}
					List<IBpmnElementDelta> elementsToRefresh = new ArrayList<IBpmnElementDelta>();
					collectElementsToRefresh(bpmnProjectDelta.getChangedProject(), elementsToRefresh);
					for (IBpmnElementDelta element : elementsToRefresh) {
						switch (((IBpmnElementDelta) element).getType()) {
						case IBpmnElementDelta.ADDED:
							processAddedElement((IBpmnElementDelta) element);
							break;

						case IBpmnElementDelta.REMOVED:
							processRemovedElement((IBpmnElementDelta) element);
							break;

						case IBpmnElementDelta.CHANGED:
							processChangedElement((IBpmnElementDelta) element);
							break;

						default:
							break;
						}
					}
					// layoutAndRefresh();
				}
			} catch (Exception e) {
				BpmnUIPlugin.log(e);
			}
		}

		@SuppressWarnings("unused")
		private void processAddedElement(IBpmnElementDelta delta) {
			EObject affectedChild = delta.getAffectedChild();

			// if (affectedChild instanceof EntityElement) {
			// // this causes a TSRuntimeException
			// // createEntity(((EntityElement) affectedChild).getEntity());
			// }
		}

		@SuppressWarnings("unused")
		private void processChangedElement(IBpmnElementDelta delta) {
			if (delta instanceof BpmnElementDelta) {
				BpmnElementDelta changeDelta = (BpmnElementDelta) delta;
				EObject removedChild = changeDelta.getRemovedChild();
				EObject addedChild = changeDelta.getAddedChild();
			}
		}

		private void processRemovedElement(IBpmnElementDelta delta) {
			@SuppressWarnings("unused")
			EObject removedElement = delta.getAffectedChild();
			// remove the node corresponding to this element
		}

		// EList<EObject> entries = delta.getEntries();
		// for (DesignerElement designerElement : entries) {
		// if (designerElement instanceof ElementContainer) {
		// collectElementsToRefresh((ElementContainer) designerElement,
		// elementsToRefresh);
		// } else if (designerElement instanceof StudioElementDelta) {
		// elementsToRefresh.add(designerElement);
		// }
		// }
	}
	private class NodeBreakpointInfo {
		private IProcessBreakpointInfo bpInfo;
		private TSENode node;

		public NodeBreakpointInfo(IProcessBreakpointInfo bpInfo, TSENode node) {
			super();
			this.bpInfo = bpInfo;
			this.node = node;
		}
		/**
		 * @return the bpInfo
		 */
		//		@SuppressWarnings("unused")
		public IProcessBreakpointInfo getBpInfo() {
			return bpInfo;
		}
		/**
		 * @return the node
		 */
		//		@SuppressWarnings("unused")
		public TSENode getNode() {
			return node;
		}
	}
	private BpmnLayoutManager layoutManager;
	private Composite swtAwtComponent;
	private BpmnEditorInput bpmnEditorInput;
	private ModelController modelController;
	// Create all TSE entities from here
	private ModelGraphFactory modelGraphFactory;
	private GraphChangeListener graphChangeListener;
	private BpmnGraphSelectionListener graphSelectionListener;
	protected BpmnSelectTool graphSelectTool;
	private BpmnCreateNodeTool graphCreateNodeTool;
	private BpmnCreateEdgeTool graphCreateEdgeTool;
	private BpmnReconnectEdgeTool reconnectEdgeTool;
	private BpmnTransferSelectedTool transferSelectedTool;
	@SuppressWarnings("unused")
	private boolean expandSubProcesses;
	private ModelChangeAdapterFactory modelChangeAdapterFactory;
	private boolean isLoading = false;
	private BpmnPasteTool bpmnPasteTool;
	private List<Annotation> fCachedAnnotations= new ArrayList<Annotation>();

	// used just for JSP
	// public BpmnGraphDiagramManager(String resourceURI) {
	// this.emfResourcePath = resourceURI;
	// controller = new EMFController(resourceURI, this);
	// }

	protected IPreferenceStore prefStore;

	@SuppressWarnings("unused")
	private boolean isFirstLayoutAfterLoading;

	protected boolean isFirstTimeCreatedGraph;


	protected List<TSEConnector> selectedConnectors = new ArrayList<TSEConnector>();

	static {
		TSEImage.setLoaderClass(BpmnDiagramManager.class);
	}

	private List<TSENode> leftMostNodes;

	private List<TSENode> otherNodes;

	private TSSeparationConstraint separationConstraint = null;

	public static final int NODE_ADDED = 1;

	public static final int NODE_DELETED = 2;

	public static final int NODE_PROPERTY_CHANGED = 3;
	//	Set< Integer > uniqueIds = new HashSet< Integer >();

	//	final private int OR = 0;
	//
	//	final private int XOR = 1;
	//
	//	final private int AND = 2;

	//	final private int ACTIVITY_RF = 10;
	//
	//	final private int ACTIVITY_TABLE = 11;
	//
	//	final private int ACTIVITY_JAVA = 12;
	//
	//	final private int ACTIVITY_GENERIC = 13;

	private static final String PROCESS_BREAKPOINT_MARKER_ANNOTATION = "com.tibco.cep.bpmn.ui.ProcessBreakpointMarkerAnnotation";

	private TSENode currentPreviouseNode;
	private IProject fProject;

	public BpmnDiagramManager() {
		super();
	}

	public BpmnDiagramManager(BpmnEditor editor) {
		this.editor = editor;
		// setPreferenceStore(BpmnUIPlugin.getDefault().getPreferenceStore());
	}

	@SuppressWarnings({ "unused", "deprecation" })
	private TSENode addSubProcess(TSEGraph graph, String title, boolean expanded) {
		TSENode parentNode = (TSENode) graph.addNode();
		TSEGraph childGraph = (TSEGraph) this.graphManager.addGraph();

		TSENode node1 = (TSENode) childGraph.addNode();
		node1.setSize(80, 70);
		node1.setTag("RF 1");
		node1.setUI((TSEObjectUI) new ActivityBadgeNodeUI(ActivityBadgeNodeUI.TYPE_TABLE));

		TSENode nodeBW = (TSENode) childGraph.addNode();
		nodeBW.setSize(80, 70);
		nodeBW.setTag("/RFs/StartupRF");
		nodeBW.setUI((TSEObjectUI) new ActivityBadgeNodeUI(ActivityBadgeNodeUI.TYPE_RF));

		childGraph.addEdge(node1, nodeBW);
		parentNode.setTag(title);
		parentNode.setChildGraph(childGraph);

		if (expanded) {
			TSENestingManager.expand(parentNode);
			BPMNExpandedSubprocessNodeUI subprocessUI = new BPMNExpandedSubprocessNodeUI();
			// subprocessUI.setBorderColor(new TSEColor(46,0,136));
			subprocessUI.setFillColor(BpmnUIConstants.SUB_PROCESS_FILL_COLOR);
			subprocessUI.setBorderDrawn(true);
			subprocessUI.setDrawChildGraphMark(false);
			parentNode.setUI(subprocessUI);
		} else {
			BPMNCollapsedSubprocessNodeUI subprocessUI = new BPMNCollapsedSubprocessNodeUI();
			subprocessUI.setFillColor(BpmnUIConstants.SUB_PROCESS_FILL_COLOR);
			subprocessUI.setBorderDrawn(true);
			// subprocessUI.setDrawChildGraphMark(false);
			parentNode.setUI(subprocessUI);
			parentNode.setSize(80, 60);
		}

		((BpmnLayoutManager) this.getLayoutManager()).setLayoutOptionsForSubProcess(childGraph);

		return parentNode;
	}

	@Override
	public void addToEditGraphMap(Map<String, Object> map, List<TSENode> selectedNodes, List<TSEEdge> selectedEdges, boolean isCopy) {
		try {
			map.clear();
			BpmnGraphUtils.addToEditGraphMap(map, selectedNodes, selectedEdges, getBpmnEditorInput().getProcessModel(), isCopy);
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
	}

	protected void addZoomListener() {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						TSEViewportChangeListener listener = new TSEViewportChangeListener() {
							@Override
							public void viewportChanged(TSEViewportChangeEvent eventData) {
								try {
									zoomChanged();
									// getDrawingCanvas().resetScrollRange();
									// getDrawingCanvas().resetWorkBounds();
								} catch (Exception e) {
									BpmnUIPlugin.log(e);
								}

							}
						};
						((TSEEventManager) graphManager.getEventManager()).addViewportChangeListener(drawingCanvas, listener, TSEViewportChangeEvent.ZOOM);
					}
				});

			}
		});

	}

	private void cacheAnnotations() {
		fCachedAnnotations.clear();
		final IAnnotationModel graphAnnotationModel = getBpmnGraphEditor().getGraphAnnotationModel();
		if (graphAnnotationModel != null) {
			Iterator<?> iter= graphAnnotationModel.getAnnotationIterator();
			while (iter.hasNext()) {
				Annotation annotation= (Annotation) iter.next();

				if (annotation.isMarkedDeleted())
					continue;

				if (skipAnnotation(annotation.getType()))
					continue;

				fCachedAnnotations.add(annotation);
			}
		}
	}



	@Override
	public boolean canCopy() {
		boolean copy = getSelectedNodes().size() > 0 ;
		return copy;
	}

	@Override
	public boolean canCut() {
		boolean cut = getSelectedNodes().size() > 0 ;
		return cut;
	}

	@Override
	public boolean canDelete() {
		List<TSENode> nodes = new ArrayList<TSENode>();
		nodes.addAll(getSelectedNodes());
		boolean delete = nodes.size() > 0 || getSelectedEdges().size() > 0;
		if (delete) {
			DeleteNodeRule deleteNodeRule = new DeleteNodeRule(new DiagramRuleSet());
			for (TSENode node : nodes) {
				if (isBpmnNode(node) && !deleteNodeRule.isAllowed(new Object[] { node })) {
					delete = false;
					break;
				}
			}
		}

		return delete;
	}

	@Override
	public boolean canPaste() {
		BPMNProcessTransfer instance = BPMNProcessTransfer.getInstance();
		Map<String, Object> cMap = instance.getCopyMap();
		boolean paste = cMap.size() >0;
		return paste;
	}

	private void changeVersion(EObjectWrapper<EClass, EObject> process) {
		String attrName = BpmnMetaModelExtensionConstants.E_ATTR_VERSION;
		if (ExtensionHelper.isValidDataExtensionAttribute(process, attrName)) {
			EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(process);
			if (valueWrapper != null) {
				int version = 0;
				boolean isVersionChanged = false;
				Object o = valueWrapper.getAttribute(attrName);
				if (o != null && o instanceof Integer) {
					version = (Integer) o;
				}

				o = valueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IS_VERSION_CHANGED);
				if (o != null) {
					isVersionChanged = (Boolean) o;
				}
				valueWrapper
				.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IS_VERSION_CHANGED, false);
				if (!isVersionChanged)
					valueWrapper.setAttribute(attrName, version + 1);
			}
		}
	}

	public void clearAnnotations() {
		List<TSENode> nodes = findNodesOfType((TSEGraph) this.graphManager.getMainDisplayGraph(), BpmnModelClass.FLOW_NODE);
		for (TSENode node : nodes) {
			if (node.getNodeUI() instanceof ShapeNodeUI) {
				ShapeNodeUI nui = (ShapeNodeUI) node.getNodeUI();
				nui.setDrawGlow(false);
				nui.setDrawError(false);
				nui.setInputBreakpoint(false);
				nui.setOutputBreakpoint(false);
				nui.setInputBreakpointHit(false);
				nui.setOutputBreakpointHit(false);
			}
		}
		DiagramUtils.refreshDiagram(this);
		OverviewUtils.refreshOverview(getEditor().getEditorSite(), true, true);

	}

	private boolean compareDouble(Double d1, Double d2, int significantDigit) {
		long l1 = Math.round(d1 * Math.pow(10, significantDigit));
		long l2 = Math.round(d2 * Math.pow(10, significantDigit));
		return l1 == l2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#copyGraph()
	 */
	public void copyGraph() {
		if (!getBpmnGraphEditor().isEnabled()) {
			return;
		}
		super.copyGraph();
		BpmnGraphUtils.editGraph(EDIT_TYPES.COPY, getEditor().getEditorSite(), this);
	}

	private TSENode createEndEvent(TSEGraph graph, String name, String toolId,String attachedResource) {
		final EObject laneObj = (EObject) graph.getUserObject();
		final EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		final EObject processObj = (EObject) graph.getGreatestAncestor().getUserObject();
		final EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(processObj);

		EObjectWrapper<EClass, EObject> eventWrapper = getModelController().createEvent(name, toolId, END_EVENT, MESSAGE_EVENT_DEFINITION, process, lane);
		setAttachedResourceAttribute(eventWrapper,attachedResource);
		// final EObjectWrapper<EClass, EObject> eventWrapper =
		// EObjectWrapper.createInstance(BpmnMetaModelConstants.END_EVENT);
		// eventWrapper.setAttribute("id", GUIDGenerator.getGUID());
		// eventWrapper.setAttribute("name", label);
		// ((EList)eventWrapper.getAttribute("lanes")).add(laneObj);
		// ((EList<EObject>)process.getAttribute("flowElements")).add(eventWrapper.getEInstance());

		AbstractNodeUIFactory uiFactory = BpmnGraphUIFactory.getInstance((BpmnLayoutManager) getLayoutManager()).getNodeUIFactory(name, "", toolId, END_EVENT,
				BpmnMetaModel.MESSAGE_EVENT_DEFINITION);
		TSENode tsNode = uiFactory.addNode(graph);
		tsNode.setUserObject(eventWrapper.getEInstance());
		uiFactory.updateNodeToolTip(tsNode);
		String label = eventWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		uiFactory.addNodeLabel(tsNode, label);
		// TSENode tsNode = (TSENode) graph.addNode();
		// tsNode.setUserObject(eventWrapper.getEInstance());
		// tsNode.setSize(80, 75);
		// tsNode.setTag(label);
		// ActivityBadgeNodeUI badgeUI = new
		// ActivityBadgeNodeUI(ActivityBadgeNodeUI.TYPE_RF);
		// badgeUI.setGradient(BpmnGraphConstants.BADGE_GRADIENT_START_COLOR,
		// BpmnGraphConstants.BADGE_GRADIENT_END_COLOR);
		// // badgeUI.setGradient(RoundRectNodeUI.ERROR_START_COLOR,
		// RoundRectNodeUI.ERROR_END_COLOR);
		// tsNode.setUI(badgeUI);
		// EndNodeCreator.decorateEndNode(tsNode,
		// label,
		// BpmnMetaModel.getInstance().getEClass(BpmnMetaModel.END_EVENT));
		// this.layoutManager.setNodeLabelOptions((TSENodeLabel)
		// tsNode.labels().get(0));
		return tsNode;
	}

	private TSENode createEndEventInSubprocess(TSEGraph graph, String name, String toolId) {
		final EObject laneObj = (EObject) graph.getUserObject();
		final EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		final EObject processObj = (EObject) graph.getUserObject();
		final EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(processObj);

		EObjectWrapper<EClass, EObject> eventWrapper = getModelController().createEvent(name, toolId, END_EVENT, null, process, lane);
		AbstractNodeUIFactory uiFactory = BpmnGraphUIFactory.getInstance((BpmnLayoutManager) getLayoutManager()).getNodeUIFactory(name, "", toolId, END_EVENT,null);
		TSENode tsNode = uiFactory.addNode(graph);
		tsNode.setUserObject(eventWrapper.getEInstance());
		uiFactory.updateNodeToolTip(tsNode);
		String label = eventWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		uiFactory.addNodeLabel(tsNode, label);
		//fixing defect BE-17439
		//		boolean showBreakpoints = prefStore.getBoolean(BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS);
		//		if (tsNode.getUI() instanceof ShapeNodeUI) {
		//			ShapeNodeUI nodeUI = (ShapeNodeUI) tsNode.getUI();
		//				nodeUI.setShowBreakPoints(showBreakpoints);
		//		}
		return tsNode;
	}

	// GGG? label, label?
	private TSENode createGateway(TSEGraph graph, String name, String toolId, GatewayType type) {
		final EObject laneObj = (EObject) graph.getUserObject();
		final EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		final EObject processObj = (EObject) graph.getGreatestAncestor().getUserObject();
		final EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(processObj);

		final EObjectWrapper<EClass, EObject> gatewayWrapper = getModelController().createGateway(name, toolId, EXCLUSIVE_GATEWAY, null, process, lane);

		// final EObjectWrapper<EClass, EObject> gatewayWrapper =
		// EObjectWrapper.createInstance(BpmnMetaModelConstants.EXCLUSIVE_GATEWAY);
		// gatewayWrapper.setAttribute("id", GUIDGenerator.getGUID());
		// gatewayWrapper.setAttribute("name", label);
		// ((EList)gatewayWrapper.getAttribute("lanes")).add(laneObj);
		// EEnumWrapper<EEnum, EEnumLiteral> gwDir =
		// EEnumWrapper.createInstance(BpmnMetaModel.ENUM_GATEWAY_DIRECTION);
		// gatewayWrapper.setAttribute("gatewayDirection",
		// gwDir.getEnumerator(BpmnMetaModel.ENUM_GATEWAY_DIRECTION_DIVERGING));
		// ((EList<EObject>)process.getAttribute("flowElements")).add(gatewayWrapper.getEInstance());

		AbstractNodeUIFactory uiFactory = BpmnGraphUIFactory.getInstance((BpmnLayoutManager) getLayoutManager()).getNodeUIFactory(name, "", toolId,
				EXCLUSIVE_GATEWAY);
		TSENode tsNode = uiFactory.addNode(graph);
		tsNode.setUserObject(gatewayWrapper.getEInstance());
		uiFactory.updateNodeToolTip(tsNode);
		// TSENode tsNode = (TSENode) graph.addNode();
		// tsNode.setUserObject(gatewayWrapper.getEInstance());
		// tsNode.setSize(80, 75);
		// tsNode.setTag(label);
		// ActivityBadgeNodeUI badgeUI = new
		// ActivityBadgeNodeUI(ActivityBadgeNodeUI.TYPE_RF);
		// badgeUI.setGradient(BpmnGraphConstants.BADGE_GRADIENT_START_COLOR,
		// BpmnGraphConstants.BADGE_GRADIENT_END_COLOR);
		// // badgeUI.setGradient(RoundRectNodeUI.ERROR_START_COLOR,
		// RoundRectNodeUI.ERROR_END_COLOR);
		// tsNode.setUI(badgeUI);
		// GatewayNodeCreator.decorateGatewayNode(tsNode, type.toString(),type);
		return tsNode;
	}

	public TSEGraphManager createGraphManager() {
		return new BpmnGraphManager();
	}

	public void createInitialModel() throws Exception {
		initializeModel();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {
				try {
					if (getBpmnEditorInput() != null) {
						createInitialModel(monitor);
						modelGraphFactory.buildNodeRegistry();
					}
				} catch (Exception e) {
					BpmnUIPlugin.log(Messages.getString(MessageFormat.format(BpmnUIConstants.BPMN_MODEL_OPEN_FAIL, getBpmnEditorInput().getFile().getName())),
							e);
				}
			}
		};
		if (Display.getCurrent() != null) {
			new ProgressMonitorDialog(BpmnUIPlugin.getActiveWorkbenchShell()).run(true, true, op);
		} else {
			op.run(new NullProgressMonitor());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.diagramming.drawing.DiagramManager#createInitialModel()
	 */
	public void createInitialModel(final IProgressMonitor monitor) throws Exception {
		try {
			isFirstTimeCreatedGraph = true;
			monitor.beginTask(Messages.getString(BpmnUIConstants.BPMN_MODEL_NEW_PROCESS), 100);
			createInitialModelWithPool(new SubProgressMonitor(monitor, 50));
			getLayoutManager().callBatchGlobalLayout();
			Display.getDefault().asyncExec(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					getBpmnGraphEditor().doSave(new SubProgressMonitor(monitor, 50));
					getBpmnGraphEditor().initializeGraphDocumentationModel();
				}
			});
		} finally {
			monitor.done();
		}
	}

	protected void createInitialModelSimple() {

		/*
		 * 
		 * TSENode g1 = this.createWebGatewayNode("blah", OR); TSENode s1 =
		 * this.createWebStartNode("start"); TSENode e1 =
		 * this.createWebEndNode("end"); TSENode a1 =
		 * this.createWebActivityNode("run", ACTIVITY_RF, false); TSENode a2 =
		 * this.createWebActivityNode("activity", ACTIVITY_RF, true);
		 * this.createWebLink("link", s1, g1); this.createWebLink("link", g1,
		 * a1); this.createWebLink("link", g1, a2); this.createWebLink("link",
		 * a2, e1);
		 * 
		 * //////////////////////////////////////////////////////////////////////
		 * / TSENode node1 = this.createWebActivityNode("/Rules/FN_1",
		 * ACTIVITY_RF, false); TSENode node2 =
		 * this.createWebActivityNode("/RF/RuleFunc2", ACTIVITY_RF, false);
		 * TSENode node3 = this.createWebActivityNode("/Rules/FN_3",
		 * ACTIVITY_RF, false); TSENode nodeBW =
		 * this.createWebActivityNode("RF Activity", ACTIVITY_JAVA, false);
		 * TSENode errorNode = this.createWebActivityNode("Error Activity",
		 * ACTIVITY_JAVA, true); TSENode errorNode2 =
		 * this.createWebActivityNode("ErrorHandler RF", ACTIVITY_RF, true);
		 * TSENode dec = this.createWebGatewayNode(null, XOR); TSENode initNode
		 * = this.createWebStartNode("start"); TSENode finalNode =
		 * this.createWebEndNode("end"); TSENode concurrentActivityNode =
		 * this.createWebGatewayNode(null, OR); TSENode noteNode =
		 * this.createNote("Some\nNote Here...");
		 * 
		 * ///// left off here....
		 * 
		 * TSENode subprocess1 = this.addSubProcess(graph, "Sub process 1",
		 * true); TSENode subprocess2 = this.addSubProcess(graph,
		 * "\nCollapsed\nProcess 2", false); TSENode eventNode =
		 * tseEntityCreator.createOtherActivity(null,
		 * "rule-function_48x48.png"); TSENode eventNode2 =
		 * tseEntityCreator.createOtherActivity(null, "table_48x48.png");
		 * this.createConnection(initNode, node1, null);
		 * this.createConnection(node1, dec, null); this.createConnection(node1,
		 * nodeBW, null); this.createConnection(dec, node2, "status=RUNNING");
		 * this.createConnection(node2, concurrentActivityNode, "path2");
		 * this.createConnection(node3, concurrentActivityNode, "path3");
		 * this.createConnection(concurrentActivityNode,finalNode, null);
		 * this.createConnection(dec, node3, "status=NEED_INFO");
		 * this.createConnection(noteNode, node1, null);
		 * this.createConnection(node3, subprocess1, null);
		 * this.createConnection(subprocess1, finalNode, null);
		 * this.createConnection(node3, subprocess2, null);
		 * this.createConnection(subprocess2, finalNode, null);
		 * this.createConnection(eventNode, node1, null); TSEEdge edge =
		 * this.createConnection(initNode, eventNode, null); TSEEdge edgeN =
		 * this.createConnection(node3, eventNode2, null); TSEEdge e =
		 * this.createConnection(dec, errorNode, null); TSEEdge e2 =
		 * this.createConnection(node1, errorNode2, null);
		 * 
		 * ((TSEEdgeUI) e.getUI()).setLineColor(TSEColor.red); ((TSEEdgeUI)
		 * e2.getUI()).setLineColor(TSEColor.red);
		 * 
		 * TSECurvedEdgeUI newEdgeUI = new TSECurvedEdgeUI();
		 * newEdgeUI.setAntiAliasingEnabled(true); newEdgeUI.setCurvature(100);
		 * newEdgeUI.setLineStyle(TSEPolylineEdgeUI.LINE_STYLE_DOT);
		 * edge.setUI((TSEEdgeUI) newEdgeUI.clone()); edgeN.setUI((TSEEdgeUI)
		 * newEdgeUI.clone());
		 */

		// /////////////////////////////////////////////////////////////////////

		// this.createPoolandLane();
		// TSENode node1 = modelGraphFactory.createActivity(null,
		// "/Rules/FN_1");
		// TSENode node2 = modelGraphFactory.createActivity(null,
		// "/RF/RuleFunc2");
		// TSENode node3 = modelGraphFactory.createActivity(null,
		// "/Rules/FN_3");
		// TSENode nodeBW = modelGraphFactory.createActivity(null,
		// "RF Activity", ActivityTypes.ACTIVITY_NONE);
		// // nodeBW.setSize(80, 75);
		// TSENode errorNode = modelGraphFactory.createActivity(null,
		// "Error Activity", ActivityTypes.ACTIVITY_NONE, true);
		// // errorNode was not RF error but "java image"...
		// TSENode errorNode2 = modelGraphFactory.createActivity(null,
		// "ErrorHandler RF", ActivityTypes.ACTIVITY_RF, true);
		// TSENode dec = modelGraphFactory.createGateway(null, null,
		// GatewayType.XOR);
		// TSENode initNode = modelGraphFactory.createStartActivity(null, null);
		// TSENode finalNode = modelGraphFactory.createEndActivity(null, null);
		// TSENode concurrentActivityNode =
		// modelGraphFactory.createGateway(null, null, GatewayType.OR);
		// TSENode noteNode = this.createNote("Some\nNote Here...");
		// TSENode subprocess1 = this.addSubProcess(graph, "Sub process 1",
		// true);
		// TSENode subprocess2 = this.addSubProcess(graph,
		// "\nCollapsed\nProcess 2", false);
		// TSENode eventNode = modelGraphFactory.createOtherActivity(null,
		// "rule-function_48x48.png");
		// TSENode eventNode2 = modelGraphFactory.createOtherActivity(null,
		// "table_48x48.png");
		// this.createConnection(initNode, node1, null);
		// this.createConnection(node1, dec, null);
		// this.createConnection(node1, nodeBW, null);
		// this.createConnection(dec, node2, "status=RUNNING");
		// this.createConnection(node2, concurrentActivityNode, "path2");
		// this.createConnection(node3, concurrentActivityNode, "path3");
		// this.createConnection(concurrentActivityNode,finalNode, null);
		// this.createConnection(dec, node3, "status=NEED_INFO");
		// this.createConnection(noteNode, node1, null);
		// this.createConnection(node3, subprocess1, null);
		// this.createConnection(subprocess1, finalNode, null);
		// this.createConnection(node3, subprocess2, null);
		// this.createConnection(subprocess2, finalNode, null);
		// this.createConnection(eventNode, node1, null);
		// TSEEdge edge = this.createConnection(initNode, eventNode, null);
		// TSEEdge edgeN = this.createConnection(node3, eventNode2, null);
		// TSEEdge e = this.createConnection(dec, errorNode, null);
		// TSEEdge e2 = this.createConnection(node1, errorNode2, null);
		//
		// ((TSEEdgeUI) e.getUI()).setLineColor(TSEColor.red);
		// ((TSEEdgeUI) e2.getUI()).setLineColor(TSEColor.red);
		//
		// TSECurvedEdgeUI newEdgeUI = new TSECurvedEdgeUI();
		// newEdgeUI.setAntiAliasingEnabled(true);
		// newEdgeUI.setCurvature(100);
		// newEdgeUI.setLineStyle(TSEPolylineEdgeUI.LINE_STYLE_DOT);
		// edge.setUI((TSEEdgeUI) newEdgeUI.clone());
		// edgeN.setUI((TSEEdgeUI) newEdgeUI.clone());
	}

	protected void createInitialModelWithPool(IProgressMonitor pm) {
		try {
			pm.beginTask(Messages.getString(BpmnUIConstants.BPMN_MODEL_CREATE_INITIAL), 10);
			pm.subTask(BpmnUIConstants.BPMN_MODEL_INIT_GRAPH);
			getGraphManager().emptyTopology();
			initializeModel();
			pm.worked(1);
			pm.subTask(Messages.getString("bpmnDiagramManager.createProcess"));
			String processName = bpmnEditorInput.getName();
			processName = processName.substring(0, processName.length() - BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION.length() - 1);
			TSEGraph graph = getModelGraphFactory().createProcess(getBpmnEditorInput().getFile(), processName, getBpmnEditorInput().getDescription(),
					new SubProgressMonitor(pm, 7));
			EObjectWrapper<EClass, EObject> useInstance = EObjectWrapper.wrap((EObject) graph.getUserObject());
			getBpmnEditorInput().setProcessModel(useInstance);
			pm.worked(1);
			pm.subTask(Messages.getString("bpmnDiagramManager.creatingPoolandLanes"));
			TSENode poolNode = getModelGraphFactory().createPoolLaneNode(graph, BpmnUIConstants.DEFAULT_LANE_NAME, "swimlane.pool");
			// TSENode poolNode =
			// getModelGraphFactory().createPoolLaneNode(graph,
			// "Order Fulfillment Pool");
			// TSENode lane1 =
			// getModelGraphFactory().createPoolLaneNode(poolNode, "Order");
			// TSENode lane2 =
			// getModelGraphFactory().createPoolLaneNode(poolNode, "Fulfill");
			// TSENode lane3 =
			// getModelGraphFactory().createPoolLaneNode(poolNode, "Monitor");
			// do this once pool is populated with the lane nodes only
			// ((BpmnLayoutManager)
			// this.getLayoutManager()).setLayoutOptionsForPool(
			// poolNode);
			pm.worked(1);
			pm.subTask(Messages.getString("bpmnDiagramManager.populatingLanes"));
			this.populateLane(poolNode);
			getGraphManager().setMainDisplayGraph((TSDGraph) poolNode.getChildGraph());
			// this.populateLane(lane1);
			// this.populateLane(lane2);
			// this.populateLane(lane3);
			pm.worked(1);
		} finally {
			pm.done();
		}
	}

	protected void createInitialModelWithPoolForSuresh() {
		TSEImage.setLoaderClass(this.getClass());
		TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
		TSENode poolNode = getModelGraphFactory().createPoolLaneNode(graph, "Pool", "swimlane.pool");
		TSENode lane1 = getModelGraphFactory().createPoolLaneNode(poolNode.getChildGraph(), "AS", "swimlane.lane");
		TSENode lane2 = getModelGraphFactory().createPoolLaneNode(poolNode.getChildGraph(), "BE Dev", "swimlane.lane");
		TSENode lane3 = getModelGraphFactory().createPoolLaneNode(poolNode.getChildGraph(), "QA", "swimlane.lane");
		TSENode lane4 = getModelGraphFactory().createPoolLaneNode(poolNode.getChildGraph(), "Docs", "swimlane.lane");
		TSENode lane5 = getModelGraphFactory().createPoolLaneNode(poolNode.getChildGraph(), "PM", "swimlane.lane");
		TSENode lane6 = getModelGraphFactory().createPoolLaneNode(poolNode.getChildGraph(), "Support", "swimlane.lane");

		// do this once pool is populated with the lane nodes only
		((BpmnLayoutManager) this.getLayoutManager()).setLayoutOptionsForPool(poolNode);
		this.populateLane(lane1);
		this.populateLane(lane2);
		this.populateLane(lane3);
		this.populateLane(lane4);
		this.populateLane(lane5);
		this.populateLane(lane6);
	}

	protected void createInitialModelWithPoolLanes(IProgressMonitor pm) {
		try {
			pm.beginTask(Messages.getString(BpmnUIConstants.BPMN_MODEL_CREATE_INITIAL), 10);
			pm.subTask(BpmnUIConstants.BPMN_MODEL_INIT_GRAPH);
			getGraphManager().emptyTopology();
			initializeModel();
			pm.worked(1);
			pm.subTask(BpmnMessages.getString("createProcess_label"));
			String processName = bpmnEditorInput.getName();
			processName = processName.substring(0, processName.length() - BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION.length() - 1);
			TSEGraph graph = getModelGraphFactory().createProcess(getBpmnEditorInput().getFile(), processName, getBpmnEditorInput().getDescription(),
					new SubProgressMonitor(pm, 7));

			EObjectWrapper<EClass, EObject> useInstance = EObjectWrapper.wrap((EObject) graph.getUserObject());
			getBpmnEditorInput().setProcessModel(useInstance);

			pm.worked(1);
			pm.subTask(BpmnMessages.getString("bpmnDiagramMgr_createPoolLaness_label"));
			// TSENode poolNode =
			// getModelGraphFactory().createPoolLaneNode(graph,
			// BpmnUIConstants.DEFAULT_LANE_NAME);
			TSENode poolNode = getModelGraphFactory().createPoolLaneNode(graph, "Order Fulfillment Pool", "swimlane.pool");
			TSENode lane1 = getModelGraphFactory().createPoolLaneNode(poolNode, "Order", "swimlane.lane");
			TSENode lane2 = getModelGraphFactory().createPoolLaneNode(poolNode, "Fulfill", "swimlane.lane");
			TSENode lane3 = getModelGraphFactory().createPoolLaneNode(poolNode, "Monitor", "swimlane.lane");
			// do this once pool is populated with the lane nodes only
			((BpmnLayoutManager) this.getLayoutManager()).setLayoutOptionsForPool(poolNode);
			pm.worked(1);
			pm.subTask(BpmnMessages.getString("populatinglanes_label"));
			this.populateLane(poolNode);
			getGraphManager().setMainDisplayGraph((TSDGraph) poolNode.getChildGraph());
			this.populateLane(lane1);
			this.populateLane(lane2);
			this.populateLane(lane3);
			pm.worked(1);
		} finally {
			pm.done();
		}
	}

	@SuppressWarnings("unused")
	private TSENode createNote(String noteString) {
		TSENode noteNode = (TSENode) this.graphManager.getMainDisplayGraph().addNode();
		noteNode.setSize(40, 25);
		if (noteString == null) {
			noteNode.setName(Messages.getString("PALLETTE_ENTRY_TOOL_NOTE_TITLE"));
		} else {
			noteNode.setName(noteString);
		}
		noteNode.setShape(TSPolygonShape.fromString("[ 6 (0, 0) (100, 0) (100, 75) (75, 75) (75, 100) (0, 100) ]"));
		noteNode.setResizability(TSESolidObject.RESIZABILITY_TIGHT_FIT);
		noteNode.setUI((TSEObjectUI) new NoteNodeUI());
		return noteNode;
	}

	@SuppressWarnings("unused")
	private void createPoolAndLane() {
		// TSENode poolNode = (TSENode) graph.addNode();
		// poolNode.setTag("Pool");
		// // reuse graph var to hold pool child graph
		// graph = (TSEGraph) this.graphManager.addGraph();
		// this.setLayoutOptionsForSubProcess(graph);
		// poolNode.setChildGraph(graph);
		// TSENestingManager.expand(poolNode);
		// poolNode.setUI((TSEObjectUI)subprocessUI.clone());
		//
		// TSENode laneNode = (TSENode) graph.addNode();
		// laneNode.setTag("Lane");
		// // reuse graph var to hold pool child graph
		// graph = (TSEGraph) this.graphManager.addGraph();
		// this.setLayoutOptionsForSubProcess(graph);
		// laneNode.setChildGraph(graph);
		// TSENestingManager.expand(laneNode);
		// laneNode.setUI((TSEObjectUI)subprocessUI.clone());
		// ========================================================
		// TSENode concurrentActivityNode = (TSENode) graph.addNode();
		// TSEConnector inConnOne = (TSEConnector)
		// concurrentActivityNode.addConnector();
		// inConnOne.setSize(3.0, 3.0);
		// inConnOne.setProportionalXOffset(-0.3);
		// inConnOne.setProportionalYOffset(0.5);
		//
		// TSEConnector inConnTwo = (TSEConnector)
		// concurrentActivityNode.addConnector();
		// inConnTwo.setSize(3.0, 3.0);
		// inConnTwo.setProportionalXOffset(0.3);
		// inConnTwo.setProportionalYOffset(0.5);
		//
		// TSEConnector outConn = (TSEConnector)
		// concurrentActivityNode.addConnector();
		// outConn.setSize(3.0, 3.0);
		// outConn.setProportionalXOffset(0.0);
		// outConn.setProportionalYOffset(-0.5);
		//
		// concurrentActivityNode.setUI((TSEObjectUI) new
		// ConcurrentActivityNodeUI());
		// edge2.setTargetConnector(inConnOne);
		// edge3.setTargetConnector(inConnTwo);
		// edgeOut.setSourceConnector(outConn);
	}

	// GGG? label, label?
	private TSENode createRFTask(TSEGraph graph, String name, String toolId,String attachedResource) {
		final EObject laneObj = (EObject) graph.getUserObject();
		final EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		final EObject processObj = (EObject) graph.getGreatestAncestor().getUserObject();
		final EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(processObj);

		final EObjectWrapper<EClass, EObject> taskWrapper = getModelController().createActivity(name, toolId, RULE_FUNCTION_TASK, process, lane);
		setAttachedResourceAttribute(taskWrapper,attachedResource);
		// final EObjectWrapper<EClass, EObject> taskWrapper =
		// EObjectWrapper.createInstance(BpmnMetaModelConstants.RULE_FUNCTION_TASK);
		// taskWrapper.setAttribute("id", GUIDGenerator.getGUID());
		// taskWrapper.setAttribute("name", label);
		// ((EList)taskWrapper.getAttribute("lanes")).add(laneObj);
		// ((EList<EObject>)process.getAttribute("flowElements")).add(taskWrapper.getEInstance());

		AbstractNodeUIFactory uiFactory = BpmnGraphUIFactory.getInstance((BpmnLayoutManager) getLayoutManager()).getNodeUIFactory(name, "", toolId,
				RULE_FUNCTION_TASK);
		TSENode tsNode = uiFactory.addNode(graph);
		tsNode.setUserObject(taskWrapper.getEInstance());
		uiFactory.updateNodeToolTip(tsNode);
		String label = taskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		uiFactory.addNodeLabel(tsNode, label);
		// TSENode tsNode = (TSENode) graph.addNode();
		// tsNode.setUserObject(taskWrapper.getEInstance());
		// tsNode.setSize(80, 75);
		// tsNode.setTag(label);
		// ActivityBadgeNodeUI badgeUI = new
		// ActivityBadgeNodeUI(ActivityBadgeNodeUI.TYPE_RF);
		// badgeUI.setGradient(BpmnGraphConstants.BADGE_GRADIENT_START_COLOR,
		// BpmnGraphConstants.BADGE_GRADIENT_END_COLOR);
		// // badgeUI.setGradient(RoundRectNodeUI.ERROR_START_COLOR,
		// RoundRectNodeUI.ERROR_END_COLOR);
		// tsNode.setUI(badgeUI);
		// TaskNodeCreator.decorateTaskNode(tsNode, label,
		// ActivityTypes.ACTIVITY_RF);
		return tsNode;
	}

	private TSENode createRFTaskInSubprocess(TSEGraph graph, String name, String toolId) {
		final EObject laneObj = (EObject) graph.getUserObject();
		final EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		final EObject processObj = (EObject) graph.getUserObject();
		final EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(processObj);

		final EObjectWrapper<EClass, EObject> taskWrapper = getModelController().createActivity(name, toolId, RULE_FUNCTION_TASK, process, lane);

		AbstractNodeUIFactory uiFactory = BpmnGraphUIFactory.getInstance((BpmnLayoutManager) getLayoutManager()).getNodeUIFactory(name, "", toolId,
				RULE_FUNCTION_TASK);
		TSENode tsNode = uiFactory.addNode(graph);
		tsNode.setUserObject(taskWrapper.getEInstance());
		uiFactory.updateNodeToolTip(tsNode);
		String label = taskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		uiFactory.addNodeLabel(tsNode, label);
		//fixing defect BE-17439
		boolean showBreakpoints = prefStore.getBoolean(BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS);
		if (tsNode.getUI() instanceof ShapeNodeUI) {
			ShapeNodeUI nodeUI = (ShapeNodeUI) tsNode.getUI();
			nodeUI.setShowBreakPoints(showBreakpoints);
		}
		return tsNode;
	}

	private TSEEdge createSeqFlowEdge(TSEGraph graph, TSENode startNode, TSENode endNode) {
		final EObject laneObj = (EObject) graph.getUserObject();
		final EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		final EObject processObj = (EObject) graph.getGreatestAncestor().getUserObject();
		final EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(processObj);

		final EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject) startNode.getUserObject());
		final EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject) endNode.getUserObject());
		EObjectWrapper<EClass, EObject> sequenceFlow = getModelController().createSequenceFlow(start, end, process, lane);

		// EObjectWrapper<EClass, EObject> sequenceFlow =
		// EObjectWrapper.createInstance(BpmnMetaModel.SEQUENCE_FLOW);
		// sequenceFlow.setAttribute("id", GUIDGenerator.getGUID());
		// sequenceFlow.setAttribute("name",
		// start.getAttribute("name")+"_"+end.getAttribute("name"));
		// sequenceFlow.setAttribute("isImmediate", true);
		// sequenceFlow.setAttribute("sourceRef", start.getEInstance());
		// sequenceFlow.setAttribute("targetRef", end.getEInstance());
		// ((EList)start.getAttribute("outgoing")).add(sequenceFlow.getEInstance());
		// ((EList)end.getAttribute("incoming")).add(sequenceFlow.getEInstance());
		// ((EList)sequenceFlow.getAttribute("lanes")).add(laneObj);
		// ((EList)lane.getAttribute("flowElementRefs")).add(sequenceFlow.getEInstance());
		// ((EList)process.getAttribute("flowElements")).add(sequenceFlow.getEInstance());
		// String name =
		// start.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME)+"_"+end.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		@SuppressWarnings("unused")
		String id = sequenceFlow.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		String label = sequenceFlow.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		AbstractEdgeUIFactory uiFactory = BpmnGraphUIFactory.getInstance((BpmnLayoutManager) getLayoutManager()).getEdgeUIFactory(label, SEQUENCE_FLOW);
		TSEEdge edge = uiFactory.addEdge(graph, startNode, endNode);
		uiFactory.addEdgeLabel(edge, label);
		edge.setUserObject(sequenceFlow.getEInstance());
		// TSEEdge edge = (TSEEdge)graph.addEdge(startNode, endNode);
		// EdgeCreator.decorateEdge(edge);
		return edge;
	}

	private TSEEdge createSeqFlowEdgeInSubprocess(TSEGraph graph, TSENode startNode, TSENode endNode) {
		final EObject laneObj = (EObject) graph.getUserObject();
		final EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		final EObject processObj = (EObject) graph.getUserObject();
		final EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(processObj);

		final EObjectWrapper<EClass, EObject> start = EObjectWrapper.wrap((EObject) startNode.getUserObject());
		final EObjectWrapper<EClass, EObject> end = EObjectWrapper.wrap((EObject) endNode.getUserObject());
		EObjectWrapper<EClass, EObject> sequenceFlow = getModelController().createSequenceFlow(start, end, process, lane);

		@SuppressWarnings("unused")
		String id = sequenceFlow.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		String label = sequenceFlow.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		AbstractEdgeUIFactory uiFactory = BpmnGraphUIFactory.getInstance((BpmnLayoutManager) getLayoutManager()).getEdgeUIFactory(label, SEQUENCE_FLOW);
		TSEEdge edge = uiFactory.addEdge(graph, startNode, endNode);
		uiFactory.addEdgeLabel(edge, label);
		edge.setUserObject(sequenceFlow.getEInstance());
		// TSEEdge edge = (TSEEdge)graph.addEdge(startNode, endNode);
		// EdgeCreator.decorateEdge(edge);
		return edge;
	}

	private TSENode createStartEvent(TSEGraph graph, String name, String toolId,String attachedResource) {
		final EObject laneObj = (EObject) graph.getUserObject();
		final EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);

		final EObject processObj = (EObject) graph.getGreatestAncestor().getUserObject();
		final EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(processObj);

		EObjectWrapper<EClass, EObject> eventWrapper = getModelController().createEvent(name, toolId, START_EVENT, MESSAGE_EVENT_DEFINITION, process, lane);
		setAttachedResourceAttribute(eventWrapper,attachedResource);
		// EObjectWrapper<EClass, EObject> eventWrapper =
		// EObjectWrapper.createInstance(BpmnMetaModelConstants.START_EVENT);
		// eventWrapper.setAttribute("id", GUIDGenerator.getGUID());
		// eventWrapper.setAttribute("name", name);
		// ((EList)eventWrapper.getAttribute("lanes")).add(laneObj);
		// ((EList<EObject>)process.getAttribute("flowElements")).add(eventWrapper.getEInstance());
		AbstractNodeUIFactory uiFactory = BpmnGraphUIFactory.getInstance((BpmnLayoutManager) getLayoutManager()).getNodeUIFactory(name, "", toolId,
				START_EVENT, BpmnMetaModel.MESSAGE_EVENT_DEFINITION);
		TSENode tsNode = uiFactory.addNode(graph);
		tsNode.setUserObject(eventWrapper.getEInstance());
		uiFactory.updateNodeToolTip(tsNode);
		String label = eventWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		uiFactory.addNodeLabel(tsNode, label);
		// TSENode tsNode = (TSENode) graph.addNode();
		// tsNode.setUserObject(eventWrapper.getEInstance());
		// tsNode.setSize(80, 75);
		// tsNode.setTag(name);
		// ActivityBadgeNodeUI badgeUI = new
		// ActivityBadgeNodeUI(ActivityBadgeNodeUI.TYPE_RF);
		// badgeUI.setGradient(BpmnGraphConstants.BADGE_GRADIENT_START_COLOR,
		// BpmnGraphConstants.BADGE_GRADIENT_END_COLOR);
		// // badgeUI.setGradient(RoundRectNodeUI.ERROR_START_COLOR,
		// RoundRectNodeUI.ERROR_END_COLOR);
		// tsNode.setUI(badgeUI);
		// StartNodeCreator.decorateStartNode(tsNode,
		// name,
		// BpmnMetaModel.getInstance().getEClass(BpmnMetaModel.START_EVENT));
		// this.layoutManager.setNodeLabelOptions((TSENodeLabel)
		// tsNode.labels().get(0));
		return tsNode;
	}

	/*
	 *Attaching attachedResourceAttribute to the model 
	 */

	private void setAttachedResourceAttribute(EObjectWrapper<EClass, EObject> eventWrapper,String attachedResource){
		if (attachedResource != null && !attachedResource.trim().isEmpty()) {
			DesignerElement element = IndexUtils.getElement(fProject.getName(),
					attachedResource);
			if (element != null)
				BpmnModelUtils.setResourceAttr(eventWrapper.getEInstance(),
						attachedResource);
		}
	}

	private TSENode createStartEventInSubprocess(TSEGraph graph, String name, String toolId) {
		final EObject laneObj = (EObject) graph.getUserObject();
		final EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);

		final EObject processObj = (EObject) graph.getUserObject();
		final EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(processObj);

		EObjectWrapper<EClass, EObject> eventWrapper = getModelController().createEvent(name, toolId, START_EVENT, null, process, lane);
		AbstractNodeUIFactory uiFactory = BpmnGraphUIFactory.getInstance((BpmnLayoutManager) getLayoutManager()).getNodeUIFactory(name, "", toolId,
				START_EVENT);
		TSENode tsNode = uiFactory.addNode(graph);
		tsNode.setUserObject(eventWrapper.getEInstance());
		uiFactory.updateNodeToolTip(tsNode);
		String label = eventWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		uiFactory.addNodeLabel(tsNode, label);
		//fixing defect BE-17439
		//		boolean showBreakpoints = prefStore.getBoolean(BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS);
		//		if (tsNode.getUI() instanceof ShapeNodeUI) {
		//			ShapeNodeUI nodeUI = (ShapeNodeUI) tsNode.getUI();
		//				nodeUI.setShowBreakPoints(showBreakpoints);
		//		}
		return tsNode;
	}

	@SuppressWarnings("deprecation")
	private TSENode createTempActivity(TSEGraph graph, String label) {
		TSENode tsNode = (TSENode) graph.addNode();
		tsNode.setSize(80, 75);
		tsNode.setTag(label);
		ActivityBadgeNodeUI badgeUI = new ActivityBadgeNodeUI(ActivityBadgeNodeUI.TYPE_RF);
		badgeUI.setGradient(BpmnUIConstants.BADGE_GRADIENT_START_COLOR, BpmnUIConstants.BADGE_GRADIENT_END_COLOR);
		// badgeUI.setGradient(RoundRectNodeUI.ERROR_START_COLOR,
		// RoundRectNodeUI.ERROR_END_COLOR);
		tsNode.setUI(badgeUI);
		return tsNode;
	}

	//	@SuppressWarnings("unused")
	//	private TSENode createWebActivityNode(String name, int activityType, boolean isError) {
	//		TSENode tsNode = (TSENode) graphManager.getMainDisplayGraph().addNode();
	//		tsNode.setSize(80, 75);
	//		tsNode.setTag(name);
	//		RoundRectNodeUI badgeUI = null;
	//
	//		switch (activityType) {
	//		case ACTIVITY_RF: {
	//			badgeUI = new ActivityBadgeNodeUI(ActivityBadgeNodeUI.TYPE_RF);
	//			break;
	//		}
	//		case ACTIVITY_TABLE: {
	//			badgeUI = new ActivityBadgeNodeUI(ActivityBadgeNodeUI.TYPE_TABLE);
	//			break;
	//		}
	//		case ACTIVITY_JAVA: {
	//			badgeUI = new ActivityBadgeNodeUI(ActivityBadgeNodeUI.TYPE_JAVA);
	//			break;
	//		}
	//		case ACTIVITY_GENERIC: {
	//			badgeUI = new SimpleActivityBadgeNodeUI(SimpleActivityBadgeNodeUI.TYPE_NONE);
	//			break;
	//		}
	//		default: {
	//			// throw new UnsupportedOperationException("Activity Type " +
	//			// activityType + " not supported yet");
	//			// Do nothing
	//			break;
	//		}
	//		}
	//
	//		if (badgeUI != null) {
	//			if (isError) {
	//				badgeUI.setGradient(RoundRectNodeUI.ERROR_START_COLOR, RoundRectNodeUI.ERROR_END_COLOR);
	//			}
	//			tsNode.setUI(badgeUI);
	//		}
	//
	//		return tsNode;
	//	}

	//	@SuppressWarnings("unused")
	//	private TSENode createWebEndNode(String name) {
	//		FinalStateNodeUI finalUI = new FinalStateNodeUI();
	//		TSENode finalNode = (TSENode) graphManager.getMainDisplayGraph().addNode();
	//		finalNode.setSize(60, 60);
	//		if (name != null) {
	//			finalNode.addLabel().setText(name);
	//		}
	//		finalNode.setShape(TSOvalShape.getInstance());
	//		finalNode.setUI((TSEObjectUI) finalUI.clone());
	//		return finalNode;
	//	}

	//	@SuppressWarnings("unused")
	//	private TSENode createWebGatewayNode(String name, int type) {
	//		TSENode tsNode = (TSENode) this.graphManager.getMainDisplayGraph().addNode();
	//		tsNode.setShape(TSPolygonShape.fromString("[ 4 (50, 0) (100, 50) (50, 100) (0, 50) ]"));
	//		tsNode.setUI((TSEObjectUI) new DecisionNodeUI());
	//
	//		if (name != null) {
	//			tsNode.addLabel().setText(name);
	//		}
	//		tsNode.setSize(60, 60);
	//
	//		switch (type) {
	//
	//		case OR:
	//			tsNode.setTag("OR");
	//			break;
	//		case XOR:
	//			tsNode.setTag("XOR");
	//			break;
	//		case AND:
	//			tsNode.setTag("AND");
	//			break;
	//		default:
	//			throw new UnsupportedOperationException("Not supported Gateway type");
	//		}
	//
	//		return tsNode;
	//	}

	// ///////////////////////////////////////////////////////////////////////////////////////////
	// Ensure triggering nodes are on the left side, not entirely complete yet.
	// TODO: handle all subprocesses as well, not just main one.
	// ///////////////////////////////////////////////////////////////////////////////////////////

	//	@SuppressWarnings("unused")
	//	private TSEEdge createWebLink(String name, TSENode srcNode, TSENode tgtNode) {
	//		TSEEdge edge = (TSEEdge) graphManager.addEdge(srcNode, tgtNode);
	//		if (name != null) {
	//			((TSEEdgeLabel) edge.addLabel()).setTag(name);
	//		}
	//		TSECurvedEdgeUI edgeUI = new TSECurvedEdgeUI();
	//		edgeUI.setAntiAliasingEnabled(true);
	//		edgeUI.setCurvature(100);
	//		edge.setUI(edgeUI);
	//		// TODO: if tgtNode is of error type, make link red...
	//		return edge;
	//	}
	//	@SuppressWarnings("unused")
	//	private TSENode createWebStartNode(String name) {
	//		InitialNodeUI initUI = new InitialNodeUI();
	//		TSENode startNode = (TSENode) graphManager.getMainDisplayGraph().addNode();
	//		if (name != null) {
	//			startNode.addLabel().setText(name);
	//		}
	//		startNode.setSize(60, 60);
	//		startNode.setShape(TSOvalShape.getInstance());
	//		startNode.setUI((TSEObjectUI) initUI.clone());
	//		return startNode;
	//	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#cutGraph()
	 */
	public void cutGraph() {
		if (!getBpmnGraphEditor().isEnabled()) {
			return;
		}
		super.cutGraph();
		BpmnGraphUtils.editGraph(EDIT_TYPES.CUT, getEditor().getEditorSite(), this);
	}
	@Override
	public void delete() {
		if (!getBpmnGraphEditor().isEnabled()) {
			return;
		}
		BpmnGraphUtils.editGraph(EDIT_TYPES.DELETE, getEditor().getEditorSite(), this);
		modelGraphFactory.buildNodeRegistry();
	}
	public List<TSENode> findNodesOfType(TSEGraph graph, EClass type) {
		List<TSENode> startNodes = new LinkedList<TSENode>();
		Object userObj;
		@SuppressWarnings("unchecked")
		List<TSENode> nodes = graph.nodes();
		for (TSENode node : nodes) {
			userObj = node.getUserObject();
			if (userObj instanceof EObject) {
				if (type.isSuperTypeOf(((EObject) userObj).eClass())) {
					startNodes.add(node);
				}
			}
			TSGraph child = node.getChildGraph();
			if(child != null) {
				List<TSENode> clist = findNodesOfType((TSEGraph) child, type);
				startNodes.addAll(clist);
			}
		}

		return startNodes;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == IEditorPart.class) {
			return this.editor;
		} 
		return super.getAdapter(adapter);
	}



	public BpmnEditorInput getBpmnEditorInput() {
		return bpmnEditorInput;
	}

	public BpmnEditor getBpmnGraphEditor() {
		return (BpmnEditor) editor;
	}

	public ModelController getController() {
		return modelController;
	}

	@Override
	public DiagramChangeListener<? extends DiagramManager> getDiagramChangeListener() {
		if (this.graphChangeListener == null) {
			this.graphChangeListener = new GraphChangeListener(this, modelGraphFactory);
		}
		return this.graphChangeListener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.diagramming.drawing.DiagramManager#getDiagramSelectionListener
	 * ()
	 */
	@Override
	public SelectionChangeListener getDiagramSelectionListener() {
		if (this.graphSelectionListener == null) {
			this.graphSelectionListener = new BpmnGraphSelectionListener(this);
		}
		return this.graphSelectionListener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getEdgeTool()
	 */
	@Override
	public BpmnCreateEdgeTool getEdgeTool() {
		if (this.graphCreateEdgeTool == null) {
			this.graphCreateEdgeTool = new BpmnCreateEdgeTool(this, ((BpmnEditor) editor).isEnabled());
		}
		return this.graphCreateEdgeTool;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#getEditor()
	 */
	@Override
	public BpmnEditor getEditor() {
		return getBpmnGraphEditor();
	}

	public Object getDiagramEditor() {
		return getBpmnGraphEditor();
	}

	public Object getDiagramEditorSite() {
		return getBpmnGraphEditor().getEditorSite();
	}

	@Override
	public TSEEditTextTool getEditTextTool() {
		if (this.editTextTool == null) {
			this.editTextTool = new BpmnEditTextTool(this, ((BpmnEditor) editor).isEnabled());
		}
		return this.editTextTool;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////
	// End triggering node separation constraint related
	// ///////////////////////////////////////////////////////////////////////////////////////

	NodeBreakpointInfo getFlowNodeBreakPointInfo(int uniqueId, IProcessBreakpoint.TASK_BREAKPOINT_LOCATION loc) {
		Map<Integer, NodeBreakpointInfo> nodeMap = new HashMap<Integer, NodeBreakpointInfo>();
		List<TSENode> nodes = findNodesOfType((TSEGraph) this.graphManager.getMainDisplayGraph(), BpmnModelClass.FLOW_NODE);
		String resourceURI = getBpmnGraphEditor().getFile().getFullPath().removeFileExtension().removeFirstSegments(1).makeAbsolute().toPortableString();
		//		final int numNodes = nodes.size();
		for (TSENode node : nodes) {
			EObject eObj = (EObject) node.getUserObject();
			EObjectWrapper<EClass, EObject> eObjWrapper = EObjectWrapper.wrap(eObj);
			int uId =  eObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
			if(uId== uniqueId){
				String nodeId = (String) eObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				String nodeTaskType = BpmnMetaModel.getMetaModelNsRegistry().inverseMap().get(eObj.eClass()).toString();
				ProcessBreakpointInfo bpInfo = new ProcessBreakpointInfo(resourceURI, nodeId, nodeTaskType, loc, uniqueId);
				NodeBreakpointInfo nbpInfo = new NodeBreakpointInfo(bpInfo,node);
				return nbpInfo;
			}
		}
		return null;
	}

	public LayoutManager getLayoutManager() {
		if (this.layoutManager == null) {
			this.layoutManager = new BpmnLayoutManager(this);
		}
		return this.layoutManager;
	}

	public ModelChangeAdapterFactory getModelChangeAdapterFactory() {
		return modelChangeAdapterFactory;
	}

	// private static boolean isFirst = true;

	// private TSENode createPoolLaneNode(TSGraphObject tsGraphObject,String
	// name) {
	// if(tsGraphObject instanceof TSGraph) { // process
	// return createPoolLaneNode((TSGraph) tsGraphObject, name, false);
	// } else if (tsGraphObject instanceof TSENode) {
	// return
	// createPoolLaneNode(((TSENode)tsGraphObject).getChildGraph(),name,true);
	// }
	// return null;
	// }
	//
	// private TSENode createPoolLaneNode(TSGraph parentGraph, String name,
	// boolean isLane) {
	// EObjectWrapper<EClass, EObject> parentLaneSet = null;
	// EObject pObj = (EObject) parentGraph.getUserObject();
	//
	// if(EObjectWrapper.useInstance(pObj).isInstanceOf(BpmnMetaModelConstants.PROCESS))
	// {
	// EObjectWrapper<EClass, EObject> processWrapper =
	// EObjectWrapper.useInstance((EObject)parentGraph.getUserObject());
	// EList<EObject>laneSets = (EList<EObject>)
	// processWrapper.getAttribute("laneSets");
	// if(laneSets.size() == 0) {
	// parentLaneSet = getModelController().createLaneSet(name,null);
	// laneSets.add(parentLaneSet.getEInstance());
	// } else {
	// parentLaneSet = EObjectWrapper.useInstance(laneSets.get(0));
	// }
	//
	// } else
	// if(EObjectWrapper.useInstance(pObj).isInstanceOf(BpmnMetaModelConstants.LANE))
	// {
	// EObjectWrapper<EClass, EObject> lane = EObjectWrapper.useInstance(pObj);
	// EObject childLaneSet = (EObject) lane.getAttribute("childLaneSet");
	// if(childLaneSet == null) {
	// parentLaneSet = getModelController().createLaneSet(name,lane);
	// lane.setAttribute("childLaneSet", parentLaneSet.getEInstance());
	// } else {
	// parentLaneSet = EObjectWrapper.useInstance(childLaneSet);
	// }
	// }
	//
	//
	// TSENode laneNode = (TSENode) parentGraph.addNode();
	// laneNode.setTag(name);
	// EObjectWrapper<EClass, EObject> laneWrapper =
	// getModelController().createLane(name,parentLaneSet);
	// laneNode.setUserObject(laneWrapper.getEInstance());
	//
	// TSEGraph laneGraph = (TSEGraph) this.graphManager.addGraph();
	// laneGraph.setUserObject(laneWrapper.getEInstance());
	// ((BpmnGraphLayoutManager)
	// this.getLayoutManager()).setLayoutOptionsForSubProcess(laneGraph);
	// laneNode.setChildGraph(laneGraph);
	// TSENestingManager.expand(laneNode);
	//
	// LaneChildGraphNodeUI ui = new LaneChildGraphNodeUI();
	// ui.setOuterRoundRect(true);
	// ui.setFillColor(BpmnGraphConstants.LANE_FILL_COLOR);
	// ui.setBorderDrawn(true);
	// ui.setDrawChildGraphMark(false);
	// ui.setIsLane(isLane);
	// ui.isFirst = isFirst;
	// laneNode.setUI(ui);
	//
	// ((BpmnGraphLayoutManager)
	// this.getLayoutManager()).setLayoutOptionsForLane(laneNode);
	//
	// if (isLane) {
	// isFirst = false;
	// }
	//
	// return laneNode;
	// }

	// private TSENode createPoolLaneNode(TSGraph parentGraph, String name,
	// boolean isLane) {
	// EObjectWrapper<EClass, EObject> parentLaneSet = null;
	// EObject pObj = (EObject) parentGraph.getUserObject();
	//
	// if(EObjectWrapper.useInstance(pObj).isInstanceOf(BpmnMetaModelConstants.PROCESS))
	// {
	// EObjectWrapper<EClass, EObject> processWrapper =
	// EObjectWrapper.useInstance((EObject)parentGraph.getUserObject());
	// EList<EObject>laneSets = (EList<EObject>)
	// processWrapper.getAttribute("laneSets");
	// if(laneSets.size() == 0) {
	// parentLaneSet = getModelController().createLaneSet(name,null);
	// laneSets.add(parentLaneSet.getEInstance());
	// } else {
	// parentLaneSet = EObjectWrapper.useInstance(laneSets.get(0));
	// }
	//
	// } else
	// if(EObjectWrapper.useInstance(pObj).isInstanceOf(BpmnMetaModelConstants.LANE))
	// {
	// EObjectWrapper<EClass, EObject> lane = EObjectWrapper.useInstance(pObj);
	// EObject childLaneSet = (EObject) lane.getAttribute("childLaneSet");
	// if(childLaneSet == null) {
	// parentLaneSet = getModelController().createLaneSet(name,lane);
	// lane.setAttribute("childLaneSet", parentLaneSet.getEInstance());
	// } else {
	// parentLaneSet = EObjectWrapper.useInstance(childLaneSet);
	// }
	// }
	//
	//
	// TSENode laneNode = (TSENode) parentGraph.addNode();
	// laneNode.setTag(name);
	// EObjectWrapper<EClass, EObject> laneWrapper =
	// getModelController().createLane(name,parentLaneSet);
	// laneNode.setUserObject(laneWrapper.getEInstance());
	//
	// TSEGraph laneGraph = (TSEGraph) this.graphManager.addGraph();
	// laneGraph.setUserObject(laneWrapper.getEInstance());
	// ((BpmnGraphLayoutManager)
	// this.getLayoutManager()).setLayoutOptionsForSubProcess(laneGraph);
	// laneNode.setChildGraph(laneGraph);
	// TSENestingManager.expand(laneNode);
	//
	// PoolLaneAbstractNodeUI ui = null;
	// if (isLane) {
	// ui = new LaneNodeUI();
	// }
	// else {
	// ui = new PoolNodeUI();
	// }
	// ui.setOuterRoundRect(true);
	// ui.setFillColor(BpmnGraphConstants.LANE_FILL_COLOR);
	// ui.setBorderDrawn(true);
	// ui.setDrawChildGraphMark(false);
	// // ui.isFirst = isFirst;
	// laneNode.setUI(ui);
	//
	// ((BpmnGraphLayoutManager)
	// this.getLayoutManager()).setLayoutOptionsForLane(laneNode);
	//
	// if (isLane) {
	// isFirst = false;
	// }
	//
	// return laneNode;
	// }

	@Override
	public ModelController getModelController() {
		return modelController;
	}

	public ModelGraphFactory getModelGraphFactory() {
		return modelGraphFactory;
	}

	@Override
	public TSECreateNodeTool getNodeTool() {
		if (this.graphCreateNodeTool == null) {
			this.graphCreateNodeTool = new BpmnCreateNodeTool(this, ((BpmnEditor) editor).isEnabled());
		}
		return this.graphCreateNodeTool;
	}

	@Override
	public TSEPasteTool getPasteTool() {
		if (bpmnPasteTool == null) {
			bpmnPasteTool = new BpmnPasteTool(this);
		}
		return bpmnPasteTool;
	}

	public IProject getProject() {
		return ((BpmnEditorInput) getEditor().getEditorInput()).getFile().getProject();
	}

	@Override
	public ReconnectEdgeTool getReconnectEdgeTool() {
		if (this.reconnectEdgeTool == null) {
			this.reconnectEdgeTool = new BpmnReconnectEdgeTool(this);
		}
		return this.reconnectEdgeTool;
	}

	public List<TSEConnector> getSelectedConnectors() {
		selectedConnectors.clear();
		for (Object obj : getGraphManager().selectedConnectors()) {
			TSEConnector connector = (TSEConnector) obj;
			selectedConnectors.add(connector);
		}
		return selectedConnectors;
	}

	@Override
	public SelectTool getSelectTool() {
		if (this.graphSelectTool == null) {
			this.graphSelectTool = new BpmnSelectTool(this);
		}
		return this.graphSelectTool;
	}

	@Override
	public TSETransferSelectedTool getTransferSelectedTool() {
		if (this.transferSelectedTool == null) {
			this.transferSelectedTool = new BpmnTransferSelectedTool(this, ((BpmnEditor) editor).isEnabled());
		}
		return this.transferSelectedTool;
	}

	public void init() {
		if (editor.getEditorInput() != null && editor.getEditorInput() instanceof BpmnEditorInput) {
			this.bpmnEditorInput = (BpmnEditorInput) editor.getEditorInput();
			this.modelChangeAdapterFactory = new ModelChangeAdapterFactory(this);
			this.modelController = new ModelController(bpmnEditorInput.getProcessModel(), this.modelChangeAdapterFactory, this);
		}
		BpmnUIPlugin bpmnPlugin = BpmnUIPlugin.getDefault();
		if (bpmnPlugin != null)
			this.prefStore = bpmnPlugin.getPreferenceStore();
		// TODO: move to drawing code:

		// this.expandSubProcesses =
		// prefStore.getBoolean(BpmnPreferenceConstants.PREF_EXPAND_SUBPROCESS);
	}


	/**
	 * initialize the model graph factory
	 */
	public void initializeModel() {
		TSEImage.setLoaderClass(this.getClass());
		if (modelController == null) {
			modelController = new ModelController(getModelChangeAdapterFactory());
		}
		if (modelGraphFactory == null) {
			modelGraphFactory = new ModelGraphFactory(this, modelController);
		}
	}

	public void initializeWeb() {
		setWeb(true);
		TSEImage.setLoaderClass(this.getClass());
		if (modelChangeAdapterFactory == null) {
			modelChangeAdapterFactory = new ModelChangeAdapterFactory(this);
		}
		initializeModel();

	}

	private void insertBendPoints(TSEEdge edge) {
		removeBendPoints(edge);
		EObject userObject = (EObject) edge.getUserObject();
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> sequence = EObjectWrapper.wrap(userObject);

			if (ExtensionHelper.isValidDataExtensionAttribute(sequence, BpmnMetaModelExtensionConstants.E_ATTR_BEND_POINTS)) {
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(sequence);
				EList<EObject> listAttribute = valueWrapper.getListAttribute((BpmnMetaModelExtensionConstants.E_ATTR_BEND_POINTS));
				for (EObject eObject : listAttribute) {
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eObject);
					Double x = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X);
					Double y = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y);
					TSConstPoint tsConstPoint = new TSConstPoint(x, y);
					if (x != null && y != null) {
						Iterator<?> pathIterator = edge.pathIterator();
						TSPEdge path = null;
						while (pathIterator.hasNext())
							path = (TSPEdge) pathIterator.next();
						if (path != null) {
							edge.addPathNode(path, tsConstPoint);
						}
					}
				}
			}

			if (ExtensionHelper.isValidDataExtensionAttribute(sequence, BpmnMetaModelExtensionConstants.E_ATTR_START_POINT)) {
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(sequence);
				EObject startPoint = valueWrapper.getAttribute((BpmnMetaModelExtensionConstants.E_ATTR_START_POINT));
				if (startPoint != null) {
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(startPoint);
					Double x = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X);
					Double y = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y);
					TSConstPoint tsConstPoint = new TSConstPoint(x, y);
					if (x != null && y != null) {
						edge.setSourceClipping(tsConstPoint, false);
					}
				}

			}

			if (ExtensionHelper.isValidDataExtensionAttribute(sequence, BpmnMetaModelExtensionConstants.E_ATTR_END_POINT)) {
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(sequence);
				EObject endPoint = valueWrapper.getAttribute((BpmnMetaModelExtensionConstants.E_ATTR_END_POINT));
				if (endPoint != null) {
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(endPoint);
					Double x = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X);
					Double y = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y);
					TSConstPoint tsConstPoint = new TSConstPoint(x, y);
					if (x != null && y != null) {
						edge.setTargetClipping(tsConstPoint, false);
					}
				}

			}
		}
	}

	private boolean isBpmnNode(TSENode node) {
		EClass nodeType = (EClass) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		return nodeType != null;
	}

	public boolean isLoading() {
		return isLoading;
	}

	private boolean isNodeOfType(TSENode node, EClass type) {
		if (node.getUserObject() != null && node.getUserObject() instanceof EObject && type.isSuperTypeOf(((EObject) node.getUserObject()).eClass())) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#isValidModelFile()
	 */
	protected boolean isValidModelFile() {
		if (bpmnEditorInput != null) {
			return bpmnEditorInput.getFile().isAccessible();
		} else {
			return false;
		}
	}
	private void loadBpmnModel(IProgressMonitor pm) throws Exception {
		try {
			this.isLoading = true;
			pm.beginTask(Messages.getString(BpmnUIConstants.BPMN_MODEL_LOAD_INITIAL), 10);
			pm.subTask(Messages.getString(BpmnUIConstants.BPMN_MODEL_INIT_GRAPH));
			getGraphManager().emptyTopology();
			initializeModel();
			pm.worked(1);
			pm.beginTask(Messages.getString(BpmnUIConstants.BPMN_MODEL_LOAD_PROCESS), 8);
			EObjectWrapper<EClass, EObject> modelRoot = getBpmnEditorInput().getProcessModel();
			getModelChangeAdapterFactory().adapt(modelRoot, ModelChangeListener.class);
			getModelGraphFactory().loadProcess(modelRoot, null, new SubProgressMonitor(pm, 8));

			if ( BpmnUIPlugin.getDefault().getPreferenceStore().getBoolean(BpmnPreferenceConstants.PREF_ALIGN_TO_LEFT_TRIGGERING_NODES)) {
				setSeparationConstraint();
			}
			getLayoutManager().callBatchGlobalLayout();
			getLayoutManager().callBatchLabeling();
			pm.worked(8);
		} finally {
			this.isLoading = false;
			pm.done();
		}
	}
	public void refreshNodesForIconAndColor() {
		for (TSEObject object : getModelGraphFactory().getNodeRegistry()
				.getAllNodes()) {
			if (object instanceof TSENode) {
				refreshNodeForIconAndColor((TSENode)object);
			}
		}
	}

	public void refreshNodeForIconAndColor(TSENode node){

		boolean showTaskIcons = prefStore
				.getBoolean(BpmnPreferenceConstants.PREF_SHOW_TASK_ICONS);
		EClass nodeType = (EClass) node
				.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		EClass nodeExtType =(EClass) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
		EObject userObject = (EObject) node.getUserObject();
		if(userObject == null)
			return;
		EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
				.wrap(userObject);
		// BpmnUIPlugin plugInstance = BpmnUIPlugin.getDefault();
		fProject = bpmnEditorInput.getFile().getProject();
		BpmnPaletteModel _toolDefinition = PaletteLoader
				.getBpmnPaletteModel(fProject);
		TSEImage image = null;
		BpmnPaletteGroupItem item = null;
		if (_toolDefinition != null) {
			String id = (String) ExtensionHelper
					.getExtensionAttributeValue(
							userObjWrapper,
							BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);

			if (id != null && !id.isEmpty()) {
				item = _toolDefinition.getPaletteItemById(id);
			}

			if(item == null){
				try {
					BpmnPaletteModel loadDefault = PaletteLoader.loadDefault();
					item = loadDefault.getPaletteItemById(id);
				} catch (Exception e) {
				}
			}

			if (item == null) {
				List<BpmnPaletteGroupItem> paletteToolByType = _toolDefinition
						.getPaletteToolItemByType(nodeType, nodeExtType);
				if (paletteToolByType.size() > 0)
					item = paletteToolByType.get(0);
			}

			if (item != null) {
				String imageSizePref = BpmnUIPlugin.getDefault().getPreferenceStore().getString(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS);
				IMAGE_SIZE imageSize = IMAGE_SIZE.get(imageSizePref);
				String imagePath =  null;
				if (new File(item.getIcon()).exists()) {
						imagePath = item.getIcon();
						image = new TSEImage(TaskNodeUI.class, imagePath);
						image.setImageType(item.getIcon());
				} else {
					imagePath = BpmnImages.getInstance()
							.getImagePath(item.getIcon(), imageSize);
					if (imagePath != null) {
						if (imagePath.trim().isEmpty())
							imagePath = item.getIcon();
						image = new TSEImage(TaskNodeUI.class, imagePath);
						image.setImageType(item.getIcon());
					}
				}
			}
		}
		
		if (node.getUI() instanceof TaskNodeUI) {
			TaskNodeUI nodeUI = (TaskNodeUI) node.getUI();
			if(showTaskIcons){
				TaskNodeUI.setSHOW_TASK_ICONS(true);
				if(image != null)
					nodeUI.setBadgeImage(image);
			}

			if(item != null){
				nodeUI.color = item.getColor();
				nodeUI.setNodeColor();
			}

		}else if (node.getUI() instanceof BPMNCallActivityNodeUI ){

			BPMNCallActivityNodeUI nodeUI = (BPMNCallActivityNodeUI) node.getUI();
			if(showTaskIcons && image != null)
				nodeUI.setBadgeImage(image);

			if(item != null){
				nodeUI.color = item.getColor();
				nodeUI.setNodeColor();
			}
		} else if (node.getUI() instanceof BPMNCollapsedSubprocessNodeUI ){

			BPMNCollapsedSubprocessNodeUI nodeUI = (BPMNCollapsedSubprocessNodeUI) node.getUI();


			boolean isSubProcessTriggerByEvent = false;
			TSEImage badgeimage = null;
			if (BpmnIndexUtils.isSubProcessTriggerByEvent(userObject)) {
				String badgeimagePath = BpmnImages.getInstance().getImagePath(
						BPMNCommonImages.MESSAGE_SUBPROCESS_NON_INTERRUPTING);
				if (badgeimagePath != null) {
					if (badgeimagePath.trim().isEmpty())
						badgeimagePath = item.getIcon();
					badgeimage = new TSEImage(TaskNodeUI.class,
							badgeimagePath);
					if (badgeimage != null) {
						isSubProcessTriggerByEvent = true;
					}
				}
			}
			if(showTaskIcons && isSubProcessTriggerByEvent ){
				nodeUI.setBadgeImage(badgeimage);
			}

			else if(showTaskIcons && image != null)
				nodeUI.setBadgeImage(image);

			if(item != null ){
				nodeUI.color = item.getColor();
				nodeUI.setNodeColor();
			}
		}else if (node.getUI() instanceof BPMNExpandedSubprocessNodeUI ){

			BPMNExpandedSubprocessNodeUI nodeUI = (BPMNExpandedSubprocessNodeUI) node.getUI();

			if(item != null){
				nodeUI.color = item.getColor();
				nodeUI.setNodeColor();
			}
		}else if (node.getUI() instanceof ShapeNodeUI){

			ShapeNodeUI nodeUI = (ShapeNodeUI) node.getUI();

			if(item != null){
				nodeUI.color = item.getColor();
				nodeUI.setNodeColor();
			}
		} 
		DiagramUtils
		.refreshDiagram((BpmnDiagramManager) (((BpmnEditor) editor)
				.getDiagramManager()));
		OverviewUtils.refreshOverview(getEditor().getEditorSite(),
				true, true);
	}

	/**
	 * Loading Existing Breakpoints and show them based on preference
	 */
	public void loadBreakpoints(IAnnotationModel graphAnnotationModel) {
		boolean showBreakpoints = prefStore.getBoolean(BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS);
		cacheAnnotations();
		if (graphAnnotationModel != null) {
			Set<String> startBreakPointNodeset = new HashSet<String>();
			Set<String> endBreakPointNodeset = new HashSet<String>();
			for(Annotation a: fCachedAnnotations) {			
				final String type = a.getType();
				if (type.equals(PROCESS_BREAKPOINT_MARKER_ANNOTATION)) {
					Position pos = graphAnnotationModel.getPosition(a);
					if(pos instanceof GraphPosition){
						GraphPosition position = (GraphPosition)pos;
						NodeBreakpointInfo nbpinfo = getFlowNodeBreakPointInfo(position.getOffset(), position.getNodeInfo().getLocation());
						if (nbpinfo.getNode() != null) {
							if (nbpinfo.getBpInfo().getLocation() == IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.START) {
								startBreakPointNodeset.add(nbpinfo.getBpInfo()
										.getGraphObjectId());
							} else {
								endBreakPointNodeset.add(nbpinfo.getBpInfo()
										.getGraphObjectId());
							}
							DiagramUtils
							.refreshDiagram((BpmnDiagramManager) (((BpmnEditor) editor)
									.getDiagramManager()));
							OverviewUtils.refreshOverview(getEditor()
									.getEditorSite(), true, true);
						}
					}
				} 
			}
			for (TSEObject object : getModelGraphFactory().getNodeRegistry().getAllNodes()) {
				if (object instanceof TSENode && object.getUI() instanceof ShapeNodeUI) {
					TSENode node = (TSENode) object;
					EObject userObject = (EObject) node.getUserObject();
					EObjectWrapper<EClass, EObject> userObjectWrapper = EObjectWrapper.wrap( userObject ) ;
					ShapeNodeUI nodeUI = (ShapeNodeUI) object.getUI();
					// remove breakpoint from subprocess start and end node
					if( isSubprocessStartEndNode(node) ){
						nodeUI.setShowBreakPoints(false);
						continue;
					}
					EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
					String nodeId = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);

					nodeUI.setShowBreakPoints(showBreakpoints);
					if (startBreakPointNodeset.contains(nodeId)) {
						nodeUI.setInputBreakpoint(true);
						nodeUI.setInputBreakPointToggle(true);
					}
					if (endBreakPointNodeset.contains(nodeId)) {
						nodeUI.setOutputBreakpoint(true);
						nodeUI.setOutputBreakPointToggle(true);
					}
				}
			}
			DiagramUtils.refreshDiagram((BpmnDiagramManager) (((BpmnEditor) editor).getDiagramManager()));
			OverviewUtils.refreshOverview(getEditor().getEditorSite(), true, true);

		} 
	}
	private Boolean isSubprocessStartEndNode(TSENode node){
		try {
			EObject userObject = (EObject) node.getUserObject();
			EObject eobjP = BpmnModelUtils.getFlowElementContainer(userObject) ;
			EObjectWrapper<EClass, EObject> userObjectWrapper = EObjectWrapper.wrap( userObject ) ;
			EObjectWrapper<EClass, EObject> userObjWrapperDef = 
					EObjectWrapper.wrap((EObject)node.getUserObject());
			EList<EObject> listAttribute = null ;
			if (userObjWrapperDef
					.containsAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS)) {
				listAttribute = userObjWrapperDef
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
			}
			if (eobjP !=null && BpmnModelClass.SUB_PROCESS.isSuperTypeOf(eobjP.eClass() ) && 
					( userObjectWrapper.isInstanceOf(START_EVENT) || userObjectWrapper.isInstanceOf(END_EVENT) ) 
					&& listAttribute != null && listAttribute.size() == 0 ) {
				return true ;
			} 
		}catch (Exception e ) {
			System.out.println("Exception while setting breakpoint in subprocess Node");
			return false;
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.decision.graph.model.controller.ModelChangeListener#
	 * modelChanged
	 * (com.tibco.cep.decision.graph.model.controller.ModelChangeEvent)
	 */
	public void modelChanged(ModelChangeEvent mce) {
		if (!this.isLoading) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					getBpmnGraphEditor().modified();
				}
			});
		}
	}

	public void onEdgeMoved(TSEEdge edge) {
		Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateMap = new HashMap<EObjectWrapper<EClass, EObject>, Map<String, Object>>();
		updateEdgeBendPoints(edge, updateMap);
		updateModel(updateMap);
	}

	@Override
	public void onLayout(final TSLayoutEvent layoutEvent) {
		// super.onLayout(layoutEvent);
		TSLayoutEventData layoutEventData = layoutEvent.getLayoutEventData();
		if (layoutEventData.getType() == TSLayoutEvent.POST_LAYOUT) {
			Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateMap = new HashMap<EObjectWrapper<EClass, EObject>, Map<String, Object>>();
			updateGraphElements(graphManager.getMainDisplayGraph(), updateMap);
			if (updateMap.size() == 0)
				return;
			TSCommand cmd = new EmfModelPropertiesUpdateCommand(modelController, updateMap);
			cmd.execute();
		}

		// Display.getDefault().asyncExec(new Runnable() {
		// public void run() {
		//
		// TSLayoutEventData layoutEventData = layoutEvent
		// .getLayoutEventData();
		// if (layoutEventData.getType() == TSLayoutEvent.POST_LAYOUT) {
		// if (!isFirstLayoutAfterLoading) {
		// updateGraphElements(graphManager.getMainDisplayGraph());
		// } else {
		// isFirstLayoutAfterLoading = false;
		// }
		// }
		//
		// }
		// });

	}

	public void onNodeAdded(TSENode tsNode) {
		Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateMap = new HashMap<EObjectWrapper<EClass, EObject>, Map<String, Object>>();
		updateNodePosition(tsNode, updateMap);
		modelGraphFactory.buildNodeRegistry();
		Set<EObjectWrapper<EClass, EObject>> keySet = updateMap.keySet();
		Iterator<EObjectWrapper<EClass, EObject>> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			EObjectWrapper<EClass, EObject> eObjectWrapper = (EObjectWrapper<EClass, EObject>) iterator.next();
			Map<String, Object> map = updateMap.get(eObjectWrapper);
			modelController.updateEmfModel(eObjectWrapper, map);
		}
		refreshNodeForIconAndColor(tsNode);
		//fixing defect BE-17439
		boolean showBreakpoints = prefStore.getBoolean(BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS);
		if (tsNode.getUI() instanceof ShapeNodeUI) {
			ShapeNodeUI nodeUI = (ShapeNodeUI) tsNode.getUI();
			nodeUI.setShowBreakPoints(showBreakpoints);
		}
		// updateModel(updateMap);
	}

	// private TSEEdge createConnection(TSENode srcNode, TSENode tgtNode, String
	// label) {
	// TSEEdge edge = (TSEEdge) graphManager.addEdge(srcNode, tgtNode);
	//
	// edge.setUserObject(createNewTransition(edge,srcNode, tgtNode));
	// if (label != null) {
	// TSEEdgeLabel edgeLabel = (TSEEdgeLabel) edge.addLabel();
	// ((TSEAnnotatedUI)edgeLabel.getUI()).setTextAntiAliasingEnabled(true);
	// getLayoutManager().setEdgeLabelOptions(edgeLabel);
	// edgeLabel.setTag(label);
	// }
	// TSECurvedEdgeUI edgeUI = new TSECurvedEdgeUI();
	// edgeUI.setAntiAliasingEnabled(true);
	// edgeUI.setCurvature(100);
	// edge.setUI(edgeUI);
	// // TODO: if tgtNode is of error type, make link red...
	// return edge;
	// }

	public void onNodeLabelMoved(TSENodeLabel label) {
		TSConstPoint labelCenter = label.getCenter();
		TSENode node = (TSENode) label.getParentEventSource();
		Object userObject = node.getUserObject();
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap((EObject) userObject);
			EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(wrap);
			if (addDataExtensionValueWrapper != null && addDataExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL_POINT)) {
				EObject lblPt = addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL_POINT);
				if (lblPt != null) {
					EObjectWrapper<EClass, EObject> nodePointWrap = EObjectWrapper.wrap(lblPt);
					Double x = nodePointWrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X);
					Double y = nodePointWrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y);
					if (compareDouble(x, labelCenter.getX(), 3) && compareDouble(y, labelCenter.getY(), 3)) {
						return;
					}
				}
			}
		}

		Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateMap = new HashMap<EObjectWrapper<EClass, EObject>, Map<String, Object>>();
		updateLabelPosition(label, updateMap);

		if (updateMap.size() == 0)
			return;
		TSCommand cmd = new EmfModelPropertiesUpdateCommand(modelController, updateMap);
		cmd.execute();
		// updateModel(updateMap);

	}

	@SuppressWarnings("rawtypes")
	public void onNodeMoved(TSENode tsNode) {
		Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateMap = new HashMap<EObjectWrapper<EClass, EObject>, Map<String, Object>>();
		updateNodePosition(tsNode, updateMap);
		List buildInAndOutEdges = tsNode.buildInAndOutEdges();
		for (Iterator<?> iterator = buildInAndOutEdges.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if (object instanceof TSEEdge) {
				TSEEdge edge = (TSEEdge) object;
				if (edge.getUserObject() != null)
					updateEdgeBendPoints(edge, updateMap);

			}
		}
		if (updateMap.size() == 0)
			return;
		TSCommand cmd = new EmfModelPropertiesUpdateCommand(modelController, updateMap);
		cmd.execute();

		// updateModel(updateMap);
	}

	//
	// private TSENode createConnection(String label) {
	// return this.createActivity(label, ACTIVITY_NORMAL);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#openModel()
	 */
	public void openModel() throws Exception {
		initializeModel();
		if (getBpmnEditorInput() != null) {
			loadBpmnModel(new NullProgressMonitor());
			if (!isFirstTimeCreatedGraph) {
				if (Display.getCurrent() != null) {
					Display.getCurrent().syncExec(new Runnable() {
						@Override
						public void run() {
							setGraphElementsPosition(
									graphManager.getMainDisplayGraph(), true,
									false);
							setGraphElementsPosition(
									graphManager.getMainDisplayGraph(), false,
									true);
						}
					});
				} else {
					if (SwingUtilities.isEventDispatchThread()) {
						setGraphElementsPosition(
								graphManager.getMainDisplayGraph(), true,
								false);
						setGraphElementsPosition(
								graphManager.getMainDisplayGraph(), false,
								true);  					
					}
					else{
						SwingUtilities.invokeAndWait(new Runnable() {
							@Override
							public void run() {
								setGraphElementsPosition(
										graphManager.getMainDisplayGraph(), true,
										false);
								setGraphElementsPosition(
										graphManager.getMainDisplayGraph(), false,
										true);
							}
						});
					}

				}
			} else {
				modelGraphFactory.buildNodeRegistry();
			}
			addAnnotationModelListener();
			loadBreakpoints(getBpmnGraphEditor().getGraphAnnotationModel());
			refreshNodesForIconAndColor();
		}
	}

	public void addAnnotationModelListener(){
		try {
			if(getBpmnGraphEditor().getGraphAnnotationModel()!=null)
				getBpmnGraphEditor()
				.getGraphAnnotationModel()
				.addAnnotationModelListener(new AnnotationManagerListener());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openXPDLModel(String tsModelPath) {
		// TODO parse xpdl file in this.file
		System.out.println("Opening " + tsModelPath);
		TSEVisualizationXMLReader reader = new TSEVisualizationXMLReader(tsModelPath);
		reader.setGraphManager(this.graphManager);
		reader.setServiceInputData(this.getLayoutManager().getInputData());

		try {
			reader.read();
		} catch (IOException e) {
			System.err.println("XPDL file not found!");
			BpmnUIPlugin.log(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.diagramming.drawing.DiagramManager#pasteGraph()
	 */
	public void pasteGraph() {
		if (!getBpmnGraphEditor().isEnabled()) {
			return;
		}
		super.pasteGraph();
		BpmnGraphUtils.editGraph(EDIT_TYPES.PASTE, getEditor().getEditorSite(), this);
	}

	public void createSubprocessInitialChild(TSENode subprocessNode) {
		((TSEEventManager) this.graphManager.getEventManager()).removeGraphChangeListener(
				this.graphManager,
				getDiagramChangeListener(),
				TSGraphChangeEvent.ANY_NODE | TSGraphChangeEvent.ANY_EDGE | TSGraphChangeEvent.EDGE_ENDNODE_CHANGED);
		TSConstPoint center = subprocessNode.getCenter();
		TSEGraph graph = (TSEGraph) subprocessNode.getChildGraph();
		fProject =bpmnEditorInput.getFile().getProject();
		BpmnPaletteModel _toolDefinition = PaletteLoader.getBpmnPaletteModel(fProject);
		List<BpmnPaletteGroupItem> paletteToolByType = _toolDefinition
				.getPaletteToolItemByType(START_EVENT, null);
		String toolId = null;
		if (paletteToolByType.size() > 0){
			BpmnPaletteGroupItem item = paletteToolByType.get(0);
			toolId = item.getId();
		}
		if(toolId == null || toolId.isEmpty())
			toolId = "event.none.message";
		TSENode start = createStartEventInSubprocess(graph, Messages.getString("new.subprocess.startTask"), toolId);
		start.setCenterX(center.getX()-100);
		start.setCenterY(center.getY());

		paletteToolByType = _toolDefinition
				.getPaletteToolItemByType(END_EVENT, null);
		toolId = null;
		if (paletteToolByType.size() > 0){
			BpmnPaletteGroupItem item = paletteToolByType.get(0);
			toolId = item.getId();
		}
		if(toolId == null || toolId.isEmpty())
			toolId = "event.none.message";
		TSENode end = createEndEventInSubprocess(graph,  Messages.getString("new.subprocess.endTask"), toolId);
		end.setCenterX(center.getX()+100);
		end.setCenterY(center.getY());

		paletteToolByType = _toolDefinition
				.getPaletteToolItemByType(RULE_FUNCTION_TASK, null);
		toolId = null;
		if (paletteToolByType.size() > 0){
			BpmnPaletteGroupItem item = paletteToolByType.get(0);
			toolId = item.getId();
		}
		if(toolId == null || toolId.isEmpty())
			toolId = "activity.ruleFunction";
		TSENode task = createRFTaskInSubprocess(graph,  Messages.getString("new.subprocess.scriptTask"), toolId);
		task.setCenterX(center.getX());
		task.setCenterY(center.getY());

		createSeqFlowEdgeInSubprocess(graph, start, task);
		createSeqFlowEdgeInSubprocess(graph, task, end);

		((TSEEventManager) this.graphManager.getEventManager()).addGraphChangeListener(
				this.graphManager,
				getDiagramChangeListener(),
				TSGraphChangeEvent.ANY_NODE | TSGraphChangeEvent.ANY_EDGE | TSGraphChangeEvent.EDGE_ENDNODE_CHANGED);
		AbstractNodeUIFactory uiFactory = BpmnGraphUIFactory.getInstance((BpmnLayoutManager) getLayoutManager()).getNodeUIFactory("", "", "", BpmnModelClass.SUB_PROCESS, null);
		uiFactory.layoutNode(subprocessNode);
		modelGraphFactory.buildNodeRegistry();
		refreshNodeForIconAndColor(task);
		refreshNodeForIconAndColor(start);
		refreshNodeForIconAndColor(end);

	}


	private void populateLane(TSENode laneNode) {
		TSEGraph graph = (TSEGraph) laneNode.getChildGraph();
		String attachedResource=null;
		if (laneNode.getName().equals(BpmnUIConstants.DEFAULT_LANE_NAME)) {
			fProject =bpmnEditorInput.getFile().getProject();
			BpmnPaletteModel _toolDefinition = PaletteLoader.getBpmnPaletteModel(fProject);
			List<BpmnPaletteGroupItem> paletteToolByType = _toolDefinition
					.getPaletteToolItemByType(START_EVENT, MESSAGE_EVENT_DEFINITION);
			String toolId = null;

			if (paletteToolByType.size() > 0){
				BpmnPaletteGroupItem item = paletteToolByType.get(0);
				attachedResource=item.getAttachedResource();
				toolId = item.getId();
			}
			if(toolId == null || toolId.isEmpty())
				toolId = "event.start.message";
			TSENode start = createStartEvent(graph, Messages.getString("title.message.start.event"), toolId,attachedResource);
			paletteToolByType = _toolDefinition
					.getPaletteToolItemByType(END_EVENT, MESSAGE_EVENT_DEFINITION);
			toolId = null;
			if (paletteToolByType.size() > 0){
				BpmnPaletteGroupItem item = paletteToolByType.get(0);
				attachedResource=item.getAttachedResource();
				toolId = item.getId();
			}
			if(toolId == null || toolId.isEmpty())
				toolId = "event.end.message";
			TSENode end = createEndEvent(graph,  Messages.getString("title.message.end.event"), toolId,attachedResource);

			paletteToolByType = _toolDefinition
					.getPaletteToolItemByType(RULE_FUNCTION_TASK, null);
			toolId = null;
			if (paletteToolByType.size() > 0){
				BpmnPaletteGroupItem item = paletteToolByType.get(0);
				attachedResource=item.getAttachedResource();
				toolId = item.getId();
			}
			if(toolId == null || toolId.isEmpty())
				toolId = "activity.ruleFunction";
			TSENode task = createRFTask(graph, Messages.getString("new.subprocess.scriptTask"), toolId,attachedResource);
			createSeqFlowEdge(graph, start, task);
			createSeqFlowEdge(graph, task, end);
		} else if (laneNode.getName().toString().equalsIgnoreCase("Order")) {
			TSENode start = createStartEvent(graph, Messages.getString("new.subprocess.startTask"), "event.start.message",attachedResource);
			TSENode end = createEndEvent(graph, Messages.getString("new.subprocess.endTask"), "event.end.message",attachedResource);
			TSENode task = createRFTask(graph, "Task", "activity.ruleFunction",attachedResource);
			createSeqFlowEdge(graph, start, task);
			createSeqFlowEdge(graph, task, end);
		} else if (laneNode.getName().toString().equalsIgnoreCase("Fulfill")) {
			TSENode start = this.createStartEvent(graph, Messages.getString("new.subprocess.startTask"), "event.start.message",attachedResource);
			TSENode end = this.createEndEvent(graph,Messages.getString("new.subprocess.endTask"), "event.end.message",attachedResource);
			TSENode task1 = this.createRFTask(graph, "Task1", "activity.ruleFunction",attachedResource);
			TSENode task2 = this.createRFTask(graph, "Task2", "activity.ruleFunction",attachedResource);
			TSENode or = this.createGateway(graph, "Decision", "gateway.exclusive", GatewayType.OR);

			createSeqFlowEdge(graph, start, or);
			createSeqFlowEdge(graph, or, task1);
			createSeqFlowEdge(graph, or, task2);
			createSeqFlowEdge(graph, task1, end);
			createSeqFlowEdge(graph, task2, end);
		} else if (laneNode.getName().toString().equalsIgnoreCase("Monitor")) {
			TSENode start = this.createStartEvent(graph, Messages.getString("new.subprocess.startTask"), "event.start.message",attachedResource);
			TSENode end = this.createEndEvent(graph, Messages.getString("new.subprocess.endTask"), "event.end.message",attachedResource);
			TSENode task1 = this.createRFTask(graph, "Task1", "activity.ruleFunction",attachedResource);
			TSENode task2 = this.createRFTask(graph, "Task2", "activity.ruleFunction",attachedResource);
			createSeqFlowEdge(graph, start, task1);
			createSeqFlowEdge(graph, task1, task2);
			createSeqFlowEdge(graph, task2, end);
		} else if (laneNode.getName().toString().equalsIgnoreCase("BE Dev")) {
			TSENode node1 = this.createTempActivity(graph, "Gap Analysis");
			TSENode node2 = this.createTempActivity(graph, "Design/Prototype");
			TSENode node3 = this.createTempActivity(graph, "Function Complete");
			TSENode node4 = this.createTempActivity(graph, "Beta");
			TSENode node5 = this.createTempActivity(graph, "GA");
			graph.addEdge(node1, node2);
			graph.addEdge(node2, node3);
			graph.addEdge(node3, node4);
			graph.addEdge(node4, node5);
		} else if (laneNode.getName().toString().equalsIgnoreCase("QA")) {
			TSENode node1 = this.createTempActivity(graph, "Basic");
			TSENode node2 = this.createTempActivity(graph, "Complete");
			TSENode node3 = this.createTempActivity(graph, "System");
			graph.addEdge(node1, node2);
			graph.addEdge(node2, node3);
		} else if (laneNode.getName().toString().equalsIgnoreCase("Docs")) {
			TSENode node1 = this.createTempActivity(graph, "Basic");
			TSENode node2 = this.createTempActivity(graph, "Beta");
			TSENode node3 = this.createTempActivity(graph, "Complete");
			graph.addEdge(node1, node2);
			graph.addEdge(node2, node3);
		} else if (laneNode.getName().toString().equalsIgnoreCase("PM")) {
			TSENode node1 = this.createTempActivity(graph, "xxx");
			node1.setVisible(false);
			TSENode node2 = this.createTempActivity(graph, "yyy");
			TSENode node3 = this.createTempActivity(graph, "zzz");
			graph.addEdge(node1, node2);
			graph.addEdge(node2, node3);
		} else if (laneNode.getName().toString().equalsIgnoreCase("Support")) {
			TSENode node1 = this.createTempActivity(graph, "Initial");
			TSENode node2 = this.createTempActivity(graph, "Learning");
			TSENode node3 = this.createTempActivity(graph, "Ready");
			graph.addEdge(node1, node2);
			graph.addEdge(node2, node3);
		}
	}

	//	@SuppressWarnings("unused")
	//	private void populateLaneOrig(TSENode laneNode) {
	//
	//		TSEGraph graph = (TSEGraph) laneNode.getChildGraph();
	//		TSENode node1 = this.createTempActivity(graph, "node1");
	//		TSENode node2 = this.createTempActivity(graph, "node2");
	//
	//		// TSENode node1 = (TSENode) graph.addNode();
	//		// node1.setTag("node1");
	//		// TSENode node2 = (TSENode) graph.addNode();
	//		// node2.setTag("node2");
	//		if (laneNode.getTagString().equalsIgnoreCase("lane 2")) {
	//			graph.addEdge(node1, node2);
	//		} else if (laneNode.getTagString().equalsIgnoreCase("lane 3")) {
	//			TSENode node3a = this.createTempActivity(graph, "node3a");
	//			// TSENode node3b = this.createTempActivity(graph, "node3b");
	//			graph.addEdge(node1, node2);
	//			graph.addEdge(node2, node3a);
	//		} else if (laneNode.getTagString().equalsIgnoreCase("lane 4")) {
	//			TSENode node3a = this.createTempActivity(graph, "node4a");
	//			// TSENode node3b = this.createTempActivity(graph, "node4b");
	//			graph.addEdge(node1, node2);
	//			graph.addEdge(node2, node3a);
	//		} else {
	//			// TSENode node3 = (TSENode) graph.addNode();
	//			// node3.setTag("node3");
	//			TSENode node3 = this.createTempActivity(graph, "node3");
	//			graph.addEdge(node1, node2);
	//			graph.addEdge(node2, node3);
	//			graph.addEdge(node1, node3);
	//		}
	//	}

	/**
	 * @param edge
	 * @param srcNode
	 * @param tgtNode
	 * @return
	 */
	// private GraphTransition createNewTransition(TSEEdge edge, TSENode
	// srcNode, TSENode tgtNode){
	//
	// GraphTransition graphTransition = modelController.createNewTransition();
	//
	// Object fromObject = srcNode.getUserObject();
	// Object toObject = tgtNode.getUserObject();
	//
	// String fromId = BpmnGraphUtils.getId(fromObject);
	// String toId = BpmnGraphUtils.getId(toObject);
	//
	// graphTransition.setFrom(fromId);
	// graphTransition.setTo(toId);
	// edge.setUserObject(graphTransition);
	//
	// return graphTransition;
	// }
	public void postPopulateDrawingCanvas() {
		EObject userObject = (EObject) getGraphManager().getMainDisplayGraph().getGreatestAncestor().getUserObject();
		EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject);
		EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(wrap);
		final Double zoom = addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ZOOM_LEVEL);

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				// if (getDrawingCanvas().getZoomLevel() > 1.0) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							getDrawingCanvas().fitInCanvas(true);

							if (zoom == null) {
								getDrawingCanvas().setZoomLevelInteractive(1.0);
							} else {
								getDrawingCanvas().setZoomLevelInteractive(zoom);
							}

						} finally {
							addZoomListener();
						}
					}
				});
			}
			// }
		});
	}

	@SuppressWarnings("static-access")
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		try {
			String property = event.getProperty();
			if (property.equals(BpmnPreferenceConstants.GRID)) {
				property = prefStore.getString(BpmnPreferenceConstants.GRID);
				// to set grid type, configurable through preference page
				if (property.equalsIgnoreCase(BpmnPreferenceConstants.NONE)) {
					gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.none"), this.drawingCanvas);
				} else if (property.equalsIgnoreCase(BpmnPreferenceConstants.LINE)) {
					gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.lines"), this.drawingCanvas);
				} else if (property.equalsIgnoreCase(BpmnPreferenceConstants.POINT)) {
					gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.points"), this.drawingCanvas);
				}
			}
			if (property.equalsIgnoreCase(BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS)) {
				property = prefStore.getString(BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS);
				boolean showBreakpoints = prefStore.getBoolean(BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS);
				for (TSEObject object : getModelGraphFactory().getNodeRegistry().getAllNodes()) {
					if (object instanceof TSENode && object.getUI() instanceof ShapeNodeUI) {
						if( isSubprocessStartEndNode((TSENode)object) ){
							continue;
						}
						((ShapeNodeUI) object.getUI()).setShowBreakPoints(showBreakpoints);
					}
				}
			}
			if(property.equalsIgnoreCase(BpmnPreferenceConstants.PREF_SHOW_TASK_ICONS)){
				property = prefStore.getString(BpmnPreferenceConstants.PREF_SHOW_TASK_ICONS);
				boolean showTaskIcons = prefStore.getBoolean(BpmnPreferenceConstants.PREF_SHOW_TASK_ICONS);
				for (TSEObject object : getModelGraphFactory().getNodeRegistry().getAllNodes()) {
					if (object instanceof TSENode && object.getUI() instanceof TaskNodeUI) {
						((TaskNodeUI) object.getUI()).setSHOW_TASK_ICONS(showTaskIcons);
					}
				}
			}
			if (property.equals(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS)) {
				//				property = prefStore.getString(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS);
				//				IMAGE_SIZE imageSize = IMAGE_SIZE.get(property);
				//				if (property.equalsIgnoreCase(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS_16)) {
				//					System.out.println("selected 16");
				//				}  else if (property.equalsIgnoreCase(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS_24)) {
				//					System.out.println("selected 32");
				//				} else if (property.equalsIgnoreCase(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS_32)) {
				//					System.out.println("selected 32");
				//				} else if (property.equalsIgnoreCase(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS_64)) {
				//					System.out.println("selected 64");
				//				}
				refreshNodesForIconAndColor();
			}

			DiagramUtils.refreshDiagram((BpmnDiagramManager) (((BpmnEditor) editor).getDiagramManager()));
			OverviewUtils.refreshOverview(getEditor().getEditorSite(), true, true);
		} catch (Exception e) {
			EditorsUIPlugin.debug(this.getClass().getName(), e.getLocalizedMessage());
		}
	}

	public TSCommandInterface redo() {
		TSCommandInterface command = super.redo();
		// TODO: do something here...
		return command;
	}

	public void refreshBreakpoints() {
		boolean showBreakpoints = prefStore.getBoolean(BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS);
		for (TSEObject object : getModelGraphFactory().getNodeRegistry().getAllNodes()) {
			if (object instanceof TSENode && object.getUI() instanceof ShapeNodeUI) {
				TSENode node = (TSENode) object;
				ShapeNodeUI nodeUI = (ShapeNodeUI) node.getUI();
				if (isSubprocessStartEndNode(node)){
					nodeUI.setShowBreakPoints(false);
					continue;
				}
				if (!nodeUI.isShowBreakPoints()) {
					nodeUI.setShowBreakPoints(showBreakpoints);
				}
			}
		}
		DiagramUtils.refreshDiagram((BpmnDiagramManager) (((BpmnEditor) editor).getDiagramManager()));
		OverviewUtils.refreshOverview(getEditor().getEditorSite(), true, true);
	}

	public void refreshEdge(TSEEdge edge) {
		refreshGraphObject(edge);
	}

	public void refreshGraph(TSEGraph graph) {
		refreshGraphObject(graph);
	}

	@SuppressWarnings("static-access")
	public void refreshGraphObject(TSGraphObject graphObject) {
		if (graphObject instanceof TSEObject) {
			this.drawingCanvas.setTooltipFormat("<html><body style=\"width:50px\"><font face='arial' size=4>" + TSBaseSwingCanvas.TOOLTIP_PLACEHOLDER +"</font></body>"
					+"</html>");
			((TSEObject) graphObject).setTooltipText(BpmnDiagramHelper.getNodeToolTip(graphObject));
			this.drawingCanvas.addInvalidRegion(((TSEObject) graphObject).getBounds());
		}
		this.drawingCanvas.drawGraph();
		this.drawingCanvas.repaint();
	}

	public void refreshLabel(TSLabel nodeLabel) {
		refreshGraphObject(nodeLabel);
	}

	public void refreshNode(TSENode node) {

		refreshGraphObject(node);
	}

	@Override
	protected void registerListeners() {

		super.registerListeners();
		addPropertyChangeListener(prefStore);
	}

	public void reLoadModel() {
		try {
			this.isLoading = true;
			EObjectWrapper<EClass, EObject> modelRoot = getBpmnEditorInput().getProcessModel();
			modelController.setModelRoot(modelRoot);
			getModelChangeAdapterFactory().adapt(modelRoot, ModelChangeListener.class);
			if (getModelGraphFactory() == null) {
				return;
			}
			getModelGraphFactory().reLoadProcess(modelRoot);

		} finally {
			this.isLoading = false;
		}
	}

	private void removeBendPoints(TSEEdge edge) {
		Iterator<?> bendIterator = edge.bendIterator();
		while (bendIterator.hasNext()) {
			TSPNode node = (TSPNode) bendIterator.next();
			edge.removePathSegment(node);
		}
	}


	public void removeBreakpointHit(GraphPosition position) {
		clearAnnotations();
		NodeBreakpointInfo nbpinfo = getFlowNodeBreakPointInfo(position.getOffset(), position.getNodeInfo().getLocation());
		if (nbpinfo.node != null) {
			if (nbpinfo.node.getNodeUI() instanceof ShapeNodeUI) {
				ShapeNodeUI nui = (ShapeNodeUI) nbpinfo.node.getNodeUI();
				nui.setDrawGlow(false);
				DiagramUtils
				.refreshDiagram((BpmnDiagramManager) (((BpmnEditor) editor)
						.getDiagramManager()));
				OverviewUtils.refreshOverview(getEditor().getEditorSite(),
						true, true);
			}
		}
	}

	/**
	 * 
	 * @param file
	 * @param subProgressMonitor
	 */
	public void saveModel(final IFile file, IProgressMonitor progressMonitor) {
		TSEGraph graph = (TSEGraph) ((TSEGraph) getGraphManager().getMainDisplayGraph()).getGreatestAncestor();
		try {

			final EObject process = (EObject) graph.getUserObject();
			Date time = Calendar.getInstance().getTime();
			EObjectWrapper<EClass, EObject> useInstance = EObjectWrapper.wrap(process);
			useInstance.setAttribute(BpmnMetaModelConstants.E_ATTR_LAST_MODIFIED, time);
			changeVersion(useInstance);
			WorkspaceModifyOperation wsMOp = new WorkspaceModifyOperation() {

				@Override
				protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
					try {
						ECoreHelper.serializeModelXMI(file, process);
					} catch (Exception e) {
						BpmnUIPlugin.log(e);
					}

				}
			};
			wsMOp.run(progressMonitor);

		} catch (Exception e1) {
			BpmnUIPlugin.log(Messages.format(BpmnUIConstants.BPMN_MODEL_SAVE_ERROR, file.getName()), e1);
		}
	}

	public void saveXPDLModel(File file) {
		// TODO parse xpdl file in this.file
		System.out.println("Saving TS model only in " + file);
		TSEVisualizationXMLWriter writer = null;
		try {
			writer = new TSEVisualizationXMLWriter(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			BpmnUIPlugin.log(e);
			return;
		}

		writer.setGraphManager(this.graphManager);
		writer.setServiceInputData(this.getLayoutManager().getInputData());
		writer.setPreferenceData(this.drawingCanvas.getPreferenceData());
		writer.setIndenting(true);
		writer.write();
	}


	public void setError(GraphPosition position, boolean enabled) {
		NodeBreakpointInfo nbpinfo = getFlowNodeBreakPointInfo(
				position.getOffset(), position.getNodeInfo().getLocation());
		if (nbpinfo.getNode() != null) {
			if (nbpinfo.getNode().getNodeUI() instanceof ShapeNodeUI) {
				ShapeNodeUI nodeUI = (ShapeNodeUI) nbpinfo.getNode()
						.getNodeUI();
				nodeUI.setDrawError(true);
				DiagramUtils
				.refreshDiagram((BpmnDiagramManager) (((BpmnEditor) editor)
						.getDiagramManager()));
				OverviewUtils.refreshOverview(getEditor().getEditorSite(),
						true, true);
			}
		}
	}


	public void setBreakpoint(GraphPosition position, boolean enabled) {
		NodeBreakpointInfo nbpinfo = getFlowNodeBreakPointInfo(
				position.getOffset(), position.getNodeInfo().getLocation());
		if (nbpinfo.getNode() != null) {
			if (nbpinfo.getNode().getNodeUI() instanceof ShapeNodeUI) {
				ShapeNodeUI nodeUI = (ShapeNodeUI) nbpinfo.getNode()
						.getNodeUI();
				if (nbpinfo.getBpInfo().getLocation() == IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.START) {
					nodeUI.setInputBreakpoint(enabled);
					nodeUI.setInputBreakPointToggle(enabled);
				} else {
					nodeUI.setOutputBreakpoint(enabled);
					nodeUI.setOutputBreakPointToggle(enabled);
				}
				DiagramUtils
				.refreshDiagram((BpmnDiagramManager) (((BpmnEditor) editor)
						.getDiagramManager()));
				OverviewUtils.refreshOverview(getEditor().getEditorSite(),
						true, true);
			}
		}

	}

	/**
	 * @param position
	 */
	public void setBreakpointHit(GraphPosition position) {
		NodeBreakpointInfo nbpinfo = getFlowNodeBreakPointInfo(
				position.getOffset(), position.getNodeInfo().getLocation());
		if (nbpinfo.node != null) {
			if (nbpinfo.node.getNodeUI() instanceof ShapeNodeUI) {
				ShapeNodeUI nodeUI = (ShapeNodeUI) nbpinfo.node.getNodeUI();
				if (currentPreviouseNode == null) {
					currentPreviouseNode = nbpinfo.node;
				}
				if (currentPreviouseNode != null
						&& currentPreviouseNode != nbpinfo.node) {
					removeBreakpointHits(currentPreviouseNode);
					currentPreviouseNode = nbpinfo.node;
				}
				if (nbpinfo.getBpInfo().getLocation() == IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.START) {
					onBreakpointHit(nbpinfo.node, nodeUI, true);
				} else {
					onBreakpointHit(nbpinfo.node, nodeUI, false);
				}
				// nui.setDrawGlow(true);
				// DiagramUtils.refreshDiagram((BpmnDiagramManager)
				// (((BpmnEditor) editor).getDiagramManager()));
				// OverviewUtils.refreshOverview(getEditor().getEditorSite(),
				// true, true);
			}
		}
	}

	/**
	 * @param node
	 */
	private void removeBreakpointHits(TSENode node) {
		if (node.getUI() instanceof ShapeNodeUI) {
			ShapeNodeUI nodeUI = (ShapeNodeUI) node.getUI();
			if (nodeUI.isInputBreakpointHit()) {
				nodeUI.setInputBreakpointHit(false);
			}
			if (nodeUI.isOutputBreakpointHit()) {
				nodeUI.setOutputBreakpointHit(false);
			}
			refreshNode(node);
		}
	}

	/**
	 * @param node
	 * @param nodeUI
	 * @param isInputBreakpointHit
	 */
	private void onBreakpointHit(TSENode node, ShapeNodeUI nodeUI, boolean isInputBreakpointHit) {
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
		refreshNode(node);
	}

	public void setFocus() {
		this.swtAwtComponent.setFocus();
	}
	private void setGraphElementsPosition(TSGraph graph, boolean updateNode, boolean updateEdge) {
		List<?> nodeSet = graph.nodeSet;
		for (Object node : nodeSet) {
			if (updateNode)
				setNodePosition((TSENode) node);
			TSGraph childGraph = ((TSENode) node).getChildGraph();
			if (childGraph != null)
				setGraphElementsPosition(childGraph, updateNode, updateEdge);
		}

		if (updateEdge) {
			List<?> edgeSet = graph.edgeSet;
			for (Object object : edgeSet) {
				insertBendPoints((TSEEdge) object);
			}
		}
	}

	public void setLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}

	@SuppressWarnings("rawtypes")
	private void setNodePosition(TSENode node) {
		EObject userObject = (EObject) node.getUserObject();
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject);
			if (ExtensionHelper.isValidDataExtensionAttribute(wrap, BpmnMetaModelExtensionConstants.E_ATTR_NODE_POINT)) {
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(userObject);
				EObject attribute = (EObject) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_NODE_POINT);
				if (attribute != null) {
					EObjectWrapper<EClass, EObject> pointWrap = EObjectWrapper.wrap(attribute);
					Double x = (Double) pointWrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X);
					Double y = (Double) pointWrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y);

					if (x != null)
						node.setCenterX(x);
					if (y != null)
						node.setCenterY(y);
				}
				if(userObject.eClass().equals(BpmnModelClass.SUB_PROCESS) ||userObject.eClass().equals(BpmnModelClass.TEXT_ANNOTATION) ){
					if(valueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LENGTH) && 
							valueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BREADTH)){
						Double length = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LENGTH);
						Double breadth = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BREADTH);
						if(length != null && breadth != null)
							node.setSize(length, breadth);
					}
				}

				List labels = node.getLabels();
				if (labels.size() > 0) {
					TSENodeLabel label = (TSENodeLabel) labels.get(0);
					attribute = (EObject) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL_POINT);
					if (attribute != null) {
						EObjectWrapper<EClass, EObject> pointWrap = EObjectWrapper.wrap(attribute);
						Double labelX = (Double) pointWrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X);
						Double labelY = (Double) pointWrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y);

						if (labelX != null && labelY != null)
							label.setCenter(labelX, labelY);
						else
							layoutManager.setNodeLabelOptions(label);
					}
				}
			}
		}
	}

	protected void setPreferences() {
		super.setPreferences();
		String gridType = getPreferenceStore().getString(BpmnPreferenceConstants.GRID);
		TSPreferenceData prefData = getDrawingCanvas().getPreferenceData();
		TSSwingCanvasPreferenceTailor swingTailor = new TSSwingCanvasPreferenceTailor(prefData);

		// to set grid type, configurable through preference page
		if (gridType.equalsIgnoreCase(BpmnPreferenceConstants.NONE)) {
			gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.none"), this.drawingCanvas);
		} else if (gridType.equalsIgnoreCase(BpmnPreferenceConstants.LINE)) {
			gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.lines"), this.drawingCanvas);
		} else if (gridType.equalsIgnoreCase(BpmnPreferenceConstants.POINT)) {
			gridType(com.tibco.cep.studio.ui.editors.utils.Messages.getString("studio.preference.diagram.points"), this.drawingCanvas);
		}

		swingTailor.setAutoHideScrollBars(false);
	}

	public boolean layoutDiagramOnChange() {
		return false;

	}
	private void setSeparationConstraint() {
		LeftLayoutActionDelegate leftAlign = new LeftLayoutActionDelegate() ; 
		leftAlign.setPage( BpmnUIPlugin.getActivePage() );
		leftAlign.setEditor( getEditor() );
		leftAlign.setDiagramManager( this );
		leftAlign.leftAlignNodes() ; 
		//		this.setSeparationConstraint((TSEGraph) this.graphManager.getMainDisplayGraph());
	}

	@SuppressWarnings("unchecked")
	private void setSeparationConstraint(TSEGraph graph) {

		this.leftMostNodes = this.findNodesOfType(graph, BpmnModelClass.START_EVENT);
		// System.out.println("Found " + leftMostNodes.size() +
		// " start nodes.");

		// if not, look for event gateways
		if (leftMostNodes.size() == 0) {
			leftMostNodes = this.findNodesOfType(graph, BpmnModelClass.GATEWAY);
			// System.out.println("found gateways: " + leftMostNodes.size());
		}

		// if not, look for tasks with no incoming but outgoing edges
		if (leftMostNodes.size() == 0) {
			leftMostNodes = this.findNodesOfType(graph, BpmnModelClass.TASK);
			List<TSENode> noInEdgeNodes = new LinkedList<TSENode>();
			for (TSENode node : leftMostNodes) {
				if (node.inDegree() == 0 && node.outDegree() > 0) {
					noInEdgeNodes.add(node);
				}
			}
			leftMostNodes = noInEdgeNodes;
			// System.out.println("found tasks with outgoing connections only: "
			// + leftMostNodes.size());
		}

		// EObjectWrapper<EClass, EObject> flowNodeWrapper =
		// EObjectWrapper.useInstance(flowNode);
		// if (flowNodeWrapper.isInstanceOf(BpmnModelClass.START_EVENT) { }

		// TODO: figure out this other node list: subtract all nodes from the
		// leftMostNodes
		this.otherNodes = new LinkedList<TSENode>();
		List<TSENode> allNodes = graph.nodes();
		for (TSENode currentNode : allNodes) {
			if (!leftMostNodes.contains(currentNode)) {
				otherNodes.add(currentNode);
			}
		}
		// System.out.println("other nodes: " + otherNodes.size());

		if (this.separationConstraint != null) {
			((BpmnLayoutManager) this.getLayoutManager()).removeConstraint(this.separationConstraint);
		}

		// ((BpmnLayoutManager)this.getLayoutManager()).addGroupConstraint(startNodes);
		this.separationConstraint = ((BpmnLayoutManager) this.getLayoutManager()).addSeparationConstraint(leftMostNodes, otherNodes,
				TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
	}

	boolean skipAnnotation(String type) {
		return !(type.equals(IDebugUIConstants.ANNOTATION_TYPE_INSTRUCTION_POINTER_CURRENT)
				|| type.equals(IResourceValidator.VALIDATION_MARKER_TYPE)
				|| type.equals("org.eclipse.ui.workbench.texteditor.error")
				|| type.equals(PROCESS_BREAKPOINT_MARKER_ANNOTATION));
	}

	public TSCommandInterface undo() {
		TSCommandInterface command = super.undo();
		// TODO: do something here...
		return command;
	}

	//	private Set< Integer > getSubprocessids ( TSEGraph graph ) {
	//		
	//		List< TSENode > Startnode = this.findNodesOfType( graph , BpmnModelClass.START_EVENT ) ;
	//		List< TSENode > Endnode = this.findNodesOfType( graph , BpmnModelClass.END_EVENT ) ;
	//		List< TSENode > subProcessNode = new ArrayList< TSENode >() ;
	//		uniqueIds.clear();
	//		for( TSENode tseNode : Startnode ) {
	//			EObject eobjP = BpmnModelUtils.getFlowElementContainer((EObject)tseNode.getUserObject()) ;
	//			if ( BpmnModelClass.SUB_PROCESS.isSuperTypeOf(eobjP.eClass() ) ) {
	//				subProcessNode.add( tseNode );
	//			} 
	//		}
	//		for( TSENode tseNode : Endnode ) {
	//			EObject eobjP = BpmnModelUtils.getFlowElementContainer((EObject)tseNode.getUserObject()) ;
	//			if ( BpmnModelClass.SUB_PROCESS.isSuperTypeOf(eobjP.eClass() ) ) {
	//				subProcessNode.add( tseNode );
	//			} 
	//		}
	//		for (TSENode tseNode : subProcessNode ) {
	//			EObject eobj = (EObject) tseNode.getUserObject() ;
	//			EObjectWrapper<EClass, EObject> eobjWrapper = EObjectWrapper.wrap(eobj) ;
	//			Integer uniqueId = eobjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID) ;
	//			uniqueIds.add( uniqueId ) ;
	//		}
	//		
	//		return uniqueIds ;
	//	}
	public void updateAnnotations() {
		try {
			cacheAnnotations();
			clearAnnotations();
			//final IAnnotationModel graphAnnotationModel = getBpmnGraphEditor().getGraphAnnotationModel();
			Iterator<Annotation> it = fCachedAnnotations.iterator();
			/*for( Iterator<Annotation> it = fCachedAnnotations.iterator(); it.hasNext();) */
			while(it.hasNext()){
				Annotation a = it.next();
				final String type = a.getType();
				if (type.equals(IDebugUIConstants.ANNOTATION_TYPE_INSTRUCTION_POINTER_CURRENT)) {
					Class<?> ipAnnotationClass = a.getClass();
					try {
						Class<?> dynamicIPClass = Class.forName("org.eclipse.debug.internal.ui.DynamicInstructionPointerAnnotation");
						if (dynamicIPClass != null && dynamicIPClass.isAssignableFrom(ipAnnotationClass)) {
							@SuppressWarnings("rawtypes")
							Class[] parameters = new Class[]{};
							Method m = dynamicIPClass.getDeclaredMethod("getStackFrame", parameters);
							if (m != null) {
								m.setAccessible(true);
								try {
									IStackFrame sf = (IStackFrame) m.invoke(a, new Object[]{});
									if (sf instanceof RuleDebugStackFrame) {
										//										ProcessDebugModel bpInfoProcessDebugModel;
										IProcessBreakpointInfo bpInfo = ProcessDebugModel.getProcessBreakPointInfo((RuleDebugStackFrame) sf);
										if (bpInfo != null) {
											GraphPosition position = GraphPosition.fromBreakPointInfo(bpInfo);
											// Position position =
											// graphAnnotationModel.getPosition(a);
											setBreakpointHit(position);
										}
									}
								} finally {
									m.setAccessible(false);
								}
							}
						}
					} catch (Exception e) {
						BpmnUIPlugin.log(e);
					}
				} else if (type.equals(PROCESS_BREAKPOINT_MARKER_ANNOTATION)){
					if(a instanceof MarkerAnnotation) { 
						MarkerAnnotation ma = (MarkerAnnotation) a;
						IMarker marker = ma.getMarker();
						Map<String, Object> attributes;
						try {
							attributes = marker.getAttributes();
							String uri = (String) attributes.get(IProcessBreakpoint.PROCESS_BREAKPOINT_PROCESS_URI);
							String nodeId = (String) attributes.get(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_ID);
							String nodeLocation = (String) attributes.get(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_LOCATION);
							String nodeTaskType = (String) attributes.get(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_TASK_TYPE);
							int uniqueId = (Integer) attributes.get(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_UNIQUE_ID);
							ProcessBreakpointInfo bpInfo = new ProcessBreakpointInfo(uri, nodeId, nodeTaskType, IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.valueOf(nodeLocation), uniqueId);
							//						Position position = graphAnnotationModel.getPosition(a);
							GraphPosition position = GraphPosition.fromBreakPointInfo(bpInfo);
							setBreakpoint(position,true);				
						} catch (CoreException e) {
							BpmnUIPlugin.log(e);
						}
					}
				} else if(type.equals("org.eclipse.ui.workbench.texteditor.error") ||
						type.equals(IResourceValidator.VALIDATION_MARKER_TYPE) ||
						type.equals(BpmnProcessValidator.INCORRECT_PROCESS_MARKER_TYPE) ||
						type.equals(BpmnProcessValidator.INDEX_FORMAT_MARKER_TYPE) ||
						type.equals(BpmnProcessValidator.MISSING_EXTDEF_MARKER_TYPE) ||
						type.equals(BpmnProcessValidator.MISSING_RESOURCE_MARKER_TYPE) ||
						type.equals(BpmnProcessValidator.EVENT_DEFAULT_DESTINATION_VALIDATION_MARKER_TYPE) ||
						type.equals(BpmnProcessValidator.BUILD_PATH_MARKER_TYPE) 
						) {
					if(a instanceof MarkerAnnotation) {
						MarkerAnnotation ma = (MarkerAnnotation) a;
						IMarker marker = ma.getMarker();
						Map<String, Object> attributes;
						try {
							attributes = marker.getAttributes();
							String uri = (String) attributes.get(IProcessBreakpoint.PROCESS_BREAKPOINT_PROCESS_URI);
							String nodeId = (String) attributes.get(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_ID);
							String nodeLocation = (String) attributes.get(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_LOCATION);
							String nodeTaskType = (String) attributes.get(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_TASK_TYPE);
							int uniqueId = (Integer) attributes.get(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_UNIQUE_ID);
							ProcessBreakpointInfo bpInfo = new ProcessBreakpointInfo(uri, nodeId, nodeTaskType, IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.valueOf(nodeLocation), uniqueId);
							//						Position position = graphAnnotationModel.getPosition(a);
							GraphPosition position = GraphPosition.fromBreakPointInfo(bpInfo);
							setError(position,true);	
						} catch (CoreException e) {
							BpmnUIPlugin.log(e);
						}
					}
					//				Position position = graphAnnotationModel.getPosition(a);
					//				setError(position,true);	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public List<Annotation> getErrorAnnotation(TSENode node) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		cacheAnnotations();
		final IAnnotationModel graphAnnotationModel = getBpmnGraphEditor().getGraphAnnotationModel();
		for(Annotation a: fCachedAnnotations) {			
			final String type = a.getType();
			if(type.equals("org.eclipse.ui.workbench.texteditor.error")) {
				Position pos = graphAnnotationModel.getPosition(a);
				if(pos instanceof GraphPosition){
					GraphPosition position = (GraphPosition)pos;
					NodeBreakpointInfo nbpinfo = getFlowNodeBreakPointInfo(position.getOffset(), position.getNodeInfo().getLocation());
					if (nbpinfo.getNode().equals(node)) {
						annotations.add(a);
					}
				}
			}
		}
		return annotations;
	}

	@SuppressWarnings("rawtypes")
	private void updateEdgeBendPoints(TSEEdge edge, Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateMap) {
		EObject userObject = (EObject) edge.getUserObject();
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject);
			if (ExtensionHelper.isValidDataExtensionAttribute(wrap, BpmnMetaModelExtensionConstants.E_ATTR_BEND_POINTS)) {
				Map<String, Object> updateList = new HashMap<String, Object>();

				List bendPoints = edge.bendPoints();
				TSConstPoint sourceClippingPoint = edge.getSourceClippingPoint();
				if (sourceClippingPoint != null) {
					double centerX = sourceClippingPoint.getX();
					double centerY = sourceClippingPoint.getY();
					EObjectWrapper<EClass, EObject> startPoint = getModelController().createPoint(centerX, centerY);
					updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_START_POINT, startPoint.getEInstance());
				}

				List<EObject> points = new ArrayList<EObject>();
				for (Object object : bendPoints) {
					TSConstPoint point = (TSConstPoint) object;
					EObjectWrapper<EClass, EObject> createPoint = getModelController().createPoint(point.getX(), point.getY());
					points.add(createPoint.getEInstance());
				}

				TSConstPoint targetClippingPoint = edge.getTargetClippingPoint();
				if (targetClippingPoint != null) {
					double centerX = targetClippingPoint.getX();
					double centerY = targetClippingPoint.getY();
					EObjectWrapper<EClass, EObject> endPoint = getModelController().createPoint(centerX, centerY);
					updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT, endPoint.getEInstance());
				}

				updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_BEND_POINTS, points);
				updateMap.put(wrap, updateList);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void updateGraphElements(TSGraph graph, Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateMap) {
		List edgeSet = graph.edgeSet;
		for (Object edge : edgeSet) {
			updateEdgeBendPoints((TSEEdge) edge, updateMap);
		}
		List nodeSet = graph.nodeSet;
		for (Object node : nodeSet) {
			updateNodePosition((TSENode) node, updateMap);
			TSGraph childGraph = ((TSENode) node).getChildGraph();
			if (childGraph != null)
				updateGraphElements(childGraph, updateMap);
		}
	}

	private void updateLabelPosition(TSENodeLabel label, Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateMap) {
		TSENode node = (TSENode) label.getParentEventSource();
		updateNodePosition(node, updateMap);
	}

	protected void updateModel(Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateMap) {
		if (updateMap.size() == 0)
			return;
		TSCommand cmd = new EmfModelPropertiesUpdateCommand(modelController, updateMap);
		executeCommand(cmd);
	}

	@SuppressWarnings("rawtypes")
	private void updateNodePosition(TSENode node, Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateMap) {
		EObject userObject = (EObject) node.getUserObject();
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject);
			if (ExtensionHelper.isValidDataExtensionAttribute(wrap, BpmnMetaModelExtensionConstants.E_ATTR_NODE_POINT)) {
				Map<String, Object> updateList = new HashMap<String, Object>();
				TSConstPoint center = node.getCenter();
				EObjectWrapper<EClass, EObject> createPoint = getModelController().createPoint(center.getX(), center.getY());
				updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_NODE_POINT, createPoint.getEInstance());
				List labels = node.getLabels();
				if (labels.size() > 0) {
					TSENodeLabel label = (TSENodeLabel) labels.get(0);
					TSConstPoint labelCenter = label.getCenter();
					EObjectWrapper<EClass, EObject> labelPoint = getModelController().createPoint(labelCenter.getX(), labelCenter.getY());
					updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_LABEL_POINT, labelPoint.getEInstance());

				}

				if (userObject.eClass().equals(BpmnModelClass.SUB_PROCESS) || userObject.eClass().equals(BpmnModelClass.TEXT_ANNOTATION)) {
					TSConstSize size = node.getSize();
					double width = size.getWidth();
					double height = size.getHeight();
					updateList.put(
							BpmnMetaModelExtensionConstants.E_ATTR_LENGTH,
							width);
					updateList.put(
							BpmnMetaModelExtensionConstants.E_ATTR_BREADTH,
							height);
				}
				updateMap.put(wrap, updateList);
			}
		}
	}

	public void updateSeparationConstraint(TSENode nodeChanged, int operation) {
		if (this.leftMostNodes == null && this.otherNodes == null && nodeChanged.isOwned() && !nodeChanged.isDiscarded()) {
			this.setSeparationConstraint((TSEGraph) nodeChanged.getOwnerGraph());
		}
		// if node was added
		if (operation == NODE_ADDED) {
			if (this.isNodeOfType(nodeChanged, BpmnModelClass.START_EVENT)) {
			}
			// if not Start Event, maybe it was of a different type. We may need
			// to
			// recalculate the constraint lists...
		}

		// if node was deleted
		if (operation == NODE_DELETED) {
		}

		// if edge was added

		// if edge was deleted

		// if property was changed?
	}

	@Override
	public boolean validateDeleteEdit(List<TSENode> selectedNodes, List<TSEEdge> selectedEdges) {
		if (selectedNodes.isEmpty() && selectedEdges.isEmpty())
			return false;

		return true;
	}

	private void zoomChanged() {
		double zoomLevel = getDrawingCanvas().getZoomLevel();
		EObject userObject = (EObject) getGraphManager().getMainDisplayGraph().getGreatestAncestor().getUserObject();
		EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject);
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(userObject);
		Double zoom = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ZOOM_LEVEL);
		if (zoom == null || (zoom != zoomLevel)) {
			EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(userObject);
			Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateMap = new HashMap<EObjectWrapper<EClass, EObject>, Map<String, Object>>();
			Map<String, Object> props = new HashMap<String, Object>();
			props.put(BpmnMetaModelExtensionConstants.E_ATTR_ZOOM_LEVEL, zoomLevel);
			updateMap.put(wrap, props);
			addDataExtensionValueWrapper.getEInstance().eSetDeliver(false);
			addDataExtensionValueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ZOOM_LEVEL, zoomLevel);
			// updateModel(updateMap);
			addDataExtensionValueWrapper.getEInstance().eSetDeliver(true);
		}
	}

	@Override
	public boolean isPrivateProcess() {
		return getBpmnEditorInput().isPrivateProcess();
	}
	@Override
	public boolean isDisplayFullName() {
		BpmnUIPlugin plugInstance = BpmnUIPlugin.getDefault();
		if(plugInstance != null){
			IPreferenceStore store = BpmnUIPlugin.getDefault().getPreferenceStore();
			return store.getBoolean(BpmnPreferenceConstants.PREF_DISPLAY_FULL_NAMES);
		}
		return false;
	}

	@Override
	public boolean isfillTaskIcons() {
		BpmnUIPlugin plugInstance = BpmnUIPlugin.getDefault();
		if (plugInstance != null) {
			IPreferenceStore store = plugInstance.getPreferenceStore();
			return store.getBoolean(BpmnPreferenceConstants.PREF_FILL_TASK_NODES);
		}
		return false;
	}

	@Override
	public void addAdditionalModel(Map<String, Object> updateMap, EObject flowNode, String attachedResource) {
		EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(flowNode);
		if(attachedResource != null && !attachedResource.trim().isEmpty()){
			if(wrap.getEClassType().equals(BpmnModelClass.SERVICE_TASK)){
				IBpmnDiagramManager diagramManager = getModelController().getDiagramManager();
				if(diagramManager != null) {
					IProject project = getProject();
					if(project != null){
						try {
							IFile file = project.getFile(attachedResource);
							String pathWsdl = file.getLocation().toPortableString() + ".wsdl";
							File file2 = new File(pathWsdl);
							if (file2.exists()) {
								WsdlWrapper wsdl = WsdlWrapperFactory.getWsdl(project, attachedResource);
								if(wsdl != null){
									Set<String> services = wsdl.getServices();
									for (String service : services) {
										updateMap.put(BpmnMetaModelExtensionConstants.E_ATTR_SERVICE, service);
										Set<String> ports = wsdl.getPort(service);
										for (String port : ports) {
											updateMap.put(BpmnMetaModelExtensionConstants.E_ATTR_PORT, port);
											String endPointUrl = wsdl.getEndPointUrl(service, port);
											if(endPointUrl != null)
												updateMap.put(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL, endPointUrl);
											Set<String> operationTypes = wsdl.getOperationTypes(port, service);
											for (String operation : operationTypes) {
												updateMap.put(BpmnMetaModelExtensionConstants.E_ATTR_OPERATION, operation);
												break;
											}
											break;
										}
										break;
									}
								}
							}
						} catch (Exception e) {
							BpmnUIPlugin.log(e);
						}
					}
				}

			}
		}

	}
}
