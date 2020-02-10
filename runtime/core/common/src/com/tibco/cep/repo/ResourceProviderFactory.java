package com.tibco.cep.repo;


import java.util.LinkedHashSet;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 18, 2006
 * Time: 11:44:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceProviderFactory {

    static com.tibco.cep.repo.ResourceProviderFactory gProviderFactory = null;
    LinkedHashSet providers = new LinkedHashSet();

    public synchronized static com.tibco.cep.repo.ResourceProviderFactory getInstance() {

        if (com.tibco.cep.repo.ResourceProviderFactory.gProviderFactory == null) {
            com.tibco.cep.repo.ResourceProviderFactory.gProviderFactory = new com.tibco.cep.repo.ResourceProviderFactory();
        }
        return com.tibco.cep.repo.ResourceProviderFactory.gProviderFactory;

    }

    public void registerProvider(ResourceProvider provider) {
        providers.add(provider);
    }

    public LinkedHashSet getProviders() {
        return providers;
    }

    public void unRegisterProvider(ResourceProvider provider) {
        providers.remove(provider);
    }
}
