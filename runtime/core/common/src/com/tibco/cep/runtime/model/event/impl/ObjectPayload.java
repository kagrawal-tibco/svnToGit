package com.tibco.cep.runtime.model.event.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.tibco.be.util.BEStringUtilities;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.values.XsBase64Binary;
import com.tibco.xml.data.primitive.values.XsString;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 22, 2006
 * Time: 1:49:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectPayload implements EventPayload {


    static {
        try {
            PayloadFactoryImpl.registerType(ObjectPayload.class, 2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object payload;

    /**
     * Used while stringifying byte[]
     */
    private String encoding;

    public ObjectPayload(TypeManager.TypeDescriptor eventDescriptor, byte[] buf) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(buf);
        ObjectInputStream ois = new ObjectInputStream(bis);
        payload = ois.readObject();
    }

    public ObjectPayload(Object payload) {
        this (payload, null);
    }

    public ObjectPayload(Object payload, String encoding) {
        this.payload = payload;
        this.encoding = encoding;
        if (this.encoding == null) {
        	this.encoding = "UTF-8";
        }
        
    }

    public void toXiNode(XiNode root) {
        if (payload == null) return;
        try {
            root.appendElement(ExpandedName.makeName(PAYLOAD_PROPERTY), new XsString(toString()));
        } catch (Exception e) {
            e.printStackTrace();
            //todo - what to do?
        }
    }

    public String toString() {
        if (payload == null)
            return null;
        else if (payload instanceof byte[]) {
            //Stringify it
            //Does not help much to run toString() on byte[]
            /**
             * Encoding is absolutely vital here because of byte[]
             * being passed to this constructor in many cases
             * and when you stringify it, it is incorrect to
             * encode it with UTF-8 always when the actual byte[]
             * encoding could be anything else.
             * See BE-10544
             */
            return BEStringUtilities.convertByteArrayToString(payload, encoding);
        } else {
            return payload.toString();
        }
    }

    public byte[] toBytes() throws Exception {
        if (payload == null)
            return new byte[0];
        else if (payload instanceof byte[]) {
            return (byte[]) payload;
        } else {
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(ba);
            os.writeObject(payload);
            byte[] ret = ba.toByteArray();
            os.close();
            ba.close();
            return ret;
        }
    }

    public Object getObject() {
        return payload;
    }
}
