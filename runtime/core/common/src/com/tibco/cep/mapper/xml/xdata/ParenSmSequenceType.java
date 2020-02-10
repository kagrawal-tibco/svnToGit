package com.tibco.cep.mapper.xml.xdata;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.NamespaceResolver;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.data.primitive.XmlSequence;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmSequenceTypeContext;
import com.tibco.xml.schema.SmSequenceTypeRemainder;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * A () wrapping SmSequenceType.
 */
/*
 * Copied from the SmType API, along with AbstractSmSequenceType, so that ParenSmSequenceType can hold a user message for coercions
 */
public class ParenSmSequenceType extends AbstractSmSequenceType
{
    private final SmSequenceType m_contained;
    private final String m_userMessage;

    public ParenSmSequenceType(final SmSequenceTypeContext context, final SmSequenceType contained)
    {
    	super(context);
    	m_contained = contained;
    	m_userMessage = null;
    }
    
    public ParenSmSequenceType(final SmSequenceTypeContext context, final SmSequenceType contained, String userMessage)
    {
        super(context);
        m_contained = contained;
        m_userMessage = userMessage;
    }

    public String formatAsSequenceType(final NamespaceToPrefixResolver npr)
    {
        return "(" + m_contained.formatAsSequenceType(npr) + ")";
    }

    public void registerAllNamespaces(final NamespaceContextRegistry namespaceImporter)
    {
        m_contained.registerAllNamespaces(namespaceImporter);
    }

    public SmSequenceType getFirstChildComponent()
    {
        return m_contained;
    }

    public SmSequenceType getSecondChildComponent()
    {
        return null;
    }

    public int getTypeCode()
    {
        return TYPE_CODE_PAREN;
    }

    public boolean isInstanceFinal()
    {
        return false;
    }

    public boolean isNillable()
    {
        return m_contained.isNillable();
    }

    public boolean isNillabilityKnown()
    {
        return m_contained.isNillabilityKnown();
    }

    public String getUserMessage()
    {
        return m_userMessage;
    }

    public boolean isAbstractType(final boolean includeAny)
    {
        return false;
    }

    public SmSequenceType getElementInContext(final ExpandedName name)
    {
        return m_contained.getElementInContext(name);
    }

    public SmSequenceType getAttributeInContext(final ExpandedName name)
    {
        return m_contained.getAttributeInContext(name);
    }

    public SmSequenceType selfAxis()
    {
        return new ParenSmSequenceType(m_context, m_contained.selfAxis());
    }

    public SmSequenceType itemTest()
    {
        return new ParenSmSequenceType(m_context, m_contained.itemTest());
    }

    public SmSequenceType nodeTest()
    {
        return new ParenSmSequenceType(m_context, m_contained.nodeTest());
    }

    public SmSequenceType parentAxis()
    {
        if (m_context.getParent() != null)
        {
            return m_context.getParent();
        }
        return new ParenSmSequenceType(null, m_contained.parentAxis());
    }

    public SmParticleTerm getParticleTerm()
    {
        return null;
    }

    public SmSequenceType getDocumentType()
    {
        return m_contained.getDocumentType();
    }

    public SmType getElementOverrideType()
    {
        return null;
    }

    public ExpandedName getName()
    {
        return null;
    }

    public SmCardinality getOccurrence()
    {
        return null;
    }

    public SmSequenceType childAxis()
    {
        return m_contained.childAxis();
    }

    public SmSequenceType descendantAxis()
    {
        return m_contained.descendantAxis();
    }

    public SmSequenceType followingSiblingAxis()
    {
        return m_contained.followingSiblingAxis();
    }

    public SmSequenceType precedingSiblingAxis()
    {
        return m_contained.precedingSiblingAxis();
    }

    public SmCardinality quantifier()
    {
        return m_contained.quantifier();
    }

    public SmSequenceType attributeAxis()
    {
        return m_contained.attributeAxis();
    }

    public SmSequenceType textTest()
    {
        return m_contained.textTest();
    }

    public SmSequenceType commentTest()
    {
        return m_contained.commentTest();
    }

    public SmSequenceType processingInstructionTest(final ExpandedName optional, final String strval)
    {
        return m_contained.processingInstructionTest(optional, strval);
    }

    public XmlSequence getConstantValue()
    {
        return null;
    }

    public SmSequenceType nameTest(final ExpandedName name)
    {
        final SmSequenceType c = m_contained.nameTest(name);
        if (SmSequenceTypeSupport.isVoid(c))
        {
            return c;
        }
        return new ParenSmSequenceType(getContext(), c);
    }

    public SmSequenceTypeRemainder getRemainingAfterType(final SmSequenceType type, final boolean fullRepetitionMatch)
    {
        final int tc = type.getTypeCode();
        if (tc == SmSequenceType.TYPE_CODE_PAREN)
        {
            return m_contained.getRemainingAfterType(type.getFirstChildComponent(), fullRepetitionMatch);
        }
        else
        {
            return m_contained.getRemainingAfterType(type, fullRepetitionMatch);
        }
    }

    public SmSequenceType shortestRemaining(final SmSequenceType type)
    {
        throw new RuntimeException();
    }

    public SmSequenceTypeRemainder skip()
    {
        return m_contained.skip();
    }

    public SmSequenceType typedValue(final boolean typedInput)
    {
        return m_contained.typedValue(typedInput);
    }

    public SmSequenceType stringValue()
    {
        return m_contained.stringValue();
    }

    public boolean equalsType(final SmSequenceType t)
    {
        if (t.getTypeCode() != SmSequenceType.TYPE_CODE_PAREN)
        {
            return false;
        }
        return m_contained.equalsType((t.getFirstChildComponent()));
    }

    public SmSequenceType simplify(final boolean stripMarkers)
    {
        if (!stripMarkers && (getContext().getSource() != null))
        {
            return new ParenSmSequenceType(m_context, m_contained.simplify(stripMarkers));
        }
        return m_contained.simplify(stripMarkers);
    }

    public SmSequenceType prime()
    {
        final SmSequenceType r = m_contained.prime();
        if (SmSequenceTypeSupport.isVoid(r))
        {
            return r;
        }
        return new ParenSmSequenceType(getContext(), r);
    }

    public SmSequenceType commonPrimeWith(final SmSequenceType type)
    {
        return m_contained.commonPrimeWith(type);
    }

    public SmType getSimpleType()
    {
        return null;
    }

    public SmSequenceType mergeDuplicateChoices()
    {
        return m_contained.mergeDuplicateChoices();
    }

    public int hashCode()
    {
        return m_contained.hashCode() + 88;
    }

    public SmSequenceType createWithContext(final SmSequenceTypeContext context)
    {
        return new ParenSmSequenceType(context, m_contained.createWithContext(context));
    }

    public String getNodeStepElement(final NamespaceResolver namespaceToPrefixResolver)
    {
        return m_contained.getNodeStepElement(namespaceToPrefixResolver);
    }

    public boolean isMixed()
    {
        return m_contained.isMixed();
    }

    public SmSequenceType getSubstitutionGroup()
    {
        return m_contained.getSubstitutionGroup();
    }

    public SmSequenceType getCommonShortestRemainder(final SmSequenceType compareTo)
    {
        if (compareTo.getTypeCode() != SmSequenceType.TYPE_CODE_PAREN)
        {
            return m_contained.getCommonShortestRemainder(compareTo);
        }
        return m_contained.getCommonShortestRemainder(compareTo.getFirstChildComponent());
    }

    public SmSequenceType assertAttributeAxis(final SmSequenceType type)
    {
        return new ParenSmSequenceType(getContext(), m_contained.assertAttributeAxis(type));
    }

    public SmSequenceType assertChildAxis(final SmSequenceType type)
    {
        return new ParenSmSequenceType(getContext(), m_contained.assertChildAxis(type));
    }

    public SmSequenceType assertTypedValue(final SmSequenceType type)
    {
        return new ParenSmSequenceType(getContext(), m_contained.assertTypedValue(type));
    }

    public SmSequenceType assertNameTest(final ExpandedName name, final SmSequenceType type)
    {
        return new ParenSmSequenceType(getContext(), m_contained.assertNameTest(name, type));
    }

    public SmSequenceType assertType(final SmType type)
    {
        return new ParenSmSequenceType(getContext(), m_contained.assertType(type));
    }
}
