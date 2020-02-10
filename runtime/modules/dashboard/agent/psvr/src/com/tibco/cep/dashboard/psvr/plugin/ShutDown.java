package com.tibco.cep.dashboard.psvr.plugin;


public abstract class ShutDown extends PlugInFunctionHelper {
	
	protected ShutDown(PlugIn plugIn){
		super(plugIn);
	}
	
	public abstract void shutdown() throws PluginException;

}