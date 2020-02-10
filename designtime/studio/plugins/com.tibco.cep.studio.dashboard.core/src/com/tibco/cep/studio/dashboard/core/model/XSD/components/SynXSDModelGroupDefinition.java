package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAnnotation;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDModelGroup;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDModelGroupDefinition;

/**
 * @
 *
 */
public class SynXSDModelGroupDefinition extends SynXSDSchemaElement implements ISynXSDModelGroupDefinition {

    private ISynXSDAnnotation annotation;

    private ISynXSDModelGroup modelGroup;

    /**
     * @return Returns the modelGroup.
     */
    public ISynXSDModelGroup getModelGroup() {
        return modelGroup;
    }

    /**
     * @param modelGroup The modelGroup to set.
     */
    public void setModelGroup(ISynXSDModelGroup modelGroup) {
        this.modelGroup = modelGroup;
    }

    public Object clone() throws CloneNotSupportedException {
        SynXSDModelGroupDefinition mgdClone = new SynXSDModelGroupDefinition();
//        mgdClone.setName(getName());
//        mgdClone.setModelGroup((ISynXSDModelGroup)getModelGroup().clone());
        return mgdClone;
    }

    /**
     * @return Returns the annotation.
     */
    public ISynXSDAnnotation getAnnotation() {
        return annotation;
    }

    /**
     * @param anotation The annotation to set.
     */
    public void setAnnotation(ISynXSDAnnotation annotation) {
        this.annotation = annotation;
    }

	public Object cloneThis() throws Exception {
		SynXSDModelGroupDefinition clone = new SynXSDModelGroupDefinition();
    	this.cloneThis(clone);
    	return clone;
    }

	public void cloneThis(SynXSDModelGroupDefinition clone) throws Exception {
    	super.cloneThis(clone);
    	if ( this.annotation == null ) {
    		clone.annotation = null;
    	}
    	else {
    		clone.annotation = (ISynXSDAnnotation ) this.annotation.cloneThis();
    	}
    	if ( this.modelGroup == null ) {
    		clone.modelGroup = null;
    	}
    	else {
    		clone.modelGroup = (ISynXSDModelGroup) this.modelGroup.cloneThis();
    	}
    }

}
