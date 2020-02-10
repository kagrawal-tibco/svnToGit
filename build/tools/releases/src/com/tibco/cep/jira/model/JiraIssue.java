package com.tibco.cep.jira.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User: nprade
 * Date: 7/20/11
 * Time: 4:01 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class JiraIssue {

    private String affectedVersion;
    private String assignee;
    private String component;
    private String created;
    private String createdDate;
    private JiraCustomFields customFields;
    private String description;
    private String due;
    private String dueDate;
    private String environment;
    private String fixVersion;
    private String id;
    private String issue;
    private String issueKey;
    private String issueType;
    private String key;
    private String originalEstimate;
    private String priority;
    private String progress;
    private String project;
    private String remainingEstimate;
    private String reporter;
    private String resolution;
    private String resolutionDate;
    private String resolved;
    private String status;
    private String subtask;
    private String summary;
    private String timeEstimate;
    private String timeOriginalEstimate;
    private String timeSpent;
    private String type;
    private String update;
    private String updatedDate;
    private String votes;
    private String workRatio;


    public JiraIssue(
            JiraCustomFields customFields,
            String affectedVersion,
            String assignee,
            String component,
            String created,
            String createdDate,
            String description,
            String due,
            String dueDate,
            String environment,
            String fixVersion,
            String id,
            String issue,
            String issueKey,
            String issueType,
            String key,
            String originalEstimate,
            String priority,
            String progress,
            String project,
            String remainingEstimate,
            String reporter,
            String resolution,
            String resolutionDate,
            String resolved,
            String status,
            String subtask,
            String summary,
            String timeEstimate,
            String timeOriginalEstimate,
            String timeSpent,
            String type,
            String update,
            String updatedDate,
            String votes,
            String workRatio) {

        this.affectedVersion = affectedVersion;
        this.assignee = assignee;
        this.component = component;
        this.created = created;
        this.createdDate = createdDate;
        this.customFields = customFields;
        this.description = description;
        this.due = due;
        this.dueDate = dueDate;
        this.environment = environment;
        this.fixVersion = fixVersion;
        this.id = id;
        this.issue = issue;
        this.issueKey = issueKey;
        this.issueType = issueType;
        this.key = key;
        this.originalEstimate = originalEstimate;
        this.priority = priority;
        this.progress = progress;
        this.project = project;
        this.remainingEstimate = remainingEstimate;
        this.reporter = reporter;
        this.resolution = resolution;
        this.resolutionDate = resolutionDate;
        this.resolved = resolved;
        this.status = status;
        this.subtask = subtask;
        this.summary = summary;
        this.timeEstimate = timeEstimate;
        this.timeOriginalEstimate = timeOriginalEstimate;
        this.timeSpent = timeSpent;
        this.type = type;
        this.update = update;
        this.updatedDate = updatedDate;
        this.votes = votes;
        this.workRatio = workRatio;
    }


    public String getAffectedVersion() {
        return this.affectedVersion;
    }


    public String getAssignee() {
        return this.assignee;
    }


    public String getComponent() {

        return this.component;
    }


    public String getCreated() {

        return this.created;
    }


    public String getCreatedDate() {

        return this.createdDate;
    }


    public JiraCustomFields getCustomFields() {

        return this.customFields;
    }


    public String getCustomFieldValue(
            JiraCustomFieldName name) {

        final List<String> values = this.getCustomFieldValues(name);
        if (values.isEmpty()) {
            return null;
        } else {
            return values.get(0);
        }
    }


    public List<String> getCustomFieldValues(
            JiraCustomFieldName name) {

        final JiraCustomField field = this.customFields.getByName(name);
        if (null == field) {
            return new ArrayList<String>();
        } else {
            return field.getValues();
        }
    }


    public String getDescription() {

        return this.description;
    }


    public String getDue() {

        return this.due;
    }


    public String getDueDate() {

        return this.dueDate;
    }


    public String getEnvironment() {

        return this.environment;
    }


    public String getFixVersion() {

        return this.fixVersion;
    }


    public String getId() {

        return this.id;
    }


    public String getIssue() {

        return this.issue;
    }


    public String getIssueKey() {

        return this.issueKey;
    }


    public String getIssueType() {

        return this.issueType;
    }


    public String getKey() {

        return this.key;
    }


    public String getOriginalEstimate() {
        return originalEstimate;
    }


    public String getPriority() {
        return this.priority;
    }


    public String getProgress() {
        return this.progress;
    }


    public String getProject() {
        return this.project;
    }


    public String getRemainingEstimate() {
        return this.remainingEstimate;
    }


    public String getReporter() {
        return this.reporter;
    }


    public String getResolution() {
        return this.resolution;
    }


    public String getResolutionDate() {
        return this.resolutionDate;
    }


    public String getResolved() {
        return this.resolved;
    }


    public String getStatus() {
        return this.status;
    }


    public String getSubtask() {
        return this.subtask;
    }


    public String getSummary() {
        return this.summary;
    }


    public String getTimeEstimate() {

        return this.timeEstimate;
    }


    public String getTimeOriginalEstimate() {

        return this.timeOriginalEstimate;
    }


    public String getTimeSpent() {

        return this.timeSpent;
    }


    public String getType() {

        return this.type;
    }


    public String getUpdate() {

        return this.update;
    }


    public String getUpdatedDate() {

        return this.updatedDate;
    }


    public String getVotes() {

        return this.votes;
    }


    public String getWorkRatio() {

        return this.workRatio;
    }


}
