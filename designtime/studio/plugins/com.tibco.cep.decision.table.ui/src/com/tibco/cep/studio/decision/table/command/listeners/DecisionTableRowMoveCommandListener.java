/**
 * 
 */
package com.tibco.cep.studio.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;

import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.IMoveCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.MoveCommand;
import com.tibco.cep.decision.table.command.memento.TableRuleStateMemento;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.decision.table.editor.DTBodyLayerStack;
import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.utils.DecisionTableUtil;

import ca.odell.glazedlists.EventList;

/**
 * Handle backend model updates for row moves.
 *
 */
public class DecisionTableRowMoveCommandListener implements ICommandCreationListener<MoveCommand<TableRuleStateMemento>, TableRuleStateMemento> {
	
	public enum ReorderType {
		MOVE,
		SORT_ALL_BY_ID,
		RESET_ALL_IDS
	}
	
	/**
	 * Reference to the editor we use
	 */	
	private IDecisionTableEditor decisionTableSWTEditor;
	private TableRuleSet targetRuleSet;
	private NatTable targetTable;
	private int fromRowIndex;
	private int toRowIndex;

	private List<TableRuleStateMemento> affectedObjects;
	private ReorderType reorderType;
	private EditingDomain editingDomain;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	@Override
	public Object commandCreated(CommandEvent<MoveCommand<TableRuleStateMemento>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IMoveCommand) {
			IMoveCommand command = (IMoveCommand)source;
			TableTypes tableType = command.getTableType();
			DecisionTableDesignViewer dv = decisionTableSWTEditor.getDecisionTableDesignViewer();
			Table parentTable = dv.getTable();
			this.targetRuleSet = (tableType == TableTypes.DECISION_TABLE) ? dv.getTable().getDecisionTable() : parentTable.getExceptionTable();
			EventList<TableRule> targetList = (tableType == TableTypes.DECISION_TABLE) ? dv.getDecisionTableEventList(): dv.getExceptionTableEventList();
			Object returnObj = null;
			switch (reorderType) {
			case RESET_ALL_IDS:
			{
				performReset(parentTable, targetList);
				break;
			}
				
			case SORT_ALL_BY_ID: {
				performSortAllByID(targetList);
				break;
			}

			case MOVE:
				returnObj = performMove(parentTable, targetList);
				break;
				
			default:
				break;
			}
			
			decisionTableSWTEditor.asyncModified();
			targetTable.refresh();
//			Display.getDefault().asyncExec(new Runnable() {
//				
//				@Override
//				public void run() {
//					targetTable.fireLayerEvent(new StructuralRefreshEvent(bodyLayer));
//					bodyLayer.getSelectionLayer().doCommand(new SelectRowsCommand(bodyLayer, 0, toRowIndex, false, false));
//				}
//			});
			return returnObj;
	
		}
		return null;
	}


	private void performSortAllByID(EventList<TableRule> targetList) {
		Comparator<TableRule> comp = new Comparator<TableRule>() {
			
			@Override
			public int compare(TableRule o1, TableRule o2) {
				int val1 = Integer.valueOf(o1.getId());
				int val2 = Integer.valueOf(o2.getId());
				
				return val1 > val2 ? 1 : -1; // should never be equal
			}
		};
		
		targetList.getReadWriteLock().writeLock().lock();
		TableRuleSet targetRuleSetCopy = EcoreUtil.copy(targetRuleSet);
		ECollections.sort(targetRuleSetCopy.getRule(), comp);
		Command emfCommand = getSetCommand(targetList, targetRuleSetCopy); 
//				new SetCommand(getEditingDomain(), targetRuleSet, targetRuleSet.eClass().getEStructuralFeature(DtmodelPackage.TABLE_RULE_SET__RULE), targetRuleSetCopy.getRule());
		if (emfCommand.canExecute()) {
			getEditingDomain().getCommandStack().execute(emfCommand);
		}
//				targetList.sort(comp);
		targetList.clear(); // need to clear/re-add since we copied the original set
		targetList.addAll(targetRuleSet.getRule());

		targetList.getReadWriteLock().writeLock().unlock();
	}


	private EditingDomain getEditingDomain() {
		if (editingDomain == null) {
			editingDomain = new AdapterFactoryEditingDomain(new AdapterFactoryImpl(), new BasicCommandStack());
		}
		return editingDomain;
	}


	private void performReset(Table parentTable, EventList<TableRule> targetList) {
		targetList.getReadWriteLock().writeLock().lock();
		Command cmd = getResetCommand(parentTable, targetList);
		if (cmd.canExecute()) {
			getEditingDomain().getCommandStack().execute(cmd);
		}
		targetList.getReadWriteLock().writeLock().unlock();
	}


	private Command getResetCommand(Table parentTable, final EventList<TableRule> targetList) {
		TableRuleSet targetRuleSetCopy = EcoreUtil.copy(targetRuleSet);
		DecisionTableUtil.resetRuleIDs(targetRuleSetCopy, parentTable.getOwnerProjectName(), parentTable.getPath());
		Command command = getSetCommand(targetList, targetRuleSetCopy);
		return command;
	}


	private SetCommand getSetCommand(final EventList<TableRule> targetList, TableRuleSet targetRuleSetCopy) {
		return new SetCommand(getEditingDomain(), targetRuleSet, targetRuleSet.eClass().getEStructuralFeature(DtmodelPackage.TABLE_RULE_SET__RULE), targetRuleSetCopy.getRule()) {
			@Override
			public void doExecute() {
				super.doExecute();
				targetList.clear();
				targetList.addAll(targetRuleSet.getRule());
			}
			
			@Override
			public void doUndo() {
				super.doUndo();
				targetList.clear();
				targetList.addAll(targetRuleSet.getRule());
			}
		};
	}


	private TableRule performMove(Table parentTable, EventList<TableRule> targetList) {
		TableRule rule = targetList.get(fromRowIndex);
		final DTBodyLayerStack<TableRule> bodyLayer = (DTBodyLayerStack<TableRule>) ((GridLayer)targetTable.getLayer()).getBodyLayer();
		final boolean resetRuleIDs = !DecisionTableUIPlugin.getDefault()
				.getPreferenceStore().getBoolean(PreferenceConstants.ALWAYS_MAINTAIN_RULE_IDS);
//		boolean reuseRuleIDs = !DecisionTableUIPlugin.getDefault()
//				.getPreferenceStore().getBoolean(PreferenceConstants.REUSE_RULE_IDS);
		
		targetList.getReadWriteLock().writeLock().lock();
		Command cmd = null;
		if (toRowIndex == -1) {
//			targetList.remove(rule);
//			targetList.add(rule);
			int toIdx = targetRuleSet.getRule().size()-1;
			cmd = getMoveCommand(parentTable, targetList, rule, resetRuleIDs, toIdx);
//			targetRuleSet.getRule().move(toIdx, targetRuleSet.getRule().indexOf(rule));
		} else {
			TableRule tableRule = targetList.get(toRowIndex);
			int toIdx = targetRuleSet.getRule().indexOf(tableRule);
			cmd = getMoveCommand(parentTable, targetList, rule, resetRuleIDs, toIdx);
		}
		if (cmd.canExecute()) {
			getEditingDomain().getCommandStack().execute(cmd);
		}
		targetList.getReadWriteLock().writeLock().unlock();
		
		TableRuleStateMemento TableRuleStateMemento = new TableRuleStateMemento(rule, fromRowIndex);			
		affectedObjects.add(TableRuleStateMemento);
		return rule;
	}


	private Command getMoveCommand(final Table parentTable, final EventList<TableRule> targetList, TableRule rule,
			final boolean resetRuleIDs, int toIdx) {
		Command moveCmd = new org.eclipse.emf.edit.command.MoveCommand(getEditingDomain(), targetRuleSet.getRule(), rule, toIdx) {
			private int fromIdx;

			@Override
			public void doExecute() {
				this.fromIdx = targetList.indexOf(this.value);
				super.doExecute();
				if (resetRuleIDs) {
					performReset(parentTable, targetList);
				} else {
					targetList.remove(this.value);
					if (toRowIndex == -1) {
						targetList.add((TableRule) this.value);
					} else {
						targetList.add(toRowIndex, (TableRule) this.value);
					}
				}
			}
			
			@Override
			public void doUndo() {
				super.doUndo();
				if (resetRuleIDs) {
					performReset(parentTable, targetList);
				} else {
					targetList.remove(this.value);
					targetList.add(fromIdx, (TableRule) this.value);
				}
			}
		};
		return moveCmd;
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	@Override
	public void commandUndone(CommandEvent<MoveCommand<TableRuleStateMemento>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IMoveCommand) {
			IMoveCommand command = (IMoveCommand)source;
			TableTypes tableType = command.getTableType();
			Table tableEModel = command.getParent();
			this.targetRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();			
			DecisionTableDesignViewer dv = decisionTableSWTEditor.getDecisionTableDesignViewer();
			EventList<TableRule> targetList = (tableType == TableTypes.DECISION_TABLE) ? dv.getDecisionTableEventList(): dv.getExceptionTableEventList();

			if (getEditingDomain().getCommandStack().canUndo()) {
				targetList.getReadWriteLock().writeLock().lock();
				getEditingDomain().getCommandStack().undo();
				targetList.getReadWriteLock().writeLock().unlock();
			}
			
			if (affectedObjects.size() > 0) {
				targetTable.refresh();
				affectedObjects.clear();
			}	
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#getAffectedObjects()
	 */
	@Override
	public List<TableRuleStateMemento> getAffectedObjects() {
		return affectedObjects;
	}
	
	/**
	 * 
	 * @param projectName
	 * @param tableEModel
	 * @param fieldToMove
	 * @param decisionDataModel
	 */
	public DecisionTableRowMoveCommandListener(String projectName,
													Table tableEModel,
													IDecisionTableEditor decisionTableEditor,
													NatTable targetTable,
													int fromRowIndex,
													int toRowIndex) {
		this(projectName, tableEModel, decisionTableEditor, targetTable, ReorderType.MOVE);
		this.fromRowIndex = fromRowIndex;
		this.toRowIndex = toRowIndex;
	}


	public DecisionTableRowMoveCommandListener(String projectName,
			Table tableEModel,
			IDecisionTableEditor decisionTableEditor,
			NatTable targetTable,
			ReorderType type) {
		this.targetTable = targetTable;
		this.affectedObjects = new ArrayList<TableRuleStateMemento>();
		this.decisionTableSWTEditor = decisionTableEditor;
		this.reorderType = type;
	}
}
