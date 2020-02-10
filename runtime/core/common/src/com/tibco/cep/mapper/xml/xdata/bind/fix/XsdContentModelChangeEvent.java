package com.tibco.cep.mapper.xml.xdata.bind.fix;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmComponent;

/**
 * A change event that notifies how an Xsd content model has changed.
 */
public final class XsdContentModelChangeEvent
{
    private int m_type;
    private SmComponent m_originalComponent;
    private XsdContentModelPath m_parentPath;
    private int [] m_childIndexes;
    private int m_offset;
    private ExpandedName m_oldName;
    private ExpandedName m_newName;
    private ExpandedName[] m_newNamePath;

    public static final int NODES_MOVED = 1;
    public static final int NODE_RENAMED = 2;

    /**
     * Constructor for {@link #NODES_MOVED}.
     * @param startingTypeOrEl The starting {@link com.tibco.xml.schema.SmType} or {@link com.tibco.xml.schema.SmElement}
     * @param path
     * @param childIndexes
     * @param moveOffset
     */
    public XsdContentModelChangeEvent(SmComponent startingTypeOrEl, XsdContentModelPath path, int[] childIndexes, int moveOffset)
    {
        if (startingTypeOrEl==null)
        {
            throw new NullPointerException();
        }
        m_originalComponent = startingTypeOrEl;
        m_parentPath = path;
        m_type = NODES_MOVED;
        m_childIndexes = childIndexes;
        m_offset = moveOffset;
    }

    /**
     * Constructor for {@link #NODE_RENAMED}.
     * @param path
     * @param oldName The old name.
     * @param newName The new name.
     */
    public XsdContentModelChangeEvent(SmComponent component, XsdContentModelPath path, ExpandedName oldName, ExpandedName newName)
    {
        m_originalComponent = component;
        if (component==null)
        {
            throw new NullPointerException();
        }
        m_parentPath = path;
        m_type = NODE_RENAMED;
        m_oldName = oldName;
        m_newName = newName;
    }

    /**
     * Another constructor for {@link #NODE_RENAMED}.
     * @param path
     * @param oldName The old name.
     * @param newNamePath The new name.
     */
    public XsdContentModelChangeEvent(SmComponent component, XsdContentModelPath path, ExpandedName oldName, ExpandedName[] newNamePath)
    {
        m_originalComponent = component;
        if (component==null)
        {
            throw new NullPointerException();
        }
        m_parentPath = path;
        m_type = NODE_RENAMED;
        m_oldName = oldName;
        m_newNamePath = newNamePath;
    }

    public XsdContentModelPath getPath()
    {
        return m_parentPath;
    }

    public int getType()
    {
        return m_type;
    }

    public int[] getChildIndexes()
    {
        return m_childIndexes;
    }

    public int getMoveOffset()
    {
        return m_offset;
    }

    public ExpandedName getOldName()
    {
        return m_oldName;
    }

    public ExpandedName getNewName()
    {
        return m_newName;
    }

    public ExpandedName[] getNewNamePath()
    {
        return m_newNamePath;
    }

    public SmComponent getOriginalComponent()
    {
        return m_originalComponent;
    }

    public String toString()
    {
        return "Content Model Change #" + m_type;
    }
}
