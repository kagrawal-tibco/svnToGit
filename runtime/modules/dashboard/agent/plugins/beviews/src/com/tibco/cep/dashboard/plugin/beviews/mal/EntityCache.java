package com.tibco.cep.dashboard.plugin.beviews.mal;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.naming.NamingException;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.ManagementConfigurator.MODE;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.ManagementUtils;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.management.ServiceContext;
import com.tibco.cep.dashboard.plugin.beviews.BackingStore;
import com.tibco.cep.dashboard.psvr.mal.CacheController;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALSession;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALExternalReference;
import com.tibco.cep.dashboard.security.SecurityClient;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.studio.core.adapters.ConceptAdapter;
import com.tibco.xml.data.primitive.ExpandedName;

public class EntityCache extends CacheController<Entity> {

	private static EntityCache instance;

	public static final synchronized EntityCache getInstance() {
		if (instance == null) {
			instance = new EntityCache();
		}
		return instance;
	}

	private RuleServiceProvider ruleServiceProvider;

	BEClassLoader classLoader;

	private Map<String, Entity> dvmMetricsIndexMap;

	private SecurityClient securityClient;

	private SecurityToken sysToken;

	private MALSession sysMALSession;

	private Set<String> superClasssEntitiesByScopeNames;

	protected EntityCache() {
		super("BEEntityCache", "BusinessEvents Entity Cache");
		dvmMetricsIndexMap = new HashMap<String, Entity>();
		superClasssEntitiesByScopeNames = new HashSet<String>();
	}

	@Override
	public void init(Logger parentLogger, MODE mode, Properties properties, ServiceContext serviceContext) throws ManagementException {
		this.logger = parentLogger;
		this.mode = mode;
		this.properties = properties;
		this.serviceContext = serviceContext;
		this.exceptionHandler = new ExceptionHandler(logger);
		this.messageGenerator = new MessageGenerator("beviews",exceptionHandler);
		super.init(parentLogger, mode, properties, serviceContext);
	}

	@Override
	protected void doInit() throws ManagementException {
		ruleServiceProvider = serviceContext.getRuleServiceProvider();
		classLoader = (BEClassLoader) ruleServiceProvider.getClassLoader();
	}

	@Override
	protected void doStart() throws ManagementException {
		if (logger.isEnabledFor(Level.DEBUG) == true){
			logger.log(Level.DEBUG,"Acquiring system token");
		}
		initSysToken();
		if (logger.isEnabledFor(Level.DEBUG) == true){
			logger.log(Level.DEBUG,"Creating a system level MAL Session");
		}
		try {
			sysMALSession = new MALSession(sysToken);
		} catch (Exception e) {
			String msg = messageGenerator.getMessage("entitycache.systemmalsession.failure");
			throw new ManagementException(msg, e);
		}
		if (logger.isEnabledFor(Level.DEBUG) == true){
			logger.log(Level.DEBUG,"Loading entity cache");
		}
		String type = "Metric";
		try {
			//load metrics
			List<MALElement> externalRefs = sysMALSession.getElementsByType(type);
			for (MALElement externalRef : externalRefs) {
				Metric metric = (Metric) ((MALExternalReference)externalRef).getExternalReference();
				// store the metric against it's id
				addObject(metric.getGUID(), metric);
				// create dvm metric
				//PATCH hard coding how to pick up dvm type descriptor
				addDVMMetric(metric, classLoader.getTypeDescriptor(metric.getFullPath()+"DVM"));
			}
			type = "Concept";
			//load concepts
			externalRefs = sysMALSession.getElementsByType(type);
			for (MALElement externalRef : externalRefs) {
				Concept concept = (Concept) ((MALExternalReference)externalRef).getExternalReference();
				if(isViewable(concept) == true) {
					// store the concept against it's id
					addObject(concept.getGUID(), concept);
					//determine if the concept is in a type hierarchy
					if (StringUtil.isEmptyOrBlank(concept.getSuperConceptPath()) == false) {
						//add the super concept to the superClasssEntitiesByScopeNames
						superClasssEntitiesByScopeNames.add(concept.getSuperConceptPath());
					}
				}
			}
		} catch (MALException e) {
			String msg = messageGenerator.getMessage("entitycache.entity.loading.failure", new MessageGeneratorArgs(e,type));
			throw new ManagementException(msg, e);
		}
	}

	private void addDVMMetric(Metric metric, TypeDescriptor typeDescriptor) {
		// clone the metric definition
		Metric metricDVM = (Metric) EcoreUtil.copy(metric);
		// change the guid
		metricDVM.setGUID(metric.getGUID()+"@dvm");
		//INFO update the name of the dvm to be metric name + DVM, that's how the backend names are
		metricDVM.setName(metric.getName()+"DVM");
		//add all incoming metric fields
		List<PropertyDefinition> metricFieldsInDVM = metricDVM.getProperties();
		List<PropertyDefinition> metricDVMFields = new LinkedList<PropertyDefinition>();
		//derive all the property names
		String[] declaredPropsInDVM = derivePropertyNames(typeDescriptor);
		// iterate over the metric DVM Fields (which are actually the metric fields)
		for (PropertyDefinition metricField : metricFieldsInDVM) {
			metricDVMFields.add(metricField);
			if (metricField.isGroupByField() == false) {
				//we are dealing with aggregate fields
				//add incoming field , if any
				PropertyDefinition incomingField = createIncomingField(declaredPropsInDVM, metricField);
				if (incomingField != null) {
					metricDVMFields.add(incomingField);
				}
			}
		}
		// add all user defined properties
		List<PropertyDefinition> userDefinedFields = metricDVM.getUserDefinedFields();
		for (PropertyDefinition userDefinedField : userDefinedFields) {
			//INFO setting a special property 'userdefined' to 'true' on DVM Metric user defined fields
			addProperty(userDefinedField, "userdefined", "true");
			metricDVMFields.add(userDefinedField);
		}

		// add the pid$ field
		metricDVMFields.add(createPID$Field(metricFieldsInDVM.get(0)));

		//update the metric dvm properties
		metricDVM.getProperties().clear();
		metricDVM.getProperties().addAll(metricDVMFields);

		EntityUtils.markAsDVM(metricDVM, metric);

		dvmMetricsIndexMap.put(metricDVM.getGUID(), metricDVM);
	}

	private String[] derivePropertyNames(TypeDescriptor typeDescriptor) {
		Method[] declaredMethods = typeDescriptor.getImplClass().getDeclaredMethods();
		List<String> getterMethodNames = new LinkedList<String>();
		List<String> setterMethodNames = new LinkedList<String>();
		for (Method method : declaredMethods) {
			if (method.getName().startsWith("get") == true) {
				getterMethodNames.add(method.getName().substring(3));
			}
			if (method.getName().startsWith("set") == true) {
				setterMethodNames.add(method.getName().substring(3));
			}
		}
		List<String> propertyNames = new LinkedList<String>();
		if (getterMethodNames.size() >= setterMethodNames.size()) {
			propertyNames.addAll(getterMethodNames);
			propertyNames.retainAll(setterMethodNames);
		}
		else {
			propertyNames.addAll(setterMethodNames);
			propertyNames.retainAll(getterMethodNames);
		}
		return propertyNames.toArray(new String[propertyNames.size()]);
	}

	private PropertyDefinition createIncomingField(String[] declaredPropsInDVM, PropertyDefinition baseField) {
		for (String declaredPropName : declaredPropsInDVM) {
			if (declaredPropName.endsWith("I_"+baseField.getName()) == true) {
				PropertyDefinition incomingField = (PropertyDefinition) EcoreUtil.copy(baseField);
				//update the GUID
				incomingField.setGUID(baseField.getGUID()+"@incoming");
				//update the name
				incomingField.setName("I_"+baseField.getName());
				//INFO setting a special property 'incoming' to 'true' on DVM Metric incoming fields
				addProperty(incomingField, "incoming", "true");
				//INFO setting a special property 'supports' to baseField.getGUID on DVM Metric incoming fields
				addProperty(incomingField, "supports", baseField.getGUID());
				return incomingField;
			}
		}
		return null;
	}

	private PropertyDefinition createPID$Field(PropertyDefinition templateField) {
		PropertyDefinition pid$Field = EcoreUtil.copy(templateField);
		pid$Field.setGUID(GUIDGenerator.getGUID());
		pid$Field.setName(ConceptAdapter.METRIC_DVM_PARENT_ID_NAME);
		pid$Field.setArray(false);
		pid$Field.setType(PROPERTY_TYPES.LONG);
		PropertyMap extendedPropertiesHolder = pid$Field.getExtendedProperties();
    	if (extendedPropertiesHolder == null) {
    		extendedPropertiesHolder = ModelFactory.eINSTANCE.createPropertyMap();
    		pid$Field.setExtendedProperties(extendedPropertiesHolder);
    	}
    	//INFO setting a special property 'parentidentifier' to 'true' for DVM Metric pid$ field
    	addProperty(pid$Field, "parentidentifier", "true");
    	//INFO setting a special property 'system' to 'true' for DVM Metric pid$ field
    	addProperty(pid$Field, "system", "true");
    	return pid$Field;
	}

	private void addProperty(PropertyDefinition field, String name, String value) {
		PropertyMap extendedPropertiesHolder = field.getExtendedProperties();
    	if (extendedPropertiesHolder == null) {
    		extendedPropertiesHolder = ModelFactory.eINSTANCE.createPropertyMap();
    		field.setExtendedProperties(extendedPropertiesHolder);
    	}
    	List<Entity> extendedPropertyList = extendedPropertiesHolder.getProperties();
    	SimpleProperty propertyObject = ModelFactory.eINSTANCE.createSimpleProperty();
    	propertyObject.setName(name);
    	propertyObject.setValue(value);
    	extendedPropertyList.add(propertyObject);
	}


	@SuppressWarnings("unchecked")
	private boolean isViewable(Concept concept){
		if (BackingStore.isJDBC() == false) {
			//the backing store is not enabled, we allow all concepts to be queried
			return true;
		}
		//backing store is enabled with jdbc, only show concepts which have backing store on
		TypeDescriptor typeDescriptor = getTypeDescriptor(concept);
		Cluster cacheCluster = CacheClusterProvider.getInstance().getCacheCluster();
		EntityDao<Object,com.tibco.cep.kernel.model.entity.Entity> entityDao = cacheCluster.getDaoProvider().getEntityDao(typeDescriptor.getImplClass());
		if (entityDao != null) {
			if (entityDao.getConfig().hasBackingStore() == true) {
				//PATCH update the isViewable(Concept) to use extended property to decide whether to show a concept or not
				return true;
			}
		}
		return false;

	}

	public Entity getEntity(String guid) {
		if (StringUtil.isEmptyOrBlank(guid) == true){
			throw new IllegalArgumentException("invalid guid - "+guid);
		}
		Entity object = getObject(guid);
		if (object == null){
			object = dvmMetricsIndexMap.get(guid);
		}
		if (object == null){
			throw new IllegalArgumentException("could not find an entity with id as "+guid);
		}
		return object;
	}

	public Entity getEntityByFullPath(String fullPath){
		if (StringUtil.isEmptyOrBlank(fullPath) == true){
			throw new IllegalArgumentException("invalid full path - "+fullPath);
		}
		TypeDescriptor typeDescriptor = getTypeDescriptorByFullPath(fullPath);
		Entity entity = internalGetEntity(typeDescriptor);
		if (entity == null){
			throw new IllegalArgumentException("could not find an entity with full path as "+fullPath);
		}
		return entity;
	}

	public Entity getEntity(ExpandedName name){
		if (name == null){
			throw new IllegalArgumentException("invalid expanded name  - "+name);
		}
		TypeDescriptor typeDescriptor = getTypeDescriptor(name);
		Entity entity = internalGetEntity(typeDescriptor);
		if (entity == null){
			throw new IllegalArgumentException("could not find an entity with expanded name as "+name);
		}
		return entity;
	}

	public Entity getEntity(TypeDescriptor typeDescriptor){
		Entity entity = internalGetEntity(typeDescriptor);
		if (entity == null){
			throw new IllegalArgumentException("could not find an entity for type descriptor "+typeDescriptor);
		}
		return entity;
	}

	private Entity internalGetEntity(TypeDescriptor typeDescriptor) {
		Iterator<Entity> allEntities = getAllObjects(true);
		while (allEntities.hasNext()) {
			Entity entity = (Entity) allEntities.next();
			TypeDescriptor entitySpecificTypeDescriptor = getTypeDescriptor(entity);
			if (entitySpecificTypeDescriptor.compareTo(typeDescriptor) == 0){
				return entity;
			}
		}
		return null;
	}

	public TypeDescriptor getTypeDescriptor(ExpandedName name) {
		if (name == null){
			throw new IllegalArgumentException("invalid expanded name  - "+name);
		}
		TypeDescriptor typeDescriptor = classLoader.getTypeDescriptor(name);
		if (typeDescriptor == null){
			throw new IllegalArgumentException("could not find an type descriptor with expanded name as "+name);
		}
		return typeDescriptor;
	}

	public TypeDescriptor getTypeDescriptor(String guid) {
		Entity entity = getEntity(guid);
		return getTypeDescriptor(entity);
	}

	public TypeDescriptor getTypeDescriptor(Entity entity) {
		if (entity == null){
			throw new IllegalArgumentException("invalid entity - "+entity);
		}
		TypeDescriptor typeDescriptor = classLoader.getTypeDescriptor(entity.getFullPath());
		if (typeDescriptor == null){
			throw new IllegalArgumentException("could not find an type descriptor for "+entity);
		}
		return typeDescriptor;
	}

	public TypeDescriptor getTypeDescriptorByFullPath(String fullPath) {
		TypeDescriptor typeDescriptor = classLoader.getTypeDescriptor(fullPath);
		if (typeDescriptor == null){
			throw new IllegalArgumentException("could not find an type descriptor for "+fullPath);
		}
		return typeDescriptor;
	}

	public final Iterator<String> getAllTransientKeys(){
		return dvmMetricsIndexMap.keySet().iterator();
	}

	public final Iterator<String> getAllKeys(boolean includeTransients) {
		if (includeTransients == false){
			return super.getAllKeys();
		}
		return new Iterator<String>(){

			Iterator<String> cacheIterator = EntityCache.this.getAllKeys();

			Iterator<String> transientsIterator = dvmMetricsIndexMap.keySet().iterator();

			@Override
			public boolean hasNext() {
				boolean hasNext = cacheIterator.hasNext();
				if (hasNext == false){
					hasNext = transientsIterator.hasNext();
				}
				return hasNext;
			}

			@Override
			public String next() {
				boolean hasNext = cacheIterator.hasNext();
				if (hasNext == true){
					return cacheIterator.next();
				}
				if (transientsIterator.hasNext() == true){
					return transientsIterator.next();
				}
				return null;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("remove");
			}

		};
	}

	public final Iterator<Entity> getAllTransientObjects(){
		return dvmMetricsIndexMap.values().iterator();
	}

	public final Iterator<Entity> getAllObjects(boolean includeTransients) {
		if (includeTransients == false){
			return super.getAllObjects();
		}
		return new Iterator<Entity>(){

			Iterator<Entity> cacheIterator = EntityCache.this.getAllObjects();

			Iterator<Entity> transientsIterator = dvmMetricsIndexMap.values().iterator();

			@Override
			public boolean hasNext() {
				boolean hasNext = cacheIterator.hasNext();
				if (hasNext == false){
					hasNext = transientsIterator.hasNext();
				}
				return hasNext;
			}

			@Override
			public Entity next() {
				boolean hasNext = cacheIterator.hasNext();
				if (hasNext == true){
					return cacheIterator.next();
				}
				if (transientsIterator.hasNext() == true){
					return transientsIterator.next();
				}
				return null;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("remove");
			}

		};
	}

	public List<Entity> searchByName(String name) {
		List<Entity> results = new LinkedList<Entity>();
		Iterator<Entity> allObjectsIterator = getAllObjects(true);
		while (allObjectsIterator.hasNext()) {
			Entity entity = allObjectsIterator.next();
			if (entity.getName().equals(name) == true) {
				results.add(entity);
			}
			else if (entity.getFullPath().equals(name) == true){
				results.add(entity);
			}
		}
		return results;
	}

	public boolean isASuperClass(Entity entity) {
		return superClasssEntitiesByScopeNames.contains(entity.getFullPath());
	}

	@Override
	protected synchronized boolean doStop() {
		dvmMetricsIndexMap.clear();
		superClasssEntitiesByScopeNames.clear();
		if (securityClient != null && sysToken != null) {
			securityClient.logout(sysToken);
		}
		if (securityClient != null) {
			try {
				securityClient.cleanup();
			} catch (ManagementException e) {
				//TODO handle securityClient.cleanup() exception
			}
		}
		return super.doStop();
	}

	private void initSysToken() throws ManagementException{
		try {
			securityClient = (SecurityClient) ManagementUtils.getContext().lookup("security");
		} catch (NamingException ex) {
			String msg = messageGenerator.getMessage("entitycache.security.lookup.failure");
			throw new ManagementException(msg,ex);
		}
		//PATCH find a better way to get system level token
		try {
			sysToken = securityClient.login(null, null);
		} catch (Exception e) {
			String msg = messageGenerator.getMessage("entitycache.security.syslogin.failure");
			throw new ManagementException(msg,e);
		}
	}

}