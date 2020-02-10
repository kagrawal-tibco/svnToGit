package com.tibco.cep.dashboard.psvr.mal.managers;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.dashboard.common.utils.SUID;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.mal.MALElementManagerFactory;
import com.tibco.cep.dashboard.psvr.mal.MALElementsCollector;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.store.PersistentStore;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;


/**
 * The {@link MALElementManager} provides an abstract means of working with
 * persistence of {@link MALElement}'s. The main job of {@link MALElementManager}
 * is to provide means to create/load/save {@link MALElement}. Every {@link MALElement}
 * has an equivalent {@link MALElementManager}
 *
 * @author anpatil
 * @see MALElement
 */
public abstract class MALElementManager {

    private static final String ID_PREFIX = "MAL:";

    protected static String indent = "";

    protected Logger logger;

    protected String definitionType;

    protected MALElementPostProcessor elementPostProcessor;

    protected MALElementManager(Logger logger) {
        this.logger = logger;
    }

    protected void setDefinitionType(String definitionType){
        if (StringUtil.isEmptyOrBlank(definitionType) == true){
            throw new IllegalArgumentException("definition type cannot be null");
        }
        this.definitionType = definitionType;
    }

    public void setElementPostProcessor(MALElementPostProcessor elementPostProcessor) {
		this.elementPostProcessor = elementPostProcessor;
	}

    /**
     * Creates an empty {@link MALElement} object given a name. A unique id is set in the
     * object, but this id is not persisted. When this object is persisted, the set
     * id will be updated to the real one. All the default properties are set
     * with appropriate values
     *
     * @param name The name to be set in the element
     * @return An instance of the element with all default properties set
     * @throws MALException if the creation/population of the element fails
     */
    public MALElement create(MALElement parent,String name) throws MALException {
        MALElement element = doCreateElement();
        setInternalDefaultProperties(element, ID_PREFIX+SUID.createId().toString(), name, BEViewsElementNames.isTopLevelElement(definitionType));
        if (parent != null && element.isTopLevelElement() == false) {
            element.setParent(parent);
        }
        //we will add the element itself as a property listener before setting
        //any default properties to get the element to be marked as dirty
        element.addPropertyChangeListener(element);
        try {
            setDefaultProperties(element);
        } catch (Exception e) {
            throw new MALException("could not set default properties for "+element);
        }
        //invoke the element post processor if set
        if (elementPostProcessor != null) {
        	elementPostProcessor.postCreateElement(element);
        }
        return element;
    }

    private void setInternalDefaultProperties(MALElement element,String id,String name, boolean isTopLevel){
        //set Id
        element.setId(id);
        //set name
        element.setName(name);
        //set definition type
        element.setDefinitionType(definitionType);
        //set top level element
        element.setTopLevelElement(isTopLevel);
    }

    /**
     * Updates the name of a non-persisted MALElement. Once a MALElement has been
     * persisted, its name cannot be changed. A persisted element is defined as
     * a element which has a persisted object attached to it. All the child MALElement's
     * are also updated
     *
     * @param element The element whose name is to be updated
     * @param oldNameSection The part of the name which is to be replaced
     * @param newNameSection The new section which replaces the old section
     * @throws MALException if the replacement fails or if the element is a persisted element
     * @see MALElement#getPersistedObject()
     */
    public void updateName(MALElement element, String oldNameSection, String newNameSection) throws MALException{
        if (logger.isEnabledFor(Level.DEBUG) == true){
            logger.log(Level.DEBUG, "Updating name in "+element+" by replacing "+oldNameSection+" with "+newNameSection);
        }
        if (element.getPersistedObject() != null){
            throw new MALException("cannot update name on "+element+" since it is a persisted element");
        }
        doUpdateName(element,oldNameSection,newNameSection);
    }

    /**
     * Creates a appropriate MALElement, given the persisted version. The MALElement
     * is created and populated. The persisted version is set on the MALElement as
     * the persisted object
     * @param pStore The PersistentStore which was used to load the persistedObject
     * @param persistedObject The persistedObject which is the persisted version
     * @param elementsCollector The MALElementsCollector which collects top level elements
     * @return a fully loaded MALElement
     * @throws MALException if the loading of the MALElement fails
     * @see PersistentStore
     * @see MALElement
     * @see BEViewsElement
     * @see MALElement#getPersistedObject()
     */
    public MALElement load(PersistentStore pStore, BEViewsElement persistedObject, MALElementsCollector elementsCollector) throws MALException {
        if (elementsCollector == null) {
            throw new IllegalArgumentException("Elements Collector cannot be null");
        }
        //provide a mean for persistent store to resolve the object fully
        BEViewsElement resolvedPersistedObject = (BEViewsElement) pStore.resolve(persistedObject);
        if (resolvedPersistedObject != null) {
            //logger.log(Level.INFO, "Resolved "+config.hashCode()+" to "+pElement.hashCode());
            persistedObject = resolvedPersistedObject;
        }

        //get the id
        String id = persistedObject.getGUID();
        if (StringUtil.isEmptyOrBlank(id) == true){
            //id is invalid
            //logger.log(Level.WARN, "A persisted version of "+definitionType+" does not have a id, generating a internal id...");
            id = ID_PREFIX+SUID.createId().toString();
        }
        MALElement element = elementsCollector.get(id);
        if (element != null) {
            return element;
        }
        try {
            /*if (logger.isEnabledFor(Level.DEBUG)) {
                logger.log(Level.DEBUG, indent + "<" + persistedObject.eClass().getName() + ">");
                indent += "\t";
            }*/
            //extract the name
            String name = persistedObject.getName();
            if (StringUtil.isEmptyOrBlank(name) == true){
                //name is invalid
                //logger.log(Level.WARN, "A persisted version of "+definitionType+" does not have a name, setting name to "+definitionType);
                name = definitionType;
            }
            boolean isTopLevelElement = BEViewsElementNames.isTopLevelElement(definitionType);
            //create the element
            MALElement result = doCreateElement();
            setInternalDefaultProperties(result,id, name, isTopLevelElement);
            //transfer folder
            //result.setFolder(persistedObject.getFolder());
            //transfer namespace
            //result.setNamespace(persistedObject.getNamespace());
            //transfer description
            result.setDescription(persistedObject.getDescription());
            //set the persistent version
            result.setPersistedObject(persistedObject);
            if (isTopLevelElement) {
                //set owner
                result.setOwner(pStore.getOwningStore(result).getIdentity());
            }
            elementsCollector.put(id, result);
            //delegate type specific loading to doLoad
            doLoad(pStore, result, persistedObject, elementsCollector);
            //invoke the element post processor if set
            if (elementPostProcessor != null) {
            	elementPostProcessor.postLoad(pStore, result, persistedObject, elementsCollector);
            }
            /*if (logger.isEnabledFor(Level.DEBUG)) {
                indent = indent.substring(0, Math.max(indent.length()-1,0));
                logger.log(Level.DEBUG, indent + "</" + persistedObject.eClass().getName() + ">");
            }*/
            //add itself as a property listener to make element as dirty when it changes
            result.addPropertyChangeListener(result);
            return result;
        } catch (MALException ex) {
            // we need to remove the element which we have added before loading it
            elementsCollector.remove(id);
            throw ex;
        } catch (Exception ex) {
            // we need to remove the element which we have added before loading it
            elementsCollector.remove(id);
            throw new MALException(ex);
        }
    }

    public void applyPersonalizations(PersistentStore pStore,MALElement element,MALElementsCollector elementsCollector) throws MALException{
        if (elementsCollector == null) {
            throw new IllegalArgumentException("Elements Collector cannot be null");
        }
        if (element.isDirty() == true){
            throw new MALException("cannot apply personalizations to a already dirty "+element);
        }
        if (elementsCollector.isPersonalized(element) == false){
//    		LoggingService.getRuntimeLogger().log(Level.INFO,"Personalizing "+element);
            elementsCollector.personalized(element);
            //remove itself as a property change listener
            element.removePropertyChangeListener(element);
            doApplyPersonalizations(pStore,element,elementsCollector);
            //invoke the element post processor if set
            if (elementPostProcessor != null) {
            	elementPostProcessor.postApplyPersonalizations(pStore, element, elementsCollector);
            }
            element.resetPropertyTracking();
            //add itself back as a property change listener
            element.addPropertyChangeListener(element);
        }
        else{
//    		LoggingService.getRuntimeLogger().log(Level.INFO,"Already personalized "+element);
        }
    }

    /**
     * Persists all the values in the MALElement. A persisted version is created
     * if necessary.
     * @param pStore The PersistentStore which is to be used to create the persisted version
     * @param element The MALElement to be saved
     * @param elementsCollector The MALElementsCollector which collects top level elements
     * @throws MALException if the saving of the element fails
     * @see PersistentStore
     * @see MALElement
     */
    public void save(PersistentStore pStore, MALElement element, MALElementsCollector elementsCollector) throws MALException {
        if (elementsCollector == null) {
            throw new IllegalArgumentException("Elements Collector cannot be null");
        }
        if (element.isDirty() == false){
            return;
        }
        BEViewsElement config = (BEViewsElement) element.getPersistedObject();
        if (config == null){
            //check if we are dealing with a top level element
            if (element.isTopLevelElement()) {
                //yes we are, then we create a new element using the persistent store
                if (definitionType.equals(element.getDefinitionType()) == false){
                    throw new MALException(element+" cannot be saved using "+getClass().getName());
                }
                config = (BEViewsElement) pStore.createElement(definitionType);
            }
            else {
                //we throw an exception
                throw new MALException(element+" does not have a persistence object attached");
            }
        }
        //transfer the persisted version's id to the element
        String guid = config.getGUID();
        if (element.getId().startsWith(ID_PREFIX) == true) {
            element.setId(guid);
        }
        //transfer name
        config.setName(element.getName());
        //transfer folder
        //config.setFolder(element.getFolder());
        //transfer namespace
        //config.setNamespace(element.getNamespace());
        //transfer description
        config.setDescription(element.getDescription());
        //set the persisted version value
        element.setPersistedObject(config);
        if (element.isTopLevelElement() == true){
            element.setOwner(pStore.getIdentity());
        }
        MALElement saved = elementsCollector.get(guid);
        if (element != saved) {
            elementsCollector.put(guid, element);
            logger.log(Level.DEBUG, "Requesting saving [name=" + config.getName() + ",type=" + config.eClass().getName() + "]");
            doSave(pStore, element, config, elementsCollector);
            //invoke the element post processor if set
            if (elementPostProcessor != null) {
            	elementPostProcessor.postSave(pStore, element, config, elementsCollector);
            }
        }
        //element.resetPropertyTracking();
    }

    public void reset(MALElement element, BEViewsElement persistedObject, MALElementsCollector elementsCollector) throws MALException {
        if (element.getPersistedObject().getClass().equals(persistedObject.getClass()) == false){
            throw new IllegalArgumentException("Invalid persisted object["+persistedObject.getClass().getName()+"] specified for "+element);
        }
        if (elementsCollector.get(element.getId()) == null) {
            element.setPersistedObject(persistedObject);
            element.resetPropertyTracking();
            elementsCollector.put(element.getId(), element);
            doReset(element,persistedObject, elementsCollector);
        }
    }

    /**
     * Creates a clone of the MALElement including the complete hierarchy of child
     * MALElement. The new clone is a exact replica of the original in every way,
     * including state of MDElement references.
     * @param originalElement The element which is to be cloned
     * @param elementsCollector The MALElementsCollector which collects top level elements
     * @return a clone of the 'originalElement'
     * @throws MALException if the cloning of the element fails
     */
    public MALElement clone(MALElement originalElement, MALElementsCollector elementsCollector) throws MALException{
        return doCopy(originalElement, elementsCollector,false);
    }

    /**
     * Creates a copy of the MALElement including the complete hierarchy of child
     * MALElement. The new copy is a exact replica of the original in every way,
     * except it is ready for save. It basically creates a duplicate
     * @param originalElement The element which is to be copied
     * @param elementsCollector The MALElementsCollector which collects top level elements
     * @return a copy of the 'originalElement' which can be saved
     * @throws MALException if the coping of the element fails
     */
    public MALElement copy(MALElement originalElement, MALElementsCollector elementsCollector) throws MALException{
        return doCopy(originalElement, elementsCollector,true);
    }

    /**
     * Creates a copy of the MALElement. If resetRefsToMDElements is <code>true</code>,
     * then all internal references to MDElements are reset and the element is given a
     * pseudo-id else the id of the originalElement's MDConfig is given to the new copy
     * @param originalElement The element to copied
     * @param elementsCollector The MALElementsCollector which collects top level elements
     * @param resetRefsToMDElements Resets the MALElement for new state if <code>true</code>
     * @return a copy of the 'originalElement'
     * @throws MALException if the copy of the MALElement fails
     */
    protected MALElement doCopy(MALElement originalElement, MALElementsCollector elementsCollector, boolean resetRefsToMDElements) throws MALException {
        if (elementsCollector == null){
            throw new IllegalArgumentException("Element Collector cache cannot be null");
        }
        if (originalElement == null){
            throw new IllegalArgumentException("Original element cannot be null");
        }
        MALElement copiedElement = elementsCollector.get(originalElement.getId());
        if (copiedElement != null){
            return copiedElement;
        }
        copiedElement = doCreateElement();
        String id = originalElement.getId();
        String name = originalElement.getName();
        Object persistedCopy = originalElement.getPersistedObject();
        if (resetRefsToMDElements == true){
            id = ID_PREFIX+SUID.createId().toString();
            persistedCopy = null;
        }
        setInternalDefaultProperties(copiedElement, id, name, BEViewsElementNames.isTopLevelElement(definitionType));
        copiedElement.setPersistedObject(persistedCopy);
        copiedElement.setTopLevelElement(originalElement.isTopLevelElement());
        copiedElement.setAutoGenerated(originalElement.isAutoGenerated());
        if (originalElement.isTopLevelElement() == true){
            copiedElement.setOwner(originalElement.getOwner());

            Collection<MALElement> references = originalElement.getReferences();
            for (MALElement reference : references) {
                copiedElement.addReference(reference);
            }
        }
        else {
            copiedElement.setParent(originalElement.getParent());
        }
        copiedElement.addPropertyChangeListener(copiedElement);
        elementsCollector.put(originalElement.getId(),copiedElement);
        copyProperties(originalElement,copiedElement,elementsCollector,resetRefsToMDElements);
        //invoke the element post processor if set
        if (elementPostProcessor != null) {
        	elementPostProcessor.postCopy(originalElement, copiedElement, elementsCollector, resetRefsToMDElements);
        }
        if (resetRefsToMDElements == false){
            //when we are copying we want the property tracking to be same of original
            //but when we are cloning we want the flag to be dirty always
            originalElement.copyPropertyTrackingInfo(copiedElement);
        }
        return copiedElement;
    }

    /**
     * Creates a blank MALElement
     * @return An instance of the appropriate MALElement
     */
    protected abstract MALElement doCreateElement();

    /**
     * Sets the values for all default properties in a MALElement
     * @param element The MALElement whose properties need to be set
     * @return An instance of MALElement
     * @throws MALException if the setting of default properties fails
     */
    protected abstract MALElement setDefaultProperties(MALElement element) throws MALException;

    /**
     * Updates the name, replacing one section with another
     * @param element The element whose name needs to be changed
     * @param oldNameSection The section which is to be searched/replaced
     * @param newNameSection The new replacement section
     * @throws MALException if the replacement of the section in the name fails
     */
    protected abstract void doUpdateName(MALElement element, String oldNameSection, String newNameSection) throws MALException;

    /**
     * Loads/parses the BEViewsElement element
     * @param pStore The PersistentStore which was used to load the persistedObject
     * @param element The MALElement into which the parsed values should be set
     * @param persistedObject The BEViewsElement which represents the persisted version
     * @param elementsCollector The MALElementsCollector which collects top level elements
     * @throws MALException if the parsing of the MDConfig and population of the MALElement fails
     */
    protected abstract void doLoad(PersistentStore pStore, MALElement element, BEViewsElement persistedObject, MALElementsCollector elementsCollector) throws MALException;


    protected abstract void doApplyPersonalizations(PersistentStore pStore, MALElement element, MALElementsCollector elementsCollector) throws MALException;

    /**
     * Transfers property values from MALElement to BEViewsElement.
     * @param pStore The PersistentStore which is to be used to create persisted objects if needed
     * @param element The element from which to transfer all property values
     * @param persistedObject The persisted object in which to transfer all the values
     * @param elementsCollector The MALElementsCollector which collects top level elements
     * @throws MALException if the saving of the element fails
     */
    protected abstract void doSave(PersistentStore pStore, MALElement element, BEViewsElement persistedObject, MALElementsCollector elementsCollector) throws MALException;

    protected abstract void doReset(MALElement element,BEViewsElement persistedObject, MALElementsCollector elementsCollector) throws MALException;

    /**
     * Copies the property values from one element to another
     * @param originalElement The MALElement from which to copy the property values
     * @param copiedElement The MALElement in which to set the property values
     * @param elementsCollector The MALElementsCollector which collects top level elements
     * @param resetPrimaryProps Indicates whether to reset MDElement references
     * @return The MALElement in which property values are set
     * @throws MALException if the copying of property values fails
     */
    protected abstract MALElement copyProperties(MALElement originalElement, MALElement copiedElement, MALElementsCollector elementsCollector, boolean resetPrimaryProps) throws MALException;

    protected String getDefaultString(EClass eClass, String propertyName) {
        EStructuralFeature feature = eClass.getEStructuralFeature(propertyName);
        Object defaultValue = feature.getDefaultValue();
        if (defaultValue == null){
            return null;
        }
        return defaultValue.toString();
    }

    protected int getDefaultInt(EClass eClass, String propertyName) {
        EStructuralFeature feature = eClass.getEStructuralFeature(propertyName);
        return (Integer)feature.getDefaultValue();
    }

    protected boolean getDefaultBoolean(EClass eClass, String propertyName) {
        EStructuralFeature feature = eClass.getEStructuralFeature(propertyName);
        return (Boolean)feature.getDefaultValue();
    }

    protected double getDefaultDouble(EClass eClass, String propertyName) {
        EStructuralFeature feature = eClass.getEStructuralFeature(propertyName);
        return (Double)feature.getDefaultValue();
    }

    protected String getString(BEViewsElement element, String propertyName) {
        EStructuralFeature eSFeature = element.eClass().getEStructuralFeature(propertyName);
        return (String)element.eGet(eSFeature);
    }

    protected int getInt(BEViewsElement element, String propertyName) {
        EStructuralFeature eSFeature = element.eClass().getEStructuralFeature(propertyName);
        return (Integer)element.eGet(eSFeature);
    }

    protected BEViewsElement[] getStructureValueArray(BEViewsElement parent, String propertyName) {
        EStructuralFeature eSFeature = parent.eClass().getEStructuralFeature(propertyName);
        EList<?> list = (EList<?>) parent.eGet(eSFeature);
        BEViewsElement[] elements = new BEViewsElement[list.size()];
        int i = 0;
        for (Iterator<?> itElements = list.iterator(); itElements.hasNext();i++) {
            BEViewsElement element = (BEViewsElement) itElements.next();
            elements[i] = element;
        }
        return elements;
    }

//    protected BEViewsElement getStructureValue(BEViewsElement parent, String propertyName) {
//    	EStructuralFeature eSFeature = parent.eClass().getEStructuralFeature(propertyName);
//    	return (BEViewsElement)parent.eGet(eSFeature);
//    }

    protected MALElement loadStructure(PersistentStore pStore, BEViewsElement cf, MALElementsCollector elementsCollector) throws MALException {
        String type = cf.eClass().getName();
        MALElementManager manager = MALElementManagerFactory.getInstance().getManager(type);
        if (manager == null){
            throw new IllegalArgumentException(cf.getName()+" of type["+type+"] does not have a manager");
        }
        return manager.load(pStore, cf, elementsCollector);
    }

    protected void saveStructure(PersistentStore pStore, MALElement element, MALElementsCollector elementsCollector) throws MALException {
        String type = element.getDefinitionType();
        if (StringUtil.isEmptyOrBlank(type) == true){
            throw new IllegalArgumentException(element+" does not have a definition type");
        }
        MALElementManager manager = MALElementManagerFactory.getInstance().getManager(type);
        if (manager == null){
            throw new IllegalArgumentException(element+" does not have a manager");
        }
        manager.save(pStore, element, elementsCollector);
    }

    protected Object createStructure(PersistentStore pStore, MALElement element, String configName) throws MALException {
        //String configTypeName = element.getClass().getName();
        // Remove the package prefix + "MAL"
        //configTypeName = configTypeName.substring(configTypeName.lastIndexOf(".") + 4);
        Object childObject = pStore.createElement(element.getDefinitionType());
        if ((childObject instanceof BEViewsElement)) {
            BEViewsElement beViewsElement = (BEViewsElement) childObject;
            beViewsElement.setGUID(GUIDGenerator.getGUID());
            beViewsElement.setName(configName);
            element.setPersistedObject(beViewsElement);
            element.setId(beViewsElement.getGUID());
        }
        return childObject;
    }
}
