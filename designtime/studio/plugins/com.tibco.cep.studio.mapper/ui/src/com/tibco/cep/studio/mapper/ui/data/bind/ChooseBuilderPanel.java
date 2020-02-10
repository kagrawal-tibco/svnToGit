package com.tibco.cep.studio.mapper.ui.data.bind;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.ChooseBinding;

/**
 * A simple panel, used in a {@link BindingEditor} menu action, which allows the selection of # of whens and otherwise
 * in a choose.
 */
public class ChooseBuilderPanel extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JTextField mWhenCount;
   private JCheckBox mOtherwise;

   public ChooseBuilderPanel() {
      super(new GridBagLayout());

      setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.BOTH;
      gbc.weightx = 0;
      gbc.insets = new Insets(0, 0, 8, 0);
      gbc.weighty = 0;
      JTextField numberOfConditions = new JTextField();
      mWhenCount = numberOfConditions;
      mOtherwise = new JCheckBox();

      gbc.gridx = 1;
      gbc.gridy = 1;
      gbc.weightx = 0;
      add(new JLabel(BindingEditorResources.NUMBER_OF_WHENS + ":  "), gbc);
      gbc.gridx = 2;
      add(numberOfConditions, gbc);
      gbc.gridx = 3;
      gbc.weightx = 1;
      add(new JLabel(), gbc);
      gbc.gridx = 1;
      gbc.gridy = 2;
      gbc.weightx = 0;
      add(new JLabel(BindingEditorResources.INCLUDE_OTHERWISE + ":  "), gbc);
      gbc.gridx = 2;
      gbc.weightx = 0;
      add(mOtherwise, gbc);
      gbc.weighty = 1;
      gbc.gridx = 1;
      gbc.gridy = 3;
      add(new JLabel(), gbc);

      mWhenCount.setColumns(3);
      mWhenCount.setText("2");
      mOtherwise.setSelected(true);
   }

   public boolean handlesBinding(Binding binding) {
      if (binding instanceof ChooseBinding) {
         return true;
      }
      return false;
   }

   public boolean getOtherwise() {
      return mOtherwise.isSelected();
   }

   private static final int SANE_UPPER_LIMIT = 50;

   public int getIfCount() {
      String text = mWhenCount.getText();
      try {
         int val = Integer.parseInt(text);
         if (val < 1) {
            return 1;
         }
         if (val > SANE_UPPER_LIMIT) {
            // artificial limit here..
            return SANE_UPPER_LIMIT;
         }
         return val;
      }
      catch (Exception e) {
         // whatever.
         return 2;
      }
   }

   public Result getResult() {
      Result r = new Result();
      r.m_numberOfWhens = getIfCount();
      r.m_includeOtherwise = getOtherwise();
      return r;
   }

   /**
    * A simple pair of values that is the result of this panel.
    */
   public static class Result {
      public int m_numberOfWhens;
      public boolean m_includeOtherwise;
   }
}

