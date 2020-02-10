package com.tibco.cep.studio.dbconcept.conceptgen;

/**
 * 
 * @author bgokhale
 * An interface that describes a property of a BaseEntity. 
 * SIGEntity has a list of these properties
 * 
 */
public interface BaseProperty {
	
	/**
	 * Data-type of properties
	 */
	public static final int STRING = 0;
	public static final int INTEGER = 1;
	public static final int LONG = 2;
	public static final int DOUBLE = 3;
	public static final int DATE = 4;
	public static final int DATETIME = 5;
	public static final int CHAR = 6;
	public static final int BOOLEAN = 7;

	/**
	 * 
	 * @return Name of the property
	 */
    public String getName();

    /**
     * 
     * @return Data type of the property as defined by above values
     */
    public int getType();

    /**
     * 
     * @return Precision to which this property holds decimal values
     */
    public int getPrecision();
    
    /**
     * 
     * @return Maximum length of this property 
     */

    public int getLength();

    /**
     * 
     * @return The default value of this property if any
     */
    public Object getDefaultValue(); 

    /**
     * 
     * @return A name alias for this property
     */
    public String getAlias();
    
    /**
     * 
     * @param dictionary Name of a dictionary from which alias has to be retrieved.
     * @return Corresponding name
     */
    public String getAlias(String dictionary);

    /**
     * 
     * @return A boolean to indicate if this property can have null values
     */
    public boolean isNullable();
    
    /**
     * 
     * @return A boolean to indicate whether multiple values should be allowed for that property
     */
    public boolean isArray();
    
    /**
     * 
     * @return int position at which this property appears in BaseEntity
     */
    public int getPosition();

}

