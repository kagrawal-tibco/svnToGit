package com.tibco.cep.studio.mapper.ui.data.param;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.xmlui.QNamePanel;
import com.tibco.cep.studio.mapper.ui.xmlui.XsdQNamePlugin;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

public class TypeRefCategory extends TypeCategory {
   public static final TypeCategory INSTANCE = new TypeRefCategory();

   private TypeRefCategory() {
   }

   static class Details {
      public ExpandedName m_name = ExpandedName.makeName("");
   }

   public String getElementName(ParameterNode node) {
      Details res = (Details) node.getContentModelDetails();
      String e = res.m_name.getLocalName();
      if (e == null) {
         return "";
      }
      return e;
   }

   public String getTypeQName(Object details, NamespaceContextRegistry mapper, UIAgent uiAgent) {
      Details res = (Details) details;
      return NamespaceManipulationUtils.getOrAddQNameFromExpandedName(res.m_name, mapper).toString();
   }

   public ExpandedName getTypeExpandedName(Object details) {
      Details res = (Details) details;
      return res.m_name;
   }

   /**
    * For a 'ref' element:
    */
   public Object readRefDetails(ExpandedName name) {
      Details ptr = new Details();
      ptr.m_name = name;
      return ptr;
   }

   public Icon getDisplayIcon() {
      return DataIcons.getTypeReferenceIcon();
   }

   public String getDisplayName() {
      return ParameterEditorResources.TYPE_REFERENCE;
   }

   public Object createDetails() {
      Details d = new Details();
      return d;
   }

   public JComponent createEditor(final ChangeListener cl,
                                  Object details,
                                  UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry,
                                  ImportRegistry importRegistry) {
      Details old = (Details) details;

      if (namespaceContextRegistry == null) {
         // for errors only.
         return new JLabel("Null namespace importer");
      }
      if (importRegistry == null) {
         // for errors only.
         return new JLabel("Null import registry");
      }
      final QNamePanel ptp = new QNamePanel(uiAgent,
                                            XsdQNamePlugin.TYPE,
                                            namespaceContextRegistry,
                                            importRegistry);
      ptp.setExpandedName(old.m_name);
      ptp.addPropertyChangeListener(QNamePanel.VALUE_PROPERTY, new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent evt) {
            cl.stateChanged(new ChangeEvent(ptp));
         }
      });
      return ptp;
   }

   public Object getEditorValue(JComponent c) {
      QNamePanel ptp = (QNamePanel) c;
      Details ret = new Details();
      ret.m_name = ptp.getExpandedName();
      return ret;
   }
}
