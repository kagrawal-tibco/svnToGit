package com.tibco.cep.studio.dbconcept.codegen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.be.parser.codegen.CGUtil;
import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.ConceptClassGeneratorSmap;
import com.tibco.be.parser.codegen.JavaClassWriter;
import com.tibco.be.parser.codegen.MethodRecWriter;
import com.tibco.be.parser.codegen.StateMachineBlockLineBuffer;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.modules.db.model.runtime.AbstractDBConceptImpl;
import com.tibco.cep.studio.dbconcept.model.DBConcept;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jun 16, 2007
 * Time: 9:38:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBConceptClassGenerator {
    private static final String BRK = CGConstants.BRK;
    private static String delimitersForQuery = System.getProperty("tibco.be.dbconcepts.sqlquery.delimiters", "");
    
    public static final String[] dataTypes = {
        "String"
      , "Int"
      , "Long"
      , "Double"
      , "Boolean"
      , "DateTime"
      , null
      , null
  };
    public static JavaClassWriter makeConceptFile(Concept cept, Map<?, ?> properties, Ontology o
    		, Map<String, Map<String, int[]>> propInfoCache) throws Exception{
//        boolean omEnabled = ((Boolean)properties.get(Packager.BE_CODEGEN_ENABLEOM)).booleanValue();
        Properties oversizeStringConstants = (Properties) properties.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS);
        Map<?, ?> ruleFnUsage = (Map<?, ?>) properties.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE);
        
     // Nikhil: Aug 23 2010. Fixing defect for BE 9129. makeConceptFile(cept,Prop, map) method signature is changed to add smblbMap
        Map<String, StateMachineBlockLineBuffer> smblbMap = StateMachineBlockLineBuffer.fromConcept(cept, o);
        return makeConceptFile(cept, oversizeStringConstants, ruleFnUsage,smblbMap, o, propInfoCache);
    }

    // Nikhil: Aug 23 2010. Fixing defect for BE 9129. Adding argument smblbMap to the method below
    public static JavaClassWriter makeConceptFile(Concept cept, Properties oversizeStringConstants, Map<?, ?> ruleFnUsage
    		, Map<String, StateMachineBlockLineBuffer> smblbMap, Ontology o,
    		Map<String, Map<String, int[]>> propInfoCache) throws Exception 
    {
        String dscn = ConceptClassGeneratorSmap.DEFAULT_SUPER_CLASS_NAME;
        try {
        	ConceptClassGeneratorSmap.DEFAULT_SUPER_CLASS_NAME= AbstractDBConceptImpl.class.getName();
//            JavaFile file = ConceptClassGenerator_V2.makeConceptFile(cept,omEnabled,oversizeStringConstants,ruleFnUsage);
        	
        	//  Nikhil: Aug 23 2010. Fixing defect for BE 9129. Using smblbMap from the method parameter instead of instantiating a new one
//        	Map<String, StateMachineBlockLineBuffer> smblbMap = StateMachineBlockLineBuffer.fromConcept(cept);
            
        	final JavaClassWriter dbcc = ConceptClassGeneratorSmap.makeConceptFile(cept, oversizeStringConstants, ruleFnUsage,smblbMap, o, propInfoCache, true);
//            JavaClassWriter dbcc = file;
//            System.out.println("---------------->");
//            System.out.println(file.getClassName());
//            System.out.println(cept.getName());
//            System.out.println("***********");
//            for(Iterator it= file.getClasses();it.hasNext();) {
//                Object cls  = it.next();
//                if(cls instanceof JavaClassWriter ) {
//                	JavaClassWriter ccls = (JavaClassWriter) cls;
//                    System.out.println(ccls.getName());
//                    if(ccls.getName().equals(cept.getName())) {
////                        dbcc= ccls;
//                        break;
//                    }
//
//                }
//            }
//            if(dbcc == null )
//            	return file;
//                throw new Exception("Concept class not found in DBConceptClassGenerator");
            // found the concept class now need to generate the interface implementation
            addDBConceptInsert(cept,dbcc);
            addDBConceptUpdate(cept,dbcc);
            addDBConceptDelete(cept,dbcc);
            //addStatementParameters(cept,dbcc);
            addWhereClauseParameters(cept,dbcc);
            addJdbcResourceName(cept,dbcc);
            addGetPrimaryKeys(cept,dbcc);
            addSetSQLParameters(cept,dbcc);
            addGetTableName(cept,dbcc);
            addSetProperties(cept,dbcc);
            addGetPropertyNames(cept,dbcc);
            addPropertyNamesToColMapping(cept,dbcc);
            
            addGetExtIdPrefix(cept, dbcc);
            addGetExtIdProperties(cept,dbcc);
            addGetVersionColumnName(cept, dbcc);
            addGetVersionPolicy(cept, dbcc);
            addGetDBColumnDataType(cept, dbcc);

            return dbcc;
        }
        finally {
            ConceptClassGeneratorSmap.DEFAULT_SUPER_CLASS_NAME= dscn;
        }
    }

    private static void addGetTableName(Concept cept, JavaClassWriter dbcc) {
        StringBuilder body = new StringBuilder();
        MethodRecWriter createMethod = dbcc.createMethod("public", "java.lang.String", "getTableName");
//        MethodRec sp = new MethodRec("public", "java.lang.String", "getTableName");
        body.append("return getDelimitedTableName();");
        createMethod.setBody(body);
//        dbcc.addMethod(sp);
    }
    
/* 
 * This method is not used anywhere - use FunctionHelper.getDelimitedFQDBObjectName()
    private static String getDelimitedFQDBObjectName(Concept cept) {
    	String tableName = getDBObjectName(cept);
    	if (tableName == null) {
    		return null;
    	}
    	String schemaName = getSchemaName(cept);
    	if (schemaName != null ) {
    		// If the schemaName as obtained by the "schema name" property in the designer is %%XYZ%% , it means that it refers to a global variable
    		// So we need to handle this case separately
    		if(schemaName.startsWith("%%") && schemaName.endsWith("%%")){
    			String schemaNameGlobalVar = schemaName.substring(2,schemaName.length()-2);
    			tableName = "getValueOfGlobalVar(\"" + schemaNameGlobalVar + "\") + \".\" + \"" + appendDelimitersForQuery(tableName) + "\"";
    		}
    		else{
    			tableName = "\"" + appendDelimitersForQuery(schemaName) + "." +  appendDelimitersForQuery(tableName) + "\"";
    		}
    	}
    	return tableName;
    }
*/
    
    /**
     * This function appends delimited identifiers to the dbObjectName
     * @param dbObjectName 
     * @return -- the delimited db object name
     */
    protected static String appendDelimitersForQuery(String dbObjectName) {
		String escapeStr = "\\"; // if the delimiters itself is a " then an extra escape is required for db concept class code generation
    	if(delimitersForQuery != null && !"".equals(delimitersForQuery.trim())) { 
	    	StringBuilder delimiterBuf = new StringBuilder(delimitersForQuery);
	    	StringBuilder quotedStr = new StringBuilder();
	    	if(delimitersForQuery.equals("\"\"")) {
	    		quotedStr.append(escapeStr).append("\"").append(dbObjectName).append(escapeStr).append("\""); 
	    	}
	    	else {
	    		quotedStr.append(delimiterBuf.charAt(0)).append(dbObjectName).append(delimiterBuf.charAt(1)); 
	    	}
	    	return quotedStr.toString();
    	} else {
    		return dbObjectName;
    	}
	}
    

//    private static void addGetPrimaryKeys(Concept cept, JavaClass dbcc) {
//		String[] pks = getPKCols(cept);
//		StringBuilder body = new StringBuilder();
//		MethodRec sp = new MethodRec("public", "java.util.Map",
//				"getPrimaryKeyMap");
//
//		body.append("java.util.Map m = new java.util.LinkedHashMap();").append(
//				BRK);
//
//		for (int i = 0; i < pks.length; i++) {
//			PropertyDefinition pd = cept.getPropertyDefinition(pks[i], true);
//			body.append("m.put(\"" + pks[i] + "\"," + getGetMethodName(pd) + "());").append(BRK);
//		}
//
//		body.append("return m;");
//		sp.setBody(body);
//		dbcc.addMethod(sp);
//	}
    
    private static void addGetPrimaryKeys(Concept cept, JavaClassWriter dbcc) {
		String[] pks = getPKCols(cept);
		StringBuilder init = new StringBuilder();
		
		init.append("new " + String.class.getName() + "[] { ");
        for (int i=0; i<pks.length; i++) {
        	init.append('"' + pks[i] + "\", ");
        }
        init.append(" }");
        dbcc.addMember("public static ", String.class.getName() + "[]", "primaryKeys", init.toString());

        MethodRecWriter createMethod = dbcc.createMethod("public ", String.class.getName() + "[]", "getPrimaryKeyNames");
//        MethodRec mr = new MethodRec("getPrimaryKeyNames");
//        mr.setAccess("public ");
//        mr.setReturnType(String.class.getName() + "[]");
        createMethod.setBody(new StringBuilder("return primaryKeys;"));
//        dbcc.addMethod(mr);
        
 	}
 
    private static void addJdbcResourceName(Concept cept, JavaClassWriter dbcc) {
        StringBuilder body = new StringBuilder();
        MethodRecWriter createMethod = dbcc.createMethod("public", "java.lang.String", "getJDBCResourceName");
//        MethodRec sp = new MethodRec("public", "java.lang.String", "getJDBCResourceName");
        String jdbcResource = (String)cept.getExtendedProperties().get(DBConcept.JDBC_RESOURCE);
        body.append("return \"" + jdbcResource + "\";");
        createMethod.setBody(body);
//        dbcc.addMethod(sp);
    }

//    private static void addStatementParameters(Concept cept, JavaClass dbcc) {
//        StringBuilder body = new StringBuilder();
//        MethodRec sp = new MethodRec("public", "int", "setStatementParameters");
//        sp.addArg("java.sql.PreparedStatement","stmt");
//        sp.addArg("int","startCount");
//
//        body.append("return 0;");
//        sp.setBody(body);
//        dbcc.addMethod(sp);
//    }

    private static void addWhereClauseParameters(Concept cept, JavaClassWriter dbcc) {
        StringBuilder body = new StringBuilder();
        MethodRecWriter createMethod = dbcc.createMethod("public", "int", "setWhereClauseParameters");
//        MethodRec sp = new MethodRec("public", "int", "setWhereClauseParameters");
//        createMethod.addArg(type, name)
        createMethod.addArg("java.sql.PreparedStatement","stmt");
        createMethod.addArg("int","startCount");

        body.append("return 0;");
        createMethod.setBody(body);
//        dbcc.addMethod(sp);
    }

    private static void addDBConceptInsert(Concept cept, JavaClassWriter cc) {
        boolean comma = false;
        MethodRecWriter createMethod = cc.createMethod("public", "java.lang.String", "getInsertStatement");
//        MethodRec dbcm = new MethodRec("public", "java.lang.String", "getInsertStatement");
        StringBuilder body = new StringBuilder();
        StringBuilder insert = new StringBuilder();
        StringBuilder values = new StringBuilder();
        
        insert.append("\"INSERT INTO \"").append("+ getTableName() + \" ").append(" (");
        values.append(" VALUES (");
        Iterator<?> it = cept.getAllPropertyDefinitions().iterator();
        while (it.hasNext()) {
            PropertyDefinition pd = (PropertyDefinition) it.next();

            // This check ensures that a property which is not a column in the table is NOT included. 
            if (!pd.getExtendedProperties().containsKey(DBConcept.COLUMN_NAME) || getPropertyColName(pd) == null)
            	continue;

            switch(pd.getType()) {
                case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
                case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
                    continue;
                default: {
                    if(comma==true) {
                        insert.append(",");
                        values.append(",");
                    }
                    comma = (comma == false )? true:comma;
                    insert.append(getPropertyColName(pd));
                    values.append("?");
                }
                break;
            }
        }
        insert.append(")");
        values.append(")\"");
        StringBuilder buf = new StringBuilder();
        buf.append(insert);
        buf.append(values);
        //body.append("java.lang.System.out.println(" + buf.toString() +");").append(BRK);
        body.append("return " ).append(buf.toString()).append(";").append(BRK);
       
        createMethod.setBody(body);
//        cc.addMethod(dbcm);

    }

    private static void addDBConceptUpdate(Concept cept, JavaClassWriter cc) {
        //boolean comma=false;
        String[] pks = getPKCols(cept);
        MethodRecWriter createMethod = cc.createMethod("public", "java.lang.String", "getUpdateStatement");
//        MethodRec dbcm = new MethodRec("public", "java.lang.String", "getUpdateStatement");
        StringBuilder body = new StringBuilder();
        body.append("return \"");
        body.append("UPDATE \" ").append("+ getTableName() + \" ").append(" SET ");
        //Iterator it = cept.getAllPropertyDefinitions().iterator();
        List<?> props = cept.getAllPropertyDefinitions();
        List<PropertyDefinition> primitiveProps = new ArrayList<PropertyDefinition>();
        for (int j=0; j<props.size(); j++) {
        	 PropertyDefinition pd = (PropertyDefinition) props.get(j);
        	 if (pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT ||
        			 pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
        		 continue;
        	 }
        	 
             // This check ensures that a property which is not a column in the table is NOT included. 
        	 if (!pd.getExtendedProperties().containsKey(DBConcept.COLUMN_NAME) || getPropertyColName(pd) == null)
             	continue;

        	 if (inPKCols(pks, pd.getName())) {
        		 continue;
        	 }
        	 primitiveProps.add(pd);
        }
        
        for (int j=0; j<primitiveProps.size(); j++) {
        	PropertyDefinition pd = primitiveProps.get(j);
        	body.append(getPropertyColName(pd));
        	String dataType = (String)pd.getExtendedProperties().get(DBConcept.DATA_TYPE);
        	if (dataType.equalsIgnoreCase("xmltype"))
				body.append("=(XMLType(?))");
			else
				body.append("=? ");
        	if (j < primitiveProps.size() - 1) {
        		body.append(", ");
        	}
        }
        
//        while (it.hasNext()) {
//            PropertyDefinition pd = (PropertyDefinition) it.next();
//
//            switch(pd.getType()) {
//                case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
//                case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
//                    continue;
//                default: {
//                    if(comma==true) {
//                        body.append(",");
//                    }
//                    comma = (comma == false )? true:comma;
//                    body.append("SET ").append(getPropertyColName(pd)).append("=?");
//                }
//                break;
//            }
//        } // end while
        if (pks.length > 0) {
			body.append(" WHERE ");
			for (int i = 0; i < pks.length; i++) {
				PropertyDefinition pd = cept.getPropertyDefinition(pks[i],
						true);
				if (pd == null) {
					System.out.println(">>>>>" + cept.getName() + " "
							+ "prop not found " + pks[i] + "<<<<");
					continue;
				}
				String pKey = getPropertyColName(pd);
				body.append(pKey).append(" = ?");
				if (i < pks.length - 1) {
					body.append(" AND ");
				}
			}

		}
        body.append("\";" + BRK);

        createMethod.setBody(body);
//        cc.addMethod(dbcm);
    }

	private static String[] getPKCols(Concept cept) {
		List<String> pkList = new ArrayList<String>();
		String [] pks = new String[] {};
        if(cept.getExtendedProperties().containsKey(DBConcept.PRIMARY_KEY_PROPS)) {
            String keys = (String) cept.getExtendedProperties().get(DBConcept.PRIMARY_KEY_PROPS);
            keys = keys.substring(1,keys.length()-1);
            if (!keys.equals("")) {
            	pks = keys.split(",");
            }
        }
        for (int i=0; i<pks.length; i++) {
        	if (!pks[i].trim().equals("")) {
        		pkList.add(pks[i]);
        	}
        }
        String[] x = new String[] {};
        return  pkList.toArray(x);
	}
	
	private static String[] getExtIdPropertyNames(Concept cept) {
		List<String> list = new ArrayList<String>();
		String [] extIdProps = new String[] {};
        if(cept.getExtendedProperties().containsKey(DBConcept.EXTID_PROPS)) {
            String keys = (String) cept.getExtendedProperties().get(DBConcept.EXTID_PROPS);
            //keys = keys.substring(1,keys.length()-1);
            if (!"".equals(keys)) {
            	extIdProps = keys.split(",");
            }
        }
        for (int i=0; i<extIdProps.length; i++) {
        	if (!extIdProps[i].trim().equals("")) {
        		list.add(extIdProps[i]);
        	}
        }
        String[] x = new String[] {};
        return (String[]) list.toArray(x);
	}
	
    private static void addGetExtIdProperties(Concept cept, JavaClassWriter dbcc) {
		String[] extIdProps = getExtIdPropertyNames(cept);
		StringBuilder init = new StringBuilder();
		
		init.append("new " + String.class.getName() + "[] { ");
        for (int i=0; i<extIdProps.length; i++) {
        	init.append('"' + extIdProps[i] + "\", ");
        }
        init.append(" }");
        dbcc.addMember("public static ", String.class.getName() + "[]", "extIdProps", init.toString());

        MethodRecWriter createMethod = dbcc.createMethod("public ", String.class.getName() + "[]", "getExtIdPropertyNames");
//        MethodRec mr = new MethodRec("getExtIdPropertyNames");
//        mr.setAccess("public ");
//        mr.setReturnType(String.class.getName() + "[]");
        StringBuilder body = new StringBuilder();
        body.append("if(extIdProps.length == 0) {").append(BRK);
        body.append("return primaryKeys;").append(BRK);
        body.append("} else {").append(BRK);
        body.append("return extIdProps;").append(BRK);
        body.append("}");
        createMethod.setBody(body.toString());
//        dbcc.addMethod(mr);
        
 	}
    
    private static void addGetExtIdPrefix(Concept cept, JavaClassWriter dbcc) {
		String extIdPrefix = (String) cept.getExtendedProperties().get(DBConcept.EXTID_PREFIX);
		if(extIdPrefix == null || "".equals(extIdPrefix))
			return;
		MethodRecWriter createMethod = dbcc.createMethod("public ", String.class.getName(), "getExtIdPrefix");
//        MethodRec mr = new MethodRec("getExtIdPrefix");
//        mr.setAccess("public ");
//        mr.setReturnType(String.class.getName());
        StringBuilder body = new StringBuilder();
        if(extIdPrefix != null && !"".equals(extIdPrefix)){
        	body.append("return \"").append(extIdPrefix).append("\";");	
        } else {
        	body.append("return  getTableName();");
        }
        createMethod.setBody(body.toString());
//        dbcc.addMethod(mr);
 	}

    private static void addGetVersionColumnName(Concept cept, JavaClassWriter dbcc) {
		String versionProp = (String) cept.getExtendedProperties().get(DBConcept.VERSION_PROP);
		MethodRecWriter createMethod = dbcc.createMethod("public ", String.class.getName(), "getVersionPropertyName");
//        MethodRec mr = new MethodRec("getVersionPropertyName");
//        mr.setAccess("public ");
//        mr.setReturnType(String.class.getName());
        StringBuilder body = new StringBuilder();
        if(versionProp != null && !"".equals(versionProp)){
        	body.append("return \"").append(versionProp).append("\";");	
        } else {
        	body.append("return  \"\";");
        }
        createMethod.setBody(body.toString());
//        dbcc.addMethod(mr);
 	}
    
    private static void addGetVersionPolicy(Concept cept, JavaClassWriter dbcc) {
		String versionPolicy = (String) cept.getExtendedProperties().get(DBConcept.VERSION_POLICY);
		
		MethodRecWriter createMethod = dbcc.createMethod("public ", String.class.getName(), "getVersionPolicy");
//        MethodRec mr = new MethodRec("getVersionPolicy");
//        mr.setAccess("public ");
//        mr.setReturnType(String.class.getName());
        StringBuilder body = new StringBuilder();
        if(versionPolicy != null && !"".equals(versionPolicy)){
        	body.append("return \"").append(versionPolicy).append("\";");	
        } else {
        	body.append("return  \"\";");
        }
        createMethod.setBody(body.toString());
//        dbcc.addMethod(mr);
 	}
    
    private static void addGetDBColumnDataType(Concept cept, JavaClassWriter dbcc){
    	 MethodRecWriter mr = dbcc.createMethod("getDBColumnDataType") ;
         mr.setAccess("public");
         mr.setReturnType("String");
         mr.addArg("String", "propName");
         Collection<?> propDefs = cept.getAllPropertyDefinitions();
         ArrayList<?> set = new ArrayList<Object>(propDefs);
        
        StringBuilder body = new StringBuilder();
        if(set.size()>0){
        	body.append("switch(propName.hashCode()) {" + BRK);
        	
        	Iterator<?> iterator = set.iterator();
        	while(iterator.hasNext()){
        		Object obj = iterator.next();
        		if (obj instanceof PropertyDefinition) {
					PropertyDefinition property = (PropertyDefinition)obj;
					String name = property.getName();
					body.append("case ").append(name.hashCode()).append(": //")
							.append(name).append(BRK);
					String dataType = (String) property.getExtendedProperties()
							.get(DBConcept.DATA_TYPE);
					body.append("return \"" + dataType + "\";").append(BRK);
				}
        	}
        	
        	body.append("default: ").append(BRK);
    		body.append("return  null;").append(BRK);
    		body.append('}');
        }else{
        	body.append("return  null;");
        }
        mr.setBody(body);
    }
    
    private static void addDBConceptDelete(Concept cept, JavaClassWriter cc) {
    	MethodRecWriter createMethod = cc.createMethod("public", "java.lang.String", "getDeleteStatement");
//        MethodRec dbcm = new MethodRec("public", "java.lang.String", "getDeleteStatement");
        StringBuilder body = new StringBuilder();
        String [] pks = getPKCols(cept);
        
        body.append("return \"");
        body.append("DELETE FROM \"").append("+ getTableName() + \" ");
//        Iterator it = cept.getAllPropertyDefinitions().iterator();
//        while (it.hasNext()) {
//            PropertyDefinition pd = (PropertyDefinition) it.next();
//
//            switch(pd.getType()) {
//                case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
//                case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
//                    continue;
//                default: 
//                
//            }
//        } // end while
        if (pks.length > 0) {
			body.append(" WHERE ");

			for (int i = 0; i < pks.length; i++) {
				PropertyDefinition pd = cept
						.getPropertyDefinition(pks[i], true);
				String pKey = getPropertyColName(pd);
				body.append(pKey).append(" = ?");
				if (i < pks.length - 1) {
					body.append(" AND ");
				}
			}

		}
        body.append("\";" + BRK);
        createMethod.setBody(body);
//        cc.addMethod(dbcm);
    }

    /*private static boolean isDBConcept(Concept cept) {
        Map hp = cept.getExtendedProperties();
        if(hp.containsKey(DBConcept.SCHEMA_NAME) &&
                hp.containsKey(DBConcept.OBJECT_NAME)&&
                hp.containsKey(DBConcept.OBJECT_TYPE)) {
            return true;
        }
        return false;
    }*/



    private static void addSetSQLParameters(Concept dbcept, JavaClassWriter cc) {
    	MethodRecWriter createMethod = cc.createMethod("protected", "int", "setStatementParameters");
//        MethodRec mr = new MethodRec("setStatementParameters");
//        mr.setAccess("protected");
//        mr.setReturnType("int");
    	createMethod.addArg("java.sql.PreparedStatement", "stmt");
    	createMethod.addArg("int", "startCount");
    	createMethod.addArg("java.lang.StringBuffer", "buf");
    	createMethod.addThrows("java.sql.SQLException");
        StringBuilder body = new StringBuilder();
        body.append("int count = startCount;" + BRK);
        body.append("com.tibco.cep.runtime.model.element.Property prop = null;" + BRK);
        body.append("Object val = null;" + BRK);
        Collection<?> propDefs = dbcept.getPropertyDefinitions(false);
        Iterator<?> it = propDefs.iterator();
        while(it.hasNext()) {
            PropertyDefinition pd = (PropertyDefinition) it.next();
            
            // This check ensures that a property which is not a column in the table is NOT included. 
            if (!pd.getExtendedProperties().containsKey(DBConcept.COLUMN_NAME) || getPropertyColName(pd) == null)
            	continue;
 
            int type = pd.getType();

            if(type == PropertyDefinition.PROPERTY_TYPE_CONCEPT ||
                    type == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE ) {
                continue;
            }
//			prop = getPropertyNullOK("COUNTRY_ID");
//			if (prop == null) {
//				val = null;
//				stmt.setString(count, null);
//				buf.append("null,");
//			} else {
//				stmt.setString(count, get$2zCOUNTRY_ID().getString());
//				buf.append(get$2zCOUNTRY_ID().getString()).append(',');
//			}
            body.append("{" + BRK);
            
            body.append("prop = getPropertyNullOK(\"").append(pd.getName()).append("\");").append(BRK);
            body.append("if (prop == null) {" + BRK);
			body.append("val = null;" + BRK);
			body.append("stmt.setObject(count, null);" + BRK);
			body.append("buf.append(\"null,\");" + BRK);
			body.append("}" + BRK);
			body.append("else {" + BRK);
            
            
            if(type == RDFTypes.DATETIME_TYPEID) {

                body.append("java.util.Calendar tempCal = " + getPDSQLValueString(pd) + ";" + BRK);
                body.append("stmt." + CGConstants.jdbcSetterNames[type] + "(count, tempCal == null ? null : new java.sql.Timestamp(tempCal.getTimeInMillis()));" + BRK);
                body.append("buf.append(tempCal == null ? null : new java.sql.Timestamp(tempCal.getTimeInMillis())).append(',');");


            }
//            else if (pd.getName().endsWith("_DT")) {
//                body.append("{" + BRK);
//                body.append("java.lang.String val = " + getPDSQLValueString(pd) + ";");
//                body.append("if (val != null) {" + BRK);
//                body.append("java.lang.System.out.println(\"" + pd.getName() +  "= \" + " + " val );" + BRK) ;
//                body.append("java.util.Calendar tempCal = com.tibco.be.model.types.Converter.readCalendar(" + getPDSQLValueString(pd) + ");" + BRK);
//                body.append("stmt.setTimestamp" +  "(count, tempCal == null ? null : new java.sql.Timestamp(tempCal.getTimeInMillis()));" + BRK);
//                body.append("buf.append(tempCal == null ? null : new java.sql.Timestamp(tempCal.getTimeInMillis())).append(',');");
//                body.append("} else {stmt.setTimestamp(count,null);}" + BRK);
//                body.append("};" + BRK);
//            }
            else {
            	String dataType = (String)pd.getExtendedProperties().get(DBConcept.DATA_TYPE);
            	if(dataType.equalsIgnoreCase("xmltype")){
            	body.append("java.sql.Connection conn = com.tibco.cep.modules.db.functions.JDBCHelper.getCurrentConnection(getJDBCResourceName());" +BRK);
            	body.append("java.sql.Clob clob=getCLOB("+getPDSQLValueString(pd)+", conn);" + BRK);
            	body.append("stmt.setObject(count, " + "clob" + ");" + BRK);
            	body.append("buf.append(clob).append(',');");
            }else{
                body.append("stmt." + CGConstants.jdbcSetterNames[type] + "(count, " + getPDSQLValueString(pd) + ");" + BRK);
                body.append("buf.append(" + getPDSQLValueString(pd) + ").append(',');");
            }

            }
            body.append("};" + BRK);
            body.append("};" + BRK);
            body.append("count++;" + BRK);
        }
        body.append("return count;");
        createMethod.setBody(body);
//        cc.addMethod(mr);
    }

    private static String getPDSQLValueString(PropertyDefinition pd) {

        switch(pd.getType()) {

            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                return getGetMethodName(pd) + "().getBoolean()";
            case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                return getGetMethodName(pd) + "().getDateTime()";
            case PropertyDefinition.PROPERTY_TYPE_STRING:
                return getGetMethodName(pd) + "().getString()";
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                return getGetMethodName(pd) + "().getInt()";
            case PropertyDefinition.PROPERTY_TYPE_LONG:
                return getGetMethodName(pd) + "().getLong()";
            case PropertyDefinition.PROPERTY_TYPE_REAL:
                return getGetMethodName(pd) + "().getDouble()";
        }
        return null;
    }

    private static String getGetMethodName(String varName) {
        return CGConstants.GET_PREFIX + CGUtil.firstCap(varName);
    }

    private static String getGetMethodName(PropertyDefinition pd) {
        return getGetMethodName(ModelNameUtil.generatedMemberName(pd.getName()));
    }

    private static String getSetMethodName(String varName) {
        return CGConstants.SET_PREFIX + CGUtil.firstCap(varName);
    }

    private static String getSetMethodName(PropertyDefinition pd) {
        return getSetMethodName(ModelNameUtil.generatedMemberName(pd.getName()));
    }

    /**
     //public void  setProperties(ResultSet rs) throws Exception {
     //  set$2zSEGMENT_SEQ(rs.getLong("SEGMENT_SEQ"));
     //  set$2zCPN_WAIVER_CD(rs.getString("CPN_WAIVER_CD"));
     //  java.sql.Timestamp ts = rs.getTimestamp("X");
     //  if(ts != null) {
     //      java.util.Calendar tc = new java.util.GregorianCalendar();
     //      tc.setTime(ts);
     //      set$2zINSERT_DT(tc);
     //  } else {
     //      set$2zINSERT_DT(null);
     //  }
     //}
     * @param cept
     * @param cc
     */
    private static void addSetProperties(Concept cept, JavaClassWriter cc) {
    	MethodRecWriter createMethod = cc.createMethod("public", "void", "setProperties");
//        MethodRec mr = new MethodRec("setProperties");
//        mr.setAccess("public");
//        mr.setReturnType("void");
    	createMethod.addArg("java.sql.ResultSet", "rs");
    	createMethod.addThrows("java.sql.SQLException");
        StringBuilder body = new StringBuilder();

        Collection<?> propDefs = cept.getPropertyDefinitions(true);
        for(Iterator<?> it = propDefs.iterator(); it.hasNext();) {

            PropertyDefinition pd = (PropertyDefinition)it.next();

            // This check ensures that a property which is not a column in the table is NOT included. 
            if (!pd.getExtendedProperties().containsKey(DBConcept.COLUMN_NAME) || getPropertyColName(pd) == null)
            	continue;


            String name = ModelNameUtil.generatedMemberName(pd.getName());
            //String colname = pd.getHiddenProperty(PROPERTY_JDBCNAME);
            int type = pd.getType();

            if(type == PropertyDefinition.PROPERTY_TYPE_CONCEPT ||
                    type == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE )
                continue;

            if((type == RDFTypes.DATETIME_TYPEID)) {
            	body.append("try {").append(BRK);
            	body.append("java.sql.Timestamp " + name + " = rs." + CGConstants.jdbcGetterNames[type] + "(\"" + 
            			pd.getExtendedProperties().get(DBConcept.COLUMN_NAME) + "\");" + BRK);
                body.append("if(" + name + " != null) {" + BRK);
                body.append("java.util.GregorianCalendar tempCal = new java.util.GregorianCalendar();" + CGConstants.BRK);
                body.append("tempCal.setTime(" + name + ");" + BRK);
                body.append(getSetMethodName(pd) + "(tempCal);" + CGConstants.BRK);
                body.append("} else {" + BRK);
                body.append( getSetMethodName(pd) + "(null);" + CGConstants.BRK);
                body.append("}" + BRK);
                body.append("} catch (java.sql.SQLException e) {}" + BRK);
                
                // All due to various vagaries of java's Date and Calendar classes.
//                processRS.append("java.sql.Timestamp " + name + " = rs." + CGConstants.jdbcGetterNames[type] + "(\"" + colname + "\");" + BRK);
//                processRS.append("if(" + name + " != null) {" + BRK);
//                processRS.append(CGConstants.setArgumentTypes[type] + " tempCal = new java.util.GregorianCalendar();" + CGConstants.BRK);
//                processRS.append("tempCal.setTime(" + name + ");" + BRK);
//                processRS.append("instance." + getSetMethodName(pd) + "(tempCal);" + CGConstants.BRK);
//                processRS.append("} else {" + BRK);
//                processRS.append("instance." + getSetMethodName(pd) + "(null);" + CGConstants.BRK);
//                processRS.append("}" + BRK);
            }
            /*else if (pd.getName().endsWith("_DT")) {
				body.append("try {").append(BRK);
				body.append("java.sql.Timestamp " + name + " = rs."	+ CGConstants.jdbcGetterNames[5] + "(\"" + 
						AbstractMutableEntity.getExtendedProperty(pd, DBConcept.COLUMN_NAME)	+ "\");" + BRK);
				body.append("if(" + name + " != null) {" + BRK);
				body.append("java.util.GregorianCalendar tempCal = new java.util.GregorianCalendar();" + CGConstants.BRK);
				body.append("tempCal.setTime(" + name + ");" + BRK);
				body.append(getSetMethodName(pd)+ "(com.tibco.be.model.types.Converter.writeCalendar(tempCal));"+ CGConstants.BRK);
				body.append("} else {" + BRK);
				body.append(getSetMethodName(pd) + "(null);" + CGConstants.BRK);
				body.append("}" + BRK);
				body.append("} catch (java.sql.SQLException e) {}" + BRK);
			}*/
            else {
            	body.append("try {").append(BRK);
            	String dataType = (String)pd.getExtendedProperties().get(DBConcept.DATA_TYPE);
            	if(dataType.equalsIgnoreCase("xmltype")){
            		body.append("oracle.sql.OPAQUE object = (oracle.sql.OPAQUE)rs.getObject(\""+(String)pd.getExtendedProperties().get(DBConcept.COLUMN_NAME)+"\");"+BRK);
            		body.append("java.lang.String val = \"\";" + BRK);
            		body.append("if(object != null ){" + BRK);
            		body.append("oracle.xdb.XMLType createXML = oracle.xdb.XMLType.createXML(object);" + BRK);
            		body.append("val =createXML.getStringVal();"+BRK);
            		body.append("}" + BRK);
            	}else{
            		body.append(CGConstants.setArgumentTypes[type]).append(" val = ").
            		append("rs." + CGConstants.jdbcGetterNames[type]).append( "(\"" + 
                		pd.getExtendedProperties().get(DBConcept.COLUMN_NAME) + "\");" + CGConstants.BRK);
            	}
            	
            	body.append("if (rs.wasNull()) {" + CGConstants.BRK);
            	body.append("    val = ").append("com.tibco.cep.runtime.model.PropertyNullValues.getNull").append(dataTypes[type]).append("(this);").
            		append(CGConstants.BRK);
            	body.append("}" + CGConstants.BRK);
                body.append(getSetMethodName(pd)+"(val);" + CGConstants.BRK);
                body.append("} catch (java.sql.SQLException e) {}" + BRK);
                //processRS.append(CGConstants.setArgumentTypes[type] + " " + name + " = rs." + CGConstants.jdbcGetterNames[type] + "(\"" + colname + "\");" + CGConstants.BRK);
                //processRS.append("instance." + getSetMethodName(pd) + "(" + name + ");" + CGConstants.BRK);
            }
        }

        createMethod.setBody(body);
//        cc.addMethod(mr);
    }



    /*private static void addGetPropertyNames(Concept cept, JavaClass cc) {
        StringBuilder body = new StringBuilder("return new " + String.class.getName() + "[] {");
        Collection propDefs = cept.getAllPropertyDefinitions();
        boolean first = true;
        for(Iterator it = propDefs.iterator(); it.hasNext();) {
            PropertyDefinition pd = (PropertyDefinition)it.next();
            if(first) {
                first = false;
            }
            else {
                body.append(", ");
            }
            body.append("\"" + pd.getName() + "\"");
        }
        body.append("};");

        MethodRec mr = new MethodRec("getPropertyNames");
        mr.setAccess("public");
        mr.setReturnType(String.class.getName() + "[]");
        mr.setBody(body);
        cc.addMethod(mr);
    }*/
    
    private static void addGetPropertyNames(Concept cept, JavaClassWriter cc) {

    	List<String> names = new ArrayList<String>();
    	Iterator<?> props = cept.getAllPropertyDefinitions().iterator();
        while(props.hasNext()) {
            PropertyDefinition propDefn = (PropertyDefinition) props.next();
            String name = propDefn.getName();
            names.add(name);
        }
        
        //add public static String[] propertyNames
        StringBuilder init = new StringBuilder();
        init.append("new " + String.class.getName() + "[] { ");
        //boolean isFirst = true;
        for(int ii = 0; ii < names.size(); ii++) {
            String propName = (String)names.get(ii);
            init.append('"' + propName + "\", ");
        }
        init.append(" }");
        cc.addMember("public static ", String.class.getName() + "[]", "propertyNames", init.toString());
        
        //add public static String[] getPropertyNames()
        MethodRecWriter createMethod = cc.createMethod("public ", String.class.getName() + "[]", "getPropertyNames");
//        MethodRec mr = new MethodRec("getPropertyNames");
//        mr.setAccess("public ");
//        mr.setReturnType(String.class.getName() + "[]");
        createMethod.setBody(new StringBuilder("return propertyNames;"));
//        cc.addMethod(mr);
    }
    
    protected static String getDBObjectName(Concept cept) {
    	return (String) cept.getExtendedProperties().get(DBConcept.OBJECT_NAME);
    }
    protected static String getSchemaName(Concept cept) {
    	return (String) cept.getExtendedProperties().get(DBConcept.SCHEMA_NAME);
    }
    /*private static String getFQDBObjectName(Concept cept) {
    	return getSchemaName(cept)+"."+getDBObjectName(cept);
    }*/

    private static String getPropertyColName(PropertyDefinition prop) {
    	return (String) prop.getExtendedProperties().get(DBConcept.COLUMN_NAME);
    }
    private static void addPropertyNamesToColMapping (Concept cept, JavaClassWriter cc) {

        //add public static String[][] propertyNamesToColumnMap = new String [][] { {"a", "b"},}
        StringBuilder init = new StringBuilder(32);
        init.append("new " + String.class.getName() + "[][] { ");
        Iterator<?> props = cept.getPropertyDefinitions(true).iterator();
        while(props.hasNext()) {
            PropertyDefinition propDefn = (PropertyDefinition) props.next();
            String name = propDefn.getName();
//            names.add(name);
            if (!propDefn.getExtendedProperties().containsKey(DBConcept.COLUMN_NAME) || getPropertyColName(propDefn) == null)
            	continue;

            String colName = getPropertyColName(propDefn);
            
            init.append('{').append('"').append(name).append('"').append(", ");
            init.append('"').append(colName).append('"').append("},\n");
        }
        init.append("};\n");
        cc.addMember("public static ", String.class.getName() + "[][]", "propertyToColMap", init.toString());
        
        //add public static String[] getPropertyNames()
        MethodRecWriter createMethod = cc.createMethod("public ", String.class.getName() + "[][]", "getPropertyToColMap");
//        MethodRec mr = new MethodRec("getPropertyToColMap");
//        mr.setAccess("public ");
//        mr.setReturnType(String.class.getName() + "[][]");
        createMethod.setBody(new StringBuilder("return propertyToColMap;"));
//        cc.addMethod(mr);
    }
    private static boolean inPKCols (String[] pkCols, String key) {
    	for (int i=0; i<pkCols.length; i++) {
    		if (pkCols[i].equals(key)) {
    			return true;
    		}
    	}
    	return false;
    }
}
