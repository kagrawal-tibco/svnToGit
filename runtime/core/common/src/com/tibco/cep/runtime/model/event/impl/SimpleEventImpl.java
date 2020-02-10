package com.tibco.cep.runtime.model.event.impl;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.runtime.model.element.ExtIdAlreadyBoundException;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;
import com.tibco.cep.runtime.model.event.*;
import com.tibco.cep.runtime.model.serializers.Serializer;
import com.tibco.cep.runtime.model.serializers.SerializerFactory;
import com.tibco.cep.runtime.model.serializers.SerializerFactoryFactory;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 29, 2006
 * Time: 10:08:07 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class SimpleEventImpl extends EntityImpl implements SimpleEvent {

    transient AbstractEventContext context;
    private transient boolean isAcknowledged = false;
    protected transient String destinationURI;
    private EventPayload m_payload;
    private boolean isRecovered=false;
    private static Map<Class, Set> perClassPropertyNameSet = new HashMap<Class, Set>();

    /**
     *
     */
    protected SimpleEventImpl() {
        super();
    }

    protected SimpleEventImpl(long _id) {
        super(_id);
    }


    protected SimpleEventImpl(long id, String _extId) {
        super(id, _extId);
    }


    public void setExtId(String extId) throws ExtIdAlreadyBoundException {
        if (this.getExtId() != null) throw new ExtIdAlreadyBoundException("Event is already bound to " + getExtId());
        this.extId = extId;
    }

    public void setRecovered(boolean isRecovered) {
        this.isRecovered=isRecovered;
    }

    public boolean getRetryOnException() {
        return true;
    }

    public boolean isRecovered() {
        return this.isRecovered;
    }

    public void setProperty(String name, Object value) throws Exception {
        throw new NoSuchFieldException(name);
    }

    public void setProperty(String name, String value) throws Exception {
        throw new NoSuchFieldException(name);
    }

    public void setProperty(String name, XmlTypedValue value) throws Exception {
        throw new NoSuchFieldException(name);
    }

    public Object getProperty(String name) throws NoSuchFieldException {
        throw new NoSuchFieldException(name);
    }

    public XmlTypedValue getPropertyAsXMLTypedValue(String name) throws Exception {
        throw new NoSuchFieldException(name);
    }

    public void setContext(EventContext o) {
        context = (AbstractEventContext) o;
    }

    public EventContext getContext(){
        return context;
    }

//    public void delete() {
//    }

    public void acknowledge() {
        if (isAcknowledged()) return;
        if(null != context) {
            if(context.decrementCount() == 0)
            {
                context.acknowledge();
                isAcknowledged = true;
            }

        }
    }

    @Override
    public boolean isAcknowledged() {
        return isAcknowledged || context == null;
    }

    public void start(Handle handle) {
    }


    public void setTTL(long ttl) {
        throw new RuntimeException("TTL is not settable for " + this);
    }

    public boolean hasExpiryAction() {
        return false;
    }

    public void onExpiry() {
        //no-op
    }

    public EventPayload getPayload() {
        return m_payload;
    }

    public void setPayload(EventPayload payload) {
        m_payload = payload;
    }

    public String getPayloadAsString() {
        if(m_payload != null)
            return m_payload.toString();
        else
            return null;
    }

    public void toXiNode(XiNode node) throws Exception {
        baseXiNode(node);
        String[] names = getPropertyNames();
        for(int i = 0; i < names.length; i++) {
            String name = names[i];
            XmlTypedValue value = getPropertyAsXMLTypedValue(name);
            if(value != null)
                node.appendElement(com.tibco.xml.data.primitive.ExpandedName.makeName(name)).appendText(value);
        }

        if(m_payload != null) {
            m_payload.toXiNode(node);
        }
    }

    public void writeToDataOutput(DataOutput output) throws Exception {
        output.writeLong(getId());
        if (getExtId() != null) {
            output.writeBoolean(true);
            output.writeUTF(getExtId());
        } else {
            output.writeBoolean(false);
        }
    }

    public void readFromDataInput(DataInput input) throws Exception {
        input.readLong();
        if (input.readBoolean()) {
            input.readUTF();
        }
    }

    /**
     *
     * @param serializer
     */
    public void serialize(EventSerializer serializer) {
        serializer.startEvent(this.getClass(), this.getId(), this.getExtId(),EventSerializer.STATE_NEW);
        serializer.writePayload(getPayload());
        _serialize(serializer);
        serializer.endEvent();
    }
    
    protected void _serialize(EventSerializer serializer){}

    public abstract void deserializeProperty(EventDeserializer deserializer, int index);
    /**
     *
     * @param deserializer
     */
    public void deserialize(EventDeserializer deserializer) {
        if (deserializer.hasSchemaChanged()) {
            deserializer.startEvent();
            long id=deserializer.startEvent();
            String ext_id = deserializer.getExtId();
            if(getExtId() == null) setExtId(ext_id);
            this.setPayload((EventPayload) deserializer.getPayload(getExpandedName()));

            EventSerializer.EventMigrator migrator= deserializer.getEventMigrator();
            EventSerializer.PropertyDescription[] map= migrator.getPropertiesMap();
            for (int i=0; i < map.length; i++) {
                if (map[i].currentIndex() != -1) {
                    deserializeProperty(deserializer, map[i].currentIndex());
                } else {
                    map[i].getDeletedProperty().drain(deserializer);
                }
            }
            deserializer.endEvent();
        } else {
            this.setId(deserializer.startEvent());
            String ext_id = deserializer.getExtId();
            if (getExtId() == null) setExtId(ext_id);
            this.setPayload((EventPayload) deserializer.getPayload(getExpandedName()));
            _deserialize(deserializer);
            deserializer.endEvent();
        }
    }
    
    protected void _deserialize(EventDeserializer deserializer){}

    /**
     * 
     * @return
     */
    public int[] getPropertyTypes() {
        return new int[0];
    }

    public String getDestinationURI() {
        return destinationURI;
    }

    public void setDestinationURI(String s) {
        this.destinationURI = s;        
    }

    public Object getPropertyValue(String name) throws NoSuchFieldException {
        return this.getProperty(name);
    }

    public void setPropertyValue(String name, Object value) throws Exception {
        this.setProperty(name, value);
    }

    public void writeExternal(DataOutput out) throws IOException {
        SerializerFactory factory = SerializerFactoryFactory.getInstance();
        Serializer serializer = factory.newSerializer();
        serializer.serialize(Serializer.Type.SIMPLE_EVENT, this, out, null);
    }

    public void readExternal(DataInput in) throws IOException {
        SerializerFactory factory = SerializerFactoryFactory.getInstance();
        Serializer serializer = factory.newSerializer();
        serializer.deserialize(Serializer.Type.SIMPLE_EVENT, in, this, null);
    }

    @Override
    public Set getPropertyNamesAsSet() {
        Set set = perClassPropertyNameSet.get(this.getClass());
        if (set != null) return set;

        set = new HashSet(Arrays.asList(this.getPropertyNames()));
        perClassPropertyNameSet.put(this.getClass(), set);

        return set;
    }
    
    protected void modified(String propName) {
    	if(context != null) context.modified();
    	//BE-12543 throw exception if modified after assertion
    	RuleSession rs = RuleSessionManager.getCurrentRuleSession();
    	if(rs != null
    		//BE-22003 allow the exception to be disabled
    		&& !rs.getConfig().allowEventModificationInRTC())
    	{
    		Handle h = rs.getObjectManager().getHandle(this);
    		if(h instanceof BaseHandle) {
    			if(((BaseHandle)h).isAsserted()) {
    				throw new EventPropertyModificationException(this, propName);
    			}
    		}
    	}
    }
}
