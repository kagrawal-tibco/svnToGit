package com.tibco.cep.studio.mapper.ui.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;

import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;
import com.tibco.xml.tns.cache.TnsDependencyProvider;
import com.tibco.xml.tns.cache.TnsDocumentProvider;
import com.tibco.xml.tns.impl.SubstitutionGroupFinder;

/**
 * The base class for nodes in the tree which displays data types (XTypes/schemas).
 */
public abstract class DataTypeTreeNode extends BasicTreeNode {
   private SmCardinality mOccurrence;

   public abstract SmSequenceType getAsXType();

   protected static final DataTypeTreeNode[] EMPTY_ARRAY = new DataTypeTreeNode[0];

   public DataTypeTreeNode(SmCardinality oc) {
      mOccurrence = oc;
   }

   public static DataTypeTreeNode createNodeForXType(SmSequenceType type,
                                                     SmCardinality baseOccurs,
                                                     String xpathMarker) {
      return createNodeForXType(type, baseOccurs, xpathMarker, null, false);
   }

   /**
    * Creates a node for an XType.
    *
    * @param type        The type, cannot be null.
    * @param baseOccurs  The occurrence, non null, for the node (will be multiplied if further occurs are found) - use {@link com.tibco.xml.xpath.type.XCardinality#EXACTLY_ONE} as a default.
    * @param xpathMarker An optional (may be null) marker indicating the original xpath (before substitution).
    * @return
    */
   public static DataTypeTreeNode createNodeForXType(SmSequenceType type,
                                                     SmCardinality baseOccurs,
                                                     String xpathMarker,
                                                     TnsDocumentProvider docProvider,
                                                     boolean subsAsChoiceGroups) {
      if (type == null) {
         // Rather than crashing....
         return new ErrorTreeNode("Internal Error: Null type passed in.");
      }
      int tc = type.getTypeCode();
      if (tc == SmSequenceType.TYPE_CODE_DOCUMENT_ROOT) {
         return new DocumentRootTreeNode(type, baseOccurs, xpathMarker);
      }
      if (tc == SmSequenceType.TYPE_CODE_PAREN) {
    	  // this had to change because there is no SmSequenceTypeFactory.createParen(SmSequenceType, String)
    	  // where String is the user message.  This API exists in XTypeFactory (ryanh).
         xpathMarker = type.getUserMessage() == null ? xpathMarker : type.getUserMessage(); // for any subs, may have been marked this way.
         return createNodeForXType(type.getFirstChildComponent(), baseOccurs, xpathMarker);
      }
      if (tc == SmSequenceType.TYPE_CODE_REPEATS) {
         SmCardinality xo = baseOccurs.multiplyBy(type.getOccurrence());
         return createNodeForXType(type.getFirstChildComponent(), xo, xpathMarker);
      }
      if (tc == SmSequenceType.TYPE_CODE_ELEMENT) {
         return new ElementTreeNode(type, baseOccurs, xpathMarker);
      }
      if (tc == SmSequenceType.TYPE_CODE_ATTRIBUTE) {
         return new AttributeTreeNode(type, baseOccurs);
      }
      if (tc == SmSequenceType.TYPE_CODE_SEQUENCE) {
         return new AnonymousSequenceTreeNode(type, baseOccurs, xpathMarker);
      }
      if (tc == SmSequenceType.TYPE_CODE_CHOICE) {
         return new ChoiceTreeNode(type, baseOccurs, xpathMarker);
      }
      if (tc == SmSequenceType.TYPE_CODE_INTERLEAVE) {
         return new InterleaveTreeNode(type, baseOccurs, null);
      }
      if (tc == SmSequenceType.TYPE_CODE_VOID) {
         return new EmptyTreeNode();
      }
      if (tc == SmSequenceType.TYPE_CODE_TEXT) {
         return new TextTreeNode(type, baseOccurs);
      }
      if (tc == SmSequenceType.TYPE_CODE_SIMPLE) {
         return new PrimitiveTypeTreeNode(type, baseOccurs);
      }
      if (tc == SmSequenceType.TYPE_CODE_PREVIOUS_ERROR) {
         String msg = type.getUserMessage();
         if (msg == null || msg.length() == 0) {
            msg = "error";
         }
         return new ErrorTreeNode(msg);
      }
      return new ErrorTreeNode("unknown: " + tc);
   }

   /**
    * Adds the child-axis nodes for the given xtype.
    *
    * @param type               the type for which to create the child nodes
    * @param docProvider        used for location an element's substitution group; if null,
    *                           substition groups cannot be located and no choice gruops will be created
    * @param subsAsChoiceGroups if true will create a choice model group for an element's
    *                           substition group
    * @return
    */
   @SuppressWarnings("rawtypes")
public static DataTypeTreeNode[] createChildNodesForXType(SmSequenceType type,
                                                             TnsDocumentProvider docProvider,
                                                             boolean subsAsChoiceGroups) {

      SmParticleTerm t = type.getParticleTerm();
      boolean mixed = false;
      if (t instanceof SmElement) {
         SmElement elem = (SmElement) t;
         if (elem.getType().isMixedContent()) {
            mixed = true;
         }
         if (subsAsChoiceGroups && docProvider instanceof TnsDependencyProvider) {
            // Only look for dependencies if the namesapce in non-null.
            if (elem.getNamespace() != null) {
               TnsDependencyProvider dependencyProvider = (TnsDependencyProvider) docProvider;
               SortedSet sSet =
                       SubstitutionGroupFinder.findSubstitutionGroups(dependencyProvider,
                                                                      elem);
               if (sSet.size() > 0) {
                  for (Iterator it = sSet.iterator(); it.hasNext();) {
                     it.next();
                  }
               }
            }
         }
      }
      ArrayList<DataTypeTreeNode> al = new ArrayList<DataTypeTreeNode>();
      SmSequenceType c = SmSequenceTypeSupport.createAttributeAndChildAndTypedValueSequenceExcludeMagic(type);
      if (type.isNillabilityKnown() && type.isNillable()) {
         addNodesForXType(SMDT.XSI_NIL_ATTRIBUTE, al, false, null, docProvider, subsAsChoiceGroups);
      }
      addNodesForXType(c, al, mixed, null, docProvider, subsAsChoiceGroups);
      return al.toArray(new DataTypeTreeNode[al.size()]);
   }

   /**
    * Adds the child-axis nodes for the given xtype.
    */
   public static DataTypeTreeNode[] createChildNodesForXType(SmSequenceType type) {
      return createChildNodesForXType(type, null, false);
   }

   private static void addNodesForXType(SmSequenceType type, ArrayList<DataTypeTreeNode> al,
                                        boolean mixed, String xpathMarker,
                                        TnsDocumentProvider docProvider,
                                        boolean subsAsChoiceGroups) {
      int tc = type.getTypeCode();
      if (tc == SmSequenceType.TYPE_CODE_SEQUENCE || tc == SmSequenceType.TYPE_CODE_INTERLEAVE) {
         addNodesForXType(type.getFirstChildComponent(), al, mixed, xpathMarker, docProvider, subsAsChoiceGroups);
         addNodesForXType(type.getSecondChildComponent(), al, mixed, xpathMarker, docProvider, subsAsChoiceGroups);
         return;
      }
      if (tc == SmSequenceType.TYPE_CODE_PAREN) {
    	  String xpath = type.getUserMessage() == null ? xpathMarker : type.getUserMessage(); // for any subs, may have been marked this way.
         addNodesForXType(type.getFirstChildComponent(), al, mixed, xpath, docProvider, subsAsChoiceGroups);
         return;
      }
      if (tc == SmSequenceType.TYPE_CODE_TEXT && !mixed) {
         return;
      }
      if (tc == SmSequenceType.TYPE_CODE_REPEATS && type.getOccurrence().equals(SmCardinality.EXACTLY_ONE)) {
         addNodesForXType(type.getFirstChildComponent(), al, mixed, xpathMarker, docProvider, subsAsChoiceGroups);
         return;
      }
      al.add(createNodeForXType(type, SmCardinality.EXACTLY_ONE, xpathMarker));
   }

   /**
    * Gets the xpath step for the node WITHOUT wildcard substitutions (NOTE: only affects last step).<br>
    * Default implementation simple calls {@link #getXStepName}.
    *
    * @return The xpath step.
    */
   public String getUnsubstitutedXStepName(boolean isLastStep, NamespaceContextRegistry ni) {
      return getXStepName(isLastStep, ni);
   }

   public final int getMin() {
      return mOccurrence.getMinOccurs();
   }

   public final int getMax() {
      return mOccurrence.getMaxOccurs();
   }
}
