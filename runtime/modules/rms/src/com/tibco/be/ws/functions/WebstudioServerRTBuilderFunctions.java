package com.tibco.be.ws.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.ws.functions.util.WebstudioFunctionUtils;
import com.tibco.be.ws.rt.RuleTemplateASTNodeVisitor;
import com.tibco.be.ws.rt.model.AbstractSymbolChildNode;
import com.tibco.be.ws.rt.model.ContainerSymbolChildNode;
import com.tibco.be.ws.rt.model.SimpleSymbolChildNode;
import com.tibco.be.ws.rt.model.SymbolChildNodeFactory;
import com.tibco.be.ws.rt.model.builder.FilterQueueResolver;
import com.tibco.be.ws.rt.model.builder.IFilterComponent;
import com.tibco.be.ws.rt.model.builder.ast.IFilterLinkDescriptor;
import com.tibco.be.ws.rt.model.builder.ast.impl.AbstractFilterLinkDescriptor;
import com.tibco.be.ws.rt.model.builder.ast.impl.ExpressionLinkDescriptor;
import com.tibco.be.ws.scs.ISCSIntegration;
import com.tibco.be.ws.scs.SCSIntegrationFactory;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.rule.ActionContextSymbol;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.DefaultProblemHandler;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/4/12
 * Time: 6:17 PM
 *
 * @.category WS.RT.Builder
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.RT.Builder",
        synopsis = "WebStudio Rule Template Builder Functions",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.RT.Builder", value=true))

public class WebstudioServerRTBuilderFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "getRuleTemplateBaseSymbols",
        synopsis = "",
        signature = "Object[] getRuleTemplateBaseSymbols(Object ruleTemplateObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateObject", type = "Object", desc = "The rule template EMF model instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "an", desc = "Object[]"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getBaseRuleTemplate",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an array of symbols in scope of this RT/RTI",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] getRuleTemplateBaseSymbols(Object ruleTemplateObject) {
        if (!(ruleTemplateObject instanceof RuleTemplate)) {
            throw new IllegalArgumentException("Passed argument should be a ruletemplate");
        }
        RuleTemplate ruleTemplate = (RuleTemplate) ruleTemplateObject;
        Symbols ruleTemplateSymbols = ruleTemplate.getSymbols();
        List<Symbol> symbolList = ruleTemplateSymbols.getSymbolList();

        return symbolList.toArray();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSymbolAlias",
        synopsis = "",
        signature = "String getSymbolAlias(Object symbolNodeObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolNodeObject", type = "Object", desc = "An instance of <code>AbstractSymbolChildNode</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "String"),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.getRuleTemplateBaseSymbols",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the alias of the symbol child node",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getSymbolAlias(Object symbolNodeObject) {
        if (!(symbolNodeObject instanceof AbstractSymbolChildNode)) {
            throw new IllegalArgumentException("Passed argument should be a AbstractSymbolChildNode");
        }
        AbstractSymbolChildNode symbolNode = (AbstractSymbolChildNode) symbolNodeObject;
        return symbolNode.getAlias();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSymbolType",
        synopsis = "",
        signature = "String getSymbolType(Object symbolNodeObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "To support a custom SCS client impl."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "The root of the SCS repo."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The name of the project."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolNodeObject", type = "Object", desc = "An instance of <code>AbstractSymbolChildNode</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "earPath", type = "String", desc = "Ear Path")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "String"),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.getRuleTemplateBaseSymbols",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the type of the symbol child node. Either a primitive or a rule participant path.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getSymbolType(String scsIntegrationType,
                                       String repoRootURL,
                                       String projectName,
                                       Object symbolNodeObject,
                                       String earPath) {
        if (!(symbolNodeObject instanceof AbstractSymbolChildNode)) {
            throw new IllegalArgumentException("Passed argument should be a AbstractSymbolChildNode");
        }
        AbstractSymbolChildNode symbolNode = (AbstractSymbolChildNode) symbolNodeObject;
        String symbolType = symbolNode.getType();
        try {        	
            ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
            String extension =
                    WebstudioFunctionUtils.resolveSymbolTypeExtension(scsIntegration, repoRootURL, projectName, symbolNode, earPath);
            if (extension != null) {
                symbolType = symbolType + "." + extension;
            }
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
        return symbolType;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "_getSymbolType_",
        synopsis = "",
        signature = "String _getSymbolType_(Object symbolNodeObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "To support a custom SCS client impl."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "The root of the SCS repo."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The name of the project."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolObject", type = "Object", desc = "An instance of <code>Symbol</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "earPath", type = "String", desc = "Ear Path")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "String"),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.getRuleTemplateBaseSymbols",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the type of the RT symbol. Either a primitive or a rule participant path.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String _getSymbolType_(String scsIntegrationType,
                                         String repoRootURL,
                                         String projectName,
                                         Object symbolObject,
                                         String earPath) {
        if (!(symbolObject instanceof Symbol)) {
            throw new IllegalArgumentException("Passed argument should be a Symbol");
        }
        Symbol symbolNode = (Symbol) symbolObject;
        String symbolType = symbolNode.getType();
        try {
            ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
            String extension =
                    WebstudioFunctionUtils.resolveSymbolTypeExtension(scsIntegration, repoRootURL, projectName, symbolNode, earPath);
            if (extension != null) {
                symbolType = symbolType + "." + extension;
            }
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
        return symbolType;
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "_getSymbolAlias_",
        synopsis = "",
        signature = "String _getSymbolAlias_(Object symbolObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolObject", type = "Object", desc = "An instance of <code>Symbol</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "String"),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.getRuleTemplateBaseSymbols",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the alias of the RT symbol.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String _getSymbolAlias_(Object symbolObject) {
        if (!(symbolObject instanceof Symbol)) {
            throw new IllegalArgumentException("Passed argument should be a Symbol");
        }
        Symbol symbolNode = (Symbol) symbolObject;
        return symbolNode.getIdName();
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "createSymbolNode",
        synopsis = "",
        signature = "Object createSymbolNode(Object symbolObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolObject", type = "Object", desc = "An instance of <code>Symbol</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "an", desc = "Object"),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.getRuleTemplateBaseSymbols",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a <code>AbstractSymbolChildNode</code> for this symbol.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object createSymbolNode(Object symbolObject) {
        if (!(symbolObject instanceof Symbol)) {
            throw new IllegalArgumentException("Passed argument should be a Symbol");
        }
        Symbol symbol = (Symbol) symbolObject;
        //TODO handle basic primitives
        return SymbolChildNodeFactory.createSymbolChildNode(symbol);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isSimpleSymbolNode",
        synopsis = "",
        signature = "Object createSymbolNode(Object symbolObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolNode", type = "Object", desc = "An instance of <code>AbstractSymbolChildNode</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "boolean"),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.createSymbolNode",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns true if the object represents a property node or a primitive type node.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean isSimpleSymbolNode(Object symbolNode) {
        //TODO Handle primitives.
        return symbolNode instanceof SimpleSymbolChildNode;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setNodeVisited",
        synopsis = "",
        signature = "void setNodeVisited(Object symbolNodeObject, boolean visited)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolNodeObject", type = "Object", desc = "An instance of <code>AbstractSymbolChildNode</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "visited", type = "boolean", desc = "To mark the visited status.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.createSymbolNode",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set the visited flag on this symbolNode Object in the tree to true|false.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void setNodeVisited(Object symbolNodeObject, boolean visited) {
        if (!(symbolNodeObject instanceof AbstractSymbolChildNode)) {
            throw new IllegalArgumentException("Passed argument should be a AbstractSymbolChildNode");
        }
        AbstractSymbolChildNode symbolNode = (AbstractSymbolChildNode) symbolNodeObject;
        symbolNode.setVisited(visited);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "isNodeVisited",
        synopsis = "",
        signature = "void setNodeVisited(Object symbolNodeObject, boolean visited)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolNodeObject", type = "Object", desc = "An instance of <code>AbstractSymbolChildNode</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "boolean"),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.createSymbolNode",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Check the visited flag on this symbolNode Object in the tree.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean isNodeVisited(Object symbolNodeObject) {
        if (!(symbolNodeObject instanceof AbstractSymbolChildNode)) {
            throw new IllegalArgumentException("Passed argument should be a AbstractSymbolChildNode");
        }
        AbstractSymbolChildNode symbolNode = (AbstractSymbolChildNode) symbolNodeObject;
        return symbolNode.isVisited();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "resolveContainedEntity",
        synopsis = "",
        signature = "void resolveContainedEntity(String scsIntegrtaionClass,\nString repoRootURL,\nString projectName,\nObject symbolNodeObject, \nboolean resolveEntityAttrs)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "To support a custom SCS client impl."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "The root of the SCS repo."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The name of the project."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolNodeObject", type = "Object", desc = "An object <code>ContainerSymbolChildNode</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isCommandSymbol", type = "boolean", desc = "If true, exclude attributes like id, length from the list of symbols"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "earPath", type = "String", desc = "Ear path for the project")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.createSymbolNode",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Resolve the rule participant entity denoted by this symbolNodeObject and load its contents.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void resolveContainedEntity(String scsIntegrationType,
                                              String repoRootURL,
                                              String projectName,
                                              Object symbolNodeObject,
                                              boolean isCommandSymbol,
                                              String earPath) {
        if (!(symbolNodeObject instanceof ContainerSymbolChildNode)) {
            throw new IllegalArgumentException("Passed argument should be a ContainerSymbolChildNode");
        }
        ContainerSymbolChildNode symbolNode = (ContainerSymbolChildNode) symbolNodeObject;
        try {
            symbolNode.resolveEntity(scsIntegrationType, repoRootURL, projectName, isCommandSymbol, earPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSymbolNodeUnvisitedChildren",
        synopsis = "",
        signature = "Object[] getSymbolNodeUnvisitedChildren(Object symbolNodeObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolNodeObject", type = "Object", desc = "An object <code>AbstractSymbolChildNode</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = ""),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.createSymbolNode",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get immediate children of this symbolNodeObject with their visited flag set to false.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] getSymbolNodeUnvisitedChildren(Object symbolNodeObject) {
        if (!(symbolNodeObject instanceof AbstractSymbolChildNode)) {
            throw new IllegalArgumentException("Passed argument should be a AbstractSymbolChildNode");
        }
        AbstractSymbolChildNode symbolNode = (AbstractSymbolChildNode) symbolNodeObject;
        Object[] children = new Object[0];

        if (symbolNode instanceof ContainerSymbolChildNode) {
            List<AbstractSymbolChildNode> unvisitedChildren = ((ContainerSymbolChildNode) symbolNode).getUnvisitedChildren();
            children = unvisitedChildren.toArray();
        }
        return children;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "hasUnvisitedChildren",
        synopsis = "",
        signature = "Object[] getSymbolNodeUnvisitedChildren(Object symbolNodeObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolNodeObject", type = "Object", desc = "An object <code>AbstractSymbolChildNode</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.createSymbolNode",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return true if there is at least one immediate child of this object whose visited flag is true.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean hasUnvisitedChildren(Object symbolNodeObject) {
        boolean hasUnvisitedChildren = false;

        if (symbolNodeObject instanceof ContainerSymbolChildNode) {
            hasUnvisitedChildren = ((ContainerSymbolChildNode) symbolNodeObject).hasUnvisitedChildren();
        }
        return hasUnvisitedChildren;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getGUID",
        synopsis = "",
        signature = "String getGUID(Object symbolNodeObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolNodeObject", type = "Object", desc = "An object <code>AbstractSymbolChildNode</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.createSymbolNode",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return a unique identifier for this symbolNodeObject.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getGUID(Object symbolNodeObject) {
        if (!(symbolNodeObject instanceof AbstractSymbolChildNode)) {
            throw new IllegalArgumentException("Passed argument should be a AbstractSymbolChildNode");
        }
        AbstractSymbolChildNode symbolNode = (AbstractSymbolChildNode) symbolNodeObject;
        return symbolNode.getGUID();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getFilterResolvers",
        synopsis = "",
        signature = "Object[] getFilterResolvers(String scsIntegrationType,\nString repoRootURL,\nString projectName,\nObject ruleTemplateObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Name of the project"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateObject", type = "Object", desc = "The ruletemplate EModel object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.getBaseRuleTemplate",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get filterqueueresolver objects for all filter conditions in this rule template.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] getFilterResolvers(String projectName,
                                              Object ruleTemplateObject) {
        if (!(ruleTemplateObject instanceof RuleTemplate)) {
            throw new IllegalArgumentException("Passed argument should be a ruletemplate");
        }
        RuleTemplate ruleTemplate = (RuleTemplate) ruleTemplateObject;
        String conditionText = ruleTemplate.getConditionText();
        //Parse it
        RulesASTNode rulesASTNode =
                (RulesASTNode) CommonRulesParserManager.parseConditionsBlockString(projectName, conditionText, new DefaultProblemHandler());
        RuleTemplateASTNodeVisitor newVisitor = new RuleTemplateASTNodeVisitor();
        rulesASTNode.accept(newVisitor);
        List<AbstractFilterLinkDescriptor> filterLinkDescriptors = newVisitor.getFilterLinkDescriptors();
        List<FilterQueueResolver> filterQueueResolvers = new ArrayList<FilterQueueResolver>(filterLinkDescriptors.size());
        for (AbstractFilterLinkDescriptor filterLinkDescriptor : filterLinkDescriptors) {
            //Get its queue
            Queue<IFilterLinkDescriptor> queue = resolveDescriptor(filterLinkDescriptor);
            FilterQueueResolver queueResolver = new FilterQueueResolver(queue, ruleTemplate.getSymbols().getSymbolList(), ruleTemplate.getBindings());
            filterQueueResolvers.add(queueResolver);
        }
        return filterQueueResolvers.toArray();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getRelationalOperator",
        synopsis = "",
        signature = "String getRelationalOperator(Object filterQueueResolverObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterQueueResolverObject", type = "Object", desc = "An object <code>FilterQueueResolver</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.getFilterResolvers",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the relational operator used in this filter condition.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getRelationalOperator(Object filterQueueResolverObject) {
        if (!(filterQueueResolverObject instanceof FilterQueueResolver)) {
            throw new IllegalArgumentException("Passed argument should be a FilterQueueResolver");
        }
        FilterQueueResolver filterQueueResolver = (FilterQueueResolver) filterQueueResolverObject;
        return filterQueueResolver.resolveRelationalOperator();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getFilterLHSComponents",
        synopsis = "",
        signature = "Object[] getFilterLHSComponents(String scsIntegrationType,\nString repoRootURL,\nString projectName,\nObject filterQueueResolverObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "SCS connector class to be used."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Root SCS url"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Name of the project"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterQueueResolverObject", type = "Object", desc = "An object <code>FilterQueueResolver</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "earPath", type = "String", desc = "Ear Path")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.getFilterResolvers",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get <code>IFilterComponent</code> objects for each filterqueueresolver for LHS.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] getFilterLHSComponents(String scsIntegrationType,
                                                  String repoRootURL,
                                                  String projectName,
                                                  Object filterQueueResolverObject,
                                                  String earPath) {
        if (!(filterQueueResolverObject instanceof FilterQueueResolver)) {
            throw new IllegalArgumentException("Passed argument should be a FilterQueueResolver");
        }
        FilterQueueResolver filterQueueResolver = (FilterQueueResolver) filterQueueResolverObject;
        try {
            ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
            List<IFilterComponent> filterComponents =
                    filterQueueResolver.resolveLHS(scsIntegration, repoRootURL, projectName, earPath);
            return filterComponents.toArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getFilterRHSComponents",
        synopsis = "",
        signature = "Object[] getFilterLHSComponents(String scsIntegrationType,\nString repoRootURL,\nString projectName,\nObject filterQueueResolverObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "SCS connector type to be used."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Root SCS url"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Name of the project"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterQueueResolverObject", type = "Object", desc = "An object <code>FilterQueueResolver</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
        version = "5.1.0",
        see = "WebstudioServerRTBuilderFunctions.getFilterResolvers",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get <code>IFilterComponent</code> objects for each filterqueueresolver for RHS.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] getFilterRHSComponents(String scsIntegrationType,
                                                  String repoRootURL,
                                                  String projectName,
                                                  Object filterQueueResolverObject,
                                                  String earPath) {
        if (!(filterQueueResolverObject instanceof FilterQueueResolver)) {
            throw new IllegalArgumentException("Passed argument should be a FilterQueueResolver");
        }
        FilterQueueResolver filterQueueResolver = (FilterQueueResolver) filterQueueResolverObject;
        try {
            ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
            List<IFilterComponent> filterComponents =
                    filterQueueResolver.resolveRHS(scsIntegration, repoRootURL, projectName, earPath);
            return filterComponents.toArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getFilterComponentType",
        synopsis = "",
        signature = "int getFilterComponentType(Object filterComponentObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterComponentObject", type = "Object", desc = "An object <code>IFilterComponent</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get id for filter component.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static int getFilterComponentType(Object filterComponentObject) {
        if (filterComponentObject instanceof IFilterComponent) {
            return ((IFilterComponent) filterComponentObject).getId();
        }
        return -1;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getBuilderCommands",
        synopsis = "",
        signature = "Object[] getBuilderCommands(Object ruleTemplateObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateObject", type = "Object", desc = "An instance of the <code>RuleTemplate</code> object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get commands inside this RT builder in form of symbols.",
        cautions = "Only basic create | modify | call commands supported.",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] getBuilderCommands(Object ruleTemplateObject) {
        if (!(ruleTemplateObject instanceof RuleTemplate)) {
            throw new IllegalArgumentException("Passed argument should be a ruletemplate");
        }
        RuleTemplate ruleTemplate = (RuleTemplate) ruleTemplateObject;
        Symbols actionContextSymbols = ruleTemplate.getActionContext().getActionContextSymbols();
        if (actionContextSymbols != null) {
            return actionContextSymbols.getSymbolList().toArray();
        }
        return new Object[0];
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getCommandAlias",
        synopsis = "",
        signature = "String getCommandAlias(Object actionContextSymbolObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "actionContextSymbolObject", type = "Object", desc = "An instance of the <code>ActionContextSymbol</code> EMF object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get alias of this command.",
        cautions = "Only basic create | modify | call commands supported.",
        fndomain = {ACTION},
        example = ""
    )
    public static String getCommandAlias(Object actionContextSymbolObject) {
        if (!(actionContextSymbolObject instanceof ActionContextSymbol)) {
            throw new IllegalArgumentException("Passed argument should be a valid symbol in action context");
        }
        ActionContextSymbol actionContextSymbol = (ActionContextSymbol)actionContextSymbolObject;
        return actionContextSymbol.getIdName();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getCommandType",
        synopsis = "",
        signature = "String getCommandType(Object actionContextSymbolObject)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "To support a custom SCS client impl."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "The root of the SCS repo."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The name of the project."),	
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "actionContextSymbolObject", type = "Object", desc = "An instance of the <code>RuleTemplate</code> object."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "earPath", type = "String", desc = "Ear Path")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get type on which this command operates. e.g : create Concepts.Account account",
        cautions = "Only basic create | modify | call commands supported.",
        fndomain = {ACTION},
        example = ""
    )
    public static String getCommandType(String scsIntegrationType,
                                        String repoRootURL,
                                        String projectName,
                                        Object actionContextSymbolObject,
                                        String earPath) {
        if (!(actionContextSymbolObject instanceof ActionContextSymbol)) {
            throw new IllegalArgumentException("Passed argument should be a valid symbol in action context");
        }
        ActionContextSymbol actionContextSymbol = (ActionContextSymbol)actionContextSymbolObject;
        String type = actionContextSymbol.getType();
        //Try and resolve extension
        try {
            ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
            String extension =
                    WebstudioFunctionUtils.resolveSymbolTypeExtension(scsIntegration, repoRootURL, projectName, actionContextSymbol, earPath);
            if (extension != null) {
                type = type + "." + extension;
            }
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
        return type;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getCommandActionType",
        synopsis = "",
        signature = "String getCommandActionType(Object actionContextSymbolObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "actionContextSymbolObject", type = "Object", desc = "An instance of the <code>RuleTemplate</code> object.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get action performed by this command. (create|modify|call)",
        cautions = "Only basic create | modify | call commands supported.",
        fndomain = {ACTION},
        example = ""
    )
    public static String getCommandActionType(Object actionContextSymbolObject) {
        if (!(actionContextSymbolObject instanceof ActionContextSymbol)) {
            throw new IllegalArgumentException("Passed argument should be a valid symbol in action context");
        }
        ActionContextSymbol actionContextSymbol = (ActionContextSymbol)actionContextSymbolObject;
        return actionContextSymbol.getActionType().getLiteral();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getFilterComponentChildren",
        signature = "int getFilterComponentChildren(Object filterComponentObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filterComponentObject", type = "Object", desc = "An object <code>IFilterComponent</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get children for a filter component.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] getFilterComponentChildren(Object filterComponentObject) {
        Object[] children = new Object[0];
        if (filterComponentObject instanceof IFilterComponent) {
            children = ((IFilterComponent) filterComponentObject).getChildren().toArray();
        }
        return children;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getDisplayProperties",
        signature = "Object[] getDisplayProperties(Object ruleTemplateObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateObject", type = "Object", desc = "An object <code>RuleTemplate</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the display properties.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] getDisplayProperties(Object ruleTemplateObject) {
        if (!(ruleTemplateObject instanceof RuleTemplate)) {
            throw new IllegalArgumentException("Passed argument should be a ruletemplate");
        }
        RuleTemplate ruleTemplate = (RuleTemplate) ruleTemplateObject;
        EList<SimpleProperty> displayProperties = ruleTemplate.getDisplayProperties();
        
        return displayProperties.toArray();
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getDisplayPropertyKey",
        signature = "String getDisplayPropertyKey(Object displayPropertyObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "displayPropertyObject", type = "Object", desc = "An object <code>SimpleProperty</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the display property key.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getDisplayPropertyKey(Object displayPropertyObject) {
    	if (!(displayPropertyObject instanceof SimpleProperty)) {
            throw new IllegalArgumentException("Passed argument should be a SimpleProperty");
        }
    	SimpleProperty displayProperty = (SimpleProperty) displayPropertyObject;
    	return displayProperty.getName();
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getDisplayPropertyValue",
        signature = "String getDisplayPropertyValue(Object displayPropertyObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "displayPropertyObject", type = "Object", desc = "An object <code>SimpleProperty</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the display property value.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getDisplayPropertyValue(Object displayPropertyObject) {
    	if (!(displayPropertyObject instanceof SimpleProperty)) {
            throw new IllegalArgumentException("Passed argument should be a SimpleProperty");
        }
    	SimpleProperty displayProperty = (SimpleProperty) displayPropertyObject;
    	String displayPropValue = null;
    	try {
    		displayPropValue = URLEncoder.encode(displayProperty.getValue(), "UTF-8");
    		if (displayPropValue.indexOf("+") > 0 ) {
    			displayPropValue = displayPropValue.replace("+", "%20");
    		}
    	} catch (Exception e) {
			throw new RuntimeException(e);
		}
    	return displayPropValue;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getDisplayPropertyType",
        signature = "String getDisplayPropertyType(Object displayPropertyObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "displayPropertyObject", type = "Object", desc = "An object <code>SimpleProperty</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the display property Type.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getDisplayPropertyType(Object displayPropertyObject) {
    	if (!(displayPropertyObject instanceof SimpleProperty)) {
            throw new IllegalArgumentException("Passed argument should be a SimpleProperty");
        }
    	SimpleProperty displayProperty = (SimpleProperty) displayPropertyObject;
    	return displayProperty.getFolder();
    }
    
    private static Queue<IFilterLinkDescriptor> resolveDescriptor(AbstractFilterLinkDescriptor filterLinkDescriptor) {
        Queue<IFilterLinkDescriptor> queue = new LinkedList<IFilterLinkDescriptor>();
        AbstractFilterLinkDescriptor tempDescriptor = filterLinkDescriptor;
        while (tempDescriptor.nextDescriptor != null) {
            if (!(tempDescriptor instanceof ExpressionLinkDescriptor)) {
                queue.offer(tempDescriptor);
            }
            tempDescriptor = (AbstractFilterLinkDescriptor) tempDescriptor.nextDescriptor;
        }
        //Push last one
        queue.offer(tempDescriptor);
        return queue;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getSymbolDomainPaths",
        signature = "String[] getSymbolDomainPaths(Object symbolNodeObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolObject", type = "Object", desc = "An instance of <code>Symbol</code>")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "Associated Domain Paths"),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the Domain model paths associated to Symbols.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String[] getSymbolDomainPaths(Object symbolNodeObject) {
        if (!(symbolNodeObject instanceof AbstractSymbolChildNode)) {
            throw new IllegalArgumentException("Passed argument should be a AbstractSymbolChildNode");
        }
        AbstractSymbolChildNode symbolNode = (AbstractSymbolChildNode) symbolNodeObject;
        
        return symbolNode.getDomainModelPath();
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "isSymbolInfoLoaded",
        signature = "boolean isSymbolInfoLoaded(String artifactPath, String artifactType, String projectName, String parentArtifactPath, String scsIntegrationClass, String repoRootURL)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactPath", type = "String", desc = "Artifact Path to look up"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactType", type = "String", desc = "Artifact Type"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Project Name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "parentArtifactPath", type = "String", desc = "Parent Artifact Path to match against"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationClass", type = "String", desc = "SCS Integration class"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Repository Path")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Is Symbol already loaded"),
        version = "5.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Check if the symbol associated to the parent artifact path is already loaded.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean isSymbolInfoLoaded(String artifactPath, String artifactType, String projectName, String parentArtifactPath, String scsIntegrationClass,
            String repoRootURL) {
    	boolean isLoaded = false;
    	try {
    		ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationClass);
    		isLoaded = checkParentPath(artifactPath, artifactType, projectName, parentArtifactPath, scsIntegration, repoRootURL);
    	} catch(Exception exception) {
    		throw new RuntimeException(exception);
    	}
    	
    	return isLoaded;
    }
    
    private static boolean checkParentPath(String artifactPath, String artifactType, String projectName, String parentArtifactPath, ISCSIntegration scsIntegration, String repoRootURL) throws Exception {
    	String entityContents = scsIntegration.showFileContents(repoRootURL, projectName, artifactPath, artifactType, null, null);
    	Entity baseRuleParticipant = (Entity)CommonIndexUtils.deserializeEObjectFromString(entityContents);
    	
    	String superPath = null;
    	if (baseRuleParticipant instanceof Concept) {
    		Concept ruleParticipant = (Concept)baseRuleParticipant;
    		superPath = ruleParticipant.getSuperConceptPath();
    	} else if (baseRuleParticipant instanceof SimpleEvent) {
    		SimpleEvent ruleParticipant = (SimpleEvent)baseRuleParticipant;
    		superPath = ruleParticipant.getSuperEventPath();
    	}

    	if (superPath != null && !superPath.isEmpty()) {
    		if (superPath.equals(parentArtifactPath)) {
    			return true;
    		} else {
    			return checkParentPath(superPath, artifactType, projectName, parentArtifactPath, scsIntegration, repoRootURL);
    		}
    	}
    	
    	return false;
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "isPrimitiveType",
        signature = "boolean isPrimitiveType(String symbolType)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolType", type = "String", desc = "Symbol Type"),
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Is Symbol type primitive"),
        version = "5.5",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Check if the symbol type is primitive.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean isPrimitiveType(String symbolType) {
    	return PROPERTY_TYPES.getByName(symbolType) != null ? true : false;
    }


    public static void main(String[] r) throws Exception {
//        String projectName = "CreditCardApplication";
//        FileInputStream fis = new FileInputStream("/media/Windows7/dev/be/5.1/examples/decision_manager/CreditCardApplication/CreditCardApplication/Rule_Templates/Applicant_PreScreen_WithoutView.ruletemplate");
//        RulesASTNode astNode = (RulesASTNode) CommonRulesParserManager.parseRuleInputStream(projectName, fis, true);
//        RuleCreatorASTVisitor aSTVisitor = new RuleCreatorASTVisitor(true, true, projectName);
//        astNode.accept(aSTVisitor);
//
//        RuleTemplate ruleTemplate = (RuleTemplate) aSTVisitor.getRule();
//        List<Symbol> symbols = ruleTemplate.getActionContext().getActionContextSymbols().getSymbolList();
//        //Get condition
//        String conditionText = ruleTemplate.getActionText();
//        //Parse
//        RulesASTNode conditionsNode = (RulesASTNode) CommonRulesParserManager.parseConditionsBlockString(projectName, conditionText, null);
//        RuleTemplateASTNodeVisitor newVisitor = new RuleTemplateASTNodeVisitor();
//        conditionsNode.accept(newVisitor);
//        List<AbstractFilterLinkDescriptor> filterLinkDescriptors = newVisitor.getFilterLinkDescriptors();
//        for (AbstractFilterLinkDescriptor d : filterLinkDescriptors) {
//            System.out.println(d);
//            resolveDescriptor(d);
//        }
//        Object filterStacks =
//                getFilterResolvers(null, "/media/Windows7/dev/be/5.1/examples/decision_manager/CreditCardApplication", "CreditCardApplication", ruleTemplate);

//        for (Symbol rtSymbol : symbols) {
//            System.out.println(rtSymbol.getType());
//            System.out.println(rtSymbol.getIdName());
//        }
    }

    
}
