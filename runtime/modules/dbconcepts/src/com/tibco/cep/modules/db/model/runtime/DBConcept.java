package com.tibco.cep.modules.db.model.runtime;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 5, 2007
 * Time: 7:00:54 PM
 * To change this template use File | Settings | File Templates.
 */
import java.sql.ResultSet;
import java.util.Map;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Jul 28, 2005
 * Time: 4:48:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DBConcept extends Concept {

    /**
     * Inserts the concept to the database table.
     */
    public void insert(String... primaryKeys) throws Exception;

    /**
     * Updates the concept to the database table.
     */
    public void update() throws Exception;

    /**
     * Deletes the concept from the database table.
     */
    public void remove() throws Exception;

    public String getTableName();

    public Map getPrimaryKeyMap();
    
    public String[] getPrimaryKeyNames();

    public void setProperties(ResultSet set) throws java.sql.SQLException;
    
    public Property getPropertyNullOK (String name);
    
    public String[] getExtIdPropertyNames();
    
    public String getExtIdPrefix();
    
    public String getVersionPropertyName();
    
    public String getVersionPolicy();
    
    public String getDBColumnDataType(String propName);

}
