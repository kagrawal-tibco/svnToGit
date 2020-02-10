package com.tibco.cep.jira.client.soap;

import com.tibco.cep.jira.model.JiraCustomFields;
import com.tibco.cep.jira.model.JiraIssue;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: nprade
 * Date: 7/20/11
 * Time: 4:17 PM
 */
public class JiraIssueSoapFactory {


    private final JiraCustomFieldsSoapFactory fieldsFactory = new JiraCustomFieldsSoapFactory();


    public JiraIssue make(
            String issueNodeRef,
            Map<String, XiNode> refToMultiRefNode) {

        XiNode node = refToMultiRefNode.get(issueNodeRef);

        final String assignee = XiChild.getString(node, XNames.ASSIGNEE);
        final String affectedVersion = XiChild.getString(node, XNames.AFFECTED_VERSION);
        final String component = XiChild.getString(node, XNames.COMPONENT);
        final String created = XiChild.getString(node, XNames.CREATED);
        final String createdDate = XiChild.getString(node, XNames.CREATED_DATE);
        final String description = XiChild.getString(node, XNames.DESCRIPTION);
        final String due = XiChild.getString(node, XNames.DUE);
        final String dueDate = XiChild.getString(node, XNames.DUE_DATE);
        final String environment = XiChild.getString(node, XNames.ENVIRONMENT);
        final String fixVersion = XiChild.getString(node, XNames.FIX_VERSION);
        final String id = XiChild.getString(node, XNames.ID);
        final String issue = XiChild.getString(node, XNames.ISSUE);
        final String issueKey = XiChild.getString(node, XNames.ISSUE_KEY);
        final String issueType = XiChild.getString(node, XNames.ISSUE_TYPE);
        final String key = XiChild.getString(node, XNames.KEY);
        final String originalEstimate = XiChild.getString(node, XNames.ORIGINAL_ESTIMATE);
        final String priority = XiChild.getString(node, XNames.PRIORITY);
        final String progress = XiChild.getString(node, XNames.PROGRESS);
        final String project = XiChild.getString(node, XNames.PROJECT);
        final String remainingEstimate = XiChild.getString(node, XNames.REMAINING_ESTIMATE);
        final String reporter = XiChild.getString(node, XNames.REPORTER);
        final String resolution = XiChild.getString(node, XNames.RESOLUTION);
        final String resolutionDate = XiChild.getString(node, XNames.RESOLUTION_DATE);
        final String resolved = XiChild.getString(node, XNames.RESOLVED);
        final String status = XiChild.getString(node, XNames.STATUS);
        final String subtask = XiChild.getString(node, XNames.SUBTASK);
        final String summary = XiChild.getString(node, XNames.SUMMARY);
        final String timeEstimate = XiChild.getString(node, XNames.TIME_ESTIMATE);
        final String timeOriginalEstimate = XiChild.getString(node, XNames.TIME_ORIGINAL_ESTIMATE);
        final String timeSpent = XiChild.getString(node, XNames.TIME_SPENT);
        final String type = XiChild.getString(node, XNames.TYPE);
        final String update = XiChild.getString(node, XNames.UPDATE);
        final String updatedDate = XiChild.getString(node, XNames.UPDATED_DATE);
        final String votes = XiChild.getString(node, XNames.VOTES);
        final String workRatio = XiChild.getString(node, XNames.WORK_RATIO);

        node = XiChild.getChild(node, XNames.CUSTOM_FIELD_VALUES);

        final List<String> refs = new ArrayList<String>();
        for (final Iterator i = XiChild.getIterator(node, XNames.CUSTOM_FIELD_VALUES); i.hasNext(); ) {
            final String ref = ((XiNode) i.next()).getAttributeStringValue(XNames.HREF).substring(1);
            refs.add(ref);
        }
        final JiraCustomFields fields = this.fieldsFactory.make(refs, refToMultiRefNode);

        return new JiraIssue(fields, affectedVersion, assignee, component, created, createdDate,
                description, due, dueDate, environment, fixVersion, id, issue, issueKey, issueType,
                key, originalEstimate, priority, progress, project, remainingEstimate, reporter,
                resolution, resolutionDate, resolved, status, subtask, summary, timeEstimate,
                timeOriginalEstimate, timeSpent, type, update, updatedDate, votes, workRatio);
    }


}
