package com.tibco.cep.loadbalancer.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

/*
* Author: Ashwin Jayaprakash / Date: 4/26/11 / Time: 4:45 PM
*/
public class Utils {
    private static ClassLoader customClassLoader;

    public static ClassLoader $findRspClassLoader() {
        RuleServiceProviderManager rspm = RuleServiceProviderManager.getInstance();

        Collection rsps = rspm.getRuleServiceProviders();

        RuleServiceProvider rsp = (rsps != null && rsps.size() > 0) ? (RuleServiceProvider) rsps.iterator().next() :
                rspm.getDefaultProvider();

        return rsp.getClassLoader();
    }

    private static ClassLoader $classLoader() {
        if (customClassLoader == null) {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            customClassLoader = cluster.getRuleServiceProvider().getClassLoader();
        }

        return customClassLoader;
    }

    public static byte[] $serializeEvent(SimpleEvent event) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeUTF(event.getClass().getName());
        event.writeExternal(oos);

        oos.close();

        return baos.toByteArray();
    }

    public static SimpleEvent $deserializeEvent(byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);

        SimpleEvent event = null;
        try {
            ClassLoader classLoader = $classLoader();
            String klassName = ois.readUTF();
            Class klass = Class.forName(klassName, true, classLoader);
            event = (SimpleEvent) klass.newInstance();
            event.readExternal(ois);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        ois.close();

        return event;
    }
}
