package com.tibco.cep.driver.http.serializer.soap;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.driver.http.client.HttpChannelClientRequest;
import com.tibco.cep.driver.http.client.HttpChannelClientResponse;
import com.tibco.cep.driver.http.server.HttpChannelServerRequest;
import com.tibco.cep.driver.http.server.HttpChannelServerResponse;
import com.tibco.cep.driver.soap.SoapConstants;
import com.tibco.xml.soap.api.transport.TransportContext;
import com.tibco.xml.soap.api.transport.TransportDriver;
import com.tibco.xml.soap.api.transport.TransportEntityStore;
import com.tibco.xml.soap.api.transport.TransportUri;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Mar 26, 2009
 * Time: 8:22:31 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class SOAPTransportContext implements TransportContext {

    private Map<String, String> fieldsMap = new HashMap<String, String>();

    @SuppressWarnings("unchecked")
    public SOAPTransportContext(final HttpChannelServerRequest request) {
        //Fill fields
        Iterator<String> headerNames = request.getHeaderNames();

        while (headerNames.hasNext()) {
            String headerName = headerNames.next();
            //Get header value
            String headerValue = request.getHeader(headerName);
//
            if (!headerName.equalsIgnoreCase("Transfer-Encoding")) {
                fieldsMap.put(headerName, headerValue);
            }
        }
    }

    public SOAPTransportContext(final HttpChannelServerResponse response) {
        fieldsMap.put("MIME-Version", "1.0");
    }

    public SOAPTransportContext(final HttpChannelClientRequest request) {
        fieldsMap.put("MIME-Version", "1.0");
    }

    public SOAPTransportContext(final HttpChannelClientResponse response) {
        //fieldsMap.put("MIME-Version", "1.0");
    }

    /**
     * Returns the TransportDriver that created this context.
     * Use the driver to create reply context.
     *
     * @return the TransportDriver that created this context
     */
    public TransportDriver getDriver() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns a Map of transport header fields. The map keys are
     * lower-case Strings.  Each map value is either String or String[] or Object.
     * String[] is used for duplicated keys.
     * <p/>
     * Object is only allowed for "synthetic" fields, which contain SYNTHETIC_MARKER within
     * the key.  Synthetic fields will not be written as part of the
     * transport header fields.
     *
     * @return Map lowercase String => String or String[], or Object in the case of "synthetic" fields
     */
    public Map getFieldMap() {
        return fieldsMap;
    }

    /**
     * Returns the SoapAction field for this transport envelope if there is
     * such a field.  Note that SoapAction is deprecated with SOAP 1.2.
     * Any surrounding quotation marks are not included in the return value.
     *
     * @return URI String or null
     */
    public String getSoapAction() {
        return fieldsMap.get(SoapConstants.HEADER_SOAP_ACTION);
    }

    /**
     * Returns the TransportUri of this transport context if one has been set.
     *
     * @return TransportUri or null.
     */
    public TransportUri getTransportUri() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Sets the soap action for this transport context. May be ignored
     * if this transport doesn't support soap action.
     * Surrounding quotation marks may be present, but the soapaction
     * will be written with quotation markes in any case, as required.
     */
    public void setSoapAction(String soapAction) {
        fieldsMap.put(SoapConstants.HEADER_SOAP_ACTION, soapAction);
    }

    /**
     * Returns the URL of this transport context if one has been set.
     *
     * @return URL or null.
     * @deprecated use getTransportUri so that foreign protocols are allowed
     */
    public URL getUrl() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


	public TransportEntityStore getEntityStore() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setEntityStore(TransportEntityStore arg0) {
		// TODO Auto-generated method stub
		
	}
}
