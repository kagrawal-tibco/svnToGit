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

import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Similar to {@link javax.xml.transform.TransformerFactory javax.xml.transform.TransformerFactory}
 * with the following differences:
 * <ul>
 * <li>It configures the transformer to be in secure processing depending on system properties (defaults to false).
 * 		See <code>TransformationFactory</code> implementation's details for what constitutes <em>secure processing</em></li>.
 * <li>Parses any unparsed source XML using the configurations of {@link XMLParsersFactory} rather than using factory defaults.</li>
 * <li>Any <code>XMLReader</code> contained in {@link StreamSource} is replaced with the result from our {@link SAXParserFactory}
 * with {@link EntityResolver} and {@link ErrorHandler} copied from the existing <code>XMLReader</code>.</li>
 * </ul>
 *
 * @see #newInstance(String)
 * @see #newInstance(String, ClassLoader, String)
 */
public class TransformerFactory extends javax.xml.transform.TransformerFactory {

	  private static Class<? extends javax.xml.transform.TransformerFactory> delegateClass = null;

	  private final javax.xml.transform.TransformerFactory delegate;
	  private final String prefix;

	public static synchronized void bootstrap() {
		String existing = System.getProperty("javax.xml.transform.TransformerFactory");
		boolean alreadyCalled = TransformerFactory.class.getName().equals(existing);
		boolean turnedOff = Boolean.getBoolean(XMLParsersFactory.XML_PARSER_SECURITY_DISABLED);
		if (!alreadyCalled && !turnedOff) {
		delegateClass = javax.xml.transform.TransformerFactory
					.newInstance().getClass();
		System.setProperty("javax.xml.transform.TransformerFactory",
					TransformerFactory.class.getName());
		}
	}

	/*
	 * no public instantiation
	 */
	private TransformerFactory(javax.xml.transform.TransformerFactory delegate, String prefix) {
        try {
    	    this.delegate = (delegate != null) ? delegate : delegateClass.newInstance();
    		this.prefix = prefix;
        	this.delegate.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, XMLParsersFactory.secureTransformations(prefix));
        } catch (Exception e) {
        	throw new TransformerFactoryConfigurationError(e);
        }
	}

	/*
	 * Will be called from from the javax...FactoryFinder
	 */
	public TransformerFactory() {
	    this(null, null);
	}

	/**
	 * Provides the most secure factory.
	 * @return a new instance of a TransformerFactory w/default, secure configuration
	 */
	public static javax.xml.transform.TransformerFactory newInstance() {
		return newInstance(null);
	}

	/**
	 * Similar to {@link #newInstance()} but it first tries to read the properties using the given prefix
	 * and then falls back to the global one
	 * @param prefix may be null
	 * @return a securely configured {@link javax.xml.transform.TransformerFactory}
	 */
	public static javax.xml.transform.TransformerFactory newInstance(String prefix) {
		return new TransformerFactory(null, prefix);
	}

	/**
	 * Similar to base's {@link javax.xml.transform.TransformerFactory#newInstance(String, ClassLoader)} but
	 * reads the configuration properties using the global prefix as defined in {@link XMLParsersFactory}
     * @param factoryClassName fully qualified factory class name that provides implementation of <code>javax.xml.transform.TransformerFactory</code>.
     *
     * @param classLoader <code>ClassLoader</code> used to load the factory class. If <code>null</code>
     *                     current <code>Thread</code>'s context classLoader is used to load the factory class.
     *
	 * @return a securely configured {@link javax.xml.transform.TransformerFactory}
	 */
	public static javax.xml.transform.TransformerFactory newInstance(String factoryClassName, ClassLoader classLoader) {
		return newInstance(factoryClassName, classLoader, null);
	}

	/**
	 * Similar to {@link #newInstance(String, ClassLoader)} but allows specification of
	 * a user-defined prefix for feature setting lookups.
     * @param factoryClassName fully qualified factory class name that provides implementation of <code>javax.xml.transform.TransformerFactory</code>.
     *
     * @param classLoader <code>ClassLoader</code> used to load the factory class. If <code>null</code>
     *                     current <code>Thread</code>'s context classLoader is used to load the factory class.
     *
	 * @param prefix may be null
	 * @return a securely configured {@link javax.xml.transform.TransformerFactory}
	 */
	public static javax.xml.transform.TransformerFactory newInstance(String factoryClassName, ClassLoader classLoader, String prefix) {
		return new TransformerFactory(javax.xml.transform.TransformerFactory.newInstance(factoryClassName, classLoader), prefix);
	}


	@Override
	public Transformer newTransformer(final Source source)
			throws TransformerConfigurationException {
		return new StreamTransformerParser(delegate.newTransformer(source));
	}

	@Override
	public Transformer newTransformer()
			throws TransformerConfigurationException {
		return new StreamTransformerParser(delegate.newTransformer());
	}

	@Override
	public Templates newTemplates(Source source)
			throws TransformerConfigurationException {
		return delegate.newTemplates(source);
	}

	@Override
	public Source getAssociatedStylesheet(Source source, String media,
			String title, String charset)
			throws TransformerConfigurationException {
		return delegate.getAssociatedStylesheet(source, media, title, charset);
	}

	@Override
	public void setURIResolver(URIResolver resolver) {
		delegate.setURIResolver(resolver);
	}

	@Override
	public URIResolver getURIResolver() {
		return delegate.getURIResolver();
	}

	@Override
	public void setFeature(String name, boolean value)
			throws TransformerConfigurationException {
		delegate.setFeature(name, value);
	}

	@Override
	public boolean getFeature(String name) {
		return delegate.getFeature(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		delegate.setAttribute(name, value);
	}

	@Override
	public Object getAttribute(String name) {
		return delegate.getAttribute(name);
	}

	@Override
	public void setErrorListener(ErrorListener listener) {
		delegate.setErrorListener(listener);
	}

	@Override
	public ErrorListener getErrorListener() {
		return delegate.getErrorListener();
	}

	private final class StreamTransformerParser extends Transformer {
		private final Transformer delegate;

		public StreamTransformerParser(Transformer d) {
			this.delegate = d;
		}

		public void reset() {
			delegate.reset();
		}

		public void transform(Source xmlSource, Result outputTarget)
				throws TransformerException {
			if (xmlSource instanceof StreamSource) {
				StreamSource streamSource = (StreamSource) xmlSource;
				SAXSource saxSource = new SAXSource();
				Reader reader = streamSource.getReader();
				InputStream inputStream = streamSource.getInputStream();
				String publicId = streamSource.getPublicId();
				if (reader != null || inputStream != null || publicId != null) {
					InputSource is = new InputSource();
					is.setCharacterStream(reader);
					is.setByteStream(inputStream);
					is.setPublicId(publicId);
					is.setSystemId(streamSource.getSystemId());
					saxSource.setInputSource(is);
				}
				saxSource.setSystemId(streamSource.getSystemId());
				xmlSource = saxSource;
				try {
					final XMLReader existingXMLReader = saxSource.getXMLReader();
					javax.xml.parsers.SAXParserFactory spf = SAXParserFactory.newInstance(prefix);
					spf.setNamespaceAware(true);
					if (saxSource.getXMLReader() == null) {
						//set a properly-configured XMLReader only if there's nothing already set (BE-20644)
						try {
							saxSource.setXMLReader(spf.newSAXParser().getXMLReader());
							if (existingXMLReader != null) {
								if (existingXMLReader.getEntityResolver() != null)
									saxSource.getXMLReader().setEntityResolver(existingXMLReader.getEntityResolver());
								if (existingXMLReader.getErrorHandler() != null)
									saxSource.getXMLReader().setErrorHandler(existingXMLReader.getErrorHandler());
							}
						} catch (ParserConfigurationException e) {
							throw new TransformerFactoryConfigurationError(e);
						}
					}
				} catch (SAXException e) {
					throw new TransformerException(e);
				}
			} else if (xmlSource instanceof SAXSource) {
				SAXSource saxSource = (SAXSource) xmlSource;
				if (null == saxSource.getXMLReader()) {
				//set a properly-configured XMLReader only if there's nothing already set (BE-20644)  
					try {
						saxSource.setXMLReader(XMLReaderFactory.createXMLReader(prefix));
					} catch (SAXException e) {
						throw new TransformerException(e);
					}
				}
			}
			delegate.transform(xmlSource, outputTarget);
		}

		public void setParameter(String name, Object value) {
			delegate.setParameter(name, value);
		}

		public Object getParameter(String name) {
			return delegate.getParameter(name);
		}

		public void clearParameters() {
			delegate.clearParameters();
		}

		public void setURIResolver(URIResolver resolver) {
			delegate.setURIResolver(resolver);
		}

		public URIResolver getURIResolver() {
			return delegate.getURIResolver();
		}

		public void setOutputProperties(Properties oformat) {
			delegate.setOutputProperties(oformat);
		}

		public Properties getOutputProperties() {
			return delegate.getOutputProperties();
		}

		public void setOutputProperty(String name, String value)
				throws IllegalArgumentException {
			delegate.setOutputProperty(name, value);
		}

		public String getOutputProperty(String name)
				throws IllegalArgumentException {
			return delegate.getOutputProperty(name);
		}

		public void setErrorListener(ErrorListener listener)
				throws IllegalArgumentException {
			delegate.setErrorListener(listener);
		}

		public ErrorListener getErrorListener() {
			return delegate.getErrorListener();
		}
	}
}
