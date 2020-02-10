package com.tibco.cep.jira.client.soap;

import com.tibco.cep.jira.model.JiraCustomField;
import com.tibco.cep.jira.model.JiraCustomFields;
import com.tibco.xml.datamodel.XiNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User: nprade
 * Date: 7/20/11
 * Time: 4:17 PM
 */
public class JiraCustomFieldsSoapFactory {


    private final JiraCustomFieldSoapFactory fieldFactory = new JiraCustomFieldSoapFactory();


    public JiraCustomFields make(
            List<String> refs,
            Map<String, XiNode> refToMultiRefNode) {

        final List<JiraCustomField> fieldList = new LinkedList<JiraCustomField>();
        for (final String ref : refs) {
            final JiraCustomField field = this.fieldFactory.make(ref, refToMultiRefNode);
            fieldList.add(field);
        }

        return new JiraCustomFields(fieldList);
    }

}
