package com.tibco.cep.bpmn.runtime.templates;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarInputStream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.bpmn.codegen.ProcessClassGenerator;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultProcessIndex;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.InitContext;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskFactory;
import com.tibco.cep.bpmn.runtime.activity.Triggerable;
import com.tibco.cep.bpmn.runtime.activity.events.StartEvent;
import com.tibco.cep.bpmn.runtime.activity.tasks.TaskInitContext;
import com.tibco.cep.bpmn.runtime.agent.ProcessAgent;
import com.tibco.cep.bpmn.runtime.agent.ProcessException;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.model.VersionInfo;
import com.tibco.cep.bpmn.runtime.utils.FQName;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.loader.ClassManager.ClassLoadingResult;
import com.tibco.cep.runtime.service.loader.JarInputStreamReader;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/*
* Author: Suresh Subramani / Date: 12/2/11 / Time: 2:10 PM
*/
public class VersionedProcessTemplate implements ProcessTemplate  {


    FQName fqName;
    VersionInfo versionInfo;

    Map<String, Task> allTasks;
    List<Task> starterTasks = new ArrayList<Task>();
    List<Task> triggerAbleTasks = new ArrayList<Task>();
    Task[] indexedTasks;
    private ProcessModel processModel;
    private ProcessAgent processAgent;
    private static Logger logger = LogManagerFactory.getLogManager().getLogger(ProcessTemplate.class);
    private Constructor<JobContext> ctor = null;
    ProcessName processName;
    private Class processClass;
    private String exceptionHandler;
	private boolean isAsyncExceptionHandler;

    public VersionedProcessTemplate(final ProcessModel processModel)
    {
        this.processModel = processModel;
        versionInfo = new DefaultVersionInfo(processModel);
        // replace subprocess separator . by $
        fqName = FQName.makeName(processModel.getFullPath().replace('.', '$'), versionInfo.getRevision());
        processName = new VersionedProcessNameImpl(processModel.getFullPath(), versionInfo.getRevision());

        allTasks = new LinkedHashMap<String, Task> ();

    }

    

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((processName == null) ? 0 : processName.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VersionedProcessTemplate other = (VersionedProcessTemplate) obj;
		if (processName == null) {
			if (other.processName != null)
				return false;
		} else if (!processName.equals(other.processName))
			return false;
		return true;
	}



	@Override
    public void init(ProcessAgent context) throws Exception {

        this.processAgent = context;
        walkProcessModel();
    }




	@Override
    public JitCompilable generate() throws Exception {

        List<MapperClassTemplate> mapperCodeSnippets = new ArrayList<MapperClassTemplate>();
        for (Task task : allTasks.values()) {
            MapperClassTemplate mct = new MapperClassTemplate(this.processModel);
            mct.init(task);
            mct.generate();
            mapperCodeSnippets.add(mct);
        }
        return generateCode(mapperCodeSnippets);

    }

    @Override
    public JobContext newProcessData() throws ProcessException {

        try {
            RuleServiceProvider rsp = processAgent.getRuleServiceProvider();
            if (ctor == null) { //Race condition here is fine. It will set to the same value

                TypeManager typeManager = rsp.getTypeManager();
                TypeManager.TypeDescriptor typeDescriptor = typeManager.getTypeDescriptor(processName.getExpandedName());
                ctor = typeDescriptor.getConstructor();
            }
            long id = rsp.getIdGenerator().nextEntityId(ctor.getDeclaringClass());
            JobContext process = ctor.newInstance(id);
            
            Ontology o = rsp.getProject().getOntology();
            ProcessModel po = (ProcessModel) o.getEntity(processName.getSimpleName());
            Collection<PropertyDefinition> pds = po.getPropertyDefinitions();
            
            
            initializeContainedConcepts(rsp,o, process, pds);
            
            return process;
        }
        catch (Exception ex) {
            logger.log(Level.ERROR, "Error while creating a new Process instance", ex);
            throw new ProcessException("Error while creating a new Process instance", ex);
        }


    }


	/**
	 * @param rsp
	 * @param parent
	 * @param pds
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private void initializeContainedConcepts(RuleServiceProvider rsp,Ontology o, Concept parent, Collection<PropertyDefinition> pds) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, Exception {
		for(PropertyDefinition pd: pds) {
			int propType = pd.getType();
			
			if (propType == PropertyDefinition.PROPERTY_TYPE_CONCEPT) {
				String pcPath = pd.getConceptTypePath();
				TypeManager typeManager = rsp.getTypeManager();
		        TypeManager.TypeDescriptor typeDescriptor = typeManager.getTypeDescriptor(pcPath);
		        Constructor pcctor = typeDescriptor.getConstructor();
		        if(pd.isArray()) {
		        	// initialize the empty property array
		        	PropertyArray pacr = ((Concept)parent).getPropertyArray(pd.getName());
		        } else {
		        	long pcid = rsp.getIdGenerator().nextEntityId(ctor.getDeclaringClass());
		        	Concept pConcept = (Concept) pcctor.newInstance(pcid);
		        	parent.setPropertyValue(pd.getName(), pConcept);
		        	com.tibco.cep.designtime.model.element.Concept cc =  (com.tibco.cep.designtime.model.element.Concept) o.getEntity(pcPath);
		        	Collection<PropertyDefinition> pdefs = cc.getAllPropertyDefinitions();
		        	initializeContainedConcepts(rsp, o, pConcept, pdefs);
		        }
			} 
		}
	}

//    public void test() {
//        for (Task task : allTasks.values()) {
//            logger.log(Level.INFO, "Mapper is : %s\n", task.getMapper().getClass().getName());
//        }
//    }




    /**
     * @param mapperCodeSnippets
     * @throws Exception
     */
    private JitCompilable generateCode(List<MapperClassTemplate> mapperCodeSnippets) throws Exception    {
    	JitCompilable compilable = null;
    	
    	RuleServiceProvider rsp = processAgent.getRuleServiceProvider();

        ProcessClassGenerator procgen = new ProcessClassGenerator(this,mapperCodeSnippets);
        
        ByteArrayOutputStream codeStream = procgen.generate();
        
        compilable = new JitCompilable(codeStream, procgen.getPackageName(), procgen.getClassName());
        
//        ProcessCompiler pc = new ProcessCompiler("",rsp.getClassLoader(), true, "1.6", "1.6","UTF-8");
        
//        pc.addProcess("processSrc",procgen.getPackageName(),procgen.getClassName(),codeStream);
        
        logger.log(Level.INFO, String.format("Generating code for process class:%s.%s.java", procgen.getPackageName(),procgen.getClassName()));
        
//        InputStream jis = pc.compile(nameToByteCode);
        
        String genFolder = rsp.getProperties().getProperty("be.gen.process.dir",null);
        if(genFolder != null ) {
        	File folder = new File(genFolder);
        	if(folder.exists()) {
        		File packageFolder = new File(genFolder,ModelUtils.convertPackageToPath(procgen.getPackageName()));
        		if(!packageFolder.exists()){
        			packageFolder.mkdirs();
        		}
        		File outFile = new File(packageFolder,procgen.getClassName()+".java");
        		if(outFile.exists()) {
        			outFile.delete();
        		}
        		logger.log(Level.INFO, String.format("Writing process source file:%s", outFile.getPath()));
        		FileOutputStream fos = new FileOutputStream(outFile);
        		codeStream.writeTo(fos);
        		fos.flush();
        		fos.close();
        	}
        }

//        Map<String, byte[]> result;
//        result = new HashMap<String, byte[]>();
//        result.putAll(JarInputStreamReader.read(new JarInputStream(jis)));
//        return result;
        return compilable;
    }


    /**
     * @param jis
     * @throws IOException
     * @throws Exception
     */
    private void loadClasses(InputStream jis)
            throws IOException, Exception {
        RuleServiceProvider rsp = processAgent.getRuleServiceProvider();
        Map<String, Object> nameToByteCode;
        ByteArrayInputStream bis = new ByteArrayInputStream(getCompiledBytes(jis).toByteArray());
        nameToByteCode = new HashMap<String, Object>();
        nameToByteCode.putAll(JarInputStreamReader.read(new JarInputStream(bis)));
        BEClassLoader beClassLoader = (BEClassLoader) rsp.getClassLoader(); // it can only be BEClassLoader
        Map rMap = new HashMap<String,ClassLoadingResult>();
        for(Entry<String, Object> entry:nameToByteCode.entrySet()){
            rMap.put(entry.getKey(), new ClassLoadingResult(entry.getKey(),(byte[]) entry.getValue(), ClassLoadingResult.NEW));
        }
        beClassLoader.commitChanges(rMap);
        beClassLoader.registerClasses(rMap);
    }


    /**
     * @param jis
     * @return
     * @throws IOException
     */
    private ByteArrayOutputStream getCompiledBytes(InputStream jis)
            throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = jis.read(data, 0, data.length)) != -1) {
            bos.write(data, 0, nRead);
        }
        bos.flush();
        bos.close();
        jis.close();
        return bos;


    }


    /**

     * @return
     * @throws Exception
     */
    private void  walkProcessModel() throws Exception  {

        //FlowNode is a Task/Activity/etc.

        List<MapperClassTemplate> mapperClassTemplates = new ArrayList<MapperClassTemplate>();
        DefaultProcessIndex pIndex = new DefaultProcessIndex(processModel.cast(EObject.class));
        Collection<EObject> taskNodes = EcoreUtil.filterDescendants(pIndex.getAllFlowNodes());
        indexedTasks = new Task[taskNodes.size()];
        short taskIndex = 0;
        for(EObject taskNode:taskNodes) {
            final ROEObjectWrapper<EClass, EObject> taskModel = ROEObjectWrapper.wrap(taskNode);
            final String type = BpmnMetaModel.INSTANCE.getExpandedName(taskNode.eClass()).toString();
            final String name = (String)taskModel.getAttribute(BpmnMetaModelConstants.E_ATTR_ID); // id of the task P1.SendEvent_01
            int uniqueId = -1;
            if(taskModel.containsAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID))
            	uniqueId = (Integer)taskModel.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
            
            final boolean  hasLoopCharAttr =  (Boolean) taskModel.containsAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS);
            boolean  hasLoopCharacteristics = false;
            if(hasLoopCharAttr) {
            	EObject loopChar = (EObject)  taskModel.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS);
            	hasLoopCharacteristics = (loopChar != null);
            }
            Task task      = TaskFactory.getInstance().newTask(type, name,hasLoopCharacteristics);
            InitContext ctx = new TaskInitContext(this, this.processAgent, taskModel);
            task.init(ctx, new Object[] {type, name, taskIndex, uniqueId});
            indexedTasks[taskIndex++] = task;
            allTasks.put(name, task);

            if (task instanceof Triggerable) {
            	
                if (task instanceof StartEvent) {
                    starterTasks.add(task);
                }

                triggerAbleTasks.add(task);
            }

        }


        for (Task task : allTasks.values()){

            logger.log(Level.INFO, String.format("Initializing Transition for task : %s", task.getName()));
            task.initTransitions();
        }
        return;

    }





    @Override
    public VersionInfo getVersionInfo() {
        return versionInfo;
    }





    @Override
    public Class getTemplateClass() throws Exception {
        if (processClass != null) return processClass;

        if (processName.getJavaClassName() != null) {
            processClass = Class.forName(processName.getJavaClassName(), true, processAgent.getRuleServiceProvider().getClassLoader() );
        }
        return processClass;

    }
    
    

    @Override
	public String getName() {
    	return getProcessName().getSimpleName();
	}


	@Override
    public ProcessModel getProcessModel() {
        return processModel;
    }

    @Override
    public Task getTask(String name) {
        return allTasks.get(name);
    }
    

    @Override
    public String toString() {

        return processName.toString();

    }
    


    @Override
    public Task getTask(short index) {
        return indexedTasks[index];
    }

    @Override
    public Collection<Task> allTasks() {
        return allTasks.values();
    }

    @Override
    public List<Task> getStarterTask() {
        return starterTasks;
    }

    @Override
    public List<Task> getTriggerableTask() {
        return triggerAbleTasks;
    }

    @Override
    public ProcessName getProcessName() {
        return this.processName;
    }


    public String getExceptionHandler() {
		return exceptionHandler;
	}
	
	public void setExceptionHandler(String exceptionHandler, boolean isAsyncExceptionHandler) {
		this.exceptionHandler = exceptionHandler;
		this.isAsyncExceptionHandler = isAsyncExceptionHandler;
	}
    
    public boolean isAsyncExceptionHandler() {
		return isAsyncExceptionHandler;
	}
    

}
