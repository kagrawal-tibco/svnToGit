package com.tibco.cep.runtime.service.decision;

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

    public Cell(byte cellType) {
        this.cellType = cellType;
    }
    public String getBody() {
        return expression.expr;
    }

    public void setBody(String body) {
        this.expression = new Expression(body);
        try {
        this.expression.parse();
        }
        catch (Exception e ) {
            System.out.println("Exception while parsing - " + body + "cell type :" + cellType + " ruleId " + ri.id + " alias = " + ci.alias);
            e.printStackTrace();
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
            ci.alias =alias;
            ri.dt.cellInfoTable.put(alias, ci);
        }

    }

    public String getPath() {
        return ci.path;
    }

    public void setPath(String path) {
        this.ci.path = path;
    }

    static class CellInfo {
        public String alias;
        public String path;

    }
}
