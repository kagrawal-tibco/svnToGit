package com.tibco.cep.studio.ui.advance.event.payload;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.param.ParameterEditorResources;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.PayloadTreeModelChild;
import com.tibco.cep.studio.ui.forms.advancedEventPayloadEditor.PayloadTreeModelParent;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * The {@link ContentModelCategory} for the xsd:any (a.k.a. Wildcard!)
 */
public class WildcardCategory extends ContentModelCategory {

   public static final ContentModelCategory INSTANCE = new WildcardCategory();

   private static final String VALIDATION_TYPE_STRICT = "strict";
   private static final String VALIDATION_TYPE_LAX = "lax";
   private static final String VALIDATION_TYPE_SKIP = "skip";

   private WildcardCategory() {
   }

   static class Details {
      public String validationType = null;
   }

   static class Editor extends JPanel {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_value;

      public Editor(String startingValue, final ChangeListener cl) {
         super(new BorderLayout());
         final JComboBox jcb = new JComboBox();
         jcb.addItem(ParameterEditorResources.DEFAULT_VALIDATION);
         jcb.addItem(ParameterEditorResources.LAX);
         jcb.addItem(ParameterEditorResources.SKIP);
         jcb.addItem(ParameterEditorResources.STRICT);
         JLabel label = new JLabel(ParameterEditorResources.PROCESS_CONTENTS_LABEL + ":  ");
         jcb.setSelectedItem(ParameterEditorResources.DEFAULT_VALIDATION);
         if (startingValue != null) {
            if (startingValue.equals(VALIDATION_TYPE_LAX)) {
               jcb.setSelectedItem(ParameterEditorResources.LAX);
            }
            if (startingValue.equals(VALIDATION_TYPE_SKIP)) {
               jcb.setSelectedItem(ParameterEditorResources.SKIP);
            }
            if (startingValue.equals(VALIDATION_TYPE_STRICT)) {
               jcb.setSelectedItem(ParameterEditorResources.STRICT);
            }
         }
         m_value = startingValue;

         JPanel panel = new JPanel(new BorderLayout());
         panel.add(label, BorderLayout.WEST);
         panel.add(jcb);
         jcb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               String itm = (String) jcb.getSelectedItem();
               if (itm.equals(ParameterEditorResources.DEFAULT_VALIDATION)) {
                  m_value = null;
               }
               if (itm.equals(ParameterEditorResources.LAX)) {
                  m_value = VALIDATION_TYPE_LAX;
               }
               if (itm.equals(ParameterEditorResources.SKIP)) {
                  m_value = VALIDATION_TYPE_SKIP;
               }
               if (itm.equals(ParameterEditorResources.STRICT)) {
                  m_value = VALIDATION_TYPE_STRICT;
               }
               cl.stateChanged(new ChangeEvent(this));
            }
         });
         add(panel, BorderLayout.NORTH);
      }

      public String getValue() {
         return m_value;
      }
   }

   public JComponent createEditor(ParameterPayloadNode node, ChangeListener changeListener,
                                  Object detailsValue, UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
      Details old = (Details) detailsValue;

      Editor e = new Editor(old.validationType, changeListener);

      return e;
   }

   public XiNode toNode(XiFactory factory, Object node, NamespaceContextRegistry ni) {
		if (node instanceof PayloadTreeModelChild) {
			XiNode r = factory.createElement(XsdSchema.ANY_NAME);
			XsdSchema.writeOccurs(r, ((PayloadTreeModelChild)node).getMin(), ((PayloadTreeModelChild)node).getMax());
			Details d = (Details) ((PayloadTreeModelChild)node).getContentModelDetails();
			if (d.validationType != null) {
				XiAttribute.setStringValue(r, "processContents",
						d.validationType);
			}
			return r;
		}
		if (node instanceof PayloadTreeModelParent) {
			XiNode r = factory.createElement(XsdSchema.ANY_NAME);
			XsdSchema.writeOccurs(r, ((PayloadTreeModelParent)node).getMin(), ((PayloadTreeModelParent)node).getMax());
			Details d = (Details) ((PayloadTreeModelParent)node).getContentModelDetails();
			if (d.validationType != null) {
				XiAttribute.setStringValue(r, "processContents",
						d.validationType);
			}
			return r;
		}
		return null;
   }

   public PayloadTreeModelChild fromXType(SmSequenceType type, SmCardinality xo, ParameterPayloadEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni) {
      if (type == SMDT.ELEMENT_NODE) //WCETODO need better test!
      {
    	  PayloadTreeModelChild pn = new PayloadTreeModelChild(null, null, null, null, ed);
         pn.setMin(xo.getMinOccurs());
         pn.setMax(xo.getMaxOccurs());
         return pn;
      }
      return null;
   }

   public PayloadTreeModelParent fromNode(XiNode node, ParameterPayloadEditor ed, NamespaceContextRegistry ni,Object parentNode) {
      if (node.getName().equals(XsdSchema.ANY_NAME)) {
    	  PayloadTreeModelParent newnode = new PayloadTreeModelParent(null, null, null, null, ed);
         newnode.setContentModelCategory(this);
         readOccursAttributes(newnode, node);
         String val = XiAttribute.getStringValue(node, "processContents");
         Details d = new Details();
         d.validationType = val;
         newnode.setContentModelDetails(d);
         return newnode;
      }
      return null;
   }

   public boolean hasType() {
      return false;
   }

   public SmSequenceType computeXType(ParameterPayloadNode node, NamespaceContextRegistry nm, UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
      return SMDT.ELEMENT_NODE;
   }

   public Object createDetails(ParameterPayloadNode node) {
      return new Details();
   }

   public String getDisplayName(Object node) {
      return "<Any Element>";
   }

   public boolean canHaveName() {
      return false;
   }

   public Object getEditorValue(ParameterPayloadNode node, JComponent editor) {
      Editor e = (Editor) editor;
      Details d = new Details();
      d.validationType = e.getValue();
      return d;
   }

   public String getDisplayName() {
      return ParameterEditorResources.ANY_ELEMENT;
   }

   public Icon getDisplayIcon(ParameterPayloadNode node) {
      return DataIcons.getWildcardIcon();
   }

@Override
public SmSequenceType computeXType(Object node, NamespaceContextRegistry nm,
		UIAgent uiAgent, SmComponentProviderEx smComponentProviderEx) {
	// TODO Auto-generated method stub
	return null;
}


}
