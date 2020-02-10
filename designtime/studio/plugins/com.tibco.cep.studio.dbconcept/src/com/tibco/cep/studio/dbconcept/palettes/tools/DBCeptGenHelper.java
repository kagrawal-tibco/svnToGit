package com.tibco.cep.studio.dbconcept.palettes.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import  com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dbconcept.conceptgen.BaseEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.BaseRelationship;
import com.tibco.cep.studio.dbconcept.conceptgen.DBDataSource;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntityCatalog;
import com.tibco.cep.studio.dbconcept.conceptgen.DBProperty;
import com.tibco.cep.studio.dbconcept.conceptgen.impl.DBCeptGenerator;
import com.tibco.cep.studio.dbconcept.conceptgen.impl.DBDataSourceImpl;
import com.tibco.cep.studio.dbconcept.conceptgen.impl.DBEntityImpl;

public class DBCeptGenHelper {

	private String projectPath;
	private String ceptPath;
	private String eventPath;
	private String destinationURI;
	private DBCeptGenerator gen;
	private Map<String, DBEntity> selectedEntities = new HashMap<String, DBEntity>();
	private boolean genWithRel = false;
	private boolean generateEvents;
	private boolean overwriteConcepts;
	private String projName;
	
	public DBCeptGenHelper(){
		
	}
	
	public DBCeptGenHelper(String proj){
		this.projName = proj;
	}
	
	public DBCeptGenerator getDBCeptGenerator() {
		return gen;
	}
	
	public void setGen(DBCeptGenerator gen) {
		this.gen = gen;
	}
	public Map<String, DBEntity> getSelectedEntities() {
		return selectedEntities;
	}
	
	public void setSelection(Map<String, DBEntity> selectedEntities) {
		this.selectedEntities.putAll(selectedEntities);
	}
	
	public void clearSelection() {
		this.selectedEntities.clear();
	}
	
	public boolean isGenWithRel() {
		return genWithRel;
	}
	public void setGenWithRel(boolean genWithRel) {
		this.genWithRel = genWithRel;
	}
	
	public boolean isGenerateEvents() {
		return generateEvents;
	}
	public void setGenerateEvents(boolean generateEvents) {
		this.generateEvents = generateEvents;
	}
	
	public DBEntityCatalog getDbEntityCatalog() {
		return this.gen.getDbEntityCatalog();
	}
	
	public void generateCatalog(String dsName,
			String jdbcURI,
			String driver,
			String connURL,
			String username,
			String password,
			String ownerName,
			int retryCount,
			JdbcSSLConnectionInfo sslConnectionInfo,
			boolean genCeptsWithRel,
			IProgressMonitor monitor,
			String userDefinedQuery) throws Exception {
		if ( monitor == null) {
			monitor = new NullProgressMonitor();
		}
		DBDataSourceImpl dbs = new DBDataSourceImpl();
		dbs.setConnectionUrl(connURL);
		dbs.setDriver(driver);
		dbs.setUsername(username);
		dbs.setPassword(password);
		dbs.setDsName(dsName);
		dbs.setSchemaOwner(ownerName);
		dbs.setJdbcResourceURI(jdbcURI);
		dbs.setRetryCount(retryCount);
		dbs.setSSLConnectionInfo(sslConnectionInfo);
		
		gen.generateCatalog(dbs, genCeptsWithRel,userDefinedQuery, new SubProgressMonitor(monitor, 80));
//		monitor.worked(50);

		// set selected entities
		clearSelection();
		monitor.worked(5);
		setSelection(gen.getDbEntityCatalog().getEntities());
		monitor.worked(5);
	}
	
	public void generateConcepts() throws Exception {
		gen.generateConcepts(ceptPath, overwriteConcepts);
	}

	public void generateEvents() throws Exception {
		gen.generateEvents(eventPath, destinationURI, overwriteConcepts);
	}

	public void generateConstrainRegistry(String ceptPath, String sharedResPath, String constFileName) throws Exception {
		gen.generateConstraintRegistry(ceptPath, sharedResPath, constFileName);
	}
	
	/*public void refreshProject() throws Exception {
		designerDocument.getResourceStore().refreshResource(designerDocument.getResourceStore().getRootResource());
	}*/
	
	public void saveProject() throws Exception {
		gen.saveProject();
	}
	
	

	public Map<String, DBEntity> getAdditionalEntities() {
		Map<String, DBEntity> additionalEntities = new HashMap<String, DBEntity>();
		Iterator<Entry<String, DBEntity>> iter = selectedEntities.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, DBEntity> entry = iter.next();
			DBEntity dbe = entry.getValue();
			Map<String, BaseEntity> scannedEntities1 = new HashMap<String, BaseEntity>();
			Map<String, BaseEntity> scannedEntities2 = new HashMap<String, BaseEntity>();
			addAdditionalChildrenEntities(dbe, additionalEntities, scannedEntities1);
			addAdditionalContainingEntities(dbe, additionalEntities, scannedEntities2);
		}
		
		return additionalEntities;
	}
	
	private void addAdditionalChildrenEntities(BaseEntity dbe, Map<String, DBEntity> additionalEntities, Map<String, BaseEntity> scannedEntities) {
		if(scannedEntities.containsKey(dbe.getFullName())){
			return;
		}
		scannedEntities.put(dbe.getFullName(), dbe);
		List<?> childEntities = dbe.getChildEntities();
		for (int i = 0; i < childEntities.size(); i++) {
			BaseRelationship rel = (BaseRelationship) childEntities.get(i);
			DBEntity childEntity = (DBEntity)rel.getChildEntity();
			if (childEntity == null) {
				continue;
			}
			String childName = childEntity.getFullName();
			addAdditionalChildrenEntities(childEntity, additionalEntities, scannedEntities);
			if(!selectedEntities.containsKey(childName) && !additionalEntities.containsKey(childName)){
				additionalEntities.put(childName, childEntity);
			}
		}
	}
	
	private void addAdditionalContainingEntities(BaseEntity e, Map<String, DBEntity> additionalEntities, Map<String, BaseEntity> scannedEntities){
		if(scannedEntities.containsKey(e.getFullName())){
			return;
		}
		scannedEntities.put(e.getFullName(), e);

		String eName = e.getName();
		Iterator<DBEntity> iter = getDbEntityCatalog().getEntities().values().iterator();
		while (iter.hasNext()) {

			DBEntity entity =  iter.next();
			BaseRelationship rel = null;
			List<?> rels = entity.getChildEntities();
			for (int i = 0; i < rels.size(); i++) {
				rel = (BaseRelationship) rels.get(i);
				if(rel.getRelationshipEnum() == BaseRelationship.CONTAINMENT){
					BaseEntity cEntity = rel.getChildEntity();
					if(cEntity != null && cEntity.getName().equals(eName)
							&& !selectedEntities.containsKey(entity.getFullName())){
						
						addAdditionalContainingEntities(entity, additionalEntities, scannedEntities);
						Map<String, BaseEntity> m = new HashMap<String, BaseEntity>();
						addAdditionalChildrenEntities(entity, additionalEntities, m);
						additionalEntities.put(entity.getFullName(), entity);
						return;
					}
				}
			}
		}
	}
	
	public void getRidOfRelations() {
		
		Iterator<DBEntity> iter = gen.getDbEntityCatalog().getEntities().values().iterator();
		while (iter.hasNext()) {
			DBEntityImpl dbe = (DBEntityImpl) iter.next();
			List<BaseRelationship> childEntities = dbe.getChildEntities();
			for (int i = 0; i < childEntities.size(); i++) {
				BaseRelationship rel = (BaseRelationship) childEntities.get(i);
				dbe.removeRelationships(rel);
			}
		}
	}
	
	public void filterDBEntityCatalog() {
		Map<String, DBEntity> originalEntities = getDbEntityCatalog().getEntities();
		Iterator<Entry<String, DBEntity>> iter = originalEntities.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, DBEntity> entry = iter.next();
			String key =  entry.getKey();
			if(selectedEntities.get(key) == null) {
				
				iter.remove();
			}
		}
	}
//	public DesignerDocument getDesignerDocument() {
//		return designerDocument;
//	}
//	public void setDesignerDocument(DesignerDocument designerDocument) {
//		this.designerDocument = designerDocument;
//	}
	public String getProjectPath() {
		if(projectPath == null){
			projectPath = this.gen.getProjectPath();
		}
		return projectPath;
	}
	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
	
	public void setConceptFolderPath(String path) {
		path = path.replace('\\', '/');
		path = path.startsWith("/") ? path : "/"+path;
		path = path.endsWith("/") ? path : path+"/";
		this.ceptPath = path;
	}
	
	public void setEventFolderPath(String path) {
		path = path.replace('\\', '/');
		path = path.startsWith("/") ? path : "/"+path;
		path = path.endsWith("/") ? path : path+"/";
		this.eventPath = path;
	}
	
	public String getConceptFolderPath() {
		return this.ceptPath;
	}
	
	public String getEventFolderPath() {
		return this.eventPath;
	}
	
//	public boolean conceptExistAtLocation(){
//		
//		String dbDataSourceName = gen.getDbEntityCatalog().getName();
//		String ceptFolderPath = ceptPath + dbDataSourceName;
//		EMFProject project = this.gen.getBEProject();
//
//		Folder f = project.getOntology().getFolder(ceptFolderPath);
//		if(f == null) {
//			return false;
//		}
//		
//		Iterator iter =  f.getEntities(false).iterator();
//		while (iter.hasNext()) {
//			Entity object = (Entity) iter.next();
//			if(object instanceof Concept){
//				Concept dbc = (Concept) object;
//				String dbObjName = (String) dbc.getExtendedProperties().get(DBConcept.OBJECT_NAME);
//				if(dbObjName != null){
//					String fullName = dbDataSourceName + "/" + dbObjName;
//					 Object entity = gen.getDbEntityCatalog().getEntity(fullName);
//					 if(entity != null){
//						 return true;
//					 }
//				}
//			}
//		}
//		return false;
//	}
	
	public boolean conceptExistAtLocationWithAlias(){
		
		Iterator<DBEntity> iter =  gen.getDbEntityCatalog().getEntities().values().iterator();
		while (iter.hasNext()) {
			DBEntity object = (DBEntity) iter.next();
			String ceptURI = ceptPath + object.getFullName();
			Concept concept = IndexUtils.getConcept(projName, ceptURI);

			if(concept != null){
				return true;
			}	
		}
		return false;
	}
	
	public Concept getExistingDBConcept(DBEntity dbe){
		String conceptURI = ceptPath + dbe.getFullName();
		Concept concept = IndexUtils.getConcept(projName, conceptURI);
		
		return concept;
	}
	
	public PropertyDefinition getExistingDBConceptProperty(Concept concept, DBProperty prop){
		
		if(concept == null || prop == null) {
			return null;
		}
		
		String columnName = prop.getName();

		Iterator<PropertyDefinition> iter =  concept.getPropertyDefinitions(true).iterator();
		while (iter.hasNext()) {
			PropertyDefinition object = (PropertyDefinition) iter.next();
			EList<Entity> properties = object.getExtendedProperties().getProperties();
			for (int i = 0; i < properties.size(); i++) {
				String name = properties.get(i).getName();
				if(name.equals(columnName))
					return object;
			}
		}
		
		return null;
	}
	
	public String getDestinationURI() {
		return destinationURI;
	}

	public void setDestinationURI(String destinationURI) {
		this.destinationURI = destinationURI;
	}

	public boolean isOverwriteConcepts() {
		return overwriteConcepts;
	}
	
	public void setOverwriteConcepts(boolean overwriteConcepts) {
		this.overwriteConcepts = overwriteConcepts;
	}
	
	public boolean isSQLServer(String driver){
		String dbType = DBDataSourceImpl.getDBType(driver);
		return DBDataSource.MSSQL.equals(dbType);
	}
	
	//get databasename part from conn url
	public String resolveDatabaseName(String driver, String connURL){
		
		String dbNamePart = "";
		
		if("tibcosoftwareinc.jdbc.sqlserver.SQLServerDriver".equals(driver)
				|| "com.microsoft.sqlserver.jdbc.SQLServerDriver".equals(driver)
				|| "net.sourceforge.jtds.jdbc.Driver".equals(driver)){
			if(connURL.indexOf("databaseName=") > -1){
			dbNamePart = connURL.substring(connURL.indexOf("databaseName=")).substring("databaseName=".length());
			} else {
				throw new RuntimeException("Invalid database url " + connURL);
			}
		} else if("weblogic.jdbc.mssqlserver4.Driver".equals(driver)) {
			dbNamePart = connURL.substring("jdbc:weblogic:mssqlserver4:".length(), connURL.indexOf("@"));
		}

		return dbNamePart;
	}
}
