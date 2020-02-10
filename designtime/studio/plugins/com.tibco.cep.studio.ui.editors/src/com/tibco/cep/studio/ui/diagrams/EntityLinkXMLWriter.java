package com.tibco.cep.studio.ui.diagrams;

import org.w3c.dom.Element;

import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.xml.TSEEdgeXMLWriter;
import com.tomsawyer.util.xml.TSObjectNotFoundException;
import com.tomsawyer.util.xml.TSXMLUtilities;

/**
 * 
 * @author ggrigore
 *
 */
public class EntityLinkXMLWriter extends TSEEdgeXMLWriter {
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
        		EntityXMLConstants.CUSTOM_DATA_BLOCK);
        element.appendChild(customEdgeData);

        TSEEdge edge = (TSEEdge) this.getEdge();
        
        TSXMLUtilities.writeIntAttribute(
        	EntityXMLConstants.LINK_TYPE,
        	((Integer)edge.getUserObject()).intValue(),
        	customEdgeData);
    }
}
