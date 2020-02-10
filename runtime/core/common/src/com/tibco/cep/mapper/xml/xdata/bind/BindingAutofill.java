package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.mapper.xml.xdata.bind.virt.MarkerBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualDataComponentBinding;
import com.tibco.cep.mapper.xml.xdata.bind.virt.VirtualDataComponentCopyMode;
import com.tibco.cep.mapper.xml.xdata.xpath.type.XTypeComparator;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmWildcard;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * Performs the 'auto-fill' feature for when a drag and drop results in similar fields.
 * The static method {@link #autofill} automatically creates the candidates.
 */
public final class BindingAutofill
{
    private final TemplateReport m_on;
    private final boolean m_isCopy;
    private final ExpandedName m_name;
    private final boolean mIsAttribute;
    private final SmSequenceType mChildContext;
    private final boolean m_surroundWithIf; // If set, use virtualdatacomponent surround w/ if is appropriate (both input & output are optional)
    private final ExpandedName m_leadingElementName; // In some circumstances, can have an implied leading element name (for example matching attributes inside a non-for-each element)

    public BindingAutofill(TemplateReport on, ExpandedName name)
    {
        this(on,name,null,false,false,false,on.getChildContext().getInput());
    }

    /**
     *
     * @param on The report, may not be null.
     * @param name The name, may not be null.
     * @param leadingElementName The implied leading element name or null for none.
     * @param isAttr
     * @param isCopy
     * @param childContext
     */
    private BindingAutofill(TemplateReport on, ExpandedName name, ExpandedName leadingElementName, boolean isAttr, boolean isCopy, boolean surroundWithIf, SmSequenceType childContext)
    {
        if (name==null)
        {
            throw new NullPointerException();
        }
        if (on==null)
        {
            throw new NullPointerException();
        }
        m_on = on;
        m_name = name;
        mIsAttribute = isAttr;
        mChildContext = childContext;
        m_leadingElementName = leadingElementName;
        m_isCopy = isCopy;
        m_surroundWithIf = surroundWithIf;
    }

    public TemplateReport getOn()
    {
        return m_on;
    }

    public boolean isCouldBeCopy()
    {
        return m_isCopy;//mComparator!=null;
    }

    public boolean isAttribute()
    {
        return mIsAttribute;
    }

    /**
     * Gets the name of the element or attribute this represents.
     * @return The name, never null.
     */
    public ExpandedName getName()
    {
        return m_name;
    }

    /**
     * Gets the child context for the binding <b>if</b> this auto-fill were to take place.
     * @return
     */
    public SmSequenceType getChildContext() {
        return mChildContext;
    }

    /**
     * Indicates if this autofill is for a leaf element match (where the element is set by a formula, not by a for-each)
     */
    public boolean isLeafElement()
    {
        return !BindingManipulationUtils.isMarkerConvertedToForEach(m_on);
    }

    /**
     * Performs the autofill.
     * @param nm A namespace mapper.
     */
    public void enact(NamespaceContextRegistry nm, boolean useCopyIfApplicable)
    {
        String formula = getFormulaForName(nm,m_name);
        if (mIsAttribute)
        {
            formula = "@" + formula;
        }
        boolean isCopying = m_isCopy && useCopyIfApplicable;
        if (m_leadingElementName!=null && !isCopying)
        {
            // If we're autofilling an simple element with attributes, the attribute formulas will need the
            formula = getFormulaForName(nm,m_leadingElementName) + "/" + formula;
        }
        if (isCopying)
        {
            BindUtilities.insertCopyOf(m_on.getBinding(),formula);
        }
        else
        {
            Binding ub;
            if (m_on.getBinding() instanceof MarkerBinding)
            {
                ub = BindingManipulationUtils.convertMarkerCommentToFormula(m_on,formula,true);
                // Make sure we get the contents copied over so that child auto-fills will still work:
                // (They are going to do a replace in parent, so they need to be in their new parent's house)
                BindingManipulationUtils.copyBindingContents(m_on.getBinding(),ub);
            }
            else
            {
                ub = m_on.getBinding();
                m_on.getBinding().setFormula(formula);
            }
            if (ub instanceof VirtualDataComponentBinding && m_surroundWithIf)
            {
                ((VirtualDataComponentBinding)ub).setCopyMode(VirtualDataComponentCopyMode.OPTIONAL_TO_OPTIONAL);
            }
        }
    }

    private static String getFormulaForName(NamespaceContextRegistry nm, ExpandedName name)
    {
        String formula = name.getLocalName();
        if (name.getNamespaceURI()!=null && name.getNamespaceURI().length()>0)
        {
            String pfx = nm.getOrAddPrefixForNamespaceURI(name.getNamespaceURI());
            formula = pfx + ":" + formula;
        }
        return formula;
    }

    /**
     * Checks if there are any potential autofills given the context.
     * @param autofillCandidate The candidate binding.
     * @param context The new xpath dot (.) context.
     * @return true if there are any potential autofills, false otherwise.
     */
    public static boolean hasAutofill(TemplateReport autofillCandidate, SmSequenceType context)
    {
        // optimize later.
        return autofill(autofillCandidate,context,false).length>0;
    }

    public static BindingAutofill[] autofill(BindingAutofill parent)
    {
        return autofill(parent.getOn(),parent.getChildContext(),parent.isLeafElement());
    }

    public static BindingAutofill[] autofill(TemplateReport autofillCandidate, SmSequenceType context, boolean insideLeafElement)
    {
        ArrayList autofilledList = new ArrayList();
        TemplateReport[] children;
        if (autofillCandidate.getChildCount()==0)
        {
            BindingManipulationUtils.expandBinding(autofillCandidate);
            children = new TemplateReport[autofillCandidate.getBinding().getChildCount()];
            for (int i=0;i<children.length;i++)
            {
                children[i] = autofillCandidate.getBinding().getChild(i).createTemplateReport(
                        null,
                        autofillCandidate.getChildContext(),
                        autofillCandidate.getChildOutputContext(),
                        autofillCandidate.getChildOutputContext(),
                        SmWildcard.STRICT,
                        new TemplateReportFormulaCache(),
                        new TemplateReportArguments()
                        );
            }
        }
        else
        {
            children = autofillCandidate.getChildren();
        }
        for (int i=0;i<children.length;i++)
        {
            TemplateReport tr = children[i];
            autofillRow(tr,context,insideLeafElement,autofilledList);
        }
        return (BindingAutofill[]) autofilledList.toArray(new BindingAutofill[0]);
    }

    private static void autofillRow(TemplateReport autofillCandidate, SmSequenceType context, boolean insideLeafElement, List autofilledList)
    {
        if (autofillCandidate.getBinding() instanceof MarkerBinding)
        {
            SmSequenceType markerType = ((MarkerBinding)autofillCandidate.getBinding()).getMarkerType();
            SmSequenceType t = markerType.prime();
            SmCardinality outputCard = markerType.quantifier();
            boolean outputOptional = outputCard.getMinOccurs()==0;
            if (t.getParticleTerm() instanceof SmAttribute)
            {
                autofillAttribute(autofillCandidate,context,insideLeafElement,(SmAttribute)t.getParticleTerm(),outputOptional,autofilledList);
            }
            else
            {
                if (t.getParticleTerm() instanceof SmElement)
                {
                    autofillElement(autofillCandidate,context,insideLeafElement,(SmElement) t.getParticleTerm(),outputCard,autofilledList);
                }
            }
        }
        else
        {
            return;
        }
    }

    private static void autofillAttribute(TemplateReport autofillCandidate, SmSequenceType context, boolean insideLeafElement, SmAttribute attribute, boolean outputOptional, List autofilledList)
    {
        if (autofillCandidate.getBinding().getFormula()!=null)
        {
            // don't attempt to overwrite what's already there...
            return;
        }
        if (autofillCandidate.getBinding() instanceof ControlBinding)
        {
            // don't attempt to fill these in either...
            return;
        }
        String candidateName = attribute.getName();
        SmSequenceType result = context.attributeAxis().prime();
        // will be a choice of attributes.
        SmSequenceType[] possibilities = SmSequenceTypeSupport.getAllChoices(result);

        // First look for a matching attribute:
        for (int i=0;i<possibilities.length;i++) {
            SmSequenceType c = possibilities[i];
            if (c.getName()!=null)
            {
                String name = c.getName().getLocalName();
                if (name.equalsIgnoreCase(candidateName))
                {
                    boolean inputOptional = context.attributeAxis().nameTest(c.getName()).quantifier().getMinOccurs()==0;
                    boolean surroundWithIf = inputOptional && outputOptional;
                    ExpandedName optEl = insideLeafElement ? context.prime().getName() : null;
                    addAttributeMatch(autofillCandidate,c,optEl,surroundWithIf,autofilledList);
                    return;
                }
            }
        }

        // not found, try an element:
        possibilities = SmSequenceTypeSupport.getAllChoices(context.childAxis().prime());
        for (int i=0;i<possibilities.length;i++)
        {
            SmSequenceType c = possibilities[i];
            if (c.getName()!=null)
            {
                String name = c.getName().getLocalName();
                if (name.equalsIgnoreCase(candidateName))
                {
                    boolean inputOptional = context.childAxis().nameTest(c.getName()).quantifier().getMinOccurs()==0;
                    boolean surroundWithIf= inputOptional && outputOptional;
                    addElementMatch(autofillCandidate,c,surroundWithIf,autofilledList);
                    return;
                }
            }
        }
    }

    /**
     * @param autofillCandidate
     * @param context
     * @param element
     * @param outputCard Set to true if the cardinality of the output min occurs = 0.
     * @param autofilledList
     */
    private static void autofillElement(TemplateReport autofillCandidate, SmSequenceType context, boolean insideLeafElement, SmElement element, SmCardinality outputCard, List autofilledList)
    {
        if (autofillCandidate.getBinding().getFormula()!=null)
        {
            // don't attempt to overwrite what's already there...
            return;
        }
        if (autofillCandidate.getBinding() instanceof ControlBinding)
        {
            // don't attempt to fill these in either...
            return;
        }
        String candidateName = element.getName();
        SmSequenceType result = context.childAxis().prime();
        // will be a choice of elements.
        SmSequenceType[] possibilities = SmSequenceTypeSupport.getAllChoices(result);
        boolean isStructure = !SmSequenceTypeSupport.hasChildren(SmSequenceTypeFactory.create(element));

        // First look for a matching element (first):
        for (int i=0;i<possibilities.length;i++)
        {
            SmSequenceType c = possibilities[i];
            if (c.getName()!=null)
            {
                String name = c.getName().getLocalName();
                if (name.equalsIgnoreCase(candidateName))
                {
                    boolean leftHandIsStructure = !SmSequenceTypeSupport.hasChildren(c);
                    if (isStructure==leftHandIsStructure)
                    {
                        boolean inputOptional = context.childAxis().nameTest(c.getName()).quantifier().getMinOccurs()==0;
                        boolean surroundWithIf = inputOptional && outputCard.getMinOccurs()==0 && outputCard.getMaxOccurs()==1; // Only surround with if if optional (not if repeating)
                        addElementMatch(autofillCandidate,c,surroundWithIf,autofilledList);
                    }
                    else
                    {
                        // otherwise do no matching.
                    }
                    return;
                }
            }
        }

        if (!isStructure)
        {
            // not found, try an attribute:
            possibilities = SmSequenceTypeSupport.getAllChoices(context.attributeAxis().prime());
            for (int i=0;i<possibilities.length;i++)
            {
                SmSequenceType c = possibilities[i];
                if (c.getName()!=null)
                {
                    String name = c.getName().getLocalName();
                    if (name.equalsIgnoreCase(candidateName))
                    {
                        boolean inputOptional = context.attributeAxis().nameTest(c.getName()).quantifier().getMinOccurs()==0;
                        boolean surroundWithIf = inputOptional && outputCard.getMinOccurs()==0 && outputCard.getMaxOccurs()==1; // Only surround with if if optional (not if repeating)
                        ExpandedName optEl = insideLeafElement ? context.prime().getName() : null;
                        addAttributeMatch(autofillCandidate,c,optEl,surroundWithIf,autofilledList);
                        return;
                    }
                }
            }
        }
    }

    private static void addAttributeMatch(TemplateReport autofillCandidate, SmSequenceType attribute, ExpandedName optionalLeafElement, boolean surroundWithIf, List autofilledList)
    {
        autofilledList.add(new BindingAutofill(autofillCandidate,attribute.getName(),optionalLeafElement,true,false,surroundWithIf,attribute));
    }

    private static void addElementMatch(TemplateReport autofillCandidate, SmSequenceType element, boolean surroundWithIf, List autofilledList)
    {
        boolean isCopy = false;
        if (SmSequenceTypeSupport.hasChildren(element))
        {
            SmSequenceType et = autofillCandidate.getExpectedType();
            if (et!=null)
            {
                SmSequenceType compareTo = SmSequenceTypeSupport.stripOccursAndParens(et);
                SmSequenceType elementSt = SmSequenceTypeSupport.stripOccursAndParens(element);
                int r = XTypeComparator.compareAssignability(elementSt,compareTo);
                if (r==XTypeComparator.EQUALS || r==XTypeComparator.LEFT_ASSIGNABLE_TO_RIGHT)
                {
                    isCopy = true;
                }
            }
        }
        BindingAutofill child = new BindingAutofill(autofillCandidate,element.getName(),null,false,isCopy,surroundWithIf,element);
        autofilledList.add(child);
    }

    /**
     * For debugging/diagnostic purposes only.
     */
    public String toString()
    {
        // (Debugging only)
        return "autofill name: " + m_name + " copy " + m_isCopy;
    }
}

