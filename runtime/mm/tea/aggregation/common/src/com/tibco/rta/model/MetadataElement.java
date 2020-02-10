package com.tibco.rta.model;

import java.io.Serializable;
import java.util.Collection;

import com.tibco.rta.model.serialize.ModelSerializer;
import com.tibco.rta.model.serialize.SerializationTarget;


/**
 * An abstraction of a model element. All model interfaces inherit this.
 */
public interface MetadataElement extends Serializable {
	

    /**
     * Get the owner schema associated with this model element.
     * A schema is an owner of itself.
     * @return the owner schema for this model element.
     */
	RtaSchema getOwnerSchema();
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();
	
	/**
	 * Gets the display name.
	 *
	 * @return the display name
	 */
	String getDisplayName();
	
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	String getDescription();
	
    /**
     * Serialize model to a format specified by the serializer
     * @param <T> - Bounded Type indicating anything that implements this interface.
     * @param serializer
     */
    <T extends SerializationTarget, S extends ModelSerializer<T>> void serialize(S serializer);
    
    
    /**
     * A metadata element can have some well-defined properties that can be used by runtime in a certain way.
     * 
     * For example, to provide a hint to the runtime to handle it in a certain manner.
     * @param name
     * @return
     */
    String getProperty(String name);
    
    Collection<String> getPropertyNames();

}