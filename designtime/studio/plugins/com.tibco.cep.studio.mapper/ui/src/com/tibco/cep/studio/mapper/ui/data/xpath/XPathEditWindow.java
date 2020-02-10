package com.tibco.cep.studio.mapper.ui.data.xpath;

import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.XPathTypeReport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.DataTree;
import com.tibco.cep.studio.mapper.ui.data.TreeState;
import com.tibco.cep.studio.mapper.ui.data.bind.StatementPanel;
import com.tibco.cep.studio.mapper.ui.data.formula.FormulaDesignerWindow;
import com.tibco.cep.studio.mapper.ui.data.formula.FormulaFunctionCategory;
import com.tibco.cep.studio.mapper.ui.data.formula.FormulaFunctionPanel;
import com.tibco.cep.studio.mapper.ui.data.formula.LanguageDescription;
import com.tibco.cep.studio.mapper.ui.data.formula.TextBasedFormulaWindow;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * The large, full featured, XPath (and related things, like AVT) editor.<br>
 * A window that edits xpaths, AVTs, and constants with a data-tree, etc., on the left side.
 */
public class XPathEditWindow extends FormulaDesignerWindow {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private int m_currentMode = -1; // The mode, i.e. xpath, avt, constant, etc. (init to -1 to match no codes)
   private TextBasedFormulaWindow m_xpathFormulaWindow;
   private TextBasedFormulaWindow m_constantFormulaWindow;
   private DataTree m_inputWindow;
   private FormulaFunctionPanel m_functions;
   private FormulaFunctionPanel m_constants;

   public XPathEditWindow(UIAgent uiAgent) {
      super();
      m_xpathFormulaWindow = new TextBasedFormulaWindow(uiAgent);
      m_constantFormulaWindow = new TextBasedFormulaWindow(uiAgent);
      m_constantFormulaWindow.setShowComputedType(false);
      m_constantFormulaWindow.setLanguageDescription(new LanguageDescription()); // nothing in it.
      m_constantFormulaWindow.setShowsContext(false); // context irrelevant for a constant.
      m_inputWindow = new DataTree(uiAgent);

      setFormulaWindow(m_xpathFormulaWindow);
      setInputWindow(m_inputWindow);
      FormulaFunctionCategory[] cats = XPathBuilderCategory.getFunctionCategories(uiAgent.getFunctionResolver());

      m_functions = new FormulaFunctionPanel(uiAgent, cats);
      addFormulaTab(m_functions);
      m_constants = new FormulaFunctionPanel(uiAgent, XPathBuilderCategory.getConstantCategories());
      addConstantsTab(m_constants);
   }

   /**
    * The xpath builder is now somewhat of a misnomer; it now handles XPath, constants, AVTs, and not-editable.
    *
    * @param formulaType The formula type as enumerated in {@link com.tibco.cep.studio.mapper.ui.data.bind.StatementPanel} for lack of a better place.
    */
   public void setMode(int formulaType) {
      if (m_currentMode == formulaType) {
         return;
      }
      m_currentMode = formulaType;

      if (formulaType == StatementPanel.FIELD_TYPE_NOT_EDITABLE) {
         setFormulaWindow(null);
      }
      else {
         if (formulaType == StatementPanel.FIELD_TYPE_COMMENT || formulaType == StatementPanel.FIELD_TYPE_CONSTANT) {
            setFormulaWindow(m_constantFormulaWindow);
            if (formulaType == StatementPanel.FIELD_TYPE_COMMENT) {
               m_constantFormulaWindow.setFormulaLabel(XPathEditResources.COMMENT_VALUE_LABEL);
            }
            else {
               m_constantFormulaWindow.setFormulaLabel(XPathEditResources.CONSTANT_VALUE_LABEL);
            }
         }
         else {
            // Either XPath or AVT.
            setFormulaWindow(m_xpathFormulaWindow);
            boolean isAVT = formulaType == StatementPanel.FIELD_TYPE_ATTRIBUTE_VALUE_TEMPLATE;
            m_xpathFormulaWindow.getTextArea().setAVTMode(isAVT); // hacky; need to flesh out editors better.
            m_xpathFormulaWindow.setFormulaLabel(isAVT ? XPathEditResources.AVT_FORMULA_LABEL : XPathEditResources.XPATH_FORMULA_LABEL);
         }
      }
   }

   public void setEditable(boolean editable) {
      m_xpathFormulaWindow.setEditable(editable);
      m_constantFormulaWindow.setEditable(editable);
   }

   static String getBuilderString(String key) {
      String fullKey = "ae.xpath.builder." + key;
      return DataIcons.getString(fullKey);
   }

   public void setInputTreeState(TreeState state) {
      m_inputWindow.setTreeState(state);
   }

   public void setTreeExprContext(ExprContext state) {
      m_inputWindow.setExprContext(state);
   }

   public void setNamespaceImporter(NamespaceContextRegistry namespaceContextRegistry) {
      m_inputWindow.setNamespaceImporter(namespaceContextRegistry);
      m_xpathFormulaWindow.setNamespaceImporter(namespaceContextRegistry);
   }

   public void setTextExprContext(ExprContext state) {
      m_xpathFormulaWindow.setTextExprContext(state);
   }

   /**
    * Reads the preferences such as window positionings to the app.
    *
    * @param fromApp The application to write preferences to.
    * @param prefix  The prefix, i.e. 'myarea.editor'.  Should not end with a '.'.
    */
   public void readPreferences(UIAgent uiAgent, String prefix) {
      super.readPreferences(uiAgent, prefix);
      m_xpathFormulaWindow.readPreferences(uiAgent, prefix + ".formula");
      m_functions.readPreferences(uiAgent, prefix + ".functions");
      m_constants.readPreferences(uiAgent, prefix + ".constants");
   }

   public void writePreferences(UIAgent uiAgent, String prefix) {
      super.writePreferences(uiAgent, prefix);
      m_xpathFormulaWindow.writePreferences(uiAgent, prefix + ".formula");
      m_functions.writePreferences(uiAgent, prefix + ".functions");
      m_constants.writePreferences(uiAgent, prefix + ".constants");
   }

   public void setTypeChecker(XTypeChecker typeChecker) {
      m_xpathFormulaWindow.setTypeChecker(typeChecker);
   }

   public XTypeChecker getTypeChecker() {
      return m_xpathFormulaWindow.getTypeChecker();
   }
   
   public XPathTypeReport getTypeReport() {
	   return m_xpathFormulaWindow.getTypeReport();
   }
}
