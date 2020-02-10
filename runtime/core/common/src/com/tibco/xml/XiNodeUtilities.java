package com.tibco.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.tibco.be.util.XiSupport;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.data.primitive.XmlNodeTest;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.datamodel.navigation.NodeKindNodeTest;

public class XiNodeUtilities
{
    public static final String NO_NAMESPACE = com.tibco.xml.schema.helpers.NoNamespace.URI;
	public static final ExpandedName NIL_ATTR_QNAME = ExpandedName.makeName("http://www.w3.org/2001/XMLSchema-instance", "nil");
	public static final XmlNodeTest NODE_TEST_ELEMENT = NodeKindNodeTest.getInstance(XmlNodeKind.ELEMENT);

    /**
     * Gets the first subnode with the given name, useful for sub-elements that are unique
     */
    public static XiNode getChildNode(XiNode node,String namespace,String name)
    {
        if(namespace == null) namespace = NO_NAMESPACE;
        XiNode retVal = null;
        XiNode child = node.getFirstChild();
        ExpandedName expName = ExpandedName.makeName(namespace,name);
        ExpandedName noNSExpName = ExpandedName.makeName(NO_NAMESPACE,name);

        while(child != null)
        {
            if(XmlNodeKind.ELEMENT.equals(child.getNodeKind())
              && (expName.equals(child.getName())||noNSExpName.equals(child.getName())))
            {
                retVal = child;
                break;
            }

            child = child.getNextSibling();
        }

        return retVal;
    }

    /**
     * Gets the first subnode with the given name, useful for sub-elements that are unique
     */
    public static XiNode getAttributeNode(XiNode node,String namespace,String name)
    {
        if(namespace == null) namespace = NO_NAMESPACE;
        return node.getAttribute(ExpandedName.makeName(namespace,name));
    }

    /**
     * find the first subnode using getSubNode and either append a new one, replace it, or remove it if value == null.
     */
    public static void setChildNode(XiNode node,String namespace,String name,XiNode value)
    {
        if(namespace == null) namespace = NO_NAMESPACE;
        //XiNode sub = getChildNode(node,namespace,name);
        XiNode sub = getChildAtPath(node,namespace,name);

        if(value == null)
        {
            if(sub!=null)
            {
                node.removeChild(sub);
            }
        }
        else
        {
            if(sub == null) node.appendChild(value);
            else
            {
                node.removeChild(sub);
                node.appendChild(value);
            }
        }
    }

    public static void setChildNodeValue(XiNode node,String namespace,String name,String value)
    {
        if(namespace == null) namespace = NO_NAMESPACE;
        //XiNode sub = getChildNode(node,namespace,name);
        XiNode sub = getChildAtPath(node,namespace,name);

        if(value == null)
        {
            if(sub!=null)
            {
                node.removeChild(sub);
            }
        }
        else
        {
            if(sub == null)
            {
                //sub = XiFactoryFactory.newInstance().createElement(ExpandedName.makeName(namespace,name));
                //node.appendChild(sub);
                sub = appendChildAtPath(node, namespace, name);
            }
            sub.setStringValue(value);
        }
    }

    public static void setChildNodeValue(XiNode node,String namespace,String name,XmlAtomicValue value)
    {
        if(namespace == null) namespace = NO_NAMESPACE;
        //XiNode sub = getChildNode(node,namespace,name);
        XiNode sub = getChildAtPath(node,namespace,name);

        if(value == null)
        {
            if(sub!=null)
            {
                node.removeChild(sub);
            }
        }
        else
        {
            if(sub == null)
            {
                //sub = XiFactoryFactory.newInstance().createElement(ExpandedName.makeName(namespace,name));
                //node.appendChild(sub);
                sub = appendChildAtPath(node, namespace, name);
            }
            sub.setAtomicValue(value);
        }
    }

    /**
     *  Null values are ignored.
     * @return the node added
     */
    public static XiNode appendChildNodeValue(XiNode node,String namespace,String name,String value)
    {
        XiNode retVal = null;

        if(namespace == null) namespace = NO_NAMESPACE;
        if(value != null)
        {
            //retVal = XiFactoryFactory.newInstance().createElement(ExpandedName.makeName(namespace,name));
            //node.appendChild(retVal);
            retVal = appendChildAtPath(node, namespace, name);
            retVal.setStringValue(value);
        }

        return retVal;
    }

    /**
     *  Null values are ignored.
     */
    public static XiNode appendChildNodeValue(XiNode node,String namespace,String name,XmlAtomicValue value)
    {
        XiNode retVal = null;

        if(namespace == null) namespace = NO_NAMESPACE;
        if(value != null)
        {
            //retVal = XiFactoryFactory.newInstance().createElement(ExpandedName.makeName(namespace,name));
            //node.appendChild(retVal);
            retVal = appendChildAtPath(node, namespace, name);
            retVal.setAtomicValue(value);
        }

        return retVal;
    }

    /**
     * Replaces the child found at path = namespace with the node specified by newChild
     * The namespace is delimited by "/".  Each level is marked by one of these.  If there
     * is no child at namespace, then one will be created and replaced with the passed in node.
     * @param parent The root node to start the search from
     * @param namespace The path of the child to be replaced
     * @param name The name of the child to replace
     * @param newChild The child to set at the specified path
     * @return The newChild
     */
    public static XiNode setChildAtPath(XiNode parent, String namespace, String name, XiNode newChild)
    {
        XiNode child = getChildAtPath(parent, namespace, name);

        if (child == null)
            child = appendChildAtPath(parent, namespace, name);

        XiNode childParent = child.getParentNode();
        childParent.removeChild(child);
        childParent.appendChild(newChild);

        return newChild;
    }

    /**
     * Appends a new child at the path specified by namespace.  The namespace is delimited by "/".
     * Each level is marked by one of these.
     * @param parent The root node to start the search from
     * @param namespace The path of the child to be replaced
     * @param name The name of the child to append
     * @return The newly appended child
     */
    public static XiNode appendChildAtPath(XiNode parent, String namespace, String name)
    {
        int index = name.indexOf("/");
        if (index == -1)
        {
            XiNode sub = XiSupport.getXiFactory().createElement(ExpandedName.makeName(namespace,name));
            parent.appendChild(sub);
            return sub;
        }
        else
        {
            String firstName = name;
            if (index != -1)
                firstName = name.substring(0,index);
            XiNode child = getChildNode(parent,namespace,firstName);
            if (child == null)
            {
                child = XiSupport.getXiFactory().createElement(ExpandedName.makeName(namespace,firstName));
                parent.appendChild(child);
            }
            return appendChildAtPath(child, namespace, name.substring(index+1));
        }
    }

    /**
     * Finds the child node specified by the namespace path.
     * @param parent The root node to start the search from
     * @param namespace The path of the child to be replaced
     * @param name The name of the child to retrieve
     * @return The found child.  null denotes the child can not be found
     */
    public static XiNode getChildAtPath(XiNode parent, String namespace, String name)
    {
        int index = name.indexOf("/");
        if (index == -1)return getChildNode(parent,namespace,name);
        String temp = name.substring(index+1);
        String firstName = name;
        if (index != -1)
            firstName = name.substring(0,index);
        XiNode child = getChildNode(parent,namespace,firstName);
        if (child != null)
            return getChildAtPath(child, namespace, temp);
        else
            return child;
    }

    public static String getChildNodeValue(XiNode node,String namespace,String name)
    {
        if(namespace == null) namespace = NO_NAMESPACE;
        String retVal = null;

        //XiNode sub = getChildNode(node,namespace,name);
        XiNode sub = getChildAtPath(node, namespace, name);
        if(sub != null) retVal = sub.getStringValue();
        return retVal;
    }


    public static XmlAtomicValue getChildNodeTypedValue(XiNode node,String namespace,String name)
    {
        if(namespace == null) namespace = NO_NAMESPACE;
        XmlAtomicValue retVal = null;
        //XiNode sub = getChildNode(node,namespace,name);
        XiNode sub = getChildAtPath(node,namespace,name);
        if(sub != null) retVal = sub.getAtomicValue();
        return retVal;
    }

    public static void setChildNodeValues(XiNode node,String namespace,String name,String[] values)
    {
        if(namespace == null) namespace = NO_NAMESPACE;
        if(values == null)
        {
            ArrayList toDelete = new ArrayList();
            XiNode child = node.getFirstChild();
            ExpandedName expName = ExpandedName.makeName(namespace,name);
            ExpandedName noNSExpName = ExpandedName.makeName(NO_NAMESPACE,name);

            while(child != null)
            {
                if(XmlNodeKind.ELEMENT.equals(child.getNodeKind())
                    && (expName.equals(child.getName())||noNSExpName.equals(child.getName())))
                {
                    toDelete.add(child);
                }

                child = child.getNextSibling();
            }

            for(int i=0,max = toDelete.size();i<max;i++)
            {
                node.removeChild((XiNode)toDelete.get(i));
            }
        }
        else
        {
            setChildNodeValues(node,namespace, name, (String[])null);//delete existing
            for(int i=0,max=values.length;i<max;i++)
            {
                appendChildNodeValue(node,namespace,name,values[i]);
            }
        }
    }

    public static void setChildNodeValues(XiNode node,String namespace,String name,XmlAtomicValue[] values)
    {
        if(namespace == null) namespace = NO_NAMESPACE;
        if(values == null)
        {
            setChildNodeValues(node,namespace, name, (String[])null);//delete existing
        }
        else
        {
            setChildNodeValues(node,namespace, name, (String[])null);//delete existing
            for(int i=0,max=values.length;i<max;i++)
            {
                appendChildNodeValue(node,namespace,name,values[i]);
            }
        }
    }

    public static XiNode[] getChildNodes(XiNode node,String namespace,String name)
    {
        if(namespace == null) namespace = NO_NAMESPACE;
        XiNode[] retVal = null;
        ArrayList toAdd = new ArrayList();
        XiNode child = node.getFirstChild();
        ExpandedName expName = ExpandedName.makeName(namespace,name);
        ExpandedName noNSExpName = ExpandedName.makeName(NO_NAMESPACE,name);

        while(child != null)
        {
            if((child.getNodeKind()==XmlNodeKind.ELEMENT)
              && (expName.equals(child.getName())||noNSExpName.equals(child.getName())))
            {
                toAdd.add(child);
            }

            child = child.getNextSibling();
        }

        retVal = new XiNode[toAdd.size()];

        for(int i=0,max = toAdd.size();i<max;i++)
        {
            retVal[i] = (XiNode)toAdd.get(i);
        }
        return retVal;
    }

    public static String[] getChildNodeValues(XiNode node,String namespace,String name)
    {
        if(namespace == null) namespace = NO_NAMESPACE;
        String[] retVal = null;
        ArrayList toAdd = new ArrayList();
        XiNode child = node.getFirstChild();
        ExpandedName expName = ExpandedName.makeName(namespace,name);
        ExpandedName noNSExpName = ExpandedName.makeName(NO_NAMESPACE,name);

        while(child != null)
        {
            if((child.getNodeKind()==XmlNodeKind.ELEMENT)
              && (expName.equals(child.getName())||noNSExpName.equals(child.getName())))
            {
                toAdd.add(child);
            }

            child = child.getNextSibling();
        }

        retVal = new String[toAdd.size()];

        for(int i=0,max = toAdd.size();i<max;i++)
        {
            retVal[i] = ((XiNode)toAdd.get(i)).getStringValue();
        }
        return retVal;
    }

    public static XmlAtomicValue[] getChildNodeTypedValues(XiNode node,String namespace,String name)
    {
        if(namespace == null) namespace = NO_NAMESPACE;
        XmlAtomicValue[] retVal = null;
        ArrayList toAdd = new ArrayList();
        XiNode child = node.getFirstChild();
        ExpandedName expName = ExpandedName.makeName(namespace,name);
        ExpandedName noNSExpName = ExpandedName.makeName(NO_NAMESPACE,name);

        while(child != null)
        {
            if((child.getNodeKind()==XmlNodeKind.ELEMENT)
              && (expName.equals(child.getName())||noNSExpName.equals(child.getName())))
            {
                toAdd.add(child);
            }

            child = child.getNextSibling();
        }

        retVal = new XmlAtomicValue[toAdd.size()];

        for(int i=0,max = toAdd.size();i<max;i++)
        {
            retVal[i] = ((XiNode)toAdd.get(i)).getAtomicValue();
        }
        return retVal;
    }

    public static String getAttribute(XiNode node, String name)
    {
        String retVal = null;
        String namespace = NO_NAMESPACE;
        XiNode sub = getAttributeNode(node,namespace,name) ;
        if(sub != null) retVal = sub.getStringValue();
        return retVal;
    }

    public static void setAttribute(XiNode node, String name,String value)
    {
        String namespace = NO_NAMESPACE;
        XiNode sub = getAttributeNode(node,namespace,name) ;

        if(value == null)
        {
            if(sub!=null)
            {
                node.removeAttribute(ExpandedName.makeName(namespace,name));
            }
        }
        else
        {
            if(sub == null)
            {
                sub = XiSupport.getXiFactory().createAttribute(ExpandedName.makeName(namespace,name),value);
                node.setAttribute(sub);
            }
            else
            {
                sub.setStringValue(value);
            }
        }
    }

    /**
     * Adds the child XiNode to parent at index. Will throw a NoSuchElementException
     * if the index requested is doesn't already exist in the list. If index == -1,
     * the node is appended to the end of the list.
     *
     * @param parent XiNode
     * @param child to insert
     * @param index of the insertion
     */
    public static void addChildAt(XiNode parent, XiNode child, int index)
    {
        if(index == -1)
        {
            parent.appendChild(child);
        }
        else
        {
            //parent.insertBefore(getNthChild(parent,index), child);
            parent.insertBefore(child,getNthChild(parent,index));
        }
    }




    /**
     * Returns the XiNode at the specified index. This method will throw a
     * NotSuchElement exception if the index requested results in exhausting
     * the elements provided by the iterator.
     *
     * @param node to search
     * @param index desired
     * @return the child XiNode at index or null if node is not found
     */
    public static XiNode getNthChild(XiNode node, int index)
    {
        XiNode retVal = null;
        Iterator iter = node.getChildren();

        for(int i=0,max=index; i <= max; i++)
        {
            retVal = (XiNode)iter.next();
        }

        return retVal;
    }

    public static int getElementCount(XiNode node)
    {
        return getElements(node).length;
    }


    public static XiNode getNthElement(XiNode node, int index)
    {
        XiNode retVal = null;
        XiNode[] n = getElements(node);
        if(index < n.length)
        {
            retVal = n[index];
        }

        return retVal;
    }

    public static XiNode clone(XiNode src)
    {
        return XiSupport.getXiFactory().copyNode(src);
    }

    public static XiNode clone(XiNode src,ExpandedName newName)
    {
        XiNode dest = XiSupport.getXiFactory().createElement(newName);

        XiNode first = src.getFirstChild();
        while(first != null)
        {
            dest.appendChild(clone(first));
            first = first.getNextSibling();
        }

        first = src.getFirstAttribute();
        while(first != null)
        {
            dest.setAttribute(clone(first));
            first = first.getNextSibling();
        }

        first = src.getFirstNamespace();
        while(first != null)
        {
            dest.setNamespace(clone(first));
            first = first.getNextSibling();
        }

        return dest;
    }

    public static XiNode[] getElements(XiNode node)
    {
        ArrayList buffer = new ArrayList();
        if((node.getNodeKind()==XmlNodeKind.ELEMENT)||(node.getNodeKind()==XmlNodeKind.DOCUMENT))
        {
            Iterator i = node.getChildren();
            while(i.hasNext())
            {
                XiNode n = (XiNode)i.next();
                if(n.getNodeKind() == XmlNodeKind.ELEMENT)
                {
                    buffer.add(n);
                }
            }
        }

        return (XiNode[]) buffer.toArray(new XiNode[0]);
    }



    public static XiNode[] getChildren(XiNode n)
    {
        ArrayList buffer = new ArrayList(0);
        if(n.getNodeKind()==XmlNodeKind.ELEMENT)
        {
            Iterator i = n.getChildren();
            while(i.hasNext())
            {
                buffer.add(i.next());
            }
        }

        return (XiNode[])buffer.toArray(new XiNode[0]);
    }

    public static int getChildCount(XiNode n)
    {
        return getChildren(n).length;
    }

    public static String toString(XiNode n) {
    	return toString(n, null);
    }
    
    public static String toString(XiNode n, String encoding) {
    	return toString(n, encoding, true);
    }

    /**
     * Return a <code>byte[]</code> representation of the {@link XiNode}
     * @param node
     * @return byte[]
     */
    public static byte[] toBytes(XiNode node) {
        if (node == null) {
    		throw new NullPointerException("Node may not be null");
    	}
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] bytes = null;
        try {
    		XiSerializer.serialize(node, bos, true);
    		bytes = bos.toByteArray();
    	} catch(IOException ex) {
    		ex.printStackTrace();
    	}
        return bytes;
    }

    public static String toString(XiNode n, boolean pretty)
    {
    	return toString(n, null, pretty);
    }
    
    
    public static String toString(XiNode n, String encoding, boolean pretty) {
    	if(n == null) {
    		throw new NullPointerException("Node may not be null");
    	}
    	
    	if (encoding == null || encoding.isEmpty()) encoding = "UTF-8";

    	String retVal = null;
    	StringWriter writer = new StringWriter();
    	try	{
    		XiSerializer.serialize(n,writer, encoding ,pretty);
    		retVal = writer.toString();
    	}
    	catch(IOException ex) {
    		ex.printStackTrace();
    	}

    	return retVal;
    }
    


    public static boolean isContainer(XiNode n)
    {
        boolean retVal = false;
        if(n.hasChildNodes())
        {
            XiNode[] children = XiNodeUtilities.getChildren(n);
            for(int i=0,max=children.length; i < max; i++)
            {
                if(n.getNodeKind()==XmlNodeKind.TEXT)
                {
                    continue;
                }
                else
                {
                    retVal = true;
                    break;
                }
            }
        }

        return retVal;
    }


    /**
     * Creates a new element that is a copy of the provided element but with the name provided.
     * This method is only used for XiNode editors and tools that rename elements on the fly.
     * This method will copy all children, attributes, etc (or should). The one assumption is
     * that containter nodes don't have a stringValue. If it does this method will ignore it.
     *
     * @param node to rename
     * @param newName the new name
     *
     * @return the new node
     */
    public static XiNode rename(XiNode node, String newName)
    {
        XiNode retVal = node;
        String name = node.getName().toString();

        if(newName.equals(name) == false)
        {
            XiNode parent = node.getParentNode();
            String ns = node.getName().getNamespaceURI();

            XiNode newNode = XiSupport.getXiFactory().createElement(ExpandedName.makeName(ns, newName));

            if(isContainer(node) == false)
            {
                String value = node.getStringValue();
                if((value != null && value.equals("") == false))
                {
                    newNode.setStringValue(value);
                }
            }

            if(node.hasAttributes())
            {
                Iterator i = node.getAttributes();
                while(i.hasNext())
                {
                    XiNode attr = (XiNode)i.next();
                    node.removeAttribute(attr.getName());
                    newNode.setAttribute(attr);
                }
            }

            if(node.hasChildNodes())
            {
                XiNode[] children = XiNodeUtilities.getChildren(node);
                for(int i=0,max=children.length; i < max; i++)
                {
                    XiNode child = children[i];
                    if((i == 0) && (child.getNodeKind() == XmlNodeKind.TEXT))
                    {
                        continue; // already added this
                    }
                    node.removeChild(child);
                    newNode.appendChild(child);
                }
            }

            if(parent != null)
            {
                parent.insertBefore(newNode,node);
                parent.removeChild(node);
            }

            retVal = newNode;
        }

        return retVal;
    }


    /**
     * Removes all child nodes from the specified node and returns the children.
     */
    public static XiNode[] removeAllChildren(XiNode n)
    {
        XiNode[] retVal = XiNodeUtilities.getChildren(n);
        for(int i=0,max=retVal.length; i < max; i++)
        {
            n.removeChild(retVal[i]);
        }

        return retVal;
    }


    public static XiNode getCreateSectionOn(XiNode parent, String url, String section)
    {
        if(parent == null) return null;

        XiNode n = XiNodeUtilities.getChildNode(parent, url, section);
        if(n == null)
        {
            XiFactory factory = XiSupport.getXiFactory();
            n = factory.createElement(ExpandedName.makeName(url, section));
            parent.appendChild(n);
        }

        return n;
    }

    public static void cleanTextNodes(XiNode node)
    {
        Stack nodesToClean = new Stack();
        XiNode curNode;
        XiNode child,test;
        XmlNodeKind elementKind = XmlNodeKind.ELEMENT;
        XmlNodeKind textKind = XmlNodeKind.TEXT;
        boolean hasElements;

        nodesToClean.push(node);

        while(!nodesToClean.isEmpty())
        {
            hasElements = false;
            curNode = (XiNode) nodesToClean.pop();

            child = curNode.getFirstChild();

            while(child!=null)
            {
                if(elementKind.equals(child.getNodeKind()))
                {
                    hasElements = true;
                    nodesToClean.push(child);
                }

                child = child.getNextSibling();
            }

            if(hasElements)
            {
                Iterator children = curNode.getChildren();

                while(children.hasNext())
                {
                    test = (XiNode) children.next();
                    if(textKind.equals(test.getNodeKind())) children.remove();
                }
            }
        }
    }

    /**
     * Sets the child node value as well as adds the "isRef" attribute with value true.
     */
    public static void setChildNodeValueAsReference(XiNode node, String namespace, String name, String value)
    {
        setChildNodeValue(node, namespace, name, (("".equals(value))?null:value));
        //XiNode xn = getChildNode(node, namespace, name);
        XiNode xn = getChildAtPath(node,namespace,name);
        if(xn!=null) XiNodeUtilities.setAttribute(xn, XMLConstants.IS_REF_ATTR, "true");
    }

    public static Node toDomNode(XiNode node) throws Exception {
        String s = XiSerializer.serialize(node);
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        // todo reset() is not supported in 1.4
        // builder.reset();
        StringReader sr = new StringReader(s);
        InputSource is = new InputSource(sr);
        return builder.parse(is);
    }

	public static boolean isNil(XiNode node) {
	    return Boolean.parseBoolean(node.getAttributeStringValue(NIL_ATTR_QNAME));
	}
}
