package com.tibco.cep.studio.dashboard.core.model.impl.classifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalEntity;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;

/**
 * @
 *
 */
public abstract class LocalClassifier extends LocalEntity {

    public static final String ELEMENT_KEY_FIELD = PROP_KEY_PREFIX + "Field";

    /**
     *
     */
    public LocalClassifier() {
        super();
    }

    /**
     * @param parentElement
     */
    public LocalClassifier(LocalElement parentElement, String name) {
        super(parentElement,name);
    }

    /**
     * @param parentElement
     * @param mdElement
     */
    public LocalClassifier(LocalElement parentElement, Entity mdElement) {
        super(parentElement, mdElement);

    }

    public void setupProperties() {
        setDefaultChildType(ELEMENT_KEY_FIELD);
        addParticle(new LocalParticle(ELEMENT_KEY_FIELD, 0, -1));
    }

    /**
     * @deprecated
     * @param localField
     * @return
     */
    protected boolean isPredefined(LocalElement localField) {
		return false;
	}

	//==================================================================
    // The following methods are convenience API's that delegates
    // to the reflection style API's for accessing attribute values
    //==================================================================

    /**
     * Add a Field and automatically assign it the next available position
     * (currently the position would be last-in-line)
     *
     * @param localAttribute
     * @throws Exception
     */
    public void addField(LocalElement localAttribute) {
//        localAttribute.setSortingOrder(getParticle(ELEMENT_KEY_FIELD).getElementCount());
        addElement(ELEMENT_KEY_FIELD, localAttribute);

    }

    /**
     * Add a field and give it the specified position while incrementing all
     * existing fields to compensate.
     *
     * @param localAttribute
     * @param position
     * @throws Exception
     */
    public void addField(LocalElement localAttribute, int position) {
        /*
         * Increment any existing element with position >= specified position
         */
        for (Iterator<LocalElement> iter = getChildren(ELEMENT_KEY_FIELD).iterator(); iter.hasNext();) {
            LocalElement element = (LocalElement) iter.next();

            if (element.getSortingOrder() >= position) {

                element.setSortingOrder(element.getSortingOrder() + 1);

            }
        }
        /*
         * Then add the element
         */
        localAttribute.setSortingOrder(position);
        addElement(ELEMENT_KEY_FIELD, localAttribute);
    }

    /**
     *
     * @return a <code>List</code> of <code>LocalAttribute</code>
     * @throws Exception
     */
    public List<LocalElement> getFields() {
        return getFields(false);
    }

    public List<LocalElement> getFields(boolean includeSystemFields) {
    	if (includeSystemFields == true){
    		return getChildren(ELEMENT_KEY_FIELD);
    	}
        LinkedList<LocalElement> children = new LinkedList<LocalElement>(getChildren(ELEMENT_KEY_FIELD));
        ListIterator<LocalElement> listIterator = children.listIterator();
        while (listIterator.hasNext()) {
			LocalAttribute field = (LocalAttribute) listIterator.next();
			if (field.isSystem() == true){
				listIterator.remove();
			}
		}
		return children;
    }

    public LocalAttribute getFieldByName(String name) {
        return (LocalAttribute) getElement(ELEMENT_KEY_FIELD, name, LocalElement.FOLDER_NOT_APPLICABLE);
    }

    public void removeField(LocalElement localAttribute) {
        removeElement(ELEMENT_KEY_FIELD, localAttribute.getName(), LocalElement.FOLDER_NOT_APPLICABLE);
    }

    public String getNewFieldName(String nameSeed) {
        return getNewName(ELEMENT_KEY_FIELD, nameSeed);
    }

    public boolean isNameUnique(String name) {
        return super.isNameUnique(ELEMENT_KEY_FIELD, name);
    }

    public LocalElement getField(long position) {
        return getField(position, false);
    }

    public LocalElement getField(long position, boolean useProximity) {
        return getAttribute(getChildren(ELEMENT_KEY_FIELD), position, useProximity);
    }

	protected LocalElement getAttribute(List<LocalElement> children, long position, boolean useProximity) {
		long nearestPosition = children.size() - 1;

        for (Iterator<LocalElement> iter = children.iterator(); iter.hasNext();) {
            LocalElement element = iter.next();

            long pos = element.getSortingOrder();

            /*
             * If the element is found then return it
             */
            if (pos == position) {

                return element;
            }

            /*
             * if proximity is requested then track the closest neighbor
             */
            else if (true == useProximity && pos > position) {

                if (pos < nearestPosition) {
                    nearestPosition = pos;
                }
            }
        }

        if (nearestPosition != children.size() - 1) {
            return getAttribute(children,nearestPosition,false);
        }

        return null;
	}

    protected void synchronizeChildrenOrder(List<LocalElement> children,List<PropertyDefinition> persistedChildren) {
	    //super.synchronizeChildren(parent);
	    final List<String> childrenNames = new ArrayList<String>(children.size());
	    for (LocalElement child : children) {
            childrenNames.add(child.getName());
        }
		List<PropertyDefinition> sortablePersistedChildren = new ArrayList<PropertyDefinition>(persistedChildren);
	    Collections.sort(sortablePersistedChildren, new Comparator<PropertyDefinition>(){

			public int compare(PropertyDefinition child0, PropertyDefinition child1) {
                int idx0 = childrenNames.indexOf(child0.getName());
                int idx1 = childrenNames.indexOf(child1.getName());
                return idx0-idx1;
            }

	    });
	    persistedChildren.clear();
	    for (PropertyDefinition persistedChild : sortablePersistedChildren) {
	    	persistedChildren.add(persistedChild);
        }
    }

}