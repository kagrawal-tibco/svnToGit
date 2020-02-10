package com.tibco.cep.studio.ui.forms.components;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.PropertyTableConstants;
/**
 * 
 * @author aasingh
 *
 */

public class EntityLabelProvider  {
	Entity entity  = null;
	
	public EntityLabelProvider(Entity entity){
		this.entity = entity;
	}


	public ITableLabelProvider getLabel(){
		   
		   ITableLabelProvider  LabelProvider = new ITableLabelProvider() {
			
			@Override
			public void removeListener(ILabelProviderListener listener) {
				
			}
			
			@Override
			public boolean isLabelProperty(Object element, String property) {
				
				return false;
			}
			
			@Override
			public void dispose() {
			
			}
			
			@Override
			public void addListener(ILabelProviderListener listener) {
		
			}
			
			@Override
			public String getColumnText(Object element, int columnIndex) {
				PropertyDefinition propDef=(PropertyDefinition)element;
				switch (columnIndex) {
				    case 0:
				      return propDef.getName();
				    case 1:
				    	if(entity instanceof Concept){
				    		if(propDef.getType().toString().equalsIgnoreCase("ContainedConcept")||propDef.getType().toString().equalsIgnoreCase("ConceptReference"))
					    		return propDef.getConceptTypePath();
					    	else{
					    	return	propDef.getType().toString();
					    	}
				    	}
				    	else {
				    	return	PropertyTableConstants.typeItems[propDef.getType().getValue()];
				    	}
				    case 2:
				    	if(entity instanceof Event){
				    		EList<DomainInstance> list=propDef.getDomainInstances();
					       	String resourcePath = "";
					       	int count=0;
					    	Iterator<DomainInstance> it=list.iterator();
					    	while(it.hasNext()){
					    		count++;
					    		if(count>1){
					    			resourcePath=((DomainInstance)it.next()).getResourcePath()+";"+resourcePath;
					    		}
					    		else{
					    			resourcePath=((DomainInstance)it.next()).getResourcePath()+resourcePath;
					    		}
					    		
					    	}
					    	return resourcePath;
				    	}
				    	else
				    		return "";
				    case 3: 
				    	return PropertyTableConstants.policyItems[propDef.getHistoryPolicy()];
				    case 4:
				    	return	String.valueOf(propDef.getHistorySize());
				    case 5:
				    	EList<DomainInstance> list=propDef.getDomainInstances();
				       	String resourcePath = "";
				       	int count=0;
				    	Iterator<DomainInstance> it=list.iterator();
				    	while(it.hasNext()){
				    		count++;
				    		if(count>1){
				    			resourcePath=((DomainInstance)it.next()).getResourcePath()+";"+resourcePath;
				    		}
				    		else{
				    			resourcePath=((DomainInstance)it.next()).getResourcePath()+resourcePath;
				    		}
				    		
				    	}
				    	
				    	return resourcePath;	
				    }
				    return null;
			}
			
			@Override
			public Image getColumnImage(Object element, int columnIndex) {
				PropertyDefinition propDef = (PropertyDefinition) element;
		    	String imageToRet = null ;
		    	if(propDef.getType().toString()!=null && propDef.getType().toString().equalsIgnoreCase("Int"))
		    		imageToRet=PropertyTableConstants.ICON_TOOLBAR_INTEGER;
		    	else if(propDef.getType().toString()!=null && propDef.getType().toString().equalsIgnoreCase("Boolean"))
		    		imageToRet=PropertyTableConstants.ICON_TOOLBAR_BOOLEAN;
		    	else if(propDef.getType().toString()!=null && propDef.getType().toString().equalsIgnoreCase("String"))
		    		imageToRet=PropertyTableConstants.ICON_TOOLBAR_STRING;
		    	else if(propDef.getType().toString()!=null && propDef.getType().toString().equalsIgnoreCase("Long"))
		    		imageToRet=PropertyTableConstants.ICON_TOOLBAR_LONG;
		    	else if(propDef.getType().toString()!=null && propDef.getType().toString().equalsIgnoreCase("Double"))
		    		imageToRet=PropertyTableConstants.ICON_TOOLBAR_DOUBLE;
		    	else if(propDef.getType().toString()!=null && propDef.getType().toString().equalsIgnoreCase("CONTAINEDCONCEPT"))
		        	imageToRet=PropertyTableConstants.ICON_TOOLBAR_CONCEPT;
		    	else if(propDef.getType().toString()!=null && propDef.getType().toString().equalsIgnoreCase("CONCEPTREFERENCE"))
		    		imageToRet=PropertyTableConstants.ICON_TOOLBAR_CONCEPTREFERENCE;
		    	else if(propDef.getType().toString()!=null && propDef.getType().toString().equalsIgnoreCase("DateTime")){
		    		imageToRet=PropertyTableConstants.ICON_TOOLBAR_DATE;
		    	}
		    	switch (columnIndex) {
		    	case 1:
		    		return StudioUIPlugin.getDefault().getImage(imageToRet);
		    	default:
		    		return null;
		    	}
			}
		};
		   
		  return LabelProvider; 
	   }
	
	
}