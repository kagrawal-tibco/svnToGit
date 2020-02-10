package com.tibco.cep.bemm.management.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Aug 4, 2010
 * Time: 2:28:31 PM
 * To change this template use File | Settings | File Templates.
 */

/** XML sent by BEMM to the UI should have the pattern:<p/>
    &lt;operation name=&quot;getResults&quot;&gt;<br/>
        &lt;data&gt;<br/>
            &lt;table&gt;<br/>
                &lt;row&gt;<br/>
                    &lt;column name=&quot;row&quot; type=&quot;java.lang.Integer&quot; value=&quot;0&quot;/&gt;<br/>
                    &lt;column name=&quot;channelURI&quot; type=&quot;java.lang.String&quot; value=&quot;/EventsAndChannels/Channel&quot;/&gt;<br/>
                &lt;/row&gt;<br/>
                &lt;row&gt;<br/>
                    &lt;column name=&quot;destinationURI&quot; type=&quot;java.lang.String&quot; value=&quot;/EventsAndChannels/Channel/QueryStopperDest&quot;/&gt;<br/>
                    &lt;column name=&quot;lastStatsIntervalReceivedEventsRate&quot; type=&quot;java.lang.Double&quot; value=&quot;0&quot;/&gt;<br/>
                &lt;/row&gt;<br/>
            &lt;/table&gt;<br/>
            &lt;value type=&quot;java.lang.String&quot;&gt;<br/>
                This is value's text<br/>
            &lt;/value&gt;<br/>
            &lt;value type=&quot;java.lang.Double&quot;&gt;<br/>
                3.2<br/>
            &lt;/value&gt;<br/>
        &lt;/data&gt;<br/>
        &lt;error&gt;<br/>
            &lt;errorcode&gt;<br/>
            &lt;/errorcode&gt;<br/>
            &lt;errormessage&gt;<br/>
                ERROR Message<br/>
            &lt;/errormessage&gt;<br/>
	    &lt;/error&gt;<br/>
        &lt;success&gt;<br/>
            True/False<br/>
        &lt;/success&gt;<br/>
        &lt;info&gt;<br/>
            This is info's text<br/>
        &lt;/info&gt;<br/>
        &lt;info&gt;<br/>
            This is MORE info's text<br/>
        &lt;/info&gt;<br/>
        &lt;warning&gt;<br/>
            This is warning's text<br/>
        &lt;/warning&gt;<br/>
    &lt;/operation&gt;<br/>
    */
public class XMLHandler {

    private static final String ERROR_TOKEN = "$";

    public static final String OPERATION = "operation";

    public static final String SUCCESS = "success";
    public static final String INFO = "info";
    public static final String WARNING = "warning";
    public static final String ERROR = "error";

    public static final String DATA = "data";
    public static final String VALUE = "value";
    public static final String TABLE = "table";

    public static final String OPER_NAME_ATTR = "name";
    public static final String ERROR_CODE = "errorcode";
    public static final String ERROR_MSG = "errormessage";

    public static final String CODE_ILEGAL_TYPE = "111";

    //methods exposed in Studio as catalog functions
    public static String infoXML(String methodFQName, String msg){
        return resultXML(methodFQName, INFO, msg);
    }

    public static String warningXML(String methodFQName, String msg){
        return resultXML(methodFQName, WARNING, msg);
    }

    public static String successXML(String methodFQName, String msg){
        return resultXML(methodFQName, SUCCESS, msg);
    }

    public static String errorXML(String methodFQName, String msg, String code) {     //todo validate if code is numeric
         code = code==null?"":code;
         msg = msg==null?"":msg;

         return resultXML(methodFQName, ERROR, msg + ERROR_TOKEN + code);
     }

    public static String singleValueXML(Object singleValue) {
        return XMLStringBuilder.createElement(VALUE, new String[]{"type"},
                new String[]{singleValue.getClass().getName()},
                singleValue.toString());
    }//singleValueXML

    // ++++++++++++++++ Internal use methods ++++++++++++++++++

    public static String resultXML(String methodFQName, String type, String xmlStr) {
        return resultXML(methodFQName, type, (Object)xmlStr);
    }

    /**
     * Puts the operation result in a String with the adequate XML format
     * */
    public static String resultXML(String methodFQName, String type, Object content) {
        String resultXML = XMLStringBuilder.createElement(OPERATION, new String[]{OPER_NAME_ATTR},
                                                          new String[]{methodFQName});

        if (type.equals(TABLE) || type.equals(VALUE)) {
            String dataElem = XMLStringBuilder.createElement(DATA);
            if (type.equals(TABLE))
                return XMLStringBuilder.addChild(resultXML, XMLStringBuilder.addChild(dataElem, tableXML(content)));
            else
                return XMLStringBuilder.addChild(resultXML, XMLStringBuilder.addChild(dataElem, singleValueXML(content)));
        } else if (type.equals(INFO) || type.equals(WARNING) || type.equals(SUCCESS)) {
            return XMLStringBuilder.addChild(resultXML, XMLStringBuilder.createElement(type, content.toString()));
        } else if (type.equals(ERROR)) {
            String msg="";
            String code="";
            if (content != null && content instanceof String) {
                String[] errSplit = ((String)content).split(ERROR_TOKEN, -1);
                msg = errSplit[0];
                code = errSplit[errSplit.length-1];
            }
            return XMLStringBuilder.addChild(resultXML, errXML(msg,code));
        } else { //Unexpected type
            return XMLStringBuilder.addChild(resultXML, errXML("Unexpected type \""+ type +"\" found while building " +
                                                                "response XML",CODE_ILEGAL_TYPE));
        }
    }

    private static String errXML(String msg, String code) {
        String errXML = XMLStringBuilder.createElement(ERROR);
        errXML=XMLStringBuilder.addChild(errXML, XMLStringBuilder.createElement(ERROR_CODE, code));
        errXML=XMLStringBuilder.addChild(errXML, XMLStringBuilder.createElement(ERROR_MSG, msg));
        return errXML;
    }

    private static String tableXML(Object tabularData) {
        StringBuilder stringBuilder = new StringBuilder();
        String rowPattern = "<column name=\"{0}\" type=\"{1}\" value=\"{2}\"/>";

        stringBuilder.append("<table>");
        for(Iterator iter= ((TabularDataSupport) tabularData).values().iterator(); iter.hasNext();) {
            stringBuilder.append("<row>");
            CompositeDataSupport currentRow = (CompositeDataSupport) iter.next();
            Set<String> columnKeysSet = currentRow.getCompositeType().keySet();
            ArrayList<String> columnKeysArray = new ArrayList(columnKeysSet);
            //sort array in ascending order for consistency
            Collections.sort(columnKeysArray);                      //TODO. clean this up

            for (String key : columnKeysArray) {
                stringBuilder.append( MessageFormat.format(rowPattern,
                        key.replaceAll("\"",""),                //remove " from attribute value             //name
                        currentRow.getCompositeType().getType(key).getTypeName().replaceAll("\"",""),       //type
                        currentRow.get(key).toString().replaceAll("\"","") ) );                             //value
           }
           stringBuilder.append("</row>");
         }
         stringBuilder.append("</table>");

         return stringBuilder.toString();
    } //tableXML

//    public static void main (String args[]) {
//        String errXml, infoXML, warnXML, sucXML, valXML, tabXML;
//        final String METH_NAME = "METH_NAME";
//        errXml = errorXML(METH_NAME,"Error Message", "007");
//        infoXML = infoXML(METH_NAME,"Info Message");
//        warnXML = warningXML(METH_NAME, "Warn Message");
//        sucXML = successXML(METH_NAME, "TRUE");
////        valXML = resultXML(METH_NAME, VALUE, "Value");
////        tabXML = resultXML(METH_NAME, TABLE, "Table");
//        System.err.println("END");
//
//    } 

}
