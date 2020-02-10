package com.tibco.cep.bpmn.runtime.activity;

/*
* Author: Suresh Subramani / Date: 11/24/11 / Time: 6:40 PM
*/

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.w3c.dom.Element;

import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.config.XMLConfiguration;

/**
 * Registry of all tasks
 */
public class TaskRegistry {


    LinkedHashMap<String, Class<? extends Task>> taskTypeRegistry;
    LinkedHashMap<String, Task> taskRegistry;
    ConcurrentHashMap<String, Transition> transitionRegistry;
    Logger logger = LogManagerFactory.getLogManager().getLogger(TaskRegistry.class);

    static final String TASK_TYPE_RESOURCE_NAME = "task-catalog.xml";



    private static TaskRegistry singleton = null;
    static {
        try {
            singleton = new TaskRegistry();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static TaskRegistry getInstance() {
        return singleton;
    }



    private TaskRegistry() throws Exception {
        initTaskTypeRegistry();
        taskRegistry = new LinkedHashMap<String, Task>();
        transitionRegistry = new ConcurrentHashMap<String, Transition>();
    }

    private  void initTaskTypeRegistry() throws Exception
    {
         taskTypeRegistry = new LinkedHashMap<String, Class<? extends Task>>();
        //
        // load any task-type.xml file in all the jar files in the root.
        //
        HashSet<URL> urlSet = new HashSet<URL>();
        Enumeration<URL> urls = TaskRegistry.class.getClassLoader().getResources(TASK_TYPE_RESOURCE_NAME);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            urlSet.add(url);
        }

        urls = TaskRegistry.class.getClassLoader().getSystemResources(TASK_TYPE_RESOURCE_NAME);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            urlSet.add(url);
        }

        for (URL url : urlSet) {
            InputStream is = url.openStream();
            XMLConfiguration config = XMLConfiguration.readConfiguration(is, "catalog");
            loadTaskTypes(null, config, null);

        }
    }

     private void loadTaskTypes(Element context, XMLConfiguration config, String parentName) throws Exception{

        List<Element> categories = config.getConfigElements(context, "category");
        for (Element el : categories) {
            String name = config.getConfigValue(el, "@name");
            String fullName = parentName == null ? name : String.format("%s.%s", parentName, name);
            loadTaskTypes(el, config, fullName);
        }

        List<Element> tasks = config.getConfigElements(context, "tasks/task");
        for (Element el : tasks) {
            String name = config.getConfigValue(el, "@name");
            String clazz = config.getConfigValue(el, "@class");

            Class<Task> clTask = (Class<Task>) Class.forName(clazz);
//            String fullName = parentName == null ? name : String.format("%s.%s", parentName, name);
            taskTypeRegistry.put(name, clTask);
        }

    }

    public Class<? extends Task> getTaskType(String taskType) {   	
        return taskTypeRegistry.get(taskType);
    }

    public synchronized void addTask(String taskId, Task task) {
    	//logger.log(Level.INFO, MessageFormat.format("Adding task :{0}", taskId));
        taskRegistry.put(taskId, task);
    }

    public Task getTask(String taskName) {
        return taskRegistry.get(taskName);
    }
    
    public void addTransition(String transitionId, Transition transition) {
    	//logger.log(Level.INFO, MessageFormat.format("Adding transition :{0}", transitionId));
        transitionRegistry.putIfAbsent(transitionId, transition);
    }
    
    public Transition getTransition(String transitionName) {
        return transitionRegistry.get(transitionName);
    }


    public Collection<Task> tasks() {
      return taskRegistry.values();
    }
    
    
    /**
     * used by the debugger to get map between task type to impl class
     * @return
     */
    public Collection<Map.Entry<String,String>> getTypeRegistry() {
    	Map<String,String> tmap = new HashMap<String,String>();
    	for(Entry<String, Class<? extends Task>> entry:taskTypeRegistry.entrySet()) {
			tmap.put(entry.getKey(), entry.getValue().getName());
		}
    	return tmap.entrySet();
    }


}
