package com.tibco.cep.bpmn.ui.graph.rule;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graph.TSNode;
import com.tomsawyer.graphicaldrawing.TSEEdge;

/**
 * 
 * @author majha
 *
 */
public class TransferRule extends AbstractDiagramRule implements DiagramRule{

	public TransferRule(DiagramRuleSet ruleSet) {
		super(ruleSet);
	}
	
	@Override
	public boolean isAllowed(Object[] args) {
		TSGraph targetGraph = (TSGraph) args[0];
		List<?> draggedObjects = (List<?>) args[1];
		Collection<TSNode> nodes = getNodes(draggedObjects);
		boolean allowed = true;
		
		if(!isValidRegionGraph(targetGraph, draggedObjects)){
			allowed= false;
		}else if(isPartialTransfer(targetGraph, nodes)){
			allowed= false;
		}

		/* example code
		Collection<TSNode> nodes = getNodes(draggedObjects);
		for(TSNode n: nodes) {
			for(Iterator it = n.inAndOutEdgeIterator();it.hasNext();) {
				TSEdge edge = (TSEdge) it.next();
				if(edge.isConnected() && edge.isIntergraphEdge()) {
				}
			}
		}
		*/
		return allowed;
	}
	
	
	Collection<TSEdge> getEdges(List<?> draggedObjects) {
		Set<TSEdge> edges = new HashSet<TSEdge>();
		for(Object o: draggedObjects) {
			if(o instanceof TSEdge) {
				edges.add((TSEdge) o);
			}
		}
		return edges;
	}
	
	Collection<TSNode> getNodes(List<?> draggedObjects) {
		Set<TSNode> nodes = new HashSet<TSNode>();
		for(Object o: draggedObjects) {
			if(o instanceof TSNode) {
				nodes.add((TSNode) o);
			}
		}
		return nodes;
	}
	
	Collection<TSGraph> getGraphs(List<?> draggedObjects) {
		Set<TSGraph> graphs = new HashSet<TSGraph>();
		for(Object o: draggedObjects) {
			if(o instanceof TSGraph) {
				graphs.add((TSGraph) o);
			}
		}
		return graphs;
	}
	
	

	
	// if a edge is being dragged for transfer then the 2 nodes connected to the
	// edge should be in the dragged node list.
	@SuppressWarnings("rawtypes")
	private boolean isPartialTransfer(TSGraph targetGraph, Collection<TSNode> nodes){
		boolean partialTransfer = false;
		for (TSNode node : nodes) {
			List inEdges = node.inEdges();
			for (Object object : inEdges) {
				TSEEdge edge = (TSEEdge)object;
				TSNode sourceNode = edge.getSourceNode();
				if(!nodes.contains(sourceNode) && !isValidInterGraphConnection(targetGraph, sourceNode.getOwnerGraph())){
					partialTransfer = true;
					break;
				}
			}
			List outEdges = node.outEdges();
			for (Object object : outEdges) {
				TSEEdge edge = (TSEEdge)object;
				TSNode targetNode = edge.getTargetNode();
				if(!nodes.contains(targetNode) && !isValidInterGraphConnection(targetGraph, targetNode.getOwnerGraph())){
					partialTransfer = true;
					break;
				}
			}
		}
		
		return partialTransfer;
	}
	
	// for each node if they have associated edges then if those edges will turn into
	// intergraph edge after the drop is complete and will conflict with the following bpmn rule		
	//    BPMN:Note that if a Sub-Process has been expanded within a Diagram, the objects within the Sub-Process cannot be 
	//    connected to objects outside of the Sub-Process. Nor can Sequence Flow cross a Pool boundary.
	// Therefore all the intergraph edges need to be removed.
	private boolean isValidInterGraphConnection(TSGraph targetGraph,
			TSGraph sourceGraph) {
		EObject targetGraphObj = (EObject) targetGraph.getUserObject();
		EObject souceGraphObj = (EObject) sourceGraph.getUserObject();
		if(targetGraph == sourceGraph){
			return true;
		}else if ((BpmnModelClass.SUB_PROCESS.isSuperTypeOf(targetGraphObj.eClass())
				|| BpmnModelClass.SUB_PROCESS.isSuperTypeOf(souceGraphObj
						.eClass())) ||
						(BpmnModelClass.PROCESS.isSuperTypeOf(targetGraphObj.eClass())
								|| BpmnModelClass.PROCESS.isSuperTypeOf(souceGraphObj
										.eClass()))) {
			return false;
		}

		return true;
	}

	// all dragged objects should not have the same parent graph as target graph
	//also dragged object can not be dropped outside of pool
	private boolean isValidRegionGraph(TSGraph graph, List<?> draggedObjects) {
		Object graphObj = graph.getUserObject();
		if(graphObj instanceof EObject) {
			EObject userObj = (EObject) graphObj;
			if (!(userObj != null
					&& (BpmnModelClass.LANE.isSuperTypeOf(userObj.eClass())
					|| BpmnModelClass.SUB_PROCESS.isSuperTypeOf(userObj.eClass())))) {
				return false;
			}		
		}

		for (Object object : draggedObjects) {
			TSGraphObject obj = (TSGraphObject)object;
			if(obj.getOwnerGraph() == graph)
				return false;
		}
		return true;
	}
}
