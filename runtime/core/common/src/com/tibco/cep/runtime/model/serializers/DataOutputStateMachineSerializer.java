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

import com.tibco.cep.runtime.model.element.StateMachineSerializer;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Feb 7, 2009
 * Time: 12:24:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataOutputStateMachineSerializer implements StateMachineSerializer {
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
    public DataOutputStateMachineSerializer(DataOutput buf, int typeId) {
        this.buf=buf;
        this.typeId=typeId;
    }

    /**
     *
     * @param buf
     * @param entityClz
     */
    public DataOutputStateMachineSerializer(DataOutput buf, Class entityClz) {
        try {
            this.buf=buf;
            typeId= CacheClusterProvider.getInstance().getCacheCluster().getMetadataCache().getTypeId(entityClz);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

//    public DataOutputStateMachineSerializer(PofWriter pofWriter) {
////        this.pofWriter=pofWriter;
//        bufStream = new ByteArrayOutputStream(32*4);
//        buf = new DataOutputStream(bufStream);
//    }

    public void addState(int stateIndex, int stateValue) {
        try {
            if (!error) {
                buf.writeInt(stateIndex);
                buf.writeInt(stateValue);
            } else {
                throw new RuntimeException(msg);
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public int getType() {
        return StateMachineSerializer.TYPE_STREAM;
    }

    public void endStateMachine() {
        //To change body of implemented methods use File | Settings | File Templates.
        try {
            if (error) {
                throw new RuntimeException("Serializer in Invalid State " + msg);
            } else {
//                if (pofWriter != null) {
//                    byte [] bytes=bufStream.toByteArray();
//                    pofWriter.writeByteArray(0, bytes);
//                    pofWriter.writeRemainder(null);
//                } else {
                    buf.writeInt(-1); // EOF for propertyIndex
//                }
            }
        } catch (Exception ex) {
            error=true;
            msg=ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void startParent(ConceptOrReference parent) {
        try {
            if (!error) {
                buf.writeLong(parent.getId());
            } else {
                throw new RuntimeException("Serializer in Invalid State " + msg);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }

    public void startStateMachine(Class clz, long key, String extKey, int state, int version) {
        try {
            if (!error) {
                buf.writeInt(typeId);

                if (buf instanceof VersionAwareDataOutput) {
                    ((VersionAwareDataOutput) buf).markBeforeVersionWrite();
                }
                buf.writeInt(version);

                if (state == StateMachineSerializer.STATE_DELETED) {
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
            } else {
                throw new RuntimeException("Serializer in Invalid State " + msg);
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(ex);
        }
    }
}
