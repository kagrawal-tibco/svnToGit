package com.tibco.be.functions.xpath;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConcept;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.jxpath.AxisName;
import com.tibco.jxpath.NodeResolver;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XObjectFactory;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.data.primitive.XmlItem;
import com.tibco.xml.data.primitive.XmlNodeList;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.nodes.Element;

/*
* Author: Suresh Subramani / Date: 11/3/11 / Time: 7:07 AM
*/
public class JXPathBEVariableResolver implements  NodeResolver {

    HashMap<QName, Object> variables;

    public JXPathBEVariableResolver() {
        variables = new HashMap<QName, Object>();
    }

    public void addVariable(QName name, Object var)
    {
        variables.put(name, var);
    }

    private boolean elementAsChildNodes(Element ele) {
        Iterator children = ele.getChildren();
        while (children.hasNext()) {
            Object next = children.next();
            if (next instanceof Element) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object getChild(XObject context, QName namedAxis, boolean abbr, AxisName axisName) {
        if (context == null) return null;

        Object object = context.object();
        if (object instanceof Element) {
            Element el = (Element) object;
            ExpandedName name = el.getName();
            if (isRootNode(el) && namedAxis.getLocalPart().equals(name.localName)) {
                // seems to be a special case for the 'root' node of the payload
                return object;
            }
            ExpandedName expName = new ExpandedName(namedAxis.getNamespaceURI(), namedAxis.getLocalPart());
            XmlNodeList childNodeList = ((Element) object).getChildNodeList(expName);
            if (childNodeList.size() == 1) {
                Element ele  = (Element) childNodeList.getItem(0);
                if (elementAsChildNodes(ele)) {
                    return ele;
                }
                return ele.getTypedValue().size() == 0 ? null : ele.getTypedValue().getAtom(0);
            } else if (childNodeList.size() == 0) {
                XiNode attribute = ((Element)object).getAttribute(expName);
                if (attribute != null) {
                    return attribute.getTypedValue().getAtom(0);
                }
                Iterator children = ((Element) object).getChildren(expName);
                while (children.hasNext()) {
                    Object next = children.next();
                    if (next instanceof Element && ((Element)next).getName().localName.equals(namedAxis.getLocalPart())) {
                        return next;
                    }
                }
                children = ((Element) object).getChildren();
                while (children.hasNext()) {
                    Object next = children.next();
                    if (next instanceof Element && ((Element)next).getName().localName.equals(namedAxis.getLocalPart())) {
                        return next;
                    }
                }
            }
            if (childNodeList != null) {
                return childNodeList;
            }
        }
        if (object instanceof XmlAtomicValue) {
            return ((XmlAtomicValue) object).getTypedValue();
        }
        if (object instanceof XmlTypedValue) {
            return ((XmlTypedValue) object).getAtom(0);
        }
        if ("payload".equals(namedAxis.getLocalPart()) && object instanceof SimpleEvent) {
            EventPayload payload = ((SimpleEvent)object).getPayload();
            return payload != null ? payload.getObject() : null;
        }
        if ("elements".equals(namedAxis.getLocalPart())) {
            if (object instanceof Object[]
                    || (object instanceof int[])
                    || (object instanceof double[])
                    || (object instanceof long[])
                    || (object instanceof String[])) {
                return object;
            }
        }
        if (object instanceof Property) {
            Property prop = Property.class.cast(object);
            if (prop instanceof PropertyAtomConcept) {
                object = ((PropertyAtomConcept)prop).getConcept();
            }
        }
        if (object instanceof Concept) {
            Concept concept = Concept.class.cast(object);

            if (concept != null) {

                String localPart = namedAxis.getLocalPart();
                if (axisName.getXPathName().equalsIgnoreCase("attribute")){

                    if (localPart.equalsIgnoreCase("id")) return concept.getId();
                    if (localPart.equalsIgnoreCase("extid")) return concept.getExtId();
                    if (localPart.equalsIgnoreCase("ref")) return concept.getId();
                    if (localPart.equalsIgnoreCase("parent")) return concept.getParent() == null ? -1 : concept.getParent().getId();
                }

                Property property = concept.getProperty(localPart);
                if (property instanceof PropertyAtom) {
                    return ((PropertyAtom) property).getValue();
                }
                return property;

            }
        }

        if (object instanceof SimpleEvent) {
            SimpleEvent se = (SimpleEvent) object;
            if (se != null) {
                try {
                    if (axisName.getXPathName().equalsIgnoreCase("attribute")){

                        if (namedAxis.getLocalPart().equalsIgnoreCase("id")) return se.getId();
                        if (namedAxis.getLocalPart().equalsIgnoreCase("extid")) return se.getExtId();
                    }
                    else {
                        Object obj = se.getProperty(namedAxis.getLocalPart());
                        return obj;
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        if (object instanceof GlobalVariablesProvider) {
        	GlobalVariableDescriptor variable = ((GlobalVariables) object).getVariable(namedAxis.getLocalPart());
        	return getGlobalVarAsType((GlobalVariables)object, variable);
        }

        return null;

    }

    private Object getGlobalVarAsType(GlobalVariables vars,
			GlobalVariableDescriptor variable) {
    	String gvTypeName = variable.getType();
        if (gvTypeName.equalsIgnoreCase("String")) {
            return vars.getVariableAsString(variable.getFullName(), "");
        }
        if (gvTypeName.equalsIgnoreCase("Integer")) {
            return vars.getVariableAsInt(variable.getFullName(), 0);
        }
        if (gvTypeName.equalsIgnoreCase("Boolean")) {
            return vars.getVariableAsBoolean(variable.getFullName(), false);
        }
        if (gvTypeName.equalsIgnoreCase("Password")) {
            // Treat 'password' as a string.
            return vars.getVariableAsString(variable.getFullName(), ""); // should we allow this?  Potential issue of showing password value...
        }
        // Unknown, don't fail, just return a string.
        return vars.getVariableAsString(variable.getFullName(), "");
	}

	private boolean isRootNode(Element el) {
        if (el.getParentNode() != null && el.getParentNode().getName() != null) {
            return "payload".equals(el.getParentNode().getName().localName);
        }
        return true; // return true here?
    }

    @Override
    public boolean hasChildren(XObject context) {
        Object obj = context.object();
        if (obj instanceof List) {
            return true;
        }
        if (obj instanceof PropertyArray) {
            return true;
        }
        if (obj instanceof Element) {
        	return ((Element) obj).hasChildNodes();
        }
        return false;


    }

    @Override
    public int count(XObject context) {
        if (context.object() instanceof PropertyArray) {
            return ((PropertyArray)context.object()).length();
        }
        List children = List.class.cast(context.object());
        if (children == null) return -1;
        return children.size();

    }

    @Override
    public XObject getChild(XObject context, int pos) {
        // note - indexes are 1 based, not 0 based
        Object object = context.object();
        if (object instanceof PropertyArray) {
            PropertyAtom child = ((PropertyArray) object).get(pos);
            return XObjectFactory.create(child);
        }
        if (object instanceof Element) {
        	Element child = (Element) object;
        	XmlItem item = child.getItem(pos);
        	return XObjectFactory.create(item);
        }

        List children = List.class.cast(context.object());
        if (children == null) return null;

        Object child = children.get(pos);
        return XObjectFactory.create(child);

    }

    @Override
    public Object resolveVariable(QName variableName) {
        return variables.get(variableName);
    }

    @Override
    public XObject getChildById(XObject contextNode, String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public QName name(XObject ctxNode) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
