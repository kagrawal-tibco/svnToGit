package com.tibco.cep.dashboard.psvr.mal;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.dashboard.psvr.mal.managers.MALElementManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALRolePreferenceManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALViewManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALRolePreference;
import com.tibco.cep.dashboard.psvr.mal.model.MALUserPreference;
import com.tibco.cep.dashboard.psvr.mal.model.MALView;
import com.tibco.cep.dashboard.psvr.mal.store.GlobalIdentity;
import com.tibco.cep.dashboard.psvr.mal.store.Identity;
import com.tibco.cep.dashboard.psvr.mal.store.PersistentStore;
import com.tibco.cep.dashboard.psvr.mal.store.PersistentStoreFactory;
import com.tibco.cep.dashboard.psvr.mal.store.RoleIdentity;
import com.tibco.cep.dashboard.psvr.mal.store.UserIdentity;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Logger;

public final class MALSession {

	private static ThreadLocal<MALSession> threadLocalSession = new ThreadLocal<MALSession>();

	static Logger LOGGER;

	SecurityToken token;

	Principal principal;

	PersistentStore persistentStore;

	PersistentStore roleLvlpersistentStore;

	MALElementsCollector elementsCollector;

	MALRolePreference rolePreference;

	MALUserPreference userPreference;

	MALTransaction transaction;

	public MALSession(SecurityToken token) throws MALException, ElementNotFoundException{
		this.token = token;
		this.principal = this.token.getPreferredPrincipal();
		if (token.isSystem() == true){
			elementsCollector = new MALElementsCollector();
			persistentStore = PersistentStoreFactory.getInstance().getStore(GlobalIdentity.getInstance());
		}
		else if (principal == null){
			throw new IllegalArgumentException("No preferred prinicpal has been set on "+token);
		}
		else {
			elementsCollector = new MALElementsCollector();
			GlobalIdentity globalIdentity = GlobalIdentity.getInstance();
			RoleIdentity roleIdentity = new RoleIdentity(globalIdentity,principal.getName());
			UserIdentity userIdentity = new UserIdentity(roleIdentity,token.getUserID());
			persistentStore = PersistentStoreFactory.getInstance().getStore(userIdentity);
			//we keep the role level persistent store separate from the user level one
			roleLvlpersistentStore = PersistentStoreFactory.getInstance().getStore(roleIdentity);
			//first we load the role preference
			loadRolePreference();
			//we load the user preference first
			loadUserPreference();
		}
	}

	private void loadRolePreference() throws MALException, ElementNotFoundException {
		List<MALElement> elements = roleLvlpersistentStore.getElementsByType(MALRolePreferenceManager.DEFINITION_TYPE, elementsCollector);
		for (MALElement element : elements) {
			MALRolePreference possibleRolePreference = (MALRolePreference) element;
			if (principal.getName().equals(possibleRolePreference.getRole()) == true) {
				rolePreference = possibleRolePreference;
				return;
			}
 		}
		rolePreference = (MALRolePreference) roleLvlpersistentStore.getElementByTypeAndName(MALRolePreferenceManager.DEFINITION_TYPE, principal.getName(),elementsCollector);
	}

	private void loadUserPreference() throws MALException, ElementNotFoundException {
		//if the we are dealing with a non system token and we have personal elements in the pstore, then we load user preference
		if (token.isSystem() == false && persistentStore.getElementIds().isEmpty() == false){
			//we have a valid user level storage
			userPreference = new MALUserPreference(token.getUserID(),persistentStore,rolePreference, elementsCollector);
		}
	}

	SecurityToken getToken(){
		return token;
	}

	Principal getPrincipal(){
		return principal;
	}

	public MALElementManager getManager(String definitionType) throws MALException{
		return MALElementManagerFactory.getInstance().getManager(definitionType);
	}

	MALElement createElement(MALElement parent,String definitionType,String name) throws MALException{
		return getManager(definitionType).create(parent, name);
	}

//	public MALElement getElementById(String id) throws MALException, ElementNotFoundException {
//		if (rolePreference.getId().equals(id) == true){
//			return rolePreference;
//		}
//		if (userPreference != null && userPreference.getId().equals(id) == true){
//			return userPreference;
//		}
//		MALElement element = elementsCollector.get(id);
//		if (element == null){
//			throw new ElementNotFoundException();
//		}
//		return element;
//	}
//
//	public MALElement getElementByName(String name) throws MALException, ElementNotFoundException {
//		if (rolePreference.getName().equals(name) == true){
//			return rolePreference;
//		}
//		if (userPreference != null && userPreference.getId().equals(name) == true){
//			return userPreference;
//		}
//		return persistentStore.getElementByName(name,elementsCollector);
//	}
//
//	public MALElement getElementByScopeName(String scopename) throws MALException, ElementNotFoundException {
//		return null;
//	}

	public List<MALElement> getElementsByType(String type) throws MALException {
		List<MALElement> elements = new ArrayList<MALElement>();
		if (type.equals(MALRolePreferenceManager.DEFINITION_TYPE) == true){
			elements.add(rolePreference);
			return elements;
		}
		if (type.equals(MALUserPreference.DEFINITION_TYPE) == true){
			if (userPreference != null) {
				elements.add(userPreference);
			}
			return elements;
		}
		return persistentStore.getElementsByType(type,elementsCollector);
	}

	public MALRolePreference getRolePreference() {
		return rolePreference;
	}

	public MALUserPreference getUserPreference() {
		return userPreference;
	}

	public MALView getView() throws MALException, ElementNotFoundException{
		MALView viewsConfig = null;
		if (userPreference != null){
			viewsConfig = userPreference.getView();
		}
		else if (rolePreference != null){
			viewsConfig = rolePreference.getView();
		}
		if (viewsConfig == null){
			throw new ElementNotFoundException("No "+MALViewManager.DEFINITION_TYPE+" found for "+token+" using "+principal);
		}
		return viewsConfig;
	}

	public MALElement getElementByIdentifier(String identifier) throws MALException, ElementNotFoundException {
		return persistentStore.getElementByIdentifier(identifier, elementsCollector);
	}

	public String getIdentifier(MALElement element) throws MALException {
		return persistentStore.getIdentifier(element);
	}

	public Identity getIdentity(){
		return persistentStore.getIdentity();
	}

	public MALTransaction beginTransaction() throws MALTransactionException{
		if (transaction != null){
			throw new MALTransactionException(MALTransactionException.CAUSE_OP.CREATION,"Transaction already running");
		}
		transaction = new MALTransaction(this);
		transaction.acquireThread();
		return transaction;
	}

	public MALTransaction getTransaction(){
		transaction.acquireThread();
		return transaction;
	}

	public void close() {
		if (transaction != null){
			transaction.close();
		}
	}

	public void attachToThread() {
		threadLocalSession.set(this);
	}

	public void deattachFromThread() {
		threadLocalSession.remove();
	}

	public static MALSession getCurrentSession() {
		MALSession session = threadLocalSession.get();
		if (session == null){
			throw new IllegalStateException("No MALSession has been attached to "+Thread.currentThread());
		}
		return session;
	}


}