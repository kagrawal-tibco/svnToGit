package com.tibco.be.functions.trax.transform;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.tibco.be.util.XmlBuilder;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.ExtIdAlreadyBoundException;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SOAPEvent;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.datamodel.XiNode;

public class EventContentHandler implements ContentHandler {
	
    SimpleEvent evt;
    XmlBuilder xiBuilder;
    boolean inPayloadElement = false;
    int depth = 0;
    String currentLocalnm;
    Logger m_logger;

    public EventContentHandler(SimpleEvent evt, Logger logger){
        m_logger = logger;
        xiBuilder = new XmlBuilder(logger);
        this.evt = evt;
    }

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
        try {
        	String s = String.valueOf(ch, start, length);
        	if (inPayloadElement) {
        		xiBuilder.text(s, false);
        	} else {
	            if (depth == 3) {
	                evt.setProperty(currentLocalnm,s);
	            }
            }
        }
        catch (Exception e) {
            throw new SAXException(e);
        }
	}

	@Override
	public void endDocument() throws SAXException {
        xiBuilder.endDocument();
        if (null != evt) {
            XiNode node = xiBuilder.getNode();
            if(node != null)  {
                setPayload(evt, node );
            }
        }
	}
	
    private void setPayload(SimpleEvent evt, XiNode payloadNode) {
        final RuleSession sess = RuleSessionManager.getCurrentRuleSession();
        final RuleServiceProvider provider = sess.getRuleServiceProvider();
        final XiNode payloadContentNode = payloadNode.getFirstChild();
        if (null == payloadContentNode) {
            return;
        }
        final Map<String, String> prefixesToCopy = new HashMap<String, String>();

        for (Iterator<?> it = payloadNode.getLocalPrefixes(); it.hasNext();) {
            final String payloadPrefix = (String) it.next();
            boolean found = false;
            for (Iterator<?> itContent = payloadContentNode.getLocalPrefixes(); (!found) && itContent.hasNext();) {
                final String payloadContentPrefix = (String) itContent.next();
                found = payloadContentPrefix.equals(payloadPrefix);
            }
            if (!found) {
                try {
                    prefixesToCopy.put(payloadPrefix, payloadNode.getNamespaceURIForPrefix(payloadPrefix));
                } catch (PrefixToNamespaceResolver.PrefixNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        for (String prefix : prefixesToCopy.keySet()) {
            payloadContentNode.setNamespace(prefix, prefixesToCopy.get(prefix));
        }

        final EventPayload payload =
                provider.getTypeManager().getPayloadFactory().createPayload(evt.getExpandedName(), payloadContentNode);
        evt.setPayload(payload);
    }

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
        try {
            --depth;
            if (depth <= 3) {
                inPayloadElement = false;
            } else {
                xiBuilder.endElement(ExpandedName.makeName(uri, name), null, null);
            }
        }
        catch (Exception e) {
            throw new SAXException(e);
        }

	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
	}

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		xiBuilder.setDocumentLocator(locator);
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
	}

	@Override
	public void startDocument() throws SAXException {
		xiBuilder.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException {
		
        ++depth;
		
        if (depth == 3) {
            currentLocalnm = localName;
        } else if (depth > 3) {
            inPayloadElement = true;
            xiBuilder.startElement(ExpandedName.makeName(uri, name), null, null);
        }
        handleAttributes(atts);
	}

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
        xiBuilder.prefixMapping(prefix, uri);
	}
	
	private void handleAttributes(Attributes atts) throws SAXException{
		int attLength = atts.getLength();
		for(int i=0; i < attLength; i++){
			String attName = atts.getQName(i);
			String attLocalName = atts.getLocalName(i);
			String namespaceURI = atts.getURI(i);
			String value = atts.getValue(i);
	    	if(inPayloadElement) {
	    		xiBuilder.attribute(ExpandedName.makeName(namespaceURI, attName), value, null);
	    	} else {
	    		if ("extId".equals(attLocalName)) {
		            try {
		                evt.setExtId(value);
		            } catch (ExtIdAlreadyBoundException e) {
		                throw new SAXException(e);
		            }
	    		} else if ("soapAction".equals(attLocalName) && evt instanceof SOAPEvent) {
	                ((SOAPEvent)evt).setSoapAction(value);
	            }
	        }
		}
	}
}
