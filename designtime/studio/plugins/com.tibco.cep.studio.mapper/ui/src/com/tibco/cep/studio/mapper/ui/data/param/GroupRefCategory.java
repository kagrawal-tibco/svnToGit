package com.tibco.cep.studio.mapper.ui.data.param;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.JComponent;
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
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmSequenceType;

/**
 * The content model category for group (model group) reference.
 */
public class GroupRefCategory extends ContentModelCategory {
   public static final ContentModelCategory INSTANCE = new GroupRefCategory();

   private GroupRefCategory() {
   }

   static class Details {
      public ExpandedName m_name = ExpandedName.makeName("");
   }

   public boolean canHaveName() {
      return false;
   }

   public boolean hasType() {
      return false;
   }

   public String getDisplayName(ParameterNode node) {
      Details res = (Details) node.getContentModelDetails();
      String e = res.m_name.getLocalName();
      if (e == null) {
         return "";
      }
      return e;
   }

   private String getTypeQName(ParameterNode node, NamespaceContextRegistry mapper, UIAgent uiAgent) {
      Details res = (Details) node.getContentModelDetails();
      return NamespaceManipulationUtils.getOrAddQNameFromExpandedName(res.m_name, mapper).toString();
   }

   public SmSequenceType computeXType(ParameterNode node, NamespaceContextRegistry nm, UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
      throw new IllegalArgumentException("Not supported");
   }

   public boolean canHandleElementReferences() {
      return true;
   }

   public boolean canHandleRefType(String namespaceURI, String locationURI) {
      return true;
   }

   public XiNode toNode(XiFactory factory, ParameterNode node, NamespaceContextRegistry ni) {
      XiNode ct = factory.createElement(XsdSchema.GROUP_NAME);
      XsdSchema.writeOccurs(ct, node.getMin(), node.getMax());
      String qname = getTypeQName(node, ni, null);
      XiAttribute.setStringValue(ct, "ref", qname);
      return ct;
   }

   public ParameterNode fromNode(XiNode node, ParameterEditor ed, NamespaceContextRegistry ni) {
      if (node.getName().equals(XsdSchema.GROUP_NAME)) {
         ParameterNode pn = new ParameterNode(ed);
         pn.setContentModelCategory(this);
         readOccursAttributes(pn, node);
         readGroupRefAttrs(pn, node, ni);
         return pn;
      }
      return null;
   }

   private void readGroupRefAttrs(ParameterNode node, XiNode attrs, NamespaceContextRegistry ni) {
      for (XiNode atAttr = attrs.getFirstAttribute(); atAttr != null; atAttr = atAttr.getNextSibling()) {
         String rawName = atAttr.getName().getLocalName();
         String str = atAttr.getStringValue();

         if ("ref".equals(rawName)) {
            QName qn = new QName(str);
            ExpandedName en;
            try {
               en = qn.getExpandedName(ni);
               if (en == null) {
                  en = ExpandedName.makeName(qn.getLocalName());
               }
            }
            catch (Exception e) {
               en = ExpandedName.makeName(qn.getLocalName());
            }
            ContentModelCategory cat = GroupRefCategory.INSTANCE;

            Object details = cat.readRefDetails(en);
            if (details != null) {
               node.setContentModelDetails(details);
            }
         }
      }
   }

   public ParameterNode fromXType(SmSequenceType type, SmCardinality oc, ParameterEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni) {
      return null;
   }

   /**
    * For a 'ref' element:
    */
   public Object readRefDetails(ExpandedName name) {
      Details ptr = new Details();
      ptr.m_name = name;
      return ptr;
   }

   public Icon getDisplayIcon(ParameterNode node) {
      return DataIcons.getReferenceIcon();
   }

   public String getDisplayName() {
      return ParameterEditorResources.GROUP_REFERENCE;
   }

   public Object createDetails(ParameterNode node) {
      Details d = new Details();
      return d;
   }

   public JComponent createEditor(ParameterNode node,
                                  final ChangeListener cl,
                                  Object details,
                                  UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry,
                                  ImportRegistry importRegistry) {
      Details old = (Details) details;

      final QNamePanel ptp = new QNamePanel(uiAgent,
                                            XsdQNamePlugin.MODEL_GROUP,
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

   public Object getEditorValue(ParameterNode node, JComponent c) {
      QNamePanel ptp = (QNamePanel) c;
      Details ret = new Details();
      ret.m_name = ptp.getExpandedName();
      return ret;
   }
}
