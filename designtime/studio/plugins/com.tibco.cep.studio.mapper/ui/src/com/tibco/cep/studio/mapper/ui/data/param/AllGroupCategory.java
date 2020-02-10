package com.tibco.cep.studio.mapper.ui.data.param;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

/**
 * The content model category for 'all', or 'interleave' in XQuery.
 */
public class AllGroupCategory extends ContentModelCategory {
   public static final ContentModelCategory INSTANCE = new AllGroupCategory();

   private AllGroupCategory() {
   }

   public JComponent createEditor(ParameterNode node, ChangeListener changeListener,
                                  Object detailsValue,                                       
                                  UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
      return null;
   }

   public boolean hasType() {
      return false;
   }

   public boolean canHaveName() {
      return false;
   }

   public SmSequenceType computeXType(ParameterNode node, NamespaceContextRegistry nm,
		   UIAgent uiAgent, SmComponentProviderEx smCompProviderEx) {
      SmSequenceType[] cc = node.computeChildrenXType(nm, uiAgent, smCompProviderEx);
      return SmSequenceTypeFactory.createInterleave(cc);
   }

   public ParameterNode fromXType(SmSequenceType type, SmCardinality oc, ParameterEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni) {
      return null;
   }

   public XiNode toNode(XiFactory factory, ParameterNode node, NamespaceContextRegistry ni) {
      XiNode ct = factory.createElement(XsdSchema.ALL_NAME);
      XsdSchema.writeOccurs(ct, node.getMin(), node.getMax());
      writeChildNodes(factory, node, ct, ni);
      return ct;
   }

   public ParameterNode fromNode(XiNode node, ParameterEditor ed, NamespaceContextRegistry ni) {
      if (node.getName().equals(XsdSchema.ALL_NAME)) {
         ParameterNode newnode = new ParameterNode(ed);
         newnode.setContentModelCategory(this);
         readOccursAttributes(newnode, node);
         readChildren(ed, newnode, node, ni);
         return newnode;
      }
      return null;
   }

   public String getDisplayName(ParameterNode node) {
      StringBuffer sb = new StringBuffer();
      sb.append("(");
      for (int i = 0; i < node.getChildCount(); i++) {
         if (i > 0) {
            sb.append(" & ");
         }
         sb.append(node.getChildParameter(i).getDisplayName());
      }
      sb.append(")");
      return sb.toString();
   }

   public Object createDetails(ParameterNode node) {
      return null;
   }

   public Object getEditorValue(ParameterNode node, JComponent c) {
      return null;
   }

   public String getDisplayName() {
      return ParameterEditorResources.ALL;
   }

   public Icon getDisplayIcon(ParameterNode node) {
      return DataIcons.getSequenceIcon(); // need new icon?
   }

   public boolean isLeaf() {
      return false;
   }
}
