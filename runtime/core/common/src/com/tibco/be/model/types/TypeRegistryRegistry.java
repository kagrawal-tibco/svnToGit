package com.tibco.be.model.types;

import java.util.HashMap;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 18, 2004
 * Time: 8:40:05 AM
 * To change this template use File | Settings | File Templates.
 */
public final class TypeRegistryRegistry {
    final public static HashMap m_registries = new HashMap();

    /**
     *
     * @param registryName
     * @return
     */
    final public static TypeConverterHandler getRegistry(ExpandedName registryName) {
        return (TypeConverterHandler) m_registries.get(registryName);
    }

    /**
     *
     * @param registryName
     * @param registry
     */
    final public static void registerRegistry(ExpandedName registryName, TypeConverterHandler registry) {
        m_registries.put(registryName, registry);
    }

}
