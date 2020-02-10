package com.tibco.cep.studio.core.functions.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.studio.common.configuration.IStudioProjectConfigurationChangeListener;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationChangeEvent;
import com.tibco.cep.studio.common.configuration.update.IStudioProjectConfigurationDelta;
import com.tibco.cep.studio.common.configuration.update.StudioProjectConfigurationDelta;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class EMFOntologyFunctionResourceChangeListener implements
		IResourceChangeListener, IStudioProjectConfigurationChangeListener {

	private static EMFOntologyFunctionResourceChangeListener INSTANCE;
	
	private HashMap<String, EMFModelFunctionsFactory> fFactories = new HashMap<String, EMFModelFunctionsFactory>();
	private List<FunctionsCategory> m_categoryList = new ArrayList<FunctionsCategory>();

	private EMFOntologyFunctionResourceChangeListener() {
		super();
	}

	public static EMFOntologyFunctionResourceChangeListener getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new EMFOntologyFunctionResourceChangeListener();
		}
		return INSTANCE;
	}
	
	public void addModelFactory(String projectName, EMFModelFunctionsFactory factory) {
		if (!fFactories.containsKey(projectName)) {
			fFactories.put(projectName, factory);
		}
	}
	
	public void removeModelFactory(String projectName) {
		if (fFactories.containsKey(projectName)) {
			fFactories.remove(projectName);
		}
	}
	
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		try {
			if (IResourceChangeEvent.POST_CHANGE == event.getType()
					|| IResourceChangeEvent.PRE_DELETE == event.getType()) {

				IResourceDelta delta = event.getDelta();
				if (delta == null) return;
				IResource resource = delta.getResource();				
				if (IResourceChangeEvent.PRE_DELETE == event.getType()) {
					if (resource instanceof IProject) {
						clear(resource.getName());
					}
					return;
				}
				delta.accept(new ModelFunctionsUpdateVisitor());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private class ModelFunctionsUpdateVisitor implements IResourceDeltaVisitor {

		public boolean visit(IResourceDelta delta) throws CoreException {
			try {
				IResource resource = delta.getResource();
				if (resource instanceof IFile) {
					IFile file = (IFile) resource;
					processFile(file, delta);
				} else if (resource instanceof IFolder && delta.getKind() == IResourceDelta.REMOVED) {
					removeFolder((IFolder) resource, delta.getKind());
					return false;
				} else if (resource instanceof IProject && delta.getKind() == IResourceDelta.REMOVED) {
					removeModelFactory(resource.getName());
					FunctionsCatalogManager.getInstance().getOntologyRegistry().deregister(FunctionsCatalogManager.getInstance().getOntologyCatalogName(resource.getName()));
					StudioCorePlugin.getDefault().fireCatalogChangedEvent(null);
					return false;
				}
			} catch (Exception e){
				StudioCorePlugin.log(e);
				return false;
			}
			return true;
		}

		private void removeFolder(IFolder file, int deltaKind) throws Exception {
			EMFModelFunctionsFactory factory = fFactories.get(file.getProject().getName());
			if (factory == null) {
				return;
			}
			if (deltaKind == IResourceDelta.REMOVED) {
				factory.removeFolder(file);
				return;
			}

		}
		
		private void processFile(IFile file, IResourceDelta delta) throws Exception {
			EMFModelFunctionsFactory factory = fFactories.get(file.getProject().getName());
			if (factory == null) {
				return;
			}
			if (delta.getKind() == IResourceDelta.REMOVED) {
				factory.removeElement(file);
				return;
			}
			if (delta.getFlags() == IResourceDelta.MARKERS) {
				// no need to update function
				return;
			}
			IndexUtils.waitForUpdateAll();
			DesignerElement element = IndexUtils.getElement(file);
			if (element == null) {
				StudioCorePlugin.debug("Could not get element from file "+file.getName());
			}
			if (element instanceof EntityElement) {
				Entity entity = ((EntityElement) element).getEntity();
				factory.processEntity(entity);
//				handle dependent entities (sub-concepts, sub-events)
				processSubEntities(factory, entity);
			} else if (element instanceof RuleElement && element.getElementType() == ELEMENT_TYPES.RULE_FUNCTION) {
				factory.processRuleElement((RuleElement) element);
			}
		}

	}

    /**
     * force Model Functions to be loaded 
     * @param ontology
     * @throws Exception
     */
    private void force(String projectName) throws Exception {
//    	if (fInstances.containsKey(projectName)) {
//    		fInstances.remove(projectName);
//    	}
//        loadOntology(projectName);

    }
    
	private void processSubEntities(EMFModelFunctionsFactory factory, Entity entity) {
		if (entity instanceof Concept) {
			processSubConcepts(factory, (Concept)entity);
		} else if (entity instanceof Event) {
			processSubEvents(factory, (Event)entity);
		}
	}

	private void processSubEvents(EMFModelFunctionsFactory factory, Event parentEvent) {
		List<Event> subEvents = new ArrayList<Event>();
		String parentEventPath = parentEvent.getFullPath();
		List<Entity> events = IndexUtils.getAllEntities(parentEvent.getOwnerProjectName(), ELEMENT_TYPES.SIMPLE_EVENT);
		for (int i = 0; i < events.size(); i++) {
			Event event = (Event) events.get(i);
			if (event instanceof SimpleEvent && isSubEvent(parentEventPath, event)) {
				addSubEvents(subEvents, parentEventPath, (SimpleEvent) event);
			}
		}
		for (Event event : subEvents) {
			try {
				factory.processEntity(event);
			} catch (Exception e) {
				StudioCorePlugin.log(e);
			}
		}
	}
	private void addSubEvents(List<Event> subEvents, String parentEventPath, SimpleEvent event) {
		if (!subEvents.contains(event)) {
			subEvents.add(event);
		}
		while (event.getSuperEvent() != null) {
			event = (SimpleEvent) event.getSuperEvent();
			if (parentEventPath.equals(event.getFullPath())) {
				break;
			}
			if (!subEvents.contains(event)) {
				subEvents.add(event);
			}
		}
	}

	private boolean isSubEvent(String parentEventPath, Event event) {
		if (event == null || event.getSuperEvent() == null) {
			return false;
		}
		if (parentEventPath.equals(event.getFullPath())) {
			return false; // this is the 'root' event, stop looking up the chain
		}
		if (parentEventPath.equals(event.getSuperEvent().getFullPath())) {
			return true;
		}
		if (event.getSuperEvent() != null) {
			return isSubEvent(parentEventPath, event.getSuperEvent());
		}
		return false;
	}

	private void processSubConcepts(EMFModelFunctionsFactory factory, Concept superConcept) {
		List<Concept> subConcepts = new ArrayList<Concept>();
		String superConceptPath = superConcept.getFullPath();
		List<Entity> concepts = IndexUtils.getAllEntities(superConcept.getOwnerProjectName(), ELEMENT_TYPES.CONCEPT);
		for (int i = 0; i < concepts.size(); i++) {
			Concept concept = (Concept) concepts.get(i);
			if (isSubConcept(superConceptPath, concept)) {
				addSubConcepts(subConcepts, superConceptPath, concept);
			}
		}
		for (Concept concept : subConcepts) {
			try {
				factory.processEntity(concept);
			} catch (Exception e) {
				StudioCorePlugin.log(e);
			}
		}
	}

	private void addSubConcepts(List<Concept> subConcepts, String superConceptPath, Concept concept) {
		if (!subConcepts.contains(concept)) {
			subConcepts.add(concept);
		}
		while (concept.getSuperConcept() != null) {
			concept = concept.getSuperConcept();
			if (superConceptPath.equals(concept.getFullPath())) {
				break;
			}
			if (!subConcepts.contains(concept)) {
				subConcepts.add(concept);
			}
		}
	}

	private boolean isSubConcept(String superConceptPath, Concept concept) {
		if (concept == null || concept.getSuperConcept() == null) {
			return false;
		}
		if (superConceptPath.equals(concept.getFullPath())) {
			return false; // this is the 'root' concept, stop looking up the chain
		}
		if (superConceptPath.equals(concept.getSuperConcept().getFullPath())) {
			return true;
		}
		if (concept.getSuperConcept() != null) {
			return isSubConcept(superConceptPath, concept.getSuperConcept());
		}
		return false;
	}

	/**
	 * Clears model functions for a specific project
	 */
    public void clear(String projectName) {
    	if (projectName == null) return;
    	for (FunctionsCategory cat : m_categoryList){
    		String catName = cat.getName().getLocalName();
    		if ((projectName+"."+"Ontology").equals(catName)){
    			((EMFFunctionsCategoryImpl)cat).clear();
    			break;
    		}
    	}
    }
    
    /**
     * clear all Categories
     */
    public void clearAll() {    	
    	for (FunctionsCategory cat : m_categoryList){    	
    		((EMFFunctionsCategoryImpl)cat).clear();   			
    	}
    }
    
	@Override
	public void configurationChanged(StudioProjectConfigurationChangeEvent event) {
		StudioProjectConfigurationDelta delta = event.getDelta();
		String projectName = StudioProjectConfigurationManager.getInstance().getProjectName(delta.getAffectedChild());
		if (projectName == null || delta.getType() == IStudioProjectConfigurationDelta.REMOVED) {
			return;
		}
		DesignerProject index = IndexUtils.getIndex(projectName);
		EMFModelFunctionsFactory factory = fFactories.get(projectName);
		if (factory == null) {
			// hasn't been loaded yet, no need to do anything
		} else {
			factory.updateProjectReferences(index);
		}
	}

}
