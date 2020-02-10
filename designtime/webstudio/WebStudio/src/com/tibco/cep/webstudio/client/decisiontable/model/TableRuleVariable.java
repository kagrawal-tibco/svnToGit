package com.tibco.cep.webstudio.client.decisiontable.model;

/**
 * 
 * @author sasahoo
 *
 */
public class TableRuleVariable {

	private String id;
	private String columnId;
	private String expression;
	private TableRule tableRule;
	private boolean enabled = true; 
	private String comment;


	/**
	 * @param id
	 * @param columnId
	 * @param expression
	 * @param enabled
	 * @param tableRule
	 */
	public TableRuleVariable(String id, 
							String columnId, 
							String expression, 
							boolean enabled,
							String comment,
							TableRule tableRule) {
		this.id = id;
		this.columnId = columnId;
		this.expression = expression;
		this.comment = comment;
		this.enabled = enabled;
		this.tableRule = tableRule;
	}

	public TableRule getTableRule() {
		return tableRule;
	}

	public void setTableRule(TableRule tableRule) {
		this.tableRule = tableRule;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
    public TableRuleVariable copy() {    	
    	String id = new String(this.getId());
    	String columnId = new String(this.getColumnId());
    	String expression = new String(this.getExpression());
    	boolean enabled = this.isEnabled();
    	String comment = new String(this.getComment());    	
    	TableRuleVariable ruleVariable = new TableRuleVariable(id, columnId, expression, enabled, comment, null);
        return ruleVariable;
    }	
}
