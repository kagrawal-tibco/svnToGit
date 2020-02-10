package com.tibco.cep.bpmn.runtime.templates;


import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.bpmn.runtime.utils.FQName;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/*
* Author: Suresh Subramani / Date: 11/24/11 / Time: 5:12 PM
*/

/**
 * JobImpl Template registry.
 */
public class ProcessTemplateRegistry {

    static ProcessTemplateRegistry singleton = new ProcessTemplateRegistry();

    ConcurrentHashMap<String, ProcessTemplate> templates = new ConcurrentHashMap<String, ProcessTemplate>();

    private ProcessTemplateRegistry() {
    }

    public static ProcessTemplateRegistry getInstance() {
        return singleton;
    }

    public void addProcessTemplate(ProcessTemplate template) throws Exception
    {
        ProcessTemplate old = templates.putIfAbsent(template.getProcessName().getJavaClassName(), template);
        if (old != null) {
            String msg = String.format("Template[%s] of class[%s] already exists in the registry", template.getProcessName().getSimpleName(),
                                        template.getProcessName().getJavaClassName());
            throw new Exception(msg);
        }
    }

    /**
     * Get a processTemplate given a fully qualified class name. The classname includes version nos.
     * @param fqName
     * @return
     */
    public ProcessTemplate getProcessTemplate(String fqName)
    {
        return templates.get(fqName);
    }

    public Collection<ProcessTemplate> templates() {
        return templates.values();
    }
    
    /**
     * Get the latest process template from the Collection given a URI, and Ontology. The URI is typically the full path as defined by the process in the designtime model
     * @param ontology
     * @param uri
     * @return
     */
    public ProcessTemplate getProcessTemplateFromURI(Ontology ontology,String uri) {
    	if(uri.endsWith(CommonIndexUtils.PROCESS_EXTENSION)){
			int index = uri.indexOf(CommonIndexUtils.PROCESS_EXTENSION);
			uri = uri.substring(0,index-1);
		}
		ProcessModel procModel = (ProcessModel) ontology.getEntity(uri);

        return getProcessTemplate(procModel);

    }

    public ProcessTemplate getProcessTemplate(ProcessModel procModel) {

        final FQName fqName = FQName.makeName(procModel.getFullPath(), procModel.getRevision());
		final String processName = ModelNameUtil.modelPathToGeneratedClassName(fqName.getName());
		final ProcessTemplate processTemplate = this.getProcessTemplate(processName);
		return processTemplate;

    }
    


}


