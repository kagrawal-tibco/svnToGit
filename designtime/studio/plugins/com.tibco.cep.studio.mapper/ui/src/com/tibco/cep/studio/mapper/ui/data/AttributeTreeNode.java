package com.tibco.cep.studio.mapper.ui.data;

import java.util.Iterator;

import javax.swing.Icon;

import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.tns.parse.TnsError;
import com.tibco.xml.tns.parse.TnsErrorSeverity;

/**
 * The {@link DataTypeTreeNode} which displays xml attributes.
 */
public class AttributeTreeNode extends DataTypeTreeNode {
   private final SmSequenceType mAttribute;
   private final String mName;
   private final Icon mIcon;
   private final boolean mIsLeaf;
   private String m_error;

   @SuppressWarnings("rawtypes")
public AttributeTreeNode(SmSequenceType attribute, SmCardinality occurs) {
      super(occurs);
      mAttribute = attribute;
      mIsLeaf = true;
      mName = DataIcons.getName(attribute);
      mIcon = DataIcons.getIcon(attribute);
      if (mAttribute.getParticleTerm() instanceof SmAttribute) {
         SmAttribute pt = (SmAttribute) mAttribute.getParticleTerm();
         Iterator errs = pt.getErrors(TnsErrorSeverity.ERROR, false);
         while (errs.hasNext()) {
            TnsError e = (TnsError) errs.next();
            m_error = e.getMessage();
            break;
         }
      }
   }

   public boolean isSubstituted() {
      return false;
   }

   public SmSequenceType getAsXType() {
      return mAttribute;//XTypeFactory.createRepeats(mAttribute,mOccurrence);
   }

   public boolean hasChildContent() {
      return ((DataTree) getTree()).nodeHasChildContent(this);
   }

   public Icon getIcon() {
      return mIcon;
   }

   public boolean isEditable() {
      return false;
   }

   public String getName() {
      return mName;
   }

   public BasicTreeNode[] buildChildren() {
      return EMPTY_ARRAY; // attributes never have children.
   }

   public String getXStepName(boolean isLastStep, NamespaceContextRegistry namespaceContextRegistry) {
      ExpandedName ename = mAttribute.getName();
      if (ename != null && ename.getNamespaceURI() != null && ename.getNamespaceURI().length() > 0) {
         String pfx = namespaceContextRegistry.getOrAddPrefixForNamespaceURI(ename.getNamespaceURI());
         String name;
         if (pfx == null || pfx.length() == 0) {
            name = "@" + ename.getLocalName();
         }
         else {
            name = "@" + pfx + ":" + ename.getLocalName();
         }
         return name;
      }
      return mName;
   }

   public String getLineError() {
      return m_error;
   }

   public boolean isLeaf() {
      return mIsLeaf;
   }

   public Object getIdentityTerm() {
      return mAttribute.getParticleTerm();
   }
}
