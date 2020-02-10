/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.xml.sax.InputSource;

import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.Error;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.ErrorResponse;
import com.tibco.cep.studio.rms.response.impl.LogoutResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;
import com.tibco.xml.datamodel.XiNode;

/**
 * @author aathalye
 *
 */
public class LogoutResponseProcessor<O extends Object, R extends IResponse> extends AbstractArtifactsResponseProcessor 
	implements IResponseProcessor<O, R> {
	
	private IResponseProcessor<O, R> processor;

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.processor.IResponseProcessor#addProcessor(com.tibco.cep.studio.rms.response.processor.IResponseProcessor)
	 */
	
	public <T extends IResponseProcessor<O, R>> void addProcessor(T processor) {
		this.processor = processor;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.processor.IResponseProcessor#nextProcessor()
	 */
	
	public IResponseProcessor<O, R> nextProcessor() {
		return processor;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.processor.IResponseProcessor#processResponse(com.tibco.cep.mgmtserver.rms.utils.OperationType, org.apache.commons.httpclient.HttpMethodBase, java.io.InputStream)
	 */
	public IResponse processResponse(OperationType responseType,
									 HttpResponse httpResponse, 
			                         InputStream response)
			throws ResponseProcessingException {
		if (responseType == OperationType.LOGOUT) {
			//In this case username who logged out should be looked for or error message
			Header userIdHeader = httpResponse.getFirstHeader("loggedInUserName");
			if (userIdHeader != null) {
				//Get value
				String loggedOutUser = userIdHeader.getValue();
				if (loggedOutUser != null && !loggedOutUser.trim().isEmpty()) {
					try {
						loggedOutUser = URLDecoder.decode(loggedOutUser, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						//Use the value as is
					}
				}
				LogoutResponse successResponse = new LogoutResponse();
				successResponse.holdResponseObject(loggedOutUser);
				return successResponse;
			} else {
				//Look for error payload
				XiNode rootNode = null;
				try {
					rootNode = RMSUtil.getXiNodeFromSource(new InputSource(response));
					Error error = getError(rootNode.getFirstChild());
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.holdResponseObject(error);
					return errorResponse;
				} catch (Exception e) {
					throw new ResponseProcessingException(e);
				}
			}
		} else if (processor != null) {
			return processor.processResponse(responseType, httpResponse, response);
		}
		return null;
	}
}
