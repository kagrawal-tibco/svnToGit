package com.tibco.cep.modules.db.functions;


import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.modules.db.model.runtime.DBConcept;
import com.tibco.cep.modules.db.service.ConstraintRegistry;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 11, 2007
 * Time: 4:27:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class InsertHelper {
	
	private static boolean triggerExists =  !(System.getProperty("TIBCO.CEP.modules.function.insert.pk_triggers", "false").
													equalsIgnoreCase("false"));
	static Logger logger;
	
	static {
		try {
			logger = RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger(InsertHelper.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public static Concept insert(Concept concept) {
		try {

			// this map holds dbconcept to concept property entries
			// a contained or reference concept which is property of a concept
			// needs to be mapped to its property so as to find its parent
			// concept when required 
			Map conceptToProp = new HashMap();

			List insertList = createInsertOrder(concept,conceptToProp);

			for (Iterator i = insertList.iterator(); i.hasNext();) {
				DBConcept dbConcept = (DBConcept) i.next();
				copyKeys(dbConcept, 'C', conceptToProp);
				if (triggerExists) {
					dbConcept.insert();
					setUniqueIdentifierValue(dbConcept);
				} else {
					setUniqueIdentifierValue(dbConcept);
					String[] primaryKeys = dbConcept.getPrimaryKeyNames();
					dbConcept.insert(primaryKeys);
				}
				copyKeys(dbConcept, 'R', conceptToProp);

			}
			return concept;

		} catch (Exception e) {
			//JDBCHelper.rollback(); --> onus on caller
			throw new RuntimeException(e);
		}
	}
	  
    /**
     * @param concept
     * @return
     */
    private static List createInsertOrder(Concept concept, Map conceptToProp) throws Exception {

        List insertList = new ArrayList();
        if (concept == null) {
        	return insertList;
        }
        if (concept instanceof DBConcept) {
            //setSequenceValue(concept);
            insertList.add(concept);
        }

        Property[] properties = ((ConceptImpl)concept).getPropertiesNullOK();
        for (int i = 0; i < properties.length; i++) {
            Property prop = properties[i];
            if (prop == null) {
            	continue;
            }
            String name = prop.getName();

            if (!(prop instanceof Property.PropertyConcept)) {
            	continue;
            }

            List childList = new ArrayList();
            if (prop instanceof PropertyAtomContainedConcept) {
            	ContainedConcept cc = ((PropertyAtomContainedConcept) prop).getContainedConcept();
            	if (cc != null) {
            		conceptToProp.put(cc, prop);
                    childList = createInsertOrder(cc, conceptToProp);
            	}
            } else if (prop instanceof PropertyAtomConceptReference) {
            	Concept cc = ((PropertyAtomConceptReference) prop).getConcept();
            	if (cc != null) {
                	conceptToProp.put(cc, prop);
                	childList = createInsertOrder(cc, conceptToProp);
            	}
                
            } else if (prop instanceof PropertyArrayContainedConcept) {
                PropertyArrayContainedConcept pacc = ((PropertyArrayContainedConcept) prop);
                int len = pacc.length();
                for (int j = 0; j < len; j++) {
                    PropertyAtomContainedConcept pc = (PropertyAtomContainedConcept) pacc.get(j);
                    ContainedConcept cc = pc.getContainedConcept();
                    if (cc != null) {
                    	conceptToProp.put(cc, prop);
                    	List propAtomCCList = createInsertOrder(cc, conceptToProp);
                    	childList.addAll(propAtomCCList);
                    }
                }
            } else if (prop instanceof PropertyArrayConceptReference) { 
                PropertyArrayConceptReference pacr = ((PropertyArrayConceptReference) prop);
                int len = pacr.length();
                for (int j = 0; j < len; j++) {
                    PropertyAtomConceptReference pc = (PropertyAtomConceptReference) pacr.get(j);
                    Concept cc = pc.getConcept();
                    if (cc != null) {
                    	conceptToProp.put(cc, prop);
                    	List propAtomCCList = createInsertOrder(cc, conceptToProp);
                    	childList.addAll(propAtomCCList);
                    }
                }
            }

            //check if this is a reference or a containment
            ConstraintRegistry.Relation reln = ConstraintRegistry.getInstance().getDerivedRelation(concept, name);
            if (reln != null) {
                char refType = reln.getRefType();
                if (refType == ConstraintRegistry.Relation.CONTAINMENT) {
                    // if containment, append to insertlist
                    insertList.addAll(childList);
                } else if (refType == ConstraintRegistry.Relation.REFERENCE) {
                    // if reference, prepend to insertlist
                    insertList.addAll(0, childList);
                }
            } else {
                insertList.addAll(childList);
            }
        }

        return insertList;


    }

    private static void copyKeysToContainedConcept(Concept referencer, Concept referencee, String propName) throws Exception{

//        if (concept instanceof DBConcept) {
//            //Pass on the primary keys of the parent to child;
//            if ((parentConcept != null) && (parentConcept instanceof DBConcept)) {
//            	
//            	((AbstractDBConceptImpl)parentConcept).getPrimaryKeyNames();
//            	
//            	
//            	java.util.Set pkeys = ((DBConcept)parentConcept).getPrimaryKeyMap().entrySet();
//                for (Iterator r=pkeys.iterator();r.hasNext();) {
//                    Map.Entry entry = (Map.Entry)r.next();
//                    PropertyAtom pa = (PropertyAtom) entry.getValue();
//                    String name = ((String) entry.getKey()).trim();
//                    if (pa != null) {
//                        PropertyAtom ppa = concept.getPropertyAtom(name);
//                        if (ppa != null) {
//                            ppa.setValue(pa.getValue());
//                        }
//                        else {
//                            if (logger.isDebug()) {
//                            	logger.logDebug("ppa is null - " + name);
//                            }
//                        }
//                    }
//                }
//            }
//        }
    	
    	// copy from referencer --> referencee

    	ConstraintRegistry.Relation reln = ConstraintRegistry.getInstance().getDerivedRelation(referencer, propName);
        if (reln != null) {
            String keyRefs[][] = reln.getKeyReferences();
            for (int i = 0; i < keyRefs.length; i++) {
                Property p = ((ConceptImpl)referencer).getPropertyNullOK(keyRefs[i][0]);
                if (p != null) {
                	PropertyAtom pa = (PropertyAtom)p;
                	referencee.getPropertyAtom(keyRefs[i][1]).setValue(pa.getValue());
                }
            }
        }	
    }

    private static void copyKeysFromConceptRef(Concept referencer, Concept referencee, String propName) throws Exception{
 
    	// copy from referencee --> referencer

    	ConstraintRegistry.Relation reln = ConstraintRegistry.getInstance().getDerivedRelation(referencer, propName);
        if (reln != null) {
            String keyRefs[][] = reln.getKeyReferences();
            for (int i = 0; i < keyRefs.length; i++) {
                Property p = ((ConceptImpl)referencee).getPropertyNullOK(keyRefs[i][1]);
                if (p != null) {
                	PropertyAtom pa = (PropertyAtom)p;
                	referencer.getPropertyAtom(keyRefs[i][0]).setValue(pa.getValue());
                }
            }
        }
    }

    private static void copyKeys(Concept concept, char refType, Map conceptToProp) throws Exception{

    	Property prop = (Property) conceptToProp.get(concept);
    	if (prop == null) {
    		return;
    	}
    	
        Concept parentConcept = prop.getParent();

        if (parentConcept == null) {
        	logger.log(Level.ERROR, "Map is wrong");
            return;
        }
        
        ConstraintRegistry.Relation reln = ConstraintRegistry.getInstance().
    	getDerivedRelation(parentConcept, prop.getName());

        if ((reln != null) && (reln.getRefType() == refType)) {
            if (reln.getRefType() == ConstraintRegistry.Relation.CONTAINMENT) {
                copyKeysToContainedConcept (parentConcept, concept, prop.getName());
            } else if (reln.getRefType() == ConstraintRegistry.Relation.REFERENCE) {
                copyKeysFromConceptRef (parentConcept, concept, prop.getName());
            }
        }

    }

    /*
     * Given a dbconcept, if there is a entry for concept property in unique id registry,
     * unique id is generated by executing either sp, sql query or sequence(oracle) 
     * and set as concept property value.
     * 
     * If stored procedure has higher preference then sql query and then oracle sequence
     */
    private static void setUniqueIdentifierValue(DBConcept concept) throws Exception {
        ConstraintRegistry.UniqueIdentifier[] seqArr = ConstraintRegistry.getInstance().getUniqueIdentifier(concept);
        for (int i = 0; i < seqArr.length; i++) {
        	ConstraintRegistry.UniqueIdentifier seq = seqArr[i];
        	String propertyName = seq.getPropertyName();
        	String seqCall = seq.getUniqueIdentifierName();
        	if (seq.getQueryType().equals(ConstraintRegistry.UniqueIdentifier.QueryType.STORED_PROC)){
                setUniqueIdValUsingStoredProc(concept, propertyName,
						seqCall);
        	} else if (seq.getQueryType().equals(ConstraintRegistry.UniqueIdentifier.QueryType.QUERY)){
                setUniqueIdValUsingQuery(concept, propertyName, seqCall);
        	} else if (seq.getQueryType().equals(ConstraintRegistry.UniqueIdentifier.QueryType.ORACLE_SEQ)){
        		setUniqueIdValUsingSequence(concept, propertyName, seqCall);
        	}
        }
    }

	/**
	 * sql query is executed and 1st resultset object is used to set concept property
	 * @param concept
	 * @param propertyName
	 * @param seqCall
	 * @throws SQLException
	 */
	private static void setUniqueIdValUsingQuery(DBConcept concept,
			String propertyName, String seqCall)
			throws SQLException {
		Connection conn = JDBCHelper.getCurrentConnection();
		Statement stmt = conn.createStatement();
		try {
		    ResultSet rs = stmt.executeQuery(seqCall);
		    while (rs.next()) {
		    	Object uniqueId = rs.getObject(1);
		    	if(uniqueId instanceof BigDecimal){
		    		uniqueId = ((BigDecimal) uniqueId).longValue();
		    	}
		        logger.log(Level.DEBUG, "UniqueId for %s : %s", seqCall, uniqueId);
		        concept.getPropertyAtom(propertyName).setValue(uniqueId);
		        break;
		    }
		    rs.close();
		} finally {
		   if (stmt != null) {
			   stmt.close();
		   }
		}
	}

	/**
	 * stored procedure is executed and out param at 1st index is used to set concept property
	 * @param concept
	 * @param propertyName
	 * @param seqCall
	 * @throws SQLException
	 */
	private static void setUniqueIdValUsingStoredProc(DBConcept concept,
			String propertyName, String seqCall)
			throws SQLException {
		Connection conn = JDBCHelper.getCurrentConnection();
		CallableStatement stmt = conn.prepareCall(seqCall);
		stmt.registerOutParameter(1, Types.VARCHAR);
		try {
		    stmt.execute();
		    Object uniqueId =  stmt.getObject(1);
		    if (uniqueId != null) {
		        logger.log(Level.DEBUG, "UniqueId for %s : %s", seqCall, uniqueId);
		        concept.getPropertyAtom(propertyName).setValue(uniqueId);
		    }
		} finally {
		   if (stmt != null){
			   stmt.close();
		   }
		}
	}
	
	/**
	 * sequence value is retrieved and set to property
	 * @param concept
	 * @param propertyName
	 * @param seqCall
	 * @throws SQLException
	 */
	private static void setUniqueIdValUsingSequence(DBConcept concept,
			String propertyName, String seqCall)
			throws Exception {
		Connection conn = JDBCHelper.getCurrentConnection();
    	String valType = "NEXTVAL";
        if (triggerExists) {
        	valType = "CURRVAL";
        }
        seqCall = "select " + seqCall + "." + valType +" from dual";
        Statement stmt = conn.createStatement();
        try {
            ResultSet set = stmt.executeQuery(seqCall);
            if (set.next()) {
                long seqValue = set.getLong(1);
                logger.log(Level.DEBUG, "Sequence is: %s value=%s", seqCall, seqValue) ;
                concept.getPropertyAtom(propertyName).setValue(String.valueOf(seqValue));
            }
            set.close();
        } finally {
           if(stmt != null){
        	   stmt.close();
           }
        }
	}
}
