package com.tibco.cep.jira.client.soap;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * User: nprade
 * Date: 7/20/11
 * Time: 5:02 PM
 */
class XNames {

    public static final ExpandedName ASSIGNEE = ExpandedName.makeName("assignee");
    public static final ExpandedName AFFECTED_VERSION = ExpandedName.makeName("affectedVersion");
    public static final ExpandedName COMPONENT = ExpandedName.makeName("component");
    public static final ExpandedName CREATED = ExpandedName.makeName("created");
    public static final ExpandedName CREATED_DATE = ExpandedName.makeName("createdDate");
    public static final ExpandedName CUSTOM_FIELD_ID = ExpandedName.makeName("customfieldId");
    public static final ExpandedName CUSTOM_FIELD_VALUES = ExpandedName.makeName("customFieldValues");
    public static final ExpandedName DESCRIPTION = ExpandedName.makeName("description");
    public static final ExpandedName DUE = ExpandedName.makeName("due");
    public static final ExpandedName DUE_DATE = ExpandedName.makeName("dueDate");
    public static final ExpandedName ENVIRONMENT = ExpandedName.makeName("environment");
    public static final ExpandedName FIX_VERSION = ExpandedName.makeName("fixVersion");
    public static final ExpandedName GET_ISSUE_RETURN = ExpandedName.makeName("getIssueReturn");
    public static final ExpandedName GET_ISSUES_FROM_JQL_SEARCH_RETURN = ExpandedName.makeName("getIssuesFromJqlSearchReturn");
    public static final ExpandedName HREF = ExpandedName.makeName("href");
    public static final ExpandedName ID = ExpandedName.makeName("id");
    public static final ExpandedName ISSUE = ExpandedName.makeName("issue");
    public static final ExpandedName ISSUE_KEY = ExpandedName.makeName("issueKey");
    public static final ExpandedName ISSUE_TYPE = ExpandedName.makeName("issueType");
    public static final ExpandedName KEY = ExpandedName.makeName("key");
    public static final ExpandedName LOGIN_RETURN = ExpandedName.makeName("loginReturn");
    public static final ExpandedName LOGOUT_RETURN = ExpandedName.makeName("logoutReturn");
    public static final ExpandedName MULTI_REF = ExpandedName.makeName("multiRef");
    public static final ExpandedName ORIGINAL_ESTIMATE = ExpandedName.makeName("originalEstimate");
    public static final ExpandedName PRIORITY = ExpandedName.makeName("priority");
    public static final ExpandedName PROGRESS = ExpandedName.makeName("progress");
    public static final ExpandedName PROJECT = ExpandedName.makeName("project");
    public static final ExpandedName REMAINING_ESTIMATE = ExpandedName.makeName("remainingEstimate");
    public static final ExpandedName REPORTER = ExpandedName.makeName("reporter");
    public static final ExpandedName RESOLUTION = ExpandedName.makeName("resolution");
    public static final ExpandedName RESOLUTION_DATE = ExpandedName.makeName("resolutionDate");
    public static final ExpandedName RESOLVED = ExpandedName.makeName("resolved");
    public static final ExpandedName STATUS = ExpandedName.makeName("status");
    public static final ExpandedName SUBTASK = ExpandedName.makeName("subtask");
    public static final ExpandedName SUMMARY = ExpandedName.makeName("summary");
    public static final ExpandedName TIME_ESTIMATE = ExpandedName.makeName("timeEstimate");
    public static final ExpandedName TIME_ORIGINAL_ESTIMATE = ExpandedName.makeName("timeOriginalEstimate");
    public static final ExpandedName TIME_SPENT = ExpandedName.makeName("timeSpent");
    public static final ExpandedName TYPE = ExpandedName.makeName("type");
    public static final ExpandedName UPDATE = ExpandedName.makeName("update");
    public static final ExpandedName UPDATED_DATE = ExpandedName.makeName("updatedDate");
    public static final ExpandedName VALUES = ExpandedName.makeName("values");
    public static final ExpandedName VOTES = ExpandedName.makeName("votes");
    public static final ExpandedName WORK_RATIO = ExpandedName.makeName("workRatio");


    public static class Jira {

        public static final String NAME_SPACE = "http://soap.rpc.jira.atlassian.com";
        public static final ExpandedName GET_ISSUE_RESPONSE = ExpandedName.makeName(NAME_SPACE, "getIssueResponse");
        public static final ExpandedName GET_ISSUES_FROM_JQL_SEARCH_RESPONSE = ExpandedName.makeName(NAME_SPACE, "getIssuesFromJqlSearchResponse");
        public static final ExpandedName LOGIN_RESPONSE = ExpandedName.makeName(NAME_SPACE, "loginResponse");
        public static final ExpandedName LOGOUT_RESPONSE = ExpandedName.makeName(NAME_SPACE, "logoutResponse");

        private Jira() {
        }

    }


    public static class Soap {

        public static class Envelope {

            public static final String NAME_SPACE = "http://schemas.xmlsoap.org/soap/envelope/";

            public static final ExpandedName BODY = ExpandedName.makeName(NAME_SPACE, "Body");
            public static final ExpandedName ENVELOPE = ExpandedName.makeName(NAME_SPACE, "Envelope");


            private Envelope() {
            }
        }

        private Soap() {
        }

    }


    private XNames() {
    }


}

