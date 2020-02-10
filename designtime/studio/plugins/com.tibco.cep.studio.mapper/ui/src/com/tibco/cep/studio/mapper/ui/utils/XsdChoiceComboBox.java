package com.tibco.cep.studio.mapper.ui.utils;

import java.util.ArrayList;

import javax.swing.JComboBox;

import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * A simple control that presents the choices of a choice {@link SmModelGroup}.<br>
 */
public class XsdChoiceComboBox extends JComboBox {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private SmSequenceType[] m_choices;

   /**
    * Creates the list of choices from the term.<br>
    * Includes both elements and non-elements (i.e. sequences), use {@link #XsdChoiceComboBox(com.tibco.xml.schema.SmParticleTerm, boolean)} with true
    * to restrict to only elements.
    *
    * @param term The term, may not be null.
    */
   public XsdChoiceComboBox(SmParticleTerm term) {
      this(term, false);
   }

   /**
    * Creates the list of choices from the term, but only elements are considered choices.
    *
    * @param term The term, may not be null.
    */
   public XsdChoiceComboBox(SmParticleTerm term, boolean elementOnly) {
      this(stripNonElements(SmSequenceTypeSupport.getAllChoices(SmSequenceTypeFactory.create(term)), elementOnly));
   }

   /**
    * Creates the list of choices.
    *
    * @param types The choices.
    */
   public XsdChoiceComboBox(SmSequenceType[] types) {
      m_choices = types;
      for (int i = 0; i < m_choices.length; i++) {
         addItem(SmSequenceTypeSupport.getDisplayName(m_choices[i]));
      }
   }

   public SmElement getSelectedElement() {
      SmSequenceType t = getSelectedXType();
      if (t == null) {
         return null;
      }
      SmSequenceType st = SmSequenceTypeSupport.stripOccursAndParens(t);
      return (SmElement) st.getParticleTerm();
   }

   public SmSequenceType getSelectedXType() {
      int idx = getSelectedIndex();
      if (idx < 0) {
         return null;
      }
      return m_choices[idx];
   }

   private static SmSequenceType[] stripNonElements(SmSequenceType[] types, boolean elementsOnly) {
      if (!elementsOnly) {
         return types;
      }
      //m_choices = choices;
      ArrayList<SmSequenceType> temp = new ArrayList<SmSequenceType>();
      for (int i = 0; i < types.length; i++) {
         SmSequenceType t = SmSequenceTypeSupport.stripOccursAndParens(types[i]);
         if (t.getParticleTerm() instanceof SmElement) {
            temp.add(types[i]);
         }
      }
      return temp.toArray(new SmSequenceType[0]);
   }

   /**
    * Gets the choices as passed in from the constructor (or computed from the constructor argument)
    *
    * @return The array of choices, never null.
    */
   public SmSequenceType[] getChoices() {
      return m_choices;
   }
}