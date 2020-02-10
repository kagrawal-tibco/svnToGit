package com.tibco.cep.webstudio.client.decisiontable.constraint;

import static com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionConstraintUtils.canonicalizeExpression;

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
public class Cell extends DecisionTableMutableAttributes.IdAttribute {

    public static byte CONDITION_CELL_TYPE = 0;
    public static byte ACTION_CELL_TYPE = 1;
    public static byte COMMENT_CELL_TYPE = 2;


    private Expression expression;
    private RuleTupleInfo ri;
    private  CellInfo ci;
    private  byte cellType;
    private byte operatorKind;
    private String columnId;
    private  boolean enabled;
    
    public Expression getExpression() {
		return expression;
	}

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
        return expression.getExpr();
    }

    public void setBody(String body) {
        this.expression = new Expression(body);
        try {
        	this.expression.parse();
        	//Canonicalize body here
        	expression.setExpr(canonicalizeExpression(expression.getExpr()));
        }
        catch (Exception e ) {
        //	DecisionTableCorePlugin.log("Exception while parsing - {0} cell type: {1} for rule id {2} with alias = {3}", body, cellType, ri.id, ci.alias, e);
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
        ci = ri.getDecisionTable().getCellInfo(alias);
        if (ci == null) {
            ci = new CellInfo();
            ci.alias = alias;
            ri.getDecisionTable().putCellInfo(alias, ci);
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
    
    public static class CellInfo {
        private String alias;
        private String path;
        private int type;
		public String getAlias() {
			return alias;
		}
		public void setAlias(String alias) {
			this.alias = alias;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
    }

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{Cell Id -> ");
		stringBuilder.append(id);
		stringBuilder.append(" ");
		stringBuilder.append(", Cell Body -> ");
		stringBuilder.append(expression.getExpr());
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
		
		String thisExpr = expression.getExpr();
		String otherExpr = other.expression.getExpr();
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
		return expression.getExpr().trim().intern().hashCode();
	}

	public RuleTupleInfo getRuleTupleInfo() {
		return ri;
	}

	public void setRuleTupleInfo(RuleTupleInfo ri) {
		this.ri = ri;
	}

	public CellInfo getCellInfo() {
		return ci;
	}

	public void setCellInfo(CellInfo ci) {
		this.ci = ci;
	}

	public byte getCellType() {
		return cellType;
	}

	public void setCellType(byte cellType) {
		this.cellType = cellType;
	}

	public byte getOperatorKind() {
		return operatorKind;
	}

	public void setOperatorKind(byte operatorKind) {
		this.operatorKind = operatorKind;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
}
