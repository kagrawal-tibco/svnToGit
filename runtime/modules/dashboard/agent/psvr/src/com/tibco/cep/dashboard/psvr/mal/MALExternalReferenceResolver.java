package com.tibco.cep.dashboard.psvr.mal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.mal.model.MALExternalReference;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandler;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandlerFactory;
import com.tibco.cep.dashboard.psvr.plugin.IResolver;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.security.SecurityToken;

public final class MALExternalReferenceResolver extends AbstractHandlerFactory {

	private static MALExternalReferenceResolver instance = new MALExternalReferenceResolver();

	static final synchronized MALExternalReferenceResolver getInstance() {
		return instance;
	}

	private Map<Class<? extends AbstractHandler>, ExternalReferenceProcessor> handlersCache;

	private MALExternalReferenceResolver() {
		super("externalreferenceresolver", "External Reference Resolver", ResolverType.EXTERNAL_REFERENCE_HANDLER, false);
		handlersCache = new HashMap<Class<? extends AbstractHandler>, ExternalReferenceProcessor>();
	}

	void shutdown(){
		handlersCache.clear();
		instance = null;
	}

	private ExternalReferenceProcessor getHandler(MALExternalReference reference) throws PluginException {
		IResolver resolver = instance.getResolver(reference);
		if (resolver == null) {
			throw new PluginException("No resolver found for "+reference+"'s "+ResolverType.EXTERNAL_REFERENCE_HANDLER);
		}
		List<Class<? extends AbstractHandler>> handlerclazzs = resolver.resolve(reference);
		if (handlerclazzs.size() > 1) {
			throw new IllegalArgumentException("Too many handlers found for "+reference+"'s "+ResolverType.EXTERNAL_REFERENCE_HANDLER);
		}
		Class<? extends AbstractHandler> handlerclazz = handlerclazzs.get(0);
		ExternalReferenceProcessor externalReferenceHandler = handlersCache.get(handlerclazz);
		if (externalReferenceHandler == null) {
			synchronized (handlersCache) {
				externalReferenceHandler = handlersCache.get(handlerclazz);
				if (externalReferenceHandler == null) {
					externalReferenceHandler = (ExternalReferenceProcessor) instantiateHandler(handlerclazz);
					initializeHandler(resolver.getPlugInID(), externalReferenceHandler);
					handlersCache.put(handlerclazz, externalReferenceHandler);
				}
			}
		}
		return externalReferenceHandler;
	}


	public static Object resolveReference(SecurityToken token, Object object) throws PluginException {
		if (object instanceof MALExternalReference) {
			MALExternalReference reference = (MALExternalReference) object;
			ExternalReferenceProcessor handler = instance.getHandler(reference);
			return handler.resolveReference(token, reference);
		}
		throw new IllegalArgumentException("Unknown type for reference resolution [" + object.getClass().getName() + "]");
	}

	public static Object[] resolveReferences(SecurityToken token, Object[] objects) throws PluginException {
		Object[] references = new Object[objects.length];
		for (int i = 0; i < objects.length; i++) {
			references[i] = resolveReference(token, objects[i]);
		}
		return references;
	}

	public static MALFieldMetaInfo resolveFieldReference(SecurityToken token, Object sourceField) throws PluginException {
		if (sourceField instanceof MALExternalReference) {
			MALExternalReference reference = (MALExternalReference) sourceField;
			ExternalReferenceProcessor handler = instance.getHandler(reference);
			return handler.resolveFieldReference(token, reference);
		}
		throw new IllegalArgumentException("Unknown type for reference resolution [" + sourceField.getClass().getName() + "]");
	}

	public static MALFieldMetaInfo[] resolveFieldReferences(SecurityToken token, Object[] sourceFields) throws PluginException {
		if (sourceFields == null) {
			return null;
		}
		MALFieldMetaInfo[] fields = new MALFieldMetaInfo[sourceFields.length];
		for (int i = 0; i < sourceFields.length; i++) {
			fields[i] = resolveFieldReference(token, sourceFields[i]);
		}
		return fields;
	}

}
