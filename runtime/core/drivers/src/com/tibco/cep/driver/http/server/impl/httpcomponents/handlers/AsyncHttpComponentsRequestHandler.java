package com.tibco.cep.driver.http.server.impl.httpcomponents.handlers;

import static com.tibco.cep.driver.soap.SoapConstants.HEADER_SOAP_ACTION;

import java.io.IOException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.nio.entity.BufferingNHttpEntity;
import org.apache.http.nio.entity.ConsumingNHttpEntity;
import org.apache.http.nio.protocol.NHttpRequestHandler;
import org.apache.http.nio.protocol.NHttpRequestHandlerRegistry;
import org.apache.http.nio.protocol.NHttpResponseTrigger;
import org.apache.http.nio.util.HeapByteBufferAllocator;
import org.apache.http.protocol.HttpContext;

import com.tibco.be.util.BEStringUtilities;
import com.tibco.cep.driver.http.HttpChannel;
import com.tibco.cep.driver.http.HttpUtils;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Asynchronous handler to handle the http requests. This handler uses HTTP Core NIO
 * entities for reading http requests asynchronously and routes the http message to
 * proper destination for processing
 */
public class AsyncHttpComponentsRequestHandler implements NHttpRequestHandler {

    private static final Logger LOGGER = com.tibco.cep.kernel.service.logging.LogManagerFactory.getLogManager().getLogger(AsyncHttpComponentsRequestHandler.class);

    private AsyncHttpComponentsRequestProcessor processor;

    private HttpChannel httpChannel;


    /**
     * Construct Asynchronous Request Handler with the specific processor
     * Currently there are two implementations
     * 1. @link ASyncHttpComponentsDestinationProcessor
     * 2. @link AsyncHttpComponentsFileProcessor
     *
     * @param processor
     */
    public AsyncHttpComponentsRequestHandler(AsyncHttpComponentsRequestProcessor processor) {
        this(processor, null);
    }

    public AsyncHttpComponentsRequestHandler(AsyncHttpComponentsRequestProcessor processor, HttpChannel httpChannel) {
        super();
        this.processor = processor;
        this.httpChannel = httpChannel;
    }

    /**
     * Returns NIO based Request Entity for asynchronous HTTP connection
     */
    public ConsumingNHttpEntity entityRequest(HttpEntityEnclosingRequest request,
                                              HttpContext context) throws HttpException,
            java.io.IOException {
        //Use non-direct buffer as this buffer is request based and not long-lived.
        return new BufferingNHttpEntity(request.getEntity(), new HeapByteBufferAllocator());
    }


    /**
     * Handle Asynchronous HTTP Request/Response handling
     *
     * @param request  -- The Asynchronous HTTP Request
     * @param response -- The Asynchronous HTTP Response
     * @param trigger  -- The Asynchronous HTTP Response Trigger, this is used to
     *                 submit the response asynchronously when the request processing is done and
     *                 response needs to be sent back
     * @param context  - HTTP Context associated with the request
     */
    public void handle(
            final HttpRequest request,
            final HttpResponse response,
            NHttpResponseTrigger trigger,
            final HttpContext context) throws HttpException, IOException {
        ASyncHttpComponentsRequest httpcorereq = new ASyncHttpComponentsRequest(request, context, new RequestUriEncoding(httpChannel));
        ASyncHttpComponentsResponse httpcoreresp = new ASyncHttpComponentsResponse(response, trigger);
        try {
            //Check if it has a soap action
            String soapAction = httpcorereq.getHeader(HEADER_SOAP_ACTION);
            if (soapAction != null && soapAction.length() > 0) {
                //SOAP action can become destination
                while (BEStringUtilities.startsWithInValidTibcoIdentifier(soapAction)) {
                    soapAction = soapAction.substring(1);
                }
                while (BEStringUtilities.endsWithInValidTibcoIdentifier(soapAction)) {
                    soapAction = soapAction.substring(0, soapAction.length() - 1);
                }
                String destinationName = BEStringUtilities.convertToValidTibcoIdentifier(soapAction, true);
                //Dispatch to this URI
                String dispatcherURI = HttpUtils.buildDestinationURL(httpcorereq.getRequestURI(), destinationName);
                NHttpRequestHandlerRegistry registry = (NHttpRequestHandlerRegistry) request.getParams().getParameter("registry");
                AsyncHttpComponentsRequestHandler reqHandler = (AsyncHttpComponentsRequestHandler) registry.lookup(dispatcherURI);
                if (reqHandler != null) {
                    httpcorereq.setAttribute(HttpUtils.DESTINATION_URI, dispatcherURI);
                    processor.process(httpcorereq, httpcoreresp);
                } else {
                    AsyncNHttpErrorHandler.sendErrorResponse(httpcoreresp, HttpStatus.SC_NOT_IMPLEMENTED);
                }
            } else {
                processor.process(httpcorereq, httpcoreresp);
            }
        } catch (Exception ex) {
            LOGGER.log(com.tibco.cep.kernel.service.logging.Level.ERROR, ex, null);
            AsyncNHttpErrorHandler.sendErrorResponse(httpcoreresp, ex.getMessage(), HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // TODO Not sure why this is exposed in the first place.
    public AsyncHttpComponentsRequestProcessor getProcessor() {
        return processor;
    }
}


