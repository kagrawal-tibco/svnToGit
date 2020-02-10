package com.tibco.cep.studio.mapper.ui.data.param;

import com.tibco.cep.studio.mapper.ui.data.DataIcons;


/**
 * The resource strings for the {@link ParameterEditor}.
 */
public class ParameterEditorResources {
   public static final String NONE;

   // Process-contents (on xsd any), and the choices.
   public static final String PROCESS_CONTENTS_LABEL;
   public static final String STRICT;
   public static final String LAX;
   public static final String SKIP;
   public static final String DEFAULT_VALIDATION;

   public static final String DEFAULT_NEW_ELEMENT_NAME;

   public static final String SUBSTITUTION_GROUP;
   public static final String ENUMERATIONS;
   public static final String IS_A;
   public static final String CHOICE_OF;
   public static final String EXTENDS;

   public static final String OCCURRENCE_REQUIRED;

   /**
    * The min label.
    */
   public static final String OCCURRENCE_MIN;

   /**
    * The min label.
    */
   public static final String OCCURRENCE_MAX;

   /**
    * The unbounded label (for maximum).
    */
   public static final String OCCURRENCE_UNBOUNDED;

   /**
    * i.e. Optional
    */
   public static final String OCCURRENCE_OPTIONAL;

   /**
    * i.e. Optional (?)
    */
   public static final String OCCURRENCE_OPTIONAL_WITH_PAREN;

   public static final String OCCURRENCE_REPEATING;
   public static final String OCCURRENCE_REPEATING_WITH_PAREN;

   public static final String OCCURRENCE_AT_LEAST_ONE;
   public static final String OCCURRENCE_AT_LEAST_ONE_WITH_PAREN;

   /**
    * i.e. content model type (element/choice, etc.)
    */
   public static final String CONTENT_MODEL;

   /**
    * WCETODO should be a common string somewhere...
    */
   public static final String NAME;

   public static final String CARDINALITY;

   public static final String DEFAULTVALUENAME;
   public static final String DETAILS;

   /**
    * WCETODO maybe consolidate these with those in DataIcons.
    */
   public static final String ELEMENT_REFERENCE;
   public static final String TYPE_REFERENCE;
   public static final String GROUP_REFERENCE;

   public static final String ELEMENT_OF_TYPE;

   public static final String ATTRIBUTE_OF_TYPE;

   public static final String SEQUENCE;

   public static final String CHOICE;

   public static final String ALL;

   public static final String ANY_TYPE;

   public static final String COMPLEX_ELEMENT;

   public static final String ANY_ELEMENT;

   /**
    * For {@link IntegerCategory} sub-field.
    */
   public static final String INTEGER_SIZE;

   /**
    * For {@link BinaryCategory} sub-field.
    */
   public static final String BINARY_ENCODING;

   public static final String SPECIAL_TYPE;
   public static final String SPECIAL_TYPE_SUBTYPE;

   static {
      NONE = getLabel("none");

      PROCESS_CONTENTS_LABEL = getLabel("processcontents");
      STRICT = getLabel("strict");
      LAX = getLabel("lax");
      SKIP = getLabel("skip");
      DEFAULT_VALIDATION = getLabel("defaultvalidation");

      DEFAULT_NEW_ELEMENT_NAME = getLabel("defaultnewelementname");

      SUBSTITUTION_GROUP = getLabel("substitutiongroup");
      ENUMERATIONS = getLabel("enumerations");
      IS_A = getLabel("isa");
      CHOICE_OF = getLabel("choiceof");
      EXTENDS = getLabel("typeextends"); // Do not use the 'reserved' word extends (localization keyword)

      OCCURRENCE_MIN = getLabel("min");
      OCCURRENCE_MAX = getLabel("max");
      OCCURRENCE_UNBOUNDED = getLabel("unbounded");

      OCCURRENCE_REQUIRED = getLabel("required");

      OCCURRENCE_OPTIONAL = getLabel("optional");
      OCCURRENCE_OPTIONAL_WITH_PAREN = OCCURRENCE_OPTIONAL + " (?)";

      OCCURRENCE_REPEATING = getLabel("repeating");
      OCCURRENCE_REPEATING_WITH_PAREN = OCCURRENCE_REPEATING + " (*)";

      OCCURRENCE_AT_LEAST_ONE = getLabel("atleastone");
      OCCURRENCE_AT_LEAST_ONE_WITH_PAREN = OCCURRENCE_AT_LEAST_ONE + " (+)";

      CONTENT_MODEL = getLabel("contentmodel");
      NAME = getLabel("name");
      CARDINALITY = getLabel("cardinality");
      DEFAULTVALUENAME = getLabel("defaultValueName");
      DETAILS = getLabel("details");

      ELEMENT_REFERENCE = getLabel("elementreference");
      TYPE_REFERENCE = getLabel("typereference");
      GROUP_REFERENCE = getLabel("groupreference");
      ELEMENT_OF_TYPE = getLabel("elementoftype");

      ATTRIBUTE_OF_TYPE = getLabel("attributeoftype");

      SEQUENCE = getLabel("sequence");
      CHOICE = getLabel("choice");
      ALL = getLabel("all");
      ANY_TYPE = getLabel("anytype");

      COMPLEX_ELEMENT = getLabel("complexelement");
      ANY_ELEMENT = getLabel("anyelement");

      INTEGER_SIZE = getLabel("integersize");
      BINARY_ENCODING = getLabel("binaryencoding");

      SPECIAL_TYPE = getLabel("specialType");
      SPECIAL_TYPE_SUBTYPE = getLabel("specialTypeSubtype");
   }

   private static String getLabel(String name) {
      return DataIcons.getString("ae.parametereditor." + name + ".label");
   }
}
