/**
 * 
 */
package com.tibco.cep.studio.decision.table.constraintpane;

import java.util.List;

import com.tibco.cep.decision.table.command.memento.ColumnNameStateMemento;
import com.tibco.cep.studio.decision.table.constraintpane.event.ConstraintsTableEvent;
import com.tibco.cep.studio.decision.table.constraintpane.event.ConstraintsTableEvent.ConstraintsTableEventType;
import com.tibco.cep.studio.decision.table.constraintpane.event.IConstraintsTableListener;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

/**
 * This class should only handle notifications.
 * <p>
 * The UI updates should be handled by {@link DecisionTableAnalyzerView}
 * </p>
 * @author aathalye
 *
 */
public class ConstraintsTableListener implements IConstraintsTableListener {
	
	private static final String CLASS = ConstraintsTableListener.class.getName();
	
	/**
	 * The originating class reference is a must
	 */
	private DecisionTableAnalyzerView analyzerView;
	

	protected ConstraintsTableListener(DecisionTableAnalyzerView analyzerView) {
		this.analyzerView = analyzerView;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.constraintpane.event.IConstraintsTableListener#cellAdded(com.tibco.cep.decision.table.constraintpane.event.ConstraintsTableEvent)
	 */
	public void cellAdded(ConstraintsTableEvent event) {
		Object source = event.getSource();
		final DecisionTable constraintsTable = event.getConstraintsTable();
		if (source instanceof Cell) {
			Cell newCell = (Cell)source;
			DecisionTableUIPlugin.debug(CLASS, "Calling cell Added {0}", newCell);
			if (newCell.getCellType() == Cell.ACTION_CELL_TYPE)
				//No action to be taken for updating UI components
				return;
			
			//Update Filters also
			List<Filter> allColumnFilters = constraintsTable.getAllFilters(newCell);
			
			if (event.getEventType() == ConstraintsTableEventType.CELL_ADD) {
				analyzerView.updateUIComponents(newCell.getAlias(), constraintsTable, allColumnFilters);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.constraintpane.event.IConstraintsTableListener#cellRemoved(com.tibco.cep.decision.table.constraintpane.event.ConstraintsTableEvent)
	 */
	public void cellRemoved(ConstraintsTableEvent event) {
		Object source = event.getSource();
		final DecisionTable constraintsTable = event.getConstraintsTable();
		if (source instanceof Cell) {
			Cell removedCell = (Cell)source;
			if (removedCell.getCellType()  == Cell.ACTION_CELL_TYPE) {
				//No action to be taken for updating UI components
				return;
			}
			DecisionTableUIPlugin.debug(CLASS, "Calling cell Removed for {0} ", removedCell);
			
			List<Filter> allFilters = constraintsTable.getAllFilters(removedCell.getAlias());
			for (final Filter filter : allFilters) {
				//Let each filter handle whether to remove or not itself.
				filter.removeCell(removedCell);
			}
			// donot update UI for cell updates
			if (event.getEventType() == ConstraintsTableEventType.CELL_REMOVE) {
				analyzerView.updateUIComponents(removedCell.getAlias(), constraintsTable, allFilters);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.constraintpane.event.IConstraintsTableListener#cellUpdated(com.tibco.cep.decision.table.constraintpane.event.ConstraintsTableEvent)
	 */
	@SuppressWarnings("unchecked")
	public void cellUpdated(ConstraintsTableEvent event) {
		Object source = event.getSource();
		final DecisionTable constraintsTable = event.getConstraintsTable();
		
		if (event.getEventType() == ConstraintsTableEventType.CELL_PROPERTY_UPDATE) {
			Cell refCell = (Cell)source;
			analyzerView.updateUIComponents(refCell.getAlias(), constraintsTable, null);
		} else if (source instanceof List) {
			List<Cell> updatedCells = (List<Cell>)source;
			List<Cell> oldCells = (List<Cell>)event.getOldSourceObject();
			
			for (Cell c : oldCells) {
				ConstraintsTableEvent removeEvent = 
		    		new ConstraintsTableEvent(c, constraintsTable, ConstraintsTableEventType.CELL_UPDATE);
				cellRemoved(removeEvent);
			}
			
			for (Cell c : updatedCells) {
				ConstraintsTableEvent addEvent = 
		    		new ConstraintsTableEvent(c, constraintsTable, ConstraintsTableEventType.CELL_UPDATE);
				cellAdded(addEvent);
			}
			
			// need some cell to get the column alias
			Cell refCell = (oldCells.get(0) != null) ? oldCells.get(0) : updatedCells.get(0);
			analyzerView.updateUIComponents(refCell.getAlias(), constraintsTable, null);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.constraintpane.event.IConstraintsTableListener#aliasRenamed(com.tibco.cep.decision.table.constraintpane.event.ConstraintsTableEvent)
	 */
	@Override
	public void aliasRenamed(ConstraintsTableEvent event) {
		Object source = event.getSource();
		final DecisionTable constraintsTable = event.getConstraintsTable();
		if (source instanceof ColumnNameStateMemento) {
			ColumnNameStateMemento columnNameStateMemento = (ColumnNameStateMemento)source;
			//Get old alias and new one
			String oldAlias = (String)columnNameStateMemento.getValue();
	    	String newAlias = columnNameStateMemento.getNewColumnName();
			analyzerView.updateUIComponents(oldAlias, constraintsTable, null);
			analyzerView.updateUIComponents(newAlias, constraintsTable, null);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.constraintpane.event.IConstraintsTableListener#aliasRemoved(com.tibco.cep.decision.table.constraintpane.event.ConstraintsTableEvent)
	 */
	@Override
	public void aliasRemoved(ConstraintsTableEvent event) {
		Object source = event.getSource();
		final DecisionTable constraintsTable = event.getConstraintsTable();
		String removedAlias = (String)source;
		// The UI has been updated when last cell gets removed 
		// from the column
		analyzerView.updateUIComponents(removedAlias, constraintsTable, null);
	}
}
