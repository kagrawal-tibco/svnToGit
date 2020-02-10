package com.tibco.cep.studio.mapper.ui.data.param;

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

   public JComponent createEditor(ParameterNode node, ChangeListener changeListener,
                                  Object detailsValue, UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
      Details old = (Details) detailsValue;

      Editor e = new Editor(old.validationType, changeListener);

      return e;
   }

   public XiNode toNode(XiFactory factory, ParameterNode node, NamespaceContextRegistry ni) {
      XiNode r = factory.createElement(XsdSchema.ANY_NAME);
      XsdSchema.writeOccurs(r, node.getMin(), node.getMax());
      Details d = (Details) node.getContentModelDetails();
      if (d.validationType != null) {
         XiAttribute.setStringValue(r, "processContents", d.validationType);
      }
      return r;
   }

   public ParameterNode fromXType(SmSequenceType type, SmCardinality xo, ParameterEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni) {
      if (type == SMDT.ELEMENT_NODE) //WCETODO need better test!
      {
         ParameterNode pn = new ParameterNode(ed);
         pn.setMin(xo.getMinOccurs());
         pn.setMax(xo.getMaxOccurs());
         return pn;
      }
      return null;
   }

   public ParameterNode fromNode(XiNode node, ParameterEditor ed, NamespaceContextRegistry ni) {
      if (node.getName().equals(XsdSchema.ANY_NAME)) {
         ParameterNode newnode = new ParameterNode(ed);
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

   public SmSequenceType computeXType(ParameterNode node, NamespaceContextRegistry nm, UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
      return SMDT.ELEMENT_NODE;
   }

   public Object createDetails(ParameterNode node) {
      return new Details();
   }

   public String getDisplayName(ParameterNode node) {
      return "<Any Element>";
   }

   public boolean canHaveName() {
      return false;
   }

   public Object getEditorValue(ParameterNode node, JComponent editor) {
      Editor e = (Editor) editor;
      Details d = new Details();
      d.validationType = e.getValue();
      return d;
   }

   public String getDisplayName() {
      return ParameterEditorResources.ANY_ELEMENT;
   }

   public Icon getDisplayIcon(ParameterNode node) {
      return DataIcons.getWildcardIcon();
   }
}
