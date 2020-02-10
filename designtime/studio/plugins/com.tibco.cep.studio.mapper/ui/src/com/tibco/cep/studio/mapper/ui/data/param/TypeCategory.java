package com.tibco.cep.studio.mapper.ui.data.param;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.mapper.xml.nsutils.ImportRegistry;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * A base super-interface for type categories.  Because it handles both scalars
 * & complex, types and elements, it has alot of methods that only apply in certain
 * cases.<br>
 * WCETODO refactor this so that primitives are handled as just a 2-level choice (not a custom GUI)
 */
public abstract class TypeCategory {
   public abstract JComponent createEditor(ChangeListener changeListener,
                                           Object detailsValue,
                                           UIAgent uiAgent,
                                           NamespaceContextRegistry namespaceContextRegistry,
                                           ImportRegistry importRegistry);

   public abstract Object getEditorValue(JComponent editor);

   public abstract Object createDetails(); // null for none.

   public abstract String getDisplayName();

   public abstract Icon getDisplayIcon();

   public String[] getXsdTypes() {
      return new String[0];
   }

   /**
    * Overloaded for both type & ref.
    */
   public String getTypeQName(Object details, NamespaceContextRegistry mapper, UIAgent uiAgent) {
      // default.
      String pfx = mapper.getOrAddPrefixForNamespaceURI(XSDL.NAMESPACE, "xsd");
      QName qn = new QName(pfx, getXsdType(details));
      return qn.toString();
   }

   /**
    * Overloaded for both type & ref.
    */
   public ExpandedName getTypeExpandedName(Object details) {
      // default.
      return ExpandedName.makeName(XSDL.NAMESPACE, getXsdType(details));
   }

   public String getXsdType(Object details) {
      return "nyi";
   }

   public String getElementName(ParameterNode node) {
      return "" + node.getContentModelDetails();
   }

   public Object readDetails(String typeName) {
      return null;
   }

   /**
    * For a 'ref' element:
    */
   public Object readRefDetails(ExpandedName name) {
      return null;
   }

   /**
    * For debugging/diagnostic purposes only.
    */
   public String toString() {
      // For debugging only.
      return "Type Category: " + getDisplayName();
   }
}
