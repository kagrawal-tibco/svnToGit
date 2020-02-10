/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Methods for generating good, consistent error messages relating to loading of QNamed components.<br>
 * Contains pre-built methods for common components in schema.
 * (This is a placeholder; not currently used, needs to be in xmlsdk)
 */
public class QNameErrorResources
{
    /**
     * Gets the message code for
     * @param namespaceTypeMessageCode The MessageCode string for the type of namespace,
     *                                 i.e. the key for 'schema', 'wsdl', etc., not the strings themselves.
     * @param componentTypeMessageCode The type of component, i.e. element, message, etc.
     * @param searchName The QName that couldn't be resolved because of namespace-not-found (local name used in the error message).
     * @return The localized message.
     */
    public static String getCannotFindNamespaceMessage(String namespaceTypeMessageCode, String componentTypeMessageCode, ExpandedName searchName)
    {
        String nsTypeStr = ResourceBundleManager.getMessage(namespaceTypeMessageCode);
        String componentStr = ResourceBundleManager.getMessage(componentTypeMessageCode);
        return ResourceBundleManager.getMessage(MessageCode.CANNOT_LOAD_SCHEMA_FOR_ELEMENT,nsTypeStr,searchName.getNamespaceURI(),componentStr, searchName.getLocalName());
    }

    public static String getCannotFindLocalNameMessage(String namespaceTypeMessageCode, String componentTypeMessageCode, ExpandedName searchName)
    {
        return "";//blah.
    }

    public static String getAmbiguousLocalNameMessage(String namespaceTypeMessageCode, String componentTypeMessageCode, ExpandedName searchName)
    {
        return "";//
    }
}
