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

import javax.xml.XMLConstants;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.validation.Schema;

import org.xml.sax.EntityResolver;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * Similar to {@link javax.xml.parsers.SAXParserFactory} but configured using the properties
 * of {@link XMLParsersFactory}
 *
 * @see #newInstance(String)
 * @see #newInstance(String, ClassLoader, String)
 */
public class SAXParserFactory extends javax.xml.parsers.SAXParserFactory {

	  private static Class<? extends javax.xml.parsers.SAXParserFactory> delegateClass = null;

	  private final javax.xml.parsers.SAXParserFactory delegate;
	  private final String prefix;

	public synchronized static void bootstrap() {
		final String existing = System.getProperty("javax.xml.parsers.SAXParserFactory");
		final boolean alreadyCalled = SAXParserFactory.class.getName().equals(
				existing);
		final boolean turnedOff = Boolean.getBoolean(XMLParsersFactory.XML_PARSER_SECURITY_DISABLED);
		if (!alreadyCalled && !turnedOff) {
			delegateClass = javax.xml.parsers.SAXParserFactory.newInstance().getClass();
			System.setProperty("javax.xml.parsers.SAXParserFactory",
					SAXParserFactory.class.getName());
		}
	}

	/*
	 * Can't instantiate factory from outside
	 */
	private SAXParserFactory(final javax.xml.parsers.SAXParserFactory delegate, final String prefix) {
		try
        {
            this.delegate = delegate != null ? delegate : delegateClass.newInstance();
            this.prefix = prefix;
            this.delegate.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, XMLParsersFactory.secureXMLParsing(prefix));
            this.delegate.setFeature(XMLParsersFactory.SAX_FEATURE_EXT_GE, XMLParsersFactory.parseExtGenEnt(prefix));
            this.delegate.setFeature(XMLParsersFactory.SAX_FEATURE_EXT_PE, XMLParsersFactory.parseExtParamEnt(prefix));
        } catch (final Exception e) {
            final FactoryConfigurationError fce = new FactoryConfigurationError(e);
            fce.initCause(e);//workaround http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6441330
            throw fce;
        }
	}

	/*
	 * Will be called from from the javax...FactoryFinder
	 */
	public SAXParserFactory() {
		this(null, null);
	}

	/**
	 * Provides the most secure factory.
	 * @return a new instance of a SAXParserFactory w/default, secure configuration
	 */
	public static javax.xml.parsers.SAXParserFactory newInstance()
			throws FactoryConfigurationError {
		return newInstance(null);
	}

	/**
	 * Similar to {@link #newInstance()} but configuration properties are first attempted to be read
	 * using the provided prefix and the global prefix if not found.
	 * @param prefix may be null
	 * @return a properly configured {@link javax.xml.parsers.SAXParserFactory}
	 */
	public static javax.xml.parsers.SAXParserFactory newInstance(final String prefix)
			throws FactoryConfigurationError {
        return new SAXParserFactory(null, prefix);
	}

	/**
	 * Similar to {@link javax.xml.parsers.SAXParserFactory#newInstance(String, ClassLoader)} but configuration properties are only attempted to be read
	 * using the global prefix and then fall back to the defaults as laid out in {@link XMLParsersFactory}.
     * @param factoryClassName fully qualified factory class name that provides implementation of <code>javax.xml.parsers.SAXParserFactory</code>.
     *
     * @param classLoader <code>ClassLoader</code> used to load the factory class. If <code>null</code>
     *                     current <code>Thread</code>'s context classLoader is used to load the factory class.
     *
	 * @return a properly configured {@link javax.xml.parsers.SAXParserFactory}
	 */
    public static javax.xml.parsers.SAXParserFactory newInstance(final String factoryClassName, final ClassLoader classLoader) {
    	return newInstance(factoryClassName, classLoader, null);
    }

	/**
	 * Similar to {@link #newInstance(String, ClassLoader)} but configuration properties are first attempted to be read
	 * using the provided prefix and the global prefix if not found.
     * @param factoryClassName fully qualified factory class name that provides implementation of <code>javax.xml.parsers.SAXParserFactory</code>.
     *
     * @param classLoader <code>ClassLoader</code> used to load the factory class. If <code>null</code>
     *                     current <code>Thread</code>'s context classLoader is used to load the factory class.
     *
	 * @param prefix may be null
	 * @return a properly configured {@link javax.xml.parsers.SAXParserFactory}
	 */
    public static javax.xml.parsers.SAXParserFactory newInstance(final String factoryClassName, final ClassLoader classLoader,
    		final String prefix)
    {
    	final javax.xml.parsers.SAXParserFactory newInstance =
    			javax.xml.parsers.SAXParserFactory.newInstance(factoryClassName, classLoader);
		return new SAXParserFactory(newInstance, prefix);
    }

	@SuppressWarnings("deprecation")
	@Override
	public SAXParser newSAXParser() throws ParserConfigurationException,
			SAXException {
		final SAXParser newSAXParser = delegate.newSAXParser();
		try {
			final EntityResolver resolver = XMLParsersFactory.loadInstance(
					prefix, XMLParsersFactory.PROPERTY_ENTITY_RESOLVER, EntityResolver.class);
			if (resolver != null) {
				newSAXParser.getParser().setEntityResolver(resolver);
				newSAXParser.getXMLReader().setEntityResolver(resolver);
			}
		} catch (final Exception e) {
			throw new SAXException(e);
		}
		return newSAXParser;
	}

	@Override
	public void setNamespaceAware(final boolean awareness) {
		delegate.setNamespaceAware(awareness);
	}

	@Override
	public void setValidating(final boolean validating) {
		delegate.setValidating(validating);
	}

	@Override
	public boolean isNamespaceAware() {
		return delegate.isNamespaceAware();
	}

	@Override
	public boolean isValidating() {
		return delegate.isValidating();
	}

	@Override
	public void setFeature(final String name, final boolean value)
			throws ParserConfigurationException, SAXNotRecognizedException,
			SAXNotSupportedException {
		delegate.setFeature(name, value);
	}

	@Override
	public boolean getFeature(final String name) throws ParserConfigurationException,
			SAXNotRecognizedException, SAXNotSupportedException {
		return delegate.getFeature(name);
	}

	@Override
	public Schema getSchema() {
		return delegate.getSchema();
	}

	@Override
	public void setSchema(final Schema schema) {
		delegate.setSchema(schema);
	}

	@Override
	public void setXIncludeAware(final boolean state) {
		delegate.setXIncludeAware(state);
	}

	@Override
	public boolean isXIncludeAware() {
		return delegate.isXIncludeAware();
	}

}
