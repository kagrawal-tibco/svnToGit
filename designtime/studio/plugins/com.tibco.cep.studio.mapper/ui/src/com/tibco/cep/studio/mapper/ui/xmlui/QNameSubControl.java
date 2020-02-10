package com.tibco.cep.studio.mapper.ui.xmlui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.xml.sax.SAXException;

import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.utils.ErrorCheckingComboBox;
import com.tibco.cep.studio.mapper.ui.data.utils.ErrorCheckingComboBoxChecker;

/**
 * A sub-field panel for choosing XML resources (QNames)
 * (Generalize to be a better QName picker, right now schema locked...)
 */
public class QNameSubControl extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private UIAgent uiAgent;
   private ErrorCheckingComboBox m_comboBox;
   private QNamePanel m_panel; // this or
   private QNameSubControl m_subControl; // this will be set.
   private QNamePluginSubField m_subFieldPlugin;
   private HashSet<String> m_currentValidNames = new HashSet<String>();

   private QNameSubControl m_chainedSubControl;

   private Object m_currentObject;
   private boolean m_isSetting; // to filter out bogus change events.

   private boolean m_errorChecking = true;

   /**
    * The java bean property that changes when the {@link #getSelectedName} changes.
    */
   public static final String VALUE_PROPERTY = "value";

   /**
    * Constructor for a sub-control off of a panel.
    */
   public QNameSubControl(QNamePanel panel, QNamePluginSubField subField) {
      super(new BorderLayout());
      uiAgent = panel.getUIAgent();
      m_panel = panel;
      m_panel.setSubControl(this);
      m_subFieldPlugin = subField;
      setup();
   }

   /**
    * Constructor for a sub-control off of another sub-control.
    */
   public QNameSubControl(QNameSubControl subControl, QNamePluginSubField subField) {
      super(new BorderLayout());
      uiAgent = subControl.getUIAgent();
      m_panel = null;
      m_subControl = subControl;
      m_subControl.setChainedSubControl(this);
      m_subFieldPlugin = subField;
      setup();
   }

   public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);
      m_comboBox.setEnabled(enabled);
      m_comboBox.setEditable(enabled);
   }

   private void setup() {
      m_comboBox = new ErrorCheckingComboBox();
      m_comboBox.setErrorChecker(new ErrorCheckingComboBoxChecker() {
         public boolean isValid(String name) {
            if (!m_errorChecking) {
               return true; // always valid if not error checking.
            }
            return m_currentValidNames.contains(name);
         }
      });
      add(m_comboBox);
      // can type in garbage here...
      m_comboBox.setEditable(true);
      m_comboBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if (!m_isSetting) {
               reloadChainedOptions();
               fireChange();
            }
         }
      });
      // can type in garbage here...
      m_comboBox.setEditable(true);
   }

   /**
    * @param object The opaque object for the context, or null if none.
    */
   void reloadOptions(Object object) {
      m_currentObject = object;
      m_isSetting = true;
      try {
         String oldValue = (String) m_comboBox.getSelectedItem();
         String[] names;
         try {
            if (object == null) {
               names = new String[0];
            }
            else {
               names = m_subFieldPlugin.getChoicesFor(object);
            }
         }
         catch (SAXException se) {
            // eat it, can't reasonably do anything.
            names = null;
         }
         if (names == null) {
            names = new String[0];
         }
         Arrays.sort(names);
         m_comboBox.removeAllItems();
         m_currentValidNames.clear();
         for (int i = 0; i < names.length; i++) {
            m_comboBox.addItem(names[i]);
            m_currentValidNames.add(names[i]);
         }

         // Must do this because JComboBox falls on its nose if there are items matching the selection (loses name)
         getComboField().setText(oldValue);
         // attempt to select an existing choice:
         m_comboBox.setSelectedItem(oldValue);
      }
      finally {
         m_isSetting = false;
      }
   }

   private JTextField getComboField() {
      return (JTextField) m_comboBox.getEditor().getEditorComponent();
   }

   public void setSubFieldType(QNamePluginSubField sf) {
      m_subFieldPlugin = sf;
   }

   public QNamePluginSubField getPlugin() {
      return m_subFieldPlugin;
   }

   /**
    * Sets if error checking (i.e. red underlines) is enabled or not for this usage.<br>
    * By default this is <code>true</code>.
    */
   public void setErrorChecking(boolean enabled) {
      if (enabled != m_errorChecking) {
         repaint();
         m_errorChecking = enabled;
      }
   }

   public boolean getErrorChecking() {
      return m_errorChecking;
   }

   private void reloadChainedOptions() {
      if (m_chainedSubControl != null) {
         if (m_currentObject == null) {
            m_chainedSubControl.reloadOptions(null);
         }
         else {
            String name = getSelectedName();
            if (name == null) {
               m_chainedSubControl.reloadOptions(null);
            }
            else {
               Object obj = m_subFieldPlugin.getObjectFor(m_currentObject, name);
               m_chainedSubControl.reloadOptions(obj);
            }
         }
      }
   }

   /**
    * Package private so that {@link QNamePanel} can call this when required.
    */
   void fireChange() {
      super.firePropertyChange(VALUE_PROPERTY, null, getSelectedName());
   }

   public void setSelectedName(String name) {
      m_isSetting = true;
      if (name == null) {
         name = "";
      }
      m_comboBox.setSelectedItem(name);
      m_isSetting = false;
      reloadChainedOptions();
   }

   public String getSelectedName() {
      return (String) m_comboBox.getSelectedItem();
   }

   UIAgent getUIAgent() {
      return uiAgent;
   }

   void setChainedSubControl(QNameSubControl subControl) {
      if (m_chainedSubControl != null) {
         throw new IllegalStateException("Attempting to set a second sub-controls; only 1 allowed");
      }
      m_chainedSubControl = subControl;
   }

   QNameSubControl getChainedSubControl() {
      return m_chainedSubControl;
   }
}
