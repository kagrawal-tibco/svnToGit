package com.tibco.cep.mapper.xml.xdata.bind;

import com.tibco.cep.mapper.xml.xdata.xpath.expr.XPathCheckArguments;


/**
 * A set of parameters to control the format of a template report and includes a {@link CancelChecker}
 * Used only in {@link com.tibco.cep.mapper.xml.xdata.bind.Binding#createTemplateReport}
 */
public final class TemplateReportArguments
{
    private boolean m_checkForMove;
    private boolean m_checkForRenameNamespace;
    private boolean m_checkForRenameGeneral;
    private boolean m_skipTemplateParams;
    private boolean m_recordMissing;
    private boolean m_checkExtendedWarnings;
    private boolean m_checkNestedNamespaceDecls = true;
    private boolean m_suggestOptimizations = false;
    private XPathCheckArguments m_xpathCheckArguments;

    private CancelChecker m_cancelChecker = new DefaultCancelChecker();

    public TemplateReportArguments()
    {
        m_checkForMove = false;
        m_checkForRenameNamespace = false;
        m_checkForRenameGeneral = false;
    }

    public CancelChecker getCancelChecker()
    {
        return m_cancelChecker;
    }

    public void setCancelChecker(CancelChecker cc)
    {
        m_cancelChecker = cc;
    }

    public boolean getCheckForMove()
    {
        return m_checkForMove;
    }

    /**
     * Sets if the report generation should attempt full (i.e. expensive) analysis of possible fixes for moving items.<br>
     * If not set, it isn't required so the extra work shouldn't be done.<br>
     * NOTE: For this to work well, the option:
     * {@link #setRecordingMissing} must be set on.
     * Default is <code>false</code>.
     */
    public void setCheckForMove(boolean f)
    {
        m_checkForMove = f;
    }

    /**
     * Shortcut to set both {@link #setCheckForRenameNamespace} and {@link #setCheckForRenameGeneral} on or off.
     * Default for both is <code>false</code>.
     */
    public void setCheckForRename(boolean f)
    {
        setCheckForRenameNamespace(f);
        setCheckForRenameGeneral(f);
    }

    /**
     * Sets if the report generation should attempt full renaming items because of a namespace change.<br>
     * If not set, it isn't required so the extra work shouldn't be done.<br>
     * Default is <code>false</code>.
     * The method {@link #setCheckForRenameGeneral} enables further but independent checks.
     */
    public void setCheckForRenameNamespace(boolean v)
    {
        m_checkForRenameNamespace = v;
    }

    public boolean getCheckForRenameNamespace()
    {
        return m_checkForRenameNamespace;
    }

    /**
     * Sets if the report generation should attempt full (i.e. expensive) analysis of possible fixes for renaming items.<br>
     * This is an independent test of {@link #setCheckForRenameNamespace}, but if both are on, this check comes after.
     * If not set, it isn't required so the extra work shouldn't be done.<br>
     * Default is <code>false</code>.
     * The method {@link #setCheckForRenameNamespace} enables an independent earlier check.
     */
    public void setCheckForRenameGeneral(boolean v)
    {
        m_checkForRenameGeneral = v;
    }

    public boolean getCheckForRenameGeneral()
    {
        return m_checkForRenameGeneral;
    }

    /**
     * If set, all the missing preceding/missing following entries will be stored.<br>
     * Otherwise, only a boolean value will be recorded.
     */
    public boolean isRecordingMissing()
    {
        return m_recordMissing;
    }

    public void setRecordingMissing(boolean f)
    {
        m_recordMissing = f;
    }

    /**
     * If set, template parameters will <b>not</b> be included in the report or any of the analysis, default is false.
     * @return
     */
    public boolean isSkippingTemplateParams()
    {
        return m_skipTemplateParams;
    }

    public void setSkippingTemplateParams(boolean val)
    {
        m_skipTemplateParams = val;
    }

    /**
     * If set, considers a nested namespace declaration a warning (move to top).<br>
     * WCETODO -- later, break into 2 categories; check for overlapping & just check.
     * By default, this is <code>true</code>
     */
    public boolean getCheckForNestedNamespacesDeclarations()
    {
        return m_checkNestedNamespaceDecls;
    }

    public void setCheckForNestedNamespaceDeclarations(boolean checkForNestedNamespaceDecls)
    {
        m_checkNestedNamespaceDecls = checkForNestedNamespaceDecls;
    }

    /**
     * Gets if extended-warnings should be checked.<br>
     * These are warnings that are not critical, and, in particular, may not have been there in earlier versions, so
     * need to be turn-offable.<br>
     * By default, this is <code>false</code>
     */
    public boolean getCheckExtendedWarnings()
    {
        return m_checkExtendedWarnings;
    }

    public void setCheckExtendedWarnings(boolean val)
    {
        m_checkExtendedWarnings = val;
    }

    /**
     * Gets if optimizations should be suggested.<br>
     * These are, of course, not critical, merely suggestions to improve performance (pulling stuff out of loops, etc.)
     * need to be turn-offable.<br>
     * By default, this is <code>false</code>
     */
    public boolean getSuggestOptimizations()
    {
        return m_suggestOptimizations;
    }

    public void setSuggestOptimizations(boolean suggestOptimizations)
    {
        m_suggestOptimizations = suggestOptimizations;
    }

    /**
     * Gets the xpath type checking arguments.<br>
     * By default, returns a default instance (see {@link XPathCheckArguments} for what those defaults are).
     */
    public XPathCheckArguments getXPathCheckArguments()
    {
        if (m_xpathCheckArguments==null)
        {
            m_xpathCheckArguments = new XPathCheckArguments();
        }
        return m_xpathCheckArguments;
    }

    public void setXPathCheckArguments(XPathCheckArguments arguments)
    {
        m_xpathCheckArguments = arguments;
    }
}
