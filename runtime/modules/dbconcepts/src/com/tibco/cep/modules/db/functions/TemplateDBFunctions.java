package com.tibco.cep.modules.db.functions;

import com.tibco.be.model.rdf.RDFTypes;
import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.modules.db.model.runtime.AbstractDBConceptImpl;
import com.tibco.cep.modules.db.model.runtime.DBConcept;
import com.tibco.cep.modules.db.service.DBTemplatesParser;
import com.tibco.cep.modules.db.service.TemplateEntry;
import com.tibco.cep.modules.db.service.TemplateEntry.DmlEntry;
import com.tibco.cep.runtime.model.PropertyNullValues;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.*;
import com.tibco.cep.runtime.model.element.Property.PropertyConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 *
 * @author bgokhale
 * Created 22 Jan 2008
 *
 * Query based on application metadata. For each concept/property a query will be specified.
 * the query will contain patters. A sample query that will be provided is as follows
 *
 * SELECT * FROM FLIGHT WHERE (AIRLINE_CODE = '%AIRLINE_CODE' ) AND (FLIGHT_NUMBER = '%FLIGHT_NUMBER') AND (FLIGHT_DATE = '%FLIGHT_DATE' )
 *
 * %AIRLINE_CODE is a pattern, which is a simple XPath like expression that when applied to the request message will
 * provide a value. This value will be used in the SQL.
 *
 *
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "RDBMS",
        category = "Database.Templates",
        enabled = @Enabled(property="TIBCO.CEP.modules.function.catalog.database.templates", value=false),
        synopsis = "Template Database functions")
public class TemplateDBFunctions {

	private static DBTemplatesParser mdParser = DBTemplatesParser.getInstance();
	static com.tibco.cep.kernel.service.logging.Logger logger;
	//fetch size for resultsets
	static int fetchSize = 0;
	static int threadCnt = 0;

	//set user defined sql time out on statement
	//zero means unlimited
	static int sqltimeout = 0;
	
	// Max rows in the result
	static int maxRows = 0;

	static {
		try {
			logger = RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger(TemplateDBFunctions.class);
			String fetchSizeStr = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().
				getProperty("be.dbconcepts.templates.jdbc.resultset.fetchsize", "0");
			fetchSize = Integer.parseInt(fetchSizeStr);
			String qT = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().
				getProperty("be.dbconcepts.templates.query.threads", "1");
			threadCnt = Integer.parseInt(qT);
			
			String maxRowsStr = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().
					getProperty("be.dbconcepts.templates.jdbc.resultset.maxRows", "0");
			maxRows = Integer.parseInt(maxRowsStr);

			//set user defined sql time out on statement
			//zero means unlimited
			String strSQLTimeOut = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().
				getProperty("be.dbconcepts.templates.query.sqltimeout", "0");
			sqltimeout = Integer.parseInt(strSQLTimeOut);

		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "templateQuery",
        synopsis = "Queries the underlying database using templates.",
        signature = "Concept[] query(String templatesResource, String key, Concept requestCpt, String requestId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "templatesResource", type = "String", desc = "A shared archive resource that points to a templates XML file"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "A key into the templates for this query"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "requestCpt", type = "Concept", desc = "Properties in this concept are used as templates in the template query"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "requestId", type = "A", desc = "unique string that identifies this invocation")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept[]", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Concept[] templateQuery(String templatesResource, String key, Concept requestCpt, String requestId) {
		try {

			String resultCeptType = mdParser.getResultType(templatesResource, key);
			Concept resultCept = FunctionHelper.createConcept(resultCeptType);

			String rootSql = mdParser.getSqlText(templatesResource, key);
			int rootSqlType = mdParser.getSqlType(templatesResource, key);

			Map spParams = mdParser.getStoredProcParametes(templatesResource, key);
			// will contain a map of propsPath v/s QueryResult after queryConceptUsingMetadata returns
			//Map qcMap = Collections.synchronizedMap(new HashMap());
			Map qcMap = new ConcurrentHashMap();
			if (rootSql != null) {
				executeQuery(requestCpt, requestId, "", resultCept, rootSql, rootSqlType, qcMap, spParams);

			} else {
				SQLContext qC = getQueryContext("", "", qcMap);
				qC.resultCepts.add(resultCept);
				qC.isWrapper = true;
				//since this is a wrapper object, assert it from here.
				assertObject(resultCept);
			}

			Set propsSet = mdParser.getPropertyNames(templatesResource, key);
			Iterator propNmIter = propsSet.iterator();

			/*String qT = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().
				getProperty("be.dbconcepts.templates.query.threads", "1");
			int threadCnt = 1;
			try {
				threadCnt = Integer.parseInt(qT);
			} catch (Exception e) {

			}*/
			/*ExecutorService es = null;
			if (threadCnt == 1) { //default to a single thread.
				es = Executors.newSingleThreadExecutor();
			} else if (threadCnt > 1) {
				es = Executors.newFixedThreadPool(threadCnt);
			} else { //anything else, use cached (dynamic) thread pool
				es = Executors.newCachedThreadPool();
			}*/
			ExecutorService es = ExecutorServiceProvider.getExecutorService();
			CountDownLatch doneSignal = new CountDownLatch(propsSet.size());
			QueryExecutorWorker [] workers = new QueryExecutorWorker[propsSet.size()];
			int index = 0;
			while (propNmIter.hasNext()) {
				String propsPath = (String) propNmIter.next();
				QueryExecutorWorker worker = new QueryExecutorWorker(templatesResource, key, propsPath, requestCpt,
						requestId, resultCept, qcMap, doneSignal);
				workers[index++] = worker;
				es.execute(worker);
			}

			doneSignal.await();
			//es.shutdown();

			//check if there was an exception in any thread. if so, throw it.
			for (int i=0; i<workers.length; i++) {
				if (workers[i].getException() != null) {
					throw workers[i].getException();
				}
			}

			/*while (propNmIter.hasNext()) {
				String propsPath = (String) propNmIter.next();
				String sqlText = mdParser.getSqlForProperty(templatesResource, key, propsPath);
				int sqlType = mdParser.getSqlTypeForProperty(templatesResource, key, propsPath);
				Concept dummyPropCeptInst = FunctionHelper.getPropertyConcept(resultCept, propsPath);
				spParams = mdParser.getStoredProcParametes(templatesResource, key, propsPath);
				executeQuery(requestCpt, requestId, propsPath, dummyPropCeptInst, sqlText, sqlType, qcMap, spParams);
			}*/

			makeLinks(templatesResource, key, resultCept, qcMap, requestId);

			SQLContext qC = (SQLContext) qcMap.get("");
			Concept[] arr = (Concept[])Array.newInstance(resultCept.getClass(), qC.resultCepts.size());
			return (Concept[])qC.resultCepts.toArray(arr);
		} catch (Exception e) {
			logger.log(Level.ERROR, "TemplateQuery: %s error while querying. %s", requestId, e.getMessage());
			throw new RuntimeException(e);
		}
	}



	@com.tibco.be.model.functions.BEFunction(
        name = "templateDml",
        synopsis = "Performs DML operations based on templates.",
        signature = "int[] templateDml(String templatesResource, String key, Concept requestCpt, String requestId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "templatesResource", type = "String", desc = "A shared archive resource that points to a templates XML file"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "A key into the templates for these DML operations"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "requestCpt", type = "Concept", desc = "Properties in this concept are used as templates in the DML operations"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "requestId", type = "String", desc = "A unique string that identifies this invocation")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int[]", desc = "array of integers. At every index is the number of rows affected by the corresponding SQL"),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Performs a DML operation using the template passed as argument.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )

	public static int[] templateDml(String templatesResource, String key, Concept requestCpt, String requestId) {

		if(templatesResource == null || "".equals(templatesResource)){
			throw new IllegalArgumentException("template resource can not be null");
		}
		if(key == null || "".equals(key)){
			throw new IllegalArgumentException("key can not be null");
		}
		if(requestCpt == null){
			throw new IllegalArgumentException("request concept can not be null");
		}

		try {
			List dmlEntries = mdParser.getDmlEntries(templatesResource, key);
			int[] retArr = new int[dmlEntries.size()];
			int j = 0;
			Iterator i = dmlEntries.iterator();

			while (i.hasNext()) {
				DmlEntry e = (DmlEntry) i.next();
				String reqCeptNm = requestCpt.getExpandedName().getNamespaceURI().substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length());
				if(!e.getBEType().equals(reqCeptNm)){
					continue;
				}

				String sqlText = e.getSqlText();
				int sqlType = e.getSqlType();
				String beType = e.getBEType();

				Map spParams = e.getStoredProcParameters();
				// will contain a map of propsPath v/s QueryResult after queryConceptUsingMetadata returns
				Map qcMap = new HashMap();


				SQLContext qC = getQueryContext("", "", qcMap);
				qC.sql = sqlText;
				qC.sqlType = sqlType;
				qC.ceptFQN = beType;

				logger.log(Level.DEBUG, "ExecuteDML -%n"
                        + "Request  Id: %s%n"
                        + "PropertyRef: %s%n"
                        + "SQL Type   : %s%n"
                        + "SQL Text   : %s",
                        requestId, e.getBEType(), sqlType, sqlText);

				if(sqlType == TemplateEntry.SELECT_STMT){
					executeDML (qC, requestCpt, requestId);
				} else if(sqlType == TemplateEntry.STORED_PROC){
					executeStoredProc (qC, null, requestCpt, requestId, spParams);
				}

                logger.log(Level.DEBUG, "ExecuteDML -%n"
                        + "Request Id: %s%n"
                        + "TimeStats : %s",
                        requestId, qC.getTimeInfo());

				retArr[j++] = qC.updateCnt;

			}
			return retArr;
		} catch (Exception e) {
			logger.log(Level.ERROR, e, "TemplateDML: %s error while executing DML", requestId);
			throw new RuntimeException(e);
		}
	}

	private static void makeLinks(String metadataURL, String operationName, Concept cept, Map qcMap, String requestId) throws Exception {

		Iterator i = qcMap.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry e = (Entry) i.next();
			String propsPath = (String) e.getKey();
			joinPropToParent(metadataURL, operationName, propsPath, cept, qcMap, requestId);
		}
	}

	private static void joinPropToParent(String metadataURL, String operationName, String propsPath, Concept cept, Map qcMap, String requestId) throws Exception {
		String prop = null;
		String parentPropPath = null;
		int lastPropIndex = propsPath.lastIndexOf(".");
		if (lastPropIndex == -1) {
			prop = propsPath;
			parentPropPath = "";
		} else {
			prop = propsPath.substring(lastPropIndex + 1);
			parentPropPath = propsPath.substring(0, lastPropIndex);
		}
		//get the override keyset for this propsPath
		String joinKeys[][] = mdParser.getKeysForProperty(metadataURL, operationName, propsPath);
		if (joinKeys.length == 0) {
			// use standard keys from constraint registry
			joinKeys = FunctionHelper.getFullKeys(cept, propsPath);
		}

		joinCepts(prop, parentPropPath, propsPath, joinKeys, qcMap);

	}

	private static void executeQuery(Concept requestCpt, String requestId,
			String propsPath, Concept responseCpt, String sqlText,
			int sqlType, Map qcMap, Map spParams) throws Exception {
		SQLContext qC = getQueryContext("", propsPath, qcMap);
		qC.sql = sqlText;
		qC.sqlType = sqlType;

		logger.log(Level.DEBUG, "Execute Query - "
                + "Request Id   : %s%n"
                + "Property Path: %s%n"
                + "SQL Type     : %s%n"
                + "SQL Text     : %s",
                requestId, propsPath, sqlType, sqlText);

		if(sqlType == TemplateEntry.SELECT_STMT){
			executeSQL (qC, responseCpt, requestCpt, requestId);
		} else if(sqlType == TemplateEntry.STORED_PROC){
			executeStoredProc (qC, responseCpt, requestCpt, requestId, spParams);
		}

        logger.log(Level.DEBUG, "Query: %s TimeStats: %s", requestId, qC.getTimeInfo());
	}

	private static SQLContext getQueryContext (String conceptFQN, String propsPath, Map qcMap) {

		String key = propsPath;
		SQLContext qC = null;
		if ( (qC = (SQLContext) qcMap.get(key)) == null) {
			qC = new SQLContext();
			qC.ceptFQN = conceptFQN;
			qC.propsPath = propsPath;
			qcMap.put(key, qC);
		}
		return qC;
	}



	private static void joinCepts (String propName, String parentPropPath, String propsPath, String[][] joinKeys, Map qcMap) {
		SQLContext qC = (SQLContext) qcMap.get(parentPropPath);
		SQLContext qC2 = (SQLContext) qcMap.get(propsPath);
		if(qC == null) {
			throw new NullPointerException("template entry missing for property reference \"" + (qC == null? parentPropPath : propsPath) + "\"");
		}

		List parentCepts = qC.resultCepts;
		List childCepts = qC2.resultCepts;

		for (int i = 0; i < parentCepts.size(); i++) {
			Concept parentCept = (Concept) parentCepts.get(i);
			Property prop = parentCept.getProperty(propName);
			if (prop == null || prop != null && ! (prop instanceof PropertyConcept)) {
                logger.log(Level.WARN, "Property does not exist in parent cept: %s: %s",
                        parentCept.getExpandedName().localName, propName);
				continue;
			}
			for (int j = 0; j < childCepts.size(); j++) {
				Concept child = (Concept) childCepts.get(j);
				int k = 0;
				for (k = 0; k < joinKeys.length; k++) {
					String srcKey = joinKeys[k][0];
					String tgtKey = joinKeys[k][1];

					Property srcProp = parentCept.getProperty(srcKey);
					if (!(srcProp instanceof PropertyAtom)) {
						logger.log(Level.WARN, "Join (source) key %s not an atom property", srcKey);
						break;
					}
					Object srcPropVal = ((PropertyAtom) srcProp).getValue();

					Property tgtProp = child.getProperty(tgtKey);
					if (!(tgtProp instanceof PropertyAtom)) {
						logger.log(Level.WARN, "Join (target) key %s not an atom property", tgtKey);
						break;
					}

					Object tgtPropVal = ((PropertyAtom) tgtProp).getValue();
					if (srcPropVal == null || tgtPropVal == null) {
						// cant compare null keys
						break;
					}
					if (tgtPropVal.hashCode() != srcPropVal.hashCode()) {
						// found keys that are not equal, so break
						break;
					}
					// hashCodes are equal, now see if values are equal.
					if (!tgtPropVal.equals(srcPropVal)) {
						// found keys that are not equal, so break
						break;
					}
				}
				if (k == joinKeys.length || qC.isWrapper) {
					// ==> all keys are equal or parent is a wrapper concept
					setPropertyConcept((PropertyConcept)prop, child);
				}
			}
		}

	}

	private static void setPropertyConcept (PropertyConcept prop, Concept cept) {
		if (prop instanceof PropertyAtomConceptReference) {
			((PropertyAtomConceptReference)prop).setConcept((Concept)cept);
		} else if (prop instanceof PropertyArrayConceptReference) {
			((PropertyArrayConceptReference)prop).add(cept);
		} else if (prop instanceof PropertyAtomContainedConcept) {
			((PropertyAtomContainedConcept)prop).setContainedConcept((ContainedConcept)cept);
		} else if (prop instanceof PropertyArrayContainedConcept) {
			((PropertyArrayContainedConcept)prop).add((ContainedConcept)cept);
		}
	}

	private static void assertObject(Concept instance) {
		RuleSession session = RuleSessionManager.getCurrentRuleSession();
		try {
			session.assertObject(instance, true);
		} catch (DuplicateExtIdException e) {
			logger.log(Level.ERROR, e, "Failed to assert concept to WM.");
		}
	}

	private static PSInfo getPSInfo(SQLContext qC, Concept requestCpt, String requestId) throws Exception {
		SQLHelper helper = new SQLHelper(qC.sql);

		Set patterns = helper.getPatterns();

		PSInfo psInfo = null;
		try {
			Map map = getPatternValues(requestCpt, patterns);
			psInfo = helper.getPreparedStmtInfo(map);
            logger.log(Level.DEBUG, "Modified query: %s %s", requestId, psInfo.getSqlWithVal());
		} catch (Exception e) {
			if (logger != null) {
                logger.log(Level.ERROR, e, "");
			}
			throw new Exception("Error while creating prepared statement for sql : " + qC.sql + " \n" + e.getMessage());
		}

		return psInfo;
	}

	private static void executeSQL(SQLContext qC, Concept dummyInst,
			Concept requestCpt, String requestId) throws Exception {

		long t1, t2;

		PSInfo psInfo = getPSInfo(qC, requestCpt, requestId);

		if (!(dummyInst instanceof DBConcept)) {
			logger.log(Level.WARN, "**Warning**: Concept %s is not mapped to a DBConcept",
                    dummyInst.getExpandedName().getLocalName());
			return;
		}

		String ceptName = ((ConceptImpl)dummyInst).getType();
		ceptName = ceptName.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length());

		String connName = ((AbstractDBConceptImpl) dummyInst).getJDBCResourceName();
		Connection conn = JDBCHelper.getCurrentConnection(connName);

		t1 = System.currentTimeMillis();

		PreparedStatement stmt = null;
		try {

			stmt = FunctionHelper.getPreparedStatement(psInfo, conn, qC.sqlType);
			if (stmt == null) {
				return;
			}

			stmt.setFetchSize(fetchSize);
			
			stmt.setMaxRows(maxRows);

			//set user defined sql time out on statement
			//zero means unlimited
			stmt.setQueryTimeout(sqltimeout);

			ResultSet rs = stmt.executeQuery();

			List cepts = new ArrayList();
			while (rs.next()) {
				DBConcept dbconcept = (DBConcept) FunctionHelper.createConcept(ceptName);
				dbconcept.setProperties(rs);
				cepts.add(dbconcept);
			}
			rs.close();

			t2 = System.currentTimeMillis();
			qC.executeTime = t2 - t1;
			qC.rowCount = cepts.size();
			qC.resultCepts = cepts;

		} catch (Exception e) {
            logger.log(Level.ERROR, e, "ExecuteSQL: %s Error while executing SQL %s%nException Message: %s",
                    requestId, qC.sql, e.getMessage());
			throw e;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {}

			JDBCHelper.unsetConnection(connName, conn);
		}
	}

	private static void executeDML(SQLContext qC, Concept requestCpt, String requestId) throws Exception {

		long t1, t2;
		t1 = System.currentTimeMillis();
		PSInfo psInfo = getPSInfo(qC, requestCpt, requestId);

		Connection conn = JDBCHelper.getCurrentConnection();
		if(conn == null){
			throw new Exception("Connection is not set. Operation failed for " + qC.propsPath);
		}

		PreparedStatement stmt = FunctionHelper.getPreparedStatement(psInfo, conn, qC.sqlType);
		if (stmt == null) {
			return;
		}

		try {

			stmt.setFetchSize(fetchSize);

			//set user defined sql time out on statement
			//zero means unlimited
			stmt.setQueryTimeout(sqltimeout);

			int updateCnt = stmt.executeUpdate();
			t2 = System.currentTimeMillis();
			qC.executeTime = t2 - t1;
			qC.updateCnt = updateCnt;

		} catch (Exception e) {
            logger.log(Level.ERROR, e, "ExecuteDML: %s Error while executing DML %s%nException message: %s",
                    requestId, qC.sql, e.getMessage());
			throw e;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {}
		}
	}

	private static void executeStoredProc(SQLContext qC, Concept dummyInst,
			Concept requestCpt, String requestId, Map spParams) throws Exception {

		long t1;
		long t2;
		String connName = null;
		Connection conn = null;
		Object stmt = null;

		try {

			if(dummyInst != null && dummyInst instanceof AbstractDBConceptImpl){
				connName = ((AbstractDBConceptImpl) dummyInst).getJDBCResourceName();
				conn = JDBCHelper.getCurrentConnection(connName);
				stmt = FunctionHelper.getCallableStatement(conn, qC.sql);
			} else {
				stmt = StoredProcsHelper.initStoredProc(qC.sql);
			}

			if(stmt == null){
				logger.log(Level.ERROR, "Stored procedure %s could not be initialized", qC.sql);
				return;
			}

			t1 = System.currentTimeMillis();
			Iterator<TemplateEntry.StoredProcParameter> iter = spParams.values().iterator();
			while (iter.hasNext()) {
				TemplateEntry.StoredProcParameter parameter = (TemplateEntry.StoredProcParameter) iter
						.next();
				if(parameter.getType() == TemplateEntry.StoredProcParameter.OUT){
					StoredProcsHelper.setOutputParameterType(stmt, parameter.getIndex(), parameter.getBEType());
				} else {
					List values = getPatternValues(requestCpt,parameter.getName());
					if(values.size() > 0) {
						Object value = values.get(0);
						StoredProcsHelper.setInputParameter(stmt, parameter.getIndex(), value);
					} else {
						//logger.log(Level.ERROR, "Value not available at index " + parameter.getIndex() + " to execute stored procedure " + qC.sql);
						StoredProcsHelper.setInputParameter(stmt, parameter.getIndex(), null);
					}
				}
			}

			StoredProcsHelper.executeStoredProc(stmt);

			List returnCepts = new ArrayList();
			Iterator<TemplateEntry.StoredProcParameter> iterator = spParams.values().iterator();
			while (iterator.hasNext()) {
				TemplateEntry.StoredProcParameter parameter = (TemplateEntry.StoredProcParameter) iterator
						.next();
				if(parameter.getType() == TemplateEntry.StoredProcParameter.OUT){
					if(dummyInst != null && parameter.getBEType() == RDFTypes.CONCEPT_TYPEID){
						String conceptNS = dummyInst.getExpandedName().getNamespaceURI().substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length());
						Concept[] cepts = StoredProcsHelper.getConceptsAtIndex(stmt, parameter.getIndex(), conceptNS);
						for (int i = 0; i < cepts.length; i++) {
							Concept concept = cepts[i];
							returnCepts.add(concept);
						}

					} else {
						//TODO non-concept type out parameters, not doing anything at present
						//Object value = StoredProcsHelper.getObjectAtIndex(stmt, parameter.getIndex());
					}
				}
			}
			t2 = System.currentTimeMillis();
			qC.executeTime = t2 - t1;
			qC.resultCepts = returnCepts;
		} catch (Exception e) {
            logger.log(Level.ERROR, e,
                    "ExecuteStoredProc: %s Error while executing stored proc %s%nException messsage %s",
                    requestId, qC.sql, e.getMessage());
			throw e;
		} finally {

			if (stmt != null) {
				StoredProcsHelper.closeStoredProc(stmt);
			}

			if(conn != null){
				JDBCHelper.unsetConnection(connName, conn);
			}
		}
	}

	private static Map getPatternValues(Concept cept, Set patterns) {
		Map patternValues = new HashMap();
		Iterator iter = patterns.iterator();

		while (iter.hasNext()) {
			SQLPattern p = (SQLPattern) iter.next();
			List values = getPatternValues(cept, p.getPattern());
			if (values.size() > 0) {
				SQLPattern pattern = new SQLPattern();
				pattern.setPattern(p.getPattern());
				pattern.setPatternType(p.getPatternType());
				pattern.setPatternValues(values);
				patternValues.put(p.getPattern(), pattern);
			}
		}

		return patternValues;
	}

	private static List getPatternValues(Concept c, String pattern) {

		List values = new ArrayList();
		// $A/B/C ==> $A, B, C
		if (pattern.startsWith(SQLHelper.PATTERN_SYMBOL)) {
			pattern = pattern.substring(SQLHelper.PATTERN_SYMBOL.length());
		}
		String[] tokens = pattern.split("/");

		Concept currCept = c;
		int i = 0;
		for (; i < tokens.length - 1; i++) {
			currCept = getChildConceptVal(currCept, tokens[i]);
		}
		//now check for final property -- this may be a collection
		values = getPropValues(currCept, tokens[i]);
        logger.log(Level.DEBUG, "Pattern: %s, value: %s", pattern, values);

		return values;
	}

	private static Concept getChildConceptVal(Concept concept, String propName) {
		if (concept != null) {
			Property childProp = ((ConceptImpl) concept)
					.getPropertyNullOK(propName);
			if (childProp != null
					&& childProp instanceof PropertyAtomConcept) {
				Concept c = ((PropertyAtomConcept) childProp).getConcept();
				return c;
			}
		}
		return null;
	}

	private static List getPropValues(Concept cept, String propName) {

		List values = new ArrayList();

		if (cept != null) {

			Property prop = ((ConceptImpl) cept)
			.getPropertyNullOK(propName);
			if (prop == null) {
				return values;
			}

			if (prop instanceof PropertyAtom) {
				Object val = null;
				if (!PropertyNullValues
						.isPropertyValueNull((PropertyAtom) prop)) {
					val = ((PropertyAtom) prop).getValue();
					if (val != null && val instanceof GregorianCalendar) {
						val = new java.sql.Timestamp(
								((GregorianCalendar) val).getTimeInMillis());
					}
					values.add(val);
				} else {
					values.add(null);
				}

			} else if (prop instanceof PropertyArray) {
				PropertyAtom pAtom[] = ((PropertyArray) prop).toArray();
				for (int i = 0; i < pAtom.length; i++) {
					Object val = null;
					if (!PropertyNullValues.isPropertyValueNull(pAtom[i])) {
						val = pAtom[i].getValue();
						if (val != null && val instanceof GregorianCalendar) {
							val = new java.sql.Timestamp(
									((GregorianCalendar) val)
									.getTimeInMillis());
						}
						values.add(val);
					} else {
						values.add(null);
					}
				}
			}
		}

		return values;
	}

	static class SQLContext {
		String sql;
		int sqlType;
		String ceptFQN;
		String propsPath;
		boolean isWrapper;

		List resultCepts = new ArrayList();
		int updateCnt = -1;
		long executeTime;
		long rowCount;
		//long getConnTime;
		//long getStmtTime;

		public String getTimeInfo() {
			StringBuffer b = new StringBuffer(256);
			//b.append(" CONNECTION TIME: ").append(getConnTime);
			//b.append(" STMT TIME: ").append(getStmtTime);
			b.append(" EXECUTION TIME: ").append(executeTime);
			b.append(" ROWCOUNT: ").append(rowCount);
			return b.toString();
		}
	}

	static class QueryExecutorWorker implements Runnable {

		String templatesResource;
		String key;
		String propsPath;
		Concept requestCept;
		String requestId;
		Concept resultCept;
		Map qcMap;
		CountDownLatch doneFlag;

		//Store the throwable if any. this will be used after all the thread complete to tell if
		//there was an error in any task. if so, an exception will be thrown.
		Exception exception = null;

		public QueryExecutorWorker (String templatesResource, String key, String propsPath, Concept requestCept, String requestId,
				Concept resultCept,  Map qcMap, CountDownLatch doneFlag) {
			this.templatesResource = templatesResource;
			this.key = key;
			this.propsPath = propsPath;
			this.requestCept = requestCept;
			this.requestId = requestId;
			this.resultCept = resultCept;
			this.qcMap = qcMap;
			this.doneFlag = doneFlag;
		}

		public void run() {
			String sqlText = null;
			int sqlType = 0;
			try {
				sqlText = mdParser.getSqlForProperty(templatesResource, key, propsPath);
				sqlType = mdParser.getSqlTypeForProperty(templatesResource, key, propsPath);
				Concept dummyPropCeptInst = FunctionHelper.getPropertyConcept(resultCept, propsPath);
				String connName = ((AbstractDBConceptImpl) dummyPropCeptInst).getJDBCResourceName();
				JDBCHelper.setCurrentConnection(connName);
				Map spParams = mdParser.getStoredProcParametes(templatesResource, key, propsPath);
				executeQuery(requestCept, requestId, propsPath, dummyPropCeptInst, sqlText, sqlType, qcMap, spParams);
			} catch (Exception e) {
				this.exception  = e;
                logger.log(Level.ERROR, e,
                        "ExecuteQuery: %s Template entry=%s, propertyReference=%s, SQL text=%s%nException message: %s",
                        this.requestId, this.key, this.propsPath, sqlText, e.getMessage());
			} finally {
				try {
					doneFlag.countDown();
				} catch (Exception e) {

				}
				JDBCHelper.unsetConnection();
			}
		}

		public Exception getException() {
			return exception;
		}
	}

	static class ExecutorServiceProvider {

		private static ExecutorService es;

		public static synchronized ExecutorService getExecutorService(){
			if(es == null){
				if (threadCnt == 1) { //default to a single thread.
					es = Executors.newSingleThreadExecutor();
				} else if (threadCnt > 1) {
					es = Executors.newFixedThreadPool(threadCnt);
				} else { //anything else, use cached (dynamic) thread pool
					es = Executors.newCachedThreadPool();
				}
			}
			return es;
		}

	}
}
