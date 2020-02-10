package com.tibco.cep.runtime.model.element.impl.property.history;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Calendar;

import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.element.impl.Reference;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyImplSerializerHelper;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Aug 24, 2006
 * Time: 3:54:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImplSerializerHelper {

    public static short getCurrentIndex(PropertyAtomImpl pa) { return pa.m_index; }

    public static void setCurrentIndex(PropertyAtomImpl pa, short _index) { pa.m_index = _index; }

    public static long [] getTimeArray(PropertyAtomImpl pa) { return pa.m_time; }

    public static void setTimeArray(PropertyAtomImpl pa, long[] _times) { pa.m_time = _times; }

    // By Property type.
    public static boolean[] getValues(PropertyAtomBooleanImpl pa) { return pa.m_values; }

    public static void setValues(PropertyAtomBooleanImpl pa, boolean[] _values) { pa.m_values = _values; }

    public static ConceptOrReference[] getValues (PropertyAtomConceptReferenceImpl pa) { return pa.m_values; }

    public static void setValues(PropertyAtomConceptReferenceImpl pa, ConceptOrReference[] _values) { pa.m_values = _values; }

    public static ConceptOrReference[] getValues(PropertyAtomContainedConceptImpl pa) { return pa.m_values; }

    public static void setValues(PropertyAtomContainedConceptImpl pa, ConceptOrReference[] _values) { pa.m_values = _values; }

    public static Calendar[] getValues(PropertyAtomDateTimeImpl pa) { return pa.getInternalValues(); }

    public static void setValues(PropertyAtomDateTimeImpl pa, Calendar[] _values) { pa.updateInternalValues(_values);}

    public static double[] getValues(PropertyAtomDoubleImpl pa) { return pa.m_values; }

    public static void setValues(PropertyAtomDoubleImpl pa, double[] _values ) { pa.m_values = _values; }

    public static int[] getValues(PropertyAtomIntImpl pa) { return pa.m_values; }

    public static void setValues(PropertyAtomIntImpl pa, int[] _values ) { pa.m_values = _values; }

    public static long[] getValues(PropertyAtomLongImpl pa) { return pa.m_values; }

    public static void setValues(PropertyAtomLongImpl pa, long[] _values)  { pa.m_values = _values; }

    public static String[] getValues(PropertyAtomStringImpl pa) { return pa.m_values; };

    public static void setValues(PropertyAtomStringImpl pa, String[] _values) { pa.m_values = _values; }

    // For use with DefaultSerializer
    public static void serialize(PropertyAtomImpl pa, DataOutput dout) throws IOException {

        dout.writeBoolean(pa.isSet());
        dout.writeInt(pa.getHistorySize());
        dout.writeShort(getCurrentIndex(pa));
        long[] times = getTimeArray(pa);
        for(int i=0; i < times.length; i++)
            dout.writeLong(times[i]);

        if(pa instanceof PropertyAtomBooleanImpl) {
            boolean[] values = getValues((PropertyAtomBooleanImpl) pa);
            for(int i=0; i < values.length; i++)
                dout.writeBoolean(values[i]);
        } else if(pa instanceof PropertyAtomConceptReferenceImpl) {
            ConceptOrReference[] values = getValues((PropertyAtomConceptReferenceImpl) pa);
            for(int i=0; i < values.length; i++) {
                if(values[i] != null) {
                    dout.writeBoolean(true);
                    dout.writeLong(values[i].getId());
                } else
                    dout.writeBoolean(false);
            }
        } else if(pa instanceof PropertyAtomContainedConceptImpl) {
            ConceptOrReference[] values = getValues((PropertyAtomContainedConceptImpl) pa);
            for(int i=0; i < values.length; i++) {
                if(values[i] != null) {
                    dout.writeBoolean(true);
                    dout.writeLong(values[i].getId());
                } else
                    dout.writeBoolean(false);
            }
        } else if(pa instanceof PropertyAtomDateTimeImpl) {
            //Calendar[] values = getValues((PropertyAtomDateTimeImpl) pa);
            PropertyAtomDateTimeImpl dt = (PropertyAtomDateTimeImpl) pa;
            long[] values = dt.m_values;
            String[] tzs = dt.m_timeZones;
            for(int i=0; i < values.length; i++) {
                dout.writeLong(values[i]);
                if(tzs[i] != null) {
                    dout.writeBoolean(true);
                    //dout.writeUTF(((XsDateTime)PropertyAtomDateTimeImpl.java2xsd_dt_conv.convertToTypedValue(values[i])).castAsString());
                    dout.writeUTF(tzs[i]);
                } else
                    dout.writeBoolean(false);
            }
        } else if(pa instanceof PropertyAtomDoubleImpl) {
            double[] values = getValues((PropertyAtomDoubleImpl)pa);
            for(int i=0; i < values.length; i++) {
                dout.writeDouble(values[i]);
            }
        } else if(pa instanceof PropertyAtomIntImpl) {
            int[] values = getValues((PropertyAtomIntImpl) pa);
            for(int i=0; i < values.length; i++) {
                dout.writeInt(values[i]);
            }
        } else if(pa instanceof PropertyAtomLongImpl) {
            long[] values = getValues((PropertyAtomLongImpl) pa);
            for(int i=0; i < values.length; i++) {
                dout.writeLong(values[i]);
            }
        } else if(pa instanceof PropertyAtomStringImpl) {
            String[] values = getValues((PropertyAtomStringImpl) pa);
            for (int i = 0; i < values.length; i++) {
                if (values[i] != null) {
                    dout.writeBoolean(true);
                    dout.writeUTF(values[i]);
                } else
                    dout.writeBoolean(false);
            }
        }
    }

    public static void deserialize(PropertyAtomImpl[] atoms, int idx, DataInput din, PropertyArrayImpl arr) throws IOException {
        boolean isSet = din.readBoolean();
        int historysize = din.readInt();

        if(arr instanceof PropertyArrayBooleanImpl)
            atoms[idx] = new PropertyAtomBooleanImpl(historysize, arr);
        else if(arr instanceof PropertyArrayConceptReferenceImpl)
            atoms[idx] = new PropertyAtomConceptReferenceImpl(historysize, arr);
        else if(arr instanceof PropertyArrayContainedConceptImpl)
            atoms[idx] = new PropertyAtomContainedConceptImpl(historysize, arr);
        else if(arr instanceof PropertyArrayDateTimeImpl)
            atoms[idx] = new PropertyAtomDateTimeImpl(historysize, arr);
        else if(arr instanceof PropertyArrayDoubleImpl)
            atoms[idx] = new PropertyAtomDoubleImpl(historysize, arr);
        else if(arr instanceof PropertyArrayIntImpl)
            atoms[idx] = new PropertyAtomIntImpl(historysize, arr);
        else if(arr instanceof PropertyArrayLongImpl)
            atoms[idx] = new PropertyAtomLongImpl(historysize, arr);
        else if(arr instanceof PropertyArrayStringImpl)
            atoms[idx] = new PropertyAtomStringImpl(historysize, arr);

        PropertyAtomImpl atom = atoms[idx];

        if(isSet)
            atom.setIsSet();
        else
            atom.clearIsSet();


        setCurrentIndex(atom, din.readShort());
        long [] times = new long[historysize];
        for(int i=0; i<historysize; i++)
            times[i] = din.readLong();
        setTimeArray(atom, times);

        if(atom instanceof PropertyAtomBooleanImpl) {
            boolean[] values = new boolean[historysize];
            for(int i=0; i < historysize; i++)
                values[i] = din.readBoolean();
            setValues((PropertyAtomBooleanImpl) atom, values);
        } else if(atom instanceof PropertyAtomConceptReferenceImpl) {
            ConceptOrReference[] values = new ConceptOrReference[historysize];
            for(int i=0; i < historysize; i++) {
                if(din.readBoolean())
                    values[i] = new Reference(din.readLong());
                else
                    values[i] = null;
            }
            setValues((PropertyAtomConceptReferenceImpl) atom, values);
        } else if(atom instanceof PropertyAtomContainedConceptImpl) {
            ConceptOrReference[] values = new ConceptOrReference[historysize];
            for(int i=0; i < historysize; i++) {
                if(din.readBoolean())
                    values[i] = new Reference(din.readLong());
                else
                    values[i] = null;
            }
            setValues((PropertyAtomContainedConceptImpl) atom, values);
        } else if(atom instanceof PropertyAtomDateTimeImpl) {
            //MutableCalendar[] values = new Calendar[historysize];
            long[] values = new long[historysize];
            String[] tzs = new String[historysize];

            for(int i=0; i < historysize; i++) {
                values[i] = din.readLong();
                if(din.readBoolean())
                    //values[i] = (Calendar) PropertyAtomDateTimeImpl.xsd2java_dt_conv.convertSimpleType(din.readUTF());
                    tzs[i] = din.readUTF();
                else
                    //values[i] = null;
                    tzs[i] = null;
            }
            //setValues((PropertyAtomDateTimeImpl) pa, values);
            PropertyAtomDateTimeImpl dt = (PropertyAtomDateTimeImpl) atom;
            dt.m_values = values;
            dt.m_timeZones = tzs;
        } else if(atom instanceof PropertyAtomDoubleImpl) {
            double[] values = new double[historysize];
            for(int i=0; i < historysize; i++) {
                values[i] = din.readDouble();
            }
            setValues((PropertyAtomDoubleImpl) atom, values);
        } else if(atom instanceof PropertyAtomIntImpl) {
            int[] values = new int[historysize];
            for(int i=0; i < historysize; i++) {
                values[i] = din.readInt();
            }
            setValues((PropertyAtomIntImpl) atom, values);
        } else if(atom instanceof PropertyAtomLongImpl) {
            long[] values = new long[historysize];
            for(int i=0; i < historysize; i++) {
                values[i] = din.readLong();
            }
            setValues((PropertyAtomLongImpl) atom, values);
        } else if(atom instanceof PropertyAtomStringImpl) {
            String[] values = new String[historysize];
            for (int i = 0; i < historysize; i++) {
                if (din.readBoolean())
                    values[i] = din.readUTF();
                else
                    values[i] = null;
            }
            setValues((PropertyAtomStringImpl) atom, values);
        }

    }

    public static void serialize(PropertyArrayImpl pa, DataOutput dout) throws IOException {
        AbstractPropertyImplSerializerHelper.serialize(pa, dout);
    }

    public static void deserialize(PropertyArrayImpl arr, DataInput din) throws IOException {
    	AbstractPropertyImplSerializerHelper.deserialize(arr, din);
    }
}
