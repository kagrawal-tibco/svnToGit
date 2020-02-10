package com.tibco.be.model.functions.impl;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.InputSource;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.model.functions.PredicateWithXSLT;
import com.tibco.xml.conversion.helpers.ConversionUtilities;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.flavor.XSDLConstants;
import com.tibco.xml.schema.helpers.SmRootElement;
import com.tibco.xml.schema.impl.DefaultSchema;
import com.tibco.xml.schema.parse.SmParseSupport;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 2, 2004
 * Time: 7:20:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class JavaStaticFunctionWithXSLT extends JavaStaticFunction implements PredicateWithXSLT{

    SmElement m_inputElement;
    SmElement m_inSignature;
    MutableSchema schema;
    String mappertype;

    /**
     *
     * @param functionName
     * @param method
     */
    JavaStaticFunctionWithXSLT(ExpandedName functionName, Method method) {
        super(functionName, method);
    }

    /**
     *
     * @return
     */
    public SmElement getInputType() {
        try {
            return getFunctionInputElement();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public SmElement getOutputType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getMapperType() {
        return mappertype;
    }

    public boolean isXPathFunction() {
        return XPATH_TYPE.equalsIgnoreCase(mappertype);
    }

    public boolean isXsltFunction() {
        return XSLT_TYPE.equalsIgnoreCase(mappertype);
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public SmElement getFunctionInputElement() throws Exception {
        return m_inputElement;
        /**
        try {
            if (m_inSignature != null) return m_inSignature;

            MutableType ipType = MutableSupport.createType(schema, functionName.getLocalName());
            Class[] params = method.getParameterTypes();
            String[] parmNames = new String[params.length];
            String[] argNames = args.split(",");

            for (int i=0; i<params.length; i++) {
                parmNames[i] = (i < argNames.length?argNames[i]:"param-" + i);
            }


            for (int i=0; i<params.length; i++) {
                Class c = params[i];
                addLocalElement(parmNames[i], c, ipType);
            }

            m_inSignature = MutableSupport.createElement(schema, functionName.getLocalName(), ipType);

            return m_inSignature;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        **/

    }

    private void addLocalElement (String name, Class c, MutableType type) throws Exception {
        SmType paramType = ConversionUtilities.getSimpleType(null, null, c);
        paramType = (paramType == null ? XSDL.ANY_TYPE: paramType);
        MutableSupport.addLocalElement(type, name, paramType, 0, c.isArray()?Integer.MAX_VALUE : 1);
    }

    public static JavaStaticFunctionWithXSLT loadJavaStaticFunctionWithXSLT(XiFactory factory, MutableSchema schema, ExpandedName functionName, XiNode node, Method method) throws Exception{

        JavaStaticFunctionWithXSLT function = new JavaStaticFunctionWithXSLT(functionName, method);

        XiNode descNode = XiChild.getChild(node, ExpandedName.makeName("desc"));
        XiNode asyncNode = XiChild.getChild(node, ExpandedName.makeName("async"));
        XiNode mapperNode = XiChild.getChild(node, ExpandedName.makeName("mapper"));
        XiNode tsNode = XiChild.getChild(node, ExpandedName.makeName("timesenstive"));
        XiNode modNode = XiChild.getChild(node, ExpandedName.makeName("modify"));
        XiNode hlpNode = XiChild.getChild(node, ExpandedName.makeName("helpurl"));
        XiNode argsNode = XiChild.getChild(node, ExpandedName.makeName("args"));
        XiNode isValidInActionOnlyNode=XiChild.getChild(node,ExpandedName.makeName("isActionOnly"));
        XiNode isDeprecatedNode=XiChild.getChild(node,ExpandedName.makeName("isDeprecated"));
        XiNode isValidInQueryNode=XiChild.getChild(node,ExpandedName.makeName("isValidInQuery"));
        XiNode isValidInBUINode=XiChild.getChild(node,ExpandedName.makeName("isValidInBUI"));


        function.mappertype = XiChild.getChild(mapperNode, ExpandedName.makeName("type")).getStringValue();
        function.isAsync = (asyncNode == null) ? false : Boolean.valueOf(asyncNode.getStringValue()).booleanValue();
        //this.modify = (modNode == null) ? false : new Boolean(modNode.getStringValue()).booleanValue();
        function.isTimeSensitive=(tsNode == null) ? false : Boolean.valueOf(tsNode.getStringValue()).booleanValue();
        function.m_inputElement = parseSmElement(factory, method.getDeclaringClass(), mapperNode);
        function.args = argsNode.getStringValue();
        function.modify = (modNode == null) ? false : Boolean.valueOf(modNode.getStringValue()).booleanValue();
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

        function.deprecated = (isDeprecatedNode == null) ? false: Boolean.valueOf(isDeprecatedNode.getStringValue()).booleanValue();
//        function.isValidInActionOnly = (isValidInActionOnlyNode == null) ? false: Boolean.valueOf(isValidInActionOnlyNode.getStringValue()).booleanValue();
//        function.isValidInQuery = (isValidInQueryNode==null) ? false: Boolean.valueOf(isValidInQueryNode.getStringValue()).booleanValue();
//        function.isValidInBUI = (isValidInBUINode==null) ? false: Boolean.valueOf(isValidInBUINode.getStringValue()).booleanValue();
        function.schema=schema;
        return function;

    }

    static SmElement parseSmElement(XiFactory factory, Class clz, XiNode mapperNode) throws Exception {


        XiNode inputElement = XiChild.getChild(mapperNode, ExpandedName.makeName("inputElement"));
        XiNode schemaNode = XiChild.getChild(inputElement, ExpandedName.makeName(XSDLConstants.NAMESPACE,"schema"));
        if (schemaNode == null) return null;
        XiNode copyschemaNode = schemaNode.copy();
        XiNode attr = copyschemaNode.getAttribute(ExpandedName.makeName("targetNamespace"));
        if (attr == null) {
            copyschemaNode.setAttributeStringValue(ExpandedName.makeName("targetNamespace"), clz.getName());
        }

        XiNode doc = factory.createDocument();
        doc.appendChild(copyschemaNode);

        String xsdschema = XiSerializer.serialize(doc);
//        System.out.println(xsdschema);

        InputSource is = new InputSource(new StringReader(xsdschema));
        is.setSystemId(copyschemaNode.getSystemId());

        DefaultSchema defaultSchema = null;

        try {
            defaultSchema = (DefaultSchema)SmParseSupport.parseSchema(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SmElement el = SmRootElement.getBestRoot(defaultSchema);
        return el;
    }

    public boolean requiresAssert() {
        return "Instance.createInstance".equals(getName().getNamespaceURI() + "." + getName().getLocalName());
    }
}
