package com.tibco.rta.model;

/**
 * An enum to define basic datatypes supported.
 */
public enum DataType {

	INTEGER("INTEGER"),
	LONG("LONG"),
	DOUBLE("DOUBLE"),
	STRING("STRING"),
	BOOLEAN("BOOLEAN"),
    OBJECT("OBJECT"),
    SHORT("SHORT"),
    CLOB("CLOB"),
    BYTE("BYTE");

    private String literal;

    private DataType(String literal) {
        this.literal = literal;
    }

    public static DataType getByLiteral(String literal) {
        DataType[] dataTypes = DataType.values();
        for (DataType dataType : dataTypes) {
            if (dataType.literal.equals(literal)) {
                return dataType;
            }
        }
        return null;
    }
}
