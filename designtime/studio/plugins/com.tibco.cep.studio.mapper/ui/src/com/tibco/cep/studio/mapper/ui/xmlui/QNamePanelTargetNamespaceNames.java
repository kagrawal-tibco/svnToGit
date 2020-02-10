package com.tibco.cep.studio.mapper.ui.xmlui;


/**
 * Works with the {@link QNamePanel} and {@link QNameFormField} to allow local-names from the target-namespace namespace
 * to be provided separately.<br>
 * The target-namespace namespace is the 'this' namespace inside an editor; i.e. inside a schema, the targetNamespace namespace is
 * the targetNamespace of the schema.
 */
public interface QNamePanelTargetNamespaceNames {
   /**
    * Gets all the local names currently valid in the context namespace.
    *
    * @return The local names, never null.
    */
   String[] getLocalNames();

   /**
    * @param localName The local name of the object, from {@link #getLocalNames}
    * @return The opaque object which can be used by {@link com.tibco.cep.studio.mapper.ui.xmlui.QNamePlugin#createPreviewView} to
    *         show a preview.
    */
   Object getObjectFor(String localName);
}
