package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.ParenSmSequenceType;
import com.tibco.util.RuntimeWrapException;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmGlobalComponentNotFoundException;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmSupport;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.SmWildcard;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;


/**
 * A type coercions (casts) expressed by an ordered set
 * of xpaths to SmType or SmElements (for type and element casting)
 */
public class Coercion
{
    private final String m_xpath;
    private final String m_typeOrElementQName;
    private final int m_coercionType;
    private final SmCardinality m_occurrence;

    public static final int COERCION_TYPE = 0;
    public static final int COERCION_ELEMENT = 1;
    public static final int COERCION_GROUP = 2;

    public Coercion(String xpath, String typeOrElementName, int coercionType)
    {
        this(xpath,typeOrElementName,coercionType,null);
    }

    /**
     *
     * @param xpath The xpath to reach the type.
     * @param typeOrElementName The qname of the substituted type or element.
     * @param coercionType One of {@link #COERCION_TYPE} {@link #COERCION_ELEMENT} or {@link #COERCION_GROUP}
     * @param card May be null, if set, will force the specified occurrence on the substituted type rather than
     * taking the 'natural' occurrence (i.e. if substituting wildcard*, maybe want to substitute with MyElement?, not
     * MyElement*)
     */
    public Coercion(String xpath, String typeOrElementName, int coercionType, SmCardinality card)
    {
        if (xpath==null || typeOrElementName==null)
        {
            throw new NullPointerException();
        }
        m_xpath = xpath;
        m_typeOrElementQName = typeOrElementName;
        m_coercionType = coercionType;
        m_occurrence = card;
    }

    public String getXPath()
    {
        return m_xpath;
    }

    public String getTypeOrElementName()
    {
        return m_typeOrElementQName;
    }

    public int getType()
    {
        return m_coercionType;
    }

    /**
     * Gets the optional cardinality.
     * @return The cardinality, or null.
     */
    public SmCardinality getOccurrence()
    {
        return m_occurrence;
    }

    public ExprContext applyTo(ExprContext context)
    {
        return applyTo(context,false);
    }

    /**
     * Generates a new 'state' given that the coercion is true.
     * @param context The context.
     * @param addXPathMarker If true, for every substitution adds a {@link SmSequenceType#TYPE_CODE_PAREN} marker with the xpath.
     * @return The new context.
     */
    public ExprContext applyTo(ExprContext context, boolean addXPathMarker)
    {
        Expr e = Parser.parse(m_xpath);
        XPathTypeReport tcontextr = e.evalType(context);
        SmSequenceType tcontext = tcontextr.xtype;
        if (SmSequenceTypeSupport.isPreviousError(tcontext))
        {
            return context;
        }
        ExpandedName ename = new QName(m_typeOrElementQName).getExpandedName(context.getNamespaceMapper());
        if (ename==null || ename.getLocalName().length()==0)
        {
            return context; // do nothing for now.
        }
        SmSequenceType updated = null;
        if (m_coercionType==COERCION_TYPE)
        {
            // get el first.
            SmNamespace ns = context.getInputSchemaProvider().getNamespace(ename.getNamespaceURI());
            if (ns==null)
            {
                return context;
            }
            SmType t = (SmType) ns.getComponent(SmComponent.TYPE_TYPE,ename.getLocalName());
            if (t==null)
            {
                return context;
            }
            updated = tcontext.assertType(t);
        }
        else
        {
            if (m_coercionType==COERCION_ELEMENT)
            {
                updated = SmSequenceTypeSupport.getElementInContext(ename,
                                                                    tcontext,
                                                                    context.getInputSmComponentProvider());
            }
            else
            {
               try {
                  updated = SmSequenceTypeSupport.getGroup(ename,
                                                           context.getInputSmComponentProvider());
               }
               catch (SmGlobalComponentNotFoundException el) {
                  throw new RuntimeWrapException(el);
               }
            }
        }
        // We want the type w/o any repetition (but don't want prime which would flatten other stuff)
        updated = SmSequenceTypeSupport.stripOccursAndParens(updated);
        if (addXPathMarker)
        {
        	updated = new ParenSmSequenceType(null, updated,m_xpath);
//            updated = SmSequenceTypeFactory.createParen(updated); //,m_xpath);
        }
        return e.assertEvaluatesTo(context,updated,m_occurrence);
    }

    /**
     * Type checks this coercion.
     * @param context The context to check this coercion against.
     * @return The error message or null if none.
     */
    public ErrorMessage checkApplyTo(ExprContext context, TextRange errorMessageTextRange)
    {
        if (errorMessageTextRange==null)
        {
            throw new NullPointerException();
        }
        Expr e = Parser.parse(m_xpath);
        XPathTypeReport tcontextr = e.evalType(context);
        SmSequenceType tcontext = tcontextr.xtype;
        if (tcontextr.errors.getCount()>0)
        {
            return tcontextr.errors.getErrorMessages()[0];
        }
        ExpandedName ename = new QName(m_typeOrElementQName).getExpandedName(context.getNamespaceMapper());
        if (ename==null || ename.getLocalName().length()==0)
        {
            // (If not yet filled out, don't mark as an error.)
            return null; // do nothing for now.
        }
        if (m_coercionType==COERCION_TYPE)
        {
            // get el first.
/*
            SmNamespace ns = context.getInputSchemaProvider().getNamespace(ename.getNamespaceURI());
            if (ns==null)
            {
                // Couldn't find schema:
                String msg = Xmlui_MMessageBundle.getMessage(MessageCode.CANNOT_LOAD_SCHEMA_FOR_TYPE_SUBSTITUTION);
                return new ErrorMessage(ErrorMessage.TYPE_ERROR,msg,errorMessageTextRange);
            }
*/
           SmType t = null;
           try {
              t = context.getInputSmComponentProvider().getType(ename);
           }
           catch (SmGlobalComponentNotFoundException ex) {
              throw new RuntimeWrapException(ex);
           }
           if (t==null)
            {
                String msg = ResourceBundleManager.getMessage(MessageCode.NO_SUCH_TYPE_FOR_TYPE_SUBSTITUTION);
                return new ErrorMessage(ErrorMessage.TYPE_ERROR,msg,errorMessageTextRange);
            }
            // Check that the type substitution makes sense here:
            SmParticleTerm term = tcontext.prime().getParticleTerm();
            if (term instanceof SmElement)
            {
                SmElement el = (SmElement) term;
                SmType at = el.getType();
                if (at!=null)
                {
                    if (!SmSupport.isEqualOrDerived(t,at))
                    {
                        // yikes, report this:
                        String msg = ResourceBundleManager.getMessage(MessageCode.BAD_TYPE_SUBSTITUTION,t.getName());
                        return new ErrorMessage(ErrorMessage.TYPE_ERROR,msg,errorMessageTextRange);
                    }
                }
            }
        }
        else
        {
            if (m_coercionType==COERCION_ELEMENT)
            {
                SmNamespace ns = context.getInputSchemaProvider().getNamespace(ename.getNamespaceURI());
                if (ns==null)
                {
                    // Couldn't find schema:
                    String msg = ResourceBundleManager.getMessage(MessageCode.CANNOT_LOAD_SCHEMA_FOR_ELEMENT);
                    return new ErrorMessage(ErrorMessage.TYPE_ERROR,msg,errorMessageTextRange);
                }
               SmElement t = null;
               try {
                  SmSequenceType sst =
                          SmSequenceTypeSupport.getElement(context.getInputSmComponentProvider(),
                                                           ename);
                  SmParticleTerm term = sst.getParticleTerm();
                  t = (SmElement) term;
               }
               catch (SmGlobalComponentNotFoundException ex) {
                  throw new RuntimeWrapException(ex);
               }
               if (t==null)
                {
                    String msg = ResourceBundleManager.getMessage(MessageCode.NO_SUCH_ELEMENT_IN_SCHEMA);
                    return new ErrorMessage(ErrorMessage.TYPE_ERROR,msg,errorMessageTextRange);
                }
                // Check that the type substitution makes sense here:
                SmParticleTerm term = tcontext.prime().getParticleTerm();
                if (term instanceof SmElement)
                {
                    SmElement el = (SmElement) term;
                    if (!SmSupport.substitutesFor(t,el))
                    {
                        // yikes, report this:
                        // (Mis-named error for now).
                        String msg = ResourceBundleManager.getMessage(MessageCode.BAD_TYPE_SUBSTITUTION,t.getName());
                        return new ErrorMessage(ErrorMessage.TYPE_ERROR,msg,errorMessageTextRange);
                    }
                }
                if (term instanceof SmWildcard)
                {
                    if (!SmSupport.matches((SmWildcard)term,t.getNamespace()))
                    {
                        // (Mis-named error for now).
                        String msg = ResourceBundleManager.getMessage(MessageCode.BAD_TYPE_SUBSTITUTION,t.getName());
                        return new ErrorMessage(ErrorMessage.TYPE_ERROR,msg,errorMessageTextRange);
                    }
                }
            }
            // WCETODO Groups not yet checked.
        }
        return null;
    }

    public String toString()
    {
        return "Coerce: " + m_xpath + " type " + m_coercionType + " name " + m_typeOrElementQName;
    }
}

