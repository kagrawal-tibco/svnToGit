/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor.impl;

import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.xml.sax.InputSource;

import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.model.Error;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.ArtifactLockResponse;
import com.tibco.cep.studio.rms.response.impl.ErrorResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;
import com.tibco.xml.datamodel.XiNode;


/**
 * @author aathalye
 *
 */
public class ArtifactLockResponseProcessor<O extends Object, R extends IResponse> extends AbstractArtifactsResponseProcessor 
	implements IResponseProcessor<O, R> {
	
	private IResponseProcessor<O, R> processor;
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor#addProcessor(com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor)
	 */
	public <T extends IResponseProcessor<O, R>> void addProcessor(T processor) {
		this.processor = processor;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor#nextProcessor()
	 */
	public IResponseProcessor<O, R> nextProcessor() {
		return processor;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor#processResponse(java.io.InputStream)
	 */
	public IResponse processResponse(final OperationType responseType,
									 HttpResponse httpResponse,
			                         final InputStream response) throws ResponseProcessingException {
		if (responseType.equals(OperationType.LCK_REQUEST)) {
			ArtifactLockResponse lockResponse = new ArtifactLockResponse();
			Header lockRequestIdHeader = httpResponse.getFirstHeader("lockRequestId");
			if (lockRequestIdHeader != null) {
				//Successful lock
				lockResponse.holdResponseObject(lockRequestIdHeader.getValue());
				return lockResponse;
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
