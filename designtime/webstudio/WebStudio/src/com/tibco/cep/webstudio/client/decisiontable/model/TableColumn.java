package com.tibco.cep.webstudio.client.decisiontable.model;

/**
 * 
 * @author sasahoo
 *
 */
public class TableColumn {

	
	String id; 
    String name; 
    String propertyPath; 
    String propertyType; 
    String columnType; 
    String alias;
    String defaultText;
    boolean associatedDomain;
    boolean isSubstitution;
    boolean isArray;
    
	TableRuleSet tableRuleSet;
    
    public TableColumn() {
    	
    }
	/**
	 * @param id
	 * @param name
	 * @param propertyPath
	 * @param propertyType
	 * @param columnType
	 * @param alias
	 */
	public TableColumn( TableRuleSet tableRuleSet,
						String id, 
			            String name, 
			            String propertyPath, 
			            String propertyType, 
			            String columnType, 
			            String alias, 
						boolean associatedDM,
						boolean isSubstitution,
						boolean isArray,
						String defaultText) {
	 this.id = id;
	 this.name = name;
	 this.propertyPath = propertyPath;
	 this.propertyType = propertyType;
	 this.columnType = columnType;
	 this.alias = alias;
	 this.associatedDomain = associatedDM;
	 this.tableRuleSet = tableRuleSet;
	 this.isSubstitution = isSubstitution;
	 this.isArray = isArray;
	 this.defaultText = defaultText;
	}


	public TableRuleSet getTableRuleSet() {
		return tableRuleSet;
	}


	public void setTableRuleSet(TableRuleSet tableRuleSet) {
		this.tableRuleSet = tableRuleSet;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPropertyPath() {
		return propertyPath;
	}


	public void setPropertyPath(String propertyPath) {
		this.propertyPath = propertyPath;
	}


	public String getPropertyType() {
		return propertyType;
	}


	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}


	public String getColumnType() {
		return columnType;
	}


	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}


	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public boolean hasAssociatedDomain() {
		return associatedDomain;
	}
	
	public void setAssociatedDomain(boolean associatedDomain) {
		this.associatedDomain = associatedDomain;
	}
	
    public boolean isSubstitution() {
		return isSubstitution;
	}
	public void setSubstitution(boolean isSubstitution) {
		this.isSubstitution = isSubstitution;
	}
	
	public boolean isArrayProperty() {
		return isArray;
	}
	public void setArrayProperty(boolean isArray) {
		this.isArray = isArray;
	}
	
	public void setDefaultCellText(String defaultText) {
		this.defaultText = defaultText;
	}
	
	public String getDefaultCellText() {
		return defaultText;
	}
	
}
