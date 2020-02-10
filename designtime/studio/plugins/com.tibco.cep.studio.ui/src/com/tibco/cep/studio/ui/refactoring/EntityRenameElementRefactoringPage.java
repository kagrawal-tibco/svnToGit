package com.tibco.cep.studio.ui.refactoring;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.ui.actions.EntityRenameElementAction;
import com.tibco.cep.studio.ui.util.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class EntityRenameElementRefactoringPage extends RenameElementRefactoringPage {
	
	private EList<PropertyDefinition> list;
	private EntityRenameElementAction action;
	protected EntityRenameElementRefactoringPage(String name, EntityRenameElementAction action) {
		super(name);
		this.action = action;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.refactoring.RenameElementRefactoringPage#validateInherits(java.lang.String)
	 */
	protected boolean validateInherits(String text) {
		list = action.getList();
		if (isDuplicatePropertyDefinitionName(list, text)){
			setErrorMessage(Messages.getString("duplicate.property"));
			setPageComplete(false);
			return false;
		}
		return true;
	}
	
	/**
	 * @param list
	 * @param name
	 * @return
	 */
	protected boolean isDuplicatePropertyDefinitionName(EList<PropertyDefinition> list, String name){
		try{
			Iterator<PropertyDefinition> iterator = list.iterator();
			while(iterator.hasNext()){
				if(iterator.next().getName().equalsIgnoreCase(name)){
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
