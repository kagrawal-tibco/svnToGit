package com.tibco.cep.mapper.xml.xdata.bind;

import org.xml.sax.SAXException;

import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiBuilder;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.transform.trax.TransformerEx;

/**
 * This implementation of {@link com.tibco.cep.mapper.xml.xdata.InputData} caches the binding result as a {@link XiNode}
 */
public class ReusableBinding extends OnceOnlyBinding {
    private XiNode cachedNode;

    public ReusableBinding(TransformerEx transformer, boolean validate, SmParticleTerm expectedOutput,
                           SmComponentProviderEx compProvider) {
        super(transformer, validate, expectedOutput, compProvider);
        cachedNode = null;
    }

    private void makeCachedNode() throws SAXException {
        if (cachedNode == null) {
            XiBuilder builder = new XiBuilder(XiFactoryFactory.newInstance());
			super.stream(builder);
            setCacheNode(builder.getNode());
        }
    }

    public XiNode getXiNode() throws IllegalStateException, SAXException {
        makeCachedNode();
        return cachedNode;
    }

    public void stream(XmlContentHandler contentHandler) throws IllegalStateException, SAXException {
        makeCachedNode();
        if (cachedNode != null) {
            try {
                cachedNode.serialize(contentHandler);
            } catch (SAXException e) {
                errorHandler.addError("Input data error: ", e.getException() == null ? e : e.getException(), null, null);
                throw e; // rethrow so that this behaves the same as super.stream() would on the same handler.
            }
        }
    }

    private void setCacheNode(XiNode node) {
        cachedNode = node;
    }
}
