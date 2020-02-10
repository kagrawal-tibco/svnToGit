/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.graph.impl;

import com.tibco.be.monitor.thread.graph.Node;
import com.tibco.be.monitor.thread.graph.visitor.Visitor;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 18, 2009 / Time: 11:29:31 AM
 */
@SuppressWarnings("unchecked")
public abstract class NodeImpl<I> implements Node<I>{
    protected I info;

    @Override
    public I getInfo() {
        return info;
    }

    /**
     * Accept the visitor.
     * @param visitor visitor
     */
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
