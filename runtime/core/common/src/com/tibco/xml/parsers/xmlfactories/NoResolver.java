//(c) Copyright 2011, TIBCO Software Inc.  All rights reserved.
//LEGAL NOTICE:  This source code is provided to specific authorized end
//users pursuant to a separate license agreement.  You MAY NOT use this
//source code if you do not have a separate license from TIBCO Software
//Inc.  Except as expressly set forth in such license agreement, this
//source code, or any portion thereof, may not be used, modified,
//reproduced, transmitted, or distributed in any form or by any means,
//electronic or mechanical, without written permission from  TIBCO
//Software Inc.

package com.tibco.xml.parsers.xmlfactories;

import java.io.IOException;

import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * A resolver and resolver-factory returning the singleton instance that doesn't actually
 * resolve anything.  It throws an exception with fixed error message and prints out on
 * console the requested entities.
 *
 * @see EntityResolver
 * @see XMLResolver
 */
public final class NoResolver implements EntityResolver, XMLResolver
{
    private static final NoResolver INSTANCE = new NoResolver();

    public static NoResolver getInstance() {
        return INSTANCE;
    }

    private NoResolver() {};

    public Object resolveEntity(final String publicID, final String systemID,
            final String baseURI, final String namespace)
        throws XMLStreamException
    {
        System.out.println("won't resolve publicId: " + publicID + " systemId: " + systemID + " baseURI: " + baseURI + " namespace: " + namespace);
        throw new XMLStreamException("default entity resolution is disabled.");
    }

    public InputSource resolveEntity(final String publicId, final String systemId)
        throws SAXException, IOException
    {
        System.out.println("won't resolve publicId: " + publicId + " systemId: " + systemId);
        throw new IOException("default entity resolution is disabled.");
    }

}
