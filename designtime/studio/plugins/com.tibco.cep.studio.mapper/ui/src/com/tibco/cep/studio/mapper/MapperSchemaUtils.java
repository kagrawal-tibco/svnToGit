package com.tibco.cep.studio.mapper;

public class MapperSchemaUtils {

    public static String getLocationFromNoTargetNamespaceNamespace(String namespace)
    {
        if (isNoTargetNamespaceNamespace(namespace))
        {
            return namespace.substring(NO_NAMESPACE_NS_PREFIX.length());
        }
        return null;
    }

    public static final String NO_NAMESPACE_NS_PREFIX = "http://www.tibco.com/ns/no_namespace_schema_location";

    public static boolean isNoTargetNamespaceNamespace(String namespace)
    {
        if (namespace==null)
        {
            return false;
        }
        return namespace.startsWith(NO_NAMESPACE_NS_PREFIX);
    }

}
