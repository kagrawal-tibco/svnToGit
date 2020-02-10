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
import com.tibco.cep.decision.table.command.IMetadataFeature;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Metadatable;
import com.tibco.cep.decision.table.model.dtmodel.Property;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;

/**
 * @author aathalye
 *
 */
public class GenericMetadataCommand<E extends Enum<E> & IMetadataFeature, M extends Metadatable> extends AbstractExecutableCommand<EObject> implements IIndexableCommand {
	
	private E metadataFeature;
	
	/**
	 * Updated value of the metadata feature.
	 */
	private String changedValue;
	
	/**
	 * Type of the feature.
	 */
	private String featureDataType;
	
	/**
	 * The {@link EObject} which implements {@link Metadatable}
	 * @see Table
	 * @see TableRule
	 */
	private M metadatableFeature;
	
	/**
	 * 
	 * @param parent
	 * @param metadatableFeature
	 * @param ownerStack
	 * @param metadataFeature
	 * @param changedValue
	 * @param featureType
	 */
	public GenericMetadataCommand(Table parent,
			                      M metadatableFeature,
			                      CommandStack<IExecutableCommand> ownerStack, 
			                      E metadataFeature,
			                      String changedValue,
			                      String featureType) {
		super(parent, metadatableFeature, ownerStack, null);
		
		this.metadatableFeature = metadatableFeature;
		
		this.metadataFeature = metadataFeature;
		
		this.changedValue = changedValue;
		
		this.featureDataType = featureType;
	}

	@Override
	public boolean canUndo() {
		return false;
	}

	@Override
	public void execute() {
		setMetaDataValue(changedValue);
	}

    protected void setMetaDataValue(String changedValue) {
        MetaData metaData = metadatableFeature.getMd();
		String propertyName = metadataFeature.getFeatureName();
		
		if (propertyName != null) {
			Property property = metaData.search(propertyName);
            if (property != null) {
				//Get existing value
				String existingValue = property.getValue();
				if (existingValue == null || !existingValue.equals(changedValue)) {
					property.setValue(changedValue);
				} else {
					//This command should be defunct.
					setDefunct(true);
				}
			} else {
				property = DtmodelFactory.eINSTANCE.createProperty();
				property.setName(propertyName);
				property.setType(featureDataType);
				property.setValue(changedValue);
				metaData.getProp().add(property);
			}
		}
    }

	@Override
	public List<EObject> getAffectedObjects() {
		return new ArrayList<EObject>(0);
	}

	@Override
	public boolean shouldDirty() {
		return true;
	}
}
