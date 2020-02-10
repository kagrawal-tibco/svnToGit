package com.tibco.cep.dashboard.psvr.mal.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALElementManagerFactory;
import com.tibco.cep.dashboard.psvr.mal.MALElementsCollector;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.managers.MALElementManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALViewManager;
import com.tibco.cep.dashboard.psvr.mal.store.PersistentStore;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;

public class MALUserPreference extends MALBEViewsElement implements Serializable {

	public static final String DEFINITION_TYPE = "UserPreference";

	private static final long serialVersionUID = -7744524850133525273L;

	private MALComponentGalleryFolder gallery;

	private MALView view;

	private List<MALElement> _elementList;

	private MALRolePreference rolePreference;

	public MALUserPreference(String userid, PersistentStore userLvlPersistentStore,MALRolePreference rolePreference, MALElementsCollector elementsCollector) throws MALException, ElementNotFoundException {
		_elementList = new LinkedList<MALElement>();
		setDefinitionType(DEFINITION_TYPE);
		setId(Integer.toString((userid+rolePreference.getName()).hashCode()));
		setName(userid);
		this.rolePreference = rolePreference;
		//we load the gallery first from user level persistence store
		Collection<String> ids = userLvlPersistentStore.getElementIds();
		if (ids != null) {
			for (String elementID : ids) {
				MALElement element = userLvlPersistentStore.getElementById(elementID, elementsCollector);
				if (element instanceof MALComponentGalleryFolder) {
					gallery = (MALComponentGalleryFolder) element;
				}
				else {
					addElement(element);
				}
			}
		}
		MALElementsCollector userLevelCollector = new MALElementsCollector();
		//add components from passed in elements collector
		for (MALElement element : elementsCollector.elements()) {
			if (element instanceof MALComponent){
				userLevelCollector.put(element.getId(),element);
			}
		}
		//add all user level elements
		userLevelCollector.putAll(_elementList);
		//we will create copy of the role preference's beviews since we will probably change it
		MALElementManager beViewsConfigManager = MALElementManagerFactory.getInstance().getManager(MALViewManager.DEFINITION_TYPE);
		view = (MALView) beViewsConfigManager.load(userLvlPersistentStore,(BEViewsElement) rolePreference.getView().getPersistedObject(), userLevelCollector);
		beViewsConfigManager.applyPersonalizations(userLvlPersistentStore, view, userLevelCollector);
		addPropertyChangeListener(this);
		elementsCollector.putAll(_elementList);
	}

	public MALRolePreference getRolePreference() {
		return rolePreference;
	}

	public void setRolePreference(MALRolePreference rolePreference) {
		this.rolePreference = rolePreference;
	}

	/**
	 * Returns the value of field 'beview'.
	 *
	 * @return MALView
	 * @return the value of field 'beview'.
	 */
	public com.tibco.cep.dashboard.psvr.mal.model.MALView getView() {
		return this.view;
	}

	/**
	 * Returns the value of field 'gallery'.
	 *
	 * @return MALComponentGalleryFolder
	 * @return the value of field 'gallery'.
	 */
	public com.tibco.cep.dashboard.psvr.mal.model.MALComponentGalleryFolder getGallery() {
		return this.gallery;
	}

	/**
	 * Sets the value of field 'beview'.
	 *
	 * @param beview
	 *            the value of field 'beview'.
	 */
	public void setView(com.tibco.cep.dashboard.psvr.mal.model.MALView beview) {
		java.lang.Object oldBeview = this.view;
		this.view = beview;
		notifyPropertyChangeListeners("beview", oldBeview, this.view);
	}

	/**
	 * Sets the value of field 'gallery'.
	 *
	 * @param gallery
	 *            the value of field 'gallery'.
	 */
	public void setGallery(com.tibco.cep.dashboard.psvr.mal.model.MALComponentGalleryFolder gallery) {
		java.lang.Object oldGallery = this.gallery;
		this.gallery = gallery;
		notifyPropertyChangeListeners("gallery", oldGallery, this.gallery);
	}

	/**
	 * Method addElement
	 *
	 *
	 *
	 * @param vElement
	 */
	public void addElement(com.tibco.cep.dashboard.psvr.mal.model.MALElement vElement) throws java.lang.IndexOutOfBoundsException {
		_elementList.add(vElement);
		notifyPropertyChangeListeners("_elementList", null, _elementList);
	}

	/**
	 * Method addElement
	 *
	 *
	 *
	 * @param index
	 * @param vElement
	 */
	public void addElement(int index, com.tibco.cep.dashboard.psvr.mal.model.MALElement vElement) throws java.lang.IndexOutOfBoundsException {
		_elementList.add(index, vElement);
		notifyPropertyChangeListeners("_elementList", null, _elementList);
	}

	/**
	 * Method clearElement
	 *
	 */
	public void clearElement() {
		_elementList.clear();
		notifyPropertyChangeListeners("_elementList", null, _elementList);
	}

	/**
	 * Method enumerateElement
	 *
	 *
	 *
	 * @return Enumeration
	 */
	@SuppressWarnings("rawtypes")
	public java.util.Enumeration enumerateElement() {
		return new org.exolab.castor.util.IteratorEnumeration(_elementList.iterator());
	}

	/**
	 * Method getElement
	 *
	 *
	 *
	 * @param index
	 * @return MALElement
	 */
	public com.tibco.cep.dashboard.psvr.mal.model.MALElement getElement(int index) throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _elementList.size())) {
			throw new IndexOutOfBoundsException();
		}

		return (com.tibco.cep.dashboard.psvr.mal.model.MALElement) _elementList.get(index);
	}

	/**
	 * Method getElement
	 *
	 *
	 *
	 * @return MALElement
	 */
	public com.tibco.cep.dashboard.psvr.mal.model.MALElement[] getElement() {
		int size = _elementList.size();
		com.tibco.cep.dashboard.psvr.mal.model.MALElement[] mArray = new com.tibco.cep.dashboard.psvr.mal.model.MALElement[size];
		for (int index = 0; index < size; index++) {
			mArray[index] = (com.tibco.cep.dashboard.psvr.mal.model.MALElement) _elementList.get(index);
		}
		return mArray;
	}

	/**
	 * Method getElementCount
	 *
	 *
	 *
	 * @return int
	 */
	public int getElementCount() {
		return _elementList.size();
	}

	/**
	 * Method removeElement
	 *
	 *
	 *
	 * @param vElement
	 * @return boolean
	 */
	public boolean removeElement(com.tibco.cep.dashboard.psvr.mal.model.MALElement vElement) {
		boolean removed = _elementList.remove(vElement);
		notifyPropertyChangeListeners("_elementList", null, _elementList);
		return removed;
	}

	/**
	 * Method setElement
	 *
	 *
	 *
	 * @param index
	 * @param vElement
	 */
	public void setElement(int index, com.tibco.cep.dashboard.psvr.mal.model.MALElement vElement) throws java.lang.IndexOutOfBoundsException {
		// -- check bounds for index
		if ((index < 0) || (index > _elementList.size())) {
			throw new IndexOutOfBoundsException();
		}
		_elementList.set(index, vElement);
		notifyPropertyChangeListeners("_elementList", null, _elementList);
	}

	/**
	 * Method setElement
	 *
	 *
	 *
	 * @param componentArray
	 */
	public void setElement(com.tibco.cep.dashboard.psvr.mal.model.MALElement[] componentArray) {
		// -- copy array
		_elementList.clear();
		for (int i = 0; i < componentArray.length; i++) {
			_elementList.add(componentArray[i]);
		}
		notifyPropertyChangeListeners("_elementList", null, _elementList);
	}

}
