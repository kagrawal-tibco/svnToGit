package com.tibco.rta.service.persistence.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.tibco.rta.model.DataType;

public class JdbcTable {

	protected String name;

	protected boolean isPrimitive = false;
	protected boolean isMultiDimension = false;
	private String tableOf = null;
	private List<ColumnDef> columns = new ArrayList<ColumnDef>();
	private List<String> constraints = new ArrayList<String>();
	private HashSet<String> indexNames = new HashSet<String>();
	protected ArrayList<IndexInfo> indexInfoList = new ArrayList<IndexInfo>();
	private boolean isDropSql = false;

	// To help generate migration script. This is the state of this Table
	protected static enum State {
		NEW, MODIFIED, UNCHANGED, DELETED
	};

	protected State state = State.NEW;

	public void setIsMultiDimension(boolean isMulti) {
		isMultiDimension = isMulti;
	}

	public void setTableType(String tableOf) {
		this.tableOf = tableOf;
	}

	public String getTableType() {
		return this.tableOf;
	}

	/**
	 * 
	 * @param name
	 */
	public JdbcTable(String name, boolean isDropSql) {
		this.name = name;
		this.isDropSql = isDropSql;
	}

	/**
	 * 
	 * @param memberName
	 * @param memberType
	 *           
	 */
	public void addMember(String memberName, String memberType, boolean isNullable) {
		String memberAlias = memberName; 
		columns.add(new ColumnDef(memberAlias, memberType, isNullable));
	}
	
	public void addMember(String memberName, DataType type) {
		addMember(memberName, type.toString(), true);
	}
	
	public void addMember(String memberName, DataType type, boolean isNullable) {
		addMember(memberName, type.toString(), isNullable);
	}
	
	public void addMember(String memberName, DataType type, boolean isNullable, int size) {
		String memberAlias = memberName; 
		columns.add(new ColumnDef(memberAlias, type.toString(), size, isNullable));
	}
	
	public ColumnDef findMember(String memberName) {
		ColumnDef member = null;
		for (ColumnDef column : columns) {
			if (column.memberName.equalsIgnoreCase(memberName)) {
				return column;
			}
		}
		return member;

	}
	
	/**
	 * To add index
	 * 
	 * @param columns
	 *            : column name with comma separated.
	 * @param isUnique
	 */
	public void addIndex(String columns, boolean isUnique) {
		indexInfoList.add(new IndexInfo(name, getMangledIndexName(name), columns, isUnique));
	}

//	/**
//	 * 
//	 * @param memberName
//	 * @param memberType
//	 * @param isArray
//	 * @param size
//	 */
//	public void addMember(String memberName, String memberType, boolean isArray, int size) {
//		String memberAlias = memberName;
//		columns.add(new ColumnDef(memberAlias, memberType, isArray, size, false));
//	}

	public void addMember(String memberName, ColumnDef memberDef) {
		columns.add(memberDef);
	}

	public List<ColumnDef> getMembers() {
		return columns;
	}

	/**
	 * 
	 * @param constraintName
	 * @param columns
	 */
	public void addPrimaryKeys(String constraintName, String columns) {
		constraints.add("ALTER TABLE " + name + " ADD CONSTRAINT " + constraintName + " PRIMARY KEY (" + columns + ")");
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	// generate add column method which handles the type translation to
	// db specific sql type
	private boolean appendColumn(String colName, String type, int size, StringBuffer buf, boolean addComma, boolean isNullable) {
		return appendColumn(colName, type, size, buf, addComma, null, isNullable);
	}

	private boolean appendColumn(String colName, String type, int size, StringBuffer buf, boolean addComma,
			String modificationType, boolean isNullable) {
		if (addComma) {
			buf.append(", ");
		}
		int lineStart = buf.lastIndexOf(JdbcDeployment.BRK);
		if ((buf.length() - lineStart) > 500) {
			buf.append(JdbcDeployment.BRK + "  ");
		}
		// VWC - do not wrap quote around it - buf.append("\"" + colName +
		// "\" ");
		// FIX THIS - Need to handle reserved keyword such as 'start', 'end'
		buf.append(colName + " ");
		if (modificationType != null) {
			buf.append(modificationType + " ");
		}
		buf.append(RDBMSType.sSqlType.getColumnTypeForPrimitiveType(type, size));
		if (!isNullable) {
			buf.append(" NOT NULL");
		}
		return true; // true means comma is needed in subsequent calls
	}

	// Adds simple type columns to the primary table
	private void addTableColumns(StringBuffer buf) {
		boolean addComma = false;
		for (ColumnDef def : columns) {
			appendColumn(def.memberName, def.memberType, def.size, buf, addComma, def.isNullable);
			addComma = true;
		}
	}

	/**
	 * 
	 * @param buildIndexes
	 *            boolean Setting this flag to true builds the indexes
	 * 
	 * @return
	 */
	public StringBuffer toStringBuffer() {
		// Alter/modified table
		if (this.state.equals(JdbcTable.State.MODIFIED)) {
			return toAlterBuffer();
		}
		
		StringBuffer buf = new StringBuffer();
		if(isDropSql){
			// Drop the primary table first
			buf.append("DROP TABLE " + name + RDBMSType.sSqlType.getNewline());
			buf.append(RDBMSType.sSqlType.getExecuteCommand());
		}		
		
		// New table
		buf.append("CREATE TABLE " + name + " (");
		addTableColumns(buf);
		buf.append(")" + RDBMSType.sSqlType.getNewline());
		buf.append(RDBMSType.sSqlType.getExecuteCommand());
		// finished creating the primary table

		// Create necessary constraints
		for (int i = 0; i < constraints.size(); i++) {
			buf.append(constraints.get(i));
			buf.append(RDBMSType.sSqlType.getNewline());
			buf.append(RDBMSType.sSqlType.getExecuteCommand());
		}
		
		// append indexes.
		buf.append(indexToStringBuffer());
		return buf;
	}
	
	public StringBuffer toAlterBuffer() {
		StringBuffer buff = new StringBuffer();
		// If table is modified.
		if (this.state.equals(JdbcTable.State.MODIFIED)) {			
			for (ColumnDef def : columns) {
				if (def.columnState.equals(ColumnDef.ColumnState.MODIFIED)) {
					buff.append("ALTER TABLE " + name);
					buff.append(RDBMSType.sSqlType.getModifyColumnCommand());
					buff.append(def.memberName + " "
							+ RDBMSType.sSqlType.getColumnTypeForPrimitiveType(def.memberType, def.size));
					buff.append(RDBMSType.sSqlType.getNewline());
					buff.append(RDBMSType.sSqlType.getExecuteCommand());
				} else if (def.columnState.equals(ColumnDef.ColumnState.NEW)) {
					// Add new column.
					buff.append("ALTER TABLE " + name);
					buff.append(RDBMSType.sSqlType.getAddColumnCommand());
					buff.append(def.memberName + " "
							+ RDBMSType.sSqlType.getColumnTypeForPrimitiveType(def.memberType, def.size));
					buff.append(RDBMSType.sSqlType.getNewline());
					buff.append(RDBMSType.sSqlType.getExecuteCommand());
					
					// Create primary key constraint, if its there.
					for (String constraint : constraints) {
						if (constraint != null && constraint.contains(def.memberName)) {
							buff.append(constraint);
							buff.append(RDBMSType.sSqlType.getNewline());
							buff.append(RDBMSType.sSqlType.getExecuteCommand());
						}
					}
					
					// index newly added column, if its indexed.
					for (IndexInfo iInfo : indexInfoList) {
						String indexColumn = iInfo.indexColumns;
						if (indexColumn != null && (indexColumn.equals(def.memberName) || indexColumn.contains(def.memberName))) {
							buff.append(RDBMSType.sSqlType.formatCreateIndexSql(iInfo.tableName, iInfo.indexName, def.memberName,
									iInfo.isUnique) + RDBMSType.sSqlType.getNewline());
							buff.append(RDBMSType.sSqlType.getExecuteCommand());
						}
					}
					
				} else if (def.columnState.equals(ColumnDef.ColumnState.DELETED)) {
					buff.append("ALTER TABLE " + name);
					buff.append(RDBMSType.sSqlType.getDropColumnCommand());
					buff.append(def.memberName);
					buff.append(RDBMSType.sSqlType.getNewline());
					buff.append(RDBMSType.sSqlType.getExecuteCommand());
				}
			}
		}
		return buff;
	}

	public StringBuffer indexToStringBuffer() {
		StringBuffer buff = new StringBuffer();
		for (IndexInfo index : indexInfoList) {
			buff.append(RDBMSType.sSqlType.formatCreateIndexSql(index.tableName, index.indexName, index.indexColumns, index.isUnique));
			buff.append(RDBMSType.sSqlType.getNewline());
			buff.append(RDBMSType.sSqlType.getExecuteCommand());
		}
		return buff;
	}

	private String getMangledIndexName(String tablename) {
		String indexName = "i_" + tablename;
		// FIX THIS - Need to be db specific
		if (indexName.length() > 25) {
			indexName = indexName.substring(0, 25);
		}
		int co = 0;
		String newName = indexName + "_" + co;
		while (indexNames.contains(newName)) {
			newName = indexName + "_" + co++;
		}
		indexNames.add(newName);
		return newName;
	}

	public String getDetails() {
		return this.getName() + ":= " + this.hashCode() + "\n\t" + columns;
	}
	
	@Override
	public String toString() {
		return "JdbcTable [name=" + name + ", isPrimitive=" + isPrimitive + ", isMultiDimension=" + isMultiDimension
				+ ", tableOf=" + tableOf + "]";
	}

	public static class ColumnDef {
		public String memberName;
		public String memberType;
		public boolean isArray = false;
		public int size = 0;
		public boolean isNullable = true;

		public static enum ColumnState {
			NEW, MODIFIED, UNCHANGED, DELETED, INCOMPATIBLE
		};

		public ColumnState columnState = ColumnState.NEW;

		public ColumnDef(String name, String type, boolean isNullable) {
			this.memberName = name;
			this.memberType = type;
			this.isNullable = isNullable;
		}

		public ColumnDef(String name, String type, int size, boolean isNullable) {
			this.memberName = name; // JdbcUtil.getInstance().getPDName(null,
									// name);
			this.memberType = type;
//			this.isArray = isArray;
			this.size = size;
			this.isNullable = isNullable;
//			validate(false);
		}

		public String toString() {
			return "Column [Name=" + this.memberName + ", Type=" + this.memberType + ", columnState="
					+ this.columnState + ", Size=" + this.size + ", isNullable=" + this.isNullable + "]";
		}

		private void validate(boolean debug) {
			// Used for debugging...
		}
	}

	class IndexInfo {
		String tableName;
		String indexName;
		String indexColumns;
		boolean isUnique;

		IndexInfo(String table, String index, String columns, boolean unique) {
			tableName = table;
			indexName = index;
			indexColumns = columns;
			isUnique = unique;
		}

		@Override
		public String toString() {
			return "IndexInfo [tableName=" + tableName + ", state=" + state + ", indexName=" + indexName + ", indexColumns=" + indexColumns
					+ ", isUnique=" + isUnique + "]";
		}
	}	

}
