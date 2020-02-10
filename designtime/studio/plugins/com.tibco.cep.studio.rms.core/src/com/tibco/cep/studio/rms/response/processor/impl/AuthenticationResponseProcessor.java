/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor.impl;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;
import com.tibco.cep.studio.rms.response.impl.AuthenticationResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;


/**
 * @author aathalye
 *
 */
public class AuthenticationResponseProcessor<O extends Object, R extends IResponse> implements IResponseProcessor<O, R> {
	
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
		if (responseType.equals(OperationType.LOGIN)) {
			AuthenticationResponse tokenResponse = new AuthenticationResponse();
			// Deal with the response.
			try {
				InputSource inputSource = new InputSource(response);
				Document doc = RMSUtil.getDocFromSource(inputSource, false);
				String tokenStringValue = getTokenPart(doc);
				
				// token_Elem.get
				if (tokenStringValue != null) {
					tokenResponse.holdResponseObject(tokenStringValue);
					return tokenResponse;
				}
			} catch (Exception e) {
				throw new ResponseProcessingException(e);
			}
		} 
		return processor.processResponse(responseType, httpResponse, response);
		
	}
	
	private String getTokenPart(Document responseDoc) throws Exception {
		//Get the <event>
		NodeList eventList = responseDoc.getElementsByTagName("event");
		//get first one
		Element eventElem = (Element)eventList.item(0);
		
		if (eventElem == null) {
			//New format
			Node tokenChild = responseDoc.getFirstChild();
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Source source = new DOMSource(tokenChild);
	        StringWriter sw = new StringWriter();
	        Result stringResult = new StreamResult(sw);
	        transformer.transform(source, stringResult);
	        return sw.toString();
		}
		//Old format
		//Get its <token> child
		NodeList tokenList = eventElem.getElementsByTagName("token");
		//Get first one
		Element token = (Element)tokenList.item(0);
		//Get its string content
		Text textNode = (Text)token.getFirstChild();
		String text = textNode.getNodeValue();
		return text;
	}

}
