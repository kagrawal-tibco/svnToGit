package com.tibco.rta.client.http;

import com.tibco.rta.client.BytesServiceResponse;
import com.tibco.rta.client.ServiceInvocationListener;
import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.util.ServiceConstants;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 6/11/12
 * Time: 11:07 AM
 * Handle response coming from service side when invoked over HTTP Transport.
 */
public class HTTPServiceInvocationResponseHandler implements ResponseHandler<ServiceResponse> {

    public HTTPServiceInvocationResponseHandler(ServiceInvocationListener serviceInvocationListener) {
    }

    @Override
    public ServiceResponse handleResponse(HttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        BytesServiceResponse serviceResponse = new BytesServiceResponse();
        serviceResponse.addProperty(ServiceConstants.STATUS_CODE, Integer.toString(statusCode));

        for (Header header : httpResponse.getAllHeaders()) {
            serviceResponse.addProperty(header.getName(), header.getValue());
        }
        HttpEntity httpEntity = httpResponse.getEntity();
        byte[] responseContent = null;
        if (httpEntity != null) {
            responseContent = EntityUtils.toByteArray(httpEntity);
        }
        serviceResponse.setPayload(responseContent);
//        if (serviceInvocationListener != null) {
//            serviceInvocationListener.serviceInvoked(serviceResponse);
//        }
        return serviceResponse;
    }
}
