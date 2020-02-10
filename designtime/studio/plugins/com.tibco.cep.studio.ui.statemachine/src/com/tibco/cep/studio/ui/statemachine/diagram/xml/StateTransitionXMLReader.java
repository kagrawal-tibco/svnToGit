package com.tibco.cep.studio.ui.statemachine.diagram.xml;

import org.w3c.dom.Element;

import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.xml.TSEEdgeXMLReader;
import com.tomsawyer.util.xml.TSObjectNotFoundException;
import com.tomsawyer.util.xml.TSXMLUtilities;

/**
 * 
 * @author ggrigore
 *
 */
public class StateTransitionXMLReader extends TSEEdgeXMLReader {
	
	private static final long serialVersionUID = 111L;
	private StateMachineDiagramManager manager;
	
	public StateTransitionXMLReader(StateMachineDiagramManager manager) {
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
			StateXMLConstants.CUSTOM_DATA_BLOCK);

		if (pathElement != null)
		{
			TSEEdge edge = (TSEEdge) this.getEdge();
			
			String name = TSXMLUtilities.parseStringAttribute(
					StateXMLConstants.LINK_NAME,
					pathElement);
			StateTransition transition = IndexUtils.getStateTransition(
				this.manager.getStateMachine(),
				name);
			edge.setUserObject(transition);
		}
		else {
//			System.err.println("Link XML attribute not found: " +
//				ConceptXMLConstants.CUSTOM_DATA_BLOCK +
//				" (using default: inheritance).");
		}
	}
	
}
