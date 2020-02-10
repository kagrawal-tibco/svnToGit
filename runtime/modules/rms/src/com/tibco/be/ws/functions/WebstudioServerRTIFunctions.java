package com.tibco.be.ws.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.cep.webstudio.model.rule.instance.*;
import com.tibco.cep.webstudio.model.rule.instance.impl.*;
import com.tibco.cep.webstudio.model.rule.instance.util.RuleTemplateInstanceSerializer;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/3/12
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.RTI",
        synopsis = "Functions to manipulate RTI model and fetch info from it.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.RTI", value=true))

public class WebstudioServerRTIFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "createRuleTemplateInstanceEObject",
        synopsis = "",
        signature = "Object createRuleTemplateInstanceEObject(String id, String implementsPath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "String", desc = "A unique id to set on the RTI EObject instance."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "implementsPath", type = "String", desc = "FQN of the RT implemented.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "the", desc = "EObject representing the RTI."),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Create an EMF model object of the RTI.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object createRuleTemplateInstanceEObject(String id, String implementsPath) {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        if (implementsPath == null) {
            throw new IllegalArgumentException("RuleTemplateInstance needs to have a valid ruletemplate implementation path");
        }
        RuleTemplateInstance ruleTemplateInstance = new RuleTemplateInstanceImpl();
        ruleTemplateInstance.setImplementsPath(implementsPath);
        ruleTemplateInstance.setId(id);
        return ruleTemplateInstance;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "addRuleTemplateInstanceBindingInfo",
        signature = "addRuleTemplateInstanceBindingInfo(Object ruleTemplateInstanceEObject,",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateInstanceEObject", type = "Object", desc = "The RTI EObject instance."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bindingId", type = "String", desc = "The id of the binding to be set."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bindingExpression", type = "String", desc = "The Binding expression value.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Create an RTI binding to be attached to this ruletemplate emf instance.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void addRuleTemplateInstanceBindingInfo(Object ruleTemplateInstanceEObject,
                                                          String bindingId,
                                                          String bindingExpression) {
        if (!(ruleTemplateInstanceEObject instanceof RuleTemplateInstance)) {
            throw new IllegalArgumentException("RuleTemplateInstance cannot be null");
        }
        Binding binding = new BindingImpl();
        binding.setId(bindingId);
        binding.setValue(bindingExpression);
        ((RuleTemplateInstance)ruleTemplateInstanceEObject).getBindings().add(binding);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "addChildMultiFilter",
        signature = "addChildMultiFilter(Object multiFilterContainerEObject,",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "multiFilterContainerEObject", type = "Object", desc = "Either a command, another multi filter, or the RTI EModel."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "multiFilterEObject", type = "Object", desc = "The Multi filter EModel.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Add a child <code>MultiFilter</code> to multi filter container object.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void addChildMultiFilter(Object multiFilterContainerEObject,
                                           Object multiFilterEObject) {
        if (multiFilterContainerEObject instanceof RuleTemplateInstance) {
            RuleTemplateInstance ruleTemplateInstance = (RuleTemplateInstance)multiFilterContainerEObject;
            ruleTemplateInstance.setConditionFilter((MultiFilter)multiFilterEObject);
        } else if (multiFilterContainerEObject instanceof Command) {
            Command command = (Command)multiFilterContainerEObject;
            BuilderSubClause builderSubClause = command.getSubClause();
            builderSubClause = (builderSubClause == null) ? new BuilderSubClauseImpl() : builderSubClause;
            builderSubClause.addFilter((Filter)multiFilterEObject);
            command.setSubClause(builderSubClause);
        } else if (multiFilterContainerEObject instanceof MultiFilter) {
            MultiFilter multiFilter = (MultiFilter)multiFilterContainerEObject;
            BuilderSubClause builderSubClause = multiFilter.getSubClause();
            builderSubClause = (builderSubClause == null) ? new BuilderSubClauseImpl() : builderSubClause;
            builderSubClause.addFilter((Filter)multiFilterEObject);
            multiFilter.setSubClause(builderSubClause);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createRuleTemplateInstanceConditionsMultiFilter",
        signature = "Object createRuleTemplateInstanceConditionsMultiFilter(String matchType)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "matchType", type = "String", desc = "The match type associated with this multi filter EModel.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "The", desc = "<code>MultiFilter</code> object."),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Create new <code>MultiFilter</code> using the match type provided.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object createRuleTemplateInstanceConditionsMultiFilter(String matchType) {
        MultiFilter multiFilter = new MultiFilterImpl();
        multiFilter.setMatchType(matchType);
        return multiFilter;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "setFilterId",
        signature = "void setFilterId(Object filter, String filterId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "The <code>Multi/Single Filter</code> EModel instance."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterId", type = "String", desc = "The filterId for the Multi/Single Filter.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getBaseRuleTemplate",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set the filterId associated with this Multi/Single filter.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )    
    public static void setFilterId(Object filter, String filterId) {
    	 if (!(filter instanceof Filter)) {
             throw new IllegalArgumentException("Filter value should be a Multi/Single Filter");
         }
    	 Filter genericFilter = (Filter)filter;
    	 genericFilter.setFilterId(filterId);
    }
    
    @com.tibco.be.model.functions.BEFunction(
    	name = "getFilterId",
    	synopsis = "",
    	signature = "void getFilterId(Object filterEObject)",
    	params = {
    		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterEObject", type = "Object", desc = "The <code>Multi/Single Filter</code> EModel instance.")
    	},
    	freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Filter Id"),
    	version = "5.1.0",
    	see = "WebstudioServerRTViewFunctions.getBaseRuleTemplate",
    	mapper = @com.tibco.be.model.functions.BEMapper(),
    	description = "Set the filterId associated with this Multi/Single filter.",
    	cautions = "",
    	fndomain = {ACTION},
    	example = ""
    )    
    public static String getFilterId(Object filterEObject) {
    	if (!(filterEObject instanceof Filter)) {
    		throw new IllegalArgumentException("Filter value should be a Multi/Single Filter");
    	}
    	Filter genericFilter = (Filter) filterEObject;
    	return genericFilter.getFilterId();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createRuleTemplateInstanceConditionsSingleFilter",
        signature = "Object createRuleTemplateInstanceConditionsSingleFilter()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "The", desc = "<code>SingleFilter</code> object."),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Create new <code>SingleFilter</code> .",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object createRuleTemplateInstanceConditionsSingleFilter() {
        return new SingleFilterImpl();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createRuleTemplateInstanceCommand",
        signature = "Object createRuleTemplateInstanceCommand(String alias,",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "alias", type = "String", desc = "The command alias."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "type", type = "String", desc = "Command type."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "actionType", type = "String", desc = "Action Type for command.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "A", desc = "<code>Command</code> EModel object."),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Create new <code>Command</code> .",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object createRuleTemplateInstanceCommand(String alias,
                                                           String type,
                                                           String actionType) {
        Command command = new CommandImpl();
        command.setActionType(actionType);
        command.setAlias(alias);
        command.setType(type);
        return command;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "addRuleTemplateInstanceCommand",
        signature = "Object addRuleTemplateInstanceCommand(Object ruleTemplateInstanceEObject,",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateInstanceEObject", type = "Object", desc = "The <code>RuleTemplateInstance</code> EModel"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "commandObject", type = "Object", desc = "The <code>Command</code> EModel.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Add a <code>Command</code>  to this RTI actions.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void addRuleTemplateInstanceCommand(Object ruleTemplateInstanceEObject,
                                                      Object commandObject) {
        if (!(ruleTemplateInstanceEObject instanceof RuleTemplateInstance)) {
            throw new IllegalArgumentException("RuleTemplateInstance cannot be null");
        }
        RuleTemplateInstance ruleTemplateInstance = (RuleTemplateInstance)ruleTemplateInstanceEObject;
        Command command = (Command)commandObject;
        ruleTemplateInstance.getActions().addAction(command);
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "addChildFilter",
        signature = "Object addChildFilter(Object filterContainerEObject,",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterContainerEObject", type = "Object", desc = "The <code>MultiFilter</code> or <code>Command</code> EModel"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "childFilter", type = "Object", desc = "The <code>Filter</code> EModel.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Add a <code>Filter</code>  to this filter container.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void addChildFilter(Object filterContainerEObject,
                                      Object childFilter) {
        if (!(childFilter instanceof Filter)) {
            throw new IllegalArgumentException("Parent filter should be a filter");
        }
        BuilderSubClause builderSubClause = null;
        if (filterContainerEObject instanceof MultiFilter) {
            MultiFilter multiFilter = (MultiFilter)filterContainerEObject;
            builderSubClause = multiFilter.getSubClause();
            builderSubClause = (builderSubClause == null) ? new BuilderSubClauseImpl() : builderSubClause;
            multiFilter.setSubClause(builderSubClause);
        } else if (filterContainerEObject instanceof Command) {
            Command command = (Command)filterContainerEObject;
            builderSubClause = command.getSubClause();
            builderSubClause = (builderSubClause == null) ? new BuilderSubClauseImpl() : builderSubClause;
            command.setSubClause(builderSubClause);
        }
        if (builderSubClause != null) {
            builderSubClause.addFilter((Filter)childFilter);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createRuleTemplateInstanceConditionsLink",
        signature = "Object createRuleTemplateInstanceConditionsLink(String linkText,",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "linkText", type = "String", desc = "The link text(name)"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "linkType", type = "String", desc = "The link type.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "A", desc = "<code>RelatedLink</code> EModel"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Create a new <code>RelatedLink</code> EModel instance.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object createRuleTemplateInstanceConditionsLink(String linkText,
                                                                  String linkType) {
        RelatedLink relatedLink = new RelatedLinkImpl();
        relatedLink.setLinkText(linkText);
        relatedLink.setLinkType(linkType);
        return relatedLink;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createSimpleValue",
        signature = "Object createSimpleValue(String simpleValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "simpleValue", type = "String", desc = "The simple value to be added to the <code>SimpleFilterValue</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "A", desc = "<code>SimpleFilterValue</code> EModel"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Create a new <code>SimpleFilterValue</code> EModel instance.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object createSimpleValue(String simpleValue) {
        SimpleFilterValue simpleFilterValue = new SimpleFilterValueImpl();
        simpleFilterValue.setValue(simpleValue);
        return simpleFilterValue;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "createComplexValue",
        signature = "Object createComplexValue()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "A", desc = "<code>RelatedFilterValue</code> EModel"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Create a new <code>RelatedFilterValue</code> EModel instance.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object createComplexValue() {
        RelatedFilterValue complexFilterValue = new RelatedFilterValueImpl();
        return complexFilterValue;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "addChildLink",
        signature = "void addChildLink(Object linkContainerEObject,",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "linkContainerEObject", type = "Object", desc = "A <code>RelatedFilterValue</code> or <code>SingleFilter</code> EModel instance."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "linkObject", type = "Object", desc = "<code>RelatedLink</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Add a <code>RelatedLink</code> to this link container EModel.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void addChildLink(Object linkContainerEObject,
                                    Object linkObject) {
        if (!(linkObject instanceof RelatedLink)) {
            throw new IllegalArgumentException("Link should be a RelatedLink");
        }
        RelatedLink relatedLink = (RelatedLink)linkObject;
        if (linkContainerEObject instanceof RelatedFilterValue) {
            RelatedFilterValue relatedFilterValue = (RelatedFilterValue)linkContainerEObject;
            relatedFilterValue.addLink(relatedLink);
        } else if (linkContainerEObject instanceof SingleFilter) {
            SingleFilter singleFilter = (SingleFilter)linkContainerEObject;
            singleFilter.addRelatedLink(relatedLink);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "addValueToFilter",
        signature = "void addValueToFilter(Object filter,",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "The <code>SingleFilter</code> EModel instance."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterValueObject", type = "Object", desc = "A <code>FilterValue</code> object")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Add a filter value to this filter EModel.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void addValueToFilter(Object filter,
                                        Object filterValueObject) {
        if (!(filter instanceof SingleFilter)) {
            throw new IllegalArgumentException("Filter value should be a SingleFilter");
        }
        if (!(filterValueObject instanceof FilterValue)) {
            throw new IllegalArgumentException("Link should be a FilterValue");
        }
        FilterValue filterValue = (FilterValue)filterValueObject;
        SingleFilter singleFilter = (SingleFilter)filter;
        singleFilter.setFilterValue(filterValue);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setFilterOperator",
        signature = "void setFilterOperator(Object filter,",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "The <code>SingleFilter</code> EModel instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getBaseRuleTemplate",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set the operator associated with this single filter.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void setFilterOperator(Object filter,
                                         String operator) {
        if (!(filter instanceof SingleFilter)) {
            throw new IllegalArgumentException("Filter value should be a SingleFilter");
        }
        SingleFilter singleFilter = (SingleFilter)filter;
        singleFilter.setOperator(operator);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "serializeRuleTemplateInstance",
        signature = "Object serializeRuleTemplateInstance(Object ruleTemplateInstanceEObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateInstanceEObject", type = "Object", desc = "The RTI EObject instance to serialize to bytes.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Serialize this emf instance to bytes.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object serializeRuleTemplateInstance(Object ruleTemplateInstanceEObject) {
        if (!(ruleTemplateInstanceEObject instanceof RuleTemplateInstance)) {
            throw new IllegalArgumentException("RuleTemplateInstance cannot be null");
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //Serialize it to xml
        RuleTemplateInstanceSerializer ruleTemplateInstanceSerializer = new RuleTemplateInstanceSerializer((RuleTemplateInstance)ruleTemplateInstanceEObject);
        ruleTemplateInstanceSerializer.serialize();
        XiNode rootDoc = ruleTemplateInstanceSerializer.getSeralized();
        try {
            //True parameter is for pretty printing.
            XiSerializer.serialize(rootDoc, bos, "UTF-8", true);
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                //Ignore for now
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getRuleTemplateInstanceBindings",
        signature = "Object[] getRuleTemplateBindings(Object ruleTemplateObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateObject", type = "Object", desc = "The rule template instance model instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "an", desc = "Object[]"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getBaseRuleTemplate",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an array of bindings inside this RTI",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] getRuleTemplateInstanceBindings(Object ruleTemplateObject) {
        if (ruleTemplateObject instanceof  RuleTemplateInstance) {
            RuleTemplateInstance ruleTemplateInstance = (RuleTemplateInstance)ruleTemplateObject;
            List<Binding> bindings = ruleTemplateInstance.getBindings();
            return bindings.toArray();
        }
        return new Object[0];
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getRuleTemplateInstanceImplementsPath",
        signature = "String getRuleTemplateInstanceImplementsPath(Object ruleTemplateObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateObject", type = "Object", desc = "The rule template instance model instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "String"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getBaseRuleTemplate",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the path to the implemented rule template",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getRuleTemplateInstanceImplementsPath(Object ruleTemplateObject) {
        if (ruleTemplateObject instanceof  RuleTemplateInstance) {
            RuleTemplateInstance ruleTemplateInstance = (RuleTemplateInstance)ruleTemplateObject;
            return ruleTemplateInstance.getImplementsPath();
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getBindingId",
        signature = "String getBindingId(Object bindingObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bindingObject", type = "Object", desc = "The rule template instance binding object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "String"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns id of the passed <code>Binding</code>.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getBindingId(Object bindingObject) {
        if (bindingObject instanceof  Binding) {
            Binding binding = (Binding)bindingObject;
            return binding.getId();
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getBindingValue",
        signature = "String getBindingValue(Object bindingObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bindingObject", type = "Object", desc = "The rule template instance binding object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "String"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns value of the passed <code>Binding</code>.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getBindingValue(Object bindingObject) {
        if (bindingObject instanceof Binding) {
            Binding binding = (Binding)bindingObject;
            return binding.getValue();
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getBaseMultiFilter",
        signature = "Object getBaseMultiFilter(Object ruleTemplateInstanceEObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateInstanceEObject", type = "Object", desc = "The rule template instance EModel")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "an", desc = "Object"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getBaseRuleTemplate",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get base <code>MultiFilter</code> associated with conditions of this RTI.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object getBaseMultiFilter(Object ruleTemplateInstanceEObject) {
        if (ruleTemplateInstanceEObject instanceof  RuleTemplateInstance) {
            RuleTemplateInstance ruleTemplateInstance = (RuleTemplateInstance)ruleTemplateInstanceEObject;
            return ruleTemplateInstance.getConditionFilter();
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getChildFilterObjects",
        signature = "Object[] getChildFilterObjects(Object filterContainerEObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterContainerEObject", type = "Object", desc = "Filter container can be a <code>MultiFilter</code>, or a <code>Command</code> instance EModel")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "an", desc = "Object[] of filters."),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an array of filters inside this container (filter | command).",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] getChildFilterObjects(Object filterContainerEObject) {
        if (filterContainerEObject instanceof  MultiFilter) {
            MultiFilter multiFilter = (MultiFilter)filterContainerEObject;
            return multiFilter.getSubClause().getFilters().toArray();
        } else if (filterContainerEObject instanceof Command) {
            Command command = (Command)filterContainerEObject;
            BuilderSubClause builderSubClause = command.getSubClause();
            if (builderSubClause != null) {
                return builderSubClause.getFilters().toArray();
            }
        }
        return new Object[0];
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isMultiFilter",
        signature = "boolean isMultiFilter(Object filterEObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterEObject", type = "Object", desc = "The <code>Filter</code> instance EModel")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "boolean"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns if a filter model object is a <code>MultiFilter</code>",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean isMultiFilter(Object filterEObject) {
        return filterEObject instanceof MultiFilter;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getLinks",
        signature = "Object[] getLinks(Object linkContainerEObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "linkContainerEObject", type = "Object", desc = "Link container can be a <code>SingleFilter</code>, or a <code>RelatedFilterValue</code> instance EModel")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "an", desc = "Object[] of links."),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an array of links inside this container (filtervalue | filter).",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] getLinks(Object linkContainerEObject) {
        if (linkContainerEObject instanceof SingleFilter) {
            SingleFilter singleFilter = (SingleFilter)linkContainerEObject;
            return singleFilter.getLinks().toArray();
        } else if (linkContainerEObject instanceof RelatedFilterValue) {
            RelatedFilterValue relatedFilterValue = (RelatedFilterValue)linkContainerEObject;
            return relatedFilterValue.getLinks().toArray();
        }
        return new Object[0];
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getMatchType",
        signature = "String getMatchType(Object filterEObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterEObject", type = "Object", desc = "The <code>MultiFilter</code> instance EModel")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "String"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns match type associated with this filter.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getMatchType(Object filterEObject) {
        if (filterEObject instanceof MultiFilter) {
            MultiFilter multiFilter = (MultiFilter)filterEObject;
            return multiFilter.getMatchType();
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getFilterOperator",
        signature = "String getFilterOperator(Object filterEObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterEObject", type = "Object", desc = "The <code>SingleFilter</code> instance EModel")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "String"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns operator associated with this filter.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getFilterOperator(Object filterEObject) {
        if (filterEObject instanceof SingleFilter) {
            SingleFilter singleFilter = (SingleFilter)filterEObject;
            return singleFilter.getOperator();
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getGenericFilterValue",
        signature = "Object getGenericFilterValue(Object filterEObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterEObject", type = "Object", desc = "The <code>SingleFilter</code> instance EModel")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "an", desc = "Object"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the <code>FilterValue</code> associated with this filter.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object getGenericFilterValue(Object filterEObject) {
        if (filterEObject instanceof SingleFilter) {
            SingleFilter singleFilter = (SingleFilter)filterEObject;
            return singleFilter.getFilterValue();
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSingleFilterValue",
        signature = "String getSingleFilterValue(Object filterValueEObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterValueEObject", type = "Object", desc = "The <code>SimpleFilterValue</code> instance EModel")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "String"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getGenericFilterValue",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns value of this <code>SimpleFilterValue</code> EModel.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getSingleFilterValue(Object filterValueEObject) {
        if (filterValueEObject instanceof SimpleFilterValue) {
            SimpleFilterValue simpleFilterValue = (SimpleFilterValue)filterValueEObject;
            return simpleFilterValue.getValue();
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getLinkText",
        signature = "String getLinkText(Object linkObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "linkObject", type = "Object", desc = "The <code>RelatedLink</code> EModel")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "String"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getBaseRuleTemplate",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the link text(name) of the <code>RelatedLink</code>",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getLinkText(Object linkObject) {
        if (linkObject instanceof RelatedLink) {
            return ((RelatedLink)linkObject).getLinkText();
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getLinkType",
        signature = "String getLinkType(Object linkObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "linkObject", type = "Object", desc = "The <code>RelatedLink</code> EModel")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "String"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getBaseRuleTemplate",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the link type of the <code>RelatedLink</code>",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getLinkType(Object linkObject) {
        if (linkObject instanceof RelatedLink) {
            return ((RelatedLink)linkObject).getLinkType();
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isSimpleFilterValue",
        signature = "boolean isSimpleFilterValue(Object filterValueEObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterValueEObject", type = "Object", desc = "The <code>Filter</code> instance EModel")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "boolean"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns if a filter model object is simple or not.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean isSimpleFilterValue(Object filterValueEObject) {
        return filterValueEObject instanceof SimpleFilterValue;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getActionCommands",
        signature = "Object[] getActionCommands(Object ruleTemplateInstanceEObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateInstanceEObject", type = "Object", desc = "The rule template instance EModel")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "an", desc = "Object"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getBaseRuleTemplate",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an array of actions inside this RTI",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] getActionCommands(Object ruleTemplateInstanceEObject) {
        if (ruleTemplateInstanceEObject instanceof RuleTemplateInstance) {
            RuleTemplateInstance ruleTemplateInstance = (RuleTemplateInstance)ruleTemplateInstanceEObject;
            return ruleTemplateInstance.getActions().getActions().toArray();
        }
        return new Object[0];
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getActionCommandAlias",
        signature = "String getActionCommandAlias(Object commandEObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "commandEObject", type = "Object", desc = "The Command EModel")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "an", desc = "Object"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the alias of this command.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getActionCommandAlias(Object commandEObject) {
        if (commandEObject instanceof Command) {
            return ((Command)commandEObject).getAlias();
        }
        return null;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getRuleTemplateInstanceId",
        signature = "getRuleTemplateInstanceId(Object ruleTemplateInstanceEObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateInstanceEObject", type = "Object", desc = "The RTI EObject instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Id"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the id of the Rule Template instance.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getRuleTemplateInstanceId(Object ruleTemplateInstanceEObject) {
        if (!(ruleTemplateInstanceEObject instanceof RuleTemplateInstance)) {
            throw new IllegalArgumentException("RuleTemplateInstance cannot be null");
        }
        
        return ((RuleTemplateInstance)ruleTemplateInstanceEObject).getId();
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getRuleTemplateInstanceDescription",
        signature = "String getRuleTemplateInstanceDescription(Object ruleTemplateInstanceEObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateInstanceEObject", type = "Object", desc = "The RTI EObject instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Description"),
        version = "5.4.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get description of the Rule Template instance.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getRuleTemplateInstanceDescription(Object ruleTemplateInstanceEObject) {
        if (!(ruleTemplateInstanceEObject instanceof RuleTemplateInstance)) {
            throw new IllegalArgumentException("RuleTemplateInstance cannot be null");
        }
        
        return ((RuleTemplateInstance)ruleTemplateInstanceEObject).getDescription();
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getRuleTemplatePriority",
        signature = "int getRuleTemplatePriority(Object ruleTemplateInstanceEObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateInstanceEObject", type = "Object", desc = "The RTI EObject instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Rule Template Instance Priority"),
        version = "5.4.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get priority of the Rule Template instance.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static int getRuleTemplateInstancePriority(Object ruleTemplateInstanceEObject) {
        if (!(ruleTemplateInstanceEObject instanceof RuleTemplateInstance)) {
            throw new IllegalArgumentException("RuleTemplateInstance cannot be null");
        }
        
        return ((RuleTemplateInstance)ruleTemplateInstanceEObject).getPriority();
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "setRuleTemplateInstanceDescription",
        signature = "void setRuleTemplateInstanceDescription(Object ruleTemplateInstanceEObject, String description)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateInstanceEObject", type = "Object", desc = "The RTI EObject instance."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "description", type = "String", desc = "Description.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.4.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set Rule Template instance description.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void setRuleTemplateInstanceDescription(Object ruleTemplateInstanceEObject, String description) {
        if (!(ruleTemplateInstanceEObject instanceof RuleTemplateInstance)) {
            throw new IllegalArgumentException("RuleTemplateInstance cannot be null");
        }
        
        ((RuleTemplateInstance)ruleTemplateInstanceEObject).setDescription(description);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "setRuleTemplateInstancePriority",
        signature = "void setRuleTemplateInstancePriority(Object ruleTemplateInstanceEObject, String description)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateInstanceEObject", type = "Object", desc = "The RTI EObject instance."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "priority", type = "int", desc = "Priority.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.4.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set Rule Template instance priority.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void setRuleTemplateInstancePriority(Object ruleTemplateInstanceEObject, int priority) {
        if (!(ruleTemplateInstanceEObject instanceof RuleTemplateInstance)) {
            throw new IllegalArgumentException("RuleTemplateInstance cannot be null");
        }
        
        ((RuleTemplateInstance)ruleTemplateInstanceEObject).setPriority(priority);
    }
}
