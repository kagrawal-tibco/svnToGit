/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.LckResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;


/**
 * @author aathalye
 *
 */
public class LckResponseProcessor<O extends Object, R extends IResponse> implements IResponseProcessor<O, R> {
	
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
		if(responseType.equals(OperationType.LCK_REQUEST)) {
			LckResponse lckResponse = new LckResponse();
			Map<Integer, String> map = new HashMap<Integer, String>();
			// Deal with the response.
			try {
				InputSource inputSource = new InputSource(response);
				Document doc = RMSUtil.getDocFromSource(inputSource, false);
				if (doc != null) {
					Element event_Elem = (Element) doc.getElementsByTagName("event")
							.item(0);
					Element errorCode_Elem = (Element) event_Elem.getElementsByTagName(
							"errorCode").item(0);
					if (errorCode_Elem != null) {
						Text errorCodeNode = (Text)errorCode_Elem.getFirstChild();
						// token_Elem.get
						if (errorCodeNode != null) {
							String errorCode = errorCodeNode.getNodeValue();
							map.put(1, errorCode);
						}
					}
					Element message_Elem = (Element) event_Elem.getElementsByTagName(
							"message").item(0);
					if (message_Elem != null) {
						Text messageNode = (Text)message_Elem.getFirstChild();
						// token_Elem.get
						if (messageNode != null) {
							String message = messageNode.getNodeValue();
							map.put(2, message);
						}
					}
					lckResponse.holdResponseObject(map);
					return lckResponse;
				}
			} catch (Exception e) {
				throw new ResponseProcessingException(e);
			}
		} 
		return processor.processResponse(responseType, httpResponse, response);
	}
}
