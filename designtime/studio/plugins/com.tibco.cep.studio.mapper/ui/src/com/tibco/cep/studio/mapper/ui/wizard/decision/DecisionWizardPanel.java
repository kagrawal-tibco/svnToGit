package com.tibco.cep.studio.mapper.ui.wizard.decision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import com.tibco.cep.studio.mapper.ui.data.utils.DisplayConstants;
import com.tibco.cep.studio.mapper.ui.wizard.DefaultWizardPanel;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;

/**
 * A WizardPanel implementation that presents a set of choices to the user.<br>
 * Each choice is represented as a radio button where labelled by the label of that WizardPanel's label.
 * If a choice's WizardPanel has a component, that is displayed inline with the choice.
 */
public class DecisionWizardPanel extends DefaultWizardPanel {
   private WizardPanel[] choices;
   private int currentSelection;
   private JRadioButton[] buttons;
   private Component mHeaderComponent;

   public DecisionWizardPanel(WizardPanel[] choices) {
      this.choices = choices;
      this.currentSelection = 0;
   }

   public DecisionWizardPanel() {
      this.currentSelection = 0;
   }

   public void setChoices(WizardPanel[] choices) {
      this.choices = choices;
      this.currentSelection = 0;
   }

   public WizardPanel[] getChoices() {
      return choices;
   }

   public Component getComponent() {
      if (super.getComponent() == null) {
         JPanel cp = buildChoicesPanel();
         JScrollPane jsp = new JScrollPane(cp);
         jsp.setBorder(BorderFactory.createEmptyBorder());
         setComponent(jsp);
      }
      return super.getComponent();
   }

   private JPanel buildChoicesPanel() {
      JPanel panel = new JPanel();
      panel.setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      ButtonGroup bg = new ButtonGroup();
      gbc.fill = GridBagConstraints.BOTH;
      gbc.weightx = 0;
      gbc.weighty = 0;
      gbc.insets = new Insets(2, 2, 2, 2);
      ActionListener al = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            choiceChanged();
         }
      };
      buttons = new JRadioButton[choices.length];
      Border emptyb = BorderFactory.createEmptyBorder(4, 4, 4, 4);
      Border lineb = BorderFactory.createLineBorder(Color.lightGray);
      Border choiceBorder = BorderFactory.createCompoundBorder(lineb, emptyb);
      Border indentBorder = BorderFactory.createEmptyBorder(0, 12, 0, 0);
      for (int i = 0; i < choices.length; i++) {
         gbc.gridy++;
         gbc.weightx = 0;

         JRadioButton choice = buttons[i] = new JRadioButton(choices[i].getLabel());
         JPanel choicePanel = new JPanel(new BorderLayout());
         choicePanel.add(choice, BorderLayout.NORTH);
         bg.add(choice);
         panel.add(choicePanel, gbc);
         Component c = choices[i].getComponent();
         if (c != null) {
            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.add(c);
            wrapper.setBorder(indentBorder);//BorderFactory.createLineBorder(Color.black));
            choicePanel.add(wrapper, BorderLayout.CENTER);
         }
         choicePanel.setBorder(choiceBorder);
         if (i == 0) {
            choice.setSelected(true);
         }
         choice.addActionListener(al);
      }
      gbc.weighty = 1;
      gbc.weightx = 1;
      gbc.gridy++;
      panel.add(new JPanel(), gbc); // filler.
      if (mHeaderComponent != null) {
         return DisplayConstants.createHeaderPanel(mHeaderComponent, panel);
      }
      else {
         return panel;
      }
   }

   public boolean canFinish() {
      if (choices.length == 0) {
         return false;
      }
      return choices[currentSelection].canFinish();
   }

   public boolean canGoToNext() {
      if (choices.length == 0) {
         return false;
      }
      return choices[currentSelection].canGoToNext();
   }

   public WizardPanel next() {
      WizardPanel sel = choices[currentSelection];
      return sel.next();
   }

   public void finish() {
      choices[currentSelection].finish();
   }

   public int getSelection() {
      return currentSelection;
   }

   public void setSelection(int selection) {
      currentSelection = Math.max(0, Math.min(selection, choices.length));
   }

   /**
    * For display, sets a component that appears ABOVE the choices (i.e. a help panel or something)
    */
   public void setHeaderComponent(Component c) {
      mHeaderComponent = c;
   }

   public Component getHeaderComponent() {
      return mHeaderComponent;
   }

   private void choiceChanged() {
      for (int i = 0; i < buttons.length; i++) {
         if (buttons[i].isSelected()) {
            currentSelection = i;
            break;
         }
      }
      super.firePanelChange();
   }
}
