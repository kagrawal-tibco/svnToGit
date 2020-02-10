package com.tibco.cep.dashboard.naming;

import java.util.Hashtable;
import java.util.Map;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NamingException;

import com.tibco.cep.dashboard.management.Client;

/**
 * @author anpatil
 * 
 */
public final class LocalSynNamingContext extends AbstractSynNamingContext {


	protected LocalSynNamingContext(Hashtable<?, ?> envoirenment, Map<String, String> supportedServices) throws NamingException {
		super(envoirenment, supportedServices);
	}

	@Override
	protected Object createInstance(Name name, String serviceName) throws NamingException {
		String implClass = supportedServices.get(serviceName);
		try {
			Class<? extends Client> apiProvider = Class.forName(implClass).asSubclass(Client.class);
			return apiProvider.newInstance();
		} catch (ClassNotFoundException e) {
			throw new NamingException(implClass+" not found for "+serviceName+" using "+name+" in local mode");
		} catch (InstantiationException e) {
			throw new NamingException("Could not instantiate "+implClass+" for "+serviceName+" using "+name+" in local mode");
		} catch (IllegalAccessException e) {
			throw new NamingException("Could not access "+implClass+" for "+serviceName+" using "+name+" in local mode");
		}
	}

	@Override
	protected void validateContextName(Name name) throws NamingException {
		// do nothing
	}

	@Override
	protected void validateManagementDomainName(Name name) throws InvalidNameException, NamingException {
		// do nothing
	}

}