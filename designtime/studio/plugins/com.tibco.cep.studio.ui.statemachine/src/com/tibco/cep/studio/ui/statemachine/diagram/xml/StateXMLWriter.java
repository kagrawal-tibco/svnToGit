package com.tibco.cep.studio.ui.statemachine.diagram.xml;

import org.w3c.dom.Element;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.xml.TSENodeXMLWriter;
import com.tomsawyer.util.xml.TSObjectNotFoundException;
import com.tomsawyer.util.xml.TSXMLUtilities;


/**
 * 
 * @author ggrigore
 *
 */
public class StateXMLWriter extends TSENodeXMLWriter {
    /**
     * This method is used to process the DOM element.  It should be
     * overridden in order to read user data from the XML file.
     */
    public void populateDOMElement(Element element)
        throws TSObjectNotFoundException
    {
        super.populateDOMElement(element);
        
        Element customNodeData =
        	element.getOwnerDocument().createElement(
        		StateXMLConstants.CUSTOM_DATA_BLOCK);
        element.appendChild(customNodeData);

        TSENode node = (TSENode) this.getNode();
        State state = this.getState(node);
        
		if (state != null) {
	        TSXMLUtilities.writeStringAttribute(
	        	StateXMLConstants.STATE_FULLPATH,
	        	IndexUtils.getStateEntityFullPath(state, false),
	        	customNodeData);
		}
    }
    
    private State getState(TSENode tsNode) {
        TSENode node = (TSENode) this.getNode();
        if (node.getUserObject() == null) {
        	return null;
        }
		return (State) node.getUserObject();
    }        
    
}
