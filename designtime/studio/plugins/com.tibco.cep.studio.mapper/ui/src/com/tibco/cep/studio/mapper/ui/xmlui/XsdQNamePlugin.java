package com.tibco.cep.studio.mapper.ui.xmlui;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.DataTree;
import com.tibco.cep.studio.mapper.ui.data.TypeDocumentationPanel;
import com.tibco.cep.studio.mapper.ui.data.basic.DetailsViewFactory;
import com.tibco.cep.studio.mapper.ui.data.bind.find.FindWindowPlugin;
import com.tibco.cep.studio.mapper.ui.data.utils.HorzSizedScrollPane;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;


/**
 * The qname plugin specific for XSD schema components.
 */
public class XsdQNamePlugin extends TnsBasedQNamePlugin {
   private int m_componentType; // The SmComponent type code, i.e. ELEMENT_TYPE, TYPE_TYPE, etc.

   public static final XsdQNamePlugin ELEMENT = new XsdQNamePlugin(SmComponent.ELEMENT_TYPE);
   public static final XsdQNamePlugin TYPE = new XsdQNamePlugin(SmComponent.TYPE_TYPE);
   public static final XsdQNamePlugin ATTRIBUTE = new XsdQNamePlugin(SmComponent.ATTRIBUTE_TYPE);
   public static final XsdQNamePlugin MODEL_GROUP = new XsdQNamePlugin(SmComponent.MODEL_GROUP_TYPE);

   private XsdQNamePlugin(int smComponentType) {
      super(smComponentType, SmNamespace.class);
      m_componentType = smComponentType;
   }

   public String getLocalNameLabel() {
      switch (m_componentType) {
         case SmComponent.ELEMENT_TYPE:
            return DataIcons.getElementLabel();
         case SmComponent.TYPE_TYPE:
            return DataIcons.getTypeLabel();
         case SmComponent.ATTRIBUTE_TYPE:
            return DataIcons.getAttributeLabel();
         case SmComponent.MODEL_GROUP_TYPE:
            return DataIcons.getModelGroupLabel(); //WCETODO add one.
      }
      // if there's an issue, don't crash.
      return "unknown code #" + m_componentType;
   }

   public String getNamespaceLabel() {
      return DataIcons.getSchemaLabel();
   }

   public void selectLocalNameOnGUI(UIAgent uiAgent, ExpandedName name) {
      //WCETODO make it select the right thing in the schema editor.
   }

   public boolean hasPreviewView() {
      return true; // Yes, Yes, I DOOO!
   }

   static class XsdViewFactory extends JTabbedPane implements DetailsViewFactory {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataTree m_tree;
      private TypeDocumentationPanel m_typeDocs;

      public XsdViewFactory(UIAgent uiAgent) {
         DataTree dt = new DataTree(uiAgent);
         m_tree = dt;
         SmSequenceType t = SMDT.VOID;
         dt.setRootType(t);
         HorzSizedScrollPane hsp = new HorzSizedScrollPane(dt);

         TypeDocumentationPanel tdp = new TypeDocumentationPanel(uiAgent);
         m_typeDocs = tdp;
         tdp.set(t);
         addTab(QNamePanelResources.DOCUMENTATION, tdp);
         addTab(QNamePanelResources.TREE, hsp);
      }

      public JComponent getComponentForNode(Object node) {
         SmComponent c = (SmComponent) node;
         SmSequenceType t;
         if (c instanceof SmParticleTerm) {
            t = SmSequenceTypeFactory.create((SmParticleTerm) c);
         }
         else {
            if (c instanceof SmType) {
               t = SmSequenceTypeFactory.createElement((SmType) c);
            }
            else {
               t = SMDT.PREVIOUS_ERROR;
            }
         }
         m_tree.setRootType(t);
         m_typeDocs.set(t);
         return this;
      }

      public void readPreferences(JComponent component, String keyprefix, UIAgent prefWriter) {
      }

      public void writePreferences(JComponent component, String keyprefix, UIAgent prefWriter) {
      }
   }

   public DetailsViewFactory createPreviewView(UIAgent uiAgent) {
      return new XsdViewFactory(uiAgent);
   }

   public FindWindowPlugin createFindWindowPlugin(UIAgent uiAgent) {
      // Not very elegant --- getting the impl here, but it'll work.
      return new XsdFindWindowPlugin(m_componentType, uiAgent.getTnsCache());
   }

   public ExpandedName getNameFromFindResult(String location, Object searchResult) {
      return (ExpandedName) searchResult;
   }
}
