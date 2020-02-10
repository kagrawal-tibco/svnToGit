/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.type;

import java.util.ArrayList;
import java.util.HashMap;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

/**
 * Some XType simplification functions.
 */
public class XTypeSimplifier
{
    /**
     * Removes all duplicate choices, not checking about different contexts (parent context will be lost)
     * @param type The type to check.
     * @return
     */
    public static SmSequenceType removeChoiceDuplicates(SmSequenceType type)
    {
        int tc = type.getTypeCode();
        if (tc==SmSequenceType.TYPE_CODE_CHOICE)
        {
            HashMap seen = new HashMap();
            ArrayList items = new ArrayList();
            removeDuplicatesInternal(type,SmCardinality.EXACTLY_ONE,items,seen);
            SmSequenceType[] c = (SmSequenceType[]) items.toArray(new SmSequenceType[items.size()]);
            return SmSequenceTypeFactory.createChoice(c);
        }
        if (tc==SmSequenceType.TYPE_CODE_REPEATS)
        {
            SmSequenceType t = removeChoiceDuplicates(type.getFirstChildComponent());
            return SmSequenceTypeFactory.createRepeats(type.getContext(),t,type.getOccurrence()).simplify(false);
        }
        return type;
    }

    /**
     * Removes all untyped choices (elements/attributes w/o a {@link SmParticleTerm} set on them}.
     * @param type The type to check.
     * @return
     */
    public static SmSequenceType removeUntypedChoiceElements(SmSequenceType type)
    {
        int tc = type.getTypeCode();
        if (tc==SmSequenceType.TYPE_CODE_CHOICE)
        {
            SmSequenceType t1 = removeUntypedChoiceElements(type.getFirstChildComponent());
            SmSequenceType t2 = removeUntypedChoiceElements(type.getSecondChildComponent());
            return SmSequenceTypeFactory.createChoice(t1,t2).simplify(false);
        }
        if (tc==SmSequenceType.TYPE_CODE_ELEMENT || tc==SmSequenceType.TYPE_CODE_ATTRIBUTE)
        {
            if (type.getParticleTerm()==null)
            {
                return SMDT.VOID;
            }
            return type;
        }
        if (tc==SmSequenceType.TYPE_CODE_REPEATS)
        {
            SmSequenceType t = removeUntypedChoiceElements(type.getFirstChildComponent());
            SmSequenceType tt = SmSequenceTypeFactory.createRepeats(type.getContext(),t,type.getOccurrence());
            return tt.simplify(false);
        }
        return type;
    }

    private static void removeDuplicatesInternal(SmSequenceType choiceChild, SmCardinality oc, ArrayList addTo, HashMap seen)
    {
        int tc = choiceChild.getTypeCode();
        if (tc==SmSequenceType.TYPE_CODE_CHOICE)
        {
            removeDuplicatesInternal(choiceChild.getFirstChildComponent(),oc,addTo,seen);
            removeDuplicatesInternal(choiceChild.getSecondChildComponent(),oc,addTo,seen);
            return;
        }
        if (tc==SmSequenceType.TYPE_CODE_SEQUENCE || tc==SmSequenceType.TYPE_CODE_INTERLEAVE)
        {
            addTo.add(choiceChild);
            return;
        }
        if (tc==SmSequenceType.TYPE_CODE_ELEMENT || tc==SmSequenceType.TYPE_CODE_ATTRIBUTE)
        {
            SmParticleTerm t = choiceChild.getParticleTerm();
            if (t!=null)
            {
                addRDChoiceInternal(t,choiceChild,oc,addTo,seen);
                return;
            }
            ExpandedName ename = choiceChild.getName();
            if (ename!=null)
            {
                addRDChoiceInternal(ename,choiceChild,oc,addTo,seen);
                return;
            }
        }
        if (tc==SmSequenceType.TYPE_CODE_PAREN)
        {
            removeDuplicatesInternal(choiceChild.getFirstChildComponent(),oc,addTo,seen);
            return;
        }
        if (tc==SmSequenceType.TYPE_CODE_REPEATS)
        {
            SmCardinality no = oc.multiplyBy(choiceChild.getOccurrence());
            removeDuplicatesInternal(choiceChild.getFirstChildComponent(),no,addTo,seen);
            return;
        }
        // o.w. keep it:
        addTo.add(choiceChild);
    }

    private static void addRDChoiceInternal(Object key, SmSequenceType type, SmCardinality oc, ArrayList addTo, HashMap seen)
    {
        if (!seen.containsKey(key))
        {
            Integer pos = new Integer(addTo.size());
            seen.put(key,pos);
            if (oc.equals(SmCardinality.EXACTLY_ONE))
            {
                addTo.add(type);
            }
            else
            {
                // re-add cardinality:
                SmSequenceType nt = SmSequenceTypeFactory.createRepeats(null,type,oc);
                addTo.add(nt);
            }
        }
        else
        {
            int at = ((Integer)seen.get(key)).intValue();
            SmSequenceType xt = (SmSequenceType) addTo.get(at);
            // check to see if we need to adjust cardinality:
            SmCardinality qt = xt.quantifier();
            SmCardinality no = qt.choiceBy(oc);
            if (!no.equals(qt))
            {
                SmSequenceType newone = SmSequenceTypeFactory.createRepeats(xt,no).simplify(false);
                addTo.set(at,newone);
            }
        }
    }
}
