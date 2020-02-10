package com.tibco.cep.studio.ui.diagrams;

import org.w3c.dom.Element;

import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.xml.TSEEdgeXMLReader;
import com.tomsawyer.util.xml.TSObjectNotFoundException;
import com.tomsawyer.util.xml.TSXMLUtilities;

/**
 * 
 * @author ggrigore
 *
 */
public class EntityLinkXMLReader extends TSEEdgeXMLReader {
	
	// DIFF
	private EntityDiagramManager manager;
	
	// DIFF
	public EntityLinkXMLReader(EntityDiagramManager manager) {
		this.manager = manager;
	}

	/**
	 * This method is used to process the DOM element.  It should be
	 * overridden in order to read user data from the XML file.
	 */
	public void processDOMElement(Element element)
		throws TSObjectNotFoundException
	{
		super.processDOMElement(element);

		Element pathElement = TSXMLUtilities.findElement(element,
			EntityXMLConstants.CUSTOM_DATA_BLOCK);

		if (pathElement != null)
		{
			TSEEdge edge = (TSEEdge) this.getEdge();
			
			int edgeType = TSXMLUtilities.parseIntAttribute(
					EntityXMLConstants.LINK_TYPE,
					pathElement);			
			
			this.manager.setEdgeUI(edge, edgeType);
		}
		else {
//			System.err.println("Link XML attribute not found: " +
//				ConceptXMLConstants.CUSTOM_DATA_BLOCK +
//				" (using default: inheritance).");
			
			// TODO: this should not happen!
			this.manager.setEdgeUI((TSEEdge) this.getEdge(),
				DrawingCanvas.INHERITANCE_LINK_TYPE);
		}
	}
	
}
