package com.tibco.cep.dashboard.psvr.mal;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponentGalleryFolder;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 * 
 */
public class MALComponentGalleryCategory extends Observable {

	protected Logger logger;

	protected String id;
	protected String name;
	protected boolean editable;
	protected boolean displayAscending;
	protected MALComponentGalleryFolder rawCategory;

	// raw storage
	private Map<String, RawIndexObject> rawIndex;
	
	// display storage
	private List<MALComponentGalleryCategory> categoryDisplayIndex;
	private NameBasedComponentGallerCategoryComparator componentCategoryComparator;
	
	private List<MALComponent> compDisplayIndex;
	private DisplayNameBasedComponentComparator componentComparator;
	
	
	public MALComponentGalleryCategory(Logger logger,MALComponentGalleryFolder category, boolean editable, boolean displayAscending) {
		this.logger = logger;
		this.rawCategory = category;
		this.id = this.rawCategory.getId();
		this.name = this.rawCategory.getName();
		this.displayAscending = displayAscending;
		this.rawIndex = new HashMap<String, RawIndexObject>();
		this.categoryDisplayIndex = new LinkedList<MALComponentGalleryCategory>();
		this.compDisplayIndex = new LinkedList<MALComponent>();
		this.editable = editable;
		parseCategory(rawCategory);
		componentComparator = new DisplayNameBasedComponentComparator(displayAscending);
		Collections.sort(compDisplayIndex, componentComparator);
		componentCategoryComparator = new NameBasedComponentGallerCategoryComparator(displayAscending);
		Collections.sort(categoryDisplayIndex, componentCategoryComparator);
	}

	protected void parseCategory(MALComponentGalleryFolder folder) {
		MALComponent[] components = folder.getComponent();
		for (MALComponent component : components) {
			rawIndex.put(component.getId(), new RawIndexObject(component));
			compDisplayIndex.add(component);
		}
		MALComponentGalleryFolder[] subFolders = folder.getSubFolder();
		for (MALComponentGalleryFolder subFolder : subFolders) {
			MALComponentGalleryCategory subCategory = new MALComponentGalleryCategory(logger,subFolder, editable, displayAscending);
			rawIndex.put(subCategory.id, new RawIndexObject(subCategory));
			if (contains(subCategory) == true){
				throw new IllegalStateException("Two categories with same name ["+subCategory.name+"] found under "+name);
			}
			categoryDisplayIndex.add(subCategory);
		}
	}

	public final String getId() {
		return id;
	}

	public final String getName() {
		return name;
	}

	public final boolean isEditable() {
		return editable;
	}

	public Iterator<MALComponent> getComponents() {
		return compDisplayIndex.iterator();
	}

	public Iterator<MALComponentGalleryCategory> getCategories() {
		return categoryDisplayIndex.iterator();
	}

	public MALComponentGalleryCategory getCategoryById(String categoryID) {
		RawIndexObject rawIndexObject = rawIndex.get(categoryID);
		if (rawIndexObject.hidden == true) {
			return null;
		}
		return rawIndexObject.subFolder;
	}

	public MALComponent getComponentById(String compID) {
		RawIndexObject rawIndexObject = rawIndex.get(compID);
		if (rawIndexObject.hidden == true) {
			return null;
		}
		return rawIndexObject.component;
	}
	
	public void addCategory(MALComponentGalleryCategory category){
		if (editable == false) {
			throw new IllegalStateException("cannot add folder[" + category.getName() + "] to non-editable folder[" + this.name + "]");
		}
		rawIndex.put(category.id, new RawIndexObject(category));
		if (contains(category) == true){
			throw new IllegalStateException("Two categories with same name ["+category.name+"] found under "+name);
		}
		categoryDisplayIndex.add(category);
		Collections.sort(categoryDisplayIndex, componentCategoryComparator);
	}
	
	public void removeCategory(MALComponentGalleryCategory category,boolean purgeAllComponents){
		if (editable == false) {
			throw new IllegalStateException("cannot remove folder[" + category.getName() + "] from non-editable folder[" + this.name + "]");
		}
		if (category.compDisplayIndex.isEmpty() == false && purgeAllComponents == false){
			throw new IllegalStateException("cannot remove non empty folder[" + category.getName() + "] from non-editable folder[" + this.name + "]");
		}
		RawIndexObject removedRawIndexObject = rawIndex.remove(category.id);
		if (removedRawIndexObject == null) {
			throw new IllegalStateException("folder ["+category+"] not found in folder[" + this.name + "]");
		}
		if (purgeAllComponents == true){
			List<MALComponent> components = new LinkedList<MALComponent>(removedRawIndexObject.subFolder.compDisplayIndex);
			for (MALComponent component : components) {
				removedRawIndexObject.subFolder.removeComponent(component);
			}
		}
		categoryDisplayIndex.remove(category);
	}
	

	public void addComponent(MALComponent component) throws MALException {
		if (editable == false) {
			throw new IllegalStateException("cannot add component[" + component + "] to non-editable folder[" + this.name + "]");
		}
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Adding " + component + " to " + name);
		}
		rawIndex.put(component.getId(), new RawIndexObject(component));
		compDisplayIndex.add(component);
		Collections.sort(compDisplayIndex, componentComparator);
		rawCategory.addComponent(component);
		setChanged();
		notifyObservers(new ComponentCategoryUpdate(component, null, ComponentCategoryUpdate.Operation.Add));
	}

	public void removeComponent(MALComponent component) {
		if (editable == false) {
			throw new IllegalStateException("cannot remove component[" + component + "] to non-editable folder[" + this.name + "]");
		}
		RawIndexObject removedRawIndexObj = rawIndex.remove(component.getId());
		if (removedRawIndexObj == null) {
			throw new IllegalStateException(component+" not found in folder[" + this.name + "]");
		}
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Removing " + component + " from " + name);
		}
		compDisplayIndex.remove(component);
		//remove does not need resorting
		rawCategory.removeComponent(component);
		setChanged();
		notifyObservers(new ComponentCategoryUpdate(component, null, ComponentCategoryUpdate.Operation.Remove));
	}

	protected void hideComponent(MALComponent component) {
		hideComponentWithNoNotification(component);
		setChanged();
		notifyObservers(new ComponentCategoryUpdate(component, null, ComponentCategoryUpdate.Operation.Hide));
	}

	protected final void hideComponentWithNoNotification(MALComponent component) {
		RawIndexObject rawIndexObj = rawIndex.get(component.getId());
		if (rawIndexObj == null){
			throw new IllegalStateException(component+" not found in folder[" + this.name + "]");
		}
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Hiding " + component + " in " + name);
		}
		rawIndexObj.hidden = true;
	}
	
	protected void unhideComponent(MALComponent component) {
		RawIndexObject rawIndexObj = rawIndex.get(component.getId());
		if (rawIndexObj == null){
			throw new IllegalStateException(component+" not found in folder[" + this.name + "]");
		}		
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Unhiding " + component + " in " + name);
		}
		rawIndexObj.hidden = false;
		setChanged();
		notifyObservers(new ComponentCategoryUpdate(component, null, ComponentCategoryUpdate.Operation.Unhide));
	}

	public void replace(MALComponent component, MALComponent replacement) {
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Adding " + replacement + " as customization over " + component + " under " + name);
		}
		RawIndexObject compRawIndexObj = rawIndex.get(component.getId());
		if (compRawIndexObj == null){
			throw new IllegalStateException(component+" not found in folder[" + this.name + "]");
		}			
		// check if component is actually a replacement itself
		if (compRawIndexObj.replacement == true) {
			// component is a replacement, so we get the component it was replacing
			// make sure original component is hidden
			if (rawIndex.get(compRawIndexObj.replacementOrOriginal.getId()).hidden == false) {
				throw new IllegalStateException(compRawIndexObj.replacementOrOriginal + " was originally replaced by " + component + " but it is not hidden");
			}			
			// remove component
			rawIndex.remove(component.getId());
			// create new raw index obj for replacement
			RawIndexObject replacementRawIndexObj = new RawIndexObject(replacement);
			// mark it as replacement
			replacementRawIndexObj.replacement = true;
			// attach to the original component replaced
			replacementRawIndexObj.replacementOrOriginal = compRawIndexObj.replacementOrOriginal;
			// add to raw index
			rawIndex.put(replacement.getId(), replacementRawIndexObj);
			// remove component from display index
			compDisplayIndex.remove(component);
			// add replacement to display index
			compDisplayIndex.add(replacement);
			//sort the display list based on display name
			Collections.sort(compDisplayIndex, componentComparator);
			setChanged();
			notifyObservers(new ComponentCategoryUpdate(component, null,ComponentCategoryUpdate.Operation.Remove));
			setChanged();
			notifyObservers(new ComponentCategoryUpdate(replacementRawIndexObj.replacementOrOriginal, replacement, ComponentCategoryUpdate.Operation.Replace));
		} else {
			// we do not have a replacement in place
			// hide component
			hideComponentWithNoNotification(component);
			// create new raw index obj for replacement
			RawIndexObject replacementRawIndexObj = new RawIndexObject(replacement);
			// mark it as replacement
			replacementRawIndexObj.replacement = true;
			// attach to the original component replaced
			replacementRawIndexObj.replacementOrOriginal = component;
			// add to raw index
			rawIndex.put(replacement.getId(), replacementRawIndexObj);
			//remove component from display index
			compDisplayIndex.remove(component);
			// add replacement to display index
			compDisplayIndex.add(replacement);
			//sort the display list based on display name
			Collections.sort(compDisplayIndex, componentComparator);
			setChanged();
			notifyObservers(new ComponentCategoryUpdate(component,replacement, ComponentCategoryUpdate.Operation.Replace));
		}
	}

	public MALComponent resetComponent(MALComponent component) {
		// check if component is a replacement
		RawIndexObject rawIndexObject = rawIndex.get(component.getId());
		if (rawIndexObject == null){
			throw new IllegalStateException(component+" not found in folder[" + this.name + "]");
		}
		if (rawIndexObject.replacement == false) {
			throw new IllegalStateException(component+" is not overriding any other component in folder[" + this.name + "]");
		}
		// yes we are a replacement , we shd reset the replacement
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Resetting " + rawIndexObject.component + " to " + rawIndexObject.replacementOrOriginal + " in " + name);
		}
		rawIndex.remove(component.getId());
		setChanged();
		notifyObservers(new ComponentCategoryUpdate(component, null, ComponentCategoryUpdate.Operation.Remove));
		unhideComponent(rawIndexObject.replacementOrOriginal);
		return rawIndexObject.replacementOrOriginal;
	}

	protected void destroy() {
		compDisplayIndex.clear();
		categoryDisplayIndex.clear();
		for (RawIndexObject rawIndexObject : rawIndex.values()) {
			if (rawIndexObject.subFolder != null) {
				rawIndexObject.subFolder.destroy();
			}
		}
		rawIndex.clear();
		deleteObservers();
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer("MALComponentGalleryCategory[");
		buffer.append("id=" + id);
		buffer.append(",name=" + name);
		buffer.append("]");
		return buffer.toString();
	}
	
	public boolean isOwnedBy(SecurityToken token) {
		return rawCategory.getOwner().equals(token.getUserID());
	}
	
	public void resetChanges(){
		if (rawCategory != null) {
			rawCategory.resetPropertyTracking();
		}
	}
	
	private boolean contains(MALComponentGalleryCategory category){
		for (MALComponentGalleryCategory existingCategory : categoryDisplayIndex) {
			if (existingCategory.name.equals(category.name) == true){
				return true;
			}
		}
		return false;
	}

	private class RawIndexObject {
		MALComponentGalleryCategory subFolder;
		MALComponent component;
		boolean hidden;
		boolean replacement;
		MALComponent replacementOrOriginal;

		RawIndexObject(MALComponentGalleryCategory subFolder) {
			this.subFolder = subFolder;
			hidden = false;
		}

		RawIndexObject(MALComponent component) {
			this.component = component;
			hidden = false;
		}
	}

	private class DisplayNameBasedComponentComparator implements Comparator<MALComponent> {
		
		private boolean displayAscending;

		DisplayNameBasedComponentComparator(boolean displayAscending) {
			this.displayAscending = displayAscending;
		}

		@Override
		public int compare(MALComponent o1, MALComponent o2) {
			int comparision = o1.getDisplayName().compareTo(o2.getDisplayName());
			if (displayAscending == true) {
				return comparision;
			}
			return -(comparision);
		}

	}
	
	private class NameBasedComponentGallerCategoryComparator implements Comparator<MALComponentGalleryCategory>{
		
		private boolean displayAscending;

		NameBasedComponentGallerCategoryComparator(boolean displayAscending) {
			this.displayAscending = displayAscending;
		}

		@Override
		public int compare(MALComponentGalleryCategory o1, MALComponentGalleryCategory o2) {
			int comparision = o1.name.compareTo(o2.name);
			if (displayAscending == true) {
				return comparision;
			}
			return -(comparision);
		}		
	}
}