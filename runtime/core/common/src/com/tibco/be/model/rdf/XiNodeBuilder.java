package com.tibco.be.model.rdf;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.InputSource;

import com.tibco.be.model.functions.Variable;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeRenderer;
import com.tibco.be.model.types.xsd.XSDTypeRegistry;
import com.tibco.be.util.XiSupport;
import com.tibco.be.util.shared.ModelConstants;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.model.event.impl.TimeEventImpl;
import com.tibco.cep.runtime.model.exception.BEException;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsBoolean;
import com.tibco.xml.data.primitive.values.XsDateTime;
import com.tibco.xml.data.primitive.values.XsDouble;
import com.tibco.xml.data.primitive.values.XsInteger;
import com.tibco.xml.data.primitive.values.XsLong;
import com.tibco.xml.data.primitive.values.XsString;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.schema.SmElement;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 2, 2004
 * Time: 4:34:26 PM
 * To change this template use Options | File Templates.
 */
public class XiNodeBuilder {

//    static XiFactory factory = XiFactoryFactory.newInstance();
    static XSDTypeRegistry registry = XSDTypeRegistry.getInstance();
    static TypeConverter xsd2java_dt_conv= registry.nativeToForeign(XsDateTime.class,  GregorianCalendar.class);
    static TypeRenderer java2xsd_dt_conv= registry.foreignToNative(XsDateTime.class, GregorianCalendar.class);

    public static XiNode makeXiNode(Variable v) throws Exception {
        return makeXiNode(v.getObject(), v.getName(), v.isArray());
    }

    public static XiNode makeXiNode(Object o, String nodeName) throws Exception {
        return makeXiNode(o, nodeName, false);
    }

    public static XiNode parse(InputStream inputStream) throws Exception {
        //Wrap as inputSource
        InputSource inputSource = new InputSource(inputStream);
        return XiParserFactory.newInstance().parse(inputSource);
    }

    public static XiNode makeXiNode(Object o, String nodeName, boolean isArray) throws Exception {

        XiNode rootNode = XiSupport.getXiFactory().createElement(ExpandedName.makeName(nodeName));

        if(isArray) {
            appendToXiNode(rootNode, ModelConstants.ARRAY_ELEMENT_NAME, o);
        } else {
            setXiNodeValue(rootNode, o);
        }

        return rootNode;
    }

    public static XiNode makeDocument(Object o, SmElement term) throws Exception {
        XiNode doc = XiSupport.getXiFactory().createDocument();
        XiNode root = XiSupport.getXiFactory().createElement(term.getExpandedName(), term, term.getType());
        doc.appendChild(root);
        setXiNodeValue(root, o);
        return doc;
    }


    public static void setXiNodeValue(XiNode rootNode, Object o) throws Exception {
        if (o instanceof Concept) {
            ((Concept)o).toXiNode(rootNode);
        }
        else if (o instanceof SimpleEvent) {
            ((SimpleEvent)o).toXiNode(rootNode);
        }
        else if (o instanceof TimeEvent) {
            ((TimeEventImpl)o).toXiNode(rootNode);
        }
        else if(o instanceof AdvisoryEvent) {
            ((AdvisoryEventImpl)o).toXiNode(rootNode);
        }
        else if (o instanceof XmlTypedValue) {
            rootNode.setTypedValue((XmlTypedValue)o);
        }
        else if (o instanceof String) {
            rootNode.setTypedValue(new XsString((String)o));
        }
        else if (o instanceof Integer) {
            rootNode.setTypedValue(new XsInteger(((Integer)o).intValue()));
        }
        else if (o instanceof Double) {
            rootNode.setTypedValue(new XsDouble(((Double)o).doubleValue()));
        }
        else if (o instanceof Boolean) {
            rootNode.setTypedValue(new XsBoolean(((Boolean)o).booleanValue()));
        }
        else if (o instanceof Long) {
            rootNode.setTypedValue(new XsLong(((Long)o)));
        }
        else if (o instanceof Calendar) {
            rootNode.setTypedValue(java2xsd_dt_conv.convertToTypedValue(o));
        }
        else if (o instanceof BEException) {
            ((BEException)o).toXiNode(rootNode);
        }
        else if (o instanceof XiNode) {
            rootNode.appendChild((XiNode)o);
        }
        else if(o == null) {
            rootNode.setTypedValue(null);
        }
    }

    private static void appendToXiNode(XiNode rootNode, String newNodeName, Object o) throws Exception{
        ExpandedName name = ExpandedName.makeName(newNodeName);
        if (o instanceof Concept[]) {
            Concept[] arr = (Concept[])o;
            for(int ii = 0; ii < arr.length; ii++) {
                if(arr[ii] == null) {
                    rootNode.appendElement(name).setTypedValue(null);
                } else {
                    arr[ii].toXiNode(rootNode.appendElement(name));
                }
            }
        }
        else if (o instanceof Event[]) {
            Event[] arr = (Event[])o;
            for(int ii = 0; ii < arr.length; ii++) {
                if(arr[ii] == null) {
                    rootNode.appendElement(name).setTypedValue(null);
                } else if(arr[ii] instanceof SimpleEvent) {
                    ((SimpleEvent)arr[ii]).toXiNode(rootNode.appendElement(name));
                }
                else if(arr[ii] instanceof TimeEvent) {
                    ((TimeEventImpl)arr[ii]).toXiNode(rootNode.appendElement(name));
                }
                else if(arr[ii] instanceof AdvisoryEvent) {
                    ((AdvisoryEventImpl)arr[ii]).toXiNode(rootNode.appendElement(name));
                }
                else {
                    throw new Exception("Unknown type " + arr[ii].getClass());
                }
            }
        }
        else if (o instanceof XmlTypedValue[]) {
            XmlTypedValue[] arr = (XmlTypedValue[])o;
            for(int ii = 0; ii < arr.length; ii++) {
                rootNode.appendElement(name, arr[ii]);
            }
        }
        else if (o instanceof String[]) {
            String[] arr = (String[])o;
            for(int ii = 0; ii < arr.length; ii++) {
                rootNode.appendElement(name, arr[ii]);
            }
        }
        else if (o instanceof Integer[]) {
            Integer[] arr = (Integer[])o;
            for(int ii = 0; ii < arr.length; ii++) {
                rootNode.appendElement(name, arr[ii] == null ? null : new XsInteger(arr[ii].intValue()));
            }
        }
        else if (o instanceof Double[]) {
            Double[] arr = (Double[])o;
            for(int ii = 0; ii < arr.length; ii++) {
                rootNode.appendElement(name, arr[ii] == null ? null : new XsDouble(arr[ii].doubleValue()));
            }
        }
        else if (o instanceof Boolean[]) {
            Boolean[] arr = (Boolean[])o;
            for(int ii = 0; ii < arr.length; ii++) {
                rootNode.appendElement(name, arr[ii] == null ? null : new XsBoolean(arr[ii].booleanValue()));
            }
        }
        else if (o instanceof Long[]) {
            Long[] arr = (Long[])o;
            for(int ii = 0; ii < arr.length; ii++) {
                rootNode.appendElement(name, arr[ii] == null ? null : new XsLong(arr[ii].longValue()));
            }
        }
        else if (o instanceof int[]) {
            int[] arr = (int[])o;
            for(int ii = 0; ii < arr.length; ii++) {
                rootNode.appendElement(name, (new XsInteger(arr[ii])));
            }
        }
        else if (o instanceof double[]) {
            double[] arr = (double[])o;
            for(int ii = 0; ii < arr.length; ii++) {
                rootNode.appendElement(name, new XsDouble(arr[ii]));
            }
        }
        else if (o instanceof boolean[]) {
            boolean[] arr = (boolean[])o;
            for(int ii = 0; ii < arr.length; ii++) {
                rootNode.appendElement(name, new XsBoolean(arr[ii]));
            }
        }
        else if (o instanceof long[]) {
            long[] arr = (long[])o;
            for(int ii = 0; ii < arr.length; ii++) {
                rootNode.appendElement(name, new XsLong(arr[ii]));
            }
        }
        else if (o instanceof Calendar[]) {
            Calendar[] arr = (Calendar[])o;
            for(int ii = 0; ii < arr.length; ii++) {
                rootNode.appendElement(name, arr[ii] == null ? null : java2xsd_dt_conv.convertToTypedValue(arr[ii]));
            }
        }
        else if (o instanceof BEException[]) {
            BEException[] arr = (BEException[])o;
            for(int ii = 0; ii < arr.length; ii++) {
                if(arr[ii] == null) {
                    rootNode.appendElement(null);
                } else {
                    arr[ii].toXiNode(rootNode);
                }
            }
        }
        else {
            if(o instanceof Object[]) {
                Object[] arr = (Object[])o;
                for(int ii = 0; ii < arr.length; ii++) {
                    Object obj = arr[ii];
                    if(obj.getClass().isArray()) {
                        appendToXiNode(rootNode.appendElement(name), newNodeName, obj);
                    } else {
                        appendToXiNode(rootNode, newNodeName, obj);
                    }
                }
            } else {
                setXiNodeValue(rootNode.appendElement(name), o);
            }
        }
    }

    /**
     *
     * @param entities - A list of entities of belonging
     * @return
     * @throws Exception
     */
    public static XiNode[] buildXiNodes(List entities) throws Exception {

        XiNode nodes[] = new XiNode[entities.size()];

        for (int i=0; i < nodes.length; i++) {
            Object o = entities.get(i);
            if ((o instanceof Concept )) {

                ExpandedName nm = ((Concept)o).getExpandedName();
                //addToMap(map, o, nm);
                XiNode rootNode = XiSupport.getXiFactory().createElement(nm);
                ((Concept)o).toXiNode(rootNode);
                nodes[i] = rootNode;

            }
            else if ((o instanceof SimpleEvent)) {

                ExpandedName nm = ((SimpleEvent)o).getExpandedName();
                //addToMap(map, o, nm);
                XiNode rootNode = XiSupport.getXiFactory().createElement(nm);
                ((SimpleEvent)o).toXiNode(rootNode);
                nodes[i] = rootNode;
            }

            throw new Exception("No conversion to XiNode provided for type " + o.getClass());

        }


        return nodes;
    }

    private static void  addToMap(Map m, Object o, ExpandedName nm) {

        List entities = (List)m.get(nm);
        if (entities == null) {
            entities = new ArrayList();
            m.put(nm, entities);
        }
        entities.add(o);
        return;

    }
}
