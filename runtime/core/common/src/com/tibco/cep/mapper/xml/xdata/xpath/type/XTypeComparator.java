package com.tibco.cep.mapper.xml.xdata.xpath.type;

import java.util.HashMap;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.xtype.SMDT;


/**
 * Deep comparison helper methods for {@link SmSequenceType}.
 */
public final class XTypeComparator
{
    private XTypeComparator()
    {
    }

    public static final int NOT_EQUALS = 0;
    public static final int LEFT_ASSIGNABLE_TO_RIGHT = 1;
    public static final int EQUALS = 2;

    public static class Result
    {
        private final int m_equality;
        private final String m_reason;

        public Result(int equality)
        {
            m_equality = equality;
            m_reason = "";
        }

        public Result(int equality, String reason)
        {
            m_equality = equality;
            m_reason = reason;
        }

        public int getEquality()
        {
            return m_equality;
        }

        public String getReasonString()
        {
            return m_reason;
        }
    }

    private static final Result RESULT_LEFT_ASSIGNABLE_TO_RIGHT = new Result(LEFT_ASSIGNABLE_TO_RIGHT);
    private static final Result RESULT_NOT_EQUALS = new Result(NOT_EQUALS);
    private static final Result RESULT_EQUALS = new Result(EQUALS);

    /**
     * Same as {@link #compareAssignability} except this only returns a boolean, true for equal false otherwise.
     */
    public static boolean compareEquivalence(SmSequenceType type1, SmSequenceType type2)
    {
        HashMap hitTerms = new HashMap();
        Result r = compareEquivalence(type1,type2,hitTerms, new Properties());
        return r.getEquality()==EQUALS;
    }

    public static class Properties
    {
        private boolean m_lenientCardinality = false;
        private boolean m_lenientPrimitives = false;
        private boolean m_exactPrimitiveComparison = false;

        public Properties()
        {
        }

        /**
         * Sets lenient cardinality checking.  If set, essentially ignores any cardinality mismatches.<br>
         * Default is <code>false</code>
         */
        public void setLenientCardinality(boolean lenient)
        {
            m_lenientCardinality = lenient;
        }

        public boolean getLenientCardinality()
        {
            return m_lenientCardinality;
        }

        /**
         * Sets lenient primitive type checking.  If set, essentially ignores any primitive type (i.e. string<->double) mismatches.<br>
         * Default is <code>false</code>
         */
        public void setLenientPrimitives(boolean lenient)
        {
            m_lenientPrimitives = lenient;
        }

        public boolean getLenientPrimitives()
        {
            return m_lenientPrimitives;
        }

        /**
         * Sets exact primitive type checking.  If set, it requires exact name equality on primitive types, not just
         * base class comparisons (i.e. a subtype of String isn't equal to a String).
         * If set to true, {@link #setLenientPrimitives} applies only in cases of anonymous types.
         * Default is <code>false</code>
         */
        public void setExactPrimitives(boolean lenient)
        {
            m_exactPrimitiveComparison = lenient;
        }

        public boolean getExactPrimitives()
        {
            return m_exactPrimitiveComparison;
        }
    }

    /**
     * Does a recursive deep equals for two types <b>ignoring</b> any physical {@link com.tibco.xml.schema.SmParticleTerm} pointers.<br>
     * It does report things like A | B versus B | A as different (WCETODO --- another method/arg that normalizes that)<br>
     * Does not check context.
     */
    public static int compareAssignability(SmSequenceType type1, SmSequenceType type2)
    {
        HashMap hitTerms = new HashMap();
        Properties props = new Properties();
        Result r = compareEquivalence(type1,type2,hitTerms, props);
        return r.getEquality();
    }

    /**
     * Does a recursive deep equals for two types <b>ignoring</b> any physical {@link com.tibco.xml.schema.SmParticleTerm} pointers.<br>
     * It does report things like A | B versus B | A as different (WCETODO --- another method/arg that normalizes that)<br>
     * Does not check context.
     */
    public static int compareAssignability(SmSequenceType type1, SmSequenceType type2, Properties properties)
    {
        HashMap hitTerms = new HashMap();
        if (properties==null)
        {
            throw new NullPointerException();
        }
        Result r = compareEquivalence(type1,type2,hitTerms,properties);
        return r.getEquality();
    }

    public static Result compareEquivalenceResult(SmSequenceType type1, SmSequenceType type2)
    {
        HashMap hitTerms = new HashMap();
        Result r = compareEquivalence(type1,type2,hitTerms, new Properties());
        return r;
    }

    public static Result compareEquivalenceResult(SmSequenceType type1, SmSequenceType type2, Properties properties)
    {
        HashMap hitTerms = new HashMap();
        if (properties==null)
        {
            throw new NullPointerException();
        }
        Result r = compareEquivalence(type1,type2,hitTerms, properties);
        return r;
    }

    private static Result compareEquivalence(SmSequenceType type1, SmSequenceType type2, HashMap hitTerms, Properties props)
    {
        int tc1 = type1.getTypeCode();
        if (tc1==SmSequenceType.TYPE_CODE_PREVIOUS_ERROR)
        {
            // don't propagate errors.
            return RESULT_LEFT_ASSIGNABLE_TO_RIGHT;
        }
        int tc2 = type2.getTypeCode();

        // Check for RHS of any:
        if (tc2==SmSequenceType.TYPE_CODE_SEQUENCE)
        {
            if (type2.equalsType(SMDT.ANY_TYPE_CHILD_CONTENT))
            {
                if (type1.equalsType(SMDT.ANY_TYPE_CHILD_CONTENT))
                {
                    return RESULT_EQUALS;
                }
                return RESULT_LEFT_ASSIGNABLE_TO_RIGHT;
            }
        }

        // General purpose comparison:
        if (tc1!=tc2)
        {
            if (tc2==SmSequenceType.TYPE_CODE_REPEATS)
            {
                SmCardinality oc = type2.getOccurrence();
                if (oc.getMinOccurs()==0 && tc1==SmSequenceType.TYPE_CODE_VOID)
                {
                    // Can assign (empty)-> optional
                    return RESULT_LEFT_ASSIGNABLE_TO_RIGHT;
                }
                if (oc.getMinOccurs()<=1)
                {
                    // Since the LHS has a minOccurs,maxOccurs of 1, so long as minOccurs<=1, check the content:
                    SmSequenceType type2First = type2.getFirstChildComponent();
                    Result v = compareEquivalence(type1,type2First,hitTerms,props);
                    if (v.getEquality()==EQUALS || v.getEquality()==LEFT_ASSIGNABLE_TO_RIGHT)
                    {
                        return RESULT_LEFT_ASSIGNABLE_TO_RIGHT;
                    }
                    // Not equals, preserve reason, though:
                    return v;
                }
                if (props.getLenientCardinality())
                {
                    Result v = compareEquivalence(type1,type2.getFirstChildComponent(),hitTerms,props);
                    if (v.getEquality()!=NOT_EQUALS)
                    {
                        return RESULT_LEFT_ASSIGNABLE_TO_RIGHT;
                    }
                    // Otherwise, fall through, keep trying...
                }
            }
            if (tc1==SmSequenceType.TYPE_CODE_REPEATS)
            {
                if (props.getLenientCardinality())
                {
                    Result v = compareEquivalence(type1.getFirstChildComponent(),type2,hitTerms,props);
                    if (v.getEquality()!=NOT_EQUALS)
                    {
                        return RESULT_LEFT_ASSIGNABLE_TO_RIGHT;
                    }
                }
                // Otherwise, fall through, keep trying...
            }
            if (tc2==SmSequenceType.TYPE_CODE_SEQUENCE)
            {
                return compareLeftToSequence(type1,type2,hitTerms, props);
            }
            if (tc2==SmSequenceType.TYPE_CODE_CHOICE)
            {
                return compareLeftToChoice(type1,type2,hitTerms, props);
            }
            String msg = ResourceBundleManager.getMessage(MessageCode.COPY_OF_MISMATCH,type1,type2);
            return new Result(NOT_EQUALS,msg);
        }
        if (tc1==SmSequenceType.TYPE_CODE_ATTRIBUTE)
        {
            return compareEquivalenceAttributes(type1, type2,hitTerms, props);
        }
        if (tc1==SmSequenceType.TYPE_CODE_ELEMENT)
        {
            return compareEquivalenceElements(type1, type2, hitTerms, props);
        }
        if (tc1==SmSequenceType.TYPE_CODE_VOID || tc1==SmSequenceType.TYPE_CODE_NONE || tc1==SmSequenceType.TYPE_CODE_PREVIOUS_ERROR)
        {
            return RESULT_EQUALS;
        }
        if (tc1==SmSequenceType.TYPE_CODE_CHOICE || tc1==SmSequenceType.TYPE_CODE_SEQUENCE || tc1==SmSequenceType.TYPE_CODE_INTERLEAVE)
        {
            SmSequenceType t1first = type1.getFirstChildComponent();
            SmSequenceType t2first = type2.getFirstChildComponent();
            Result val = compareEquivalence(t1first, t2first,hitTerms,props);
            if (val.getEquality()==NOT_EQUALS)
            {
                // shortcut & preserve reason
                return val;
            }
            SmSequenceType t1second = type1.getSecondChildComponent();
            SmSequenceType t2second = type2.getSecondChildComponent();
            Result val2 = compareEquivalence(t1second,t2second,hitTerms,props);
            if (val2.getEquality()==NOT_EQUALS)
            {
                return val2; // preserve reason.
            }
            return leastOf(val,val2);
        }
        if (tc1==SmSequenceType.TYPE_CODE_PAREN)
        {
            return compareEquivalence(type1.getFirstChildComponent(),type2.getFirstChildComponent(),hitTerms,props);
        }
        if (tc1==SmSequenceType.TYPE_CODE_REPEATS)
        {
            SmCardinality oc1 = type1.getOccurrence();
            SmCardinality oc2 = type2.getOccurrence();
            SmSequenceType t1Content = type1.getFirstChildComponent();
            SmSequenceType t2Content = type2.getFirstChildComponent();
            Result contentVal = compareEquivalence(t1Content,t2Content,hitTerms,props);
            if (contentVal.getEquality()==NOT_EQUALS)
            {
                return contentVal;
            }
            Result val;
            if (!oc1.equals(oc2))
            {
                if (oc1.getMinOccurs()>=oc2.getMinOccurs() && oc1.getMaxOccurs()<= oc2.getMaxOccurs())
                {
                    val = RESULT_LEFT_ASSIGNABLE_TO_RIGHT;
                }
                else
                {
                    // Short cut; don't need to check content, they aren't equal.
                    return RESULT_NOT_EQUALS;
                }
            }
            else
            {
                val = RESULT_EQUALS;
            }
            return leastOf(contentVal,val);
        }
        if (tc1==SmSequenceType.TYPE_CODE_COMMENT || tc1==SmSequenceType.TYPE_CODE_PROCESSING_INSTRUCTION || tc1==SmSequenceType.TYPE_CODE_TEXT)
        {
            return RESULT_EQUALS;
        }
        if (tc1==SmSequenceType.TYPE_CODE_SIMPLE)
        {
            return compareSimpleEquivalence(type1,type2,props);
        }
        // Shouldn't get here:
        System.err.println("Unknown comparison tc:" + tc1);
        return RESULT_NOT_EQUALS;
    }

    private static Result leastOf(Result r1, Result r2)
    {
        if (r1.getEquality()==NOT_EQUALS)
        {
            return r1;
        }
        if (r2.getEquality()==NOT_EQUALS)
        {
            return r2;
        }
        if (r1.getEquality()==EQUALS)
        {
            return r2;
        }
        if (r2.getEquality()==EQUALS)
        {
            return r1;
        }
        // they must both be left-assignable-to-right.
        return r1;
    }

    private static Result compareLeftToSequence(SmSequenceType type1, SmSequenceType type2Sequence, HashMap hitTerms, Properties props)
    {
        Result r = compareEquivalence(SMDT.VOID,type2Sequence.getFirstChildComponent(),hitTerms,props);
        if (r.getEquality()==LEFT_ASSIGNABLE_TO_RIGHT)
        {
            Result rc = compareEquivalence(type1,type2Sequence.getSecondChildComponent(),hitTerms,props);
            if (rc.getEquality()==LEFT_ASSIGNABLE_TO_RIGHT || rc.getEquality()==EQUALS)
            {
                return RESULT_LEFT_ASSIGNABLE_TO_RIGHT;
            }
        }
        // ok, try the reverse:
        if (compareEquivalence(SMDT.VOID,type2Sequence.getSecondChildComponent(),hitTerms,props).getEquality()==LEFT_ASSIGNABLE_TO_RIGHT)
        {
            Result rc = compareEquivalence(type1,type2Sequence.getFirstChildComponent(),hitTerms,props);
            if (rc.getEquality()==LEFT_ASSIGNABLE_TO_RIGHT || rc.getEquality()==EQUALS)
            {
                return RESULT_LEFT_ASSIGNABLE_TO_RIGHT;
            }
        }
        return RESULT_NOT_EQUALS;
    }

    private static Result compareLeftToChoice(SmSequenceType type1, SmSequenceType typeChoice, HashMap hitTerms, Properties props)
    {
        int rc = compareEquivalence(type1,typeChoice.getSecondChildComponent(),hitTerms,props).getEquality();
        if (rc==LEFT_ASSIGNABLE_TO_RIGHT || rc==EQUALS)
        {
            return RESULT_LEFT_ASSIGNABLE_TO_RIGHT;
        }
        int rc2 = compareEquivalence(type1,typeChoice.getFirstChildComponent(),hitTerms,props).getEquality();
        if (rc2==LEFT_ASSIGNABLE_TO_RIGHT || rc2==EQUALS)
        {
            return RESULT_LEFT_ASSIGNABLE_TO_RIGHT;
        }
        return RESULT_NOT_EQUALS;
    }

    private static Result compareSimpleEquivalence(SmSequenceType t1, SmSequenceType t2, Properties props)
    {
        SmType st1 = t1.getSimpleType();
        SmType st2 = t2.getSimpleType();
        if (st1==st2) // quick optimization:
        {
            return RESULT_EQUALS;
        }
        // SmType is a tough beast to figure out.... sometimes can have different pointers, value type is a good fallback
        // comparison:
        if (props.getExactPrimitives() && st1.getExpandedName()!=null)
        {
            if (st1.getExpandedName().equals(st2.getExpandedName()))
            {
                return RESULT_EQUALS;
            }
        }
        else
        {
            String vt1 = st1.getValueType();
            String vt2 = st2.getValueType();
            if (vt1!=null && vt1.equals(vt2))
            {
                return RESULT_EQUALS;
            }
            if (props.getLenientPrimitives())
            {
                // When set, be generous here --- while double->string is always equal, string->double isn't, however that's getting
                // a bit picky --- realistically, in many scenarios, it is probably ok; maybe should be a flag.
                return RESULT_LEFT_ASSIGNABLE_TO_RIGHT;
            }
            if (vt2!=null && vt2.equals(SmType.VALUE_TYPE_STRING))
            {
                // anything is assignable to a string.
                return RESULT_LEFT_ASSIGNABLE_TO_RIGHT;
            }
        }
        return RESULT_NOT_EQUALS; // Add reason here; maybe needs context.
    }

    /**
     * A simple pair of 2 particle terms where hashcode/equals compare the identity of both terms.
     */
    private static class ComparisonPair
    {
        private final SmParticleTerm m_p1;
        private final SmParticleTerm m_p2;

        public ComparisonPair(SmParticleTerm p1, SmParticleTerm p2)
        {
            m_p1 = p1;
            m_p2 = p2;
        }

        public int hashCode()
        {
            return m_p1.hashCode()+2*m_p2.hashCode();
        }

        public boolean equals(Object obj)
        {
            if (!(obj instanceof ComparisonPair))
            {
                return false;
            }
            ComparisonPair p2 = (ComparisonPair) obj;
            return m_p1==p2.m_p1 && m_p2==p2.m_p2;
        }
    }

    private static Result compareEquivalenceElements(SmSequenceType type1, SmSequenceType type2, HashMap hitTerms, Properties props)
    {
        SmParticleTerm t1 = type1.getParticleTerm();
        SmParticleTerm t2 = type2.getParticleTerm();
        if (t1==t2 && t1!=null)
        {
            // shortcut optimization; they are identical. (Shortcut only!)
            return RESULT_EQUALS;
        }
        if (type2.getName()==null)
        {
            if (type1.getName()==null)
            {
                // Both wildcards.
                return XTypeComparator.RESULT_EQUALS;
            }
            // wildcard on RHS, is assignable.
            return XTypeComparator.RESULT_LEFT_ASSIGNABLE_TO_RIGHT;
        }
        if (!compareOptionalNames(type1.getName(),type2.getName()))
        {
            // Generate reasonable message explaining what the differences are:
            String msg;
            if (compareNamesDifferByNamespace(type1.getName(),type2.getName()))
            {
                msg = ResourceBundleManager.getMessage(MessageCode.COPY_OF_NAMESPACE_MISMATCH,type1.getName().getLocalName(),type1.getName().getNamespaceURI(),type2.getName().getNamespaceURI());
            }
            else
            {
                if (type1.getName()!=null && type2.getName()!=null)
                {
                    msg = ResourceBundleManager.getMessage(MessageCode.COPY_OF_NAME_MISMATCH,type1.getName().getLocalName(),type2.getName().getLocalName());
                }
                else
                {
                    msg = ResourceBundleManager.getMessage(MessageCode.COPY_OF_MISMATCH,type1,type2);
                }
            }
            return new Result(NOT_EQUALS,msg);
        }
        // For recursive schemas, need to be careful:
        ComparisonPair cp = null;
        if (type1.getParticleTerm()!=null && type2.getParticleTerm()!=null)
        {
            cp = new ComparisonPair(type1.getParticleTerm(),type2.getParticleTerm());
            Result v = (Result) hitTerms.get(cp);
            if (v!=null)
            {
                // Already tested this:
                return v;
            }
        }
        else
        {
            if (type1.getParticleTerm()==null && type2.getParticleTerm()==null)
            {
                // They are both unknown PT, go ahead & say that's ok.
                return RESULT_EQUALS;
            }
            // Particle term not known for one of them...
            return RESULT_LEFT_ASSIGNABLE_TO_RIGHT; // be lenient...
        }
        if (cp!=null)
        {
            hitTerms.put(cp,RESULT_EQUALS); // for induction, we must assume they are true before recursing below:
        }
        Result val = compareAttrAndChildInternal(type1,type2,hitTerms,props);
        hitTerms.put(cp,val); // put actual value here.
        return val;
    }

    private static Result compareEquivalenceAttributes(SmSequenceType type1, SmSequenceType type2, HashMap hitTerms, Properties props)
    {
        SmParticleTerm t1 = type1.getParticleTerm();
        SmParticleTerm t2 = type2.getParticleTerm();
        if (t1==t2 && t1!=null)
        {
            // shortcut optimization; they are identical. (Shortcut only!)
            return RESULT_EQUALS;
        }
        if (!compareOptionalNames(type1.getName(),type2.getName()))
        {
            return new Result(NOT_EQUALS,"ATTRNAME unknown");//WCETODO removeme.
            //return RESULT_NOT_EQUALS;
        }
        return compareEquivalence(type1.typedValue(true),type2.typedValue(true),hitTerms,props);
    }

    private static Result compareAttrAndChildInternal(SmSequenceType type1, SmSequenceType type2, HashMap hitTerms, Properties props)
    {
        Result r = compareEquivalence(type1.attributeAxis(),type2.attributeAxis(),hitTerms,props);
        if (r.getEquality()==NOT_EQUALS)
        {
            // shortcut optimization.
            return r;
        }
        Result r2 = compareEquivalence(type1.childAxis(),type2.childAxis(),hitTerms,props);
        if (r2.getEquality()==NOT_EQUALS)
        {
            // shortcut optimization.
            return r2;
        }
        Result val = leastOf(r2,r);
        Result r3 = compareEquivalence(type1.typedValue(true),type2.typedValue(true),hitTerms,props);
        if (r3.getEquality()==NOT_EQUALS)
        {
            // (To preserve reason)
            return r3;
        }
        val = leastOf(r3,val);
        return val;
    }

    private static boolean compareOptionalNames(ExpandedName e1, ExpandedName e2)
    {
        if (e1==e2) // does both null check.
        {
            return true;
        }
        if (e1!=null)
        {
            return e1.equals(e2);
        }
        else
        {
            // e1 == null, e2 non-null.
            return false;
        }
    }

    /**
     * Given that {@link #compareOptionalNames} is <code>false</code>, is the reason that the namespaces and only the
     * namespaces are different?
     * @param ename1
     * @param ename2
     * @return
     */
    private static boolean compareNamesDifferByNamespace(ExpandedName ename1, ExpandedName ename2)
    {
        if (ename1==null || ename2==null)
        {
            return false;
        }
        if (ename1.getLocalName().equals(ename2.getLocalName()))
        {
            return true;
        }
        return false;
    }
}
