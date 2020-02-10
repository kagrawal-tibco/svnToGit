package com.tibco.cep.jira.client;

import com.tibco.cep.jira.model.JiraIssue;

import java.net.PasswordAuthentication;
import java.util.List;

/**
 * User: nprade
 * Date: 7/21/11
 * Time: 3:57 PM
 */
public interface JiraClient {

    @SuppressWarnings({"UnusedDeclaration"})
    JiraIssue getIssue(
            String authenticationToken,
            String key)
            throws Exception;

    List<JiraIssue> getIssuesFromJqlSearch(
            String authenticationToken,
            String jql,
            int limit)
            throws Exception;

    String login(
            PasswordAuthentication passwordAuthentication)
            throws Exception;

    boolean logout(
            String authenticationToken)
            throws Exception;

}
