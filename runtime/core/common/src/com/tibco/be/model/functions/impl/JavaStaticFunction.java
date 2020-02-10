package com.tibco.be.model.functions.impl;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;
import static com.tibco.be.model.functions.FunctionDomain.BUI;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.model.functions.Predicate;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 2, 2004
 * Time: 4:53:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class JavaStaticFunction implements Predicate{

    protected static final ExpandedName XNAME_ARGS = ExpandedName.makeName("args");
    protected static final ExpandedName XNAME_IS_ASYNC = ExpandedName.makeName("async");
    protected static final ExpandedName XNAME_IS_DEPRECATED = ExpandedName.makeName("isDeprecated");
    protected static final ExpandedName XNAME_IS_TIME_SENSITIVE = ExpandedName.makeName("timesenstive");
    protected static final ExpandedName XNAME_IS_VALID_IN_ACTION_ONLY = ExpandedName.makeName("isActionOnly");
    protected static final ExpandedName XNAME_IS_VALID_IN_BUI = ExpandedName.makeName("isValidInBUI");
    protected static final ExpandedName XNAME_IS_VALID_IN_QUERY = ExpandedName.makeName("isValidInQuery");
    protected static final ExpandedName XNAME_MODIFY = ExpandedName.makeName("modify");
    protected static final ExpandedName XNAME_REEVALUATE = ExpandedName.makeName("reevaluate");

    private final static String SIGNATURE_TAG = "___SIGNATURE";


    ExpandedName functionName;
    Method method;
    protected boolean isTimeSensitive=false, isAsync=false;
    protected String template;
    protected String contentTemplate; // template without comment tags, used for content assist
    protected String tooltip=null;
    protected String signature=null;
    protected String signatureFormat=null;
    protected String args;
    protected boolean modify=false, deprecated=false;
//    protected boolean isValidInActionOnly=false;
//    protected boolean isValidInQuery =false;
//    protected boolean isValidInBUI = false;

    protected boolean reevaluate = false;
    protected FunctionDomain[] fndomains = new FunctionDomain[0];

    /**
     *
     * @param functionName
     * @param method
     */
    protected JavaStaticFunction(ExpandedName functionName, Method method) {
        this.functionName=functionName;
        this.method=method;
    }

    /**
     *
     * @return
     */
    public ExpandedName getName() {
        return functionName;
    }
    
    /**
     * @return
     */
    public Method getMethod() {
    	return this.method;
    }

    /**
     *
     * @return
     */
    public Class getReturnClass() {
        return method.getReturnType();
    }

    /**
     *
     * @return
     */
    public Class[] getThrownExceptions() {
        return method.getExceptionTypes();
    }

    /**
     *
     * @return
     */
    public Class[] getArguments() {
        return method.getParameterTypes();
    }

    /**
     *
     * @return
     */
    public String getToolTip() {
        return tooltip;
    }

    /**
     * @return
     */
    public String getSignature() {
        return signature;
    }
    
    /**
     * @return
     */
    public String getSignatureFormat() {
        return signatureFormat;
    }

   /**
     *
     * @return
     */
    public String code() {
        return method.getDeclaringClass().getName() + "." + method.getName();
    }

    public boolean isValidInDomain(FunctionDomain domain) {
    	return Arrays.asList(fndomains).contains(domain);
    }
    
    /**
     *
     * @return
     */
    public boolean isValidInCondition() {
    	return isValidInDomain(CONDITION);
    }

    /**
     *
     * @return
     */
    public boolean isValidInAction() {
    	return isValidInDomain(ACTION);
    }

    /**
     *
     * @return
     */
    public boolean isValidInBUI() {
    	return isValidInDomain(BUI);
    }


    /**
     *
     * @return
     */
    public boolean isValidInQuery() {
    	return isValidInDomain(QUERY);
    }


    /***
     *
     * @return
     */
    public boolean isTimeSensitive() {
        return isTimeSensitive;
    }

    /**
     *
     * @return
     */
    public boolean requiresAsync() {
        return isAsync;
    }

    public boolean reevaluate() {
        return reevaluate;
    }

    /**
     *
     * @return
     */
    public String getDocumentation() {
        return null;
    }

    /**
     *
     * @return
     */
    public String inline() {
        try {
            return JavaStaticFunctionsFactory.tooltip(this);
        } catch (Exception e) {
            return "foobar";
        }
    }

    /**
     *
     * @return
     */
    public String toString() {
        try {
            return functionName.getNamespaceURI() + "." + functionName.getLocalName();
            //return template();
        } catch (Exception e) {
            return "foobar";
        }
    }

    String printableClz(Class clz) {
    	String arraySuffix = "";
    	while(clz.isArray()) {
    		arraySuffix += "[]";
    		clz = clz.getComponentType();
    	}
    	String s1= clz.getName() + arraySuffix;
    	return s1.substring(s1.lastIndexOf('.')+1);
    }

    public String[] getArgumentNames() {
    	if (args == null) {
    		return null;
    	}
        String [] argNames = args.split(",");
        return argNames;
    }
    
    public String argumentTemplate() {
        if (contentTemplate == null) {
            StringBuffer sb = new StringBuffer();

            Class [] parmTypes = method.getParameterTypes();
            String [] argNames = args.split(",");

            for (int i=0; i < parmTypes.length; i++) {
                String argName;
                if (i >= argNames.length) {
                    argName="?";
                } else {
                    argName= argNames[i].trim();
                }

                Class parmClass = parmTypes[i];
                if (Calendar.class.equals(parmClass)) {
                	// special case, convert "Calendar" to "DateTime" for display to the user
                	sb.append("DateTime");
                	if (parmClass.isArray()) {
                		sb.append("[]");
                	}
                } else {
                	sb.append(printableClz(parmClass));
                }
                sb.append(' ');
                sb.append(argName);
                if (i != (parmTypes.length-1)) {
                    sb.append(", ");
                }
            }
            contentTemplate = sb.toString();
        }
        return contentTemplate;
    }
    
    /**
     *
     * @return
     */
    public String template() {
        if (template == null) {
            StringBuffer sb = new StringBuffer();

            sb.append(toString() + "(");

            Class [] parmTypes = method.getParameterTypes();
            String [] argNames = args.split(",");

            for (int i=0; i < parmTypes.length; i++) {
                String argName;
                if (i >= argNames.length) {
                    argName="?";
                } else {
                    argName= argNames[i];
                }

                sb.append("/*");
                sb.append(argName);
                sb.append(' ');
                sb.append(printableClz(parmTypes[i]));
                sb.append(" */");
                if (i != (parmTypes.length-1)) {
                    sb.append(",");
                }
            }
            sb.append(")");
            template = sb.toString();
        }
        return template;
    }

    /**
     *
     * @return
     */
    public String signature() {
        return JavaStaticFunctionsFactory.signature(this);
    }
    
    @Override
    public String signatureFormat() {
    	return JavaStaticFunctionsFactory.signatureFormat(this);
    }

    public boolean doesModify() {
        return modify;
    }

    /**
     *
     * @param functionName
     * @param node
     * @param method
     * @return
     * @throws Exception
     */
    public static JavaStaticFunction loadJavaStaticFunction(ExpandedName functionName, XiNode node, Method method) throws Exception{

        JavaStaticFunction function = new JavaStaticFunction(functionName, method);

        final XiNode argsNode = XiChild.getChild(node, XNAME_ARGS);
        function.args = (argsNode == null) ? "<args></args> element specified" : argsNode.getStringValue();

        function.isAsync = XiChild.getBoolean(node, XNAME_IS_ASYNC, false);
        function.isTimeSensitive = XiChild.getBoolean(node, XNAME_IS_TIME_SENSITIVE, false);
        function.modify = XiChild.getBoolean(node, XNAME_MODIFY, false);
        List<FunctionDomain> domainlist = new ArrayList<FunctionDomain>();
        domainlist.add(ACTION); 
        boolean isValidInActionOnly = XiChild.getBoolean(node, XNAME_IS_VALID_IN_ACTION_ONLY, false);
        if(isValidInActionOnly) {
        	// not valid in CONDITION section
        } else {
        	domainlist.add(CONDITION);
        }
        boolean isValidInBUI = XiChild.getBoolean(node, XNAME_IS_VALID_IN_BUI, false);
        if(isValidInBUI) domainlist.add(BUI);
        boolean isValidInQuery = XiChild.getBoolean(node, XNAME_IS_VALID_IN_QUERY, false);
        if(isValidInQuery) domainlist.add(QUERY);
        function.setFunctionDomains(domainlist.toArray(new FunctionDomain[domainlist.size()]));
        function.deprecated = XiChild.getBoolean(node, XNAME_IS_DEPRECATED, false);
        function.reevaluate = XiChild.getBoolean(node, XNAME_REEVALUATE, false);
        try {
            function.tooltip   = function.generateToolTipAndSignature(node);
        } catch (Exception e) { /* maybe tips are in javadocs */  }
        return function;
    }


    /*
    *   Recognises a format like following if available with function definition in
    *   functions.catalog file to create tooltip -------------------------------
            <tooltip>
                <synopsis>This is the synopsis</synopsis>
                <args>
                    <paramdesc name='pad' type='PropertyAtomDouble'>First arg</paramdesc>
                    <paramdesc name='no_of_seconds_ago' type='int'>Second arg</paramdesc>
                </args>
                <returns type='double'>yahoo</returns>
            </tooltip>
    * --------------------------------------------------------------------------
    */
    private String generateToolTipAndSignature(XiNode node) {
        XiNode tt = XiChild.getChild(node, ExpandedName.makeName("tooltip"));
        if (null == tt) return null; // no tooltip specified

        XiNode synopsisNode = XiChild.getChild(tt, ExpandedName.makeName("synopsis"));
        String synopsis = (synopsisNode!=null) ? synopsisNode.getStringValue() : "synopsis not specified";
        StringBuffer toolTip = new StringBuffer("<html>")
            .append("<table width=\"700\"><tr><th align=\"left\" width=\"15%\">Function:</th><td colspan=\"2\"><i>")
            .append(functionName.getLocalName()).append("</i></td></tr>")
            .append("<tr><th align=\"left\">Signature:</th><td colspan=\"2\"><i>")
            .append(SIGNATURE_TAG).append("</i></td></tr>")
            .append("<tr><th align=\"left\">Synopsis:</th><td colspan=\"2\"><i>")
            .append(synopsis).append("</i></td></tr></table>");

        XiNode argsNode = XiChild.getChild(tt, XNAME_ARGS);

        String paramlist="";
        String formatParamlist="";
        if (null != argsNode) {
            toolTip.append("<table width=\"700\"><tr><th align=\"left\">Parameters:</th></tr>")
                .append("<tr><th align=\"left\" width=\"15%\"><u>Name</u></th><th align=\"left\" width=\"15%\"><u>Type</u></th>")
                .append("<th align=\"left\" width=\"70%\"><u>Description</u></th>");
            int i=0;
            for (Iterator params = argsNode.getChildren(); params.hasNext(); i++) {
                XiNode pNode = (XiNode)params.next();
                if (pNode.getName() == null) continue;
                if (pNode.getName().getLocalName().equalsIgnoreCase("paramdesc")) {
                	if (!paramlist.equals("")) paramlist+=",";
                    if (!paramlist.equals("")) formatParamlist+=",";
                    XiNode nameNode = pNode.getAttribute(ExpandedName.makeName("name"));
                    XiNode typeNode = pNode.getAttribute(ExpandedName.makeName("type"));
                    String name = (nameNode!=null) ? nameNode.getStringValue() : "_NG";
                    String type = (typeNode!=null) ? typeNode.getStringValue() : "_NG";
                    String desc = (pNode.getStringValue()!=null) ? pNode.getStringValue() : "_NG";
                    toolTip.append("<tr><td><i>").append(name).append("</i></td><td>")
                           .append(type).append("</td><td>").append(desc).append("</td></tr>");
                    paramlist+=(type+" "+name);
                    formatParamlist+=(type+" {"+i+"}");
                }
            }
        } else { toolTip.append("<table><tr><th align=\"left\">No Parameters</th></tr>"); }

        XiNode returnValNode=XiChild.getChild(tt, ExpandedName.makeName("returns"));

        if (null != returnValNode) {
            XiNode typeNode = returnValNode.getAttribute(ExpandedName.makeName("type"));
            String type = (typeNode!=null) ? typeNode.getStringValue() : "type attribute not specified";
            String desc = returnValNode.getStringValue();
            toolTip.append("<tr><td align=\"left\" width=\"15%\"><b>Returns:</td><td width=\"15%\">")
                   .append(type).append("</td><td align=\"left\" width=\"70%\">").append(desc).append("</td></tr>");
            signature=type+" "+ functionName.getLocalName()+"("+paramlist+")";
            signatureFormat=type+" "+ functionName.getLocalName()+"("+formatParamlist+")";
        } else  {
        	signature="void "+ functionName.getLocalName()+"("+paramlist+")";
            signatureFormat="void "+ functionName.getLocalName()+"("+formatParamlist+")";
        }
        replaceTag (toolTip, SIGNATURE_TAG, signature);
        toolTip.append("</table></html>");
        return toolTip.toString();
    }

    private void replaceTag(StringBuffer sb, String tag, String val) {
        int idx = sb.indexOf(tag);
        if (idx>=0) sb.replace(idx, idx+tag.length(), val);
    }

    public boolean requiresAssert() { return false; }

    public boolean isVarargs() {
        return method.isVarArgs();
    }
    public boolean isVarargsCodegen() { return isVarargs(); }
    public Class getVarargsCodegenArgType() {
    	Class[] args = getArguments();
    	if(args != null && args.length > 0) return args[args.length - 1];
    	else return null;
    }

	@Override
	public FunctionDomain[] getFunctionDomains() {
		return fndomains;
	}
	
	public void setFunctionDomains(FunctionDomain[] domains) {
		FunctionDomain[] d = new FunctionDomain[domains.length];
		System.arraycopy(domains, 0, d, 0, domains.length);
		this.fndomains = d;
	}
    
    
}