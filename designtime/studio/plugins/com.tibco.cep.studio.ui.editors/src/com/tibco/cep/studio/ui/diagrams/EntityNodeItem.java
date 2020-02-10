package com.tibco.cep.studio.ui.diagrams;

import com.tomsawyer.drawing.TSConnector;

/**
 * author: ggrigore
 *
 * This is a data storage class for each "item" in a concept node, where an
 * item can be an attribute
 */
public class EntityNodeItem
{
    public static final int ATTRIBUTE = 0;
    public static final int EXT_ATTRIBUTE = 1;
    
    public static final int BOOLEAN_ATTRIBUTE = 0;
    public static final int CONCEPTREF_ATTRIBUTE = 1;
    public static final int CONCEPTCON_ATTRIBUTE = 2;
    public static final int DATETIME_ATTRIBUTE = 3;
    public static final int DOUBLE_ATTRIBUTE = 4;
    public static final int INTEGER_ATTRIBUTE = 5;
    public static final int LONG_ATTRIBUTE = 6;
    public static final int STRING_ATTRIBUTE = 7;
    

    private TSConnector connector;
    private String label;
    private Object userObject;
    private int type;
    private int attrtype;
    private EntityNodeData owner;


    private EntityNodeItem(int type)
    {
        if (type == ATTRIBUTE)
        {
            this.type = ATTRIBUTE;
        }
        else
        {
            this.type = EXT_ATTRIBUTE;
        }
    }

    private EntityNodeItem(int type, Object userObject)
    {
        this(type);
        this.setUserObject(userObject);
    }

    public EntityNodeItem(int type, EntityNodeData owner)
    {
        this(type);
        this.owner = owner;
    }

    public EntityNodeItem(int type,
    					int attrtype,
                        String label,
                        Object userObject,
                        EntityNodeData owner)
    {
        this(type, userObject);
        this.attrtype = attrtype;
        this.setLabel(label);
        this.owner = owner;
    }

    public void setConnector(TSConnector connector)
    {
        this.connector = connector;
    }

    public TSConnector getConnector()
    {
        return this.connector;
    }

    public String getLabel()
    {
        return this.label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public Object getUserObject()
    {
        return this.userObject;
    }

    public void setUserObject(Object userObject)
    {
        this.userObject = userObject;
    }

    public int getType()
    {
        return this.type;
    }

    public int getAttributeType()
    {
        return this.attrtype;
    }
    
    public EntityNodeData getOwner()
    {
        return this.owner;
    }
}
