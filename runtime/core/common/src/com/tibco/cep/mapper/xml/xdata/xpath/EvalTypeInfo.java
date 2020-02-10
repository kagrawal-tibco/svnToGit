package com.tibco.cep.mapper.xml.xdata.xpath;

import java.util.HashMap;

import com.tibco.cep.mapper.xml.xdata.xpath.expr.NodeTestExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.XPathCheckArguments;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * A set of functions & data for additional functionality used in Expr's evalType,
 * for example, recording errors, recording expr->type maps, etc.
 */
public final class EvalTypeInfo {
    private CodeCompleteData mCodeComplete;
    private boolean m_recordErrors;
    private ErrorMessageList m_errors = ErrorMessageList.EMPTY_LIST;
    private HashMap mNamespaceResolutions; // Expr -> String (namespace)
    private HashMap mPrefixResolutions;    // Expr -> String (prefix)
    private HashMap mInputContexts;
    private HashMap mReturnTypes;          // Expr -> XType
    private SmSequenceType mCodeCompleteReturnType;
    private boolean mRecordingNamespaceResolutions;
    private boolean mFixCustomFunctionNamespaces;
    private XPathCheckArguments m_xpathArguments;

    public EvalTypeInfo() {
    }

    /**
     * Turns error recording on.
     */
    public void setRecordErrors(boolean on)
    {
        m_recordErrors = on;
    }

    public boolean isRecordingPrefixResolutions() {
        return mPrefixResolutions!=null;
    }

    public void setRecordNamespaceResolutions(boolean on) {
        mRecordingNamespaceResolutions = on;
    }

    public void setRecordPrefixResolutions(boolean on) {
        if (on) {
            mPrefixResolutions = new HashMap();
        } else {
            mPrefixResolutions = null;
        }
    }

    public void setRecordExprContexts(boolean on) {
        if (on) {
            mInputContexts = new HashMap();
        } else {
            mInputContexts = null;
        }
    }

    public void setRecordReturnTypes(boolean on) {
        if (on) {
            mReturnTypes = new HashMap();
        } else {
            mReturnTypes = null;
        }
    }

    public void setCodeComplete(CodeCompleteData data) {
        mCodeComplete = data;
    }

    public CodeCompleteData getCodeComplete() {
        return mCodeComplete;
    }

    /**
     * Sets the expected return type, which code complete may use for better reporting.
     * (Clean up, maybe put as regular argument?)
     */
    public void setCodeCompleteReturnType(SmSequenceType type) {
        mCodeCompleteReturnType = type;
    }

    public SmSequenceType getCodeCompleteReturnType() {
        return mCodeCompleteReturnType;
    }

    public void addError(ErrorMessage message)
    {
        if (m_recordErrors)
        {
            m_errors = m_errors.addMessage(message);
        }
    }

    public void addErrors(ErrorMessageList message)
    {
        if (m_recordErrors)
        {
            m_errors = m_errors.addMessages(message);
        }
    }

    public ErrorMessageList getErrors()
    {
        return m_errors;
    }

    public int getErrorCount()
    {
        return m_errors.size();
    }

    /**
     * Called by Expr subclasses to record types.
     * For convenience, returns the recorded type.
     */
    public SmSequenceType recordReturnType(Expr theExpr, SmSequenceType type) {
        if (mReturnTypes!=null) {
            mReturnTypes.put(theExpr,type);
        }
        // nyi.
        return type;
    }

    /**
     * Called by Expr subclasses to record types.
     */
    public void recordExprContext(Expr theExpr, ExprContext context) {
        if (mInputContexts!=null) {
            mInputContexts.put(theExpr,context);
        }
    }

    /**
     * Returns the return type of an expression, setRecordReturnTypes must have been turned on.
     */
    public SmSequenceType getReturnType(Expr expr) {
        SmSequenceType t = (SmSequenceType) mReturnTypes.get(expr);
        if (t==null) {
            // this can happen if there was an error detected somewhere... in that case, every below there becomes an error.
            t = SMDT.VOID;
        }
        return t;
    }

    public ExprContext getExprContext(Expr theExpr) {
        return (ExprContext) mInputContexts.get(theExpr);
    }

    /**
     * For converting between an unnamespaced name test (i.e. 'street') to
     * a namespaced one (i.e. 'po:street').  Records the node test expression and
     * the namespaceURI it should become.
     * @param namespaceURI The namespaceURI for this node, null if there is none.
     */
    public void recordNamespaceResolution(NodeTestExpr expr, String namespaceURI) {
        if (mNamespaceResolutions==null)
        {
            //WCETODO hacky, these are always on, I guess.
            mNamespaceResolutions = new HashMap();
        }
        if (mNamespaceResolutions!=null) {
            mNamespaceResolutions.put(expr,namespaceURI);
        }
    }

    /**
     * Indicates that this prefix is not needed because it's namespace can be
     * inferred from context.
     */
    public void recordPrefixResolution(NodeTestExpr expr, String prefix) {
        if (mPrefixResolutions!=null && prefix!=null) {
            mPrefixResolutions.put(expr,prefix);
        }
    }

    /**
     * For recorded namespace resolution, returns the URI of for this node test.
     * (Must have run with namespace resolution on)
     */
    public String getNamespaceResolution(NodeTestExpr expr)
    {
        if (mNamespaceResolutions==null)
        {
            return null;
        }
        return (String) mNamespaceResolutions.get(expr);
    }

    public boolean hasNamespaceResolution(NodeTestExpr expr) {
        if (mNamespaceResolutions==null)
        {
            return false;
        }
        return mNamespaceResolutions.containsKey(expr);
    }

    public boolean isRecordingNamespaceResolutions()
    {
        return mRecordingNamespaceResolutions;
    }
    /**
     * For recorded prefix resolution, returns the URI of for this node test.
     * (Must have run with prefix resolution on)
     */
    public String getPrefixResolution(NodeTestExpr expr) {
        return (String) mPrefixResolutions.get(expr);
    }

    public String[] getAllRecordedNamespaces() {
        return (String[]) mNamespaceResolutions.values().toArray(new String[0]);
    }

    /**
     * A flag indicating if (for migrate fixing a bug where namespaces weren't written for custom-functions),
     * java custom function namespaces should be 'repaired'
     */
    public boolean getFixCustomFunctionNamespaces() {
        return mFixCustomFunctionNamespaces;
    }

    /**
     * @see #getFixCustomFunctionNamespaces()
     */
    public void setFixCustomFunctionNamespaces(boolean val) {
        mFixCustomFunctionNamespaces = val;
    }

    public XPathCheckArguments getXPathCheckArguments()
    {
        if (m_xpathArguments==null)
        {
            m_xpathArguments = new XPathCheckArguments();
        }
        return m_xpathArguments;
    }

    public void setXPathCheckArguments(XPathCheckArguments args)
    {
        m_xpathArguments = args;
    }
}

