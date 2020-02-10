package com.tibco.cep.bpmn.runtime.templates;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.JarInputStream;

import com.tibco.cep.bpmn.codegen.ProcessCompiler;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.tasks.AbstractTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.CallActivityTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.DefaultLoopTask;
import com.tibco.cep.bpmn.runtime.agent.ProcessAgent;
import com.tibco.cep.bpmn.runtime.config.ProcessAgentConfiguration;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.utils.BEJarInputStreamReader;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.designtime.model.process.SubProcessModel;
import com.tibco.cep.designtime.model.registry.ElementTypes;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.provider.JavaArchiveResourceProvider;
import com.tibco.cep.runtime.model.process.LoopTuple;
import com.tibco.cep.runtime.model.process.MergeTuple;
import com.tibco.cep.runtime.service.cluster.backingstore.BackingStore;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.loader.ClassManager;
import com.tibco.cep.runtime.service.loader.JarInputStreamReader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionEx;

/*
* Author: Suresh Subramani / Date: 12/2/11 / Time: 2:06 PM
*/
public class ProcessTemplateManager {


    private static Logger logger = LogManagerFactory.getLogManager().getLogger(ProcessTemplateManager.class);

    ProcessAgent context;
    List<ProcessTemplate> deployedProcessTemplates = new ArrayList<ProcessTemplate>();
    List<String> deployedProcessUris = new ArrayList<String>();

    public ProcessTemplateManager() {
    }

    public void init(ProcessAgent context, boolean isCacheServer) throws Exception {
        this.context = context;
        if (isCacheServer) {
            loadAllTemplates();
        }
        else {
            loadDeployedTemplates();
        }
        compileTemplates();
        //compileControlBeans();
        
    }



    private  void loadDeployedTemplates() throws Exception {

        RuleServiceProvider rsp = context.getRuleServiceProvider();
        Ontology ontology = rsp.getProject().getOntology();
        ProcessAgentConfiguration pac = context.getProcessAgentConfiguration();
        Set<String> procs = new HashSet<String>(pac.getDeployedProcesses());
        // The deployed processes list should actually contain all the startup
        // and shutdown processes too, validation should ensure that
        procs.addAll(pac.getStartupProcesses());
        procs.addAll(pac.getShutdownProcesses());
        Collection<Entity> processes = ontology.getEntities(new ElementTypes[] {ElementTypes.PROCESS});
        for(Entity p: processes) {
            ProcessModel process = (ProcessModel) p;
            String path = process.getFullPath();
            //path = path.substring(0, path.lastIndexOf(".")); Without the extension

            if (procs.contains(path)) {
                loadTemplate(process);
            }

        }
        

    }

    private  void loadAllTemplates() throws Exception {

        RuleServiceProvider rsp = context.getRuleServiceProvider();
        Ontology ontology = rsp.getProject().getOntology();

        Collection<Entity> processes = ontology.getEntities(new ElementTypes[] {ElementTypes.PROCESS});
        for(Entity p: processes) {
            ProcessModel process = (ProcessModel) p;
            loadTemplate(process);
        }
        

    }


    private void compileControlBeans() throws Exception {
    	Class[] controlBeans = { MergeTuple.class, LoopTuple.class};
    	
    	Map<Class,Class> classDependency = getControlBeanDependency(controlBeans);
    	
    	List<JitCompilable> compilables = new ArrayList<JitCompilable>();
    	for(Entry<Class, Class> entry:classDependency.entrySet()) {
    		logger.log(Level.INFO, String.format("Loading control template : %s", entry.getKey().getName()));
    		CodegenTemplate ct = new ControlBeanTemplateImpl(entry.getKey(),entry.getValue() != null);
    		ct.init(context);
    		compilables.add(ct.generate());
    	}
    	
    	 RuleServiceProvider rsp = context.getRuleServiceProvider();


         Map<String,byte[]> nameToByteCode = new HashMap<String,byte[]>();
         final JavaArchiveResourceProvider jarp = rsp.getProject().getJavaArchiveResourceProvider();
         final Collection uris = jarp.getAllResourceURI();
         for (Object uri : uris) {
             final byte[] raw = jarp.getResourceAsByteArray(uri.toString());
             if ((raw != null) && (raw.length != 0)) {
                 ByteArrayInputStream bis = new ByteArrayInputStream(raw);
                 nameToByteCode.putAll(BEJarInputStreamReader.read(new JarInputStream(bis)));
             }
         }

        Map<String,byte[]> processClasses = new HashMap<String,byte[]>();
        processClasses.putAll(jitCompile(compilables, nameToByteCode));

        Map<String, ClassManager.ClassLoadingResult> rMap = new HashMap<String,ClassManager.ClassLoadingResult>();
        for(Map.Entry<String, byte[]> entry : processClasses.entrySet()){

            rMap.put(entry.getKey(), new ClassManager.ClassLoadingResult(entry.getKey(), entry.getValue(), ClassManager.ClassLoadingResult.NEW));
        }

        BEClassLoader beClassLoader = (BEClassLoader) rsp.getClassLoader();
        beClassLoader.commitChanges(rMap);
        beClassLoader.registerClasses(rMap);

        RuleSessionEx rsex = (RuleSessionEx) context.getRuleSession();
        rsex.registerType(JobContext.class);

        for (String templateClassName : processClasses.keySet())  {
        	Class templateClass = beClassLoader.loadClass(templateClassName);
            rsex.registerType(templateClass);
            this.context.getCluster().getMetadataCache().registerType(templateClass);
            GenericBackingStore bs = this.context.getCluster().getCacheAsideStore();
            if (bs != null && bs instanceof BackingStore) {
                ((BackingStore) bs).registerType(templateClass, 0);
            }
            //((VersionedProcessTemplate)template).test();
        }
        GenericBackingStore bs = this.context.getCluster().getCacheAsideStore();
        if (bs != null && bs instanceof BackingStore) {
            ((BackingStore) bs).reInitializeTypes();
        }
	}

	private Map<Class, Class> getControlBeanDependency(Class[] beanClasses) throws Exception {
		Map<Class,Class> dependencyMap = new HashMap<Class,Class>();
		for(Class bc:beanClasses) {
			dependencyMap.put(bc, null);
			BeanInfo bi = Introspector.getBeanInfo(bc);
			for(PropertyDescriptor pd:bi.getPropertyDescriptors()){
				Class<?> ptype = pd.getPropertyType();
				if( ptype.isMemberClass()){
					dependencyMap.put(ptype, bc);
				} else if(ptype.isArray() && ptype.getComponentType().isMemberClass()){
					dependencyMap.put(ptype.getComponentType(), bc);
				}
			}
		}
		return dependencyMap;
	}

	private void loadTemplate(ProcessModel process) throws Exception {
    	if(deployedProcessUris.contains(process.getFullPath())) return;
    	
        logger.log(Level.INFO, String.format("Initializing process %s ", process.getFullPath()));
        ProcessTemplate template = new VersionedProcessTemplate(process);
        template.init(context);
        ProcessTemplateRegistry.getInstance().addProcessTemplate(template);
        deployedProcessTemplates.add(template);
        deployedProcessUris.add(process.getFullPath());

        Collection<SubProcessModel> subprocesses = process.getSubProcesses();
        for(SubProcessModel s: subprocesses) {

            loadTemplate(s);
        }

        Collection<Task> allTasks = template.allTasks();
        for (Task task : allTasks) {
            if(task instanceof CallActivityTask){
                CallActivityTask callActivity = (CallActivityTask)task;
                ProcessModel calledProcess = callActivity.getCalledProcess();
                if(calledProcess != null && !deployedProcessUris.contains(calledProcess.getFullPath()))
                    loadTemplate(calledProcess);
            }else if(task instanceof DefaultLoopTask){
            	DefaultLoopTask loopTask = (DefaultLoopTask)task;
            	AbstractTask wrappedTask = loopTask.getLoopTask();
            	if(wrappedTask instanceof CallActivityTask){
            		 CallActivityTask callActivity = (CallActivityTask)wrappedTask;
                     ProcessModel calledProcess = callActivity.getCalledProcess();
                     if(calledProcess != null && !deployedProcessUris.contains(calledProcess.getFullPath()))
                         loadTemplate(calledProcess);
            	}
            }
        }
    }

    private void compileTemplates() throws Exception
    {

        if (deployedProcessTemplates.size() == 0) return;

        RuleServiceProvider rsp = context.getRuleServiceProvider();
        Collection<ProcessTemplate> templates = ProcessTemplateRegistry.getInstance().templates();


        Map<String,byte[]> nameToByteCode = new HashMap<String,byte[]>();
        final JavaArchiveResourceProvider jarp = rsp.getProject().getJavaArchiveResourceProvider();
        final Collection uris = jarp.getAllResourceURI();
        for (Object uri : uris) {
            final byte[] raw = jarp.getResourceAsByteArray(uri.toString());
            if ((raw != null) && (raw.length != 0)) {
                ByteArrayInputStream bis = new ByteArrayInputStream(raw);
                nameToByteCode.putAll(BEJarInputStreamReader.read(new JarInputStream(bis)));
            }
        }

        List<JitCompilable> compilables = new ArrayList<JitCompilable>();
        for (ProcessTemplate template : templates)
        {
            compilables.add(template.generate());
        }

        Map<String,byte[]> processClasses = new HashMap<String,byte[]>();
        processClasses.putAll(jitCompile(compilables, nameToByteCode));

        Map<String, ClassManager.ClassLoadingResult> rMap = new HashMap<String,ClassManager.ClassLoadingResult>();
        for(Map.Entry<String, byte[]> entry : processClasses.entrySet()){
        	if(ClassManager.isJavaClass(entry.getValue())) {
        		rMap.put(entry.getKey(), new ClassManager.ClassLoadingResult(entry.getKey(), entry.getValue(), ClassManager.ClassLoadingResult.NEW));
        	} else {
        		rMap.put(entry.getKey(), new ClassManager.ClassLoadingResult(entry.getKey(), entry.getValue(), ClassManager.ClassLoadingResult.RESOURCE));
        	}
        }

        BEClassLoader beClassLoader = (BEClassLoader) rsp.getClassLoader();
        beClassLoader.commitChanges(rMap);
        beClassLoader.registerClasses(rMap);

        RuleSessionEx rsex = (RuleSessionEx) context.getRuleSession();
        rsex.registerType(JobContext.class);

        for (ProcessTemplate template : templates)
        {
            Class templateClass = template.getTemplateClass();
            rsex.registerType(templateClass);
            this.context.getCluster().getMetadataCache().registerType(templateClass);
            GenericBackingStore bs = this.context.getCluster().getCacheAsideStore();
            if (bs != null && bs instanceof BackingStore) {
                ((BackingStore) bs).registerType(templateClass, template.getVersionInfo().getRevision());
            }
            //((VersionedProcessTemplate)template).test();
        }
        GenericBackingStore bs = this.context.getCluster().getCacheAsideStore();
        if (bs != null && bs instanceof BackingStore) {
            ((BackingStore) bs).reInitializeTypes();
        }
    }

    public Map<String, byte[]> jitCompile(List<JitCompilable> compilables,Map<String,byte[]>nameToByteCode) throws Exception {
        RuleServiceProvider rsp = this.context.getRuleServiceProvider();
        ProcessCompiler pc = new ProcessCompiler("",rsp.getClassLoader(), true, "1.6", "1.6","UTF-8",false,new String[0]);
        for(JitCompilable c: compilables) {
            pc.addProcess("processSrc",c.getPackageName(),c.getClassName(),c.getCodeStream());
        }
        InputStream jis = pc.compile(nameToByteCode);
        Map<String, byte[]> result;
        result = new HashMap<String, byte[]>();
        result.putAll(JarInputStreamReader.read(new JarInputStream(jis)));
        return result;
    }

    public List<ProcessTemplate> getProcessTemplates() {
        return deployedProcessTemplates;
    }

}
