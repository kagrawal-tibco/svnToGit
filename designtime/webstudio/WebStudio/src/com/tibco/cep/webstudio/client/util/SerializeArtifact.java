/**
 * 
 */
package com.tibco.cep.webstudio.client.util;

import java.util.Map;

import com.tibco.cep.webstudio.client.request.model.IRequest;
import com.tibco.cep.webstudio.client.request.model.IRequestData;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.IRequestProject;
import com.tibco.cep.webstudio.client.request.model.RequestModelBuilder;

/**
 * @author Vikram Patil
 *
 */
/**
 * Common class to serialize to XML data for all artifacts.
 */
public class SerializeArtifact<D extends IRequestDataItem, P extends IRequestProject<D>, Q extends IRequestData<D, P>, R extends IRequest<D, P, Q>> {

	/**
	 * Generate XML data corresponding to associated artifact
	 * @param serverEndPoint
	 * @param data
	 * @return
	 */
	public String generateXML(ServerEndpoints serverEndPoint, Map<String, Object> data) {
		return RequestModelBuilder.<D, P, Q, R>buildXMLRequest(serverEndPoint, data);
	}
}
