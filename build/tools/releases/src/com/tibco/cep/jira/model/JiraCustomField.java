package com.tibco.cep.jira.model;

import java.util.Collections;
import java.util.List;

/**
 * User: nprade
 * Date: 7/20/11
 * Time: 5:52 PM
 */
public class JiraCustomField {


    private final Integer id;
    private final String key;
    private final JiraCustomFieldName name;
    private final List<String> values;


    public JiraCustomField(
            Integer id,
            JiraCustomFieldName name,
            String key,
            List<String> values) {

        if (null == id) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.key = key;
        this.name = name;
        this.values = Collections.unmodifiableList(values);
    }


    public Integer getId() {

        return this.id;
    }


    public String getKey() {

        return this.key;
    }


    public JiraCustomFieldName getName() {
        return this.name;
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public String getSingleValue() {

        if (!this.values.isEmpty()) {
            return this.values.get(0);
        } else {
            return null;
        }
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public List<String> getValues() {
        return this.values;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append((null == this.name) ? this.id : this.name).append(" = ");
        boolean first = true;
        for (final String value : this.values) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append("[").append(value).append("]");
        }
        return sb.toString();
    }


}
