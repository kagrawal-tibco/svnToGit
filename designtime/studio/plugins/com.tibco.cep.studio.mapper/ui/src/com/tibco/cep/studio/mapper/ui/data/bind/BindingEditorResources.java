package com.tibco.cep.studio.mapper.ui.data.bind;

import com.tibco.cep.studio.mapper.ui.data.DataIcons;


/**
 * Helper class for maintaining/loading all string resources for binding editor.
 */
public class BindingEditorResources {
   private BindingEditorResources() {
   }

   // Menu related:
   public static final String SHOW_FORMULA_IN_LINE;

   public static final String SHOW_CONNECTED;

   public static final String FIND;
   public static final String FIND_IN_PROJECT;

   // Coercion related:
   public static final String COERCE_TITLE;

   // Statement dialog:
   public static final String EDIT_STATEMENT_TITLE;
   public static final String NEW_STATEMENT_TITLE;
   public static final String MOVE_INTO_NEW_STATEMENT_TITLE;

   public static final String STATEMENT_TYPE;

   // Attribute/Element Editor related:
   public static final String TYPE_SUBSTITUTION;
   public static final String FORMULA_CONTENT;
   public static final String EXPLICIT_NIL;
   public static final String CONSTANT_VALUE;
   public static final String FORMULA;

   public static final String COPY_MODE;

   public static final String REQUIRED_TO_REQUIRED;
   public static final String OPTIONAL_TO_OPTIONAL;
   public static final String NIL_TO_NIL;
   public static final String OPTIONALNIL_TO_OPTIONALNIL;
   public static final String OPTIONAL_TO_NIL;
   public static final String NIL_TO_OPTIONAL;

   // Stylesheet related:
   public static final String EXCLUDE_RESULT_PREFIXES;
   public static final String EXCLUDED_RESULT_PREFIXES;

   // Copy of related:
   public static final String COPY_ALL_NAMESPACES;

   // Doc tab related:
   public static final String TYPE_DOCUMENTATION;
   public static final String TYPE_EXPECTED;
   public static final String TYPE_COMPUTED;

   public static final String ERROR_FORMULA_HAS_ERRORS;

   public static final String BINDING_FIXER_TITLE;

   // Statement menu stuff:
   public static final String STATEMENT_MENU;
   public static final String SURROUND_WITH_CHOICE_MENU;
   public static final String SURROUND_WITH_IF_MENU;
   public static final String SURROUND_WITH_FOR_EACH_MENU;
   public static final String SURROUND_WITH_FOR_EACH_GROUP_MENU;
   public static final String MAKE_COPY_MENU;
   public static final String ADD_GROUP;

   public static final String NUMBER_OF_WHENS;
   public static final String INCLUDE_OTHERWISE;

   public static final String CHOICE_PARAMETERS_TITLE;

   // Very misc:
   public static final String FILTER_MARKER;

   static {
      SHOW_FORMULA_IN_LINE = loadBindingString("formulainline");
      SHOW_CONNECTED = loadBindingString("showconnected");
      FIND = loadBindingString("find");
      FIND_IN_PROJECT = loadBindingString("findinproject");

      COERCE_TITLE = loadBindingTitle("coercion");

      FORMULA = loadBindingString("formula");

      TYPE_SUBSTITUTION = loadBindingString("typesubstitution");
      FORMULA_CONTENT = loadBindingString("formulacontent");
      EXPLICIT_NIL = loadBindingString("explicitnil");
      CONSTANT_VALUE = loadBindingString("constantvalue");

      COPY_ALL_NAMESPACES = loadBindingString("copyallnamespaces");

      TYPE_DOCUMENTATION = loadBindingString("typedocumentation");
      TYPE_EXPECTED = loadBindingString("typeexpected");
      TYPE_COMPUTED = loadBindingString("typecomputed");

      ERROR_FORMULA_HAS_ERRORS = loadBindingString("formulahaserrors");

      BINDING_FIXER_TITLE = loadBindingTitle("fixer");

      EDIT_STATEMENT_TITLE = loadBindingTitle("editstatement");
      NEW_STATEMENT_TITLE = loadBindingTitle("newstatement");
      MOVE_INTO_NEW_STATEMENT_TITLE = loadBindingTitle("moveintonewstatement");

      STATEMENT_TYPE = loadBindingTitle("statementtype");

      COPY_MODE = loadBindingString("copymode");
      REQUIRED_TO_REQUIRED = loadBindingString("requiredtorequired");
      OPTIONAL_TO_OPTIONAL = loadBindingString("optionaltooptional");
      OPTIONAL_TO_NIL = loadBindingString("optionaltonil");
      NIL_TO_OPTIONAL = loadBindingString("niltooptional");
      NIL_TO_NIL = loadBindingString("niltonil");
      OPTIONALNIL_TO_OPTIONALNIL = loadBindingString("optionalniltooptionalnil");

      EXCLUDE_RESULT_PREFIXES = loadBindingString("excluderesultprefixes");
      EXCLUDED_RESULT_PREFIXES = loadBindingString("excludedresultprefixes");

      STATEMENT_MENU = loadBindingMenu("statement");
      SURROUND_WITH_CHOICE_MENU = loadBindingMenu("surroundwithchoice");
      SURROUND_WITH_IF_MENU = loadBindingMenu("surroundwithif");
      SURROUND_WITH_FOR_EACH_GROUP_MENU = loadBindingMenu("surroundwithforeachgroup");
      SURROUND_WITH_FOR_EACH_MENU = loadBindingMenu("surroundwithforeach");
      ADD_GROUP = loadBindingMenu("addgroup");

      MAKE_COPY_MENU = loadBindingMenu("makecopy");

      INCLUDE_OTHERWISE = loadBindingString("includeotherwise");
      NUMBER_OF_WHENS = loadBindingString("numberofwhens");

      CHOICE_PARAMETERS_TITLE = loadBindingTitle("choiceparameters");

      FILTER_MARKER = loadBindingString("filtermarker");
   }

   /**
    * Helper method that loads a binding string by just the 'local' name --- it auto-prepends the common
    * part and 'label' suffix.
    *
    * @param key The local part of the resource key.
    */
   private static String loadBindingString(String key) {
      String fullKey = "ae.binding.editor." + key + ".label";
      return DataIcons.getString(fullKey);
   }

   /**
    * Helper method that loads a binding string by just the 'local' name --- it auto-prepends the common
    * part and 'title' suffix.
    *
    * @param key The local part of the resource key.
    */
   private static String loadBindingTitle(String key) {
      String fullKey = "ae.binding.editor." + key + ".title";
      return DataIcons.getString(fullKey);
   }

   /**
    * Helper method that loads a binding string by just the 'local' name --- it auto-prepends the common
    * part and 'title' suffix.
    *
    * @param key The local part of the resource key.
    */
   private static String loadBindingMenu(String key) {
      String fullKey = "ae.binding.editor." + key + ".menu";
      return DataIcons.getString(fullKey);
   }
}
