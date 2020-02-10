package com.tibco.cep.dashboard.psvr.mal.store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.ServiceContext;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALElementManagerFactory;
import com.tibco.cep.dashboard.psvr.mal.MALElementsCollector;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.managers.MALElementManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALExternalReference;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.kernel.service.logging.Logger;

public abstract class PersistentStore {

	protected Logger logger;
	protected ServiceContext serviceContext;
	protected Properties properties;
	protected ExceptionHandler exceptionHandler;
	protected MessageGenerator messageGenerator;

	protected final PersistentStore parent;
	protected final Identity identity;

	public PersistentStore(PersistentStore parent, Identity identity) {
		this.parent = parent;
		this.identity = identity;
	}

	public final Identity getIdentity(){
		return identity;
	}

	protected abstract void init() throws MALException;

	public MALElement getElementById(String id,MALElementsCollector elementsCollector) throws MALException, ElementNotFoundException {
		Entity entity = loadElementById(id);
		PersistentStore parentStore = parent;
		while (entity == null && parentStore != null){
			entity = parentStore.loadElementById(id);
			parentStore = parentStore.parent;
		}
		if (entity != null){
			return toMALElement(null, entity, elementsCollector);
		}
		throw new ElementNotFoundException("could not find element with id as"+id+" under "+identity);
	}

	public MALElement getElementByName(String name,MALElementsCollector elementsCollector) throws MALException, ElementNotFoundException {
		Entity entity = loadElementByName(name);
		PersistentStore parentStore = parent;
		while (entity == null && parentStore != null){
			entity = parentStore.loadElementByName(name);
			parentStore = parentStore.parent;
		}
		if (entity != null){
			return toMALElement(null, entity, elementsCollector);
		}
		throw new ElementNotFoundException("could not find element with name as"+name+" under "+identity);
	}

	public List<MALElement> getElementsByType(String type,MALElementsCollector elementsCollector) throws MALException {
		List<Entity> entities = loadElementsByType(type);
		PersistentStore parentStore = parent;
		while ((entities == null || entities.isEmpty() == true) && parentStore != null){
			entities = parentStore.loadElementsByType(type);
			parentStore = parentStore.parent;
		}
		if (entities != null && entities.isEmpty() == false){
			return toMALElements(entities, elementsCollector);
		}
		return new LinkedList<MALElement>();
	}

	public MALElement getElementByTypeAndId(String type, String id,MALElementsCollector elementsCollector) throws MALException, ElementNotFoundException {
		Entity entity = loadElementByTypeAndId(type,id);
		PersistentStore parentStore = parent;
		while (entity == null && parentStore != null){
			entity = parentStore.loadElementByTypeAndId(type,id);
			parentStore = parentStore.parent;
		}
		if (entity != null){
			return toMALElement(null, entity, elementsCollector);
		}
		throw new ElementNotFoundException("could not find element of type "+type+" with id as"+id+" under "+identity);
	}

	public MALElement getElementByTypeAndName(String type, String name,MALElementsCollector elementsCollector) throws MALException, ElementNotFoundException {
		Entity entity = loadElementByTypeAndName(type,name);
		PersistentStore parentStore = parent;
		while (entity == null && parentStore != null){
			entity = parentStore.loadElementByTypeAndName(type,name);
			parentStore = parentStore.parent;
		}
		if (entity != null){
			return toMALElement(null, entity, elementsCollector);
		}
		throw new ElementNotFoundException("could not find "+type+" with name as "+name+" under "+identity);
	}

	public MALElement getElementByIdentifier(String identifier,MALElementsCollector elementsCollector) throws MALException, ElementNotFoundException{
		Entity entity = loadElementByIdentitfier(identifier);
		PersistentStore parentStore = parent;
		while (entity == null && parentStore != null){
			entity = parentStore.loadElementByIdentitfier(identifier);
			parentStore = parentStore.parent;
		}
		if (entity != null){
			return toMALElement(null, entity, elementsCollector);
		}
		throw new ElementNotFoundException("could not find element with identifier as"+identifier+" under "+identity);
	}

	public String getIdentifier(MALElement element) throws MALException{
		PersistentStore owningStore = getOwningStore(element);
		if (owningStore == null){
			throw new MALException("could not determine owning persistent store for "+element);
		}
		return owningStore.getIdentifier((Entity)element.getPersistedObject());
	}

	private MALElement toMALElement(MALElementManager manager, Entity entity, MALElementsCollector elementsCollector) throws MALException {
		if (entity instanceof BEViewsElement) {
			if (manager == null) {
				String elementType = entity.eClass().getName();
				manager = MALElementManagerFactory.getInstance().getManager(elementType);
			}
			MALElement element = manager.load(this, (BEViewsElement) entity, elementsCollector);
			return element;
		}
		throw new IllegalArgumentException(entity.getName()+" is not parsable");
	}

	private List<MALElement> toMALElements(List<Entity> entities, MALElementsCollector elementsCollector) throws MALException {
		if (entities == null || entities.isEmpty() == true){
			return null;
		}
		MALElementManager manager = null;
		List<MALElement> malElements = new ArrayList<MALElement>();
		for (Entity entity : entities) {
			if (entity instanceof BEViewsElement) {
				if (manager == null){
					String elementType = entity.eClass().getName();
					manager = MALElementManagerFactory.getInstance().getManager(elementType);
				}
				malElements.add(toMALElement(manager, (BEViewsElement) entity, elementsCollector));
			} else {
				//throw new IllegalArgumentException(entity.getName()+" is not parsable");
				malElements.add(new MALExternalReference(resolve(entity)));
			}
		}
		return malElements;
	}

	public void saveElement(MALElement malElement,MALElementsCollector elementsCollector) throws MALException, IOException {
		MALElementManager manager = MALElementManagerFactory.getInstance().getManager(malElement.getDefinitionType());
		if (manager == null){
			throw new IllegalArgumentException(malElement+" does not have a manager");
		}
		manager.save(this, malElement, elementsCollector);
		saveElement((Entity) malElement.getPersistedObject());
	}

	public abstract Entity createElement(String definitionType);

	protected abstract Entity loadElementById(String id);

	protected abstract List<Entity> loadElementsByType(String type);

	protected abstract Entity loadElementByTypeAndId(String type, String id);

	protected abstract Entity loadElementByTypeAndName(String type, String name);

	protected abstract Entity loadElementByName(String name);

	protected abstract Entity loadElementByIdentitfier(String identifier);

	protected abstract Entity saveElement(Entity entity) throws MALException;

	protected abstract String getIdentifier(Entity entity);

	public abstract Collection<String> getElementIds();

	//PATCH we should ideally throw ElementNotFoundException here
	public abstract Object resolve(Object object) throws MALException;

	protected abstract void shutdown();

	public abstract void deleteElementById(String id);

	public PersistentStore getOwningStore(MALElement element) {
		boolean owns = getElementIds().contains(element.getId());
		if (owns == true){
			return this;
		}
		if (parent == null){
			throw new IllegalStateException("no owning store found for "+element);
		}
		return parent.getOwningStore(element);
	}

	public boolean isOwnedBy(SecurityToken token){
		if (identity.getId().equals(token.getUserID()) == true){
			if (identity.getParent() != null && identity.getParent().getId().equals(token.getPreferredPrincipal().getName()) == true){
				return true;
			}
		}
		return false;
	}
}
