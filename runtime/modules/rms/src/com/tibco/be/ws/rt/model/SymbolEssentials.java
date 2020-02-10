package com.tibco.be.ws.rt.model;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/4/12
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class SymbolEssentials {

    private String alias;

    private String symbolType;

    SymbolEssentials(String alias, String symbolType) {
        this.alias = alias;
        this.symbolType = symbolType;
    }

    public String getAlias() {
        return alias;
    }

    public String getSymbolType() {
        return symbolType;
    }
}
