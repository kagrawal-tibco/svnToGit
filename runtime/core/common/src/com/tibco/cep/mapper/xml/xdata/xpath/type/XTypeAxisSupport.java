package com.tibco.cep.mapper.xml.xdata.xpath.type;

import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;


/**
 * Helper methods for {@link SmSequenceType} that give all xpath axes not directly supported in the api.
 */
public final class XTypeAxisSupport
{
    public static SmSequenceType getDescendantOrSelfAxis(SmSequenceType type)
    {
        SmSequenceType t = type.descendantAxis();
        if (SmSequenceTypeSupport.isPreviousError(t))
        {
            return t;
        }
        if (SmSequenceTypeSupport.isVoid(t))
        {
            // quicky optimization:
            return type;
        }
        return SmSequenceTypeFactory.createSequence(type.selfAxis(),t);
    }

    private static final SmSequenceType ANCESTOR_ROOT = SmSequenceTypeFactory.createSequence(SMDT.DOCUMENT_OR_ELEMENT_NODE,SMDT.REPEATING_ANY_ELEMENT);

    public static SmSequenceType getAncestorAxis(SmSequenceType type)
    {
        if (SmSequenceTypeSupport.isNone(type))
        {
            return type;
        }
        SmSequenceType t = type.parentAxis();
        if (SmSequenceTypeSupport.isPreviousError(t))
        {
            return t;
        }
        if (SmSequenceTypeSupport.isVoid(t))
        {
            // quicky optimization:
            return t;
        }
        if (t==SMDT.DOCUMENT_OR_ELEMENT_NODE)
        {
            // The above is the 'give up' answer for parent axis, so give up here:
            return ANCESTOR_ROOT;
        }
        SmSequenceType p = getAncestorAxis(t);
        if (SmSequenceTypeSupport.isVoid(p))
        {
            return t;
        }
        return SmSequenceTypeFactory.createSequence(t,p); // ancestor is a reverse axis so closer nodes come sooner.
    }

    public static SmSequenceType getAncestorOrSelfAxis(SmSequenceType type)
    {
        SmSequenceType t = getAncestorAxis(type);
        SmSequenceType t2 = type.selfAxis();
        if (SmSequenceTypeSupport.isPreviousError(t) || SmSequenceTypeSupport.isPreviousError(t2))
        {
            return SMDT.PREVIOUS_ERROR;
        }
        return SmSequenceTypeFactory.createSequence(t2,t); // ancestor-or-self is a reverse axis so closer nodes come sooner.
    }

    public static SmSequenceType getFollowingAxis(SmSequenceType type)
    {
        if (SmSequenceTypeSupport.isVoid(type))
        {
            return type;
        }
        if (SmSequenceTypeSupport.isPreviousError(type))
        {
            return type;
        }
        if (SmSequenceTypeSupport.isNone(type))
        {
            return type;
        }
        SmSequenceType t = type.followingSiblingAxis();
        if (t==SMDT.REPEATING_CHILD_AXIS_NODE)
        {
            return t; // give up.
        }
        SmSequenceType pa = type.parentAxis();
        SmSequenceType parentFollowing;
        if (pa==SMDT.DOCUMENT_OR_ELEMENT_NODE)
        {
            // The above is the 'give up' answer for parent axis, so give up here:
            parentFollowing = SMDT.REPEATING_CHILD_AXIS_NODE;
        }
        else
        {
            parentFollowing = getFollowingAxis(pa);
        }
        if (SmSequenceTypeSupport.isPreviousError(parentFollowing))
        {
            return SMDT.PREVIOUS_ERROR;
        }
        if (SmSequenceTypeSupport.isVoid(parentFollowing))
        {
            // quicky optimization:
            return t;
        }
        if (SmSequenceTypeSupport.isPreviousError(t))
        {
            return SMDT.PREVIOUS_ERROR;
        }
        if (SmSequenceTypeSupport.isVoid(t))
        {
            // quicky optimization:
            return parentFollowing;
        }
        return SmSequenceTypeFactory.createSequence(t,parentFollowing);
    }

    public static SmSequenceType getPrecedingAxis(SmSequenceType type)
    {
        if (SmSequenceTypeSupport.isVoid(type))
        {
            return type;
        }
        if (SmSequenceTypeSupport.isPreviousError(type))
        {
            return type;
        }
        if (SmSequenceTypeSupport.isNone(type))
        {
            return type;
        }        
        SmSequenceType t = type.precedingSiblingAxis();
        if (t==SMDT.REPEATING_CHILD_AXIS_NODE)
        {
            return t; // give up.
        }
        SmSequenceType pa = type.parentAxis();
        SmSequenceType parentPreceding;
        if (pa==SMDT.DOCUMENT_OR_ELEMENT_NODE)
        {
            // The above is the 'give up' answer for parent axis, so give up here:
            parentPreceding = SMDT.REPEATING_CHILD_AXIS_NODE;
        }
        else
        {
            parentPreceding = getPrecedingAxis(pa);
        }
        if (SmSequenceTypeSupport.isVoid(parentPreceding))
        {
            // quicky optimization:
            return t;
        }
        if (SmSequenceTypeSupport.isVoid(t))
        {
            // quicky optimization:
            return parentPreceding;
        }
        return SmSequenceTypeFactory.createSequence(t,parentPreceding);
    }
}
