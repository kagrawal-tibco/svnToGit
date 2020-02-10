package com.tibco.cep.studio.mapper.ui.data.formula;

import javax.swing.JSplitPane;
import javax.swing.event.ChangeListener;

import com.tibco.cep.studio.mapper.ui.data.xpath.XTypeChecker;

/**
 * A {@link FormulaWindow} which splits the formula into pieces & displays in sub-windows..
 */
public abstract class SplittingFormulaWindow extends FormulaWindow {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private FormulaWindow m_piece1;
   private FormulaWindow m_piece2;

   public SplittingFormulaWindow(FormulaWindow piece1, FormulaWindow piece2) {
      m_piece1 = piece1;
      m_piece2 = piece2;
      JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, m_piece1, m_piece2);
      add(jsp);
   }

   public void addChangeListener(ChangeListener changeListener) {
      m_piece1.addChangeListener(changeListener);
      m_piece2.addChangeListener(changeListener);
   }

   public String getFormula() {
      return reassembleFormula(m_piece1.getFormula(), m_piece2.getFormula());
   }

   public XTypeChecker getTypeChecker() {
      return null;
   }

   public void removeChangeListener(ChangeListener changeListener) {
      m_piece1.removeChangeListener(changeListener);
      m_piece2.removeChangeListener(changeListener);
   }

   public void setFormula(String formula) {
      String[] s = disassembleFormula(formula);
      m_piece1.setFormula(s[0]);
      m_piece2.setFormula(s[1]);
   }

   public abstract String reassembleFormula(String piece1, String piece2);

   public abstract String[] disassembleFormula(String formula);
}
