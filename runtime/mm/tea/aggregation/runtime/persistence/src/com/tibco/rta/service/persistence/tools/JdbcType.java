package com.tibco.rta.service.persistence.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * To change this template use File | Settings | File Templates.
 */
public class JdbcType {
	// All primitive types need to be translated to the final database type
	// based on the database used.
	public final static JdbcType TYPE_STRING = new JdbcType("STRING", true);
	public final static JdbcType TYPE_NUMBER = new JdbcType("NUMBER", true);
	public final static JdbcType TYPE_INTEGER = new JdbcType("INTEGER", true);
	public final static JdbcType TYPE_DATETIME = new JdbcType("T_DATETIME", true);
	public final static JdbcType TYPE_BOOLEAN = new JdbcType("BOOLEAN", true);
	public final static JdbcType TYPE_LONG = new JdbcType("LONG", true);
	public final static JdbcType TYPE_DOUBLE = new JdbcType("DOUBLE", true);
	public final static JdbcType TYPE_XML = new JdbcType("XMLTYPE", true);
	public final static JdbcType TYPE_CLOB = new JdbcType("CLOB", true);
	public final static JdbcType TYPE_BLOB = new JdbcType("BLOB", true);

	protected String name;

	protected boolean isPrimitive = false;

	protected String superType = null;
	protected String tableOf = null;

	protected List<String> members = new ArrayList<String>();

	/**
	 * 
	 * @param name
	 * @param isPrimitive
	 */
	public JdbcType(String name, boolean isPrimitive) {
		this.name = name;
		this.isPrimitive = isPrimitive;
	}

	public JdbcType(String name, boolean isPrimitive, String tableOf) {
		this.name = name;
		this.isPrimitive = isPrimitive;
		this.tableOf = tableOf;
	}

	public JdbcType(String name, boolean isPrimitive, String[] memberNames, String[] memberTypes) {
		this.name = name;
		this.isPrimitive = isPrimitive;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param superType
	 */
	public void setSuperType(String superType) {
		this.superType = superType;
	}

	/**
	 * 
	 * @param tableOf
	 */
	public void setTableType(String tableOf) {
		this.tableOf = tableOf;
	}

}
