/**
 * 
 */
package com.tibco.cep.decision.table.command.impl;

import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.IMemento;
import com.tibco.cep.decision.table.command.IMetadataFeature;
import com.tibco.cep.decision.table.command.IUndoableCommand;
import com.tibco.cep.decision.table.model.dtmodel.Metadatable;
import com.tibco.cep.decision.table.model.dtmodel.Table;

public class UndoableMetadataCommand<E extends Enum<E> & IMetadataFeature, M extends Metadatable> extends GenericMetadataCommand<E, M>
        implements IUndoableCommand {

    /** The value to revert to during undo */
    private final String oldValue;

    /**
     * @param parent
     * @param metadatableFeature
     * @param ownerStack
     * @param metadataFeature
     * @param changedValue
     * @param featureType
     */
    public UndoableMetadataCommand(Table parent, M metadatableFeature, CommandStack<IExecutableCommand> ownerStack, E metadataFeature, String changedValue,
            String oldValue, String featureType) {
        super(parent, metadatableFeature, ownerStack, metadataFeature, changedValue, featureType);
        this.oldValue = oldValue;
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public void undo() {
        setMetaDataValue(oldValue);
    }

    @Override
    public void saveMemento(IMemento memento) {
        this.memento = memento;
    }

    @Override
    public Object getValue() {
        if (memento != null) {
            return memento.getValue();
        }
        return null;
    }

}
