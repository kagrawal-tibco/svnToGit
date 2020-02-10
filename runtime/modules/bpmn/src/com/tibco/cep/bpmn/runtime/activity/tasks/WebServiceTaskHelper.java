package com.tibco.cep.bpmn.runtime.activity.tasks;

import com.tibco.xml.datamodel.XiNode;

/**
 * 
 * @author majha
 *
 */
interface WebServiceTaskHelper {

	XiNode sendWebServiceRequest(XiNode soapRequest) throws Exception;

}
