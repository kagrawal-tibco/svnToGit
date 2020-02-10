package com.tibco.cep.mapper.xml.xdata;

import java.util.ArrayList;
import java.util.IdentityHashMap;

import com.tibco.xml.data.primitive.NamespaceResolver;
import com.tibco.xml.data.primitive.helpers.DefaultNamespaceMapper;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmSequenceTypeContext;
import com.tibco.xml.schema.SmSequenceTypeRemainder;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/*
 * Copied from the SmType API, along with ParenSmSequenceType, so that ParenSmSequenceType can hold a user message for coercions
 */
public abstract class AbstractSmSequenceType implements SmSequenceType
{
    protected final SmSequenceTypeContext m_context;

    protected final static SmSequenceTypeContext EMPTY_CONTEXT = new SmSequenceTypeContext(null, null);
    protected final static SmSequenceType[] EMPTY_ARRAY = new SmSequenceType[0];

    protected AbstractSmSequenceType(final SmSequenceTypeContext context)
    {
        m_context = context == null ? EMPTY_CONTEXT : context;
    }

    public final String[] getNodePath(final NamespaceResolver namespaceToPrefixResolver)
    {
        final SmSequenceTypeContext ctx = getContext();
        final String ns;
        if (ctx.getSource() != null)
        {
            ns = ctx.getSource().getXPath(namespaceToPrefixResolver);
        }
        else
        {
            ns = getNodeStepElement(namespaceToPrefixResolver);
        }
        if (ctx.getParent() != null)
        {
            final String[] str = ctx.getParent().getNodePath(namespaceToPrefixResolver);
            if (ns == null)
            {
                return str;
            }
            final String[] nstr = new String[str.length + 1];
            for (int i = 0; i < str.length; i++)
            {
                nstr[i] = str[i];
            }
            nstr[str.length] = ns;
            return nstr;
        }
        else
        {
            if (ns == null)
            {
                return new String[0];
            }
            else
            {
                return new String[]{ns};
            }
        }
    }

    /**
     * Implemented here because it behaves mostly like a static method, however, it relies
     * on pointer equality to be efficient, so it was made a member.
     */
    public final SmSequenceType leastCommonRemainder(SmSequenceType to)
    {
        SmSequenceType from = this;
        if (from == to)
        {
            // quicky optimization.
            return to;
        }
        // Interleaves require a different algorithm because they are unordered:
        if (SmSequenceTypeSupport.stripParens(from).getTypeCode() == SmSequenceType.TYPE_CODE_INTERLEAVE)
        {
            return leastCommonRemainderInterleave(SmSequenceTypeSupport.stripParens(from), to);
        }
        if (SmSequenceTypeSupport.stripParens(to).getTypeCode() == SmSequenceType.TYPE_CODE_INTERLEAVE)
        {
            return leastCommonRemainderInterleave(SmSequenceTypeSupport.stripParens(to), from);
        }

        // The algorithm here is to effectively find the first identical skipped term.
        // Because we expect these to be often fairly long term sequences but usually being very close together,
        // we do a simultaneous search where we step down both terms 1 term at a time simultaneously.  That way if
        // they are identical we only need 1 step, if they are off by 1 term, we only need 2 steps, etc., regardless of
        // how long the terms are.
        IdentityHashMap fromMap = new IdentityHashMap();
        IdentityHashMap toMap = new IdentityHashMap();
        for (; ;)
        {
            SmSequenceTypeRemainder fskip = from == null ? null : from.skip();
            if (fskip != null)
            {
                SmSequenceType fmatch = fskip.getMatched();
                SmSequenceType fmatchStripped = SmSequenceTypeSupport.stripOccursAndParens(fmatch);
                if (toMap.containsKey(fmatchStripped))
                {
                    SmSequenceType newto = (SmSequenceType)toMap.get(fmatchStripped);
                    SmSequenceType newToMatch = newto.skip().getMatched();
                    return getLongerRepetition(fmatch, from, newToMatch, newto);
                }
                fromMap.put(fmatchStripped, from);
                from = fskip.getRemainder();
            }
            else
            {
                from = null;
            }

            SmSequenceTypeRemainder toskip = to == null ? null : to.skip();
            if (toskip != null)
            {
                SmSequenceType tomatch = toskip.getMatched();
                SmSequenceType tomatchStripped = SmSequenceTypeSupport.stripOccursAndParens(tomatch);
                if (fromMap.containsKey(tomatchStripped))
                {
                    SmSequenceType newfrom = (SmSequenceType)fromMap.get(tomatchStripped);
                    SmSequenceType newFromMatch = newfrom.skip().getMatched();
                    return getLongerRepetition(tomatch, to, newFromMatch, newfrom);
                }
                toMap.put(tomatchStripped, to);
                to = toskip.getRemainder();
            }
            else
            {
                to = null;
            }
            if (from == null && to == null)
            {
                break;
            }
        }
        // no common.
        return SMDT.VOID;
    }

    private static SmSequenceType leastCommonRemainderInterleave(SmSequenceType interleave, SmSequenceType matchTo)
    {
        IdentityHashMap termsInInterleave = new IdentityHashMap();
        SmSequenceType[] t = SmSequenceTypeSupport.getAllInterleaves(interleave);
        for (int i = 0; i < t.length; i++)
        {
            SmSequenceType tt = SmSequenceTypeSupport.stripOccursAndParens(t[i]);
            termsInInterleave.put(tt, t[i]);
        }

        ArrayList result = new ArrayList();
        SmSequenceType mtp = SmSequenceTypeSupport.stripParens(matchTo);
        SmSequenceType[] t2 = SmSequenceTypeSupport.getAllInterleaves(mtp);
        for (int i = 0; i < t2.length; i++)
        {
            SmSequenceType tt = SmSequenceTypeSupport.stripOccursAndParens(t2[i]);
            if (termsInInterleave.containsKey(tt))
            {
                SmSequenceType m = (SmSequenceType)termsInInterleave.get(tt);
                SmSequenceType lr = getLongerRepetition(t2[i], t2[i], m, m);
                result.add(lr);
            }
        }
        return SmSequenceTypeFactory.createInterleave((SmSequenceType[])result.toArray(new SmSequenceType[result.size()]));
    }

    private static SmSequenceType getLongerRepetition(SmSequenceType fromMatch, SmSequenceType from, SmSequenceType toMatch, SmSequenceType to)
    {
        // Two identical terms were found, though they might have different repetitions.  Find the longer
        // match & return the corresponding term.
        fromMatch = SmSequenceTypeSupport.stripParens(fromMatch);
        toMatch = SmSequenceTypeSupport.stripParens(toMatch);
        if (fromMatch.getIsAccountedFor())
        {
            // This is accounted for, so it is 'longer'.
            return from;
        }
        if (toMatch.getIsAccountedFor())
        {
            return to;
        }
        return to; // toss up.
    }

    /**
     * Assumes that both term and skipTo are 'cut from the same cloth', and that skipTo is from
     * {@link SmSequenceType#leastCommonRemainder}.
     *
     * @param skipTo The 'target' position to which
     */
    public SmSequenceType[] getSkippedTermsTo(SmSequenceType skipTo)
    {
        SmSequenceType term = this;
        if (this == skipTo)
        {
            return EMPTY_ARRAY; // quicky optimization.
        }

        // Interleaves require a different algorithm because they are unordered:
        if (SmSequenceTypeSupport.stripParens(term).getTypeCode() == SmSequenceType.TYPE_CODE_INTERLEAVE)
        {
            return getSkippedTermsToInterleave(SmSequenceTypeSupport.stripParens(term), SmSequenceTypeSupport.stripParens(skipTo));
        }

        skipTo = stripAccountedForTerm(skipTo);
        // Find the 'marker' term to look for as termination.  Strip off occurs and parens
        // because getMatchedTerms can alter the occurs, but not the content.
        // (Failure to strip off occurs would result is missed matches sometimes, i.e. for alpha[2,*] match alpha)

        SmSequenceTypeRemainder fmt = skipTo.skip();
        SmSequenceType terminatingTerm;
        if (fmt == null)
        {
            terminatingTerm = SMDT.VOID;
        }
        else
        {
            terminatingTerm = SmSequenceTypeSupport.stripOccursAndParens(fmt.getMatched());
        }

        ArrayList skippedTerms = null;
        for (; ;)
        {
            SmSequenceTypeRemainder skip = term.skip();
            if (skip == null)
            {
                break;
            }
            // For obscure cases where minOccurs>1, can get situation where there are different versions of the term
            // differing by repetition, so ignore those:
            SmSequenceType mr = SmSequenceTypeSupport.stripOccursAndParens(skip.getMatched());
            if (mr == terminatingTerm)
            {
                break;
            }
            if (skippedTerms == null)
            {
                skippedTerms = new ArrayList();
            }
            skippedTerms.add(skip.getMatched());
            term = skip.getRemainder();
        }
        if (skippedTerms == null)
        {
            return EMPTY_ARRAY;
        }
        return (SmSequenceType[])skippedTerms.toArray(new SmSequenceType[skippedTerms.size()]);
    }

    private static SmSequenceType stripAccountedForTerm(SmSequenceType t)
    {
        SmSequenceTypeRemainder r = t.skip();
        if (r != null && r.getMatched().getIsAccountedFor())
        {
            // had an accounted for term, skip it:
            return r.getRemainder();
        }
        // no change.
        return t;
    }

    private static SmSequenceType[] getSkippedTermsToInterleave(SmSequenceType interleaveTerm, SmSequenceType skipTo)
    {
        IdentityHashMap requiredRemainder = new IdentityHashMap();
        SmSequenceType[] t = SmSequenceTypeSupport.getAllInterleaves(skipTo);
        for (int i = 0; i < t.length; i++)
        {
            SmSequenceType s = SmSequenceTypeSupport.stripParens(t[i]);
            if (!s.getIsAccountedFor())
            {
                // If accounted for, don't consider it
                SmSequenceType s2 = SmSequenceTypeSupport.stripOccursAndParens(s);
                requiredRemainder.put(s2, s2);
            }
        }
        SmSequenceType[] o = SmSequenceTypeSupport.getAllInterleaves(interleaveTerm);
        ArrayList skippedTerms = null;
        for (int i = 0; i < o.length; i++)
        {
            SmSequenceType ot = SmSequenceTypeSupport.stripOccursAndParens(o[i]);
            if (!requiredRemainder.containsKey(ot))
            {
                if (skippedTerms == null)
                {
                    skippedTerms = new ArrayList();
                }
                skippedTerms.add(o[i]);
            }
        }
        if (skippedTerms == null)
        {
            return EMPTY_ARRAY;
        }
        return (SmSequenceType[])skippedTerms.toArray(new SmSequenceType[skippedTerms.size()]);
    }

    public final boolean equals(Object obj)
    {
        if (!(obj instanceof SmSequenceType))
        {
            return false;
        }
        return equalsType((SmSequenceType)obj);
    }

    /**
     * Re-emphasize that these need to be done.
     */
    public abstract int hashCode();

    public final SmSequenceTypeContext getContext()
    {
        return m_context;
    }

    public boolean isMixed()
    {
        return false; // default.
    }

    /**
     * Default implementation, can override.
     */
    public String getUserMessage()
    {
        return null;
    }

    public boolean getIsAccountedFor()
    {
        return false;
    }

    public SmSequenceType encodedValue()
    {
        return this; // default.
    }

    public final String toString()
    {
        return formatAsSequenceType(new DefaultNamespaceMapper(true));
    }
}
