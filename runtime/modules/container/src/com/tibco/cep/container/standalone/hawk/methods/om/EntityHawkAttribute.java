package com.tibco.cep.container.standalone.hawk.methods.om;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 30, 2006
 * Time: 11:10:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityHawkAttribute {

    private String name;
    private String value;
    private char type;
    public static final char ATTRIBUTE = 'A';
    public static final char PROPERTY = 'P';
    public static final String TYPE = "type";


    public EntityHawkAttribute(String name, String value, char type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public char getType() {
        return type;
    }

}
