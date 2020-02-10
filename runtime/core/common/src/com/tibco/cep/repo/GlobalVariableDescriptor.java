package com.tibco.cep.repo;


/**
 * Represents a global variable.
 * See the TIBCO Designer and TIBCO Administrator documentations for details about global variables.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public class GlobalVariableDescriptor {

	public static final String FIELD_NAME = "name";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_ISDEPLOY = "deploymentSettable";
	public static final String FIELD_ISSERVICE = "serviceSettable";
	public static final String FIELD_DESCRIPTION = "description";
	public static final String FIELD_CONSTRAINT = "constraint";
	public static final String FIELD_MODTIME = "modTime";
	
	private long modTime;
    private String name;
    private String path;
    private String type;
    private String value;
    private boolean deploymentSettable;
    private boolean serviceSettable;
    private String description;
    private String constraint;
    private String projectSource;
	private boolean overridden;
    
    public GlobalVariableDescriptor(String name, String path, String value, String type, boolean deploymentSettable,
            boolean serviceSettable, long modTime, String description, String constraint,String projectSource) {
    	this(name,path,value,type,deploymentSettable,serviceSettable,modTime,description,constraint);
    	this.projectSource = projectSource;
    }

    public GlobalVariableDescriptor(String name, String path, String value, String type, boolean deploymentSettable,
                                    boolean serviceSettable, long modTime, String description, String constraint) {
        this.name = name;
        this.path = path;
        this.value = value;
        this.type = type;
        this.deploymentSettable = deploymentSettable;
        this.serviceSettable = serviceSettable;
        this.modTime = modTime;
        this.description = description;
        this.constraint = constraint;
    }

    public GlobalVariableDescriptor() {
    }

    /**
     * Gets the time this global variable was last modified.
     *
     * @return a <code>long</code>.
     * @.category not-public-api
     * @since 2.0.0
     */
    public long getModificationTime() {
        return modTime;
    }


    public String getConstraint() {
        return this.constraint;
    }

    /**
     * Gets the description of this global variable.
     * @return a String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the full name of this global variable, including all folders.
     *
     * @return a <code>String</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public String getFullName() {
        return this.path + this.name;
    }


    /**
     * Gets the name of this global variable.
     *
     * @return a <code>String</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public String getName() {
        return name;
    }


    /**
     * Gets the path of the folder that contains this global variable.
     *
     * @return a <code>String</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public String getPath() {
        return path;
    }


    /**
     * Gets the type of the value of this global variable.
     *
     * @return the <code>String</code> name of the type.
     * @.category public-api
     * @since 2.0.0
     */
    public String getType() {
        return type;
    }


    /**
     * The String representation of the value of this global variable.
     *
     * @return a <code>String</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public String getValueAsString() {
        return value;
    }


    /**
     * Returns true if this is a deployment level global variable.
     *
     * @return a <code>boolean</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public boolean isDeploymentSettable() {
        return deploymentSettable;
    }


    /**
     * Returns true if this is a service level global variable.
     *
     * @return a <code>boolean</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public boolean isServiceSettable() {
        return serviceSettable;
    }

    public void setModificationTime(long _time) {
        modTime = _time;
    }

    public void setConstraint(String _constraint) {
        constraint = _constraint; 
    }

    public void setDescription(String _description) {
        description = _description;
    }

    public void setName(String _name) {
        name = _name;
    }

    public void setPath(String _path) {
        path = _path;
    }

    public void setType(String _type) {
        type = _type;
    }

    public void setDeploymentSettable(boolean _deploymentSettable) {
        deploymentSettable = _deploymentSettable;
    }

    public void setServiceSettable(boolean _serviceSettable) {
        serviceSettable = _serviceSettable;
    }
    
    public void setValue(String _value) {
    	overwriteValue(_value);
    }

    public void overwriteValue(String _value) {
        value = _value;
    }
    
    public void setProjectSource(String projectSource) {
		this.projectSource = projectSource;
	}
    
    public String getProjectSource() {
		return projectSource;
	}

	public void setOverridden(boolean b) {
		this.overridden = b;
	}
	 
	public boolean isOverridden() {
		return overridden;
	}
	
	

}
