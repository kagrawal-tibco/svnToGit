package com.tibco.jxpath;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathVariableResolver;

import com.tibco.jxpath.objects.XObject;

public interface NodeResolver extends XPathVariableResolver {

    /**
     * Get the Xobject for Named axis w.r.t the parentContext object.
     * @param context - This can be variable value returned from the call to resolveVariable or from the call to resolveNamedAxis.
     * @param qName - The qName from the xpath.
     * @param abbr - This tells if we have to look recursively in the context
     * @param axisName - the name of the axis
     * @return
     */
    Object getChild(XObject context, QName qName, boolean abbr, AxisName axisName);

    /**
     * Does this context object have children.
     * @param context
     * @return
     */
    boolean hasChildren(XObject context);

    /**
     *
     * @param context
     * @return the count of children for the context looked At.
     */
    int count(XObject context);

    /**
     * Get the Child at position specified for context object.
     * @param context
     * @param pos
     * @return
     */
    XObject getChild(XObject context, int pos);

    /**
     * Return the QName for this Context Node.
     * @param ctxNode
     * @return
     */
    QName name(XObject ctxNode);

    /**
     *
     * @param contextNode
     * @param id
     * @return
     */
    XObject getChildById(XObject contextNode, String id);
}
