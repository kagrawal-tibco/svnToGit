package com.tibco.cep.studio.ui.diagrams;

import org.w3c.dom.Element;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.xml.TSENodeXMLReader;
import com.tomsawyer.util.xml.TSObjectNotFoundException;
import com.tomsawyer.util.xml.TSXMLUtilities;

/**
 * 
 * @author ggrigore
 *
 */
public class EntityXMLReader extends TSENodeXMLReader {
	
	// DIFF
	private EntityDiagramManager manager;
	
	// DIFF
	public EntityXMLReader(EntityDiagramManager manager) {
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

		// DIFF
		Entity entity = null;
		Element pathElement = TSXMLUtilities.findElement(element,
			EntityXMLConstants.CUSTOM_DATA_BLOCK);

		if (pathElement != null)
		{
			TSENode node = (TSENode) this.getNode();
			String fullPath = TSXMLUtilities.parseStringAttribute(
				EntityXMLConstants.FULL_PATH,
				pathElement);
			
			// DIFF:
			entity = IndexUtils.getEntity(
				this.manager.getProjectName(),
				fullPath,
				this.manager.getEntityType());
			this.manager.populateTSNode(node, entity);
		}
		else {
			System.err.println("XML attribute not found: " +
				EntityXMLConstants.CUSTOM_DATA_BLOCK);
		}
	}
	
}
