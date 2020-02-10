package com.tibco.cep.studio.mapper.ui.xmlui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.xml.sax.SAXException;

import com.tibco.cep.studio.mapper.MapperSchemaUtils;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.objectrepo.schema.SchemaRepoUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.helpers.NoNamespace;
import com.tibco.xml.tns.TargetNamespace;
import com.tibco.xml.tns.TnsComponent;
import com.tibco.xml.tns.cache.TnsDocumentProvider;
import com.tibco.xml.tns.parse.TnsDocument;
import com.tibco.xml.tns.parse.TnsError;
import com.tibco.xml.tns.parse.TnsErrorSeverity;
import com.tibco.xml.tns.parse.TnsFragment;


/**
 * A helper abstract class that implements most of {@link QNamePlugin} for components using
 * the {@link TnsDocument} based api.
 * This class, and some of the api behind it, should probably be deleted & just use the TNSCache apis straight; they
 * were co-developed... There is quite a bit of duplicate logic for handling imports, etc., which should be reused
 * (though doing that necessarily won't be as easy as just deleting this class)
 */
@SuppressWarnings("rawtypes")
public abstract class TnsBasedQNamePlugin implements QNamePlugin {
   private int m_componentType; // The SmComponent type code, i.e. ELEMENT_TYPE, TYPE_TYPE, etc.
   protected Class m_namespaceType;
   private static final String[] EMPTY_ARRAY = new String[0];

   protected TnsBasedQNamePlugin(int smComponentType, Class namespaceType) {
      m_componentType = smComponentType;
      m_namespaceType = namespaceType;
   }

   /**
    * Implements with TNS.
    */
public String[] getLocalNamesFor(UIAgent uiAgent,
                                          String absoluteLocationURI, String namespace) throws SAXException {
      if (absoluteLocationURI == null) {
         // Special case no-namespace stuff (need to push into TNS cache)
         if (MapperSchemaUtils.isNoTargetNamespaceNamespace(namespace)) {
            // ... and, here we go:
            String location = MapperSchemaUtils.getLocationFromNoTargetNamespaceNamespace(namespace);
            String absLoc = uiAgent.getAbsoluteURIFromProjectRelativeURI(location);
            return getLocalNamesFor(uiAgent, absLoc, namespace);
         }
         TargetNamespace ts = uiAgent.getTnsCache().getNamespace(namespace, m_namespaceType);
         if (ts != null) {
            ArrayList<String> temp = new ArrayList<String>();
            Iterator i = ts.getComponents(m_componentType);
            // Can, in some circumstances, get massive numbers of duplicates (for include-heavy schemas...)
            HashSet<String> set = new HashSet<String>();
            while (i.hasNext()) {
               TnsComponent sc = (TnsComponent) i.next();
               String ln = sc.getExpandedName().getLocalName();
               if (!set.contains(ln)) {
                  temp.add(ln);
                  set.add(ln);
               }
            }

            return temp.toArray(new String[0]);
         }
         return new String[0]; // not found.
      }
      TnsFragment frag;
      try {
         frag = getFragment(uiAgent.getTnsCache(), absoluteLocationURI, namespace);
      }
      catch (Exception se) {
         // just in case, shouldn't happen:
         throw new SAXException("Internal error:" + se.getMessage(), se);
      }
      if (frag == null) {
         return null;
      }
      ArrayList<String> temp = new ArrayList<String>();
      Iterator i = frag.getComponents(m_componentType);
      while (i.hasNext()) {
         TnsComponent sc = (TnsComponent) i.next();
         temp.add(sc.getExpandedName().getLocalName());
      }

      return temp.toArray(new String[0]);
   }

   /**
    * Implemented here using Tns.
    */
public final String[] getNamespacesFor(UIAgent uiAgent,
                                          String absoluteLocationURI) throws SAXException {
      try {
         TnsDocument tnsDoc = getDocumentForLocation(uiAgent.getTnsCache(), absoluteLocationURI);
         ArrayList<String> ns = new ArrayList<String>();
         if (tnsDoc == null) {
            // none.
         }
         else {
            if (tnsDoc.hasErrors(TnsErrorSeverity.FATAL, false)) {
               Iterator i = tnsDoc.getErrors(TnsErrorSeverity.FATAL, false);
               if (i.hasNext()) {
                  TnsError e = (TnsError) i.next();
                  //TnsErrorSeverity severity = e.getSeverity();
                  // all of these error types indicate that it is totally unusable, so stop.
                  throw new SAXException(e.getMessage());
               }
            }
            Iterator i = tnsDoc.getFragments();
            while (i.hasNext()) {
               TnsFragment frag = (TnsFragment) i.next();
               getNamespacesFor(frag, uiAgent, absoluteLocationURI, ns);
            }
         }
         return ns.toArray(new String[0]);
      }
      catch (Exception se) // WCETODO SmException, get errors.
      {
         throw new SAXException(se.getMessage(), se);
      }
   }

   private void getNamespacesFor(TnsFragment frag,
		   UIAgent uiAgent,
                                 String absoluteLocationURI, ArrayList<String> ns) {
      if (frag.getNamespaceType() == m_namespaceType) {
         String uri = frag.getNamespaceURI();
         if (uri == null || NoNamespace.isNoNamespaceURI(uri)) {
            String rel = null;
            rel = uiAgent.getProjectRelativeURIFromAbsoluteURI(absoluteLocationURI);
            ns.add(SchemaRepoUtils.makeNoTargetNamespaceNamespace('/' + rel));
         }
         else {
            ns.add(uri);
         }
      }
      Iterator i = frag.getFragments();
      while (i.hasNext()) {
         getNamespacesFor((TnsFragment) i.next(), uiAgent, absoluteLocationURI, ns);
      }
   }

   private TnsDocument getDocumentForLocation(TnsDocumentProvider tdp, String absoluteLocationURI) {

      // Exactly what URI scheme is used to store things has changed a bit & is very confusing (there are so many choices)
      TnsDocument tnsDoc = tdp.getDocument(absoluteLocationURI);
      return tnsDoc;
   }

   public String[] getInterestingSuffixes(TnsDocumentProvider tdp) {
      //WCETODO This is not component type specific!
      return tdp.getInterestingExtensions();
   }

   /**
    * Implemented here (this is sort of suspect anyway)
    */
   public final String[] getLocationsForName(UIAgent uiAgent, ExpandedName name) {
      TargetNamespace sns = uiAgent.getTnsCache().getNamespace(name.getNamespaceURI(), m_namespaceType);
      if (sns == null) {
         return EMPTY_ARRAY;
      }
      TnsComponent component = sns.getComponent(name.getLocalName(), m_componentType);
      if (component != null) {
         TnsFragment frag = component.getContainingFragment();
         // If it's from multiple locations, will have null fragment:
         // (Which defeats the purpose of this routine, but oh, well... not clear that this is possible).

         // (For BPEL / next gen. stuff --- probably a good idea to insist on 1 namespace = 1 schema (same for WSDL),
         // and not allow multiples)
         TnsDocument doc = frag == null ? null : frag.getDocument();
         String location = doc != null ? doc.getLocation() : (String) null;
         if (location == null) {
            location = "";
         }
         if (location.length() > 0) {
            location = uiAgent.getProjectRelativeURIFromAbsoluteURI(location);
         }
         return new String[]{location};
      }
      return EMPTY_ARRAY;
   }

   public Object getObjectFor(TnsDocumentProvider tdp,
                              String absoluteLocationURI,
                              ExpandedName name) {
      TnsFragment frag = getFragment(tdp, absoluteLocationURI, name.getNamespaceURI());
      if (frag == null) {
         return null;
      }
      return frag.getComponent(name.getLocalName(), m_componentType);
   }

protected TnsFragment getFragment(TnsDocumentProvider tdp,
                                   String absoluteLocationURI,
                                   String namespace) {
      TnsDocument doc = getDocumentForLocation(tdp, absoluteLocationURI);
      if (doc == null) {
         return null;
      }
      Iterator i = doc.getFragments();
      while (i.hasNext()) {
         TnsFragment frag = (TnsFragment) i.next();
         TnsFragment fr = getFragmentInNamespaceRecursive(frag, namespace);
         if (fr != null) {
            return fr;
         }
      }
      return null;
   }

   /**
    * Checks that the fragment is of the correct type AND having the given namespace.
    */
private TnsFragment getFragmentInNamespaceRecursive(TnsFragment frag, String namespace) {
      if (isFragmentInNamespace(frag, namespace)) {
         return frag;
      }
      Iterator i = frag.getFragments();
      while (i.hasNext()) {
         TnsFragment ff = (TnsFragment) i.next();
         TnsFragment r = getFragmentInNamespaceRecursive(ff, namespace);
         if (r != null) {
            return r;
         }
      }
      return null;
   }

   /**
    * Checks that the fragment is of the correct type AND having the given namespace.
    */
   private boolean isFragmentInNamespace(TnsFragment frag, String namespace) {
      if (frag.getNamespaceType() == m_namespaceType) {
         if (MapperSchemaUtils.isNoTargetNamespaceNamespace(namespace)) {
            // Ugly -- need better approach probably -- will give the first fragment of the right kind,
            // but may not be the right fragment (i.e. in embedded WSDL schemas).
            return true;
         }
         String uri = frag.getNamespaceURI();
         if (uri == null) {
            if (uri == namespace || MapperSchemaUtils.isNoTargetNamespaceNamespace(namespace)) {
               return true;
            }
         }
         else {
            if (uri.equals(namespace)) {
               return true;
            }
         }
      }
      return false;
   }

   /**
    * Gets the component type passed in the constructor.
    */
   protected int getComponentType() {
      return m_componentType;
   }

   /**
    * Finds a component by absoluteLocationURI and name.<br>
    * (Helper method for derived classes)
    *
    * @param absoluteLocationURI The absoluteLocationURI.
    * @param name                The name.
    * @return The component or null if not found.
    */
   protected TnsComponent getTnsComponent(TnsDocumentProvider tdp, String absoluteLocationURI, ExpandedName name) {
      return (TnsComponent) getObjectFor(tdp, absoluteLocationURI, name);
   }
}
