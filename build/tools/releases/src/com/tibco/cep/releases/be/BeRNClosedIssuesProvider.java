package com.tibco.cep.releases.be;

import com.tibco.cep.jira.client.JiraClient;
import com.tibco.cep.jira.client.soap.SimpleJiraSoapClient;
import com.tibco.cep.jira.model.JiraCustomFieldName;
import com.tibco.cep.jira.model.JiraIssue;
import com.tibco.cep.releases.notes.RNClosedIssue;
import com.tibco.cep.releases.notes.RNClosedIssueFactory;

import java.net.PasswordAuthentication;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * User: nprade
 * Date: 7/22/11
 * Time: 3:21 PM
 */
public class BeRNClosedIssuesProvider {


    private static final String DEFAULT_SERVER_URL = "http://jira.tibco.com/rpc/soap/jirasoapservice-v2";
    private static final int MAX_LOGOUT_ATTEMPTS = 10;
    private static final Pattern VERSION_PATTERN = Pattern.compile("(?:\\w|\\s|-|\\.)+");

    private final RNClosedIssueFactory rnClosedIssueFactory = new RNClosedIssueFactory();

    private PasswordAuthentication authentication;
    private final String serverUrl;


    @SuppressWarnings({"UnusedDeclaration"})
    public BeRNClosedIssuesProvider(
            PasswordAuthentication authentication) {

        this(DEFAULT_SERVER_URL, authentication);
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public BeRNClosedIssuesProvider(
            String serverUrl,
            PasswordAuthentication authentication) {

        this.authentication = authentication;
        this.serverUrl = serverUrl;
    }


    public SortedSet<RNClosedIssue> getClosedIssues(
            String beVersion)
            throws Exception {

            return this.getIssues(this.makeQuery(beVersion, true));
    }


    public SortedSet<RNClosedIssue> getFixedIssues(
            String beVersion)
            throws Exception {

            return this.getIssues(this.makeQuery(beVersion, false));
    }


    public SortedSet<RNClosedIssue> getIssues(
            String query)
            throws Exception {

        final SortedSet<RNClosedIssue> releaseNotes = new TreeSet<RNClosedIssue>();

        final JiraClient jiraClient = new SimpleJiraSoapClient(this.serverUrl);
        final String token = jiraClient.login(authentication);
        try {
            final List<JiraIssue> issues = jiraClient.getIssuesFromJqlSearch(token, query, Integer.MAX_VALUE);

            for (final JiraIssue issue : issues) {
                final String releaseNoteFullText = issue.getCustomFieldValue(JiraCustomFieldName.RELEASE_NOTE);
                if ((null != releaseNoteFullText) && !releaseNoteFullText.isEmpty()) {
                    releaseNotes.add(this.rnClosedIssueFactory.make(issue.getKey(), releaseNoteFullText));
                }
            }

        } finally {
            for (int i = 0; (i < MAX_LOGOUT_ATTEMPTS) && !jiraClient.logout(token); i++) {
            }
        }

        return releaseNotes;
    }


    public static void main(
            String[] args)
            throws Exception {


        final String serverUrl = args[0];
        final String userName = args[1];
        final String version = args[3];

        System.err.println("Getting fixed issues for release notes of BE version '" + version
                + "' for user '" + userName + "' at <" + serverUrl + ">.");

        final BeRNClosedIssuesProvider provider = new BeRNClosedIssuesProvider(
                serverUrl,
                new PasswordAuthentication(userName, args[2].toCharArray()));

        final SortedSet<RNClosedIssue> closedIssues = provider.getFixedIssues(version);

        System.err.println("Found " + closedIssues.size() + " issues.");

        boolean first = true;
        while (!closedIssues.isEmpty()) {
            if (first) {
                first = false;
            } else {
                System.out.println();
            }
            final RNClosedIssue closedIssue = closedIssues.last();
            closedIssues.remove(closedIssue);
            System.out.println(closedIssue.toString());
        }

    }


    private String makeQuery(
            String beVersion,
            boolean closedOnly) {

        if (!VERSION_PATTERN.matcher(beVersion).matches()) {
            throw new IllegalArgumentException();
        }

        return new StringBuilder("project=BE AND fixVersion=\"")
                .append(beVersion)
                .append("\" AND issuetype=\"Release Notes Subtask\" AND resolution=\"Fixed\"")
                .append(closedOnly ? " AND Status=\"Closed\"" : "")
                .toString();
    }


}
