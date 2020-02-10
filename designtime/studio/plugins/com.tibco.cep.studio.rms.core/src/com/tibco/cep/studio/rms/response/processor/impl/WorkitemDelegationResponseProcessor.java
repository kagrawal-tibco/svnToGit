/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor.impl;

import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.tibco.cep.studio.rms.core.RMSCorePlugin;
import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.WorkitemDelegationResponse;
import com.tibco.cep.studio.rms.response.impl.WorkitemDelegationResponse.DelegationStatus;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;

/**
 * @author aathalye
 *
 */
public class WorkitemDelegationResponseProcessor<O extends Object, R extends IResponse> implements IResponseProcessor<O, R> {
	
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
	 * @see com.tibco.cep.mgmtserver.rms.response.processor.IResponseProcessor#processResponse(com.tibco.cep.mgmtserver.rms.utils.ResponseType, java.io.InputStream)
	 */
	public IResponse processResponse(OperationType responseType,
									 HttpResponse httpResponse,
			                         InputStream response) throws ResponseProcessingException {
		if (OperationType.WORKITEM_DELEGATION == responseType) {
			try {
				Header revisionIdHeader = httpResponse.getFirstHeader("taskId");
				if (revisionIdHeader != null) {
					WorkitemDelegationResponse delegationResponse = new WorkitemDelegationResponse();
					delegationResponse.holdResponseObject(DelegationStatus.SUCCESS);
					return delegationResponse;
				}
			} catch (Exception e) {
				RMSCorePlugin.log(e);
			}
		}
		return null;
	}
}
