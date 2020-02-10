package com.tibco.cep.ws.util.wsdlimport.channel;

import org.eclipse.core.resources.IProject;

import com.tibco.cep.ws.util.wsdlimport.DefaultWsdlImport;
import com.tibco.xml.ws.wsdl.helpers.WsdlConstants;

public class WsdlChannelBuilderFactory {

	private final IProject project;
	private final String transportFolderName;


	public WsdlChannelBuilderFactory(
			IProject project,
			String transportFolderName)
    {
		this.project = project;
		this.transportFolderName = transportFolderName;
	}


    public WsdlChannelBuilder make(
            String transport)
    {
        if (WsdlConstants.SOAP_11_TRANSPORT_URI.equals(transport)) {
            return new WsdlHttpChannelBuilder(this.project, this.transportFolderName);
        }
        else if (WsdlConstants.JMS_11_TRANSPORT_URI.equalsIgnoreCase(transport)
                || DefaultWsdlImport.W3C_SOAP_OVER_JMS_TRANSPORT_URI.equalsIgnoreCase(transport)
                || DefaultWsdlImport.TIBCO_2004_SOAP_OVER_JMS_TRANSPORT_URI.equals(transport)) {
            return new WsdlJmsChannelBuilder(this.project, this.transportFolderName);
        }
        else {
            return null;
        }
    }

}
