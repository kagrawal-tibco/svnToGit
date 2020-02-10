package com.tibco.cep.runtime.model.event.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.runtime.model.element.impl.EntityImpl;
import com.tibco.cep.runtime.model.event.EventDeserializer;
import com.tibco.cep.runtime.model.event.EventSerializer;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.serializers.SerializableLite;
import com.tibco.cep.runtime.model.serializers.Serializer;
import com.tibco.cep.runtime.model.serializers.SerializerFactory;
import com.tibco.cep.runtime.model.serializers.SerializerFactoryFactory;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jul 1, 2006
 * Time: 3:06:15 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class TimeEventImpl extends EntityImpl implements TimeEvent, SerializableLite {

    protected String closure;
    protected long scheduledTime;
    protected boolean fired = false;

    /**
     *
     */
    protected TimeEventImpl() {
        super();
    }

    public TimeEventImpl(long _id) {
        super(_id);
    }

    public TimeEventImpl(long _id, String _extId) {
        super(_id, _extId);
    }

    public void start(Handle handle) {
    }

//    public void delete() {
//    }

    abstract public boolean isRepeating();

    abstract public long getInterval();

    @Override
    public long getTTL() {
    	return 0;
    }

    public void setTTL(long ttl) {
        throw new RuntimeException("TTL is not settable for " + this);
    }

    public boolean getRetryOnException() {
        return true;
    }

    public boolean hasExpiryAction() {
        return false;
    }

    public void onExpiry() {
        //no-op
    }

    public void setClosure(String closure) {
        this.closure = closure;
    }

    public String getClosure() {
        return closure;
    }

    public void setScheduledTime(Calendar ct) {
        scheduledTime = ct.getTimeInMillis();
    }

    public void setScheduledTime(long ct) {
        scheduledTime = ct;
    }

    public Calendar getScheduledTime() {
        Calendar ret = new GregorianCalendar();
        ret.setTimeInMillis(scheduledTime);
        return ret;
    }

    public long getScheduledTimeMillis() {
        return scheduledTime;
    }

    public void fire() {
        fired = true;
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean flag){
        fired = flag;
    }

    public void toXiNode(com.tibco.xml.datamodel.XiNode node) throws java.lang.Exception {
        baseXiNode(node);
        if (closure != null)
            node.setAttributeStringValue(com.tibco.xml.data.primitive.ExpandedName.makeName("closure"), getClosure());
        node.setAttributeStringValue(com.tibco.xml.data.primitive.ExpandedName.makeName("scheduledTime"), com.tibco.xml.data.primitive.helpers.XmlTypedValueConversionSupport.convertToXsDateTime(getScheduledTime()).toString());
        node.setAttributeStringValue(com.tibco.xml.data.primitive.ExpandedName.makeName("interval"), Long.toString(getInterval()));
        node.setAttributeStringValue(com.tibco.xml.data.primitive.ExpandedName.makeName("ttl"), Long.toString(getTTL()));

    }
    
    public void writeExternal(DataOutput out) throws IOException {
        SerializerFactory factory = SerializerFactoryFactory.getInstance();
        Serializer serializer = factory.newSerializer();
        serializer.serialize(Serializer.Type.TIME_EVENT, this, out, null);
    }

    public void readExternal(DataInput in) throws IOException {
        SerializerFactory factory = SerializerFactoryFactory.getInstance();
        Serializer serializer = factory.newSerializer();
        serializer.deserialize(Serializer.Type.TIME_EVENT, in, this, null);
    }

    public void serialize(EventSerializer serializer) {
        _serialize(serializer);
        serializer.endEvent();
    }

    protected void _serialize(EventSerializer serializer) {
        serializer.startEvent(this.getClass(), this.getId(), this.getExtId(), EventSerializer.STATE_NEW);

        serializer.startProperty("@now", 0, true);
        serializer.writeLongProperty(System.currentTimeMillis());
        serializer.endProperty();

        serializer.startProperty("@next", 1, true);
        serializer.writeLongProperty(this.getScheduledTimeMillis());
        serializer.endProperty();

        serializer.startProperty("@closure", 2, true);
        serializer.writeStringProperty(this.getClosure());
        serializer.endProperty();

        serializer.startProperty("@ttl",3,true);
        serializer.writeLongProperty(this.getTTL());
        serializer.endProperty();

        serializer.startProperty("@fired",4,true);
        serializer.writeBooleanProperty(fired);
        serializer.endProperty();
    }

    public void deserialize(EventDeserializer deserializer) {
        _deserialize(deserializer);

        deserializer.endEvent();

    }

    protected void _deserialize(EventDeserializer deserializer) {
        this.setId(deserializer.startEvent());
        if (this.getExtId() == null)
            this.setExtId(deserializer.getExtId());

        //this.setExtId(deserializer.getExtId());

        if(deserializer.startProperty("@now", 0)) {
        	deserializer.getLongProperty();
        }
        deserializer.endProperty();

        if(deserializer.startProperty("@next", 1)) {
        	scheduledTime= deserializer.getLongProperty();
        }
        deserializer.endProperty();

        if(deserializer.startProperty("@closure", 2)) {
        	closure=deserializer.getStringProperty();
        }
        deserializer.endProperty();

        if(deserializer.startProperty("@ttl",3)) {
        	setTTL_deserialize(deserializer.getLongProperty());
        }
        deserializer.endProperty();

        if(deserializer.startProperty("@fired",4)) {
        	fired = deserializer.getBooleanProperty();
        }
        deserializer.endProperty();

        deserializer.endEvent();

    }
    
    //overriden in VariableTTLTimeEventImpl, other event types
    //throw an exception if you call setTTL
    protected void setTTL_deserialize(long _ttl) {}

    public Object getPropertyValue(String name) throws NoSuchFieldException {
        return null;
    }

    public void setPropertyValue(String name, Object value) throws Exception {
    }

}
