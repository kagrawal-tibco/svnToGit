/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.system.impl;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.serializers.SerializableLite;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable.Tuple;

// It was ExternalizableLite, but moved to SerializableLite.
public class ObjectTupleImpl implements Tuple<Entity>, SerializableLite {
    private String extId;
    private int typeId;
    private long id;
    private boolean isDeleted=false;
    private Long timeDeleted=null;

    /**
     *
     * @param id
     * @param extId
     * @param typeId
     */
    public ObjectTupleImpl(long id, String extId, int typeId) {
        this.id=id;
        this.extId=extId;
        this.typeId=typeId;
    }

    /**
     *
     * @param id
     * @param extId
     */
    public ObjectTupleImpl(long id, String extId) {
        this.id=id;
        this.extId=extId;
    }

    public ObjectTupleImpl(int typeId, long id, String extId, boolean isDeleted) {
        this.typeId = typeId;
        this.id = id;
        this.extId = extId;
        this.isDeleted = isDeleted;
        if (isDeleted) {
            this.timeDeleted = System.currentTimeMillis();
        }
    }

    public String toString() {
        return "ObjectTuple[id="+id+",extId="+extId+",typeId="+typeId+"]";
    }

    public String toString(Cluster cluster) throws Exception{
        return "ObjectTuple[id="+id+",extId="+extId+",class="+cluster.getMetadataCache().getClass(typeId).getName()+",isDeleted="+isDeleted+"]";
    }

    /**
     *
     */
    public ObjectTupleImpl() {
    }
    
    public void setTypeId(int typeId) {
        this.typeId=typeId;
    }
    
    /**
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getExtId() {
        return extId;
    }

    /**
     *
     * @return
     */
    public int getTypeId() {
        return typeId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public long getTimeDeleted() {
        return timeDeleted;
    }

    public void markDeleted() {
        isDeleted=true;
        timeDeleted=new Long(System.currentTimeMillis());
    }

    protected void markDeleted(long timeDeleted) {
        isDeleted=true;
        this.timeDeleted=timeDeleted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void readExternal(DataInput dataInput) throws IOException {
        typeId=dataInput.readInt();
        isDeleted=dataInput.readBoolean();
        if (isDeleted) {
            timeDeleted=dataInput.readLong();
            id=dataInput.readLong();
        } else {
            id=dataInput.readLong();
            if (dataInput.readBoolean()) {
                extId=dataInput.readUTF();
            }
        }
    }

    /**
     *
     * @param dataOutput
     * @throws IOException
     */
    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(typeId);
        dataOutput.writeBoolean(isDeleted);
        if (isDeleted) {
            dataOutput.writeLong(timeDeleted);
            dataOutput.writeLong(id);
        } else {
            dataOutput.writeLong(id);
            if (extId != null) {
                dataOutput.writeBoolean(true);
                dataOutput.writeUTF(extId);
            } else {
                dataOutput.writeBoolean(false);
            }
        }
    }

    public static String extractExtId(byte[] b) throws IOException {
        DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(b));
        dataInput.readInt();
        if (!dataInput.readBoolean()) {
            dataInput.readLong();
            if (dataInput.readBoolean()) {
                return dataInput.readUTF();
            }
        }
        return null;
    }

    public static byte[] serialize(Tuple tuple) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeInt(tuple.getTypeId());
        dos.writeBoolean(tuple.isDeleted());
        if (tuple.isDeleted()) {
            dos.writeLong(tuple.getTimeDeleted());
        } else {
            dos.writeLong(tuple.getId());
            if ((tuple.getExtId() != null) && (tuple.getExtId().length() > 0)){
                dos.writeBoolean(true);
                dos.writeUTF(tuple.getExtId());
            } else {
                dos.writeBoolean(false);
            }
        }
        return bos.toByteArray();
    }

    public static byte[] makeDeletedTuple(int typeId) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeInt(typeId);
            dos.writeBoolean(true);
            dos.writeLong(System.currentTimeMillis());
            return bos.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static byte[] serialize(int typeId, long id, String extId, boolean isDeleted) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeInt(typeId);
        dos.writeBoolean(isDeleted);

        if (isDeleted) {
            dos.writeLong(System.currentTimeMillis());
            dos.writeLong(id);
        } else {
            dos.writeLong(id);
            if ((extId != null) && (extId.length() > 0)){
                dos.writeBoolean(true);
                dos.writeUTF(extId);
            } else {
                dos.writeBoolean(false);
            }
        }
        return bos.toByteArray();
    }

    public static ObjectTupleImpl deserialize(byte[] buf) throws IOException {
        if (buf == null || buf.length == 0)
            return null;
        ByteArrayInputStream bis = new ByteArrayInputStream(buf);
        DataInputStream dis = new DataInputStream(bis);
        int typeId=dis.readInt();
        boolean isDeleted= dis.readBoolean();
        if (isDeleted) {
            long timeDeleted=dis.readLong();
            long id = dis.readLong();
            ObjectTupleImpl tuple = new ObjectTupleImpl(id,null);
            tuple.setTypeId(typeId);
            tuple.markDeleted(timeDeleted);
            return tuple;
        } else {
            long id = dis.readLong();
            String extId = null;
            if (dis.readBoolean()) {
                extId=dis.readUTF();
            }
            ObjectTupleImpl tuple = new ObjectTupleImpl(id,extId);
            tuple.setTypeId(typeId);
            return tuple;
        }
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Entity getEntity() {
        return null;
    }
}