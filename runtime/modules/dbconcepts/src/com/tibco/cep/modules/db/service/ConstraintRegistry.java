package com.tibco.cep.modules.db.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.modules.db.model.designtime.DBConstants;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * Created by IntelliJ IDEA. User: ssubrama Date: Jun 18, 2007 Time: 10:26:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConstraintRegistry {

	private static ConstraintRegistry thisInstance;

	private HashMap registry = new HashMap();

	// map of concept uri to set of uniqueidentifier
	private HashMap sequences = new HashMap();
	
	public static ConstraintRegistry getInstance() {
		if (thisInstance == null) {
			thisInstance = new ConstraintRegistry();
		}
		return thisInstance;
	}

	public static void readConstraints(XiNode root) {

		Iterator itr = XiChild.getIterator(root, ExpandedName
				.makeName("relation"));
		while (itr.hasNext()) {
			XiNode reln = (XiNode) itr.next();
			String name = reln.getAttributeStringValue(ExpandedName
					.makeName("name"));
			if (name.startsWith("/")) {
				name = TypeManager.DEFAULT_BE_NAMESPACE_URI + name;
			} else {
				name = TypeManager.DEFAULT_BE_NAMESPACE_URI + "/" + name;
			}
			String source = reln.getAttributeStringValue(ExpandedName
					.makeName("source"));
			String target = reln.getAttributeStringValue(ExpandedName
					.makeName("target"));

			char refType = ConstraintRegistry.Relation.CONTAINMENT;
			String refTypeStr = reln.getAttributeStringValue(ExpandedName
					.makeName("refType"));
			if (refTypeStr != null && refTypeStr.length() > 0) {
				refType = refTypeStr.charAt(0);
				if (refType != ConstraintRegistry.Relation.CONTAINMENT
						&& refType != ConstraintRegistry.Relation.REFERENCE) {
					refType = ConstraintRegistry.Relation.CONTAINMENT;
				}
			}
			XiNode keys = XiChild.getChild(reln, ExpandedName.makeName("keys"));
			Iterator keysIter = XiChild.getIterator(keys, ExpandedName
					.makeName("key"));

			ArrayList srcCols = new ArrayList();
			ArrayList tgtCols = new ArrayList();

			while (keysIter.hasNext()) {
				XiNode key = (XiNode) keysIter.next();

				String srcCol = key.getAttributeStringValue(ExpandedName
						.makeName("source"));
				String tgtCol = key.getAttributeStringValue(ExpandedName
						.makeName("target"));

				srcCols.add(srcCol);
				tgtCols.add(tgtCol);
			}

			String fwdReferences[][] = new String[srcCols.size()][];

			for (int i = 0; i < srcCols.size(); i++) {
				fwdReferences[i] = new String[2];
				String srcKey = (String) srcCols.get(i);
				String trgKey = (String) tgtCols.get(i);

				fwdReferences[i][0] = srcKey;
				fwdReferences[i][1] = trgKey;
			}

			Relation fwdRelation = new Relation(name, source, target,
					refType, fwdReferences);

			ConstraintRegistry.getInstance().addRelation(fwdRelation);
		}
	}

	protected void addRelation(Relation r) {
		registry.put(r.getName(), r);
	}

	public Relation[] getRelations() {
		Relation[] relations = new Relation[0];
		if (!registry.isEmpty()) {
			// Get the values
			Set keys = registry.keySet();
			relations = new Relation[keys.size()];
			Iterator iter = keys.iterator();
			int counter = 0;
			while (iter.hasNext()) {
				Relation rel = (Relation) registry.get(iter.next());
				relations[counter++] = rel;
			}
		}
		return relations;
	}

	public List getRelationsByReference() {
		List relsList = new ArrayList();
		Iterator iter = registry.values().iterator();
		while(iter.hasNext()){
			Relation rel = (Relation) iter.next();
			if (Relation.REFERENCE == rel.getRefType()) {
				relsList.add(rel);
			}
		}
		return relsList;
	}
	
	public Relation getRelation(String key) {
		return (Relation) registry.get(key);
	}

	public ConstraintRegistry.Relation getDerivedRelation(
			ExpandedName ceptName, String propName) {
		if (ceptName == null) {
			return null;
		}

		com.tibco.cep.designtime.model.element.Concept dtConcept = (com.tibco.cep.designtime.model.element.Concept) RuleSessionManager
				.getCurrentRuleSession().getRuleServiceProvider().getProject()
				.getOntology().getEntity(ceptName);

		String constraintName = dtConcept.getFullPath() + "." + propName;

		ConstraintRegistry.Relation reln = ConstraintRegistry.getInstance()
				.getRelation(constraintName);

		if (reln == null) {

			com.tibco.cep.designtime.model.element.Concept parentDtConcept = dtConcept
					.getSuperConcept();
			if (parentDtConcept == null) {
				return null;
			}

			reln = getDerivedRelation(ExpandedName.makeName(
					TypeManager.DEFAULT_BE_NAMESPACE_URI
							+ parentDtConcept.getFullPath(), parentDtConcept
							.getName()), propName);
		}

		return reln;

	}

	public ConstraintRegistry.Relation getDerivedRelation(Concept cept,
			String propName) {
		if (cept == null) {
			return null;
		}

		String constraintName = cept.getExpandedName().getNamespaceURI() + "."
				+ propName;

		ConstraintRegistry.Relation reln = ConstraintRegistry.getInstance()
				.getRelation(constraintName);

		if (reln == null) {

			Class parentConceptClass = cept.getClass().getSuperclass();
			if (parentConceptClass == null
					|| parentConceptClass == com.tibco.cep.modules.db.model.runtime.AbstractDBConceptImpl.class
					|| parentConceptClass == com.tibco.cep.runtime.model.element.impl.ConceptImpl.class) {
				return null;
			}

			Concept parentDtConcept;
			try {
				parentDtConcept = (Concept) parentConceptClass
						.newInstance();
			} catch (Exception e) {
				return null;
			}

			reln = getDerivedRelation(parentDtConcept, propName);
		}

		return reln;

	}

	public static void readsequences(XiNode root) {
		Iterator itr = XiChild.getIterator(root, ExpandedName
				.makeName("unique_identifier"));
		while (itr.hasNext()) {
			XiNode sequenceNode = (XiNode) itr.next();
			String name = sequenceNode.getAttributeStringValue(ExpandedName
					.makeName("entity"));
			if (name.startsWith("/")) {
				name = TypeManager.DEFAULT_BE_NAMESPACE_URI + name;
			} else {
				name = TypeManager.DEFAULT_BE_NAMESPACE_URI + "/" + name;
			}
			String property = sequenceNode.getAttributeStringValue(ExpandedName
					.makeName("property"));
			String ora_seq = sequenceNode.getAttributeStringValue(ExpandedName
					.makeName("unique_identifier"));
			String query = sequenceNode.getAttributeStringValue(ExpandedName
					.makeName("sql_query"));
			String stored_proc_call = sequenceNode.getAttributeStringValue(ExpandedName
					.makeName("stored_proc"));
			if(stored_proc_call != null && !"".equals(stored_proc_call)){
				UniqueIdentifier seq = new UniqueIdentifier(name, property, stored_proc_call, UniqueIdentifier.QueryType.STORED_PROC);
				ConstraintRegistry.getInstance().addUniqueIdentifier(seq);
			} else if(query != null && !"".equals(query)){
				UniqueIdentifier seq = new UniqueIdentifier(name, property, query, UniqueIdentifier.QueryType.QUERY);
				ConstraintRegistry.getInstance().addUniqueIdentifier(seq);				
			} else if(ora_seq != null && !"".equals(ora_seq)){
				UniqueIdentifier seq = new UniqueIdentifier(name, property, ora_seq, UniqueIdentifier.QueryType.ORACLE_SEQ);
				ConstraintRegistry.getInstance().addUniqueIdentifier(seq);
			}
		}
	}

	public UniqueIdentifier[] getUniqueIdentifier(Concept cept) throws Exception {
		ArrayList uidList = getUniqueIdentifierList(cept);
		UniqueIdentifier[] retObj =  new UniqueIdentifier[]{};
		if(uidList != null)
			return (UniqueIdentifier[]) uidList.toArray(retObj);
		else
			return retObj;
	}
	
	public UniqueIdentifier getUniqueIdentifier(Concept cept, String propName) throws Exception {
		ArrayList uidList = getUniqueIdentifierList(cept);
		if(uidList != null){
			Iterator iter = uidList.iterator();
			while(iter.hasNext()){
				UniqueIdentifier id = (UniqueIdentifier) iter.next();
				if(id.getPropertyName().equals(propName)){
					return id;
				}
			}
		}
		return null;
	}
	
	public ArrayList getUniqueIdentifierList(Concept cept) throws Exception {

		ArrayList uidList = (ArrayList) sequences.get(cept
				.getExpandedName().getNamespaceURI());
		if (uidList == null) {

			Class parentConceptClass = cept.getClass().getSuperclass();
			if (parentConceptClass == null
					|| parentConceptClass == com.tibco.cep.modules.db.model.runtime.AbstractDBConceptImpl.class
					|| parentConceptClass == com.tibco.cep.runtime.model.element.impl.ConceptImpl.class) {
				return null;
			}

			Concept parentDtConcept = (Concept) parentConceptClass
					.newInstance();

			uidList = getUniqueIdentifierList(parentDtConcept);
		}

		return uidList;
	}

	
	private void addUniqueIdentifier(UniqueIdentifier uid) {
		String ceptNS = uid.getEntityNamespace();
		ArrayList uidSet = (ArrayList)sequences.get(ceptNS);
		if(uidSet == null){
			uidSet = new ArrayList<UniqueIdentifier>();
			uidSet.add(uid);
			sequences.put(ceptNS, uidSet);
		} else {
			uidSet.add(uid);
		}
		//sequences.put(ceptNS, uid);
	}
	
	public static class Relation {

		public static final char CONTAINMENT = 'C';

		public static final char REFERENCE = 'R';

		private String name;

		private String[][] allKeyReferences;

		private char refType;

		public Relation(String name, String srcTable, String targetTable,
				char refType, String[][] keyReferences) {
			this.name = name;
			this.refType = refType;
			this.allKeyReferences = keyReferences;
		}

		public String[][] getKeyReferences() {
			return allKeyReferences;
		}

		public String getName() {
			return name;
		}

		public char getRefType() {
			return refType;
		}
		public String toString() {
			StringBuffer buf = new StringBuffer(256);
			buf.append("name = ").append(name).append(", refType = ").append(refType).append("\n");
			for (int i=0; i<allKeyReferences.length; i++) {
				buf.append("   src=").append(allKeyReferences[i][0]).append(", tgtKey=").
					append(allKeyReferences[i][1]).append("\n");
			}
			return buf.toString();
		}
	}

	public static class UniqueIdentifier {
		
		public enum QueryType {ORACLE_SEQ, QUERY, STORED_PROC};
		
		private String entityNamespace;

		private String propertyName;

		private String uid;
		
		private QueryType type;

		public UniqueIdentifier(String entityNamespace, String propertyName,
				String uid, QueryType type) {
			this.entityNamespace = entityNamespace;
			this.propertyName = propertyName;
			this.uid = uid;
			this.type = type;
		}

		public String getPropertyName() {
			return propertyName;
		}

		public String getUniqueIdentifierName() {
			return uid;
		}

		public String getEntityNamespace() {
			return entityNamespace;
		}
		
		public QueryType getQueryType(){
			return type;
		}
	}

	public void generateConstraints(Ontology o) {

		for (Iterator i = o.getConcepts().iterator(); i.hasNext();) {
			com.tibco.cep.designtime.model.element.Concept c = (com.tibco.cep.designtime.model.element.Concept) i.next();
			
			List l = c.getAllPropertyDefinitions();
			for (int j=0; j<l.size(); j++) {
				PropertyDefinition pd = (PropertyDefinition) l.get(j);
				if (pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPT || 
						pd.getType() == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
					Map extProps = pd.getExtendedProperties();
					String relType = (String) extProps.get(DBConstants.REL_TYPE);
					if (relType == null ) {
						continue;
					}
					char refType = relType.equals("C") ? 'C' : 'R';
					Map relKeys = (Map) extProps.get(DBConstants.REL_KEYS);
					
					String name = TypeManager.DEFAULT_BE_NAMESPACE_URI + c.getFullPath() + "." + pd.getName();
					
					String[][] keyRefs = new String [relKeys.size()][2];
					
					int cnt = 0;
					for (Iterator k = relKeys.entrySet().iterator(); k.hasNext(); ){
						Map.Entry e = (Entry) k.next();
						String srcKey = (String) e.getKey();
						String tgtKey = (String) e.getValue();
						keyRefs[cnt][0] = srcKey;
						keyRefs[cnt][1] = tgtKey;
						cnt++;	
					}
					
					Relation reln = new ConstraintRegistry.Relation(name, null, null, refType, keyRefs);

					addRelation(reln);
				}
			}
		} 
	}
	public String toString() {
		StringBuffer b = new StringBuffer(256);
		for (Iterator i = registry.entrySet().iterator(); i.hasNext();) {
			Map.Entry e = (Entry) i.next();
			b.append("key=").append(e.getKey()).append("\n");
			b.append("value=").append(e.getValue());
		}
		return b.toString();
	}
}