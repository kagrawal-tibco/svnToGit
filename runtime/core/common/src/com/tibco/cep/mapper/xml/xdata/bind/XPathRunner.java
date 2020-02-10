package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.List;

import javax.xml.transform.URIResolver;

import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableList;
import com.tibco.xml.channel.variable.helpers.SimpleVariableContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.data.primitive.XmlSequence;
import com.tibco.xml.data.primitive.XmlTreeNode;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XmlLoadConfig;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.helpers.NoNamespace;
import com.tibco.xml.xpath.XPathCompiler;
import com.tibco.xml.xquery.Expr;
import com.tibco.xml.xquery.ExprFocus;
import com.tibco.xml.xquery.FunctionGroup;
import com.tibco.xml.xquery.helpers.DefaultExprContext;
import com.tibco.xml.xquery.helpers.DefaultExprFocus;

/**
 * A pre-built compiled, ready-to-run XPath expression.<br>
 * Wraps-up the XPath implementation & presents a simple call method.
 */
public class XPathRunner {
    //private Templates mTemplates;
    //private VariableDefinitionList mVarDefs;

    public Expr mNewExpr;
    private final ExprContext mContext;
    private VariableDefinitionList mScope;
    private URIResolver m_uriResolver;
    private String m_xpath;

    public XPathRunner(ExprContext exprContext, String xpath)
    {
        this(exprContext,null,xpath);
    }

    public XPathRunner(ExprContext exprContext, URIResolver resolver, String xpath)
    {
        mContext = exprContext;
        mScope = exprContext.getVariables();
        m_uriResolver = resolver;
        m_xpath = xpath;
        buildNewXPath(exprContext,xpath);
    }

    /*
    public XPathRunner(ExprContext exprContext, String xpath,
                       int processor,
                       boolean optimizeXData,
                       boolean optimizeGeneric) {

        buildNewXPath(exprContext,xpath);
    }*/

    private static class XPExprContext extends DefaultExprContext
    {
        private final ExprContext m_exprContext;

        public XPExprContext(ExprContext ec, URIResolver r)
        {
            m_exprContext = ec;
            setLegacyMode(true);
            setLoadConfig(StripAllWhitespaceConfig.INSTANCE);
            if (r!=null)
            {
                setURIResolver(r);
            }
        }

        public String getNamespaceURIForPrefix(String prefix) throws PrefixToNamespaceResolver.PrefixNotFoundException
        {
            return m_exprContext.getNamespaceMapper().getNamespaceURIForPrefix(prefix);
        }

        public String getPrefixForNamespaceURI(String uri) throws NamespaceToPrefixResolver.NamespaceNotFoundException
        {
            return m_exprContext.getNamespaceMapper().getPrefixForNamespaceURI(uri);
        }

        public String getURI(String prefix)
        {
            return NoNamespace.getURI(prefix,this);
        }

        public boolean isFunctionAvailable(ExpandedName expandedName, int i)
        {
            // apparently not used...
            return super.isFunctionAvailable(expandedName, i);
        }

        public FunctionGroup getInScopeFunctions()
        {
            final FunctionGroup p = super.getInScopeFunctions();
            return new FunctionGroup()
            {
                public Expr getFunctionCallExpr(ExpandedName expandedName, Expr[] exprs)
                {
                    // is it a custom fn?
                    if (m_exprContext.getXPathFunctionSet().getAsFunctionGroup().isFunctionAvailable(expandedName,exprs.length))
                    {
                        // yup, get it from there.
                        return m_exprContext.getXPathFunctionSet().getAsFunctionGroup().getFunctionCallExpr(expandedName,exprs);
                    }
                    return p.getFunctionCallExpr(expandedName,exprs);
                }

                public boolean isFunctionAvailable(ExpandedName expandedName, int i)
                {
                    if (!p.isFunctionAvailable(expandedName,i))
                    {
                        return m_exprContext.getXPathFunctionSet().getAsFunctionGroup().isFunctionAvailable(expandedName,i);
                    }
                    return true;
                }
            };
        }
    }
    private void buildNewXPath(ExprContext exprContext, String xpath)
    {
        //WCETODO don't subclass like this; figure out how to setLexicalContext() when that appears; maybe change ExprContext.

        DefaultExprContext ec = new XPExprContext(exprContext,m_uriResolver);
        try
        {
            mNewExpr = XPathCompiler.compile(xpath,ec);
        }
        catch (Exception e)
        {
            //e.printStackTrace(System.err);
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean runBoolean(VariableList inputData) throws BindingException {

        try {
            DefaultExprContext ec = buildRuntimeExprContext();

            // performance.. instead of cloning the VariableList, give it to the SimpleVariableContext
            // which will treat it as a readOnly VariableProvider...
            SimpleVariableContext variableContext = new SimpleVariableContext(inputData);
            ec.pushVariableStack(variableContext);

            ExprFocus fc = new DefaultExprFocus();
            return mNewExpr.booleanValue(fc, ec);
        }
        catch (Exception e) {
            throw new BindingException(e);
        }
    }

    public String runString(VariableList inputData) throws BindingException {
        try {
            DefaultExprContext ec = buildRuntimeExprContext();
            ec.pushVariableStack(inputData);
            ExprFocus fc = new DefaultExprFocus();
            String s = mNewExpr.stringValue(fc,ec);
            if (s==null) // stringValue is now really stringValue or null.
            {
                return "";
            }
            return s;
        } catch (Exception e) {
            throw new BindingException(e);
        }
    }

    public double runNumber(final VariableList inputData) throws BindingException {
        try {
            DefaultExprContext ec = buildRuntimeExprContext();
            ec.pushVariableStack(inputData);
            ExprFocus fc = new DefaultExprFocus();
            // WCETODO really want an .doubleValue() on new expr.
            return mNewExpr.typedValue(fc,ec).getAtom(0).castAsDouble();
        } catch (Exception e) {
            throw new BindingException(e);
        }
    }

    public XmlSequence runSequence(final VariableList inputData) throws BindingException {
        try {
            DefaultExprContext ec = buildRuntimeExprContext();
            ec.pushVariableStack(inputData);
            ExprFocus fc = new DefaultExprFocus();
            // WCETODO really want an .doubleValue() on new expr.
            return mNewExpr.execute(fc,ec);
        } catch (Exception e) {
            throw new BindingException(e);
        }
    }

    /**
     *
     * @param inputData The input data context for evaluation. There are no variables.
     * @return The string result.
     * @throws BindingException
     */
    public String runString(XiNode inputData) throws BindingException {
        try {
            DefaultExprContext ec = buildRuntimeExprContext();
            ExprFocus fc = new DefaultExprFocus(inputData);
            String r = mNewExpr.stringValue(fc,ec);
            // String value can return null now, deal with it.
            if (r==null)
            {
                return "";
            }
            return r;
        } catch (Exception e) {
            throw new BindingException(e);
        }
    }

    /**
     * For testing, debugging only, returns a string representation of the value.
     */
    public String runDebugString(VariableList inputData, XiNode data) throws Exception {
        DefaultExprContext ec = buildRuntimeExprContext();
        ec.pushVariableStack(inputData);
        ExprFocus fc = new DefaultExprFocus(data);
        String r = mNewExpr.stringValue(fc,ec);
        // stringValue can return null for empty.
        if (r==null)
        {
            return "";
        }
        return r;
    }

    private DefaultExprContext buildRuntimeExprContext()
    {
        DefaultExprContext ec = new XPExprContext(mContext,m_uriResolver);

        // These are set in the constructor...
        //ec.setLegacyMode(true);
        //ec.setLoadConfig(StripAllWhitespaceConfig.INSTANCE);
        return ec;
    }

    /**
     * Adds the variable names referenced by this XPath expression to the list
     */
    public void getAllVariables(List list) {
        if (m_xpath!=null) {
            com.tibco.cep.mapper.xml.xdata.xpath.Expr e = Parser.parse(m_xpath);
            Utilities.getAllVariables(e,list);
        }
    }
    public static class StripAllWhitespaceConfig implements XmlLoadConfig
    {

        public static final XmlLoadConfig INSTANCE = new StripAllWhitespaceConfig();
        public boolean getIncludeComments()
        {
            return true;
        }

        public boolean getIncludeProcessingInstructions()
        {
            return true;
        }

        public boolean getStripWhitespace(ExpandedName name)
        {
            return true;
        }

        public boolean isNoOp()
        {
            return false;
        }

        public boolean matches(XmlTreeNode node)
        {
            if (node.getNodeKind()==XmlNodeKind.TEXT)
            {
                XmlTypedValue value = node.getTypedValue();
                return !value.isWhitespace();
            }
            return true;
        }
    }

}

