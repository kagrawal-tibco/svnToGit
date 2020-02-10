package com.tibco.cep.designtime.model.dotnetsupport;


import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.ConceptChangeListener;
import com.tibco.cep.designtime.model.element.mutable.MutableConcept;
import com.tibco.cep.designtime.model.element.mutable.MutablePropertyDefinition;


/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Mar 6, 2006
 * Time: 3:20:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class NativeConceptChangeListenerProxy extends AbstractNativeChangeListenerProxy implements ConceptChangeListener {


    public NativeConceptChangeListenerProxy(long gcHandle) {
        super(gcHandle);
    }


    public void pathChanged(MutableConcept concept, String oldPath, String newPath) {
        pathChanged(gcHandle, concept, oldPath, newPath);
    }


    native private static void pathChanged(long gcHandle, Concept concept, String oldPath, String newPath);


    public void propertyCardinalityChanged(MutableConcept concept, String definitionName, int min, int max) {
        propertyCardinalityChanged(gcHandle, concept, definitionName, min, max);
    }


    native private static void propertyCardinalityChanged(long gcHandle, Concept concept, String definitionName, int min, int max);


    public void propertyTypeChanged(MutableConcept concept, int typeFlag, Concept conceptType) {
        propertyTypeChanged(gcHandle, concept, typeFlag, conceptType);
    }


    native private static void propertyTypeChanged(long gcHandle, Concept concept, int typeFlag, Concept conceptType);


    public void propertyDefinitionAdded(MutableConcept concept, MutablePropertyDefinition definition) {
        propertyDefinitionAdded(gcHandle, concept, definition);
    }


    native private static void propertyDefinitionAdded(long gcHandle, Concept concept, MutablePropertyDefinition definition);


    public void propertyDefinitionRemoved(MutableConcept concept, MutablePropertyDefinition definition) {
        propertyDefinitionRemoved(gcHandle, concept, definition);
    }


    native private static void propertyDefinitionRemoved(long gcHandle, Concept concept, MutablePropertyDefinition definition);


    public void inheritanceChanged(MutableConcept concept, MutableConcept oldSuper, MutableConcept newSuper) {
        inheritanceChanged(gcHandle, concept, oldSuper, newSuper);
    }


    native private static void inheritanceChanged(long gcHandle, Concept concept, Concept oldSuper, Concept newSuper);
}
