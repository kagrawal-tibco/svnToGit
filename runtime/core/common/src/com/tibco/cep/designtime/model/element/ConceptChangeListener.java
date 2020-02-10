package com.tibco.cep.designtime.model.element;

import com.tibco.cep.designtime.model.element.mutable.MutableConcept;
import com.tibco.cep.designtime.model.element.mutable.MutablePropertyDefinition;


/**
 * Listens to changes on a Concept.
 *
 * @author ishaan
 * @version Apr 12, 2004 4:51:52 PM
 */
public interface ConceptChangeListener {


    public void pathChanged(MutableConcept concept, String oldPath, String newPath);


    public void propertyCardinalityChanged(MutableConcept concept, String definitionName, int min, int max);


    public void propertyTypeChanged(MutableConcept concept, int typeFlag, Concept conceptType);


    public void propertyDefinitionAdded(MutableConcept concept, MutablePropertyDefinition definition);


    public void propertyDefinitionRemoved(MutableConcept concept, MutablePropertyDefinition definition);


    public void inheritanceChanged(MutableConcept concept, MutableConcept oldSuper, MutableConcept newSuper);
}
