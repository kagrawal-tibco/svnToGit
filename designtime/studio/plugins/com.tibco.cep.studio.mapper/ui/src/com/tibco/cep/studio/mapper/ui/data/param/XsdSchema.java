package com.tibco.cep.studio.mapper.ui.data.param;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * Constains constants and helper functions for the {@link ParameterEditor}
 */
class XsdSchema {
   public static final ExpandedName ANY_NAME = makeName("any");
   public static final ExpandedName ELEMENT_NAME = makeName("element");
   public static final ExpandedName ATTRIBUTE_NAME = makeName("attribute");
   public static final ExpandedName COMPLEX_TYPE_NAME = makeName("complexType");
   public static final ExpandedName SEQUENCE_NAME = makeName("sequence");
   public static final ExpandedName CHOICE_NAME = makeName("choice");
   public static final ExpandedName ALL_NAME = makeName("all");
   public static final ExpandedName GROUP_NAME = makeName("group");

   private static final ExpandedName makeName(String localName) {
      return ExpandedName.makeName(XSDL.NAMESPACE, localName);
   }

   public static void writeOccurs(XiNode on, int min, int max) {
      if (min != 1) {
         XiAttribute.setStringValue(on, "minOccurs", "" + min);
      }
      if (max != 1) {
         String mval;
         if (max == SmParticle.UNBOUNDED) {
            mval = "unbounded";
         }
         else {
            mval = "" + max;
         }
         XiAttribute.setStringValue(on, "maxOccurs", mval);
      }
   }

   public static void writeAttrOccurs(XiNode on, int min, int max) {
      if (min == 1) {
         XiAttribute.setStringValue(on, "use", "required");
      }
      // 'optional' is the default, so don't write anything.
   }
}
