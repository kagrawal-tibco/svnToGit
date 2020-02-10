package com.tibco.jxpath;

import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;

/*
* Author: Suresh Subramani / Date: 10/31/11 / Time: 6:31 PM
*/
public class JXPathFactory extends XPathFactory {

    XPathVariableResolver variableResolver;
    XPathFunctionResolver functionResolver;

    XPathFactory delegateFactory;

    public JXPathFactory() {
        delegateFactory = XPathFactory.newInstance();
    }

    @Override
    public boolean isObjectModelSupported(String objectModel) {
        return true;
    }

    @Override
    public void setFeature(String name, boolean value) throws XPathFactoryConfigurationException {
        delegateFactory.setFeature(name, value);

    }

    @Override
    public boolean getFeature(String name) throws XPathFactoryConfigurationException {
        return delegateFactory.getFeature(name);
    }

    @Override
    public void setXPathVariableResolver(XPathVariableResolver resolver) {
        this.variableResolver = resolver;
        delegateFactory.setXPathVariableResolver(resolver);
    }

    @Override
    public void setXPathFunctionResolver(XPathFunctionResolver resolver) {
        this.functionResolver = resolver;
        delegateFactory.setXPathFunctionResolver(resolver);
    }

    @Override
    public javax.xml.xpath.XPath newXPath() {
        return new JXPath(delegateFactory.newXPath());
    }
}
