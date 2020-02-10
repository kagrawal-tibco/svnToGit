package com.tibco.cep.studio.mapper.ui.data.bind.fix;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.bind.CancelChecker;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.DataIcons;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingTree;
import com.tibco.cep.studio.mapper.ui.data.utils.ButtonUtils;
import com.tibco.cep.studio.mapper.ui.data.utils.DisplayConstants;
import com.tibco.cep.studio.mapper.ui.data.utils.LazyComponentLoader;
import com.tibco.cep.studio.mapper.ui.data.utils.LazyComponentLoaderRunnable;
import com.tibco.cep.studio.mapper.ui.data.utils.OKCancelPanel;

/**
 * A dialog which shows errors & allows selective choosing of which elements to fix.
 */
class BindingFixerPanel extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JButton mOKButton;
   private JButton mCancelButton;
   private JButton mSelectAllButton;
   private JButton mDeselectAllButton;
   private JToggleButton m_showWarningsButton;
   private BindingFixerListView m_listView;
   private LazyComponentLoader m_lazyComponentLoader;
   private boolean m_editable = true;

   private boolean m_setting; // for toggle buttons.

   /**
    * Creates the panel, the method {@link #load} must be called after set-up is ready.
    *
    * @param tree
    */
   public BindingFixerPanel(BindingTree tree) {
      super(new BorderLayout());

      JPanel bottom = buildBottomButtons();
      JPanel left = buildLeftButtons();
      m_lazyComponentLoader = new LazyComponentLoader();
      add(m_lazyComponentLoader, BorderLayout.CENTER);
      add(bottom, BorderLayout.SOUTH);
      add(left, BorderLayout.WEST);
      m_listView = new BindingFixerListView(tree);

      reenableButtons();
   }

   public void load() {
      reload();
   }

   public void setEditable(boolean editable) {
      mOKButton.setEnabled(editable);
      m_listView.setEditable(editable);
      m_editable = editable;
      reenableButtons();
   }

   public boolean isEditable() {
      return m_editable;
   }

   private void reload() {
      m_lazyComponentLoader.setComponentLoading(new LazyComponentLoaderRunnable() {
         public Component run(CancelChecker cancel) {
            m_listView.runAnalysis();
            reenableButtons();
            return m_listView;
         }
      });
   }

   private JPanel buildBottomButtons() {
      OKCancelPanel okc = new OKCancelPanel();
      mOKButton = okc.getOKButton();
      mCancelButton = okc.getCancelButton();

      mSelectAllButton = new JButton(DataIcons.getSelectAllLabel());
      mDeselectAllButton = new JButton(DataIcons.getDeselectAllLabel());

      ActionListener sel = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            selpressed(e);
         }
      };
      mSelectAllButton.addActionListener(sel);
      mDeselectAllButton.addActionListener(sel);

      JPanel selp = new JPanel(new GridLayout(1, 2, 8, 0));
      selp.add(mSelectAllButton);
      selp.add(mDeselectAllButton);
      selp.setBorder(DisplayConstants.getInternalBorder());

      JPanel panel = new JPanel(new BorderLayout());
      panel.add(selp, BorderLayout.WEST);
      panel.add(okc, BorderLayout.EAST);
      return panel;
   }

   private JPanel buildLeftButtons() {
      JPanel panel = new JPanel();

      String tt = ResourceBundleManager.getMessage("ae.validate.project.dialog.show.warnings.tooltip", getClass().getClassLoader());
      Icon ic = ResourceBundleManager.getIcon("ae.validate.project.dialog.show.warnings.icon", getClass().getClassLoader());
      if (ic == null) {
         ic = DataIcons.getStatementIcon(); // whatever, just in case.
      }

      m_showWarningsButton = ButtonUtils.makeBarToggleButton(ic, tt);

      panel.add(m_showWarningsButton);
      m_showWarningsButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            hideWarningsChanged();
         }
      });
      return panel;
   }

   private void selpressed(ActionEvent ae) {
      if (ae.getSource() == mSelectAllButton) {
         m_listView.selectAll(true);
         reenableButtons();
      }
      if (ae.getSource() == mDeselectAllButton) {
         m_listView.selectAll(false);
         reenableButtons();
      }
   }

   public void applyChanges() {
      m_listView.applyChanges();
   }

   public JButton getOKButton() {
      return mOKButton;
   }

   public JButton getCancelButton() {
      return mCancelButton;
   }

   public void writePreferences(UIAgent uiAgent, String prefix) {
      m_listView.writePreferences(uiAgent, prefix);
   }

   public void readPreferences(UIAgent uiAgent, String prefix) {
      m_listView.readPreferences(uiAgent, prefix);
      reenableButtons();
   }

   public boolean hasAnySelectedChanges() {
      return m_listView.hasAnySelectedChanges();
   }

   private void hideWarningsChanged() {
      if (m_setting) {
         return;
      }
      boolean sw = m_showWarningsButton.isSelected();
      if (sw == m_listView.getShowWarnings()) {
         return;
      }
      m_listView.setShowWarnings(sw);
      reenableButtons();
      reload();
   }

   private void reenableButtons() {
      mSelectAllButton.setEnabled(m_listView.canSelectAll(true) && m_editable);
      mDeselectAllButton.setEnabled(m_listView.canSelectAll(false) && m_editable);
      boolean sw = m_listView.getShowWarnings();

      m_setting = true;
      try {
         m_showWarningsButton.setSelected(sw);
      }
      finally {
         m_setting = false;
      }
   }
}
