package com.tibco.cep.dashboard.psvr.mal;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

class ComponentGalleryIndex implements Observer {

	protected Logger logger;

	private Map<String, IndexObject> idBasedRawIndex;

	private Map<String, String> nameToIdIndex;
	
	private long lastModifiedTime;

	ComponentGalleryIndex(Logger logger) {
		this.logger = logger;
		idBasedRawIndex = new HashMap<String, IndexObject>();
		nameToIdIndex = new HashMap<String, String>();
		lastModifiedTime = -1;
	}

	IndexObject addComponent(MALComponent component, MALComponentGalleryCategory category) {
		IndexObject indexObject = idBasedRawIndex.get(component.getId());
		if (indexObject == null) {
			indexObject = new IndexObject();
			indexObject.component = component;
			indexObject.originalCategories = new HashSet<MALComponentGalleryCategory>();
			indexObject.categorysHiddenIn = new HashSet<MALComponentGalleryCategory>();
			indexObject.categorysVisibleIn = new HashSet<MALComponentGalleryCategory>();
			idBasedRawIndex.put(component.getId(), indexObject);
			nameToIdIndex.put(component.getName(), component.getId());
		}
		indexObject.originalCategories.add(category);
		if (indexObject.categorysVisibleIn.add(category) == false) {
			throw new IllegalStateException(component + " is already present in " + category.getName());
		}
		lastModifiedTime = System.currentTimeMillis();
		return indexObject;
	}

	void removeComponent(MALComponent component, MALComponentGalleryCategory category) {
		IndexObject indexObject = idBasedRawIndex.get(component.getId());
		if (indexObject != null) {
			indexObject.categorysVisibleIn.remove(category);
			if (indexObject.categorysHiddenIn.isEmpty() == true && indexObject.categorysVisibleIn.isEmpty() == true) {
				idBasedRawIndex.remove(component.getId());
			}
		}
		lastModifiedTime = System.currentTimeMillis();
	}

	IndexObject hideComponent(MALComponent component, MALComponentGalleryCategory category) {
		IndexObject indexObject = idBasedRawIndex.get(component.getId());
		if (indexObject != null) {
			boolean removed = indexObject.categorysVisibleIn.remove(category);
			if (removed == true) {
				if (indexObject.categorysHiddenIn.add(category) == false) {
					throw new IllegalStateException(component + " is already hidden in " + category.getName());
				}
			}
		}
		lastModifiedTime = System.currentTimeMillis();
		return indexObject;
	}

	void unhideComponent(MALComponent component, MALComponentGalleryCategory category) {
		IndexObject indexObject = idBasedRawIndex.get(component.getId());
		if (indexObject != null) {
			boolean removed = indexObject.categorysHiddenIn.remove(category);
			if (removed == true) {
				if (indexObject.categorysVisibleIn.add(category) == false) {
					throw new IllegalStateException(component + " is already visible in " + category.getName());
				}
			}
		}
		lastModifiedTime = System.currentTimeMillis();
	}

	void replaceComponent(MALComponent component, MALComponent replacement, MALComponentGalleryCategory category) {
		IndexObject compIndexObject = hideComponent(component, category);
		IndexObject replacementIndexObject = idBasedRawIndex.get(replacement.getId());
		if (replacementIndexObject == null) {
			replacementIndexObject = addComponent(replacement, category);
		}
		else {
			//remove all categories the replacement is visible in
			replacementIndexObject.categorysHiddenIn.addAll(replacementIndexObject.categorysVisibleIn);
			//make sure that the hidden categories contains only those categories in which the replacement is replacing  
			replacementIndexObject.categorysHiddenIn.removeAll(compIndexObject.categorysHiddenIn);
			//add all hidden category of original to visible category list
			replacementIndexObject.categorysVisibleIn.addAll(compIndexObject.categorysHiddenIn);
		}
		lastModifiedTime = System.currentTimeMillis();
	}
	
	long getLastModifiedTime() {
		return lastModifiedTime;
	}

	Iterator<String> getComponentIDs(){
		return new Iterator<String>(){
			
			private Iterator<String> idIterator = idBasedRawIndex.keySet().iterator();
			
			private String nextValidId = null;

			@Override
			public boolean hasNext() {
				nextValidId = null;
				while (idIterator.hasNext() == true) {
					nextValidId = idIterator.next();
					if (idBasedRawIndex.get(nextValidId).categorysVisibleIn.isEmpty() == false){
						break;
					}
					else {
						logger.log(Level.INFO, "Skipping "+idBasedRawIndex.get(nextValidId).component+" since it is not visible in any categories...");
						nextValidId = null;
					}
				}
				return nextValidId != null;
			}

			@Override
			public String next() {
				logger.log(Level.INFO, "Returning "+idBasedRawIndex.get(nextValidId).component+"...");
				return nextValidId;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("remove");
			}
			
		};
	}

	MALComponent getComponentByID(String componentID) {
		return getComponentByID(componentID, false);
	}

	MALComponent getComponentByName(String componentName) {
		return getComponentByName(componentName, false);
	}

	MALComponent getComponentByID(String componentID, boolean searchHiddenComps) {
		IndexObject indexObject = idBasedRawIndex.get(componentID);
		if (indexObject != null) {
			if (indexObject.categorysVisibleIn.isEmpty() == true) {
				if (searchHiddenComps == true) {
					if (indexObject.categorysHiddenIn.isEmpty() == false) {
						return indexObject.component;
					}
				}
				return null;
			}
			return indexObject.component;
		}
		return null;
	}

	MALComponent getComponentByName(String componentName, boolean searchHiddenComps) {
		return getComponentByID(nameToIdIndex.get(componentName), searchHiddenComps);
	}

	List<MALComponentGalleryCategory> getCategoriesFor(MALComponent component) {
		return getCategoriesFor(component, false);
	}

	List<MALComponentGalleryCategory> getCategoriesFor(MALComponent component, boolean includeHiddenCategories) {
		IndexObject indexObject = idBasedRawIndex.get(component.getId());
		if (indexObject != null) {
			List<MALComponentGalleryCategory> categories = new LinkedList<MALComponentGalleryCategory>(indexObject.categorysVisibleIn);
			if (includeHiddenCategories == true) {
				categories.addAll(indexObject.categorysHiddenIn);
			}
			return categories;
		}
		return Collections.emptyList();
	}

	Set<MALComponentGalleryCategory> getOriginalCategoriesFor(MALComponent component) {
		IndexObject indexObject = idBasedRawIndex.get(component);
		if (indexObject != null) {
			return Collections.unmodifiableSet(indexObject.originalCategories);
		}
		return Collections.emptySet();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof MALComponentGalleryCategory) {
			MALComponentGalleryCategory category = (MALComponentGalleryCategory) o;
			ComponentCategoryUpdate update = (ComponentCategoryUpdate) arg;
			switch (update.operation) {
			case Add:
				addComponent(update.component, category);
				break;
			case Remove:
				removeComponent(update.component, category);
				break;
			case Hide:
				hideComponent(update.component, category);
				break;
			case Unhide:
				unhideComponent(update.component, category);
				break;
			case Replace:
				replaceComponent(update.component, update.replacement, category);
				break;
			default:
				logger.log(Level.WARN, "Unknown operation[" + update.operation + "] encountered while processing update from " + o);
			}
		}
	}

	void destroy() {
		this.idBasedRawIndex.clear();
		this.nameToIdIndex.clear();
	}
	
	private class IndexObject {
		MALComponent component;
		Set<MALComponentGalleryCategory> originalCategories;
		Set<MALComponentGalleryCategory> categorysVisibleIn;
		Set<MALComponentGalleryCategory> categorysHiddenIn;
	}
}
