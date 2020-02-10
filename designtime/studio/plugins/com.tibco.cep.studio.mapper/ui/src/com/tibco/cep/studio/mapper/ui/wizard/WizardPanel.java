package com.tibco.cep.studio.mapper.ui.wizard;

import java.awt.Component;

import javax.swing.event.ChangeListener;

/**
 * A single panel inside a wizard; represents a single 'step' inside a Wizard flow.<br>
 * Contains methodsRepresents a single wizard 'state'. (doc more)
 */
public interface WizardPanel {

   /**
    * Gets the text label for this step of the wizard.
    *
    * @return The label.  Null if none.
    */
   public String getLabel();

   /**
    * Gets the display component of this step of the wizard.<br>
    * Panels are not re-used from entry to entry.
    *
    * @return The component.
    */
   public Component getComponent();

   /**
    * Notification that the panel has been entered.
    */
   public void enter();

   /**
    * Adds a change listener to the wizard panel.<br>
    * Change listeners should be notified anytime any of the following may change:
    * <ul>
    * <li>The answer to {@link #canFinish()}.</li>
    * <li>The answer to {@link #canGoToNext()}.</li>
    * </ul>
    *
    * @param changeListener The listener
    */
   public void addPanelChangeListener(ChangeListener changeListener);

   public void removePanelChangeListener(ChangeListener changeListener);

   /**
    * The wizard will call this method to indicate the panel whose next() went into this one.<br>
    * The default behavior should be to return this for {@link #previous()}, but an implementation may
    * choose to return a different panel.
    *
    * @param panel The previous panel, null if there was none.
    */
   public void setPrevious(WizardPanel panel);

   /**
    * Indicates that, as currently configured, the user can finish now.
    *
    * @return true if finish should be enabled.
    */
   public boolean canFinish();

   /**
    * Indicates that, as currently configured, the user can go to the next panel now.
    *
    * @return true if next should be enabled.
    */
   public boolean canGoToNext();

   /**
    * Indicates that, as currently configured, the user can go to the previous panel now.<br>
    * (If there is no previous panel, this should return false).
    *
    * @return true if previous should be enabled.
    */
   public boolean canGoToPrevious();

   /**
    * Gets the previous wizard panel, if applicable.<br>
    * This will not be called if {@link #canGoToPrevious()} is false.
    *
    * @return The previous wizard panel.
    */
   public WizardPanel previous();

   /**
    * Called when the user selects 'next' on the wizard.<br>
    * Will not be called if {@link #canGoToNext()} is false.
    *
    * @return The next wizard panel.
    */
   public WizardPanel next();

   /**
    * Called when the user selects 'finish' on the wizard.<br>
    * Will not be called if {@link #canFinish()} is false.
    */
   public void finish();
}
