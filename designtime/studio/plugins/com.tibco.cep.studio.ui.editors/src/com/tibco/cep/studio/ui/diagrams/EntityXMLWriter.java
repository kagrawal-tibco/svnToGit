package com.tibco.cep.studio.ui.diagrams;

import org.w3c.dom.Element;

import com.tibco.cep.designtime.core.model.Entity;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.xml.TSENodeXMLWriter;
import com.tomsawyer.util.xml.TSObjectNotFoundException;
import com.tomsawyer.util.xml.TSXMLUtilities;

/**
 * 
 * @author ggrigore
 *
 */
public class EntityXMLWriter extends TSENodeXMLWriter {
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
        		EntityXMLConstants.CUSTOM_DATA_BLOCK);
        element.appendChild(customNodeData);

        TSENode node = (TSENode) this.getNode();
        // DIFF
        Entity entity = this.getEntity(node);
        // DIFF
		if (entity != null) {
	        TSXMLUtilities.writeStringAttribute(
	        	EntityXMLConstants.FULL_PATH,
	        	entity.getFolder() + entity.getName(),
	        	customNodeData);
		}
    }
    
    // DIFF
    private Entity getEntity(TSENode tsNode) {
    	Entity entity = null;
    	if (tsNode.getUserObject() instanceof EntityNodeData) {
    		entity = (Entity) 
    			((EntityNodeData)
    				tsNode.getUserObject()).getEntity().getUserObject();
    	}    	
		return entity;
	}      
    
}
