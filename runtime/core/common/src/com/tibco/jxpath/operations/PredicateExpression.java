package com.tibco.jxpath.operations;

import java.util.LinkedList;

import javax.xml.transform.TransformerException;

import com.tibco.jxpath.Expression;
import com.tibco.jxpath.MutableXPathContext;
import com.tibco.jxpath.NodeResolver;
import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XNumber;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XObjectFactory;
import com.tibco.jxpath.objects.XObjectList;

public abstract class PredicateExpression implements Expression {


    private LinkedList<Expression> predicates = new LinkedList<Expression>();



    public void addPredicate(Expression predicate)
    {
        predicates.add(predicate);
    }


     protected XObject evalPredicates(XPathContext context, XObject xobj) throws TransformerException {
        if (predicates.size() == 0) return xobj;

        XObject exprContextObject = xobj;
        for(Expression expr : predicates) {
            exprContextObject = evalPredicate((MutableXPathContext)context, exprContextObject, expr);

        }
        return exprContextObject;
    }

    private XObject evalPredicate(MutableXPathContext context, XObject exprContextObject, Expression expr) throws TransformerException {

        XObject nodesetOrNode = exprContextObject;

        NodeResolver nodeResolver = context.getNodeResolver();

        if (nodeResolver == null) return  nodesetOrNode; //SS/RYAN : TODO what to do.

        boolean hasChildren = nodeResolver.hasChildren(nodesetOrNode);

        XObject retObject = null;

        if (hasChildren) {
            XObject nodeset = nodesetOrNode;
            retObject = evalPredicateWithReferenceToNodeset(context, nodeset, expr);
        }
        else {
            XObject node = nodesetOrNode;
            retObject = evalPredicateWithReferenceToChildNode(context, node, expr);
        }


        return retObject;
    }

    private XObject evalPredicateWithReferenceToNodeset(MutableXPathContext context, XObject nodeset, Expression expr) throws TransformerException {
        NodeResolver nodeResolver = context.getNodeResolver();

        if (expr instanceof XNumber) {
            return nodeResolver.getChild(nodeset, (int)((XNumber)expr).num()-1);
        }

        int count = nodeResolver.count(nodeset);
        context.setCurrentContextCount(count);
       XObjectList xobjects = new XObjectList();      //SS:TODO - convert to XObjectList
        for (int i=0; i<count; i++) {

            XObject childNode = nodeResolver.getChild(nodeset, i);
            context.setCurrentContextPosition(i);
            context.setCurrentContextNode(childNode);
            XObject retObject = evalPredicateWithReferenceToChildNode(context, childNode, expr);
            if (retObject instanceof XNumber) {
                return nodeResolver.getChild(nodeset, (int)retObject.num()-1);

            }
            else  {
                if (retObject.bool()) {
                    xobjects.add(childNode);
                }
            }
        }

        if (xobjects.size() == 1) return xobjects.get(0);

        return XObjectFactory.create(xobjects);
    }

    private XObject evalPredicateWithReferenceToChildNode(MutableXPathContext context, XObject childNode, Expression expr) throws TransformerException {

        context.setCurrentContextNode(childNode);
        XObject xobj = expr.eval(context);
        return xobj;
    }
}
