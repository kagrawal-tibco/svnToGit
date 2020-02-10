package com.tibco.cep.mapper.xml.xdata.bind;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.InputData;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.datamodel.XiNode;

/**
 * {@link InputData} implementation that never runs the bindings; instead, it just
 * works off an existing XiNode.
 */
public class XiNodeInputData implements InputData {
    private XiNode node;

    public XiNodeInputData(XiNode node) {
        this.node = node;
    }

    public XiNode getXiNode() throws IllegalStateException, SAXException {
        return node;
    }

    public void stream(XmlContentHandler contentHandler) throws IllegalStateException, SAXException {
        node.serialize(contentHandler);
    }

    public void assertNoErrors() throws SAXException {
    }
}
