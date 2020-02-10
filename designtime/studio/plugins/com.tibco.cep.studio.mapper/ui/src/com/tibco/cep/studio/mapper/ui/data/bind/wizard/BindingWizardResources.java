package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.wizard.DefaultWizardPanel;

/**
 * The resource strings for the drag'n'drop wizard.
 */
public class BindingWizardResources extends DefaultWizardPanel {
   public static final String ADJUST_CONTAINED_FORMULA;

   public static final String WIZARD_TITLE;

   public static final String AUTOMAP_TITLE;
   public static final String AUTOMAP_HEADER;
   public static final String AUTOMAP_COPY_ALL;
   public static final String AUTOMAP_COPY_NONE;
   public static final String AUTOMAP_SET;
   public static final String AUTOMAP_COPY;

   public static final String FORMULA_ALREADY_EXISTS_TITLE;
   public static final String FORMULA_ALREADY_EXISTS;

   public static final String SET_FORMULA;
   public static final String REPLACE_EXISTING_FORMULA;
   public static final String INCORPORATE_INTO_EXISTING_FORMULA;

   public static final String FOR_EACH_CREATE;

   public static final String MERGE_PARALLEL_TITLE;
   public static final String MERGE_PARALLEL_HEADER;
   public static final String MERGE_PARALLEL_POSITION_VAR;
   public static final String MERGE_PARALLEL_POSITION_VAR_DEFAULT;
   public static final String MERGE_PARALLEL_ITEM_VAR;
   public static final String MERGE_PARALLEL_ITEM_VAR_DEFAULT;

   public static final String NO_ACTION_AVAILABLE;

   public static final String MAPPING_DROP_UNDOREDO;

   static {
      WIZARD_TITLE = loadWizardTitle("wizard");
      ADJUST_CONTAINED_FORMULA = loadWizardString("adjustcontainedformula");

      AUTOMAP_TITLE = loadWizardTitle("automap");
      AUTOMAP_HEADER = loadWizardString("automap.description");
      AUTOMAP_COPY_ALL = loadWizardString("automap.copyall");
      AUTOMAP_COPY_NONE = loadWizardString("automap.copynone");
      AUTOMAP_SET = loadWizardString("automap.set");
      AUTOMAP_COPY = loadWizardString("automap.copy");

      FOR_EACH_CREATE = loadWizardString("foreachcreate");

      MERGE_PARALLEL_TITLE = loadWizardTitle("mergeparallel");
      MERGE_PARALLEL_HEADER = loadWizardString("mergeparallel.header");
      MERGE_PARALLEL_POSITION_VAR = loadWizardString("mergeparallel.position");
      MERGE_PARALLEL_ITEM_VAR = loadWizardString("mergeparallel.item");
      MERGE_PARALLEL_POSITION_VAR_DEFAULT = loadWizardString("mergeparallel.positiondef");
      MERGE_PARALLEL_ITEM_VAR_DEFAULT = loadWizardString("mergeparallel.itemdef");

      FORMULA_ALREADY_EXISTS_TITLE = loadWizardTitle("formulaalreadyexists");
      FORMULA_ALREADY_EXISTS = loadWizardString("formulaalreadyexists");

      SET_FORMULA = loadWizardString("setformula");
      REPLACE_EXISTING_FORMULA = loadWizardString("replaceexistingformula");
      INCORPORATE_INTO_EXISTING_FORMULA = loadWizardString("incorporateintoexistingformula");

      NO_ACTION_AVAILABLE = loadWizardString("noactionavailable");

      MAPPING_DROP_UNDOREDO = loadWizardString("undoredo");
   }

   /**
    * Helper method that loads a binding string by just the 'local' name --- it auto-prepends the common
    * part and 'label' suffix.
    *
    * @param key The local part of the resource key.
    */
   private static String loadWizardString(String key) {
      String fullKey = "ae.binding.wizard." + key + ".label";
      return DataIcons.getString(fullKey);
   }

   /**
    * Helper method that loads a binding string by just the 'local' name --- it auto-prepends the common
    * part and 'title' suffix.
    *
    * @param key The local part of the resource key.
    */
   private static String loadWizardTitle(String key) {
      String fullKey = "ae.binding.wizard." + key + ".title";
      return DataIcons.getString(fullKey);
   }
}

;
