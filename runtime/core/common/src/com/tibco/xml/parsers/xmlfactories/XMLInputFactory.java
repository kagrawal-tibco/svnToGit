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

import javax.xml.stream.EventFilter;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLReporter;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.XMLEventAllocator;
import javax.xml.transform.Source;

/**
 * Similar to {@link javax.xml.stream.XMLInputFactory} but
 * configuring using the defaults as described in {@link XMLParsersFactory}
 */
public class XMLInputFactory extends javax.xml.stream.XMLInputFactory {

	  private static Class<? extends javax.xml.stream.XMLInputFactory> delegateClass = null;

	  private final javax.xml.stream.XMLInputFactory delegate;

	public static synchronized void bootstrap() {
		final String existing = System.getProperty("javax.xml.stream.XMLInputFactory");
		final boolean alreadyCalled = XMLInputFactory.class.getName().equals(existing);
		final boolean turnedOff = Boolean.getBoolean(XMLParsersFactory.XML_PARSER_SECURITY_DISABLED);
		if (!alreadyCalled && !turnedOff) {
			delegateClass = javax.xml.stream.XMLInputFactory
					.newInstance().getClass();
			System.setProperty("javax.xml.stream.XMLInputFactory",
					XMLInputFactory.class.getName());
		}
	}

	/**
	 * Don't instantiate the factory
	 */
    private XMLInputFactory(final javax.xml.stream.XMLInputFactory delegate,
			final String prefix) {
		try
        {
            this.delegate = delegate != null ? delegate : delegateClass.newInstance();
            final boolean ext_pe = XMLParsersFactory.parseExtParamEnt(prefix);
            final boolean ext_ge = XMLParsersFactory.parseExtGenEnt(prefix);
            this.delegate.setProperty(IS_SUPPORTING_EXTERNAL_ENTITIES, ext_pe || ext_ge);

            XMLResolver resolver;
            try {
            	resolver = XMLParsersFactory.loadInstance(prefix, XMLParsersFactory.PROPERTY_XML_RESOLVER, XMLResolver.class);
            	if (resolver != null)
            	    this.delegate.setXMLResolver(resolver);
            } catch (final Exception e) {
            	throw new FactoryConfigurationError(e);
            }
        } catch (final Exception e)
        {
            throw new FactoryConfigurationError(e);
        }
	}

	public XMLInputFactory() {
		this(null, null);
	}

	/**
	 * Provides the most secure factory.
	 * @return a new instance of a XMLInputFactory w/default, secure configuration
	 */
	public static javax.xml.stream.XMLInputFactory newInstance()
			throws FactoryConfigurationError {
		return newInstance(null);
	}

	/**
	 * Similar to {@link #newInstance()} but configuration properties are first attempted to be read
	 * using the provided prefix and the global prefix if not found.
	 * @param prefix may be null
	 * @return a properly configured {@link javax.xml.stream.XMLInputFactory}
	 */
	public static javax.xml.stream.XMLInputFactory newInstance(final String prefix)
			throws FactoryConfigurationError {
		return new XMLInputFactory(null, prefix);
	}

	/**
	 * Allows specification of delegate factory.  Uses global prefix for
	 * security settings with default settings as the fallback values.
	 * @param factoryId             Name of the factory to find, same as
	 *                              a property name
	 * @param classLoader           classLoader to use
	 * @return a properly configured {@link javax.xml.stream.XMLInputFactory}
	 */
	public static javax.xml.stream.XMLInputFactory newInstance(final String factoryId,
			final ClassLoader classLoader) throws FactoryConfigurationError {
		return newInstance(factoryId, classLoader, null);
	}


	/**
	 * Similar to {@link #newInstance(String, ClassLoader)} but configuration properties are first attempted to be read
	 * using the provided prefix and the global prefix if not found.
	 * @param factoryId             Name of the factory to find, same as
	 *                              a property name
	 * @param classLoader           classLoader to use
	 * @param prefix may be null
	 * @return a properly configured {@link javax.xml.stream.XMLInputFactory}
	 */
	public static javax.xml.stream.XMLInputFactory newInstance(final String factoryId,
			final ClassLoader classLoader, final String prefix) throws FactoryConfigurationError {
		@SuppressWarnings("deprecation")
        final javax.xml.stream.XMLInputFactory delegate = javax.xml.stream.XMLInputFactory
				.newInstance(factoryId, classLoader);
		return new XMLInputFactory(delegate, prefix);
	}

	public static javax.xml.stream.XMLInputFactory newFactory()
			throws FactoryConfigurationError {
		return newInstance(null);
	}

	/**
	 * Similar to {@link #newFactory()} but configuration properties are first attempted to be read
	 * using the provided prefix and the global prefix if not found.
	 * @param prefix may be null
	 * @return a properly configured {@link javax.xml.stream.XMLInputFactory}
	 */
	public static javax.xml.stream.XMLInputFactory newFactory(final String prefix)
			throws FactoryConfigurationError {
		return newInstance(prefix);
	}

	public static javax.xml.stream.XMLInputFactory newFactory(final String factoryId,
			final ClassLoader classLoader) throws FactoryConfigurationError {
		return newInstance(factoryId, classLoader, null);
	}

	/**
	 * Similar to {@link #newFactory(String, ClassLoader)} but configuration properties are first attempted to be read
	 * using the provided prefix and the global prefix if not found.
	 * @param prefix may be null
	 * @return a properly configured {@link javax.xml.stream.XMLInputFactory}
	 */
	public static javax.xml.stream.XMLInputFactory newFactory(final String factoryId,
			final ClassLoader classLoader, final String prefix)
			throws FactoryConfigurationError {
		return newInstance(factoryId, classLoader, prefix);
	}

	@Override
	public XMLStreamReader createXMLStreamReader(final java.io.Reader reader)
			throws XMLStreamException {
		return delegate.createXMLStreamReader(reader);
	}

	/**
	 * Create a new XMLStreamReader from a JAXP source. This method is optional.
	 *
	 * @param source
	 *            the source to read from
	 * @throws UnsupportedOperationException
	 *             if this method is not supported by this XMLInputFactory
	 * @throws XMLStreamException
	 */
	@Override
	public XMLStreamReader createXMLStreamReader(final Source source)
			throws XMLStreamException {
		return delegate.createXMLStreamReader(source);
	}

	/**
	 * Create a new XMLStreamReader from a java.io.InputStream
	 *
	 * @param stream
	 *            the InputStream to read from
	 * @throws XMLStreamException
	 */
	@Override
	public XMLStreamReader createXMLStreamReader(final java.io.InputStream stream)
			throws XMLStreamException {
		return delegate.createXMLStreamReader(stream);
	}

	/**
	 * Create a new XMLStreamReader from a java.io.InputStream
	 *
	 * @param stream
	 *            the InputStream to read from
	 * @param encoding
	 *            the character encoding of the stream
	 * @throws XMLStreamException
	 */
	@Override
	public XMLStreamReader createXMLStreamReader(final java.io.InputStream stream,
			final String encoding) throws XMLStreamException {
		return delegate.createXMLStreamReader(stream, encoding);
	}

	/**
	 * Create a new XMLStreamReader from a java.io.InputStream
	 *
	 * @param systemId
	 *            the system ID of the stream
	 * @param stream
	 *            the InputStream to read from
	 */
	@Override
	public XMLStreamReader createXMLStreamReader(final String systemId,
			final java.io.InputStream stream) throws XMLStreamException {
		return delegate.createXMLStreamReader(systemId, stream);
	}

	/**
	 * Create a new XMLStreamReader from a java.io.InputStream
	 *
	 * @param systemId
	 *            the system ID of the stream
	 * @param reader
	 *            the InputStream to read from
	 */
	@Override
	public XMLStreamReader createXMLStreamReader(final String systemId,
			final java.io.Reader reader) throws XMLStreamException {
		return delegate.createXMLStreamReader(systemId, reader);
	}

	@Override
	public XMLEventReader createXMLEventReader(final java.io.Reader reader)
			throws XMLStreamException {
		return delegate.createXMLEventReader(reader);
	}

	@Override
	public XMLEventReader createXMLEventReader(final String systemId,
			final java.io.Reader reader) throws XMLStreamException {
		return delegate.createXMLEventReader(systemId, reader);
	}

	@Override
	public XMLEventReader createXMLEventReader(final XMLStreamReader reader)
			throws XMLStreamException {
		return delegate.createXMLEventReader(reader);
	}

	@Override
	public XMLEventReader createXMLEventReader(final Source source)
			throws XMLStreamException {
		return delegate.createXMLEventReader(source);
	}

	@Override
	public XMLEventReader createXMLEventReader(final java.io.InputStream stream)
			throws XMLStreamException {
		return delegate.createXMLEventReader(stream);
	}

	@Override
	public XMLEventReader createXMLEventReader(final java.io.InputStream stream,
			final String encoding) throws XMLStreamException {
		return delegate.createXMLEventReader(stream, encoding);
	}

	@Override
	public XMLEventReader createXMLEventReader(final String systemId,
			final java.io.InputStream stream) throws XMLStreamException {
		return delegate.createXMLEventReader(systemId, stream);
	}

	@Override
	public XMLStreamReader createFilteredReader(final XMLStreamReader reader,
			final StreamFilter filter) throws XMLStreamException {
		return delegate.createFilteredReader(reader, filter);
	}

	@Override
	public XMLEventReader createFilteredReader(final XMLEventReader reader,
			final EventFilter filter) throws XMLStreamException {
		return delegate.createFilteredReader(reader, filter);
	}

	@Override
	public XMLResolver getXMLResolver() {
		return delegate.getXMLResolver();
	}

	@Override
	public void setXMLResolver(final XMLResolver resolver) {
		delegate.setXMLResolver(resolver);
	}

	@Override
	public XMLReporter getXMLReporter() {
		return delegate.getXMLReporter();
	}

	@Override
	public void setXMLReporter(final XMLReporter reporter) {
		delegate.setXMLReporter(reporter);
	}

	@Override
	public void setProperty(final java.lang.String name, final Object value)
			throws java.lang.IllegalArgumentException {
		delegate.setProperty(name, value);
	}

	@Override
	public Object getProperty(final java.lang.String name)
			throws IllegalArgumentException {
		return delegate.getProperty(name);
	}

	@Override
	public boolean isPropertySupported(final String name) {
		return delegate.isPropertySupported(name);
	}

	@Override
	public void setEventAllocator(final XMLEventAllocator allocator) {
		delegate.setEventAllocator(allocator);
	}

	@Override
	public XMLEventAllocator getEventAllocator() {
		return delegate.getEventAllocator();
	}

}
