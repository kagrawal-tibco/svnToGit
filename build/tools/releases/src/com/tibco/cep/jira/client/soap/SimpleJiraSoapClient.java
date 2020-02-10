package com.tibco.cep.jira.client.soap;

import com.tibco.cep.jira.client.JiraClient;
import com.tibco.cep.jira.model.JiraCustomFieldName;
import com.tibco.cep.jira.model.JiraIssue;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.*;

/**
 * User: nprade
 * Date: 7/20/11
 * Time: 2:13 PM
 */
public class SimpleJiraSoapClient implements JiraClient {


    private final URL serverUrl;
    private final JiraIssueSoapFactory jiraIssueSoapFactory = new JiraIssueSoapFactory();


    public SimpleJiraSoapClient(
            String serverUrl)
            throws MalformedURLException {

        this(new URL(serverUrl));
    }


    public SimpleJiraSoapClient(
            URL serverUrl) {

        this.serverUrl = serverUrl;
    }


    private Map<String, XiNode> buildRefToMultiRefNodeMap(
            XiNode node) {

        final Map<String, XiNode> refToMultiRefNode = new HashMap<String, XiNode>();

        for (final Iterator i = XiChild.getIterator(node, XNames.MULTI_REF); i.hasNext(); ) {
            final XiNode multiRefNode = (XiNode) i.next();
            refToMultiRefNode.put(multiRefNode.getAttributeStringValue(XNames.ID), multiRefNode);
        }

        return refToMultiRefNode;
    }


    private XiNode doRequest(
            String action,
            String requestString,
            Object[] parameters)
            throws IOException, SAXException {

        final XiNode xmlResponse;

        final HttpURLConnection connection = (HttpURLConnection) this.serverUrl.openConnection();
        try {
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("SOAPAction", this.serverUrl.toString() + "#" + action);
            connection.setRequestProperty("Content-Type", "text/xml; charset=\"utf-8\"");

            final String msg = String.format(requestString, parameters);

            connection.setRequestProperty("Content-Length", String.valueOf(msg.length()));

            final Writer writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(msg);
            writer.flush();
            writer.close();

            final InputStream is = connection.getInputStream();
            try {
                xmlResponse = XiParserFactory.newInstance().parse(new InputSource(is));
            } finally {
                is.close();
            }
        } finally {
            connection.disconnect();
        }

        return xmlResponse;
    }


    @Override
    @SuppressWarnings({"UnusedDeclaration"})
    public JiraIssue getIssue(
            String authenticationToken,
            String key)
            throws Exception {

        final String requestString = "<soapenv:Envelope" +
                " xmlns:soap='http://soap.rpc.jira.atlassian.com'" +
                " xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'" +
                " xmlns:xsd='http://www.w3.org/2001/XMLSchema' " +
                " xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <soap:getIssue soapenv:encodingStyle='http://schemas.xmlsoap.org/soap/encoding/'>\n" +
                "         <in0 xsi:type='xsd:string'>%1$s</in0>\n" +
                "         <in1 xsi:type='xsd:string'>%2$s</in1>\n" +
                "      </soap:getIssue>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        final Object[] parameters = new Object[]{authenticationToken, key};

        XiNode node = this.doRequest("getIssue", requestString, parameters);

        node = XiChild.getChild(node, XNames.Soap.Envelope.ENVELOPE);
        node = XiChild.getChild(node, XNames.Soap.Envelope.BODY);

        final Map<String, XiNode> refToMultiRefNode = this.buildRefToMultiRefNodeMap(node);

        node = XiChild.getChild(node, XNames.Jira.GET_ISSUE_RESPONSE);
        node = XiChild.getChild(node, XNames.GET_ISSUE_RETURN);

        final String ref = node.getAttributeStringValue(XNames.HREF).substring(1);

        return this.jiraIssueSoapFactory.make(ref, refToMultiRefNode);
    }


    @Override
    public List<JiraIssue> getIssuesFromJqlSearch(
            String authenticationToken,
            String jql,
            int limit)
            throws Exception {

        final String requestString = "<soapenv:Envelope" +
                " xmlns:soap='http://soap.rpc.jira.atlassian.com'" +
                " xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'" +
                " xmlns:xsd='http://www.w3.org/2001/XMLSchema' " +
                " xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <soap:getIssuesFromJqlSearch soapenv:encodingStyle='http://schemas.xmlsoap.org/soap/encoding/'>\n" +
                "         <in0 xsi:type='xsd:string'>%1$s</in0>\n" +
                "         <in1 xsi:type='xsd:string'>%2$s</in1>\n" +
                "         <in2 xsi:type='xsd:int'>%3$d</in2>\n" +
                "      </soap:getIssuesFromJqlSearch>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        final Object[] parameters = new Object[]{authenticationToken, jql, limit};

        XiNode node = this.doRequest("getIssuesFromJqlSearch", requestString, parameters);

        node = XiChild.getChild(node, XNames.Soap.Envelope.ENVELOPE);
        node = XiChild.getChild(node, XNames.Soap.Envelope.BODY);

        final Map<String, XiNode> refToMultiRefNode = this.buildRefToMultiRefNodeMap(node);

        node = XiChild.getChild(node, XNames.Jira.GET_ISSUES_FROM_JQL_SEARCH_RESPONSE);
        node = XiChild.getChild(node, XNames.GET_ISSUES_FROM_JQL_SEARCH_RETURN);

        final List<JiraIssue> issues = new ArrayList<JiraIssue>();
        for (final Iterator i = XiChild.getIterator(node, XNames.GET_ISSUES_FROM_JQL_SEARCH_RETURN); i.hasNext(); ) {
            node = (XiNode) i.next();
            final String ref = node.getAttributeStringValue(XNames.HREF).substring(1);
            issues.add(this.jiraIssueSoapFactory.make(ref, refToMultiRefNode));
        }

        return issues;
    }


    @Override
    public String login(
            PasswordAuthentication passwordAuthentication)
            throws Exception {

        final String requestString = "<soapenv:Envelope" +
                " xmlns:soap='http://soap.rpc.jira.atlassian.com'" +
                " xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'" +
                " xmlns:xsd='http://www.w3.org/2001/XMLSchema' " +
                " xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <soap:login soapenv:encodingStyle='http://schemas.xmlsoap.org/soap/encoding/'>\n" +
                "         <in0 xsi:type='xsd:string'>%1$s</in0>\n" +
                "         <in1 xsi:type='xsd:string'>%2$s</in1>\n" +
                "      </soap:login>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        final Object[] parameters = new Object[]{
                passwordAuthentication.getUserName(),
                new String(passwordAuthentication.getPassword())
        };

        XiNode node = this.doRequest("login", requestString, parameters);

        node = XiChild.getChild(node, XNames.Soap.Envelope.ENVELOPE);
        node = XiChild.getChild(node, XNames.Soap.Envelope.BODY);
        node = XiChild.getChild(node, XNames.Jira.LOGIN_RESPONSE);
        node = XiChild.getChild(node, XNames.LOGIN_RETURN);

        return node.getStringValue();
    }


    @Override
    public boolean logout(
            String authenticationToken)
            throws Exception {

        final String requestString = "<soapenv:Envelope" +
                " xmlns:soap='http://soap.rpc.jira.atlassian.com'" +
                " xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'" +
                " xmlns:xsd='http://www.w3.org/2001/XMLSchema' " +
                " xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <soap:logout soapenv:encodingStyle='http://schemas.xmlsoap.org/soap/encoding/'>\n" +
                "         <in0 xsi:type='xsd:string'>%1$s</in0>\n" +
                "      </soap:logout>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        final Object[] parameters = new Object[]{authenticationToken};

        XiNode node = this.doRequest("logout", requestString, parameters);

        node = XiChild.getChild(node, XNames.Soap.Envelope.ENVELOPE);
        node = XiChild.getChild(node, XNames.Soap.Envelope.BODY);
        node = XiChild.getChild(node, XNames.Jira.LOGOUT_RESPONSE);
        node = XiChild.getChild(node, XNames.LOGOUT_RETURN);

        return Boolean.parseBoolean(node.getStringValue());
    }


    public static void main(
            String[] args)
            throws Exception {


        final String serverUrl = args[0];
        final String userName = args[1];
        final String query = args[3];
        final int limit = (args.length > 4) ? Integer.parseInt(args[4]) : Integer.MAX_VALUE;


        System.err.println("Connecting to: " + serverUrl);
        final JiraClient jiraClient = new SimpleJiraSoapClient(serverUrl);

        System.err.println("Logging in as: " + userName);
        final String token = jiraClient.login(new PasswordAuthentication(userName, args[2].toCharArray()));

        System.err.println("Getting at most " + limit + " issues for JQL: " + query);
        final List<JiraIssue> issues = jiraClient.getIssuesFromJqlSearch(token, query, limit);

        System.err.println("Found " + issues.size() + " issues.");

        final SortedSet<String> releaseNotes = new TreeSet<String>(); // todo Comparator
        for (final JiraIssue issue : issues) {
            final String releaseNote = issue.getCustomFieldValue(JiraCustomFieldName.RELEASE_NOTE);
            if ((null != releaseNote) && !releaseNote.isEmpty()) {
                releaseNotes.add(releaseNote.trim());
            }
        }

        boolean first = true;
        for (final String releaseNote : releaseNotes) {
            if (first) {
                first = false;
            } else {
                System.out.println();
                System.out.println();
            }
//            System.out.println("[" + issue.getKey() + "]");
            System.out.println(releaseNote);
        }

        System.err.println("Logging out.");
        if (jiraClient.logout(token)) {
            System.err.println("Logged out.");
        } else {
            System.err.println("Failed to log out!");
        }

    }

}
