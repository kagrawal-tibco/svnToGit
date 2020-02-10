package com.tibco.cep.webstudio.model.rule.instance.util;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 12/4/12
 * Time: 10:46 AM
 * Constants required for RTI serialize|deserialize
 */
public class XMLConstants {
    
    public static final ExpandedName ELEM_RULE_TEMPLATE_INSTANCE_NAME = ExpandedName.makeName("RuleTemplateInstance");
    public static final ExpandedName ATTR_ID = ExpandedName.makeName("id");
    public static final ExpandedName ATTR_NAME = ExpandedName.makeName("name");
    public static final ExpandedName ATTR_IMPLEMENTS_PATH = ExpandedName.makeName("implementsPath");
    public static final ExpandedName ATTR_DESCRIPTION = ExpandedName.makeName("description");
    public static final ExpandedName ATTR_PRIORITY = ExpandedName.makeName("priority");

    public static final ExpandedName ELEM_FILTER_NAME = ExpandedName.makeName("filter");
    public static final ExpandedName ATTR_MATCH_TYPE = ExpandedName.makeName("matchType");
    public static final ExpandedName ATTR_OPERATOR = ExpandedName.makeName("operator");
    public static final ExpandedName ATTR_TYPE = ExpandedName.makeName("type");
    public static final ExpandedName ATTR_ACTION_TYPE = ExpandedName.makeName("actionType");
    public static final ExpandedName ATTR_ALIAS = ExpandedName.makeName("alias");

    public static final ExpandedName ELEM_LINK_NAME = ExpandedName.makeName("link");
    public static final ExpandedName ELEM_FILTER_VALUE = ExpandedName.makeName("filterValue");
    public static final ExpandedName ATTR_VALUE = ExpandedName.makeName("value");

    public static final ExpandedName ELEM_ACTIONS_NAME = ExpandedName.makeName("actions");
    public static final ExpandedName ELEM_COMMAND_NAME = ExpandedName.makeName("command");

    public static final ExpandedName ELEM_BINDING_NAME = ExpandedName.makeName("binding");
}
