package com.tibco.cep.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.math.BigInteger;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.tibco.cep.common.log.LoggerService;
import com.tibco.cep.common.resource.Configuration;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.impl.common.resource.DefaultId;
import com.tibco.cep.service.ScriptingService;
import com.tibco.cep.util.annotation.LogCategory;
import com.tibco.cep.util.annotation.Optional;

/*
* Author: Ashwin Jayaprakash / Date: Mar 17, 2010 / Time: 5:59:27 PM
*/

public class Helper {
    public static long $time() {
        return System.currentTimeMillis();
    }

    public static void $sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
        }
    }

    /**
     * @param resourceProvider
     * @param clazz            Annotated with {@link LogCategory}.
     * @return
     */
    public static Logger $logger(ResourceProvider resourceProvider, Class clazz) {
        LoggerService loggerService = resourceProvider.fetchResource(LoggerService.class);

        return loggerService.getLogger(clazz);
    }

    public static Id $id(Object actual) {
        return $id(null, actual);
    }

    public static Id $id(@Optional Object parent, Object actual) {
        return new DefaultId(parent, actual);
    }

    public static Id $id(ResourceProvider resourceProvider, String script, Object... namesAndValues) {
        Object o = $eval(resourceProvider, script, namesAndValues);

        return $id(o);
    }

    public static Object $eval(ResourceProvider resourceProvider, String script, Object... namesAndValues) {
        ScriptingService scriptingService = resourceProvider.fetchResource(ScriptingService.class);

        Object[] array = namesAndValues;
        if (array.length % 2 != 0) {
            throw new IllegalArgumentException(
                    "The parameter names and values must be an even number of consecutive <String:Key, Object:Value> pairs." +
                            " What it has instead is [" + Arrays.asList(namesAndValues) + "]");
        }

        HashMap<String, Object> params = new HashMap<String, Object>(array.length);

        try {
            for (int i = 0; i < array.length;) {
                Object key = array[i];
                i++;
                Object value = array[i];
                i++;

                if (key == null) {
                    throw new IllegalArgumentException(
                            "The parameter names and values must be an even number of consecutive <String:Key, Object:Value> pairs." +
                                    " Keys cannot be null." +
                                    " What it has instead is [" + Arrays.asList(array) + "]");
                }

                params.put(key.toString(), value);
            }

            return scriptingService.evaluate(script, params);
        }
        finally {
            params.clear();
        }
    }

    /**
     * @param resourceProvider
     * @param propertyName
     * @param valueToSetIfNotPresent
     * @return
     * @see #$configProperty(ResourceProvider, Id, String, String)
     */
    public static String $configProperty(ResourceProvider resourceProvider, String propertyName,
                                         String valueToSetIfNotPresent) {
        return $configProperty(resourceProvider, null, propertyName, valueToSetIfNotPresent);
    }

    /**
     * @param resourceProvider
     * @param propertyName
     * @return
     * @see #$configProperty(ResourceProvider, Id, String, String)
     */
    public static String $configProperty(ResourceProvider resourceProvider, String propertyName) {
        return $configProperty(resourceProvider, null, propertyName, null);
    }

    /**
     * @param resourceProvider
     * @param configurationId
     * @param propertyName
     * @param valueToSetIfNotPresent
     * @return Trimmed value. If trimmed value has 0 length, then null is returned.
     */
    public static String $configProperty(ResourceProvider resourceProvider, @Optional Id configurationId,
                                         String propertyName, @Optional String valueToSetIfNotPresent) {
        Configuration configuration = null;

        if (configurationId == null) {
            configuration = resourceProvider.fetchResource(Configuration.class);
        }
        else {
            configuration = resourceProvider.fetchResource(Configuration.class, configurationId);
        }

        if (configuration == null) {
            throw new IllegalArgumentException(
                    "No configuration instance could be found for id [" + configurationId + "]");
        }

        //-----------------

        String s = configuration.getProperty(propertyName);

        if (s != null) {
            s = s.trim();

            if (s.length() == 0) {
                s = null;
            }
        }

        if (s == null && valueToSetIfNotPresent != null) {
            configuration.setProperty(propertyName, valueToSetIfNotPresent);

            return valueToSetIfNotPresent;
        }

        return s;
    }

    /**
     * @param resourceProvider
     * @param propertyNameOfClass
     * @param superClassOrIntf
     * @param <T>
     * @return
     * @see #$instantiate(ResourceProvider, Id, String, Class)
     */
    public static <T> T $instantiate(ResourceProvider resourceProvider, String propertyNameOfClass,
                                     Class<T> superClassOrIntf) {
        return $instantiate(resourceProvider, null, propertyNameOfClass, superClassOrIntf);
    }

    /**
     * @param resourceProvider
     * @param configurationId
     * @param propertyNameOfClass
     * @param superClassOrIntf
     * @param <T>
     * @return
     * @throws RuntimeException
     */
    public static <T> T $instantiate(ResourceProvider resourceProvider, @Optional Id configurationId,
                                     String propertyNameOfClass, Class<T> superClassOrIntf) {
        String className = $configProperty(resourceProvider, configurationId, propertyNameOfClass, null);

        if (className == null || className.length() == 0) {
            throw new IllegalArgumentException(
                    "The class name [" + className + "] specified by the property [" + propertyNameOfClass +
                            "] is not valid.");
        }

        //-----------------

        try {
            return $instantiate(superClassOrIntf, className);
        }
        catch (RuntimeException e) {
            throw new RuntimeException(
                    "Error occurred while creating an instance of class [" + className + "] specified by the property" +
                            " [" + propertyNameOfClass + "]", e);
        }
    }

    /**
     * @param superClassOrIntf
     * @param className
     * @param <T>
     * @return
     * @throws RuntimeException
     */
    public static <T> T $instantiate(Class<T> superClassOrIntf, String className) {
        try {
            Class k = Class.forName(className, true, Thread.currentThread().getContextClassLoader());

            Object o = k.newInstance();

            return superClassOrIntf.cast(o);
        }
        catch (Throwable t) {
            throw new RuntimeException("Error occurred while creating an instance of class [" + className + "]", t);
        }
    }

    public static String $uniqueMachineId() {
        TreeMap<String, byte[]> nicAddresses = new TreeMap<String, byte[]>();
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");

            Enumeration<NetworkInterface> nwInterfaces = NetworkInterface.getNetworkInterfaces();

            for (; nwInterfaces.hasMoreElements();) {
                NetworkInterface anInterface = nwInterfaces.nextElement();

                byte[] hwAddress = anInterface.getHardwareAddress();
                if (hwAddress != null) {
                    nicAddresses.put(anInterface.getName(), hwAddress);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error occurred while retrieving network interface details for the machine", e);
        }

        //Do this in ascending order to ensure it's the same digest for that machine, all the time.
        for (Entry<String, byte[]> entry : nicAddresses.entrySet()) {
            md.update(entry.getValue());
        }

        byte[] digestBytes = md.digest();

        BigInteger digestInt = new BigInteger(1, digestBytes);

        return digestInt.toString(16);
    }

    public static String $processId() {
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();

        return bean.getName();
    }
}
