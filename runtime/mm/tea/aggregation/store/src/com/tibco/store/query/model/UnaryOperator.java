package com.tibco.store.query.model;

public enum UnaryOperator {
	NOT("!");

    private String literal;

    private UnaryOperator(String literal) {
        this.literal = literal;
    }

    public static UnaryOperator getByLiteral(String literal) {
    	UnaryOperator[] ops = UnaryOperator.values();
        for (UnaryOperator op : ops) {
            if (op.literal.equals(literal)) {
                return op;
            }
        }
        return null;
    }

}
