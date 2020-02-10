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
import com.tibco.cep.studio.rms.response.impl.ErrorResponse;
import com.tibco.cep.studio.rms.response.impl.GenerateDeployableResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;
import com.tibco.xml.datamodel.XiNode;

/**
 * @author aathalye
 *
 */
public class GenerateDeployableResponseProcessor<O extends Object, R extends IResponse>
		extends AbstractArtifactsResponseProcessor implements
		IResponseProcessor<O, R> {
	
	private IResponseProcessor<O, R> processor;

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.processor.IResponseProcessor#addProcessor(com.tibco.cep.studio.rms.response.processor.IResponseProcessor)
	 */
	@Override
	public <T extends IResponseProcessor<O, R>> void addProcessor(T processor) {
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
		if (responseType == OperationType.GENERATE_DEPLOYABLE) {
			IResponse returnResponse = null;
			Header statusHeader = httpResponse.getFirstHeader("earBuilt");
			
			try {
				if (statusHeader != null) {
					String refreshStatus = statusHeader.getValue();
					if ("true".equals(refreshStatus)) {
						returnResponse = new GenerateDeployableResponse();
						returnResponse.holdResponseObject(refreshStatus);
					} else {
						XiNode rootNode = RMSUtil.getXiNodeFromSource(new InputSource(response));
						Error error = getError(rootNode.getFirstChild());
						returnResponse = new ErrorResponse();
						returnResponse.holdResponseObject(error);
					}
				}
				return returnResponse;
			} catch (Exception e) {
				throw new ResponseProcessingException(e);
			}
		} else if (processor != null) {
			return processor.processResponse(responseType, httpResponse, response);
		}
		return null;
	}
}
