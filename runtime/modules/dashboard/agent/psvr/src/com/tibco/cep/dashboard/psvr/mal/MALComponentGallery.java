package com.tibco.cep.dashboard.psvr.mal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponentGalleryFolder;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALUserPreference;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * The <code>MALComponentGallery</code> maintains information about the 
 * component gallery. The <code>MALComponentGallery</code> can handle 
 * inherited component gallery. The <code>MALComponentGallery</code> handles 
 * inheritance by merging the inherited folder with the actual folder. 
 * The <code>MALComponentGallery</code> will not be able to merge inherited folder 
 * which has the same name of the actual folder. The <code>MALComponentGallery</code> 
 * provides a generic list of APIs which will be typically needed to access a 
 * tree like structure. The <code>MALComponentGallery</code> is meant to be exposed 
 * to the general consumption via a inherited class 
 * 
 * @author apatil
 *
 */
public class MALComponentGallery {
	
	protected static Logger LOGGER;
	protected static ExceptionHandler EXCEPTION_HANDLER;
	protected static MessageGenerator MESSAGE_GENERATOR;	
	
	private MALSession session;
	
    /**
     * The MDS element which represents the root category/folder
     */
    protected MALComponentGalleryFolder rawRootCategory;
    
    /**
     * The MAL element which represents the root category/folder
     */
    protected MALComponentGalleryCategory rootCategory;
    
    /**
     * The MDS element which represents the inherited category/folder
     */
    protected MALComponentGalleryFolder inheritedRawCategory;
    
    /**
     * The MAL element which represents the inherited category/folder
     */
    protected MALComponentGalleryCategory inheritedRootCategory;
    
    /**
     * The instance of <code>ComponentGalleryIndex</code> which maintains the index
     */
    protected ComponentGalleryIndex galleryIndex;
    
    //protected HashMap nameIndexMap;
    
    /**
     * The <code>LinkedHashMap</code> which maintains the list of gallery folders 
     */
    protected List<MALComponentGalleryCategory> mergedCategories;
	
    
    public MALComponentGallery(MALSession session) {
    	this.session = session;
    	MALComponentGalleryFolder roleLevelGalleryCategory = session.getRolePreference().getGallery();
    	MALUserPreference userPreference = session.getUserPreference();
		if (userPreference == null) {
			create(roleLevelGalleryCategory, false);
		} else {
			create(userPreference.getGallery(), true, roleLevelGalleryCategory, false);
		}    	
    }
    
    private void create(MALComponentGalleryFolder inheritedRawCategory,boolean inheritedCategoryEditable) {
        create(null,false,inheritedRawCategory,inheritedCategoryEditable);
    }
    
    private void create(MALComponentGalleryFolder rawRootCategory,boolean rootCategoryEditable,MALComponentGalleryFolder inheritedRawCategory,boolean inheritedCategoryEditable) {
    	galleryIndex = new ComponentGalleryIndex(LOGGER);
        mergedCategories = new ArrayList<MALComponentGalleryCategory>(2);
        if (inheritedRawCategory != null){
            setInheritedRootCategory(new MALComponentGalleryCategory(LOGGER,inheritedRawCategory,inheritedCategoryEditable,true));
        }
        if (rawRootCategory != null){
        	setRootCategory(new MALComponentGalleryCategory(LOGGER,rawRootCategory,rootCategoryEditable,true));
        }
    }

	public void setInheritedRootCategory(MALComponentGalleryCategory inheritedRootCategory) {
		if (inheritedRootCategory == null){
			throw new IllegalArgumentException();
		}
		this.inheritedRootCategory = inheritedRootCategory;
        this.inheritedRawCategory = inheritedRootCategory.rawCategory;
        buildIndex(inheritedRootCategory);
        mergedCategories.add(inheritedRootCategory);
        if (session.getUserPreference() != null) {
        	MALElement[] elements = session.getUserPreference().getElement();
        	List<MALComponent> components = new LinkedList<MALComponent>();
        	for (MALElement element : elements) {
				if (element instanceof MALComponent){
					components.add((MALComponent) element);
				}
			}
        	applyReplacements(components);	
        }
	}
	
	public void setRootCategory(MALComponentGalleryCategory rootCategory) {
		if (this.rootCategory != null){
			throw new IllegalStateException("root category is already set");
		}
		if (this.inheritedRawCategory == null || this.inheritedRootCategory == null){
			throw new IllegalStateException("inherited root category is not set");
		}
		if (rootCategory == null){
			throw new IllegalArgumentException();
		}
		this.rootCategory = rootCategory;
        this.rawRootCategory = rootCategory.rawCategory;
        buildIndex(rootCategory);
        mergedCategories.add(0,rootCategory);
	}

    /**
     * Indexes the <code>MALComponentGalleryFolder</code>. Includes the inherited 
     * category if not null
     * @throws MALException if indexing fails
     */
    protected void buildIndex(MALComponentGalleryCategory category) {
    	//index all components
    	Iterator<MALComponent> components = category.getComponents();
    	while (components.hasNext()) {
			MALComponent component = (MALComponent) components.next();
			galleryIndex.addComponent(component, category);
		}
    	Iterator<MALComponentGalleryCategory> categories = category.getCategories();
    	while (categories.hasNext()) {
			MALComponentGalleryCategory subFolder = (MALComponentGalleryCategory) categories.next();
			buildIndex(subFolder);
		}
    	category.addObserver(galleryIndex);
    }
    
//    protected void applyReplacements(MALComponentGalleryCategory folder){
//    	applyReplacements(folder.getComponents());
//    	Iterator<MALComponentGalleryCategory> subFolders = folder.getCategories();
//    	while (subFolders.hasNext()) {
//			MALComponentGalleryCategory subFolder = (MALComponentGalleryCategory) subFolders.next();
//			applyReplacements(subFolder);
//		}  
//    }
    
    protected void applyReplacements(Collection<MALComponent> components){
    	for (MALComponent component : components) {
			String originalElementIdentifier = component.getOriginalElementIdentifier();
			if (StringUtil.isEmptyOrBlank(originalElementIdentifier) == false){
				try {
					MALComponent referencedElement = (MALComponent) session.getElementByIdentifier(originalElementIdentifier);
					//this component is a replacement for the 'referenced element'
					List<MALComponentGalleryCategory> categories = getCategoriesFor(referencedElement.getId());
					for (MALComponentGalleryCategory category : categories) {
						category.replace(referencedElement,component);
					}
				} catch (ElementNotFoundException e) {
					//INFO ignoring ElementNotFoundException in applyReplacements 
				} catch (MALException e) {
					//INFO ignoring MALException in applyReplacements
				}
			}
		}    	
    }
    
    @SuppressWarnings("unused")
	private void hideReplacements(Set<String> replacementids){
    	for (String replacementid : replacementids) {
			MALComponent replacement = galleryIndex.getComponentByID(replacementid);
			Set<MALComponentGalleryCategory> originalFolders = galleryIndex.getOriginalCategoriesFor(replacement);
			for (MALComponentGalleryCategory folder : originalFolders) {
				folder.hideComponent(replacement);
			}
		}
    }
	
	public MALComponentGalleryCategory getInheritedRootCategory(){
    	return inheritedRootCategory;
    }
    
	public MALComponentGalleryCategory getRootCategory() {
		return rootCategory;
	}    

	/**
     * Returns an <code>iterator</code> of the top level categories
     * @return an <code>iterator</code>
     */
    public Iterator<MALComponentGalleryCategory> getTopLevelCategories(){
        return mergedCategories.iterator();
    }
    
    public Iterator<String> getComponentIDs(){
    	return galleryIndex.getComponentIDs();
    }
    
    /**
     * Searches for a element using the elementID.  
     * @param elementID The id of the element
     * @return The element if found else <code>null</code>
     */
    public MALComponent searchComponent(String componentID){
        return galleryIndex.getComponentByID(componentID);
    }
    
    /**
     * Searches for a element using the elementname.  
     * @param elementName The name of the element
     * @return The element if found else <code>null</code>
     */    
    public MALComponent searchComponentByName(String componentName){
        return galleryIndex.getComponentByName(componentName);
    }
    
    public List<MALComponentGalleryCategory> getCategoriesFor(String componentID){
    	return getCategoriesFor(componentID,false);
    }

    public List<MALComponentGalleryCategory> getCategoriesFor(String componentID,boolean includeHiddenCategories){
    	MALComponent component = searchComponent(componentID);
    	if (component != null){
    		return galleryIndex.getCategoriesFor(component,includeHiddenCategories);
    	}
    	return Collections.emptyList();
    }    
    
    /**
     * Destroys the gallery
     */
    public void destroy() {
        if (rootCategory != null){
            rootCategory.destroy();
        }
        if (inheritedRootCategory != null){
        	inheritedRootCategory.destroy();
        }
        galleryIndex.destroy();
    }

	public long getLastModifiedTime() {
		return galleryIndex.getLastModifiedTime();
	}
}