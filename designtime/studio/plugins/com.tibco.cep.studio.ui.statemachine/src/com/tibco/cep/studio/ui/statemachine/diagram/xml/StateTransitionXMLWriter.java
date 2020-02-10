package com.tibco.cep.studio.ui.statemachine.diagram.xml;

import org.w3c.dom.Element;

import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.xml.TSEEdgeXMLWriter;
import com.tomsawyer.util.xml.TSObjectNotFoundException;
import com.tomsawyer.util.xml.TSXMLUtilities;

/**
 * 
 * @author ggrigore
 *
 */
public class StateTransitionXMLWriter extends TSEEdgeXMLWriter {
    /**
     * This method is used to process the DOM element.  It should be
     * overridden in order to read user data from the XML file.
     */
    public void populateDOMElement(Element element)
        throws TSObjectNotFoundException
    {
        super.populateDOMElement(element);
        
        Element customEdgeData =
        	element.getOwnerDocument().createElement(
        		StateXMLConstants.CUSTOM_DATA_BLOCK);
        element.appendChild(customEdgeData);

        TSEEdge edge = (TSEEdge) this.getEdge();
        
        TSXMLUtilities.writeStringAttribute(
        		StateXMLConstants.LINK_NAME,
        	((StateTransition)edge.getUserObject()).getName(),
        	customEdgeData);
    }
}
