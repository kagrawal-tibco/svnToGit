package com.tibco.cep.mapper.xml.xdata.xpath.expr;



/**
 * A set of switches for xpath type checking, i.e. warning levels.
 */
public final class XPathCheckArguments
{
    private boolean m_lenientMaxOccursCheck = true;
    private boolean m_includeAutoCast;
    private boolean m_includePrimitiveTypeWarnings=true;
    private boolean m_inputDataChecking = true;
    private boolean m_checkMiscWarnings = true;

    private boolean m_hasXMLEscapedLiterals=true;
    private boolean m_usesTypedNodes; // XPath 1.0, the answer is false, for 2.0 the answer is yes (in 1.0 it operates
    // in untyped mode always)

    private boolean m_checkForNameTestRenamespace = true;

    /**
     * Indicates if auto-cast warnings should be included.<br>
     * Default is <code>false</code>.
     */
    public boolean getIncludeAutoCast()
    {
        return m_includeAutoCast;
    }

    public void setIncludeAutoCast(boolean autoCast)
    {
        m_includeAutoCast = autoCast;
    }

    /**
     * Indicates if primitive type conversions warnings should be included.<br>
     * Default is <code>true</code>.
     */
    public boolean getIncludePrimitiveTypeConversions()
    {
        return m_includePrimitiveTypeWarnings;
    }

    public void setIncludePrimitiveTypeConversions(boolean primitiveTypeConversions)
    {
        m_includePrimitiveTypeWarnings = primitiveTypeConversions;
    }

    /**
     * Indicates if errors/warnings concerning input data (input and variables) should be issued.<br>
     * Default is <code>true</code>.
     */
    public boolean getInputDataChecking()
    {
        return m_inputDataChecking;
    }

    public void setInputDataChecking(boolean inputDataChecking)
    {
        m_inputDataChecking = inputDataChecking;
    }

    /**
     * Indicates if the xpaths being checked have had the literal strings XML-character escaped or not.<br>
     * Default is <code>true</code>.<br>
     * In the xpaths/bindings, before displaying in the GUI, all strings are, internally, converted to use XML-character escaping
     * so that the display, error message positions, etc., can work off the 'logical' string (with escapes).
     */
    public boolean getHasXMLEscapeLiterals()
    {
        return m_hasXMLEscapedLiterals;
    }

    public void setHasXMLEscapeLiterals(boolean lit)
    {
        m_hasXMLEscapedLiterals = lit;
    }

    /**
     * Gets if the xpath engine uses typed input nodes (as in XPath 2.0) or not (as in XPath 1.0).<br>
     * Default is <code>false</code>
     */
    public boolean getUsesTypedNodes()
    {
        return m_usesTypedNodes;
    }

    public void setUsesTypedNodes(boolean val)
    {
        m_usesTypedNodes = val;
    }

    /**
     * If true, don't issue max occurs warnings in the event that max occurs is not unbounded.<br>
     * Default is <code>true</code>.<br>
     * This is for schemas, such as EDI, which use stuff like: (A | B | C)(1,3) to indicate 'you must have 1 to 3 of these,
     * but no two may be the same'.
     */
    public boolean getLenientMaxOccursChecking()
    {
        return m_lenientMaxOccursCheck;
    }

    public void setLenientMaxOccursChecking(boolean lenient)
    {
        m_lenientMaxOccursCheck = lenient;
    }

    /**
     * If true check for misc. non-critical xpath warnings.<br>
     * Default is <code>true</code>.
     */
    public boolean getCheckMiscWarnings()
    {
        return m_checkMiscWarnings;
    }

    public void setCheckMiscWarnings(boolean checkMisc)
    {
        m_checkMiscWarnings = checkMisc;
    }

    /**
     * Sets if should check for fixes nametests being in the wrong namespace.<br>
     * Default is <code>true</code>.
     */
    public void setCheckForNameTestRenameNamespace(boolean t)
    {
        m_checkForNameTestRenamespace = t;
    }

    public boolean getCheckForNameTestRenameNamespace()
    {
        return m_checkForNameTestRenamespace;
    }
}
