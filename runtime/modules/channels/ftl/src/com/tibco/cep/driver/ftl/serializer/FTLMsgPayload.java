package com.tibco.cep.driver.ftl.serializer;

import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.ftl.Message;
import com.tibco.xml.datamodel.XiNode;

public class FTLMsgPayload implements EventPayload {

	private Message msg;
	
	
	public FTLMsgPayload(Message msg) {
		this.msg = msg;
	}
	
	@Override
	public Object getObject() {
		// TODO Auto-generated method stub
		return msg;
	}

	@Override
	public byte[] toBytes() throws Exception {
		// TODO Auto-generated method stub
        int length = 2048;
        byte[] bytes = new byte[length];
        length = msg.writeToByteArray(bytes);
        if(length > bytes.length)
        {
            bytes = new byte[length];
            length = msg.writeToByteArray(bytes);
        }
		return bytes;
	}

	@Override
	public void toXiNode(XiNode arg0) {
		// TODO Auto-generated method stub
		
	}

}
