package com.tibco.cep.studio.mapper.ui.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.MissingResourceException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.border.Border;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.studio.mapper.ui.StudioStrings;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmDataComponent;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.SmWildcard;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;
import com.tibco.xml.xdata.UtilitySchema;

/**
 * Convenient place to get Icons for data types:
 */
public class DataIcons {
   private static boolean INITIALIZED = false;
   // Icon for window titles
   private static Icon ICON_WINDOW_TITLE;
   // XML content model Sequence (not normal-people sequence)
   private static Icon ICON_SEQUENCE;
   // A sequence not appearing at the top level:
   private static Icon ICON_ANONYMOUS_SEQUENCE;
   // normal-people array, used for data.
   private static Icon ICON_ARRAY;
   private static Icon ICON_CHOICE;
   private static Icon ICON_ANY;
   private static Icon ICON_NIL;
   private static Icon ICON_WILDCARD;
   private static Icon ICON_STRING;
   private static Icon ICON_INTEGER;
   private static Icon ICON_REAL;
   private static Icon ICON_BOOLEAN;
   private static Icon ICON_BINARY;
   private static Icon ICON_DATE;
   private static Icon ICON_JAVA;

   private static Icon ICON_ATTRIBUTE;
   private static Icon ICON_ELEMENT;

   private static HashMap<String, Icon> PRIMITIVE_ICON_MAP = new HashMap<String, Icon>(); // String->Icon, (For now...)

   private static Icon ICON_EDIT; // i.e. the Pencil.
   private static Icon ICON_EDIT_XPATH;
   private static Icon ICON_MOVE_UP;
   private static Icon ICON_MOVE_DOWN;
   private static Icon ICON_MOVE_LEFT;
   private static Icon ICON_MOVE_RIGHT;
   private static Icon ICON_DELETE;
   private static Icon ICON_ADD;
   private static Icon ICON_ADD_CHILD;
   private static Icon ICON_BLANK;
   private static Icon ICON_COPY;
   private static Icon ICON_TYPE_COPY;

   private static Icon ICON_UNDO;
   private static Icon ICON_REDO;

   private static Icon ICON_ANY_SUB;
   private static Icon ICON_ANY_ABSTRACT;
   private static Icon ICON_ANY_SUBSTITUTED;
   private static Icon ICON_IF;
   private static Icon ICON_FOR_EACH;
   private static Icon ICON_TEXT;
   private static Icon ICON_VALUE_OF;
   private static Icon ICON_CALL_TEMPLATE;
   private static Icon ICON_APPLY_TEMPLATES;
   private static Icon ICON_FOR_EACH_GROUP;
   private static Icon ICON_GROUP_BY;
   private static Icon ICON_SORT;
   private static Icon ICON_CHOOSE;
   private static Icon ICON_WHEN;
   private static Icon ICON_OTHERWISE;
   private static Icon ICON_STATEMENT;
   private static Icon ICON_SOURCE_COMMENT;
   private static Icon ICON_GENERATE_COMMENT;
   private static Icon ICON_GENERATE_PI;
   private static Icon ICON_ERROR;
   private static Icon ICON_INLINE_HELP;
   private static Icon ICON_INLINE_EDIT;
   private static Icon ICON_DATA_MODE;
   private static Icon ICON_VARIABLE;
   private static Icon ICON_FIX;

   /**
    * Special icon for TypeTreeEditor's reference to external classes/elements.
    */
   private static Icon ICON_REFERENCE;

   /**
    * Special icon for TypeTreeEditor's reference to external classes/elements.
    */
   private static Icon ICON_TYPE_REFERENCE;

   private static String STRING_SELECT_ALL;
   private static String STRING_DESELECT_ALL;

   // Strings that are common:
   private static String STRING_ELEMENT;
   private static String STRING_ATTRIBUTE;
   private static String STRING_MODEL_GROUP;
   private static String STRING_TYPE;
   private static String STRING_SCHEMA;
   private static String STRING_PREFIX;
   private static String STRING_NAMESPACE;
   private static String STRING_TARGET_NAMESPACE;
   private static String STRING_NO_NAMESPACE;
   private static String STRING_LOCAL_NAME;
   private static String STRING_OCCURRENCE;
   private static String STRING_OCCURRENCES;
   private static String STRING_EMPTY;
   private static String STRING_XPATH;

   private static String STRING_LITERALEXPLICIT_REPRESENTATION;
   private static String STRING_LITERALEXPLICIT_LITERAL;
   private static String STRING_LITERALEXPLICIT_EXPLICIT;
   private static String STRING_LITERALEXPLICIT_SEPARATE_NAMESPACE;

   private static String STRING_COPYOFPANEL_COPY_NAMESPACES;

   private static String STRING_VARIABLEPANEL_VARIABLE_NAME;
   private static String STRING_VARIABLEPANEL_USE_SELECT;

   private static Border LARGE_SPACE_BORDER;

   // Start XSD primitives:
   public static final String XSD_STRING;
   public static final String XSD_NORMALIZED_STRING;
   public static final String XSD_TOKEN;
   public static final String XSD_LANGUAGE;
   public static final String XSD_NAME;
   public static final String XSD_NCNAME;
   public static final String XSD_QNAME;
   public static final String XSD_NMTOKEN;
   public static final String XSD_NMTOKENS;
   public static final String XSD_ID;
   public static final String XSD_IDREF;
   public static final String XSD_IDREFS;
   public static final String XSD_ENTITY;
   public static final String XSD_ENTITIES;

   /**
    * Generic binary isn't an actual type; just a 'name' that's common to both base64 and hex.
    */
   public static final String XSD_GENERIC_BINARY;
   public static final String XSD_BASE64BINARY;
   public static final String XSD_HEXBINARY;

   public static final String XSD_BOOLEAN;

   public static final String XSD_BYTE;
   public static final String XSD_SHORT;
   public static final String XSD_INT;
   public static final String XSD_LONG;
   public static final String XSD_UNSIGNED_BYTE;
   public static final String XSD_UNSIGNED_SHORT;
   public static final String XSD_UNSIGNED_INT;
   public static final String XSD_UNSIGNED_LONG;

   public static final String XSD_INTEGER;
   public static final String XSD_POSITIVE_INTEGER;
   public static final String XSD_NEGATIVE_INTEGER;
   public static final String XSD_NONPOSITIVE_INTEGER;
   public static final String XSD_NONNEGATIVE_INTEGER;

   public static final String XSD_FLOAT;
   public static final String XSD_DOUBLE;
   public static final String XSD_DECIMAL;

   /**
    * Not a real type; it is a category name.
    */
   public static final String XSD_GENERIC_DATE_TIME;
   public static final String XSD_TIME;
   public static final String XSD_DATE;
   public static final String XSD_DATE_TIME;
   public static final String XSD_DURATION;
   public static final String XSD_DAY;
   public static final String XSD_MONTH;
   public static final String XSD_YEAR;
   public static final String XSD_YEAR_MONTH;
   public static final String XSD_MONTH_DAY;

   public static final String XSD_ANY_URI;
   public static final String JAVA_NATIVE_OBJECT_REFERENCE_TYPE;

    static {
        ResourceBundleManager.addResourceBundle("com.tibco.cep.studio.mapper.ui.data.Resources", null);
        ResourceBundleManager.addResourceBundle("com.tibco.cep.studio.mapper.ui.data.bind.Resources", null);
    }

   /**
    * Gets a display name for the given type.
    *
    * @param xtype The type.
    * @return The display name, never null.
    */
   public static String getName(SmSequenceType xtype) {
      return SmSequenceTypeSupport.getDisplayName(xtype);
   }

   public static Icon getWindowTitleIcon() {
	   initialize();
	   return ICON_WINDOW_TITLE;
   }
   
   public static Icon getArrayIcon() {
      initialize();
      return ICON_ARRAY;
   }

   public static Icon getSequenceIcon() {
      initialize();
      return ICON_SEQUENCE;
   }

   public static Icon getAnonymousSequenceIcon() {
      initialize();
      return ICON_ANONYMOUS_SEQUENCE;
   }

   public static Icon getChoiceIcon() {
      initialize();
      return ICON_CHOICE;
   }

   /**
    * Overlay icon for something that has been substituted.
    */
   public static Icon getAnyAbstractIcon() {
      initialize();
      return ICON_ANY_ABSTRACT;
   }

   /**
    * Overlay icon for something that has been substituted.
    */
   public static Icon getAnySubstitutedIcon() {
      initialize();
      return ICON_ANY_SUBSTITUTED;
   }

   /**
    * Icon for any substitution button.
    */
   public static Icon getAnySubIcon() {
      initialize();
      return ICON_ANY_SUB;
   }

   public static Icon getAnyIcon() {
      initialize();
      return ICON_ANY;
   }

   public static Icon getNilIcon() {
      initialize();
      return ICON_NIL;
   }

   public static Icon getWildcardIcon() {
      initialize();
      return ICON_WILDCARD;
   }

   public static Icon getStatementIcon() {
      initialize();
      return ICON_STATEMENT;
   }

   public static Icon getIfIcon() {
      initialize();
      return ICON_IF;
   }

   public static Icon getValueOfIcon() {
      initialize();
      return ICON_VALUE_OF;
   }

   public static Icon getTextIcon() {
      initialize();
      return ICON_TEXT;
   }

   public static Icon getForEachIcon() {
      initialize();
      return ICON_FOR_EACH;
   }

   public static Icon getSortIcon() {
      initialize();
      return ICON_SORT;
   }

   public static Icon getCallTemplateIcon() {
      initialize();
      return ICON_CALL_TEMPLATE;
   }

   public static Icon getApplyTemplatesIcon() {
      initialize();
      return ICON_APPLY_TEMPLATES;
   }

   public static Icon getForEachGroupIcon() {
      initialize();
      return ICON_FOR_EACH_GROUP;
   }

   public static Icon getGroupByIcon() {
      initialize();
      return ICON_GROUP_BY;
   }

   public static Icon getChooseIcon() {
      initialize();
      return ICON_CHOOSE;
   }

   public static Icon getWhenIcon() {
      initialize();
      return ICON_WHEN;
   }

   public static Icon getOtherwiseIcon() {
      initialize();
      return ICON_OTHERWISE;
   }

   public static Icon getCopyIcon() {
      initialize();
      return ICON_COPY;
   }

   public static Icon getTypeCopyIcon() {
      initialize();
      return ICON_TYPE_COPY;
   }

   public static Icon getElementIcon() {
      initialize();
      return ICON_ELEMENT;
   }

   public static Icon getAttributeIcon() {
      initialize();
      return ICON_ATTRIBUTE;
   }

   public static Icon getStringIcon() {
      initialize();
      return ICON_STRING;
   }

   public static Icon getIntegerIcon() {
      initialize();
      return ICON_INTEGER;
   }

   public static Icon getRealIcon() {
      initialize();
      return ICON_REAL;
   }

   public static Icon getBinaryIcon() {
      initialize();
      return ICON_BINARY;
   }

   public static Icon getErrorIcon() {
      initialize();
      if(ICON_ERROR == null) {
         ICON_ERROR = ResourceBundleManager.getIcon("ae.form.field.validation.error.icon", DataIcons.class.getClassLoader());//WCETODO make designer have this public static. //getIcon("ae.data.Erro");  //getIcon("ae.data.Error");
      }
      return ICON_ERROR;
   }

   public static Icon getBooleanIcon() {
      initialize();
      return ICON_BOOLEAN;
   }

   public static Icon getJavaIcon() {
      initialize();
      return ICON_JAVA;
   }

   public static Icon getFixIcon() {
      initialize();
      return ICON_FIX;
   }

   public static Icon getDateIcon() {
      initialize();
      return ICON_DATE;
   }

   public static Icon getReferenceIcon() {
      initialize();
      return ICON_REFERENCE;
   }

   public static Icon getTypeReferenceIcon() {
      initialize();
      return ICON_TYPE_REFERENCE;
   }

   public static Icon getDataModeIcon() {
      initialize();
      return ICON_DATA_MODE;
   }

   public static Icon getBlankIcon() {
      initialize();
      return ICON_BLANK;
   }

   public static Icon getVariableIcon() {
      initialize();
      return ICON_VARIABLE;
   }

   /**
    * An icon representing a comment in source code.
    */
   public static Icon getSourceCommentIcon() {
      return ICON_SOURCE_COMMENT;
   }

   /**
    * An icon representing a statement generating a comment (i.e. in xslt, xsl:comment).
    */
   public static Icon getGenerateCommentIcon() {
      return ICON_GENERATE_COMMENT;
   }

   /**
    * An icon representing a statement generating a PI (in xslt).
    */
   public static Icon getGeneratePIIcon() {
      return ICON_GENERATE_PI;
   }

   /**
    * @deprecated Use {@link DesignerStrings#OK}.
    */
   public static String getOKLabel() {
      return StudioStrings.OK;
   }

   /**
    * @deprecated Use {@link DesignerStrings#CANCEL}.
    */
   public static String getCancelLabel() {
      return StudioStrings.CANCEL;
   }

   /**
    * The string label for an XML/XSD type, capitalized.
    */
   public static String getTypeLabel() {
      initialize();
      return STRING_TYPE;
   }

   /**
    * The string label for an XML/XSD element, capitalized.
    */
   public static String getElementLabel() {
      initialize();
      return STRING_ELEMENT;
   }

   /**
    * The string label for an XML/XSD attribute, capitalized.
    */
   public static String getAttributeLabel() {
      initialize();
      return STRING_ATTRIBUTE;
   }

   /**
    * The string label for an XSD model group, capitalized.
    */
   public static String getModelGroupLabel() {
      initialize();
      return STRING_MODEL_GROUP;
   }

   /**
    * The string label for a schema, capitalized.
    */
   public static String getSchemaLabel() {
      initialize();
      return STRING_SCHEMA;
   }

   /**
    * The string label for a generic XML namespace, capitalized.
    */
   public static String getNamespaceLabel() {
      initialize();
      return STRING_NAMESPACE;
   }

   /**
    * The string label for a generic XML namespace, capitalized.
    */
   public static String getPrefixLabel() {
      initialize();
      return STRING_PREFIX;
   }

   /**
    * The string label for a generic XML namespace, capitalized.
    */
   public static String getTargetNamespaceLabel() {
      initialize();
      return STRING_TARGET_NAMESPACE;
   }

   /**
    * The string label for a generic XML namespace, capitalized.
    */
   public static String getNoNamespaceLabel() {
      initialize();
      return STRING_NO_NAMESPACE;
   }

   /**
    * The string label for a generic XML local-name, capitalized.
    */
   public static String getLocalNameLabel() {
      initialize();
      return STRING_LOCAL_NAME;
   }

   /**
    * The string label for 'Occurrence' (singular)
    */
   public static String getOccurrenceLabel() {
      initialize();
      return STRING_OCCURRENCE;
   }

   /**
    * The string label for 'Occurrences' (plural)
    */
   public static String getOccurrencesLabel() {
      initialize();
      return STRING_OCCURRENCES;
   }

   /**
    * The string label for empty, i.e. XPath empty, for i10n.
    */
   public static String getEmptyLabel() {
      initialize();
      return STRING_EMPTY;
   }

   /**
    * Gets a label for a generic XPath.
    *
    * @return The label
    */
   public static String getXPathLabel() {
      initialize();
      return STRING_XPATH;
   }

   /**
    * Gets a label for a literal/explicit representation radio buttons.
    *
    * @return The label
    */
   public static String getLiteralExplicitPanelRepresentationLabel() {
      initialize();
      return STRING_LITERALEXPLICIT_REPRESENTATION;
   }

   /**
    * Gets a label for a literal/explicit representation radio buttons.
    *
    * @return The label
    */
   public static String getLiteralExplicitPanelLiteralLabel() {
      initialize();
      return STRING_LITERALEXPLICIT_LITERAL;
   }

   /**
    * Gets a label for a literal/explicit representation radio buttons.
    *
    * @return The label
    */
   public static String getLiteralExplicitPanelExplicitLabel() {
      initialize();
      return STRING_LITERALEXPLICIT_EXPLICIT;
   }

   /**
    * Gets a label for a literal/explicit representation check box label.
    *
    * @return The label
    */
   public static String getLiteralExplicitPanelSeparateNamespaceLabel() {
      initialize();
      return STRING_LITERALEXPLICIT_SEPARATE_NAMESPACE;
   }

   /**
    * Gets a label for a copyOfPanel separate namespaces label.
    *
    * @return The label
    */
   public static String getCopyOfPanelCopyNamespacesLabel() {
      initialize();
      return STRING_COPYOFPANEL_COPY_NAMESPACES;
   }

   /**
    * Gets a label for a VariablePanel variable name label.
    *
    * @return The label
    */
   public static String getVariablePanelVariableNameLabel() {
      initialize();
      return STRING_VARIABLEPANEL_VARIABLE_NAME;
   }

   /**
    * Gets a label for a VariablePanel use select label.
    *
    * @return The label
    */
   public static String getVariablePanelUseSelectLabel() {
      initialize();
      return STRING_VARIABLEPANEL_USE_SELECT;
   }

   /**
    * Gets the right one of {@link #getOccurrenceLabel} and {@link #getOccurrencesLabel}.
    *
    * @param isPlural If it should be 'occurrences' instead of 'occurrence'
    */
   public static String getOccurrencesLabel(boolean isPlural) {
      return isPlural ? getOccurrencesLabel() : getOccurrenceLabel();
   }

   public static String getSelectAllLabel() {
      initialize();
      return STRING_SELECT_ALL;
   }

   public static String getDeselectAllLabel() {
      initialize();
      return STRING_DESELECT_ALL;
   }

   public static boolean getIsAnonymous(SmSequenceType type) {
      SmParticleTerm c = type.getParticleTerm();
      return c instanceof SmModelGroup || c instanceof SmWildcard;
   }

   public static String getName(SmParticleTerm term) {
      if (term instanceof SmDataComponent) {
         /*if (UtilitySchema.isErrorMarkerType(((SmDataComponent)term).getType())) {
             String msg;
             if (term instanceof SmElement) {
                 msg = UtilitySchema.extractErrorStringFromMarkerElement((SmElement)term);
             } else {
                 msg = null;
             }
             if (msg==null) {
                 return "<< error >>;";
             } else {
                 return "<< error >> : " + msg;
             }
         }*/
         if (term instanceof SmAttribute) {
            String tname = term.getName();
            if (tname.equals("xml:lang")) { // special case, not properly handled by SmStuff
               return "@lang";
            }
            return "@" + term.getName();
         }
         if (term == UtilitySchema.CONTENT_ELEMENT) {
            // one special case, for mixed text representation.
            return "text()";
         }
         return term.getName();
      }
      if (term instanceof SmModelGroup) {
         return getGroupName((SmModelGroup) term);
      }
      if (term instanceof SmWildcard) {
         return "<Any Element>";
      }
      return "unknown: " + term.getClass().getName();
   }

   @SuppressWarnings("rawtypes")
private static String getGroupName(SmModelGroup clz) {
      if (clz.getName() != null) {
         return clz.getName();
      }
      Iterator i = clz.getParticles();
      boolean isChoice = clz.getCompositor() == SmModelGroup.CHOICE;
      StringBuffer sb = new StringBuffer();
      sb.append("(");
      int ct = 0;
      while (i.hasNext()) {
         SmParticle particle = (SmParticle) i.next();
         if (ct > 0) {
            if (isChoice) {
               sb.append(" | ");
            }
            else {
               sb.append(", ");
            }
         }
         sb.append(getName(particle.getTerm()));
         sb.append(getSuffix(particle.getMinOccurrence(), particle.getMaxOccurrence()));
         ct++;
      }
      sb.append(")");
      return sb.toString();
   }

   private static String getSuffix(int min, int max) {
      if (min == 0) {
         if (max == 1) {
            return "?";
         }
         return "*";
      }
      if (max == 1) {
         return "";
      }
      return "+";
   }

   /**
    * Gets the most appropriate icon for the given XType.
    *
    * @param type The type of icon needed.
    * @return An icon, never null, will always be something.
    */
   public static Icon getIcon(SmSequenceType type) {
      if (type == null) {
         return getErrorIcon();
      }
      SmParticleTerm c = type.getParticleTerm();
      if (c == null) {
         int tc = type.getTypeCode();
         if (tc == SmSequenceType.TYPE_CODE_ELEMENT) {
            return ICON_WILDCARD;//getElementIcon();
         }
         if (tc == SmSequenceType.TYPE_CODE_ATTRIBUTE) {
            return ICON_ANY;//getAttributeIcon();
         }
         if (tc == SmSequenceType.TYPE_CODE_PAREN || tc == SmSequenceType.TYPE_CODE_REPEATS) {
            return getIcon(type.getFirstChildComponent());
         }
         if (tc == SmSequenceType.TYPE_CODE_CHOICE) {
            return getChoiceIcon();
         }
         if (tc == SmSequenceType.TYPE_CODE_TEXT) {
            return ICON_TEXT;
         }
         if (tc == SmSequenceType.TYPE_CODE_SEQUENCE || tc == SmSequenceType.TYPE_CODE_INTERLEAVE) {
            return getAnonymousSequenceIcon();
         }
         if (tc == SmSequenceType.TYPE_CODE_PREVIOUS_ERROR) {
            return ICON_ANY;
         }
         if (tc == SmSequenceType.TYPE_CODE_SIMPLE) {
            SmType t = type.getSimpleType();
            return getIcon(t);
         }
      }
      SmType t = SmSequenceTypeSupport.getSchemaType(type);

      Icon i = getIcon(c, t);
      if (i == null) {
         throw new NullPointerException("Null icon for " + c.getClass().getName());
      }
      return i;
   }

   public static Icon getPrimitiveIcon(SmType t) {
      Icon icon = PRIMITIVE_ICON_MAP.get(t.getValueType());
      if (icon != null) {
         return icon;
      }
      return ICON_STRING;
   }

   /**
    * Returns the number of spacing pixels between a label and its contents.
    */
   public static int getLabelDataSpacing() {
      return 5;
   }

   /**
    * Gets the small 16x16 icon most appropriate for the given type.
    *
    * @param type The type.
    * @return An icon, never returns null.
    */
   public static Icon getIcon(SmType type) {
      if (type == null) {
         // don't fail when this happens:
         return ICON_ANY;
      }
      String vt = type.getValueType();
      if (type == UtilitySchema.JAVA_NATIVE_OBJECT_REFERENCE_TYPE) {
         return ICON_JAVA;
      }
      if (vt != null) {
         Icon icon = PRIMITIVE_ICON_MAP.get(vt);
         if (icon != null) {
            return icon;
         }
         // unknown:
         return ICON_STRING;
      }
      if (type == XSDL.ANY_SIMPLE_TYPE) {
         return ICON_ANY;
      }
      if (type == XSDL.ANY_TYPE) {
         return ICON_ANY;
      }
      if (type.isAbstract()) {
         //?? also check for null content?
         return ICON_ANY;
      }
      return ICON_SEQUENCE;
   }

   public static Icon getIcon(SmParticleTerm c) {
      return getIcon(c, null);
   }

   private static Icon getIcon(SmParticleTerm c, SmType optionalSubType) {
      initialize();
      if (c instanceof SmDataComponent) {
         SmType t = ((SmDataComponent) c).getType();
//            ExpandedName javaRef = ExpandedName.makeName("http://www.tibco.com/pe/EngineTypes", "JavaObjectReference");
         ExpandedName javaRef = ExpandedName.makeName(UtilitySchema.UTILITY_SCHEMA_NS, UtilitySchema.JAVA_NATIVE_OBJECT_REFERENCE_TYPE.getName());
         if (optionalSubType != null) {
            t = optionalSubType;
         }
         if (t.getExpandedName() != null && t.getExpandedName().equals(javaRef)) {
            t = UtilitySchema.JAVA_NATIVE_OBJECT_REFERENCE_TYPE;
         }
         Icon ic = getIcon(t);
         return ic;
      }
      if (c instanceof SmModelGroup) {
         SmModelGroup g = (SmModelGroup) c;
         if (g.getCompositor() == SmModelGroup.CHOICE) {
            return ICON_CHOICE;
         }
         else {
            return ICON_ANONYMOUS_SEQUENCE;
         }
      }
      if (c instanceof SmWildcard) {
         return ICON_WILDCARD;
      }
      // Shouldn't get here...
      if (c == null) {
         return getErrorIcon();
      }
      throw new RuntimeException("Unknown type " + c.getClass().getName());
   }

   private static boolean isFirstIcon = true;
   private static boolean useLocalManager;

   private static Icon getIcon(String key) {
      key = key + ".icon";
      if (isFirstIcon) {
         try {
        	 ResourceBundleManager.getIcon(key, DataIcons.class.getClassLoader());
         }
         catch (MissingResourceException mre) {
            // for local testing the designer resource manager doesn't work for whatever reason.
            useLocalManager = true;
         }
      }
      isFirstIcon = false;
      Icon icon;
      if (useLocalManager) {
         icon = ResourceBundleManager.getIcon(key, DataIcons.class.getClassLoader());
      }
      else {
         icon = ResourceBundleManager.getIcon(key, DataIcons.class.getClassLoader());
      }
      if (icon == null) {
         throw new NullPointerException("No image found");
      }
      return icon;
   }

   public static String getString(String key) {
      //ResourceBundle rb = ResourceBundle.getBundle("com.tibco.ui.data.Resources");
      //String val = rb.getString(key);
      String val = ResourceBundleManager.getMessage(key);
      if (val == null) {
         // Print it out now so that we can see it easily.
         System.err.println("Missing resource ----> " + key);
         return "# missing: " + key;
      }
      return val;
   }

   private static void initialize() {
      if (INITIALIZED) {
         return;
      }
      INITIALIZED = true;
      // Couldn't keep working: ResourceManager b = getResourceManager();
      /*ResourceBundle rb = ResourceBundle.getBundle("com.tibco.ui.data.Resources");
      if (rb==null) {
          throw new RuntimeException("Internal error: Can't get bundle for data icons");
      }*/
      ICON_WINDOW_TITLE = getIcon("ae.data.WindowTitle");

      ICON_SEQUENCE = getIcon("ae.data.Sequence");
      ICON_ANONYMOUS_SEQUENCE = getIcon("ae.data.AnonymousSequence");
      ICON_CHOICE = getIcon("ae.data.Choice");
      ICON_ARRAY = getIcon("ae.data.Array");
      ICON_STRING = getIcon("ae.data.String");
      ICON_INTEGER = getIcon("ae.data.Integer");
      ICON_REAL = getIcon("ae.data.Real");
      ICON_BOOLEAN = getIcon("ae.data.Boolean");
      ICON_BINARY = getIcon("ae.data.Binary");
      ICON_DATE = getIcon("ae.data.Date");
      ICON_ANY = getIcon("ae.data.Any");
      ICON_NIL = getIcon("ae.data.Nil");
      ICON_WILDCARD = getIcon("ae.data.Wildcard");
      ICON_JAVA = getIcon("ae.data.Java");

      addIcon(SmType.VALUE_TYPE_BINARY, ICON_BINARY);
      addIcon(SmType.VALUE_TYPE_BOOLEAN, ICON_BOOLEAN);
      addIcon(SmType.VALUE_TYPE_CENTURY, ICON_STRING);
      addIcon(SmType.VALUE_TYPE_CHAR, ICON_STRING);
      addIcon(SmType.VALUE_TYPE_DATE, ICON_DATE);
      addIcon(SmType.VALUE_TYPE_DATETIME, ICON_DATE);
      addIcon(SmType.VALUE_TYPE_DATETIMETZ, ICON_DATE);
      addIcon(SmType.VALUE_TYPE_DECIMAL, ICON_REAL);
      addIcon(SmType.VALUE_TYPE_DURATION, ICON_DATE);//
      addIcon(SmType.VALUE_TYPE_FIXED144, ICON_REAL); // why here, XSD???
      addIcon(SmType.VALUE_TYPE_FLOAT, ICON_REAL);
      addIcon(SmType.VALUE_TYPE_ID, ICON_INTEGER); //
      addIcon(SmType.VALUE_TYPE_INTEGER, ICON_INTEGER);
      addIcon(SmType.VALUE_TYPE_LIST, ICON_SEQUENCE); //?
      addIcon(SmType.VALUE_TYPE_MONTH, ICON_DATE);
      addIcon(SmType.VALUE_TYPE_NMTOKEN, ICON_STRING); //?
      //addIcon(SmType.VALUE_TYPE_R4,ICON_REAL);
      addIcon(SmType.VALUE_TYPE_RECURRING_DURATION, ICON_DATE);
      addIcon(SmType.VALUE_TYPE_RECUR_DATE, ICON_DATE);
      addIcon(SmType.VALUE_TYPE_RECUR_DAY, ICON_DATE);
      addIcon(SmType.VALUE_TYPE_STRING, ICON_STRING);
      addIcon(SmType.VALUE_TYPE_TIME, ICON_DATE);
      addIcon(SmType.VALUE_TYPE_TIMETZ, ICON_DATE);
      addIcon(SmType.VALUE_TYPE_TIME_INSTANCE, ICON_DATE);
      addIcon(SmType.VALUE_TYPE_TIME_PERIOD, ICON_DATE);
      addIcon(SmType.VALUE_TYPE_UNION, ICON_CHOICE); //
      addIcon(SmType.VALUE_TYPE_URI, ICON_STRING); //
      addIcon(SmType.VALUE_TYPE_YEAR, ICON_DATE);

      // Other icons:

      ICON_EDIT_XPATH = getIcon("ae.xpath.Edit");
      ICON_EDIT = getIcon("ae.data.Edit");
      ICON_MOVE_UP = getIcon("ae.data.MoveUp");
      ICON_MOVE_DOWN = getIcon("ae.data.MoveDown");
      ICON_MOVE_LEFT = getIcon("ae.data.MoveLeft");
      ICON_MOVE_RIGHT = getIcon("ae.data.MoveRight");
      ICON_ADD = getIcon("ae.data.Add");
      ICON_ADD_CHILD = getIcon("ae.data.AddChild");
      ICON_DELETE = getIcon("ae.data.Delete");
      ICON_VARIABLE = getIcon("ae.data.Variable");

      ICON_REFERENCE = getIcon("ae.data.Reference");
      ICON_TYPE_REFERENCE = getIcon("ae.data.TypeReference");
      ICON_ATTRIBUTE = getIcon("ae.data.Attribute");
      ICON_ELEMENT = getIcon("ae.data.Element");

      ICON_ANY_ABSTRACT = getIcon("ae.data.AnyAbstract");
      ICON_ANY_SUB = getIcon("ae.data.AnySub");
      ICON_ANY_SUBSTITUTED = getIcon("ae.data.AnySubstituted");
      ICON_IF = getIcon("ae.data.If");
      ICON_VALUE_OF = getIcon("ae.data.ValueOf");
      ICON_TEXT = getIcon("ae.data.Text");
      ICON_CALL_TEMPLATE = getIcon("ae.data.CallTemplate");
      ICON_APPLY_TEMPLATES = getIcon("ae.data.ApplyTemplates");
      ICON_SORT = getIcon("ae.data.Sort");
      ICON_FOR_EACH = getIcon("ae.data.ForEach");
      ICON_FOR_EACH_GROUP = getIcon("ae.data.ForEachGroup");
      ICON_GROUP_BY = getIcon("ae.data.GroupBy");
      ICON_CHOOSE = getIcon("ae.data.Choose");
      ICON_WHEN = getIcon("ae.data.When");
      ICON_OTHERWISE = getIcon("ae.data.Otherwise");
      ICON_SOURCE_COMMENT = getIcon("ae.data.SourceComment");
      ICON_GENERATE_COMMENT = getIcon("ae.data.GenerateComment");
      ICON_GENERATE_PI = getIcon("ae.data.GeneratePI");
      ICON_COPY = getIcon("ae.data.Copy");
      ICON_TYPE_COPY = getIcon("ae.data.TypeCopy");
      ICON_STATEMENT = getIcon("ae.data.Statement");
      ICON_INLINE_HELP = getIcon("ae.data.InlineHelp");
      ICON_INLINE_EDIT = getIcon("ae.data.InlineEdit");
      ICON_BLANK = getIcon("ae.data.Blank");
        ICON_ERROR = getIcon("ae.data.Error");//WCETODO make designer have this public static. //getIcon("ae.data.Erro");  //getIcon("ae.data.Error");
//      ICON_ERROR = ResourceBundleManager.getIcon("ae.form.field.validation.error.icon");//WCETODO make designer have this public static. //getIcon("ae.data.Erro");  //getIcon("ae.data.Error");
      ICON_DATA_MODE = getIcon("ae.data.DataMode");

      ICON_UNDO = getIcon("ae.data.Undo");
      ICON_REDO = getIcon("ae.data.Redo");

      ICON_FIX = getIcon("ae.data.Fix");

      STRING_SELECT_ALL = getString("ae.data.selectall.label");
      STRING_DESELECT_ALL = getString("ae.data.deselectall.label");

      STRING_ELEMENT = getString("ae.data.element.label");
      STRING_ATTRIBUTE = getString("ae.data.attribute.label");
      STRING_MODEL_GROUP = getString("ae.data.modelgroup.label");
      STRING_TYPE = getString("ae.data.type.label");
      STRING_SCHEMA = getString("ae.data.schema.label");
      STRING_PREFIX = getString("ae.data.prefix.label");
      STRING_NAMESPACE = getString("ae.data.namespace.label");
      STRING_TARGET_NAMESPACE = getString("ae.data.targetnamespace.label");
      STRING_NO_NAMESPACE = getString("ae.data.nonamespace.label");
      STRING_LOCAL_NAME = getString("ae.data.localname.label");
      STRING_OCCURRENCE = getString("ae.data.occurrence.label");
      STRING_OCCURRENCES = getString("ae.data.occurrences.label");
      STRING_EMPTY = getString("ae.data.empty.label");
      STRING_XPATH = getString("ae.data.xpath.label");
      STRING_LITERALEXPLICIT_REPRESENTATION = getString("ae.data.bind.literalexplicit.representation");
      STRING_LITERALEXPLICIT_LITERAL = getString("ae.data.bind.literalexplicit.literal");
      STRING_LITERALEXPLICIT_EXPLICIT = getString("ae.data.bind.literalexplicit.explicit");
      STRING_LITERALEXPLICIT_SEPARATE_NAMESPACE = getString("ae.data.bind.literalexplicit.separate.namespace");
      STRING_COPYOFPANEL_COPY_NAMESPACES = getString("ae.data.bind.copyofpanel.copy.namespaces");
      STRING_VARIABLEPANEL_VARIABLE_NAME = getString("ae.data.bind.variablepanel.variable.name");
      STRING_VARIABLEPANEL_USE_SELECT = getString("ae.data.bind.variablepanel.use.select");
   }

   static {
      initialize(); // for whatever reason, a stand-alone test needs this here to ensure resource manager loaded..

      XSD_STRING = getXsdString("string");
      XSD_NORMALIZED_STRING = getXsdString("normalizedstring");
      XSD_TOKEN = getXsdString("token");
      XSD_LANGUAGE = getXsdString("language");
      XSD_NAME = getXsdString("name");
      XSD_NCNAME = getXsdString("ncname");
      XSD_QNAME = getXsdString("qname");
      XSD_NMTOKEN = getXsdString("nmtoken");
      XSD_NMTOKENS = getXsdString("nmtokens");
      XSD_ID = getXsdString("id");
      XSD_IDREF = getXsdString("idref");
      XSD_IDREFS = getXsdString("idrefs");
      XSD_ENTITY = getXsdString("entity");
      XSD_ENTITIES = getXsdString("entities");

      XSD_GENERIC_BINARY = getXsdString("genericbinary");
      XSD_BASE64BINARY = getXsdString("base64binary");
      XSD_HEXBINARY = getXsdString("hexbinary");

      XSD_BOOLEAN = getXsdString("boolean");

      XSD_BYTE = getXsdString("byte");
      XSD_SHORT = getXsdString("short");
      XSD_INT = getXsdString("int");
      XSD_LONG = getXsdString("long");
      XSD_UNSIGNED_BYTE = getXsdString("unsignedbyte");
      XSD_UNSIGNED_SHORT = getXsdString("unsignedshort");
      XSD_UNSIGNED_INT = getXsdString("unsignedint");
      XSD_UNSIGNED_LONG = getXsdString("unsignedlong");

      XSD_INTEGER = getXsdString("integer");
      XSD_POSITIVE_INTEGER = getXsdString("positiveinteger");
      XSD_NEGATIVE_INTEGER = getXsdString("negativeinteger");
      XSD_NONPOSITIVE_INTEGER = getXsdString("nonpositiveinteger");
      XSD_NONNEGATIVE_INTEGER = getXsdString("nonnegativeinteger");

      XSD_FLOAT = getXsdString("float");
      XSD_DOUBLE = getXsdString("double");
      XSD_DECIMAL = getXsdString("decimal");

      XSD_GENERIC_DATE_TIME = getXsdString("genericdatetime");
      XSD_TIME = getXsdString("time");
      XSD_DATE = getXsdString("date");
      XSD_DATE_TIME = getXsdString("datetime");
      XSD_DURATION = getXsdString("duration");

      XSD_YEAR = getXsdString("year");
      XSD_MONTH = getXsdString("month");
      XSD_DAY = getXsdString("day");
      XSD_YEAR_MONTH = getXsdString("yearmonth");
      XSD_MONTH_DAY = getXsdString("monthday");

      XSD_ANY_URI = getXsdString("anyuri");
      JAVA_NATIVE_OBJECT_REFERENCE_TYPE = getXsdString("javaobjectreference");
   }

   private static String getXsdString(String name) {
      return getString("ae.xsdtypes." + name + ".label");
   }

   private static void addIcon(String primitiveName, Icon icon) {
      if (primitiveName == null) {
         throw new RuntimeException("Null name");
      }
      if (icon == null) {
         throw new RuntimeException("Null icon for primitive " + primitiveName);
      }
      if (PRIMITIVE_ICON_MAP.containsKey(primitiveName)) {
         throw new RuntimeException("Added twice " + primitiveName);
      }
      PRIMITIVE_ICON_MAP.put(primitiveName, icon);
   }

   public static Icon getEditIcon() {
      initialize();
      return ICON_EDIT;
   }

   public static Icon getXPathEditIcon() {
      initialize();
      return ICON_EDIT_XPATH;
   }

   public static Icon getInlineHelpIcon() {
      initialize();
      return ICON_INLINE_HELP;
   }

   public static Icon getUndoIcon() {
      initialize();
      return ICON_UNDO;
   }

   public static Icon getRedoIcon() {
      initialize();
      return ICON_REDO;
   }

   public static Icon getInlineEditIcon() {
      initialize();
      return ICON_INLINE_EDIT;
   }

   public static Icon getMoveUpIcon() {
      initialize();
      return ICON_MOVE_UP;
   }

   public static Icon getMoveDownIcon() {
      initialize();
      return ICON_MOVE_DOWN;
   }

   public static Icon getMoveLeftIcon() {
      initialize();
      return ICON_MOVE_LEFT;
   }

   public static Icon getMoveRightIcon() {
      initialize();
      return ICON_MOVE_RIGHT;
   }

   public static Icon getAddIcon() {
      initialize();
      return ICON_ADD;
   }

   public static Icon getAddChildIcon() {
      initialize();
      return ICON_ADD_CHILD;
   }

   public static Icon getCheckErrorsIcon() {
      initialize();
      return ICON_ADD_CHILD;
   }

   public static Icon getDeleteIcon() {
      initialize();
      return ICON_DELETE;
   }

   public static Border getLargeSpaceBorder() {
      if (LARGE_SPACE_BORDER == null) {
         LARGE_SPACE_BORDER = BorderFactory.createEmptyBorder(12, 12, 11, 11);
      }
      return LARGE_SPACE_BORDER;
   }

   /**
    * Does substitution Xmlui_MMessageBundle style w/o the mmessagebundle... seems like it should be a helper function,
    * maybe it exists somewhere... if you know where, deprecate this & add a ref.
    */
   public static String printf(String msg, Object param1) {
      // Cut'n'pasted from Xmlui_MMessageBundle (not public there)
      String val = msg;
      String retVal = val;
      int ind = val.indexOf("%1");
      if (ind >= 0) {
         retVal = val.substring(0, ind) + param1 + val.substring(ind + 2);
      }
      return (retVal);
   }

   /**
    * Does substitution Xmlui_MMessageBundle style w/o the mmessagebundle... seems like it should be a helper function,
    * maybe it exists somewhere... if you know where, deprecate this & add a ref.
    */
   public static String printf(String msg, Object param1, Object param2) {
      // Cut'n'pasted from Xmlui_MMessageBundle (not public there)
      String val = msg;
      {
         int ind = val.indexOf("%1");
         if (ind >= 0) {
            val = val.substring(0, ind) + param1 + val.substring(ind + 2);
         }
      }
      {
         int ind = val.indexOf("%2");
         if (ind >= 0) {
            val = val.substring(0, ind) + param2 + val.substring(ind + 2);
         }
      }
      return val;
   }
}
