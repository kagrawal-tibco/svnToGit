package com.tibco.be.functions.xpath;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.namespace.QName;

import org.genxdm.bridgekit.xs.BuiltInSchema;
import org.genxdm.processor.xpath.v20.XPath2Toolkit;
import org.genxdm.processor.xpath.v20.fnop.FunctionGroup;
import org.genxdm.processor.xpath.v20.functions.XQueryFunctionGroup;
import org.genxdm.processor.xpath.v20.impl.DefaultDynamicContext;
import org.genxdm.processor.xpath.v20.operators.XPath2OperatorImpls;
import org.genxdm.processor.xpath.v20.operators.XPath2OperatorResolver;
import org.genxdm.processor.xpath.v20.operators.XPath2OperatorSigns;
import org.genxdm.processor.xpath.v20.operators.api.OperatorImpls;
import org.genxdm.processor.xpath.v20.operators.api.OperatorResolver;
import org.genxdm.processor.xpath.v20.operators.api.OperatorSigns;
import org.genxdm.processor.xpath.v20.shared.util.UnaryIterable;
import org.genxdm.typed.TypedContext;
import org.genxdm.typed.types.Emulation;
import org.genxdm.typed.types.TypesBridge;
import org.genxdm.typed.variant.ItemIterable;
import org.genxdm.xpath.v20.expressions.DynamicContextArgs;
import org.genxdm.xpath.v20.expressions.Expr;
import org.genxdm.xpath.v20.expressions.ExprResult;
import org.genxdm.xpath.v20.expressions.StaticContextArgs;
import org.genxdm.xs.types.ComplexType;
import org.genxdm.xs.types.SequenceType;
import org.xml.sax.InputSource;

import com.tibco.be.model.functions.FunctionsCatalogVisitor;
import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.Variable;
import com.tibco.be.model.functions.VariableList;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.rdf.XiNodeBuilder;
import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeRenderer;
import com.tibco.be.model.types.xsd.XSDTypeRegistry;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.genxdm.processor.xslt.XSLTCompositeFunctionGroup;
import com.tibco.xml.cxf.ootb.runtime.OOTBCustomFunctionsImpl;
import com.tibco.xml.cxf.runtime.impl.CustomXPathFunctionGroup;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.values.XsDateTime;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.nodes.Text;

public class XPath2Helper {

	static {
		// with BW 6.5.1, null variables get converted to an empty string (from XiTypedModel::getValue)
		// setting this property forces the previous functionality to maintain a null
		// NOTE: This is set in both XSLT2Helper and XPath2Helper to ensure it is set before any mappings
        System.setProperty("bw.xml.typedmodel.oldgetvalue", "true");
    }
	
	private class ResultContext {
//		ItemIterable<XiNode,XmlAtomicValue> result;
		XmlAtomicValue result;
		DynamicContextArgs<XiNode, XmlAtomicValue> dargs;
		TypedContext<XiNode,XmlAtomicValue> typedContext;
		
		public ResultContext(XmlAtomicValue xmlAtomicValue, DynamicContextArgs<XiNode, XmlAtomicValue> dArgs, TypedContext<XiNode,XmlAtomicValue> typedContext) {
			this.result = xmlAtomicValue;
			this.dargs = dArgs;
			this.typedContext = typedContext;
		}
		
	}
	
	/*
	 * Keeps the variables used in XPath so
	 * we don't need to recalculate each time
	 */
	private class ExprContext {
		Expr expression;
		List variables;
		DynamicContextArgs<XiNode, XmlAtomicValue> dargs;
		TypedContext<XiNode, XmlAtomicValue> typedContext;
		
		public ExprContext(Expr expression, List variables) {
			this.expression = expression;
			this.variables = variables;
		}
		public void setDynamicArgs(
				DynamicContextArgs<XiNode, XmlAtomicValue> dargs) {
			this.dargs = dargs;
		}
		public void setTypedContext(
				TypedContext<XiNode, XmlAtomicValue> typedContext) {
			this.typedContext = typedContext;
		}
	}
	
	static XPath2Toolkit toolKit = new XPath2Toolkit();
    static XSDTypeRegistry registry = XSDTypeRegistry.getInstance();
    public static TypeConverter xsd2java_dt_conv= registry.nativeToForeign(XsDateTime.class,  GregorianCalendar.class);
    public static TypeRenderer java2xsd_dt_conv= registry.foreignToNative(XsDateTime.class, GregorianCalendar.class);
    // keep the cache ThreadLocal as the ExprContext contains the dynamic args, which changes with each XPath evaluation
    private ThreadLocal<ConcurrentHashMap<String, ExprContext>> queryCache = new ThreadLocal<ConcurrentHashMap<String, ExprContext>>();

    private final OperatorResolver operators = new XPath2OperatorResolver();
	private XQueryFunctionGroup functionGroup = new XQueryFunctionGroup();
	
	private OperatorSigns opSigns = new XPath2OperatorSigns();
	private OperatorImpls opImpls = new XPath2OperatorImpls(this.operators);
	private boolean generic = false;

//	private DefaultDynamicContext<XiNode, XmlAtomicValue> env;
	public static final String BE_CUSTOM_NAMESPACE = "www.tibco.com/be/custom/";
	public static final String TIB_CUSTOM_NAMESPACE = "http://www.tibco.com/bw/xslt/custom-functions";
	
	public DynamicContextArgs<XiNode, XmlAtomicValue> getDynamicArgs(
			TypedContext<XiNode, XmlAtomicValue> typedContext, boolean compatibleMode) {
//		if (env == null) {
		DefaultDynamicContext<XiNode, XmlAtomicValue> env = new DefaultDynamicContext<XiNode, XmlAtomicValue>(typedContext);

//		env.setFunctionSigns("http://www.w3.org/2005/xpath-functions", functionGroup);
//		env.setFunctionImpls("http://www.w3.org/2005/xpath-functions", functionGroup);
//
//		BECustomFunctionImpls beCustomFunctionImpls = new BECustomFunctionImpls();
//		env.setFunctionSigns(BE_CUSTOM_NAMESPACE, beCustomFunctionImpls);
//		env.setFunctionImpls(BE_CUSTOM_NAMESPACE, beCustomFunctionImpls);
//		
//		try {
//			Iterator<String> catalogNames = FunctionsCatalog.getINSTANCE().catalogNames();
//			while (catalogNames.hasNext()) {
//				String catName = (String) catalogNames.next();
//				env.setFunctionSigns(BE_CUSTOM_NAMESPACE+catName, beCustomFunctionImpls);
//				env.setFunctionImpls(BE_CUSTOM_NAMESPACE+catName, beCustomFunctionImpls);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		env.setOperatorSigns(opSigns);
		env.setOperatorImpls(opImpls);
		
		env.setCompatibleMode(compatibleMode);

//		}
	    return env;
	}

    private ExprContext prepareExprContext(
			final String xpathExpression,
			final VariableList varList,
			final boolean compatibleMode) {
    	TypedContext<XiNode,XmlAtomicValue> typedContext = BESchemaComponentCacheManager.getInstance().getTypedContext();
    	
    	ExprContext exprContext = prepareExpression(xpathExpression, varList,
    			typedContext);

    	DynamicContextArgs<XiNode,XmlAtomicValue> dargs = getDynamicArgs(typedContext, compatibleMode);
    	List vars = exprContext.variables;
    	try {
    		makeDynamicArgs(vars, varList, dargs, typedContext);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    	exprContext.setDynamicArgs(dargs);
    	exprContext.setTypedContext(typedContext);
    	
    	// Evaluate.
    	return exprContext;
//    	return new ResultContext(exprContext.expression.atom(toolKit.<XiNode, XmlAtomicValue>emptyFocus(typedContext), dargs, typedContext), dargs, typedContext);
    }
    
	public <N,A> ItemIterable<XiNode,XmlAtomicValue> execute(String xpathExpression, String xml, String prefixes) {
    	try {
    	    XiNode node = XiSupport.getParser().parse(new InputSource(new StringReader(xml)));
    	    return executeWithContext(xpathExpression, node, prefixes);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	}
	}
	
	public <N,A> ItemIterable<XiNode,XmlAtomicValue> executeWithContext(String xpathExpression, XiNode context, String prefixes) {
		TypedContext<XiNode,XmlAtomicValue> typedContext = BESchemaComponentCacheManager.getInstance().getTypedContext();
		ExprContext exprContext = prepareExpression("var", xpathExpression, prefixes, typedContext);
		
		DynamicContextArgs<XiNode,XmlAtomicValue> dargs = getDynamicArgs(typedContext, true);
		final ItemIterable<XiNode, XmlAtomicValue> varItemIt = new ItemIterable.NodeIterable<XiNode, XmlAtomicValue>(new UnaryIterable<XiNode>(context));
		dargs.bindVariableValue(new QName("var"), varItemIt);
		dargs.setEmulation(Emulation.LEGACY);
		exprContext.setDynamicArgs(dargs);
		exprContext.setTypedContext(typedContext);
		
		ItemIterable<XiNode,XmlAtomicValue> result = exprContext.expression.evaluate(toolKit.<XiNode, XmlAtomicValue>emptyFocus(exprContext.typedContext), exprContext.dargs, exprContext.typedContext);
		return result;
		
//		Item<XiNode, XmlAtomicValue> item = new Item.NodeItem<XiNode, XmlAtomicValue>(context);
//		Focus<XiNode, XmlAtomicValue> focus = toolKit.<XiNode, XmlAtomicValue>newDynamicFocus(item, 1, 1);
//		ItemIterable<XiNode,XmlAtomicValue> result = exprContext.expression.evaluate(focus, exprContext.dargs, exprContext.typedContext);
//		return result;
	}

	public <N,A> String evalAsString(
			final String xpathExpression,
			final VariableList varList,
			final boolean compatibleMode) // true if compatible w/XPath 1.0, but usually false
	{

		ExprContext context = prepareExprContext(xpathExpression, varList, compatibleMode);
		// This seems to be the fastest way to execute
//		return context.expression.stringFunction(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext);
//		return context.expression.stringValue(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext);
		// see BE-23198 : we must use the below, otherwise an empty string will be returned instead of null, breaking previous functionality
		XmlAtomicValue atom = context.expression.atom(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext);
		if (atom != null) {
			return atom.castAsString();
		}
		return null;
	}

	public <N,A> boolean evalAsBoolean(
			final String xpathExpression,
			final VariableList varList,
			final boolean compatibleMode) // true if compatible w/XPath 1.0, but usually false
	{
		ExprContext context = prepareExprContext(xpathExpression, varList, compatibleMode);
		// Evaluate.
		// BE-23105 : Calling booleanFunction does not yield the proper result for XML (i.e. payload) nodes, instead always returning true.  Need to use the below execution instead
		XmlAtomicValue atom = context.expression.atom(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext);
		try {
			if (atom != null) {
				return atom.castAsBoolean();
			}
		} catch (XmlAtomicValueCastException e) {
			e.printStackTrace();
		}
		// if there is a cast exception, just return false
		return false;
		// This seems to be the fastest way to execute
//		boolean booleanFunction = context.expression.booleanFunction(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext);
		//Boolean booleanValue = context.expression.booleanValue(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext);
//		return booleanFunction;
	}
	
	public <N,A> double evalAsDouble(
			final String xpathExpression,
			final VariableList varList,
			final boolean compatibleMode) // true if compatible w/XPath 1.0, but usually false
	{
		ExprContext context = prepareExprContext(xpathExpression, varList, compatibleMode);
		// Evaluate.
		// This seems to be the fastest way to execute
		double val = context.expression.numberFunction(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext);
		if (Double.isNaN(val)) {
			return 0.0;
		}
		return val;
//		return context.expression.doubleValue(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext);
//		XmlAtomicValue atom = context.expression.atom(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext);
//		try {
//			return atom.castAsDouble();
//		} catch (XmlAtomicValueCastException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return 0d;
	}
	
	public <N,A> long evalAsLong(
			final String xpathExpression,
			final VariableList varList,
			final boolean compatibleMode) // true if compatible w/XPath 1.0, but usually false
	{
		ExprContext context = prepareExprContext(xpathExpression, varList, compatibleMode);
		// Evaluate.
		// This seems to be the fastest way to execute
		return (long) context.expression.numberFunction(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext);
//		return (long) context.expression.doubleValue(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext).longValue();
//		XmlAtomicValue atom = context.expression.atom(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext);
//		try {
//			return atom.castAsLong();
//		} catch (XmlAtomicValueCastException e) {
//			e.printStackTrace();
//		}
//		return 0L;
	}
	
	public <N,A> int evalAsInt(
			final String xpathExpression,
			final VariableList varList,
			final boolean compatibleMode) // true if compatible w/XPath 1.0, but usually false
	{
		ExprContext context = prepareExprContext(xpathExpression, varList, compatibleMode);
		// Evaluate.
		// This seems to be the fastest way to execute
		return (int) context.expression.numberFunction(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext);
//		return (int) context.expression.doubleValue(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext).intValue();
//		XmlAtomicValue atom = context.expression.atom(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext);
//		try {
//			return atom.castAsInt();
//		} catch (XmlAtomicValueCastException e) {
//			e.printStackTrace();
//		}
//		return 0;
	}
	
    public <N,A> Calendar evalAsDateTime(
			final String xpathExpression,
			final VariableList varList,
			final boolean compatibleMode) // true if compatible w/XPath 1.0, but usually false
	{
		ExprContext context = prepareExprContext(xpathExpression, varList, compatibleMode);
		// Evaluate.
//		return (Calendar) xsd2java_dt_conv.convertSimpleType(context.expression.stringFunction(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext));
		// Faster to do it this way
		XmlAtomicValue atom = context.expression.atom(toolKit.<XiNode, XmlAtomicValue>emptyFocus(context.typedContext), context.dargs, context.typedContext);
		try {
			if (atom != null) {
				return (Calendar) xsd2java_dt_conv.convertSimpleType(atom.castAsDateTime());
			}
		} catch (XmlAtomicValueCastException e) {
			e.printStackTrace();
		} catch (ConversionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    public static String getStringValue(ItemIterable<XiNode,XmlAtomicValue> result) throws Exception {
    	if (result == null) {
    		return null;
    	}
    	String[] vals = getStringArrayValue(result);
    	if (vals != null && vals.length > 0) {
    		if (vals.length == 1) {
    			return vals[0];
    		}
			StringBuilder b = new StringBuilder();
			for (String string : vals) {
				b.append(string);
			}
    		return b.toString();
    	}
    	return null;
    }
    
    public static String[] getStringArrayValue(ItemIterable<XiNode,XmlAtomicValue> result) throws Exception {
    	List<String> values = new ArrayList<>();
    	if (result.isAtomIterable()) {
    		Iterable<XmlAtomicValue> iterable = result.atomIterable();
    		Iterator<XmlAtomicValue> iter = iterable.iterator();
    		try {
        		if (iter == null || !iter.hasNext()) {
        			return null;
        		}
			} catch (NullPointerException e) {
				// if the iterable is empty, hasNext can throw a NPE - don't see a way to avoid it
				return null;
			}
    		XmlAtomicValue first = iter.next();
    		
    		if(iter.hasNext()) {
    			values.add(first.getTypedValue().toString());
    			while(iter.hasNext()) {
    				XmlAtomicValue next = iter.next();
    				values.add(next.getTypedValue().toString());
    			}
    			return (String[]) values.toArray(new String[values.size()]);
    		}
    		else {
    			return new String[] { first.getTypedValue().toString() };
    		}
    	} else if (result.isNodeIterable()) {
    		Iterable<XiNode> iterable = result.nodeIterable();
    		Iterator<XiNode> iter = iterable.iterator();
//    		try {
//        		if (iter == null || !iter.hasNext()) {
//        			return null;
//        		}
//			} catch (NullPointerException e) {
//				// if the iterable is empty, hasNext can throw a NPE - don't see a way to avoid it
//				return null;
//			}
    		XiNode first = iter.next();
    		
    		if (first instanceof Text) {
    			values.add(first.getStringValue());
    		} else {
    			values.add(XPathHelper.serializeNode(first));
    		}
    		if(iter.hasNext()) {
    			while(iter.hasNext()) {
    				XiNode next = iter.next();
    				if (next instanceof Text) {
    	    			values.add(next.getStringValue());
    	    		} else {
    	    			values.add(XPathHelper.serializeNode(next));
    	    		}
    			}
    		}
			return (String[]) values.toArray(new String[values.size()]);
    	}
    	return null;
    }
    
    private ConcurrentHashMap<String, ExprContext> getQueryCache() {
    	if (queryCache.get() == null) {
    		queryCache.set(new ConcurrentHashMap<String,XPath2Helper.ExprContext>());
    	}
    	return queryCache.get();
    }
    
	private ExprContext prepareExpression(final String varName, final String xpathExpression,
			final String prefixes, TypedContext<XiNode, XmlAtomicValue> typedContext) {
		
		String key;

        if(prefixes == null) {
            key = xpathExpression;
        }
        else {
            key = xpathExpression + prefixes;
        }
        
		if (getQueryCache().containsKey(key)) {
			return getQueryCache().get(key);
		}
		
		String expr;
        if(xpathExpression.indexOf("$var") == -1) {
            expr = "$" + varName + xpathExpression;
        }
        else {
            expr = xpathExpression;
        }
        List<String> varNames = new ArrayList<>();
        varNames.add(varName);
        
		TypesBridge metaBridge = typedContext.getTypesBridge();

		final StaticContextArgs sargs = toolKit.newStaticContextArgs();
		sargs.bindVariableType(new QName(varName), BuiltInSchema.SINGLETON.ANY_COMPLEX_TYPE);
		
		// need to override the function group as some functions (format-date, format-dateTime) are not in the default XQueryFunctionGroup
        final FunctionGroup functions = new XSLTCompositeFunctionGroup(typedContext.getProcessingContext());
        sargs.setFunctionImpls(functions.getNamespaceURI(), functions);
        sargs.setFunctionSigns(functions.getNamespaceURI(), functions);
        
		sargs.setEmulation(Emulation.LEGACY);

		if (prefixes != null) {
			StringTokenizer token = new StringTokenizer(prefixes, ",");
			while (token.hasMoreTokens()) {
				String str = token.nextToken();
				int st = str.indexOf('=');
				String pfx = str.substring(0, st);
				String uri = str.substring(st + 1);
				sargs.getInScopeNamespaces().declarePrefix(pfx, uri);
			}
		}

		addCustomFunctions(sargs);
		
        // Compile
//		anything to do with metaBridge?  
//		metaBridge.nodeType()?
//				change function sign for OOTB?
		ExprResult prepared = toolKit.prepare(expr, metaBridge.emptyType(), sargs, typedContext);
		Expr result = prepared.getExpr();
		ExprContext exprContext = new ExprContext(result, varNames);
		getQueryCache().putIfAbsent(key, exprContext);
		return exprContext;
	}
    
	private ExprContext prepareExpression(final String xpathExpression,
			final VariableList varList, TypedContext<XiNode, XmlAtomicValue> typedContext) {
		
		if (getQueryCache().containsKey(xpathExpression)) {
			return getQueryCache().get(xpathExpression);
		}
		TypesBridge metaBridge = typedContext.getTypesBridge();

		final StaticContextArgs sargs = toolKit.newStaticContextArgs();
		// need to override the function group as some functions (format-date, format-dateTime) are not in the default XQueryFunctionGroup
        final FunctionGroup functions = new XSLTCompositeFunctionGroup(typedContext.getProcessingContext());
        sargs.setFunctionImpls(functions.getNamespaceURI(), functions);
        sargs.setFunctionSigns(functions.getNamespaceURI(), functions);

		sargs.setEmulation(Emulation.LEGACY);
		XiNode xpathNode = null;
		try {
			xpathNode = XSTemplateSerializer.deSerializeXPathString(xpathExpression);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		String xpath = XSTemplateSerializer.getXPathExpressionAsStringValue(xpathNode);
		List vars = XSTemplateSerializer.getVariablesinXPath(xpathNode);
        Iterator itr = vars.iterator();
        while (itr.hasNext()) {
        	String variable = (String) itr.next();
        	Variable var = varList.getVariable(variable);
            if (var != null) {
                SequenceType type = getType(var, typedContext);
                sargs.bindVariableType(new QName(var.getName()), type);
            }
        }

        HashMap nsPrefixesinXPath = XSTemplateSerializer.getNSPrefixesinXPath(xpathNode);
        Iterator nsItr = nsPrefixesinXPath.keySet().iterator();

        while (nsItr.hasNext()) {
            String pfx = (String) nsItr.next();
            String uri = (String) nsPrefixesinXPath.get(pfx);
            sargs.getInScopeNamespaces().declarePrefix(pfx, uri);
        }

		addCustomFunctions(sargs);
		
        // Compile
//		anything to do with metaBridge?  
//		metaBridge.nodeType()?
//				change function sign for OOTB?
		ExprResult prepared = toolKit.prepare(xpath, metaBridge.emptyType(), sargs, typedContext);
		Expr result = prepared.getExpr();
		ExprContext exprContext = new ExprContext(result, vars);
		getQueryCache().putIfAbsent(xpathExpression, exprContext);
		return exprContext;
	}

	public void addCustomFunctions(final StaticContextArgs sargs) {
		final BECustomFunctionImpls beCustomFunctionImpls = new BECustomFunctionImpls();
		sargs.setFunctionSigns(BE_CUSTOM_NAMESPACE, beCustomFunctionImpls);
		sargs.setFunctionImpls(BE_CUSTOM_NAMESPACE, beCustomFunctionImpls);
		
		final CustomXPathFunctionGroup tibGroup = new CustomXPathFunctionGroup(TIB_CUSTOM_NAMESPACE, new OOTBCustomFunctionsImpl());
		sargs.setFunctionSigns(TIB_CUSTOM_NAMESPACE, tibGroup);
		sargs.setFunctionImpls(TIB_CUSTOM_NAMESPACE, tibGroup);
		
		try {
			Iterator<String> catalogNames = FunctionsCatalog.getINSTANCE().catalogNames();
			while (catalogNames.hasNext()) {
				String catName = (String) catalogNames.next();
				FunctionsCategory catalog = FunctionsCatalog.getINSTANCE().getCatalog(catName);
				catalog.accept(new FunctionsCatalogVisitor() {
					
					@Override
					public boolean visit(FunctionsCategory category) {
						ExpandedName name = category.getName();
						String str = name.namespaceURI == null ? name.localName : name.namespaceURI+"."+name.localName;
						str = str.replaceAll("\\.", "-");
						sargs.setFunctionSigns(BE_CUSTOM_NAMESPACE+str, beCustomFunctionImpls);
						sargs.setFunctionImpls(BE_CUSTOM_NAMESPACE+str, beCustomFunctionImpls);
						return true;
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    private void makeDynamicArgs(List vars, VariableList varlist, DynamicContextArgs<XiNode,XmlAtomicValue> dargs, TypedContext<XiNode,XmlAtomicValue> typedContext) throws Exception {

        GlobalVariables gVars = RuleServiceProviderManager.getInstance().getDefaultProvider().getGlobalVariables();

        XiNode gvarNode = gVars.toXiNode();
        final ItemIterable<XiNode, XmlAtomicValue> gvarItemIt = new ItemIterable.NodeIterable<XiNode, XmlAtomicValue>(new UnaryIterable<XiNode>(gvarNode));
        dargs.bindVariableType(new QName(gvarNode.getName().localName), BuiltInSchema.SINGLETON.STRING);
        dargs.bindVariableValue(new QName(gvarNode.getName().localName), gvarItemIt);
		
        Iterator itr = vars.iterator();
        while (itr.hasNext()) {
        	String varName = (String) itr.next();
        	Variable o = varlist.getVariable(varName);
            if (o != null) {
            	XiNode node = XiNodeBuilder.makeXiNode(o);
        		final ItemIterable<XiNode, XmlAtomicValue> varItemIt = new ItemIterable.NodeIterable<XiNode, XmlAtomicValue>(new UnaryIterable<XiNode>(node));
                dargs.bindVariableValue(new QName(o.getName()), varItemIt);
//                SequenceType type = getType(o, typedContext);
//                dargs.bindVariableType(new QName(o.getName()), type);
            }
		}
    }
	
    private SequenceType getType(Variable o, TypedContext<XiNode,XmlAtomicValue> typedContext) {
		Object object = o.getObject();
		if (object instanceof Entity) {
			Entity e = (Entity) object;
			String ns = e.getType();
			String localPart = ns.substring(ns.lastIndexOf('/')+1);
			ComplexType complexType = typedContext.getSchema().getComponentProvider().getComplexType(new QName(ns, localPart));
			if (complexType != null) {
				return complexType;
			}
		}
		if (object instanceof String) {
			return BuiltInSchema.SINGLETON.STRING;
		}
		if (object instanceof Integer) {
			return BuiltInSchema.SINGLETON.INT;
		}
		return BuiltInSchema.SINGLETON.ANY_ATOMIC_TYPE;
	}

}
