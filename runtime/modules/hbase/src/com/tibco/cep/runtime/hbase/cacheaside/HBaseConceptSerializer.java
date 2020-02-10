package com.tibco.cep.runtime.hbase.cacheaside;

import com.tibco.cep.runtime.hbase.cacheaside.Interfaces.HConstants_old;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.DateTimeTuple;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 7/9/13
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class HBaseConceptSerializer implements ConceptSerializer {

    private Map<String, byte[]> fieldDataMap;
    //Map<String, String> fieldTypeMap;
    //HChannel hChannel;
    private String conceptUri;
    private String currPropertyName;

    //***todo  : Handle History****
    int currentHistorySize = 0;

    public HBaseConceptSerializer(String conceptUri) {
//        this.fieldTypeMap = fieldTypeMap;
//        this.hChannel = hChannel;
        this.conceptUri = conceptUri;
        this.fieldDataMap = new HashMap<String, byte[]>();
    }

    public Map<String, byte[]> getFieldDataMap() {
        return Collections.unmodifiableMap(fieldDataMap);
    }


    @Override
    public void startConcept(Class clz, long key, String extKey, int state, int version) {
        fieldDataMap.put(HConstants_old.ID, Bytes.toBytes(key));
        fieldDataMap.put(HConstants_old.EXTID, Bytes.toBytes(extKey));
    }

    @Override
    public void endConcept() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getType() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void startParentConcept(ConceptOrReference parent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void endParentConcept() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void startReverseReferences(ConceptImpl.RevRefItr itr, int size) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void endReverseReferences() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void startProperty(String propertyName, int index, boolean isSet) {
        currPropertyName = propertyName;
    }

    @Override
    public void startPropertyArray(String propertyName, int index, int length) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void startPropertyArrayElement(int index, boolean isSet) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void startPropertyArrayElement(int index) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void endPropertyArrayElement() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void startPropertyAtom(String propertyName, int index, boolean isSet, int historyIndex, int historySize) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void endProperty() {
        currPropertyName = null;
    }

    @Override
    public void writeStringProperty(String s) {
        byte[] dataBytes = Bytes.toBytes(s);
        fieldDataMap.put(currPropertyName, dataBytes);
    }

    @Override
    public void writeIntProperty(int i) {
        byte[] dataBytes = Bytes.toBytes(i);
        fieldDataMap.put(currPropertyName, dataBytes);
    }

    @Override
    public void writeBooleanProperty(boolean b) {
        byte[] dataBytes = Bytes.toBytes(b);
        fieldDataMap.put(currPropertyName, dataBytes);
    }

    @Override
    public void writeLongProperty(long l) {
        byte[] dataBytes = Bytes.toBytes(l);
        fieldDataMap.put(currPropertyName, dataBytes);
    }

    @Override
    public void writeDoubleProperty(double d) {
        byte[] dataBytes = Bytes.toBytes(d);
        fieldDataMap.put(currPropertyName, dataBytes);
    }

    @Override
    public void writeDateTimeProperty(DateTimeTuple date) {
        if (date.getTimeZone() != null) {
            Calendar c = new GregorianCalendar(TimeZone.getTimeZone(date.getTimeZone()));
            c.setTimeInMillis(date.getTime());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os = null;
            try {
                os = new ObjectOutputStream(out);
                os.writeObject(c);
            } catch (IOException e) {
                e.printStackTrace();
            }

            fieldDataMap.put(currPropertyName, out.toByteArray());
        }
    }

    @Override
    public void writeReferenceConceptProperty(ConceptOrReference ref) {
        if(ref != null)
        {
            Long id = ref.getId();
            fieldDataMap.put(currPropertyName,Bytes.toBytes(id));
        }
    }

    @Override
    public void writeContainedConceptProperty(ConceptOrReference ref) {
        if(ref != null)
        {
            Long id = ref.getId();
            fieldDataMap.put(currPropertyName,Bytes.toBytes(id));
        }
    }

    @Override
    public void writeStringProperty(String[] s, long[] time) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeIntProperty(int[] i, long[] time) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeBooleanProperty(boolean[] b, long[] time) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeLongProperty(long[] l, long[] time) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeDoubleProperty(double[] d, long[] time) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeDateTimeProperty(DateTimeTuple[] date, long[] time) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeDateTimeProperty(long[] date, String[] tz, long[] time) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeReferenceConceptProperty(ConceptOrReference[] ref, long[] time) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeContainedConceptProperty(ConceptOrReference[] ref, long[] time) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean areNullPropsSerialized() {
        return true;
    }
}
