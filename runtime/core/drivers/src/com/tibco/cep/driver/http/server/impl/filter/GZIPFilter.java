/**
 * 
 */
package com.tibco.cep.driver.http.server.impl.filter;

import static com.tibco.cep.driver.http.HttpChannelConstants.CONTENT_ENCODING_HEADER;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Filter to intercept GZIP compatible requests and convert them into GZip compatible requests.
 * 
 * @author vpatil
 */
public class GZIPFilter implements Filter {
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		 
		if (req instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest)req;
			if (isGZIPed(request)) {
				req = new GZIPRequestWrapper(request);
			}
		}
		chain.doFilter(req, resp);
	}
	
	@Override
	public void destroy() {}
	
	/**
	 * Check if the request is GZiped
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	private boolean isGZIPed(HttpServletRequest httpServletRequest) {
	    boolean isGZIPed = false;
	    
	    String contentEncoding = httpServletRequest.getHeader(CONTENT_ENCODING_HEADER);
	    if (null != contentEncoding && contentEncoding.toLowerCase().indexOf("gzip") > -1) {
	    	isGZIPed = true;
	    }
	    
	    return isGZIPed;
	}
}
