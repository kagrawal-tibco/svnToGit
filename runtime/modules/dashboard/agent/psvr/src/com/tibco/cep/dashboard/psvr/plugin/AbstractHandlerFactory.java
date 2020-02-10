package com.tibco.cep.dashboard.psvr.plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.ServiceDependent;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;

public abstract class AbstractHandlerFactory extends ServiceDependent {

	private ResolverType type;

	private UnifiedMALElementResolver elementResolver;

	private Map<String, AbstractHandler[]> optimizedCache;

	protected AbstractHandlerFactory(String name, String descriptiveName, ResolverType type) {
		this(name, descriptiveName, type, true);
	}

	protected AbstractHandlerFactory(String name, String descriptiveName, ResolverType type, boolean cacheResolvers) {
		super(name, descriptiveName);
		optimizedCache = new HashMap<String, AbstractHandler[]>();
		this.type = type;
		this.elementResolver = new UnifiedMALElementResolver(this.type, cacheResolvers);
		this.elementResolver.loadResolvers();
	}

	protected IResolver getResolver(MALElement element) throws PluginException {
		return elementResolver.resolve(element);
	}

	protected AbstractHandler[] getHandlers(MALElement element) throws PluginException {
		String elementDefinitionType = element.getDefinitionType();
		AbstractHandler[] handlers = optimizedCache.get(elementDefinitionType);
		if (handlers == null) {
			IResolver resolver = getResolver(element);
			List<Class<? extends AbstractHandler>> handlerClassList = resolver.resolve(element);
			if (handlerClassList != null && handlerClassList.isEmpty() == false) {
				handlers = instantiateHandlers(handlerClassList);
				initializeHandlers(resolver.getPlugInID(), handlers);
			}
			optimizedCache.put(elementDefinitionType, handlers);
		}
		return handlers;
	}

	protected AbstractHandler[] instantiateHandlers(List<Class<? extends AbstractHandler>> handlerClassList) throws PluginException {
		AbstractHandler[] handlers = new AbstractHandler[handlerClassList.size()];
		int i = 0;
		for (Class<? extends AbstractHandler> clazz : handlerClassList) {
			try {
				handlers[i] = clazz.newInstance();
				i++;
			} catch (InstantiationException e) {
				throw new PluginException("could not instantiate " + clazz.getName() + " for " + type, e);
			} catch (IllegalAccessException e) {
				throw new PluginException("could not access " + clazz.getName() + " for " + type, e);
			}
		}
		return handlers;
	}

	protected void initializeHandlers(String plugInID, AbstractHandler[] handlers) {
		PlugIn plugIn = findPlugIn(plugInID);
		for (AbstractHandler handler : handlers) {
			if (plugIn != null) {
				initializeHandlerProperties(handler, plugIn);
			}
			handler.init();
		}
	}

	protected AbstractHandler instantiateHandler(Class<? extends AbstractHandler> handlerClazz) throws PluginException {
		try {
			return handlerClazz.newInstance();
		} catch (InstantiationException e) {
			throw new PluginException("could not instantiate " + handlerClazz.getName() + " for " + type, e);
		} catch (IllegalAccessException e) {
			throw new PluginException("could not access " + handlerClazz.getName() + " for " + type, e);
		}
	}

	protected void initializeHandler(String plugInID, AbstractHandler handler) {
		PlugIn plugIn = findPlugIn(plugInID);
		if (plugIn != null) {
			initializeHandlerProperties(handler, plugIn);
		}
		handler.init();
	}

	protected PlugIn findPlugIn(String plugInID) {
		PlugIn plugin = PluginFinder.getInstance().getPluginById(plugInID);
		if (plugin == null) {
			if (PlugInsService.getInstance().getDefaultPlugin().getId().equals(plugInID) == true) {
				return PlugInsService.getInstance().getDefaultPlugin();
			}
		}
		return plugin;
	}

	private void initializeHandlerProperties(AbstractHandler handler, PlugIn plugIn) {
		handler.logger = LoggingService.getChildLogger(plugIn.logger, type.toString());
		handler.exceptionHandler = new ExceptionHandler(handler.logger);
		handler.messageGenerator = new MessageGenerator(plugIn.getName(), handler.exceptionHandler);
		handler.plugIn = plugIn;
		handler.properties = plugIn.properties;
	}

	protected AbstractHandler getHandler(MALElement element) throws PluginException {
		AbstractHandler[] handlers = getHandlers(element);
		return (handlers != null && handlers.length != 0) ? handlers[0] : null;
	}
}