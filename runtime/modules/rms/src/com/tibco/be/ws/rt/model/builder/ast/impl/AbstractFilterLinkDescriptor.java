package com.tibco.be.ws.rt.model.builder.ast.impl;

import com.tibco.be.ws.rt.model.builder.ast.IFilterLinkDescriptor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/4/12
 * Time: 11:23 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFilterLinkDescriptor<F extends IFilterLinkDescriptor> implements IFilterLinkDescriptor<F> {

    /**
     * Keep a reference to the next descriptor.
     */
    public F nextDescriptor;

    /**
     * Maintain children if any
     */
    private List<F> childDescriptors = new LinkedList<F>();

    protected RulesASTNode wrappedNode;

    protected AbstractFilterLinkDescriptor(RulesASTNode wrappedNode) {
        this.wrappedNode = wrappedNode;
    }
    
    public void addChildDescriptor(F childDescriptor) {
        childDescriptors.add(childDescriptor);
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return wrappedNode.getText();
    }

    /**
     * Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p/>
     * The <code>toString</code> method for class <code>Object</code>
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `<code>@</code>', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(getName());
        for (F childDescriptor : childDescriptors) {
            stringBuilder.append("->");
            stringBuilder.append(childDescriptor.toString());
        }
        stringBuilder.append(")");
        if (nextDescriptor != null) {
            stringBuilder.append("|");
        }
        return stringBuilder.toString();
    }

    public List<F> getChildDescriptors() {
        return Collections.unmodifiableList(childDescriptors);
    }
}
