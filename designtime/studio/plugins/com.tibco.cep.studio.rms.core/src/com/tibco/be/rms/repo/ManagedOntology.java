package com.tibco.be.rms.repo;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.model.AddOnRegistry;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.ConceptView;
import com.tibco.cep.designtime.model.element.Instance;
import com.tibco.cep.designtime.model.element.InstanceView;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.designtime.model.registry.ElementTypes;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.RuleSet;
import com.tibco.cep.designtime.model.rule.RuleTemplate;
import com.tibco.cep.designtime.model.service.calendar.Calendar;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.cep.studio.core.adapters.CoreAdapterFactory;
import com.tibco.cep.studio.rms.artifacts.ArtifactsType;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Sep 15, 2010
 * Time: 10:59:14 AM
 * Non-index implementation of {@link com.tibco.cep.studio.core.repo.emf.EMFProject} for
 * projects managed by RMS.
 */
public class ManagedOntology implements Ontology {

    /**
     * A reference to this will be needed
     */
    private ManagedEMFProject _managedProject;

    public ManagedOntology(ManagedEMFProject _managedProject) {

        this._managedProject = _managedProject;
    }

    public Entity getAlias(String alias) {
        throw new UnsupportedOperationException("To be implemented");
    }

    /**
     * Gets the Calendar at the path
     *
     * @param path The path at which to look.
     * @return The Calendar at the path provided, or null if it doesn't exist.
     */
    public Calendar getCalendar(String path) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns all Channels in the Ontology.
     *
     * @return All Channels in the Ontology.
     */
    public Collection<Channel> getChannels() {
        Collection<com.tibco.cep.designtime.core.model.Entity> channelEntities = _managedProject.getEntities(new ArtifactsType[] {ArtifactsType.CHANNEL});
        Collection<Channel> channels = new LinkedHashSet<Channel>();
		try {
			for (com.tibco.cep.designtime.core.model.Entity entity : channelEntities) {
				Channel channel = CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
				channels.add(channel);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return channels;
    }

    /**
     * Returns the Concept at the path provided.
     *
     * @param conceptPath The folderPath at which to look.
     * @return The Concept at the path provided, or null if it doesn't exist.
     */
    public Concept getConcept(String conceptPath) {
        //Get matching concept
        com.tibco.cep.designtime.core.model.Entity matchingEntity = _managedProject.getEntity(ArtifactsType.CONCEPT, conceptPath);

        try {
			if (matchingEntity != null) {
				return CoreAdapterFactory.INSTANCE.createAdapter(matchingEntity, this);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
    }

    /**
     * Returns all Concepts in the Ontology.
     *
     * @return All Concepts in the Ontology.
     */
    public Collection<Concept> getConcepts() {
        Collection<com.tibco.cep.designtime.core.model.Entity> allEntities =
			_managedProject.getEntities(new ArtifactsType[] {ArtifactsType.CONCEPT, ArtifactsType.SCORECARD, ArtifactsType.METRIC});
		Collection<Concept> concepts = new LinkedHashSet<Concept>();
		try {
			for (com.tibco.cep.designtime.core.model.Entity entity : allEntities) {
				Concept concept = CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
				concepts.add(concept);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return concepts;
    }

    /**
     * Returns the ConceptView at the path provided.
     *
     * @param path The folderPath at which to look.
     * @return The ConceptView at the path provided, or null if it doesn't exist.
     */
    public ConceptView getConceptView(String path) {
        throw new UnsupportedOperationException("May not be needed");
    }

    /**
     * Returns all Entities in this Ontology (except for Folders).
     *
     * @return All Entities in this Ontology (except for Folders).
     */
    public Collection<Entity> getEntities() {
        LinkedHashSet<Entity> allEntities  = new LinkedHashSet<Entity>();
		Collection<com.tibco.cep.designtime.core.model.Entity> allManagedEntities = _managedProject.getEntities(ArtifactsType.values());

        try {
	        for (com.tibco.cep.designtime.core.model.Entity adaptedEntity : allManagedEntities) {
	        	Entity entity = CoreAdapterFactory.INSTANCE.createAdapter(adaptedEntity, this);
	        	if (entity != null) {
	        		allEntities.add(entity);
	        	}
	        }
//	        allEntities.addAll(getRuleFunctions());
//	        allEntities.addAll(getRules());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return allEntities;
    }

    /**
     * Returns an Entity with the specified Folder String representation and name.
     *
     * @param folder
     * @param name
     * @return an Entity
     */
    public Entity getEntity(String folder, String name) {
        throw new UnsupportedOperationException("To be implemented");
    }

    /**
     * Returns the Entity for the ExpandedName provided.
     *
     * @param name The ExpandedName for which to look.
     * @return Entity for the given ExandedName.
     */
    public Entity getEntity(ExpandedName name) {
        /**
		 * We have to keep the same namespace here. The runtime uses this namespace
		 */
		String fullPath = name.getNamespaceURI().substring(RDFTnsFlavor.BE_NAMESPACE.length());
		return getEntity(fullPath);
    }

    /**
     * Returns the Entity at the path provided.
     *
     * @param entityPath The path at which to look.
     * @return The Entity at the path provided, or null if it doesn't exist.
     */
    public Entity getEntity(String entityPath) {
        com.tibco.cep.designtime.core.model.Entity entity = _managedProject.getEntity(entityPath);
		try {
			return CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

    /**
     * Returns the Event at the eventPath provided.
     *
     * @param eventPath The eventPath at which to look.
     * @return The Event at the eventPath provided, or null if it doesn't exist.
     */
    public Event getEvent(String eventPath) {
        //Get matching event
        com.tibco.cep.designtime.core.model.Entity matchingEntity =
                _managedProject.getEntity(new ArtifactsType[] {ArtifactsType.EVENT, ArtifactsType.TIMEEVENT}, eventPath);

        try {
			if (matchingEntity != null) {
				return CoreAdapterFactory.INSTANCE.createAdapter(matchingEntity, this);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
    }

    /**
     * Returns all Events in the Ontology.
     *
     * @return All Events in the Ontology.
     */
    public Collection<Event> getEvents() {
        Collection<com.tibco.cep.designtime.core.model.Entity> allEntities =
			_managedProject.getEntities(new ArtifactsType[] {ArtifactsType.EVENT, ArtifactsType.TIMEEVENT, ArtifactsType.METRIC});
		Collection<Event> events = new LinkedHashSet<Event>();
		try {
			for (com.tibco.cep.designtime.core.model.Entity entity : allEntities) {
				Event event = CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
				events.add(event);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return events;
    }

    /**
     * Gets a Folder with the fully specified Folder name.
     *
     * @param fullPath A String naming the full Folder.
     * @return The Folder corresponding to the Path, or null.
     */
    public Folder getFolder(String fullPath) {
        return new ManagedFolder(this, fullPath);
    }

    /**
     * Returns the Instance at the path provided.
     *
     * @param path The path at which to look.
     * @return The Instance at the path provided, or null if it doesn't exist.
     */
    public Instance getInstance(String path) {
        throw new UnsupportedOperationException("May not be needed");
    }

    /**
     * Returns all Instances in the Ontology.
     *
     * @return All Instances in the Ontology.
     */
    public Collection<?> getInstances() {
        throw new UnsupportedOperationException("May not be needed");
    }

    /**
     * Returns the InstanceView at the path provided.
     *
     * @param path The folderPath at which to look.
     * @return The InstanceView at the path provided, or null if it doesn't exist.
     */
    public InstanceView getInstanceView(String path) {
        throw new UnsupportedOperationException("May not be needed");
    }

    /**
     * returns the last modified timestamp
     *
     * @return
     * @since 4.0
     */
    public Date getLastModifiedDate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * returns the last serialized timestamp
     *
     * @return
     * @since 4.0
     */
    public Date getLastPersistedDate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getName() {
        return _managedProject.getName();
    }

    /**
     * Returns all PropertyInstances in the Ontology.
     *
     * @return All PropertyInstances in the Ontology.
     */
    public Collection<?> getPropertyInstances() {
        throw new UnsupportedOperationException("May not be needed");
    }

    /**
     * Returns the top most Folder.
     *
     * @return The top most Folder.
     */
    public Folder getRootFolder() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RuleFunction getRuleFunction(String ruleFunctionPath) {
        Compilable compilable = _managedProject.getRuleElement(ArtifactsType.RULEFUNCTION, ruleFunctionPath);
        try {
			if (compilable != null) {
				return CoreAdapterFactory.INSTANCE.createAdapter(compilable, this);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
    }

    public Collection<RuleFunction> getRuleFunctions() {
    	Collection<com.tibco.cep.designtime.core.model.Entity> allEntities =
    			_managedProject.getEntities(new ArtifactsType[] {ArtifactsType.RULEFUNCTION});
    	Collection<RuleFunction> ruleFunctions = new LinkedHashSet<RuleFunction>();
    	try {
    		for (com.tibco.cep.designtime.core.model.Entity entity : allEntities) {
    			RuleFunction ruleFunction = CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
    			ruleFunctions.add(ruleFunction);
    		}
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    	return ruleFunctions;
    }

    /**
     * Returns all Rules from all RuleSets in the Ontology.
     *
     * @return All Rules from all RuleSets in the Ontology.
     */
    public Collection<Rule> getRules() {
    	Collection<com.tibco.cep.designtime.core.model.Entity> allEntities =
    			_managedProject.getEntities(new ArtifactsType[] {ArtifactsType.RULE});
    	Collection<Rule> rules = new LinkedHashSet<Rule>();
    	try {
    		for (com.tibco.cep.designtime.core.model.Entity entity : allEntities) {
    			Rule rule = CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
    			rules.add(rule);
    		}
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    	return rules;
    }

    @Override
    public Collection<RuleTemplate> getRuleTemplates() {
    	Collection<com.tibco.cep.designtime.core.model.Entity> allEntities =
    			_managedProject.getEntities(new ArtifactsType[] {ArtifactsType.RULETEMPLATE});
    	Collection<RuleTemplate> ruleTemplates = new LinkedHashSet<RuleTemplate>();
    	try {
    		for (com.tibco.cep.designtime.core.model.Entity entity : allEntities) {
    			RuleTemplate ruleTemplate = CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
    			ruleTemplates.add(ruleTemplate);
    		}
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    	return ruleTemplates;
    }

    /**
     * Returns the RuleSet at the path provided.
     *
     * @param path The path at which to look.
     * @return The RuleSet at the path provided, or null if it doesn't exist.
     */
    public RuleSet getRuleSet(String path) {
        throw new UnsupportedOperationException("To be done");
    }

    /**
     * Returns all RuleSets in the Ontology.
     *
     * @return All RuleSets in the Ontology.
     */
    public Collection<?> getRuleSets() {
        throw new UnsupportedOperationException("To be done");
    }

    public Collection<?> getStandaloneRules() {
        throw new UnsupportedOperationException("To be done");
    }

    public Collection<?> getStandaloneStateMachines() {
        throw new UnsupportedOperationException("To be done");
    }

    /**
     * Returns all Views in the Ontology.
     *
     * @return All Views in the Ontology.
     */
    public Collection<?> getViews() {
        throw new UnsupportedOperationException("To be done");
    }

    /**
     * Serializes an Entity to a particular Stream.
     *
     * @param entity The Entity to serialize.
     * @param out    The stream on which to serialize the entity.
     */
    public void serializeEntity(Entity entity, OutputStream out) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<Entity> getEntities(ElementTypes[] types) {
    	AddOn addon;
		Collection<Entity> entities = new HashSet<Entity>();
		try {
			// make a list of ELEMENT_TYPES supported by this AddOn
			addon = AddOnRegistry.getInstance().getAddOnMap()
					.get(AddOnType.CORE);
			List<ArtifactsType> artifactTypes = new ArrayList<ArtifactsType>();
			for (ElementTypes type : addon.getSupportedEntityTypes()
					.getElementType()) {
				if (Arrays.asList(types).contains(type)) {
					ArtifactsType modelElementType = getModelElementType(type);
					if (modelElementType != null)artifactTypes.add(modelElementType);
				}
			}
			
			// make a list of the selected Artifact Types
			Collection<com.tibco.cep.designtime.core.model.Entity> allEntities =
	    			_managedProject.getEntities(artifactTypes.toArray(new ArtifactsType[artifactTypes.size()]));
			
			for (com.tibco.cep.designtime.core.model.Entity entity : allEntities) {
				if (entity instanceof Rule){
					Rule rule = CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
					entities.add(rule);
				} else if (entity instanceof RuleFunction){
					RuleFunction ruleFunction = CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
					entities.add(ruleFunction);
				} else {
					Entity et = CoreAdapterFactory.INSTANCE.createAdapter(entity, this);
					if (et != null) {
						entities.add(et);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return entities;
    }
    
    /**
     * 
     * @param type
     * @return
     */
    private ArtifactsType getModelElementType(ElementTypes type){
    	for (ArtifactsType aType : (List<ArtifactsType>)ArtifactsType.VALUES){
    		if (aType.name().equals(type.name())){
    			return aType;
    		}
    	}
    	return null;
    }
}
