package com.tibco.cep.dashboard.psvr.mal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.mal.model.MALBEViewsElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.store.PersistentStore;

public final class MALElementsCollector {
	
	private Map<String,MALElement> elements;
	
	private Map<String,String> links;
	
	private List<String> personalizedElements;
	
	public MALElementsCollector() {
		elements = new HashMap<String, MALElement>();
		links = new HashMap<String, String>();
		personalizedElements = new LinkedList<String>();
		//we add the skin by default
		for (MALSkinIndexer skinIndexer : MALSkinCache.getInstance().getAllInsightSkinIndexers()) {
			putAll(skinIndexer.elements());
		};			
	}
	
	public <T extends MALElement> MALElementsCollector(Collection<T> existingElements) {
		this();
		putAll(existingElements);
	}

	public <T extends MALElement> void putAll(Collection<T> existingElements) {
		for (MALElement existingElement : existingElements) {
			put(existingElement.getId(),existingElement);
		}
	}
	
	public void put(String id,MALElement element){
//		LoggingService.getRuntimeLogger().log(Level.INFO, "Attempting to add "+element+" to collection...");
		if (element.isTopLevelElement() == false){
//			LoggingService.getRuntimeLogger().log(Level.INFO, element+" is not top level element...");
			return;
		}
//		LoggingService.getRuntimeLogger().log(Level.INFO, "Adding "+element+" to collection...");
		elements.put(id,element);
		if (element instanceof MALBEViewsElement){
			MALBEViewsElement beViewsElement = (MALBEViewsElement) element;
			if (StringUtil.isEmptyOrBlank(beViewsElement.getOriginalElementIdentifier()) == false){
				//we have a personalization 
//				LoggingService.getRuntimeLogger().log(Level.INFO, "Adding "+element+" as personalization for "+beViewsElement.getOriginalElementIdentifier()+" to collection...");
				links.put(beViewsElement.getOriginalElementIdentifier(),beViewsElement.getId());
			}
		}		
	}
	
	public void remove(String id){
		MALElement element = elements.remove(id);
		if (element instanceof MALBEViewsElement ){
			MALBEViewsElement beViewsElement = (MALBEViewsElement) element;
			if (StringUtil.isEmptyOrBlank(beViewsElement.getOriginalElementIdentifier()) == false){
				//we have a personalization 
				links.remove(beViewsElement.getOriginalElementIdentifier());
			}			
			else {
				//a original element is being removed
				//we do nothing
			}
		}
	}

	public MALElement get(String id){
		MALElement element = elements.get(id);
//		LoggingService.getRuntimeLogger().log(Level.INFO, "Returning "+element+" from collection for "+id+"...");
		return element;
	}
	
	public Collection<MALElement> elements(){
		return Collections.unmodifiableCollection(elements.values());
	}
	
	public Collection<String> ids(){
		return Collections.unmodifiableCollection(elements.keySet());
	}

	public void retain(Collection<String> ids){
		elements.keySet().retainAll(ids);
	}
	
	public MALElement resolve(PersistentStore persistentStore, MALElement element) throws MALException{
		if (element == null){
			return null;
		}
		//PORT need a better logic to resolve non top level elements
		if (element.isTopLevelElement() == false){
			return element;
		}
		String identifier = persistentStore.getIdentifier(element);
		String personalizedId = links.get(identifier);
		if (personalizedId == null){
			return element;
		}
		MALElement personalizedElement = elements.get(personalizedId);
//		LoggingService.getRuntimeLogger().log(Level.INFO, "Personalizing "+personalizedElement+" for "+element);
		return personalizedElement;
	}
	
	public void personalized(MALElement element){
		personalizedElements.add(element.getId());
	}
	
	public boolean isPersonalized(MALElement element){
		return personalizedElements.contains(element.getId());
	}
	
//	@Override
//	public String toString() {
//		return elements.toString();
//	}
}
