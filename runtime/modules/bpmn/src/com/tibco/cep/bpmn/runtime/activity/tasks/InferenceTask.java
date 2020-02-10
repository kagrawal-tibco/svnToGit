package com.tibco.cep.bpmn.runtime.activity.tasks;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.results.DefaultResult;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.agent.ProcessRuleSession;
import com.tibco.cep.bpmn.runtime.agent.ProcessWM;
import com.tibco.cep.bpmn.runtime.agent.RetePool;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.templates.MapperConstants;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.core.base.tuple.JoinTableCollectionProvider;
import com.tibco.cep.kernel.core.base.tuple.NoOpJoinTableCollection;
import com.tibco.cep.kernel.model.knowledgebase.RuleLoader;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.rule.InvalidRuleException;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.PropertyAtomString;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineRule;


public class InferenceTask extends AbstractTask {

    private static final Class RULE_TEMPLATE_CLASS;
    private static final Class RULE_TEMPLATE_LOADER_CLASS;
    static {
        Class rtClass, rtlClass;
        try {
            rtClass = Class.forName("com.tibco.cep.interpreter.template.TemplatedRule"); //$NON-NLS-1$
            rtlClass = Class.forName("com.tibco.cep.interpreter.template.TemplatedRuleLoader"); //$NON-NLS-1$
        }
        catch (Throwable t) {
            rtClass = rtlClass = null;
        }
        RULE_TEMPLATE_CLASS = rtClass;
        RULE_TEMPLATE_LOADER_CLASS = rtlClass;
    }


    Set<String> ruleUris;
    public InferenceTask() {
        ruleUris = new HashSet<String>();

    }

    public String getRuleUris() {
        StringBuilder sb = new StringBuilder();
        for(String s: ruleUris) {
            sb.append(s).append(";");
        }
        return sb.toString();
    }


    @Override
    public void init(InitContext context, Object... args) throws Exception {
        super.init(context, args);
        EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(getTaskModel().getEInstance());
        EObjectWrapper<EClass, EObject> valueWrapper =	ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
        EList<?> rulesList =  valueWrapper.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULES);
        String[] rules = new String[rulesList.size()];
        for(int i=0; i<rulesList.size(); i++) {
            rules[i] = rulesList.get(i).toString();
        }
        final Ontology o = context.getProcessAgent().getRuleServiceProvider().getProject().getOntology();
        ruleUris = traversePathsAndBuildRuleList(o,rules);

    }

    /* (non-Javadoc)
      * @see com.tibco.cep.bpmn.runtime.activity.Task#execute(com.tibco.cep.bpmn.runtime.agent.Job)
      */
    @Override
    public TaskResult execute(Job job, Variables vars, Task loopTask) {
        //synchronized(InferenceTask.class) {
    		
    		
            TaskResult result = null;
            PropertyAtomString[] rulesProps = (PropertyAtomString[]) vars.getVariable(MapperConstants.RULES);
            Set<String> ruleSet = ruleUris;
            if ((rulesProps != null) && (rulesProps.length > 0)) {
                String[] rules = new String[rulesProps.length];
                for (int i = 0; i < rulesProps.length; i++) {
                    rules[i] = rulesProps[i].getString();
                }
                final Ontology o = job.getProcessAgent().getRuleServiceProvider().getProject().getOntology();
                ruleSet = traversePathsAndBuildRuleList(o,rules);
            }
            ProcessRuleSession processSession = (ProcessRuleSession) job.getProcessAgent().getRuleSession();
            RetePool retePool = processSession.getRetePool();
            ProcessWM procWM = (ProcessWM) retePool.getFreeRete();

            try {
            	String lContext = makeLockContext(job.getJobContext(), this);
        		lContext = lContext+"-Inference";
        		setLockContext(lContext);
                JoinTableCollectionProvider.getInstance().setThreadJoinTableCollection(new NoOpJoinTableCollection());
                Set<Rule> rules = loadRules(ruleSet, procWM.getRuleLoader(), processSession.getRuleServiceProvider().getTypeManager());
                for (Rule rule : rules) {
                    procWM.addRule(rule);
                    rule.activate();
                }

                procWM.initEntitySharingLevels();


                procWM.executeRules(getName(), job);

                result = new DefaultResult(TaskResult.Status.OK, null);

            }
            catch (Throwable e) {
                result =  new ExceptionResult(e);
            }
            finally {
            	setLockContext(null);
                procWM.fastReset();     //removes all join table.
                retePool.returnRete(procWM);
                JoinTableCollectionProvider.getInstance().clearThreadJoinTableCollection();
            }
            return result; // needed for debugger exit task notification
        //}
    }

    /**
     * @param deployedRuleUris
     * @param ruleLoader
     * @param typeManager
     * @return
     * @throws SetupException
     * @throws InvalidRuleException
     */
    private Set<Rule> loadRules(final Set<String> deployedRuleUris, final RuleLoader ruleLoader, final TypeManager typeManager) throws SetupException, InvalidRuleException {
        final Set<Rule> rules = new HashSet<Rule>();


        final String separator = String.valueOf(Folder.FOLDER_SEPARATOR_CHAR);

        for (Object o : typeManager.getTypeDescriptors(TypeManager.TYPE_RULE)) {
            final TypeManager.TypeDescriptor descriptor = (TypeManager.TypeDescriptor) o;
            if (StateMachineRule.class.isAssignableFrom(descriptor.getImplClass())) {
                rules.add(ruleLoader.loadRule(descriptor.getImplClass()));
            } else if (!isRuleTemplate(descriptor)) {
                // Checks for both rules and rule folders
                final String uri = descriptor.getURI();
                for (String dru : deployedRuleUris) {
                    if (uri.equals(dru)
                            || (dru.endsWith(separator) && uri.startsWith(dru))
                            || ((!dru.endsWith(separator)) && uri.startsWith(dru + separator))) {
                        rules.add(ruleLoader.loadRule(descriptor.getImplClass()));
                    }
                }
            }
        }

        //rules.addAll(this.loadRuleTemplates());

        return rules;
    }

    private boolean isRuleTemplate(TypeManager.TypeDescriptor descriptor) {
        return (null != RULE_TEMPLATE_CLASS)
                && RULE_TEMPLATE_CLASS.isAssignableFrom(descriptor.getImplClass());
    }

    private Set<String> traversePathsAndBuildRuleList(Ontology o, String[] rulePathArr) {
        if (rulePathArr == null) return Collections.EMPTY_SET;
        HashSet<String> ruleUriSet = new HashSet<String>();

//        String[] pathTokens = listOfRulePaths.trim().split(";");
        //TODO - Pranab/Manish - please fill this. if it is path, then you have traverse the path, nested, and find all the the Rules that are needed.
        //I am sure there is code given the ontology to traverse such
        for (String ruleUri : rulePathArr) {
            Entity e = o.getEntity(ruleUri);
            if(e != null) {
                if(e instanceof com.tibco.cep.designtime.model.rule.Rule) {
                    ruleUriSet.add(ruleUri);
                } else if( e instanceof Folder) {
                    Folder f = (Folder) e;
                    List<Entity> entities = f.getEntities(true);
                    for(Entity fe:entities ) {
                        if( fe instanceof com.tibco.cep.designtime.model.rule.Rule) {
                            ruleUriSet.add(fe.getFullPath());
                        }
                    }
                }
            }
        }

        return ruleUriSet;
    }

    

	
	private String makeLockContext(JobContext context, Task ltask) {
		long id = context.getId();
		String taskId = ltask.getName();
		return String.format("[%d]:%s", id,taskId);
	}


}
