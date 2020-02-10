package com.tibco.cep.webstudio.model.rule.instance.util;

import com.tibco.cep.webstudio.model.rule.instance.Actions;
import com.tibco.cep.webstudio.model.rule.instance.Binding;
import com.tibco.cep.webstudio.model.rule.instance.BuilderSubClause;
import com.tibco.cep.webstudio.model.rule.instance.Command;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.FilterValue;
import com.tibco.cep.webstudio.model.rule.instance.MultiFilter;
import com.tibco.cep.webstudio.model.rule.instance.RelatedFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;
import com.tibco.cep.webstudio.model.rule.instance.SimpleFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.SingleFilter;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;

import java.util.List;

import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ATTR_ACTION_TYPE;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ATTR_ALIAS;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ATTR_ID;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ATTR_IMPLEMENTS_PATH;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ATTR_DESCRIPTION;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ATTR_PRIORITY;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ATTR_MATCH_TYPE;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ATTR_NAME;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ATTR_OPERATOR;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ATTR_TYPE;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ATTR_VALUE;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ELEM_ACTIONS_NAME;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ELEM_BINDING_NAME;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ELEM_COMMAND_NAME;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ELEM_FILTER_NAME;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ELEM_FILTER_VALUE;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ELEM_LINK_NAME;
import static com.tibco.cep.webstudio.model.rule.instance.util.XMLConstants.ELEM_RULE_TEMPLATE_INSTANCE_NAME;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 12/4/12
 * Time: 7:33 AM
 * Serializer class to convert {@link RuleTemplateInstance} EMF object to {@link XiNode}
 */
public class RuleTemplateInstanceSerializer {

    private RuleTemplateInstance ruleTemplateInstance;

    /**
     * The {@link XiNode} to contain serialized XML tree.
     */
    private XiNode rootDoc;

    public RuleTemplateInstanceSerializer(final RuleTemplateInstance ruleTemplateInstance) {
        this.ruleTemplateInstance = ruleTemplateInstance;
    }

    public void serialize() {
        rootDoc = XiFactoryFactory.newInstance().createDocument();
        serializeRuleTemplateInstance(rootDoc);
    }

    /**
     *
     * @param rootNode
     */
    private void serializeRuleTemplateInstance(XiNode rootNode) {
        XiNode ruleTemplateInstanceNode = XiFactoryFactory.newInstance().createElement(ELEM_RULE_TEMPLATE_INSTANCE_NAME);
        //Append to root node.
        rootNode.appendChild(ruleTemplateInstanceNode);

        String id = ruleTemplateInstance.getId();
        String name = ruleTemplateInstance.getName();
        String implementsPath = ruleTemplateInstance.getImplementsPath();
        String description = ruleTemplateInstance.getDescription();
        int rulePriority = ruleTemplateInstance.getPriority();
        
        MultiFilter baseConditionsFilter = ruleTemplateInstance.getConditionFilter();

        if (id != null) {
            ruleTemplateInstanceNode.setAttributeStringValue(ATTR_ID, id);
        }
        if (name != null) {
            ruleTemplateInstanceNode.setAttributeStringValue(ATTR_NAME, name);
        }
        if (implementsPath != null) {
            ruleTemplateInstanceNode.setAttributeStringValue(ATTR_IMPLEMENTS_PATH, implementsPath);
        }
        
        ruleTemplateInstanceNode.setAttributeStringValue(ATTR_PRIORITY, rulePriority+"");
        
        if (description != null) {
            ruleTemplateInstanceNode.setAttributeStringValue(ATTR_DESCRIPTION, description);
        }
        if (baseConditionsFilter != null) {
            serializeMultiFilter(ruleTemplateInstanceNode, baseConditionsFilter);
        }
        Actions actions = ruleTemplateInstance.getActions();
        if (actions != null) {
            serializeActions(ruleTemplateInstanceNode, actions);
        }
        List<Binding> bindings = ruleTemplateInstance.getBindings();
        for (Binding binding : bindings) {
            serializeBinding(ruleTemplateInstanceNode, binding);
        }
    }

    /**
     *
     * @param rootNode
     * @param multiFilter
     */
    private void serializeMultiFilter(XiNode rootNode, MultiFilter multiFilter) {
        XiNode filterNode = XiFactoryFactory.newInstance().createElement(ELEM_FILTER_NAME);
        //Append to root node
        rootNode.appendChild(filterNode);

        String matchType = multiFilter.getMatchType();
        BuilderSubClause subClause = multiFilter.getSubClause();
        
        String filterId = multiFilter.getFilterId();
        if (filterId != null && !filterId.isEmpty()) {
        	filterNode.setAttributeStringValue(ATTR_ID, String.valueOf(filterId));
        }

        if (matchType != null && !matchType.isEmpty()) {
            filterNode.setAttributeStringValue(ATTR_MATCH_TYPE, matchType);
        }
        
        if (subClause != null) {
            serializeBuilderSubClause(filterNode, subClause);
        }
    }

    /**
     *
     * @param rootNode
     * @param builderSubClause
     */
    private void serializeBuilderSubClause(XiNode rootNode, BuilderSubClause builderSubClause) {
        List<Filter> filters = builderSubClause.getFilters();
        if (filters == null || filters.isEmpty()) {
            return;
        }
        for (Filter filter : filters) {
            if (filter instanceof SingleFilter) {
                serializeSingleFilter(rootNode, (SingleFilter)filter);
            } else if (filter instanceof MultiFilter) {
                serializeMultiFilter(rootNode, (MultiFilter)filter);
            }
        }
    }

    /**
     *
     * @param rootNode
     * @param filter
     */
    private void serializeSingleFilter(XiNode rootNode, SingleFilter filter) {
        XiNode filterNode = XiFactoryFactory.newInstance().createElement(ELEM_FILTER_NAME);
         //Append to root node
        rootNode.appendChild(filterNode);
        
        String filterId = filter.getFilterId();
        if (filterId != null && !filterId.isEmpty()) {
        	filterNode.setAttributeStringValue(ATTR_ID, String.valueOf(filterId));
        }

        String operator = filter.getOperator();
        if (operator != null && !operator.isEmpty()) {
            filterNode.setAttributeStringValue(ATTR_OPERATOR, operator);
        }
        
        List<RelatedLink> links = filter.getLinks();
        for (RelatedLink relatedLink : links) {
            serializeLink(filterNode, relatedLink);
        }
        FilterValue filterValue = filter.getFilterValue();
        if (filterValue != null) {
            serializeFilterValue(filterNode, filterValue);
        }
    }

    /**
     *
     * @param rootNode
     * @param relatedLink
     */
    private void serializeLink(XiNode rootNode, RelatedLink relatedLink) {
        XiNode linkNode = XiFactoryFactory.newInstance().createElement(ELEM_LINK_NAME);
        //Append to root node
        rootNode.appendChild(linkNode);

        String linkType = relatedLink.getLinkType();
        String linkText = relatedLink.getLinkText();

        if (linkType != null && !linkType.isEmpty()) {
            linkNode.setAttributeStringValue(ATTR_TYPE, linkType);
        }
        if (linkText != null && !linkText.isEmpty()) {
            linkNode.setAttributeStringValue(ATTR_NAME, linkText);
        }
    }

    /**
     *
     * @param rootNode
     * @param filterValue
     */
    private void serializeFilterValue(XiNode rootNode, FilterValue filterValue) {
        XiNode valueNode = XiFactoryFactory.newInstance().createElement(ELEM_FILTER_VALUE);
        //Append to root node
        rootNode.appendChild(valueNode);

        if (filterValue instanceof SimpleFilterValue) {
            SimpleFilterValue simpleFilterValue = (SimpleFilterValue)filterValue;
            String value = simpleFilterValue.getValue();

            if (value != null && !value.isEmpty()) {
                valueNode.setAttributeStringValue(ATTR_VALUE, value);
            }
        } else if (filterValue instanceof RelatedFilterValue) {
            RelatedFilterValue relatedFilterValue = (RelatedFilterValue)filterValue;
            List<RelatedLink> links = relatedFilterValue.getLinks();
            for (RelatedLink relatedLink : links) {
                serializeLink(valueNode, relatedLink);
            }
        }
    }

    /**
     *
     * @param rootNode
     * @param actions
     */
    private void serializeActions(XiNode rootNode, Actions actions) {
        XiNode actionsNode = XiFactoryFactory.newInstance().createElement(ELEM_ACTIONS_NAME);
        //Append to root node
        rootNode.appendChild(actionsNode);

        if (actions != null) {
            for (Command command : actions.getActions()) {
                serializeCommand(actionsNode, command);
            }
        }
    }

    /**
     *
     * @param rootNode
     * @param command
     */
    private void serializeCommand(XiNode rootNode, Command command) {
        XiNode commandNode = XiFactoryFactory.newInstance().createElement(ELEM_COMMAND_NAME);
        //Append to root node
        rootNode.appendChild(commandNode);

        String actionType = command.getActionType();
        String alias = command.getAlias();
        String type = command.getType();

        if (actionType != null && !actionType.isEmpty()) {
            commandNode.setAttributeStringValue(ATTR_ACTION_TYPE, actionType);
        }
        if (type != null && !type.isEmpty()) {
            commandNode.setAttributeStringValue(ATTR_TYPE, type);
        }
        if (alias != null && !alias.isEmpty()) {
            commandNode.setAttributeStringValue(ATTR_ALIAS, alias);
        }
        BuilderSubClause builderSubClause = command.getSubClause();
        if (builderSubClause != null) {
            serializeBuilderSubClause(commandNode, builderSubClause);
        }
    }

    /**
     *
     * @param rootNode
     * @param binding
     */
    private void serializeBinding(XiNode rootNode, Binding binding) {
        XiNode bindingNode = XiFactoryFactory.newInstance().createElement(ELEM_BINDING_NAME);
        //Append to root node
        rootNode.appendChild(bindingNode);

        String id = binding.getId();
        String value = binding.getValue();

        if (id != null && !id.isEmpty()) {
            bindingNode.setAttributeStringValue(ATTR_ID, id);
        }
        if (value != null && !value.isEmpty()) {
            bindingNode.setAttributeStringValue(ATTR_VALUE, value);
        }
    }

    public XiNode getSeralized() {
        return rootDoc;
    }
}
