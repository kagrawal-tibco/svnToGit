package com.tibco.cep.query.client.console.swing.control.query;

/**
 *
 * @author ksubrama
 */
public enum QueryType {
    SNAPSHOT("SNAPSHOT"), CONTINUOUS("CONTINUOUS"),
    SNAPSHOT_THEN_CONTINUOUS("SNAPSHOT_THEN_CONTINUOUS");
    private final String name;

    private QueryType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
