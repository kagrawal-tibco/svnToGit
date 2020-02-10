package com.tibco.cep.jira.model;

import com.tibco.cep.util.UnmodifiableSortedMap;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * User: nprade
 * Date: 7/20/11
 * Time: 5:48 PM
 */
public class JiraCustomFields
        extends UnmodifiableSortedMap<Integer, JiraCustomField> {


    private final JiraCustomFieldNameProvider fieldNameProvider = new JiraCustomFieldNameProvider();
    private final UnmodifiableSortedMap<String, JiraCustomField> keyToField;


    public JiraCustomFields(
            List<JiraCustomField> fieldList) {

        super(makeIdToFieldMap(fieldList));

        final TreeMap<String, JiraCustomField> keyToField = new TreeMap<String, JiraCustomField>();
        for (final JiraCustomField field : fieldList) {
            keyToField.put(field.getKey(), field);
        }
        this.keyToField = new UnmodifiableSortedMap<String, JiraCustomField>(keyToField);
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public JiraCustomField getById(
            String id) {

        return this.get(id);
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public JiraCustomField getByKey(
            String key) {

        return this.keyToField.get(key);
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public JiraCustomField getByName(
            JiraCustomFieldName name) {

        return this.get(name.getId());
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public JiraCustomField getByName(
            String name) {

        return this.get(String.valueOf(this.fieldNameProvider.getByName(name).getId()));
    }


    private static Map<Integer, JiraCustomField> makeIdToFieldMap(
            List<JiraCustomField> fieldList) {

        final Map<Integer, JiraCustomField> map = new TreeMap<Integer, JiraCustomField>();
        for (final JiraCustomField field : fieldList) {
            map.put(field.getId(), field);
        }
        return map;
    }


}
