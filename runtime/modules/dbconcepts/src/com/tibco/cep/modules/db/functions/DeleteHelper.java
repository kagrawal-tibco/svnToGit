package com.tibco.cep.modules.db.functions;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.modules.db.functions.FunctionHelper.PreparedStmtDbg;
import com.tibco.cep.modules.db.model.runtime.DBConcept;
import com.tibco.cep.modules.db.service.ConstraintRegistry;
import com.tibco.cep.modules.db.service.ConstraintRegistry.Relation;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

/**
 * Helper class to delete concepts
 * @author bgokhale
 *
 */

public class DeleteHelper {
	
	static com.tibco.cep.kernel.service.logging.Logger logger;
	static {
		try {
			logger = RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger(DeleteHelper.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public static int delete (Concept concept, boolean cascade) {
		List deleteList = new ArrayList();
		try {
			LinkedHashMap pkSubset = new LinkedHashMap();
			
			//atleast the root concept needs to be a DBConcept. else, how can the primary keys be determined?
			if (concept instanceof DBConcept) {
				pkSubset = FunctionHelper.getNotNullPKs((DBConcept)concept);				
			}
			com.tibco.cep.designtime.model.element.Concept dtConcept = FunctionHelper.getDTConceptFromRT(concept);
			
			deleteList = createDeleteOrder(dtConcept, pkSubset, cascade);
			int totDels = 0;
			for (Iterator i = deleteList.iterator(); i.hasNext();) {
				FunctionHelper.PreparedStmtDbg ps = 
					(FunctionHelper.PreparedStmtDbg) i.next();
                logger.log(Level.DEBUG, "SQL: %s", ps.debugStr);
				PreparedStatement stmt = ps.stmt;
				boolean hasResults = stmt.execute();
				if (!hasResults) {
					int updtCnt = stmt.getUpdateCount();
					totDels += updtCnt;
				}
				stmt.close();
			}
			return totDels;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			for (Iterator i = deleteList.iterator(); i.hasNext();) {
				FunctionHelper.PreparedStmtDbg ps = 
					(FunctionHelper.PreparedStmtDbg) i.next();
				try {
					ps.stmt.close();
				} catch (Exception e) {
					
				}
			}
		}
	}
	

	/**
	 * Sequence of operation should be:
	 * 1. delete children (containment)
	 * 2. update references (referential)
	 * 3. delete myself
	 * Perform step 1,2,3 recursively.
	 */
	static List createDeleteOrder (com.tibco.cep.designtime.model.element.Concept cept, LinkedHashMap pkSubset, boolean cascade) throws Exception {
		//This is equivalent to List<FunctionHelper.PreparedStmtDbg>
		List preparedStmts = new ArrayList();
		if (cascade) {
			// create update statements for dependent tables.
			List updateStatements = createUpdateStatements(cept, pkSubset);
			// add to the end of the list.
			preparedStmts.addAll(updateStatements);

		}

		// then this is a DBConcept, so generate a delete statement
		FunctionHelper.PreparedStmtDbg stmt = FunctionHelper.getPrepStmt(cept, pkSubset, "DELETE");
		if (stmt != null) {
			preparedStmts.add(stmt);
		}

		if (!cascade) {
			return preparedStmts;
		}
		List l = cept.getAllPropertyDefinitions();
		for (int i=0; i<l.size(); i++) {
			PropertyDefinition pd = (PropertyDefinition) l.get(i);
			String propName = pd.getName();
			
			com.tibco.cep.designtime.model.element.Concept child = 
				pd.getConceptType();
			
			//loop over non-concept properties
			if (child == null) {
				continue;
			}
			
			
	        String key = TypeManager.DEFAULT_BE_NAMESPACE_URI + cept.getFullPath() + "." + propName;
	        ConstraintRegistry.Relation reln = 
	        	ConstraintRegistry.getInstance().getRelation(key);
	        if (reln != null && 
	        		reln.getRefType() == ConstraintRegistry.Relation.CONTAINMENT) {
				String[][] keys = reln.getKeyReferences();
				LinkedHashMap pkeys = new LinkedHashMap();
				for (int j = 0; j < keys.length; j++) {
					String parentKey = keys[j][0];
					if (pkSubset.containsKey(parentKey)) {
						pkeys.put(keys[j][1], pkSubset.get(parentKey));
					}
				}	        	
	        	List childPreparedStmts = createDeleteOrder (child, pkeys, cascade);

	        	//prepend dependent deletes... after update statements
	        	preparedStmts.addAll(0, childPreparedStmts);
	        	
	        }

		}
		return preparedStmts;
	}
	

	private static List createUpdateStatements(com.tibco.cep.designtime.model.element.Concept dtConcept, LinkedHashMap pkSubset) throws Exception {
		Map dependentCepts = getDependentCepts(dtConcept);

		List updtStmts = new ArrayList();
		
		Iterator i = dependentCepts.entrySet().iterator();
		final Connection conn = 
			JDBCHelper.getCurrentConnection();
		
		Set processedTables = new HashSet();
		
		while (i.hasNext()) {
			Map.Entry e = (Entry) i.next();
			com.tibco.cep.designtime.model.element.Concept srcCept = (com.tibco.cep.designtime.model.element.Concept) e.getKey();
			Relation rel = (Relation) e.getValue();
			
			String tableName = srcCept.getExtendedProperties().get(com.tibco.cep.modules.db.model.designtime.DBConstants.SCHEMA_NAME) + "." +
				srcCept.getExtendedProperties().get(com.tibco.cep.modules.db.model.designtime.DBConstants.OBJECT_NAME);
			if (processedTables.contains(tableName)) {
				continue;
			}
			processedTables.add(tableName);
			
				// Get the foreign key references
			String[][] fks = rel.getKeyReferences();
			StringBuffer sql = new StringBuffer(64);
			StringBuffer debugStr = new StringBuffer(64);
			sql.append("update ");
			sql.append(FunctionHelper.getDelimitedFQDBObjectName(srcCept));
			//sql.append(tableName);
			sql.append(" set ");
			
			for (int j = 0; j < fks.length; j++) {
				// Create Update statements
				sql.append(FunctionHelper.getColNameFromCeptProp(srcCept, fks[j][0]));
				sql.append("=?");
				if (j < fks.length - 1) {
					sql.append(", ");
				}
			}
			

			Map fkSubset = new LinkedHashMap();
			for (int j = 0; j < fks.length; j++) {
				// Create Update statements
				String srcProp = fks[j][0];
				String tgtProp = fks[j][1];
				if (pkSubset.containsKey(tgtProp)) {
					fkSubset.put(srcProp, tgtProp);
				}
			}

			if (fkSubset.size() > 0) {
				sql.append(" where ");

				Iterator l = fkSubset.entrySet().iterator();
				while (l.hasNext()) {
					Map.Entry m = (Entry) l.next();
					String srcProp = (String) m.getKey();
					sql.append(FunctionHelper.getColNameFromCeptProp(srcCept, srcProp));
					sql.append("=?");
					if (l.hasNext()) {
						sql.append(" and ");
					}
				}
			}
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			if (logger.isEnabledFor(Level.DEBUG)) {
				debugStr.append(sql);
				debugStr.append(" ( ");
			}
			int k = 1;
			for (int j = 0; j < fks.length; j++) {
				stmt.setObject(k, null);
				FunctionHelper.addPreparedStmtParam(stmt, null, k, debugStr);
				k++;
			}

			Iterator l = fkSubset.entrySet().iterator();
			while (l.hasNext()) {
				Map.Entry m = (Entry) l.next();
				String tgtProp = (String) m.getValue();
				PropertyAtom pa = (PropertyAtom) pkSubset.get(tgtProp);
				FunctionHelper.addPreparedStmtParam(stmt, pa, k, debugStr);
				k++;
			}
			debugStr.append(" ) ");

			PreparedStmtDbg prepStmt = new PreparedStmtDbg(stmt, debugStr.toString());
			updtStmts.add(prepStmt);
			
		}
		return updtStmts;
	}



    private static Map getDependentCepts (com.tibco.cep.designtime.model.element.Concept dtConcept) {
		
		Relation[] rels = ConstraintRegistry.getInstance().getRelations();
		Map dependentCepts = new HashMap();
		for (int i=0; i<rels.length; i++) {
			Relation r = rels[i];
			if (r.getRefType() == Relation.CONTAINMENT) {
				continue;
			}
			String ceptName = r.getName().substring(0, r.getName().lastIndexOf("."));
			String propName = r.getName().substring(r.getName().lastIndexOf(".")+1);
			ceptName = ceptName.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length());
			com.tibco.cep.designtime.model.element.Concept srcCept = dtConcept.getOntology().getConcept(ceptName);
			PropertyDefinition pd = srcCept.getPropertyDefinition(propName, false);
			if (propAssignableToCept(pd, dtConcept)) {
				dependentCepts.put(srcCept, r);
			}
		}
		return dependentCepts;
	}   

	private static boolean propAssignableToCept(PropertyDefinition pd, com.tibco.cep.designtime.model.element.Concept dtConcept) {
		if (dtConcept == null) {
			return false;
		}
		if (pd == null || (pd != null &&
			pd.getType() != PropertyDefinition.PROPERTY_TYPE_CONCEPT &&
			pd.getType() != PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE)) {
			return false;
		}
		
		com.tibco.cep.designtime.model.element.Concept dtCept = pd.getConceptType();
		if (dtCept == null) {
			return false;
		}
		if (dtCept.getName().equals(dtConcept.getName())) {
			return true;
		} else {
			return propAssignableToCept(pd, dtCept.getSuperConcept());
		}
	}

}
