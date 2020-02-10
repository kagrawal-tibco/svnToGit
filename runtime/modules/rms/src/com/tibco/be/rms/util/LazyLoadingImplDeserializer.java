package com.tibco.be.rms.util;

import com.tibco.cep.studio.rms.core.utils.RMSConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Sep 1, 2008
 * Time: 12:05:07 PM
 * Simply parse &lt;Table&gt; element of the {@link com.tibco.cep.decisionproject.ontology.Implementation}
 * and stop parsing there.
 */
public class LazyLoadingImplDeserializer extends DefaultHandler2 implements ArtifactAttributesReader {

    private String implName;

    private String containingFolderPath;

    private String implementedResourcePath;


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
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException {
        if (RMSConstants.DT_MODEL_NS == uri.intern()) {
            if ("Table" == localName.intern()) {
                //Use its attributes
                handleTableAttributes(attributes);
                throw new ParsingCompletedException();
            }
        }
    }

    private void handleTableAttributes(final Attributes attributes) {
        String name = attributes.getValue("", "name");
        String folderName = attributes.getValue("", "folder");
        implementedResourcePath = attributes.getValue("", "implements");
        containingFolderPath = folderName;
        implName = name;
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
    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException {
        System.out.println("Ended table element");
    }

    public String getArtifactName() {
        return implName;
    }

    public String getContainingFolderPath() {
        return containingFolderPath;
    }

    public String getImplementedResPath() {
        return implementedResourcePath;
    }
}
