package com.tibco.cep.mapper.xml.xdata.bind.fix;

/**
 * A change event that notifies how an Xsd content model has changed.
 */
public class XsdContentModelPath
{
    private XsdContentModelPath m_parent;
    private int m_termIndex;
    private boolean m_isContentModel;

    public XsdContentModelPath(XsdContentModelPath parent, int termIndex)
    {
        m_parent = parent;
        m_termIndex = termIndex;
        m_isContentModel = false; // n/a
    }

    /**
     *
     * @param parent
     * @param isContentModel For a complex type, true if the path steps down the attribute axis (false) or the child axis (true).
     */
    public XsdContentModelPath(XsdContentModelPath parent, boolean isContentModel)
    {
        m_parent = parent;
        m_termIndex = -1;
        m_isContentModel = isContentModel;
    }

    /**
     * Gets the parent path, or null if is root.
     */
    public XsdContentModelPath getParentPath()
    {
        return m_parent;
    }

    /**
     * If this step is within a {@link com.tibco.xml.schema.SmModelGroup}, true, if within a {@link com.tibco.xml.schema.SmType} false.
     * @return The index.
     */
    public boolean isModelGroupStep()
    {
        return m_termIndex != -1;
    }

    /**
     * Gets the index of the model group child that is changing.<br>
     * This is not applicable if not {@link #isModelGroupStep}.
     * @return The 0-based index
     */
    public int getModelGroupIndex()
    {
        return m_termIndex;
    }

    /**
     * Gets true if this step is into the child (element) content model of false if into the attribute model.
     * This is not applicable if {@link #isModelGroupStep}.
     * @return The 0-based index
     */
    public boolean isStepInContentModel()
    {
        return m_isContentModel;
    }

    public String toString()
    {
        String p = m_parent == null ? "" : (m_parent.toString() + ", ");
        if (m_termIndex==-1)
        {
            p = p + " model-group(" + m_isContentModel + ")";
        }
        else
        {
            p = p + " term(" + m_termIndex + ")";
        }
        return p;
    }
}
