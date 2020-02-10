/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.scheduler;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.serializers.SerializableHelper;
import com.tibco.cep.runtime.model.serializers.SerializableLite;


/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 14, 2008
 * Time: 9:53:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class WorkTuple implements SerializableLite {
	private final static Logger logger = LogManagerFactory.getLogManager().getLogger(WorkTuple.class);
	
    private String workQueue;
    private String workId;
    private long scheduledTime;
    private int workStatus = WorkEntry.STATUS_PENDING;
    private WorkEntry work;
    protected byte [] buf;

    public WorkTuple() {
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public long getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(long scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

//BALA: ensured backward compatibility with 3.x/4.x for coherence
    public synchronized WorkEntry getWork() {
        try {
            if (work == null) {
                if (buf != null) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(buf);
                    DataInputStream dis = new DataInputStream(bis);
                    work = (WorkEntry) SerializableHelper.readSerializableLite(dis);
//                    String classNm = dis.readUTF();
//                    work = (WorkEntry) Class.forName(classNm).newInstance();
//                    work.readExternal(dis);
                    buf = null;
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return work;
    }

    public void setWork(WorkEntry work) {
        this.work = work;
    }

    public void setBuf(byte [] buf) {
        this.buf=buf;
    }
//BALA: ensured backward compatibility with 3.x/4.x for coherence
    public synchronized byte [] getBuf() throws Exception{
        if (buf == null) {
            if (work != null) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(bos);
                SerializableHelper.writeSerializableLite(dos, work);
//                dos.writeUTF(work.getClass().getName());
//                work.writeExternal(dos);
                dos.flush();
                buf=bos.toByteArray();
                dos.close();
            }
        }
        return buf;
    }
    
    public void readExternal(DataInput dataInput) throws IOException {
        scheduledTime=dataInput.readLong();
        workQueue=dataInput.readUTF();
        workId=dataInput.readUTF();
        workStatus=dataInput.readInt();
//        if (dataInput.readBoolean()) {
//        	 String clzNm = dataInput.readUTF();
//        	 try {
//				  Class clz = Class.forName(clzNm);
//				  work = (WorkEntry) clz.newInstance();
//				  work.readExternal(dataInput);
//		     } catch (Exception e) {
//				//e.printStackTrace();
//			 }
//        }
        try {
        	work = (WorkEntry) SerializableHelper.readSerializableLite(dataInput);
        } catch (Exception exception) {
        	logger.log(Level.ERROR, "Error occurred while deserializing work entry with Id[%s]", exception, workId);
        }
        if (work == null) {
        	logger.log(Level.WARN, "Work entry with Id[%s] cannot be deserialized.", workId);
        }
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(scheduledTime);
        dataOutput.writeUTF(workQueue);
        dataOutput.writeUTF(workId);
        dataOutput.writeInt(workStatus);
        SerializableHelper.writeSerializableLite(dataOutput, getWork());
//        if (getWork() != null) {
//        	dataOutput.writeBoolean(true);
//        	dataOutput.writeUTF(work.getClass().getName());
//            work.writeExternal(dataOutput);
//        } else {
//        	dataOutput.writeBoolean(false);
//        }
    }

    public int getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(int workStatus) {
        this.workStatus = workStatus;
    }

    public final  byte [] toBytes() throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeUTF(getWorkId());
        dos.writeLong(getScheduledTime());
        dos.writeInt(getWorkStatus());
        dos.writeInt(buf.length);
        dos.write(buf);
        dos.flush();
        return bos.toByteArray();
        //SerializableHelper.writeSerializableLite(dataOutput, work);
    }

    public final  WorkTuple fromBytes(byte [] buf) throws Exception{
        WorkTuple tuple = new WorkTuple();
        ByteArrayInputStream bos = new ByteArrayInputStream(buf);
        DataInputStream dos = new DataInputStream(bos);
        tuple.setWorkId(dos.readUTF());
        tuple.setScheduledTime(dos.readLong());
        tuple.setWorkStatus(dos.readInt());
        int len=dos.readInt();
        tuple.buf = new byte [len];
        dos.read(tuple.buf);
        dos.close();
        bos.close();
        return tuple;
    }

    public final static long extractScheduledTime(byte [] buf) throws Exception{
        ByteArrayInputStream bos = new ByteArrayInputStream(buf);
        DataInputStream dos = new DataInputStream(bos);
        long ret= dos.readLong();
        dos.close();
        return ret;
    }

    public String getWorkQueue() {
        return workQueue;
    }

    public void setWorkQueue(String workQueue) {
        this.workQueue = workQueue;
    }
}
