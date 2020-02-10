package com.tibco.cep.studio.dbconcept.conceptgen;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.xml.sax.InputSource;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.ObjectProperty;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.modules.db.model.designtime.DBConstants;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.MutableUtils;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.datamodel.nodes.Element;

/**
 * 
 * @author bgokhale Generates BE model (MutableOntology) from entities as
 *         defined in BaseEntityCatalog
 * 
 */
public class ModelGenerator {

	protected final static int BE_TYPE_STRING = 0;
	protected final static int BE_TYPE_INT = 1;
	protected final static int BE_TYPE_LONG = 2;
	protected final static int BE_TYPE_DOUBLE = 3;
	protected final static int BE_TYPE_BOOLEAN = 4;
	protected final static int BE_TYPE_DATETIME = 5;
	protected final static int BE_TYPE_CONTAIN = 6;
	protected final static int BE_TYPE_REFERENCE = 7;

	
	protected XiFactory xiFactory = XiFactoryFactory.newInstance();
	protected BaseEntityCatalog catalog;
	
	private String conceptsRoot;
	private String eventsRoot;
	private String projectName;
	private IProject project;
	
	private Set<String> createdConcepts;
	
	public ModelGenerator(BaseEntityCatalog catalog, IProject prj /*, boolean overwite*/) throws Exception {

		this.projectName = prj.getName();
		this.project = prj;
		this.catalog = catalog;
		
	}

	public void generateConcepts(String ceptsRoot, boolean overwrite) throws Exception {
		createdConcepts = new HashSet<String>();
		ceptsRoot = ceptsRoot.startsWith("/") ? ceptsRoot : ("/" + ceptsRoot);
		ceptsRoot = ceptsRoot.endsWith("/") ? ceptsRoot : (ceptsRoot + "/");
		this.conceptsRoot = ceptsRoot;

		Iterator<?> iter = catalog.getEntities().values().iterator();

		while (iter.hasNext()) {
			BaseEntity value = (BaseEntity) iter.next();
			generateConcept(value , 0, overwrite);
		}
		createdConcepts.clear();
		createdConcepts = null;
	}
	
	public void generateEvents(String evtsRoot, String destinationPath, boolean overwrite) throws Exception {
		evtsRoot = evtsRoot.startsWith("/") ? evtsRoot : ("/" + evtsRoot);
		evtsRoot = evtsRoot.endsWith("/") ? evtsRoot : (evtsRoot + "/");
		this.eventsRoot = evtsRoot;
		
		Iterator<?> iter = catalog.getEntities().values().iterator();
		while (iter.hasNext()) {
			BaseEntity value = (BaseEntity) iter.next();
			generateEvent(evtsRoot,  value, destinationPath, 0, overwrite);
		}
	}
	
	private void addProperties(com.tibco.cep.designtime.core.model.event.Event me, BaseEntity e) throws ModelException {
		
		if(e == null) {
			return;
		}
		
		BaseEntity superEntity = catalog.getEntity(e.getSuperEntityName());
		addProperties(me, superEntity);
		
		Iterator<?> iter = e.getProperties().iterator();
		while (iter.hasNext()) {
			BaseProperty property = (BaseProperty) iter.next();
			String propertyName = getPropertyName(property);
			PROPERTY_TYPES type = getEMFType(property.getType());
			PropertyDefinition pd = ElementFactory.eINSTANCE.createPropertyDefinition();
			pd.setName(propertyName);
			pd.setType(type);
			pd.setOwnerPath(me.getFullPath());
			pd.setOwnerProjectName(me.getOwnerProjectName());
			me.getProperties().add(pd);
		}
	}
	
	protected boolean resourceExists(String resourceUri) {
		return false;
	}
	
	private void generateEvent(String eventsRoot, BaseEntity e, String destinationURI, int level, boolean overwrite) throws ModelException {
		
		if(e == null){
			return;
		}

		String eventURI = convertToValidJavaIdentifier(getEventNamespace(e));
		Entity  entity  = IndexUtils.getEntity(projectName, eventURI);
		if (entity != null) {
			if(overwrite) {
				MutableUtils.deleteEntity(entity);
			} else {
				return;
			}
		}
		
		String eventName = convertToValidJavaIdentifier(getEventName(e));
		String folderPath = eventsRoot + convertToValidJavaIdentifier(e.getNamespace());
//		String namespace =  convertToValidJavaIdentifier(e.getNamespace());
		//to fix the concept clashes at run for concepts and events
		com.tibco.cep.designtime.core.model.event.Event me = MutableUtils.createEvent(projectName,folderPath , folderPath , eventName, "0", TIMEOUT_UNITS.SECONDS, true, false);
		if(me instanceof com.tibco.cep.designtime.core.model.event.SimpleEvent) {
			com.tibco.cep.designtime.core.model.event.SimpleEvent se = (SimpleEvent) me;
			if (destinationURI != null) {
				//get channelPath and destination name from it
				if (destinationURI.lastIndexOf('/') != -1) {
					String channelURI = destinationURI.substring(0, destinationURI
							.lastIndexOf('/'));
					if (channelURI.endsWith(".channel")) {
						channelURI = channelURI.substring(0, channelURI.indexOf(".channel"));
					}
					if (MutableUtils.getChannel(projectName, channelURI)!= null) {
						se.setChannelURI(channelURI);
					}
					String destinationName = destinationURI
					.substring(destinationURI.lastIndexOf('/') + 1);
					se.setDestinationName(destinationName);
				}
			}
			addProperties(se, e);
		} else if(me instanceof TimeEvent) {
			TimeEvent te = (TimeEvent) me;
			addProperties(te, e);
		}
		
		String conceptURI = convertToValidJavaIdentifier(getEntityConceptNamespace(e));
		com.tibco.cep.designtime.core.model.element.Concept concept = IndexUtils.getConcept(projectName, conceptURI);
		if (concept != null) {
			String ns = TypeManager.DEFAULT_BE_NAMESPACE_URI
					+ concept.getNamespace() + concept.getName();
			String sl = concept.getFullPath() + ".concept";

			Element n = createXiNode(concept, ns);
			com.tibco.cep.designtime.core.model.event.ImportRegistryEntry ire = EventFactory.eINSTANCE.createImportRegistryEntry();
			ire.setNamespace(ns);
			ire.setLocation(sl);			
			me.getRegistryImportEntries().add(ire);
			NamespaceEntry entry = EventFactory.eINSTANCE.createNamespaceEntry();
			entry.setPrefix("pfx");
			entry.setNamespace(ns);			
			me.getNamespaceEntries().add(entry);
			entry = EventFactory.eINSTANCE.createNamespaceEntry();
			entry.setPrefix("xsd");
			entry.setNamespace("http://www.w3.org/2001/XMLSchema");
			me.getNamespaceEntries().add(entry);
			me.setPayloadString(XiSerializer.serialize(n));
		}
		
		MutableUtils.persistEntityInProject(project, me, new NullProgressMonitor());	
		
	}

	private Element createXiNode(com.tibco.cep.designtime.core.model.element.Concept ee, String ns) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
						"<payload ref=\"pfx:" + ee.getName() + "\"/>";
		InputStream is = new ByteArrayInputStream(xml.getBytes());
		InputSource source = new InputSource(is);
		XiNode n = null;
		try {
			n = XiParserFactory.newInstance().parse(source);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return (Element)XiChild.getFirstChild(n);
	}

	private void generateConcept(BaseEntity e, int level, boolean overwrite) throws ModelException {

		Concept concept = null;
		if (e == null) {
			return;
		}
		// check with alias name + datasource name
		String conceptURI = convertToValidJavaIdentifier(getEntityConceptNamespace(e));
		concept = IndexUtils.getConcept(projectName, conceptURI);
		if (concept != null) {
			if(overwrite) {
				MutableUtils.deleteEntity(concept);
			} else {
				return;
			}
		}
		
		// create with alias name and datasource name
		String namespace = convertToValidJavaIdentifier(e.getNamespace());
		String conceptFolderURI = conceptsRoot + namespace;
		String conceptName = convertToValidJavaIdentifier(getConceptName(e));
		//to fix the concept clashes at run for concepts and events
		concept = MutableUtils.createConcept(projectName, conceptFolderURI,conceptFolderURI, conceptName, null, true);
		createdConcepts.add(concept.getFullPath());
		String description = e.getDescription();
		if (description != null && !"".equals(description)) {
			concept.setDescription(description);
		}

		if (e instanceof DBEntity) {
			DBEntity dbe = (DBEntity) e;
			EList<Entity> plist = concept.getExtendedProperties().getProperties();
			SimpleProperty op = ModelFactory.eINSTANCE.createSimpleProperty();
			op.setName(DBConstants.SCHEMA_NAME);
			op.setValue(dbe.getSchema());
			plist.add(op);
			op = ModelFactory.eINSTANCE.createSimpleProperty();
			op.setName(DBConstants.OBJECT_NAME);
			op.setValue(dbe.getObjName());
			plist.add(op);
			String entType = dbe.getEntityType() == DBEntity.TABLE ? "T"
					: "V";
			op = ModelFactory.eINSTANCE.createSimpleProperty();
			op.setName(DBConstants.OBJECT_TYPE);
			op.setValue(entType);
			plist.add(op);
			String pks = getPKs(e);
			op = ModelFactory.eINSTANCE.createSimpleProperty();
			op.setName(DBConstants.PRIMARY_KEY_PROPS);
			op.setValue(pks);
			plist.add(op);
			op = ModelFactory.eINSTANCE.createSimpleProperty();
			op.setName(DBConstants.JDBC_RESOURCE);
			if(dbe.getJdbcResourceURI().contains(".projlib")){
				op.setValue(dbe.getJdbcResourceURI().split(".projlib")[1]);
			}else{
				op.setValue(dbe.getJdbcResourceURI());
			}
			plist.add(op);
		}

		if (e.getSuperEntityName() != null) {
			BaseEntity superEntity = catalog.getEntity(e.getSuperEntityName());
			generateConcept(superEntity, (level + 1), overwrite);
			// set alias + datasource
			String superConceptURI = convertToValidJavaIdentifier(getEntityConceptNamespace(superEntity));
			concept.setParentConceptPath(superConceptURI);
		}

		addProperties(e, concept, level, overwrite);
		
		MutableUtils.persistEntityInProject(project, concept, new NullProgressMonitor());	
	}
	
	public void save() throws Exception {
	}

	private void addProperties(BaseEntity e, com.tibco.cep.designtime.core.model.element.Concept concept, int level, boolean overwrite)
	throws ModelException {
		
		Collection<Object> c = getOrderedList(e);
		Iterator<Object> iter = c.iterator();
		while (iter.hasNext()) {
			
			Object object = (Object) iter.next();
			
			if(object instanceof BaseProperty){
				BaseProperty p = (BaseProperty) object;
				//create with prop alias
				String propName = convertToValidJavaIdentifier(getPropertyName(p));
				String ownerPath= concept.getFullPath();
				PropertyDefinition prop = MutableUtils.createPropertyDefinition(
						concept, propName, translate2EMFType(p.getType()),
						ownerPath, 0, 0, p.isArray(), null);
				EList<Entity> plist = prop.getExtendedProperties().getProperties();
				if(p.getDefaultValue() != null) {
					prop.setDefaultValue((String)p.getDefaultValue());
				}
				if (p instanceof DBProperty) {
					SimpleProperty op = ModelFactory.eINSTANCE.createSimpleProperty();
					op.setName(DBConstants.COLUMN_NAME);
					op.setValue(((DBProperty) p).getColumnName());
					plist.add(op);

					op = ModelFactory.eINSTANCE.createSimpleProperty();
					op.setName(DBConstants.DATA_TYPE);
					op.setValue(((DBProperty) p).getColumnType());
					plist.add(op);
					op = ModelFactory.eINSTANCE.createSimpleProperty();
					op.setName(DBConstants.LENGTH);
					op.setValue(""+((DBProperty) p).getLength());
					plist.add(op);
					op = ModelFactory.eINSTANCE.createSimpleProperty();
					op.setName(DBConstants.PRECISION);
					op.setValue(""+((DBProperty) p).getPrecision());
					plist.add(op);
				}
				
			} else if(object instanceof BaseRelationship){

				BaseRelationship r = (BaseRelationship) object;
				BaseEntity childEntity = catalog.getEntity(r.getChildEntityName());

				//get with alias and ...
				String childEntityURI = convertToValidJavaIdentifier(getEntityConceptNamespace(childEntity));
//				Concept childConcept = IndexUtils.getConcept(projectName, childEntityURI);
				if(!createdConcepts.contains(childEntityURI)) {
					generateConcept(childEntity, level + 1, overwrite);
				}

				// 0 implies unbounded or multiple
				boolean isArray = r.getCardinality() == 0;

				//In case of ConceptReference properties, escape the SQL special symbols as they point to table names
				String propName = convertToValidJavaIdentifier(r.getName());
				String childConceptURI = convertToValidJavaIdentifier(getEntityConceptNamespace(r.getChildEntity()));
				PropertyDefinition prop = MutableUtils.createPropertyDefinition(
						concept, propName, translate2EMFRelationType(r.getRelationshipEnum()),
						childConceptURI, 0, 0, isArray, null);
				prop.setType(translate2EMFRelationType(r.getRelationshipEnum()));
				prop.setConceptTypePath(childConceptURI);

				EList<Entity> extProps = prop.getExtendedProperties().getProperties();

				PropertyMap pmap = ModelFactory.eINSTANCE.createPropertyMap();
				EList<Entity> pmapProps = pmap.getProperties();
				for (Iterator<?> ks = r.getRelationshipKeySet().iterator(); ks.hasNext();) {
					RelationshipKey rk = (RelationshipKey) ks.next();
					String parentKey = rk.getParentKey();
					String childKey = rk.getChildKey();
					SimpleProperty sp = ModelFactory.eINSTANCE.createSimpleProperty();
					sp.setName(parentKey);
					sp.setValue(childKey);
					pmapProps.add(sp);
				}
				String relType = r.getRelationshipEnum() == BaseRelationship.CONTAINMENT ? "C" : "R";
				SimpleProperty sp = ModelFactory.eINSTANCE.createSimpleProperty();
				sp.setName(DBConstants.REL_TYPE);
				sp.setValue(relType);
				extProps.add(sp);
				ObjectProperty op = ModelFactory.eINSTANCE.createObjectProperty();
				op.setName(DBConstants.REL_KEYS);
				op.setValue(pmap);
				extProps.add(op);
			}
		}
	}
	
	private Collection<Object> getOrderedList(BaseEntity e){
		
		LinkedList<Object> list = new LinkedList<Object>();
		List<?> props = e.getProperties();
		Iterator<?> iterProps = props.iterator();
		while (iterProps.hasNext()) {
			BaseProperty prop = (BaseProperty) iterProps.next();
			int propIndex = prop.getPosition();
			addToOrderedList(list, prop, propIndex);
		}

		List<?> rels = e.getChildEntities();
		Iterator<?> iterRels = rels.iterator();
		while (iterRels.hasNext()) {
			BaseRelationship rel = (BaseRelationship) iterRels.next();
			int relIndex = rel.getPosition();
			addToOrderedList(list, rel, relIndex);
		}
		
		return list;
	}
	
	private void addToOrderedList(LinkedList<Object> list, Object object, int objPosition){
		
		int insertionPos = -1;
		int index = 0;
		Iterator<Object> iter = list.iterator();
		while(iter.hasNext()){
			Object obj = iter.next();
			int position = 0;
			if(obj instanceof BaseProperty){
				position = ((BaseProperty)obj).getPosition();
			} else if(obj instanceof BaseRelationship){
				position = ((BaseRelationship)obj).getPosition();
			}
			if(objPosition < position){
				insertionPos = index;
				break;
			}
			index++;
		}
		if(insertionPos == -1){
			insertionPos = index;
		}
		list.add(insertionPos, object);
	}

	private String getPKs(BaseEntity e) {
		StringBuffer pkBuf = new StringBuffer("[");

		List<BaseProperty> pkl = new ArrayList<BaseProperty>();
		for (int i = 0; i < e.getProperties().size(); i++) {
			BaseProperty p = (BaseProperty) e.getProperties().get(i);
			if (p instanceof DBProperty && ((DBProperty) p).isPK()) {
				pkl.add(p);
			}
		}

		for (int i = 0; i < pkl.size(); i++) {
			BaseProperty p = (BaseProperty) pkl.get(i);
			String pk = ((DBProperty) p).getColumnName();
			pkBuf.append(convertToValidJavaIdentifier(pk));
			if (i < pkl.size() - 1) {
				pkBuf.append(",");
			}
		}
		pkBuf.append("]");
		return pkBuf.toString();
	}

	protected int translateType(int dataType) {

		int type = ModelGenerator.BE_TYPE_STRING;

		if (dataType == BaseProperty.STRING) {
			type = ModelGenerator.BE_TYPE_STRING;
		} else if (dataType == BaseProperty.CHAR) {
			type = ModelGenerator.BE_TYPE_STRING;
		} else if (dataType == BaseProperty.DATE
				|| dataType == BaseProperty.DATETIME) {
			type = ModelGenerator.BE_TYPE_DATETIME;
		} else if (dataType == BaseProperty.DOUBLE) {
			type = ModelGenerator.BE_TYPE_DOUBLE;
		} else if (dataType == BaseProperty.LONG) {
			type = ModelGenerator.BE_TYPE_LONG;
		} else if (dataType == BaseProperty.INTEGER
				|| dataType == BaseProperty.BOOLEAN) {
			type = ModelGenerator.BE_TYPE_INT;
		}

		return type;
	}
	
	private PROPERTY_TYPES translate2EMFType(int dataType) {

		PROPERTY_TYPES type = PROPERTY_TYPES.STRING;

		if (dataType == BaseProperty.STRING) {
			type = PROPERTY_TYPES.STRING;
		} else if (dataType == BaseProperty.CHAR) {
			type = PROPERTY_TYPES.STRING;
		} else if (dataType == BaseProperty.DATE
				|| dataType == BaseProperty.DATETIME) {
			type = PROPERTY_TYPES.DATE_TIME;
		} else if (dataType == BaseProperty.DOUBLE) {
			type = PROPERTY_TYPES.DOUBLE;
		} else if (dataType == BaseProperty.LONG) {
			type = PROPERTY_TYPES.LONG;
		} else if (dataType == BaseProperty.INTEGER
				|| dataType == BaseProperty.BOOLEAN) {
			type = PROPERTY_TYPES.INTEGER;
		}

		return type;
	}
	
	protected RDFPrimitiveTerm getRDFPrimitiveTerm(int dataType) {

		RDFUberType type = RDFTypes.STRING;

		if (dataType == BaseProperty.STRING) {
			type = RDFTypes.STRING;
		} else if (dataType == BaseProperty.CHAR) {
			type = RDFTypes.STRING;
		} else if (dataType == BaseProperty.DATE
				|| dataType == BaseProperty.DATETIME) {
			type = RDFTypes.DATETIME;
		} else if (dataType == BaseProperty.DOUBLE) {
			type = RDFTypes.DOUBLE;
		} else if (dataType == BaseProperty.LONG) {
			type = RDFTypes.LONG;
		} else if (dataType == BaseProperty.INTEGER
				|| dataType == BaseProperty.BOOLEAN) {
			type = RDFTypes.INTEGER;
		}

		return (RDFPrimitiveTerm)type;
	}
	
	private PROPERTY_TYPES getEMFType(int dataType) {

		PROPERTY_TYPES type = PROPERTY_TYPES.STRING;

		if (dataType == BaseProperty.STRING) {
			type = PROPERTY_TYPES.STRING;
		} else if (dataType == BaseProperty.CHAR) {
			type = PROPERTY_TYPES.STRING;
		} else if (dataType == BaseProperty.DATE
				|| dataType == BaseProperty.DATETIME) {
			type = PROPERTY_TYPES.DATE_TIME;
		} else if (dataType == BaseProperty.DOUBLE) {
			type = PROPERTY_TYPES.DOUBLE;
		} else if (dataType == BaseProperty.LONG) {
			type = PROPERTY_TYPES.LONG;
		} else if (dataType == BaseProperty.INTEGER
				|| dataType == BaseProperty.BOOLEAN) {
			type = PROPERTY_TYPES.INTEGER;
		}

		return type;
	}

	protected int translateRelationType(int relEnum) {
		return ModelGenerator.BE_TYPE_REFERENCE;
	}
	
	private PROPERTY_TYPES translate2EMFRelationType(int relEnum) {
		return PROPERTY_TYPES.CONCEPT_REFERENCE;
	}
	
	private String getEntityConceptNamespace(BaseEntity e){
		if(e == null){
			return "";
		}
		String namespace = "";
		if( e.getAlias() != null && !"".equals(e.getAlias())) {
			namespace = conceptsRoot + e.getNamespace() + e.getAlias();
		} else {
			namespace = conceptsRoot + e.getNamespace()+ e.getName();
		}
		return namespace;
	}
	
	private String getEventNamespace(BaseEntity e){
		if(e == null){
			return "";
		}
		String namespace = "";
		if( e.getAlias() != null && !"".equals(e.getAlias())) {
			namespace = eventsRoot + e.getNamespace() + e.getAlias();
		} else {
			namespace = eventsRoot + e.getNamespace() + e.getName();
		}
		return namespace;
	}
	
	private String getConceptName(BaseEntity e) {
		if(e == null){
			return "";
		}

		if( e.getAlias() != null && !"".equals(e.getAlias())) {
			return e.getAlias();
		} else {
			return e.getName();
		}
	}
	
	private String getPropertyName(BaseProperty p) {
		if(p == null){
			return "";
		}

		if( p.getAlias() != null && !"".equals(p.getAlias())) {
			 return p.getAlias();
		} else {
			return p.getName();
		}
	}
	
	private String getEventName(BaseEntity e) {
		if(e == null){
			return "";
		}

		if( e.getAlias() != null && !"".equals(e.getAlias())) {
			return e.getAlias();
		} else {
			return e.getName();
		}
	}
	
	/**
	 * Function to convert an entityIdentifier to valid java identifier, here only SQL special symbols are handled for concept and event names as 
	 * they are probable to occur in case of DB table names.
	 * Here, though '$' is a valid java identifier it is also replace with a '_' as 
	 * designer does not allow $ in concept , events and property names.  
	 * @param entitiyIdentifier
	 * @return
	 */
	private String convertToValidJavaIdentifier(String entitiyIdentifier) {
		String validIdentifier = entitiyIdentifier.replace('-', '_');
		validIdentifier = validIdentifier.replace('$', '_');
		validIdentifier = validIdentifier.replace('~', '_');
		validIdentifier = validIdentifier.replace('^', '_');
		validIdentifier = validIdentifier.replace('&', '_');
		validIdentifier = validIdentifier.replace(' ', '_');
		validIdentifier = validIdentifier.replace('#', '_');
		return validIdentifier;
		
	}

}
