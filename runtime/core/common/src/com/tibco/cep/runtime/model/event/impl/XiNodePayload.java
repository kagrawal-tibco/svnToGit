package com.tibco.cep.runtime.model.event.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.ref.SoftReference;
import java.util.Iterator;

import org.xml.sax.InputSource;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.util.JSONXiNodeConversionUtil;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNodeTest;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.schema.SmParticleTerm;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 22, 2006
 * Time: 12:28:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class XiNodePayload implements EventPayload {

    protected static final XmlNodeTest NODE_TEST_ELEMENT  = XiNodeUtilities.NODE_TEST_ELEMENT;
    static {
        try {
            PayloadFactoryImpl.registerType(XiNodePayload.class, 1);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //todo - Not sure why? but copied from V1.3
//    private static ThreadLocal parser = new ThreadLocal() {
//        protected synchronized Object initialValue() {
//            return XiParserFactory.newInstance();
//        }
//    };

    private XiNode xmlNode;
    private SoftReference<byte[]> xmlNodeAsOrigBytes;
    
    private String encoding;
    
    private boolean isJSONPayload;

    /**
     * Semantics of reconstruction
     * @param eventDescriptor
     * @param buf
     */
    public XiNodePayload(TypeManager.TypeDescriptor eventDescriptor,  byte[] buf) throws Exception {
        this(PayloadFactoryImpl.getPayloadTerm(eventDescriptor.getSmElement()), buf);
    }

    public XiNodePayload(XiNode node, TypeManager.TypeDescriptor eventDescriptor) {
        this(PayloadFactoryImpl.getPayloadTerm(eventDescriptor.getSmElement()), node);
    }

    public XiNodePayload(SmParticleTerm element, XiNode node) {
        this.xmlNode = node;
    }

    protected XiNodePayload(SmParticleTerm element, String xmlString) throws Exception {
        this.xmlNode = parseDocument(xmlString);
    }

    protected XiNodePayload(SmParticleTerm element, byte[] buf) throws Exception {
        if(buf != null){
            this.xmlNode = parseDocument(new ByteArrayInputStream(buf));
            this.xmlNodeAsOrigBytes = new SoftReference<byte[]>(buf);
        }
    }

    public XiNode getNode() {
        return xmlNode;
    }

    public String toString() {
    	if (xmlNode == null)
    		return null;
    	else if (isJSONPayload) {
    		try {
    			return JSONXiNodeConversionUtil.convertXiNodeToJSON(xmlNode, true).toString();
    		} catch (Exception exception) {
    			throw new RuntimeException(exception);
    		}
    	} else {
    		return XiNodeUtilities.toString(xmlNode, encoding);
    	}
    }

    public void toXiNode(XiNode root) {
        if (null != xmlNode) {
            XiNode $payload = root.appendElement(ExpandedName.makeName(PAYLOAD_PROPERTY));
            $payload.appendChild(xmlNode);
        }
    }

    XiNode parseDocument(InputStream is) throws Exception {
        is.reset();
//        XiParser xiParser = (XiParser)parser.get();
        XiParser xiParser = XiSupport.getParser();
        InputSource source = new InputSource(is);
        // Removed to allow UTF-16:  source.setEncoding( "UTF-8");
        XiNode node = xiParser.parse(source);
        XiNodeUtilities.cleanTextNodes(node);
        if(node == null) return null;
        Iterator r = node.getChildren(NODE_TEST_ELEMENT);
        if (r.hasNext()) return (XiNode)r.next();

        return node.getRootNode();
        //return node.getFirstChild();
    }


    private XiNode parseDocument(
            String xmlString)
            throws Exception {
        XiNode node = null;
        if (null != xmlString) {
            try (final StringReader reader = new StringReader(xmlString)) {
            	setEncoding(xmlString);        	

            	node = XiSupport.getParser().parse(new InputSource(reader));
                if (null != node) {
                    XiNodeUtilities.cleanTextNodes(node);
                    final Iterator elementChildren = node.getChildren(NODE_TEST_ELEMENT);
                    if (elementChildren.hasNext()) {
                        node = (XiNode) elementChildren.next();
                    } else {
                        node = node.getRootNode();
                    }
                }                
            }           
        }
        return node;
    }

    public byte[] toBytes() throws Exception {
        //Never had a payload.
        if(xmlNode == null){
            return null;
        }

        byte[] bytes = null;
        if (xmlNodeAsOrigBytes != null) { 
            bytes = xmlNodeAsOrigBytes.get();
            //Good. Cached copy is still present.
            if(bytes != null){
                return bytes;
            }
        }
        
        String encodingType = (encoding == null || encoding.isEmpty()) ? "UTF-8" : encoding;

        //Fetch it from the XML Node.
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XiSerializer.serialize(xmlNode, bos, encodingType, true);
        bos.flush();

        bytes = bos.toByteArray();
        xmlNodeAsOrigBytes = new SoftReference<byte[]>(bytes);

        return bytes;
    }

    public Object getObject() {
        return xmlNode;
    }
    
    private void setEncoding(String payloadString) {
    	if (payloadString != null && !payloadString.isEmpty() && (payloadString.indexOf("UTF-16") != -1 || payloadString.indexOf("utf-16") != -1)) {
    		this.encoding = "UTF-16";
    	} else {
    		this.encoding = "UTF-8";
    	}
    }

	public boolean isJSONPayload() {
		return isJSONPayload;
	}

	public void setJSONPayload(boolean isJSONPayload) {
		this.isJSONPayload = isJSONPayload;
	}
}
