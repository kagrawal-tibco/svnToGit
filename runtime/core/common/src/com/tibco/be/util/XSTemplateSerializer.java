package com.tibco.be.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.InputSource;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 17, 2004
 * Time: 11:52:00 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class provides convienence method to serialize an xslt template with function parameters
 * types.
 * It also provides methods to get the template and the function parameters. The function parameters
 * are position dependent
 *
 * The serialization is done as follows
 * <xslt://{{param1, param2, ...}}[actualtemplate]
 */

public class XSTemplateSerializer {
    public static final String ARG_BEGIN_DELIMITER = "{{";
    public static final String COERCION_SEPARATOR  = ",^^";
    public static final String ARG_END_DELIMITER = "}}";
    public static String XSLTSCHEME="xslt://";
    public static int XSLTSCHEME_LENGTH=XSLTSCHEME.length();
    public static String XSLTLITERAL="\"\"";

    public static String XPATHSCHEME="xpath://";
    public static int XPATHSCHEME_LENGTH=XPATHSCHEME.length();
    public static XiFactory factory = XiSupport.getXiFactory();
    //public static XiParser parser = XiParserFactory.newInstance();

    /**
     * The following definitions have been taken W3C XML Definition 1.1
     * Please take Nicolas Prade's help on regularExpression.
     */
    static String nm_start = "[:A-Z_a-z\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u02FF\\u0370-\\u037D\\u037F-\\u1FFF" +
            "\\u200C-\\u200D\\u2070-\\u218F" +
            "\\u2C00-\\u2FEF\\u3001-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFFD]";
    // NP: \U100000-\UEFFFF not working in JVM 1.4, fixed in 1.5 (bug 4900747). In 1.5: "\\uD800\\uDC00-\\uDB7F\\uDFFF".
    static String nm_char = nm_start + "|[\\-\\.0-9\\u00B7\\u0300-\\u036F\\u203F-\\u2040]";
    static String name =  nm_start +"(?:"+ nm_char + ")*";
    static String nm_tokens = "(\\$"+name +")";
    static Pattern nm_pattern = Pattern.compile(nm_tokens);

    public static String serialize(String template, ArrayList fnParams, ArrayList coerionStrList) {
        StringBuffer sb = new StringBuffer();
        sb.append(XSLTSCHEME);
        sb.append(ARG_BEGIN_DELIMITER);
        if (fnParams != null) {
            int size = fnParams.size();
            for (int i=0; i<size;i++) {
                sb.append(fnParams.get(i));
                if (i < size -1)
                    sb.append(',');
            }
        }
        if(coerionStrList != null && coerionStrList.size() > 0){
            sb.append(XSTemplateSerializer.COERCION_SEPARATOR);
            for (int i=0; i < coerionStrList.size(); i++){
                String coe = (String) coerionStrList.get(i);
                sb.append(coe);
                if(i < coerionStrList.size()-1)
                    sb.append('^');
            }
        }
        sb.append(ARG_END_DELIMITER);

        if (template !=null) {
            String escapedTemplate = BEStringUtilities.escape(template);
            sb.append(escapedTemplate);
        }
        return sb.toString();

    }

    public static String deSerialize(String serializedForm, ArrayList recvParms, ArrayList coerionStrList) {
        if (serializedForm.startsWith(XSLTSCHEME)) {

            String str = serializedForm.substring(XSLTSCHEME_LENGTH);
            int endParmIdx = str.indexOf(ARG_END_DELIMITER);
            int templateIdx = 0;
            if (endParmIdx != -1) {
                String fnAndCoerionsParams = str.substring(ARG_BEGIN_DELIMITER.length(), endParmIdx);
                int coerionStartIdx = fnAndCoerionsParams.indexOf(COERCION_SEPARATOR);
                if(coerionStartIdx != -1) {
                    String fnParms = fnAndCoerionsParams.substring(0, coerionStartIdx);
                    //get the function params
                    String []parms = fnParms.split(",");
                    if (parms != null) {
                        for (int i=0; i< parms.length; i++) {
                            recvParms.add(parms[i]);
                        }
                    }
                    if(coerionStrList != null) {
                        String coerionParams = fnAndCoerionsParams.substring(coerionStartIdx+COERCION_SEPARATOR.length());
                        StringTokenizer tokenizer = new StringTokenizer(coerionParams, "^");
                        while(tokenizer.hasMoreTokens()) {
                            String coerionStr = tokenizer.nextToken();
                            coerionStrList.add(coerionStr);
                        }
                    }
                    templateIdx = endParmIdx+ARG_END_DELIMITER.length();
                }
                else {
                    String fnParms = str.substring(ARG_BEGIN_DELIMITER.length(), endParmIdx);
                    String []parms = fnParms.split(",");
                    if (parms != null) {
                        for (int i=0; i< parms.length; i++) {
                            recvParms.add(parms[i]);
                        }
                    }
                    templateIdx = endParmIdx+ARG_END_DELIMITER.length();
                }
            }
            String template = str.substring(templateIdx);
            template = BEStringUtilities.unescape(template);
//            System.out.println(template);
            return template;

        }
        else {
            try {
                if ((serializedForm == null) || (serializedForm.isEmpty())) {
                    return null;
                }
                XiNode doc = XiSupport.getParser().parse(new InputSource(new StringReader(serializedForm)));
                XiNode node = doc.getFirstChild();

                Iterator r = node.getNamespaces();
                while (r.hasNext()) {
                    XiNode n = (XiNode)r.next();
                    String ns = n.getStringValue();
                    if (ns.startsWith("http://www.w3.org")) continue;
                    recvParms.add(ns);
                }
                return serializedForm;

            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        //return null;

    }


    public static boolean isXSLTString(String s) {
        if ((s.startsWith(XSLTLITERAL)) || (s.startsWith(XSLTSCHEME))) {
            return true;
        }
        return false;
    }

    public static boolean isXPathString(String s) {
        if ((s.startsWith(XSLTLITERAL)) || (s.startsWith(XPATHSCHEME))) {
            return true;
        }
        return false;
    }

//    public static String deSerializeXPathString(String s) {
//        if (isXPathString(s)) {
//            String expr = s.substring(XPATHSCHEME_LENGTH);
//            expr = BEStringUtilities.unescape(expr);
//            return expr;
//        }
//        return null;
//    }

    public static XiNode deSerializeXPathString(String s) throws Exception {

        if (isXPathString(s)) {
            String expr = s.substring(XPATHSCHEME_LENGTH);
            expr = BEStringUtilities.unescape(expr);
            //String unescaped = BEStringUtilities.unescape(s);
            if (expr.length() == 0) return null;
            XiNode doc = XiSupport.getParser().parse(new InputSource(new StringReader(expr)));
            XiNode xpath = XiChild.getChild(doc, XPATH_NM);
            if (xpath == null) throw new Exception("Invalid XPath Expression string provided");

            return xpath;
        }
        return null;

    }

    public static ExpandedName XPATH_NM = ExpandedName.makeName("xpath");
    public static ExpandedName XPATH_EXPR = ExpandedName.makeName("expr");
    public static ExpandedName NAMESPACES = ExpandedName.makeName("namespaces");
    public static ExpandedName NAMESPACE = ExpandedName.makeName("namespace");
    public static ExpandedName VARIABLES = ExpandedName.makeName("variables");
    public static ExpandedName VARIABLE = ExpandedName.makeName("variable");
    public static ExpandedName PREFIX = ExpandedName.makeName("pfx");
    public static ExpandedName URI = ExpandedName.makeName("URI");



    public static String serializeXPathString(String expr, HashMap pfxs, List variables) {

        if (isXPathString(expr)) return expr;

        XiNode doc = factory.createDocument();
        XiNode xpath = doc.appendElement(XPATH_NM);
        XiNode xpr = xpath.appendElement(XPATH_EXPR);
        xpr.setStringValue(expr);

        XiNode nss = xpath.appendElement(NAMESPACES);
        Iterator itr = pfxs.keySet().iterator();
        while (itr.hasNext()) {
            String key = (String) itr.next();
            String namespace = (String)pfxs.get(key);

            XiNode ns = nss.appendElement(NAMESPACE);
            ns.setAttributeStringValue(URI, namespace);
            ns.setAttributeStringValue(PREFIX, key);

        }

        XiNode vars = xpath.appendElement(VARIABLES);
        Iterator itr1 = variables.iterator();
        while (itr1.hasNext()) {
            String varName = (String) itr1.next();
            XiNode var = vars.appendElement(VARIABLE);
            var.setStringValue(varName);
        }

        StringWriter sw = new StringWriter();
        XiSerializer.serialize(doc, sw);


        StringBuffer sb = new StringBuffer();
        sb.append(XPATHSCHEME);
        if (sw !=null) {
            String xmlexpr  = BEStringUtilities.escape(sw.toString());
            sb.append(xmlexpr);
        }
        return sb.toString();

    }

    public static ArrayList getReceivingParms(String serializedForm) {

        ArrayList recvParms = new ArrayList();
        if (serializedForm.startsWith(XSLTSCHEME)) {

            String str = serializedForm.substring(XSLTSCHEME_LENGTH);
            int endParmIdx = str.indexOf(ARG_END_DELIMITER);
            int templateIdx = 0;
            if (endParmIdx != -1) {
                String fnParms = str.substring(2, endParmIdx);
                String []parms = fnParms.split(",");
                if (parms != null) {
                    for (int i=0; i< parms.length; i++) {
                        recvParms.add(parms[i]);
                    }
                }
            }
        }
        return recvParms;


    }


    public static String getXPathExpressionAsStringValue(String s) {
        try {
            XiNode xpath = deSerializeXPathString(s);
            return getXPathExpressionAsStringValue(xpath);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static String getXPathExpressionAsStringValue(XiNode xpath) {
        if (xpath == null) return "";
        XiNode expr = XiChild.getChild(xpath, XPATH_EXPR);
        if (expr == null) return "";
        return expr.getStringValue();
    }

    public static HashMap getNSPrefixesinXPath(XiNode xpath) {

        HashMap map = new HashMap();
        if (xpath == null) return map;
        XiNode nss = XiChild.getChild(xpath, NAMESPACES);

        if (nss == null) return map;
        Iterator itr = XiChild.getIterator(nss, NAMESPACE);
        while (itr.hasNext()) {
            XiNode ns = (XiNode) itr.next();
            String uri = ns.getAttributeStringValue(URI);
            String pfx = ns.getAttributeStringValue(PREFIX);
            map.put(pfx, uri);
        }

        return map;

    }

    public static List getVariablesinXPath(XiNode xpath) {
        ArrayList list = new ArrayList();
        if (xpath == null) return list;

        XiNode nss = XiChild.getChild(xpath, VARIABLES);

        if (nss == null) return list;
        Iterator itr = XiChild.getIterator(nss, VARIABLE);
        while (itr.hasNext()) {
            XiNode ns = (XiNode) itr.next();
            list.add(ns.getStringValue());
        }

        return list;

    }

    /**
     * Author - Nicolas Prade
     * @param expr String containing Xpath Expression such as
     * "concat($event/abc, $gv/def)"
     * $event and $gv are variable place holder for XiNode.
     *
     * @return A List of $names in the xpath.
     */
    public static List searchForVariableNamesinExpression(String expr) {

        ArrayList vars = new ArrayList();
        Matcher m = nm_pattern.matcher(expr);
        while (m.find()) {
            String varname = m.group();
            vars.add(varname.substring(1));
        }

        return vars;

    }


    public static List getVariablesinXPath(String image) {
        List list = new ArrayList();
        try {
            XiNode xpath = deSerializeXPathString(image);
            list = getVariablesinXPath(xpath);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
