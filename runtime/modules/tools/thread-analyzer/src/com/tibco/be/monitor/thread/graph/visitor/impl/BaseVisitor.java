/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.graph.visitor.impl;

import com.tibco.be.monitor.thread.graph.Node;
import com.tibco.be.monitor.thread.graph.impl.ResourceImpl;
import com.tibco.be.monitor.thread.graph.impl.ThreadImpl;
import com.tibco.be.monitor.thread.graph.visitor.Visitor;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 17, 2009 / Time: 11:33:07 AM
 */
@SuppressWarnings("unchecked")
abstract class BaseVisitor implements Visitor {

    @Override
    public void visit(Node node) {
        if(node instanceof ThreadImpl) {
            visitNode((ThreadImpl)node);
        } else if(node instanceof ResourceImpl) {
            visitNode((ResourceImpl)node);
        }
    }

    protected abstract void visitNode(ThreadImpl thread);

    protected abstract void visitNode(ResourceImpl thread);

}
