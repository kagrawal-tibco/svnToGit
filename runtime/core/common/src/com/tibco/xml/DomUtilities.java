package com.tibco.xml;

import java.util.Stack;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jan 28, 2009
 * Time: 2:31:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class DomUtilities {

    public static void cleanTextNodes(Node node)
    {
        Stack nodesToClean = new Stack();
        Node curNode;
        Node child,test;
        short elementKind = Node.ELEMENT_NODE;
        short textKind = Node.TEXT_NODE;
        boolean hasElements;

        nodesToClean.push(node);

        while(!nodesToClean.isEmpty())
        {
            hasElements = false;
            curNode = (Node) nodesToClean.pop();

            child = curNode.getFirstChild();

            while(child!=null)
            {
                if(elementKind == child.getNodeType())
                {
                    hasElements = true;
                    nodesToClean.push(child);
                }

                child = child.getNextSibling();
            }

            if(hasElements)
            {
                NodeList children = curNode.getChildNodes();

                for(int i=0; i < children.getLength();i++)  {
                    test =  children.item(i);
                    if(textKind == test.getNodeType())
                        curNode.removeChild(test);
                }
            }
        }
    }
}
