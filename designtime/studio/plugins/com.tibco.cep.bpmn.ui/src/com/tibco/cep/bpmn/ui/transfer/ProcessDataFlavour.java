package com.tibco.cep.bpmn.ui.transfer;

import java.awt.datatransfer.Clipboard;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author sasahoo
 *
 */
public class ProcessDataFlavour {
	
	private TSENode tsNode;
	private EObject eObject;
	private DiagramManager manager;
	private Clipboard clipboard;

	/**
	 * @param tsNode
	 * @param eObject
	 * @param manager
	 */
	public ProcessDataFlavour(TSENode tsNode, 
			                  EObject eObject, 
			                  DiagramManager manager) {
		this.tsNode = tsNode;
		this.eObject = eObject;
		this.manager = manager;
		this.clipboard = manager.getDrawingCanvas().getClipboard();
	}
	
	public TSENode getNode() {
		return tsNode;
	}

	public EObject getEObject() {
		return eObject;
	}

	public DiagramManager getManager() {
		return manager;
	}

	public Clipboard getClipboard() {
		return clipboard;
	}
}