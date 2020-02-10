/**
 * 
 */
package com.tibco.be.ws.process;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.xml.sax.InputSource;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.ws.wsdl.WsExtensionElement;
import com.tibco.xml.ws.wsdl.WsOperationReference;
import com.tibco.xml.ws.wsdl.WsPort;
import com.tibco.xml.ws.wsdl.WsService;
import com.tibco.xml.ws.wsdl.WsWsdl;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapOperation;
import com.tibco.xml.ws.wsdl.helpers.DefaultFactory;
import com.tibco.xml.ws.wsdl.helpers.WsdlConstants;

/**
 * Helper method to Load and Parse WSDL
 * 
 * @author vpatil
 */
public class WSDLHelper {
	
	private static final String WSDL_ELEMENT_START = "<wsdl>";
	private static final String WSDL_ELEMENT_END = "</wsdl>";
	private static final String SERVICE_ELEMENT_START = "<service>";
	private static final String SERVICE_ELEMENT_END = "</service>";
	private static final String PORT_ELEMENT_START = "<port>";
	private static final String PORT_ELEMENT_END = "</port>";
	private static final String OPERATION_ELEMENT_START = "<operation>";
	private static final String OPERATION_ELEMENT_END = "</operation>";
	private static final String NAME_ELEMENT_START = "<name>";
	private static final String NAME_ELEMENT_END = "</name>";
	private static final String SOAPACTION_ELEMENT_START = "<soapAction>";
	private static final String SOAPACTION_ELEMENT_END = "</soapAction>";
	
	/**
	 * Load and parse WSDL file
	 * 
	 * @param wsdlPath
	 * @return
	 * @throws Exception
	 */
	public static String loadAndParseWSDL(String wsdlPath) throws Exception {
		if (!wsdlPath.endsWith(".wsdl")) wsdlPath = wsdlPath + ".wsdl";
		
		InputSource source = new InputSource(new FileInputStream(new File(wsdlPath)));
		source.setSystemId(wsdlPath);
		
		DefaultFactory wsdlParser = DefaultFactory.getInstance();
		WsWsdl wsdl = wsdlParser.parse(source);
		
		StringBuilder sBuff = new StringBuilder();
		
		if (wsdl != null) {
			sBuff.append(WSDL_ELEMENT_START);

			Iterator<?> iter = wsdl.getServices();
			while (iter.hasNext()) {
				sBuff.append(SERVICE_ELEMENT_START);
				WsService service = (WsService) iter.next();
				sBuff.append(NAME_ELEMENT_START + service.getLocalName() + NAME_ELEMENT_END);
				
				Iterator<?> ports = service.getPorts();
				while (ports.hasNext()) {
					sBuff.append(PORT_ELEMENT_START);
					WsPort port = (WsPort)ports.next();
					sBuff.append(NAME_ELEMENT_START + port.getLocalName() + NAME_ELEMENT_END);
					
					Iterator<?> op = port.getBinding().getOperations();
					while (op.hasNext()) {
						sBuff.append(OPERATION_ELEMENT_START);
						WsOperationReference operation = (WsOperationReference) op.next();
						sBuff.append(NAME_ELEMENT_START + operation.getLocalName() + NAME_ELEMENT_END);
						
						WsExtensionElement extensionElement = getExtensionElement(operation);
						if (extensionElement != null) {
							String soapAction = ((WsSoapOperation) extensionElement)
								.getSoapAction();
							sBuff.append(SOAPACTION_ELEMENT_START + soapAction + SOAPACTION_ELEMENT_END);
						}
						
						sBuff.append(OPERATION_ELEMENT_END);
					}
					
					sBuff.append(PORT_ELEMENT_END);
				}
				
				sBuff.append(SERVICE_ELEMENT_END);
			}
			
			sBuff.append(WSDL_ELEMENT_END);
		}
		
		return sBuff.toString();
	}
		
	private static WsExtensionElement getExtensionElement(WsOperationReference opnRef) {
		WsExtensionElement element = opnRef.getExtensionElement(ExpandedName
				.makeName(WsdlConstants.SOAP_URI_11,
						WsdlConstants.OPERATION_ELEMENT));
		if (element == null) {
			element = opnRef.getExtensionElement(ExpandedName.makeName(
					WsdlConstants.SOAP_URI_12,
					WsdlConstants.OPERATION_ELEMENT));
		}
		return element;
	}
}
