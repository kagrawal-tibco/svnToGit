package com.tibco.cep.runtime.model.serializers;

import java.util.concurrent.atomic.AtomicReference;

import com.tibco.cep.runtime.util.SystemProperty;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Jun 22, 2010
 * Time: 10:06:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class SerializerFactoryFactory {

    private static AtomicReference<SerializerFactory> instance = new AtomicReference<SerializerFactory>();

    public static SerializerFactory getInstance() {
        SerializerFactory sfactory = instance.get();
        if (sfactory == null) {
        	String className = System.getProperty("com.tibco.cep.runtime.model.serializers.factory", "com.tibco.cep.runtime.model.serializers.as.ASSerializerFactory");
       	
            String providerName = System.getProperty(SystemProperty.VM_DAOPROVIDER_CLASSNAME.getPropertyName(),
                    SystemProperty.VM_DAOPROVIDER_CLASSNAME.getValidValues()[0].toString());
 
        	if (providerName.equalsIgnoreCase("com.tibco.cep.runtime.service.cluster.om.impl.CoherenceDaoProvider")) {
        		className = "com.tibco.cep.runtime.model.serializers.coherence.CoherenceSerializerFactory";
        	}
        	
            try {
                Class cls = Class.forName(className);
                sfactory = (SerializerFactory) cls.newInstance();
                if (!instance.compareAndSet(null, sfactory)) {
                    sfactory = instance.get();
                }
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return sfactory;
    }
}
