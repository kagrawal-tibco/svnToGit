/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.model.serializers;

//import com.tangosol.io.pof.PofWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.IOException;

import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.DateTimeTuple;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;

//import java.io.DataOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Feb 24, 2009
 * Time: 12:38:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataOutputConceptSerializer_NoNullProps implements ConceptSerializer {
    boolean error=false;
    String msg;
    int currentHistorySize;
    int currentHistoryIndex;

    public DataOutput buf;
//    protected PofWriter pofWriter;
    protected ByteArrayOutputStream bufStream;
    protected int typeId;

    /**
     *
     *
     * @param buf
     */
    public DataOutputConceptSerializer_NoNullProps(DataOutput buf, int typeId) {
        this.buf=buf;
        this.typeId=typeId;
    }

    /**
     *
     * @param buf
     * @param entityClz
     */
    public DataOutputConceptSerializer_NoNullProps(DataOutput buf, Class entityClz) {
        try {
            this.buf=buf;
            typeId= CacheClusterProvider.getInstance().getCacheCluster().getMetadataCache().getTypeId(entityClz);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

//TODO: Bala: ensure this is not used.    
//    public DataOutputConceptSerializer_NoNullProps(PofWriter pofWriter) {
//        this.pofWriter=pofWriter;
//        bufStream = new ByteArrayOutputStream(32*4);
//        buf = new DataOutputStream(bufStream);
//    }

    public boolean areNullPropsSerialized() {
        return false;
    }

    /**
     *
     * @param clz
     * @param key
     * @param extKey
     * @param state
     */
    public void startConcept(Class clz, long key, String extKey, int state, int version) {
        try {
            if (!error) {
                buf.writeInt(typeId);

                if (buf instanceof VersionAwareDataOutput) {
                    ((VersionAwareDataOutput) buf).markBeforeVersionWrite();
                }
                buf.writeInt(version);

                if (state == ConceptSerializer.STATE_DELETED) {
                    buf.writeBoolean(true);
                } else {
                    buf.writeBoolean(false);
                }

                buf.writeLong(key);
                if (extKey != null) {
                    buf.writeBoolean(true);
                    buf.writeUTF(extKey);
                } else {
                    buf.writeBoolean(false);
                }
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }


    public void endConcept() {
        //To change body of implemented methods use File | Settings | File Templates.
        try {
//            if (pofWriter != null) {
//                byte [] bytes=bufStream.toByteArray();
//                pofWriter.writeByteArray(0, bytes);
//                pofWriter.writeRemainder(null);
//            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
        }
    }

    /**
     *
     * @param parent
     */
    public void startParentConcept(ConceptOrReference parent) {
        try {
            if (!error) {
                if (parent != null) {
                    buf.writeBoolean(true);
                    buf.writeLong(parent.getId());
                } else {
                    buf.writeBoolean(false);
                }
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    public void endParentConcept() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void startReverseReferences(ConceptImpl.RevRefItr itr, int size) {
        try {
            if (!error) {
                if (size > 0) {
                    buf.writeInt(size);
                    while(itr.next()) {
                        if(itr.reverseRef() != null && itr.propName() != null) {
                            buf.writeBoolean(true);
                            buf.writeUTF(itr.propName());
                            buf.writeLong(itr.reverseRef().getId());
                        } else {
                            buf.writeBoolean(false);
                        }
                    }
                } else {
                    buf.writeInt(0);
                }
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    public void endReverseReferences() {

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
     * @param propertyName
     * @param propertyIndex
     * @param isSet
     */
    public void startProperty(String propertyName, int propertyIndex, boolean isSet) {
        try {
            if (!error) {
                buf.writeBoolean(isSet);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    public void startPropertyArray(String propertyName, int index, int length) {
        try {
            if (!error) {
                buf.writeInt(length);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     * @param index
     */
    public void startPropertyArrayElement(int index, boolean isSet) {
        try {
            if (!error) {
                buf.writeBoolean(isSet);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     */
    public void endPropertyArrayElement() {
    }

    public void startPropertyAtom(String propertyName, int index, boolean isSet, int historyIndex, int historySize) {
        try {
            if (!error) {
//                buf.writeBoolean(isSet);
//                if (isSet) {
//                    buf.writeInt(historySize);
//                }
                buf.writeInt(historySize);
                currentHistorySize=historySize;
                currentHistoryIndex=historyIndex;
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     */
    public void endProperty() {
        currentHistorySize=currentHistoryIndex=0;
    }


    /**
     *
     * @param s
     */
    public void writeStringProperty(String s) {
        try {
            if (!error) {
                if (s != null) {
                    buf.writeBoolean(true);
                    buf.writeUTF(s);
                } else {
                    buf.writeBoolean(false);
                }
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     * @param i
     */
    public void writeIntProperty(int i) {
        try {
            if (!error) {
                buf.writeInt(i);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     * @param b
     */
    public void writeBooleanProperty(boolean b) {
        try {
            if (!error) {
                buf.writeBoolean(b);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     * @param l
     */
    public void writeLongProperty(long l) {
        try {
            if (!error) {
                buf.writeLong(l);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     * @param d
     */
    public void writeDoubleProperty(double d) {
        try {
            if (!error) {
                buf.writeDouble(d);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     * @param date
     */
    public void writeDateTimeProperty(DateTimeTuple date) {
        try {
            if (!error) {
                if (date != null) {
                    if (date.getTimeZone() != null) {
                        buf.writeBoolean(true);
                        buf.writeLong(date.getTime());
                        buf.writeUTF(date.getTimeZone());
                    } else {
                        buf.writeBoolean(false);
                    }
                } else {
                    buf.writeBoolean(false);
                }
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }


    /**
     *
     * @param ref
     */
    public void writeReferenceConceptProperty(ConceptOrReference ref) {
        try {
            if (!error) {
                if (ref != null) {
                    buf.writeBoolean(true);
                    buf.writeLong(ref.getId());
                } else {
                    buf.writeBoolean(false);
                }
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     * @param ref
     */
    public void writeContainedConceptProperty(ConceptOrReference ref) {
        try {
            if (!error) {
                if (ref != null) {
                    buf.writeBoolean(true);
                    buf.writeLong(ref.getId());
                } else {
                    buf.writeBoolean(false);
                }
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }


    /**
     *
     * @param s
     * @param time
     */
    public void writeStringProperty(String s[], long time[]) {
        try {
            if (!error) {
                if (s != null) {
                    for (int i=0; i < currentHistorySize;i++ ) {
                        String val= s[mapIndex(currentHistoryIndex, time,i)];
                        long time_v=time[mapIndex(currentHistoryIndex, time,i)];
                        buf.writeLong(time_v);
                        if (val != null) {
                            buf.writeBoolean(true);
                            buf.writeUTF(val);
                        } else {
                            buf.writeBoolean(false);
                        }

                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     * @param val
     * @param time
     */
    public void writeIntProperty(int []val, long time[]) {
        try {
            if (!error) {
                if (val != null) {
                    for (int i=0; i < currentHistorySize;i++ ) {
                        buf.writeLong(time[mapIndex(currentHistoryIndex, time,i)]);
                        buf.writeInt(val[mapIndex(currentHistoryIndex, time,i)]);
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    /**
     *
     * @param b
     * @param time
     */
    public void writeBooleanProperty(boolean b[], long time[]) {
        try {
            if (!error) {
                if (b != null) {
                    for (int i=0; i < currentHistorySize;i++ ) {
                        buf.writeLong(time[mapIndex(currentHistoryIndex, time,i)]);
                        buf.writeBoolean(b[mapIndex(currentHistoryIndex, time,i)]);
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    public void writeLongProperty(long l[], long time[]) {
        try {
            if (!error) {
                if (l != null) {
                    for (int i=0; i < currentHistorySize;i++ ) {
                        buf.writeLong(time[mapIndex(currentHistoryIndex, time,i)]);
                        buf.writeLong(l[mapIndex(currentHistoryIndex, time,i)]);
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    public void writeDoubleProperty(double d[], long time[]) {
        try {
            if (!error) {
                if (d != null) {
                    for (int i=0; i < currentHistorySize;i++ ) {
                        buf.writeLong(time[mapIndex(currentHistoryIndex, time,i)]);
                        buf.writeDouble(d[mapIndex(currentHistoryIndex, time,i)]);
                    }
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    public void writeDateTimeProperty(DateTimeTuple date[], long time[]) {
        try {
            if (!error) {
                if (date != null) {
                    for (int i=0; i < currentHistorySize;i++ ) {
                        buf.writeLong(time[mapIndex(currentHistoryIndex, time,i)]);
                        DateTimeTuple val= date[mapIndex(currentHistoryIndex, time,i)];
                        if ((val != null) && (val.getTimeZone() != null)){
                            buf.writeBoolean(true);
                            buf.writeLong(val.getTime());
                            buf.writeUTF(val.getTimeZone());
                        } else {
                            buf.writeBoolean(false);
                        }
                    }
                } else {
                    buf.writeBoolean(false);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    public void writeDateTimeProperty(long date[], String tz[], long time[]) {
        try {
            if (!error) {
                if (date != null) {
                    for (int i=0; i < currentHistorySize;i++ ) {
                        buf.writeLong(time[mapIndex(currentHistoryIndex, time,i)]);
                        String stz= tz[mapIndex(currentHistoryIndex, time,i)];
                        if (stz != null) {
                            buf.writeBoolean(true);
                            buf.writeLong(date[mapIndex(currentHistoryIndex, time,i)]);
                            buf.writeUTF(stz);
                        } else {
                            buf.writeBoolean(false);
                        }
                    }
                } else {
                    buf.writeBoolean(false);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    public void writeReferenceConceptProperty(ConceptOrReference ref[], long time[]) {
        try {
            if (!error) {
                if (ref != null) {
                    for (int i=0; i < currentHistorySize;i++ ) {
                        buf.writeLong(time[mapIndex(currentHistoryIndex, time,i)]);
                        ConceptOrReference sref= ref[mapIndex(currentHistoryIndex, time,i)];
                        if (sref != null) {
                            buf.writeBoolean(true);
                            buf.writeLong(sref.getId());
                        } else {
                            buf.writeBoolean(false);
                        }
                    }
                } else {
                    buf.writeBoolean(false);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    public void writeContainedConceptProperty(ConceptOrReference ref[], long time[]) {
        try {
            if (!error) {
                if (ref != null) {
                    for (int i=0; i < currentHistorySize;i++ ) {
                        buf.writeLong(time[mapIndex(currentHistoryIndex, time,i)]);
                        ConceptOrReference sref= ref[mapIndex(currentHistoryIndex, time,i)];
                        if (sref != null) {
                            buf.writeBoolean(true);
                            buf.writeLong(sref.getId());
                        } else {
                            buf.writeBoolean(false);
                        }
                    }
                } else {
                    buf.writeBoolean(false);
                }
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

    protected int mapIndex(int m_index, long m_time[], int idx) {
        if (m_time[m_time.length-1]==0L) return idx;
        return (m_index+idx+1)%m_time.length; // buffer has rotated; also upgrading to int
    }

    public void startPropertyArrayElement(int index) {
        throw new UnsupportedOperationException("Unsupported Operation startPropertyArrayElement ..");
    }
}

