package com.tibco.cep.dashboard.management;

/**
 * @author anpatil
 * 
 */
public interface ServiceListener {

	public void postInit(Service service);

	public void postStart(Service service);

	public void preStop(Service service);

	public void prePause(Service controllable);

	public void preUnpause(Service controllable);

}
