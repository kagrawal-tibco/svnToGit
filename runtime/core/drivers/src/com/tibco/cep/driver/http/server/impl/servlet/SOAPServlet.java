package com.tibco.cep.driver.http.server.impl.servlet;

import static com.tibco.be.util.BEStringUtilities.convertToValidTibcoIdentifier;
import static com.tibco.cep.driver.soap.SoapConstants.HEADER_SOAP_ACTION;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tibco.be.util.BEStringUtilities;
import com.tibco.cep.driver.http.HttpUtils;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Mar 18, 2009
 * Time: 12:48:27 PM
 * Servlet to handle requests containing <b>SOAPAction</b> HTTP Header
 */
public class SOAPServlet extends HttpServlet {

	private static final long serialVersionUID = -5698277095324902757L;

	@Override
    protected void doGet(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse) throws ServletException, IOException {
        try {
            dispatch(httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Dispatch request to {@linkplain com.tibco.cep.driver.http.server.impl.servlet.HttpChannelServlet}, if a mapping is found
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    private void dispatch(HttpServletRequest httpServletRequest,
                          HttpServletResponse httpServletResponse) throws Exception {

        //Get SOAP Action header
        String soapAction = httpServletRequest.getHeader(HEADER_SOAP_ACTION);
        //SOAP action can become destination

        while (BEStringUtilities.startsWithInValidTibcoIdentifier(soapAction)) {
            soapAction = soapAction.substring(1);
        }
        while (BEStringUtilities.endsWithInValidTibcoIdentifier(soapAction)) {
            soapAction = soapAction.substring(0, soapAction.length() - 1);
        }
        String destinationName = convertToValidTibcoIdentifier(soapAction, true);

        //Dispatch to this URI
        String dispatcherURI = HttpUtils.buildDestinationURL(httpServletRequest.getRequestURI(),
                destinationName);

        dispatch(dispatcherURI, httpServletRequest, httpServletResponse);

    }


    private void dispatch(String url,
                          HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        RequestDispatcher requestDispatcher =
                this.getServletContext().getRequestDispatcher(url);
        if (requestDispatcher != null) {
            //dispatch this request
            requestDispatcher.forward(request, response);
        } else {
            throw new ServletException("Could not dispatch request to uri" + url);
        }
    }


    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doGet(httpServletRequest, httpServletResponse);
    }

    @Override
    public void init() throws ServletException {
        super.init();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
