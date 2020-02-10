package com.tibco.cep.designtime.model.rule;


import java.util.Collection;

import com.tibco.xml.data.primitive.ExpandedName;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 26, 2006
 * Time: 1:08:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RuleSetEx extends RuleSet {


    int RULE_DEFAULT_TYPE = 1;
    int RULE_FUNCTION_TYPE = 2;
    int RULE_TEMPLATE_TYPE = 3;
    int RULE_DECISION_GRAPH_TYPE = 4;
    int RULE_DECISION_TABLE_TYPE = 5;
    ExpandedName DEFAULT_RULE_ELEMENT_NAME = ExpandedName.makeName("rule");
    ExpandedName DECISION_TABLE_ELEMENT_NAME = ExpandedName.makeName("decisiontable");
    ExpandedName DECISION_GRAPH_ELEMENT_NAME = ExpandedName.makeName("decisiongraph");


    /**
     * @return A list of entities used in all the rules within this ruleset
     */
    Collection getEntities();
}
