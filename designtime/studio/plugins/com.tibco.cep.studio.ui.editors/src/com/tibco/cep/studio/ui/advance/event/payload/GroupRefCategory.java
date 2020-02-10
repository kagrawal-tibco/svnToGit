package com.tibco.cep.studio.ui.advance.event.payload;

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
import com.tibco.cep.studio.mapper.ui.data.param.ParameterEditorResources;
import com.tibco.cep.studio.mapper.ui.xmlui.QNamePanel;
import com.tibco.cep.studio.mapper.ui.xmlui.XsdQNamePlugin;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.PayloadTreeModelChild;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.PayloadTreeModelParent;
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

   public String getDisplayName(Object node) {
		if (node instanceof PayloadTreeModelChild) {
			Details res = (Details) ((PayloadTreeModelChild)node).getContentModelDetails();
			String e = res.m_name.getLocalName();
			if (e == null) {
				return "";
			}
			return e;
		}
		if (node instanceof PayloadTreeModelParent) {
			Details res = (Details) ((PayloadTreeModelParent)node).getContentModelDetails();
			String e = res.m_name.getLocalName();
			if (e == null) {
				return "";
			}
			return e;
		}
		return null;
   }

   private String getTypeQName(Object node, NamespaceContextRegistry mapper, UIAgent uiAgent) {
		if (node instanceof PayloadTreeModelChild) {
			Details res = (Details) ((PayloadTreeModelChild)node).getContentModelDetails();
			return NamespaceManipulationUtils.getOrAddQNameFromExpandedName(res.m_name, mapper).toString();
		}
		if (node instanceof PayloadTreeModelParent) {
			Details res = (Details) ((PayloadTreeModelParent)node).getContentModelDetails();
			return NamespaceManipulationUtils.getOrAddQNameFromExpandedName(res.m_name, mapper).toString();
		}
		return null;
   }

   public SmSequenceType computeXType(Object node, NamespaceContextRegistry nm, UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
      throw new IllegalArgumentException("Not supported");
   }

   public boolean canHandleElementReferences() {
      return true;
   }

   public boolean canHandleRefType(String namespaceURI, String locationURI) {
      return true;
   }

   public XiNode toNode(XiFactory factory, Object node, NamespaceContextRegistry ni) {
		if (node instanceof PayloadTreeModelChild) {
			XiNode ct = factory.createElement(XsdSchema.GROUP_NAME);
			XsdSchema.writeOccurs(ct, ((PayloadTreeModelChild)node).getMin(), ((PayloadTreeModelChild)node).getMax());
			String qname = getTypeQName(node, ni, null);
			XiAttribute.setStringValue(ct, "ref", qname);
			return ct;
		}
		if (node instanceof PayloadTreeModelParent) {
			XiNode ct = factory.createElement(XsdSchema.GROUP_NAME);
			XsdSchema.writeOccurs(ct, ((PayloadTreeModelParent)node).getMin(), ((PayloadTreeModelParent)node).getMax());
			String qname = getTypeQName(node, ni, null);
			XiAttribute.setStringValue(ct, "ref", qname);
			return ct;
		}
		return null;
   }

   public PayloadTreeModelParent fromNode(XiNode node, ParameterPayloadEditor ed, NamespaceContextRegistry ni,Object parentNode) {
      if (node.getName().equals(XsdSchema.GROUP_NAME)) {
    	  PayloadTreeModelParent pn = new PayloadTreeModelParent(null, null, null, null, ed);
         pn.setContentModelCategory(this);
         readOccursAttributes(pn, node);
         readGroupRefAttrs(pn, node, ni);
         return pn;
      }
      return null;
   }

   private void readGroupRefAttrs(Object node, XiNode attrs, NamespaceContextRegistry ni) {
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
            	if(node instanceof PayloadTreeModelChild)
            		((PayloadTreeModelChild)node).setContentModelDetails(details);
            	if(node instanceof PayloadTreeModelParent)
            		((PayloadTreeModelParent)node).setContentModelDetails(details);
            }
         }
      }
   }

   public ParameterPayloadNode fromXType(SmSequenceType type, SmCardinality oc, ParameterPayloadEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni) {
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

   public Icon getDisplayIcon(ParameterPayloadNode node) {
      return DataIcons.getReferenceIcon();
   }

   public String getDisplayName() {
      return ParameterEditorResources.GROUP_REFERENCE;
   }

   public Object createDetails(ParameterPayloadNode node) {
      Details d = new Details();
      return d;
   }

   public JComponent createEditor(ParameterPayloadNode node,
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

   public Object getEditorValue(ParameterPayloadNode node, JComponent c) {
      QNamePanel ptp = (QNamePanel) c;
      Details ret = new Details();
      ret.m_name = ptp.getExpandedName();
      return ret;
   }

}
