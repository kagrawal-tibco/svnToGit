package com.tibco.cep.dashboard.psvr.mal.store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationFactory;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.TextComponent;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

//TODO think about making maps concurrent. Do I need to make them so ?
public abstract class XMIPersistentStore extends PersistentStore {

	private static final String UTF_8_ENCODING = "UTF-8";
	protected Map<String, Entity> idToElementsMap;
	protected Map<String, String> nameToIdMap;
	protected Map<String, List<String>> typeToIdsMap;
	protected Map<String, List<String>> typeToNamesMap;

	protected Map<String, String> URIToIdMap;
	protected Map<String, String> idToURIMap;

	public XMIPersistentStore(PersistentStore parent, Identity identity) {
		super(parent, identity);
		idToElementsMap = new HashMap<String, Entity>();
		nameToIdMap = new HashMap<String, String>();
		typeToIdsMap = new HashMap<String, List<String>>();
		typeToNamesMap = new HashMap<String, List<String>>();
		URIToIdMap = new HashMap<String, String>();
		idToURIMap = new HashMap<String, String>();
	}

	protected void cacheIt(String id, String name, Entity entity) throws MALException {
		String uri = getURI(entity).toString();
		if (logger.isEnabledFor(Level.DEBUG)) {
			logger.log(Level.DEBUG, "Caching " + entity + " against " + id + "," + name + "," + uri + " under " + identity);
		}
		if (URIToIdMap.containsKey(uri) == true) {
			Entity existingEntity = idToElementsMap.get(URIToIdMap.get(uri));
			if (existingEntity.getGUID().equals(id) == false) {
				String entityToStr = entity.eClass().getName() + "[id=" + id + ",name=" + name + "]";
				String existingEntityToStr = existingEntity.eClass().getName() + "[id=" + existingEntity.getGUID() + ",name=" + existingEntity.getName() + "]";
				throw new MALException(uri + " requested for " + entityToStr + " is already mapped to " + existingEntityToStr);
			}
		}

		// ntamhank: Check for duplicate GUID, throw DuplicateGUIDException
		if (idToElementsMap.containsKey(id)) {
			if (idToElementsMap.get(id).equals(entity) == false) {
				logger.log(Level.WARN, "Duplicate GUID found: %s for entity: %s", id, name);
	            logger.log(Level.WARN, "Generating new GUID");
				throw new DuplicateGUIDException("Duplicate GUID: "+ id + " for " + name + ", " + uri + " clashes with " + idToElementsMap.get(id).getName());
			}
		}

		idToElementsMap.put(id, entity);
		nameToIdMap.put(name, id);

		String type = entity.eClass().getName();
		List<String> ids = typeToIdsMap.get(type);
		if (ids == null) {
			ids = new LinkedList<String>();
			typeToIdsMap.put(type, ids);
		}
		ids.add(id);

		List<String> names = typeToNamesMap.get(type);
		if (names == null) {
			names = new LinkedList<String>();
			typeToNamesMap.put(type, names);
		}
		names.add(name);

		URIToIdMap.put(uri, id);
		idToURIMap.put(id, uri);
	}

	protected void uncacheIt(String id, String name, Entity entity) {
		String uri = getURI(entity).toString();
		if (logger.isEnabledFor(Level.DEBUG)) {
			logger.log(Level.DEBUG, "Uncaching %s against %s, %s, %s under %s", entity, id, name, uri, identity);
		}
		String type = entity.eClass().getName();

		idToElementsMap.remove(id);
		nameToIdMap.remove(name);

		List<String> ids = typeToIdsMap.get(type);
		if (ids != null) {
			ids.remove(id);
		}

		List<String> names = typeToNamesMap.get(type);
		if (names != null) {
			names.remove(name);
		}

		URIToIdMap.remove(uri);
		idToURIMap.remove(id);
	}

	@Override
	protected Entity loadElementById(String id) {
		return idToElementsMap.get(id);
	}

	@Override
	protected Entity loadElementByName(String name) {
		String id = nameToIdMap.get(name);
		if (id != null) {
			return idToElementsMap.get(id);
		}
		return null;
	}

	@Override
	protected Entity loadElementByTypeAndId(String type, String id) {
		List<String> ids = typeToIdsMap.get(type);
		if (ids != null) {
			if (ids.contains(id) == true) {
				return idToElementsMap.get(id);
			}
		}
		return null;
	}

	@Override
	protected Entity loadElementByTypeAndName(String type, String name) {
		List<String> names = typeToNamesMap.get(type);
		if (names != null) {
			if (names.contains(name) == true) {
				String id = nameToIdMap.get(name);
				if (id != null) {
					return idToElementsMap.get(id);
				}
			}
		}
		return null;
	}

	@Override
	protected List<Entity> loadElementsByType(String type) {
		List<String> ids = typeToIdsMap.get(type);
		if (ids != null) {
			List<Entity> entities = new ArrayList<Entity>(ids.size());
			for (String id : ids) {
				entities.add(idToElementsMap.get(id));
			}
			return entities;
		}
		return null;
	}

	@Override
	public Collection<String> getElementIds() {
		return Collections.unmodifiableCollection(idToElementsMap.keySet());
	}

	@Override
	public Object resolve(Object object) throws MALException {
		if (object instanceof EObject) {
			EObject emfObject = (EObject) object;
			if (emfObject.eIsProxy() == true) {
				emfObject = EcoreUtil.resolve(emfObject, getResourceSet());
				if (emfObject.eIsProxy() == true) {
					if (parent != null) {
						return parent.resolve(object);
					}
					throw new MALException("Could not load " + EcoreUtil.getURI(emfObject));
				}
				return emfObject;
			}
		}
		return object;
	}

	@Override
	protected String getIdentifier(Entity entity) {
		return idToURIMap.get(entity.getGUID());
	}

	@Override
	protected Entity loadElementByIdentitfier(String identifier) {
		String id = URIToIdMap.get(identifier);
		if (StringUtil.isEmptyOrBlank(id) == true) {
			return null;
		}
		return idToElementsMap.get(id);
	}

	@Override
	public Entity createElement(String definitionType) {
		EClass classifier = (EClass) BEViewsConfigurationFactory.eINSTANCE.getEPackage().getEClassifier(definitionType);
		if (classifier == null) {
			// Child not found
			System.out.println("Element could not be created");
		}
		Entity entity = (Entity) BEViewsConfigurationFactory.eINSTANCE.create(classifier);
		entity.setGUID(GUIDGenerator.getGUID());

		// INFO setting newly created entity's owner project as identity.toString()
		entity.setOwnerProjectName(identity.toString());
		setDefaultProperties(entity);
		if (logger.isEnabledFor(Level.DEBUG) == true){
			logger.log(Level.DEBUG,"Created %s for %s", entity, definitionType);
		}
		return entity;
	}

	@Override
	public void deleteElementById(String id) {
		Entity entity = idToElementsMap.get(id);
		boolean deleted = deleteElement(entity);
		if (deleted == true) {
			uncacheIt(entity.getGUID(), entity.getName(), entity);
		}
	}

	@Override
	protected Entity saveElement(Entity entity) throws MALException {
		if (entity instanceof BEViewsElement == false) {
			throw new IllegalArgumentException(entity.getName() + " is not an instance of " + BEViewsElement.class.getName());
		}
		try {
			((BEViewsElement)entity).setVersion(Long.toString(System.currentTimeMillis()));
			URI uri = generateStorageURI(entity);
			// using XMI
			Resource resource = getResourceSet().createResource(uri);
			resource.getContents().add(entity);
			HashMap<Object, Object> options = new HashMap<Object, Object>();
			options.put(XMIResource.OPTION_ENCODING, UTF_8_ENCODING);
			resource.save(options);
			// now update the entity's uri to referenceable uri
			updateURI(entity, generateReferenceableURI(entity));
			if (logger.isEnabledFor(Level.DEBUG) == true){
				logger.log(Level.DEBUG, "Saved %s in %s", entity, uri);
			}
			cacheIt(entity.getGUID(), entity.getName(), entity);
			return entity;
		} catch (IOException e) {
			throw new MALException("could not save " + entity.getName(), e);
		}
	}

	protected URI getURI(Entity entity) {
		URI uri = null;
		if (entity.eIsProxy() == true) {
			uri = ((InternalEObject) entity).eProxyURI();
		} else {
			uri = entity.eResource().getURI();
		}
		return uri;
	}

	protected abstract URI generateStorageURI(Entity entity);

	protected URI generateReferenceableURI(Entity entity) {
		String namespace = entity.getNamespace();
		if (namespace == null) {
			throw new IllegalArgumentException(entity.eClass().getName() + "[id=" + entity.getGUID() + ",name=" + entity.getName() + "] has no namespace defined");
		}
		URI entityURI = EcoreUtil.getURI(entity);
		URI newURI = URI.createURI(getReferenceableRootURI().toString());
		String[] namespaceSegments = namespace.split("/");
		for (String segment : namespaceSegments) {
			if (StringUtil.isEmptyOrBlank(segment) == false) {
				newURI = newURI.appendSegment(segment);
			}
		}
		newURI = newURI.appendSegment(entityURI.lastSegment());
		if (entityURI.hasFragment() == true) {
			newURI = newURI.appendFragment(entityURI.fragment());
		}
		return newURI;
	}

	protected abstract URI getReferenceableRootURI();

	protected String getExtension(Entity entity) {
		String extension = BEViewsElementNames.getExtension(entity.eClass().getName());
		if (entity instanceof ChartComponent || entity instanceof TextComponent) {
			extension = "chart";
		}
		return extension;
	}

	protected URI updateURI(Entity entity, URI uri) {
		if (entity.eIsProxy() == true) {
			((InternalEObject) entity).eSetProxyURI(uri);
		} else {
			entity.eResource().setURI(uri);
		}
		return uri;
	}

	protected abstract ResourceSet getResourceSet();

	protected abstract void setDefaultProperties(Entity entity);

	protected abstract boolean deleteElement(Entity entity);

	@Override
	protected void shutdown() {
		this.typeToIdsMap.clear();
		this.typeToNamesMap.clear();
		this.nameToIdMap.clear();
		this.URIToIdMap.clear();
		this.idToURIMap.clear();
		this.idToElementsMap.clear();
	}

}