package com.tibco.cep.studio.mapper.ui.xmlui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.studio.mapper.ui.data.bind.find.FindResultHandler;
import com.tibco.cep.studio.mapper.ui.data.bind.find.FindWindowPlugin;
import com.tibco.cep.studio.mapper.ui.data.bind.find.FindWindowResult;
import com.tibco.cep.studio.mapper.ui.data.bind.find.GenericFindWindow;
import com.tibco.cep.studio.mapper.ui.data.bind.find.SearchStringPanel;
import com.tibco.objectrepo.object.ObjectProvider;
import com.tibco.objectrepo.schema.SchemaRepoUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.helpers.NoNamespace;
import com.tibco.xml.tns.TnsComponent;
import com.tibco.xml.tns.cache.TnsDocumentProvider;
import com.tibco.xml.tns.parse.TnsDocument;
import com.tibco.xml.tns.parse.TnsFragment;

/**
 * A pretty powerful find window for Tns-based types.
 */
@SuppressWarnings("rawtypes")
public abstract class TnsBasedFindWindowPlugin implements FindWindowPlugin {
   private final int m_componentType; // The SmComponent type code, i.e. ELEMENT_TYPE, TYPE_TYPE, etc.
   private ChangeListener m_editorChangeListener;
   private SearchStringPanel m_nameField;
   private TnsDocumentProvider m_tnsComponentProvider;
   private JLabel m_rendererLabel = new JLabel();
   private JPanel m_panel;
   private ArrayList<SearchType> m_searchTypes = new ArrayList<SearchType>();
   private Class m_namespaceType;

   public TnsBasedFindWindowPlugin(Class namespaceType, int smComponentType, TnsDocumentProvider scp) {
      if (scp == null) {
         throw new NullPointerException("Null document provider");
      }
      m_namespaceType = namespaceType;
      m_panel = new JPanel(new BorderLayout());
      m_componentType = smComponentType;
      m_tnsComponentProvider = scp;
      m_rendererLabel.setOpaque(true);
      m_nameField = new SearchStringPanel();
      m_nameField.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            changed();
         }
      });
      m_panel.add(m_nameField);
   }

   public static interface SearchType {
      String getLabel();

      boolean matches(Pattern regularExpression, Object component);
   }

   public void addSearchType(SearchType st) {
      m_searchTypes.add(st);
      m_nameField.addSearchType(st.getLabel());
   }

   public void addNameSearchType() {
      SearchType st = new SearchType() {
         public String getLabel() {
            return QNamePanelResources.NAME;
         }

         public boolean matches(Pattern regularExpression, Object component) {
            TnsComponent c = (TnsComponent) component;
            ExpandedName n = c.getExpandedName();
            if (n != null) {
               return regularExpression.matcher(n.getLocalName()).matches();
            }
            return false;
         }
      };
      addSearchType(st);
   }

public void find(FindResultHandler toHandler, ObjectProvider objectProvider, String resourceLoc, Object findData) {
	   if (resourceLoc == null) {
		   System.out.println("resource location is null");
		   return;
	   }
      FindData fd = (FindData) findData;
      TnsDocument schema = m_tnsComponentProvider.getDocument(resourceLoc); // This is not ideal, but for now.
      if (schema == null) {
         return;
      }
      Iterator fri = schema.getFragments();
      Pattern r = fd.m_regex;
      SearchType st = fd.m_searchType;
      while (fri.hasNext()) {
         TnsFragment frag = (TnsFragment) fri.next();
         if (frag.getNamespaceType() == m_namespaceType) {
            findInFragment(frag, st, r, toHandler);
         }
      }
   }

   private void findInFragment(TnsFragment frag, SearchType st, Pattern re, FindResultHandler toHandler) {
      Iterator i = frag.getComponents(m_componentType);
      String loc = frag.getDocument().getLocation();
      while (i.hasNext()) {
         TnsComponent c = (TnsComponent) i.next();
         if (st.matches(re, c)) {
            ExpandedName en = c.getExpandedName();
            // Special case no-namespaced things, give them the Tibco no-namespace special treatment:
            if (NoNamespace.isNoNamespaceURI(en.getNamespaceURI())) {
               String nonamespacens = SchemaRepoUtils.makeNoTargetNamespaceNamespace(loc);
               en = ExpandedName.makeName(nonamespacens, en.getLocalName());
            }
            toHandler.result(c.getExpandedName());
         }
      }
   }

   public final Object getFindData() throws Exception {
      FindData fd = new FindData();
      String name = m_nameField.getText();
      if (name.length() == 0) {
         throw new Exception(ResourceBundleManager.getMessage("resource.one.character.message"));
      }
      boolean caseSensitive = m_nameField.getCaseSensitive();
      try {
         // Not a real regex; make *,? work like more normal search style:
         String fixedRegex = replace(name, '*', ".*");
         fixedRegex = replace(fixedRegex, '?', ".?");
         // Add '^' to indicate only mark start of line (user can always put a '*' in front)
         if (!caseSensitive) {
        	 fd.m_regex = Pattern.compile("^" + fixedRegex + ".*", Pattern.CASE_INSENSITIVE);
         } else {
        	 fd.m_regex = Pattern.compile("^" + fixedRegex + ".*");
         }

         String stn = m_nameField.getSearchType();
         SearchType st = null;
         for (int i = 0; i < m_searchTypes.size(); i++) {
            SearchType t = m_searchTypes.get(i);
            if (t.getLabel().equals(stn)) {
               st = t;
               break;
            }
         }
         fd.m_searchType = st;
      }
      catch (PatternSyntaxException res) {
         throw new Exception("Bad regular expression: " + res.getMessage());
      }
      return fd;
   }

   private static String replace(String base, char old, String news) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < base.length(); i++) {
         char b = base.charAt(i);
         if (b == old) {
            sb.append(news);
         }
         else {
            sb.append(b);
         }
      }
      return sb.toString();
   }

   public final JComponent getFindParametersPanel() {
      return m_panel;
   }

   public final JComponent getPrimaryEntryComponent() {
      return m_nameField;
   }

   public final JComponent getResultRendererComponent(Object result, boolean isSelected) {
	   if (result instanceof FindWindowResult) {
		   result = ((FindWindowResult) result).getClosure();
	   }

      ExpandedName r = (ExpandedName) result;
      m_rendererLabel.setText(r.getLocalName());
      m_rendererLabel.setBackground(GenericFindWindow.getDefaultBackgroundColor(isSelected));
      m_rendererLabel.setForeground(GenericFindWindow.getDefaultForegroundColor(isSelected));
      return m_rendererLabel;
   }

   public final void setEditorChangeListener(ChangeListener cl) {
      m_editorChangeListener = cl;
   }

   private static class FindData {
      public Pattern m_regex; // null if not a regex search, set to mName if is a regex search.
      public SearchType m_searchType;
   }

   private void changed() {
      if (m_editorChangeListener == null) {
         return;
      }
      m_editorChangeListener.stateChanged(new ChangeEvent(this));
   }
}
