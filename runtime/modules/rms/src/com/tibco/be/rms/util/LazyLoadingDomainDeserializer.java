package com.tibco.be.rms.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Dec 8, 2009
 * Time: 1:56:57 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class LazyLoadingDomainDeserializer extends DefaultHandler2 implements ArtifactAttributesReader {

    private String domainName;

    private String containingFolderPath;

    private static final String DOMAIN_NS = "http:///com/tibco/cep/designtime/core/model/domain";

    /**
     * Receive notification of the start of an element.
     * <p/>
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions at the start of
     * each element (such as allocating a new tree node or writing
     * output to a file).</p>
     *
     * @param uri        The Namespace URI, or the empty string if the
     *                   element has no Namespace URI or if Namespace
     *                   processing is not being performed.
     * @param localName  The local name (without prefix), or the
     *                   empty string if Namespace processing is not being
     *                   performed.
     * @param qName      The qualified name (with prefix), or the
     *                   empty string if qualified names are not available.
     * @param attributes The attributes attached to the element.  If
     *                   there are no attributes, it shall be an empty
     *                   Attributes object.
     * @throws org.xml.sax.SAXException Any SAX exception, possibly
     *                                  wrapping another exception.
     * @see org.xml.sax.ContentHandler#startElement
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (DOMAIN_NS == uri.intern()) {
            if ("Domain" == localName.intern()) {
                //Use its attributes
                handleTableAttributes(attributes);
                throw new ParsingCompletedException();
            }
        }
    }

    private void handleTableAttributes(final Attributes attributes) {
        String name = attributes.getValue("", "name");
        String folderName = attributes.getValue("", "folder");
        containingFolderPath = folderName;
        domainName = name;
    }

    /**
     * Receive notification of the end of an element.
     * <p/>
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions at the end of
     * each element (such as finalising a tree node or writing
     * output to a file).</p>
     *
     * @param uri       The Namespace URI, or the empty string if the
     *                  element has no Namespace URI or if Namespace
     *                  processing is not being performed.
     * @param localName The local name (without prefix), or the
     *                  empty string if Namespace processing is not being
     *                  performed.
     * @param qName     The qualified name (with prefix), or the
     *                  empty string if qualified names are not available.
     * @throws org.xml.sax.SAXException Any SAX exception, possibly
     *                                  wrapping another exception.
     * @see org.xml.sax.ContentHandler#endElement
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public String getArtifactName() {
        return domainName;
    }

    public String getContainingFolderPath() {
        return containingFolderPath;
    }
}
