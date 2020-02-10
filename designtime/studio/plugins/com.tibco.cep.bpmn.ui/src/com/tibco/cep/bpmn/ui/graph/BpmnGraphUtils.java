package com.tibco.cep.bpmn.ui.graph;

import static com.tibco.cep.diagramming.utils.DiagramUtils.refreshDiagram;
import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverview;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;

import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.editor.GraphSelection;
import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.SupportedProcessPropertiesType;
import com.tibco.cep.bpmn.ui.graph.model.command.AbstractConnectorCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.AbstractDeleteEdgeCommand;
import com.tibco.cep.bpmn.ui.graph.model.command.AbstractDeleteNodeCommand;
import com.tibco.cep.bpmn.ui.graph.model.command.AbstractEdgeCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.AbstractNodeCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.IGraphCommand;
import com.tibco.cep.bpmn.ui.graph.rule.DeleteEdgeRule;
import com.tibco.cep.bpmn.ui.graph.rule.DeleteNodeRule;
import com.tibco.cep.bpmn.ui.transfer.BPMNProcessTransfer;
import com.tibco.cep.bpmn.ui.transfer.ProcessDataFlavour;
import com.tibco.cep.diagramming.tool.EDIT_TYPES;
import com.tibco.cep.studio.core.util.StudioJavaUtil;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.drawing.TSConnector;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEEdgeLabel;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.events.TSESelectionChangeEventData;
import com.tomsawyer.interactive.command.TSCommand;
import com.tomsawyer.interactive.command.TSGroupCommand;
import com.tomsawyer.interactive.service.TSEAllOptionsServiceInputData;
import com.tomsawyer.interactive.swing.editing.tool.TSEPasteTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;

public class BpmnGraphUtils {
	
	private static Clipboard clipboard;

	private static DeleteNodeRule deleteNodeRule;
	private static DeleteEdgeRule deleteEdgeRule;

//	@SuppressWarnings("unused")
//	private static HashMap<EClass, String> nodeNameMap;
	
	/**
	 * @return
	 */
	public static String generateNodeId() {
		return UUID.randomUUID().toString();
    }
		
	/**
	 * @param types
	 * @param site
	 * @param diagramManager
	 */
	public static void editGraph(final EDIT_TYPES types,
			                     final IEditorSite site, 
							     final BpmnDiagramManager diagramManager){
		try{
			SwingUtilities.invokeLater(new Runnable(){
				/* (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				public void run() {
					List<TSENode> selectedNodes = diagramManager.getSelectedNodes();
					List<TSEEdge> selectedEdges = diagramManager.getSelectedEdges();
					@SuppressWarnings("unused")
					List<TSENodeLabel> selectedNodeLabels = diagramManager.getSelectedNodeLabels();
					@SuppressWarnings("unused")
					List<TSEEdgeLabel> selectedEdgeLabels = diagramManager.getSelectedEdgeLabels();
					switch(types){
						case CUT:
							if(!diagramManager.validateCutEdit(selectedNodes, selectedEdges))
								break;
							cutCommand(diagramManager, selectedNodes,selectedEdges);
							break;
						case COPY:
							if(!diagramManager.validateCopyEdit(selectedNodes, selectedEdges))
								break;
							diagramManager.addToEditGraphMap(BPMNProcessTransfer.getInstance().getCopyMap(), selectedNodes,selectedEdges, true);
							diagramManager.getDrawingCanvas().copy(diagramManager.getServiceInputData());
//							BpmnUIPlugin.getDefault().setDiagrammeClipboard(diagramManager.getDrawingCanvas().getCutCopyPasteControl().getClipboard());
							break;
						case PASTE:
							if(!diagramManager.validatePasteEdit(selectedNodes, selectedEdges))
								break;
							
                            if(diagramManager.handleTransitionsEditGraphMap()){
                            	break;
                            }
							
							TSEPasteTool pasteTool = (TSEPasteTool)TSEditingToolHelper.getPasteTool(diagramManager.getDrawingCanvas().getToolManager());
							pasteTool.setServiceInputData(diagramManager.getServiceInputData());
							diagramManager.getDrawingCanvas().getToolManager().setActiveTool(pasteTool);
							break;
						case DELETE:
							if(!diagramManager.validateDeleteEdit(selectedNodes, selectedEdges))
								break;
							confirmDeleteBeforeEdit(diagramManager, selectedNodes, selectedEdges);
							 break;
						}
					if((types == EDIT_TYPES.CUT) || (types == EDIT_TYPES.PASTE) || (types == EDIT_TYPES.DELETE)){
						refreshDiagram(diagramManager);
						refreshOverview(site, true, true);
					}
				}
			});
		}catch(Exception e){
			BpmnUIPlugin.log(e);
		}	
	}
	
	
	
	
	/**
	 * @param diagramManager
	 */
	//Confirmation before performing DELETE operation
	public static void confirmDeleteBeforeEdit(final BpmnDiagramManager diagramManager, final List<TSENode> selectedNodes, 
            final List<TSEEdge> selectedEdges){
		final Shell shell = diagramManager.getEditor().getEditorSite().getShell();
		BpmnUIPlugin.invokeOnDisplayThread(new Runnable(){
			public void run() {
				boolean deleteConfirm = MessageDialog.openQuestion(shell, Messages.getString("delete_title"), Messages.getString("delete_confirm"));
			 	if(deleteConfirm){
			 			deleteCommand(diagramManager,selectedNodes, selectedEdges, false);
			 }
			}
		}, false);
	}
	
	
	
	/**
	 * @param diagramManager
	 */
	public static void deleteCommand(BpmnDiagramManager diagramManager, 
            List<TSENode> selectedNodes, 
            List<TSEEdge> selectedEdges, boolean deleteEmfModelOnly) {
		if(deleteNodeRule == null )
			deleteNodeRule = new DeleteNodeRule(null);
		if(deleteEdgeRule == null)
			deleteEdgeRule = new DeleteEdgeRule(null);
		TSGroupCommand grpCommand = new TSGroupCommand();
		List<TSENode> nodes = new ArrayList<TSENode>(selectedNodes);
		for (TSENode node : nodes) {
			if(node.getUserObject() == null)
				continue;
			if (!deleteNodeRule.isAllowed(new Object[]{node})) {
				Shell shell = diagramManager.getEditor().getEditorSite()
						.getShell();
				MessageDialog.openWarning(shell, com.tibco.cep.bpmn.ui.Messages
						.getString("delete_node_title"), deleteNodeRule.getMessage());
			} else {
				EClass nodeType = (EClass) node
						.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
				if(nodeType != null){
					ENamedElement extType = (EClass) node
							.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
					AbstractNodeCommandFactory cf = (AbstractNodeCommandFactory) ((BpmnDiagramManager) diagramManager)
							.getModelGraphFactory().getCommandFactory(nodeType,
									extType);
					TSCommand cmd = (TSCommand) cf.getCommand(
							IGraphCommand.COMMAND_DELETE, null, node);
					if (cmd != null && cmd instanceof AbstractDeleteNodeCommand) {
						((AbstractDeleteNodeCommand) cmd)
								.setDeleteEmfModelOnly(deleteEmfModelOnly);
					}
					if (cmd != null)
						grpCommand.add(cmd);
				}

			}
		} 
		
		List<TSEEdge> edges = new ArrayList<TSEEdge>(selectedEdges);
		for (TSEEdge edge : edges) {
			if(edge.getUserObject() == null)
				continue;
			if (!deleteEdgeRule.isAllowed(new Object[] { edge })) {
				Shell shell = diagramManager.getEditor().getEditorSite()
						.getShell();
				MessageDialog.openWarning(shell, com.tibco.cep.bpmn.ui.Messages
						.getString("delete_edg_title"), deleteEdgeRule
						.getMessage());
			} else {
				EClass edgeType = (EClass) edge
						.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
				boolean connectedToConnector = false;
				if(edge.getSourceConnector() != null){
					TSConnector sourceConnector = edge.getSourceConnector();
					Object userObject = sourceConnector.getUserObject();
					if (userObject != null && (userObject instanceof EObject)) {
						EObject connec = (EObject) userObject;
						if(connec.eClass().equals(BpmnModelClass.BOUNDARY_EVENT))
							connectedToConnector = true;
					}
				}
				AbstractEdgeCommandFactory cf = (AbstractEdgeCommandFactory) ((BpmnDiagramManager) diagramManager)
						.getModelGraphFactory().getEdgeCommandFactory(edgeType);
				TSCommand cmd = (TSCommand) cf.getCommand(
						IGraphCommand.COMMAND_DELETE, edge, connectedToConnector);
				if (cmd != null && cmd instanceof AbstractDeleteEdgeCommand) {
					((AbstractDeleteEdgeCommand) cmd)
							.setDeleteEmfModelOnly(deleteEmfModelOnly);
				}
				if (cmd != null)
					grpCommand.add(cmd);
			}
		} 
		diagramManager.executeCommand(grpCommand);
	}
	
	
	/**
	 * @param diagramManager
	 * @param selectedNodes
	 * @param selectedEdges
	 * @param isCopy
	 */
	public static void cutCommand(BpmnDiagramManager diagramManager, 
			                      List<TSENode> selectedNodes, 
			                      List<TSEEdge> selectedEdges) {
		

//		
//		//As TomSwayer doesn't have the "CUT" Edges functionality, here is the alternative.
//		//Delete the edges instead.
//		if(selectedEdges.size()>0){
//			 TSEDeleteSelectedCommand com = new TSEDeleteSelectedCommand(diagramManager.getDrawingCanvas());
//			 diagramManager.getDrawingCanvas().getCommandManager().transmit(com);
//		}
		
		diagramManager.addToEditGraphMap(BPMNProcessTransfer.getInstance().getCopyMap(),selectedNodes,selectedEdges, true);
		TSEAllOptionsServiceInputData serviceInputData = diagramManager.getServiceInputData();
		if(serviceInputData != null){
			diagramManager.getDrawingCanvas().copy(diagramManager.getServiceInputData());
			deleteCommand(diagramManager, selectedNodes, selectedEdges,false);
		}
//		diagramManager.getDrawingCanvas().cut(diagramManager.getServiceInputData());

//		BpmnUIPlugin.getDefault().setDiagrammeClipboard(diagramManager.getDrawingCanvas().getCutCopyPasteControl().getClipboard());
	}

	public static void addToEditGraphMap(Map<String, Object> map,
			List<TSENode> selectedNodes, List<TSEEdge> selectedEdges,
			EObjectWrapper<EClass, EObject> processWrapper, boolean isCopy) {
		for (TSENode node : selectedNodes) { 
			EObject flowNode = (EObject) node.getUserObject();
			if(flowNode == null)
				continue;
			try {
				EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper
						.wrap(flowNode);
				String copyId = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				node.setAttribute(BpmnUIConstants.NODE_ATTR_COPY_ID, copyId);
				map.put(copyId, flowNode);
				// handle child nodes and child edges
				TSGraph childGraph = node.getChildGraph();
				if(childGraph != null){
					@SuppressWarnings("unchecked")
					List<TSENode> childNodes = new ArrayList<TSENode>(childGraph.nodeSet);
					@SuppressWarnings("unchecked")
					List<TSEEdge> childEdges = new ArrayList<TSEEdge>(childGraph.edgeSet);
					addToEditGraphMap(map, childNodes, childEdges, processWrapper, isCopy);
				}
				
			} catch (Exception e) {
				BpmnUIPlugin.log(e);
			}
		}
		
		//For Selected Edges
		for (TSEEdge edge : selectedEdges) {
			EObject sequenceFlow = (EObject) edge.getUserObject();
			try {
				EObjectWrapper<EClass, EObject> sequenceFlowWrapper = EObjectWrapper
						.wrap(sequenceFlow);
				String copyId = sequenceFlowWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				edge.setAttribute(BpmnUIConstants.NODE_ATTR_COPY_ID, copyId);
				map.put(copyId, sequenceFlow);
			} catch (Exception e) {
				BpmnUIPlugin.log(e);
			}
		}
		
	}
	
	/**
	 * @param stateEntity
	 */
	public static void removeSequenceFlows(ROEObjectWrapper<EClass, EObject> fe2, EObjectWrapper<EClass, EObject> processWrapper, boolean isCopy){
		if(fe2 != null){
			if (fe2.isInstanceOf(BpmnModelClass.FLOW_ELEMENTS_CONTAINER)){
				for (ROEObjectWrapper<EClass, EObject> fe : fe2.getWrappedEObjectList(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS)){
					if(fe.isInstanceOf(BpmnModelClass.FLOW_NODE)){
//						if(!isCopy){
//							for(StateTransition transtion:((State)s).getIncomingTransitions()){
//								stateMachine.getStateTransitions().remove(transtion);
//							}
//							for(StateTransition transtion:((State)s).getOutgoingTransitions()){
//								stateMachine.getStateTransitions().remove(transtion);
//							}
//						}
//						((State)s).getIncomingTransitions().clear();
//						((State)s).getOutgoingTransitions().clear();
						fe.getListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING).clear();
						fe.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING).clear();

					}
					if(fe.isInstanceOf(BpmnModelClass.FLOW_ELEMENTS_CONTAINER)){
						removeSequenceFlows(fe, processWrapper, isCopy);
					}
				}
			} 
		}
	}
	
	/**
	 * @param shell
	 * @param diagramManager
	 */
	public static void editProcessObjects(final Shell shell, BpmnDiagramManager diagramManager) {
		TSENode[] nodes = new TSENode[diagramManager.getSelectedNodes().size()];
		diagramManager.getSelectedNodes().toArray(nodes);
//		List<EObject> list = new ArrayList<EObject>();
		List<ProcessDataFlavour> prlist = new ArrayList<ProcessDataFlavour>();
		for (TSENode node : nodes) {
			if (node != null) {
				Object object = node.getUserObject();
				if (object != null) {
					EObject eObject = (EObject) object;
					EObjectWrapper<EClass, EObject> modelRoot = diagramManager.getModelController().getModelRoot();
					String projName = diagramManager.getProject().getName();
					EClass nodeType = (EClass) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
					EClass extType = (EClass) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
					String nodeName = getNodeName(nodeType, extType);
					EObject copyObj = BpmnModelUtils.createEObjectCopy(eObject, modelRoot, projName, nodeName).getEInstance(); 
//					list.add(copyObj);
					TSENode cloned =(TSENode)node.clone();
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(copyObj);
					String name = "";
					if(wrap.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME))
						name = (String)wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
					else if (ExtensionHelper.isValidDataExtensionAttribute(
							wrap,
							BpmnMetaModelExtensionConstants.E_ATTR_LABEL)){
						EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(wrap);
						if (valWrapper != null)
							name = valWrapper
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
					}
					if(name != null && !name.trim().isEmpty())
						refreshLabel(cloned, name);
					
					prlist.add(new ProcessDataFlavour(cloned, copyObj, diagramManager));
				}
			}
		}
//		EObject[] objs = new EObject[list.size()]; 
//		list.toArray(objs);
		ProcessDataFlavour[] proobjs = new ProcessDataFlavour[prlist.size()]; 
		prlist.toArray(proobjs);
		
		Object[] contents = new Object[] { proobjs };
		if (getProcessClipboard() != null) {
			getProcessClipboard().setContents(contents, new  Transfer[]{BPMNProcessTransfer.getInstance()});
		}
	}
	
	
	private static void refreshLabel(TSENode node, String nodeName){
		EClass nodeType = (EClass) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		EClass nodeExtType = (EClass) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
		String toolId = (String)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		String resUrl = (String)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
		AbstractNodeUIFactory nodeUIFactory ;
		if (nodeExtType != null) {
			ExpandedName classSpec = BpmnMetaModel.INSTANCE
					.getExpandedName(nodeExtType);
			nodeUIFactory = BpmnGraphUIFactory.getInstance(null)
					.getNodeUIFactory(nodeName, resUrl, toolId, nodeType, classSpec);
		}
		else
			nodeUIFactory = BpmnGraphUIFactory.getInstance(null).getNodeUIFactory(nodeName, resUrl,toolId, nodeType);
		// ((TSENodeLabel)(((TSENode)fTSENode).labels().get(0))).setDefaultOffset();
		nodeUIFactory.addNodeLabel(node, nodeName);
	}
	
	/**
	 * @param shell
	 * @param diagramManager
	 */
	public static void pasteClipBoardProcessObjects(IEditorPart editor) {
		if (getProcessClipboard() == null) {
			return;
		}
		Object transferContents = getProcessClipboard().getContents(BPMNProcessTransfer.getInstance());
		if (transferContents != null) {
			Object[] contents = (Object[])transferContents;
			for (@SuppressWarnings("unused") Object object:contents) {
				//TODO: paste Objects to the canvas
			}
		}
	}
	
	/**
	 * @param updateList
	 * @param fTSENode
	 * @param bpmnGraphDiagramManager
	 */
	public static void fireUpdate(Map<String, Object> updateList, TSENode fTSENode, BpmnDiagramManager bpmnGraphDiagramManager){
		if(updateList.size() == 0) {
			return ;
		}
		EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		ENamedElement extType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);

		AbstractNodeCommandFactory cf = (AbstractNodeCommandFactory) bpmnGraphDiagramManager.getModelGraphFactory().getCommandFactory(nodeType,extType);
		IGraphCommand<TSENode> cmd = cf.getCommand(IGraphCommand.COMMAND_UPDATE, fTSENode, PropertiesType.GENERAL_PROPERTIES, updateList);
		bpmnGraphDiagramManager.executeCommand((TSCommand)cmd);
	}
	
	
	public static void fireUpdate(Map<String, Object> updateList, TSEConnector tseConnector, BpmnDiagramManager bpmnGraphDiagramManager){
		if(updateList.size() == 0) {
			return ;
		}
		EClass nodeType = (EClass) tseConnector.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		ENamedElement extType = (EClass) tseConnector.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);

		AbstractConnectorCommandFactory cf = (AbstractConnectorCommandFactory) bpmnGraphDiagramManager.getModelGraphFactory().getCommandFactory(nodeType,extType);
		IGraphCommand<TSEConnector> cmd = cf.getCommand(IGraphCommand.COMMAND_UPDATE, tseConnector, PropertiesType.GENERAL_PROPERTIES, updateList);
		bpmnGraphDiagramManager.executeCommand((TSCommand)cmd);
	}
	
	
	/**
	 * @param updateList
	 * @param fTSENode
	 * @param bpmnGraphDiagramManager
	 */
	public static void fireUpdate(Map<String, Object> updateList, TSEEdge fTSEEdge, BpmnDiagramManager bpmnGraphDiagramManager){
		if(updateList.size() == 0) {
			return ;
		}
		EClass nodeType = (EClass) fTSEEdge.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		ENamedElement extType = (EClass) fTSEEdge.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);

		AbstractEdgeCommandFactory cf = (AbstractEdgeCommandFactory) bpmnGraphDiagramManager.getModelGraphFactory().getCommandFactory(nodeType,extType);
		IGraphCommand<TSEEdge> cmd = cf.getCommand(IGraphCommand.COMMAND_UPDATE, fTSEEdge, PropertiesType.GENERAL_PROPERTIES, updateList);
		bpmnGraphDiagramManager.executeCommand((TSCommand)cmd);
	}
	
	/**
	 * @param editor
	 * @return
	 */
	public static Clipboard setProcessClipboard(IEditorPart editor) {
		if (clipboard == null) {
			if (editor.getSite() != null) {
				Shell shell = editor.getEditorSite().getWorkbenchWindow().getShell();
				clipboard = new Clipboard(shell.getDisplay());
			}
		}
		return clipboard;
	}
	
	/**
	 * @return
	 */
	public static Clipboard getProcessClipboard() {
		return clipboard;
	}
	
	public static void setWorkBenchMultiSelection(final BpmnDiagramManager manager,final TSESelectionChangeEventData eventData,final IEditorPart editor,final boolean causedDueToWindowActivation) {
		Display.getDefault().syncExec(new Runnable(){

			@Override
			public void run() {
				
				GraphSelection graphSelection = new GraphSelection();
				if(isGraphSelected(manager)){
					graphSelection.add(getSelectedGraph(manager));
				}
				graphSelection.addAll(manager.getSelectedNodes());
				graphSelection.addAll(manager.getSelectedEdges());
				
				@SuppressWarnings("unused")
				final ISelection selection = editor.getSite().getWorkbenchWindow().getSelectionService().getSelection();
				final IEditorInput editorInput = editor.getEditorInput();
				final IFile file = (IFile) editorInput.getAdapter(IFile.class);
				final IProject project = (IProject) editor.getAdapter(IProject.class);
				for(TSEObject object: graphSelection.toList()){
					if(object instanceof TSGraphObject) {
						((TSGraphObject)object).setAttribute(IFile.class.getName(), file);
						((TSGraphObject)object).setAttribute(IProject.class.getName(), project);
					}
				}
				
				final ISelectionProvider selectionProvider = editor.getEditorSite().getSelectionProvider();
				final ISelection currentSelection = selectionProvider.getSelection();
				if(currentSelection instanceof IGraphSelection){
					IGraphSelection gs = ((IGraphSelection)currentSelection);
					if(gs != null && gs.equals(graphSelection)){
						return;
					}
				}
				
				selectionProvider.setSelection(graphSelection);
			}
			
		});
		
	}
	
	/**
	 * @param manager
	 * @return
	 */
	public static boolean isGraphSelected(final BpmnDiagramManager manager) {
		TSEGraph selectedGraph = null;
		Iterator<?> i = manager.getGraphManager().graphs().iterator();
		while (i.hasNext()) {
			selectedGraph = (TSEGraph) i.next();
			if (selectedGraph.isSelected()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param manager
	 * @return
	 */
	public static TSEGraph getSelectedGraph(final BpmnDiagramManager manager) {
		TSEGraph selectedGraph = null;
		Iterator<?> i = manager.getGraphManager().graphs().iterator();
		while (i.hasNext()) {
			selectedGraph = (TSEGraph) i.next();
			if (selectedGraph.isSelected()) {
				return selectedGraph;
			}
		}
		return selectedGraph;
	}
	
    /**
	 * Delegating workbench selection on Studio Elements selection
	 * @param object
	 * @param editor
	 */
	static public void setWorkbenchSelection(final TSEObject object,final IEditorPart editor , final boolean causedDueToWindowActivation){
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run() {
				try{
					ISelection selection = editor.getSite().getWorkbenchWindow().getSelectionService().getSelection();
					IEditorInput editorInput = editor.getEditorInput();
					IFile file = (IFile) editorInput.getAdapter(IFile.class);
					IProject project = (IProject) editor.getAdapter(IProject.class);
					if(object instanceof TSGraphObject) {
						((TSGraphObject)object).setAttribute(IFile.class.getName(), file);
						((TSGraphObject)object).setAttribute(IProject.class.getName(), project);
					}
					ISelectionProvider selectionProvider = editor.getEditorSite().getSelectionProvider();
					ISelection currentSelection = selectionProvider.getSelection();
					if(currentSelection instanceof IGraphSelection){
						Object obj = ((IGraphSelection)currentSelection).getGraphObject();
						if(obj != null && obj.equals(object)){
							return;
						}
					}
					
					 TSGraphObject selectionObj = ((BpmnDiagramManager)((BpmnEditor)editor).getDiagramManager()).getCurrentSelection();
					if(causedDueToWindowActivation && selectionObj != null && !selectionObj.equals(object)){
						return ;// while  graph editor is activating , if some other graph object is seleted in between,  fire event for current graph selection
					}
					
//					selection = new StructuredSelection(object);
					selection = new GraphSelection(object);
					selectionProvider.setSelection(selection);
					//((MultiPageSelectionProvider)selectionProvider).firePostSelectionChanged(new SelectionChangedEvent(selectionProvider, selection));
				}
				catch(Exception e){
					BpmnUIPlugin.log(e);
				}

			}});
	}
    
	public static ImageIcon getImageIcon(EObjectWrapper<EClass, EObject> propDef) {
		return EditorUtils.createImageIcon(SupportedProcessPropertiesType.getImageIcon(propDef), SupportedProcessPropertiesType.getResourceType(propDef));
	}
	
	
//	@SuppressWarnings("serial")
//	public static DefaultTableCellRenderer getCellRenderer(final boolean isNonBrowseFileSystem){
//		return new DefaultTableCellRenderer() {
//			@SuppressWarnings({ "unchecked", "rawtypes" })
//			public Component getTableCellRendererComponent(
//					JTable table, Object value,
//					boolean isSelected, boolean hasFocus,
//					int row, int col) {
//				JLabel label = (JLabel) super
//						.getTableCellRendererComponent(table,
//								value, isSelected, hasFocus, row,
//								col);
//				label
//						.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
//				DefaultTableModel model = (DefaultTableModel) TableModelWrapperUtils.getActualTableModel(table.getModel());
//				if (value instanceof EObjectWrapper) {
//					ImageIcon icon =getImageIcon((EObjectWrapper)value);
//					label.setText(icon.getDescription());
//					label.setIcon(icon);
//				}else{
//					if(!isNonBrowseFileSystem){
//						EObjectWrapper prop_types = (EObjectWrapper)model.getValueAt(row, 3);
//						ImageIcon icon = getImageIcon(prop_types);
//						label.setText(icon.getDescription());
//						label.setIcon(icon);
//					}
//				}
//				return label;
//			}
//		};
//	}
	
	public static String getNodeName(EClass nodeType, EClass extType){
		String nodeNme = null;
		if(nodeType.equals(BpmnModelClass.RULE_FUNCTION_TASK))
			nodeNme = com.tibco.cep.bpmn.ui.Messages.getString("title.rulefunction");
		else if(nodeType.equals(BpmnModelClass.BUSINESS_RULE_TASK))
			nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.businessrule.task");
		else if(nodeType.equals(BpmnModelClass.SEND_TASK))
			nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.send.task");
		else if(nodeType.equals(BpmnModelClass.RECEIVE_TASK))
			nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.receive.task");
		else if(nodeType.equals(BpmnModelClass.INFERENCE_TASK))
			nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.inference.task");
		else if(nodeType.equals(BpmnModelClass.SERVICE_TASK))
			nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.webservice.task");
		else if(nodeType.equals(BpmnModelClass.SUB_PROCESS))
			nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.subprocess");
		else if(nodeType.equals(BpmnModelClass.MANUAL_TASK))
			nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.manual");
		else if(nodeType.equals(BpmnModelClass.CALL_ACTIVITY))
			nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.callactivity");
		else if(nodeType.equals(BpmnModelClass.PARALLEL_GATEWAY))
			nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.parallel");
		else if(nodeType.equals(BpmnModelClass.EXCLUSIVE_GATEWAY))
			nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.exclusive");
		else if(nodeType.equals(BpmnModelClass.INCLUSIVE_GATEWAY))
			nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.inclusive");
		else if(nodeType.equals(BpmnModelClass.COMPLEX_GATEWAY))
			nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.complex");
		else if(BpmnModelClass.START_EVENT.isSuperTypeOf(nodeType)){
			if(extType == null || extType.equals(""))
				nodeNme = com.tibco.cep.bpmn.ui.Messages.getString("title.start.event");
			else if(extType.equals(BpmnModelClass.MESSAGE_EVENT_DEFINITION))
				nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.message.start.event");
			else if(extType.equals(BpmnModelClass.TIMER_EVENT_DEFINITION))
				nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.timer.start.event");
			else if(extType.equals(BpmnModelClass.SIGNAL_EVENT_DEFINITION))
				nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.signal.start.event");
		}else if (BpmnModelClass.END_EVENT.isSuperTypeOf(nodeType)){
			if(extType == null || extType.equals(""))
				nodeNme = com.tibco.cep.bpmn.ui.Messages.getString("title.end.event");
			else if(extType.equals(BpmnModelClass.MESSAGE_EVENT_DEFINITION))
				nodeNme = com.tibco.cep.bpmn.ui.Messages.getString("title.message.end.event");
			else if(extType.equals(BpmnModelClass.ERROR_EVENT_DEFINITION))
				nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.error.end.event");
			else if(extType.equals(BpmnModelClass.SIGNAL_EVENT_DEFINITION))
				nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.signal.end.event");
		}else if (BpmnModelClass.INTERMEDIATE_CATCH_EVENT.isSuperTypeOf(nodeType)){
			if(extType == null || extType.equals(""))
				nodeNme =null;
			else if(extType.equals(BpmnModelClass.MESSAGE_EVENT_DEFINITION))
				nodeNme = com.tibco.cep.bpmn.ui.Messages.getString("title.catch.message.intermediate");
			else if(extType.equals(BpmnModelClass.ERROR_EVENT_DEFINITION))
				nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.catch.error.intermediate");
			else if(extType.equals(BpmnModelClass.SIGNAL_EVENT_DEFINITION))
				nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.catch.signal.intermediate");
		}else if (BpmnModelClass.INTERMEDIATE_THROW_EVENT.isSuperTypeOf(nodeType)){
			if(extType == null || extType.equals(""))
				nodeNme =com.tibco.cep.bpmn.ui.Messages.getString("title.throw.intermediate");
			else if(extType.equals(BpmnModelClass.MESSAGE_EVENT_DEFINITION))
				nodeNme = com.tibco.cep.bpmn.ui.Messages.getString("title.throw.message.intermediate");
			else if(extType.equals(BpmnModelClass.SIGNAL_EVENT_DEFINITION))
				nodeNme =  com.tibco.cep.bpmn.ui.Messages.getString("title.throw.signal.intermediate");
		}
		
	
		return nodeNme ;
	}
	
	public static TSEEdge getEdge(TSENode node, String id){
		TSEEdge found = null;;
		if(node!= null){
			List<?> outEdges = node.outEdges();
			for (Object object : outEdges) {
				if(object instanceof TSEEdge ){
					TSEEdge edge = (TSEEdge)object;
					EObject userObject = (EObject)edge.getUserObject();
					if(userObject != null){
						EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject);
						String seqId = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
						if(seqId.equals(id)){
							found = edge; 
							break;
						}
					}
				}
			}
		}
		
		return found;
	}
	public static boolean isMethodPresentInJavaSrc(IResource resource,String methodName){
		if (!StudioJavaUtil.isJavaSource((IFile)resource)) {
			return false;
		}
		try {
			ICompilationUnit cu = JavaCore
					.createCompilationUnitFrom((IFile) resource);
			if (cu != null) {
				cu.getImports();
				IType[] types = cu.getTypes();
				for (IType iType : types) {
					IMethod[] methods = iType.getMethods();
					for (IMethod iMethod : methods) {
						int modifier = iMethod.getFlags();
						if (Modifier.isPublic(modifier)) {

							boolean flag = false;
							List<Annotation> annotationList = StudioJavaUtil
									.getAnnotations(iMethod);
							for (Annotation annotation : annotationList) {
								if (StudioJavaUtil
										.getAnnotationName(annotation)
										.equals(BpmnCommonIndexUtils.ANNOTATION_BPMN_JAVA_CLASS_METHOD_TASK)) {
									if(iMethod.getElementName().equals(methodName))
										return true;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false ;
		}
		return false;
	}
	
}