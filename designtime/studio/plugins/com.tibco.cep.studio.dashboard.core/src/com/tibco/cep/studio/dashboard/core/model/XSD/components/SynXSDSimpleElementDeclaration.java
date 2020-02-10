package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import java.util.EventListener;
import java.util.List;

import com.tibco.cep.studio.dashboard.core.listeners.ISynPropertyChangeNotifier;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeDeclaration;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeGroupDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeUse;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSimpleTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDTypeDefinition;

/**
 * Simple elements can only be of a simple type and cannot contain attributes or
 * other elements
 *
 */
public class SynXSDSimpleElementDeclaration extends SynXSDElementDeclaration {

    private static final long serialVersionUID = -2940985574176463034L;

	public SynXSDSimpleElementDeclaration(String name) {
        super(name);
    }

    /**
     * @param name
     */
    public SynXSDSimpleElementDeclaration(String name, ISynXSDSimpleTypeDefinition simpleType) {
        super(name);
        setTypeDefinition(simpleType);
    }

    public Object cloneThis() throws Exception {
    	SynXSDSimpleElementDeclaration clone = new SynXSDSimpleElementDeclaration(this.getName());
    	super.cloneThis(clone);
    	return clone;
    }

    public void setTypeDefinition(ISynXSDTypeDefinition typeDefinition) {
        if (false == (typeDefinition instanceof ISynXSDSimpleTypeDefinition)) {
            throw new UnsupportedOperationException("Invalid use of a complex type [" + typeDefinition.getName()
                    + "] for a simple element.");
        }
        super.setTypeDefinition(typeDefinition);
    }

    //===============================================================
    // Delegation to attribute provider
    //===============================================================

    public void addProperty(ISynXSDAttributeDeclaration attribute) {
    }

    public void addAttributeGroup(ISynXSDAttributeGroupDefinition attributeGroup) {
    }

    public void addPropertyUse(ISynXSDAttributeUse attributeUse) {
    }

    public ISynXSDAttributeDeclaration getProperty(String attributeName) {
        return null;
    }

    public ISynXSDAttributeGroupDefinition getAttributeGroup(String attributeGroupName) {
        return null;
    }

    public List<ISynXSDAttributeGroupDefinition> getAttributeGroups() {
        return null;
    }

    public List<ISynXSDAttributeDeclaration> getProperties() {
        return null;
    }

    public List<ISynXSDAttributeUse> getPropertyUses() {
        return null;
    }

    public void removeProperty(String attributeName) {
    }

    public void removeAttributeGroup(String attributeGroupName) {
    }

    public void removePropertyUse(ISynXSDAttributeUse attributeUse) {
    }

    //===============================================================
    // Delegation to property change notifier
    //===============================================================

    public void addListener(EventListener listener) {
    }

    public List<ISynPropertyChangeNotifier> getListeners() {
        return null;
    }

    public void firePropertyChanged(ISynPropertyChangeNotifier prop, Object oldValue, Object newValue) {
    }

    public void removeListener(EventListener listener) {
    }

}
