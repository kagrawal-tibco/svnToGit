/**
 * 
 */
package com.tibco.cep.decision.table.editors.dnd;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetEvent;

import org.eclipse.ui.IEditorInput;

import com.tibco.cep.studio.ui.navigator.model.ArgumentTransfer;

/**
 * Default DnD data converter.
 * 
 * @author aathalye
 * 
 */
public class DefaultDecisionTableDnDDataConverter implements
        IDecisionTableDnDDataConverter {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.cep.decision.table.editors.dnd.IDecisionTableDnDDataConverter
     * #convertInputData(java.awt.dnd.DropTargetEvent)
     */
    @Override
    public Object[] convertInputData(DropTargetEvent dropTargetEvent,
            IEditorInput editorInput) {
        return ArgumentTransfer.getInstance().getTransferData();
    }

    /**
     * @see com.tibco.cep.decision.table.editors.dnd.IDecisionTableDnDDataConverter#dragEnter(java.awt.dnd.DropTargetDragEvent,
     *      org.eclipse.ui.IEditorInput)
     * 
     * @param dropTargetEvent
     * @param editorInput
     */
    @Override
    public void dragEnter(DropTargetDragEvent dropTargetEvent,
            IEditorInput editorInput) {
        // TODO Auto-generated method stub

    }

    /**
     * @see com.tibco.cep.decision.table.editors.dnd.IDecisionTableDnDDataConverter#dragExit(java.awt.dnd.DropTargetEvent,
     *      org.eclipse.ui.IEditorInput)
     * 
     * @param dropTargetEvent
     * @param editorInput
     */
    @Override
    public void dragExit(DropTargetEvent dropTargetEvent,
            IEditorInput editorInput) {
        // TODO Auto-generated method stub

    }

    /**
     * @see com.tibco.cep.decision.table.editors.dnd.IDecisionTableDnDDataConverter#dragOver(java.awt.dnd.DropTargetDragEvent,
     *      org.eclipse.ui.IEditorInput)
     * 
     * @param dropTargetEvent
     * @param editorInput
     */
    @Override
    public void dragOver(DropTargetDragEvent dropTargetEvent,
            IEditorInput editorInput) {
        // TODO Auto-generated method stub

    }

    /**
     * @see com.tibco.cep.decision.table.editors.dnd.IDecisionTableDnDDataConverter#dropActionChanged(java.awt.dnd.DropTargetDragEvent,
     *      org.eclipse.ui.IEditorInput)
     * 
     * @param dropTargetEvent
     * @param editorInput
     */
    @Override
    public void dropActionChanged(DropTargetDragEvent dropTargetEvent,
            IEditorInput editorInput) {
        // TODO Auto-generated method stub

    }

}
