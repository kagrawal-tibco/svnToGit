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

import javax.xml.XMLConstants;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

/**
 * Similar to <code>org.xml.sax.helpers.XMLReaderFactory</code> but returning an
 * {@link XMLReader} that is configured as described in
 * {@link XMLParsersFactory}
 */
public class XMLReaderFactory
    implements XMLReader
{

    private static Class<? extends XMLReader> staticSystemDefaultFactory = null;
    private final org.xml.sax.XMLReader delegate;
    private final String prefix;

    public static void bootstrap()
    {
        final String existing = System.getProperty("org.xml.sax.driver");
        final boolean alreadyCalled = XMLReaderFactory.class.getName().equals(existing);
        final boolean turnedOff = Boolean.getBoolean(XMLParsersFactory.XML_PARSER_SECURITY_DISABLED);
        if (!alreadyCalled && !turnedOff)
        {
            try
            {
                staticSystemDefaultFactory = org.xml.sax.helpers.XMLReaderFactory.createXMLReader().getClass();
                System.setProperty("org.xml.sax.driver", XMLReaderFactory.class.getName());
            } catch (final SAXException e)
            {
            	//XMLReaderFactory.createXMLReader()is always fails. Let it fail then but not now.
            }
        }
    }

    /*
     * Will be instantiated by the javax.xml....FactoryFinder
     */
    public XMLReaderFactory() throws SAXNotSupportedException, SAXException, InstantiationException, IllegalAccessException {
		this.delegate = staticSystemDefaultFactory.newInstance();
		this.prefix = null;
		configureSecurity(prefix, delegate);
	}

    public static XMLReader createXMLReader()
        throws SAXException
    {
        return createXMLReader(null);
    }

    public static XMLReader createXMLReader(final String prefix)
        throws SAXException
    {
        return createXMLReader(null, prefix);
    }

    public static XMLReader createXMLReader(final String className, final String prefix)
        throws SAXException
    {
        final XMLReader r;
        if (className == null)
            try
            {
                r = staticSystemDefaultFactory.newInstance();
            } catch (final InstantiationException e1)
            {
                throw new SAXException(e1);
            } catch (final IllegalAccessException e1)
            {
                throw new SAXException(e1);
            }
        else
            r = org.xml.sax.helpers.XMLReaderFactory.createXMLReader(className);
        try
        {
            final EntityResolver resolver = XMLParsersFactory.loadInstance(prefix,
                    XMLParsersFactory.PROPERTY_ENTITY_RESOLVER,
                    EntityResolver.class);
            if (resolver != null)
            {
                r.setEntityResolver(resolver);
            }
        } catch (final Exception e)
        {
            throw new SAXException(e);
        }
        configureSecurity(prefix, r);
        return r;
    }

    private static void configureSecurity(final String prefix, final XMLReader r)
        throws SAXNotSupportedException, SAXException
    {
        try
        {
            r.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING,
                    XMLParsersFactory.secureXMLParsing(prefix));
        } catch (final SAXNotRecognizedException e)
        {
            final String pkg = r.getClass().getPackage().getName();
            final String sm = pkg.substring(0, pkg.lastIndexOf('.'))
                    + ".util.SecurityManager";
            try
            {
                r.setProperty(
                        "http://apache.org/xml/properties/security-manager",
                        Class.forName(sm).newInstance());
            } catch (final Exception e2)
            {
                throw new SAXException(e2);
            }
        }
        r.setFeature(XMLParsersFactory.SAX_FEATURE_EXT_GE,
                XMLParsersFactory.parseExtGenEnt(prefix));
        r.setFeature(XMLParsersFactory.SAX_FEATURE_EXT_PE,
                XMLParsersFactory.parseExtParamEnt(prefix));
    }

    public ContentHandler getContentHandler()
    {
        return delegate.getContentHandler();
    }

    public DTDHandler getDTDHandler()
    {
        return delegate.getDTDHandler();
    }

    public EntityResolver getEntityResolver()
    {
        return delegate.getEntityResolver();
    }

    public ErrorHandler getErrorHandler()
    {
        return delegate.getErrorHandler();
    }

    public boolean getFeature(final String name)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        return delegate.getFeature(name);
    }

    public Object getProperty(final String name)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        return delegate.getProperty(name);
    }

    public void parse(final InputSource input)
        throws IOException, SAXException
    {
        delegate.parse(input);
    }

    public void parse(final String systemId)
        throws IOException, SAXException
    {
        delegate.parse(systemId);
    }

    public void setContentHandler(final ContentHandler handler)
    {
        delegate.setContentHandler(handler);
    }

    public void setDTDHandler(final DTDHandler handler)
    {
        delegate.setDTDHandler(handler);
    }

    public void setEntityResolver(final EntityResolver resolver)
    {
        delegate.setEntityResolver(resolver);
    }

    public void setErrorHandler(final ErrorHandler handler)
    {
        delegate.setErrorHandler(handler);
    }

    public void setFeature(final String name, final boolean value)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        delegate.setFeature(name, value);
    }

    public void setProperty(final String name, final Object value)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        delegate.setProperty(name, value);
    }
}
