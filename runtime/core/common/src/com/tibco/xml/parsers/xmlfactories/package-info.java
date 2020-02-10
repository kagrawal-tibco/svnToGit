/**
 * Provides replacement factory implementations that return uniformly-configured instances of XML parsers.
 * The library must be initialized by first calling
 * {@link com.tibco.xml.parsers.xmlfactories.XMLParsersFactory#bootstrapFactories()} at the earliest possible time.
 * Consumers of default instances of XML parsers can continue to call the usual <code>javax.xml...</code> factories as before.
 * <b>NO changes are are required</b> but the settings are global/system-wide.
 * In other words calls to {@link javax.xml.parsers.DocumentBuilderFactory#newInstance()} benefit from this fix
 * but calls explicitly specifying the parser's name will <b>not</b> get our factories.
 * (Ex. {@link javax.xml.parsers.DocumentBuilderFactory#newInstance(String, ClassLoader)} will take the same parser as
 * requested.  External System properties setting parser-factory names
 * (ex. <code>-Djavax.xml.parsers.DocumentBuilderFactory=org.apache.xerces.jaxp.DocumentBuilderFactoryImpl</code>)
 * are respected, however <b>they should not be set programmatically at run-time.</b><p>
 *
 * Defaults are set for the safest use of XML.  Such defaults can be overridden by either a global
 * property or a per-caller one.  The global property setting requires no code change.<p>
 *
 * @see com.tibco.xml.parsers.xmlfactories.XMLParsersFactory
 *
 * @since 1.0.0
 *
 * @author ashundi
 */
package com.tibco.xml.parsers.xmlfactories;
