package com.tibco.be.ws.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;
import com.tibco.cep.webstudio.model.rule.instance.util.RuleTemplateInstanceDeserializer;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.RT",
        synopsis = "Functions For Webstudio Rule Template",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.RT", value=true))

public class WebstudioServerRTFunctions {
	
	 private static final String RULE_TEMPLATE_INSTANCE_EXTN = "ruletemplateinstance";

	 @com.tibco.be.model.functions.BEFunction(
            name = "getBaseRuleTemplate",
            synopsis = "",
            signature = "Object getBaseRuleTemplate(String projectName, String ruleTemplateContents, String extension)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Name of the project"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateContents", type = "String", desc = "The raw string contents of the RT/RTI"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extension", type = "String", desc = "(Valid values : ruletemplate | ruletemplateinstance)")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "object", desc = "representing RT/RTI"),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the <code>RuleTemplate</code>  model instance.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
        public static Object getBaseRuleTemplate(String projectName,
                                                 String ruleTemplateContents,
                                                 String extension) {
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(ruleTemplateContents.getBytes("UTF-8"));
                if (CommonIndexUtils.RULE_TEMPLATE_EXTENSION == extension.intern()) {
                    RulesASTNode astNode = (RulesASTNode) CommonRulesParserManager.parseRuleInputStream(projectName, bis, true);
                    RuleCreatorASTVisitor aSTVisitor = new RuleCreatorASTVisitor(true, true, projectName);
                    astNode.accept(aSTVisitor);
                    //Get compilable object
                    return aSTVisitor.getRule();
                } else if (RULE_TEMPLATE_INSTANCE_EXTN == extension.intern()) {
                    RuleTemplateInstanceDeserializer ruleTemplateInstanceDeserializer = new RuleTemplateInstanceDeserializer(bis);
                    ruleTemplateInstanceDeserializer.deserialize();
                    return ruleTemplateInstanceDeserializer.getDeserialized();
                } else {
                    throw new IllegalArgumentException("Extension value not supported. Valid values are ruletemplate and ruletemplateinstance");
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException cfe) {
                throw new RuntimeException(cfe);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
	
    @com.tibco.be.model.functions.BEFunction(
            name = "getRTFullPath",
            synopsis = "",
            signature = "Object getRTFullPath(Object ruleTemplateObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateObject", type = "Object", desc = "The rule template model instance.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "the", desc = "FQN of the RT/RTI."),
            version = "5.1.0",
            see = "WebstudioServerRTViewFunctions.getBaseRuleTemplate",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the FQN of the RT/RTI",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
        public static String getRTFullPath(Object ruleTemplateObject) {
            if (!(ruleTemplateObject instanceof RuleTemplate)) {
                throw new IllegalArgumentException("Passed argument should be a ruletemplate");
            }
            return ((RuleTemplate) (ruleTemplateObject)).getFullPath();
        }

    
    @com.tibco.be.model.functions.BEFunction(
        name = "hasViews",
        synopsis = "",
        signature = "boolean hasViews(Object ruleTemplateObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateObject", type = "Object", desc = "The rule template model instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "a", desc = "boolean"),
        version = "5.1.0",
        see = "WebstudioServerRTViewFunctions.getBaseRuleTemplate",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns whether there are any views in RT/RTI",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean hasViews(Object ruleTemplateObject) {
        if (ruleTemplateObject instanceof RuleTemplate) {
            RuleTemplate ruleTemplate = (RuleTemplate) ruleTemplateObject;
            return !ruleTemplate.getViews().isEmpty();
        } else if (ruleTemplateObject instanceof RuleTemplateInstance) {
            return WebstudioServerRTIFunctions.getRuleTemplateInstanceBindings(ruleTemplateObject).length > 0;
        }
        return false;
    }
    
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getRTInstances",
        signature = "Object getRTInstances(String projectPath, String rtPath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectPath", type = "String", desc = "Project Path"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "rtPath", type = "String", desc = "Rule Template Path")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Array containing the RT instances"),
        version = "5.2.1",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the list of RT instances",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object getRTInstances(String projectPath, String rtPath) {
    	File projectFilePath = new File(projectPath);
    	if (!projectFilePath.exists()) {
    		throw new IllegalArgumentException("Passed argument projectPath should be a valid file path"); 
    	}
    	List<String> rtInstances = new ArrayList<String>();

    	try {
    		List<String> rtInstancePaths = new ArrayList<String>(); 
    		getRTIPaths(projectFilePath, rtInstancePaths, projectPath);

    		RuleTemplateInstance rtInstance = null;
    		for(String rtiPath : rtInstancePaths) {

    			rtInstance = getRuleTemplateInstance(rtiPath);

    			if (rtInstance.getImplementsPath().equals(rtPath)) {
    				rtInstances.add(rtiPath.substring(projectPath.length()));
    			}
    		}
    	} catch(Exception exception) {
    		throw new RuntimeException(exception);
    	}
    	
    	return rtInstances.toArray(new String[rtInstances.size()]);
    }
    
    private static void getRTIPaths(File projectFilePath, List<String> rtInstancePaths, String projectPath) {
    	File[] rtInstances = projectFilePath.listFiles();
    	for (File file : rtInstances) {
    		if (file.isDirectory()) {
    			getRTIPaths(file, rtInstancePaths, projectPath);
    		} else {
    			if (file.getAbsolutePath().endsWith(".ruletemplateInstance")) {
    				rtInstancePaths.add(file.getAbsolutePath().substring(projectPath.length(), file.getAbsolutePath().indexOf(".")));
    			}
    		}
    	}
    }
    
    /**
     * Deserializes the RTI content into a Rule Template Instance Object
     * 
     * @param filePath
     * @return
     * @throws Exception
     */
    private static RuleTemplateInstance getRuleTemplateInstance(String filePath) throws Exception {
    	FileInputStream inputStream = new FileInputStream(new File(filePath));
    	RuleTemplateInstanceDeserializer ruleTemplateInstanceDeserializer = new RuleTemplateInstanceDeserializer(inputStream);
        ruleTemplateInstanceDeserializer.deserialize();

        return ruleTemplateInstanceDeserializer.getDeserialized();
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getRuleTemplatePriority",
        signature = "int getRuleTemplatePriority(Object ruleTemplateObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleTemplateObject", type = "Object", desc = "The rule template model instance.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Rule Priority"),
        version = "5.4.0",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the Rule Template Priority",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static int getRuleTemplatePriority(Object ruleTemplateObject) {
        if (!(ruleTemplateObject instanceof RuleTemplate)) {
            throw new IllegalArgumentException("Passed argument should be a ruletemplate");
        }
        return ((RuleTemplate) (ruleTemplateObject)).getPriority();
    }
}
