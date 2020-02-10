package com.tibco.cep.runtime.model.element.impl.property.atomic;

import com.tibco.cep.runtime.model.element.*;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsDouble;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:56:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomDoubleAtomic extends PropertyAtomAtomic implements PropertyAtomDouble {
	AtomicLong m_value = new AtomicLong();
	
    public PropertyAtomDoubleAtomic(Object owner) {
        super(owner);
        setRaw(DEFAULT_VALUE);
        //not worth having synchronized blocks in the setters to keep this thread safe so just set it to true
        setIsSet();
    }

    public PropertyAtomDoubleAtomic(Object owner, double defaultValue) {
        super(owner);
        setRaw(defaultValue);
        setIsSet();
    }

    private void setRaw(double val) {
    	m_value.set(Double.doubleToLongBits(val));
    }
    
    @Override
    public PropertyAtomAtomic copy(Object newOwner) {
        return _copy(new PropertyAtomDoubleAtomic(newOwner));
    }

    protected PropertyAtomDoubleAtomic _copy(PropertyAtomDoubleAtomic ret) {
        super._copy(ret);
        ret.m_value.set(m_value.get());
        return ret;
    }

    public Object getValue() {
        return getDouble();
    }

    public boolean setValue(Object obj) {
        if (obj != null) {
            return setCurrent(objectToDouble(obj));
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }
    
    public boolean setValue(Object obj, long time) {
    	return setValue(obj);
    }

    public XmlTypedValue getXMLTypedValue() {
        return new XsDouble(getDouble());
    }

    public boolean setValue(XmlTypedValue value) throws Exception {
        if (value != null) {
            return setCurrent(value.getAtom(0).castAsDouble());
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public boolean setValue(String value) throws Exception {
        if (value != null) {
            return setCurrent(Double.valueOf(value).doubleValue());
        } else {
            return setCurrent(DEFAULT_VALUE);
        }
    }

    public double getDouble() {
        return Double.longBitsToDouble(m_value.get());
    }

    public double getDouble(long time) throws PropertyException {
        return getDouble();
    }

    public double getDoubleAtIdx(int idx) throws PropertyException {
        return getDouble();
    }

    private boolean setCurrent(double value) {
        ((ConceptImpl)getParent()).checkSession();
//        if (!isSet()) {
//            setIsSet();
//            setRaw(value);
//            setConceptModified();
//            return true;
//        }
//        else 
    	if (getHistoryPolicy() == Property.HISTORY_POLICY_ALL_VALUES || value != m_value.get()) {
        	setRaw(value);
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
    
    public boolean setDouble(double value) {
        return setCurrent(value);
    }

    public boolean setDouble(double value, long time) {
        return setCurrent(value);
    }

    protected Object valueToObject(int index) {
        return getDouble();
    }

    public int getInt() {
        return (int)getDouble();
    }

    public int getInt(long time) throws PropertyException {
        return getInt();
    }
    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer, int order) {
        super.serialize(serializer, order);
        if (isSet()) {
            serializer.writeDoubleProperty(getDouble());
        }
    }

    /**
     *
     * @param deserializer
     */
    public void deserialize(ConceptDeserializer deserializer, int order) {
        super.deserialize(deserializer, order);
        if (isSet()) {
            setRaw(deserializer.getDoubleProperty());
        }
    }

    /**
     *
     * @param output
     * @throws Exception
     */
    protected void writeValueBytes(DataOutput output) throws Exception {
        output.writeDouble(getDouble());
    }

    /**
     *
     * @param input
     * @throws Exception
     */
    protected void readValueBytes(DataInput input) throws Exception {
        setRaw(input.readDouble());
    }

    static protected double objectToDouble(Object obj) {
        if ((null != obj) && Number.class.isAssignableFrom(obj.getClass())) {
            return ((Number) obj).doubleValue();
        }
        else if(obj instanceof String) {
            return Double.parseDouble((String)obj);
        }
        else {
            throw new ClassCastException();
        }
    }

    public double preIncrement() {
        return addAssign(1);
    }

    public double preDecrement() {
        return subAssign(1);
    }

    public double postIncrement() {
        double temp = getDouble();
        preIncrement();
        setConceptModified();
        return temp;
    }

    public double postDecrement() {
        double temp = getDouble();
        preDecrement();
        setConceptModified();
        return temp;
    }

    public double multAssign(double mult) {
    	while(true) {
    		long current = m_value.get();
    		double ret = getDouble() * mult;
    		long update = Double.doubleToLongBits(ret);
    		if(compareAndSet(current, update)) {
    			return ret;
    		}
    	}
    }

    public double divAssign(double div) {
    	while(true) {
    		long current = m_value.get();
    		double ret = getDouble() / div;
    		long update = Double.doubleToLongBits(ret);
    		if(compareAndSet(current, update)) {
    			return ret;
    		}
    	}
    }

    public double modAssign(double mod) {
    	while(true) {
    		long current = m_value.get();
    		double ret = getDouble() % mod;
    		long update = Double.doubleToLongBits(ret);
    		if(compareAndSet(current, update)) {
    			return ret;
    		}
    	}
    }

    public double addAssign(double add) {
    	while(true) {
    		long current = m_value.get();
    		double ret = getDouble() + add;
    		long update = Double.doubleToLongBits(ret);
    		if(compareAndSet(current, update)) {
    			return ret;
    		}
    	}
    }

    public double subAssign(double sub) {
    	while(true) {
    		long current = m_value.get();
    		double ret = getDouble() - sub;
    		long update = Double.doubleToLongBits(ret);
    		if(compareAndSet(current, update)) {
    			return ret;
    		}
    	}
    }
}