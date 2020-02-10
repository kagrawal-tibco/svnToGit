package com.tibco.cep.webstudio.model.rule.instance.util;

import com.tibco.cep.webstudio.model.rule.instance.Actions;
import com.tibco.cep.webstudio.model.rule.instance.Binding;
import com.tibco.cep.webstudio.model.rule.instance.BuilderSubClause;
import com.tibco.cep.webstudio.model.rule.instance.Command;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.MultiFilter;
import com.tibco.cep.webstudio.model.rule.instance.RelatedFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;
import com.tibco.cep.webstudio.model.rule.instance.SimpleFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.SingleFilter;
import com.tibco.cep.webstudio.model.rule.instance.impl.ActionsImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.BindingImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.BuilderSubClauseImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.CommandImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.MultiFilterImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.RelatedFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.RelatedLinkImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.RuleTemplateInstanceImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.SimpleFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.SingleFilterImpl;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;

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
 * Date: 11/4/12
 * Time: 3:14 PM
 * Deserialize XML format rule template instance into EMF model.
 */
public class RuleTemplateInstanceDeserializer {

    private InputStream ruleTemplateContentsStream;

    private RuleTemplateInstance deserialized;

    public RuleTemplateInstanceDeserializer(InputStream ruleTemplateContentsStream) {
        this.ruleTemplateContentsStream = ruleTemplateContentsStream;
    }

    public RuleTemplateInstanceDeserializer(File ruleTemplateInstanceFile) throws FileNotFoundException {
        this(new FileInputStream(ruleTemplateInstanceFile));
    }

    /**
     * Deserialize the RTI XML format string and create EMF model {@link RuleTemplateInstance} from it.
     * @throws Exception
     */
    public void deserialize() throws Exception {
        XiNode rootDoc = XiParserFactory.newInstance().parse(new InputSource(ruleTemplateContentsStream));
        processRuleTemplateInstanceNode(rootDoc);
    }

    /**
     *
     * @param rootDoc
     */
    @SuppressWarnings("unchecked")
    private void processRuleTemplateInstanceNode(XiNode rootDoc) {
        XiNode ruleTemplateInstanceNode = XiChild.getChild(rootDoc, ELEM_RULE_TEMPLATE_INSTANCE_NAME);
        if (ruleTemplateInstanceNode != null) {
            deserialized = new RuleTemplateInstanceImpl();
            String id = ruleTemplateInstanceNode.getAttributeStringValue(ATTR_ID);
            String implementsPath = ruleTemplateInstanceNode.getAttributeStringValue(ATTR_IMPLEMENTS_PATH);
            String description = ruleTemplateInstanceNode.getAttributeStringValue(ATTR_DESCRIPTION);
            String rulePriority = ruleTemplateInstanceNode.getAttributeStringValue(ATTR_PRIORITY);
            
            deserialized.setId(id);
            deserialized.setImplementsPath(implementsPath);
            deserialized.setDescription(description);
            if (rulePriority != null && !rulePriority.isEmpty()) {
            	deserialized.setPriority(Integer.parseInt(rulePriority));
            }
            
            //Get condition filters
            XiNode baseConditionsFilter = XiChild.getChild(ruleTemplateInstanceNode, ELEM_FILTER_NAME);
            if (baseConditionsFilter != null) {
                MultiFilter multiFilter = new MultiFilterImpl();
                deserialized.setConditionFilter(multiFilter);
                processBaseMultiFilter(baseConditionsFilter, multiFilter);
            }
            XiNode actionsNode = XiChild.getChild(ruleTemplateInstanceNode, ELEM_ACTIONS_NAME);
            if (actionsNode != null) {
                Actions actions = new ActionsImpl();
                processActions(actionsNode, actions);
                deserialized.setActions(actions);
            }
            Iterator<XiNode> bindingsNodes = XiChild.getIterator(ruleTemplateInstanceNode, ELEM_BINDING_NAME);
            while (bindingsNodes.hasNext()) {
                processBinding(bindingsNodes.next());
            }
        }
    }

    /**
     *
     * @param baseConditionsFilter
     * @param multiFilter
     */
    @SuppressWarnings("unchecked")
    private void processBaseMultiFilter(XiNode baseConditionsFilter, MultiFilter multiFilter) {
    	//Get Filter Id
        String filterId = baseConditionsFilter.getAttributeStringValue(ATTR_ID);
        if (filterId != null) {
            multiFilter.setFilterId(filterId);
        }
        
        //Get match type
        String matchType = baseConditionsFilter.getAttributeStringValue(ATTR_MATCH_TYPE);
        if (matchType != null) {
            multiFilter.setMatchType(matchType);
        }
        //Process children if any
        Iterator<XiNode> filterChildren = XiChild.getIterator(baseConditionsFilter, ELEM_FILTER_NAME);
        BuilderSubClause builderSubClause = multiFilter.getSubClause();
        builderSubClause = (builderSubClause == null) ? new BuilderSubClauseImpl() : builderSubClause;
        multiFilter.setSubClause(builderSubClause);
        while (filterChildren.hasNext()) {
            processChildFilter(filterChildren.next(), multiFilter);
        }
    }

    /**
     *
     * @param childFilter
     * @param parentFilter
     */
    private void processChildFilter(XiNode childFilter, MultiFilter parentFilter) {
        String matchType = childFilter.getAttributeStringValue(ATTR_MATCH_TYPE);
        if (matchType != null) {
            MultiFilter childMultiFilter = new MultiFilterImpl();
            BuilderSubClause builderSubClause = parentFilter.getSubClause();
            builderSubClause.addFilter(childMultiFilter);
            processBaseMultiFilter(childFilter, childMultiFilter);
        } else {
            SingleFilter singleFilter = new SingleFilterImpl();
            //Add it to parent
            parentFilter.getSubClause().addFilter(singleFilter);
            //Process simple filter
            processSimpleFilter(childFilter, singleFilter);
        }
    } 
    
    /**
     *  Process children of command nodes.
     * @param childFilter
     * @param command
     */
	private void processChildFilter(XiNode childFilter, Command command) {
	    String matchType = childFilter.getAttributeStringValue(ATTR_MATCH_TYPE);
	    BuilderSubClause builderSubClause = command.getSubClause();
	    builderSubClause = (builderSubClause != null) ? builderSubClause : new BuilderSubClauseImpl();
	    command.setSubClause(builderSubClause);
	
	    if (matchType != null) {
	        MultiFilter childMultiFilter = new MultiFilterImpl();
	        builderSubClause.addFilter(childMultiFilter);
	        processBaseMultiFilter(childFilter, childMultiFilter);
	    } else {
	        SingleFilter singleFilter = new SingleFilterImpl();
	        builderSubClause.addFilter(singleFilter);
	        //Process simple filter
	        processSimpleFilter(childFilter, singleFilter);
	    }
	}

    /**
     *
     * @param childFilter
     * @param parentFilter
     * @param <F>
     */
    @SuppressWarnings("unchecked")
    private <F extends Filter> void processSimpleFilter(XiNode childFilter, F parentFilter) {
    	SingleFilter singleFilter = (SingleFilter)parentFilter;
    	
    	String filterId = childFilter.getAttributeStringValue(ATTR_ID);
        if (filterId != null) {
        	singleFilter.setFilterId(filterId);
        }
        String operator = childFilter.getAttributeStringValue(ATTR_OPERATOR);
        singleFilter.setOperator(operator);
        
        //Get link children
        Iterator<XiNode> linkNodes = XiChild.getIterator(childFilter, ELEM_LINK_NAME);
        while (linkNodes.hasNext()) {
            XiNode linkNode = linkNodes.next();
            processLinkNode(linkNode, singleFilter);
        }
        XiNode filterValueNode = XiChild.getChild(childFilter, ELEM_FILTER_VALUE);
        processFilterValueNode(filterValueNode, parentFilter);
    }

    /**
     *
     * @param linkNode
     * @param linkContainer
     * @param <T>
     */
    private <T> void processLinkNode(XiNode linkNode, T linkContainer) {
        //Get type and name
        String linkType = linkNode.getAttributeStringValue(ATTR_TYPE);
        String linkText = linkNode.getAttributeStringValue(ATTR_NAME);

        RelatedLink relatedLink = new RelatedLinkImpl();
        relatedLink.setLinkText(linkText);
        relatedLink.setLinkType(linkType);

        if (linkContainer instanceof SingleFilter) {
            ((SingleFilter)linkContainer).addRelatedLink(relatedLink);
        } else if (linkContainer instanceof RelatedFilterValue) {
            ((RelatedFilterValue)linkContainer).addLink(relatedLink);
        }
    }

    /**
     *
     * @param filterValueNode
     * @param parentFilter
     * @param <F>
     */
    @SuppressWarnings("unchecked")
    private <F extends Filter> void processFilterValueNode(XiNode filterValueNode, F parentFilter) {
        //Check if it has value attr
        String simpleValue = filterValueNode.getAttributeStringValue(ATTR_VALUE);
        if (simpleValue == null || simpleValue.isEmpty()) {
            RelatedFilterValue relatedFilterValue = new RelatedFilterValueImpl();
            if (parentFilter instanceof SingleFilter) {
                ((SingleFilter)parentFilter).setFilterValue(relatedFilterValue);
            }
            //Process link nodes
            Iterator<XiNode> linkNodes = XiChild.getIterator(filterValueNode, ELEM_LINK_NAME);
            while (linkNodes.hasNext()) {
                XiNode linkNode = linkNodes.next();
                processLinkNode(linkNode, relatedFilterValue);
            }
        } else {
            SimpleFilterValue simpleFilterValue = new SimpleFilterValueImpl();
            simpleFilterValue.setValue(simpleValue);
            if (parentFilter instanceof SingleFilter) {
                ((SingleFilter)parentFilter).setFilterValue(simpleFilterValue);
            }
        }
    }

    /**
     *
     * @param actionsNode
     */
    @SuppressWarnings("unchecked")
    private void processActions(XiNode actionsNode, Actions actions) {
        //get command children
        Iterator<XiNode> commandChildren = XiChild.getIterator(actionsNode, ELEM_COMMAND_NAME);
        while (commandChildren.hasNext()) {
            processCommand(commandChildren.next(), actions);
        }
    }

    /**
     *
     * @param commandNode
     * @param actions
     */
    @SuppressWarnings("unchecked")
	private void processCommand(XiNode commandNode, Actions actions) {
        //Get attrs
        String actionType = commandNode.getAttributeStringValue(ATTR_ACTION_TYPE);
        String type = commandNode.getAttributeStringValue(ATTR_TYPE);
        String alias = commandNode.getAttributeStringValue(ATTR_ALIAS);

        Command command = new CommandImpl();
        command.setActionType(actionType);
        command.setAlias(alias);
        command.setType(type);

        //Process child filters if any
        //Get command children
        Iterator<XiNode> commandChildren = XiChild.getIterator(commandNode, ELEM_FILTER_NAME);
        while (commandChildren.hasNext()) {
            processChildFilter(commandChildren.next(), command);
        }
        actions.addAction(command);
    }

    /**
     *
     * @param bindingNode
     */
    private void processBinding(XiNode bindingNode) {
        Binding binding = new BindingImpl();
        String bindingId = bindingNode.getAttributeStringValue(ATTR_ID);
        String bindingvalue = bindingNode.getAttributeStringValue(ATTR_VALUE);
        binding.setId(bindingId);
        binding.setValue(bindingvalue);
        deserialized.addBinding(binding);
    }

    public RuleTemplateInstance getDeserialized() {
        return deserialized;
    }
}
