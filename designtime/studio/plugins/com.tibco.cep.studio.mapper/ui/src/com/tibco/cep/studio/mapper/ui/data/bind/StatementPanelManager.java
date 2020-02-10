package com.tibco.cep.studio.mapper.ui.data.bind;

import java.util.ArrayList;
import java.util.HashMap;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;

/**
 * A utility class to manage the statement panels.
 */
public class StatementPanelManager {
   private final ArrayList<StatementPanel> m_panelsInOrder = new ArrayList<StatementPanel>();
   @SuppressWarnings("rawtypes")
private final HashMap<Class, StatementPanel> m_classToPanel = new HashMap<Class, StatementPanel>();
   private static ArrayList<StatementPanel> ALL_BUILT_IN_PANELS = new ArrayList<StatementPanel>();
   private static final StatementPanel DEFAULT_STATEMENT_PANEL = new UnknownStatementPanel();

   public static StatementPanelManager DEFAULT_INSTANCE = new StatementPanelManager();

   private StatementPanelManager() {
      this(null);
   }

   public StatementPanelManager(StatementPanel[] additionalPanels) {
      synchronized (ALL_BUILT_IN_PANELS) {
         if (ALL_BUILT_IN_PANELS.size() == 0) {
            addBuiltInPanels();
         }
      }
      for (int i = 0; i < ALL_BUILT_IN_PANELS.size(); i++) {
         addPanelInternal(ALL_BUILT_IN_PANELS.get(i));
      }
      if (additionalPanels != null) {
         for (int i = 0; i < additionalPanels.length; i++) {
            addPanelInternal(additionalPanels[i]);
         }
      }
   }

   /**
    * Finds the StatementPanel that works with the specified type of binding.
    *
    * @param binding Binding
    * @return A statement panel, never null (may be a default one, though)
    */
   public StatementPanel getStatementPanelFor(Binding binding) {
      if (binding == null) {
         return DEFAULT_STATEMENT_PANEL;
      }
      StatementPanel sp = m_classToPanel.get(binding.getClass());
      if (sp == null) {
         return DEFAULT_STATEMENT_PANEL;
      }
      return sp;
   }

   public StatementPanel[] getAllPanels() {
      return m_panelsInOrder.toArray(new StatementPanel[m_panelsInOrder.size()]);
   }

   private void addPanelInternal(StatementPanel sp) {
      m_panelsInOrder.add(sp);
      m_classToPanel.put(sp.getHandlesBindingClass(), sp);
   }


   private static void addBuiltInPanels() {
      // Start with output generating things:
      addPanel(new ElementPanel());
      addPanel(new AttributePanel());
      addPanel(new GenerateCommentPanel()); // handles missing term internally.
      addPanel(new GeneratePIPanel()); // handles missing term internally.
      addPanel(new CopyOfPanel());
      addPanel(new CopyTypePanel());
      addPanel(new CopyPanel());

      // Next, control structures:
      addPanel(new ForEachPanel());
      addPanel(new ForEachGroupPanel());
      addPanel(new GroupingPanel());
      addPanel(new SortPanel());
      addPanel(new IfPanel());
      addPanel(new ChoosePanel());
      addPanel(new WhenPanel());
      addPanel(new OtherwisePanel());
      addPanel(new VariablePanel());

      // Template related:
      // addPanel(new CallTemplatePanel()); (Add these back for full shared XSLT, not relevant in inline)
      // addPanel(new ApplyTemplatesPanel());
      addPanel(new TemplatePanel());
      addPanel(new StylesheetPanel());

      // Comment:
      addPanel(new SourceCommentPanel());

      addPanel(new MarkerBindingPanel());

      // Put output value-of as similar at bottom:
      addPanel(new ValueOfPanel());
      addPanel(new TextPanel());
      //DEFAULT_STATEMENT_PANEL = new DefaultStatementPanel();
   }

   private static void addPanel(StatementPanel panel) {
      ALL_BUILT_IN_PANELS.add(panel);
   }
}

