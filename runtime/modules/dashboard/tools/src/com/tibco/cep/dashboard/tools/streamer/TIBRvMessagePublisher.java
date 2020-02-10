package com.tibco.cep.dashboard.tools.streamer;

import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvRvdTransport;

public class TIBRvMessagePublisher {
	
	protected String service;
	protected String network;
	protected String daemon;	
	
	protected TibrvRvdTransport transport;
	
	public TIBRvMessagePublisher(String service, String network, String daemon) throws TibrvException {
		this.service = service;
		this.network = network;
		this.daemon = daemon;
		initTIBRv();
	}
	
	protected void initTIBRv() throws TibrvException {
		Tibrv.open(Tibrv.IMPL_NATIVE);
		transport = new TibrvRvdTransport(service, network, daemon);
	}

	public TibrvMsg sendMessage(String subject, String[] fieldNames, DataType[] dataTypes, String[] values) throws TibrvException {
		if (fieldNames.length != dataTypes.length){
			throw new IllegalArgumentException("Incompatible lengths for fieldnames and data types");
		}
		if (fieldNames.length != values.length){
			throw new IllegalArgumentException("Incompatible lengths for fieldnames and values");
		}
		TibrvMsg msg = new TibrvMsg();
		msg.setSendSubject(subject);
		for (int i = 0; i < fieldNames.length; i++) {
			String fieldName = fieldNames[i];
			msg.add(fieldName, dataTypes[i].valueOf(values[i]));
		}
		transport.send(msg);
		return msg;
	}

}