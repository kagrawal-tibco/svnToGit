package com.tibco.be.util;

import com.tibco.xml.XMLSDK;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.parse.DefaultXiParser;
import com.tibco.xml.parsers.xmlfactories.XMLParsersFactory;
import com.tibco.xml.transform.trax.DefaultTransformerFactory;
import com.tibco.xml.xquery.XQueryCompiler;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jun 11, 2008
 * Time: 10:24:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class XiSupport {

    static ThreadLocal tlvParsers = new ThreadLocal();
    static ThreadLocal tlvXiFactory = new ThreadLocal();
    static ThreadLocal tlvTransformerFactory = new ThreadLocal();
    static ThreadLocal tlvXMLSDK = new ThreadLocal();
    static ThreadLocal tlvXQueryCompiler = new ThreadLocal();

//    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(XiSupport.class);

    public static XiParser getParser() {
        XiParser parser = (XiParser) tlvParsers.get();
        if (null == parser) {
            XMLParsersFactory.bootstrapFactories();
            try {
                parser = XiParserFactory.newInstance();
            } catch (Throwable e) {
                //This is a hack to avoid ClassCastExceptions
                //arising from XiParserFactory's implementation
                //which uses context classloader of the current thread
                //to load DefaultXiParser
//                if (e instanceof ClassCastException) {
//                    if (!PlatformUtil.isStudioRunning()) {
//                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
//                            LOGGER.log(Level.DEBUG, "DefaultXiParser being loaded from different classloader. Defaulting to direct instantiation");
//                        }
//                    }
//                }
				parser = new DefaultXiParser();
            }
            tlvParsers.set(parser);
        }
        return parser;
    }

    public static XiFactory getXiFactory() {
        XiFactory xiFactory = (XiFactory) tlvXiFactory.get();
        if (xiFactory == null) {
            xiFactory = XiFactoryFactory.newInstance();
            tlvXiFactory.set(xiFactory);
        }
        return xiFactory;
    }

    public static DefaultTransformerFactory getTransformerFactory() {
        DefaultTransformerFactory transfactory = (DefaultTransformerFactory) tlvTransformerFactory.get();
        if (null == transfactory) {
            transfactory = new DefaultTransformerFactory();
            tlvTransformerFactory.set(transfactory);
        }
        return transfactory;
    }

    public static XMLSDK getXMLSDK() {
        XMLSDK xmlsdk = (XMLSDK) tlvXMLSDK.get();
        if (null == xmlsdk) {
            xmlsdk = new XMLSDK();
            tlvXMLSDK.set(xmlsdk);
        }
        return xmlsdk;
    }

    public static XQueryCompiler getXQueryCompiler() {
        XQueryCompiler xqcompiler = (XQueryCompiler) tlvXQueryCompiler.get();
        if (null == xqcompiler) {
            xqcompiler = getXMLSDK().createXQueryCompiler();
            tlvXMLSDK.set(xqcompiler);
        }
        return xqcompiler;
    }
}
