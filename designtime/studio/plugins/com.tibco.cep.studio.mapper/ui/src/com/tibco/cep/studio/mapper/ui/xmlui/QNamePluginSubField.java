package com.tibco.cep.studio.mapper.ui.xmlui;

import javax.swing.JComponent;

import org.xml.sax.SAXException;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.bind.find.FindResultHandler;
import com.tibco.cep.studio.mapper.ui.data.bind.find.FindWindowPlugin;
import com.tibco.xml.data.primitive.ExpandedName;


/**
 * A customization interface for sub-field choosing off of the {@link QNamePanel}.<br>
 * Works with {@link QNamePlugin}.
 */
public interface QNamePluginSubField {
   /**
    * Retrieves what to get as the label for the local name part of the picker.
    *
    * @return A display string, or null to indicate to use default.
    */
   String getFieldNameLabel();

   /**
    * Retrieves all of the choices the namespaces for the given location.
    *
    * @return A set of choices.
    */
   String[] getChoicesFor(Object object) throws SAXException;

   /**
    * Given an open 'file-editor' (AEResource) for the namespace/location, make the GUI select the
    * proper local name.<br>
    * If an implementation does nothing, no biggie; it's just a nice to have feature.<br>
    * The implementation can assume that the passed in resource has been 'opened' in Designer, but should not
    * assume it is of the correct type (though hopefully it is!)
    *
    * @param resource The resource which contains the namespace/location.  For safety sake, do an instanceof before down-casting to the expected type.
    * @param name     The name of the component to be selected; typically an implementation of this should only care about {@link ExpandedName#getLocalName}.
    */
   void selectLocalNameOnGUI(UIAgent uiAgent, ExpandedName name);

   /**
    * Indicates if {@link #createPreviewView}
    * will work (without having to pass in a name).<br>
    * If true, the preview must always return a non-null component, otherwise it will never be called.
    */
   boolean hasPreviewView();

   /**
    * Gets an opaque object representation of the sub field.
    *
    * @param previousObject The previous opaque object, either representing the qname or the previous sub-field.
    * @param name           The local name.
    * @return An opaque object representing the sub-field.
    */
   Object getObjectFor(Object previousObject, String name);

   /**
    * Gets a preview component for the given QName.<br>
    *
    * @param object The opaque object returned in {@link #getObjectFor}.
    *               If {@link #hasPreviewView} is true, this must work, otherwise it will never get called (and can just return null).
    */
   JComponent createPreviewView(UIAgent uiAgent, Object object);

   /**
    * Gets a find window plugin for this type of qname.
    *
    * @return A find window plugin, or null, if not supported.
    */
   FindWindowPlugin createFindWindowPlugin();

   /**
    * This method extracts the expanded name from the opaque search results data on {@link FindResultHandler#result}.
    * Will only be called if {@link #createFindWindowPlugin()} has returned non-null;
    * that plugin will be generating this opaque search results.
    *
    * @param location
    * @param searchResult The opaque search result
    * @return The name of the result.
    */
   ExpandedName getNameFromFindResult(String location, Object searchResult);
}
