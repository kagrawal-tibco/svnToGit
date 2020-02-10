package com.tibco.cep.studio.mapper.ui.data.formula.sample;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.XPathSoftDragItem;
import com.tibco.cep.studio.mapper.ui.data.formula.FormulaDesignerWindow;
import com.tibco.cep.studio.mapper.ui.data.formula.FormulaFunctionCategory;
import com.tibco.cep.studio.mapper.ui.data.formula.FormulaFunctionPanel;
import com.tibco.cep.studio.mapper.ui.data.formula.LanguageDescription;
import com.tibco.cep.studio.mapper.ui.data.formula.SplittingFormulaWindow;
import com.tibco.cep.studio.mapper.ui.data.formula.TextBasedFormulaWindow;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathBuilderCategory;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTree;
import com.tibco.cep.studio.mapper.ui.edittree.EditableTreeDragNDropHandler;
import com.tibco.cep.studio.mapper.ui.edittree.render.DefaultNameValueTreeCellPlugin;
import com.tibco.cep.studio.mapper.ui.edittree.render.NameValueTreeCellPlugin;
import com.tibco.cep.studio.mapper.ui.edittree.render.NameValueTreeCellRenderer;
import com.tibco.cep.studio.mapper.ui.edittree.simple.SimpleTreeModel;
import com.tibco.cep.studio.mapper.ui.edittree.simple.SimpleTreeNode;

/**
 * A sample use of FormulaDesignerWindow which uses a split pane & a custom left-hand-side tree.
 */
public class SampleFormulaDesignerWindow extends FormulaDesignerWindow {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public SampleFormulaDesignerWindow(UIAgent uiAgent) {
      super();
      setFormulaWindow(new SampleSplitWindow(uiAgent));
      final EditableTree et = new EditableTree(uiAgent);
      et.setEditableModel(new SimpleTreeModel(new MyNode()));
      NameValueTreeCellPlugin p = new DefaultNameValueTreeCellPlugin(uiAgent) {
         public String getNodeNameValue(Object node) {
            return "Test";
         }
      };
      et.setDragNDropHandler(new EditableTreeDragNDropHandler() {
         public Object getDragObjectForNode(Object node) {
            return new XPathSoftDragItem("abcdefg", et); // badly named for now...
         }

         public Object createNodeFromDragObject(Object dragObject) {
            return null;
         }
      });
      NameValueTreeCellRenderer r = new NameValueTreeCellRenderer(et, p);
      et.setCellRenderer(r);

      setInputWindow(et);

      FormulaFunctionCategory[] cats = XPathBuilderCategory.getFunctionCategories(null);
      addFormulaTab(new FormulaFunctionPanel(uiAgent, cats));
      //ld.setTokenMarker();
      //setPathSeparator(".");
   }

   private static class MyNode extends SimpleTreeNode {
      public SimpleTreeNode[] buildChildren() {
         return new SimpleTreeNode[]{new MyNode(), new MyNode()};
      }

      public boolean isLeaf() {
         return false;
      }
   }

   static class SampleSplitWindow extends SplittingFormulaWindow {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SampleSplitWindow(UIAgent uiAgent) {
         super(createWindow(uiAgent, "condition"), createWindow(uiAgent, "then"));
      }

      private static TextBasedFormulaWindow createWindow(UIAgent uiAgent,
                                                         String label) {
         LanguageDescription ld = new LanguageDescription();
         TextBasedFormulaWindow fat = new TextBasedFormulaWindow(uiAgent);
         fat.setLanguageDescription(ld);
         fat.setFormulaLabel(label);
         fat.setShowsContext(false);
         fat.setShowComputedType(false);
         return fat;
      }

      public String[] disassembleFormula(String formula) {
         int i = formula.indexOf("then");
         if (i < 0) {
            return new String[]{formula, ""};
         }
         return new String[]{formula.substring(0, i).trim(), formula.substring(i + 4).trim()};
      }

      public String reassembleFormula(String piece1, String piece2) {
         return piece1 + " then " + piece2;
      }
   }
}
