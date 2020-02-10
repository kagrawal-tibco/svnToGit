package com.tibco.be.functions.xpath;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.be.model.functions.VariableImpl;
import com.tibco.be.model.functions.VariableList;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.rdf.XiNodeBuilder;
import com.tibco.be.util.BECustomFunctionQMResolver;
import com.tibco.be.util.BECustomFunctionResolver;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.DefaultNamespaceMapper;
import com.tibco.xml.NamespaceMapper;
import com.tibco.xml.XMLSDK;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlSequence;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.xquery.ExprFocus;
import com.tibco.xml.xquery.XQueryCompiler;
import com.tibco.xml.xquery.XQueryDynamicArgs;
import com.tibco.xml.xquery.XQueryExpr;
import com.tibco.xml.xquery.XQueryStaticArgs;
import com.tibco.xml.xquery.model.QmFunctionResolver;


public class XQueryExprContext {


    static XMLSDK sdk = new XMLSDK();
    static final ConcurrentHashMap queryCache = new ConcurrentHashMap();
    static XQueryCompiler compiler = sdk.createXQueryCompiler();
    //static XiFactory xiFactory = XiFactoryFactory.newInstance();


    public XQueryExpr expr;
    public List varNames;


    protected XQueryExprContext(XQueryExpr expr, List varNames) {
        this.expr = expr;
        this.varNames = varNames;
    }

    public static XQueryExprContext buildXQuery(String varName, String xpath, String prefixes) {

        XQueryExprContext qexprCtx = null;
        try {
            String key;

            if(prefixes == null) {
                key = xpath;
            }
            else {
                key = xpath + prefixes;
            }

            qexprCtx = (XQueryExprContext) queryCache.get(key);
            if (qexprCtx != null) return qexprCtx;

            String expr;
            if(xpath.indexOf("$var") == -1) {
                expr = "$" + varName + xpath;
            }
            else {
                expr = xpath;
            }
            List varNames = new ArrayList();
            varNames.add(varName);

            XQueryStaticArgs args = sdk.createXQueryStaticArgs();
            DefaultNamespaceMapper nsm = new DefaultNamespaceMapper();
            nsm.addXSDNamespace();
            nsm.addXSINamespace();
            if (prefixes != null) {
                StringTokenizer token = new StringTokenizer(prefixes, ",");
                while (token.hasMoreTokens()) {
                    String str = token.nextToken();
                    int st = str.indexOf('=');
                    String pref = str.substring(0, st);
                    String uri = str.substring(st + 1);
                    nsm.addNamespaceURI(pref.trim(), uri.trim());
                }
            }
            args.setPrefixToNamespaceResolver(nsm);
            XQueryExpr qexpr = compiler.compile(expr, args);
            qexprCtx = new XQueryExprContext(qexpr, varNames);

            queryCache.putIfAbsent(key, qexprCtx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return qexprCtx;

    }


    public static XQueryExprContext buildXQuery(String key, String rawString) {

        XQueryExprContext qexprCtx = null;
        try {

            qexprCtx = (XQueryExprContext) queryCache.get(key);
            if (qexprCtx != null) return qexprCtx;

            XiNode xpath = XSTemplateSerializer.deSerializeXPathString(rawString);
            String expr = XSTemplateSerializer.getXPathExpressionAsStringValue(xpath);
            List varNames = XSTemplateSerializer.getVariablesinXPath(xpath);

            XQueryStaticArgs args = sdk.createXQueryStaticArgs();
            NamespaceMapper resolver = getPrefixToNSResolver(xpath);
            args.setPrefixToNamespaceResolver(resolver);
        	if ("true".equalsIgnoreCase(System.getProperty("xpath.disable.customfunctions", "false"))) {
        		// don't add custom functions in xpath
        	} else {
        		QmFunctionResolver scopeResolver = new BECustomFunctionQMResolver(new BECustomFunctionResolver(FunctionsCatalog.getINSTANCE()));
        		args.setInScopeFunctions(scopeResolver);
        	}
            XQueryExpr qexpr = compiler.compile(expr, args);
            qexprCtx = new XQueryExprContext(qexpr, varNames);

            queryCache.putIfAbsent(key, qexprCtx);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return qexprCtx;

    }

    public XmlSequence execute(VariableList varlist) throws Exception {

    	XiNode item = XiSupport.getXiFactory().createDocument();
    	ExprFocus focus = sdk.createXQueryExprFocus(item, 1, 1);
    	XQueryDynamicArgs dctxt = makeDynamicArgs(varlist);
    	return expr.execute(focus, dctxt);
    }

    public XmlSequence execute(String varName, XiNode value) throws Exception {
        XiNode item = XiSupport.getXiFactory().createDocument();
        ExprFocus focus = sdk.createXQueryExprFocus(item, 1, 1);

        XQueryDynamicArgs dctxt = sdk.createXQueryDynamicArgs();
        XPathDefaultVarContext varprovider = new XPathDefaultVarContext();
        varprovider.setVariableValue(ExpandedName.makeName(varName), value);
        dctxt.setVariableProvider(varprovider);

        return expr.execute(focus, dctxt);
    }


    private XQueryDynamicArgs makeDynamicArgs(VariableList varlist) throws Exception {

        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        GlobalVariables gVars = session.getRuleServiceProvider().getGlobalVariables();
        XQueryDynamicArgs dctxt = sdk.createXQueryDynamicArgs();
        XPathDefaultVarContext varprovider = new XPathDefaultVarContext();

        XiNode gvarNode = gVars.toXiNode();
        varprovider.setVariableValue(gvarNode.getName(), gvarNode);

        Iterator itr = varNames.iterator();
        while (itr.hasNext()) {
            String varName = (String) itr.next();
            VariableImpl o = (VariableImpl) varlist.getVariable(varName);
            if (o != null) {
            	XiNode node = XiNodeBuilder.makeXiNode(o);
                varprovider.setVariableValue(node.getName(), node);
            }
        }
        dctxt.setVariableProvider(varprovider);

        return dctxt;
    }



    private static NamespaceMapper getPrefixToNSResolver(XiNode xpath) {
        DefaultNamespaceMapper nsm = new DefaultNamespaceMapper();
        nsm.addXSDNamespace();
        nsm.addXSINamespace();
        HashMap map = XSTemplateSerializer.getNSPrefixesinXPath(xpath);
        Iterator itr = map.keySet().iterator();

        while (itr.hasNext()) {
            String pfx = (String) itr.next();
            String uri = (String) map.get(pfx);
            nsm.addNamespaceURI(pfx, uri);
        }
        return nsm;
    }
}

