package com.tibco.cep.studio.mapper.ui.data.formula;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

/**
 * A abstract base class for all windows designed for formula editing.
 * Used in {@link FormulaDesignerWindow}.
 */
public abstract class FormulaWindow extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public FormulaWindow() {
      super(new BorderLayout());
   }

   /**
    * Sets the formula, will not trigger a change event.
    */
   public abstract void setFormula(String formula);

   /**
    * Gets the formula.
    */
   public abstract String getFormula();

   public abstract void addChangeListener(ChangeListener changeListener);

   public abstract void removeChangeListener(ChangeListener changeListener);
}
