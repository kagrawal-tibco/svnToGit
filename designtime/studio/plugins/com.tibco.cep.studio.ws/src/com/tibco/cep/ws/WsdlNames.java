package com.tibco.cep.ws;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.ws.wsdl.helpers.WsdlConstants;



public interface WsdlNames
{
	ExpandedName SOAP11_ADDRESS = ExpandedName.makeName(WsdlConstants.SOAP_URI_11, WsdlConstants.ADDRESS_ELEMENT);
	ExpandedName SOAP11_BINDING = ExpandedName.makeName(WsdlConstants.SOAP_URI_11, WsdlConstants.BINDING_ELEMENT);
	ExpandedName SOAP11_BODY = ExpandedName.makeName(WsdlConstants.SOAP_URI_11, WsdlConstants.BODY_ELEMENT);
    ExpandedName SOAP11_ELEMENT = ExpandedName.makeName(WsdlConstants.SOAP_URI_11, WsdlConstants.OPERATION_ELEMENT);
    ExpandedName SOAP11_FAULT = ExpandedName.makeName(WsdlConstants.SOAP_URI_11, WsdlConstants.FAULT_ELEMENT);
	ExpandedName SOAP11_HEADER = ExpandedName.makeName(WsdlConstants.SOAP_URI_11, WsdlConstants.HEADER_ELEMENT);
	ExpandedName SOAP11_HEADER_FAULT = ExpandedName.makeName(WsdlConstants.SOAP_URI_11, WsdlConstants.HEADER_FAULT_ELEMENT);
	ExpandedName SOAP11_OPERATION = ExpandedName.makeName(WsdlConstants.SOAP_URI_11, WsdlConstants.OPERATION_ELEMENT);	
	
	ExpandedName SOAP12_ADDRESS = ExpandedName.makeName(WsdlConstants.SOAP_URI_12, WsdlConstants.ADDRESS_ELEMENT);
	ExpandedName SOAP12_BINDING = ExpandedName.makeName(WsdlConstants.SOAP_URI_12, WsdlConstants.BINDING_ELEMENT);
	ExpandedName SOAP12_BODY = ExpandedName.makeName(WsdlConstants.SOAP_URI_12, WsdlConstants.BODY_ELEMENT);
	ExpandedName SOAP12_HEADER = ExpandedName.makeName(WsdlConstants.SOAP_URI_12, WsdlConstants.HEADER_ELEMENT);
	ExpandedName SOAP12_HEADER_FAULT = ExpandedName.makeName(WsdlConstants.SOAP_URI_12, WsdlConstants.HEADER_FAULT_ELEMENT);
	ExpandedName SOAP12_OPERATION = ExpandedName.makeName(WsdlConstants.SOAP_URI_12, WsdlConstants.OPERATION_ELEMENT);
}
