package com.tibco.cep.dashboard.session;

import com.tibco.cep.runtime.session.RuleSession;

public interface DashboardSession extends RuleSession {

	public void suspend();

	public void resume();

}