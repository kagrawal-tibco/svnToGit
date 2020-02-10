/**
 * 
 */
package com.tibco.cep.studio.ui.editors.utils;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.common.util.ModelNameUtil;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;

/**
 * @author mgujrath
 * 
 */
public class EntityCellModifier implements ICellModifier {

	private TableViewer viewer;
	private AbstractSaveableEntityEditorPart editor;
	private Entity entity;

	public EntityCellModifier(TableViewer tableViewer,
			AbstractSaveableEntityEditorPart editor) {
		this.viewer = tableViewer;
		this.editor = editor;
		this.entity = editor.getEntity();
	}

	@Override
	public boolean canModify(Object arg0, String arg1) {
		return true;
	}

	@Override
	public Object getValue(Object element, String property) {
		PropertyDefinition p = (PropertyDefinition) element;
		if (PropertyTableConstants.NAME.equals(property))
			return p.getName();
		else if (PropertyTableConstants.TYPE.toString().equals(property)) {
			if (entity instanceof Scorecard)
				return p.getType().getValue();
			else if (entity instanceof Event)
				return p.getType().getValue();
			else if (entity instanceof Concept) {
				if (p.getType().toString().equalsIgnoreCase("ContainedConcept")
						|| p.getType().toString()
								.equalsIgnoreCase("ConceptReference"))
					return p.getConceptTypePath();
				else
					return p.getType().toString();
			} else
				return null;
		} else if (PropertyTableConstants.DOMAIN.equals(property))
			return getDomain(p);
		else if (PropertyTableConstants.HISTORY.equals(property))
			return String.valueOf(p.getHistorySize());
		else if (PropertyTableConstants.MULTIPLE.equals(property))
			return p.isArray();
		else if (PropertyTableConstants.POLICY.equals(property))
			return p.getHistoryPolicy();
		else
			return null;
	}

	@Override
	public void modify(Object element, String property, Object value) {
		boolean flag = false;
		if (element instanceof Item)
			element = ((Item) element).getData();

		PropertyDefinition p = (PropertyDefinition) element;
		Object oldValue = null;
		if (PropertyTableConstants.NAME.equals(property)) {
			oldValue = p.getName(); 
			flag = validatePropertyDefnName( value, oldValue , entity);
			if (flag == true)
				p.setName((String) value);
			else
				p.setName((String)oldValue);
		} else if (PropertyTableConstants.TYPE.equals(property)) {
			
			if (entity instanceof Scorecard) {
				oldValue = p.getType();
				p.setType(PROPERTY_TYPES.get(Integer.parseInt(value.toString())));
			} else if (entity instanceof Event) {
				oldValue = p.getType().getValue();
				p.setType(PROPERTY_TYPES.get(Integer.parseInt(value.toString())));
			} else {
				oldValue = p.getType();
				String[] arr = value.toString().split("-");
				if (arr.length == 1
						&& PropertyTableConstants.validate(arr[0]) != null) {
					p.setType(PROPERTY_TYPES.get(arr[0].toString()));
					p.setConceptTypePath(null);
				} else if (arr.length == 2) {
					p.setType(PROPERTY_TYPES.get(arr[1].toString()));
					p.setConceptTypePath(arr[0].toString());
				}
			}
		} else if (PropertyTableConstants.MULTIPLE.equals(property)) {
			oldValue = p.isArray();
			p.setArray((Boolean) value);
		} else if (PropertyTableConstants.HISTORY.equals(property)) {
			Integer history = null;
			try {
				// Integer history= (Integer)value;
				history = Integer.parseInt((String) value);
			} catch (Exception e) {
				System.out.println("cannot cast string to Integer" + value);
				return;
			}
			oldValue = p.getHistorySize();
			p.setHistorySize(history);
		} else if (PropertyTableConstants.POLICY.equals(property)) {
			oldValue = p.getHistoryPolicy();
				p.setHistoryPolicy((Integer) value);
		} else if (PropertyTableConstants.DOMAIN.equals(property)) {
			oldValue = p.getConceptTypePath();
			EList<DomainInstance> list = p.getDomainInstances();
			String resourcePath = null;
			Iterator<DomainInstance> it = list.iterator();
			while (it.hasNext()) {
				resourcePath = ((DomainInstance) it.next()).getResourcePath();
			}
			p.setConceptTypePath(resourcePath);
		}
		if(value!=null&&oldValue!=null){
			if (!value.toString().equals(oldValue.toString())) {
				editor.modified();
			}
		}
		viewer.refresh();
	}

	private boolean validatePropertyDefnName(Object value, Object OldValue, Entity entity2 ) {
		final String name = (String)value;
		String oldName = (String) OldValue; 
		Entity entity =  entity2;
		EList<PropertyDefinition> list = null;
		if(name.equalsIgnoreCase(oldName)){
			return false;
		}
		if( entity instanceof Scorecard){
			Scorecard scorecard = (Scorecard) entity;
			list = scorecard.getProperties();
		}else if ( entity instanceof Event){
			Event event = (Event) entity;
			list = event.getAllUserProperties();
		}
		else if(entity instanceof Concept){
			Concept concept = (Concept) entity;
			list = concept.getAllProperties();
	    }else{
	    	System.out.println( "Type not supported by Entitytable");
	    }
		boolean validDef = isValidPropertyDefName(name);
		boolean keywordDef = isPropertyNameKeyword(name);
		boolean duplicateDef = isDuplicatePropertyDefinitionName(list, name);
		
		if(validDef && !duplicateDef && !keywordDef){
			return true;
		} else if(!validDef){		
			Display.getDefault().asyncExec(new Runnable(){
				public void run() {
					MessageDialog.openError(Display.getDefault().getActiveShell(), "Invalid Property", "Invalid property name '" + name +"'");
				}});
			return false;
		} else if (duplicateDef){
			Display.getDefault().asyncExec(new Runnable(){
				public void run() {
					MessageDialog.openError(Display.getDefault().getActiveShell(), "Duplicate Property", "Duplicate Property.");
				}});
			return false;
		}else if(keywordDef){
			Display.getDefault().asyncExec(new Runnable(){
				public void run() {
						MessageDialog.openError(Display.getDefault().getActiveShell(), "Invalid Property", "Cannot use reserved word '" + name +"'");
				}});
			return false;
		}
		return false;
	}
	
	private boolean isDuplicatePropertyDefinitionName(EList<PropertyDefinition> list, String name){
		try{
			Iterator<PropertyDefinition> iterator = list.iterator();
			int count = 0;
			while(iterator.hasNext()){
				if(iterator.next().getName().equalsIgnoreCase(name/*.trim()*/)){
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
    private boolean isValidPropertyDefName(String name){
    	try{
			if(name.trim().equalsIgnoreCase("")) {
				return false;
			}
			if(!ModelNameUtil.isValidIdentifier(name) || name.charAt(0)=='_'){
				return false; // validating the character
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
    }
    private boolean isPropertyNameKeyword(String name){
    	try{
			if(name.trim().equalsIgnoreCase("")) {
				return true;
			}
			if(EntityNameHelper.isKeyword(name)){
				return true; 
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
    }
	private boolean isValidPropertyDefinitionName(String name){
		try{
			if(name.trim().equalsIgnoreCase("")) {
				return false;
			}
			if(!EntityNameHelper.isValidBEEntityIdentifier(name/*.trim()*/)){
				return false; // validating the character
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	private String getDomain(PropertyDefinition pd) {
		EList<DomainInstance> list = pd.getDomainInstances();
		String resourcePath = "";
		int count = 0;
		Iterator<DomainInstance> it = list.iterator();
		while (it.hasNext()) {
			count++;
			if (count > 1) {
				resourcePath = ((DomainInstance) it.next()).getResourcePath()
						+ ";" + resourcePath;
			} else {
				resourcePath = ((DomainInstance) it.next()).getResourcePath()
						+ resourcePath;
			}
		}
		return resourcePath;
	}

}
