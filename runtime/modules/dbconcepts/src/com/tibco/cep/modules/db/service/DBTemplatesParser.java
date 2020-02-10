package com.tibco.cep.modules.db.service;

/**
 * @author bgokhale
 * 
 * 
 * Parses an XML file with this structure
 * 
 *
 * <templates>
 *	<template-query result-type = "/EDS/Model/Concepts/EXTENDED/CarrierPreferencesDetails\name = "CarrierPreferencesVal">
 *		<sql>select * from CarrierPreferenceDetails where data > $Date </sql>
 *		<properties>            
 *			<property name="A">
 *				<sql type="sql">select * from A </sql>
 *				<keys>
 *					<key source="k1\target="k2"/>
 *				</keys>
 *			</property>
 *			<property name="A.B">
 *				<sql type="stored-proc">sp_getB($Date)</sql>
 *			</property>
 *		</properties>
 *  </template-query>   
 * 	<template-dml name = "UpdateOperation">
 *		<dmls>
 *			<dml be-type = "/Path/Products" dml-type = "update">
 *				<sql>update PRODUCTS set TYPE=$Type</sql>
 *			</dml>
 *			<dml be-type = "/Path/Products" dml-type = "delete">
 *				<sql>delete PRODUCTS where TYPE=$Type</sql>
 *			</dml>
 *		</dmls>
 *	</template-dml>
 *</templates>
 */

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.xml.sax.InputSource;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.modules.db.service.TemplateEntry.DmlEntry;
import com.tibco.cep.modules.db.service.TemplateEntry.QueryEntry;
import com.tibco.cep.modules.db.service.TemplateEntry.StoredProcParameter;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;

public class DBTemplatesParser {
	
	static String CONTENTS = "" +
		"<templates>" +
		"<template name = \"CarrierPreferencesVal\">" +
		"<template-query result-be-type = \"/EDS/Model/Concepts/EXTENDED/CarrierPreferencesDetails\">" +
		"	<sql>select * from CarrierPreferenceDetails where data > $Date </sql>" +
		"	<properties>            " +
		"		<property name=\"A\">" +
		"			<sql type=\"sql\">select * from A </sql>" +
		"			<keys>" +
		"				<key source=\"k1\" target=\"k2\"/>" +
		"			</keys>" +
		"		</property>" +
		"		<property name=\"A.B\">" +
		"			<sql type=\"stored-proc\">sp_getB($Date)</sql>" +
		"			<params>" +
		"				<param name=\"\" type=\"IN\" index=\"1\" datatype=\"DOUBLE\"/>" +
		"				<param name=\"\" type=\"OUT\" index=\"2\" datatype=\"CONCEPT\"/>" +
		"			</params>" +
		"		</property>" +
		"	</properties>" +
		"</template-query>   " +
		"</template>" +
		"<template name = \"CarrierPreferencesVal2\">" +
		"<template-query result-be-type = \"/EDS/Model/Concepts/EXTENDED/CarrierPreferencesDetails\">" +
		"	<properties>            " +
		"		<property name=\"A\">" +
		"			<sql type=\"sql\">select * from A </sql>" +
		"			<keys>" +
		"				<key source=\"k1\" target=\"k2\"/>" +
		"				<key source=\"k1\" target=\"k2\"/>" +
		"			</keys>" +
		"		</property>" +
		"		<property name=\"A.B\">" +
		"			<sql type=\"stored-proc\">sp_getB($Date)</sql>" +
		"		</property>" +
		"	</properties>" +
		"</template-query>   " +
		"</template>" +
		"<template name = \"dml-ENTRY\">" +
		"	<template-dml>" +
		"		<dmls>" +
		"			<dml name=\"\" be-type = \"/Path1/\" dml-type = \"update\">" +
		"				<sql>sql1</sql>" +
		"			</dml>" +
		"			<dml name=\"\" be-type = \"/Path2/\" dml-type = \"delete\">" +
		"			<sql type=\"sql\" >sql2</sql>" +
		"			</dml>" +
		"			<dml name=\"\" be-type = \"/Path3/\" dml-type = \"insert\">" +
		"			<sql type=\"stored-proc\" >sql3</sql>" +
		"			</dml>" +
		"		</dmls>" +
		"	</template-dml>" +
		"</template>" +
		"</templates>" ;
		
	
	private static DBTemplatesParser metadataFileParser;
	//this is a map of maps
	private Map templatesMap = Collections.synchronizedMap(new LinkedHashMap());
	//private Map dmlEntryMap = new LinkedHashMap();
	public static boolean testMode;
	private DBTemplatesParser() {
		
	}
	public static DBTemplatesParser getInstance() {
		if (metadataFileParser == null) {
			metadataFileParser = new DBTemplatesParser();
		}
		return metadataFileParser;
	}
	
	private void parse (String templatesResource) throws Exception {
		if (templatesMap.containsKey(templatesResource)) {
			return;
		}
		XiNode node = null;
		if (testMode) {
			try {
				node = XiParserFactory.newInstance().parse(
						new InputSource(new StringBufferInputStream(
								templatesResource))).getRootNode();
				node = XiChild.getFirstChild(node);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			node = getResource(templatesResource);
		}
		if (node == null) {
			throw new Exception ("Template resource not found: " + templatesResource);
		}
		Map templates = parseEntries(node);
		if (!templatesMap.containsKey(templatesResource)) {
			templatesMap.put(templatesResource, templates);		
		}
	}
	
	public String getSqlText(String templatesResource, String name) throws Exception {
		TemplateEntry t = getTemplateEntry(templatesResource, name);
		if (t != null) {
			return t.getSqlText();
		}
		return null;
	}

	public int getSqlType (String templatesResource, String name)  throws Exception {
		TemplateEntry t = getTemplateEntry(templatesResource, name);
		if (t != null) {
			return t.getSqlType();
		}
		return 0;
	}
	
	public String getResultType (String templatesResource, String name)  throws Exception {
		TemplateEntry t = getTemplateEntry(templatesResource, name);
		if (t != null) {
			return t.getResultType();
		} 
		return null;
	}
	
	public Set getPropertyNames(String templatesResource, String name)  throws Exception {
		TemplateEntry t = getTemplateEntry(templatesResource, name);
		if (t != null) {
			return t.getPropertyNames();
		}
		return new HashSet();
	}
	
	public String getSqlForProperty (String templatesResource, String name, String propName)  throws Exception {
		TemplateEntry t = getTemplateEntry(templatesResource, name);
		if (t != null) {
			QueryEntry p = t.getProperty(propName);
			if (p != null) {
				return p.getSqlText();
			}
		}
		return null;
	}
	
	public int getSqlTypeForProperty (String templatesResource, String name, String propName)  throws Exception {
		TemplateEntry t = getTemplateEntry(templatesResource, name);
		if (t != null) {
			QueryEntry p = t.getProperty(propName);
			if (p != null) {
				return p.getSqlType();
			}
		}
		return 0;
	}
	
	public String [][] getKeysForProperty(String templatesResource, String name, String propName)  throws Exception {
		TemplateEntry t = getTemplateEntry(templatesResource, name);
		if (t != null) {
			QueryEntry p = t.getProperty(propName);
			if (p != null) {
				return p.getKeys();
			}
		}
		return new String[][] {};
	}
	
	public List getDmlEntries(String templatesResource, String name)  throws Exception {
		TemplateEntry t = getTemplateEntry(templatesResource, name);
		if (t != null && t.getEntryType() == TemplateEntry.ENTRY_TYPE_DML) {
			List dmlEntries = t.getDmls();
			return dmlEntries;
		}
		return new ArrayList();
	}
	
	public Map getStoredProcParametes(String templatesResource, String name)  throws Exception {
		TemplateEntry t = getTemplateEntry(templatesResource, name);
		if (t != null) {
			Map paremeters = t.getStoredProcParameters();
			return paremeters;
		}
		return new LinkedHashMap();
	}
	
	public Map getStoredProcParametes(String templatesResource, String name, String propName)  throws Exception {
		TemplateEntry t = getTemplateEntry(templatesResource, name);
		if (t != null) {
			QueryEntry p = t.getProperty(propName);
			if (p != null) {
				return p.getStoredProcParameters();
			}
		}
		return new LinkedHashMap();
	}

	private TemplateEntry getTemplateEntry(String templatesResource, String name) throws Exception {
		parse(templatesResource);
		Map m = (Map) templatesMap.get(templatesResource);
		TemplateEntry t = (TemplateEntry) m.get(name);
		return t;
	}
	
	private Map parseEntries(XiNode root) throws Exception {
		//root = root.getFirstChild();
		Iterator itr = XiChild.getIterator(root, ExpandedName.makeName("template"));
		Map templates = new LinkedHashMap();
		while (itr.hasNext()) {
			XiNode template = (XiNode) itr.next();
			String name = template.getAttributeStringValue(ExpandedName.makeName("name"));
			if (name == null || name != null && name.equals("")) {
				throw new Exception ("Missing Attribute 'name' in template entry");
			}
			
			XiNode child = XiChild.getFirstChild(template);
			String childElemName = child.getName().localName;
			if (childElemName.equals("template-query")) {
				parseTemplateQueryNode(name, child, templates);
			} else if (childElemName.equals("template-dml")) {
				parseTemplateDml(name, child, templates);
			}
		}
		return templates;
	}

    private XiNode getResource(String resourceName) {
    	try {
			RuleServiceProvider rsp = RuleSessionManager
				.getCurrentRuleSession().getRuleServiceProvider();
			ArchiveResourceProvider provider = rsp.getProject().getSharedArchiveResourceProvider();
			Collection col = provider.getAllResourceURI();
			Iterator r = col.iterator();
			while (r.hasNext()) {
			    String uri = (String) r.next();
			    if (uri.endsWith(resourceName)) {
			    	XiNode node = null;
			    	synchronized (this) {
			    		node = provider.getResourceAsXiNode(uri);
					}
			        return node;
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
    private void parseTemplateQueryNode(String name, XiNode template, Map templates) throws Exception {
		String resultType = template.getAttributeStringValue(ExpandedName.makeName("result-be-type"));
		if (resultType == null) {
			throw new Exception ("result-be-type attribute not specified in template");
		}
		String sqlText = null;
		String sqlType = null;
		XiNode sqlNode = XiChild.getChild(template, ExpandedName.makeName("sql"));
		if (sqlNode != null) {
			sqlText = sqlNode.getStringValue();
			sqlType = sqlNode.getAttributeStringValue(ExpandedName.makeName("type"));
		}

		TemplateEntryImpl templateEntry = new TemplateEntryImpl(name, resultType, sqlText, sqlType);
		
		if(templateEntry.getSqlType() == TemplateEntry.STORED_PROC){
			XiNode spParamNode = XiChild.getChild(template, ExpandedName.makeName("params"));
			if(spParamNode != null){
				Map map = getStoredProcParameters(spParamNode);
				templateEntry.addParameters(map);				
			}
		}
		
		XiNode propsNode = XiChild.getChild(template, ExpandedName.makeName("properties"));
		if (propsNode == null) {
			return;
		}
		
		Iterator propsIter = XiChild.getIterator(propsNode, ExpandedName.makeName("property"));

		while (propsIter.hasNext()) {
			
			XiNode propNode = (XiNode) propsIter.next();
			String propName = propNode.getAttributeStringValue(ExpandedName.makeName("name"));
			if (propName == null || propName != null && propName.equals("")) {
				throw new Exception ("Missing Property Attribute 'name' within entry " + name);
			}
			
			sqlNode = XiChild.getChild(propNode, ExpandedName.makeName("sql"));

			if (sqlNode != null) {
				sqlText = sqlNode.getStringValue();
				sqlType = sqlNode.getAttributeStringValue(ExpandedName.makeName("type"));
			} else {
				sqlText = null;
				sqlType = null;
			}
			
			TemplateEntryPropertyImpl prop = new TemplateEntryPropertyImpl(name, sqlText, sqlType);
			
			if(prop.getSqlType() == TemplateEntry.STORED_PROC){
				XiNode spParamNode = XiChild.getChild(propNode, ExpandedName.makeName("params"));
				if(spParamNode != null){
					Map map = getStoredProcParameters(spParamNode);
					prop.addParameters(map);
				}
			}
			
			String keys[][] = {};
			
			XiNode keysNode = XiChild.getChild(propNode, ExpandedName.makeName("keys"));
			if(keysNode != null){
				int keyCount = XiChild.getChildCount(keysNode, ExpandedName.makeName("key"));
				if(keyCount > 0){
					Iterator keysItr = XiChild.getIterator(keysNode, ExpandedName
							.makeName("key"));
					int i = 0;
					keys = new String[keyCount][2];
					while (keysItr.hasNext()) {
						XiNode keyNode = (XiNode) keysItr.next();
						
						String srcKey = keyNode.getAttributeStringValue(ExpandedName.makeName("source"));
						String tgtKey = keyNode.getAttributeStringValue(ExpandedName.makeName("target"));
						prop.addKeyPair(srcKey, tgtKey);
						
						keys[i][0] = srcKey;
						keys[i][1] = tgtKey;
						
						i++;
					}
				}
			}
			templateEntry.addProperty(propName, prop);
		}
		templates.put(name, templateEntry);
    }
    
    private Map getStoredProcParameters(XiNode pNode){
    	
    	Map map = new LinkedHashMap();
		Iterator<XiNode> iter = XiChild.getIterator(pNode);
		while (iter.hasNext()) {
			XiNode paramNode = (XiNode) iter.next();
			String paramName = paramNode.getAttributeStringValue(ExpandedName.makeName("name"));
			String paramType = paramNode.getAttributeStringValue(ExpandedName.makeName("type"));
			String paramIndex = paramNode.getAttributeStringValue(ExpandedName.makeName("index"));
			String paramBeType = paramNode.getAttributeStringValue(ExpandedName.makeName("datatype"));
			StoredProcParameterImpl param = new StoredProcParameterImpl(paramName,paramIndex,paramType,paramBeType);
			map.put(paramName, param);
		}
		return map;
    }
	
    private void parseTemplateDml(String name, XiNode dmlEntryNode, Map templates) throws Exception {

    	TemplateEntryImpl e = new TemplateEntryImpl(name);
		XiNode dmlNodes = XiChild.getChild(dmlEntryNode, ExpandedName
				.makeName("dmls"));
		if (dmlNodes == null) {
			return;
		}
		Iterator dmlIter = XiChild.getIterator(dmlNodes, ExpandedName
				.makeName("dml"));

		while (dmlIter.hasNext()) {
			XiNode dmlNode = (XiNode) dmlIter.next();
			String dmlType = dmlNode.getAttributeStringValue(ExpandedName
					.makeName("dml-type"));
			String beType = dmlNode.getAttributeStringValue(ExpandedName
					.makeName("be-type"));
			if (beType == null) {
				throw new Exception ("Missing attribute: be-type in template entry: " + name);
			}
			XiNode sqlNode = XiChild.getChild(dmlNode, ExpandedName
					.makeName("sql"));
			String sqlText = null;
			String sqlType = null;
			if (sqlNode != null) {
				sqlText = sqlNode.getStringValue();
				sqlType = sqlNode.getAttributeStringValue(ExpandedName
						.makeName("type"));
			}

			DmlEntryImpl dmlEntry = new DmlEntryImpl(dmlType, sqlText, sqlType,
					beType);
			
			if(dmlEntry.getSqlType()  == TemplateEntry.STORED_PROC){
				XiNode spParamNode = XiChild.getChild(dmlNode, ExpandedName.makeName("params"));
				if(spParamNode != null){
					Map map = getStoredProcParameters(spParamNode);
					dmlEntry.addParameters(map);
				}
			}

			e.addDmlEntry(dmlEntry);
		}
		templates.put(name, e);
	}
    
	class TemplateEntryImpl implements TemplateEntry {
		
		String name;
		String resultTpe;
		Map propsMap = new LinkedHashMap();
		List dmlList = new ArrayList();
		String sqlText;
		int sqlType;
		int templateType;
		Map paramMap = new LinkedHashMap();
		
		public TemplateEntryImpl (String name, String resultType, String sqlText, String sqlType) {
			this.name = name;
			this.resultTpe = resultType;
			this.sqlText = sqlText;
			int sqlTypeInt = sqlType == null ? SELECT_STMT : sqlType.equalsIgnoreCase("sql") ? SELECT_STMT : STORED_PROC;
			this.sqlType = sqlTypeInt;
			templateType = ENTRY_TYPE_QUERY;
		}
		public TemplateEntryImpl (String name) {
			this.name = name;
			templateType = ENTRY_TYPE_DML;
		}
		
		public String getName() {
			return name;
		}

		public Map getProperties() {
			return propsMap;
		}

		public QueryEntry getProperty(String name) {
			return (QueryEntry) propsMap.get(name);
		}

		public String getSqlText() {
			return sqlText;
		}

		public int getSqlType() {
			return sqlType;
		}
		
		public void addProperty(String name, QueryEntry value) {
			propsMap.put(name, value);
		}

		public String getResultType() {
			return resultTpe;
		}
		
		public Set getPropertyNames() {
			return propsMap.keySet();
		}
		
		public int getEntryType() {
			return templateType;
		}
		public void addDmlEntry (DmlEntry entry) {
			dmlList.add(entry);
		}
		public List getDmls() {
			return dmlList;
		}
		public Map getStoredProcParameters() {
			return paramMap;
		}
		public void addParameters(String paramName, StoredProcParameter param) {
			paramMap.put(paramName, param);
		}
		public void addParameters(Map map) {
			paramMap.putAll(map);
		}
	}
	
	class TemplateEntryPropertyImpl implements TemplateEntry.QueryEntry {
		
		List keys = new ArrayList();
		String name;
		String sqlText;
		int sqlType;
		
		Map paramMap = new LinkedHashMap();
		
		public TemplateEntryPropertyImpl (String name, String sqlText, String sqlType) {
			this.name = name;
			this.sqlText = sqlText;
			int sqlTypeInt = sqlType == null ? TemplateEntry.SELECT_STMT : 
					sqlType.equalsIgnoreCase("sql") ? TemplateEntry.SELECT_STMT : TemplateEntry.STORED_PROC;
			this.sqlType = sqlTypeInt;		
		}

		public String[][] getKeys() {
			
			String[][] keysArr = new String[keys.size()][2];
			for (int i=0; i<keysArr.length; i++) {
				keysArr[i][0] = ((String[]) keys.get(i))[0];
				keysArr[i][1] = ((String[]) keys.get(i))[1];
			}
			return keysArr;
		}

		public String getName() {
			return name;
		}

		public String getSqlText() {
			return sqlText;
		}

		public int getSqlType() {
			return sqlType;
		}
		
		public void addKeyPair(String srcKey, String tgtKey) {
			String [] keyPair = new String[2];
			keyPair[0] = srcKey;
			keyPair[1] = tgtKey;
			keys.add(keyPair);
		}

		public Map getStoredProcParameters() {
			return paramMap;
		}
		
		public void addParameters(String paramName, StoredProcParameter param) {
			paramMap.put(paramName, param);
		}
		
		public void addParameters(Map map) {
			paramMap.putAll(map);
		}
	}
	
	class DmlEntryImpl implements TemplateEntry.DmlEntry {
		private int dmlType = -1;
		private String sqlText;
		private int sqlType = -1;
		private String beType;
		private Map spParams = new HashMap();
		
		public DmlEntryImpl (String dmlType, String sqlText, String sqlType, String beType) {
			if (dmlType.equalsIgnoreCase("update")) {
				this.dmlType = UPDATE;
			} else if (dmlType.equalsIgnoreCase("delete")) {
				this.dmlType = DELETE;
			} else if (dmlType.equalsIgnoreCase("insert")) {
				this.dmlType = INSERT;
			}
			this.sqlText = sqlText;
			int sqlTypeInt = sqlType == null ? TemplateEntry.SELECT_STMT : 
				sqlType.equalsIgnoreCase("sql") ? TemplateEntry.SELECT_STMT : TemplateEntry.STORED_PROC;
			this.sqlType = sqlTypeInt;
			this.beType = beType;
		}
		

		public int getDmlType() {
			return dmlType;
		}

		public String getSqlText() {
			return sqlText;
		}

		public int getSqlType() {
			return sqlType;
		}

		public String getBEType() {
			return beType;
		}

		public Map getStoredProcParameters() {
			return spParams;
		}
		
		public void addParameters(Map spParams) {
			this.spParams.putAll(spParams);
		}
		
	}
	
	class StoredProcParameterImpl implements TemplateEntry.StoredProcParameter {
		
		private String name;
		private int index;
		private int beType;
		private int type;
		
		public StoredProcParameterImpl (String name, String index, String type, String beType) {
			
			this.name = name;
			
			this.index = Integer.parseInt(index);
			
			if (type.equalsIgnoreCase("in")) {
				this.type = TemplateEntry.StoredProcParameter.IN;
			} else if (type.equalsIgnoreCase("out")) {
				this.type = TemplateEntry.StoredProcParameter.OUT;
			}
			
			if(beType.equalsIgnoreCase("int")){
				this.beType = RDFTypes.INTEGER_TYPEID;
			} else if(beType.equalsIgnoreCase("long")){
				this.beType = RDFTypes.LONG_TYPEID;
			} else if(beType.equalsIgnoreCase("double")){
				this.beType = RDFTypes.DOUBLE_TYPEID;
			} else if(beType.equalsIgnoreCase("boolean")){
				this.beType = RDFTypes.BOOLEAN_TYPEID;
			} else if(beType.equalsIgnoreCase("datetime")){
				this.beType = RDFTypes.DATETIME_TYPEID;
			} else if(beType.equalsIgnoreCase("string")) {
				this.beType = RDFTypes.STRING_TYPEID;
			} else {
				this.beType = RDFTypes.CONCEPT_TYPEID;
			}
		}

		public int getBEType() {
			return beType;
		}

		public int getIndex() {
			return index;
		}

		public String getName() {
			return name;
		}

		public int getType() {
			return type;
		}
	}
	
	public void printStr() {
		
		Iterator i = templatesMap.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry e = (Entry) i.next();
			String key = (String) e.getKey();
			System.out.println("** FILE = " + key);
			Map val = (Map) e.getValue();
			Iterator j = val.entrySet().iterator();
			while (j.hasNext()) {
				Map.Entry e2 = (Entry) j.next();
				//String entryName = (String) e2.getKey();
				
				TemplateEntry te = (TemplateEntry) e2.getValue();
				System.out.println("TemplateEntry: " + te.getName());
				if (te.getEntryType() == TemplateEntry.ENTRY_TYPE_DML) {
					Iterator dmlEntries = te.getDmls().iterator();
					while (dmlEntries.hasNext()) {
						DmlEntry dml = (DmlEntry) dmlEntries.next();
						System.out.println("  dml type = " + dml.getDmlType() + " be-type = " + dml.getBEType());
						System.out.println("      sql = " + dml.getSqlText() + " sqlType = " + dml.getSqlType());
					}
					
				} else {

					System.out.println("TemplateEntry Sql: " + te.getSqlText());
					System.out.println("              SqlType: " + te.getSqlType());
					System.out.println("              result-be-type: " + te.getResultType());
					Iterator k = te.getProperties().entrySet().iterator();
					while (k.hasNext()) {
						Map.Entry e3 = (Entry) k.next();
						String propName = (String) e3.getKey();
						QueryEntry p = (QueryEntry) e3.getValue();
						System.out.println("Prop: " + propName);
						System.out.println("Prop Sql: " + p.getSqlText());
						System.out.println("Prop SqlType: " + p.getSqlType());
						String[][] keys = p.getKeys();
						for (int l = 0; l < keys.length; l++) {
							String[] keyset = keys[l];
							System.out.println("Prop Keys      src: "
									+ keyset[0] + "  tgt: " + keyset[1]);
						}
					}
				}
			}
		}
	}
	
	public String getStoredProcedure(String templatesResource, String name) throws Exception {
		TemplateEntry t = getTemplateEntry(templatesResource, name);
		if (t != null) {
			return t.getSqlText();
		}
		return null;
	}
	
	/*public void printAllSQLs(){
		Iterator iter1 = this.templatesMap.values().iterator();
		while (iter1.hasNext()) {
			Map m = (Map) iter1.next();
			Iterator iter2 = m.values().iterator();
			while (iter2.hasNext()) {
				TemplateEntry t = (TemplateEntry) iter2.next();
				Map p = t.getProperties();
				Iterator iter3 = p.keySet().iterator();
				while (iter3.hasNext()) {
					String prop = (String) iter3.next();
					QueryEntry q = t.getProperty(prop);
					if(q.getSqlType() != TemplateEntry.STORED_PROC){
						System.out.println(q.getSqlText());
					}
				}
				List l = t.getDmls();
				Iterator iter4 = l.iterator();
				while (iter4.hasNext()) {
					DmlEntry d = (DmlEntry) iter4.next();
					if(d.getSqlType() != TemplateEntry.STORED_PROC){
						System.out.println(d.getSqlText());
					}
				}
			}
		}
	}*/
	
//	public static void main(String[] args) throws Exception {
//		
//		DBTemplatesParser p = DBTemplatesParser.getInstance();
//		testMode = true;
//		p.parse(CONTENTS);
//		
//		p.printStr();
//		/*String content = FileHelper.readFileAsString("E:\\be\\Customers\\eds\\PurchaseDS.operations.xml");
//		DBTemplatesParser p = DBTemplatesParser.getInstance();
//		testMode = true;
//		p.parse(content);
//		p.printAllSQLs();*/
//		
//	}
}
