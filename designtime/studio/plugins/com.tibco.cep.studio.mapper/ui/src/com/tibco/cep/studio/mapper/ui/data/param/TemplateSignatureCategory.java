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

/**
 * 'fake' category for root of template signature.
 */
public class TemplateSignatureCategory extends ContentModelCategory {

   public static final ContentModelCategory INSTANCE = new TemplateSignatureCategory();

   private TemplateSignatureCategory() {
   }

   public JComponent createEditor(ParameterNode node, ChangeListener changeListener,
                                  Object detailsValue, UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
      return null;
   }

   public Object createDetails(ParameterNode node) {
      return null;
   }

   public ParameterNode createNewChild(ParameterEditor editor) {
      ParameterNode pn = super.createNewChild(editor);
      pn.setContentModelCategory(TypedValueCategory.INSTANCE);
      return pn;
   }

   public boolean hasType() {
      return false;
   }

   public Object getEditorValue(ParameterNode node, JComponent c) {
      return null;
   }

   public String getDisplayName() {
      return "Not-Shown";
   }

   public SmSequenceType computeXType(ParameterNode node, NamespaceContextRegistry nm, UIAgent doc, SmComponentProviderEx smCompProvider) {
      throw new RuntimeException("Not used");
   }

   public XiNode toNode(XiFactory factory, ParameterNode node, NamespaceContextRegistry ni) {
      throw new RuntimeException("Not used");
   }

   public ParameterNode fromNode(XiNode node, ParameterEditor ed, NamespaceContextRegistry ni) {
      throw new RuntimeException("Not used");
   }

   public ParameterNode fromXType(SmSequenceType type, SmCardinality oc, ParameterEditor ed, UIAgent doc, NamespaceContextRegistry ni) {
      throw new RuntimeException("Not used");
   }

   public boolean canHaveName() {
      return false;
   }

   public String getDisplayName(ParameterNode node) {
      return "Not used";
   }

   public Icon getDisplayIcon(ParameterNode node) {
      // not used.
      return DataIcons.getAnonymousSequenceIcon();
   }

   public boolean isLeaf() {
      return false;
   }
}
