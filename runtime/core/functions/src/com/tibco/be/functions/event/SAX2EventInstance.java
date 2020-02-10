package com.tibco.be.functions.event;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmType;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 8, 2004
 * Time: 3:44:39 PM
 * To change this template use Options | File Templates.
 */
public class SAX2EventInstance implements XmlContentHandler {

    static ExpandedName ATTRTYPE_NM = ExpandedName.makeName("http://www.w3.org/2001/XMLSchema-instance", "type");


    SimpleEvent evt;
    XmlBuilder xiBuilder;
    HashMap pfxMapping;
    //    SmElement type;
    boolean inPayloadElement = false;
    int depth = 0;
    String currentLocalnm;
    HashMap arguments;
//    private XiNode m_currentNode;
//    private Locator m_locator;
    //    private ArrayList m_pendingNamespaces;
    Logger m_logger;


    public SAX2EventInstance(SimpleEvent evt, Logger logger) {
        m_logger = logger;
        xiBuilder = new XmlBuilder(logger);
        pfxMapping = new HashMap();
        //      type = null;
        arguments = new HashMap();
        this.evt = evt;
    }


    public void attribute(ExpandedName expandedName, String s, SmAttribute smAttribute) throws SAXException {
    	if(inPayloadElement) {
    		xiBuilder.attribute(expandedName, s, smAttribute);
    	} else {
    		if (expandedName.getLocalName().equals("extId")) {
	            try {
	                evt.setExtId(s);
	            } catch (ExtIdAlreadyBoundException e) {
	                throw new SAXException(e);
	            }
    		}
        }
    }

    public void attribute(ExpandedName expandedName, XmlTypedValue value, SmAttribute smAttribute) throws SAXException {

        try {
        	if (inPayloadElement) {
                xiBuilder.attribute(expandedName, value, smAttribute);
        	} else {
	        	if (expandedName.getLocalName().equals("extId")) {
	                evt.setExtId(value.getAtom(0).castAsString());
	            } else if (expandedName.getLocalName().equals("soapAction") && evt instanceof SOAPEvent) {
	                ((SOAPEvent)evt).setSoapAction(value.getAtom(0).castAsString());
	            }
        	}
        }
        catch (ExtIdAlreadyBoundException e) {
            throw new SAXException(e);
        }
    }


    public void comment(String s) throws SAXException {
    }

    public void endDocument() throws SAXException {
        xiBuilder.endDocument();
        if (null != evt) {
            XiNode node = xiBuilder.getNode();
            if(node != null)  {
                setPayload(evt, node );
            }
        }
    }

    private void setPayload(SimpleEvent evt, XiNode payloadNode) throws SAXException {
        final RuleSession sess = RuleSessionManager.getCurrentRuleSession();
        final RuleServiceProvider provider = sess.getRuleServiceProvider();
        final XiNode payloadContentNode = payloadNode.getFirstChild();
        if (null == payloadContentNode) {
            return;
        }
        final Map<String, String> prefixesToCopy = new HashMap<String, String>();

        for (Iterator it = payloadNode.getLocalPrefixes(); it.hasNext();) {
            final String payloadPrefix = (String) it.next();
            boolean found = false;
            for (Iterator itContent = payloadContentNode.getLocalPrefixes(); (!found) && itContent.hasNext();) {
                final String payloadContentPrefix = (String) itContent.next();
                found = payloadContentPrefix.equals(payloadPrefix);
            }
            if (!found) {
                try {
                    prefixesToCopy.put(payloadPrefix, payloadNode.getNamespaceURIForPrefix(payloadPrefix));
                } catch (Exception e) {
                    throw new SAXException(e);
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


    public void endElement(ExpandedName expandedName, SmElement smElement, SmType smType) throws SAXException {

        try {
            --depth;
            if (depth <= 3) {
                inPayloadElement = false;
            }
            else {
                xiBuilder.endElement(expandedName, smElement, smType);
            }
        }
        catch (Exception e) {
            throw new SAXException(e);
        }
    }

    public void ignorableWhitespace(String s, boolean b) throws SAXException {
    }

    public void prefixMapping(String prefix, String uri) throws SAXException {
        xiBuilder.prefixMapping(prefix, uri);
        pfxMapping.put(prefix, uri);
    }


    public void processingInstruction(String s, String s1) throws SAXException {
    }

    public void setDocumentLocator(Locator locator) {
        xiBuilder.setDocumentLocator(locator);
    }

    public void skippedEntity(String s) throws SAXException {
    }

    public void startDocument() throws SAXException {
        xiBuilder.startDocument();
    }


    public void startElement(ExpandedName expandedName, SmElement smElement, SmType smType) throws SAXException {

        try {
            ++depth;

            if (depth == 3) {
                currentLocalnm = expandedName.getLocalName();
            }
            else if (depth > 3)   {
                inPayloadElement = true;
                xiBuilder.startElement(expandedName, smElement, smType);
            }

        } catch (Exception e) {
            throw new SAXException(e);
        }

    }

    public void text(String s, boolean b) throws SAXException {
        try {
        	if (inPayloadElement) {
        		xiBuilder.text(s,b);
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

    public void text(XmlTypedValue value, boolean b) throws SAXException {
        try {
        	if (inPayloadElement) {
                xiBuilder.text(value,b);
        	} else {
		        if (depth == 3) {
		            evt.setProperty(currentLocalnm,value);
		        }
        	}
        }
        catch (Exception e) {
            throw new SAXException(e);
        }
    }

    public SimpleEvent getEvent() {
        return evt;
    }

    public HashMap getArguments() {
        return arguments;
    }

}
