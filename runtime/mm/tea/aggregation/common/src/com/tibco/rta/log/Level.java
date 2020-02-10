package com.tibco.rta.log;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Levels are, by order of inclusion: OFF, FATAL, ERROR, WARN, INFO, DEBUG, and ALL.
 * 
 */
public class Level {

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


	public Level(int level, String name) {
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


	public int toInt() {
		return level;
	}

	public String toString() {
		return this.name;
	}


	public static Level valueOf(Integer level) {
		return INT_TO_LEVEL.get(level);
	}

	public static Level valueOf(String name) {
		return NAME_TO_LEVEL.get(name);
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Level)) {
            return false;
        }
        Level other = (Level) obj;

        return (other.toInt() == this.toInt()) && (other.name.equals(this.name));
    }

    @Override
    public int hashCode() {
        return level.hashCode();
    }
}
