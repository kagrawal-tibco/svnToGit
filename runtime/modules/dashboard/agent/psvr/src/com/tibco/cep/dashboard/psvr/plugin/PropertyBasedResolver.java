package com.tibco.cep.dashboard.psvr.plugin;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public abstract class PropertyBasedResolver extends BaseResolverImpl {
	
	protected Map<String,List<Class<? extends AbstractHandler>>> handlersMap;
	protected String propertyFileName;
	
	protected Properties properties;
	
	public PropertyBasedResolver(String plugInID,ResolverType resolverType,String propertyFileName) {
		super(plugInID,resolverType);
		this.propertyFileName = propertyFileName;
		handlersMap = new HashMap<String, List<Class<? extends AbstractHandler>>>();
	}
	
    @Override
    public void init(Logger logger,ExceptionHandler exceptionHandler,MessageGenerator messageGenerator) throws PluginException {
    	super.init(logger, exceptionHandler, messageGenerator);
		try {
			loadPropertyFile(propertyFileName);
			Enumeration<Object> keys = properties.keys();
			while (keys.hasMoreElements()) {
				String definitionType = (String) keys.nextElement();
				String[] handlerClassNames = properties.getProperty(definitionType).split(",");
				LinkedList<Class<? extends AbstractHandler>> handlerClassList = new LinkedList<Class<? extends AbstractHandler>>();
				for (String handlerClassName : handlerClassNames) {
					try {
						Class<? extends AbstractHandler> handlerClass = Class.forName(handlerClassName).asSubclass(AbstractHandler.class);
						handlerClassList.add(handlerClass);
					} catch (ClassNotFoundException e) {
						String message = "could not find "+handlerClassName+" for "+resolverType;
						exceptionHandler.handleException(message, e, Level.WARN);						
					}
				}
				handlersMap.put(definitionType, handlerClassList);
			}
		} catch (IOException e) {
			String message = "could not load "+propertyFileName+" for "+resolverType;
			exceptionHandler.handleException(message, e);
			throw new PluginException(message,e);
		} 
    }
    
    protected void loadPropertyFile(String propertyFileName) throws IOException{
        properties = new Properties();
        URL propertyLocationURL = PropertyBasedFactory.class.getResource("/"+propertyFileName);
        properties.load(propertyLocationURL.openStream());
    }    

	@Override
	public boolean isAcceptable(MALElement element) {
		String definitionType = element.getDefinitionType();
		return handlersMap.containsKey(definitionType);
	}

	@Override
	public List<Class<? extends AbstractHandler>> resolve(MALElement element) throws PluginException {
		return handlersMap.get(element.getDefinitionType());
	}

}