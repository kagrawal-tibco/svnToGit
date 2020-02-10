/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor.impl;

import java.io.InputStream;

import org.apache.http.HttpResponse;

import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.ArtifactStatusChangeResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;

/**
 * @author aathalye
 *
 */
public class ArtifactStatusChangeResponseProcessorImpl<O extends Object, R extends IResponse>
		extends AbstractArtifactsResponseProcessor implements IResponseProcessor<O, R> {
	
	protected IResponseProcessor<O, R> processor;

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.processor.IResponseProcessor#addProcessor(com.tibco.cep.studio.rms.response.processor.IResponseProcessor)
	 */
	@Override
	public <T extends IResponseProcessor<O, R>> void addProcessor(T processor) {
		// TODO Auto-generated method stub
		this.processor = processor;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.processor.IResponseProcessor#nextProcessor()
	 */
	@Override
	public IResponseProcessor<O, R> nextProcessor() {
		return processor;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.processor.IResponseProcessor#processResponse(com.tibco.cep.mgmtserver.rms.utils.OperationType, org.apache.commons.httpclient.HttpMethodBase, java.io.InputStream)
	 */
	@Override
	public IResponse processResponse(OperationType responseType,
									 HttpResponse httpResponse, 
			                         InputStream response) throws ResponseProcessingException {
		if (OperationType.STATUS_CHANGE == responseType) {
			ArtifactStatusChangeResponse statusChangeResponse = new ArtifactStatusChangeResponse();
			statusChangeResponse.holdResponseObject(new Object());
			return statusChangeResponse;
		} if (processor != null) {
			return processor.processResponse(responseType, httpResponse, response);
		}
		return null;
	}
}
