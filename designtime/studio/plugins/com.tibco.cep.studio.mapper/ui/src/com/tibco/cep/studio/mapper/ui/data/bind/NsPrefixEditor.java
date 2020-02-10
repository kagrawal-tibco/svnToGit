package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import com.tibco.cep.mapper.xml.xdata.DefaultNamespaceMapper;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.basic.BasicTreeResources;
import com.tibco.cep.studio.mapper.ui.data.utils.ButtonUtils;

/**
 * An emergency ns-prefix editor.
 */
public class NsPrefixEditor extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JTable m_table;
   private JPanel m_buttonGrid;
   private JButton m_moveUp;
   private JButton m_moveDown;
   private JButton m_delete;
   private JButton m_add;
   private DefaultTableModel m_tableModel;
   private DefaultNamespaceMapper m_namespaces;

   private ActionListener m_actionListener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         performed(e);
      }
   };

   /**
    * @param doc
    * @param nm  The namespace/prefix mappings.
    */
   @SuppressWarnings("rawtypes")
public NsPrefixEditor(UIAgent uiAgent, DefaultNamespaceMapper nm) {
      super(new BorderLayout());
      m_namespaces = nm;
      // Tried to use TableFormField, but couldn't get AEResource/XiNode thing working... just do the simple thing.
      m_table = new JTable();
      DefaultTableModel model = new DefaultTableModel();
      m_tableModel = model;
      m_tableModel.setColumnIdentifiers(new String[]{DataIcons.getPrefixLabel(), DataIcons.getNamespaceLabel()});
      model.setColumnCount(2);
      m_table.setModel(model);

      JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
      m_buttonGrid = new JPanel(new GridLayout(1, 6, 2, 0));
      m_moveUp = makeButton(DataIcons.getMoveUpIcon(), BasicTreeResources.MOVE_UP);
      m_moveDown = makeButton(DataIcons.getMoveDownIcon(), BasicTreeResources.MOVE_DOWN);
      m_delete = makeButton(DataIcons.getDeleteIcon(), BasicTreeResources.DELETE);
      m_add = makeButton(DataIcons.getAddIcon(), BasicTreeResources.INSERT);

      m_buttonGrid.add(m_moveUp);
      m_buttonGrid.add(m_moveDown);
      m_buttonGrid.add(m_delete);
      m_buttonGrid.add(m_add);
      buttons.add(m_buttonGrid);
      add(buttons, BorderLayout.NORTH);

      JScrollPane jsp = new JScrollPane(m_table);
      jsp.setPreferredSize(new Dimension(50, 50));
      add(jsp);

      Iterator nd = m_namespaces.getPrefixes();
      while (nd.hasNext()) {
         String pfx = (String) nd.next();
         try {
            String ns = m_namespaces.getNamespaceURIForPrefix(pfx);
            m_tableModel.addRow(new String[]{pfx, ns});
         }
         catch (Exception e) {
            // Shouldn't happen, ignore.
         }
      }
      m_tableModel.addTableModelListener(new TableModelListener() {
         public void tableChanged(TableModelEvent e) {
            save();
         }
      });
      m_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent e) {
            reenable();
         }
      });
      reenable();
   }

   /**
    * Forces any active editing to be saved.
    */
   public void stopEditing() {
      TableCellEditor ce = m_table.getCellEditor();
      if (ce != null) {
         ce.stopCellEditing();
      }
   }

   private JButton makeButton(Icon icon, String tooltip) {
      JButton button = ButtonUtils.makeBarButton(icon, tooltip);
      button.addActionListener(m_actionListener);
      return button;
   }

   private void performed(ActionEvent ae) {
      if (ae.getSource() == m_add) {
         int rc = m_table.getSelectedRow();
         int at = rc < 0 ? 0 : rc + 1;
         String pfx = findUniquePrefix("pfx");
         m_tableModel.insertRow(at, new String[]{pfx, "http://"});
         m_table.getSelectionModel().setSelectionInterval(at, at);
         reenable();
      }
      if (ae.getSource() == m_delete) {
         int rc = m_table.getSelectedRow();
         if (rc >= 0) {
            m_tableModel.removeRow(rc);
            if (rc < m_tableModel.getRowCount()) {
               m_table.getSelectionModel().setSelectionInterval(rc, rc);
            }
            else {
               rc--; // deleted last row, back off one.
               if (rc >= 0) {
                  m_table.getSelectionModel().setSelectionInterval(rc, rc);
               }
            }
            reenable();
         }
      }
      if (ae.getSource() == m_moveUp) {
         int rc = m_table.getSelectedRow();
         if (rc >= 1) {
            String pfx = (String) m_tableModel.getValueAt(rc, 0);
            String ns = (String) m_tableModel.getValueAt(rc, 1);
            m_tableModel.removeRow(rc);
            int nr = rc - 1;
            m_tableModel.insertRow(nr, new String[]{pfx, ns});
            m_table.getSelectionModel().setSelectionInterval(nr, nr);
            reenable();
         }
      }
      if (ae.getSource() == m_moveDown) {
         int rc = m_table.getSelectedRow();
         if (rc < m_table.getRowCount() - 1 && rc >= 0) {
            String pfx = (String) m_tableModel.getValueAt(rc, 0);
            String ns = (String) m_tableModel.getValueAt(rc, 1);
            m_tableModel.removeRow(rc);
            int nr = rc + 1;
            m_tableModel.insertRow(nr, new String[]{pfx, ns});
            m_table.getSelectionModel().setSelectionInterval(nr, nr);
            reenable();
         }
      }
   }

   private String findUniquePrefix(String startWith) {
      for (int i = 0; ; i++) {
         String pfx = i == 0 ? startWith : (startWith + (i + 1));
         if (!containsPrefix(pfx)) {
            return pfx;
         }
      }
   }

   private boolean containsPrefix(String pfx) {
      for (int i = 0; i < m_tableModel.getRowCount(); i++) {
         String p = (String) m_tableModel.getValueAt(i, 0);
         if (p.equals(pfx)) {
            return true;
         }
      }
      return false;
   }

   @SuppressWarnings("rawtypes")
private void save() {
      int rc = m_tableModel.getRowCount();
      Iterator ip = m_namespaces.getLocalPrefixes();
      while (ip.hasNext()) {
         m_namespaces.removePrefix((String) ip.next());
      }
      for (int i = 0; i < rc; i++) {
         String pfx = (String) m_tableModel.getValueAt(i, 0);
         String ns = (String) m_tableModel.getValueAt(i, 1);
         // Note: pfx of "" indicates default namespace
         m_namespaces.addNamespaceURI(pfx, ns);
      }
   }

   private void reenable() {
      int r = m_table.getSelectedRow();
      int rc = m_tableModel.getRowCount();
      m_moveUp.setEnabled(r > 0);
      m_moveDown.setEnabled(r < rc - 1 && r >= 0);
      m_delete.setEnabled(r >= 0);
      m_add.setEnabled(true);
   }
}

