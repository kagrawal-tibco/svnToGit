/**
 * 
 */
package com.tibco.cep.security.authz.core;

import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * Implement this interface to serialize the object to xml structure
 * @author aathalye
 *
 */
public interface SerializableObject {
	
	/**
	 * Serialize this object to an {@link XiNode}
	 * @param node
	 */
	void serialize(XiFactory factory, XiNode node);
}
