package com.tibco.cep.studio.core.util;

import java.io.IOException;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class MutableUtils extends IndexUtils {

	/**
	 * @param projectName
	 * @param folderPath
	 * @param namespace
	 * @param name
	 * @param superConceptPath
	 * @param autoNameOnConflict
	 * @param persist
	 * @return
	 */
	public static Concept createConcept(String projectName, 
			String folderPath, 
			String namespace, 
			String name, 
			String superConceptPath, 
			boolean autoNameOnConflict,
			boolean persist) {
		Concept concept = createConcept(projectName, folderPath, namespace, name, superConceptPath, autoNameOnConflict);
		if(persist) {
			persistEntity(projectName, concept, new NullProgressMonitor());	
		}
		return concept;
	}
	
	/**
	 * @param projectName
	 * @param folderPath
	 * @param namespace
	 * @param name
	 * @param superConceptPath
	 * @param autoNameOnConflict
	 * @param persist
	 * @return
	 */
	public static Scorecard createScoreCard(String projectName, 
			String folderPath, 
			String namespace, 
			String name, 
			String superConceptPath, 
			boolean autoNameOnConflict,
			boolean persist) {
		Scorecard scorecard = createScoreCard(projectName, folderPath, namespace, name, superConceptPath, autoNameOnConflict);
		if(persist) {
			persistEntity(projectName, scorecard, new NullProgressMonitor());	
		}
		return scorecard;
	}
	/**
	 * @param projectName
	 * @param folderPath
	 * @param namespace
	 * @param name
	 * @param ttl
	 * @param ttlUnits
	 * @param renameOnConflict
	 * @param persist
	 * @return
	 */
	public static SimpleEvent createEvent(String projectName, String folderPath,
			String namespace, String name, String ttl, TIMEOUT_UNITS ttlUnits,
			boolean renameOnConflict, boolean persist) {
		SimpleEvent event = createEvent(projectName, folderPath, namespace, name, ttl, ttlUnits, renameOnConflict);
		if(persist) {
			persistEntity(projectName, event, new NullProgressMonitor());	
		}
		return event;
	}
	
	/**
	 * @param projectName
	 * @param folderPath
	 * @param namespace
	 * @param name
	 * @param ttl
	 * @param ttlUnits
	 * @param interval
	 * @param intervalUnits
	 * @param scheduleType
	 * @param renameOnConflict
	 * @param persist
	 * @return
	 */
	public static Event createTimeEvent(String projectName, 
			String folderPath,
			String namespace, 
			String name, 
			String ttl, 
			TIMEOUT_UNITS ttlUnits,
			String interval, 
			TIMEOUT_UNITS intervalUnits,
			EVENT_SCHEDULE_TYPE scheduleType, 
			boolean renameOnConflict,
			boolean persist) {
		TimeEvent event = createTimeEvent(projectName, folderPath, namespace,
				name, ttl, ttlUnits, interval, intervalUnits, scheduleType,
				renameOnConflict);
		if (persist) {
			persistEntity(projectName, event, new NullProgressMonitor());
		}
		return event;
	}
	
	/**
	 * @param projectName
	 * @param entity
	 * @param monitor
	 */
	public static void persistEntity(String projectName, Entity entity, IProgressMonitor monitor ) {
		Map<?, ?> options = ModelUtilsCore.getPersistenceOptions();
		if(monitor!=null) {
			monitor.subTask("persisting "+entity.getName());
		}
		
		IFile file = getFile(projectName, entity);
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toPortableString(),false);
		ResourceSet resourceSet = new ResourceSetImpl();//using XMI
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(entity);
		try {
			resource.save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(monitor!=null) {
			monitor.worked(1);
		}
    }
	
	/**
	 * @param project
	 * @param entity
	 * @param monitor
	 */
	public static void persistEntityInProject(IProject project, Entity entity, IProgressMonitor monitor ) {
		Map<?, ?> options = ModelUtilsCore.getPersistenceOptions();
		if(monitor!=null) {
			monitor.subTask("persisting "+entity.getName());
		}
		String folder = entity.getFolder();
		String extension = IndexUtils.getFileExtension(entity);
		URI uri = URI.createFileURI(project.getLocation().toString()+folder+entity.getName()+"."+extension);
		ResourceSet resourceSet = new ResourceSetImpl();//using XMI
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(entity);
		try {
			resource.save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(monitor!=null) {
			monitor.worked(1);
		}
    }
	
	public static String getCurrentWorkspacePath() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		return workspace.getRoot().getLocation().toPortableString();
	}

	public static void deleteEntity(Entity entity) {
		// TODO : either delete entity here or delegate call to Entity itself
	}
	
	/**
	 * Creates a studio project rule
	 * @param projectName
	 * @param folderPath
	 * @param namespace
	 * @param name
	 * @param persist
	 * @return
	 * @throws Exception
	 */
	public static Rule createRule(String projectName, String folderPath,
			String namespace, String name, boolean persist) throws Exception {
		com.tibco.cep.designtime.core.model.rule.Rule rule = createRule(projectName, folderPath, namespace, name);
		if(persist) {
			IFile file = IndexUtils.getFile(projectName,rule);
			URI entityURI =  URI.createPlatformResourceURI(file.getFullPath().toPortableString(), false);
			ModelUtilsCore.persistRule(rule, entityURI);
		}
		return rule;
	}
	
	/**
	 * creates a Studio project rule function
	 * @param projectName
	 * @param folderPath
	 * @param namespace
	 * @param name
	 * @param persist
	 * @return
	 * @throws Exception
	 */
	public static RuleFunction createRulefunction(String projectName,
			String folderPath, String namespace, String name, boolean persist)
			throws Exception {
		com.tibco.cep.designtime.core.model.rule.RuleFunction rulefunction = createRulefunction(
				projectName, folderPath, namespace, name);
		if (persist) {
			IFile file = IndexUtils.getFile(projectName, rulefunction);
			URI entityURI = URI.createPlatformResourceURI(file.getFullPath()
					.toPortableString(), false);
			ModelUtilsCore.persistRuleFunction(rulefunction, entityURI);
		}
		return rulefunction;
	}
	
	public static void createRuleFunctionFile(RuleFunction convertedRuleFn, URI fileURI) throws Exception{
    	ModelUtilsCore.persistRuleFunction(convertedRuleFn, fileURI);
    }
	
    
	public static void createRuleFile(Rule convertedRule, URI fileURI) throws Exception{
		ModelUtilsCore.persistRule(convertedRule, fileURI);
	}

}
