package com.tibco.cep.dashboard.psvr.plugin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

class UnifiedMALElementResolver {

	static Logger LOGGER;

	static ExceptionHandler EXCEPTION_HANDLER;

	static MessageGenerator MESSAGE_GENERATOR;

	static PlugIn INTERNAL_PLUGIN;

	private ResolverType type;

	protected List<IResolver> resolvers;

	private Map<String, IResolver> optimizedResolverCache;

	private IResolver defaultResolver;

	UnifiedMALElementResolver(ResolverType type, boolean cacheResolvers) {
		this.type = type;
		this.resolvers = new LinkedList<IResolver>();
		if (cacheResolvers == true) {
			optimizedResolverCache = new HashMap<String, IResolver>();
		}
		defaultResolver = INTERNAL_PLUGIN.getResolver(type);
	}

    public void loadResolvers() {
    	List<PlugIn> plugins = PluginFinder.getInstance().getPlugins();
    	for (PlugIn plugIn : plugins) {
    		IResolver resolver = plugIn.getResolver(type);
			if (resolver != null) {
				resolvers.add(resolver);
			}
			else {
				LOGGER.log(Level.DEBUG, plugIn.getDescriptiveName()+" did not provide a resolver for "+type);
			}
		}
    	if (resolvers.isEmpty() == true) {
    		LOGGER.log(Level.WARN, "No resolver were found for "+type+"...");
    	}
    }

    IResolver resolve(MALElement element) throws PluginException {
    	String elementDefinitionType = element.getDefinitionType();
		if (optimizedResolverCache != null) {
			IResolver resolver = optimizedResolverCache.get(elementDefinitionType);
			if (resolver != null) {
				return resolver;
			}
		}
		for (IResolver potentialResolver : resolvers) {
			if (potentialResolver.isAcceptable(element) == true) {
				if (optimizedResolverCache != null) {
					optimizedResolverCache.put(elementDefinitionType, potentialResolver);
				}
				if (LOGGER.isEnabledFor(Level.DEBUG) == true){
	    			LOGGER.log(Level.DEBUG,potentialResolver+" of  "+PluginFinder.getInstance().getPluginById(potentialResolver.getPlugInID()).getDescriptiveName()+" has accepted "+element);
				}
				return potentialResolver;
			}
		}
    	if (defaultResolver == null || defaultResolver.isAcceptable(element) == false) {
    		LOGGER.log(Level.WARN, "No "+type+" match found for "+element+"...");
    		throw new PluginException("No "+type+" match found for "+element+"...");
    	}
		if (LOGGER.isEnabledFor(Level.DEBUG) == true){
			LOGGER.log(Level.DEBUG,defaultResolver+" of  "+INTERNAL_PLUGIN.getDescriptiveName()+" has accepted "+element);
		}
    	return defaultResolver;
    }

}