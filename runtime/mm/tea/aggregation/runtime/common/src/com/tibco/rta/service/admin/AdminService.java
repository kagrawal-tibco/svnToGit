package com.tibco.rta.service.admin;

/**
 * @author bgokhale
 * 
 * AdminService handles admin tasks off the transport layer
 * 
 */

import com.tibco.rta.common.service.ModelChangeListener;
import com.tibco.rta.common.service.StartStopService;
import com.tibco.rta.model.RtaSchema;

import java.util.Collection;

public interface AdminService extends StartStopService {

   /**
     * Remote method to be called by client to create/update a schema and save it.
     * @param schema
     */
	void saveSchema (RtaSchema schema) throws Exception;

   /**
     * Remote method to be called by client to load a schema based on its name.
     * @param schemaName
     * @return
     */
	RtaSchema loadSchema (String schemaName) throws Exception;
	
	Collection<RtaSchema> getAllSchemas (String username);
	
	Collection<RtaSchema> getAllSchemas ();

    /**
     *
     * @param schemaName
     * @return
     * @throws Exception
     */
    RtaSchema getSchema (String schemaName) throws Exception;

    /**
     * Remote method to be called by client to delete an existing schema.
     * @param schemaName The schema name to remove.
     * @throws Exception
     */
    void removeSchema (String schemaName) throws Exception;

	public abstract void removeModelChangeListener(ModelChangeListener modelChangeListener);

	public abstract void addModelChangeListener(ModelChangeListener modelChangeListener);

    public String getCurrentActivationStatus();

    public boolean getHierarchyStatus(String schemaName,
                                      String cubeName,
                                      String hierarchyName);

    public void changeHierarchyStatus(String schemaName,
                                      String cubeName,
                                      String hierarchyName,
                                      boolean enabled);

}
