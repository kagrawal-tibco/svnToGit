package com.tibco.cep.studio.dbconcept.conceptgen;

/**
 * 
 * @author bgokhale
 * An interface that describes database columns
 */
public interface DBProperty extends BaseProperty {

	/**
	 * 
	 * @return Name of the database table/view
	 */
    public String getColumnName();
    
    /**
     * 
     * @return Data type of the column
     */
    public String getColumnType();

    /**
     * 
     * @return boolean indicating if this is a primary key
     */
    public boolean isPK();

    /**
     * 
     * @return boolean indicating if this is a foreign key
     */
    public boolean isFK();

    /**
     * 
     * @return Name of a database sequence from which this property gets its value.
     */
    public String getSequence();

}

