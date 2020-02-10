package com.tibco.cep.jira.model;

import com.tibco.cep.util.UnmodifiableSortedMap;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * User: nprade
 * Date: 7/21/11
 * Time: 4:23 PM
 */
public final class JiraCustomFieldNameProvider {


    private static final UnmodifiableSortedMap<String, JiraCustomFieldName> ID_TO_ENUM;
    private static final UnmodifiableSortedMap<String, JiraCustomFieldName> NAME_TO_ENUM;

    static {
        final SortedMap<String, JiraCustomFieldName> idToEnum = new TreeMap<String, JiraCustomFieldName>();
        final SortedMap<String, JiraCustomFieldName> nameToEnum = new TreeMap<String, JiraCustomFieldName>();
        for (JiraCustomFieldName n : JiraCustomFieldName.values()) {
            idToEnum.put(String.valueOf(n.getId()), n);
            idToEnum.put("customfield_" + n.getId(), n);
            nameToEnum.put(n.toString(), n);
        }
        ID_TO_ENUM = new UnmodifiableSortedMap<String, JiraCustomFieldName>(idToEnum);
        NAME_TO_ENUM = new UnmodifiableSortedMap<String, JiraCustomFieldName>(nameToEnum);
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public JiraCustomFieldName getByName(
            String name) {

        return NAME_TO_ENUM.get(name);
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public JiraCustomFieldName getById(
            int id) {

        return this.getById(String.valueOf(id));
    }


    public JiraCustomFieldName getById(
            String id) {

        return ID_TO_ENUM.get(id);
    }

}
