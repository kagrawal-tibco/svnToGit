package com.tibco.cep.studio.mapper.ui.data;

import javax.swing.Icon;

import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeNode;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

/**
 * The display for a text node in the type tree.
 */
public class TextTreeNode extends DataTypeTreeNode {
   private final SmSequenceType mDoc;
   private final String mName;

   public TextTreeNode(SmSequenceType type, SmCardinality occurrence) {
      super(occurrence);
      mDoc = type;
      mName = DataIcons.getName(type);
   }

   public boolean isSubstituted() {
      return false;
   }

   public SmSequenceType getAsXType() {
      return mDoc;
   }

   public boolean hasChildContent() {
      return ((DataTree) getTree()).nodeHasChildContent(this);
   }

   public Icon getIcon() {
      return DataIcons.getStringIcon();
   }

   public boolean isEditable() {
      return false;
   }

   public String getName() {
      return mName;
   }

   public BasicTreeNode[] buildChildren() {
      return new BasicTreeNode[0];
   }


//   private static boolean isSubstitution(SmDataComponent dc) {
//      if (!(dc instanceof SmElement)) {
//         return false;
//      }
//      SmElement el = (SmElement) dc;
//      if (!el.isAbstract()) {
//         return false;
//      }
//      return true;
//   }

   public String getXStepName(boolean isLastStep, NamespaceContextRegistry namespaceContextRegistry) {
      /*if (mElement.getNamespace()!=null) {
          String pfx = namespaceMapper.registerOrGetPrefixForNamespaceURI(mElement.getNamespace());
          String name;
          if (pfx==null || pfx.length()==0) {
              name = mElement.getName();
          } else {
              name = pfx + ":" + mElement.getName();
          }
          if (mElement instanceof SmAttribute) {
              return "@" + name;
          } else {
              return name;
          }
      }
      return mName;*/

      // jtb: changed return of "/" to "text()"
      return "text()";
//      return "/";
   }

   public boolean isLeaf() {
      return true;
   }

   public Object getIdentityTerm() {
      return null;
   }
}
