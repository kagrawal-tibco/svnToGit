package com.tibco.be.functions.event;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Stack;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.ExtIdAlreadyBoundException;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiBuilder;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmType;

public class SAX4EventInstance implements XmlContentHandler {

    static ExpandedName ATTRTYPE_NM = ExpandedName.makeName("http://www.w3.org/2001/XMLSchema-instance", "type");


    SimpleEvent evt;
    XiBuilder xiBuilder;
    HashMap pfxMapping;
//    SmElement type;
    boolean inPayloadElement = false;
    int depth = 0;
    HashMap arguments;
    RuleSession ruleSession;
    Logger m_logger;
    Stack<ExpandedName> currentLocalName = new Stack<ExpandedName>();

    public SAX4EventInstance(RuleSession ruleSession, SimpleEvent evt) {
        xiBuilder = new XiBuilder();
        pfxMapping = new HashMap();
  //      type = null;
        arguments = new HashMap();
        this.evt = evt;
        this.ruleSession=ruleSession;
        this.m_logger = ruleSession.getRuleServiceProvider().getLogger(getClass());
    }


    public void attribute(ExpandedName expandedName, String s, SmAttribute smAttribute) throws SAXException {
    	if (expandedName.getLocalName().equals("inlineAssert")) {
    		return; // ignored for Events
    	}
        if (expandedName.getLocalName().equals("extId")) {
            try {
                if (!inPayloadElement) {
                    evt.setExtId(s);
                }  else
                    xiBuilder.attribute(expandedName, s, smAttribute);
            } catch (ExtIdAlreadyBoundException e) {
                throw new SAXException(e);
            }
        }else {
            xiBuilder.attribute(expandedName, s, smAttribute);
        }
    }

    public void attribute(ExpandedName expandedName, XmlTypedValue value, SmAttribute smAttribute) throws SAXException {
    	if (expandedName.getLocalName().equals("inlineAssert")) {
    		return; // ignored for Events
    	}
        try {
            if (expandedName.getLocalName().equals("extId")) {
                if (!inPayloadElement)
                    evt.setExtId(value.getAtom(0).castAsString());
                else
                    xiBuilder.attribute(expandedName, value, smAttribute);
            }
            else if (!expandedName.getLocalName().equals("Id"))
            {
                 xiBuilder.attribute(expandedName, value, smAttribute);
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
           // m_logger.log(Level.INFO,"Payload:"+ XiSerializer.serialize(node));

            if(node != null) {
            	//m_logger.log(Level.INFO, "Event :"+evt +" Class:"+evt.getClass()+" isSimpleEvent:"+ (evt instanceof SimpleEvent));
                if (evt instanceof SimpleEvent) {
                    XiNode n1 = XiChild.getChild(node, ExpandedName.makeName("payload"));
                    if(n1 != null) {
                    	//m_logger.log(Level.INFO,"1payload node:"+ XiSerializer.serialize(n1));
                        setPayload(evt, n1 );
                    } else if(node.getNodeKind().equals(XmlNodeKind.ELEMENT) && 
                    		node.getName() != null && 
                    		node.getName().equals(ExpandedName.makeName("payload"))) {
                    	//m_logger.log(Level.INFO,"2payload node:"+ XiSerializer.serialize(node));
                    	setPayload(evt, node);
                    }
                }
            }
        }
    }

    private void setPayload(SimpleEvent evt, XiNode n1) throws SAXException {
    	// commenting code - use the member variable instead 
        //RuleSession sess = RuleSessionManager.getCurrentRuleSession();
        //RuleServiceProvider provider = sess.getRuleServiceProvider();
    	RuleServiceProvider provider = ruleSession.getRuleServiceProvider();
    	XiNode firstChild = XiNodeUtilities.getNthElement(n1, 0);
        EventPayload payload = provider.getTypeManager().getPayloadFactory().createPayload(evt.getExpandedName(), firstChild);
        evt.setPayload(payload);
        final XiNode docRoot = XiSupport.getXiFactory().createDocument();
        final XiNode rootNode = docRoot.appendElement(evt.getExpandedName());
        try {
			evt.toXiNode(rootNode);
			m_logger.log(Level.DEBUG, "Event:"+XiSerializer.serialize(docRoot));
        } catch (Exception e) {
            throw new SAXException(e);
        }
    }

    public void endElement(ExpandedName expandedName, SmElement smElement, SmType smType) throws SAXException {

        try {
        	//m_logger.log(Level.INFO,"EE->depth:"+depth+" en:"+expandedName);
        	currentLocalName.pop();
            --depth;
            String name = expandedName.getLocalName();

            if (inPayloadElement) {
                xiBuilder.endElement(expandedName, smElement, smType);
            }

            if ((depth <= 3) && (EventPayload.PAYLOAD_PROPERTY.equalsIgnoreCase(name))) {
                inPayloadElement = false;
            }
            if (depth < 3) {
                inPayloadElement = false;
            }
        } catch (Exception e) {
            throw new SAXException(e);
        }


    }

    public void ignorableWhitespace(String s, boolean b) throws SAXException {
    }

    public void prefixMapping(String pfx, String value) throws SAXException {
        pfxMapping.put(pfx, value);
        xiBuilder.prefixMapping(pfx, value);
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
        	//m_logger.log(Level.INFO,"SE->depth:"+depth+" en:"+expandedName);
            currentLocalName.push(expandedName);
            if (evt == null) {
                TypeManager.TypeDescriptor entityType= ruleSession.getRuleServiceProvider().getTypeManager().getTypeDescriptor(expandedName);
                evt =(SimpleEvent) this.newInstance(entityType.getImplClass());
                return;
            }
            if (depth == 2)   {
                //Elements attribute
                if ("payLoad".equalsIgnoreCase(currentLocalName.peek().getLocalName())) {
                    inPayloadElement = true;
                }
            }

            if ((inPayloadElement) && (evt != null)) {
                xiBuilder.startElement(expandedName, smElement, smType);
            }
        } catch (Exception e) {
            throw new SAXException(e);
        }
    }

    public void text(String s, boolean b) throws SAXException {

        try {
        	//m_logger.log(Level.INFO,"Text->depth:"+depth+" String:"+s);
            if ((depth == 2) && (!inPayloadElement)) {
                if (null != evt) {

                    evt.setProperty(currentLocalName.peek().getLocalName(),s);
//                    evt.setProperty(currentLocalnm, s);
                }
            }
            else if (inPayloadElement) {
                xiBuilder.text(s,b);
            }
            else {
                arguments.put(currentLocalName.peek().getLocalName(), s);
            }
        }
        catch (Exception e) {
            throw new SAXException(e);
        }
    }

    public void text(XmlTypedValue value, boolean b) throws SAXException {
        try {
        	//m_logger.log(Level.INFO,"Text->depth:"+depth+" Value:"+value);
            if ((depth == 2) && (!inPayloadElement)) {
                if (null != evt) {
                	if(!currentLocalName.peek().getLocalName().equalsIgnoreCase(EventPayload.PAYLOAD_PROPERTY)) {
                		evt.setProperty(currentLocalName.peek().getLocalName(),value);
                	} else {
                		xiBuilder.text(value,b);
                	}
                }
            }
            else if (inPayloadElement) {
                xiBuilder.text(value,b);
            }
            else {
                arguments.put(currentLocalName.peek().getLocalName(), value.toString());
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

    private Object newInstance (Class clz) throws Exception{
        long id = ruleSession.getRuleServiceProvider().getIdGenerator().nextEntityId(clz);
        Constructor cons = clz.getConstructor(new Class[] {long.class});
        Object o=cons.newInstance(new Object[] {new Long(id)});
        return o;
    }

}
