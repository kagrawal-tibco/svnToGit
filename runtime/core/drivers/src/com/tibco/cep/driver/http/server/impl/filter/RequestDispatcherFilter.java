package com.tibco.cep.driver.http.server.impl.filter;

import static com.tibco.cep.driver.soap.SoapConstants.HEADER_SOAP_ACTION;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Mar 19, 2009
 * Time: 2:52:32 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class RequestDispatcherFilter implements Filter {

    protected FilterConfig filterConfig;

    public void destroy() {
        filterConfig = null;
    }

    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        //Get servlet context from here
        ServletContext servletContext = filterConfig.getServletContext();

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        //Check if it has a soap action
        String soapAction = httpServletRequest.getHeader(HEADER_SOAP_ACTION);

        String requestURI = httpServletRequest.getRequestURI();

        if (soapAction != null && soapAction.length() > 0) {
            //Dispatch to soap servlet
            dispatchToServlet("soap",
                    servletContext,
                    httpServletRequest,
                    httpServletResponse);
        } else {
            if (requestURI.equalsIgnoreCase("/")) {
                String welcomePage = (String) servletContext.getAttribute("welcome-page");
                if (welcomePage != null && welcomePage.trim().length() != 0)
                    requestURI = requestURI + welcomePage;
            }

            //Check if it can be dispatched to any of registered servlets
            boolean canDispatch = canDispatch(requestURI, servletContext);

            if (canDispatch) {
                dispatchToURI(requestURI,
                        servletContext,
                        httpServletRequest,
                        httpServletResponse);
            }
        }
    }

    private void dispatchToServlet(String servletName,
                                   ServletContext servletContext,
                                   HttpServletRequest httpServletRequest,
                                   HttpServletResponse httpServletResponse) throws ServletException, IOException {


        RequestDispatcher requestDispatcher = servletContext.getNamedDispatcher(servletName);

        if (requestDispatcher != null) {
            //Dispatch to this servlet
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
    }

    private void dispatchToURI(String requestURI,
                               ServletContext servletContext,
                               HttpServletRequest httpServletRequest,
                               HttpServletResponse httpServletResponse) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(requestURI);

        if (requestDispatcher != null) {
            //Dispatch to this servlet
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
    }

    private boolean canDispatch(String requestURI,
                                ServletContext servletContext) {
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(requestURI);
        return requestDispatcher != null;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
}
