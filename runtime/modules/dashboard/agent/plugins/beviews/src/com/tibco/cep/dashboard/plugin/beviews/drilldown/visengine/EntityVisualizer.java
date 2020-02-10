package com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine;

import java.util.Map;

public interface EntityVisualizer {

	/**
	 * Returns the type id of the entity
	 * @return
	 */
	public String getTypeId();

	/**
	 * Returns the display name of the entity
	 * @return
	 */
	public String getName();

	/**
	 * Returns the fields which are to be shown. Note that the fields shown
	 * to the user can be less then the actual entity. Also the sequence
	 * could be different
	 * @return a {@link Map} where the key is the name of the field and value is the display name of the field
	 */
	public Map<String,String> getDisplayableFields();

	/**
	 * Returns the display name of a field
	 * @param fieldName The name of the field
	 * @return the display name if the field is to be shown else <code>null</code>
	 */
	public String getDisplayableName(String fieldName);

	/**
	 * Returns the name of a field
	 * @param fieldDisplayName The display name of the field
	 * @return The name of the field
	 */
	public String getName(String fieldDisplayName);

}
