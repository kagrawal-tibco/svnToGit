package com.tibco.cep.studio.mapper.ui.data;

import java.util.ArrayList;

import javax.swing.Icon;

import com.tibco.cep.mapper.xml.xdata.xpath.Coercion;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

public class ExprContextTreeNode extends BasicTreeNode {
   private final ExprContext m_context;
private CoercionSet mCoercionSet;

   public ExprContextTreeNode(ExprContext dc, CoercionSet set) {
      if (set != null) {
    	  mCoercionSet = set;
         m_context = set.applyTo(dc, true);
      }
      else {
         m_context = dc;
      }
   }

   public Icon getIcon() {
      return DataIcons.getIfIcon(); // whatever, not used.
   }

   public String getName() {
      return "context"; // whatever, never seen.
   }

   public boolean isEditable() {
      return false;
   }

   public BasicTreeNode[] buildChildren() {
      VariableDefinitionList varDefs = m_context.getVariables();
      VariableDefinition[] defs = varDefs.getGlobalVariables();
      //VariableDefinition[] locals = varDefs.getLocalVariables();
      ArrayList<DataTypeTreeNode> nodes = new ArrayList<DataTypeTreeNode>();
      boolean showInput = !SmSequenceTypeSupport.isVoid(m_context.getInput());
      if (showInput) {
         DataTypeTreeNode ft = DataTypeTreeNode.createNodeForXType(m_context.getInput(), SmCardinality.EXACTLY_ONE, null);
         nodes.add(new ContextTypeTreeNode(ft));
      }
      for (int i = 0; i < defs.length; i++) {
         VariableDefinition varDef = defs[i];
         // this had to change because there is no SmSequenceTypeFactory.createParen(SmSequenceType, String)
         // where String is the user message.  This API exists in XTypeFactory (ryanh).
         String xpath = null;
         if (mCoercionSet!=null) {
        	 String var = "$" + varDef.getName();
        	 @SuppressWarnings("rawtypes")
        	 ArrayList coercionList = mCoercionSet.getCoercionList();
        	 for (Object object : coercionList) {
				Coercion c = (Coercion) object;
				String path = c.getXPath();
				if (var.equals(path)) {
					xpath = path;
					break;
				}
			}
//             varSet = mCoercionSet.extract("$" + varDef.getName());
         }
         DataTypeTreeNode ft = DataTypeTreeNode.createNodeForXType(varDef.getType(), SmCardinality.EXACTLY_ONE, xpath);
         nodes.add(new VariableDefinitionTreeNode(varDef, ft));
      }
      boolean showCurrentGroup = !SmSequenceTypeSupport.isVoid(m_context.getCurrentGroup());
      if (showCurrentGroup) {
         DataTypeTreeNode ft = DataTypeTreeNode.createNodeForXType(m_context.getCurrentGroup(), SmCardinality.EXACTLY_ONE, null);
         nodes.add(new FunctionDefinitionTreeNode("current-group", ft));
      }
      boolean showCurrent = !SmSequenceTypeSupport.isVoid(m_context.getCurrent()) && !SmSequenceTypeSupport.isPreviousError(m_context.getCurrent());
      if (showCurrent) {
         DataTypeTreeNode ft = DataTypeTreeNode.createNodeForXType(m_context.getCurrent(), SmCardinality.EXACTLY_ONE, null);
         nodes.add(new FunctionDefinitionTreeNode("current", ft));
      }
      /*if (locals.length>0)
      {
          ret[defs.length+contextOffset] = new LocalVariablesTreeNode(new VariableDefinitionList(locals));
      }*/
      return nodes.toArray(new BasicTreeNode[nodes.size()]);
   }

   public String getXStepName(boolean isLastStep, NamespaceContextRegistry namespaceContextRegistry) {
      // (It's the root, not a real step)
      return null;
   }

   public boolean isLeaf() {
      return false;
   }

   public Object getIdentityTerm() {
      return null;
   }

   public String toString() {
      return "ExprContext node";
   }
}
