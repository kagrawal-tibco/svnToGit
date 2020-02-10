package com.tibco.be.ws.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static com.tibco.cep.studio.core.index.utils.CommonIndexUtils.RULE_TEMPLATE_VIEW_EXTENSION;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 19/3/12
 * Time: 3:31 PM
 *
 * @.category WS.RT.View
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.RT.View",
        synopsis = "Functions For Webstudio Rule Template View",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.RT.View", value=true))

public class WebstudioServerRTViewFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "getRuleTemplateBindings",
        synopsis = "",
        signature = "Object[] getRuleTemplateBindings(Object ruleTemplateObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateObject", type = "Object", desc = "The rule template model instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "an", desc = "Object[]"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getBaseRuleTemplate",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an array of bindings inside this RT/RTI",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] getRuleTemplateBindings(Object ruleTemplateObject) {
        if (ruleTemplateObject instanceof RuleTemplate) {
            RuleTemplate ruleTemplate = (RuleTemplate) ruleTemplateObject;
            List<Binding> bindings = ruleTemplate.getBindings();
            return bindings.toArray();
        }
        return new Object[0];
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getPresentationText",
        signature = "String getPresentationText(Object ruleTemplateObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The SCS integration type to be used. Default is file for null value."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "The SCS root url."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The name of the project"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateObject", type = "Object", desc = "The rule template model instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "String"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getBaseRuleTemplate",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns string representing html presentation text associated with this RT/RTI",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getPresentationText(String scsIntegrationType,
                                             String repoRootURL,
                                             String projectName,
                                             Object ruleTemplateObject) {
        if (!(ruleTemplateObject instanceof RuleTemplate)) {
            throw new IllegalArgumentException("Passed argument should be a ruletemplate");
        }
        RuleTemplate ruleTemplate = (RuleTemplate) ruleTemplateObject;
        StringBuilder stringBuilder = new StringBuilder();

        for (String viewPath : ruleTemplate.getViews()) {
            //TODO check if loaded in WS already.
            String ruleTemplateViewContents =
                WebstudioServerSCSFunctions.showArtifactContents(scsIntegrationType,
                                                              repoRootURL,
                                                              projectName,
                                                              viewPath,
                                                              RULE_TEMPLATE_VIEW_EXTENSION,
                                                              null, null);
            try {
                RuleTemplateView ruleTemplateView = (RuleTemplateView)CommonIndexUtils.deserializeEObjectFromString(ruleTemplateViewContents);
                String htmlFilePath = ruleTemplateView.getHtmlFile();
                if (htmlFilePath != null && htmlFilePath.trim().length() > 0) {
                	File htmlFile = new File(repoRootURL+File.separator+projectName, htmlFilePath);
                	if (htmlFile.exists()) {
                		FileInputStream fis = null;
                		try {
                			fis = new FileInputStream(htmlFile);
                			byte[] arr = new byte[fis.available()];
                			fis.read(arr);
                			stringBuilder.append(new String(arr));
                			return stringBuilder.toString();
						} catch (Exception e) {
						} finally {
							fis.close();
						}
                	}
                }
                stringBuilder.append(ruleTemplateView.getPresentationText());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return stringBuilder.toString();
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getAssociatedRuleTemplate",
            signature = "String getAssociatedRuleTemplate(String scsIntegrationType, String repoRootURL, String projectName, String ruleTemplateViewPath)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The SCS integration type to be used. Default is file for null value."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "The SCS root url."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The name of the project"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateViewPath", type = "String", desc = "The rule template view path.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "Rule Template Path"),
            version = "5.5.0",
            see = "WebstudioServerRTViewFunctions.getAssociatedRuleTemplate",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the associated Rule Template path",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
        public static String getAssociatedRuleTemplate(String scsIntegrationType,
                                                 String repoRootURL,
                                                 String projectName,
                                                 String ruleTemplateViewPath) {
    	String ruleTemplatePath = null;
    	try {
    		String ruleTemplateViewContent = WebstudioServerSCSFunctions.showArtifactContents(scsIntegrationType,
    				repoRootURL,
    				projectName,
    				ruleTemplateViewPath,
    				RULE_TEMPLATE_VIEW_EXTENSION,
    				null, null);
    		 RuleTemplateView ruleTemplateView = (RuleTemplateView)CommonIndexUtils.deserializeEObjectFromString(ruleTemplateViewContent);
    		 if (ruleTemplateView != null) ruleTemplatePath = ruleTemplateView.getRuleTemplatePath();
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    	return ruleTemplatePath;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getBindingType",
        synopsis = "",
        signature = "String getBindingType(Object bindingObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bindingObject", type = "Object", desc = "The rule template binding model instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "string for the binding type"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getRuleTemplateBindings",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Fetch the type of the binding.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getBindingType(Object bindingObject) {
        if (!(bindingObject instanceof Binding)) {
            throw new IllegalArgumentException("Passed argument should be a Binding");
        }
        return ((Binding) bindingObject).getType();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getBindingId",
        synopsis = "",
        signature = "String getBindingId(Object bindingObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bindingObject", type = "Object", desc = "The rule template binding model instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "string for the binding Id"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getRuleTemplateBindings",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Fetch the Id of the binding.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getBindingId(Object bindingObject) {
        if (!(bindingObject instanceof Binding)) {
            throw new IllegalArgumentException("Passed argument should be a Binding");
        }
        return ((Binding) bindingObject).getIdName();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getBindingExpression",
        synopsis = "",
        signature = "String getBindingExpression(Object bindingObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bindingObject", type = "Object", desc = "The rule template binding model instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "string for the binding expression"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getRuleTemplateBindings",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Fetch the Value Expression of the binding.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String getBindingExpression(Object bindingObject) {
        if (!(bindingObject instanceof Binding)) {
            throw new IllegalArgumentException("Passed argument should be a Binding");
        }
        return ((Binding) bindingObject).getExpression();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "getDomainPathsForBinding",
        synopsis = "",
        signature = "String[] getDomainPathsForBinding(Object binding)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "binding", type = "String", desc = "The binding model instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "an", desc = "String[]"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getRuleTemplateBindings",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Fetch the domain model paths associated with this binding instance.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String[] getDomainPathsForBinding(Object binding) {
        if (!(binding instanceof Binding)) {
            throw new IllegalArgumentException("Passed argument should be a Binding");
        }
        String[] domainPaths = new String[0];
        Binding rtBinding = (Binding) binding;
        String domainPath = rtBinding.getDomainModelPath();
        if (domainPath != null && !domainPath.isEmpty()) {
            //TODO Handle multiple domains for a binding.
            domainPaths = new String[]{domainPath};
        }
        return domainPaths;
    }
}
