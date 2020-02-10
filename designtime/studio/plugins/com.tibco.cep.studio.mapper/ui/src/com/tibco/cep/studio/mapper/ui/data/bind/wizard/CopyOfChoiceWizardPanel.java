package com.tibco.cep.studio.mapper.ui.data.bind.wizard;

import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.CopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateReport;
import com.tibco.cep.mapper.xml.xdata.bind.virt.TypeCopyOfBinding;
import com.tibco.cep.mapper.xml.xdata.xpath.type.XTypeComparator;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingWizardState;
import com.tibco.cep.studio.mapper.ui.wizard.DefaultWizardPanel;
import com.tibco.cep.studio.mapper.ui.wizard.WizardPanel;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmSequenceTypeRemainder;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * The wizard panel for a choice of copy-of (and related detection code).<br>
 */
public class CopyOfChoiceWizardPanel extends DefaultWizardPanel {
   private String m_xpath;
   private TemplateReport m_on;
   private BindingWizardState m_state;
   private SmElement m_optEl;

   private CopyOfChoiceWizardPanel(BindingWizardState state, TemplateReport on, SmSequenceType droppedType, String xpath, SmElement elForTypeCopy) {
      m_on = on;
      m_xpath = xpath;
      m_state = state;
      m_optEl = elForTypeCopy;
      String termName = SmSequenceTypeSupport.getDisplayName(droppedType);
      if (elForTypeCopy == null) {
         if (droppedType.quantifier().getMaxOccurs() > 1) {
            setLabel("Make a copy of each '" + termName + "'");
         }
         else {
            setLabel("Make a copy of '" + termName + "'");
         }
      }
      else {
         /*if (droppedType.quantifier().getMaxOccurs()>1)
         {
             setLabel("Copy-contents of each '" + termName + "'");
         }
         else
         {*/
         setLabel("Copy-contents of '" + termName + "'");
         //}
      }
   }

   /**
    * Optionally creates a wizard panel if a 'copy-of' makes sense.
    *
    * @param from     The from (drop) xtype.
    * @param relxpath The <b>relative</b> drop xpath.
    * @return A
    */
   public static WizardPanel createPanelIfApplicable(BindingWizardState state, SmSequenceType from, TemplateReport on, String relxpath) {
      // Often the LHS will have a not well done wildcard (at the same level as regular elements and where the 'local'
      // namespace isn't excluded).  When this happens, the type from the left hand side will be something like:
      // MatchedElement | MatchedElement, where one of those corresponds to the possibility that the wildcard <happens>
      // to be that element.
      // The method (below) accounts for that possibility & removes the wildcard matched 'MatchedElement' which allows
      // the copy-of detection logic to work in this scenario.
      from = SmSequenceTypeSupport.stripDuplicateWildcardMatchedTypes(from);

      SmSequenceType to = on.getExpectedType();
      if (to == null) {
         return null;
      }
      if (isCopyOf(from, to)) {
         return new CopyOfChoiceWizardPanel(state, on, from, relxpath, null);
      }
      if (isTypeCopyOf(from, to)) {
         SmElement el = (SmElement) to.prime().getParticleTerm();
         return new CopyOfChoiceWizardPanel(state, on, from, relxpath, el);
      }
      return null;
   }

   public void finish() {
      if (m_optEl == null) {
         CopyOfBinding cob = new CopyOfBinding(BindingElementInfo.EMPTY_INFO, m_xpath);
         BindingManipulationUtils.replaceInParent(m_on.getBinding(), cob);
         m_state.wizardFinished(cob);
      }
      else {
         TypeCopyOfBinding tcob = new TypeCopyOfBinding(BindingElementInfo.EMPTY_INFO, m_optEl.getExpandedName());
         tcob.setFormula(m_xpath);
         BindingManipulationUtils.replaceInParent(m_on.getBinding(), tcob);
         m_state.wizardFinished(tcob);
      }
   }


   private static boolean isCopyOf(SmSequenceType from, SmSequenceType to) {
      SmSequenceTypeRemainder r = from.getRemainingAfterType(to, false);
      if (r != null) {
         SmSequenceType toPrime = to.prime();
         SmSequenceType matchedPrime = r.getMatched().prime();
         int tc = XTypeComparator.compareAssignability(matchedPrime, toPrime);
         return (tc == XTypeComparator.EQUALS || tc == XTypeComparator.LEFT_ASSIGNABLE_TO_RIGHT);
      }
      return false;
   }

   private static boolean isTypeCopyOf(SmSequenceType from, SmSequenceType to) {
      SmParticleTerm t = to.prime().getParticleTerm();
      if (!(t instanceof SmElement)) {
         return false;
      }
      SmSequenceType fromType = SmSequenceTypeSupport.createAttributeAndChildAndTypedValueSequenceExcludeMagic(SmSequenceTypeSupport.stripOccursAndParens(from));
      SmSequenceType toType = SmSequenceTypeSupport.createAttributeAndChildAndTypedValueSequenceExcludeMagic(SmSequenceTypeSupport.stripOccursAndParens(to));
      int tc = XTypeComparator.compareAssignability(fromType, toType);
      return (tc == XTypeComparator.EQUALS || tc == XTypeComparator.LEFT_ASSIGNABLE_TO_RIGHT);
   }
}

;
