package com.tibco.cep.studio.mapper.ui.wizard;

import java.awt.Component;
import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A default implementation WizardPanel that provides convenient set methods for all of the properties.<br>
 * This is intended to either be used outright with the set methods or subclassed.
 */
public class DefaultWizardPanel implements WizardPanel {
   private Component component;
   private String label = "";
   private Vector<ChangeListener> panelChangeListeners = new Vector<ChangeListener>();
   private WizardPanel nextPanel;
   private WizardPanel previousPanel;
   private Runnable finisher;

   public DefaultWizardPanel() {
   }

   public String getLabel() {
      return label;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public void setComponent(Component component) {
      this.component = component;
   }

   public void setNextPanel(WizardPanel panel) {
      this.nextPanel = panel;
   }

   public WizardPanel getNextPanel() {
      return nextPanel;
   }

   public void setPrevious(WizardPanel panel) {
      this.previousPanel = panel;
   }

   public Component getComponent() {
      return component;
   }

   public void addPanelChangeListener(ChangeListener changeListener) {
      panelChangeListeners.add(changeListener);
   }

   public void removePanelChangeListener(ChangeListener changeListener) {
      panelChangeListeners.remove(changeListener);
   }

   public void setFinisher(Runnable r) {
      this.finisher = r;
   }

   public boolean canFinish() {
      return nextPanel == null;
   }

   public boolean canGoToNext() {
      return nextPanel != null;
   }

   public boolean canGoToPrevious() {
      return previousPanel != null;
   }

   public WizardPanel previous() {
      return previousPanel;
   }

   public WizardPanel next() {
      return nextPanel;
   }

   public void finish() {
      if (finisher != null) {
         finisher.run();
      }
   }

   public void enter() {
   }

   /**
    * For subclasses to call when something changes.
    */
   protected void firePanelChange() {
      for (int i = 0; i < panelChangeListeners.size(); i++) {
         ChangeListener cl = panelChangeListeners.get(i);
         cl.stateChanged(new ChangeEvent(this));
      }
   }
}
