package com.tibco.cep.mapper.xml.xdata.xpath;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.ParenSmSequenceType;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmSupport;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.SmWildcard;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * A subclass of Coercion, this class models multiple Coercions with a common
 * substituted-for xpath as a single choice group.  That way, we can emulate
 * a choice group when displaying mapping possibilities to the user.
 *
 * User: jbaysdon
 * Date: Jul 23, 2004
 * Version ${VERSION}
 * See LOG at end of file for modification notes.
 */

public class CoercionChoiceGroup extends Coercion {

   private SmSequenceType m_groupType;
   private List m_originalCoercions;

   /**
    *
    * @param xpath The xpath to reach the type.
    * @param typeOrElementName The qname of the substituted type or element.
    * @param groupType Type of group
    * @param card May be null, if set, will force the specified occurrence on the substituted type rather than
    * taking the 'natural' occurrence (i.e. if substituting wildcard*, maybe want to substitute with MyElement?, not
    * MyElement*)
    */
   public CoercionChoiceGroup(String xpath, String typeOrElementName,
                              SmSequenceType groupType, SmCardinality card, List originalCoercions)
   {
      super(xpath, typeOrElementName, Coercion.COERCION_GROUP, card);
      setGroupType(groupType);
      setOriginalCoercions(originalCoercions);
   }

   public SmSequenceType getGroupType() {
      return m_groupType;
   }

   public void setGroupType(SmSequenceType groupType) {
      m_groupType = groupType;
   }

   public List getOriginalCoercions() {
      return m_originalCoercions;
   }

   public void setOriginalCoercions(List originalCoercions) {
      m_originalCoercions = originalCoercions;
   }

   /**
    * Generates a new 'state' given that the coercion is true.
    * @param context The context.
    * @param addXPathMarker If true, for every substitution adds a {@link com.tibco.xml.schema.SmSequenceType#TYPE_CODE_PAREN} marker with the xpath.
    * @return The new context.
    */
   public ExprContext applyTo(ExprContext context, boolean addXPathMarker)
   {
       Expr e = Parser.parse(getXPath());
       XPathTypeReport tcontextr = e.evalType(context);
       SmSequenceType tcontext = tcontextr.xtype;
       if (SmSequenceTypeSupport.isPreviousError(tcontext))
       {
           return context;
       }
       ExpandedName ename = new QName(getTypeOrElementName()).getExpandedName(context.getNamespaceMapper());
       if (ename==null || ename.getLocalName().length()==0)
       {
           return context; // do nothing for now.
       }
       SmSequenceType updated = getGroupType();

       // We want the type w/o any repetition (but don't want prime which would flatten other stuff)
       updated = SmSequenceTypeSupport.stripOccursAndParens(updated);
       if (addXPathMarker)
       {
    	   updated = new ParenSmSequenceType(null, updated,getXPath());
//           updated = SmSequenceTypeFactory.createParen(updated); // ,getXPath());
       }
       return e.assertEvaluatesTo(context,updated,getOccurrence());
   }

   /**
    * Type checks this coercion.
    * @param context The context to check this coercion against.
    * @return The error message or null if none.
    */
   public List checkGroupApplyTo(ExprContext context, TextRange errorMessageTextRange)
   {
       ArrayList errors = new ArrayList();
       if (errorMessageTextRange==null)
       {
           throw new NullPointerException();
       }
       Expr e = Parser.parse(getXPath());
       XPathTypeReport tcontextr = e.evalType(context);
       SmSequenceType subForXType = tcontextr.xtype;
       SmParticleTerm subForTerm = subForXType.prime().getParticleTerm();
       if(subForTerm instanceof SmElement) {
          SmElement subForElem = (SmElement)(subForTerm);
          if (tcontextr.errors.getCount()>0)
          {
              errors.add(tcontextr.errors.getErrorMessages()[0]);
              return errors;
          }
          ExpandedName ename = new QName(getTypeOrElementName()).getExpandedName(context.getNamespaceMapper());
          if (ename==null || ename.getLocalName().length()==0)
          {
              // (If not yet filled out, don't mark as an error.)
              return errors; // do nothing for now.
          }

          // WCETODO Groups not yet checked.

          // Check each member of the group, ensuring that it is a proper substitution.
          SmSequenceType groupType = getGroupType();
          SmSequenceType leftSide = groupType.getFirstChildComponent();
          SmSequenceType rightSide = groupType.getSecondChildComponent();
          return validatePairs(subForElem, errorMessageTextRange, leftSide, rightSide, errors);
       }
       return errors;
   }

   private List validatePairs(SmElement subForElem, TextRange errorMessageTextRange,
                                      SmSequenceType leftSide, SmSequenceType rightSide, List errors) {
      // Check SmType or SmDataComponent?
      if(leftSide.getParticleTerm() != null) {
         // Check SmDataComponent
         SmElement leftElem = (SmElement) leftSide.getParticleTerm();
         if (!SmSupport.substitutesFor(leftElem, subForElem))  {
            // yikes, report this:
            // (Mis-named error for now).
            String msg = ResourceBundleManager.getMessage(MessageCode.BAD_TYPE_SUBSTITUTION,
                                                   leftElem.getName());
            errors.add(new ErrorMessage(ErrorMessage.TYPE_ERROR,msg,errorMessageTextRange));
         }
         else {
            errors.add(null);
         }
         if (leftElem instanceof SmWildcard) {
            if (!SmSupport.matches((SmWildcard)leftElem,subForElem.getNamespace())) {
               // (Mis-named error for now).
               String msg = ResourceBundleManager.getMessage(MessageCode.BAD_TYPE_SUBSTITUTION,
                                                      leftElem.getName());
               errors.add(new ErrorMessage(ErrorMessage.TYPE_ERROR,msg,errorMessageTextRange));
            }
            else
               errors.add(null);
         }
      }
      else if(leftSide.getElementOverrideType() != null) {
         // Check SmType
         SmType myType = leftSide.getElementOverrideType();
         SmType subForType = subForElem.getType();
         if (subForType!=null)
         {
             if (!SmSupport.isEqualOrDerived(myType, subForType))
             {
                 // yikes, report this:
                 String msg = ResourceBundleManager.getMessage(MessageCode.BAD_TYPE_SUBSTITUTION,
                                                        myType.getName());
                 errors.add(new ErrorMessage(ErrorMessage.TYPE_ERROR,msg,errorMessageTextRange));
             }
         }
      }
      else if(leftSide.getSimpleType() != null) {
         // Check Simple Type
         SmType myType = leftSide.getSimpleType();
         SmType subForType = subForElem.getType();
         if (subForType!=null)
         {
             if (!SmSupport.isEqualOrDerived(myType, subForType))
             {
                 // yikes, report this:
                 String msg = ResourceBundleManager.getMessage(MessageCode.BAD_TYPE_SUBSTITUTION,
                                                        myType.getName());
                 errors.add(new ErrorMessage(ErrorMessage.TYPE_ERROR,msg,errorMessageTextRange));
             }
         }
      }
      // Is the rightSide another choice pair?
      SmSequenceType childLeftSide = rightSide.getFirstChildComponent();
      SmSequenceType childRightSide = rightSide.getSecondChildComponent();

      if(childRightSide != null) {
         // Recurse to continue validating pairs.
         return validatePairs(subForElem, errorMessageTextRange,
                              childLeftSide, childRightSide, errors);
      }
      return errors;
   }
   public String toString()
   {
       return "CoercionChoiceGroup: " + getXPath() + " type " + getType()
               + " name " + getTypeOrElementName();
   }

}

/*
 * ${Log}
 */