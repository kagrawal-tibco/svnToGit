package com.tibco.cep.studio.dashboard.core.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationErrorMessage;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationInfoMessage;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationMessage;
import com.tibco.cep.studio.dashboard.core.listeners.IMessageProvider;
import com.tibco.cep.studio.dashboard.core.listeners.ISynElementChangeListener;
import com.tibco.cep.studio.dashboard.core.listeners.SynElementMessageProvider;
import com.tibco.cep.studio.dashboard.core.model.ISynInternalStatusProvider;
import com.tibco.cep.studio.dashboard.core.model.ISynPropertyEnumProvider;
import com.tibco.cep.studio.dashboard.core.model.ISynValidationProvider;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynOptionalProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynRequiredProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.providers.SynPropertyInstanceProvider;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeDeclaration;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynDescriptionType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynIntType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynStringType;
import com.tibco.cep.studio.dashboard.core.notification.Adapter;
import com.tibco.cep.studio.dashboard.core.notification.Notification;
import com.tibco.cep.studio.dashboard.core.notification.Notifier;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;

/**
 * @
 *
 */
public abstract class LocalElement extends SynPropertyInstanceProvider implements ISynInternalStatusProvider,
        ISynValidationProvider, ISynElementChangeListener, IMessageProvider, ISynPropertyEnumProvider, Comparable<LocalElement>, Notifier, Adapter {


	private static final Logger LOGGER = Logger.getLogger(LocalElement.class.getName());

	public static final String PROP_KEY_PREFIX = "";

	public static final String PROP_KEY_GUID = PROP_KEY_PREFIX + "GUID";

	public static final String PROP_KEY_SCOPE_NAME = PROP_KEY_PREFIX + "ScopeName";

	public static final String PROP_KEY_NAME = PROP_KEY_PREFIX + "Name";

	public static final String PROP_KEY_FOLDER = PROP_KEY_PREFIX + "Folder";

	public static final String PROP_KEY_DESCRIPTION = PROP_KEY_PREFIX + "Description";

	public static final String PROP_KEY_SORTING_ORDER = PROP_KEY_PREFIX + "SortOrder";

	public static final String FOLDER_NOT_APPLICABLE = "";

	/**
     * The internal status is default to new. Subclasses should set the internal
     * status appropriately upon instantiation of this class
     */
    private InternalStatusEnum internalStatus = InternalStatusEnum.StatusNew;

    private boolean isSystemStatus = false;

    private SynElementMessageProvider elementMessageProvider;

    private SynValidationMessage validationMessage = null;

    protected String elementLocatorKey = "";

    protected LocalParticle parentParticle;

    private List<LocalParticle> referenceParentParticles = new ArrayList<LocalParticle>();

    protected LocalElement parent;

    /**
     * Tracks <code>LocalParticle</code>s that represent containment
     * relationships. Maps particle name (String) to <code>LocalParticle</code>.
     */
    private Map<String, LocalParticle> childrenParticleMap = new HashMap<String, LocalParticle>();
    /**
     * Tracks <code>LocalParticle</code>s that represent non-containment
     * relationships. Maps particle name (String) to <code>LocalParticle</code>.
     */
    private Map<String, LocalParticle> referenceParticleMap = new HashMap<String, LocalParticle>();

    private boolean childrenLoaded = false;

    private String defaultChildType = null;

    protected boolean bulkOperation = false;

    /*
     * Because sorting orders are used so frequently it has been made concrete
     * for performance
     */
    private int sortingOrder = 0;

    private String fullPath;

    protected enum LabelMode {YES, NO, DEPENDS_ON_PARENT};

    protected LabelMode labelMode;

    protected EObject persistedElement;

    public LocalElement() {
        this(true);
    }

    protected LocalElement(boolean setup) {
        try {
            if (setup) {
            	this.setupDefaultProperties();
            	this.setupProperties();
            }
        } catch (Exception e) {
        	getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public LocalElement(LocalElement parentElement) {
        try {
        	this.setParent(parentElement);
        	this.setupDefaultProperties();
        	this.setupProperties();
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public LocalElement(LocalElement parentElement, EObject eObject) {
        try {
            this.setElementLocatorKey(eObject);
            this.setParent(parentElement);
            this.setupDefaultProperties();
            this.setupProperties();
            this.persistedElement = eObject;
            this.refresh();
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public LocalElement(LocalParticle parentParticle) {
        try {
        	this.setParentParticle(parentParticle);
        	this.setupDefaultProperties();
        	this.setupProperties();
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public LocalElement(LocalElement parentElement, String name) {
        try {
            this.setParent(parentElement);
            this.setupDefaultProperties();
            this.setupProperties();
            this.setName(name);
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    //===================================================================
    // Initialization
    //===================================================================

    /**
     * Sets up the default properties
     *
     * @throws Exception
     *
     */
    protected void setupDefaultProperties() {
        /*
         * Elements are initially given a random ID for referential use;
         * elements that are synchronized with a real instance of EObject will
         * override this property with the appropriate ID from the EObject
         */
        addProperty(this, new SynRequiredProperty(PROP_KEY_GUID, new SynStringType(), GUIDGenerator.getGUID(), true));
        addProperty(this, new SynRequiredProperty(PROP_KEY_NAME, new SynStringType()));
        addProperty(this, new SynOptionalProperty(PROP_KEY_FOLDER, new SynStringType(), "", true));
        addProperty(this, new SynOptionalProperty(PROP_KEY_DESCRIPTION, new SynDescriptionType()));
        addProperty(this, new SynRequiredProperty(PROP_KEY_SORTING_ORDER, new SynIntType(), "0", true));

    }

    /**
     * Sets the properties that are specific to the subclass
     *
     * @throws Exception
     *
     */
    public abstract void setupProperties();

    public void setPropertyValue(String key, String value) {
        // TODO dchesney Jun 3, 2006 Trouble here. Getting the value before it
        // has been set once causes stack overflow.
//      Object oldValue = getPropertyValue(key);
        super.setPropertyValue(key, value);
        //notify(Notification.SET, key, null, getPropertyValue(key));
    }

    /**
     * In a hierarchy properties at the super type levels tends to be called
     * upon more frequently than those at the subtype level.
     *
     * This method is a convenience for subtypes to call from an overridden
     * implementation of parseMDProperty(...) so that a quick delegation can be
     * employed as opposed to iterating through all the local properties of the
     * subtypes first and then defaulting to the su[per type.
     *
     * This method is only needed if this instance can have subclasses
     *
     * @param propertyName
     * @return
     */
    protected boolean isSuperProperty(String propertyName) {
        if (true == PROP_KEY_GUID.equals(propertyName)) {
            return true;
        }
        if (true == PROP_KEY_NAME.equals(propertyName)) {
            return true;
        }
        if (true == PROP_KEY_DESCRIPTION.equals(propertyName)) {
            return true;
        }
        if (true == PROP_KEY_FOLDER.equals(propertyName)) {
            return true;
        }
        if (true == PROP_KEY_SORTING_ORDER.equals(propertyName)) {
            return true;
        }
        return false;
    }

    /**
     * Parses the initial properties applicable for every LocalElement
     *
     * Every subclass of LocalElement that has an overriding implementation of
     * this method should make sure to delegate to this method first... unless there is a real need to
     * override the way these properties are handled.
     *
     * @param propertyName
     * @throws Exception
     */
    public void parseMDProperty(String propertyName) {
    	EObject eObject = getEObject();
    	if (eObject == null) {
    		return;
    	}
        if (true == PROP_KEY_GUID.equals(propertyName)) {
            setID(getEObjectId(eObject));
        }
        if (true == PROP_KEY_FOLDER.equals(propertyName)) {
            setFolder(getEObjectFolder(eObject));
        }
        else if (true == PROP_KEY_NAME.equals(propertyName)) {
            setName(getEObjectName(eObject));
        }
        else if (true == PROP_KEY_DESCRIPTION.equals(propertyName)) {
            setDescription(getEObjectDescription(eObject));
        }
        else if (true == PROP_KEY_SORTING_ORDER.equals(propertyName)) {
            //do nothing
        }
        else {
        	throw new IllegalArgumentException("[ " + propertyName + " ] is not a recognized name for a property in this element");
        }
    }

    /**
     * Parses a property that has been set already without marking the property as modified.
     * This is useful for refreshing a property without having to force a save of the element
     *
     * @param propertyName
     * @param resetAlreadySetFlag
     * @throws Exception
     */
    public void parseMDProperty(String propertyName, boolean resetAlreadySetFlag) {
        if(false == resetAlreadySetFlag) {
            parseMDProperty(propertyName);
        }
        else {
            SynProperty prop = (SynProperty)getProperty(propertyName);
            if(true == prop.isAlreadySet()) {
                prop.setAlreadySet(false);
                parseMDProperty(propertyName);
            }
        }
    }

    /**
     * Synchronizes both the element and its children
     *
     * @throws Exception
     */
    public void update() {
        EObject eObject = getEObject();
        if (null == eObject) {
            throw new IllegalStateException("No EObject found in "+toString());
        }
        update(eObject);
    }

    /**
     * Side effect: When parseFully is false, the logic will set particle to
     * initialize.
     *
     * @param eObject
     */
    protected void update(EObject eObject) {
        if (null != eObject) {
            setElementLocatorKey(eObject);
            //reset the guid
            parseMDProperty(PROP_KEY_GUID, true);
            // It is called at the end of synchronize.
            // New local element has mdElement. ToBePurged elements have
            // been removed.
            List<LocalParticle> list = this.getParticles(false);
            for (int i = 0; i < list.size(); i++) {
                LocalParticle localParticle = list.get(i);
                localParticle.recreateElements();
                List<LocalElement> elements = localParticle.getElements();
                for (int ij = 0; ij < elements.size(); ij++) {
                    LocalElement localElement = elements.get(ij);
                    /*
                     * Only forward the processing to elements that have
                     * an MDElement and ignore the rest
                     */
                    if (localElement.getEObject() != null) {
                        localElement.update();
                    }
                }
            }
            /*
             * Also set the status to existing
             */
            if (!InternalStatusEnum.StatusRemove.equals(getInternalStatus())) {
                setExisting();
            }
        }
    }

    /*
     * Refreshes all properties and all particles
     */
    public void refresh() {
    	this.refresh(this.getEObject());
    }

    /*
     * Refreshes all properties and all particles
     *
     * As mdElement is avaliable in this method,
     * use it directly, and don't call parseMDProperty.
     */
    public void refresh(EObject eObject) {
    	if (eObject.getClass().equals(getEObject().getClass()) == false){
    		throw new IllegalArgumentException(eObject.getClass().getName()+" is not valid for "+this.getClass().getName());
    	}
        // turn off both notifications as it is refresh and not any user changes.
        // Khai had the first notification mechanism in subscribe.
        // Doug Chesley had the second notification mechansim in a way similar to EMF.
        // Ernie extended Doug's way to support Architect refresh and any future notification.
        try {
            this.isDeliver = false;
            this.bulkOperation = true;
	        /*
	         * Reset all the properties in case they have been set already The
	         * properties will be lazily re-parsed when they are called upon
	         */
	        this.setPropertiesAlreadySet(false);
	        this.removeAllChildren();
	        if ( eObject != null ) {
	        	//Added by Anand 6/17/2010 to support editor refresh
	        	if (getEObject() != eObject){
	        		persistedElement = eObject;
	        	}
		        /*
		         * These are the basic properties that all elements can have and
		         * is aligned with the initial properties returned by MDS for
		         * each element.
		         *
		         * At the minimum these properties are required to be parsed so
		         * that the framework can properly handle them; all other
		         * properties can be lazily parsed.
		         */
		        // Now, re-fetch information from mdElement if mdElement is not null.
		        this.setElementLocatorKey(eObject);
		        SynProperty property = (SynProperty) this.getProperty(PROP_KEY_GUID);
		        property.setValue(getEObjectId(eObject));
		        property.setAlreadySet(true);
		        // After refresh, all properties and children should have status existing.
		        setInternalStatus(InternalStatusEnum.StatusExisting,false);
	        }
        }
	    finally {
	        this.bulkOperation = false;
	        this.isDeliver = true;
	    }
    }

    protected void refreshExtra(EObject mdElement) {
        try {
        	// turn off Release 3.3 notification
            this.isDeliver = false;
	        this.getAllChildren(false);
	        this.getAllReferences(false);
        }
        finally {
            this.isDeliver = true;
        }
    	// turn on Release 3.3 notification
    }

    /*
     * Refreshes only a particular particle
     */
    public void refresh(String particleName) {
        LocalParticle particle = getParticle(particleName);
        if (particle == null){
        	return;
        }
		particle.removeAll();
    }

    public abstract EObject createMDChild(LocalElement localElement);

    public abstract void deleteMDChild(LocalElement localElement);

    public abstract Object cloneThis() throws Exception;

    /**
     * A convenience method for creating a new default child LocalElement with
     * default values
     *
     * @return
     * @throws Exception
     */
    public LocalElement createLocalElement() {
        if (null == getDefaultChildType() || getDefaultChildType().length() < 1) {
            throw new IllegalStateException(
                    "createLocalElement() is a convenience method that requires getDefaultChildType() to return a non-null String representing the type of the child to create.");
        }
        return createLocalElement(getDefaultChildType());
    }

    /**
     * Creates a new child LocalElement of the given elementType with default
     * values
     *
     * @param elementType
     *
     * @return
     * @throws Exception
     */
    public abstract LocalElement createLocalElement(String elementType);

    /**
     * Initialize the children
     *
     * @param propertyName
     */
    public abstract void loadChildren(String childrenType);

    public abstract void loadChild(String childrenType, String childName);

    public abstract void loadChildByID(String childrenType, String childID);

    //===================================================================
    // Validation
    //===================================================================
    public boolean isValid(Object value) {
        return true;
    }


    public boolean isValid() throws Exception{

    	// If the element is marked to be removed, it must have existed in MDS, and hence must have
    	// been valid.
    	if (isRemoved())
    		return true;

        setValidationMessage(null);

        /*
         * validate all properties
         */

        for (Iterator<ISynXSDAttributeDeclaration> iter = getProperties().iterator(); iter.hasNext();) {
            SynProperty prop = (SynProperty) iter.next();
            validateProperty(prop);

        }

        /*
         * validate all elements in the particle
         */

        for (Iterator<LocalParticle> iter = getParticles(true).iterator(); iter.hasNext();) {
            LocalParticle particle = iter.next();
            validateParticle(particle);

        }

        return (null == getValidationMessage());
    }

	protected void validateParticle(LocalParticle particle) throws Exception {
		if (false == isValid(particle)) {
		    addValidationMessage(particle.getValidationMessage());
		}
		if (particle.isReference()){
		    //addValidationMessage(particle.getValidationMessage());
			validateReferences(particle);
		}
	}

	protected void validateProperty(SynProperty prop) throws Exception {
		if (false == isValid(prop)) {
		    addValidationMessage(prop.getValidationMessage());
		}
	}

	protected boolean isValid(SynProperty prop) throws Exception {
		return prop.isValid();
	}

	protected boolean isValid(LocalParticle particle) throws Exception {
		return particle.isValid();
	}

	protected boolean validateReferences(LocalParticle particle) throws Exception {
		return true;
	}

    public boolean isNameUnique(String particleName, String name) {
        try {
            // FIXME: The logic below allows the name to already exist - once.
            // Only for the second or subsequent occurrences, it is returning 'not true'.
            boolean nameFound = false;
            for (Iterator<LocalElement> iter = getChildren(particleName).iterator(); iter.hasNext();) {
                LocalElement element = iter.next();
                if (element.getName().equals(name)) {
                    if (nameFound) {
                        return false;
                    }
                    nameFound = true;
                }
            }
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, e.getMessage(), e);
            //Do nothing for now
        }
        return true;
    }

    public boolean isNameBeingUsed(String particleName, String name) {
        for (Iterator<LocalElement> iter = getChildren(particleName).iterator(); iter.hasNext();) {
            LocalElement element = iter.next();
            if (element.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String getNewName(String particleName) {
        return getNewName(particleName, generateSeed(particleName));
    }

    private String generateSeed(String particleName){
        StringBuilder name = new StringBuilder();
        char[] charArray = particleName.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (Character.isUpperCase(c) == true){
				name.append(c);
			}
		}
        return name.toString();
    }

    public String getNewName(String particleName, String nameSeed) {
        if (null == particleName || particleName.length() < 1) {
            return "";
        }

        if (null == nameSeed || nameSeed.length() < 1) {
            return "";
        }

        int counter = 1;
        String newName = "";

        if (nameSeed.endsWith("Field") == true) {
        	// Treat field names differently, name them starting at '_1'
            newName = nameSeed + "_" + counter;
        }
        else {
            if (nameSeed.endsWith("_") || particleName.endsWith("DerivationSpec")) {
            	// Prevent double "__" if one used default name
            	// Prevent ending DerivationSpec names with "_".
                newName = nameSeed;
            }
            else if (isNameBeingUsed(particleName, nameSeed)) {
            	// Clone, copy operation. Start with existing name.
                newName = nameSeed;
            }
            else {
            	// All other element names, suggest a '_' as separator.
                newName = nameSeed + "_";
            }
        }

        while (isNameBeingUsed(particleName, newName)) {
            counter++;
            /*
             * Again, check to prevent double '_'
             */
            newName = nameSeed + (nameSeed.endsWith("_") ? "" : "_") + counter;
        }

        return newName;
    }

    public boolean hasError() {
        if ( validationMessage == null ) {
            return false;
        }
        return validationMessage.hasError();
    }

    public SynValidationMessage getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(SynValidationMessage validationMessage) {
        this.validationMessage = validationMessage;
    }

    public void addValidationInfoMessage(String message)  {
        if (null == getValidationMessage()) {
            setValidationMessage(new SynValidationInfoMessage(message));
        }
        else {
            addValidationMessage(new SynValidationInfoMessage(message));
        }
    }

    /**
     * If a message already exist add this SynValidationMessage as a sub message
     * to it otherwise set this SynValidationMessage as the message itself
     *
     * @param validationMessage
     */
    public void addValidationErrorMessage(String message) {
        if (null == getValidationMessage()) {
            setValidationMessage(new SynValidationErrorMessage(message));
        }
        else {
            addValidationMessage(new SynValidationErrorMessage(message));
        }
    }

    /**
     * If a message already exist add this SynValidationMessage as a sub message
     * to it otherwise set this SynValidationMessage as the message itself
     *
     * @param validationMessage
     */
    public void addValidationMessage(SynValidationMessage validationMessage) {
        if (null == getValidationMessage()) {
            setValidationMessage(validationMessage);
        }
        else {
            getValidationMessage().addSubMessage(validationMessage);
        }
    }

    public boolean isReference() {
        if (null != getParentParticle()) {
		    return getParentParticle().isReference();
		}
        return false;
    }

    public String getDefaultChildType() {
        return defaultChildType;
    }

    protected void setDefaultChildType(String childType) {
        defaultChildType = childType;
    }

    public boolean isChildrenLoaded() {
        return childrenLoaded;
    }

    public boolean isParticleLoaded(String particleName) {
        LocalParticle particle = childrenParticleMap.get(particleName);
        if (null != particle) {
            return particle.isInitialized();
        }
        return false;
    }

    public void setChildrenLoaded(boolean childrenLoaded) {
        this.childrenLoaded = childrenLoaded;
    }

    public LocalElement getParent() {
        // If I have a biological parent, use it.
        if (null != parent) {
            return parent;
        }
        if (null != getParentParticle()) {
            return getParentParticle().getParent();
        }
        return null;
    }

    public LocalElement getRoot() {
    	LocalElement root = getParent();
    	if (root == null) {
    		return this;
    	}
    	return root.getRoot();
    }

    public void setParent(LocalElement parentElement) {
        this.parent = parentElement;
    }

    public abstract String getElementType();

    /*public void setElementType(String elementType) {
    	this.elementType = elementType;
    }*/

    public List<String> getAllElementTypes() {
    	//Using a hashset here to prevent duplicates
    	//e.g. seriesconfig is contained in a particle named seriesconfig
        Set<String> elementTypes = new HashSet<String>();
        elementTypes.add(getElementType());

        try {
        	//PATCH not sure why we need to add parent particle
            if (null != getParentParticle()) {
                elementTypes.add(getParentParticle().getName());
            }
            /*
             * Also add all the reference parent particles
             */
            for (Iterator<LocalParticle> iter = getReferenceParentParticles().iterator(); iter.hasNext();) {
            	//PATCH I think we need to use getTypeName() instead of getName()
                elementTypes.add(iter.next().getName());
            }

        } catch (Exception e) {
            getLogger().log(Level.SEVERE, e.getMessage(), e);
        }

        return new ArrayList<String>(elementTypes);
    }

    /**
     * Returns only the <code>LocalParticle</code>s that represent
     * containment relationships. Convenience method equivalent to
     * {@link #getParticles(boolean)} passing <code>false</code>.
     *
     * @return <code>List</code> of <code>LocalParticle</code>
     */
    public List<LocalParticle> getParticles() {
        return getParticles(false);
    }

    /**
     * Returns all containment relationships, and optionally, all
     * non-containment relationships.
     *
     * @param includeReferenceParticles <code>true</code> if non-containment
     * <code>LocalParticle</code>s should be included in the result
     * @return <code>List</code> of <code>LocalParticle</code>
     */
    public List<LocalParticle> getParticles(boolean includeReferenceParticles) {
        List<LocalParticle> allParticles = new ArrayList<LocalParticle>();
        allParticles = new ArrayList<LocalParticle>(childrenParticleMap.values());
        if (true == includeReferenceParticles) {
            allParticles.addAll(getReferenceParticles());
        }
        return allParticles;
    }

    /**
     * Returns only the particles that are references (non-containment
     * relationships).
     *
     * @return <code>List</code> of <code>LocalParticle</code>
     */
    public List<LocalParticle> getReferenceParticles() {
        return new ArrayList<LocalParticle>(referenceParticleMap.values());
    }

    /**
     * Returns the names of all containment relationships.
     *
     * @return <code>List</code> of <code>String</code>
     */
    public List<String> getParticleNames() {
        return getParticleNames(false);
    }

    /**
     * Returns a listing of the names of all containment references, and
     * optionally, the names of all non-containment references.
     *
     * @param includeReferenceParticles <code>true</code> to return the names
     * of all non-containment references
     * @return <code>List</code> of <code>String</code>
     */
    public List<String> getParticleNames(boolean includeReferenceParticles) {
        List<String> particleNames = new ArrayList<String>();
        for (Iterator<LocalParticle> iter = getParticles(includeReferenceParticles).iterator(); iter.hasNext();) {
            LocalParticle particle = iter.next();
            particleNames.add(particle.getName());
        }
        return particleNames;
    }

    /**
     * Returns the particle whose name is given by <code></code>particleName</code>.
     *
     * @param particleName the name of the particle to find
     * @return the <code>LocalParticle</code> or <code>null</code> if no
     * particle exists with the given name
     * @throws Exception
     */
    public LocalParticle getParticle(String particleName) {
        LocalParticle particle = childrenParticleMap.get(particleName);
        if (null != particle) {
            return particle;
        }
        particle = referenceParticleMap.get(particleName);
        return particle;
    }

    /**
     * Add a particle to the element with the particle set as the default
     * children particle
     *
     * @param localParticle
     */
    public void addParticle(LocalParticle localParticle) {
        addParticle(localParticle, false);

    }

    /**
     * Add a particle to the element with the flag indicating whether this
     * partile is for children or for references
     *
     * @param localParticle
     * @param isReference
     *            boolean true to set as reference
     */
    public void addParticle(LocalParticle localParticle, boolean isReference) {
        if (null == localParticle) {
            throw new IllegalArgumentException("localParticle can not be null");
        }
        localParticle.setReference(isReference);
        String particleName = localParticle.getName();
        if (false == isReference) {
            if (childrenParticleMap.containsKey(particleName)) {
                getLogger().fine("Reference particle [" + particleName + "] exists and will be overridden");
            }
            childrenParticleMap.put(particleName, localParticle);
        }
        else {
            if (referenceParticleMap.containsKey(particleName)) {
                getLogger().fine("Containment particle [" + particleName + "] exists and will be overridden");
            }
            referenceParticleMap.put(particleName, localParticle);
        }
        localParticle.setParent(this);
    }

    /**
     * Removes <code>particle</code>.
     *
     * @param particle <code>LocalParticle</code> to remove
     */
    protected void removeParticle(LocalParticle particle) {
//      if (childrenParticleMap.containsValue(particle)) {
//          childrenParticleMap.remove(particle.getName());
//      } else if (referenceParticleMap.containsValue(particle)) {
//          referenceParticleMap.remove(particle.getName());
//      }
        Object particleRemoved = childrenParticleMap.remove(particle.getName());
        if (particleRemoved == null){
            particleRemoved = referenceParticleMap.remove(particle.getName());
        }
    }

    /**
     * Add a LocalElement to the given particle
     *
     * @param particleName
     * @param localElement
     * @return
     * @throws Exception
     */
    public boolean addElement(String particleName, LocalElement localElement) {
        /*
         * If the particle is not found then create a new LocalParticle
         */
        LocalParticle localParticle = getParticle(particleName);
        if (localParticle == null) {
            throw new IllegalArgumentException("could not find "+particleName+" in "+getName());
        }
        /*
         * Finally add the element
         */
        if (false == localParticle.isReference()) {
            localElement.setParent(this);
        }

        if (true == localParticle.addElement(localElement)) {
            // trigger a new event when there is a new element added.
            // TODO dchesney Jun 3, 2006 Trouble here. An existing element could
            // be added to a LocalParticle, in which case no event would be fired.
            //if ( true == localElement.isNew() ) {
        		setModified();
        		fireElementAdded(this, localElement);
            //}
            notify(Notification.ADD, particleName, null, localElement);
        }
        return true;
    }

    /**
     * Add a LocalElement to the given particle
     *
     * @param particleName
     * @param localElement
     * @return
     * @throws Exception
     */
    public boolean addElement(String particleName, LocalElement localElement, int sortingOrder) {
        /*
         * If the particle is not found then create a new LocalParticle
         */
        LocalParticle localParticle = getParticle(particleName);

        if (localParticle == null) {
        	throw new IllegalArgumentException("could not find "+particleName+" in "+getName());
        }
        /*
         * Finally add the element
         */
        if (false == localParticle.isReference()) {
            localElement.setParent(this);
        }

        if (true == localParticle.addElement(localElement,sortingOrder)) {
            // trigger a new event when there is a new element added.
            // TODO dchesney Jun 3, 2006 Trouble here. An existing element could
            // be added to a LocalParticle, in which case no event would be fired.
            //if ( true == localElement.isNew() ) {
            	setModified();
            	fireElementAdded(this, localElement);
            //}
            notify(Notification.ADD, particleName, null, localElement);
        }
        return true;
    }

    /**
     * Set a LocalElement to the given particle
     *
     * @param particleName
     * @param localElement
     * @return
     * @throws Exception
     */
    public boolean setElement(String particleName, LocalElement localElement) {

        LocalParticle particle = getParticle(particleName);

        if (particle.getMaxOccurs() != 1) {
            throw new UnsupportedOperationException("This operation is only allowed for elements with maximum occurence set to 1");
        }

        particle.removeAll(false);

        /*
         * Only if it's not null should it be added; A null means simply to
         * remove the element
         */
        if (null != localElement) {
            if (true == particle.addElement(localElement)) {
                particle.setInitialized(true);
                fireElementAdded(this, localElement);
            }
        }
        else {
            particle.setInitialized(true);
            this.fireElementChanged(this, this);
        }
        notify(Notification.SET, particleName, null, localElement);
        return true;
    }

    /**
     * Return a single LocalElement from the given particle
     *
     * @param particleName
     * @param localElement
     * @return
     * @throws Exception
     */
    public LocalElement getElement(String particleName) {

        LocalParticle localParticle = getParticle(particleName);

        if (localParticle.getMaxOccurs() != 1) {
            throw new UnsupportedOperationException(
                    "This operation is only allowed for elements with minimum and  maximum occurence set to 1(" + particleName + " " + this.getName()
                            + ")");
        }

        /*
         * If there is no element initialized then try to load it
         */

        if (false == localParticle.isInitialized()) {
            setBulkOperation(true);
            getLogger().fine("Loading elements: " + particleName);
            loadChildren(particleName);
            localParticle.setInitialized(true);
            getLogger().fine(particleName + " loaded.");
            setBulkOperation(false);
        }

        /*
         * If it is still empty then there is no element for this so return null
         */
        if (true == localParticle.getElementCount() < 1) {
            return null;
        }

        return localParticle.getElement(false, 0);

    }

    /**
     * Return a single LocalElement from the given particle
     *
     * @param particleName
     * @param localElement
     * @return
     * @throws Exception
     */
    public List<LocalElement> getElementList(String particleName) {

        LocalParticle localParticle = getParticle(particleName);

        // There are only 2 roles, roleTypes and roleKinds.
        if (localParticle.getMaxOccurs() != -1 &&
                localParticle.getMaxOccurs() < 2    ) {
            throw new UnsupportedOperationException("This operation is only allowed for elements with maximum occurence set to -1("
                    + particleName + "," + this.getName() + ")");
        }

        /*
         * If there is no element initialized then try to load it
         */

        if (false == localParticle.isInitialized()) {
            setBulkOperation(true);
            getLogger().fine("Loading elements: " + particleName);
            loadChildren(particleName);
            localParticle.setInitialized(true);
            getLogger().fine(particleName + " loaded.");
            setBulkOperation(false);
        }

        return localParticle.getElements();

    }

    /**
     * Return the element with the given name in the given particle
     *
     * @param particleName
     * @param elementFolder folder to look for
     * @param localElement
     * @return
     * @throws Exception
     */
    public LocalElement getElement(String particleName, String elementName, String elementFolder) {

        LocalParticle localParticle = getParticle(particleName);

        LocalElement element = localParticle.getElement(elementName, elementFolder);
        if (null == element) {
            getLogger().fine("Loading element: " + particleName + " : " + elementName);
            setBulkOperation(true);
            loadChild(particleName, elementName);
            setBulkOperation(false);
            getLogger().fine(particleName + " : " + elementName + " loaded.");
        }
        return localParticle.getElement(elementName, elementFolder);

    }

    /**
     * Return the element with the given ID in the given particle
     *
     * @param particleName
     * @param localElement
     * @return
     * @throws Exception
     */
    public LocalElement getElementByID(String particleName, String elementID) {

        LocalParticle localParticle = getParticle(particleName);
        LocalElement element = localParticle.getElementByID(elementID);
        if (null == element) {
            getLogger().fine("Loading: " + particleName);
            setBulkOperation(true);
            loadChildByID(particleName, elementID);
            setBulkOperation(false);
            getLogger().fine(particleName + " loaded.");
        }
        return localParticle.getElementByID(elementID);

    }

    /**
     * Return whether the element with the given name exists in the given
     * particle
     *
     * @param particleName
     * @param elementFolder
     * @param localElement
     * @return
     * @throws Exception
     */
    public boolean hasElement(String particleName, String elementName, String elementFolder) {
        LocalParticle localParticle = getParticle(particleName);
        return null == localParticle.getElement(elementName, elementFolder) ? false : true;
    }

    public boolean removeElement(LocalElement localElement) {
        return removeElement(localElement.getElementType(), localElement.getName(), localElement.getFolder(), false);
    }

    public boolean removeElement(LocalElement localElement, boolean forcePurge) {
        return removeElement(localElement.getElementType(), localElement.getName(), localElement.getFolder(), forcePurge);
    }

    public boolean removeElement(String particleName, String elementName, String elementFolder) {
        return removeElement(particleName, elementName, elementFolder, false);
    }

    public boolean removeElementByID(String particleName, String elementID, String elementFolder) {
    	return removeElementByID(particleName, elementID, elementFolder, false);
    }

    public boolean removeElementByID(String particleName, String elementID, String elementFolder, boolean forcePurge) {
        // Makes sure the particleName refers to a valid particle
        LocalParticle localParticle = getParticle(particleName);
        if (null == localParticle) {
            setValidationMessage(new SynValidationErrorMessage(particleName + " is not a recognized key for "+getName()));
            return false;
        }
        LocalElement element = localParticle.getElementByID(elementID);
        if (element == null){
        	//setValidationMessage(new SynValidationErrorMessage("No "+localParticle.getName() + " with id as "+elementID+" was found in "+getName()));
        	return false;
        }
        // If it's new or it's a reference then just remove it completely Or
        // if the forcePurge flag is true
        //also checking if the particle name is 'panel' because particle is by containment
        //relationship in architect,if the particle is panel then the code shouldnt go inside this if loop
    	if (true == element.isNew() || true == forcePurge || (localParticle.isReference() && particleName.equalsIgnoreCase("Panel") == false)) {
            setModified();
            // Make sure the element is removed successfully
            if (null == localParticle.removeElementByID(elementID)) {
                setValidationMessage(new SynValidationErrorMessage(localParticle.getName()+" with id as "+elementID+" could not be deleted"));
                return false;
            }
            fireElementRemoved(this, element);
            notify(Notification.REMOVE, particleName, element, null);
            return true;
        }
        element.setRemoved();
        // Set the parent as modified also
        setModified();
        fireElementRemoved(this, element);
        notify(Notification.REMOVE, particleName, element, null);
        return true;
    }

    /**
     *
     * @param localElement
     * @throws Exception
     * @since R3.0
     */
    public void replaceElementByObject(String id, LocalElement localElement) {
    	for (LocalParticle particle : childrenParticleMap.values()) {
    		if (particle.isInitialized() == false){
    			loadChildren(particle.getName());
    			particle.setInitialized(true);
    		}
			particle.replaceElementByObject(id, localElement);
		}
    	for (LocalParticle particle : referenceParticleMap.values()) {
    		if (particle.isInitialized() == false){
    			loadChildren(particle.getName());
    			particle.setInitialized(true);
    		}
			particle.replaceElementByObject(id, localElement);
		}
    }

    /**
     * Remove the element from the given particle
     *
     * @param particleName
     * @param elementName
     * @param elementFolder
     * @param forcePurge
     *            is true will forcibly remove the element regardless of its
     *            status
     * @return
     * @throws Exception
     */
    public boolean removeElement(String particleName, String elementName, String elementFolder, boolean forcePurge) {
        // Makes sure the particleName refers to a valid particle
        LocalParticle localParticle = getParticle(particleName);
        if (null == localParticle) {
            setValidationMessage(new SynValidationErrorMessage(particleName + " is not a recognized key for "+getName()));
            return false;
        }
        //Anand 11/16/06 - Modified to call getActiveElement instead of getElement
        //since getElement returns the first element matching the name irrespective of
        //whether the element has been removed previously. This causes issues in
        //the transformation logic aka bug 6617
        //LocalElement element = localParticle.getElement(elementName);
        LocalElement element = localParticle.getActiveElement(elementName, elementFolder);
        if (null != element) {
            // If it's new or it's a reference then just remove it completely Or
            // if the forcePurge flag is true
            //also checking if the particle name is 'panel' because particle is by containment
            //relationship in architect,if the particle is panel then the code shouldnt go inside this if loop

        	if (true == element.isNew() || true == forcePurge
        			|| (localParticle.isReference() && particleName.equalsIgnoreCase("Panel") == false))
            {
                setModified();
//                fireElementRemoved(this, element);
                // Make sure the element is removed successfully
                //Anand 11/16/06 - Modified to call removeActiveElementByName instead of removeElementByName
                //since removeElementByName removes the first element matching the name irrespective of
                //whether the element is to be really removed (we need to remove only that element which is New)
                //This causes issues in the transformation logic aka bug 6617
                if (false == localParticle.removeActiveElementByName(elementName, elementFolder)) {
                    //setValidationMessage(localParticle.getValidationMessage());
                    return false;
                }
                //Anand 10/17/2011, moved the fireElementRemoved after the element has been removed
                fireElementRemoved(this, element);
                notify(Notification.REMOVE, particleName, element, null);
                return true;
            }
            element.setRemoved();
            // Set the parent as modified also
            setModified();
            fireElementRemoved(this, element);
            notify(Notification.REMOVE, particleName, element, null);
            return true;
        }
        return false;
    }


    void doRemoveElement(LocalElement element) {

    }

    /**
     * Remove the element from the given particle
     *
     * @param particleName
     * @param elementName
     * @return
     * @throws Exception
     */
    public boolean removeChildren(String particleName) {
        /*
         * Makes sure the particleName refers to a valid particle
         */
        LocalParticle localParticle = getParticle(particleName);
        if (null == localParticle) {
            setValidationMessage(new SynValidationErrorMessage(particleName + " is not a recognized key for this element"));
            return false;
        }

        //KHAI: Not sure why the original implementation was this way...
        // changing to a simpler syntax.
        //        List list = new ArrayList();
        //        list.addAll(localParticle.getElements());
        //        for (int i = 0; i < list.size(); i++) {
        //            this.removeElement(particleName, ((LocalElement)
        // list.get(i)).getName());
        //        }

        localParticle.removeAll();

        return true;
    }

    // TODO: Somehow, removeChildren is designed to clear elements and set initialize to false.
    //       removeChildrenByParticleName is created to really remove all the elements of a particle in update mode.
    //       As we are not fully sure what knobs we have to switch for event mechanism, reuse removeElement() API.
    public boolean removeChildrenByParticleName(String particleName) {
        /*
         * Makes sure the particleName refers to a valid particle
         */
        LocalParticle localParticle = getParticle(particleName);
        if (null == localParticle) {
            setValidationMessage(new SynValidationErrorMessage(particleName + " is not a recognized key for this element"));
            return false;
        }

        List<LocalElement> elements = localParticle.getElements();
        for (int i=0; i < elements.size(); i++) {
            LocalElement child = (LocalElement) elements.get(i);
            this.removeElement(particleName,child.getName(), child.getFolder());
        }
        return true;
    }

    /**
     * gets the last visible element if there is any
     *
     * @return LocalElement the highest ranked active child; may return null if
     *         there are no children or there are children but none are still
     *         active (meaning they are all marked as 'removed')
     */
    public LocalElement getLastActiveChild() {

        List<LocalElement> children = getAllChildren(false);

        LocalElement highestRankedChild = null;
        int highestRank = -1;
        if (false == children.isEmpty()) {
            for (Iterator<LocalElement> iter = children.iterator(); iter.hasNext();) {
                LocalElement element = iter.next();
                if (element.getSortingOrder() > highestRank) {
                    highestRank = element.getSortingOrder();
                    highestRankedChild = element;
                }
            }
        }

        return highestRankedChild;
    }

    /**
     * gets the next active child by sorting position relative to the given rank
     *
     * @return LocalElement the highest ranked active child; may return null if
     *         there are no children or there are children but none are still
     *         active (meaning they are all marked as 'removed')
     */
    public LocalElement getNextActiveChild(int sourceRank) {

        List<LocalElement> children = getAllChildren(false);

        int nextHighestRank = Integer.MAX_VALUE;
        LocalElement nextActiveChild = null;
        if (false == children.isEmpty()) {
            for (Iterator<LocalElement> iter = children.iterator(); iter.hasNext();) {
                LocalElement element = iter.next();
                int currentRank = element.getSortingOrder();
                if (currentRank > sourceRank && currentRank < nextHighestRank) {
                    nextHighestRank = element.getSortingOrder();
                    nextActiveChild = element;
                }
            }
        }

        return nextActiveChild;
    }

    public void removeAllChildren() {
        for (Iterator<LocalParticle> iter = childrenParticleMap.values().iterator(); iter.hasNext();) {
            LocalParticle particle = iter.next();
            if (true == particle.isInitialized()) {
                getLogger().fine("Removing all children of: " + getFullPath() + "::" + particle.getName());
                particle.removeAll();
            }

        }

        for (Iterator<LocalParticle> iter = referenceParticleMap.values().iterator(); iter.hasNext();) {
            LocalParticle particle = iter.next();
            if (true == particle.isInitialized()) {
                getLogger().fine("Removing all references of: " + getFullPath() + "::" + particle.getName());
                particle.removeAll();
            }
        }
        setChildrenLoaded(false);
    }

    /**
     * Returns the <code>LocalElement</code>s that are part of the
     * relationship with the given name. This API filters out all children
     * marked as REMOVED.
     *
     * @param particleName name of the relationship
     * @return <code>List</code> of <code>LocalElement</code>
     * @throws Exception
     */
    public List<LocalElement> getChildren(String particleName) {
        return getChildren(particleName, false);
    }

    /**
     * Returns the <code>LocalElement</code>s that are part of the
     * relationship with the given name.
     *
     * @param particleName name of the relationship
     * @param includeInactive if <code>true</code> then return all children
     * including those marked as removed
     * @return <code>List</code> of <code>LocalElement</code>
     * @throws Exception
     */
    public List<LocalElement> getChildren(String particleName, boolean includeInactive) {
        LocalParticle localParticle = getParticle(particleName);
        if (localParticle == null) {
            return Collections.emptyList();
        }
        if (false == localParticle.isInitialized()) {
            setBulkOperation(true);
            getLogger().fine("Loading elements: " + particleName);
            loadChildren(particleName);
            getLogger().fine(localParticle.getElementCount() + " " + particleName + " loaded.");
            setBulkOperation(false);
            localParticle.setInitialized(true);
        }
        if (true == includeInactive) {
            return localParticle.getElements();
        }
        return localParticle.getElements(true);
    }

    /**
     * Return a listing of all contained <code>LocalElement</code>s.
     *
     * @param includeInactive <code>true</code> to return all children
     * (including those marked as REMOVED). If <code>false</code> then REMOVED
     * children are filtered as a convenience.
     * @return a <code>List</code> of <code>LocalElement</code>
     * @throws Exception
     */
    public List<LocalElement> getAllChildren(boolean includeInactive) {
        bulkOperation = true;
        List<LocalElement> allChildren = new ArrayList<LocalElement>();
        for (Iterator<LocalParticle> pIter = getParticles(false).iterator(); pIter.hasNext();) {
            LocalParticle particle = pIter.next();
            List<LocalElement> children = getChildren(particle.getName(), includeInactive);
            if (null != children && false == children.isEmpty()) {
                allChildren.addAll(children);
            }
        }
        bulkOperation = false;
        return allChildren;
    }

    /**
     * return a list of children; bypassing the particle containers
     *
     * @param getAll
     *            if true then return all children (including those marked as
     *            REMOVED) If false then filter out the REMOVED children (as a
     *            convenience)
     * @return a list of LocalElement
     * @throws Exception
     */
    public List<LocalElement> getAllReferences(boolean includeInactive) {
        bulkOperation = true;
        List<LocalElement> allChildren = new ArrayList<LocalElement>();

        for (Iterator<LocalParticle> pIter = getReferenceParticles().iterator(); pIter.hasNext();) {
            LocalParticle particle = pIter.next();
            List<LocalElement> children = getChildren(particle.getName(), includeInactive);

            if (null != children && false == children.isEmpty()) {
                allChildren.addAll(children);
            }
        }
        bulkOperation = false;
        return allChildren;
    }

    /**
     * Subclasses should override this method if the subclass has a choice of
     * elements to choose from for the references
     *
     * @param elementType
     * @return a list of LocalElement as choices
     */
    public List<LocalElement> getReferenceChoices(String elementType) {
        return Collections.emptyList();
    }

    /**
     * Returns the integer Index of the given choice object an ordered set of
     * choices
     *
     * Subclasses should implement this.
     *
     * @param ref
     * @return
     */
    public int getReferenceIndex(String refType, Object ref) {
        return -1;
    }

    /**
     * Return the reference element
     *
     * @param elementType
     * @param referenceName
     * @return
     * @throws Exception
     */
    public LocalElement getReferenceByName(String elementType, String referenceName) {
        List<LocalElement> allChoices = getReferenceChoices(elementType);
        LocalElement element = null;
        for (Iterator<LocalElement> iter = allChoices.iterator(); iter.hasNext();) {
            element = iter.next();
            if (element.getName().equals(referenceName)) {
                break;
            }
        }

        return element;
    }

    /**
     * Return the reference element
     *
     * @param elementType
     * @param referenceID
     * @return
     * @throws Exception
     */
    public LocalElement getReferenceByID(String elementType, String referenceID) {
        List<LocalElement> allChoices = getReferenceChoices(elementType);
        LocalElement element = null;
        for (Iterator<LocalElement> iter = allChoices.iterator(); iter.hasNext();) {
            element = iter.next();
            if (element.getID().equals(referenceID)) {
                break;
            }
        }

        return element;
    }

    /**
     * A refresh hint to signal whether there are any children to refresh This
     * method only return true if at least 1 particle has been initialized
     *
     * @return
     */
    public boolean isChildrenRefreshNeeded() {

        for (Iterator<LocalParticle> pIter = getParticles(true).iterator(); pIter.hasNext();) {
            LocalParticle particle = pIter.next();
            if (true == particle.isInitialized()) {
                return true;
            }
        }
        return false;
    }

    public String getElementLocatorKey() {
        return elementLocatorKey;
    }

    public void setElementLocatorKey(EObject mdElement) {
        this.elementLocatorKey = getEObjectId(mdElement);
    }

    public EObject getEObject() {
    	return persistedElement;
    }

    /**
     * Returns the full containment path from the root of the containment
     * hierarchy down to this element; each level is separated by a "/"
     *
     *
     * @return the full path
     * @throws Exception
     */
    public final String getFullPath() {
        if (fullPath != null)
            return fullPath;

        String myName = getElementType() + ":" + getName();

        if (null != getParent()) {
            fullPath = getParent().getFullPath() + "/" + myName;
        }
        if (fullPath == null)
            fullPath = myName;

        return fullPath;
    }


    public boolean isRemoved() {
        return getInternalStatus().equals(InternalStatusEnum.StatusRemove);
    }

    public boolean isNew() {
        return getInternalStatus().equals(InternalStatusEnum.StatusNew);
    }

    public boolean isModified() {
        return getInternalStatus().equals(InternalStatusEnum.StatusModified);
    }

    public boolean isExisting() {
        return getInternalStatus().equals(InternalStatusEnum.StatusExisting);
    }

    public void setRemoved() {
        setInternalStatus(InternalStatusEnum.StatusRemove);
    }

    public void setNew() {
        setInternalStatus(InternalStatusEnum.StatusNew);
    }

    public void setModified() {
        setInternalStatus(InternalStatusEnum.StatusModified);
    }

    public void setCascadedModified() {
        setInternalStatus(InternalStatusEnum.StatusModified);
        if (this.getParent() != null) {
            this.getParent().setModified();
        }
    }

    public void setExisting() {
        setInternalStatus(InternalStatusEnum.StatusExisting);
    }

    public boolean isBulkOperation() {
        if (true == bulkOperation) {
            return true;
        }
        if (null != getParent()) {
            return getParent().isBulkOperation();
        }
        return false;
    }

    public void setBulkOperation(boolean bulkOperation) {
        this.bulkOperation = bulkOperation;
    }

    /**
     * Resets the alreadySet flag for all properties; this is mainly used for
     * refreshing the elements properties.
     *
     * @param flag
     * @throws Exception
     */
    public void setPropertiesAlreadySet(boolean flag) {
        List<ISynXSDAttributeDeclaration> props = getProperties();
        for (Iterator<ISynXSDAttributeDeclaration> iter = props.iterator(); iter.hasNext();) {
            SynProperty prop = (SynProperty) iter.next();
            prop.setAlreadySet(flag);
//            System.err.println("setting alreadySet flag to: " + flag + " for: " + getFullPath() + "::" + prop.getName() + " isWorkingCopy: " + isWorkingCopy());
        }
    }

    public void elementAdded(IMessageProvider parent, IMessageProvider newElement) {
        fireElementAdded(parent, newElement);

    }

    public void elementChanged(IMessageProvider parent, IMessageProvider changedElement) {
        fireElementChanged(parent, changedElement);

    }

    public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement) {
        fireElementRemoved(parent, removedElement);

    }

    public void elementStatusChanged(IMessageProvider provider, InternalStatusEnum status) {
        fireStatusChanged(provider, status);

    }

    //=================================================================================
    // A callback method for the contained properties to notidy this element
    // that a value has changes
    //=================================================================================

    public void notifyPropertyChanged(SynProperty property, Object oldValue, Object newValue) {
        firePropertyChanged(this, property, oldValue, newValue);
    }

    //=================================================================================
    // Delegation to elementMessageProvider
    //=================================================================================

    /**
     * The elementMessageProvider
     */
    public SynElementMessageProvider getElementMessageProvider() {
        return elementMessageProvider;
    }

    public String getProviderType() {
        return getElementType();
    }

    public List<String> getProviderTypes() {
        return getAllElementTypes();
    }

    public boolean hasSubscriber() {
        if (null != elementMessageProvider) {
            return elementMessageProvider.hasSubscriber();
        }
        return false;
    }

    /**
     * @param subscriber
     */
    public void pause(ISynElementChangeListener subscriber) {
        if (null != elementMessageProvider) {
            elementMessageProvider.pause(subscriber);
        }
    }

    /**
     * @param subscriber
     */
    public void resume(ISynElementChangeListener subscriber) {
        if (null != elementMessageProvider) {
            elementMessageProvider.resume(subscriber);
        }
    }

    public void fireElementAdded(IMessageProvider provider, IMessageProvider newElement) {
        setModified();

        if (false == isBulkOperation()) {
            /*
             * First notifies all the referencing parents; these are parents not
             * by containment bu by reference
             */
            //            for (Iterator iter = getReferenceParentParticles().iterator();
            // iter.hasNext();) {
            //                LocalParticle referencingParentParticle = (LocalParticle)
            // iter.next();
            //
            //                if (null != referencingParentParticle.getParent()) {
            //                    referencingParentParticle.getParent().fireElementAdded(provider,
            // newElement);
            //                }
            //            }
            /*
             * Then notifies the containment parents
             */
            if (true == shouldForwardEvent()) {
                getParent().fireElementAdded(provider, newElement);
            }

            /*
             * Then any additional subscribers
             */
            if (true == hasSubscriber()) {
                elementMessageProvider.fireElementAdded(provider, newElement);
            }
        }
    }

    public void fireElementChanged(IMessageProvider provider, IMessageProvider changedElement) {
        setModified();

        if (false == isBulkOperation()) {
            /*
             * First notifies all the referencing parents; these are parents not
             * by containment bu by reference
             */
            //            for (Iterator iter = getReferenceParentParticles().iterator();
            // iter.hasNext();) {
            //                LocalParticle referencingParentParticle = (LocalParticle)
            // iter.next();
            //
            //                if (null != referencingParentParticle.getParent()) {
            //                    referencingParentParticle.getParent().fireElementChanged(provider,
            // changedElement);
            //                }
            //            }
            /*
             * Then notifies the containment parents
             */
            if (true == shouldForwardEvent()) {
                getParent().fireElementChanged(provider, changedElement);
            }

            /*
             * Then any additional subscribers
             */if (true == hasSubscriber()) {
                elementMessageProvider.fireElementChanged(provider, changedElement);
            }
        }
    }

    public void fireElementRemoved(IMessageProvider provider, IMessageProvider removedElement) {
        setModified();

        if (false == isBulkOperation()) {
            /*
             * First notifies all the referencing parents; these are parents not
             * by containment bu by reference
             */
            //            for (Iterator iter = getReferenceParentParticles().iterator();
            // iter.hasNext();) {
            //                LocalParticle referencingParentParticle = (LocalParticle)
            // iter.next();
            //
            //                if (null != referencingParentParticle.getParent()) {
            //                    referencingParentParticle.getParent().fireElementRemoved(provider,
            // removedElement);
            //                }
            //            }
            /*
             * Then notifies the containment parents
             */
            if (true == shouldForwardEvent()) {
                getParent().fireElementRemoved(provider, removedElement);
            }

            /*
             * Then any additional subscribers
             */
            if (true == hasSubscriber()) {
                elementMessageProvider.fireElementRemoved(provider, removedElement);
            }
        }
    }

    public void firePropertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
        setModified();
        if (false == isBulkOperation()) {

            /*
             * First notifies all the referencing parents; these are parents not
             * by containment bu by reference
             */
            //            for (Iterator iter = getReferenceParentParticles().iterator();
            // iter.hasNext();) {
            //                LocalParticle referencingParentParticle = (LocalParticle)
            // iter.next();
            //
            //                if (null != referencingParentParticle.getParent()) {
            //                    referencingParentParticle.getParent().firePropertyChanged(provider,
            // property, oldValue, newValue);
            //                }
            //            }
            /*
             * Then notifies the containment parents
             */
            if (true == shouldForwardEvent()) {
                getParent().firePropertyChanged(provider, property, oldValue, newValue);
            }

            /*
             * Then any additional subscribers
             */
            if (true == hasSubscriber()) {
                elementMessageProvider.firePropertyChanged(provider, property, oldValue, newValue);
            }
        }
    }

    public void fireStatusChanged(IMessageProvider provider, InternalStatusEnum status) {
        if (false == isBulkOperation()) {
            /*
             * First notifies all the referencing parents; these are parents not
             * by containment bu by reference
             */
            //            for (Iterator iter = getReferenceParentParticles().iterator();
            // iter.hasNext();) {
            //                LocalParticle referencingParentParticle = (LocalParticle)
            // iter.next();
            //
            //                if (null != referencingParentParticle.getParent()) {
            //                    referencingParentParticle.getParent().fireStatusChanged(provider,
            // status);
            //                }
            //            }
            /*
             * Then notifies the containment parents
             */
            if (true == shouldForwardEvent()) {
                getParent().fireStatusChanged(provider, status);
            }

            /*
             * Then any additional subscribers
             */

            if (true == hasSubscriber()) {
                elementMessageProvider.fireStatusChanged(provider, status);
            }
        }
    }

    private boolean shouldForwardEvent() {
        if (null == getParent()) {
            return false;
        }
        return true;
    }

    public List<String> getSubscribedTopics(ISynElementChangeListener subscriber) {
        if (null != elementMessageProvider) {
            return elementMessageProvider.getSubscribedTopics(subscriber);
        }
        return Collections.emptyList();
    }

    public List<ISynElementChangeListener> getSubscribers(String topicName) {
        if (null != elementMessageProvider) {
            return elementMessageProvider.getSubscribers(topicName);
        }
        return Collections.emptyList();
    }

    public List<String> getTopics() {
        if (null != elementMessageProvider) {
            return elementMessageProvider.getTopics();
        }
        return Collections.emptyList();
    }

    public void subscribe(ISynElementChangeListener subscriber, String topicName) {
        if (null == elementMessageProvider) {
            elementMessageProvider = new SynElementMessageProvider(this);
        }
        elementMessageProvider.subscribe(subscriber, topicName);
    }

    public void subscribe(ISynElementChangeListener subscriber, List<String> topicList) {
        if (null == elementMessageProvider) {
            elementMessageProvider = new SynElementMessageProvider(this);
        }
        elementMessageProvider.subscribe(subscriber, topicList);
    }

    public void subscribeForPropertyChange(ISynElementChangeListener subscriber, String propertyName) {
        if (null == elementMessageProvider) {
            elementMessageProvider = new SynElementMessageProvider(this);
        }
        elementMessageProvider.subscribeToPropertyTopic(subscriber, propertyName);
    }

    public void subscribeToAll(ISynElementChangeListener subscriber) {
        if (null == elementMessageProvider) {
            elementMessageProvider = new SynElementMessageProvider(this);
        }

        elementMessageProvider.subscribeToAll(subscriber);
    }

    public void unsubscribe(ISynElementChangeListener subscriber, String topicName) {
        if (null != elementMessageProvider) {
            elementMessageProvider.unsubscribe(subscriber, topicName);
        }
    }

    public void unsubscribe(ISynElementChangeListener subscriber, List<String> topicList) {
        if (null != elementMessageProvider) {
            elementMessageProvider.unsubscribe(subscriber, topicList);
        }
    }

    public void unsubscribeForPropertyChange(ISynElementChangeListener subscriber, String propertyName) {
        if (null != elementMessageProvider) {
        	elementMessageProvider.unsubscribeFromPropertyTopic(subscriber, propertyName);
        }
    }

    public void unsubscribeAll(ISynElementChangeListener subscriber) {
        if (null != elementMessageProvider) {
            elementMessageProvider.unsubscribeAll(subscriber);
        }
    }

    //=================================================================================
    // Delegation to particle (that contains this element) to get occurrence
    // constraints
    //=================================================================================

    public LocalParticle getParentParticle() {
        return parentParticle;
    }

    public void setParentParticle(LocalParticle parentParticle) {
        this.parentParticle = parentParticle;
//        if (null != parentParticle) {
//            setElementType(parentParticle.getName());
//        }
//        else {
//            setElementType("NONE");
//        }
    }

    //===================================================================================

    public List<LocalParticle> getReferenceParentParticles() {
        return referenceParentParticles;
    }

    public void addReferenceParentParticles(LocalParticle referenceParentParticle) {
        this.referenceParentParticles.add(referenceParentParticle);
    }

    public void removeReferenceParentParticles(LocalParticle referenceParentParticle) {
        this.referenceParentParticles.remove(referenceParentParticle);
    }

	/**
     * Synchronizes both the element and its children
     * We are synchronizing LocalXXXX with MDXXXX.
     *
     * (1) If LocalSession is a psuedo element which does not exist in MDS.
     * (2) If there is no mdElement, it is considered to be a new element (The Create of CRUD).
     * (3) If mdElement exists, it is considered to be either update or delete (The Update/Delete of CRUD).
     *
     */
    public synchronized void synchronize() {
        boolean newChildCreated = false;
        // Create new EObject
        if (null == persistedElement) {
        	EObject newEntity = getParent().createMDChild(this);
            if (null != newEntity) {
            	newChildCreated = true;
            	persistedElement = newEntity;
                synchronize(newEntity);
            }
        }
        else {
            synchronize(persistedElement);
        }
        if (true == newChildCreated) {
            fireElementAdded(getParent(), this);
        }
        else {
            fireElementChanged(getParent(), this);
        }
    }

    /**
     * Synchronized both the element and its children to the given MDElement.
     * This method is appropriate for use in cloning-type support where the
     * children are synchronized to a new parent
     *
     * @param pEntity
     * @throws Exception
     */
    protected synchronized void synchronize(EObject pEntity) {
        // Properties, By reference particles and By relationship particles are
        // synchronized in synchronizeElement
        synchronizeElement(pEntity);
        // Only By containment particles are synchronized in
        // synchronizedChildren.
        // This method will be recursive call.
        synchronizeChildren(pEntity);
        setExisting();

    }

    /**
     * Synchronizes the state of <code>eObject</code> with the current state
     * of this <code>LocalElement</code>.
     *
     * @param eObject the <code>MDElement</code> whose state will be made to
     * reflect the current state of this <code>LocalElement</code>
     * @throws Exception
     */
    protected void synchronizeElement(EObject eObject) {
    	setEObjectId(eObject, getID());
    	setEObjectName(eObject, getName());
    	setEObjectFolder(eObject, getFolder());
    	setEObjectDescription(eObject, getDescription());
        setElementLocatorKey(eObject);
    }

    /**
     * Synchronize the children element with the given parent element. This
     * method is appropriate for use in cloning-type support where the children
     * are synchronized to a new parent
     *
     * @param parent
     * @throws Exception
     */
    protected void synchronizeChildren(EObject parent) {
        setElementLocatorKey(parent);
        if (null == parent) {
            getLogger().warning("The parent argument is null which may lead to unexpected behavior");
        }
        synchronizeChildren();
    }

    /**
     * Synchronize the children element with the existing parent element
     *
     * @throws Exception
     */
    protected void synchronizeChildren() {
        List<LocalElement> childrenToBePurged = new ArrayList<LocalElement>();
        List<LocalElement> allChildren = new ArrayList<LocalElement>();

        /*
         * Iterate through all the particles and only add elements from
         * particles that have already been initialized Note: This is a
         * deviation from how children are normally accessed which is through
         * the getChildren(...) API's but those API's will attempt to populate
         * the children if the particles have not been initialized.
         */
        for (Iterator<LocalParticle> iter = getParticles().iterator(); iter.hasNext();) {
            LocalParticle particle = iter.next();
            if (true == particle.getElementCount() > 0) {
                allChildren.addAll(particle.getElements());
            }

        }

        allChildren = getOrderedElementsByAction(allChildren);

        for (Iterator<LocalElement> iter = allChildren.iterator(); iter.hasNext();) {
            LocalElement child = iter.next();
            getLogger().fine("Synchronizing: " + child.getElementType() + ":" + child.getName() + " status: " + child.getInternalStatus().toString());
            if (child.isRemoved()) {
                deleteMDChild(child);
                childrenToBePurged.add(child);
            }
            else if (true == child.isNew()) {
                child.synchronize();
                fireElementAdded(this, child);
            }
            else if (true == child.isModified()) {
                child.synchronize();
            }
        }

        for (Iterator<LocalElement> iter = childrenToBePurged.iterator(); iter.hasNext();) {
            LocalElement orphan = iter.next();
            LocalParticle particle = orphan.getParentParticle();
            particle.removeElementByName(orphan.getName());

        }

    }

    protected List<LocalElement> getOrderedElementsByAction(List<LocalElement> allChildren) {
		// NO OP here, subclassed by classes where needed. Later the same API will come here and this would become private.
    	//This actually orders deleted elements first, then new, then modified.
    	return allChildren;
	}


    //===================================================================
    // Repositioning
    //===================================================================

    /**
     * Increments the sorting order by 1 by switching the sorting order with the
     * element that is 1 position immediately higher than this element.
     *
     */
    public void moveUp() {
        try {

            LocalElementMoveHandler.moveUp(this);

        } catch (Exception e) {
            getLogger().log(Level.SEVERE, e.getMessage(), e);
        }

    }

    public void moveDown() {

        try {

            LocalElementMoveHandler.moveDown(this);

        } catch (Exception e) {
            getLogger().log(Level.SEVERE, e.getMessage(), e);
        }

    }

    /**
     * Returns whether the given localElement may move to the position occupied
     * by this element.
     *
     * This is mainly used to check whether a drag-n-drop is allowed on this
     * element.
     *
     * @param moveCandidate
     * @return
     */
    public boolean moveToHereAllowed(LocalElement moveCandidate) {

        try {
            return LocalElementMoveHandler.moveToHereAllowed(this, moveCandidate);
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return true;
    }

    /**
     * Moves this element to a sorting position above the given target element
     *
     * This is mainly use in a drag-n-drop action when the elements are in a
     * tree or table; to re-order their placement in the tree or table.
     *
     * @param targetElement
     */
    public void moveToAbove(LocalElement targetElement) {

        try {
            LocalElementMoveHandler.moveToAbove(this, targetElement);

        } catch (Exception e) {
            getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Moves this element to a sorting position below the given target element
     *
     * This is mainly use in a drag-n-drop action when the elements are in a
     * tree or table; to re-order their placement in the tree or table.
     *
     * @param targetElement
     */
    public void moveToBelow(LocalElement targetElement) {

        try {
            LocalElementMoveHandler.moveToBelow(this, targetElement);
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    //===================================================================
    // Misc
    //===================================================================

    public InternalStatusEnum getInternalStatus() {
        return internalStatus;
    }

    public boolean hasThisStatus(InternalStatusEnum newInternalStatus) {
        return hasThisStatus(newInternalStatus, true);
    }

    public boolean hasThisStatus(InternalStatusEnum newInternalStatus, boolean queryAllChildren) {
        if (true == getInternalStatus().equals(newInternalStatus)) {
            return true;
        }

        List<ISynXSDAttributeDeclaration> properties = getProperties();
        for (Iterator<ISynXSDAttributeDeclaration> iter = properties.iterator(); iter.hasNext();) {
            SynProperty prop = (SynProperty) iter.next();
            if (true == prop.getInternalStatus().equals(newInternalStatus)) {
                return true;
            }
        }

        if (true == queryAllChildren) {
            List<LocalElement> childrenList = getAllChildren(true);
            for (Iterator<LocalElement> iter = childrenList.iterator(); iter.hasNext();) {
                LocalElement child = iter.next();
                if (true == child.hasThisStatus(newInternalStatus, queryAllChildren)) {
                    return true;
                }
            }

            //            childrenList = getAllReferences(true);
            //            for (Iterator iter = childrenList.iterator(); iter.hasNext();) {
            //                LocalElement child = (LocalElement) iter.next();
            //                if (true == child.hasThisStatus(newInternalStatus,
            // queryAllChildren)) {
            //                    return true;
            //                }
            //            }
        }

        return false;
    }

    public void setInternalStatus(InternalStatusEnum newInternalStatus) {
        setInternalStatus(newInternalStatus, false);
    }

    public void setInternalStatus(InternalStatusEnum newInternalStatus, boolean synchAllChildren) {

        /*
         * If the element has been marked as removed then it is illegal to
         * change its status
         */
        if (this.internalStatus.equals(InternalStatusEnum.StatusRemove)) {
            // do nothing. Once it is marked remove, don't change the status.
            return;
        }
        /*
         * Only set the 'Modified' status if the element is not new because new
         * elements, when modified, are still considered new.
         *
         */
        if (true == newInternalStatus.equals(InternalStatusEnum.StatusModified)) {
            if (false == this.internalStatus.equals(InternalStatusEnum.StatusNew)) {
                internalSetInternalStatus(newInternalStatus);
                propagateStatus(newInternalStatus, synchAllChildren);
            }
        }
        else {
            internalSetInternalStatus(newInternalStatus);
            propagateStatus(newInternalStatus, synchAllChildren);
        }

        /*
         * Only notify if the status is not "new"
         */
        if (false == newInternalStatus.equals(InternalStatusEnum.StatusNew)) {
            fireStatusChanged(this, internalStatus);
        }
    }

    /**
     * Sets the internal status to <code>newInternalStatus</code> with no
     * side-effects (no notifications are fired).
     *
     * @param newInternalStatus the new status
     */
    public void internalSetInternalStatus(InternalStatusEnum newInternalStatus) {
        internalStatus = newInternalStatus;
    }

    /**
     * Propagate the internal status to all the properties and all the children
     *
     * @param newInternalStatus
     * @throws Exception
     */
    protected void propagateStatus(InternalStatusEnum newInternalStatus, boolean synchAllChildren) {
        if (true == synchAllChildren) {
            List<ISynXSDAttributeDeclaration> properties = getProperties();
            for (Iterator<ISynXSDAttributeDeclaration> iter = properties.iterator(); iter.hasNext();) {
                SynProperty prop = (SynProperty) iter.next();
                prop.setInternalStatus(newInternalStatus, synchAllChildren);
            }

            List<LocalElement> childrenList = getAllChildren(false);
            for (Iterator<LocalElement> iter = childrenList.iterator(); iter.hasNext();) {
                LocalElement child = iter.next();
                child.setInternalStatus(newInternalStatus, synchAllChildren);
            }

            //  ERNIE: In the majority of cases, reference is not part of the
            // main piece. Don't bother to notify them.
            //            List references = getAllReferences(false);
            //            for (Iterator iter = references.iterator(); iter.hasNext();) {
            //                LocalElement child = (LocalElement) iter.next();
            //                child.setInternalStatus(newInternalStatus, synchAllChildren);
            //            }
        }
    }

    public boolean equals(Object obj) {

        try {
            if (obj instanceof LocalElement) {
                LocalElement localElement = (LocalElement) obj;
                if (StringUtil.isEmpty(getID()) == true) {
                	return getScopeName().equalsIgnoreCase(localElement.getScopeName());
                }
                return getID().equals(localElement.getID());
            }
        } catch (Exception e) {
            return super.equals(obj);
        }

        return super.equals(obj);
    }

    public int compareTo(LocalElement o) {
        try {
            int result = 0;
			String thisFolder = getFolder();
			thisFolder = (thisFolder == null) ? "" : thisFolder;
			String otherFolder = o.getFolder();
			otherFolder = (otherFolder == null) ? "" : otherFolder;
    		result = thisFolder.compareToIgnoreCase(otherFolder);
            if (result == 0){
        		String thisName = getName();
    			thisName = (thisName == null) ? "" : thisName;
        		String otherName = o.getName();
    			otherName = (otherName == null) ? "" : otherName;
            	result = thisName.compareToIgnoreCase(otherName);
            }
            return result;
		} catch (Exception e) {
		}
		return 0;
    }

    public Object clone() {
        try {
            // initialize all particles (containment and non-containment) before cloning
            for (Iterator<String> iterator = getParticleNames(true).iterator(); iterator.hasNext();) {
                String particleName = iterator.next();
                getChildren(particleName);
            }
            LocalElement sClone = (LocalElement) cloneThis();
//            sClone.setElementType(getElementType());
            // clone non-system properties
            for (Iterator<ISynXSDAttributeDeclaration> iter = getProperties().iterator(); iter.hasNext();) {
                SynProperty prop = (SynProperty) iter.next();
                if (false == prop.isSystem()) {
                    sClone.setPropertyValue(prop.getName(), prop.getValue());
                }
            }
            // clone all particles that represent a containment relationship
            for (Iterator<LocalParticle> iter = getParticles().iterator(); iter.hasNext();) {
                LocalParticle particle = iter.next();
                LocalParticle cloneParticle = (LocalParticle) particle.clone();
                sClone.addParticle(cloneParticle);
            }
            // clone all particles that represent a non-containment relationship
            for (Iterator<LocalParticle> iter = getReferenceParticles().iterator(); iter.hasNext();) {
                LocalParticle particle = iter.next();
                LocalParticle cloneParticle = (LocalParticle) particle.clone();
                sClone.addParticle(cloneParticle, true);
            }
            return sClone;
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    public LocalElement getWorkingCopy() {
        throw new UnsupportedOperationException("getWorkingCopy");
    }

    public void purgeElement(LocalElement localElement) {

        /*
         * First purge all of its children elements
         *
         * Note: This is a deviation from how children are normally accessed
         * which is through the getChildren(...) API's but those API's will
         * attempt to populate the children if the particles have not been
         * initialized.
         */
        for (Iterator<LocalParticle> iter = localElement.getParticles().iterator(); iter.hasNext();) {
            LocalParticle particle = iter.next();
            if (true == particle.isInitialized() && false == particle.isReference()) {
                List<LocalElement> childrenELements = particle.getElements();
                /*
                 * Purge each child of the given localElement
                 */
                for (int i = 0; i < childrenELements.size(); i++) {
                    LocalElement child = (LocalElement) childrenELements.get(i);
                    localElement.purgeElement(child);
                }

            }

        }

        /*
         * Then finally remove the given element itself
         */
        removeElement(localElement, true);
    }

    public void purgeElement() {
        /*
         * First purge all of its children elements
         *
         * Note: This is a deviation from how children are normally accessed
         * which is through the getChildren(...) API's but those API's will
         * attempt to populate the children if the particles have not been
         * initialized.
         */
        for (Iterator<LocalParticle> iter = getParticles().iterator(); iter.hasNext();) {
            LocalParticle particle = iter.next();
            if (true == particle.isInitialized() && false == particle.isReference()) {
                List<LocalElement> childrenELements = particle.getElements();
                /*
                 * Purge each child of the given localElement
                 */
                for (int i = 0; i < childrenELements.size(); i++) {
                    LocalElement child = (LocalElement) childrenELements.get(i);
                    purgeElement(child);
                }

            }

        }
    }

    //==================================================================
    // The following methods are convenience API's that delegates
    // to the reflection style API's for accessing attribute values
    //==================================================================

    public int getSortingOrder() {
        return sortingOrder;
//        return Integer.parseInt(getPropertyValue(PROP_KEY_SORTING_ORDER));
    }

    public void setSortingOrder(int sortingOrder) {
        this.sortingOrder = sortingOrder;
        setPropertyValue(PROP_KEY_SORTING_ORDER, sortingOrder + "");
        setSystemStatus(true);
    }

    //==============================================================
    // messaging
    //==============================================================

    public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
        setModified();
        firePropertyChanged(provider, property, oldValue, newValue);

    }

    //==============================================================
    // mainly for debug
    //==============================================================


    public String toString() {
//        StringBuffer sb = new StringBuffer();
//        try {
//            getElementInfo("", this, sb);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return sb.toString();
    	return toShortString();
    }

    public static final void getElementInfo(String indent, LocalElement element,StringBuffer buffer) {
        if (null != element) {
            String newIndent =  indent + "  ";
            buffer.append(indent);
            buffer.append("ElementName: ");
            buffer.append(element.getName());
            buffer.append(":\n");

            buffer.append(newIndent);
            buffer.append("status = ");
            buffer.append(element.getInternalStatus().toString());
            buffer.append("\n");

            buffer.append(newIndent);
            buffer.append("Properties: \n");

            for (Iterator<ISynXSDAttributeDeclaration> iter = element.getProperties().iterator(); iter.hasNext();) {
                SynProperty prop = (SynProperty) iter.next();
                buffer.append(newIndent + "  ");
                buffer.append(prop.getName());
                buffer.append(" = ");
                buffer.append(prop.getValue());
                buffer.append("\n");
            }

            buffer.append(newIndent).append("Reference Particles:");
            buffer.append("\n");
            getParticleInfo(newIndent + "  ", element.getReferenceParticles(), buffer, false);

            buffer.append(newIndent).append("Containment Particles:");
            buffer.append("\n");
            getParticleInfo(newIndent + "  ", element.getParticles(), buffer, true);
        }
    }

	private static void getParticleInfo(String indent, List<LocalParticle> particles, StringBuffer buffer, boolean recurse) {
		boolean localRecurse;
		for (Iterator<LocalParticle> iter = particles.iterator(); iter.hasNext();) {
			LocalParticle particle = iter.next();
			buffer.append(indent);
			buffer.append(particle.getName());
			buffer.append(" initialized = ");
			buffer.append(particle.isInitialized());
			buffer.append("\n");

			localRecurse = recurse;

			for (Iterator<LocalElement> iterator = particle.getElements(false).iterator(); iterator.hasNext();) {
				LocalElement element = iterator.next();
				if (localRecurse) {
					getElementInfo(indent + "  ", element, buffer);
				} else {
					buffer.append(indent + "  ").append("ElementName: ").append(element.getName()).append("\n");
				}
			}
		}
	}

    /**
     * @return Returns the isSystemStatus.
     */
    public boolean isSystemStatus() {
        return isSystemStatus;
    }

    /**
     * @param isSystemStatus The isSystemStatus to set.
     */
    public void setSystemStatus(boolean isSystemStatus) {
        this.isSystemStatus = isSystemStatus;
    }

    public List<Object> getEnumerations(String propName) {
        return Collections.emptyList();
    }

    public void addEnumeration(String propName, Object o) {
        // Ignore.

    }

    public void setEnumerations(String propName, List<Object> l) {
        // Ignore

    }

    public void removeEnumeration(String propName, Object o) {
        // ignore
    }

    //---------
    // Notifier
    //---------

    protected List<Adapter> adapters = new ArrayList<Adapter>();
    private boolean isDeliver = true; // default is to notify adapters

    public boolean isDeliver() {
    	return this.isDeliver;
    }

    public void setDeliver(boolean isDeliver) {
    	this.isDeliver = isDeliver;
    }

    public List<Adapter> getAdapters() {
    	return adapters;
    }

    public void addAdapter(Adapter adapter) {
    	if (adapter != null && !this.adapters.contains(adapter) ) {
            this.adapters.add(adapter);
        }
    }

    public void removeAdapter(Adapter adapter) {
        this.adapters.remove(adapter);
    }

    public void notify(Notification notification) {
        if ( this.isDeliver ) {
            for ( Adapter eachAdapter : this.adapters ) {
                eachAdapter.notifyChanged(notification);
            }
        }
    }

    /**
     *
     * @param notificationType: the type of Notification
     * @param particleName: feature id of Notification
     * @param oldValue: the old value which may be an object or String
     * @param newValue: the new value which may be an object or String
     */
	protected void notify(int notificationType, String particleName, Object oldValue, Object newValue) {
		if (this.isDeliver) {
			Notification notice = new Notification(notificationType, this, particleName, oldValue, newValue);
			notify(notice);
		}
	}

    /**
     * call back method when there is
     * @param notice
     */
    public void notifyChanged(Notification notice) {
    	/*if ( notice.getEventType() == Notification.LOCAL_REFRESH ) {
    		try {
    			MDElement mdElement = (MDElement)notice.getNotifier();
    			if ( mdElement.isDelete() ) {
    				LocalElement thisParent = getParent();
    				this.mdElement = null;
    				this.mdElementLocatorKey = null;
                    this.parent = null;
                    this.parentParticle = null;
    				this.refresh((EObject)null);
    				if (thisParent != null) {
    					thisParent.fireElementRemoved(thisParent, this);
    				}
    			}
    			else {
	                this.refresh(mdElement);
	                this.refreshExtra(mdElement);
    			}
    		}
    		catch (Exception e) {
    			try {
    			    getLogger().warning("refresh failed for local element:"+this.getID()+" "+this.getName());
    			}
    			catch (Exception ee) {
    			}
    		}
    	}*/
    }

    // Only specialized adapter will return true on type id. i.e. AnyEventChangeAdapter.
    public boolean isAdapterForType(Object object) {
    	return false;
    }

    protected LabelMode getLabelMode(){
        return LabelMode.YES;
    }

    protected Logger getLogger() {
    	return LOGGER;
    }

    protected String getPropertyValueWithNoException(String key) {
	    try {
	        return getPropertyValue(key);
        } catch (Exception e) {
	        throw new RuntimeException("could not retrieve value for "+key,e);
        }
    }

    protected void setPropertyValueWithNoException(String key, String value) {
	    try {
	        setPropertyValue(key,value);
        } catch (Exception e) {
	        throw new RuntimeException("could not set "+value+" on "+key,e);
        }
    }

    public String getID() {
    	return getPropertyValueWithNoException(PROP_KEY_GUID);
    }

    public void setID(String value) {
    	setPropertyValueWithNoException(PROP_KEY_GUID, value);
    }

   	public String getName() {
   		return getPropertyValueWithNoException(PROP_KEY_NAME);
   	}

    public void setName(String value) {
    	setPropertyValueWithNoException(PROP_KEY_NAME, value);
    }

   	public String getFolder() {
   		return getPropertyValueWithNoException(PROP_KEY_FOLDER);
   	}

    public void setFolder(String value) {
    	setPropertyValueWithNoException(PROP_KEY_FOLDER, value);
    }

   	public String getDescription() {
   		return getPropertyValueWithNoException(PROP_KEY_DESCRIPTION);
   	}

   	public void setDescription(String value) {
   		setPropertyValueWithNoException(PROP_KEY_DESCRIPTION, value);
   	}

   	public String getScopeName() {
   		return getFolder() + getName();
   	}

    protected abstract String getEObjectId(EObject eObject);

    protected abstract void setEObjectId(EObject eObject, String id);

    protected abstract String getEObjectName(EObject eObject);

    protected abstract void setEObjectName(EObject eObject, String name);

    protected abstract String getEObjectFolder(EObject eObject);

    protected abstract void setEObjectFolder(EObject eObject, String folder);

    protected abstract String getEObjectDescription(EObject eObject);

    protected abstract void setEObjectDescription(EObject eObject, String description);

    public String toShortString(){
    	StringBuilder sb = new StringBuilder();
    	sb.append(getElementType());
    	sb.append("[key=");
    	sb.append(getElementLocatorKey());
    	sb.append(",name=");
    	sb.append(getName());
    	sb.append(",status=");
        sb.append(getInternalStatus().toString());
    	sb.append("]");
    	return sb.toString();
    }

	public boolean isPropertyValueSameAsDefault(String propertyName) {
		SynProperty property = (SynProperty) getProperty(propertyName);
		return getPropertyValue(propertyName).equals(property.getDefault());
	}

	public String getDisplayableName() {
		return getName();
	}

	public String getURI(){
		return null;
	}

}
