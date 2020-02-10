package com.tibco.cep.bpmn.ui.editor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.text.AbstractDocument;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.CopyOnWriteTextStore;
import org.eclipse.jface.text.DefaultLineTracker;
import org.eclipse.jface.text.GapTextStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultProcessIndex;

public class GraphDocument extends AbstractDocument implements IGraphDocument {

	private int numFlowNodes;


	public GraphDocument() {
		setTextStore(new CopyOnWriteTextStore(new GapTextStore()));
		setLineTracker(new DefaultLineTracker());
		completeInitialization();
	}
	
	@Override
	public void set(String text) {
		super.set(text);
		ResourceSet rs = new ResourceSetImpl();
		org.eclipse.emf.common.util.URI uri = URI.createURI(""+hashCode());
		Map<Object, Object> options = new HashMap<Object, Object>();
		Resource res = rs.createResource(uri);
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(text.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			bais = new ByteArrayInputStream(text.getBytes());
		}
		try {
			res.load(bais, options);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EList<EObject> contents = res.getContents();
		if(contents.size()>0){
			
			DefaultProcessIndex pi = new DefaultProcessIndex(contents.get(0));
			this.numFlowNodes = pi.getAllFlowNodes().size();
		}
	}
	
	
	@Override
	public IRegion getLineInformation(int line) throws BadLocationException {
		line++;// reset the hasCode decrement --
		return new Region(line,numFlowNodes);
	}
	
	@Override
	public int getNumNodes() {
		return numFlowNodes;
	}

}
