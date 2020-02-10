package com.tibco.cep.studio.mapper.ui.data;

import java.util.ArrayList;

import javax.swing.Icon;

import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

public class ChoiceTreeNode extends DataTypeTreeNode {
   private final SmSequenceType mChoice;
   private final String mName;
   private final Icon mIcon;
   private final String m_srcXPathIfCoerced;

   public ChoiceTreeNode(SmSequenceType choice, SmCardinality occurs) {
      this(choice, occurs, null);
   }

   /**
    * Creates a ChoiceTreeNode object
    *
    * @param choice            should be instanceof ChoiceXPath
    * @param occurs            contains min & max occurence information
    * @param srcXpathIfCoerced may be null, if choice isn't coerced; otherwise, should
    *                          be xpath representation of substituted-for declaration
    */
   public ChoiceTreeNode(SmSequenceType choice, SmCardinality occurs, String srcXpathIfCoerced) {
      super(occurs);
      mChoice = choice;
      mIcon = DataIcons.getIcon(choice);
      mName = DataIcons.getName(choice);
      m_srcXPathIfCoerced = srcXpathIfCoerced;
   }

   /**
    * Indicates whether or not this node created by coerced substitution
    *
    * @return true IFF this node created by coercion (i.e. user "coerced" or substituted
    *         one declaration for another
    */
   public boolean isSubstituted() {
      return m_srcXPathIfCoerced != null;
   }

   public SmSequenceType getAsXType() {
      return mChoice;//XTypeFactory.createRepeats(XTypeFactory.create(mGroup),XOccurrence.create(mMin,mMax));
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
      SmSequenceType[] choices = SmSequenceTypeSupport.getAllChoices(mChoice);
      ArrayList<DataTypeTreeNode> al = new ArrayList<DataTypeTreeNode>();
      for (int i = 0; i < choices.length; i++) {
         al.add(createNodeForXType(choices[i], SmCardinality.EXACTLY_ONE, null));
      }
      return al.toArray(new BasicTreeNode[0]);
   }

   /**
    * If this node was created via coercion, this method will return the
    * xpath string of the substituted-for type.  For example is "b" is an
    * an abstract element and "c" has been substituted, via coercion,
    * for "b", this method will return "a/b" rather than "a/c".
    *
    * @param isLastStep
    * @param ni
    * @return
    */
   public String getUnsubstitutedXStepName(boolean isLastStep, NamespaceContextRegistry ni) {
      if (!isLastStep || m_srcXPathIfCoerced == null) {
         // no change.
         return getXStepName(isLastStep, ni);
      }
      else {
         String[] str = Utilities.getAsArray(m_srcXPathIfCoerced);
         if (str.length > 0) {
            return str[str.length - 1];
         }
         // Shouldn't happen.
         return "*";
      }
   }

   public String getXStepName(boolean isLastStep, NamespaceContextRegistry namespaceContextRegistry) {
      if (isLastStep) {
         return "*";
      }
      return null;
   }

   public boolean isLeaf() {
      return false;
   }

   public Object getIdentityTerm() {
      return mChoice.getParticleTerm();
   }
}
