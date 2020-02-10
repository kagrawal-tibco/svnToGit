package com.tibco.store.query.model;


public enum BinaryOperator {
	
	EQ("="),
	
	NEQ("!="),
	
	LT("<"),
	
	LE("<="),
	
	GT(">"),
	
	GE(">="),
	
	NOTEQ("!=");

//	IN("in"),
//	
//	LIKE("like");	
	
    private String literal;

    private BinaryOperator(String literal) {
        this.literal = literal;
    }

    public static BinaryOperator getByLiteral(String literal) {
    	BinaryOperator[] ops = BinaryOperator.values();
        for (BinaryOperator op : ops) {
            if (op.literal.equals(literal)) {
                return op;
            }
        }
        return null;
    }

	
}
