package com.tibco.cep.studio.mapper.ui.data.xpath;

import com.tibco.cep.studio.mapper.ui.data.DataIcons;


/**
 * String constants for XPath editor.
 */
public class XPathEditResources {

   public static final String XPATH_FORMULA_LABEL;
   public static final String AVT_FORMULA_LABEL;

   public static final String EVALUATING_LABEL;
   public static final String DATA_LABEL;
   public static final String FUNCTIONS_LABEL;
   public static final String CONSTANTS_LABEL;
   public static final String CONSTANT_VALUE_LABEL;
   public static final String COMMENT_VALUE_LABEL;
   public static final String NO_ELEMENT_LABEL;
   public static final String EVALCONTEXT_LABEL;
   public static final String EVALERROR_LABEL;
   public static final String EVALWARNING_LABEL;
   public static final String EVALTO_LABEL;
   public static final String FIX_LABEL;
   public static final String SEVERITY_LABEL;
   public static final String EXAMPLE_HEADER;
   public static final String RETURNS_HEADER;
   public static final String ALSO_SEE_HEADER;

   public static final String TITLE;

   /**
    * Initialized all the resource strings in 1 fell-swoop.
    */
   static {
      TITLE = getBuilderString("title");
      EVALUATING_LABEL = getBuilderString("evaluating.label");
      DATA_LABEL = getBuilderString("data.label");
      FUNCTIONS_LABEL = getBuilderString("functions.label");
      CONSTANTS_LABEL = getBuilderString("constants.label");
      NO_ELEMENT_LABEL = getBuilderString("noelement.label");
      EVALCONTEXT_LABEL = getBuilderString("evalcontext.label");
      EVALERROR_LABEL = getBuilderString("evalerror.label");
      EVALWARNING_LABEL = getBuilderString("evalwarning.label");
      EVALTO_LABEL = getBuilderString("evalto.label");
      XPATH_FORMULA_LABEL = getBuilderString("formula.label");
      AVT_FORMULA_LABEL = getBuilderString("avtformula.label");
      CONSTANT_VALUE_LABEL = getBuilderString("constant.label");
      COMMENT_VALUE_LABEL = getBuilderString("comment.label");
      FIX_LABEL = getBuilderString("fix.label");
      SEVERITY_LABEL = getBuilderString("severity.label");
      EXAMPLE_HEADER = getBuilderString("example.header");
      RETURNS_HEADER = getBuilderString("returns.header");
      ALSO_SEE_HEADER = getBuilderString("also.see.header");
   }

   static String getBuilderString(String key) {
      String fullKey = "ae.xpath.builder." + key;
      return DataIcons.getString(fullKey);
   }
}
