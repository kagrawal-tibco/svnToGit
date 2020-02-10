/**
 * 
 */
package com.tibco.be.bemm.functions;

import COM.TIBCO.hawk.console.hawkeye.AgentManager;
import COM.TIBCO.hawk.talon.DataElement;
import COM.TIBCO.hawk.talon.MethodDescriptor;
import COM.TIBCO.hawk.talon.MethodInvocation;
import COM.TIBCO.hawk.talon.MethodSubscription;
import COM.TIBCO.hawk.talon.MicroAgentData;
import COM.TIBCO.hawk.talon.MicroAgentDescriptor;
import COM.TIBCO.hawk.talon.MicroAgentException;
import COM.TIBCO.hawk.talon.MicroAgentID;
import COM.TIBCO.hawk.talon.Subscription;
import COM.TIBCO.hawk.talon.SubscriptionHandler;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author anpatil
 *
 */
class HAWKMethodInvoker implements SubscriptionHandler {
	
	private Logger logger;
	
	private AgentManager agentManager;
	
	private MicroAgentID agentID;
	
	private String methodName;
	
	private Object returnedData;
	
	private MethodInvocation methodInvocation;
	
	private MethodSubscription methodSubscription;

	private MicroAgentDescriptor agentDescriptor;

	private MethodDescriptor methodDescriptor;

	private boolean isAsync;
	
	private MicroAgentException initializationException;

	HAWKMethodInvoker(Logger logger, AgentManager agentManager, MicroAgentID agentID, String methodName, DataElement[] arguments, long interval) {
		super();
		this.logger = logger;
		this.agentManager = agentManager;
		this.agentID = agentID;
		this.methodName = methodName;
		try {
			agentDescriptor = agentManager.describe(agentID);
			methodDescriptor = findMethodDescriptor(agentDescriptor.getMethodDescriptors(),methodName);
			if (methodDescriptor == null){
				//log a warning and leave
                logger.log(Level.DEBUG, "Could not find MethodDescriptor for "+agentID+"/"+methodName);
				return;
			}
			isAsync = methodDescriptor.isAsync();
			if (isAsync == true){
				this.methodSubscription = new MethodSubscription(methodName,arguments,interval);
				logger.log(Level.DEBUG, "Subscribing for "+agentID+"/"+methodName+" with a "+interval+" msec(s) interval...");
				agentManager.subscribe(agentID, methodSubscription, this, null);	
			}
			else {
				this.methodInvocation = new MethodInvocation(methodName,arguments);
			}
		} catch (MicroAgentException e) {
			initializationException = e;
		}
	}

	private MethodDescriptor findMethodDescriptor(MethodDescriptor[] methodDescriptors, String methodName) {
		for (MethodDescriptor methodDescriptor : methodDescriptors) {
			if (methodDescriptor.getName().equals(methodName) == true){
				return methodDescriptor;
			}
		}
		return null;
	}

	public void onData(Subscription subscription, MicroAgentData agentData) {
		logger.log(Level.DEBUG, "Received data for "+agentID+"/"+methodName+"...");
		synchronized (this) {
			this.returnedData = agentData.getData();
		}
	}

	public void onError(Subscription subscription, MicroAgentException agentData) {
		//do nothing
	}

	public void onErrorCleared(Subscription subscription) {
		//do nothing
	}

	public void onTermination(Subscription subscription, MicroAgentException agentData) {
		//do nothing
	}
	
	protected MicroAgentData invoke() throws MicroAgentException {
		if (initializationException != null){
			MicroAgentException e = initializationException;
			initializationException = null;
			throw e;
		}
		if (isAsync == true) {
			synchronized (this) {
				return new MicroAgentData(agentID, this.returnedData);
			}
		}
		logger.log(Level.DEBUG, "Invoking "+agentID+"/"+methodName+"...");
		return agentManager.invoke(agentID, methodInvocation);
	}

	String getMethodName() {
		return methodName;
	}
}