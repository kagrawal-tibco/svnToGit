package com.tibco.cep.dashboard.integration.embedded;

import java.util.Collection;
import java.util.List;

import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.store.Identity;
import com.tibco.cep.dashboard.psvr.mal.store.PersistentStore;
import com.tibco.cep.designtime.core.model.Entity;

public class EPersistentStore extends PersistentStore {

	public EPersistentStore(PersistentStore parent, Identity identity) {
		super(parent, identity);
	}

	@Override
	public Entity createElement(String definitionType) {
		return null;
	}

	@Override
	public void deleteElementById(String id) {

	}

	@Override
	public Collection<String> getElementIds() {
		return null;
	}

	@Override
	protected String getIdentifier(Entity entity) {
		return null;
	}

	@Override
	protected void init() throws MALException {

	}

	@Override
	protected Entity loadElementById(String id) {
		return null;
	}

	@Override
	protected Entity loadElementByIdentitfier(String identifier) {
		return null;
	}

	@Override
	protected Entity loadElementByName(String name) {
		return null;
	}

	@Override
	protected Entity loadElementByTypeAndId(String type, String id) {
		return null;
	}

	@Override
	protected Entity loadElementByTypeAndName(String type, String name) {
		return null;
	}

	@Override
	protected List<Entity> loadElementsByType(String type) {
		return null;
	}

	@Override
	public Object resolve(Object object) {
		return object;
	}

	@Override
	protected Entity saveElement(Entity entity) throws MALException {
		return null;
	}

	@Override
	protected void shutdown() {

	}
	
	public PersistentStore getStore(Identity identity) throws MALException{
		return this;
	}

	@Override
	public PersistentStore getOwningStore(MALElement element) {
		return this;
	}
}
