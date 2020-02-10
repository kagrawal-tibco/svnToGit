package com.tibco.cep.designtime.model;


import java.util.Collection;
import java.util.Date;

import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.registry.ElementTypes;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.RuleTemplate;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.xml.data.primitive.ExpandedName;


/**
 * The Ontology is the centralized location for all Entities within the Data Model.  It provides
 * methods for creating, modifying, retrieving and deleting entities from the system.
 * <p/>
 * All methods listed here are identical in functionality to methods found on the various other classes that
 * comprise the data model.
 *
 * @author ishaan
 * @version Mar 18, 2004 4:35:01 PM
 */
public interface Ontology {

    public static final String CEP_EMF_NS = "http:///www.tibco.com/be/ontology/emf/be-ontology.ecore";
    public static final String XMI_NS = "http://www.omg.org/XMI";
    public static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
    

    
    /**
     * @param alias
     * @return
     */
    Entity getAlias(String alias);


    /**
     * Returns all Channels in the Ontology.
     *
     * @return All Channels in the Ontology.
     */
    Collection<Channel> getChannels();


    /**
     * Returns the Concept at the path provided.
     *
     * @param path The folderPath at which to look.
     * @return The Concept at the path provided, or null if it doesn't exist.
     */
    Concept getConcept(String path);


    /**
     * Returns all Concepts in the Ontology.
     *
     * @return All Concepts in the Ontology.
     */
    Collection<Concept> getConcepts();

    /**
     * Returns all Entities in this Ontology (except for Folders).
     *
     * @return All Entities in this Ontology (except for Folders).
     */
    Collection<Entity> getEntities();


    /**
     * @param types
     * @return
     */
    Collection<Entity> getEntities(ElementTypes[] types);


    /**
     * Returns the Entity for the ExpandedName provided.
     *
     * @param name The ExpandedName for which to look.
     * @return Entity for the given ExandedName.
     */
    Entity getEntity(ExpandedName name);


    /**
     * Returns the Entity at the path provided.
     *
     * @param path The path at which to look.
     * @return The Entity at the path provided, or null if it doesn't exist.
     */
    Entity getEntity(String path);

    /**
     * Returns an Entity with the specified Folder String representation and name.
     *
     * @param folder
     * @param name
     * @return an Entity
     */
    Entity getEntity(String folder, String name);

    /**
     * Returns the Event at the path provided.
     *
     * @param path The path at which to look.
     * @return The Event at the path provided, or null if it doesn't exist.
     */
    Event getEvent(String path);


    /**
     * Returns all Events in the Ontology.
     *
     * @return All Events in the Ontology.
     */
    Collection<Event> getEvents();


    /**
     * Gets a Folder with the fully specified Folder name.
     *
     * @param fullPath A String naming the full Folder.
     * @return The Folder corresponding to the Path, or null.
     */
    Folder getFolder(String fullPath);


    /**
     * returns the last modified timestamp
     * @since 4.0
     * @return
     */
    Date getLastModifiedDate();


    /**
     * returns the last serialized timestamp
     * @since 4.0
     * @return
     */
    Date getLastPersistedDate();


    /**
     * returns the project name
     * @return
     */
    String getName();

    /**
     * Returns the top most Folder.
     *
     * @return The top most Folder.
     */
    Folder getRootFolder();



    /**
     * @param path
     * @return
     */
    RuleFunction getRuleFunction(String path);
   
    
    /**
     * @return
     */
    Collection<RuleFunction> getRuleFunctions();
    
    /**
     * Returns all Rules from all RuleSets in the Ontology.
     *
     * @return All Rules from all RuleSets in the Ontology.
     */
    Collection<Rule> getRules();


    Collection<RuleTemplate> getRuleTemplates();


    Collection getStandaloneStateMachines();

}
