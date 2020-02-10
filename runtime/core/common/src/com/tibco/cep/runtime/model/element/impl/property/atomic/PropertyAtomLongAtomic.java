package com.tibco.cep.runtime.model.element.impl.property.atomic;

import com.tibco.cep.runtime.model.element.*;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsLong;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by LongelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:57:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomLongAtomic extends PropertyAtomAtomic implements PropertyAtomLong {

    AtomicLong m_value;

    public PropertyAtomLongAtomic(Object owner) {
        super(owner);
        m_value = new AtomicLong(DEFAULT_VALUE);
        //not worth having synchronized blocks in the setters to keep this thread safe so just set it to true
        setIsSet();
    }

    public PropertyAtomLongAtomic(Object owner, long defaultValue) {
        super(owner);
        m_value = new AtomicLong(defaultValue);
        setIsSet();
    }

    @Override
    public PropertyAtomAtomic copy(Object newOwner) {
        return _copy(new PropertyAtomLongAtomic(newOwner));
    }

    protected PropertyAtomLongAtomic _copy(PropertyAtomLongAtomic ret) {
        super._copy(ret);
        ret.m_value.set(getLong());
        return ret;
    }

    public Object getValue() {
        return getLong();
    }

    public boolean setValue(Object obj) {
        if (obj != null) {
            return setCurrent(objectToLong(obj));
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(Object obj, long time) {
        return setValue(obj);
    }

    public long getLong() {
        return m_value.get();
    }

    public long getLong(long time) throws PropertyException {
        return getLong();
    }

    public long getLongAtIdx(int idx) throws PropertyException {
        return getLong();
    }

    public XmlTypedValue getXMLTypedValue() {
        return new XsLong(getLong());
    }

    public boolean setValue(XmlTypedValue value) throws Exception {
        if (value != null) {
            return setCurrent(value.getAtom(0).castAsLong());
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(String value) throws Exception {
        if (value != null) {
            return setCurrent(Long.valueOf(value).longValue());
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    private boolean setCurrent(long value) {
        ((ConceptImpl)getParent()).checkSession();
//        if(!isSet()) {
//            setIsSet();
//            m_value.set(value);
//            setConceptModified();
//            return true;
//        }
//        else 
    	if(getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES || value != m_value.get()) {
        	m_value.set(value);
            setConceptModified();
            return true;
        }
        return false;
    }
    
    protected boolean compareAndSet(long current, long update) {
    	if(current == update) {
			if(getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES) setConceptModified();
			return true;
		} else if(m_value.compareAndSet(current, update)) {
			setConceptModified();
			return true;
		}
    	return false;
    }

    public boolean setLong(long value) {
        return setCurrent(value);
    }

    public boolean setLong(long value, long time) {
        return setCurrent(value);
    }

    public double getDouble() {
        return (double)getLong();
    }

    public double getDouble(long time) throws PropertyException {
        return (double)getLong(time);
    }

    protected Object valueToObject(long index) {
        return getLong();
    }

    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer, int order) {
        super.serialize(serializer,order);
        if (isSet()) {
            serializer.writeLongProperty(getLong());
        }
    }

    /**
     *
     * @param deserializer
     */
    public void deserialize(ConceptDeserializer deserializer, int order) {
        super.deserialize(deserializer, order);
        if (isSet()) {
            m_value.set(deserializer.getLongProperty());
        }
    }

    /**
     *
     * @param output
     * @throws Exception
     */
    protected void writeValueBytes(DataOutput output) throws Exception {
        output.writeLong(getLong());
    }

    /**
     *
     * @param input
     * @throws Exception
     */
    protected void readValueBytes(DataInput input) throws Exception {
        m_value.set(input.readLong());
    }

    static protected long objectToLong(Object obj) {
        if ((null != obj) && Number.class.isAssignableFrom(obj.getClass())) {
            return ((Number) obj).longValue();
        }
        else if(obj instanceof String) {
            return Long.parseLong((String)obj);
        }
        else {
            throw new ClassCastException();
        }
    }

    public long preIncrement() {
    	setConceptModified();
        return m_value.incrementAndGet();
    }

    public long preDecrement() {
    	setConceptModified();
        return m_value.decrementAndGet();
    }

    public long postIncrement() {
    	setConceptModified();
        return m_value.getAndIncrement();
    }

    public long postDecrement() {
    	setConceptModified();
        return m_value.getAndDecrement();
    }

    public long multAssign(long mult) {
    	while(true) {
    		long current = m_value.get();
    		long update = current * mult;
    		if(compareAndSet(current, update)) {
    			return update;
    		}
    	}
    }

    public long divAssign(long div) {
    	while(true) {
    		long current = m_value.get();
    		long update = current / div;
    		if(compareAndSet(current, update)) {
    			return update;
    		}
    	}
    }

    public long modAssign(long mod) {
    	while(true) {
    		long current = m_value.get();
    		long update = current % mod;
    		if(compareAndSet(current, update)) {
    			return update;
    		}
    	}
    }

    public long addAssign(long add) {
    	if(add != 0 || getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES) setConceptModified();
    	return m_value.addAndGet(add);
    }

    public long subAssign(long sub) {
    	if(sub != 0 || getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES) setConceptModified();
        return m_value.addAndGet(-sub);
    }    
}
