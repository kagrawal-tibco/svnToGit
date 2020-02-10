package com.tibco.cep.studio.ui.statemachine.diagram.xml;

import org.w3c.dom.Element;

import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.xml.TSENodeXMLReader;
import com.tomsawyer.util.xml.TSObjectNotFoundException;
import com.tomsawyer.util.xml.TSXMLUtilities;

/**
 * 
 * @author ggrigore
 *
 */
public class StateXMLReader extends TSENodeXMLReader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4510611082176106646L;
	private StateMachineDiagramManager manager;
	
	public StateXMLReader(StateMachineDiagramManager manager) {
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
			TSENode node = (TSENode) this.getNode();
			String stateFullPath = TSXMLUtilities.parseStringAttribute(
				StateXMLConstants.STATE_FULLPATH,
				pathElement);

			// find State element given the GUID, and set it
			StateEntity state = IndexUtils.getStateEntity(
				this.manager.getStateMachine(),
				stateFullPath);
			
			if (state != null) {
				node.setUserObject(state);
				// do something with "state" now to decorate it, etc.
//				this.manager.populateTSNode(node, (State) state);
			}
			else {
				System.err.println("State not found!");
			}
		}
		else {
			System.err.println("XML attribute not found: " +
				StateXMLConstants.CUSTOM_DATA_BLOCK);
		}		
	}
	
}
