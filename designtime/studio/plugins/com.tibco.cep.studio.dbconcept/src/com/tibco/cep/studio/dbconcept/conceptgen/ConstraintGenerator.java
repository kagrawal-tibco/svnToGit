package com.tibco.cep.studio.dbconcept.conceptgen;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;

import com.tibco.cep.repo.BEProject;
import com.tibco.objectrepo.vfile.VFile;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * 
 * @author bgokhale
 * Generate constraints file, format: 
 * 
 * 
 * <?xml version = "1.0" encoding = "UTF-8"?>
<relations>
	<relation name = "RefundQuote.TravelDocVerRefund" refType = "R" source = "REFUND_QUOTE" target = "TRAVEL_DOCUMENT_VER">
		<keys>
			<key source = "PRIMARY_AIRLINE_CD" target = "PRIMARY_AIRLINE_CD"/>
			<key source = "PRIMARY_DOCUMENT_NUM" target = "PRIMARY_DOCUMENT_NUM"/>
			<key source = "PRIMARY_ISSUE_DT" target = "PRIMARY_ISSUE_DT"/>
			<key source = "TRAVEL_DOCUMENT_VERSION" target = "TRAVEL_DOCUMENT_VERSION"/>
		</keys>
	</relation>
<relations>
 *
 */
public class ConstraintGenerator {

	private XiNode rootNode;
	protected BEProject project;
	protected String conceptsRoot;
	protected String constraintsFileName;

	protected BaseEntityCatalog catalog;
	
	protected XiFactory xiFactory = XiFactoryFactory.newInstance();
	
	private static final String FILE_EXT = ".constraints.xml";
	
	public ConstraintGenerator (BEProject project, String conceptsRoot, String constraintsFileName,  BaseEntityCatalog catalog) {
		this.project = project;
		this.constraintsFileName = constraintsFileName + FILE_EXT;
		this.catalog = catalog;
		this.conceptsRoot = conceptsRoot;
		rootNode = xiFactory.createElement(ExpandedName.makeName("relations"));
	}
	
	public void generateConstraints() throws Exception {

		 Iterator<?> entities = catalog.getEntities().values().iterator();
		 
		 while(entities.hasNext()) {
			 BaseEntity e = (BaseEntity) entities.next();
			 for (int j=0; j<e.getChildEntities().size(); j++) {
				 BaseRelationship r = (BaseRelationship) e.getChildEntities().get(j);
				 BaseEntity child = r.getChildEntity();
				 addXiNode(e, child, r);
			 }
		 }

		 writeToFile(constraintsFileName, rootNode, null);
		 
	}
	
	public String getConstraintsRegistryContent() throws Exception {

		 Iterator<?> entities = catalog.getEntities().values().iterator();
		 
		 while(entities.hasNext()) {
			 BaseEntity e = (BaseEntity) entities.next();
			 for (int j=0; j<e.getChildEntities().size(); j++) {
				 BaseRelationship r = (BaseRelationship) e.getChildEntities().get(j);
				 BaseEntity child = r.getChildEntity();
				 addXiNode(e, child, r);
			 }
		 }

		 return XiSerializer.serialize(rootNode);
	}
	
	private void addXiNode (BaseEntity parent, BaseEntity child, BaseRelationship r) {

		String entityObjName = parent.getName();
		 if (entityObjName == null) {
			 entityObjName = "";
		 }

		 String childObjName = child.getName();
		 if (childObjName == null) {
			 childObjName = "";
		 }

		 StringBuffer buf = new StringBuffer();
		 String parentNamespace = "";
		 if(parent.getType() == 0){
			 parentNamespace = conceptsRoot;
			 parentNamespace += parent.getFullName();
		 }
		 buf.append(parentNamespace);
		 buf.append(".");
		 buf.append(r.getName());

		 String relationName = buf.toString();

		 XiNode relation = xiFactory.createElement(ExpandedName.makeName("relation"));
		 XiAttribute.setStringValue(relation, "name", relationName);
		 XiAttribute.setStringValue(relation, "source", entityObjName);
		 XiAttribute.setStringValue(relation, "target", childObjName);

		 String refType = r.getRelationshipEnum() == 0 ? "R" : "C";
		 XiAttribute.setStringValue(relation, "refType", refType);
		 
		 XiNode keysElem = xiFactory.createElement(ExpandedName.makeName("keys"));
		 relation.appendChild(keysElem);
		 
		 //Iterator i = r.getKeyMap().entrySet().iterator();
		 Iterator<?> i = r.getRelationshipKeySet().iterator();
		 while (i.hasNext()) {
			 RelationshipKey e = (RelationshipKey) i.next();
			 String srcKey = e.getParentKey();
			 String tgtKey = e.getChildKey();
			 boolean flag  = e.isUsedForSelect();
			 
			 XiNode key = xiFactory.createElement(ExpandedName.makeName("key"));
			 keysElem.appendChild(key);
			 XiAttribute.setStringValue(key, "source", srcKey);
			 XiAttribute.setStringValue(key, "target", tgtKey);
			 XiAttribute.setStringValue(key, "select-flag", String.valueOf(flag));
		 }
		
		 rootNode.appendChild(relation);
	}

	private void writeToFile(String fullPath, String value, VFile file) throws Exception {
		VFileFactory vfac = project.getVFileFactory();
		if (file == null) {
			file = vfac.create(fullPath, true);
		}
		InputStream is = new ByteArrayInputStream(value.getBytes());
		((VFileStream) file).update(is);
	}
	protected void writeToFile(String fullPath, XiNode node, VFile file) throws Exception {
		String xiNodeStr = XiSerializer.serialize(node);
		writeToFile(fullPath, xiNodeStr, file);
	}
	
	public String getFileName(){
		return this.constraintsFileName;
	}
	
}
