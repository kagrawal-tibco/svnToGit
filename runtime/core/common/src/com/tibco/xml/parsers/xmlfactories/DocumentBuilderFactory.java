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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;

import org.xml.sax.EntityResolver;

/**
 * Similar to the base {@link javax.xml.parsers.DocumentBuilderFactory} but configured using the
 * defaults of {@link XMLParsersFactory}
 * @see #newInstance(String)
 * @see #newInstance(String, ClassLoader, String)
 */
public class DocumentBuilderFactory extends javax.xml.parsers.DocumentBuilderFactory {

	  private static Class<? extends javax.xml.parsers.DocumentBuilderFactory> delegateClass = null;

	  private final javax.xml.parsers.DocumentBuilderFactory delegate;
	  private final String prefix;


	public static synchronized void bootstrap() {
	   	final String existing = System.getProperty("javax.xml.parsers.DocumentBuilderFactory");
	   	final boolean alreadyCalled = DocumentBuilderFactory.class.getName().equals(existing);
	   	final boolean turnedOff = Boolean.getBoolean(XMLParsersFactory.XML_PARSER_SECURITY_DISABLED);
		if (!alreadyCalled && !turnedOff) {
			delegateClass = javax.xml.parsers.DocumentBuilderFactory.newInstance().getClass();
			System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
					DocumentBuilderFactory.class.getName());
		}
	}


	/*
	 * Will be called from from the javax...FactoryFinder
	 */
	public DocumentBuilderFactory() {
		this(null, null);
	}

	/*
	 * Can't instantiate factory from outside
	 */
	private DocumentBuilderFactory(final javax.xml.parsers.DocumentBuilderFactory delegate, final String prefix) {
		try
        {
            this.delegate = delegate != null ? delegate
                    : delegateClass.newInstance();
            this.prefix = prefix;
            this.delegate.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, XMLParsersFactory.secureXMLParsing(prefix));
            this.delegate.setFeature(XMLParsersFactory.SAX_FEATURE_EXT_PE, XMLParsersFactory.parseExtParamEnt(prefix));
            this.delegate.setFeature(XMLParsersFactory.SAX_FEATURE_EXT_GE, XMLParsersFactory.parseExtGenEnt(prefix));
        } catch (final Exception e) {
			final FactoryConfigurationError fce = new FactoryConfigurationError(e);
            fce.initCause(e);//workaround http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6441330
            throw fce;
		}
	}

	/**
	 * Provides the most secure factory.
	 * @return a new instance of a DocumentBuilderFactory w/default, secure configuration
	 */
	public static javax.xml.parsers.DocumentBuilderFactory newInstance() {
		return newInstance(null);
	}

	/**
	 * Similar to {@link #newInstance()} but configuration properties are first attempted to be read
	 * using the provided prefix and the global prefix if not found.
	 * @param prefix may be null
	 * @return a properly configured {@link javax.xml.parsers.DocumentBuilderFactory}
	 */
	public static javax.xml.parsers.DocumentBuilderFactory newInstance(final String prefix) {
		return new DocumentBuilderFactory(null, prefix);
	}

	/**
	 * Similar to {@link javax.xml.parsers.DocumentBuilderFactory#newInstance(String, ClassLoader)} but configuration properties are first attempted to be read
	 * using the global prefix and if not found the defaults as described in {@link XMLParsersFactory}.
     * @param factoryClassName fully qualified factory class name that provides implementation of <code>javax.xml.parsers.DocumentBuilderFactory</code>.
     *
     * @param classLoader <code>ClassLoader</code> used to load the factory class. If <code>null</code>
     *                     current <code>Thread</code>'s context classLoader is used to load the factory class.
	 * @return a properly configured {@link javax.xml.parsers.DocumentBuilderFactory}
	 */
	public static javax.xml.parsers.DocumentBuilderFactory newInstance(final String factoryClassName, final ClassLoader classLoader) {
		return newInstance(factoryClassName, classLoader, null);
	}

	/**
	 * Similar to {@link #newInstance(String, ClassLoader)} but configuration properties are first attempted to be read
	 * using the provided prefix and the global prefix if not found.
     * @param factoryClassName fully qualified factory class name that provides implementation of <code>javax.xml.parsers.DocumentBuilderFactory</code>.
     *
     * @param classLoader <code>ClassLoader</code> used to load the factory class. If <code>null</code>
     *                     current <code>Thread</code>'s context classLoader is used to load the factory class.
	 * @param prefix may be null
	 * @return a properly configured {@link javax.xml.parsers.DocumentBuilderFactory}
	 */
	public static javax.xml.parsers.DocumentBuilderFactory newInstance(final String factoryClassName,
			final ClassLoader classLoader,
			final String prefix) {
		return new DocumentBuilderFactory(
				javax.xml.parsers.DocumentBuilderFactory.newInstance(
						factoryClassName, classLoader), prefix);
	}

	@Override
	public DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
		final DocumentBuilder docBuilder = delegate.newDocumentBuilder();
		try {
			final EntityResolver resolver = XMLParsersFactory.loadInstance(prefix, XMLParsersFactory.PROPERTY_ENTITY_RESOLVER, EntityResolver.class);
			if (resolver != null) {
				docBuilder.setEntityResolver(resolver);
			}
		} catch (final Exception e) {
			throw new ParserConfigurationException(e.toString());
		}
		return docBuilder;
	}

	@Override
	public void setAttribute(final String name, final Object value) throws IllegalArgumentException {
		delegate.setAttribute(name, value);
	}

	@Override
	public Object getAttribute(final String name) throws IllegalArgumentException {
		return delegate .getAttribute(name);
	}

	@Override
	public void setFeature(final String name, final boolean value)
			throws ParserConfigurationException {
		delegate .setFeature(name, value);
	}

	@Override
	public boolean getFeature(final String name) throws ParserConfigurationException {
		return delegate .getFeature(name);
	}

	@Override
	public void setNamespaceAware(final boolean awareness) {
		delegate .setNamespaceAware(awareness);
	}

	@Override
	public void setValidating(final boolean validating) {
		delegate .setValidating(validating);
	}

	@Override
	public void setIgnoringElementContentWhitespace(final boolean whitespace) {
		delegate .setIgnoringElementContentWhitespace(whitespace);
	}

	@Override
	public String toString() {
		return delegate .toString();
	}

	@Override
	public void setExpandEntityReferences(final boolean expandEntityRef) {
		delegate .setExpandEntityReferences(expandEntityRef);
	}

	@Override
	public void setIgnoringComments(final boolean ignoreComments) {
		delegate .setIgnoringComments(ignoreComments);
	}

	@Override
	public void setCoalescing(final boolean coalescing) {
		delegate .setCoalescing(coalescing);
	}

	@Override
	public boolean isNamespaceAware() {
		return delegate .isNamespaceAware();
	}

	@Override
	public boolean isValidating() {
		return delegate .isValidating();
	}

	@Override
	public boolean isIgnoringElementContentWhitespace() {
		return delegate .isIgnoringElementContentWhitespace();
	}

	@Override
	public boolean isExpandEntityReferences() {
		return delegate .isExpandEntityReferences();
	}

	@Override
	public boolean isIgnoringComments() {
		return delegate .isIgnoringComments();
	}

	@Override
	public boolean isCoalescing() {
		return delegate .isCoalescing();
	}

	@Override
	public Schema getSchema() {
		return delegate .getSchema();
	}

	@Override
	public void setSchema(final Schema schema) {
		delegate .setSchema(schema);
	}

	@Override
	public void setXIncludeAware(final boolean state) {
		delegate .setXIncludeAware(state);
	}

	@Override
	public boolean isXIncludeAware() {
		return delegate .isXIncludeAware();
	}
}
