package com.tibco.cep.studio.ui.advance.event.payload;

import javax.swing.Icon;
import javax.swing.JComponent;
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
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

public class SequenceCategory extends ContentModelCategory {

   public static final ContentModelCategory INSTANCE = new SequenceCategory();

   private SequenceCategory() {
   }

   public JComponent createEditor(ParameterPayloadNode node, ChangeListener changeListener,
                                  Object detailsValue, UIAgent uiAgent,
                                  NamespaceContextRegistry namespaceContextRegistry, ImportRegistry importRegistry) {
      return null;
   }

   public Object createDetails(ParameterPayloadNode node) {
      return null;
   }

   public boolean hasType() {
      return false;
   }

   public Object getEditorValue(ParameterPayloadNode node, JComponent c) {
      return null;
   }

   public String getDisplayName() {
      return ParameterEditorResources.SEQUENCE;
   }

   public SmSequenceType computeXType(Object node, NamespaceContextRegistry nm, UIAgent uiAgent, SmComponentProviderEx smCompProvider) {
	   if(node instanceof PayloadTreeModelChild){
		   SmSequenceType[] cc = ((PayloadTreeModelChild)node).computeChildrenXType(nm, uiAgent, smCompProvider);
		   return SmSequenceTypeFactory.createSequence(cc);
	   }
	   if(node instanceof PayloadTreeModelParent){
		   SmSequenceType[] cc = ((PayloadTreeModelParent)node).computeChildrenXType(nm, uiAgent, smCompProvider);
		   return SmSequenceTypeFactory.createSequence(cc);
	   }
	   return null;
   }

   public XiNode toNode(XiFactory factory, Object node, NamespaceContextRegistry ni) {
	  if(node instanceof PayloadTreeModelChild){
		  XiNode ct = factory.createElement(XsdSchema.SEQUENCE_NAME);
      	  XsdSchema.writeOccurs(ct, ((PayloadTreeModelChild)node).getMin(), ((PayloadTreeModelChild)node).getMax());
      	  writeChildNodes(factory, node, ct, ni);
      	  return ct;
      }
   	  if(node instanceof PayloadTreeModelParent){
		  XiNode ct = factory.createElement(XsdSchema.SEQUENCE_NAME);
   	      XsdSchema.writeOccurs(ct, ((PayloadTreeModelParent)node).getMin(), ((PayloadTreeModelParent)node).getMax());
   	      writeChildNodes(factory, node, ct, ni);
   	      return ct;
      }
   	  return null;
   
   }

   public PayloadTreeModelParent fromNode(XiNode node, ParameterPayloadEditor ed, NamespaceContextRegistry ni,Object parentNode) {
      if (node.getName().equals(XsdSchema.SEQUENCE_NAME)) {
    	  PayloadTreeModelParent newnode = new PayloadTreeModelParent(null, null, null, null, ed);
         newnode.setContentModelCategory(this);
         readOccursAttributes(newnode, node);
         readChildren(ed, newnode, node, ni);
         return newnode;
      }
      return null;
   }

   public ParameterPayloadNode fromXType(SmSequenceType type, SmCardinality oc, ParameterPayloadEditor ed, UIAgent uiAgent, NamespaceContextRegistry ni) {
      return null;
   }

   public boolean canHaveName() {
      return false;
   }

   public String getDisplayName(Object node) {
	   
		if (node instanceof PayloadTreeModelChild) {
			StringBuffer sb = new StringBuffer();
			sb.append("(");
			for (int i = 0; i < ((PayloadTreeModelChild)node).getChildCount(); i++) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(((PayloadTreeModelChild)node).getChildParameter(i).getDisplayName());
			}
			sb.append(")");
			return sb.toString();
		}
		if (node instanceof PayloadTreeModelParent) {
			StringBuffer sb = new StringBuffer();
			sb.append("(");
			for (int i = 0; i < ((PayloadTreeModelParent)node).getChildCount(); i++) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(((PayloadTreeModelParent)node).getChildParameter(i).getDisplayName());
			}
			sb.append(")");
			return sb.toString();
		}
		return null;
   }

   public Icon getDisplayIcon(ParameterPayloadNode node) {
      return DataIcons.getAnonymousSequenceIcon();
   }

   public boolean isLeaf() {
      return false;
   }
}
