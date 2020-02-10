package com.tibco.be.functions.xpath;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.genxdm.exceptions.GenXDMException;
import org.genxdm.io.DtdAttributeKind;

import com.tibco.be.util.GenXDMXmlBuilder;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.ExtIdAlreadyBoundException;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SOAPEvent;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.flavor.XSDL;

public class EventSequenceHandler extends AbstractSequenceHandler {

    static ExpandedName ATTRTYPE_NM = ExpandedName.makeName("http://www.w3.org/2001/XMLSchema-instance", "type");

    SimpleEvent evt;
    GenXDMXmlBuilder xiBuilder;
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

    public EventSequenceHandler(SimpleEvent evt, Logger logger) {
        m_logger = logger;
        xiBuilder = new GenXDMXmlBuilder(logger);
        pfxMapping = new HashMap();
        //      type = null;
        arguments = new HashMap();
        this.evt = evt;
    }

	public SimpleEvent getEvent() {
		return evt;
	}

    public HashMap getArguments() {
        return arguments;
    }

    @Override
	public void attribute(String arg0, String arg1, String arg2, String arg3,
			DtdAttributeKind arg4) throws GenXDMException {
//    	if(inPayloadElement) {
//    		xiBuilder.attribute(ExpandedName.makeName(arg2), arg3, smAttribute);
//    	} else {
//    		if (expandedName.getLocalName().equals("extId")) {
//	            try {
//	                evt.setExtId(s);
//	            } catch (ExtIdAlreadyBoundException e) {
//	                throw new GenXDMException(e);
//	            }
//    		}
//        }
    }

	@Override
	public void attribute(String ns, String elementName, String prefix,
			List<? extends XmlAtomicValue> vals, QName arg4)
			throws GenXDMException {
		XmlAtomicValue value = vals.get(0);
        try {
        	ExpandedName expandedName = ExpandedName.makeName(ns, elementName);
			if (inPayloadElement) {
                xiBuilder.attribute(expandedName, value, null); // TODO : smAttribute
        	} else {
	        	if (expandedName.getLocalName().equals("extId")) {
	                evt.setExtId(value.getAtom(0).castAsString());
	            } else if (expandedName.getLocalName().equals("soapAction") && evt instanceof SOAPEvent) {
	                ((SOAPEvent)evt).setSoapAction(value.getAtom(0).castAsString());
	            }
        	}
        }
        catch (ExtIdAlreadyBoundException e) {
            throw new GenXDMException(e);
        }
    }

	@Override
	public void endDocument() throws GenXDMException {
        xiBuilder.endDocument();
        if (null != evt) {
            XiNode node = xiBuilder.getNode();
            if(node != null)  {
                setPayload(evt, node );
            }
        }
    }

	@Override
	public void endElement() throws GenXDMException {
        try {
            --depth;
            if (depth <= 3) {
                inPayloadElement = false;
            }
            else {
                xiBuilder.endElement(null, null, null);
            }
        }
        catch (Exception e) {
            throw new GenXDMException(e);
        }
    }

	@Override
	public void startDocument(URI arg0, String arg1) throws GenXDMException {
        xiBuilder.startDocument();
	}

	@Override
	public void startElement(String ns, String elementName, String prefix, QName arg3)
			throws GenXDMException {
    	ExpandedName expandedName = ExpandedName.makeName(ns, elementName);

        try {
            ++depth;

            if (depth == 3) {
                currentLocalnm = elementName;
            }
            else if (depth > 3)   {
                inPayloadElement = true;
                xiBuilder.startElement(expandedName, null, XSDL.UNTYPED_ANY);
            }

        } catch (Exception e) {
            throw new GenXDMException(e);
        }

    }

	@Override
	public void text(String s) throws GenXDMException {
        try {
        	if (inPayloadElement) {
        		xiBuilder.text(s,false);
        	} else {
	            if (depth == 3) {
	                evt.setProperty(currentLocalnm,s);
	            }
            }
        }
        catch (Exception e) {
            throw new GenXDMException(e);
        }
    }

	@Override
	public void text(List<? extends XmlAtomicValue> vals)
			throws GenXDMException {
		XmlAtomicValue value = vals.get(0);
        try {
        	if (inPayloadElement) {
                xiBuilder.text(value,false); // TODO : boolean?
        	} else {
		        if (depth == 3) {
		            evt.setProperty(currentLocalnm,value);
		        }
        	}
        }
        catch (Exception e) {
            throw new GenXDMException(e);
        }
    }

    public void prefixMapping(String prefix, String uri) throws GenXDMException {
    	// doesn't appear to be used anymore - they decided to change the method name to 'namespace'
        xiBuilder.prefixMapping(prefix, uri);
        pfxMapping.put(prefix, uri);
    }
    
    @Override
    public void namespace(String prefix, String uri) throws GenXDMException {
    	xiBuilder.prefixMapping(prefix, uri);
    	pfxMapping.put(prefix, uri);
    }
    
    private void setPayload(SimpleEvent evt, XiNode payloadNode) throws GenXDMException {
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
                    throw new GenXDMException(e);
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

}
