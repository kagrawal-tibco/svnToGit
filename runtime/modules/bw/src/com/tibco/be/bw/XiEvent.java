package com.tibco.be.bw;

import java.util.List;

import com.tibco.be.model.rdf.primitives.RDFBooleanTerm;
import com.tibco.be.model.rdf.primitives.RDFDateTimeTerm;
import com.tibco.be.model.rdf.primitives.RDFDoubleTerm;
import com.tibco.be.model.rdf.primitives.RDFIntegerTerm;
import com.tibco.be.model.rdf.primitives.RDFLongTerm;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.Converter;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.ExtIdAlreadyBoundException;
import com.tibco.cep.runtime.model.event.EventDeserializer;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.EventSerializer;
import com.tibco.cep.runtime.model.event.NotModeledEvent;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsDateTime;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmElement;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 13, 2006
 * Time: 6:18:19 PM
 * To change this template use File | Settings | File Templates.
 */
//An implementation of SimpleEvent using XiNode as the internal structure. Is Only used by BW.
public class XiEvent extends SimpleEventImpl implements NotModeledEvent {


    //static XSDTypeRegistry registry = XSDTypeRegistry.getInstance();
    //static TypeConverter xsd2java_dt_conv= registry.nativeToForeign(XsDateTime.class,  GregorianCalendar.class);
//    static XiFactory factory = XiFactoryFactory.newInstance();

    XiNode inputData;
    ExpandedName eventName;
    SmElement type;
    Event eventDesc;
    TypeManager manager;



    public XiEvent(long _id, TypeManager manager, ExpandedName eventName, SmElement type, Event eventDesc) {
        super(_id);
        this.type = type;
        this.eventDesc = eventDesc;
        this.eventName = eventName;
        this.manager = manager;
        inputData = XiSupport.getXiFactory().createElement(eventName);

    }

    public void setXiNode(XiNode node) {
        this.inputData = node;
    }

    public String getDestinationURI() {
        return eventDesc.getDestinationName();
    }

    public ExpandedName getExpandedName() {
        return eventName;
    }

    public EventPayload getPayload() {
        if (inputData == null) return null;
        XiNode payload = XiChild.getChild(inputData, ExpandedName.makeName(EventPayload.PAYLOAD_PROPERTY));
        if(payload == null) return null;
        return manager.getPayloadFactory().createPayload(eventName, payload.getFirstChild());

    }

    public Object getProperty(String name) throws NoSuchFieldException {
        ExpandedName field = ExpandedName.makeName(name);
        RDFPrimitiveTerm term = eventDesc.getPropertyType(name);

        if (term == null) throw new NoSuchFieldException(name);

        XiNode node = XiChild.getChild(inputData, field);
        if (node == null) return null;

        try {
            if (term instanceof RDFBooleanTerm) return Boolean.valueOf(XiChild.getBoolean(inputData, field));
            else if (term instanceof RDFIntegerTerm)return new Integer(XiChild.getInt(inputData, field));
            else if (term instanceof RDFLongTerm)   return new Long(XiChild.getLong(inputData, field));
            else if (term instanceof RDFDoubleTerm) return new Double(XiChild.getDouble(inputData, field));
            else if (term instanceof RDFDateTimeTerm) {
                XsDateTime dt = (XsDateTime)node.getItem(0).getTypedValue();
                return dt.castAsString();
            }
            else return XiChild.getString(inputData, field);
        } catch (XmlAtomicValueCastException e) {
            //TODO - NL and SS discuss
            return null;
        }
    }

    public XmlTypedValue getPropertyAsXMLTypedValue(String name) throws Exception {

        ExpandedName field = ExpandedName.makeName(name);
        RDFPrimitiveTerm term = eventDesc.getPropertyType(name);

        if (term == null) throw new NoSuchFieldException(name);

        XiNode node = XiChild.getChild(inputData, field);
        if (node == null) return null;


        return node.getTypedValue();

    }

    public String[] getPropertyNames() {
        final List list = eventDesc.getAllUserProperties();
        String names[] = new String[list.size()];

        for (int i=0; i < names.length; i++) {
            EventPropertyDefinition epd = (EventPropertyDefinition) list.get(i);
            names[i] = epd.getPropertyName();
        }
        return names;

    }

    public void setExtId(String extId) throws ExtIdAlreadyBoundException {
        inputData.setAttributeStringValue(ExpandedName.makeName(ATTRIBUTE_EXTID), extId);
    }

    public void setPayload(EventPayload payload) {
        payload.toXiNode(inputData);
    }



    public void setProperty(String name, Object value) throws Exception {
        ExpandedName field = ExpandedName.makeName(name);
        RDFPrimitiveTerm term = eventDesc.getPropertyType(name);

        if (term == null) throw new NoSuchFieldException(name);

        XmlTypedValue val = null;
        try {
            if (term instanceof RDFBooleanTerm) val = Converter.getBooleanProperty(((Boolean)value).booleanValue());
            else if (term instanceof RDFIntegerTerm) val = Converter.getIntegerProperty(((Integer)value).intValue());
            else if (term instanceof RDFLongTerm)   val = Converter.getLongProperty(((Long)value).longValue());
            else if (term instanceof RDFDoubleTerm) val = Converter.getDoubleProperty(((Double)value).doubleValue());
            else if (term instanceof RDFDateTimeTerm) {
                  val = Converter.getDateTimeProperty(value);
                //Calendar c = Converter.convertDateTimeProperty(value);
                //val = Converter.getDateTimeProperty(c);
            }
            else {
                if (value != null)
                    val = Converter.getStringProperty(value.toString());
            }

            if (val != null)
            inputData.appendElement(field, val);

        } catch (ConversionException e) {
            //TODO
        }
    }

    public void setProperty(String name, String value) throws Exception {
        if (value != null)
            inputData.appendElement(ExpandedName.makeName(name), value);
    }

    public void setProperty(String name, XmlTypedValue value) throws Exception {
        inputData.appendElement(ExpandedName.makeName(name), value);
    }

    public void toXiNode(XiNode root) throws Exception {
        root.appendChild(inputData);
    }

    public String getExtId() {
    	if (inputData == null) return null;
        return inputData.getAttributeStringValue(ExpandedName.makeName(ATTRIBUTE_EXTID));
    }
    
    public String getType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getTTL() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void serialize(EventSerializer serializer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deserialize(EventDeserializer deserializer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deserializeProperty(EventDeserializer deserializer, int index) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean getRetryOnException() {
        return true;
    }

    
}
