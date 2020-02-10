package com.tibco.cep.runtime.model.element.impl.property.atomic;

import com.tibco.cep.runtime.model.element.*;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsInt;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:57:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomIntAtomic extends PropertyAtomAtomic implements PropertyAtomInt {

    AtomicInteger m_value;

    public PropertyAtomIntAtomic(Object owner) {
        super(owner);
        m_value = new AtomicInteger(DEFAULT_VALUE);
      //not worth having synchronized blocks in the setters to keep this thread safe so just set it to true
        setIsSet();
    }

    public PropertyAtomIntAtomic(Object owner, int defaultValue) {
        super(owner);
        m_value = new AtomicInteger(defaultValue);
        setIsSet();
    }

    @Override
    public PropertyAtomAtomic copy(Object newOwner) {
        return _copy(new PropertyAtomIntAtomic(newOwner));
    }

    protected PropertyAtomIntAtomic _copy(PropertyAtomIntAtomic ret) {
        super._copy(ret);
        ret.m_value.set(getInt());
        return ret;
    }

    public Object getValue() {
        return getInt();
    }

    public boolean setValue(Object obj) {
        if (obj != null) {
            return setCurrent(objectToInt(obj));
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(Object obj, long time) {
        return setValue(obj);
    }

    public int getInt() {
        return m_value.get();
    }

    public int getInt(long time) throws PropertyException {
        return getInt();
    }

    public int getIntAtIdx(int idx) throws PropertyException {
        return getInt();
    }

    public XmlTypedValue getXMLTypedValue() {
        return new XsInt(getInt());
    }

    public boolean setValue(XmlTypedValue value) throws Exception {
        if (value != null) {
            return setCurrent(value.getAtom(0).castAsInt());
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(String value) throws Exception {
        if (value != null) {
            return setCurrent(Integer.valueOf(value).intValue());
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    private boolean setCurrent(int value) {
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

    protected boolean compareAndSet(int current, int update) {
    	if(current == update) {
			if(getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES) setConceptModified();
			return true;
		} else if(m_value.compareAndSet(current, update)) {
			setConceptModified();
			return true;
		}
    	return false;
    }
    
    public boolean setInt(int value) {
        return setCurrent(value);
    }

    public boolean setInt(int value, long time) {
        return setCurrent(value);
    }

    public double getDouble() {
        return (double)getInt();
    }

    public double getDouble(long time) throws PropertyException {
        return (double)getInt(time);
    }

    protected Object valueToObject(int index) {
        return getInt();
    }

    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer, int order) {
        super.serialize(serializer,order);
        if (isSet()) {
            serializer.writeIntProperty(getInt());
        }
    }

    /**
     *
     * @param deserializer
     */
    public void deserialize(ConceptDeserializer deserializer, int order) {
        super.deserialize(deserializer, order);
        if (isSet()) {
            m_value.set(deserializer.getIntProperty());
        }
    }

    /**
     *
     * @param output
     * @throws Exception
     */
    protected void writeValueBytes(DataOutput output) throws Exception {
        output.writeInt(getInt());
    }

    /**
     *
     * @param input
     * @throws Exception
     */
    protected void readValueBytes(DataInput input) throws Exception {
        m_value.set(input.readInt());
    }

    static protected int objectToInt(Object obj) {
        if ((null != obj) && Number.class.isAssignableFrom(obj.getClass())) {
            return ((Number) obj).intValue();
        }
        else if(obj instanceof String) {
            return Integer.parseInt((String)obj);
        }
        else {
            throw new ClassCastException();
        }
    }

    public int preIncrement() {
    	setConceptModified();
        return m_value.incrementAndGet();
    }

    public int preDecrement() {
    	setConceptModified();
        return m_value.decrementAndGet();
    }

    public int postIncrement() {
    	setConceptModified();
        return m_value.getAndIncrement();
    }

    public int postDecrement() {
    	setConceptModified();
        return m_value.getAndDecrement();
    }

    public int multAssign(int mult) {
    	while(true) {
    		int current = m_value.get();
    		int update = current * mult;
    		if(compareAndSet(current, update)) {
    			return update;
    		}
    	}
    }

    public int divAssign(int div) {
    	while(true) {
    		int current = m_value.get();
    		int update = current / div;
    		if(compareAndSet(current, update)) {
    			return update;
    		}
    	}
    }

    public int modAssign(int mod) {
    	while(true) {
    		int current = m_value.get();
    		int update = current % mod;
    		if(compareAndSet(current, update)) {
    			return update;
    		}
    	}
    }
    
    public int addAssign(int add) {
    	if(add != 0 || getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES) setConceptModified();
    	return m_value.addAndGet(add);
    }

    public int subAssign(int sub) {
    	if(sub != 0 || getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES) setConceptModified();
        return m_value.addAndGet(-sub);
    }    
}
