package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.providers;

/**
 * @
 *
 */
public interface ISynXSDValueConstraintProvider {
    /**
     * If getDefault() and getAttributeUse() are both present, then getAttributeUse()
     * must correspond to SynXSDAttributeUse.usageOptional
     * 
     * If getFixed() is present then getDefault() must be null.
     * 
     * @return Object The default value for this attribute.  The default value
     *         must conform to the type definition returned by getTypeDefinition()
     */
    public abstract Object getDefault();
    public abstract void setDefault(Object defaultValue);
    
    
    /**
     * 
     * If getDefault() is present then getFixed() must be null.
     * 
     * @return Object The fixed value; if specified  The fixed value
     *         must conform to the type definition returned by getTypeDefinition()
     */
    public abstract Object getFixed();
    public abstract void setFixed(Object fixed);
}
