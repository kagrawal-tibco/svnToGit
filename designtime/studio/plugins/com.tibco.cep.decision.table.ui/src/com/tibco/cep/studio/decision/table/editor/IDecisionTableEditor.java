package com.tibco.cep.studio.decision.table.editor;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.ui.IEditorPart;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.core.utils.DecisionTableColumnIdGenerator;
import com.tibco.cep.studio.decision.table.constraintpane.DecisionTable;
import com.tibco.cep.studio.decision.table.providers.DomainValueDataConverter;

public interface IDecisionTableEditor extends IEditorPart {
    /**
     * @return the table being edited
     */
    Table getTable();

    /**
     * callback after the table has been edited
     */
    void modified();

    /**
     * call {@link #modified()} asynchronously on the UI thread
     */
    void asyncModified();
    
    /**
     * finds the model for a cell in a table
     * 
     * @param column a column index of the cell
     * @param row a row index of the cell
     * @param nTable the table
     * @return the model for the cell at the given indexes in the table
     */
    Object getModelDataByPosition(int column, int row, NatTable nTable);

    /**
     * @return same as {@link #getDecisionTableDesignViewer()}
     * TODO replace all calls to this with {@link #getDecisionTableDesignViewer()} and remove
     */
    DecisionTableDesignViewer getDtDesignViewer();

    /**
     * @return the decision table design viewer
     */
    DecisionTableDesignViewer getDecisionTableDesignViewer();

    /**
     * @return whether the editor is enabled
     */
    boolean isEnabled();

    /**
     * @return a list of rows that should be highlighted
     */
    List<String> getRowsToHighlight();

    /**
     * @return true if the editor input is read only
     */
    boolean isEditorInputReadOnly();

	IProject getProject();

	void setRowsToHighlight(List<String> rowsToHighlight);

	DecisionTable getDecisionTable();

	void setDecisionTable(DecisionTable decisionTable);

	DropTargetListener createDropTargetListener(NatTable natTable,
			TableTypes tableType);

	void editorContentRestored();

    /**
     * @return the column ID generator for this editor
     */
    DecisionTableColumnIdGenerator getColumnIdGenerator();

    /**
     * @return the name of the project containing the file being edited
     */
    String getProjectName();

    /**
     * @param id an action id
     * @return the handler for that action
     */
    Action getActionHandler(String id);

    List<List<String>> getDomainValues(Column column);

	DomainValueDataConverter getDomainValueDataConverter(boolean conditionColumn,
			String separator);
    
}
