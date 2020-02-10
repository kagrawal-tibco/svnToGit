package com.tibco.cep.bpmn.ui.editor;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.BpmnProcessLoader;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.PoolLaneNodeUIFactory;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.diagrams.IProjectDiagramContributor;
import com.tibco.cep.studio.ui.diagrams.ProjectDiagramManager;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEPolylineEdgeUI;

public class BpmnDiagramContributor implements IProjectDiagramContributor {
	
	private ProjectDiagramManager diagramManager;

	private HashMap<String, Set<TSENode>> processChildNodesMap;	

	private List<TSEEdge> processLinks;
	private List<TSENode> processes;

	private boolean collapseProcesses = false;

	private BpmnProcessLoader processLoader;

	public static final String PROCESS = " Processes";

	@SuppressWarnings("unused")
	private static final TSEObject TSEConnector = null;
	
	@Override
	public void initialize() {
		// BpmnCorePlugin.log("Initializing BpmnDiagramContributor");
		this.processLinks = new LinkedList<TSEEdge>();			
		this.processes = new LinkedList<TSENode>();
	}

	@Override
	public void clear() {
		this.processLinks.clear();
		this.processes.clear();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void generateContent(ProjectDiagramManager projectDiagramManager) {
		this.diagramManager = projectDiagramManager;
		BpmnLayoutManager bpmnLayoutManager = new BpmnLayoutManager(diagramManager);
		this.processLoader = new BpmnProcessLoader(diagramManager.getGraphManager(),bpmnLayoutManager);
		processChildNodesMap = new HashMap<String, Set<TSENode>>();
		
		EObject index = BpmnIndexUtils.getIndex(this.diagramManager.getProject());
		if (index == null) {
			BpmnCorePlugin.debug("BPMN index is null!");
			return;
		}
		DefaultBpmnIndex bpmnOntology = new DefaultBpmnIndex(index);
		// BpmnCorePlugin.log("Project has " + bpmnOntology.getAllProcesses().size() + " processes.");
		Iterator<EObject> iter = bpmnOntology.getAllProcesses().iterator();
		while (iter.hasNext()) {
			final EObject pObj = iter.next();
			if (EObjectWrapper.wrap(pObj).isInstanceOf(BpmnModelClass.PROCESS)) {
				this.createProcess(pObj, bpmnLayoutManager);
			}
			else {
				BpmnCorePlugin.debug("Found something other than a process!");
			}
		}
		
		if (processChildNodesMap != null) {
			Set<String> keySet = processChildNodesMap.keySet();
			for (String string : keySet) {
				Set<TSENode> set = processChildNodesMap.get(string);
				for (TSENode node : set) {
					EObject userObject = (EObject)node.getUserObject();
					this.addLinksToOtherArtifacts(node, userObject, bpmnLayoutManager);
					if(BpmnModelClass.ACTIVITY.isSuperTypeOf( userObject.eClass())){
						List connectors = node.connectors();
						for (Object object : connectors) {
							if (object instanceof TSEConnector) {
								TSEConnector connector = (TSEConnector) object;
								Object obj = connector.getUserObject();
								if(obj != null && (obj instanceof EObject)){
									this.addLinksToOtherArtifacts(connector,(EObject)obj, bpmnLayoutManager);
								}
							}
						}	
					}
				}
			}
			diagramManager.setProcessChildNodesMap(processChildNodesMap);
		}
	}

	public TSENode createProcess(EObject processObj, BpmnLayoutManager bpmnLayoutManager) {
		EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(processObj);
		String name = (String) processWrapper.getAttribute("name");
		// BpmnCorePlugin.log("Creating process: " + name);

		String processPath = processWrapper.getAttribute("folder") + "/" + name;
		if (this.diagramManager.getNodeMap().containsKey(processPath)) {
			return this.diagramManager.getNodeMap().get(processPath);
		}
		// XYZ
		TSENode tsProcess = (TSENode) this.diagramManager.getGraphManager().getMainDisplayGraph().addNode();
		tsProcess.setUserObject(processObj);
		// XYZ
		TSGraph childGraph = this.diagramManager.getGraphManager().addGraph();
		tsProcess.setChildGraph(childGraph);
		
		this.processLoader.clearCache();
		this.processLoader.setChangeMainDisplayGraph(false);
		this.processLoader.setShowPoolLaneNode(shouldShowPool(processWrapper));
		this.processLoader.loadProcess(processWrapper, childGraph, new NullProgressMonitor());
		
		setUiToProcessNode(tsProcess, bpmnLayoutManager);
		this.processes.add(tsProcess);
		this.diagramManager.getNodeMap().put(processPath, tsProcess);		
		
		Set<TSENode> nodeSet = new HashSet<TSENode>();
		nodeSet.add(tsProcess);
		Collection<TSEObject> allNodes = processLoader.getNodeRegistry().getAllNodes();
		for (TSEObject object : allNodes) {
			if(object instanceof TSENode)
				nodeSet.add((TSENode)object);
		}
		processChildNodesMap.put(processPath, nodeSet);

		
		this.diagramManager.getLayoutManager().setLeftToRightOrthHierarchicalOptions((TSEGraph) tsProcess.getChildGraph());
		if (this.collapseProcesses) {
			TSENestingManager.getManager(this.diagramManager.getGraphManager()).collapseNode(tsProcess);
		}
		else {
			TSENestingManager.getManager(this.diagramManager.getGraphManager()).expandNode(tsProcess);
		}

		return tsProcess;
	}
	
	private void setUiToProcessNode(TSENode tsProcess, BpmnLayoutManager bpmnLayoutManager) {
		PoolLaneNodeUIFactory uiFactory = (PoolLaneNodeUIFactory) BpmnGraphUIFactory
				.getInstance(bpmnLayoutManager)
				.getNodeUIFactory("","","", BpmnMetaModel.LANE,(Object[])null);
		uiFactory.decorateNode(tsProcess);
		uiFactory.layoutNode(tsProcess);
	}

	private void addLinksToOtherArtifacts(TSEObject processNode, EObject flowNode, BpmnLayoutManager bpmnLayoutManager) {
		// once we get a node in the lane, this is how we process it:
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNode);
		EObjectWrapper<EClass, EObject> valueWrapper =
			ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
		// "rulefunction" "decisiontable" "rule" "service"
		if (valueWrapper != null) {
			if(processNode instanceof TSENode){
				if (flowNodeWrapper.isInstanceOf(BpmnModelClass.RULE_FUNCTION_TASK)) {
					String taskName = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION);
					if (taskName != null && !taskName.isEmpty()) {
						// find corresponding object (RF for example)
						RuleElement re = this.diagramManager.getRuleElement(taskName);
						if (re == null) {
							BpmnCorePlugin.debug("Could not find R/RF:" + taskName);
							return;
						}
						else {
							this.diagramManager.createLinkToRuleElement((TSENode)processNode, re, "calls", this.processLinks);
						}
					}
					else {
						BpmnCorePlugin.debug("null task name!");
					}
				}
				else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.INFERENCE_TASK)) {
					@SuppressWarnings("unchecked")
					Collection<String> rules = (Collection<String>) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULES);
					if (rules != null && !rules.isEmpty()) {
						for (String ruleName : rules) {
							// find corresponding object (Rule)
							RuleElement re = this.diagramManager.getRuleElement(ruleName);
							if (re == null) {
								BpmnCorePlugin.debug("Could not find Rule:" + ruleName);
							}
							else {
								this.diagramManager.createLinkToRuleElement((TSENode)processNode, re, "calls", this.processLinks);
							}
						}
						return;
					}
					else {
						BpmnCorePlugin.debug("no rules attached to !" + flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID));
					}
				}
				else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.START_EVENT) ||
						flowNodeWrapper.isInstanceOf(BpmnModelClass.END_EVENT) ||
						flowNodeWrapper.isInstanceOf(BpmnModelClass.SEND_TASK) ||
						flowNodeWrapper.isInstanceOf(BpmnModelClass.RECEIVE_TASK) ||
						flowNodeWrapper.isInstanceOf(BpmnModelClass.CATCH_EVENT) ||
						flowNodeWrapper.isInstanceOf(BpmnModelClass.THROW_EVENT)) {

					if (valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT) != null) {
						String eventString = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
						Entity eventEntity = IndexUtils.getEntity(this.diagramManager.getProjectName(), eventString);
						if (eventEntity == null) {
							BpmnCorePlugin.debug("Could not find event:" + eventString);
							return;
						}
						else {
							this.diagramManager.createLinkToEntity((TSENode)processNode,eventEntity, "onEvent", this.processLinks);
						}
					}
					else {
						BpmnCorePlugin.debug("No 'event' property defined in EMF model!");
					}
				}else if (
						flowNodeWrapper.isInstanceOf(BpmnModelClass.SERVICE_TASK)) {

					if (valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_WSDL) != null) {
						String eventString = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SERVICE);
						Entity eventEntity = IndexUtils.getEntity(this.diagramManager.getProjectName(), eventString);
						if (eventEntity == null) {
							BpmnCorePlugin.debug("Could not find service task:" + eventString);
							return;
						}
						else {
							this.diagramManager.createLinkToEntity((TSENode)processNode,eventEntity, "onService", this.processLinks);
						}
					}
					else {
						BpmnCorePlugin.debug("No 'service' property defined in EMF model!");
					}
				}
				else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.BUSINESS_RULE_TASK)) {
					// TODO
					String dtPath = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VIRTUALRULEFUNCTION);
					if (dtPath != null) {
						TSENode dtNode = this.diagramManager.getNodeMap().get(dtPath);
						if (dtNode != null) {
							this.diagramManager.createLink((TSENode)processNode, dtNode, TSEPolylineEdgeUI.LINE_STYLE_DOT, "calls");
						}
						else {
							BpmnCorePlugin.debug("Decision Table not found in project analyzer model.");
						}
					}
				}
				else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.CALL_ACTIVITY)) {
					// we are invoking yet another process here
					// TODO: need EObject reference to what we're calling, and String path
					EObject attribute = (EObject) flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_CALLED_ELEMENT);
					if (attribute != null) {
						EObjectWrapper<EClass, EObject> processWrap = EObjectWrapper.wrap(attribute);
						String folder = processWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
						String name = processWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
						String calledProcessPath = folder + IPath.SEPARATOR + name;
						TSENode calledProcessNode = this.diagramManager.getNodeMap().get(calledProcessPath);
						if (calledProcessNode == null) {
							calledProcessNode = this.createProcess(processWrap.getEInstance(), bpmnLayoutManager);
						}

						if (calledProcessNode != null) {
							this.diagramManager.createLink((TSENode)processNode, calledProcessNode, TSEPolylineEdgeUI.LINE_STYLE_DOT, "calls");
						}
						else {
							BpmnCorePlugin.debug("call activity referenced project not found in project analyzer model.");
						}
					}
				}else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.PROCESS)) {
					String conceptPath = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOB_DATA_CONCEPT);
					if (conceptPath != null) {
						Entity conceptEntity = IndexUtils.getEntity(this.diagramManager.getProjectName(), conceptPath);
						if (conceptEntity != null) {
							this.diagramManager.createLinkToEntity((TSENode)processNode,conceptEntity, "jobDataConcept", this.processLinks);
						}
						else {
							BpmnCorePlugin.debug("concept not found in project analyzer model.");
						}
					}
				}
				else {
					BpmnCorePlugin.debug("WARNING: Unknown process node type: " + flowNodeWrapper.getEClassType());
				}
			}else if(processNode instanceof TSEConnector){
				if (flowNodeWrapper.isInstanceOf(BpmnModelClass.BOUNDARY_EVENT)) {

					if (valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT) != null) {
						String eventString = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
						Entity eventEntity = IndexUtils.getEntity(this.diagramManager.getProjectName(), eventString);
						if (eventEntity == null) {
							BpmnCorePlugin.debug("Could not find event:" + eventString);
							return;
						}
						else {
							TSEConnector connector = (TSEConnector)processNode;
							this.diagramManager.createConnectorLinkToEntity((TSENode)connector.getOwner(), connector, eventEntity, "onEvent", this.processLinks);
						}
					}
					else {
						BpmnCorePlugin.debug("No 'event' property defined in EMF model!");
					}
				}
			}
		}
		else {
			BpmnCorePlugin.debug("null value wrapper!!!->"+flowNode.eClass().getName());
		}
	}

	@Override
	public void addDiagramInfo(HashMap<String, Integer> defintionsMap) {
		defintionsMap.put(PROCESS,  this.processes.size());
	}

	@Override
	public void appendDiagramLayoutInfo(StringBuilder builder) {
		builder.append(this.processes.size());
		builder.append(" processes");
	}

	@Override
	public void setShowLinks(boolean showLinks) {
		if (!showLinks) {
			this.diagramManager.showLinks(this.processLinks, true);
		}
		else {
			this.diagramManager.showLinks(this.processLinks, false);
		}
	}

	@Override
	public void setShowNodes(boolean showNodes) {
		if (!showNodes) {
			this.diagramManager.showObjects(this.processes, true);
		}
		else {
			this.diagramManager.showObjects(this.processes, false);
		}
	}

	@Override
	public void setShowNodesExpanded(boolean showExpanded) {
		this.collapseProcesses = !showExpanded;

		if (showExpanded) {
			for (TSENode node : this.processes) {
				TSENestingManager.getManager(this.diagramManager.getGraphManager()).expandNode(node);
			}
		}
		else {
			for (TSENode node : this.processes) {
				TSENestingManager.getManager(this.diagramManager.getGraphManager()).collapseNode(node);
			}
		}		
	}
	
	private boolean shouldShowPool(EObjectWrapper<EClass, EObject> process){
		EList<EObject>laneSets = process.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANE_SETS);
		boolean showPool= false;
		for(EObject laneSetObj:laneSets) {
			EObjectWrapper<EClass, EObject> laneSet = EObjectWrapper.wrap(laneSetObj);
			EList<EObject> lanes = laneSet.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
			showPool= (laneSets.size() > 1 || lanes.size() >1);
			break;
		}
		return showPool;
	}
	
	@Override
	public String getContentType() {
		return PROCESS;
	}	

}
