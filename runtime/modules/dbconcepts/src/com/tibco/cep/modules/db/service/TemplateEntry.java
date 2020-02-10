package com.tibco.cep.modules.db.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author bgokhale
 * An interface that represents the following metadata
 * 
 * 		<template-sql result-type = "/EDS/Model/Concepts/EXTENDED/CarrierPreferencesDetails" name = "CarrierPreferencesVal">
 * 			<sql>select * from CarrierPreferenceDetails where data > $Date </sql>
 * 			<properties>            
 *				<property name="A">
 *					<sql type="sql">select * from A </sql>
 *					<keys>
 *						<key source="k1" target="k2">
 *					</keys>
 *				</property>
 *				<property name="A.B">
 *					<sql> type="stored-proc">sp_getB($Date)</sql>
 *				</property>
 *          </properties>
 *		</template-sql>   
 *     
 */

public interface TemplateEntry {
		public static int SELECT_STMT = 0;
		public static int STORED_PROC = 1;
		
		public static int ENTRY_TYPE_QUERY = 0;
		public static int ENTRY_TYPE_DML = 1;
		/**
		 * 
		 * @return Name of the template entry
		 */
		public String getName();
		
		/**
		 * 
		 * @return Name of its property
		 */
		public QueryEntry getProperty(String name);
		
		/**
		 * 
		 * @return Map of propertyname v/s Property
		 */
		public Map getProperties();
		
		/**
		 * 
		 * @return SQL or Stored proc?
		 */
		public int getSqlType();
		
		/**
		 * 
		 * @return SQL text to execute
		 */
		public String getSqlText();
		
		/**
		 * 
		 * @return result type concept
		 */
		public String getResultType();
		
		/**
		 * 
		 * @return a Set of String property names
		 */
		public Set getPropertyNames();
		
		/**
		 * 
		 * @return List of DmlEntry objects
		 */
		public List getDmls();
		
		/**
		 * 
		 * @return entry ENTRY_TYPE_QUERY or ENTRY_TYPE_DML
		 *
		 */
		public int getEntryType();
		
		
		/**
		 * 
		 * @return map of index to values for a stored procedure in param
		 */
		public Map getStoredProcParameters();

		
		public interface QueryEntry {
			/**
			 * 
			 * @return Name of the property
			 */
			public String getName();
			/**
			 * 
			 * @return SQL to execute
			 */
			public String getSqlText();
			/**
			 * 
			 * @return SELECT_STMT for "sql" or STORED_PROC for "stored-proc"
			 */
			public int getSqlType();
			
			/**
			 * 
			 * @return Join key tuples
			 */
			public String [][] getKeys();
			
			/**
			 * 
			 * @return stored procedure parameters
			 */
			public Map getStoredProcParameters();
		}
		
		public interface DmlEntry {
			public static int UPDATE = 0;
			public static int DELETE = 1;
			public static int INSERT = 2;

			/**
			 * 
			 * @return SQL to execute
			 */
			public String getSqlText();
			/**
			 * 
			 * @return SELECT_STMT for "sql" or STORED_PROC for "stored-proc"
			 */
			public int getSqlType();
			
			/**
			 * 
			 * @return Returns UPDATE or DELETE or INSERT
			 */
			public int getDmlType();
			
			/**
			 * 
			 * @return Full path of BE type
			 */
			public String getBEType();
			
			/**
			 * 
			 * @return Full path of BE type
			 */
			public Map getStoredProcParameters();
		}
		
		public interface StoredProcParameter {
			
			public static int IN = 0;
			public static int OUT = 1;
			
			/**
			 * 
			 * @return Returns parameter name
			 */
			public String getName();
			/**
			 * 
			 * @return Returns IN for input parameter and OUT for output parameter
			 */
			public int getType();
			
			/**
			 * 
			 * @return Returns parameter index
			 */
			public int getIndex();
			
			/**
			 * 
			 * @return Returns one of the RDFTypes
			 */
			public int getBEType();

		}
}
