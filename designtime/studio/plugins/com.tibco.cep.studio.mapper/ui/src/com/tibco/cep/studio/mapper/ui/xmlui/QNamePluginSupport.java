package com.tibco.cep.studio.mapper.ui.xmlui;

import java.util.HashSet;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.ImportRegistrySupport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.tns.parse.TnsFragment;

/**
 * A set of helper methods working again {@link QNamePlugin}.
 */
public class QNamePluginSupport {
   /**
    * Gets all the valid local names given the current namespace & import list, including ambiguous ones.
    *
    * @return The valid local names.
    * @throws SAXException For exceptions looking up names.
    */
   public static String[] getAllValidLocalNames(UIAgent uiAgent,
                                                QNamePlugin plugin,
                                                String namespace,
                                                String contextLocation,
                                                ImportRegistry importRegistry) throws SAXException {
      String[] locations = ImportRegistrySupport.getLocationsForNamespaceURI(importRegistry, namespace);
      if (locations.length == 0 && contextLocation != null) {
         locations = new String[]{contextLocation};
      }
      // If we have no imports, we're going to fall back on searching namespaces:
      if (locations.length == 0) {
         locations = new String[]{null};
      }

      HashSet<String> set = null;
      for (int i = 0; i < locations.length; i++) {
         String loc = locations[i];
         String absLoc;
         if (loc != null) {
            String prLoc = makeProjectRelativeURI(contextLocation, loc);
            absLoc = uiAgent.getAbsoluteURIFromProjectRelativeURI(prLoc);
         }
         else {
            absLoc = null;
         }
         String[] ln = plugin.getLocalNamesFor(uiAgent, absLoc, namespace);
         if (ln == null) {
            ln = new String[0];
         }
         if (locations.length == 1) {
            return ln; // quicky optimization.
         }
         if (set == null) {
            set = new HashSet<String>();
         }
         for (int xi = 0; xi < ln.length; xi++) {
            set.add(ln[xi]);
         }
      }
      if (set == null) {
         return new String[0];
      }
      return set.toArray(new String[set.size()]);
   }

   /**
    * Get the (project relative currently) location of the name given the imports & context.
    *
    * @return The location or null if none found.
    */
   public static String getLocationForName(UIAgent uiAgent,
                                           QNamePlugin plugin,
                                           String contextLocation,
                                           ImportRegistry importRegistry,
                                           ExpandedName name) {
      String[] locations = ImportRegistrySupport.getLocationsForNamespaceURI(importRegistry, name.getNamespaceURI());
      if (locations.length == 0 && contextLocation != null) {
         locations = new String[]{contextLocation}; // WCETODO like below, make this be able to deal with multiple locations.
      }
      for (int i = 0; i < locations.length; i++) {
         String loc = locations[i];
         String prLoc = makeProjectRelativeURI(contextLocation, loc);
         String absLoc = uiAgent.getAbsoluteURIFromProjectRelativeURI(prLoc);
         if (plugin.getObjectFor(uiAgent.getTnsCache(), absLoc, name) != null) {
            return prLoc;
         }
      }
      // try alternative lookup approach (BE-13239)
      for (int i = 0; i < locations.length; i++) {
    	  String loc = locations[i];
    	  TnsFragment frag = (TnsFragment)uiAgent.getSmNamespaceProvider().getNamespace(name.getNamespaceURI());
    	  if (frag == null) {
    		  continue;
    	  }
          String absLoc = frag.getDocument().getLocation();
    	  String prLoc = makeProjectRelativeURI(contextLocation, loc);
    	  if (plugin.getObjectFor(uiAgent.getTnsCache(), absLoc, name) != null) {
    		  return prLoc;
    	  }
      }
      return null;
   }

   /**
    * Gets all the ambiguous local names given the current namespace & import list (so if there's not more than 1 import, can't be ambiguous).
    *
    * @return The valid local names.
    * @throws SAXException For exceptions looking up names.
    */
   public static String[] getAllAmbiguousLocalNames(UIAgent uiAgent, QNamePlugin plugin, String namespace, String contextLocation, ImportRegistry importRegistry) throws SAXException {
      String[] locations = ImportRegistrySupport.getLocationsForNamespaceURI(importRegistry, namespace);
      if (locations.length == 0 && contextLocation != null) {
         locations = new String[]{contextLocation}; // WCETODO like below, make this be able to deal with multiple locations.
      }
      if (locations.length <= 1) {
         // with only 1 import, can't be ambiguous.
         return new String[0];
      }
      return new String[0];
      //WCETODO code below doesn't work because, currently, the local-names at a location/namespaces include included names
      // and there's no immediately obvious way to fix that... maybe add a getNonIncludedLocalNames type method
      // to qnameplugin to deal w/ this... this is going to be required for more advanced import checking type stuff...

      /*
      HashSet set = new HashSet();
      HashSet ambig = new HashSet();
      for (int i=0;i<locations.length;i++)
      {
          String loc = locations[i];
          if (loc.startsWith("/")) // ugly, need to fix... need baseURI done.
          {
              loc = loc.substring(1);
          }
          String absLoc = BWUtilities.getXmluiAgent(doc).getAbsoluteURIFromProjectRelativeURI(loc);
          String[] ln = plugin.getLocalNamesFor(doc, absLoc, namespace);
          if (ln==null) ln = new String[0];
          for (int xi=0;xi<ln.length;xi++)
          {
              if (set.contains(ln[xi]))
              {
                  ambig.add(ln[xi]);
              }
              set.add(ln[xi]);
          }
      }
      return (String[]) ambig.toArray(new String[ambig.size()]);*/
   }

   /**                                     repo
    * Gets the opaque object for the given qname and imports, or null if not found or ambiguous.
    * (This logic exists elsewhere & should be consolidated; see note on {@link com.tibco.cep.studio.mapper.ui.xmlui.TnsBasedQNamePlugin}.)
    */
   public static Object getQNameObject(QNamePlugin plugin,
		   								UIAgent uiAgent,
                                       String contextURI,
                                       ImportRegistry importRegistry,
                                       ExpandedName name) {
      String[] locations = ImportRegistrySupport.getLocationsForNamespaceURI(importRegistry, name.getNamespaceURI());
      Object obj = null;
      boolean isAmbiguous = false;
      for (int i = 0; i < locations.length; i++) {
         String loc = locations[i];
         String prLoc = makeProjectRelativeURI(contextURI, loc);
         String absLoc = uiAgent.getAbsoluteURIFromProjectRelativeURI(prLoc);

         Object tobj = plugin.getObjectFor(uiAgent.getTnsCache(), absLoc, name);
         if (obj != null && tobj != null) {
            isAmbiguous = true;
         }
         if (tobj != null) {
            obj = tobj;
         }
      }
      if (isAmbiguous) {
         return null;
      }
      if (obj == null) {
         // can happen if there's no import:
         String[] loc = plugin.getLocationsForName(uiAgent, name);
         if (loc != null && loc.length == 1) {
            String prLoc = makeProjectRelativeURI(contextURI, loc[0]);
            String absLoc = uiAgent.getAbsoluteURIFromProjectRelativeURI(prLoc);

            return plugin.getObjectFor(uiAgent.getTnsCache(), absLoc, name);
         }
      }
      return obj;
   }

   /**
    * This is not real URIs, this is project possiblyRelative pseudo-URIs for now.
    *
    * @param contextURI
    * @param possiblyRelative
    * @return A project relative URI, always starting with '/'
    */
   public static String makeProjectRelativeURI(String contextURI, String possiblyRelative) {
      if (contextURI == null) {
         return possiblyRelative;
      }
      if (!possiblyRelative.startsWith("/")) {
         // Can't use URL classes because of special characters; (This whole thing needs total redo; needs to be at VFile level).
         int i = contextURI.lastIndexOf('/');
         if (i < 0) {
            return contextURI + "/" + possiblyRelative; //??
         }
         String acontext = contextURI.substring(0, i);
         return makeRelativeURI(acontext, possiblyRelative);
      }
      return possiblyRelative; // was already project relative.
   }

   private static String makeRelativeURI(String contextURI, String uri) {
      if (uri.startsWith("../")) {
         // VPath ?
         int i2 = contextURI.lastIndexOf("/");
         if (i2 >= 0) {
            String r = makeRelativeURI(contextURI.substring(0, i2), uri.substring(3));
            return r;
         }
      }
      return contextURI + "/" + uri;
   }
}
