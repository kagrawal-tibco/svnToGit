package com.tibco.cep.runtime.session.impl;

/**
 * Created by IntelliJ IDEA.
 * User: bgokhale
 * To get notified of FT activation and deactivation events.
 */
public interface FTEventNotifier {
	public void activated ();
	public void deactivated();
}
