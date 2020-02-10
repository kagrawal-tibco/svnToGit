package com.tibco.rta.model.mutable;

import com.tibco.rta.model.MetadataElement;


/**
 * An abstraction of a model element. All model interfaces inherit this.
 */
public interface MutableMetadataElement extends MetadataElement {
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	void setName (String name);
	
	/**
	 * Sets the display name.
	 *
	 * @param name the new display name
	 */
	void setDisplayName (String displayName);

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	void setDescription(String description);

	/**
	 * Set a property for this metadataelement.  An extension mechanism to add loose properties to
	 * the model that can be interpreted in a certain way by the server.
	 * 
	 * @param name Name of the property to add.
	 * @param value the associated value.
	 */
	void setProperty (String name, String value);
}