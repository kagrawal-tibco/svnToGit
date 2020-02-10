/**
 * 
 */
package com.tibco.cep.decision.table.editors.dnd;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetEvent;

import org.eclipse.ui.IEditorInput;

import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decisionproject.ontology.PrimitiveHolder;
import com.tibco.cep.decisionproject.ontology.Property;

/**
 * Convert input data used for dragging from different sources.
 * <p>
 * This interface should be implemented by classes wishing to get callbacks
 * while doing DnD onto {@link DecisionTableEditor} for converting their input
 * data to the format the Decision Table editor expects.
 * </p>
 * 
 * @author aathalye
 * 
 */
public interface IDecisionTableDnDDataConverter {

    /**
     * Convert input data and return droppable data onto decision table editor.
     * <p>
     * See the @see for the return data type.
     * </p>
     * 
     * @see Property
     * @see PrimitiveHolder
     * @param dropTargetEvent
     * @param editorInput
     * @return
     */
    Object[] convertInputData(DropTargetEvent dropTargetEvent,
            IEditorInput editorInput);

    /**
     * Use {@link DropTargetDragEvent#rejectDrag()} to prevent the drag-drop
     * operation.
     * 
     * @param dropTargetEvent
     * @param editorInput
     */
    void dragEnter(DropTargetDragEvent dropTargetEvent, IEditorInput editorInput);

    /**
     * @param dropTargetEvent
     * @param editorInput
     */
    void dragExit(DropTargetEvent dropTargetEvent, IEditorInput editorInput);

    /**
     * Use {@link DropTargetDragEvent#rejectDrag()} to prevent the drag-drop
     * operation.
     * 
     * @param dropTargetEvent
     * @param editorInput
     */
    void dragOver(DropTargetDragEvent dropTargetEvent, IEditorInput editorInput);

    /**
     * Use {@link DropTargetDragEvent#rejectDrag()} to prevent the drag-drop
     * operation.
     * 
     * 
     * @param dropTargetEvent
     * @param editorInput
     */
    void dropActionChanged(DropTargetDragEvent dropTargetEvent,
            IEditorInput editorInput);
}
