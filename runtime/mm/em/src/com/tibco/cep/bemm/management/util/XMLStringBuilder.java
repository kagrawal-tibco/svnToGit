package com.tibco.cep.bemm.management.util;

import java.text.MessageFormat;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Aug 3, 2010
 * Time: 4:45:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class XMLStringBuilder {

    private static final String[] XML_SYMBOLS_TO_ESCAPE = {"&","<",">","\"","'"};
    private static final String[] XML_ESCAPE_SEQUENCES = {"&amp;","&lt;","&gt;","&quot;","&apos;"};

    /** Returns an XML string containing one XML element with the specified name whose value is specified by elemValue,
     * and with the attributes specified in the attrNames/attrValues arrays. The XML element value is the emtpy String.
     * @param elemName name of the XML element
     * @param attrNames Array with the attribute names
     * @param attrValues Array with the attribute values
     * @return XML String
     * @throws MalformedXMLAttributeException if the the arrays with the attribute names and values have different sizes
     */
    public static String createElement(String elemName, String[] attrNames, String[] attrValues, String elemValue) {
        final String openPattern = "<{0}";
        final String closePattern = "</{0}>";
        final String attrPattern = " {0}=\"{1}\"";

        StringBuilder builder = new StringBuilder();

        builder.append(MessageFormat.format(openPattern, elemName));
        
        if (attrNames != null && attrValues != null) {
            if (attrNames.length != attrValues.length) {   //for now skips attributes if names and values count don't match 
                try {
                    throw new MalformedXMLAttributeException("The arrays with the attribute names and attribute values have different sizes");
                } catch (MalformedXMLAttributeException e) {     //TODO. Handle this exception
                    e.printStackTrace();
                }
            } else {
                for (int i=0; i<attrNames.length; i++) {
                    builder.append(MessageFormat.format( attrPattern, escape(attrNames[i]), escape(attrValues[i]) ) );
                }
            }
        }
        
        builder.append(">");

        if (elemValue!=null && elemValue.trim().length() != 0 )
            builder.append(elemValue);

        builder.append(MessageFormat.format(closePattern, elemName));

        return builder.toString();
    }

    private static String escape(String str){
        String escapedStr = str;
        for (int i=0; i< XML_SYMBOLS_TO_ESCAPE.length; i++) {
            escapedStr = escapedStr.replaceAll(XML_SYMBOLS_TO_ESCAPE[i], XML_ESCAPE_SEQUENCES[i]);
        }
        return escapedStr;
    }

    /** Returns an XML string containing one XML element whose value is specified by elemValue, and with no attributes.
     * @param elemName name of the XML element
     * @param elemValue value of this XML element
     * @return XML String
     */
    public static String createElement(String elemName, String elemValue) {
        return createElement(elemName, null, null, elemValue);
    }

    /** Returns an XML string containing an empty XML element with no attributes.
     * @param elemName name of the XML element
     * @return XML String
     */
    public static String createElement(String elemName) {
        return createElement(elemName, null, null, null);
    }

    /** Returns an XML string containing one XML element with the specified name, with the attributes specified
     * in the name / value arrays. The XML element value is the emtpy String.
     * @param elemName name of the XML element
     * @param attrNames Array with the attribute names
     * @param attrValues Array with the attribute values
     * @return XML String
     * @throws MalformedXMLAttributeException if the the arrays with the attribute names and values have different sizes
     */
    public static String createElement(String elemName, String[] attrNames, String[] attrValues) {
        return createElement(elemName, attrNames, attrValues, null);
    }
    
    /**
     * Adds the children specified in childrenXMLStr array to the end of the XML string specified by parentXmlObjStr.
     * @param parentXmlObjStr XML string to which children are to be added
     * @param childrenXMLStr Array containing the XML strings to be added
     * @return XML string containing the original string plus the specified children
     * */
    public static String addChildren(String parentXmlObjStr, String[] childrenXMLStr) {
        int index = parentXmlObjStr.lastIndexOf("</");
        String closeNode = parentXmlObjStr.substring(index, parentXmlObjStr.length());
        
        StringBuilder builder = new StringBuilder(parentXmlObjStr.substring(0, index));
        for (String child:childrenXMLStr) {
            builder.append(child);
        }
        builder.append(closeNode);

        return builder.toString();
    }

    /**
     * Adds the child specified in the childrenXMLStr array to the end of the XML string specified by parentXmlObjStr.
     * @param parentXmlObjStr XML string to which children are to be added
     * @param childXMLStr Array containing the XML strings to be added
     * @return XML string containing the original string plus the specified children
     * */
    public static String addChild(String parentXmlObjStr, String childXMLStr) {
        return addChildren(parentXmlObjStr, new String[]{childXMLStr});
    }

//    //test
//    public static void main(String[] args) {
////      String xmlObjStr = createElement("operation", new String[]{"name","param"}, new String[]{"getChannels","33"});
//        String xmlObjStr = createElement("operation", new String[]{"name","param"}, new String[]{"getChannels","33"},"operation value");
//        String xmlDataStr = createElement("data",null);
//        String withChild = XMLStringBuilder.addChild(xmlObjStr, xmlDataStr);
//
//        System.err.println(xmlObjStr);
//        System.err.println(xmlDataStr);
//        System.err.println(withChild);
//    }

}
