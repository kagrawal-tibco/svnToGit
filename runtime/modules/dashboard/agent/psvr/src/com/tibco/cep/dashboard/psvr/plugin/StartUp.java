package com.tibco.cep.dashboard.psvr.plugin;


public abstract class StartUp extends PlugInFunctionHelper {
	
	protected StartUp(PlugIn plugIn){
		super(plugIn);
	}
	
	public abstract void startup() throws PluginException;

}