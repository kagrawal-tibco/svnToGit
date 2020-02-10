package com.tibco.rta.model.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_ATTR_REF_NAME;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.Dimension;

/**
 * Default impl for interface {@link Dimension}
 */

@XmlAccessorType(XmlAccessType.NONE)
public class DimensionImpl extends MetadataElementImpl implements Dimension {
	
	private static final long serialVersionUID = -2335103936030043999L;
	
	protected Attribute associatedAttribute;	
	
	protected String associatedAssetName;
		
	@XmlAttribute(name = ATTR_ATTR_REF_NAME)
	private String associatedAttributeName;

   /**
     *
     * @param associatedAttribute
     */
    public DimensionImpl(Attribute associatedAttribute) {
		super();
        this.associatedAttribute = associatedAttribute;
        associatedAttributeName = associatedAttribute.getName();
	}

   /**
     *
     * @param name - A name possibly different from the name of the attribute
     * @param associatedAttribute
     */
    public DimensionImpl(String name, Attribute associatedAttribute) {
		super(name, associatedAttribute.getOwnerSchema());
        this.associatedAttribute = associatedAttribute;
        associatedAttributeName = associatedAttribute.getName();
	}
    
    protected DimensionImpl() {    	
    }

    
    @Override
    public Attribute getAssociatedAttribute() {
        return associatedAttribute;
    }
    
    public String getAssociatedAttributeName() {
    	return associatedAttributeName;
    }
    

    @Override
    public String toString() {
        if (name != null) {
            return name;
        } else {
            return associatedAttribute.getName();
        }
    }

	@Override
	public String getAssociatedAssetName() {
		return associatedAssetName;
	}
	
	public void setAssociatedAssetName(String associatedAssetName) {
		this.associatedAssetName = associatedAssetName;
	}

}