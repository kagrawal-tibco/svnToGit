package com.tibco.tea.agent.be.provider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.tibco.tea.agent.api.TeaObject;

/**
 * Created with IntelliJ IDEA. User: mgharat Date: 7/11/14 Time: 4:19 PM To
 * change this template use File | Settings | File Templates.
 */
public class ObjectCacheProvider {

	private static ObjectCacheProvider objectCacheProvider;

	private Map<String, ObjectProvider<? extends TeaObject>> providerStore;

	private ObjectCacheProvider() {
		providerStore = new HashMap<>();
	}

	public static synchronized ObjectCacheProvider getInstance() {
		if (null == objectCacheProvider) {
			objectCacheProvider = new ObjectCacheProvider();
		}
		return objectCacheProvider;
	}

	public ObjectProvider<? extends TeaObject> getProvider(String provider) {
		return providerStore.get(provider);
	}

	public void addProvider(String key, ObjectProvider<? extends TeaObject> value) {
		providerStore.put(key, value);
	}

	public Collection<ObjectProvider<? extends TeaObject>> getProviders() {
		return providerStore.values();
	}

	public void clear() {
		for (ObjectProvider<? extends TeaObject> provider : getProviders()) {
			provider.clear();
		}
	}

}
