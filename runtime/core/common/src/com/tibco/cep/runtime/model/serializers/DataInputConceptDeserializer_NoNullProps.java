package com.tibco.cep.runtime.model.serializers;

//import com.tangosol.io.pof.PofReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.DateTimeTuple;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.element.impl.DateTimeTupleImpl;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Feb 24, 2009
 * Time: 12:41:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataInputConceptDeserializer_NoNullProps implements ConceptDeserializer {
    boolean error=false;
    String msg;

    public DataInput buf;
    int currentHistorySize;
    int currentHistoryIndex;
    long id;
    int version;
    String extId;
    boolean hasSchemaChanged=false;
    ConceptSerializer.ConceptMigrator migrator;
    boolean isDeleted=false;
    int typeId;

    /**
     * {@value}.
     */
    public static final int BYTE_POSITION_TYPE_ID_INT = 0;

    /**
     * {@value}.
     */
    public static final int BYTE_POSITION_VERSION_INT = 4;

    /**
     * {@value}.
     */
    public static final int BYTE_POSITION_DELETED_BOOLEAN = 8;

    /**
     * <b>Note:</b> Make sure the first 3 item positions match the positions in these properties -
     * {@link #BYTE_POSITION_TYPE_ID_INT}, {@link #BYTE_POSITION_TYPE_ID_INT} and
     * {@link #BYTE_POSITION_DELETED_BOOLEAN}.
     * @param buf
     */
    public DataInputConceptDeserializer_NoNullProps(DataInput buf) throws IOException {
        this.buf=buf;
        typeId=buf.readInt();
        version=buf.readInt();
        isDeleted=buf.readBoolean();
        id = buf.readLong();
        if (buf.readBoolean()) {
            extId= buf.readUTF();
        }
    }

    /**
    if (!error) {
        buf.writeLong(key);
        if (extKey != null) {
            buf.writeBoolean(true);
            buf.writeUTF(extKey);
        } else {
            buf.writeBoolean(false);
        }
        if (state == ConceptSerializer.STATE_DELETED) {
            buf.writeBoolean(true);
        } else {
            buf.writeBoolean(false);
        }
        buf.writeInt(version);
    }
    **/
//TODO: Bala: ensure not used    
//    public DataInputConceptDeserializer_NoNullProps(PofReader pofReader) throws IOException{
//        byte [] bytes=pofReader.readByteArray(0);
//        pofReader.readRemainder();
//        buf = new DataInputStream(new ByteArrayInputStream(bytes));
//        id = buf.readLong();
//        if (buf.readBoolean()) {
//            extId= buf.readUTF();
//        }
//        isDeleted=buf.readBoolean();
//        version=buf.readInt();
//    }

    public DataInputConceptDeserializer_NoNullProps(DataInput buf, ConceptSerializer.ConceptMigrator migrator) throws IOException{
        this(buf);
        this.hasSchemaChanged=true;
        this.migrator=migrator;
    }


    public boolean isDeleted() {
        return isDeleted;
    }

    public int getTypeId() {
        return typeId;
    }

    /**
     *
     * @return
     */
    public boolean hasSchemaChanged() {
        return hasSchemaChanged;  //To change body of implemented methods use File | Settings | File Templates.
    }


    /**
     *
     * @return
     */
    public ConceptSerializer.ConceptMigrator getConceptMigrator() {
        return migrator;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     *
     * @return
     */
    public long startConcept() {
        try {
            return id;
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @return
     */
    public long getId() {
        try {
            return id;
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @return
     */
    public String getExtId() {
        return extId;
    }

    public int getVersion() {
        return version;
    }

    public void endConcept() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public ConceptOrReference startParentConcept() {
        try {
            if (buf.readBoolean()) {
                return new com.tibco.cep.runtime.model.element.impl.Reference(buf.readLong());
            }
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void endParentConcept() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int startReverseReferences() {
        try {
            return buf.readInt();
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getReverseReferences(ConceptImpl.RevRefItr itr) {
        try {
            while(itr.next()) {
                if(buf.readBoolean()) {
                    itr.setPropName(buf.readUTF());
                    itr.setReverseRef(new com.tibco.cep.runtime.model.element.impl.Reference(buf.readLong()));
                }
                else {
                    itr.setPropName(null);
                    itr.setReverseRef(null);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void endReverseReferences() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     *
     * @return
     */
    public int getType() {
        return ConceptSerializer.TYPE_STREAM;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     *
     * @param propertyIndex
     * @return
     */
    public boolean startProperty(String propertyName, int propertyIndex) {
        try {
            return buf.readBoolean();
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param propertyName
     * @param index
     * @return
     */
    public int startPropertyArray(String propertyName, int index) {
        try {
            return buf.readInt();
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    public boolean startPropertyArrayElement(int index) {
        try {
            return buf.readBoolean();
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }

    }

    public void endPropertyArrayElement() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int startPropertyAtom(String propertyName, int index) {
        try {
           currentHistorySize=buf.readInt();
           return currentHistorySize;
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     */
    public void endProperty() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     *
     * @param s
     */
    public String getStringProperty() {
        try {
            if (buf.readBoolean()) {
                return buf.readUTF();
            } else {
                return null;
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param i
     */
    public int getIntProperty() {
        try {
            return buf.readInt();
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param b
     */
    public boolean getBooleanProperty() {
        try {
            return buf.readBoolean();
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param l
     */
    public long getLongProperty() {
        try {
            return buf.readLong();
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param d
     */
    public double getDoubleProperty() {
        try {
            return buf.readDouble();
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param time
     */
    public DateTimeTuple getDateTimeProperty() {
        try {
            if (buf.readBoolean()) {
                return new DateTimeTupleImpl(buf.readLong(), buf.readUTF());
            } else {
                return new DateTimeTupleImpl(0L, null);
            }
            //return buf.readLong();
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param ref
     */
    public ConceptOrReference getReferenceConceptProperty() {
        try {
            if (buf.readBoolean()) {
                return new com.tibco.cep.runtime.model.element.impl.Reference(buf.readLong());
            } else {
                return null;
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param ref
     */
    public ConceptOrReference getContainedConceptProperty() {
        try {
            if (buf.readBoolean()) {
                return new com.tibco.cep.runtime.model.element.impl.Reference(buf.readLong());
            } else {
                return null;
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @param s
     * @param time
     */
    public void getStringProperty(String[] s, long[]time) {
        try {
            int till= s.length > currentHistorySize? currentHistorySize: s.length;
            for (int i=0; i < till; i++) {
                time[i]= buf.readLong();
                if (buf.readBoolean()) {
                    s[i]= buf.readUTF();
                } else {
                    s[i]=null;
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getIntProperty(int[]val, long[]time) {
        try {
            int till= val.length > currentHistorySize? currentHistorySize: val.length;
            for (int i=0; i < till; i++) {
                time[i]= buf.readLong();
                val[i]= buf.readInt();
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getBooleanProperty(boolean[]b, long[] time) {
        try {
            int till= b.length > currentHistorySize? currentHistorySize: b.length;
            for (int i=0; i < till; i++) {
                time[i]= buf.readLong();
                b[i]= buf.readBoolean();
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getLongProperty(long[] l, long[] time) {
        try {
            int till= l.length > currentHistorySize? currentHistorySize: l.length;
            for (int i=0; i < till; i++) {
                time[i]= buf.readLong();
                l[i]= buf.readLong();
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getDoubleProperty(double[] d, long[] time) {
        try {
            int till= d.length > currentHistorySize? currentHistorySize: d.length;
            for (int i=0; i < till; i++) {
                time[i]= buf.readLong();
                d[i]= buf.readDouble();
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getDateTimeProperty(DateTimeTuple[] date, long[] time) {
        try {
            int till= date.length > currentHistorySize? currentHistorySize: date.length;
            for (int i=0; i < till; i++) {
                time[i]= buf.readLong();
                if (buf.readBoolean()) {
                    date[i]= new DateTimeTupleImpl(buf.readLong(), buf.readUTF());
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getDateTimeProperty(long[] date, String [] tz, long[] time) {
        try {
            int till= date.length > currentHistorySize? currentHistorySize: date.length;
            for (int i=0; i < till; i++) {
                time[i]= buf.readLong();
                if (buf.readBoolean()) {
                    date[i]= buf.readLong();
                    tz[i] = buf.readUTF();
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getReferenceConceptProperty(ConceptOrReference[] ref, long[] time) {
        try {
            int till= ref.length > currentHistorySize? currentHistorySize: ref.length;
            for (int i=0; i < till; i++) {
                time[i]= buf.readLong();
                if (buf.readBoolean()) {
                    ref[i]= new com.tibco.cep.runtime.model.element.impl.Reference(buf.readLong());
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void getContainedConceptProperty(ConceptOrReference[] ref, long[] time) {
        try {
            int till= ref.length > currentHistorySize? currentHistorySize: ref.length;
            for (int i=0; i < till; i++) {
                time[i]= buf.readLong();
                if (buf.readBoolean()) {
                    ref[i]= new com.tibco.cep.runtime.model.element.impl.Reference(buf.readLong());
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    /**
     * @see #BYTE_POSITION_TYPE_ID_INT
     * @param buf
     * @return
     * @throws IOException
     */
    public final static int getTypeId (DataInputStream buf) throws IOException{
        buf.reset();
        return buf.readInt();
    }

    /**
     * @see #BYTE_POSITION_VERSION_INT
     * @param buf
     * @return
     * @throws IOException
     */
    public final static int getVersion (DataInputStream buf) throws IOException{
        buf.reset();
        buf.readInt();
        return buf.readInt();
    }

    /**
     * @see #BYTE_POSITION_DELETED_BOOLEAN
     * @param buf
     * @return
     * @throws IOException
     */
    public final static boolean isDeleted (DataInputStream buf) throws IOException{
        buf.reset();
        buf.readInt();
        buf.readInt();
        return buf.readBoolean();
    }

    public boolean areNullPropsSerialized() {
        return false;
    }
}

