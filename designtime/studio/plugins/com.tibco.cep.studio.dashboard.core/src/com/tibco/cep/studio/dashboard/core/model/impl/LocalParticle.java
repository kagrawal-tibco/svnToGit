package com.tibco.cep.studio.dashboard.core.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.studio.dashboard.core.exception.SynValidationErrorMessage;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationInfoMessage;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationMessage;
import com.tibco.cep.studio.dashboard.core.model.ISynValidationProvider;
import com.tibco.cep.studio.dashboard.core.util.CurrentObjectCounter;

/**
 * <p><code>LocalParticle</code> represents the relationship between two <code>LocalElement</code>s.
 * It may represent:
 * <ol>
 * <li>containment relationship
 * <li>non-containment relationship
 * </ol>
 * <p><code>LocalParticle</code> may represent relationships of any multiplicity (1, 2, many).
 */
public class LocalParticle implements ISynValidationProvider {

    // TODO dchesney Jun 21, 2006 If a LocalElement is added and the name of the
    // element is changed problems occur (element can't be removed because it is
    // hashed under the original name)
    // TODO dchesney Jun 21, 2006 Adding elements with the same name is not
    // supported

    private String name;

    private String typeName;

    private String path;

    private long minOccurs = 1;

    private long maxOccurs = 1;

    // ERNIE
    // TODO: There is a need to implement sorting order of elements in LocalParticle.
    // e.g. Multiple panels may hold on components in different sequence.
    // MDS has no problem as the list of id(s) is stored in parent.
    // The long term solution is to have an indexed hashmap.
    // Work-around: Keep the existing behavior.
    //              Recreate the linked HashMap when there is a change of sequence. It may not happen so often.
    //              When there is a performance hit, will implement a better algorithm than recreating LinkedHashMap
    private Map<String, LocalElement> elements = new LinkedHashMap<String, LocalElement>();

    private SynValidationMessage validationMessage = null;

    private boolean isInitialized = false;

    private boolean isMDConfigType = false;

    private static Logger logger = Logger.getLogger(LocalParticle.class.getName());

    private LocalElement parent;

    private boolean isReference = false;

    /**
     *
     */
    public LocalParticle(String name) {
        super();
        setName(name);
    }

    public LocalParticle(String name, long minOccurs, long maxOccurs) {
        setName(name);
        setMinOccurs(minOccurs);
        setMaxOccurs(maxOccurs);
    }

    /**
     *
     */
    public LocalParticle(LocalElement parent, String name) {
        setName(name);
        setParent(parent);

    }

    public LocalParticle(LocalElement parent, String name, long minOccurs, long maxOccurs) {
        setName(name);
        setParent(parent);
        setMinOccurs(minOccurs);
        setMaxOccurs(maxOccurs);
    }

    /**
     * @inheritDoc
     */
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("LocalParticle");
    	sb.append("[name="+getName());
    	sb.append(",reference="+isReference());
    	sb.append(",initialized="+isInitialized);
    	sb.append(",elementCnt="+elements.size());
    	sb.append("]");
    	return sb.toString();
    }

    //======================================================================
    // Accessors
    //======================================================================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.equals("")) {
            logger.info("Particle name is empty");
        }
        this.name = name;
    }

    /**
     * @return <code>true</code> if this particle is initialized
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * @param isInitialized
     *            The isInitialized to set.
     */
    public void setInitialized(boolean isInitialized) {
        this.isInitialized = isInitialized;
    }

    /**
     * Returns all the elements (com.tibco.cep.designer.dashboard.core.model.impl.LocalElement)
     * in this particle, including those elements that are removed. The list is
     * sorted.
     *
     * @return <code>List</code> of <code>LocalElement</code>
     */
    public List<LocalElement> getElements() {
        return getElements(false, true);
    }

    /**
     * Returns all the elements (com.tibco.cep.designer.dashboard.core.model.impl.LocalElement)
     * in this particle. The list is sorted.
     *
     * @param activeOnly <code>true</code> if removed elements should NOT be
     * included in the return result
     * @return <code>List</code> of <code>LocalElement</code>
     * @see com.tibco.cep.designer.dashboard.core.model.impl.models.LocalElement
     */
    public List<LocalElement> getElements(boolean activeOnly) {
        return getElements(activeOnly, true);
    }

    /**
     * Returns all the elements (com.tibco.cep.designer.dashboard.core.model.impl..LocalElement)
     * in this particle.
     *
     * @param activeOnly <code>true</code> if removed elements should NOT be
     * included in the return result
     * @param sort <code>true</code> to sort the return result
     * @return <code>List</code> of <code>LocalElement</code>
     */
    public List<LocalElement> getElements(boolean activeOnly, boolean sort) {
        List<LocalElement> allElement = new ArrayList<LocalElement>(elements.values());
        if (false == activeOnly) {
            if (sort) {
                Collections.sort(allElement, new SortingOrderComparator());
            }
            return allElement;
        }
        List<LocalElement> filteredElements = new ArrayList<LocalElement>();
        for (Iterator<LocalElement> iter = allElement.iterator(); iter.hasNext();) {
            LocalElement element = iter.next();
            if (false == element.isRemoved()) {
                filteredElements.add(element);
            }
        }
        if (sort) {
            Collections.sort(filteredElements, new SortingOrderComparator());
        }
        return filteredElements;
    }

    /**
     * Swap the position so that firstElement will appear before second element in the final LinkedHashMap
     * It is single thread operation.
     *
     * @param element
     * @param sequence
     * @return
     */
    public boolean swapElements(LocalElement firstElement, LocalElement secondElement) {
        // do nothing if the key of element is not in LinkedHashMap.
        String firstKey = firstElement.getID();
        String secondKey = secondElement.getID();
        if ( (elements.containsKey(firstKey) == false) ||
             (elements.containsKey(secondKey) == false) ) {
            return false;
        }
        List<String> elementIDs = new LinkedList<String>(elements.keySet());
        int firstElementIdx = elementIDs.indexOf(firstElement.getID());
        int secondElementIdx = elementIDs.indexOf(secondElement.getID());
        Collections.swap(elementIDs, firstElementIdx, secondElementIdx);
        int sortOrderOffirstElement = firstElement.getSortingOrder();
        firstElement.setSortingOrder(secondElement.getSortingOrder());
        secondElement.setSortingOrder(sortOrderOffirstElement);
        LinkedHashMap<String, LocalElement> newElements = new LinkedHashMap<String, LocalElement>(elements.size());
        Iterator<String> elementIDsIterator = elementIDs.iterator();
        while (elementIDsIterator.hasNext()) {
            String elementID = elementIDsIterator.next();
            newElements.put(elementID,elements.get(elementID));
        }
        elements = newElements;
        getParent().setModified();
        return true;
    }

    /**
     * @deprecated
     */
    public void recreateElements() {
        // do nothing if the key of element is not in LinkedHashMap.
        LinkedHashMap<String, LocalElement> newElements = new LinkedHashMap<String, LocalElement>(elements.size());
        for ( Iterator<LocalElement> iter = elements.values().iterator(); iter.hasNext(); ) {
            // look for the second element.
            LocalElement nextElement = iter.next();
            newElements.put(nextElement.getID(),nextElement);
        }
        elements = newElements;
        this.setInitialized(true);
    }

    /**
     * Returns an element with the given name in this particle
     * @param elementFolder
     *
     * @return
     * @throws Exception
     * @see com.tibco.cep.designer.dashboard.core.model.impl.models.LocalElement
     */
    public LocalElement getElement(String elementName, String elementFolder) {
    	boolean folderNotApplicable = (elementFolder == LocalElement.FOLDER_NOT_APPLICABLE);
        for (Iterator<LocalElement> iter = elements.values().iterator(); iter.hasNext();) {
            LocalElement element = iter.next();
            if (folderNotApplicable || elementFolder.equals(element.getFolder())) {
                if (elementName.equals(element.getName())) {
                    return element;
                }
            }
        }
        return null;
    }

    /**
     * Returns an active element with the given name in this particle. If the
     * particle contains an deleted element with the same name then it is not returned
     * @param elementName
     * @param elementFolder
     * @return
     * @throws Exception
     * @since 3.0 bug 6617
     */
    public LocalElement getActiveElement(String elementName, String elementFolder) {
    	if (LocalElement.FOLDER_NOT_APPLICABLE.equals(elementFolder)){
            for (Iterator<LocalElement> iter = elements.values().iterator(); iter.hasNext();) {
                LocalElement element = iter.next();
                if (elementName.equals(element.getName())) {
                    if (element.isRemoved() == false) {
                        return element;
                    }
                }
            }
    	}
    	else {
            for (Iterator<LocalElement> iter = elements.values().iterator(); iter.hasNext();) {
                LocalElement element = iter.next();
                if (elementName.equals(element.getName()) && elementFolder.equals(element.getFolder())) {
                    if (element.isRemoved() == false) {
                        return element;
                    }
                }
            }
    	}
        return null;
    }

    /**
     * Returns an element with the given ID in this particle
     *
     * @return
     * @see com.tibco.cep.designer.dashboard.core.model.impl.models.LocalElement
     */
    public LocalElement getElementByID(String elementID) {
        return elements.get(elementID);
    }

    /**
     * Returns an element with the given index in this particle
     *
     * @return
     * @see com.tibco.cep.designer.dashboard.core.model.impl.models.LocalElement
     */
    public LocalElement getElement(boolean includeInactive, int elementIndex) {
        return (LocalElement) getElements(includeInactive).get(elementIndex);
    }

    /**
     * Add a LocalElement to this particle.
     *
     * @param localElement <code>LocalElement</code> to add
     * @return <code>true</code> if successful; <code>false</code> if the attempt has failed
     */
    public boolean addElement(LocalElement localElement) {
        String elementID = localElement.getID();
        if (true == elements.containsKey(elementID)) {
//            logger.info("The " + getName() + " [" + localElement.getName() + "/"+ elementID + "] already exists in this element and will not be overridden.");
//            return false;
        	//replace the existing element with the new one
        	LocalElement oldElement = elements.put(elementID, localElement);
        	if (isReference() == false) {
				localElement.setSortingOrder(oldElement.getSortingOrder());
				localElement.setParentParticle(this);
			}
        	return true;
        }
        if (false == isReference()) {
            localElement.setSortingOrder(elements.size());
            localElement.setParentParticle(this);
        }
        elements.put(elementID, localElement);
        CurrentObjectCounter.increment(getName());
        return true;
    }

    /**
     * Add a LocalElement to this particle.
     *
     * @param localElement <code>LocalElement</code> to add
     * @return <code>true</code> if successful; <code>false</code> if the attempt has failed
     */
    public boolean addElement(LocalElement localElement, int sortingOrder) {
        String elementID = localElement.getID();
        if (true == elements.containsKey(elementID)) {
//            logger.warning("The " + getName() + " [" + localElement.getName() + "/"+ elementID + "] already exists in this element and will not be overridden.");
//            return false;
        	//replace the existing element with the new one
        	elements.put(elementID, localElement);
        	localElement.setSortingOrder(sortingOrder);
        	if (isReference() == false) {
				localElement.setParentParticle(this);
			}
        	return true;
        }
        localElement.setSortingOrder(sortingOrder);
        if (false == isReference()) {
            localElement.setParentParticle(this);
        }
        elements.put(elementID, localElement);
        CurrentObjectCounter.increment(getName());
        return true;
    }

    /**
     * Removes a LocalElement with the given name from this particle
     *
     * @param localElement
     * @return true is successful; false if the attempt has failed
     */
    public boolean removeElementByName(String elementName) {
        /*
         * Here we must find the key that retrieves the correct localElement
         * then use that key to remove the element TODO: this implementation
         * assumes that all elements in the map have different name; there may
         * be some issue with working copies since they also have the same name
         * as the source
         */

        LocalElement element = null;
        String elementKey = null;
        for (Iterator<String> iter = elements.keySet().iterator(); iter.hasNext();) {
            elementKey = iter.next().toString();
            element = elements.get(elementKey);
            if (element.getName().equals(elementName)) {
                break;
            }
        }

        if (null != element) {
            elements.remove(elementKey);

            if (false == isReference()) {
                element.setParentParticle(null);
                element.setRemoved();
            } else {
                element.removeReferenceParentParticles(this);
            }

            CurrentObjectCounter.decrement(getName());
            return true;
        }

        setValidationMessage(new SynValidationInfoMessage("Element ["
                + elementName + "] does not exist in this collection", null));
        return false;
    }

    /**
     *
     * @param elementName
     * @param elementFolder
     * @return
     * @since 3.0 bug 6617
     */
    public boolean removeActiveElementByName(String elementName, String elementFolder) {

        LocalElement element = null;
        String elementKey = null;
        for (Iterator<String> iter = elements.keySet().iterator(); iter.hasNext();) {
            elementKey = iter.next().toString();
            element = elements.get(elementKey);
            if (element.getName().equals(elementName) && (LocalElement.FOLDER_NOT_APPLICABLE.equals(elementFolder) || elementFolder.equals(element.getFolder()))) {
                if (element.isRemoved() == false) {
                    break;
                }
            }
        }

        if (null != element) {
            elements.remove(elementKey);

            if (false == isReference()) {
                element.setParentParticle(null);
                element.setRemoved();
            } else {
                element.removeReferenceParentParticles(this);
            }

            CurrentObjectCounter.decrement(getName());
            return true;
        }

        setValidationMessage(new SynValidationInfoMessage("Element ["
                + elementName + "] does not exist in this collection", null));
        return false;
    }

    /**
     * Returns an element with the given ID in this particle
     *
     * @return
     * @see com.tibco.cep.designer.dashboard.core.model.impl.models.LocalElement
     */
    LocalElement removeElementByID(String elementID) {
        return elements.remove(elementID);
    }

    /**
     *
     * @param elementName
     * @return
     * @since R3.0
     */
    public void replaceElementByObject(String id, LocalElement localElement) {
    	if (localElement == null) {
    		elements.remove(id);
    	}
    	else {
	    	for (Map.Entry<String, LocalElement> entry : elements.entrySet()) {
				if (entry.getValue().getID().equals(id) == true){
					entry.setValue(localElement);
				}
			}
    	}
    	if (this.isReference == false){
	    	for (LocalElement element : elements.values()) {
				element.replaceElementByObject(id, localElement);
			}
    	}
    }

    /**
     * Purges an element given the element name. An element is purged only if it
     * has been removed
     * @param elementName
     * @return
     * @throws Exception
     */
    public boolean purgeElementByName(String elementName) {
        LocalElement element = null;
        String elementKey = null;
        for (Iterator<String> iter = elements.keySet().iterator(); iter.hasNext();) {
            elementKey = iter.next().toString();
            element = elements.get(elementKey);
            if (element.getName().equals(elementName)) {
                if (element.isRemoved() == true) {
                    break;
                }
            }
        }

        if (null != element) {
            elements.remove(elementKey);

            if (false == isReference()) {
                element.setParentParticle(null);
                element.setRemoved();
            } else {
                element.removeReferenceParentParticles(this);
            }

            CurrentObjectCounter.decrement(getName());
            return true;
        }

        setValidationMessage(new SynValidationInfoMessage("Element ["
                + elementName + "] does not exist in this collection", null));
        return false;
    }

    /**
     * Removes a LocalElement with the given ID from this particle
     *
     * @param localElement
     * @return true is successfull; false if the attempt has failed
     */
//    Temporarily comment out removeElementByID ( Ernie needs to implement logical delete of reference particle.)
//    public boolean removeElementByID(String elementID) {
//
//        if (elements.containsKey(elementID)) {
//            LocalElement element = (LocalElement) elements.get(elementID);
//            elements.remove(elementID);
//            element.setParentParticle(null);
//            //            fireElementRemoved(this, element);
//            CurrentObjectCounter.decrement(getName());
//
//            return true;
//        }
//
//        setValidationMessage(new SynValidationInfoMessage("Element [" + elementID + "] does not exist in this collection", null));
//        return false;
//    }

    /**
     * Convenience method equivalent to calling {@link #removeAll(boolean)}
     * passing <code>true</code>.
     */
    public boolean removeAll() {
        return removeAll(true);
    }

    public boolean removeAll(boolean resetInitFlag) {
        elements.clear();
        if (true == resetInitFlag) {
            setInitialized(false);
        }
        CurrentObjectCounter.resetCounter(getName());
        return true;
    }

    public int indexOf(LocalElement localElement) {
        // Bug#7070 - Moving fields...
        return getElements(true, true).indexOf(localElement);
    }

    /**
     * Returns the count of elements (both active and inactive) in this particle
     *
     * @return
     */
    public int getElementCount() {
        return elements.size();
    }

    /**
     * Returns the count of active elements in this particle
     *
     * @return
     */
    public int getActiveElementCount() {
        return this.getElements(true).size();
    }

    public Object clone() {
        LocalParticle pClone = new LocalParticle(getName(), getMinOccurs(), getMaxOccurs());
        pClone.setReference(isReference());
        try {
            if (false == isReference()) {
                // containment reference
            	//FIXME fixed by Anand to include only active elements in cloning
                for (Iterator<LocalElement> iter = getElements(true, false).iterator(); iter.hasNext();) {
                    LocalElement localElement = (LocalElement) iter.next();
                    for (Iterator<String> iterator = localElement.getParticleNames(true).iterator(); iterator.hasNext();) {
                        String particleName = (String) iterator.next();
                        localElement.getChildren(particleName);
                    }
                    pClone.addElement((LocalElement) localElement.clone());
                }
            } else {
                // non-containment reference
            	//FIXME fixed by Anand to include only active elements in cloning
                for (Iterator<LocalElement> iter = getElements(true, false).iterator(); iter.hasNext();) {
                    LocalElement localElement = (LocalElement) iter.next();

                    // Fix for Bug# 4993 - Hack to Fix the Cloning of Process Model Component
                    // Remove the loading of children before adding it to the cloned particle
//                for (Iterator iterator = localElement.getParticleNames(true).iterator(); iterator.hasNext();) {
//                        String particleName = (String) iterator.next();
//                        localElement.getChildren(particleName);
//                    }
                    pClone.addElement(localElement);
                }
            }
            pClone.setInitialized(isInitialized());
// TODO: the original version does not clone parent. Ask Khai
//            pClone.setParent(parent);
            pClone.setPath(path);
            pClone.setTypeName(typeName);
            pClone.setMDConfigType(isMDConfigType);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return pClone;
    }

    public LocalParticle getWorkingCopy(LocalElement parent) {
    	throw new UnsupportedOperationException("getWorkingCopy");
    }

    /**
     * Particles are equal if they have the same name. Note: Particles may share
     * names with other particles in different parents Example: Element A and
     * Element B may both have particles named "Field", in this case they are
     * locally equal because of the name but are not globally equal because they
     * are in different parents.
     *
     * This implementation is purposefully this loose to allow flexibility in
     * the use of particle comparison.
     */
    public boolean equals(Object obj) {
        if (obj instanceof LocalParticle) {
            LocalParticle candidate = (LocalParticle) obj;

            if (true == candidate.getName().equals(getName())) {

                LocalElement candidateParent = candidate.getParent();
                try {
                    return candidateParent.getFullPath().equals(getParent().getFullPath());
                } catch (Exception e) {
                    //RAJESH JUST silently drop it
                }
                return true;
            }
        }
        return false;
    }

    //======================================================================
    // Occurence constaints
    //======================================================================

    public long getMaxOccurs() {
        return maxOccurs;
    }

    public void setMaxOccurs(long maxOccurs) {
        this.maxOccurs = maxOccurs;
    }

    public long getMinOccurs() {
        return minOccurs;
    }

    public void setMinOccurs(long minOccurs) {
        this.minOccurs = minOccurs;
    }

    //======================================================================
    // Validation
    //======================================================================

    /**
     * Validate constraints
     *
     * @return
     */
    public boolean isValid() throws Exception {
        boolean isForcefulValidation = false;
        return isValid(isForcefulValidation);
    }

    /**
     * Validate constraints
     *
     * @return
     */
    public boolean isValid(boolean isForcefulValidation) throws Exception {
        setValidationMessage(null);

        /*
         * Validate constraints
         */

        if ((getElementCount()==0 || getActiveElementCount()==0) && getMinOccurs() == 1 && getMaxOccurs() == 1) {
            setValidationMessage(new SynValidationErrorMessage("A " + getName() + " must be specified."));
            return false;
        }

        if ((getElementCount() < getMinOccurs()) || (getActiveElementCount() < getMinOccurs())) {
            setValidationMessage(new SynValidationErrorMessage("Atleast [" + getMinOccurs() + "] " + getName() + "(s) must be declared."));
            return false;
        }

        // ERNIE: Now we have cardinality 2, and the checking needs to be on active element count.
        if (getMaxOccurs() > -1 && getActiveElementCount() > getMaxOccurs()) {
            setValidationMessage(new SynValidationErrorMessage("The element count of [" + getElementCount() + "] exceeds the maximum allowed ["
                    + getMaxOccurs() + "] for "+getName()));
            return false;
        }

        ArrayList<String> names = new ArrayList<String>(getActiveElementCount());
        for (LocalElement element : getElements(true)) {
			if (names.contains(element.getScopeName()) == true){
				// To display folder name, if the folder name is blank then get its parent folder name
				String folderName = ("".equals(element.getFolder())) ? element.getParent().getFolder() : element.getFolder();
				if (element.getParent() != null) {
					if (element.getParent().getParent() == null) {
						setValidationMessage(new SynValidationErrorMessage("More then one " + getName() + " was found with the same name \"" + element.getName() + "\" in same folder \"" + folderName + "\""));
					}
					else {
						setValidationMessage(new SynValidationErrorMessage("More then one " + getName() + " was found with the same name \"" + element.getName() + "\" in \"" + element.getParent().getName() + "\""));
					}
				}
				else {
					setValidationMessage(new SynValidationErrorMessage("More then one " + getName() + " was found with the same name \"" + element.getName()));
				}
				return false;
			}
			names.add(element.getScopeName());
		}

        /*
         * Delegates to the children to return their validation status also
         */
        if (isForcefulValidation == true || isReference() == false ) {
            // For removed elements, we should ignore them.
            for (Iterator<LocalElement> iter = getElements(true).iterator(); iter.hasNext();) {
                LocalElement element = (LocalElement) iter.next();
                if (false == element.isValid()) {
                	SynValidationMessage elementValidationMessage = element.getValidationMessage();
                    if (null == elementValidationMessage) {
                    	elementValidationMessage = new SynValidationErrorMessage(this.getName() + " by name [" + element.getName() + "] has failed validation");
                    }
                    else {
                    	SynValidationMessage valMessage = getValidationMessage();
                    	if (null == valMessage) {
                    		valMessage = new SynValidationErrorMessage("One or more "+this.getName()+" have failed validation");
                    		setValidationMessage(valMessage);
                    	}
                    	valMessage.addSubMessage(elementValidationMessage);
                    }
                }
            }
        }
        return (null == getValidationMessage());
    }

    /**
     * For a particle the validation is on whether the given object is of the
     * proper type and is within the occurence constraints of this particle
     */
    public boolean isValid(Object value) throws Exception {

        if (false == isValid()) {
            return false;
        }

        if (0 == getElementCount()) {
            return true;
        }

        Object localElement = getElements().get(0);
        if (null != localElement && false == value.getClass().isAssignableFrom(localElement.getClass())) {
            setValidationMessage(new SynValidationInfoMessage("The element type [" + value.getClass().getName()
                    + "] is not compatible with the type of existing elements [" + localElement.getClass().getName() + "] in this particle."));
            return false;
        }

        /*
         * Validate whether adding this object will violate the maxOccurs
         * constraint
         */

        if (getElementCount() + 1 > getMaxOccurs()) {
            setValidationMessage(new SynValidationInfoMessage("Adding this this element will exceeds the maximum allowed [" + getMaxOccurs()
                    + "] for this type of element."));
            return false;
        }
        return true;
    }

    public SynValidationMessage getValidationMessage() throws Exception {
        return validationMessage;
    }

    public void setValidationMessage(SynValidationMessage validationMessage) {
        this.validationMessage = validationMessage;
    }

    public LocalElement getParent() {
        return parent;
    }

    public void setParent(LocalElement parent) {
        this.parent = parent;
    }

    public boolean isReference() {
        return isReference;
    }

    public void setReference(boolean isReference) {
        this.isReference = isReference;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isMDConfigType() {
        return this.isMDConfigType;
    }

    public void setMDConfigType(boolean flag) {
        this.isMDConfigType = flag;
    }
}
