package com.tibco.cep.dashboard.psvr.plugin;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author anpatil
 *
 */
public final class PluginFinder {
	
	private static final String DEFAULT_PLUGIN = "com.tibco.cep.dashboard.plugin.beviews.BEViewsPlugIn";
	
	private static PluginFinder instance = null;
	
	private Map<String,PlugIn> indexedPlugIns;
	
	private List<PlugIn> plugIns;

	public synchronized static PluginFinder getInstance() {
		if (instance == null) {
			instance = new PluginFinder();
		}
		return instance;
	}
	
	private ExceptionHandler exceptionHandler;
	private MessageGenerator messageGenerator;

    void init(Logger logger,ExceptionHandler exceptionHandler, MessageGenerator messageGenerator, Properties properties){
    	this.exceptionHandler = exceptionHandler;
    	this.messageGenerator = messageGenerator;
    }

	private PluginFinder() {
		indexedPlugIns = new HashMap<String,PlugIn>();
		String plugInClassName = DEFAULT_PLUGIN;
		try {
			PlugIn defaultPlugin = (PlugIn) Class.forName(plugInClassName).newInstance();
			indexedPlugIns.put(defaultPlugin.getId(), defaultPlugin);
			plugIns = new LinkedList<PlugIn>(indexedPlugIns.values());
			//INFO add logic to skip plug-in whose name contains a space when we implement external plug-in loading 
		} catch (InstantiationException e) {
			exceptionHandler.handleException(messageGenerator.getMessage("plugin.instantiation.failure", new MessageGeneratorArgs(e,plugInClassName)), e, Level.WARN);
		} catch (IllegalAccessException e) {
			exceptionHandler.handleException(messageGenerator.getMessage("plugin.illegalaccess.failure", new MessageGeneratorArgs(e,plugInClassName)), e, Level.WARN);
		} catch (ClassNotFoundException e) {
			exceptionHandler.handleException(messageGenerator.getMessage("plugin.classnotfound.failure", new MessageGeneratorArgs(e,plugInClassName)), e, Level.WARN);
		}
		Collections.sort(plugIns, new Comparator<PlugIn>() {

			public int compare(PlugIn o1, PlugIn o2) {
				return o1.getStartOrder() - o2.getStartOrder();
			}
		});			
	}
	
	List<PlugIn> getPlugins(){
		return plugIns;
	}
	
	public PlugIn getPluginById(String id){
		return indexedPlugIns.get(id);
	}	

}