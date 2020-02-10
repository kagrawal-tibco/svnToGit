package com.tibco.cep.studio.mapper.ui.data.bind.fix;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.tree.TreePath;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportArguments;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReportFormulaCache;
import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChange;
import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChangeList;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingNode;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingTree;
import com.tibco.cep.studio.mapper.ui.data.utils.PreferenceUtils;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathEditResources;

/**
 * The main list (actually a JTable, but whose counting) view inside the binding fixer dialog.
 */
class BindingFixerListView extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JTable mTable;
   private BindingFixerChangeList mChangeList;
   private BindingTree mTree;
   private int mMinWidth;
   private boolean m_editable = true;
   private boolean m_showWarnings = true;

   private static final int DESCRIPTION_COLUMN = 2;

   public BindingFixerListView(BindingTree tree) {
      super(new BorderLayout());
      mTable = new JTable();

      String detailsLabel = ResourceBundleManager.getMessage("ae.validate.project.dialog.details.label");
      if (detailsLabel.endsWith(":")) {
         detailsLabel = detailsLabel.substring(0, detailsLabel.length() - 1);
      }
      final String fdetailsLabel = detailsLabel;
      mTable.setModel(new TableModel() {
         private Vector<TableModelListener> mListeners = new Vector<TableModelListener>();

         public int getRowCount() {
            return mChangeList.size();
         }

         public int getColumnCount() {
            return 3; // 1 for check box, 1 for error/warning, 1 for message.
         }

         public String getColumnName(int columnIndex) {
            if (columnIndex == 0) {
               return XPathEditResources.FIX_LABEL;
            }
            if (columnIndex == 1) {
               return XPathEditResources.SEVERITY_LABEL;
            }
            if (columnIndex == DESCRIPTION_COLUMN) {
               return fdetailsLabel;
            }
            // Error.
            return "" + columnIndex;
         }

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
            if (columnIndex == 0) {
               return Boolean.class;
            }
            if (columnIndex == 1) {
               return Color.class;
            }
            return String.class;
         }

         public boolean isCellEditable(int rowIndex, int columnIndex) {
            BindingFixerChange r = mChangeList.getChange(rowIndex);
            if (columnIndex == 0) {
               return r.canApply() && m_editable;
            }
            return false;
         }

         public Object getValueAt(int rowIndex, int columnIndex) {
            BindingFixerChange r = mChangeList.getChange(rowIndex);
            if (columnIndex == 0) {
               if (!r.canApply()) {
                  return null;
               }
               return r.getDoApply() ? Boolean.TRUE : Boolean.FALSE;
            }
            if (columnIndex == 1) {
               if (r.getCategory() == BindingFixerChange.CATEGORY_WARNING) {
                  return Color.yellow;
               }
               return r.getCategory() == BindingFixerChange.CATEGORY_OPTIMIZATION ? Color.green : Color.red;
            }
            return r.getMessage();
         }

         public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
               BindingFixerChange r = mChangeList.getChange(rowIndex);
               r.setDoApply(((Boolean) aValue).booleanValue());
            }
         }

         public void addTableModelListener(TableModelListener l) {
            mListeners.add(l);
         }

         public void removeTableModelListener(TableModelListener l) {
            mListeners.remove(l);
         }
      });
      JScrollPane jsp = new JScrollPane(mTable);
      add(jsp, BorderLayout.CENTER);
      mTree = tree;
      mTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      mTable.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      mTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent e) {
            selectionChanged();
         }
      });
      mTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      mTable.getColumnModel().getColumn(DESCRIPTION_COLUMN).setResizable(false);
      mTable.getTableHeader().setReorderingAllowed(false);
      mTable.setDefaultRenderer(Boolean.class, new OptBooleanRenderer());
      mTable.setDefaultRenderer(Color.class, new ColorRenderer());
   }

   public void setEditable(boolean editable) {
      m_editable = editable;
   }

   public boolean isEditable() {
      return m_editable;
   }

   public void writePreferences(UIAgent uiAgent, String prefix) {
      if (prefix.endsWith(".")) {
         throw new IllegalStateException();
      }
      PreferenceUtils.writeBool(uiAgent, prefix + ".showWarnings", m_showWarnings);
      PreferenceUtils.writeInt(uiAgent, prefix + ".fixColumn", mTable.getColumnModel().getColumn(0).getWidth());
      PreferenceUtils.writeInt(uiAgent, prefix + ".categoryColumn", mTable.getColumnModel().getColumn(1).getWidth());
   }

   public void readPreferences(UIAgent uiAgent, String prefix) {
      if (prefix.endsWith(".")) {
         throw new IllegalStateException();
      }
      m_showWarnings = PreferenceUtils.readBool(uiAgent, prefix + ".showWarnings", true);
      final int fcw = PreferenceUtils.readInt(uiAgent, prefix + ".fixColumn", 50);

      final int ccw = PreferenceUtils.readInt(uiAgent, prefix + ".categoryColumn", 50);

      // Need to do later for whatever reason.
//        SwingUtilities.invokeLater(new Runnable()
//        {
//            public void run()
//            {
      mTable.getColumnModel().getColumn(0).setPreferredWidth(fcw);
      mTable.getColumnModel().getColumn(1).setPreferredWidth(ccw);
//            }
//        });
   }

   public void setShowWarnings(boolean showWarnings) {
      m_showWarnings = showWarnings;
   }

   public boolean getShowWarnings() {
      return m_showWarnings;
   }

   public void runAnalysis() {
      TemplateReportArguments args = new TemplateReportArguments();

      args.setCheckForMove(true); // Yes, that sounds good.
      args.setCheckForRename(true); // hmmm, that too!
      args.setRecordingMissing(true); // want that, for sure.
      args.setSuggestOptimizations(m_showWarnings); // For now, just lump optimizations under warnings (too lazy to make separate button resources)

      TemplateEditorConfiguration tec = mTree.getTemplateEditorConfiguration();
      TemplateReportFormulaCache formulaCache = mTree.getFormulaCache();
      TemplateReport report = TemplateReport.create(tec, formulaCache, args);
      mChangeList = new BindingFixerChangeList();
      mChangeList.setIncludeOtherwiseCheck(true); // Always want these on here (they are off elsewhere because of the expense)
      mChangeList.setIncludeWarnings(m_showWarnings);
      mChangeList.run(report);
      if (mChangeList.size() > 0) {
         // auto-select:
         mTable.getSelectionModel().setSelectionInterval(0, 0);
      }
   }

   private static class ColorRenderer implements TableCellRenderer {
      private JLabel m_label = new JLabel(" ");

      public Component getTableCellRendererComponent(JTable table, Object value,
                                                     boolean isSelected, boolean hasFocus,
                                                     int row, int column) {
         if (value instanceof Color) {
            m_label.setBackground((Color) value);
         }
         else {
            // whatever...
            m_label.setBackground(Color.pink);
         }
         m_label.setOpaque(true);
         return m_label;
      }
   }

   private class OptBooleanRenderer extends JCheckBox implements TableCellRenderer {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel mEmptyLabel;

      public OptBooleanRenderer() {
         super();
         setHorizontalAlignment(JLabel.CENTER);
         mEmptyLabel = new JLabel();
      }

      public Component getTableCellRendererComponent(JTable table, Object value,
                                                     boolean isSelected, boolean hasFocus, int row, int column) {
         JComponent c;
         if (value == null) {
            c = mEmptyLabel;
         }
         else {
            c = this;
         }
         if (isSelected) {
            c.setForeground(table.getSelectionForeground());
            c.setBackground(table.getSelectionBackground());
         }
         else {
            c.setForeground(table.getForeground());
            c.setBackground(table.getBackground());
         }
         if (!m_editable) {
            c.setBackground(Color.lightGray);
         }
         setSelected((value != null && ((Boolean) value).booleanValue()) && m_editable);
         return c;
      }
   }

   public void doLayout() {
      if (mMinWidth == 0) {
         int c0w = mTable.getColumnModel().getColumn(0).getWidth();
         int c1w = mTable.getColumnModel().getColumn(1).getWidth();
         int minWidth = (getWidth() - (c0w + c1w)) - 18; // 18 -> some slop.
         for (int i = 0; i < mChangeList.size(); i++) {
            BindingFixerChange c = mChangeList.getChange(i);
            String m = c.getMessage();
            int width = mTable.getCellRenderer(i, DESCRIPTION_COLUMN).getTableCellRendererComponent(mTable, m, false, false, i, DESCRIPTION_COLUMN).getPreferredSize().width;
            int actualWidth = width + 16; // 16 = slop for row spacing, etc.
            minWidth = Math.max(actualWidth, minWidth);
         }
         mMinWidth = minWidth;
         mTable.getColumnModel().getColumn(DESCRIPTION_COLUMN).setPreferredWidth(mMinWidth);
      }
      super.doLayout();
   }

   /**
    * Returns true if there are ANY check-boxes that don't match the selected state.
    *
    * @param selected The selected state to match against.
    */
   public boolean canSelectAll(boolean selected) {
      if (mChangeList == null) {
         return false;
      }
      for (int i = 0; i < mChangeList.size(); i++) {
         BindingFixerChange c = mChangeList.getChange(i);
         if (c.canApply()) {
            if (c.getDoApply() != selected) {
               return true;
            }
         }
      }
      return false;
   }

   /**
    * Makes all of the check-boxes match the selected state.
    *
    * @param selected The selected state to set all check-boxes to.
    */
   public void selectAll(boolean selected) {
      for (int i = 0; i < mChangeList.size(); i++) {
         BindingFixerChange c = mChangeList.getChange(i);
         if (c.canApply()) {
            c.setDoApply(selected);
         }
      }
      repaint();
   }

   private void selectionChanged() {
      int row = mTable.getSelectedRow();
      // only care about rows.
      if (row < 0) {
         return;
      }
      BindingFixerChange c = mChangeList.getChange(row);
      Binding b = c.getTemplateReport().getBinding();
      if (mTree != null) { // null for testing only.
         BindingNode n = ((BindingNode) mTree.getRootNode()).findForBinding(b, true);
         if (n == null) {
            // Shouldn't happen, but if so, leave some indication:
            System.err.println("Error binding node found: " + b);
            return;
         }
         TreePath path = n.getTreePath();

         // Show what we're talking about:
         mTree.expandPath(path);
         mTree.setSelectionPath(path);
         mTree.scrollPathToVisible(path);
      }
   }

   public void applyChanges() {
      mChangeList.applyChanges();
   }

   public boolean hasAnySelectedChanges() {
      return mChangeList.hasAnySelectedChanges();
   }
}
