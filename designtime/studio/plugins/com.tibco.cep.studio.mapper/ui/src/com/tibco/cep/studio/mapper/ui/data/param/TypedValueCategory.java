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
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

/**
 * The category representing an xsl typed value.
 * (Currently not in use because XSLT editor is not in use)
 */
public class TypedValueCategory extends ContentModelCategory {

   public static final ContentModelCategory INSTANCE = new TypedValueCategory();

   private TypedValueCategory() {
   }

   public JComponent createEditor(ParameterNode node, ChangeListener changeListener,
                                  Object detailsValue, UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry,
                                  ImportRegistry importRegistry) {
      // has primitives:
      return node.getTypeCategory().createEditor(changeListener, detailsValue,
    		  uiAgent,
                                                 namespaceContextRegistry, importRegistry);
   }

   public String getTypeQName(ParameterNode node, NamespaceContextRegistry mapper, UIAgent uiAgent) {
      return node.getTypeCategory().getTypeQName(node.getContentModelDetails(), mapper, uiAgent);
   }

   public Object readDetails(ParameterNode node, String typeName) {
      return node.getTypeCategory().readDetails(typeName);
   }

   public String getDisplayName(ParameterNode node) {
      return "(" + node.getTypeCategory().getDisplayName() + ")";
   }

   public SmSequenceType computeXType(ParameterNode node, NamespaceContextRegistry nm, UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
      String t = node.getTypeCategory().getXsdType(node.getContentModelDetails());
      SmSequenceType base = computeBaseType(t);
      SmCardinality xo = SmCardinality.create(node.getMin(), node.getMax());
      if (!xo.equals(SmCardinality.EXACTLY_ONE)) {
         return SmSequenceTypeFactory.createRepeats(base, xo);
      }
      return base;
   }

   public ParameterNode fromNode(XiNode node, ParameterEditor ed, NamespaceContextRegistry ni) {
      return null;
   }

   public ParameterNode fromXType(SmSequenceType type, SmCardinality oc, ParameterEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni) {
      return null;
   }

   public XiNode toNode(XiFactory factory, ParameterNode node, NamespaceContextRegistry ni) {
      return null;
   }

   private SmSequenceType computeBaseType(String t) {
      if (t == null || t.equals("string")) {
         return SMDT.STRING;
      }
      if (t.equals("int")) {
         return SMDT.INTEGER;
      }
      return SMDT.STRING;
   }

   public boolean hasType() {
      return true;
   }

   public Object createDetails(ParameterNode node) {
      return node.getTypeCategory().createDetails();
   }

   public Object getEditorValue(ParameterNode node, JComponent c) {
      return node.getTypeCategory().getEditorValue(c);
   }

   public String getDisplayName() {
      return "Simple Value";
   }

   public Icon getDisplayIcon(ParameterNode node) {
      if (node == null) {
         return DataIcons.getTypeReferenceIcon();// need another icon.
      }
      return node.getTypeCategory().getDisplayIcon();
   }

   public boolean isLeaf() {
      return true;
   }
}
