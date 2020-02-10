package com.tibco.cep.decision.tree.common;

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
import com.tibco.cep.decision.tree.common.model.node.NodeElement;

/*
 @author ssailapp
 @date Nov 1, 2011
 */

public class DecisionTreePersistenceUtil {

	public static void createEmptyTree(String filePath) {
		ArrayList<NodeElement> nodes = new ArrayList<NodeElement>();
		ArrayList<Edge> edges = new ArrayList<Edge>();

		ModelFactory factory = ModelFactory.eINSTANCE;
		DecisionTree tree = factory.createDecisionTree();

		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		URI fileURI = URI.createFileURI(new File(filePath).getAbsolutePath());
		Resource resource = resourceSet.getResource(fileURI, true);

		resource.getContents().add(tree);

		for (NodeElement node : nodes) {
			resource.getContents().add(node);
		}

		for (Edge edge : edges) {
			resource.getContents().add(edge);
		}

		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
