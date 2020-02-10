package com.tibco.cep.studio.mapper.ui.xmlui;

import org.xml.sax.SAXException;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.basic.DetailsViewFactory;
import com.tibco.cep.studio.mapper.ui.data.bind.find.FindResultHandler;
import com.tibco.cep.studio.mapper.ui.data.bind.find.FindWindowPlugin;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.tns.cache.TnsDocumentProvider;


/**
 * A customization interface for the QName picker component.
 */
public interface QNamePlugin {
   /**
    * Retrieves what to get as the label for the local name part of the picker.
    *
    * @return A display string, or null to indicate to use default.
    */
   String getLocalNameLabel();

   /**
    * Retrieves what to get as the label for the location part of the picker.
    * May return null to indicate using a default label.
    *
    * @return A display string, or null string to indicate to use default.
    */
   String getNamespaceLabel();

   /**
    * Retrieves all the file type (suffixes) that are potential containers of this type of qname.
    *
    * @return The interesting suffixes, without the dots, i.e. 'xsd', not '.xsd'.
    */
   String[] getInterestingSuffixes(TnsDocumentProvider tdp);

   /**
    * Retrieves all the namespaces for the given location.
    *
    * @return A set of namespaces, zero length array or null indicates no namespaces.
    *         An exception should be thrown if the resource type <b>is</b> correct, but can't be loaded for whatever reason.
    * @throws SAXException If the resource at the location could not be parsed (or otherwise read properly),
    *                      then the {@link SAXException#getMessage()} message will be displayed as a reason why choices are unavailable.
    */
   String[] getNamespacesFor(UIAgent uiAgent, String absoluteLocationURI) throws SAXException;

   /**
    * Retrieves all the local names for the given namespace.<br>
    * An implementation can return these in arbitrary order; the {@link QNamePanel} and friends, are
    * responsible for sorting, etc.
    *
    * @param absoluteLocationURI May be null, if passed in should limit to those at that absoluteLocationURI.
    * @return A set of local names (possibly zero length indicating no names in this namespace).
    * @throws SAXException If the resource at the namespace could not be parsed (or otherwise read properly),
    *                      then the {@link SAXException#getMessage()} message will be displayed as a reason why choices are unavailable.
    */
   String[] getLocalNamesFor(UIAgent uiAgent, String absoluteLocationURI, String namespace) throws SAXException;

   /**
    * For the name, get the locations that contains it.
    *
    * @param name The name of the item.
    * @return The location, or null, if cannot be found for whatever reason (including failures)
    */
   String[] getLocationsForName(UIAgent uiAgent, ExpandedName name);

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
    * Gets an opaque object that represents the QNamed thing.<br>
    * The object will be used in {@link #createPreviewView} (WCETODO make this true)
    *
    * @param tdp
    * @param absoluteLocationURI
    * @param name
    * @return
    */
   Object getObjectFor(TnsDocumentProvider tdp, String absoluteLocationURI, ExpandedName name);


   /**
    * Indicates if {@link #createPreviewView}
    * will work (without having to pass in a name).<br>
    * If true, the preview must always return a non-null component, otherwise it will never be called.
    */
   boolean hasPreviewView();

   /**
    * Gets a factory for the preview component.<br>
    * The {@link DetailsViewFactory#getComponentForNode} will be passed an object from {@link #getObjectFor} and should
    * return an appropriate details view.
    * If {@link #hasPreviewView} is true, this must work, otherwise it will never get called (and can just return null).
    */
   DetailsViewFactory createPreviewView(UIAgent uiAgent);

   /**
    * Gets a find window plugin for this type of qname.
    *
    * @return A find window plugin, or null, if not supported.
    */
   FindWindowPlugin createFindWindowPlugin(UIAgent uiAgent);

   /**
    * This method extracts the expanded name from the opaque search results data on {@link FindResultHandler#result}.
    * Will only be called if {@link #createFindWindowPlugin} has returned non-null;
    * that plugin will be generating this opaque search results.
    *
    * @param location
    * @param searchResult The opaque search result
    * @return The name of the result.
    */
   ExpandedName getNameFromFindResult(String location, Object searchResult);
}
