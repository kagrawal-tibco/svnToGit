package com.tibco.cep.decision.tree.ui.actions;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.tree.common.model.DecisionTree;
import com.tibco.cep.decision.tree.common.model.ModelFactory;
import com.tibco.cep.decision.tree.common.model.edge.Edge;
import com.tibco.cep.decision.tree.common.model.edge.EdgeFactory;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;
import com.tibco.cep.decision.tree.common.model.node.action.Action;
import com.tibco.cep.decision.tree.common.model.node.action.ActionFactory;
import com.tibco.cep.decision.tree.common.model.node.condition.BoolCond;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionFactory;
import com.tibco.cep.decision.tree.common.model.node.terminal.Start;
import com.tibco.cep.decision.tree.common.model.node.terminal.TerminalFactory;
import com.tibco.cep.decision.tree.ui.editor.DecisionTreeEditorInput;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;

/*
 @author ssailapp
 @date Sep 21, 2011
 */

public class DecisionTableTreeAdapter {

	private NodeElement rootNode;
	//private NodeElement lastNode;
	
	private boolean inRule = false;
	private String EMPTY_STRING = "            ";
	//ArrayList<NodeElement> nodes;
	//ArrayList<Edge> edges;
	private EdgeFactory edgeFactory;
	private DecisionTree tree;
	private ConditionFactory conditionFactory;
	private ActionFactory actionFactory;
	private IFile dTreeFile;
	private TerminalFactory terminalFactory;
	private long id=0;
	private Table tableEModel;
	private IProgressMonitor monitor;
	
	public DecisionTableTreeAdapter(DecisionTreeEditorInput input, IProgressMonitor monitor) {
		//nodes = new ArrayList<NodeElement>();
		//edges = new ArrayList<Edge>();
		
		edgeFactory = EdgeFactory.eINSTANCE;
		conditionFactory = ConditionFactory.eINSTANCE;
		actionFactory = ActionFactory.eINSTANCE;
		terminalFactory = TerminalFactory.eINSTANCE;
		
		this.dTreeFile = input.getFile();
		this.tableEModel = input.getTableEModel();
		this.monitor = monitor;
	}

	public void translateTableToTree() {
		if (tableEModel != null) {
			TableRuleSet dtRuleSet = tableEModel.getDecisionTable();
			if (dtRuleSet != null) {
				EList<TableRule> dtRuleElist = dtRuleSet.getRule();
				createTreeFile(tableEModel, dtRuleElist);
			}
		}
	}

	private void createTreeFile(Table tableEModel, EList<TableRule> dtRuleElist) {
		ModelFactory factory = ModelFactory.eINSTANCE;
		tree = factory.createDecisionTree();

		monitor.worked(10);
		monitor.subTask("Parsing table...");
		parseTableEntriesBinary(tableEModel.getDecisionTable().getColumns(), dtRuleElist);
		monitor.worked(40);
		monitor.subTask("Saving tree...");

		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		URI fileURI = URI.createFileURI(new File(dTreeFile.getLocation().toString()).getAbsolutePath());
		Resource resource = resourceSet.createResource(fileURI);
		resource.getContents().add(tree);

		monitor.worked(10);
		monitor.subTask("Saving nodes...");
		for (NodeElement node : tree.getNodes()) {
			node.setData(null);
			resource.getContents().add(node);
		}

		monitor.worked(10);
		monitor.subTask("Saving edges...");
		for (Edge edge : tree.getEdges()) {
			edge.setData(null);
			resource.getContents().add(edge);
		}

		// Save the contents of the resource to the file system.
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void resetNodeData(DecisionTree tree) {
		for (NodeElement node : tree.getNodes()) {
			node.setData(null);
		}

		for (Edge edge : tree.getEdges()) {
			edge.setData(null);
		}
	}

	private void parseTableEntriesBinary(Columns columns, EList<TableRule> dtRuleElist) {
		rootNode = createStartNode();
		NodeElement curNode = null;

		if (columns == null) {
			// System.err.println("No table columns found.");
			return;
		}

		for (TableRule dtRule : dtRuleElist) {
			EList<TableRuleVariable> condElist = dtRule.getCondition();
			EList<TableRuleVariable> actionElist = dtRule.getAction();
			// now we process the equivalent of each rule
			// System.out.println("RULE: " + dtRule.getId());

			curNode = null;
			inRule = false;

			// first process all conditions
			for (TableRuleVariable cond : condElist) {
				// Expression expr = cond.getExpression();
				Column column = columns.search(cond.getColId());
				if (column == null)
					continue;

				String conditionString = cond.getExpr();

				// Create the edge
				if (getOutDegree(rootNode) == 0) {
					// first node
					NodeElement newNode = createBooleanConditionElement(column, conditionString);
					Edge edge = createDecisionEdgeYes(rootNode, newNode);
					DecisionTreeUiUtil.connectNodes(((Start) rootNode), newNode, edge);
					//((Start) rootNode).setOutEdge(newNode);
					curNode = newNode;
				} else {
					if (curNode == null) {
						// we are at the root, and need to begin traversing
						curNode = placeNodeInTree(rootNode, column, conditionString);
					} else {
						if (!inRule) {
							NodeElement newNode = createBooleanConditionElement(column, conditionString);
							createDecisionEdgeYes(curNode, newNode);
							curNode = newNode;
						} else {
							curNode = placeNodeInTree(curNode, column, conditionString);
						}
					}
				}
			}
			//lastNode = curNode;

			StringBuffer nodeName = new StringBuffer("");
			boolean first = true;
			String newLine = "";
			String actionComment = null;

			// now process actions
			if (actionElist.size() == 0) {
				nodeName.append(EMPTY_STRING);
			} else {
				for (TableRuleVariable action : actionElist) {
					if(columns.search(action.getColId()) != null) {
						String columnName = columns.search(action.getColId()).getName();
						if (!columnName.equalsIgnoreCase("")) {
							columnName = columnName + " = ";
						}
						actionComment = action.getComment();
						if (first) {
							first = false;
							newLine = "";
						} else {
							newLine = "\n";
						}
	
						String entireActionString = action.getExpr();
						nodeName.append(newLine);
						nodeName.append(columnName);
						nodeName.append(entireActionString);
	
						NodeElement actionNode = createBinaryTreeActionNode(actionElist, columnName + entireActionString, actionComment);
						createDecisionEdgeYes(curNode, actionNode);
						curNode = actionNode;
					}
				}
			}
		}
	}

	private NodeElement createStartNode() {
		Start node = terminalFactory.createStart();
		node.setId(id++);
		//node.setData(null);
		node.setName("start");
		tree.setStartNode(node);
		tree.getNodes().add(node);
		return node;
	}
	
	private BoolCond createBooleanConditionElement(Column column, String conditionString) {
		BoolCond node = conditionFactory.createBoolCond();
		node.setId(id++);
		String condition = column.getName();
		if (!conditionString.contains("<") && !conditionString.contains(">") && !conditionString.contains("=")) 
			condition += "=";
		condition += conditionString;
		node.setName(condition);
		node.setData(column);
		tree.getNodes().add(node);
		return node;
	}

	protected NodeElement createBinaryTreeActionNode(EList<TableRuleVariable> actionElist, String label, String comment) {
		Action node = actionFactory.createAction();
		node.setId(id++);
		node.setName(label);
		node.setData(actionElist);
		tree.getNodes().add(node);
		return node;
	}
	
	private int getOutDegree(NodeElement node) {
		int degree = 0;
		if (node.getOutEdge() != null) {
			degree++;
		}
		if (node instanceof BoolCond) {
			if (((BoolCond)node).getFalseEdge() != null)
				degree++;
		}
		return degree;
	}
	
	private Edge createDecisionEdgeYes(NodeElement srcNode, NodeElement tgtNode) {
		return createDecisionEdge(srcNode, tgtNode, "yes");
	}
	
	private Edge createDecisionEdgeNo(NodeElement srcNode, NodeElement tgtNode) {
		return createDecisionEdge(srcNode, tgtNode, "no");
	}
	
	private Edge createDecisionEdge(NodeElement srcElement, NodeElement tgtElement, String label) {
		Edge edgeElement = edgeFactory.createEdge();
		edgeElement.setSrc(srcElement);
		edgeElement.setTgt(tgtElement);
		edgeElement.setName(label);
		edgeElement.setId(id++);
		if (srcElement instanceof BoolCond) {
			if (getOutDegree(srcElement)!=0)
				DecisionTreeUiUtil.connectNodesFalseEdge(srcElement, tgtElement, edgeElement);
			else {
				DecisionTreeUiUtil.connectNodes(srcElement, tgtElement, edgeElement);
			}
		} else {
			DecisionTreeUiUtil.connectNodes(srcElement, tgtElement, edgeElement);
		}
		tree.getEdges().add(edgeElement);
		return edgeElement;
	}

	private NodeElement placeNodeInTree(NodeElement startElement, Column column, String conditionString) {
		NodeElement curNodeElement = null;
		if (getOutDegree(startElement) == 0) {
			NodeElement newNodeElement = createBooleanConditionElement(column, conditionString);
			createDecisionEdgeYes(startElement, newNodeElement);
			curNodeElement = newNodeElement;
			return curNodeElement;
		}

		NodeElement targetNode = getFirstOutEdgeNodeElement(startElement);
		while (getOutDegree(targetNode)==2) {
			if ( ((String) targetNode.getName()).equals(conditionString) ) {
				break;
			}
			targetNode = getOtherOutEdgeNodeElement(targetNode);
		}

		while (targetNode.getData() instanceof Column && !(((Column)targetNode.getData()).getName()).equals(column.getName())) {
			if (getOutDegree(targetNode) > 0) {
				targetNode = getFirstOutEdgeNodeElement(targetNode);
			} else {
				break;
			}
		}
		
		if (!((String) targetNode.getName()).equals(conditionString)) {
			NodeElement newNode = createBooleanConditionElement(column, conditionString);
			createDecisionEdgeNo(targetNode, newNode);
			curNodeElement = newNode;
		} else {
			curNodeElement = targetNode;
			inRule = true;
		}
		return curNodeElement;
	}
	
	private NodeElement getFirstOutEdgeNodeElement(NodeElement node) {
		return node.getOutEdge().getTgt();
	}

	private NodeElement getOtherOutEdgeNodeElement(NodeElement node) {
		if (node instanceof BoolCond) {
			return ((BoolCond) node).getFalseEdge().getTgt();
		}
		return null;
	}
}
