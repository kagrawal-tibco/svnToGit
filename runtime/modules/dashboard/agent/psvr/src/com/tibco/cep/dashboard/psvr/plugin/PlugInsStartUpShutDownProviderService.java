package com.tibco.cep.dashboard.psvr.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.management.Service;
import com.tibco.cep.kernel.service.logging.Level;

public class PlugInsStartUpShutDownProviderService extends Service {

	private PlugIn internalPlugin;

	private List<StartUp> startUps;

	private List<ShutDown> shutDowns;

	public PlugInsStartUpShutDownProviderService() {
		super("pluginsstartstop","Plugins Start Up And ShutDown Provider");
	}

	@Override
	protected void doInit() throws ManagementException {
		internalPlugin = PlugInsService.getInstance().getDefaultPlugin();
		List<PlugIn> plugIns = PluginFinder.getInstance().getPlugins();
		startUps = new ArrayList<StartUp>();
		if (internalPlugin.getStartUp() != null){
			startUps.add(internalPlugin.getStartUp());
		}
		shutDowns = new ArrayList<ShutDown>();
		if (internalPlugin.getShutdown() != null){
			shutDowns.add(internalPlugin.getShutdown());
		}
		for (PlugIn plugIn : plugIns) {
			if (plugIn.getStartUp() != null) {
				startUps.add(plugIn.getStartUp());
			}
			if (plugIn.getShutdown() != null){
				shutDowns.add(plugIn.getShutdown());
			}
		}
		Collections.reverse(shutDowns);
	}

	@Override
	protected void doStart() throws ManagementException {
		for (StartUp startUp : startUps) {
			try {
				if (logger.isEnabledFor(Level.DEBUG) == true){
					logger.log(Level.DEBUG,"Attempting to invoke "+startUp.getDescriptiveName()+"/startup");
				}
				startUp.startup();
			} catch (PluginException e) {
				String msg = messageGenerator.getMessage("plugin.startup.failure",new MessageGeneratorArgs(e,startUp.getDescriptiveName()+"/startup"));
				exceptionHandler.handleException(msg, e, Level.ERROR);
			}
		}
	}

	@Override
	protected boolean doStop() {
		for (ShutDown shutdown : shutDowns) {
			try {
				if (logger.isEnabledFor(Level.DEBUG) == true){
					logger.log(Level.DEBUG,"Attempting to invoke "+shutdown.getDescriptiveName()+"/shutdown");
				}
				shutdown.shutdown();
			} catch (PluginException e) {
				String msg = messageGenerator.getMessage("plugin.shutdown.failure",new MessageGeneratorArgs(e,shutdown.getDescriptiveName()+"/shutdown"));
				exceptionHandler.handleException(msg, e, Level.ERROR);
			}
		}
		return true;
	}

}