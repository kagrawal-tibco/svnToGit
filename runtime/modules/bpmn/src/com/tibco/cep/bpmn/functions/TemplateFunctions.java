package com.tibco.cep.bpmn.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.PROCESS;
import com.tibco.be.model.functions.Enabled;

import java.util.List;

import com.tibco.be.model.functions.BEFunction;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.agent.JobImpl;
import com.tibco.cep.bpmn.runtime.agent.ProcessRuleSession;
import com.tibco.cep.bpmn.runtime.model.AbstractJobContext;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.templates.ProcessName;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.bpmn.runtime.templates.VersionedProcessName;
import com.tibco.cep.bpmn.runtime.templates.VersionedProcessTemplate;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.service.Filter;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 * @author pdhar
 *
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Process Orchestration",
        category = "Process.Template",
        synopsis = "Process Template Functions")

public class TemplateFunctions {


    @BEFunction(
            name = "getTemplate",
            synopsis = "Returns a deployed process template based on the name and arguments provided.",
            signature = "Object getTemplate(String  name, Object ...args)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "The process template name."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "args", type = "Object", desc = "The arguments provided.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The process template"),
            version = "1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the currently executing Process template name",
            cautions = "none",
            fndomain = {ACTION, PROCESS},
            example = ""
    )
    public static Object getTemplate(String name, Object ...args) throws RuntimeException {
    	int revision = -1;
        if ((args != null) && args.length > 0)
        {
            revision = (Integer)args[0];
        }


        RuleSession rsex = (RuleSession) RuleSessionManager.getCurrentRuleSession();

        if(rsex instanceof ProcessRuleSession) {

            Job proc = JobImpl.getCurrentJob();

            List<ProcessTemplate> templates = proc.getProcessAgent().getDeployedProcessTemplates();

            for(ProcessTemplate template:templates) {

                ProcessName pName = template.getProcessName();
                if (pName.getModeledFQN().equals(name)) {

                    if (revision == -1) return template;

                    if (pName instanceof VersionedProcessName) {
                        return ((VersionedProcessName)pName).getVersion() == revision ? template :null;
                    }

                    return template;
                }

            }
            return null;
        }
        throw new RuntimeException(String.format("The current Rule Session % is invalid for %() function call ", rsex.getName(),"getTemplate"));
    }

    @BEFunction(
            name = "getProcessInstances",
            synopsis = "Returns a collection of process instances known to the process session",
            enabled = @Enabled(value=false),
            signature = "Concept[] getProcessInstances()",
            params = { },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Collection of process instances"),
            version = "1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns a collection of process instances known to the process session",
            cautions = "none",
            fndomain = {ACTION, PROCESS},
            example = ""
    )
    public static Concept[] getProcessInstances() throws RuntimeException {
        RuleSession rsex = (RuleSession) RuleSessionManager.getCurrentRuleSession();
        if(rsex instanceof ProcessRuleSession) {
            ProcessRuleSession prs = (ProcessRuleSession) rsex;
            Job proc = JobImpl.getCurrentJob();
            @SuppressWarnings("unchecked")
            List<Object> pdataObjs = prs.getObjects(new Filter() {

                @Override
                public boolean evaluate(Object o) {
                    if(o instanceof JobContext){
                        return true;
                    }
                    return false;
                }
            });
            return pdataObjs.toArray(new Concept[0]);
        }
        throw new RuntimeException(String.format("The current Rule Session % is invalid for %() function call ", rsex.getName(),"getProcessInstances"));
    }

	@BEFunction(
    		name = "setExceptionHandler",
    		synopsis = "Set the default Exception handler process or function URI for the given template",
    		signature = "void setExceptionHandler(Object template,String processOrfunctionURI,&lt;optional&gt;boolean isAsynchronous)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "template", type = "Object", desc = "The process template."),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(
    						name = "processOrfunctionURI", 
    						type = "String", 
    						desc = "The exception handler process or function URI.<br/>"+
    								"The ExceptionHandler function signature is <br/>"+
    								"&nbsp;&nbsp;void handlerFn(String taskName,Concept jobContext,Exception e)<br/><br/>"+    							
    								"The ExceptionHandler process variables needs to have the following optional<br/>"+
    								" String properties to show details <br/>"+  
    	    						"&nbsp;&nbsp;\"taskName\"=Task Name,<br/>"+
    								"&nbsp;&nbsp;\"jobData\"=Serialized Job Context Data XML,<br/>"+
    	    						"&nbsp;&nbsp;\"taskException\"=Serialized Exception XML<br/>"+
    	    						"The exception handler process must have default start event without<br/>"+
    	    						" any associated SimpleEvent or TimeEvent"
    								),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(
    						name = "isAsynchronous", 
    						type = "boolean", 
    						desc = "&lt;optional&gt;true if the exception handler is invoked asynchronously<br/>"+
    								" else false, default is true. This parameter is not used for rulefunction <br/>"+
    								"handler and it is executed synchronously")
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "Set the default Exception handler process or function URI for the given template",
    		cautions = "none",
    		fndomain = {ACTION, PROCESS},
    		example = ""
    		)
    public static void setExceptionHandler(Object template,String handlerFunctionURI,Object ...args) throws RuntimeException {
    	
    	
    	
    	JobContext processData = null;
    	boolean isAsynchronous = true;
    	
   	 	if ((args != null) && args.length > 0)
        {
            isAsynchronous = (Boolean)args[0];
        }
    	
    	RuleSession rsex = (RuleSession) RuleSessionManager.getCurrentRuleSession();
    	Ontology ontology = rsex.getRuleServiceProvider().getProject().getOntology();
    	ProcessTemplate processTemplate = (ProcessTemplate) template;
    	
    	if (processTemplate == null) {
    		throw new RuntimeException(String.format("Invalid process template %s specified ", template.toString()));
    	}
    	
    	//TODO search for revision.
    	
    	try {
    		if(processTemplate.getExceptionHandler() == null) {
    			((VersionedProcessTemplate)processTemplate).setExceptionHandler(handlerFunctionURI,isAsynchronous);
    		}
    		
    	} catch (Exception ex) {
    		throw new RuntimeException(ex);
    	}
    	
    	
    }

	/*
	@BEFunction(
    		name = "setExceptionHandlerProcess",
    		synopsis = "Set the default Exception handler process URI for the given template",
    		signature = "void setExceptionHandlerProcess(Object template,String processURI,&lt;optional&gt; boolean isAsynchronous)",
    		params = {
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "template", type = "Object", desc = "The process template."),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "processURI", 
    				type = "String", 
    				desc = "The exception handler process URI.<br/>"+
    				"The ExceptionHandler process variables needs to have the following optional String properties to show details <br/>"+  
    						"\"taskName\"=Task Name,\"jobData\"=Serialized Job Context Data XML,\"taskException\"=Serialized Exception XML<br/>"+
    				"The exception handler process must have default start event without any associated SimpleEvent or TimeEvent"),
    				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "isAsynchronous", type = "boolean", desc = "&lt;optional&gt;true if the exception handler is invoked asynchronously else false, default is true")
    				
    		},
    		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
    		version = "1.0",
    		see = "",
    		mapper = @com.tibco.be.model.functions.BEMapper(),
    		description = "Set the default Exception handler process URI for the given template",
    		cautions = "none",
    		domain = {ACTION, PROCESS},
    		example = ""
    		)
    public static void setExceptionHandlerProcess(Object template,String handlerProcessURI,Object ...args) throws RuntimeException {
    	
    	setExceptionHandler(template, handlerProcessURI, args);
    }
	*/

    @BEFunction(
            name = "newJobContext",
            synopsis = "creates a process instance from the template name and arguments provided.",
            signature = "Concept newJobContext(Object template,Object ... args)",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "template", type = "Object", desc = "The process template."),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(name = "args", type = "Object", desc = "The arguments provided.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The newly created process instance"),
            version = "1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the newly created process instance name",
            cautions = "none",
            fndomain = {ACTION, PROCESS},
            example = ""
    )
    public static Concept newJobContext(Object template, Object ... args) throws RuntimeException {

        String extId = null;

        
        if ((args != null) && args.length > 0)
        { 	
            extId = (String) args[0];
        }

        JobContext processData = null;

        RuleSession rsex = (RuleSession) RuleSessionManager.getCurrentRuleSession();
        Ontology ontology = rsex.getRuleServiceProvider().getProject().getOntology();
        ProcessTemplate processTemplate = (ProcessTemplate) template;

        if (processTemplate == null) {
            throw new RuntimeException(String.format("Invalid process template %s specified ", template.toString()));
        }

        //TODO search for revision.

        try {
            processData = processTemplate.newProcessData();
            if(extId != null) {
            	
            	((AbstractJobContext)processData).setExtId(extId,true);
            }
            Job cjob = JobImpl.getCurrentJob();
            JobContext currentContext = cjob.getJobContext();
            if(currentContext.getProcessTemplate().equals(processTemplate)) {
            	processData.setLastTaskExecuted(currentContext.getLastTaskExecuted());
            }
        }

        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return processData;


    }




}
