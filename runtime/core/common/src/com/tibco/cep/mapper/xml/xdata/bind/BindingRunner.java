package com.tibco.cep.mapper.xml.xdata.bind;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.ChainedURIResolver;
import com.tibco.cep.mapper.xml.xdata.InputData;
import com.tibco.cep.mapper.xml.xdata.ValidationUtils;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.Utilities;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableList;
import com.tibco.xml.channel.variable.helpers.SimpleVariableContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.ItemSequence;
import com.tibco.xml.data.primitive.NamespaceContext;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.data.primitive.XmlSequence;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiBuilder;
import com.tibco.xml.datamodel.helpers.XiNamespace;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.helpers.NoNamespace;
import com.tibco.xml.transform.trax.DefaultTransformerFactory;
import com.tibco.xml.transform.trax.TransformerEx;
import com.tibco.xml.util.XmlTreeNodeHandler;
import com.tibco.xml.validation.factory.XmlTreeNodeValidatorFactory;
import com.tibco.xml.validation.factory.state.StateDrivenXmlTreeNodeValidatorFactory;
import com.tibco.xml.validation.helpers.Validator_XmlContent_Remark_ExternalCache;
import com.tibco.xml.xquery.FunctionGroup;

/**
 * Runs and validates the 'input bindings' for activities; does the dirty-work setup for the XSLT engine.
 * WCETODO -- Maybe change how variables get passed in --- make variable list compatible w/ XSLT engine so don't need
 * to find 'em all before calling.
 *
 * WCETODO --- remove XiTransformer --- should stick with an extended TraX; this is just double-wrapping.  Instead,
 * the XiBuilder results should have validation baked in...
 */
public class BindingRunner
{
    private boolean mValidate;
    private Templates mTemplates;
    private ExpandedName[] mArgs;
    private SmParticleTerm mExpectedOutput;
    private SmComponentProviderEx mComponentProvider;
    private DefaultTransformerFactory mTransformerFactory;
    private URIResolver mResolver;

    /**
     * Use this constructor to specify variables in scope
     */
    public BindingRunner(TemplateBinding tb,
                         StylesheetContext stylesheetContext,
                         NamespaceContext namespaceContext,
                         SmParticleTerm expectedOutput,
                         URIResolver resolver,
                         boolean validate,
                         String[] allowedVariables) throws TransformerConfigurationException {
       setURIResolver(resolver);
       String[] usedVariables;
        if (allowedVariables == null) {
            usedVariables = getAllVariables(tb);
        } else {
            usedVariables = allowedVariables;
        }
        mExpectedOutput = expectedOutput;
        mValidate = validate;
        String xslt = getXsltForInternal(tb,usedVariables,namespaceContext);

		FunctionResolver mFunctionResolver = stylesheetContext.getFunctionResolver();
		if ( mFunctionResolver == null )
			throw new NullPointerException( "Function set was null!" );
        FunctionGroup fg = mFunctionResolver.getAsFunctionGroup();
        mComponentProvider = stylesheetContext.getComponentProvider();
		if ( mComponentProvider==null )
			throw new NullPointerException( "Null schema provider" );

		getTransformerFactory() .setExternalFunctions( fg );

        mArgs = new ExpandedName[ usedVariables.length ];
        for ( int i = 0; i < mArgs.length; i++ )
        	mArgs[i] = ExpandedName.makeName( usedVariables[i] );
        mTemplates = getTransformerFactory().newTemplates(new StreamSource(new StringReader(xslt)));
    }

    public DefaultTransformerFactory getTransformerFactory() {
        if(mTransformerFactory == null) {
            mTransformerFactory = new DefaultTransformerFactory();
            URIResolver resolver = mTransformerFactory.getURIResolver();
            if(getURIResolver() != null) {
               resolver = new ChainedURIResolver(getURIResolver(), resolver);
            }
            mTransformerFactory.setURIResolver(resolver);
        }
        return mTransformerFactory;
    }
   public URIResolver getURIResolver() {
       return mResolver;
   }
   public void setURIResolver(URIResolver resolver) {
       mResolver = resolver;
   }
    private static final String[] getAllVariables(Binding binding)
    {
        ArrayList temp = new ArrayList();
        getAllVariables(binding,temp);
        return (String[]) temp.toArray(new String[temp.size()]);
    }

    public static final void getAllVariables(Binding binding, List temp) {
        String f = binding.getFormula();
        if (!(binding instanceof TextBinding) && f!=null)
        {
            // WCETODO, fix this, not totally correct because of AVTs...
            Expr e = Parser.parse(f);
            Utilities.getAllVariables(e,temp);
        }
        Binding[] children = binding.getChildren();
        for (int i=0;i<children.length;i++) {
            getAllVariables(children[i],temp);
        }
    }

    public static String getXsltFor(TemplateBinding tb, NamespaceContext namespaceContext)
    {
		String[] usedVars = getAllVariables(tb);
        return getXsltForInternal(tb,usedVars,namespaceContext);
    }

    private static String getXsltForInternal(TemplateBinding tb, String[] usedVariables, NamespaceContext namespaceContext) {
        TemplateBinding ctb = (TemplateBinding) tb.cloneDeep();
        ctb.setFormula("/"); // the match.
        StylesheetBinding sb = new StylesheetBinding(BindingElementInfo.EMPTY_INFO);
        OutputBinding ob = new OutputBinding(BindingElementInfo.EMPTY_INFO);
        ob.setOutputMethod("xml");
        sb.addChild(ob);
        for (int i=0;i<usedVariables.length;i++)
        {
            String v = usedVariables[i];
            ExpandedName vExName = ExpandedName.makeName(NoNamespace.URI, v);
            sb.addChild(new ParamBinding(BindingElementInfo.EMPTY_INFO, vExName));
        }
        HashSet keepers = new HashSet();
        boolean isAutoExclude;
        if (ctb.getLocalExcludePrefixes()!=null)
        {
            // Exclude prefixes has been manually specified:
            sb.setExcludedPrefixes(ctb.getLocalExcludePrefixes());
            isAutoExclude = false;
        }
        else
        {
            findPrefixesToKeep(ctb,keepers);
            isAutoExclude = true;
        }
        Iterator i = namespaceContext.getPrefixes();

        // hand compute (default)
        while (i.hasNext())
        {
            String pfx = (String) i.next();
            try
            {
                String ns = namespaceContext.getNamespaceURIForPrefix(pfx);
                XiNamespace.setNamespace(sb.asXiNode(),pfx,ns);
                if (isAutoExclude && !keepers.contains(pfx))
                {
                    sb.getExcludedPrefixesAsSet().add(pfx);
                }
            }
            catch (PrefixToNamespaceResolver.PrefixNotFoundException pnfe)
            {
                // punt.
            }
        }
        sb.addChild(ctb);
        return sb.toString();
    }

    /**
     * Records all prefixes the must not be excluded from output generation.<br>
     * Currently keeps all prefixes for xsi:type.
     * @param b
     * @param set
     */
    private static void findPrefixesToKeep(Binding b, HashSet set)
    {
        QName qn = BindingManipulationUtils.getXsiTypeSubQName(b);
        if (qn!=null)
        {
            String pfx = qn.getPrefix();
            if (pfx!=null)
            {
                set.add(pfx);
            }
        }
        for (int i=0;i<b.getChildCount();i++)
        {
            findPrefixesToKeep(b.getChild(i),set);
        }
    }
    /**
     * Execute a transformation
     * @param variables the list of variables variables
     * may be required by the transformation's output data
     * @return the result of the transformation
     */
    public XiNode run(final VariableList variables) throws BindingException {
        try {
            InputData id = getOnceOnlyBinding(variables);
            XiNode ret = id.getXiNode();
            return ret;
        } catch (SAXException se) {
            Exception chained = se.getException();
            if (chained==null) {
                chained = se;
            }
            throw new BindingException("Validation error",chained);
        } catch (Exception e) {
            //e.printStackTrace();
            throw new BindingException(e);
        }
    }

    public ReusableBinding getReusableBinding(VariableList variables) throws TransformerConfigurationException {
        Transformer transformer = mTemplates.newTransformer();
        prepareTransformerRun((TransformerEx) transformer, mArgs, variables);
        return new ReusableBinding((TransformerEx) transformer, mValidate, mExpectedOutput, mComponentProvider);
    }

    public OnceOnlyBinding getOnceOnlyBinding(VariableList variables) throws TransformerConfigurationException {
        TransformerEx transformer = (TransformerEx)mTemplates.newTransformer();

        // performance.. instead of calling setArgument for each variable, give the VariableList
        // to the SimpleVariableContext which will treat it as a readOnly VariableProvider...
        SimpleVariableContext variableContext = new SimpleVariableContext(variables);
        transformer.setVariableContext(variableContext);
        return new OnceOnlyBinding(transformer, mValidate, mExpectedOutput, mComponentProvider);
    }

    /**
     * Sets up the variables for a given run.
     * @param transformer
     * @param vars
     * @param variables
     */
    protected static void prepareTransformerRun(TransformerEx transformer, ExpandedName[] vars, VariableList variables)
    {
        for (int i = 0; i < vars.length; i++)
        {
            ExpandedName varName = vars[i];
            if (!variables.containsKey(varName))
                transformer.setArgument(varName, ItemSequence.EMPTY);
            else
            {
                XmlSequence varData = variables.getVariableValue(varName);
                transformer.setArgument(varName, varData);
            }
        }
    }

    /**
     * Move this elsewhere, but for now, this is as good a place as any.<br>
     * Validates the documentNode, and ensures that it is a document.
     * @param documentNode
     * @param nsProvider
     * @param expectedTerm
     * @throws SAXException
     */
    public static XiNode validate(XiNode documentNode, final SmNamespaceProvider nsProvider, final SmParticleTerm expectedTerm)
            throws SAXException {

        // Nice sanity check:
        if (documentNode.getNodeKind()!=XmlNodeKind.DOCUMENT)
        {
            throw new IllegalArgumentException("Expected a document node, got a " + documentNode.getNodeKind());
        }

        XiBuilder builder = new XiBuilder();
        BindingRemarkHandler remarkHandler = new BindingRemarkHandler();
		if (ValidationUtils.isUsingTheOldValidator()) {
            Validator_XmlContent_Remark_ExternalCache validator = new Validator_XmlContent_Remark_ExternalCache();
            validator.setNamespaceProvider(nsProvider);
            validator.setRootSmParticleTerm(expectedTerm);
            //ssi validator.setXmlContentHandler(XmlContentTermination.getInstance());
            //validator.setXmlContentHandler(new XmlContentTracer(builder));
            validator.setXmlContentHandler(builder);
            validator.setXmlRemarkHandler(remarkHandler);
            documentNode.serialize(validator);
		}
		else {
/*            Validator_XmlContent_Remark_ExternalCache2 validator = new Validator_XmlContent_Remark_ExternalCache2();
			validator.setNamespaceProvider(nsProvider);
			validator.setRootSmParticleTerm(expectedTerm);
			//ssi validator.setXmlContentHandler(XmlContentTermination.getInstance());
            validator.setXmlContentHandler(builder);

			validator.setXmlRemarkHandler(remarkHandler);
			documentNode.serialize(validator);
*/
            XmlTreeNodeValidatorFactory	factory = new StateDrivenXmlTreeNodeValidatorFactory(builder, remarkHandler);
            XmlTreeNodeHandler	validator = factory.newValidator(nsProvider, expectedTerm);
            validator.xmlTreeNode(documentNode);

		}
        remarkHandler.assertNoErrors();

       XiNode retNode = builder.getNode();

       // we need to reset the builder.. it has a reference into the thing
       // that it built, and the builder sticks around because the validator has a reference to it,
       // and this causes a delay in GC for the document.
       // Having a reset method on the validator would probably be cleaner, but this works.
       builder.reset();
       return retNode;
    }

    /**
     * For testing, returns the raw output results.
     */
    public String test_run(VariableList variables) throws Exception
    {
        /*Transformer transformer = mTemplates.newTransformer();
        prepareTransformer(transformer,mVarDefs,variables);
        StringWriter sw = new StringWriter();
        transformer.transform(new StreamSource(new StringReader("<?xml version=\"1.0\"?>\n<empty/>")),new StreamResult(sw));
        sw.flush();
        return sw.toString();*/
        throw new RuntimeException();
    }
}
