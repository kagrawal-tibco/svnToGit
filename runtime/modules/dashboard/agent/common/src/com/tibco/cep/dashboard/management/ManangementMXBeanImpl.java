package com.tibco.cep.dashboard.management;

import com.tibco.cep.dashboard.session.DashboardSession;

public class ManangementMXBeanImpl implements ManagementMXBean {

	private DashboardSession session;

	public ManangementMXBeanImpl(DashboardSession session) {
		if (session == null) {
			throw new IllegalArgumentException();
		}
		this.session = session;
	}

	@Override
	public void suspend() {
		session.suspend();
	}

	@Override
	public void resume() {
		session.resume();
	}

	@Override
	public void stop() {
		session.stopAndShutdown();
	}
}