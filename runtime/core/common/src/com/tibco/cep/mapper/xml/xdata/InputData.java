package com.tibco.cep.mapper.xml.xdata;

import org.xml.sax.SAXException;

import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.datamodel.XiNode;

/**
 * Represents an activity invokation input.  At the interface level there is no
 * guarantee that the data can be consumed more than once.
 */
public interface InputData {
    /**
     * @return the data as {@link com.tibco.xml.datamodel.XiNode}, or null if there was an error
     * creating the data.
     * @throws java.lang.IllegalStateException if the data has already been consumed.
     * @throws SAXException if there was an error creating the data.
     */
    XiNode getXiNode() throws IllegalStateException, SAXException;

    /**
     * @param contentHandler the data will be streamed to this handler.  If an error occurs,
     * <code>contentHandler</code>'s {@link com.tibco.xml.channel.infoset.XmlContentHandler#endDocument} method
     * will be called "prematurely" and a {@link org.xml.sax.SAXException} will be thrown.
     * @throws java.lang.IllegalStateException if the data has already been consumed.
     * @throws SAXException if there was an error creating the data.
     */
    void stream(XmlContentHandler contentHandler) throws IllegalStateException, SAXException;

    /**
     * Activities don't have to call this, it's really intended for the process engine.  If there are any errors
     * then this will throw a {@link SAXException} that lists all the errors found while the input data was created.
     * @throws SAXException with the errors found while creating the input data.
     */
    void assertNoErrors() throws SAXException;
}
