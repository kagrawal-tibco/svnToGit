package com.tibco.cep.decision.table.constraintpane;

import static com.tibco.cep.decision.table.constraintpane.DecisionTableAnalyzerUtils.canonicalizeExpression;

import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;



/**
 * User: ssubrama
 * Creation Date: Aug 2, 2008
 * Creation Time: 8:23:28 AM
 * <p/>
 * $LastChangedDate$
 * $Rev$
 * $LastChangedBy$
 * $URL$
 */
class Cell extends DecisionTableMutableAttributes.IdAttribute {

    static byte CONDITION_CELL_TYPE = 0;
    static byte ACTION_CELL_TYPE = 1;
    static byte COMMENT_CELL_TYPE = 2;


    Expression expression;
    RuleTupleInfo ri;
    CellInfo ci;
    byte cellType;
    byte operatorKind;
    String columnId;
    boolean enabled;
    
    public boolean isEnabled() {
		return enabled;
	}
	
    public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
    public Cell(byte cellType) {
        this.cellType = cellType;
        this.enabled = true;
    }
    public String getBody() {
        return expression.expr;
    }

    public void setBody(String body) {
        this.expression = new Expression(body);
        try {
        	this.expression.parse();
        	//Canonicalize body here
        	expression.expr = canonicalizeExpression(expression.expr);
        }
        catch (Exception e ) {
        	DecisionTableUIPlugin.log("Exception while parsing - {0} cell type: {1} for rule id {2} with alias = {3}", body, cellType, ri.id, ci.alias, e);
        }
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return this.ci.alias;
    }

    public void setAlias(String alias) {
        ci = ri.dt.cellInfoTable.get(alias);
        if (ci == null) {
            ci = new CellInfo();
            ci.alias = alias;
            ri.dt.cellInfoTable.put(alias, ci);
        }

    }

    public String getPath() {
        return ci.path;
    }

    public void setPath(String path) {
        this.ci.path = path;
    }
    
    public void setType(int type){
    	this.ci.type = type;
    }

    public int getType(){
    	return ci.type;
    }
    
    static class CellInfo {
        public String alias;
        public String path;
        public int type;

    }

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{Cell Id -> ");
		stringBuilder.append(id);
		stringBuilder.append(" ");
		stringBuilder.append(", Cell Body -> ");
		stringBuilder.append(expression.expr);
		stringBuilder.append("}");
		return stringBuilder.toString();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		//Check for id and body
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Cell)) {
			return false;
		}
		Cell other = (Cell)obj;
		if (!id.equals(other.id)) {
			return false;
		}
		
		String thisExpr = expression.expr;
		String otherExpr = other.expression.expr;
		if (!thisExpr.equals(otherExpr)) {
			return false;
		}
		return true;
	}
	
	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode(java.lang.Object)
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return expression.expr.trim().intern().hashCode();
	}
}
