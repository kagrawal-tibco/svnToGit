/*
	Wizard.java

	Created 8/20/2001

	Copyright 2001 TIBCO Software. All rights reserved.

*/

package com.tibco.cep.studio.mapper.ui.wizard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tibco.cep.studio.mapper.ui.data.DataIcons;


/**
 * A generic wizard implementation.<br>
 * The wizard is driven completely off the current {@link WizardPanel},
 * which can be set in the constructor or with {@link #setCurrentPanel(com.tibco.ui.wizard.WizardPanel)}.
 * Use {@link javax.swing.JDialog#setTitle(String)} to set the title of the wizard.
 */
public class Wizard extends JDialog {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JPanel panelPane = new JPanel(new BorderLayout());
   private WizardPanel currentPanel;
   private boolean previousButtonAllowed = true;
   private boolean cancelled = false;
   private String finishButtonTitle = DataIcons.getString("ae.wizard.finish.button.label"); //"Finish"; //WCETODO unhardcode.
   private JButton nextButton;
   private JButton finishButton;
   private JButton cancelButton;
   private JButton previousButton;
   private String mBaseTitle = "";
   private ChangeListener panelChangeListener = new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
         updateButtons();
      }
   };

   /**
    * Creates a new wizard over a frame.
    *
    * @param locationRelativeToComponent The frame to place the wizard over.
    * @see #create(java.awt.Window)
    * @see #setCurrentPanel(com.tibco.ui.wizard.WizardPanel)
    */
   public Wizard(Frame locationRelativeToComponent) {
      super(locationRelativeToComponent, true);
      prepare();
      setLocationRelativeTo(locationRelativeToComponent);
   }

   /**
    * Creates a new wizard over a dialog.
    *
    * @param locationRelativeToComponent The frame to place the wizard over.
    * @see #create(java.awt.Window)
    * @see #setCurrentPanel(com.tibco.ui.wizard.WizardPanel)
    */
   public Wizard(Dialog locationRelativeToComponent) {
      super(locationRelativeToComponent, true);
      prepare();
      setLocationRelativeTo(locationRelativeToComponent);
   }

   /**
    * Creates a wizard, positioned relative to the frame or dialog.  This method is useful because
    * of a JDialog issue --- it needs either a frame or a dialog and there is no way to pass in either.
    *
    * @param window The frame or dialog to place the wizard over.
    * @return A new wizard.
    * @see #setCurrentPanel(com.tibco.ui.wizard.WizardPanel)
    */
   public static Wizard create(Window window) {
      if (window instanceof Frame) {
         return new Wizard((Frame) window);
      }
      else {
         return new Wizard((Dialog) window);
      }
   }

   /**
    * Sets if the 'previous' button should be allowed to appear.<br>
    * By default, it is on.
    *
    * @param previousButtonAllowed True if the previous button is allowed.
    */
   public void setPreviousButtonAllowed(boolean previousButtonAllowed) {
      this.previousButtonAllowed = previousButtonAllowed;
   }

   /**
    * Gets if the 'previous' button should be allowed to appear.<br>
    * By default, it is on.
    */
   public boolean setPreviousButtonAllowed() {
      return previousButtonAllowed;
   }

   /**
    * Sets the text for the finish button.
    *
    * @param finishButtonString
    */
   public void setFinishButtonString(String finishButtonString) {
      this.finishButtonTitle = finishButtonString;
   }

   /**
    * Gets the text for the finish button.
    */
   public String getFinishButtonString() {
      return finishButtonTitle;
   }

   /**
    * @return True if the wizard ended with a cancel.
    */
   public boolean wasCancelled() {
      return cancelled;
   }

   private void prepare() {
      //setTitle(wizardInfo.getWizardTitle());
      JPanel rootPanel = new JPanel(new BorderLayout(5, 5));
      rootPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      // buttons

      // Unhardcode these labels:
      previousButton = new JButton(DataIcons.getString("ae.wizard.previous.button.label"));
      nextButton = new JButton(DataIcons.getString("ae.wizard.next.button.label"));
      cancelButton = new JButton(DataIcons.getString("ae.wizard.cancel.button.label"));
      finishButton = new JButton(finishButtonTitle);

      previousButton.addActionListener(new AbstractAction() {
         /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
            if (!currentPanel.canGoToPrevious()) {
               return;
            }
            setCurrentPanel(currentPanel.previous());
         }
      });

      nextButton.addActionListener(new AbstractAction() {
         /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
            if (!currentPanel.canGoToNext()) {
               return;
            }
            WizardPanel nextP = currentPanel.next();
            if (nextP == null) {
               // Instead of exploding, doing something nice for a change:
               nextP = buildNullPointerPanel("next panel from " + currentPanel.getClass().getName());
            }
            setCurrentPanel(nextP);
         }
      });

      cancelButton.addActionListener(new AbstractAction() {
         /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
            cancelled = true;
         }
      });

      finishButton.addActionListener(new AbstractAction() {
         /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
            if (!currentPanel.canFinish()) {
               return;
            }
            currentPanel.finish();
            setVisible(false);
            dispose();
         }
      });

      JPanel btnPanel = new JPanel(new GridLayout(1, 4, 5, 0));
      // Add previous button if requested
      if (previousButtonAllowed) {
         btnPanel.add(previousButton);
      }
      btnPanel.add(nextButton);
      btnPanel.add(finishButton);
      btnPanel.add(cancelButton);

      JPanel southPanel = new JPanel(new BorderLayout());
      southPanel.add(btnPanel, BorderLayout.EAST);
      rootPanel.add(panelPane, BorderLayout.CENTER);
      rootPanel.add(southPanel, BorderLayout.SOUTH);
      setContentPane(rootPanel);
      pack();
   }

   /**
    * Sets the currently displayed panel on the wizard.
    *
    * @param panel The panel to display.
    */
   public void setCurrentPanel(WizardPanel panel) {
      if (currentPanel != null) {
         currentPanel.removePanelChangeListener(panelChangeListener);
      }
      WizardPanel previousPanel = currentPanel;
      currentPanel = panel;

      panelPane.removeAll();
      if (panel == null) {
         // don't crash, just put in a message panel:
         panel = buildNullPointerPanel("Set current panel");
      }
      // send notification:
      panel.enter();
      updateTitle();
      Component display = panel.getComponent();
      if (display == null) {
         display = new JLabel("<html>A null JComponent was passed into the wizard from WizardPanel: " + panel);
      }
      currentPanel.setPrevious(previousPanel);
      currentPanel.addPanelChangeListener(panelChangeListener);

      panelPane.add(display, BorderLayout.CENTER);

      panelPane.revalidate();
      panelPane.repaint();
      updateButtons();
   }

   private void updateTitle() {
      String title = currentPanel == null ? null : currentPanel.getLabel();
      if (title == null || title.length() == 0) {
         super.setTitle(mBaseTitle);
      }
      else {
         super.setTitle(mBaseTitle + " - " + title);
      }
   }

   public void setTitle(String title) {
      mBaseTitle = title;
      updateTitle();
   }

   /**
    * Builds a panel that displays 'a null panel was passed in' (rather than just crashing left & right)
    *
    * @return A null panel panel.
    */
   private WizardPanel buildNullPointerPanel(String context) {
      DefaultWizardPanel def = new DefaultWizardPanel();
      def.setLabel("error");
      JPanel panel = new JPanel(new BorderLayout());
      panel.add(new JLabel("<html>A null WizardPanel was passed into the wizard at:" + context + ".<br/>This is a bug. Please fix it."));
      def.setComponent(panel);
      return def;
   }

   private void updateButtons() {
      finishButton.setEnabled(currentPanel.canFinish());
      nextButton.setEnabled(currentPanel.canGoToNext());
      previousButton.setEnabled(currentPanel.canGoToPrevious());
   }
}
