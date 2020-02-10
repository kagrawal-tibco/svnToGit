package com.tibco.cep.driver.ancillary.tcp;

import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.values.XsBase64Binary;
import com.tibco.xml.datamodel.XiNode;

/*
* Author: Ashwin Jayaprakash Date: Mar 20, 2009 Time: 2:34:35 PM
*/
public class RawByteArrayPayload implements EventPayload {
    protected byte[] bytes;

    public RawByteArrayPayload(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * @param root
     */
    public void toXiNode(XiNode root) {
    	if (bytes == null) return;
        try {
            root.appendElement(ExpandedName.makeName(PAYLOAD_PROPERTY), new XsBase64Binary(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] toBytes() throws Exception {
        return bytes;
    }

    /**
     * @return The raw byte array - {@link #toBytes()}.
     */
    public Object getObject() {
        return bytes;
    }

    @Override
    public String toString() {
        return new String(bytes);
    }
}
