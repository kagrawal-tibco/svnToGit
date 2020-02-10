package com.tibco.cep.kernel.service.logging;


import java.util.HashMap;
import java.util.Map;


/**
 * Levels are, by order of inclusion: OFF, FATAL, ERROR, WARN, INFO, DEBUG, and ALL.
 *
 * @version 4.0.0
 * @.category public-api
 * @since 4.0.0
 */
public class Level
        implements Comparable {

    private static final Map<String, Level> NAME_TO_LEVEL = new HashMap<String, Level>();
    private static final Map<Integer, Level> INT_TO_LEVEL = new HashMap<Integer, Level>();
    
    /**
     * @version 4.0.0
     * @.category public-api
     * @since 4.0.0
     */
    public static final Level OFF = new Level(2147483647, "off");

    /**
     * @version 4.0.0
     * @.category public-api
     * @since 4.0.0
     */
    public static final Level FATAL = new Level(50000, "fatal");

    /**
     * @version 4.0.0
     * @.category public-api
     * @since 4.0.0
     */
    public static final Level ERROR = new Level(40000, "error");

    /**
     * @version 4.0.0
     * @.category public-api
     * @since 4.0.0
     */
    public static final Level WARN = new Level(30000, "warn");

    /**
     * @version 4.0.0
     * @.category public-api
     * @since 4.0.0
     */
    public static final Level INFO = new Level(20000, "info");

    /**
     * @version 4.0.0
     * @.category public-api
     * @since 4.0.0
     */
    public static final Level DEBUG = new Level(10000, "debug");

    /**
     * @version 4.0.0
     * @.category public-api
     * @since 4.0.0
     */
    public static final Level TRACE = new Level(5000, "trace");

    /**
     * @version 4.0.0
     * @.category public-api
     * @since 4.0.0
     */
    public static final Level ALL = new Level(-2147483648, "all");


    protected Integer level;
    protected String name;


    /**
     * @version 4.0.0
     * @.category public-api
     * @since 4.0.0
     */
    public Level(
            int level,
            String name) {
        this.level = level;
        this.name = name;
        NAME_TO_LEVEL.put(name, this);
        INT_TO_LEVEL.put(level, this);
    }


    public int compareTo(Object o) {
        if (o instanceof Level) {
            return this.level.compareTo(((Level) o).level);
        } else {
            throw new IllegalArgumentException();
        }
    }


    /**
     * @version 4.0.0
     * @.category public-api
     * @since 4.0.0
     */
    public int toInt() {
        return this.level.intValue();
    }


    public String toString() {
        return this.name;
    }


    /**
     * @version 4.0.0
     * @.category public-api
     * @since 4.0.0
     */
    public static Level valueOf(Integer level) {
        return INT_TO_LEVEL.get(level);
    }


    /**
     * @version 4.0.0
     * @.category public-api
     * @since 4.0.0
     */
    public static Level valueOf(String name) {
        return NAME_TO_LEVEL.get(name);
    }
}
