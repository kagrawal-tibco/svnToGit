package com.tibco.cep.dashboard.naming;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.CompoundName;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * @author anpatil
 * 
 */
abstract class AbstractSynNamingContext implements NamingContext {

	protected Map<String, String> supportedServices; 
	
	protected Hashtable<String, Object> envoirenment;

	private Properties compoundNameProperties;

	private Map<Name, Object> objectCache;

	protected AbstractSynNamingContext(Hashtable<?, ?> envoirenment,Map<String, String> supportedServices) throws NamingException {
		this(envoirenment, supportedServices, true);
	}

	protected AbstractSynNamingContext(Hashtable<?, ?> envoirenment, Map<String, String> supportedServices, boolean cacheObjects) throws NamingException {
		compoundNameProperties = new Properties();
		compoundNameProperties.put("jndi.syntax.direction", "left_to_right");
		compoundNameProperties.put("jndi.syntax.separator", "/");
		compoundNameProperties.put("jndi.syntax.ignorecase", "true");
		compoundNameProperties.put("jndi.syntax.trimblanks", "true");
		this.supportedServices = new HashMap<String, String>(supportedServices);
		if (cacheObjects == true) {
			objectCache = new HashMap<Name, Object>();
		}
		this.envoirenment = new Hashtable<String, Object>();
		for (Object key : envoirenment.keySet()) {
			Object value = envoirenment.get(key);
			this.envoirenment.put((String) key,value);
		}
	}

	public Object addToEnvironment(String propName, Object propVal) throws NamingException {
		return envoirenment.put(propName, propVal);
	}

	public Object removeFromEnvironment(String propName) throws NamingException {
		return envoirenment.remove(propName);
	}

	public Hashtable<?, ?> getEnvironment() throws NamingException {
		return envoirenment;
	}

	public void bind(Name name, Object obj) throws NamingException {
		throw new UnsupportedOperationException("bind");
	}

	public void bind(String name, Object obj) throws NamingException {
		throw new UnsupportedOperationException("bind");
	}

	public void rebind(Name name, Object obj) throws NamingException {
		throw new UnsupportedOperationException("rebind");
	}

	public void rebind(String name, Object obj) throws NamingException {
		throw new UnsupportedOperationException("rebind");
	}

	public void unbind(Name name) throws NamingException {
		throw new UnsupportedOperationException("unbind");
	}

	public void unbind(String name) throws NamingException {
		throw new UnsupportedOperationException("unbind");
	}

	public Name composeName(Name name, Name prefix) throws NamingException {
		CompoundName compoundName = new CompoundName(prefix.toString(), compoundNameProperties);
		compoundName.add(name.toString());
		return compoundName;
	}

	public String composeName(String name, String prefix) throws NamingException {
		CompoundName compoundName = new CompoundName(prefix, compoundNameProperties);
		compoundName.add(name);
		return compoundName.toString();
	}

	public Context createSubcontext(Name name) throws NamingException {
		throw new UnsupportedOperationException("createSubcontext");
	}

	public Context createSubcontext(String name) throws NamingException {
		throw new UnsupportedOperationException("createSubcontext");
	}

	public void destroySubcontext(Name name) throws NamingException {
		throw new UnsupportedOperationException("destroySubcontext");
	}

	public void destroySubcontext(String name) throws NamingException {
		throw new UnsupportedOperationException("destroySubcontext");
	}

	public String getNameInNamespace() throws NamingException {
		throw new UnsupportedOperationException("getNameInNamespace");
	}

	public NameParser getNameParser(Name name) throws NamingException {
		return new SynNameParserImpl(name.toString());
	}

	public NameParser getNameParser(String name) throws NamingException {
		return new SynNameParserImpl(name);
	}

	public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
		return new NamingEnumeration<NameClassPair>() {

			public void close() throws NamingException {
			}

			public boolean hasMore() throws NamingException {
				return false;
			}

			public NameClassPair next() throws NamingException {
				return null;
			}

			public boolean hasMoreElements() {
				return false;
			}

			public NameClassPair nextElement() {
				return null;
			}

		};
	}

	public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
		return new NamingEnumeration<NameClassPair>() {

			public void close() throws NamingException {
			}

			public boolean hasMore() throws NamingException {
				return false;
			}

			public NameClassPair next() throws NamingException {
				return null;
			}

			public boolean hasMoreElements() {
				return false;
			}

			public NameClassPair nextElement() {
				return null;
			}

		};
	}

	public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
		return new NamingEnumeration<Binding>() {

			public void close() throws NamingException {
			}

			public boolean hasMore() throws NamingException {
				return false;
			}

			public Binding next() throws NamingException {
				return null;
			}

			public boolean hasMoreElements() {
				return false;
			}

			public Binding nextElement() {
				return null;
			}

		};
	}

	public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
		return new NamingEnumeration<Binding>() {

			public void close() throws NamingException {
			}

			public boolean hasMore() throws NamingException {
				return false;
			}

			public Binding next() throws NamingException {
				return null;
			}

			public boolean hasMoreElements() {
				return false;
			}

			public Binding nextElement() {
				return null;
			}

		};
	}

	public Object lookup(String name) throws NamingException {
		CompoundName compoundName = new CompoundName(name, compoundNameProperties);
		return lookup(compoundName);
	}

	public Object lookup(Name name) throws NamingException {
		name = validateName(name);
		String serviceName = name.get(0);
		if (objectCache != null) {
			Object instance = objectCache.get(name);
			if (instance == null) {
				instance = createInstance(name, serviceName);
				objectCache.put(name, instance);
			}
			return instance;
		} else {
			return createInstance(name, serviceName);
		}
	}

	protected Name validateName(Name name) throws NamingException {
		if (name.size() != 1) {
			throw new NamingException("Invalid name [" + name.toString() + "]");
		}
		// check management domain name
		//validateManagementDomainName(name);
		// check context name
		//validateContextName(name);
		// check service name
		validateServiceName(name);
		return name;
	}

	protected void validateManagementDomainName(Name name) throws InvalidNameException, NamingException {
	}

	protected void validateContextName(Name name) throws NamingException {
	}
	
	protected void validateServiceName(Name name) throws NamingException {
		if (supportedServices.containsKey(name.get(0)) == false){
			throw new NamingException("Unsupported service request in ["+name+"]");
		}
	}	

	protected abstract Object createInstance(Name name, String serviceName) throws NamingException;

	public Object lookupLink(Name name) throws NamingException {
		throw new UnsupportedOperationException("lookupLink");
	}

	public Object lookupLink(String name) throws NamingException {
		throw new UnsupportedOperationException("lookupLink");
	}

	public void rename(Name oldName, Name newName) throws NamingException {
		throw new UnsupportedOperationException("rename");

	}

	public void rename(String oldName, String newName) throws NamingException {
		throw new UnsupportedOperationException("rename");

	}

	public void close() throws NamingException {
	}

	private final class SynNameParserImpl implements NameParser {

		private String prefix;

		SynNameParserImpl(String prefix) {
			this.prefix = prefix;
		}

		public Name parse(String name) throws NamingException {
			CompoundName compoundName = new CompoundName(prefix, compoundNameProperties);
			compoundName.add(name);
			validateName(compoundName);
			return compoundName;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof SynNameParserImpl) {
				return ((SynNameParserImpl) obj).prefix.equals(prefix);
			}
			return false;
		}

	}

}
