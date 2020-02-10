package com.tibco.cep.ws.Export.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EventRegistrationParser
{

    public static class Entry {

        public final String destination;
        public final String eventUri;
        public final boolean input;
        public final String operation;
        public final String portType;
        public final String service;
        public final String soapAction;
        public final String preprocessor;
        
        public Entry(
                String destination,
                String eventUri,
                boolean input,
                String operation,
                String portType,
                String service,
                String soapAction,
                String preprocessor)
        {
            this.destination = destination;
            this.eventUri = eventUri;
            this.input = input;
            this.operation = operation;
            this.portType = portType;
            this.service = service;
            this.soapAction = soapAction;
            this.preprocessor = preprocessor;
        }
    }
    
    
    private static final Pattern RF_PATTERN = Pattern.compile("\\s*.*\\srulefunction.*\\{"
        + "(?:.*\\s)?attribute\\s*\\{[^\\{]*\\}"
        + "(?:.*\\s)?scope\\s*\\{[^\\{]*\\}"
        + "(?:.*\\s)?body\\s*\\{(.*)\\}"
        + "\\s*\\}\\s*",
        Pattern.DOTALL);
        
    private static final int RF_PATTERN_BODY_GROUP = 1;
    
    private static final Pattern BODY_PATTERN = Pattern.compile(
            "//[ \\t]*(input|output)[ \\t]*for[ \\t]*\\[(.*)\\][ \\t]*@[ \\t]*\\[(.*)\\]\\s*?$"
            + "(?:\\s*service\\s*=\\s*\"([^\"]*)\"\\s*;)?"
            + "\\s*eventUri\\s*=\\s*\"([^\"]*)\"\\s*;"
            + "\\s*destination\\s*=\\s*\"([^\"]*)\"\\s*;"
            + "\\s*soapAction\\s*=\\s*\"([^\"]*)\"\\s*;"
            + "(?:\\s*preprocessor\\s*=\\s*\"([^\"]*)\"\\s*;)?"            
            + "(\\s*SOAP\\.registerEventUri\\(eventUri,\\s*destination,\\s*service,\\s*soapAction(?:,\\s*preprocessor)?\\);)?"
            + "(\\s*SOAP\\.registerEventUri\\(eventUri,\\s*destination,\\s*\"\",\\s*soapAction(?:,\\s*preprocessor)?\\);)?",
            Pattern.MULTILINE);
    private static final int BODY_PATTERN_IO_GROUP = 1;
    private static final int BODY_PATTERN_OPERATION_GROUP = 2;
    private static final int BODY_PATTERN_PORT_TYPE_GROUP = 3;
    private static final int BODY_PATTERN_SERVICE_GROUP = 4;
    private static final int BODY_PATTERN_EVENT_URI_GROUP = 5;
    private static final int BODY_PATTERN_DESTINATION_GROUP = 6;
    private static final int BODY_PATTERN_SOAP_ACTION_GROUP = 7;
    private static final int BODY_PATTERN_PREPROCESSOR_GROUP = 8;
    private static final int BODY_PATTERN_REGISTER_WITH_SERVICE_GROUP = 9;
    private static final int BODY_PATTERN_REGISTER_WITHOUT_SERVICE_GROUP = 10;
    
    
    public List<Entry> parseRuleFunction(
            String functionSource)
    {
        final Matcher m = RF_PATTERN.matcher(functionSource);
        if (m.matches()) {
            return this.parseBody(m.group(RF_PATTERN_BODY_GROUP));
        } else {        
            return new ArrayList<Entry>();
        }
    }
    
 
    public List<Entry> parseBody(
            String bodySource)
    {
       final List<Entry> results = new ArrayList<Entry>();
       
       if (null != bodySource) {
           final Matcher m = BODY_PATTERN.matcher(bodySource);
           while (m.find()) {
                final String destination = m.group(BODY_PATTERN_DESTINATION_GROUP);
                final String eventUri = m.group(BODY_PATTERN_EVENT_URI_GROUP);
                final boolean io = "input".equals(m.group(BODY_PATTERN_IO_GROUP));
                final String operation = m.group(BODY_PATTERN_OPERATION_GROUP);                
                final String portType = m.group(BODY_PATTERN_PORT_TYPE_GROUP);                
                final String service = m.group(BODY_PATTERN_SERVICE_GROUP);
                final String soapAction = m.group(BODY_PATTERN_SOAP_ACTION_GROUP);              
                final String preprocessor = m.group(BODY_PATTERN_PREPROCESSOR_GROUP);              
            
                if (null != m.group(BODY_PATTERN_REGISTER_WITH_SERVICE_GROUP)) {
                    results.add(new Entry(destination, eventUri, io, operation, portType, service, soapAction, preprocessor));
                }                  
                if (null != m.group(BODY_PATTERN_REGISTER_WITHOUT_SERVICE_GROUP)) {
                    results.add(new Entry(destination, eventUri, io, operation, portType, "", soapAction, preprocessor));
                }
           }
       }
        
       return results;
    }
    
 
//    public static void main(
//            String[] args)
//    {
//        List<Entry> l = new EventRegistrationParser().parseRuleFunction("/**\n"
//        +" * @description Generated during WSDL import. This function registers specific event types for use in some SOAP deserializers.\n"
//        +" */\n"
//        +"void rulefunction StockQuoteService.Transport.RegisterSoapEventUris {\n"
//        +"\tattribute {\n"
//        +"\t\tvalidity = ACTION;\n"
//        +"\t}\n"
//        +"\tscope {\n"
//        +"\t\t\n"
//        +"\t}\n"
//        +"\tbody {\n"
//        +"\t\tString eventUri, destination, service, soapAction;\n"
//        +"\t\t\n"
//        +"\t\t// input for GetLastTradePrice @ StockQuoteService\n"
//        +"\t\tservice = \"StockQuoteService\";\n"
//        +"\t\teventUri = \"/StockQuoteService/StockQuotePortType/Events/GetLastTradePriceInput\";\n"
//        +"\t\tdestination = \"/stockquote/http___example_com_GetLastTradePrice\";\n"
//        +"\t\tsoapAction = \"http://example.com/GetLastTradePrice\";\n"
//        +"\t\tSOAP.registerEventUri(eventUri, destination, service, soapAction);\n"
//        +"\t\tSOAP.registerEventUri(eventUri, destination, \"\",      soapAction);\n"
//        +"\t\t\n"
//        +"\t\t// input for GetLastTradePrice @ StockQuoteService\n"
//        +"\t\tservice = \"StockQuoteService\";\n"
//        +"\t\teventUri = \"/StockQuoteService/StockQuotePortType/Events/GetLastTradePriceInput\";\n"
//        +"\t\tdestination = \"/Channels/SoapJms/myQueue\";\n"
//        +"\t\tsoapAction = \"http://example.com/GetLastTradePrice\";\n"
//        +"\t\tSOAP.registerEventUri(eventUri, destination, service, soapAction);\n"
//        +"\t\tSOAP.registerEventUri(eventUri, destination, \"\",      soapAction);\n"
//        +"\t\t\n"
//        +"\t\t// output for GetLastTradePrice @ StockQuoteService\n"
//        +"\t\tservice = \"StockQuoteService\";\n"
//        +"\t\teventUri = \"/StockQuoteService/StockQuotePortType/Events/GetLastTradePriceOutput\";\n"
//        +"\t\tdestination = \"/Channels/SoapJms/interested\";\n"
//        +"\t\tsoapAction = \"http://example.com/GetLastTradePrice\";\n"
//        +"\t\tSOAP.registerEventUri(eventUri, destination, service, soapAction);\n"
//        +"\t\tSOAP.registerEventUri(eventUri, destination, \"\",      soapAction);\n"
//        +"\t\t\n"
//        +"\t\t// input for GetLastTradePrice @ StockQuoteService\n"
//        +"\t\tservice = \"StockQuoteService\";\n"
//        +"\t\teventUri = \"/StockQuoteService/StockQuotePortType/Events/GetLastTradePriceInput\";\n"
//        +"\t\tdestination = \"/Channels/SoapJms/otherQueue\";\n"
//        +"\t\tsoapAction = \"http://example.com/GetLastTradePrice\";\n"
//        +"\t\tSOAP.registerEventUri(eventUri, destination, service, soapAction);\n"
//        +"\t\tSOAP.registerEventUri(eventUri, destination, \"\",      soapAction);\n"
//        +"\t\t\n"
//        +"\t\t// output for GetLastTradePrice @ StockQuoteService\n"
//        +"\t\tservice = \"StockQuoteService\";\n"
//        +"\t\teventUri = \"/StockQuoteService/StockQuotePortType/Events/GetLastTradePriceOutput\";\n"
//        +"\t\tdestination = \"/Channels/SoapJms/other\";\n"
//        +"\t\tsoapAction = \"http://example.com/GetLastTradePrice\";\n"
//        +"\t\tSOAP.registerEventUri(eventUri, destination, service, soapAction);\n"
//        +"\t\tSOAP.registerEventUri(eventUri, destination, \"\",      soapAction);\n"
//        +"\t}\n"
//        +"}");
//                
//        l.size();
//    }
    
}
