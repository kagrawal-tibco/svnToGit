package com.tibco.cep.bemm.common.service.logging;


import java.util.HashMap;
import java.util.Map;


/**
 * Levels are, by order of inclusion: OFF, FATAL, ERROR, WARN, INFO, DEBUG, and ALL.
 *
 */
public class Level
        implements Comparable<Level> {

    private static final Map<String, Level> NAME_TO_LEVEL = new HashMap<String, Level>();
    private static final Map<Integer, Level> INT_TO_LEVEL = new HashMap<Integer, Level>();
    
    public static final Level OFF = new Level(2147483647, "off");

    public static final Level FATAL = new Level(50000, "fatal");

    public static final Level ERROR = new Level(40000, "error");

    public static final Level WARN = new Level(30000, "warn");

    public static final Level INFO = new Level(20000, "info");

    public static final Level DEBUG = new Level(10000, "debug");

    public static final Level TRACE = new Level(5000, "trace");

    public static final Level ALL = new Level(-2147483648, "all");


    protected Integer level;
    protected String name;

    /**
     * @param level
     * @param name
     */
    public Level(int level, String name) {
        this.level = level;
        this.name = name;
        NAME_TO_LEVEL.put(name, this);
        INT_TO_LEVEL.put(level, this);
    }

    @Override
    public int compareTo(Level l) {
    	return this.level.compareTo(l.level);
    }

    /**
     * @return
     */
    public int toInt() {
        return this.level.intValue();
    }


    @Override
    public String toString() {
        return this.name;
    }

    /**
     * @param level
     * @return
     */
    public static Level valueOf(Integer level) {
        return INT_TO_LEVEL.get(level);
    }


    /**
     * @param name
     * @return
     */
    public static Level valueOf(String name) {
        return NAME_TO_LEVEL.get(name);
    }
}
