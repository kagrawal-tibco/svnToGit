package com.tibco.cep.decision.tree.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.cep.decision.tree.common.model.DecisionTree;
import com.tibco.cep.decision.tree.common.model.ModelFactory;
import com.tibco.cep.decision.tree.common.model.edge.Edge;
import com.tibco.cep.decision.tree.common.model.edge.EdgeFactory;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;
import com.tibco.cep.decision.tree.common.model.node.NodeFactory;

/*
@author ssailapp
@date Sep 19, 2011
 */

public class DecisionTreePersistence {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    


	ArrayList<NodeElement> nodes;
	ArrayList<Edge> edges;
	
	public static void main(String[] args) {
		DecisionTreePersistence dtp = new DecisionTreePersistence();
		dtp.create();
		System.out.println("Done.");
	}

	public DecisionTreePersistence() { 
		nodes = new ArrayList<NodeElement>();
		edges = new ArrayList<Edge>();
	}
	
	private void create() {

		ModelFactory factory = ModelFactory.eINSTANCE;
		DecisionTree tree = factory.createDecisionTree();
		
		addNodes(tree, 8);
		
		// Create a resource set.
		ResourceSet resourceSet = new ResourceSetImpl();

	  // Register the default resource factory -- only needed for stand-alone!
	  resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
	    Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

	  // Get the URI of the model file.
	  URI fileURI = URI.createFileURI(new File("c:\\tmp\\tree1.dtree").getAbsolutePath());

	  // Create a resource for this file.
	  Resource resource = resourceSet.createResource(fileURI);

	  resource.getContents().add(tree);
	  
	  for (NodeElement node: nodes) { 
		  resource.getContents().add(node);
	  }
	  
	  for (Edge edge: edges) { 
		  resource.getContents().add(edge);
	  }

	  // Save the contents of the resource to the file system.
	  try
	  {
	    resource.save(Collections.EMPTY_MAP);
	  }
	  catch (IOException e) {
		  e.printStackTrace();
	  }
	}
	
	private void addNodes(DecisionTree tree, int nodesCnt) {
		NodeFactory nodeFactory = NodeFactory.eINSTANCE;
		EdgeFactory edgeFactory = EdgeFactory.eINSTANCE;
		
		NodeElement prevNode = null;
		for (int n=0; n<nodesCnt; n++) {
			NodeElement node = nodeFactory.createNodeElement();
			node.setName("node" + n);
			tree.getNodes().add(node);
			nodes.add(node);
			if (prevNode != null) {
				Edge edge = edgeFactory.createEdge();
				edge.setSrc(prevNode);
				edge.setTgt(node);
				edge.setName("edgeTo" + n);
				edges.add(edge);
				tree.getEdges().add(edge);
			}
			prevNode = node;
		}
	}
	
}
