package com.tibco.cep.studio.mapper.ui.data.bind.find;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.tibco.cep.studio.mapper.ui.xmlui.QNamePanelResources;

/**
 * A utility panel for entering search strings (allows case sensitive to be specified -- assumes is regex)
 */
public class SearchStringPanel extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JCheckBox m_isCaseSensitive;
   private JLabel m_fieldLabel;
   private JTextField m_field;
   private JLabel m_searchByLabel;
   private JComboBox m_searchType;

   private Vector<ChangeListener> m_changeListeners = new Vector<ChangeListener>();

   public SearchStringPanel() {
      super(new GridBagLayout());
      m_field = new JTextField();
      m_isCaseSensitive = new JCheckBox();
      m_isCaseSensitive.setSelected(true); // default this.
      m_field.getDocument().addDocumentListener(new DocumentListener() {
         public void changedUpdate(DocumentEvent e) {
            changed();
         }

         public void insertUpdate(DocumentEvent e) {
            changed();
         }

         public void removeUpdate(DocumentEvent e) {
            changed();
         }
      });
      m_isCaseSensitive.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            changed();
         }
      });

      m_fieldLabel = new JLabel(QNamePanelResources.NAME + ":");

      m_searchByLabel = new JLabel(QNamePanelResources.SEARCH_BY + ":");
      m_searchType = new JComboBox();
      m_searchType.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            changed();
         }
      });

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.BOTH;
      gbc.weightx = 0;
      gbc.weighty = 0;
      gbc.insets = new Insets(2, 2, 2, 2);
      gbc.gridy = 0;

      add(m_searchByLabel, gbc);
      gbc.gridx = 1;
      gbc.weightx = 0;
      add(m_searchType, gbc);

      gbc.gridy++;
      gbc.gridx = 0;
      add(m_fieldLabel, gbc);

      gbc.gridx = 1;
      gbc.gridwidth = 2;
      gbc.weightx = 1;
      add(m_field, gbc);
      gbc.gridwidth = 1;

      gbc.weightx = 0;
      gbc.gridx = 0;
      gbc.gridy++;
      add(new JLabel(QNamePanelResources.CASE_SENSITIVE + ":"), gbc);

      gbc.gridx = 1;
      add(m_isCaseSensitive, gbc);

      gbc.gridx = 0;
      gbc.gridy++;
      gbc.weighty = 1;
      add(new JLabel(), gbc); // spacer.
   }

   public void addSearchType(String name) {
      m_searchType.addItem(name);
   }

   public String getText() {
      return m_field.getText();
   }

   public String getSearchType() {
      return (String) m_searchType.getSelectedItem();
   }

   public boolean getCaseSensitive() {
      return m_isCaseSensitive.isSelected();
   }

   public void addChangeListener(ChangeListener cl) {
      m_changeListeners.add(cl);
   }

   public void removeChangeListener(ChangeListener cl) {
      m_changeListeners.remove(cl);
   }

   private void changed() {
      for (int i = 0; i < m_changeListeners.size(); i++) {
         ChangeListener cl = m_changeListeners.get(i);
         cl.stateChanged(new ChangeEvent(this));
      }
   }
}
