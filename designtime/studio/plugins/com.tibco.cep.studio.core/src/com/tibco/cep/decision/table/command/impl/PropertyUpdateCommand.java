/**
 * 
 */
package com.tibco.cep.decision.table.command.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.command.AbstractExecutableCommand;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.IIndexableCommand;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

/**
 * Command For updates to cell level properties.
 * @author aathalye
 *
 */
public class PropertyUpdateCommand extends AbstractExecutableCommand<EObject> implements IIndexableCommand {
	
	/**
	 * The feature to update.
	 */
	private PROPERTY_FEATURE property_feature;
	
	/**
	 * The changed value of the feature
	 */
	private Object changedValue;
	
	/**
	 * The model object whose features are to be updated
	 */
	private TableRuleVariable tableRuleVariable;
	
	public PropertyUpdateCommand(Table parent, 
			                     TableRuleVariable commandReceiver,
			                     CommandStack<IExecutableCommand> ownerStack, 
			                     PROPERTY_FEATURE propertyFeature,
			                     Object changedValue) {
		super(parent, commandReceiver, ownerStack, null);
		
		this.tableRuleVariable = commandReceiver;
		
		this.property_feature = propertyFeature;
		
		this.changedValue = changedValue;
	}
	
	public static enum PROPERTY_FEATURE {
		ENABLED,
		COMMENT;
	}

	@Override
	public boolean canUndo() {
		return false;
	}

	@Override
	public void execute() {
		switch (property_feature) {
		
		case ENABLED :
			//Get old value
			boolean isEnabled = tableRuleVariable.isEnabled();
			
			if (isEnabled == (Boolean)changedValue) {
				setDefunct(true);
			} else {
				tableRuleVariable.setEnabled((Boolean)changedValue);
			}
			break;
			
		case COMMENT :
			//Get old value
			String oldComment = tableRuleVariable.getComment();
			String newComment = changedValue.toString();
			
			if (oldComment == null) {
				oldComment = "";
			}
			if (oldComment.equals(newComment)) {
				setDefunct(true);
			} else {
				tableRuleVariable.setComment(newComment);
			}
			break;
		}
	}


	/**
	 * @return the property_feature
	 */
	public final PROPERTY_FEATURE getPropertyFeature() {
		return property_feature;
	}

	@Override
	public boolean shouldDirty() {
		return true;
	}
	
	@Override
	public List<EObject> getAffectedObjects() {
		List<EObject> affectedObjects = new ArrayList<EObject>(0);
		affectedObjects.add(tableRuleVariable);
		return affectedObjects;
	}
}
