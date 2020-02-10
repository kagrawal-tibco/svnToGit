package com.tibco.cep.dashboard.psvr.mal;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.dashboard.psvr.mal.MALTransactionException.CAUSE_OP;
import com.tibco.cep.dashboard.psvr.mal.managers.MALElementManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALBEViewsElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALUserPreference;
import com.tibco.cep.dashboard.psvr.mal.store.PersistentStore;
import com.tibco.cep.dashboard.psvr.mal.store.PersistentStoreFactory;
import com.tibco.cep.dashboard.psvr.mal.store.RoleIdentity;
import com.tibco.cep.dashboard.psvr.mal.store.UserIdentity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class MALTransaction {

	private static final String ROLLBACKED = "rollbacked";

	private static final String COMMITTED = "committed";

	static Logger LOGGER;

	private static ThreadLocal<MALTransaction> threadLocalTransaction = new ThreadLocal<MALTransaction>();

//	private Map<MALElement, List<String>> modifiedElements;

//	private Map<MALElement, List<MALElement>> affectedTopLevelElements;

	protected Map<String,Change> changeRecords;

	protected MALSession session;

	protected String state;

	protected MALUserPreference userPreference;

//	protected PersistentStore userPersistentStore;

	protected Set<MALElement> deletedElements;

	protected String userID;

	protected boolean recordElementChanges;

	protected MALTransaction(){

	}

	protected MALTransaction(MALSession session) {
		this.session = session;
		this.userID = this.session.getToken().getUserID();
		this.changeRecords = new HashMap<String, Change>();
//		modifiedElements = new HashMap<MALElement, List<String>>();
//		affectedTopLevelElements = new HashMap<MALElement, List<MALElement>>();
		deletedElements = new HashSet<MALElement>();
		recordElementChanges = true;
		if (LOGGER.isEnabledFor(Level.DEBUG) == true){
			LOGGER.log(Level.DEBUG,"Created transaction for "+userID+" using "+session.token);
		}
	}

	public MALUserPreference createUserPreference() throws MALTransactionException {
		try {
//			userPersistentStore = PersistentStoreFactory.getInstance().getStore(new UserIdentity(new RoleIdentity(session.principal.getName()),userID));
			userPreference = new MALUserPreference(userID,session.persistentStore,session.rolePreference, new MALElementsCollector());
			if (LOGGER.isEnabledFor(Level.DEBUG) == true){
				LOGGER.log(Level.DEBUG,"Created "+userPreference+" using "+session.token);
			}
			return userPreference;
		} catch (MALException e) {
			throw new MALTransactionException(MALTransactionException.CAUSE_OP.EXECUTION,e);
		} catch (ElementNotFoundException e) {
			throw new MALTransactionException(MALTransactionException.CAUSE_OP.EXECUTION,e);
		}
	}

	protected void validateState() {
		if (state != null) {
			throw new IllegalArgumentException("Transaction has already been " + state);
		}
	}

	public void recordChange(MALElement element, String property, Object oldValue, Object newValue) {
		validateState();
		if (recordElementChanges == false){
			return;
		}
		Change change = new Change(element,property);
		changeRecords.put(URIHelper.getURI(element)+"@"+property, change);
//		List<String> properties = modifiedElements.get(element);
//		if (properties == null) {
//			properties = new LinkedList<String>();
//			modifiedElements.put(element, properties);
//		}
//		properties.add(property);
//		List<MALElement> parentage = element.getParentage();
//		MALElement topLevelParent = parentage.get(0);
//		List<MALElement> affectingElements = affectedTopLevelElements.get(topLevelParent);
//		if (affectingElements == null) {
//			affectingElements = new LinkedList<MALElement>();
//			affectedTopLevelElements.put(topLevelParent, affectingElements);
//		}
//		affectingElements.add(element);
		if (LOGGER.isEnabledFor(Level.DEBUG) == true){
			LOGGER.log(Level.DEBUG,"Recording "+property+" changes for "+element+" with top level element as "+change.topLevelElement+" for "+session.token);
		}
	}

	public void removeChange(MALElement element, String property) {
		validateState();
		if (recordElementChanges == false){
			return;
		}
		changeRecords.remove(URIHelper.getURI(element)+"@"+property);
//		List<String> properties = modifiedElements.get(element);
//		if (properties != null) {
//			properties.remove(property);
//			if (properties.isEmpty() == true) {
//				modifiedElements.remove(element);
//			}
//			MALElement topLevelParent = element.getTopLevelParent();
//			List<MALElement> affectingElements = affectedTopLevelElements.get(topLevelParent);
//			if (affectingElements != null) {
//				affectingElements.remove(element);
//				if (affectingElements.isEmpty() == true) {
//					affectedTopLevelElements.remove(topLevelParent);
//				}
//			}
//		}
	}

	public void removeChange(MALElement element) {
		validateState();
		if (recordElementChanges == false){
			return;
		}
		String keyPrefix = URIHelper.getURI(element);
		List<String> keys = new LinkedList<String>();
		for (String key : changeRecords.keySet()) {
			if (key.startsWith(keyPrefix) == true){
				keys.add(key);
			}
		}
		changeRecords.keySet().removeAll(keys);
//		List<String> properties = modifiedElements.remove(element);
//		if (properties != null) {
//			properties.clear();
//			MALElement topLevelParent = element.getTopLevelParent();
//			List<MALElement> affectingElements = affectedTopLevelElements.get(topLevelParent);
//			if (affectingElements != null) {
//				affectingElements.remove(element);
//				if (affectingElements.isEmpty() == true) {
//					affectedTopLevelElements.remove(topLevelParent);
//				}
//			}
//		}
	}

	public void delete(MALElement element) throws MALTransactionException {
		if (element.isTopLevelElement() == false){
			throw new IllegalArgumentException(element+" is not a top level element");
		}
		if (element.isOwnedBy(session.getToken()) == false){
			throw new IllegalArgumentException(element+" is not owned by "+userID);
		}
		deletedElements.add(element);
		if (LOGGER.isEnabledFor(Level.DEBUG) == true){
			LOGGER.log(Level.DEBUG,"Deleting "+element+" for "+session.token);
		}
	}

	public void commit() throws MALTransactionException {
		if (userPreference == null){
			throw new MALTransactionException(CAUSE_OP.COMMIT,"user preference not created");
		}
		if (LOGGER.isEnabledFor(Level.DEBUG) == true){
			LOGGER.log(Level.DEBUG,"Commiting changes for "+session.token);
		}
		recordElementChanges = false;
		try {
			//taking care of top level elements will take care of all the affected elements
			Collection<MALElement> topLevelElements = getTopLevelElements();
			for (MALElement topLevelElement : topLevelElements) {
				MALBEViewsElement elementToBeSaved = (MALBEViewsElement) topLevelElement;
				//get the manager
				MALElementManager manager = session.getManager(elementToBeSaved.getDefinitionType());
				if (elementToBeSaved.isOwnedBy(session.getToken()) == false){
					if (LOGGER.isEnabledFor(Level.DEBUG) == true){
						LOGGER.log(Level.DEBUG,"Personalizing "+elementToBeSaved+" using "+session.token);
					}
					//we have a personalization on our hand
					//create a copy of the element
					MALBEViewsElement personalizedElement = (MALBEViewsElement) manager.copy(elementToBeSaved, new MALElementsCollector());
					//set the original element as dependency
					personalizedElement.setOriginalElementIdentifier(session.getIdentifier(elementToBeSaved));
					personalizedElement.setOriginalElementVersion(elementToBeSaved.getVersion());
					//save the newly create element using the user's persistent store
					session.persistentStore.saveElement(personalizedElement, new MALElementsCollector());
					//PORT elementToBeSaved.setId(personalizedElement.getId()); ?
					//PORT elementToBeSaved.setFolder(personalizedElement.getFolder());
					//PORT elementToBeSaved.setNamespace(personalizedElement.getNamespace());
					elementToBeSaved.setOwner(personalizedElement.getOwner());
					//INFO Set original element identifier & version
					elementToBeSaved.setOriginalElementIdentifier(personalizedElement.getOriginalElementIdentifier());
					elementToBeSaved.setOriginalElementVersion(personalizedElement.getOriginalElementVersion());
					elementToBeSaved.setPersistedObject(personalizedElement.getPersistedObject());
					userPreference.addElement(elementToBeSaved);
				}
				else {
					if (LOGGER.isEnabledFor(Level.DEBUG) == true){
						LOGGER.log(Level.DEBUG,"Saving "+elementToBeSaved+" using "+session.token);
					}
					//user owns the element, just save it
					session.persistentStore.saveElement(elementToBeSaved, new MALElementsCollector());
				}
			}
			for (MALElement element : deletedElements) {
				if (LOGGER.isEnabledFor(Level.DEBUG) == true){
					LOGGER.log(Level.DEBUG,"Deleting "+element+" using "+session.token);
				}
				session.persistentStore.deleteElementById(element.getId());
				userPreference.removeElement(element);
			}
			if (LOGGER.isEnabledFor(Level.DEBUG) == true){
				LOGGER.log(Level.DEBUG,"Resetting "+userPreference+" using "+session.token);
			}
			userPreference.resetPropertyTracking();
			for (MALElement element : topLevelElements) {
				if (LOGGER.isEnabledFor(Level.DEBUG) == true){
					LOGGER.log(Level.DEBUG,"Resetting "+element+" using "+session.token);
				}
//				element.resetPropertyTracking();
				session.getManager(element.getDefinitionType()).reset(element, (BEViewsElement) element.getPersistedObject(), new MALElementsCollector());
			}
			state = COMMITTED;
		} catch (MALException e) {
			throw new MALTransactionException(MALTransactionException.CAUSE_OP.COMMIT,e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MALTransactionException(MALTransactionException.CAUSE_OP.COMMIT,e);
		} finally {
			recordElementChanges = true;
		}
	}

	public void rollBack() throws MALTransactionException {
		if (LOGGER.isEnabledFor(Level.DEBUG) == true){
			LOGGER.log(Level.DEBUG,"Rolling back changes for "+session.token);
		}
		try {
			for (MALElement element : getTopLevelElements()) {
				if (element.getPersistedObject() == null){
					continue;
				}
				if (LOGGER.isEnabledFor(Level.DEBUG) == true){
					LOGGER.log(Level.DEBUG,"Rolling back "+element+" using "+session.token);
				}
//				PersistentStore pStore = session.persistentStore;
//				if (element.isOwnedBy(session.token) == true){
//					if (pStore.isOwnedBy(session.token) == false) {
//						pStore = userPersistentStore;
//						if (pStore == null){
//							pStore = PersistentStoreFactory.getInstance().getStore(new UserIdentity(new RoleIdentity(session.principal.getName()),userID));
//						}
//					}
//				}
				MALElement newlyLoadedElement = session.persistentStore.getElementByTypeAndId(element.getDefinitionType(), element.getId(), new MALElementsCollector());
				Object resolvedObject = session.persistentStore.resolve(newlyLoadedElement.getPersistedObject());
				session.getManager(element.getDefinitionType()).reset(element, (BEViewsElement) resolvedObject, new MALElementsCollector());
				newlyLoadedElement = null;
			}
			state = ROLLBACKED;
		} catch (MALException e) {
			throw new MALTransactionException(MALTransactionException.CAUSE_OP.ROLLBACK,e);
		} catch (ElementNotFoundException e) {
			throw new MALTransactionException(MALTransactionException.CAUSE_OP.ROLLBACK,e);
		}
	}

	public void close() {
		if (LOGGER.isEnabledFor(Level.DEBUG) == true){
			LOGGER.log(Level.DEBUG,"Closing transaction for "+userID+" using "+session.token);
		}
		if (state == COMMITTED){
			session.userPreference = userPreference;
		}
		relinquishThread();
		session.transaction = null;
		this.changeRecords.clear();
		this.changeRecords = null;
		this.session = null;
	}

	public void acquireThread() {
		threadLocalTransaction.set(this);
	}

	public void relinquishThread() {
		threadLocalTransaction.remove();
	}

	public static MALTransaction getCurrentTransaction() {
		return threadLocalTransaction.get();
	}

	private Collection<MALElement> getTopLevelElements(){
		Set<MALElement> elements = new HashSet<MALElement>();
		for (Change change : changeRecords.values()) {
			elements.add(change.topLevelElement);
		}
		return elements;
	}

//	private Collection<MALElement> getAffectedElements(){
//		Set<MALElement> elements = new HashSet<MALElement>();
//		for (Change change : changeRecords.values()) {
//			elements.addAll(change.parentage);
//		}
//		return elements;
//	}

	private class Change {

		@SuppressWarnings("unused")
		MALElement targetElement;
		@SuppressWarnings("unused")
		String propertyName;
		MALElement topLevelElement;
		@SuppressWarnings("unused")
		List<MALElement> parentage;

		Change(MALElement targetElement,String propertyName){
			this.targetElement = targetElement;
			this.propertyName = propertyName;
			parentage = targetElement.getParentage();
			topLevelElement = targetElement.getTopLevelParent();
		}

	}

}
