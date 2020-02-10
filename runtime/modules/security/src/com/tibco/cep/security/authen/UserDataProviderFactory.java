/**
 *
 */
package com.tibco.cep.security.authen;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.security.dataprovider.IUserDataProvider;
import com.tibco.cep.security.dataprovider.impl.FileUserDataProvider;
import com.tibco.cep.security.dataprovider.impl.LDAPUserDataProvider;
import com.tibco.cep.security.dataprovider.impl.OpenLDAPUserDataProvider;

/**
 * @author aathalye
 *
 */
public class UserDataProviderFactory {

	private static final String BE_AUTH_TYPE = "be.auth.type";

	public static final String USER_DATA_PROVIDER_TYPE_LDAP = "ldap";

	public static final String USER_DATA_PROVIDER_TYPE_FILE = "file";

	public static final UserDataProviderFactory INSTANCE = new UserDataProviderFactory();

	private final Map<String, IUserDataProvider> providers = new HashMap<String, IUserDataProvider>();

    private final ReentrantLock providerLock = new ReentrantLock();

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(UserDataProviderFactory.class);

	public IUserDataProvider getProvider() throws Exception {
        //getCurrentRuleSession throws NPE for Cache Servers
        Properties properties = System.getProperties();
        String providerType = (properties != null) ? properties.getProperty(BE_AUTH_TYPE) : null;

		if (providerType == null) {
			providerType = USER_DATA_PROVIDER_TYPE_FILE;
            LOGGER.log(Level.INFO, "No %s specified, defaulting to %s", BE_AUTH_TYPE, USER_DATA_PROVIDER_TYPE_FILE);
		}
		else if (providerType.trim().length() == 0) {
			providerType = USER_DATA_PROVIDER_TYPE_FILE;
            LOGGER.log(Level.INFO, "Invalid %s specified, defaulting to %s", BE_AUTH_TYPE, USER_DATA_PROVIDER_TYPE_FILE);
		}
        IUserDataProvider provider = null;

        providerLock.lock();
        try {
            providerType = providerType.intern();
            provider = providers.get(providerType);
            if (provider == null) {
                if (USER_DATA_PROVIDER_TYPE_LDAP == providerType) {
                	String ldapType = (properties != null) ? properties.getProperty(LDAPUserDataProvider.BE_AUTH_LDAP_TYPE) : null;
                	if (LDAPUserDataProvider.LDAP_PROVIDER_TYPE_OPENLDAP.equalsIgnoreCase(ldapType)) {
                		provider = new OpenLDAPUserDataProvider();
                	} else {
                		provider = new LDAPUserDataProvider();
                	}
                    providers.put(providerType, provider);
                    provider.init(properties);
                } else if (USER_DATA_PROVIDER_TYPE_FILE == providerType) {
                    provider = new FileUserDataProvider();
                    providers.put(providerType, provider);
                    provider.init(properties);
                } else {
                    throw new Exception("Unknown " + BE_AUTH_TYPE + " specified");
                }
            }
        } finally {
            providerLock.unlock();
        }
		return provider;
	}

	public void addProvider(final String providerType, final IUserDataProvider provider) {
        providerLock.lock();
        try {
		    providers.put(providerType.intern(), provider);
        } finally {
            providerLock.unlock();
        }
	}

	public void addProvider(final String providerType, final String providerClass) {
		Class<?> clazz;
		try {
			clazz = Thread.currentThread().getContextClassLoader().loadClass(providerClass);
			if (!(clazz.isAssignableFrom(IUserDataProvider.class))) {
				throw new IllegalArgumentException("Provider class should be of type IUserDataProvider");
			}
			IUserDataProvider provider = (IUserDataProvider) clazz.newInstance();
			synchronized (providers) {
				if (providers.containsKey(providerType)) {
					throw new IllegalArgumentException("The specified provider type already maps to a provider class");
				}
				addProvider(providerType, provider);
			}
		} catch (ClassNotFoundException cfe) {
			throw new IllegalArgumentException(String.format("%s not found", providerClass), cfe);
		} catch (IllegalAccessException iae) {
			throw new IllegalArgumentException(String.format("%s is not accessible", providerClass), iae);
		} catch (InstantiationException ite) {
			throw new IllegalArgumentException(String.format("%s is not instantiable", providerClass), ite);
		}
	}
}
