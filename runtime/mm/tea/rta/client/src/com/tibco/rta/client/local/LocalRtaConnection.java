package com.tibco.rta.client.local;

import java.util.Map;

import javax.naming.OperationNotSupportedException;

import com.tibco.rta.AbstractRtaConnection;
import com.tibco.rta.ConfigProperty;
import com.tibco.rta.RtaException;
import com.tibco.rta.RtaSession;
import com.tibco.rta.client.ServiceInvocationListener;
import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.impl.LocalRtaSession;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.service.transport.TransportTypes;

/**
 * @author vdhumal
 *
 */
public class LocalRtaConnection extends AbstractRtaConnection {

	@Override
	public synchronized RtaSession createSession(Map<ConfigProperty, PropertyAtom<?>> sessionProps) throws RtaException {
		DefaultRtaSession localSession = new LocalRtaSession(this, sessionProps);
        sessions.add(localSession);
		return localSession;
	}

	@Override
	public synchronized RtaSession createSession(String name, Map<ConfigProperty, PropertyAtom<?>> sessionProps)
			throws RtaException {
		DefaultRtaSession localSession = new LocalRtaSession(this, name, sessionProps);
        sessions.add(localSession);
		return localSession;
	}

	@Override
	public void close() throws RtaException {
		//No-Op
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ServiceResponse invokeService(String endpoint, String serviceOp, Map<String, String> properties, String payload) throws Exception {
		throw new OperationNotSupportedException();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ServiceResponse invokeService(String endpoint, String serviceOp, Map<String, String> properties, byte[] payload) throws Exception {
		throw new OperationNotSupportedException();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ServiceResponse invokeService(String endpoint, String serviceOp, Map<String, String> properties,
			String payload, ServiceInvocationListener factPublisherListener) throws Exception {
		throw new OperationNotSupportedException();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ServiceResponse invokeService(String endpoint, String serviceOp, Map<String, String> properties,
			byte[] payload, ServiceInvocationListener factPublisherListener) throws Exception {
		throw new OperationNotSupportedException();
	}

	@Override
	public TransportTypes getTransportType() {
		return null;
	}

	@Override
	public boolean shouldSendHeartbeat() {
		return false;
	}
	
}
