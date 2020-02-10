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
import com.tibco.util.RuntimeWrapException;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmGlobalComponentNotFoundException;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * The content model category for element reference.
 */
public class ElementRefCategory extends ContentModelCategory {
   public static final ContentModelCategory INSTANCE = new ElementRefCategory();

   private ElementRefCategory() {
   }

   public String getAsRootReference(NamespaceContextRegistry namespaceContextRegistry, Object details) {
      Details res = (Details) details;
      return NamespaceManipulationUtils.getOrAddQNameFromExpandedName(res.m_name, namespaceContextRegistry).toString();
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

   public SmSequenceType computeXType(Object node, 
                                      NamespaceContextRegistry nm,
                                      UIAgent uiAgent,
                                      SmComponentProviderEx smCompProvider) {
      String qn = getTypeQName(node, nm, uiAgent);
      QName qqn = new QName(qn);
      ExpandedName en = qqn.getExpandedName(nm);
      SmSequenceType bt = null;
      try {
         bt = SmSequenceTypeSupport.getElement(smCompProvider, en);
      }
      catch (SmGlobalComponentNotFoundException e) {
         throw new RuntimeWrapException(e);
      }
      SmCardinality xo=null;
      if(node instanceof PayloadTreeModelChild)
    	  xo = SmCardinality.create(((PayloadTreeModelChild)node).getMin(), ((PayloadTreeModelChild)node).getMax());
      if(node instanceof PayloadTreeModelParent)
    	  xo = SmCardinality.create(((PayloadTreeModelParent)node).getMin(), ((PayloadTreeModelParent)node).getMax());
      
      if (!xo.equals(SmCardinality.EXACTLY_ONE)) {
         return SmSequenceTypeFactory.createRepeats(bt, xo);
      }
      return bt;
   }

   public XiNode toNode(XiFactory factory, Object node, NamespaceContextRegistry ni) {
		if (node instanceof PayloadTreeModelChild) {
			XiNode el = factory.createElement(XsdSchema.ELEMENT_NAME);
			String qname = getTypeQName(node, ni, null);
			XiAttribute.setStringValue(el, "ref", qname);
			XsdSchema.writeOccurs(el, ((PayloadTreeModelChild)node).getMin(), ((PayloadTreeModelChild)node).getMax());
			return el;
		}
		if (node instanceof PayloadTreeModelParent) {
			XiNode el = factory.createElement(XsdSchema.ELEMENT_NAME);
			String qname = getTypeQName(node, ni, null);
			XiAttribute.setStringValue(el, "ref", qname);
			XsdSchema.writeOccurs(el, ((PayloadTreeModelParent)node).getMin(), ((PayloadTreeModelParent)node).getMax());
			return el;
		}
		return null;
   }

   public PayloadTreeModelParent fromNode(XiNode node, ParameterPayloadEditor ed, NamespaceContextRegistry ni,Object parentNode) {
      if (node.getName().equals(XsdSchema.ELEMENT_NAME)) {
         // we don't handle this one:
         if (XiAttribute.getNode(node, "type") != null) {
            return null;
         }
         ;
         // we do handle this case:
         if (XiAttribute.getNode(node, "ref") == null) {
            return null;
         }
         ;
         String ref = XiAttribute.getStringValue(node, "ref");
         PayloadTreeModelParent pn = new PayloadTreeModelParent(null, null, null, null, ed);
         pn.setContentModelCategory(this);
         readOccursAttributes(pn, node);
         Details d = new Details();

         QName qn = new QName(ref);
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
         d.m_name = en;
         pn.setContentModelDetails(d);

         return pn;
      }
      return null;
   }

   public PayloadTreeModelChild fromXType(SmSequenceType type, SmCardinality oc, ParameterPayloadEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni) {
      int tc = type.getTypeCode();
      if (tc == SmSequenceType.TYPE_CODE_ELEMENT && type.getName() != null) {
    	  PayloadTreeModelChild pn = new PayloadTreeModelChild(null, null, null, null, ed);
         pn.setMin(oc.getMinOccurs());
         pn.setMax(oc.getMaxOccurs());
         pn.setContentModelCategory(this);
         Details d = new Details();
         ExpandedName n = type.getName();
         d.m_name = n;
         pn.setContentModelDetails(d);
         return pn;
      }
      return null;
   }

   public String getDisplayName(Object node) {
		Details res = null;
		if (node instanceof PayloadTreeModelChild) {
			res = (Details) ((PayloadTreeModelChild) node)
					.getContentModelDetails();
		}
		if (node instanceof PayloadTreeModelParent) {
			res = (Details) ((PayloadTreeModelParent) node)
					.getContentModelDetails();
		}
		if (res == null) {
			return "";
		}
		ExpandedName e = res.m_name;
		if (e == null) {
			return "";
		}
		return e.getLocalName();
   }

   private String getTypeQName(Object node, NamespaceContextRegistry mapper, UIAgent uiAgent) {
		if (node instanceof PayloadTreeModelChild) {
			Details res = (Details) ((PayloadTreeModelChild)node).getContentModelDetails();
			ExpandedName ns = res.m_name;
			return NamespaceManipulationUtils.getOrAddQNameFromExpandedName(ns,
					mapper).toString();
		}
		if (node instanceof PayloadTreeModelParent) {
			Details res = (Details) ((PayloadTreeModelChild)node).getContentModelDetails();
			ExpandedName ns = res.m_name;
			return NamespaceManipulationUtils.getOrAddQNameFromExpandedName(ns,
					mapper).toString();
		}
		return null;
   }
   

   public boolean canHandleElementReferences() {
      return true;
   }

   public boolean canHandleRefType(String namespaceURI, String locationURI) {
      return true;
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
      return ParameterEditorResources.ELEMENT_REFERENCE;
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
                                            XsdQNamePlugin.ELEMENT,
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
