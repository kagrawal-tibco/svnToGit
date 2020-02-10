package com.tibco.rta.service.persistence.nostore;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.service.persistence.SessionPersistenceService;

public class NullSessionPersistenceProvider extends	AbstractStartStopServiceImpl implements SessionPersistenceService {

	@Override
	public void init(Properties configuration) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void suspend() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void persist(ServerSession<?> serverSession) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void cleanup(ServerSession<?> serverSession) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public ServerSession<?>[] recover() throws Exception {
		List<ServerSession> recoveredSessionList = new ArrayList();
		return recoveredSessionList.toArray(new ServerSession[0]);
	}

}
