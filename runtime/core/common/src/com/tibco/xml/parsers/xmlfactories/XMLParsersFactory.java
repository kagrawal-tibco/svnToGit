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

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.xml.stream.XMLResolver;

import org.xml.sax.EntityResolver;

import com.tibco.xml.parsers.resource.ParsersMessageBundle;

/**
 * Defines the property names for configuring the parsers
 */
public class XMLParsersFactory {

	/**
	 * The default global prefix used to look up system properties
	 * @see #PROPERTY_GENERAL_ENTITIES
	 * @see #PROPERTY_PARAMETER_ENTITIES
	 * @see #PROPERTY_SECURE_XML_PARSING
	 * @see #PROPERTY_SECURE_XSLT_PROCESSING
	 */
	public static final String DEFAULT_PREFIX = "com.tibco.xml."; //$NON-NLS-1$
	/**
	 * The global flag used to provide xml parser security feature enabled or disabled.
	 */
	public static final String XML_PARSER_SECURITY_DISABLED = "com.tibco.xml.parsers.disabled"; //$NON-NLS-1$
	/**
	 * Property name specifying whether to parse external general entities<p>
	 * Defaults to <code>false</code> (case insensitive). Note that this a change from
	 * previous defaults and not conforming to XML spec.
	 */
	public static final String PROPERTY_GENERAL_ENTITIES = "general-entities"; //$NON-NLS-1$
	/**
	 * Property name specifying whether to parse external parameter entities.<p>
	 * Defaults to <code>false</code> (case insensitive). Note that this a change from
	 * previous defaults and not conforming to XML spec.
	 */
	public static final String PROPERTY_PARAMETER_ENTITIES = "parameter-entities"; //$NON-NLS-1$
	/**
	 * Property name specifying whether to instantiate the XML parser (of whatever flavor)
	 * in secure mode to fend off Denial of Service attacks<p>
	 * Defaults to <code>true</code> (case insensitive).  This is JRE's default but not Xerces' default.
	 * The use of this entry point uses the safest approach.  It is not strictly conforming to spec.
	 * Global properties can increase the maximum values.  See actual implementation's documentation
	 * for details, ex: <a href="here">http://xerces.apache.org/xerces2-j/properties.html</a>
	 */
	public static final String PROPERTY_SECURE_XML_PARSING = "secure-xml-parsing"; //$NON-NLS-1$
	/**
	 * Property name specifying whether to instantiate the XSLT parser (of whatever flavor)
	 * in secure mode<p>
	 * Defaults to <code>false<code> (case insensitive) since normally only trusted transformations
	 * are performed on potentially untrusted data.  See actual implementation's documentation
	 * for details.
	 */
	public static final String PROPERTY_SECURE_XSLT_PROCESSING = "secure-xslt-processing"; //$NON-NLS-1$
	/**
	 * Whether to support DTDs (internal or external).  Default is yes.
	 */
	public static final String PROPERTY_PARSE_DTD = "support-dtd"; //$NON-NLS-1$

	/**
	 * Property name specifying a factory with a static {@link #FACTORY_METHOD} returning {@link EntityResolver}
	 * <b>N.B.</b> if you set this property it makes sense to enable {@link #PROPERTY_GENERAL_ENTITIES general entities}
	 * and {@link #PROPERTY_PARAMETER_ENTITIES parameter entities} so they can be handled by the resolver
	 * instead of the parser.
	 */
	public static final String PROPERTY_ENTITY_RESOLVER = "EntityResolver"; //$NON-NLS-1$
	/**
	 * Property name specifying a factory with a static {@link #FACTORY_METHOD} returning {@link XMLResolver}
     * <b>N.B.</b> if you set this property it makes sense to enable {@link #PROPERTY_GENERAL_ENTITIES general entities}
     * and {@link #PROPERTY_PARAMETER_ENTITIES parameter entities} so they can be handled by the resolver
     * instead of the parser.
	 */
	public static final String PROPERTY_XML_RESOLVER = "XMLResolver"; //$NON-NLS-1$
	/**
	 * The name of the static method that the factories returning resolvers should have
	 */
	public static final String FACTORY_METHOD = "getInstance"; //$NON-NLS-1$

	static final String SAX_FEATURE_EXT_GE = "http://xml.org/sax/features/external-general-entities"; //$NON-NLS-1$
	static final String SAX_FEATURE_EXT_PE = "http://xml.org/sax/features/external-parameter-entities"; //$NON-NLS-1$

	/*
	 * Can't instantiate helper
	 */
	private XMLParsersFactory(){}

	public static void bootstrapFactories() {
		com.tibco.xml.parsers.xmlfactories.DocumentBuilderFactory.bootstrap();
		com.tibco.xml.parsers.xmlfactories.XMLReaderFactory.bootstrap();
		com.tibco.xml.parsers.xmlfactories.SAXParserFactory.bootstrap();
		com.tibco.xml.parsers.xmlfactories.XMLInputFactory.bootstrap();
		com.tibco.xml.parsers.xmlfactories.TransformerFactory.bootstrap();
	}

	static <T> T loadInstance(final String optionalPrefix, final String propertyName, final Class<T> type) throws Exception {
		final String factory = XMLParsersFactory.readProperty(optionalPrefix, propertyName);
		Class<?> cls = null;
		if (factory != null) {
	        final ClassLoader cl = XMLParsersFactory.getClassLoader();
			try {
				if (cl != null)
					cls = Class.forName(factory, true, cl);
				else
					cls = Class.forName(factory);
				final Method m = cls.getMethod(FACTORY_METHOD, (Class[])null);
				@SuppressWarnings("unchecked")
                final T result = (T) m.invoke(null, (Object[])null);
				if (!type.isAssignableFrom(result.getClass()))
					throw new ClassCastException(ParsersMessageBundle.ClassCastException
							.format(new Object[]{result.getClass().getName(), type.getName()}));
				return result;
			} catch (final Exception t) {
				throw new Exception(ParsersMessageBundle.LoadingException.format(new Object[]{factory, cl, cls, t}), t);
			}
		}
		return null;
	}

	static private boolean getProperty(final String prefix, final String propertyName, final boolean defaultValue) {
		final String propertyValue = readProperty(prefix, propertyName);
		if (propertyValue == null)
			return defaultValue;
		else
			return Boolean.parseBoolean(propertyValue);
	}

	private static ClassLoader getClassLoader() {
		return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
			public ClassLoader run() {
				return Thread.currentThread().getContextClassLoader();
			}
		});
	}

	private static String readProperty(final String prefix, final String name) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			public String run() {
				String value = null;
				if (prefix != null) {
					final String lookupName;
					if (prefix.charAt(prefix.length() - 1) == '.' || name.charAt(0) == '.')
						lookupName = prefix + name;
					else
						lookupName = prefix + '.' + name;
					value = System.getProperty(lookupName);
				}
				if (value == null)
					value = System.getProperty(DEFAULT_PREFIX + name);
				return value;
			}
		});
	}

	static boolean secureXMLParsing(final String requester) {
		return getProperty(requester, PROPERTY_SECURE_XML_PARSING, true);
	}

	static boolean secureTransformations(final String requester) {
		return getProperty(requester, PROPERTY_SECURE_XSLT_PROCESSING, false);
	}

	static boolean parseExtParamEnt(final String requester) {
		return getProperty(requester, PROPERTY_PARAMETER_ENTITIES, false);
	}

	static boolean parseExtGenEnt(final String requester) {
		return getProperty(requester, PROPERTY_GENERAL_ENTITIES, false);
	}

}
