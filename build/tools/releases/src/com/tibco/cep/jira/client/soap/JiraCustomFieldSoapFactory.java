package com.tibco.cep.jira.client.soap;

import com.tibco.cep.jira.model.JiraCustomField;
import com.tibco.cep.jira.model.JiraCustomFieldName;
import com.tibco.cep.jira.model.JiraCustomFieldNameProvider;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: nprade
 * Date: 7/20/11
 * Time: 4:17 PM
 */
public class JiraCustomFieldSoapFactory {

    private static final Pattern ID_PATTERN = Pattern.compile("\\s*(?:customfield_)?(\\d+)\\s*");

    private final JiraCustomFieldNameProvider fieldNameProvider = new JiraCustomFieldNameProvider();


    public JiraCustomField make(
            String ref,
            Map<String, XiNode> refToMultiRefNode) {

        XiNode node = refToMultiRefNode.get(ref);

        final String rawId = XiChild.getString(node, XNames.CUSTOM_FIELD_ID);
        final Matcher matcher = ID_PATTERN.matcher(rawId);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }
        final Integer id = new Integer(matcher.group(1));

        final JiraCustomFieldName name = this.fieldNameProvider.getById(rawId);

        final String key = XiChild.getString(node, XNames.KEY);

        node = XiChild.getChild(node, XNames.VALUES);

        final List<String> values = new LinkedList<String>();
        for (final Iterator i = XiChild.getIterator(node, XNames.VALUES); i.hasNext(); ) {
            values.add(((XiNode) i.next()).getStringValue());
        }

        return new JiraCustomField(id, name, key, values);
    }

}
