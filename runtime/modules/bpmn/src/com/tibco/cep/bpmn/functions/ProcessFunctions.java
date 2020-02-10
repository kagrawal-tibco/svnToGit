package com.tibco.cep.bpmn.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.PROCESS;

import java.util.ArrayList;
import java.util.List;

import com.tibco.be.model.functions.BEFunction;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.agent.JobImpl;
import com.tibco.cep.bpmn.runtime.agent.ProcessRuleSession;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 * @author pdhar
 *
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Process Orchestration",
        category = "Process",
        synopsis = "Process Functions")

public class ProcessFunctions {


    @BEFunction(
            name = "getCurrentJob",
            synopsis = "Returns the currently executing Job",
            signature = "Object getCurrentJob()",
            params = { },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The currently executing Process Context"),
            version = "1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the currently executing Process context",
            cautions = "none",
            fndomain = {ACTION, PROCESS},
            example = ""
    )
    public static Object getCurrentJob() {
        RuleSession rsex = (RuleSession) RuleSessionManager.getCurrentRuleSession();
        if(rsex instanceof ProcessRuleSession) {
            Job proc = JobImpl.getCurrentJob();
            return proc;
        } else {
            throw new RuntimeException(String.format("The current Rule Session % is invalid for %() function call ", rsex.getName(),"getCurrentJob"));
        }
    }


    @BEFunction(
            name = "getCurrentJobContext",
            synopsis = "Returns the context (data) for the currently executing job",
            signature = "Concept getCurrentJobContext()",
            params = { },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The currently executing Process Concept"),
            version = "1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the currently executing Process concept",
            cautions = "none",
            fndomain = {ACTION, PROCESS},
            example = ""
    )
    public static Concept getCurrentJobContext() {
        RuleSession rsex = (RuleSession) RuleSessionManager.getCurrentRuleSession();
        if(rsex instanceof ProcessRuleSession) {
            Job proc = JobImpl.getCurrentJob();
            return proc.getJobContext();
        } else {
            throw new RuntimeException(String.format("The current Rule Session % is invalid for %() function call ", rsex.getName(),"getCurrentJob"));
        }
    }

    @BEFunction(
            name = "getCurrentTemplateName",
            synopsis = "Returns the currently executing process template name",
            signature = "String getCurrentTemplateName()",
            params = { },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Returns the currently executing Process template name"),
            version = "1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the currently executing Process template name",
            cautions = "none",
            fndomain = {ACTION, CONDITION, PROCESS},
            example = ""
    )
    public static String getCurrentTemplateName() throws RuntimeException{
        Job job = JobImpl.getCurrentJob();
        return job != null ? job.getJobContext().getProcessTemplate().getProcessName().getModeledFQN() : null;

    }

    @BEFunction(
            name = "listAllTemplates",
            synopsis = "Returns a list of deployed process templates for the current process",
            signature = "String[] listAllTemplates()",
            params = { },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "list of template names"),
            version = "1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns a list of deployed process templates for the current process",
            cautions = "none",
            fndomain = {ACTION, CONDITION, PROCESS},
            example = ""
    )
    public static String[] listAllTemplates() throws RuntimeException{

        Job job = JobImpl.getCurrentJob();
        if (job == null) return new String[0];


        List<ProcessTemplate> templates = job.getProcessAgent().getDeployedProcessTemplates();
        List<String> templateNames = new ArrayList<String>();
        for(ProcessTemplate template:templates) {
            templateNames.add(template.getProcessName().getModeledFQN());
        }
        return templateNames.toArray(new String[0]);

    }

}
