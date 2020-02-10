package com.tibco.cep.designtime.model.mutable;


import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.EntityChangeListener;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.mutable.MutableConcept;
import com.tibco.cep.designtime.model.element.mutable.MutableConceptView;
import com.tibco.cep.designtime.model.element.mutable.MutableInstance;
import com.tibco.cep.designtime.model.element.mutable.MutableInstanceView;
import com.tibco.cep.designtime.model.element.mutable.MutablePropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.MutablePropertyInstance;
import com.tibco.cep.designtime.model.event.mutable.MutableEvent;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleFunction;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleSet;
import com.tibco.cep.designtime.model.rule.mutable.MutableStandaloneRule;
import com.tibco.cep.designtime.model.service.calendar.mutable.MutableCalendar;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableChannel;


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

public interface MutableOntology extends Ontology {


    /**
     * Creates a new Folder.
     *
     * @param fullPath           The full path for the Folder; this must begin with the Folder path separator.  The path separator may be omitted from the end.
     * @param autoNameOnConflict If a naming conflict occurs, whether or not to handle it automatically.
     * @return The newly created Folder.
     * @throws ModelException Thrown if the Folder already exists
     */
    public MutableFolder createFolder(String fullPath, boolean autoNameOnConflict) throws ModelException;


    /**
     * Sets the parent for a Folder.
     *
     * @param f      The Folder whose parent to set.
     * @param parent The new parent for f.
     * @throws ModelException Thrown if this operation will cause a loop in the Folder tree.
     */
    public void setFolderParent(MutableFolder f, MutableFolder parent) throws ModelException;


    /**
     * Creates a Concept.
     *
     * @param folder             The Folder for the Concept.
     * @param name               The name for the new Concept.
     * @param superConcept       The super MutableConcept for the new Concept.
     * @param autoNameOnConflict If a naming conflict occurs, whether or not to handle it automatically.
     * @return The new Concept.
     * @throws ModelException Thrown if folder already has a Concept with the specified name.
     */
    public MutableConcept createConcept(MutableFolder folder, String name, Concept superConcept, boolean autoNameOnConflict) throws ModelException;


    /**
     * Creates a Concept.
     *
     * @param folder             The full specified folder name for the Concept.
     * @param name               The name for the new Concept.
     * @param superConceptPath   The super Concept for the new MutableConcept.
     * @param autoNameOnConflict If a naming conflict occurs, whether or not to handle it automatically.
     * @return The new Concept.
     * @throws ModelException Thrown if the folder already has a Concept with the specified name.
     */
    public MutableConcept createConcept(String folder, String name, String superConceptPath, boolean autoNameOnConflict) throws ModelException;


    /**
     * @param folder
     * @param name
     * @param superConceptPath
     * @param autoNameOnConflict
     * @param isScorecard
     * @return
     * @throws ModelException
     */
    public MutableConcept createConcept(String folder, String name, String superConceptPath, boolean autoNameOnConflict, boolean isScorecard) throws ModelException;


    /**
     * @param folder
     * @param name
     * @param autoNameOnConflict
     * @return
     * @throws ModelException
     */
//    public MutableDBConcept createDBConcept(String folder, String name, boolean autoNameOnConflict) throws ModelException;


    /**
     * Creates a ConceptView.
     *
     * @param folder             The path for ConceptView.
     * @param name               The name for this view.
     * @param autoNameOnConflict If a naming conflict occurs, whether or not to handle it automatically.
     * @return The new ConceptView.
     * @throws ModelException Thrown if a ConceptView with the same name already exists in the folder.
     */
    public MutableConceptView createConceptView(String folder, String name, boolean autoNameOnConflict) throws ModelException;


    /**
     * Creates an Instance of a Concept.
     *
     * @param folder             The Folder for the new Instance.
     * @param concept            The Concept of which to create an Instance.
     * @param name               The name for the new Instance.
     * @param autoNameOnConflict If a naming conflict occurs, whether or not to handle it automatically.
     * @return Thrown if the Concept has an Instance with the specified name and Folder.
     */
    public MutableInstance createInstance(MutableFolder folder, Concept concept, String name, boolean autoNameOnConflict) throws ModelException;


    /**
     * Creates an Instance of a Concept.
     *
     * @param domain             The domain for the new Instance.
     * @param conceptPath        The Concept of which to create an Instance.
     * @param name               The name for the new Instance.
     * @param autoNameOnConflict If a naming conflict occurs, whether or not to handle it automatically.
     * @return Thrown if the Concept has an Instance with the specified name and Folder.
     */
    public MutableInstance createInstance(String domain, String conceptPath, String name, boolean autoNameOnConflict) throws ModelException;


    /**
     * Sets which Concept owns an Instance.
     *
     * @param instance The Instance.
     * @param concept  The Concept.
     * @throws ModelException
     */
    public void setConcept(MutableInstance instance, MutableConcept concept) throws ModelException;


    /**
     * Sets the primary Domain of an Instance.
     *
     * @param instance The Instance of which to set the primary Folder.
     * @param folder   The Folder to add.
     * @throws ModelException Thrown if the Folder already has an Instance with instance's name.
     */
    public void setPrimaryDomain(MutableInstance instance, MutableFolder folder) throws ModelException;


    /**
     * Adds a secondary Domain to an Instance.
     *
     * @param instance   The instance for which to add a secondary Folder.
     * @param folderPath The Folder to be added.
     * @throws ModelException Thrown if instance already is part of Folder.
     */
    public void addSecondaryDomain(MutableInstance instance, String folderPath) throws ModelException;


    /**
     * Removes a secondary Folder from an Instance.
     *
     * @param instance   The Instance to remove.
     * @param folderPath The Folder from which to remove instance.
     */
    public void removeSecondaryDomain(MutableInstance instance, String folderPath);


    /**
     * Sets the value of a PropertyInstance.
     *
     * @param instance The PropertyInstance whose value is to be set.
     * @param value    An object representing the value for the PropertyInstance.
     * @throws ModelException Thrown if the value does not match the type of the PropertyInstance.
     */
    public void setValue(MutablePropertyInstance instance, String value) throws ModelException;


    /**
     * Creates an InstanceView.
     *
     * @param folder             The name for the InstanceView.
     * @param name               The description for the View.
     * @param autoNameOnConflict If a naming conflict occurs, whether or not to handle it automatically.
     * @return The new InstanceView.
     * @throws ModelException Thrown if an InstanceView with the name exists.
     */
    public MutableInstanceView createInstanceView(String folder, String name, boolean autoNameOnConflict) throws ModelException;


    /**
     * Creates a RuleSet in the provided location, with the provided name.
     *
     * @param folderPath       The location for the RuleSet.
     * @param name             The name for the RuleSet.
     * @param renameOnConflict Whether or not to rename if a RuleSet of the specified name already exists in the specified Folder.
     * @return The new RuleSet.
     * @throws ModelException Thrown if rename is false, and a collision occurs.
     */
    public MutableRuleSet createRuleSet(String folderPath, String name, boolean renameOnConflict) throws ModelException;


    /**
     * Creates a RuleSet in the provided location, with the provided name.
     *
     * @param folder           The location for the RuleSet.
     * @param name             The name for the RuleSet.
     * @param renameOnConflict Whether or not to rename if a RuleSet of the specified name already exists in the specified Folder.
     * @return The new RuleSet.
     * @throws ModelException Thrown if rename is false, and a collision occurs.
     */
    public MutableRuleSet createRuleSet(MutableFolder folder, String name, boolean renameOnConflict) throws ModelException;


    /**
     * Sets the super Concept of a MutableConcept.
     *
     * @param child      The Concept of which to set the parent.
     * @param parentPath The path of the super-Concept.
     * @throws ModelException Thrown if setting the parent will cause an inheritance loop.
     */
    public void setSuperConcept(MutableConcept child, String parentPath) throws ModelException;


    /**
     * Deletes a PropertyDefinition.
     *
     * @param def The definition to delete.
     */
    public void deletePropertyDefinition(MutablePropertyDefinition def);


    /**
     * Deletes an instance of a Concept.
     *
     * @param instance The instance to delete.
     */
    public void deleteInstance(MutableInstance instance);


    /**
     * Deletes a PropertyInstance.
     *
     * @param propertyInstance The PropertyInstance to delete.
     */
    public void deletePropertyInstance(MutablePropertyInstance propertyInstance);


    /**
     * Deletes a PropertyInstance.
     *
     * @param name  The name of the property whose instance to delete.
     * @param index The index to delete.
     */
    public void deletePropertyInstance(MutableInstance instance, String name, int index);


    /**
     * Deletes all instances of a PropertyDefinition
     *
     * @param pd The PropertyDefinition of which to delete instances.
     */
    public void deletePropertyInstances(MutablePropertyDefinition pd);


    /**
     * Completely clears all Entities, Folders, Folders from the Ontology.
     */
    public void clear();


    /**
     * Creates an Event in the provided location, with the provided name.
     *
     * @param folder                 The location for the Event.
     * @param name                   The name for the Event.
     * @param specifiedTimeInSeconds The time at which this Event will occur.
     * @param intervalInSeconds      The time between occurrences of this Event.
     * @param schedule               The repetition schedule of the Event.
     * @param renameOnConflict       Whether or not to rename if an entity of the specified name already exists in the specified Folder.
     * @return The new Event.
     * @throws ModelException Thrown if rename is false, and a collision occurs.
     */
    public MutableEvent createTimeEvent(MutableFolder folder, String name, long specifiedTimeInSeconds, String intervalInSeconds, int schedule, boolean renameOnConflict) throws ModelException;


    /**
     * Creates an Event in the provided location, with the provided name.
     *
     * @param fullPath         The location for the Event.
     * @param name             The name for the Event.
     * @param schedule         The repetition schedule of the Event.
     * @param renameOnConflict Whether or not to rename if an Event of the specified name already exists in the specified Folder.
     * @return The new Event.
     * @throws ModelException Thrown if rename is false, and a collision occurs.
     */
    public MutableEvent createTimeEvent(String fullPath, String name, long specifiedTimeInSeconds, String intervalInSeconds, int schedule, boolean renameOnConflict) throws ModelException;


    /**
     * Creates an Event in the provided location, with the provided name.
     *
     * @param folder           The location for the Event.
     * @param name             The name for the Event.
     * @param ttl              The time to live for this Event.
     * @param ttlUnits
     * @param renameOnConflict Whether or not to rename if an entity of the specified name already exists in the specified Folder.
     * @return The new Event.
     * @throws ModelException Thrown if rename is false, and a collision occurs.
     */
    public MutableEvent createEvent(MutableFolder folder, String name, String ttl, int ttlUnits, boolean renameOnConflict) throws ModelException;


    /**
     * Creates an Event in the provided location, with the provided name.
     *
     * @param folderPath       The location for the Event.
     * @param name             The name for the Event.
     * @param ttl              The time to live for this Event.
     * @param ttlUnits
     * @param renameOnConflict Whether or not to rename if an Event of the specified name already exists in the specified Folder.
     * @return The new Event.
     * @throws ModelException Thrown if rename is false, and a collision occurs.
     */
    public MutableEvent createEvent(String folderPath, String name, String ttl, int ttlUnits, boolean renameOnConflict) throws ModelException;


    /**
     * Sets the Folder to which a Event belongs.
     *
     * @param event  The Event whose Folder to set.
     * @param folder The new location for the Event.
     */
    public void setEventFolder(MutableEvent event, MutableFolder folder) throws ModelException;


    /**
     * Creates a MutableChannel.
     *
     * @param folder           The Folder for the MutableChannel.
     * @param name             The name for the MutableChannel.
     * @param renameOnConflict Whether or not to rename the MutableChannel on a name conflict.
     * @return The new MutableChannel.
     */
    public MutableChannel createChannel(MutableFolder folder, String name, boolean renameOnConflict) throws ModelException;


    /**
     * Creates a JDBC MutableChannel.
     *
     * @param folderPath       The Folder path for the MutableChannel.
     * @param name             The name for the MutableChannel.
     * @param renameOnConflict Whether or not to rename the MutableChannel on a name conflict.
     * @return The new MutableChannel.
     */
    public MutableChannel createChannel(String folderPath, String name, boolean renameOnConflict) throws ModelException;


    /**
     * Creates a JDBC MutableChannel.
     *
     * @param folder
     * @param name
     * @param renameOnConflict
     * @return
     * @throws ModelException
     */
//    public MutableJDBCChannel createJDBCChannel(MutableFolder folder, String name, boolean renameOnConflict) throws ModelException;


    /**
     * Creates a JDBC MutableChannel.
     *
     * @param folderPath       The Folder path for the MutableChannel.
     * @param name             The name for the MutableChannel.
     * @param renameOnConflict Whether or not to rename the MutableChannel on a name conflict.
     * @return The new MutableChannel.
     */
//    public MutableJDBCChannel createJDBCChannel(String folderPath, String name, boolean renameOnConflict) throws ModelException;


    /**
     * Creates a calendar object
     *
     * @param folderPath  The folder path for the Calendar
     * @param name        The name for the Calendar
     * @param isRecurring Whether or not the calendar is recurring
     * @return Calendar
     * @throws ModelException
     */
    public MutableCalendar createCalendar(String folderPath, String name, boolean isRecurring) throws ModelException;


    /**
     * @param entityListener
     */
    public void addEntityChangeListener(EntityChangeListener entityListener);


    public void removeEntityChangeListener(EntityChangeListener entityListener);


    public MutableRuleFunction createRuleFunction(String folderPath, String name, boolean renameOnConflict) throws ModelException;


    public MutableRuleFunction createRuleFunction(MutableFolder folder, String name, boolean renameOnConflict) throws ModelException;


    

    /**
     * SS - ADDED : Ishaan and me should sit down and refactor the following
     * a.> designtime model
     * b.> sdk metadata
     * c.> provide MutableOntology and Ontology Interface
     * d.> API can be exposed ? (V2.0 - I think) if not atleast JSR94 compliant
     * e.> How much time will it take?? I thinke post 1.1 we should sit and refactor this also
     */

    public void addEntity(Entity e);


    public void removeEntity(Entity e);

    void addAlias(String s, Entity entity);

    void removeAlias(String s);

    void removeAlias(Entity e);


    MutableEntity createStandaloneRule(
            String folderPath,
            String name,
            boolean renameOnConflict)
            throws ModelException;


    MutableStandaloneRule createStandaloneRule(
                    MutableFolder folder,
                    String name,
                    boolean renameOnConflict)
                    throws ModelException;

}
